package com.example.demo.postHashTag.model;

import com.example.demo.post.model.Post;
import com.example.demo.postKeyword.model.PostKeyword;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class PostHashTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hasttag_id")
    private Long hashtagId;

    @Setter
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Setter
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "content")
    private Long memberId;

    @Column(name = "post_id")
    private Long postId;

    @Column(name = "keyword_id")
    private Long keywordId;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_post_id")
    private Post post;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "postkeyword_keyword_id")
    private PostKeyword postkeyword;


}
