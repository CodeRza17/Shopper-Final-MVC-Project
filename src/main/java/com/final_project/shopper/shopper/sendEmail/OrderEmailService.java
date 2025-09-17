package com.final_project.shopper.shopper.sendEmail;

import com.final_project.shopper.shopper.models.Order;
import com.final_project.shopper.shopper.models.OrderProduct;
import com.final_project.shopper.shopper.repositories.OrderProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderEmailService {


    @Autowired
    private JavaMailSender mailSender;
    private final OrderProductRepository orderProductRepository;

    public OrderEmailService(OrderProductRepository orderProductRepository) {
        this.orderProductRepository = orderProductRepository;
    }

    public void sendOrderConfirmationEmail(String toEmail, Order order, Double totalPrice) {
        StringBuilder emailContent = new StringBuilder();
        emailContent.append("Dear customer,\n\n");
        emailContent.append("Thank you for your order! Here are the details:\n\n");

        List<OrderProduct> orderProducts = orderProductRepository.findAllByOrderId(order.getId());

        for (OrderProduct item : orderProducts) {
            emailContent.append("- " + item.getProduct().getName() + " x " + item.getSize() + item.getQuantity() +
                    " = " + item.getQuantity()*item.getProduct().getPrice()*item.getProduct().getDiscountPrice() + " USD\n");
        }

        emailContent.append("\nTotal Amount: " + totalPrice + " USD\n");
        emailContent.append("\nWe appreciate your business!");

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("shopper4653135@gmail.com");
        message.setTo(toEmail);
        message.setSubject("Order Confirmation");
        message.setText(emailContent.toString());

        mailSender.send(message);
    }
}

