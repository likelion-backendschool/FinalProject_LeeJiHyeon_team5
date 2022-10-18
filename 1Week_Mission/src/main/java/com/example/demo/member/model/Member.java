package com.example.demo.member.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;


    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;


    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


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


    @Builder
    public Member( String username, String password, String email, String nickName ) {

        this.username = username;
        this.password = password;
        this.email = email;
        this.nickName = nickName;

    }


}
