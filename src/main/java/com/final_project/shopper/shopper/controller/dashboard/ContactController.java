package com.final_project.shopper.shopper.controller.dashboard;

import com.final_project.shopper.shopper.dtos.contact.ContactDashboardDto;
import com.final_project.shopper.shopper.models.Brand;
import com.final_project.shopper.shopper.models.User;
import com.final_project.shopper.shopper.repositories.BrandRepository;
import com.final_project.shopper.shopper.repositories.ContactRepository;
import com.final_project.shopper.shopper.services.ContactService;
import com.final_project.shopper.shopper.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import java.security.Principal;
import java.util.List;

@Controller
@AllArgsConstructor
public class ContactController {
    private final UserService userService;
    private final BrandRepository brandRepository;
    private final ContactService contactService;
    private final ContactRepository contactRepository;

    @GetMapping("/dashboard/contact")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN', 'ROLE_MANAGER')")
    public String index(Principal principal, Model model){
        String email = principal.getName();
        User user = userService.findUserByEmail(email);
        Brand brand = brandRepository.findByUsersId(user.getId());
        List<ContactDashboardDto> contactDashboardDtoList = contactService.findByBrandId(brand.getId());
        model.addAttribute("messages", contactDashboardDtoList);
        return "/dashboard/contact/index.html";
    }

}
