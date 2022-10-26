package com.example.demo.order;

import com.example.demo.cart.CartService;
import com.example.demo.cart.model.CartItem;
import com.example.demo.member.MemberService;
import com.example.demo.member.model.Member;
import com.example.demo.order.model.Order;
import com.example.demo.order.model.OrderItem;
import com.example.demo.product.model.Product;
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

    private final MemberService memberService;
    @Transactional
    public Order createOrderFromCart(Member member) {
        List<CartItem> cartItems = cartService.getItemsByMember(member);

        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cartItems) {
            Product product = cartItem.getProduct();
            orderItems.add(new OrderItem(product));
        }

        return creatOrder(member, orderItems);
    }

    @Transactional
    public Order creatOrder(Member member, List<OrderItem> orderItems) {

        Order order = new Order();
        order.setMember(member);

        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }

        order.makeName();

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

    public void payByCash(Member member, Long orderId) {
        Order order =  orderRepository.findByOrderId(orderId)
                .orElseThrow(()->new OrderNotFoundException("주문내역이 존재하지 않습니다."));

        long restCash = member.getRestCash();
        int payPrice = order.calculatePayPrice();

        if (payPrice > restCash) {
            throw new RuntimeException("예치금이 부족합니다.");
        }

        memberService.addCash(member, payPrice * -1, "주문__%d__사용__예치금".formatted(order.getOrderId()));


        order.setPaymentDone();
        orderRepository.save(order);

    }
    @Transactional
    public void refund(Long orderId) {
        Order order = getOrder(orderId);

        int payPrice = order.getPayPrice();
        memberService.addCash(order.getMember(), payPrice, "주문__%d__환불__예치금".formatted(order.getOrderId()));

        order.setRefundDone();
        orderRepository.save(order);
    }


    @Transactional
    public void payByTossPayments(Order order, long useRestCash) {
        Member orderer = order.getMember();
        int payPrice = order.calculatePayPrice();

        long pgPayPrice = payPrice - useRestCash;
        memberService.addCash(orderer, pgPayPrice, "주문__%d__충전__토스페이먼츠".formatted(order.getOrderId()));
        memberService.addCash(orderer, pgPayPrice * -1, "주문__%d__사용__토스페이먼츠".formatted(order.getOrderId()));

        if ( useRestCash > 0 ) {
            memberService.addCash(orderer, useRestCash * -1, "주문__%d__사용__예치금".formatted(order.getOrderId()));
        }

        order.setPaymentDone();
        orderRepository.save(order);
    }

}