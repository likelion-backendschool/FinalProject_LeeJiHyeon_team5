package com.example.demo.post;

import com.example.demo.auth.PrincipalDetails;
import com.example.demo.post.model.Post;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/post")
public class PostController {

    private final  PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/write")
    public String wrtieForm (@AuthenticationPrincipal PrincipalDetails principalDetails, PostForm postForm, Model model) {
        if(principalDetails == null){
            model.addAttribute("msg", "로그인 후 이용해주세요");
            return "/alert";
        }

        return "/post/postForm";
    }
    @PostMapping("/write")
    public String wrtie (@AuthenticationPrincipal PrincipalDetails principalDetails, PostForm postForm) {
        postService.write(principalDetails,postForm );

        return "redirect:/post/list";
    }

    @GetMapping("/list")
    public String list( Model model) {
        List<Post> posts =  postService.findAllPost();

        model.addAttribute("posts",posts);
        return "post/postList";
    }

    @PostAuthorize("hasRole('ROLE_ADMIN') or #postForm.username == authentication.principal.username")
    @GetMapping("/{id}/modify")
    public String postModifyGet(@AuthenticationPrincipal PrincipalDetails principalDetails,@PathVariable long id, PostForm postForm, Model model) {

        Post post = postService.getPost(id);
        System.out.println(id);
        postForm.setTitle(post.getTitle());
        postForm.setContent(post.getContent());

        model.addAttribute("post",post);
        model.addAttribute("id",id);

        return "post/postModify";
    }

    @PostMapping("/{id}/modify")
    public String postModifyPost(@PathVariable long id, PostForm postForm) {

        postService.modify(id, postForm);

        return "redirect:/post/{id}";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable long id) {
        postService.delete(id);

        return "redirect:/post/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable long id, Model model) {

        model.addAttribute("post", postService.getPost(id));
        return "post/postDetail";
    }

}
