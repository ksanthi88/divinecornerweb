package com.santhi.divinecornerweb.controller;

import com.santhi.divinecornerweb.model.Cart;
import com.santhi.divinecornerweb.model.Product;
import com.santhi.divinecornerweb.model.User;
import com.santhi.divinecornerweb.service.CartService;
import com.santhi.divinecornerweb.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/cart")
@SessionAttributes("cartCount")
public class CartController {

    private final CartService cartService;
    private final UserService userService;

    public CartController(CartService cartService, UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
    }

    @ModelAttribute("cartCount")
    public int getCartCount(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails != null) {
            return cartService.getCartItemCount(userDetails.getUsername());
        }
        return 0;
    }

    @GetMapping
    public String viewCart(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails == null) {
            System.out.println("No user logged in");
            model.addAttribute("cartProducts", Collections.emptyMap());
            return "cart";
        }

        User user = userService.findByEmail(userDetails.getUsername());
        if (user == null) {
            System.out.println("User not found in DB");
            model.addAttribute("cartProducts", Collections.emptyMap());
            return "cart";
        }

        Cart cart = cartService.findByUser(user).orElse(new Cart());

        if (cart.getProducts() == null || cart.getProducts().isEmpty()) {
            System.out.println("Cart is empty or products list is null");
            model.addAttribute("cartProducts", Collections.emptyMap());
        } else {
            // Group products by count (quantity)
            Map<Product, Long> productQuantityMap = cart.getProducts().stream()
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

            System.out.println("Cart Products: " + productQuantityMap);
            model.addAttribute("cartProducts", productQuantityMap);
        }

        return "cart";
    }

    // Add a product to the cart
    @PostMapping("/add/{productId}")
    public String addToCart(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long productId) {
        if (userDetails != null) {
            cartService.addToCart(userDetails.getUsername(), productId);
        }
        return "redirect:/cart";
    }

    // Remove a product from the cart
    @PostMapping("/remove/{productId}")
    public String removeFromCart(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long productId) {
        if (userDetails != null) {
            cartService.removeFromCart(userDetails.getUsername(), productId);
        }
        return "redirect:/cart";
    }

    // Clear the cart
    @PostMapping("/clear")
    public String clearCart(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails != null) {
            cartService.clearCart(userDetails.getUsername());
        }
        return "redirect:/cart";
    }



}