package com.example.demo.Keyword.model;

import com.example.demo.base.Base;
import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@Table(name = "postKeyword")
public class Keyword extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "keyword_id")
    private Long keywordId;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;



}
