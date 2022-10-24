package com.example.demo.cart.model;

import com.example.demo.base.Base;
import com.example.demo.member.model.Member;
import com.example.demo.product.model.Product;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@Table(name = "cart")
public class CartItem extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cartId")
    private Long cartId;


    @ManyToOne(fetch = LAZY)
    private Member member;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Product product;

}
