package com.final_project.shopper.shopper.services;

import com.final_project.shopper.shopper.models.Order;
import com.final_project.shopper.shopper.models.OrderProduct;

import java.util.List;

public interface OrderProductService {
    boolean createOrderProduct(Order order);
    List<OrderProduct> findOrdersByBrandId(String email);
}
