package com.final_project.shopper.shopper.dtos.basket;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddToCart {
    private Long productId;
    private Double quantity;
}
