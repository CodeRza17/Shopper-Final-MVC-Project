package com.final_project.shopper.shopper.dtos.orderProduct;


import com.final_project.shopper.shopper.dtos.product.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderProductDto {
    private Double price;
    private Integer quantity;
    private ProductDto product;
}
