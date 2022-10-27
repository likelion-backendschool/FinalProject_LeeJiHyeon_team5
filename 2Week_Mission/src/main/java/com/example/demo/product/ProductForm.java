package com.example.demo.product;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;


@Getter
@Setter
public class ProductForm {

    private String subject;
    private int price;
    private int salePrice;
    private int wholesalePrice;
    private String keyword;
    private Long memberId;

}