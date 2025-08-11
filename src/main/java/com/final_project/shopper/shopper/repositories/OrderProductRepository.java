package com.final_project.shopper.shopper.repositories;

import com.final_project.shopper.shopper.models.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
    List<OrderProduct> findAllByOrderId(Long id);
}
