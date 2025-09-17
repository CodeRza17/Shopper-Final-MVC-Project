package com.final_project.shopper.shopper.controller.ownerDashboard;

import com.final_project.shopper.shopper.dtos.adPaymentInfo.AdPaymentInfoDashboardDto;
import com.final_project.shopper.shopper.enums.TimeType;
import com.final_project.shopper.shopper.models.AdPaymentInfo;
import com.final_project.shopper.shopper.repositories.AdPaymentInfoRepository;
import com.final_project.shopper.shopper.services.AdPaymentInfoService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class OwnerHomeController {
    private final AdPaymentInfoRepository adPaymentInfoRepository;
    private final AdPaymentInfoService adPaymentInfoService;
    private final ModelMapper modelMapper;

    public OwnerHomeController(AdPaymentInfoRepository adPaymentInfoRepository, AdPaymentInfoService adPaymentInfoService, ModelMapper modelMapper) {
        this.adPaymentInfoRepository = adPaymentInfoRepository;
        this.adPaymentInfoService = adPaymentInfoService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/owner-dashboard")
    @PreAuthorize("hasRole('OWNER')")
    public String index(){
        return "/owner-dashboard/index";
    }

    @GetMapping("/owner-dashboard/ad-payment-infos")
    @PreAuthorize("hasRole('OWNER')")
    public String paymentInfos(Model model){
        List<AdPaymentInfo> findAdPaymentInfo = adPaymentInfoRepository.findAll();
        List<AdPaymentInfoDashboardDto> adPaymentInfoDashboardDtoList = findAdPaymentInfo.stream().map(adPaymentInfo -> modelMapper.map(adPaymentInfo, AdPaymentInfoDashboardDto.class)).toList();
        model.addAttribute("paymentInfos", adPaymentInfoDashboardDtoList);
        return "/owner-dashboard/ad-payment-infos/index";
    }

    @GetMapping("/owner-dashboard/ad-payment-infos/add")
    @PreAuthorize("hasRole('OWNER')")
    public String createAdPaymentInfoForm(Model model) {
        model.addAttribute("timeTypes", TimeType.values());
        return "/owner-dashboard/ad-payment-infos/create";
    }

    @PostMapping("/owner-dashboard/ad-payment-infos/add")
    public String createAdPaymentInfoForm(AdPaymentInfoDashboardDto adPaymentInfoDashboardDto) {
        boolean result = adPaymentInfoService.addNewPaymentInfo(adPaymentInfoDashboardDto);
        if(result){
            return "redirect:/owner-dashboard/ad-payment-infos";
        }
        return "/owner-dashboard/ad-payment-infos/create";
    }

    @PostMapping("/owner-dashboard/ad-payment-infos/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public String deletePaymentInfo(@PathVariable Long id) {
        adPaymentInfoRepository.deleteById(id);
        return "redirect:/owner-dashboard/ad-payment-infos";
    }

}
