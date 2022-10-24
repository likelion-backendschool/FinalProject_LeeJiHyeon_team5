package com.example.demo.member.model;

import com.example.demo.base.Base;
import lombok.*;
import javax.persistence.*;


@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@Table(name = "member")
public class Member extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "user_name")
    private String username;  // 로그인아이디

    @Column(name = "password")
    private String password;

    @Setter
    @Column(name = "nickname")
    private String nickName; // 실명

    @Column(unique = true)
    private String email;

    @Column(name = "role")
    private String role;

    @Column(name = "auth_level")
    private Long authLevel;

}
