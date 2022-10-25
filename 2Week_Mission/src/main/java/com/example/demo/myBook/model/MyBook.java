package com.example.demo.myBook.model;


import com.example.demo.base.Base;
import lombok.*;
import javax.persistence.*;


@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@Table(name = "cart")
public class MyBook extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mybookId")
    private Long mybookId;

    private Long memberId;

    private Long productId;
}
