package com.example.demo.cash.model;

import com.example.demo.base.Base;
import com.example.demo.member.model.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class CashLog extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cashId")
    private Long cashId;

    @ManyToOne(fetch = LAZY)
    private Member member;

    private long price; // 변동

    private String eventType;
}