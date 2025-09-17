package com.final_project.shopper.shopper.controller.dashboard;

import com.final_project.shopper.shopper.models.OrderProduct;
import com.final_project.shopper.shopper.services.OrderProductService;
import com.final_project.shopper.shopper.services.OrderService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class OrderController {
    private final OrderProductService orderProductService;
    private final OrderService orderService;

    public OrderController(OrderService orderService, OrderProductService orderProductService, OrderService orderService1) {
        this.orderProductService = orderProductService;
        this.orderService = orderService1;
    }

    @GetMapping("/dashboard/orders")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN', 'ROLE_MANAGER')")
    public String orders(Model model, Principal principal){
        String email = principal.getName();
        List<OrderProduct> orderList = orderProductService.findOrdersByBrandId(email);
        model.addAttribute("orderProducts", orderList);
        return "dashboard/orders/index";
    }
}
