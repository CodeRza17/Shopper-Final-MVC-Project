package com.final_project.shopper.shopper.dtos.product;

import com.final_project.shopper.shopper.dtos.audienceCatergory.AudienceCategoryDto;
import com.final_project.shopper.shopper.dtos.brand.BrandDto;
import com.final_project.shopper.shopper.dtos.productCategory.ProductCategoryDto;
import com.final_project.shopper.shopper.dtos.sizes.SizesDto;
import com.final_project.shopper.shopper.models.AudienceCategory;
import com.final_project.shopper.shopper.models.Brand;
import com.final_project.shopper.shopper.models.ProductCategory;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateDto {
    private String name;
    private Double rewardPoints;
    private String productCode;
    private Double price;
    private String photoUrl;
    private List<SizesDto> sizesDtos;
    private String color;
    private String description;
    private Long audienceCategoryId;
    private Long productCategoryId;
    private Double discountPrice;
    private Long salesCount;
}
