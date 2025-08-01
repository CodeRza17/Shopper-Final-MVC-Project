package com.final_project.shopper.shopper.sevices;

import com.final_project.shopper.shopper.dtos.product.*;
import com.final_project.shopper.shopper.models.Product;
import com.final_project.shopper.shopper.payloads.PaginationPayload;


import java.util.List;

public interface ProductService {
    List<ProductDashboardDto> getACurrentBrandAllProducts();

    boolean createProduct(ProductCreateDto productCreateDto );

    Object getAllAudienceCategories();

    Object getAllProductCategories();

    ProductUpdateDto getUpdateProducts(Long id);

    boolean updateProduct(Long id,ProductUpdateDto productUpdateDto);

    ProductUpdateDto getDeletedProducts(Long id);

    boolean deleteProduct(Long id);

    PaginationPayload<ProductAudienceDto> getProductsFiltered(String audience, List<Long> categoryIds, List<Long> brandIds, Integer currentPage);

    List<ProductRandomDto> getRandomProducts();

    List<ProductBsDto> getTopFourProducts();

    List<ProductAudienceDto> getBestSeller();

    ProductDetailDto findProductDetailsById(Long id);

    List<RelatedProductsDto> getRelatedProducts(Long id);

    Product findProductById(Long productId);
}
