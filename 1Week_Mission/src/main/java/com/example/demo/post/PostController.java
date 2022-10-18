package com.example.demo.post;

import com.example.demo.auth.PrincipalDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/post")
public class PostController {

    private final  PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/write")
    public String wrtieForm (@AuthenticationPrincipal PrincipalDetails principalDetails, PostForm postForm) {


        return "/post/postForm";
    }
    @PostMapping("/write")
    public String wrtie (@AuthenticationPrincipal PrincipalDetails principalDetails, PostForm postForm) {


        postService.write(principalDetails,postForm );

        return "redirect:/post/list";
    }


}
