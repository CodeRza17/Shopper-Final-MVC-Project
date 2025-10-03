package com.final_project.shopper.shopper.controller;

import com.final_project.shopper.shopper.dtos.contact.ContactSendMessageDto;
import com.final_project.shopper.shopper.dtos.contactInfo.ContactInfoDto;
import com.final_project.shopper.shopper.models.Brand;
import com.final_project.shopper.shopper.models.User;
import com.final_project.shopper.shopper.services.BrandService;
import com.final_project.shopper.shopper.services.ContactInfoService;
import com.final_project.shopper.shopper.services.ContactService;
import com.final_project.shopper.shopper.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class SupportController {
    public final BrandService brandService;
    public final ContactInfoService contactInfoService;
    public final UserService userService;
    public final ContactService contactService;

    @GetMapping("/contact/selectBrand")
    @PreAuthorize("isAuthenticated()")
    public String selectBrandPage(Model model) {
        List<Brand> brands = brandService.findAll();
        model.addAttribute("brands", brands);
        return "select-brand";
    }
    @GetMapping("/contact/{id}")
    @PreAuthorize("isAuthenticated()")
    public String contact(@PathVariable Long id, Model model, Principal principal){
        ContactInfoDto contactInfoDto = contactInfoService.findContactInfoByBrandId(id);
        model.addAttribute("contactInfo",contactInfoDto);
        String email = principal.getName();
        User findUser = userService.findUserByEmail(email);
        model.addAttribute("user", findUser);
        model.addAttribute("brandId", id);
        return "contact";
    }
    @PostMapping("/contact/sendMessage")
    @PreAuthorize("isAuthenticated()")
    public String sendMessage(ContactSendMessageDto contactSendMessageDto, @RequestParam Long brandId, Principal principal){
        String email = principal.getName();
        boolean result = contactService.sendMessageToBrand(contactSendMessageDto, email, brandId);
        if (result){
            return "redirect:/";
        }
        return "/contact/selectBrand";
    }


}
