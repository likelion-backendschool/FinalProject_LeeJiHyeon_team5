package com.example.demo.order;


import com.example.demo.cart.CartService;
import com.example.demo.cart.model.CartItem;
import com.example.demo.member.model.Member;
import com.example.demo.order.model.Order;
import com.example.demo.order.model.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final CartService cartService;
    private final OrderRepository orderRepository;

    @Transactional
    public Order createOrderFromCart(Member member) {
        // 입력된 회원의 장바구니 아이템들을 전부 가져온다.
        // 만약에 특정 장바구니의 상품옵션이 판매가능이면 주문품목으로 옮긴 후 삭제

        List<CartItem> cartItems = cartService.getItemsByMember(member);

        List<OrderItem> orderItems = new ArrayList<>();


        return creatOrder(member, orderItems);
    }

    @Transactional
    public Order creatOrder(Member member, List<OrderItem> orderItems) {
        Order order = Order
                .builder()
                .member(member)
                .build();

        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }

        orderRepository.save(order);

        return order;
    }
}