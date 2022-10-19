package com.example.demo.postHashTag;

import com.example.demo.post.model.Post;
import com.example.demo.postHashTag.model.PostHashTag;
import com.example.demo.postKeyword.PostKeywordService;
import com.example.demo.postKeyword.model.PostKeyword;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostHashTagService {

    private final PostKeywordService postkeywordService;
    private final PostHashTagRepository posthashTagRepository;

    public void applyHashTags(Post post, String keywordContentsStr) {
        List<String> keywordContents = Arrays.stream(keywordContentsStr.split("#"))
                .map(String::trim)
                .filter(s -> s.length() > 0)
                .collect(Collectors.toList());

        keywordContents.forEach(keywordContent -> {
            saveHashTag(post, keywordContent);
        });
    }

    private PostHashTag saveHashTag(Post post, String keywordContent) {
        PostKeyword postkeyword = postkeywordService.save(keywordContent);

        Optional<PostHashTag> opHashTag = posthashTagRepository.findByPostIdAndKeywordId(post.getPostId(), postkeyword.getKeywordId());

        if (opHashTag.isPresent()) {
            return opHashTag.get();
        }

        PostHashTag posthashTag = PostHashTag.builder()
                .post(post)
                .postkeyword(postkeyword)
                .build();

        posthashTagRepository.save(posthashTag);

        return posthashTag;
    }
}