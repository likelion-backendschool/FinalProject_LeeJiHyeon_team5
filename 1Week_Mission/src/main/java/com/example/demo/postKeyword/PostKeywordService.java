package com.example.demo.postKeyword;

import com.example.demo.postKeyword.model.PostKeyword;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostKeywordService {
    private final PostKeywordRepository postkeywordRepository;

    public PostKeyword save(String keywordContent) {
        Optional<PostKeyword> optKeyword = postkeywordRepository.findByContent(keywordContent);

        if ( optKeyword.isPresent() ) {
            return optKeyword.get();
        }

        PostKeyword postKeyword = PostKeyword
                .builder()
                .content(keywordContent)
                .build();

        postkeywordRepository.save(postKeyword);

        return postKeyword;
    }
}