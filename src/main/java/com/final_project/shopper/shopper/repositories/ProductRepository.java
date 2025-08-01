package com.final_project.shopper.shopper.repositories;

import com.final_project.shopper.shopper.models.Product;
import com.final_project.shopper.shopper.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Arrays;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByBrandId(Long id);

    @Query("SELECT p FROM Product p " +
            "WHERE LOWER(p.audienceCategory.name) = LOWER(:audience) " +
            "AND (:categoryIds IS NULL OR p.productCategory.id IN :categoryIds) " +
            "AND (:brandIds IS NULL OR p.brand.id IN :brandIds)")
    Page<Product> findFilteredProducts(@Param("audience") String audience,
                                       @Param("categoryIds") List<Long> categoryIds,
                                       @Param("brandIds") List<Long> brandIds,
                                       Pageable pageable);



    @Query(value = "SELECT * FROM products ORDER BY RANDOM() LIMIT 10", nativeQuery = true)
    List<Product> findRandom10Products();

    List<Product> findTop4ByOrderBySalesCountDesc();


    List<Product> findTop12ByOrderBySalesCountDesc();

    List<Product> findByProductCategoryId(Long id);
}
