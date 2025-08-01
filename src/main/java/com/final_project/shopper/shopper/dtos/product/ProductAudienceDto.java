package com.final_project.shopper.shopper.dtos.product;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductAudienceDto {
    private Long id;
    private String name;
    private Long productCategoryId;
    private Double price;
    private Double discountPrice;
    private String photoUrl;
    private Long salesCount;
}
