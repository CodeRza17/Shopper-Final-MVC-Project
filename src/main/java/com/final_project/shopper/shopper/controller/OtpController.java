package com.final_project.shopper.shopper.controller;

import com.final_project.shopper.shopper.models.User;
import com.final_project.shopper.shopper.otp.OtpService;
import com.final_project.shopper.shopper.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
public class OtpController {
    private final UserRepository userRepository;
    private final OtpService otpService;


    public OtpController(UserRepository userRepository, OtpService otpService) {
        this.userRepository = userRepository;
        this.otpService = otpService;
    }


    @GetMapping("/verify")
    public String showOtpPage(@RequestParam("email") String email, Model model) {
        model.addAttribute("email", email);
        return "auth/verify-otp";
    }

    @PostMapping("/verify")
    public String verifyOtp(@RequestParam String email, @RequestParam String otp, Model model) {
        if (!otpService.verifyOtp(email, otp)) {
            model.addAttribute("error", "OTP is invalid or expired.");
            model.addAttribute("email", email);
            return "auth/verify-otp";
        }


        User user = otpService.getPendingUser(email);
        if (user == null) {
            model.addAttribute("error", "No pending registration found.");
            return "auth/verify-otp";
        }

        user.setIsVerified(true);
        user.setOtpCode(null);
        user.setOtpGeneratedTime(null);

        userRepository.save(user);

        otpService.removePendingUser(email);

        return "redirect:/login?verified=true";
    }


    @PostMapping("/resend-otp")
    public String resendOtp(@RequestParam String email, Model model) {
        User user = otpService.getPendingUser(email);

        if (user == null) {
            model.addAttribute("error", "No pending registration found.");
            return "auth/verify-otp";
        }

        String newOtp = otpService.generateOtp();
        otpService.storePendingUser(email, user, newOtp);
        otpService.sendOtpEmail(email, newOtp);

        model.addAttribute("email", email);
        model.addAttribute("message", "OTP has been resent.");
        return "auth/verify-otp";
    }



}
