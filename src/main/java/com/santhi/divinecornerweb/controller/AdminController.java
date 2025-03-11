package com.santhi.divinecornerweb.controller;


import com.santhi.divinecornerweb.model.Product;
import com.santhi.divinecornerweb.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;

@Controller
@RequestMapping("/admin/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // ✅ Show product form
    @GetMapping("/new")
    public String showProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "product-form";
    }

    // ✅ Save product and handle image upload
    @PostMapping("/save")
    public String saveProduct(@ModelAttribute Product product,
                              @RequestParam("imageFile") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                // ✅ Save image in `static/images/`
                String fileName = file.getOriginalFilename();
                Path filePath = Paths.get("src/main/resources/static/images", fileName);
                Files.write(filePath, file.getBytes());

                // ✅ Store the filename in the database
                product.setImageName(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        productService.saveProduct(product);
        return "redirect:/admin/products";
    }
    // ✅ Show all products
    @GetMapping
    public String showProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "products";
    }
}