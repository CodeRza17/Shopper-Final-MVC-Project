package com.final_project.shopper.shopper.repositories;

import com.final_project.shopper.shopper.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
