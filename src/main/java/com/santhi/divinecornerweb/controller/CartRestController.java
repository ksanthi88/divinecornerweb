package com.santhi.divinecornerweb.controller;

import com.santhi.divinecornerweb.model.Product;
import com.santhi.divinecornerweb.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartRestController {

    private final CartService cartService;

    public CartRestController(CartService cartService) {
        this.cartService = cartService;
    }

    //  Get all products in the cart
    @GetMapping("/items")
    public ResponseEntity<List<Product>> getCartItems(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(401).build();
        }
        List<Product> products = cartService.getCartProducts(userDetails.getUsername());
        return ResponseEntity.ok(products);
    }

    //  Add a product to the cart
    @PostMapping("/add/{productId}")
    public ResponseEntity<String> addToCart(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long productId) {
        if (userDetails == null) {
            return ResponseEntity.status(401).body("Unauthorized: Please login first.");
        }
        cartService.addToCart(userDetails.getUsername(), productId);
        return ResponseEntity.ok("Product added to cart successfully!");
    }

    //  Remove a product from the cart
    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<String> removeFromCart(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long productId) {
        if (userDetails == null) {
            return ResponseEntity.status(401).body("Unauthorized: Please login first.");
        }
        cartService.removeFromCart(userDetails.getUsername(), productId);
        return ResponseEntity.ok("Product removed from cart.");
    }

    // Clear the cart
    @DeleteMapping("/clear")
    public ResponseEntity<String> clearCart(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(401).body("Unauthorized: Please login first.");
        }
        cartService.clearCart(userDetails.getUsername());
        return ResponseEntity.ok("Cart cleared successfully.");
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getCartCount(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.ok(0); // Return 0 if not logged in
        }
        int count = cartService.getCartItemCount(userDetails.getUsername());
        return ResponseEntity.ok(count);
    }
}
