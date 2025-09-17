package com.final_project.shopper.shopper.controller;

import com.final_project.shopper.shopper.models.Product;
import com.final_project.shopper.shopper.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SearchController {

    private final ProductService productService;

    public SearchController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/search")
    public String searchProducts(@RequestParam("keyword") String keyword, Model model) {
        List<Product> results = productService.searchByName(keyword);
        model.addAttribute("results", results);
        model.addAttribute("keyword", keyword);
        return "search-result";
    }
}

