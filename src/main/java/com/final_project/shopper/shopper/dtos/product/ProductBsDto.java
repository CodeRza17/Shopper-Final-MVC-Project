package com.final_project.shopper.shopper.dtos.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductBsDto {
    private Long id;
    private String name;
    private String photoUrl;
}
