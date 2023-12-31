package com.example.demo.cart;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findAllByUserId(Long id);

    void deleteByUserIdAndId(Long userId, Long cartId);

}
