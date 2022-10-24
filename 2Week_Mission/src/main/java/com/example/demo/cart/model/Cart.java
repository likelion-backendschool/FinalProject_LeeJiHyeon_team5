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
public class Cart extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cartId")
    private Long cartId;


    @OneToOne(mappedBy = "cart")  //회원 한 명당 한 개의 장바구니니
   private Member member;

    @ManyToOne(cascade = CascadeType.ALL)
    private Product product;

}
