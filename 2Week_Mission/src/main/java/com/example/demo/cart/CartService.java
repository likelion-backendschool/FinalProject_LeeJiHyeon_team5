package com.example.demo.cart;

import com.example.demo.cart.model.Cart;
import com.example.demo.member.MemberNotFoundException;
import com.example.demo.member.model.Member;
import com.example.demo.product.ProductRepository;
import com.example.demo.product.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public void addItem(Member member, Long productId, HttpServletResponse response) throws IOException {

        Product product = productRepository.findByProductId(productId)
                .orElseThrow(() -> new ProductNotFoundException("일치하는 상품을 찾을 수 없습니다."));

        Cart preCart = cartRepository.findByMemberAndProductId(member,productId).orElse(null);

        if(preCart != null){
            response.setContentType("text/html; charset=utf-8");
            response.getWriter().print("<script>alert('이미 추가된 항목입니다.');  location.href = \"/cart/list\";</script>");
        }

        Cart cart = new Cart();
        cart.setMember(member);
        cart.setProduct(product);

        cartRepository.save(cart);
    }
}

