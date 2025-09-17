package com.final_project.shopper.shopper.services.impls;

import com.final_project.shopper.shopper.dtos.productCategory.ProductCategoryCreateDto;
import com.final_project.shopper.shopper.dtos.productCategory.ProductCategoryDashboardDto;
import com.final_project.shopper.shopper.dtos.productCategory.ProductCategoryUpdateDto;
import com.final_project.shopper.shopper.helpers.SeoHelper;
import com.final_project.shopper.shopper.models.ProductCategory;
import com.final_project.shopper.shopper.repositories.ProductCategoryRepository;
import com.final_project.shopper.shopper.services.ProductCategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    private final ProductCategoryRepository productCategoryRepository;
    private final ModelMapper modelMapper;

    public ProductCategoryServiceImpl(ProductCategoryRepository productCategoryRepository, ModelMapper modelMapper) {
        this.productCategoryRepository = productCategoryRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public boolean createCategory(ProductCategoryCreateDto categoryCreateDto) {
        try {
            ProductCategory findCategory = productCategoryRepository.findByNameIgnoreCase(categoryCreateDto.getName());
            if (findCategory != null){
                return false;
            }
            ProductCategory category = new ProductCategory();
            category.setName(categoryCreateDto.getName());
            String seoUrl = SeoHelper.createSeoUrl(categoryCreateDto.getName());
            category.setSeoUrl(seoUrl);
            productCategoryRepository.save(category);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<ProductCategoryDashboardDto> getAllProductCategories() {
        List<ProductCategory> findProduct = productCategoryRepository.findAll();
        List<ProductCategoryDashboardDto> categoryDashboardDtos = findProduct.stream().map(productCategory -> modelMapper.map(productCategory, ProductCategoryDashboardDto.class)).toList();
        return categoryDashboardDtos;
    }

    @Override
    public ProductCategoryUpdateDto getUpdateCategories(Long id) {
        ProductCategory findCategory = productCategoryRepository.findById(id).orElseThrow();
        ProductCategoryUpdateDto categoryUpdateDto = modelMapper.map(findCategory, ProductCategoryUpdateDto.class);
        return categoryUpdateDto;
    }

    @Override
    public boolean updateCategory(Long id, ProductCategoryUpdateDto categoryUpdateDto) {
        try{
            ProductCategory findCategory = productCategoryRepository.findById(id).orElseThrow();
            findCategory.setName(categoryUpdateDto.getName());

            String seoUrl = SeoHelper.createSeoUrl(categoryUpdateDto.getName());
            findCategory.setSeoUrl(seoUrl);

            productCategoryRepository.save(findCategory);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public ProductCategoryUpdateDto getDeletedCategories(Long id) {
        ProductCategory findCategory = productCategoryRepository.findById(id).orElseThrow();
        ProductCategoryUpdateDto categoryUpdateDto = modelMapper.map(findCategory, ProductCategoryUpdateDto.class);
        return categoryUpdateDto;
    }

    @Override
    public boolean deleteCategory(Long id) {
        try{
            ProductCategory findCategory = productCategoryRepository.findById(id).orElseThrow();
            productCategoryRepository.delete(findCategory);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<ProductCategory> getAll() {
        return productCategoryRepository.findAll();
    }
}
