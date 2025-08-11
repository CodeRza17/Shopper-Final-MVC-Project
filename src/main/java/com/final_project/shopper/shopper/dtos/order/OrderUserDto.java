package com.final_project.shopper.shopper.dtos.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderUserDto {
    private String address;
    private String city;
    private String postcode;
    private String phoneNumber;
    private String comment;
}