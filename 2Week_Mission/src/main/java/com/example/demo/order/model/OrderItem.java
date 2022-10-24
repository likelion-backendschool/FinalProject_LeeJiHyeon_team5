package com.example.demo.order.model;


import com.example.demo.base.Base;
import com.example.demo.product.model.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class OrderItem extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    @ManyToOne(fetch = LAZY)
    @ToString.Exclude
    private Order order;


    private LocalDateTime payDate; // 결제일
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_product_id")
    private Product product;

    private int price; // 권장판매가
    private int salePrice; // 실제판매가, 결제금액
    private int wholesalePrice; // 도매가

    private int pgFee; // 결제대행사 수수료

    private int refundPrice; // 환불금액

    // private int refundQuantity; // 환불한 개수
    private boolean isPaid; // 결제여부


    public OrderItem(Product product) {
        this.product = product;
        this.price = product.getPrice();
        this.salePrice = product.getSalePrice();
        this.wholesalePrice = product.getWholesalePrice();

    }

    public void setPaymentDone() {
        this.pgFee = 0;
        this.isPaid = true;
        this.payDate = LocalDateTime.now();
    }
}