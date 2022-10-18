package com.example.demo.member;

import com.example.demo.auth.PrincipalDetails;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/member")
@AllArgsConstructor
public class MemberController {

    private MemberService memberService;


    @GetMapping("/join")
    public String join(){
        return "member/joinform";
    }

    @GetMapping("/login")
    public String login(){
        return "member/loginform";
    }

    @GetMapping("/admin")
    @ResponseBody
    public String admin(){
        return "admin 페이지";
    }

    @PostMapping("/join")
    @ResponseBody
    public String joinPost( JoinForm joinForm) {
        String rawPassword = joinForm.getPassword();
        memberService.join(joinForm);

        return rawPassword;
    }

    @GetMapping("/modify")
    public String modify(@AuthenticationPrincipal PrincipalDetails principalDetails){
         return "member/modify";
    }

    @PostMapping("/modify")
    public String modifyPost(@AuthenticationPrincipal PrincipalDetails principalDetails, ModifyForm modifyForm){
        memberService.modify(principalDetails,modifyForm);
        System.out.println(principalDetails);
        return  "redirect:/";
    }
    @GetMapping("modifyPassword")
    public String modifyPassword(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model){
        String password = principalDetails.getPassword();
        model.addAttribute("password",password);
        System.out.println(password);
        return "member/modifyPassword";
    }

    @PostMapping("modifyPassword")
    public String modifyPasswordPost(@AuthenticationPrincipal PrincipalDetails principalDetails,@RequestParam String new_password){
        memberService.modifyPassword(principalDetails,new_password);
        return  "redirect:/";
    }

    @GetMapping("/findUsername")
    public String findUserName(){
        return "member/findUsername";
    }

    @PostMapping("/findUsername")
    public String foundUsername(@RequestParam String email, Model model){
        String username = memberService.findUsernameByEmail(email);
        model.addAttribute("msg", "회원님의 아이디는 " + username + "입니다.");

        return "member/alert";
    }


    @GetMapping("/findPassword")
    public String findPassword(){
        return "member/findPassword";
    }

    @PostMapping("/findPassword")
    public String foundPassword(@RequestParam String username,@RequestParam String email, Model model){
        String password = "1234a";
        memberService.setTemporaryPassword(username,password);
        model.addAttribute("msg",
                "회원님의 임시 비밀번호는 " + password + "입니다. 로그인 후 비밀번호를 변경해주세요");

        return "member/alert";
    }




}
