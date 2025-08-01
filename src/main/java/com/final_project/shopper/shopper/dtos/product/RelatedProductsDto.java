package com.final_project.shopper.shopper.dtos.product;

import com.final_project.shopper.shopper.models.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RelatedProductsDto {
    private Long id;
    private String name;
    private ProductCategory productCategory;
    private String photoUrl;
    private Double price;
    private Double discountPrice;
}
