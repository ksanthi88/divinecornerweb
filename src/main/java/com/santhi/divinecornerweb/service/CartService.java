package com.santhi.divinecornerweb.service;

import com.santhi.divinecornerweb.model.Cart;
import com.santhi.divinecornerweb.model.Product;
import com.santhi.divinecornerweb.model.User;
import com.santhi.divinecornerweb.repository.CartRepository;
import com.santhi.divinecornerweb.repository.ProductRepository;
import com.santhi.divinecornerweb.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    // Add a product to the cart
    @Transactional
    public void addToCart(String email, Long productId) {
        User user = userRepository.findByEmail(email);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Cart cart = cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return newCart;
                });

        if (cart.getProducts()!= null) {
            cart.getProducts().add(product);
            cartRepository.save(cart);
        } else{
            cart.setProducts(List.of(product));
            cartRepository.save(cart);
        }
    }
    //  Remove a product from the cart
    @Transactional
    public void removeFromCart(String email, Long productId) {
        User user = userRepository.findByEmail(email);
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        cart.getProducts().removeIf(product -> product.getId().equals(productId));
        cartRepository.save(cart);
    }

    //  Get all products in a user's cart
    public List<Product> getCartProducts(String email) {
        User user = userRepository.findByEmail(email);
        return cartRepository.findByUser(user)
                .map(Cart::getProducts)
                .orElse(List.of());
    }

    //  Clear all products from the cart
    @Transactional
    public void clearCart(String email) {
        User user = userRepository.findByEmail(email);
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        cart.setProducts(new ArrayList<>());
        cartRepository.save(cart);
    }

    public int getCartItemCount(String userId) {
        User user = userRepository.findByEmail(userId);
        Cart cart = cartRepository.findByUserId(user.getId());
        return (cart != null && cart.getProducts() != null) ? cart.getProducts().size() : 0;
    }


    public Optional<Cart> findByUser(User user) {
        return cartRepository.findByUser(user);
    }
}
