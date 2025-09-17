package com.final_project.shopper.shopper.services;

import com.final_project.shopper.shopper.dtos.order.OrderUserDto;
import com.final_project.shopper.shopper.models.Order;

import java.util.List;

public interface OrderService {
    Order orderProduct(String userEmail, OrderUserDto orderUserDto);
    public List<Order> getOrdersByUser(String email);

}
