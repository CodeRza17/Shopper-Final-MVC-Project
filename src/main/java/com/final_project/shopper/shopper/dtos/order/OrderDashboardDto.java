package com.final_project.shopper.shopper.dtos.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDashboardDto {
    private Long id;
    private String address;
    private String city;
    private String postcode;
    private String phoneNumber;
    private Date orderDate;
}
