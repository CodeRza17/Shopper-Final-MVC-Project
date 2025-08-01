package com.final_project.shopper.shopper.dtos.brand;


import com.final_project.shopper.shopper.dtos.contactInfo.ContactInfoDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandCreateDto {
    private String name;
    private String logoUrl;
    private String bradOwnerCode;
    private ContactInfoDto contactInfo;
}
