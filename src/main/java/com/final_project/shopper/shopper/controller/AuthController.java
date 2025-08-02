package com.final_project.shopper.shopper.controller;

import com.final_project.shopper.shopper.dtos.auth.UserRegisterDto;
import com.final_project.shopper.shopper.dtos.brand.BrandCreateDto;
import com.final_project.shopper.shopper.models.Brand;
import com.final_project.shopper.shopper.models.User;
import com.final_project.shopper.shopper.otp.OtpService;
import com.final_project.shopper.shopper.repositories.BrandRepository;
import com.final_project.shopper.shopper.repositories.UserRepository;
import com.final_project.shopper.shopper.sevices.BrandService;
import com.final_project.shopper.shopper.sevices.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@Controller
public class AuthController {

    private final UserService userService;
    private final BrandService brandService;
    private final BrandRepository brandRepository;
    private final UserRepository userRepository;
    private final OtpService otpService;

    public AuthController(UserService userService, BrandService brandService, BrandRepository brandRepository, UserRepository userRepositoryjn, OtpService otpService) {
        this.userService = userService;
        this.brandService = brandService;
        this.brandRepository = brandRepository;
        this.userRepository = userRepositoryjn;
        this.otpService = otpService;
    }

    @GetMapping("/login")
    public String login(){
        return "auth/login.html";
    }



    @GetMapping("/register")
    public String register(Model model){
        List<Brand> brands = brandRepository.findAll();
        model.addAttribute("brands", brands);
        return "auth/register.html";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute UserRegisterDto userRegisterDto, Model model) {
        List<Brand> brands = brandRepository.findAll();
        model.addAttribute("brands", brands);

        boolean result = userService.register(userRegisterDto);
        if (result){
            model.addAttribute("email", userRegisterDto.getEmail());
            return "auth/verify-otp";
        }else{
            model.addAttribute("errorMessage", "This email is already in use");
            return "auth/register.html";
        }
    }




    @GetMapping("/create-company")
    public String createCompany(){

        return "auth/create-company.html";
    }
    @PostMapping("/create-company")
    public String createCompany(@ModelAttribute BrandCreateDto brandCreateDto){
        boolean result = brandService.create(brandCreateDto);
        if (result){
            return "redirect:/register";
        }
        return "auth/create-company";
    }


}
