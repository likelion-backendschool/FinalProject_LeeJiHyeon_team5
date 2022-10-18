package com.example.demo.postHashTag;


import com.example.demo.postHashTag.model.PostHashTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostHashTagRepository extends JpaRepository<PostHashTag, Long> {
    Optional<PostHashTag> findByPostIdAndKeywordId(Long postId, Long keywordId);
}