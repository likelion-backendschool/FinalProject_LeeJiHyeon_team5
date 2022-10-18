package com.example.demo.member;

import lombok.Getter;
import lombok.Setter;

 // import javax.validation.constraints.NotEmpty;
@Getter
@Setter
public class JoinForm {

 //   @NotEmpty(message = "내용은 필수항목입니다.")
    private String username;  // 로그인아이디

    private String password;

    private String email;

    private String nickName; // 실명




}

