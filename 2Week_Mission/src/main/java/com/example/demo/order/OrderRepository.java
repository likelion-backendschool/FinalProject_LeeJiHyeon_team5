package com.example.demo.order;

import com.example.demo.member.model.Member;
import com.example.demo.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByOrderId(Long orderId);

    List<Order> findAllByMember(Member member);
}