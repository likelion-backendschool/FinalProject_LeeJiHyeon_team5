package com.example.demo.post;

import com.example.demo.auth.PrincipalDetails;
import com.example.demo.post.model.Post;

public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public void write(PrincipalDetails principalDetails, PostForm postForm) {
       Post post = new Post();

       post.setTitle(postForm.getTitle());
       post.setContent(postForm.getContent());
       post.setMemberId(principalDetails.getMember().getMemberId());

        postRepository.save(post);
    }
}
