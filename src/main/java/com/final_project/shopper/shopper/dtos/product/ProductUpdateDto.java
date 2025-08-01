package com.final_project.shopper.shopper.dtos.product;

import com.final_project.shopper.shopper.dtos.sizes.SizesDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductUpdateDto {
    private Long id;
    private String name;
    private Double rewardPoints;
    private String productCode;
    private Integer countStock;
    private Double price;
    private String photoUrl;
    private List<SizesDto> sizesDtos;
    private String color;
    private String description;
    private Long audienceCategoryId;
    private Long productCategoryId;
    private Double discountPrice;
}
