package com.example.demo.cart;

import com.example.demo.cart.model.Cart;
import com.example.demo.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByMemberAndProductId(Member member, Long ProductId);
}
