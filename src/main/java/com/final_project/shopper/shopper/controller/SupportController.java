package com.final_project.shopper.shopper.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SupportController {
    @GetMapping("/contact")
    @PreAuthorize("isAuthenticated()")
    public String contact(){
        return "contact.html";
    }
}
