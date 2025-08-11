package com.final_project.shopper.shopper.sevices;

import com.final_project.shopper.shopper.dtos.order.OrderUserDto;
import com.final_project.shopper.shopper.models.Order;

public interface OrderService {
    Order orderProduct(String userEmail, OrderUserDto orderUserDto);
}
