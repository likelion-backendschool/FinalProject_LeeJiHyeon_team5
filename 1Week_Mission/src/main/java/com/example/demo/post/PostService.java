package com.example.demo.post;

import com.example.demo.auth.PrincipalDetails;
import com.example.demo.post.model.Post;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public void write(PrincipalDetails principalDetails, PostForm postForm) {
       Post post = new Post();

       post.setTitle(postForm.getTitle());
       post.setContent(postForm.getContent());
       post.setUsername(principalDetails.getUsername());

        postRepository.save(post);
    }

    public Post getPost(long postId) {
        return postRepository.findByPostId(postId)
                .orElseThrow(() -> new UsernameNotFoundException("일치하는 게시글을 찾을 수 없습니다."));
    }

    public void modify(long postId, PostForm postForm) {
        Post post = postRepository.findByPostId(postId)
                      .orElseThrow(() -> new UsernameNotFoundException("일치하는 게시글을 찾을 수 없습니다."));
        System.out.println(postId);

        post.setTitle(postForm.getTitle());
        post.setContent(postForm.getContent());

        postRepository.save(post);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or #postDto.user.userId == authentication.principal.username")
    public void delete(long postId) {
        Post post = postRepository.findByPostId(postId)
                .orElseThrow(() -> new UsernameNotFoundException("일치하는 게시글을 찾을 수 없습니다."));

        postRepository.delete(post);
    }

    public List<Post> findAllPost() {
        return postRepository.findAll();
    }


}