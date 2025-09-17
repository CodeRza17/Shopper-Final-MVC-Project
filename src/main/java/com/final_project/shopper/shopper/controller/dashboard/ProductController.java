package com.final_project.shopper.shopper.controller.dashboard;


import com.final_project.shopper.shopper.dtos.product.ProductCreateDto;
import com.final_project.shopper.shopper.dtos.product.ProductDashboardDto;
import com.final_project.shopper.shopper.dtos.product.ProductUpdateDto;
import com.final_project.shopper.shopper.models.Brand;
import com.final_project.shopper.shopper.models.User;
import com.final_project.shopper.shopper.repositories.ProductRepository;
import com.final_project.shopper.shopper.repositories.UserRepository;
import com.final_project.shopper.shopper.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @GetMapping("/dashboard/products")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN', 'ROLE_MANAGER')")
    public String index(Model model){
        List<ProductDashboardDto> productDashboardDtos = productService.getACurrentBrandAllProducts();
        model.addAttribute("products", productDashboardDtos);
        return "dashboard/product/index.html";
    }
    @GetMapping("/dashboard/product/create")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN', 'ROLE_MANAGER')")
    public String create(Model model){
        model.addAttribute("productCreateDto", new ProductCreateDto());
        model.addAttribute("audienceCategories", productService.getAllAudienceCategories());
        model.addAttribute("productCategories", productService.getAllProductCategories());
        return "dashboard/product/create.html";
    }



    @PostMapping("/dashboard/product/create")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN', 'ROLE_MANAGER')")
    public String create(ProductCreateDto productCreateDto){
        boolean result = productService.createProduct(productCreateDto);
        if (result){
            return "redirect:/dashboard/products";
        }
        return "dashboard/product/create.html";
    }

    @GetMapping("/dashboard/product/update/{id}")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_MANAGER')")
    public String update(@PathVariable Long id, Model model, Principal principal){
        Brand brand = productRepository.findById(id).orElseThrow().getBrand();
        List<User> users = userRepository.findAllByBrandId(brand.getId());
        User currentUser = userRepository.findByEmail(principal.getName());

        boolean allMatch = users.stream()
                .allMatch(u -> u.getBrand().getId().equals(currentUser.getBrand().getId()));

        if (!allMatch) {
            throw new AccessDeniedException("You are not authorized to edit these users");
        }

        model.addAttribute("audienceCategories", productService.getAllAudienceCategories());
        model.addAttribute("productCategories", productService.getAllProductCategories());
        ProductUpdateDto productUpdateDto = productService.getUpdateProducts(id);
        model.addAttribute("product", productUpdateDto);
        return "dashboard/product/update.html";
    }
    @PostMapping("/dashboard/product/update/{id}")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_MANAGER')")
    public String update(@PathVariable Long id, @ModelAttribute("product") ProductUpdateDto productUpdateDto, Model model){
        boolean result = productService.updateProduct(id, productUpdateDto);
        if (result){
            return "redirect:/dashboard/products";
        }else{
            model.addAttribute("error", "Update failed. Check logs.");
            model.addAttribute("audienceCategories", productService.getAllAudienceCategories());
            model.addAttribute("productCategories", productService.getAllProductCategories());
            return "dashboard/product/update.html";
        }
    }
    @GetMapping("/dashboard/product/delete/{id}")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_MANAGER')")
    public String delete(@PathVariable Long id, Model model, Principal principal){
        Brand brand = productRepository.findById(id).orElseThrow().getBrand();
        List<User> users = userRepository.findAllByBrandId(brand.getId());
        User currentUser = userRepository.findByEmail(principal.getName());

        boolean allMatch = users.stream()
                .allMatch(u -> u.getBrand().getId().equals(currentUser.getBrand().getId()));

        if (!allMatch) {
            throw new AccessDeniedException("You are not authorized to edit these users");
        }
        ProductUpdateDto productUpdateDto = productService.getDeletedProducts(id);
        model.addAttribute("product", productUpdateDto);
        return "dashboard/product/delete.html";
    }

    @PostMapping("/dashboard/product/delete/{id}")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_MANAGER')")
    public String delete(@PathVariable Long id){
        boolean result = productService.deleteProduct(id);
        if (result){
            return "redirect:/dashboard/products";
        }
        return "dashboard/product/delete.html";
    }

}
