package com.final_project.shopper.shopper.dtos.contact;


import lombok.Data;


@Data
public class ContactDashboardDto {
    private Long id;
    private String fullName;
    private String email;
    private String message;
    private boolean read;
}
