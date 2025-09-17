package com.final_project.shopper.shopper.controller.dashboard;

import com.final_project.shopper.shopper.dtos.ad.AdCreateDto;
import com.final_project.shopper.shopper.dtos.ad.AdDashboardDto;
import com.final_project.shopper.shopper.models.AdPaymentInfo;
import com.final_project.shopper.shopper.repositories.AdPaymentInfoRepository;
import com.final_project.shopper.shopper.services.AdService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class AdController {
    private final AdService adService;
    private final AdPaymentInfoRepository adPaymentInfoRepository;

    public AdController(AdService adService, AdPaymentInfoRepository adPaymentInfoRepository) {
        this.adService = adService;
        this.adPaymentInfoRepository = adPaymentInfoRepository;
    }


    @GetMapping("/dashboard/ad")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public String ad(Model model, Principal principal){
        String email = principal.getName();
        List<AdDashboardDto> adDashboardDtoList = adService.findAllByBrand(email);
        model.addAttribute("ads", adDashboardDtoList);
        return "dashboard/ad/index";
    }
    @GetMapping("/dashboard/ad/create")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public String adCreate(Model model){
        List<AdPaymentInfo> adPaymentInfoList = adPaymentInfoRepository.findAll();
        model.addAttribute("ads", adPaymentInfoList);
        return "dashboard/ad/create";
    }
    @PostMapping("/dashboard/ad/create")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public String adCreate(Principal principal, AdCreateDto adCreateDto){
        String email = principal.getName();
        boolean result = adService.createAd(email,adCreateDto);
        if (result){
            return "redirect:/dashboard/ad";
        }else {
            return "dashboard/ad/create";
        }
    }
    @PostMapping("/dashboard/ad/delete/{id}")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public String deleteAd(@PathVariable Long id) {
        boolean result = adService.deleteAd(id);

            return "redirect:/dashboard/ad";

    }


}
