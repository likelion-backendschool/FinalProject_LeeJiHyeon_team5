package com.example.demo.product;

import com.example.demo.auth.PrincipalDetails;
import com.example.demo.post.PostForm;
import com.example.demo.post.PostService;
import com.example.demo.post.model.Post;
import com.example.demo.product.model.Product;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/create")
    public String productCreate (@AuthenticationPrincipal PrincipalDetails principalDetails, ProductForm productForm, Model model) {
        if(principalDetails == null){
            model.addAttribute("msg", "로그인 후 이용해주세요");
            return "/alert";
        }
        return "/product/productForm";
    }

    @PostMapping("/create")
    public String productCreatePost (@AuthenticationPrincipal PrincipalDetails principalDetails, ProductForm productForm) {
        productService.create(principalDetails,productForm );

        return "redirect:/product/list";
    }

    @GetMapping("/list")
    public String list( Model model) {
        List<Product> products =  productService.findAllProduct();

        model.addAttribute("products",products);
        return "product/productList";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}/modify")
    public String productModify(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable long id,
                                HttpServletResponse response, ProductForm productForm, Model model) throws IOException {

        Product product = productService.getProduct(id);

        if(!principalDetails.getMember().getMemberId().equals(product.getMemberId())){
            response.setContentType("text/html; charset=utf-8");
            response.getWriter().print("<script>alert('해당 페이지에 접근권한이 없습니다.');history.back();</script>");
        }

        model.addAttribute("product",product);
        model.addAttribute("id",id);

        return "product/productModify";
    }

    @PostMapping("/{id}/modify")
    public String productModifyPost(@PathVariable long id,ProductForm productForm) {

        productService.modify(id, productForm);

        return "redirect:/product/{id}";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}/delete")
    public String delete(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable long id,
                         HttpServletResponse response) throws IOException {
        Product product = productService.getProduct(id);

        // alert 메서드 만들기
        if(!principalDetails.getMember().getMemberId().equals(product.getMemberId())){
            response.setContentType("text/html; charset=utf-8");
            response.getWriter().print("<script>alert('해당 페이지에 접근권한이 없습니다.');history.back();</script>");
        }
        productService.delete(id);

        return "redirect:/product/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable long id, Model model) {

        model.addAttribute("product", productService.getProduct(id));
        return "product/productDetail";
    }

}
