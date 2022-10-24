package com.example.demo.cart;

import com.example.demo.cart.model.CartItem;
import com.example.demo.member.model.Member;
import com.example.demo.product.ProductRepository;
import com.example.demo.product.model.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public void addItem(Member member, Long productId, HttpServletResponse response) throws IOException {

        Product product = productRepository.findByProductId(productId)
                .orElseThrow(() -> new ProductNotFoundException("일치하는 상품을 찾을 수 없습니다."));

        CartItem preCart = cartRepository.findByMemberAndProduct(member,product).orElse(null);

        if(preCart != null){
            response.setContentType("text/html; charset=utf-8");
            response.getWriter().print("<script>alert('이미 추가된 항목입니다.');  location.href = \"/cart/list\";</script>");
        }

        CartItem cartItem = new CartItem();
        cartItem.setMember(member);
        cartItem.setProduct(product);

        cartRepository.save(cartItem);
    }

    public void remove(Member member, Long productId) {
        Product product = productRepository.findByProductId(productId)
                .orElseThrow(() -> new ProductNotFoundException("일치하는 상품을 찾을 수 없습니다."));

        CartItem cartItem = cartRepository.findByMemberAndProduct(member,product).orElse(null);

        cartRepository.delete(cartItem);

    }

    public List<CartItem> getItemsByMember(Member member) {
        return cartRepository.findAllByMember(member);
    }


}

