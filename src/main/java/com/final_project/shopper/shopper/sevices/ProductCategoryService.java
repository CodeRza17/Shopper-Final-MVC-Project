package com.final_project.shopper.shopper.sevices;

import com.final_project.shopper.shopper.dtos.productCategory.ProductCategoryCreateDto;
import com.final_project.shopper.shopper.dtos.productCategory.ProductCategoryDashboardDto;
import com.final_project.shopper.shopper.dtos.productCategory.ProductCategoryUpdateDto;
import com.final_project.shopper.shopper.models.ProductCategory;

import java.util.List;

public interface ProductCategoryService {
    boolean createCategory(ProductCategoryCreateDto categoryCreateDto);

    List<ProductCategoryDashboardDto> getAllProductCategories();

    ProductCategoryUpdateDto getUpdateCategories(Long id);

    boolean updateCategory(Long id, ProductCategoryUpdateDto categoryUpdateDto);

    ProductCategoryUpdateDto getDeletedCategories(Long id);

    boolean deleteCategory(Long id);

    List<ProductCategory> getAll();
}
