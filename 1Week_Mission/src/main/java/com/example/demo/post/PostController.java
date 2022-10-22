package com.example.demo.post;

import com.example.demo.auth.PrincipalDetails;
import com.example.demo.post.model.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final  PostService postService;


   // @PreAuthorize("isAuthenticated()")
    @GetMapping("/write")
    public String wrtieForm (@AuthenticationPrincipal PrincipalDetails principalDetails, PostForm postForm,
                             Model model,HttpServletResponse response) throws IOException {

        if(principalDetails == null){
            alert(response, "로그인 후 이용해주세요");
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


    //@PostAuthorize("hasRole('ROLE_ADMIN') or #postForm.username == authentication.principal.username")
    @GetMapping("/{id}/modify")
    public String postModifyGet(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                @PathVariable long id, PostForm postForm, Model model, HttpServletResponse response) throws IOException {
        Post post = postService.getPost(id);

        if(!principalDetails.getUsername().equals(post.getUsername())){
            alert(response,"작성자만 이용 가능합니다.");
        }

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

    public void alert(HttpServletResponse response, String msg) throws IOException {

        response.setContentType("text/html; charset=utf-8");
        response.getWriter().print("<script>alert('" + msg + "');history.back();</script>");
    }

}
