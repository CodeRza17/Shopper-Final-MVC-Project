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
public class BasketUserDto {
    private Long id;
    private ProductBasketDto product;
    private Double quantity;
    private String size;

    public Double getTotalPrice() {
        return quantity * product.getResultPrice();
    }
}
