package com.final_project.shopper.shopper.services.impls;

import com.final_project.shopper.shopper.dtos.order.OrderUserDto;
import com.final_project.shopper.shopper.models.Order;
import com.final_project.shopper.shopper.models.User;
import com.final_project.shopper.shopper.repositories.OrderProductRepository;
import com.final_project.shopper.shopper.repositories.OrderRepository;
import com.final_project.shopper.shopper.repositories.UserRepository;
import com.final_project.shopper.shopper.services.OrderProductService;
import com.final_project.shopper.shopper.services.OrderService;
import com.final_project.shopper.shopper.services.UserService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final UserService userService;
    private final OrderProductService orderProductService;
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final UserRepository userRepository;

    public OrderServiceImpl(UserService userService, OrderProductService orderProductService, OrderRepository orderRepository, OrderProductRepository orderProductRepository, UserRepository userRepository) {
        this.userService = userService;
        this.orderProductService = orderProductService;
        this.orderRepository = orderRepository;
        this.orderProductRepository = orderProductRepository;
        this.userRepository = userRepository;
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
        orderProductService.createOrderProduct(order);
        return order;
    }

    @Override
    public List<Order> getOrdersByUser(String email) {
        Long id = userRepository.findByEmail(email).getId();
        return orderRepository.findAllOrderByUserId(id);
    }


}
