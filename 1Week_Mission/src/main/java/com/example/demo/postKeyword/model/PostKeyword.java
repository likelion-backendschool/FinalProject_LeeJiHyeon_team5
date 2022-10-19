package com.example.demo.postKeyword.model;

import com.example.demo.base.Base;
import com.example.demo.postHashTag.model.PostHashTag;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@Table(name = "postKeyword")
public class PostKeyword extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "keyword_id")
    private Long keywordId;


    @Column(name = "content", columnDefinition = "TEXT")
    private String content;



}
