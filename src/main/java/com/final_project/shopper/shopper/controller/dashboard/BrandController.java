package com.final_project.shopper.shopper.controller.dashboard;

import com.final_project.shopper.shopper.dtos.brand.BrandDashboardDto;
import com.final_project.shopper.shopper.dtos.brand.BrandDeleteDto;
import com.final_project.shopper.shopper.dtos.brand.BrandUpdateDto;
import com.final_project.shopper.shopper.dtos.contactInfo.ContactInfoDto;
import com.final_project.shopper.shopper.sevices.BrandService;
import com.final_project.shopper.shopper.sevices.ContactInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class BrandController {
    private final BrandService brandService;
    private final ContactInfoService contactInfoService;

    public BrandController(BrandService brandService, ContactInfoService contactInfoService) {
        this.brandService = brandService;
        this.contactInfoService = contactInfoService;
    }

    @GetMapping("/dashboard/brand")
    public String brand(Model model){
        BrandDashboardDto brandDashboardDto = brandService.getBrand();
        model.addAttribute("brand", brandDashboardDto);
        return "dashboard/brand/index.html";
    }

    @GetMapping("/dashboard/brand/update")
    public String brandUpdate(Model model){
        BrandUpdateDto brandUpdateDto = brandService.getUpdatedBrand();
        ContactInfoDto contactInfoDto = contactInfoService.getUpdatedContactInfos();
        model.addAttribute("updatedBrand", brandUpdateDto);
        model.addAttribute("updatedContactInfos", contactInfoDto);
        return "dashboard/brand/update.html";
    }
    @PostMapping("/dashboard/brand/update")
    public String brandUpdate( BrandUpdateDto brandUpdateDto, ContactInfoDto contactInfoDto){
        boolean result = brandService.UpdateBrand( brandUpdateDto,contactInfoDto);
        if (result){
            return "redirect:/dashboard/brand";
        }
        return "dashboard/brand/update.html";
    }

    @GetMapping("/dashboard/brand/delete")
    public String delete(Model model) {
        BrandDeleteDto brandDeleteDto = brandService.getDeletedBrand();
        if (brandDeleteDto == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Brand not found");
        }
        model.addAttribute("brand", brandDeleteDto);
        System.out.println("Brand to delete: " + brandDeleteDto);
        return "dashboard/brand/delete.html";
    }

    @PostMapping("/dashboard/brand/delete")
    public String delete(){
        boolean result = brandService.deleteBrand();
        if (result){
            return "redirect:/logout";
        }
        return "dashboard/brand/delete.html";
    }
}
