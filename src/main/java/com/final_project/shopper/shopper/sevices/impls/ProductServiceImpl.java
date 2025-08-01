package com.final_project.shopper.shopper.sevices.impls;

import com.final_project.shopper.shopper.dtos.audienceCatergory.AudienceCategoryDto;
import com.final_project.shopper.shopper.dtos.product.*;
import com.final_project.shopper.shopper.dtos.productCategory.ProductCategoryDto;
import com.final_project.shopper.shopper.dtos.sizes.SizesDto;
import com.final_project.shopper.shopper.models.*;
import com.final_project.shopper.shopper.payloads.PaginationPayload;
import com.final_project.shopper.shopper.repositories.*;
import com.final_project.shopper.shopper.sevices.ProductService;
import com.final_project.shopper.shopper.sevices.SizesService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final AudienceCategoryRepository audienceCategoryRepository;
    private final ProductCategoryRepository productCategoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, UserRepository userRepository, ModelMapper modelMapper, AudienceCategoryRepository audienceCategoryRepository, ProductCategoryRepository productCategoryRepository, SizesRepository sizesRepository, SizesService sizesService) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.audienceCategoryRepository = audienceCategoryRepository;
        this.productCategoryRepository = productCategoryRepository;
    }

    @Override
    public List<ProductDashboardDto> getACurrentBrandAllProducts() {
        String email = UserDetailService.getCurrentUserEmail();
        User user = userRepository.findByEmail(email);
        Brand brand = user.getBrand();
        List<Product> findProducts = productRepository.findAllByBrandId(brand.getId());
        List<ProductDashboardDto> productDashboardDtos =
                findProducts.stream()
                        .map(product -> modelMapper.map(product, ProductDashboardDto.class))
                        .toList();
        return productDashboardDtos;
    }

    @Override
    @Transactional
    public boolean createProduct(ProductCreateDto productCreateDto) {
        try {
            String email = UserDetailService.getCurrentUserEmail();
            User user = userRepository.findByEmail(email);
            Brand brand = user.getBrand();

            Product product = new Product();
            product.setName(productCreateDto.getName());
            product.setRewardPoints(productCreateDto.getRewardPoints());
            product.setProductCode(productCreateDto.getProductCode());
            product.setDiscountPrice(productCreateDto.getDiscountPrice());
            product.setSalesCount(0L);

            List<Sizes> sizes = productCreateDto.getSizesDtos().stream()
                    .map(sizeDto -> {
                        Sizes size = modelMapper.map(sizeDto, Sizes.class);
                        size.setProduct(product);
                        return size;
                    }).toList();

            product.setSizeQuantities(sizes);

            Integer totalQuantity = sizes.stream()
                    .mapToInt(size -> size.getQuantity() != null ? size.getQuantity() : 0)
                    .sum();

            product.setCountStock(totalQuantity);
            product.setPrice(productCreateDto.getPrice());
            product.setPhotoUrl(productCreateDto.getPhotoUrl());
            product.setColor(productCreateDto.getColor());
            product.setDescription(productCreateDto.getDescription());
            product.setBrand(brand);


            AudienceCategory audienceCategory = audienceCategoryRepository.findById(productCreateDto.getAudienceCategoryId())
                    .orElseThrow(() -> new RuntimeException("AudienceCategory not found"));

            ProductCategory productCategory = productCategoryRepository.findById(productCreateDto.getProductCategoryId())
                    .orElseThrow(() -> new RuntimeException("ProductCategory not found"));

            product.setAudienceCategory(audienceCategory);
            product.setProductCategory(productCategory);

            productRepository.save(product);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Object getAllAudienceCategories() {
        return audienceCategoryRepository.findAll().stream()
                .map(category -> modelMapper.map(category, AudienceCategoryDto.class))
                .toList();
    }

    @Override
    public Object getAllProductCategories() {
        return productCategoryRepository.findAll().stream()
                .map(category -> modelMapper.map(category, ProductCategoryDto.class))
                .toList();
    }

    @Override
    public ProductUpdateDto getUpdateProducts(Long id) {
        Product findProduct = productRepository.findById(id).orElseThrow();
        ProductUpdateDto productUpdateDto = modelMapper.map(findProduct, ProductUpdateDto.class);


        List<SizesDto> sizesDtos = findProduct.getSizeQuantities().stream()
                .map(size -> modelMapper.map(size, SizesDto.class))
                .collect(Collectors.toList());
        productUpdateDto.setSizesDtos(sizesDtos);

        return productUpdateDto;
    }

    @Override
    public boolean updateProduct(Long id, ProductUpdateDto productUpdateDto) {
        try {
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Product not found with ID: " + id));


            String email = UserDetailService.getCurrentUserEmail();
            User user = userRepository.findByEmail(email);
            Brand brand = user.getBrand();


            product.setName(productUpdateDto.getName());
            product.setRewardPoints(productUpdateDto.getRewardPoints());
            product.setProductCode(productUpdateDto.getProductCode());
            product.setPrice(productUpdateDto.getPrice());
            product.setDiscountPrice(productUpdateDto.getDiscountPrice());
            product.setPhotoUrl(productUpdateDto.getPhotoUrl());
            product.setColor(productUpdateDto.getColor());
            product.setDescription(productUpdateDto.getDescription());
            product.setBrand(brand);


            AudienceCategory audienceCategory = audienceCategoryRepository.findById(productUpdateDto.getAudienceCategoryId())
                    .orElseThrow(() -> new RuntimeException("AudienceCategory not found"));
            product.setAudienceCategory(audienceCategory);

            ProductCategory productCategory = productCategoryRepository.findById(productUpdateDto.getProductCategoryId())
                    .orElseThrow(() -> new RuntimeException("ProductCategory not found"));
            product.setProductCategory(productCategory);


            List<Sizes> updatedSizes = productUpdateDto.getSizesDtos().stream()
                    .map(sizeDto -> {
                        Sizes size = modelMapper.map(sizeDto, Sizes.class);
                        size.setProduct(product);
                        return size;
                    }).toList();


            Integer totalQuantity = updatedSizes.stream()
                    .mapToInt(size -> size.getQuantity() != null ? size.getQuantity() : 0)
                    .sum();
            product.setCountStock(totalQuantity);


            product.getSizeQuantities().clear();
            product.getSizeQuantities().addAll(updatedSizes);

            productRepository.save(product);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ProductUpdateDto getDeletedProducts(Long id) {
        Product findProduct = productRepository.findById(id).orElseThrow();
        ProductUpdateDto productUpdateDto = modelMapper.map(findProduct, ProductUpdateDto.class);
        return productUpdateDto;
    }

    @Override
    public boolean deleteProduct(Long id) {
        try{
            Product findProduct = productRepository.findById(id).orElseThrow();
            productRepository.delete(findProduct);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public PaginationPayload<ProductAudienceDto> getProductsFiltered(
            String audience,
            List<Long> categoryIds,
            List<Long> brandIds,
            Integer currentPage) {

        Pageable pageable = PageRequest.of(currentPage - 1, 9);

        Page<Product> findProducts = productRepository.findFilteredProducts(
                audience, categoryIds, brandIds, pageable);

        List<ProductAudienceDto> dtoList = findProducts.getContent()
                .stream()
                .map(product -> modelMapper.map(product, ProductAudienceDto.class))
                .toList();

        PaginationPayload<ProductAudienceDto> result = new PaginationPayload<>();
        result.setCurrentPage(currentPage);
        result.setTotalPage(findProducts.getTotalPages());
        result.setModels(dtoList);

        return result;
    }

    @Override
    public List<ProductRandomDto> getRandomProducts() {
        List<Product> findRandomProduct = productRepository.findRandom10Products();
        List<ProductRandomDto> productRandomDtoList = findRandomProduct.stream()
                .map(product -> modelMapper.map(product, ProductRandomDto.class))
                .toList();
        return productRandomDtoList;
    }

    @Override
    public List<ProductBsDto> getTopFourProducts() {
        List<Product> productList = productRepository.findTop4ByOrderBySalesCountDesc();

        List<ProductBsDto> productBsDtoList = productList.stream()
                .map(product -> modelMapper.map(product, ProductBsDto.class))
                .toList();
        return productBsDtoList;
    }

    @Override
    public List<ProductAudienceDto> getBestSeller() {
        List<Product> productList = productRepository.findTop12ByOrderBySalesCountDesc();
        List<ProductAudienceDto> productBsDtoList = productList.stream()
                .map(product -> modelMapper.map(product, ProductAudienceDto.class))
                .toList();
        return productBsDtoList;
    }

    @Override
    public ProductDetailDto findProductDetailsById(Long id) {
        Product findProduct = productRepository.findById(id).orElseThrow();

        ProductDetailDto productDetailDto = modelMapper.map(findProduct, ProductDetailDto.class);

        return productDetailDto;
    }

    @Override
    public List<RelatedProductsDto> getRelatedProducts(Long id) {
        Product findProduct = productRepository.findById(id).orElseThrow();
        ProductCategory productCategory = findProduct.getProductCategory();

        List<Product> findRelatedProducts = productRepository.findByProductCategoryId(productCategory.getId());

        List<RelatedProductsDto> relatedProductsDtoList = findRelatedProducts.stream()
                .map(product -> modelMapper.map(product, RelatedProductsDto.class))
                .toList();

        return relatedProductsDtoList;
    }

    @Override
    public Product findProductById(Long productId) {
        return productRepository.findById(productId).orElseThrow();
    }


}
