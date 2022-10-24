package com.example.demo.cart;


import com.example.demo.auth.PrincipalDetails;
import com.example.demo.cart.model.CartItem;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/cart")
@AllArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping("/list")
    public String showCartList(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model){
       List<CartItem> cartItems = cartService.getItemsByMember(principalDetails.getMember());
        int cartItemSize = cartItems.size();
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("cartItemSize",cartItemSize);
        return "/cart/list";
    }


    @RequestMapping("/add/{id}")
    public String addToCart(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable("id") Long productId
                            , HttpServletResponse response) throws IOException {
        cartService.addItem(principalDetails.getMember(), productId ,response);
        return "/cart/list";
    }


    @RequestMapping("/remove/{id}")
    public String removeFromCart(@AuthenticationPrincipal PrincipalDetails principalDetails,@PathVariable("id") Long productId){
        cartService.remove(principalDetails.getMember(), productId);
        return "redirect:/cart/list";
    }
}
