package com.example.demo.post.model;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    @Setter
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Setter
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "user_name")
    private String username;

    @Column(name = "title")
    private String title;

    @Column(name = "content",columnDefinition = "TEXT")
    private String content;

    @Column(name = "keyword")
    private String keyword;


}
