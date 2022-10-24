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

    public Order getOrder(Long orderId) {
       Order order =  orderRepository.findByOrderId(orderId)
               .orElseThrow(()->new OrderNotFoundException("주문내역이 존재하지 않습니다."));

       return order;

    }

    public List<Order> getOrders(Member member) {
        List<Order> orders = orderRepository.findAllByMember(member);

        return orders;
    }
}