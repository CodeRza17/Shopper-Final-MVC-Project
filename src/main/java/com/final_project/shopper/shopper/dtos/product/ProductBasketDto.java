package com.final_project.shopper.shopper.dtos.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductBasketDto {
    private Long id;
    private String name;
    private String photoUrl;
    private Double price;
    private Double discountPrice;

    public Double getResultPrice(){
        if (discountPrice == null || discountPrice == 0){
            discountPrice = 1.0;
        }
        return price*(1-discountPrice);
    }
}
