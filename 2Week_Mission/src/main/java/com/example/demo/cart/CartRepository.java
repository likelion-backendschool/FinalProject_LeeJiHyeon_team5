package com.example.demo.cart;

import com.example.demo.cart.model.CartItem;
import com.example.demo.member.model.Member;
import com.example.demo.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByMemberAndProduct(Member member, Product product);

    List<CartItem> findAllByMember(Member member);

}