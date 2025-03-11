package com.santhi.divinecornerweb.controller;

import com.santhi.divinecornerweb.model.Product;
import com.santhi.divinecornerweb.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String showProductsPage(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "products"; // ✅ This should match the Thymeleaf template name
    }
}
