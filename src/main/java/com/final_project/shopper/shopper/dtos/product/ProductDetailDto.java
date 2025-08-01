package com.final_project.shopper.shopper.dtos.product;


import com.final_project.shopper.shopper.dtos.audienceCatergory.AudienceCategoryDto;
import com.final_project.shopper.shopper.dtos.brand.BrandDto;
import com.final_project.shopper.shopper.dtos.productCategory.ProductCategoryDto;
import com.final_project.shopper.shopper.dtos.sizes.SizesDto;
import com.final_project.shopper.shopper.models.Brand;
import com.final_project.shopper.shopper.models.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailDto {
    private Long id;
    private String name;
    private Double rewardPoints;
    private String productCode;
    private Integer countStock;
    private Double price;
    private Double discountPrice;
    private Long salesCount;
    private String photoUrl;
    private String color;
    private String description;
    private Brand brand;
    private ProductCategory productCategory;
}
