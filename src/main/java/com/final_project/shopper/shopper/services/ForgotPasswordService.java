package com.final_project.shopper.shopper.services;

import com.final_project.shopper.shopper.models.User;
import com.final_project.shopper.shopper.otp.OtpService;
import com.final_project.shopper.shopper.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ForgotPasswordService {

    private final UserRepository userRepository;
    private final OtpService otpService;
    private final PasswordEncoder passwordEncoder;


    public boolean sendOtp(String email) {
        Optional<User> optionalUser = Optional.ofNullable(userRepository.findByEmail(email));
        if (optionalUser.isEmpty()) return false;

        User user = optionalUser.get();
        String otp = otpService.generateOtp();
        otpService.storePendingUser(email, user, otp);
        otpService.sendOtpEmail(email, otp);
        return true;
    }


    public boolean verifyOtpAndResetPassword(String email, String otp, String newPassword) {
        if (!otpService.verifyOtp(email, otp)) return false;

        User user = otpService.getPendingUser(email);
        if (user == null) return false;

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        otpService.removePendingUser(email);
        return true;
    }

}
