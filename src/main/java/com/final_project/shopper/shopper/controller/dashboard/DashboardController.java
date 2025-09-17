package com.final_project.shopper.shopper.controller.dashboard;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {
    @GetMapping("/dashboard")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN', 'ROLE_MANAGER')")
    public String index(){
        return "/dashboard/index.html";
    }
}
