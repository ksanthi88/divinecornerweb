package com.santhi.divinecornerweb.repository;

import com.santhi.divinecornerweb.model.Cart;
import com.santhi.divinecornerweb.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);
    Cart findByUserId(Long id);
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM cart_products WHERE product_id = :productId", nativeQuery = true)
    void removeProductFromCarts(@Param("productId") Long productId);
}
