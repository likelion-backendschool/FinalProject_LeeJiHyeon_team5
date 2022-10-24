package com.example.demo.cart;


import com.example.demo.auth.PrincipalDetails;
import com.example.demo.member.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping("/list")
    public String showCastList(){
        return "/cart/list";
    }


    @PostMapping("/add/{id}")
    public String addToCart(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable("id") Long productId
                            , HttpServletResponse response) throws IOException {
        cartService.addItem(principalDetails.getMember(), productId ,response);
        return "/cart/list";
    }


    @PostMapping("/remove/{id}")
    public String removFromCart(){
        return "/cart/list";
    }
}
