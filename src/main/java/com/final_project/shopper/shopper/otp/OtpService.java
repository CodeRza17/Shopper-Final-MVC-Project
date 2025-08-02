package com.final_project.shopper.shopper.otp;

import com.final_project.shopper.shopper.models.User;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class OtpService {

    private final JavaMailSender mailSender;

    public OtpService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    private static class OtpEntry {
        String code;
        LocalDateTime expiryTime;

        OtpEntry(String code, LocalDateTime expiryTime) {
            this.code = code;
            this.expiryTime = expiryTime;
        }
    }

    private final Map<String, OtpEntry> otpStorage = new HashMap<>();

    public String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    public void sendOtpEmail(String toEmail, String otpCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Your OTP Code");
        message.setText("Your OTP code is: " + otpCode + "\nThis code is valid for 2 minutes.");

        mailSender.send(message);
    }

    public void createAndSendOtp(String email) {
        String otpCode = generateOtp();
        otpStorage.put(email, new OtpEntry(otpCode, LocalDateTime.now().plusMinutes(2)));
        sendOtpEmail(email, otpCode);
    }

    public boolean verifyOtp(String email, String code) {
        OtpEntry entry = otpStorage.get(email);
        if (entry == null) return false;
        if (entry.expiryTime.isBefore(LocalDateTime.now())) {
            otpStorage.remove(email);
            return false;
        }
        boolean valid = entry.code.equals(code);
        if (valid) otpStorage.remove(email);
        return valid;
    }

    public boolean canResendOtp(String email) {
        OtpEntry entry = otpStorage.get(email);
        if (entry == null) return true;
        return entry.expiryTime.isBefore(LocalDateTime.now());
    }

    private final Map<String, User> pendingUsers = new HashMap<>();


    public void storePendingUser(String email, User user, String otp) {
        otpStorage.remove(email);
        pendingUsers.remove(email);

        otpStorage.put(email, new OtpEntry(otp, LocalDateTime.now().plusMinutes(2)));
        pendingUsers.put(email, user);
    }


    public User getPendingUser(String email) {
        return pendingUsers.get(email);
    }


    public void removePendingUser(String email) {
        pendingUsers.remove(email);
    }

}
