package com.final_project.shopper.shopper.controller;

import com.final_project.shopper.shopper.services.ForgotPasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class ForgotPasswordController {

    private final ForgotPasswordService forgotPasswordService;


    @GetMapping("/forgot-password")
    public String forgotPasswordForm() {
        return "auth/forgot-password";
    }

    @PostMapping("/forgot-password")
    public String sendOtp(@RequestParam("email") String email, Model model) {
        boolean sent = forgotPasswordService.sendOtp(email);
        if (sent) {
            model.addAttribute("email", email);
            model.addAttribute("message", "OTP code has been sent to your email.");
            return "auth/forgot-password-verify";
        } else {
            model.addAttribute("error", "Email not found.");
            return "auth/forgot-password";
        }
    }


    @GetMapping("/forgot-password/verify")
    public String forgotPasswordVerifyForm(@RequestParam("email") String email, Model model) {
        model.addAttribute("email", email);
        return "auth/forgot-password-verify";
    }

    @PostMapping("/forgot-password/verify")
    public String verifyOtpAndReset(@RequestParam("email") String email,
                                    @RequestParam("otp") String otp,
                                    @RequestParam("password") String newPassword,
                                    Model model) {
        boolean success = forgotPasswordService.verifyOtpAndResetPassword(email, otp, newPassword);
        if (success) {
            model.addAttribute("message", "Your password has been reset successfully. You can login now.");
            return "auth/login";
        } else {
            model.addAttribute("email", email);
            model.addAttribute("error", "Invalid OTP or expired.");
            return "auth/forgot-password-verify";
        }
    }

}
