package com.example.demo.order.model;


import com.example.demo.base.Base;
import com.example.demo.member.model.Member;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@Table(name = "order_from_cart")
public class Order extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @ManyToOne(fetch = LAZY)
    private Member member;

    private String name;

    private boolean isPaid; // 결제여부
    private boolean isCanceled; // 취소여부
    private boolean isRefunded; // 환불여부

    @Builder.Default
    @OneToMany(mappedBy = "order", cascade = ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    public void addOrderItem(OrderItem orderItem) {
        orderItem.setOrder(this);
        orderItems.add(orderItem);
    }

    public int calculatePayPrice() {
        int payPrice = 0;

        for ( OrderItem orderItem : orderItems ) {
            payPrice += orderItem.getSalePrice();
        }

        return payPrice;
    }

    public void setPaymentDone() {
        for ( OrderItem orderItem : orderItems ) {
            orderItem.setPaymentDone();
        }
        isPaid = true;
    }

    public void setRefundDone() {
        for ( OrderItem orderItem : orderItems ) {
            orderItem.setRefundDone();
        }
        isRefunded = true;
    }
    public int getPayPrice() {
        int payPrice = 0;
        for ( OrderItem orderItem : orderItems ) {
            payPrice += orderItem.getPayPrice();
        }

        return payPrice;
    }

    public void makeName() {
        String name = orderItems.get(0).getProduct().getSubject();

        if ( orderItems.size() > 1 ) {
            name += " 외 %d권".formatted(orderItems.size() - 1);
        }

        this.name = name;
    }
}