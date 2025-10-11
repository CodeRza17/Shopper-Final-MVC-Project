package com.final_project.shopper.shopper.controller.dashboard;

import com.final_project.shopper.shopper.models.Brand;
import com.final_project.shopper.shopper.models.User;
import com.final_project.shopper.shopper.repositories.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.PrimitiveIterator;

@Controller
public class DashboardController {
    private final UserRepository userRepository;

    public DashboardController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/dashboard")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN', 'ROLE_MANAGER')")
    public String index(Principal principal, Model model){
        User user = userRepository.findByEmail(principal.getName());
        model.addAttribute("admin", user);
        Brand brand = user.getBrand();
        model.addAttribute("brand", brand);
        return "/dashboard/index.html";
    }
}
