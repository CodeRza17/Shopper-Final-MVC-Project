package com.final_project.shopper.shopper.dtos.brand;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandDeleteDto {
    private Long id;
    private String name;
}
