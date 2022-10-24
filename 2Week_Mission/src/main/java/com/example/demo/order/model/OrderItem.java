package com.example.demo.order.model;


import com.example.demo.base.Base;
import com.example.demo.product.model.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class OrderItem extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    @ManyToOne(fetch = LAZY)
    @ToString.Exclude
    private Order order;


    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_product_id")
    private Product product;


    public OrderItem(Product product) {
        this.product = product;

    }
}