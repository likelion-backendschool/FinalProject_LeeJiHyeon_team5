package com.example.demo.HashTag;


import com.example.demo.HashTag.model.HashTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HashTagRepository extends JpaRepository<HashTag, Long> {
    Optional<HashTag> findByPostIdAndKeywordId(Long postId, Long keywordId);
}