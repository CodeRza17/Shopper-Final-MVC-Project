package com.final_project.shopper.shopper.controller.dashboard;


import com.final_project.shopper.shopper.dtos.productCategory.ProductCategoryCreateDto;
import com.final_project.shopper.shopper.dtos.productCategory.ProductCategoryDashboardDto;
import com.final_project.shopper.shopper.dtos.productCategory.ProductCategoryUpdateDto;
import com.final_project.shopper.shopper.sevices.ProductCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductCategoryController {
    private final ProductCategoryService productCategoryService;

    @GetMapping("/dashboard/product-categories")
    public String index(Model model){
        List<ProductCategoryDashboardDto> categoryDashboardDtos = productCategoryService.getAllProductCategories();
        model.addAttribute("productCategories", categoryDashboardDtos);
        return "dashboard/product-category/index.html";
    }
    @GetMapping("/dashboard/product-category/create")
    public String create(){
        return "dashboard/product-category/create.html";
    }
    @PostMapping("/dashboard/product-category/create")
    public String create(ProductCategoryCreateDto categoryCreateDto){
        boolean result = productCategoryService.createCategory(categoryCreateDto);
        if (result){
            return "redirect:/dashboard/product-categories";
        }
        return "dashboard/product-category/create.html";
    }
    @GetMapping("/dashboard/product-category/update/{id}")
    public String update(@PathVariable Long id, Model model){
        ProductCategoryUpdateDto categoryUpdateDto = productCategoryService.getUpdateCategories(id);
        model.addAttribute("productCategory", categoryUpdateDto);
        return "dashboard/product-category/update.html";
    }

    @PostMapping("/dashboard/product-category/update/{id}")
    public String update(@PathVariable Long id, ProductCategoryUpdateDto categoryUpdateDto){
        boolean result = productCategoryService.updateCategory(id, categoryUpdateDto);
        if (result){
            return "redirect:/dashboard/product-categories";
        }
        return "dashboard/product-category/update.html";
    }

    @GetMapping("/dashboard/product-category/delete/{id}")
    public String delete(@PathVariable Long id, Model model){
        ProductCategoryUpdateDto categoryUpdateDto = productCategoryService.getDeletedCategories(id);
        model.addAttribute("productCategory", categoryUpdateDto);
        return "dashboard/product-category/delete.html";
    }

    @PostMapping("/dashboard/product-category/delete/{id}")
    public String delete(@PathVariable Long id){
        boolean result = productCategoryService.deleteCategory(id);
        if (result){
            return "redirect:/dashboard/product-categories";
        }
        return "dashboard/product-category/delete.html";
    }
}
