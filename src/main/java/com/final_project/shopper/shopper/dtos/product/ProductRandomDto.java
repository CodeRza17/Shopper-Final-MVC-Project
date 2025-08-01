package com.final_project.shopper.shopper.dtos.product;


import com.final_project.shopper.shopper.models.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRandomDto {
    private Long id;
    private String name;
    private Double price;
    private String photoUrl;
    private ProductCategory productCategory;
}
