package com.final_project.shopper.shopper.repositories;

import com.final_project.shopper.shopper.models.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
    ProductCategory findByNameIgnoreCase(String name);
}
