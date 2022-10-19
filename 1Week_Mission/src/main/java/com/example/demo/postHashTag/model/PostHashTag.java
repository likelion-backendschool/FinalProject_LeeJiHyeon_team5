package com.example.demo.postHashTag.model;

import com.example.demo.base.Base;
import com.example.demo.post.model.Post;
import com.example.demo.postKeyword.model.PostKeyword;
import lombok.*;
import lombok.experimental.SuperBuilder;
import javax.persistence.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class PostHashTag extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hashtag_id")
    private Long hashtagId;


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
