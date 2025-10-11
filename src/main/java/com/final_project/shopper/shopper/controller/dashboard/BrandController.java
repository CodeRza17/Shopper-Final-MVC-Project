package com.final_project.shopper.shopper.controller.dashboard;

import com.final_project.shopper.shopper.dtos.brand.BrandDashboardDto;
import com.final_project.shopper.shopper.dtos.brand.BrandDeleteDto;
import com.final_project.shopper.shopper.dtos.brand.BrandUpdateDto;
import com.final_project.shopper.shopper.dtos.contactInfo.ContactInfoDto;
import com.final_project.shopper.shopper.services.BrandService;
import com.final_project.shopper.shopper.services.ContactInfoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

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
        ContactInfoDto contactInfoDto = contactInfoService.getUpdatedContactInfos();
        model.addAttribute("updatedContactInfos", contactInfoDto);
        return "dashboard/brand/index.html";
    }

    @GetMapping("/dashboard/brand/update")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public String brandUpdate(Model model){
        BrandUpdateDto brandUpdateDto = brandService.getUpdatedBrand();
        ContactInfoDto contactInfoDto = contactInfoService.getUpdatedContactInfos();
        model.addAttribute("updatedBrand", brandUpdateDto);
        model.addAttribute("updatedContactInfos", contactInfoDto);
        return "dashboard/brand/update.html";
    }
    @PostMapping("/dashboard/brand/update")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public String brandUpdate( BrandUpdateDto brandUpdateDto, ContactInfoDto contactInfoDto){
        boolean result = brandService.UpdateBrand( brandUpdateDto,contactInfoDto);
        if (result){
            return "redirect:/dashboard/brand";
        }
        return "dashboard/brand/update.html";
    }

    @GetMapping("/dashboard/brand/delete")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
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
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public String delete(HttpServletRequest request, HttpServletResponse response) {
        boolean result = brandService.deleteBrand();

        if (result) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null) {
                new SecurityContextLogoutHandler().logout(request, response, auth);
            }
            return "redirect:/login";
        }

        return "dashboard/brand/delete.html";
    }
}
