package com.final_project.shopper.shopper.repositories;


import com.final_project.shopper.shopper.models.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BasketRepository extends JpaRepository<Basket, Long> {

    List<Basket> findByUserId(Long userId);

    Basket findByProductIdAndUserIdAndSize(Long productId, Long id, String selectedSize);
}
