package com.example.demo.order;


import com.example.demo.auth.PrincipalDetails;
import com.example.demo.order.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public String createOrder(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        orderService.createOrderFromCart(principalDetails.getMember());
        return "redirect:/";
    }

    @GetMapping("/list")
    public String orderList(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        List<Order> orders = orderService.getOrders(principalDetails.getMember());
        int orderSize = orders.size();
        model.addAttribute("orders", orders);
        model.addAttribute("orderSize", orderSize);

        return"/order/list";
    }

    @GetMapping("/{id}")
    public String orderDetail(@PathVariable("id") Long orderId, Model model) {
        Order order = orderService.getOrder(orderId);
        model.addAttribute("order",order);
        return "order/detail";

    }

    @PostMapping("/{id}/cancel")
    public String cancleOrder(@PathVariable("id") Long orderId){

        return"/order/list";
    }

    @PostMapping("/{id}/pay")
    public String payOrder(@AuthenticationPrincipal PrincipalDetails principalDetails,@PathVariable("id") Long orderId){
        orderService.payByCash(principalDetails.getMember(),orderId);
        return"/order/list";
    }

    @PostMapping("/{id}/refund")
    public String refundOrder(@PathVariable("id") Long orderId){

        return"/order/list";
    }



}
