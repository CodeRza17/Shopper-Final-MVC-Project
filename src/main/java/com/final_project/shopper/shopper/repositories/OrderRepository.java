package com.final_project.shopper.shopper.repositories;

import com.final_project.shopper.shopper.models.Order;
import com.final_project.shopper.shopper.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllOrderByUserId(Long id);
}
