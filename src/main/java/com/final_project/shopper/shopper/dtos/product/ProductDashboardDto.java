package com.final_project.shopper.shopper.dtos.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDashboardDto {
    private Long id;
    private String name;
    private Integer countStock;
    private Double price;
    private String photoUrl;
}
