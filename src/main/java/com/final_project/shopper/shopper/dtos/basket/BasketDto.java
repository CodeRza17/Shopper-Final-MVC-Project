package com.final_project.shopper.shopper.dtos.basket;

import com.final_project.shopper.shopper.dtos.product.ProductBasketDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BasketDto {
    private Long id;
    private int quantity;
    private ProductBasketDto product;

    private Double totalPrice;

    public Double getTotalPrice() {
        return product.getResultPrice() * quantity;
    }
}
