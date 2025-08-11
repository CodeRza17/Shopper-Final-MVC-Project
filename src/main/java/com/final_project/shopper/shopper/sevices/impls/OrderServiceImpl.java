package com.final_project.shopper.shopper.sevices.impls;

import com.final_project.shopper.shopper.dtos.order.OrderUserDto;
import com.final_project.shopper.shopper.models.Order;
import com.final_project.shopper.shopper.models.User;
import com.final_project.shopper.shopper.repositories.OrderRepository;
import com.final_project.shopper.shopper.sevices.OrderProductService;
import com.final_project.shopper.shopper.sevices.OrderService;
import com.final_project.shopper.shopper.sevices.UserService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class OrderServiceImpl implements OrderService {
    private final UserService userService;
    private final OrderProductService orderProductService;
    private final OrderRepository orderRepository;

    public OrderServiceImpl(UserService userService, OrderProductService orderProductService, OrderRepository orderRepository) {
        this.userService = userService;
        this.orderProductService = orderProductService;
        this.orderRepository = orderRepository;
    }

    @Override
    public Order orderProduct(String userEmail, OrderUserDto orderUserDto) {
        User user = userService.findUserByEmail(userEmail);
        Order order = new Order();
        order.setAddress(orderUserDto.getAddress());
        order.setCity(orderUserDto.getCity());
        order.setPostcode(orderUserDto.getPostcode());
        order.setPhoneNumber(orderUserDto.getPhoneNumber());
        order.setOrderDate(new Date());
        order.setUser(user);
        order.setComment(orderUserDto.getComment());
        orderRepository.save(order);

        boolean result = orderProductService.createOrderProduct(order);
        return order;
    }
}
