package com.final_project.shopper.shopper.controller;

import com.final_project.shopper.shopper.dtos.ad.AdHomeDto;
import com.final_project.shopper.shopper.dtos.product.ProductAudienceDto;
import com.final_project.shopper.shopper.models.Brand;
import com.final_project.shopper.shopper.repositories.BrandRepository;
import com.final_project.shopper.shopper.services.AdService;
import com.final_project.shopper.shopper.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    private final AdService adService;
    private final ProductService productService;
    private final BrandRepository brandRepository;

    public HomeController(AdService adService, ProductService productService, BrandRepository brandRepository) {
        this.adService = adService;
        this.productService = productService;
        this.brandRepository = brandRepository;
    }


    @GetMapping("/")
    public String index(Model model){
        List<AdHomeDto> adHomeDtoList = adService.findAllActiveAds();
        model.addAttribute("ads", adHomeDtoList);
        List<ProductAudienceDto> productBsDtoList = productService.getBestSeller();

        model.addAttribute("bsProduct", productBsDtoList);

        List<Brand> brands = brandRepository.findTop6ByOrderByIdAsc();
        model.addAttribute("brands", brands);
        return "index";
    }
}
