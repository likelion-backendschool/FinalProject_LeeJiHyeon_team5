package com.example.demo.product;

import com.example.demo.auth.PrincipalDetails;
import com.example.demo.post.PostForm;
import com.example.demo.post.PostService;
import com.example.demo.post.model.Post;
import com.example.demo.product.model.Product;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @PostAuthorize("hasRole('ROLE_ADMIN') or #postForm.username == authentication.principal.username")
    @GetMapping("/{id}/modify")
    public String productModify(@AuthenticationPrincipal PrincipalDetails principalDetails,@PathVariable long id,ProductForm productForm, Model model) {

        Product product = productService.getProduct(id);
        System.out.println(id);
        productForm.setSubject(product.getSubject());
        productForm.setPrice(product.getPrice());

        model.addAttribute("product",product);
        model.addAttribute("id",id);

        return "product/productModify";
    }

    @PostMapping("/{id}/modify")
    public String productModifyPost(@PathVariable long id,ProductForm productForm) {

        productService.modify(id, productForm);

        return "redirect:/product/{id}";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable long id) {
        productService.delete(id);

        return "redirect:/product/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable long id, Model model) {

        model.addAttribute("product", productService.getProduct(id));
        return "product/productDetail";
    }

}
