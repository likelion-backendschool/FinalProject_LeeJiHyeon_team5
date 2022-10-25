package com.example.demo.order;


import com.example.demo.auth.PrincipalDetails;
import com.example.demo.member.model.Member;
import com.example.demo.order.model.Order;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper;

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public String createOrderFromCart(@AuthenticationPrincipal PrincipalDetails principalDetails) {

        Order order = orderService.createOrderFromCart(principalDetails.getMember());
        Long id = order.getOrderId();
        String redirect = "redirect:/order/%d".formatted(id);

        return redirect;
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
    @PreAuthorize("isAuthenticated()")
    public String showDetail(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable long orderId, Model model) {
        Order order = orderService.getOrder(orderId);

        Member member = principalDetails.getMember();
        long restCash = member.getRestCash();

        model.addAttribute("order", order);
        model.addAttribute("restCash", restCash);

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
        orderService.refund(orderId);
        return"/order/list";
    }

// 토스 결제 코드
    @PostConstruct
    private void init() {
        restTemplate.setErrorHandler(new ResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse response) {
                return false;
            }

            @Override
            public void handleError(ClientHttpResponse response) {
            }
        });
    }

    private final String SECRET_KEY = "test_sk_P24xLea5zVAnZZ1mA07rQAMYNwW6";

    @RequestMapping("/{id}/success")
    public String confirmPayment(
            @PathVariable long id,
            @RequestParam String paymentKey,
            @RequestParam String orderId,
            @RequestParam Long amount,
            Model model
    ) throws Exception {

        Order order = orderService.getOrder(id);

        long orderIdInputed = Long.parseLong(orderId.split("__")[1]);

        if ( id != orderIdInputed ) {
            throw new OrderIdNotMatchedException();
        }


        HttpHeaders headers = new HttpHeaders();
        // headers.setBasicAuth(SECRET_KEY, ""); // spring framework 5.2 이상 버전에서 지원
        headers.set("Authorization", "Basic " + Base64.getEncoder().encodeToString((SECRET_KEY + ":").getBytes()));
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> payloadMap = new HashMap<>();
        payloadMap.put("orderId", orderId);
        payloadMap.put("amount", String.valueOf(order.calculatePayPrice()));

        HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(payloadMap), headers);

        ResponseEntity<JsonNode> responseEntity = restTemplate.postForEntity(
                "https://api.tosspayments.com/v1/payments/" + paymentKey, request, JsonNode.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            orderService.payByTossPayments(order);

            return "redirect:/order/%d".formatted(order.getOrderId());
        } else {
            JsonNode failNode = responseEntity.getBody();
            model.addAttribute("message", failNode.get("message").asText());
            model.addAttribute("code", failNode.get("code").asText());
            return "order/fail";
        }
    }

    @RequestMapping("/{id}/fail")
    public String failPayment(@RequestParam String message, @RequestParam String code, Model model) {
        model.addAttribute("message", message);
        model.addAttribute("code", code);
        return "order/fail";
    }



}
