package com.final_project.shopper.shopper.sevices;

import com.final_project.shopper.shopper.dtos.brand.BrandCreateDto;
import com.final_project.shopper.shopper.dtos.brand.BrandDashboardDto;
import com.final_project.shopper.shopper.dtos.brand.BrandDeleteDto;
import com.final_project.shopper.shopper.dtos.brand.BrandUpdateDto;
import com.final_project.shopper.shopper.dtos.contactInfo.ContactInfoDto;

public interface BrandService {
    boolean create(BrandCreateDto brandCreateDto);

    BrandDashboardDto getBrand();


    boolean UpdateBrand(BrandUpdateDto brandUpdateDto, ContactInfoDto contactInfoDto);

    BrandUpdateDto getUpdatedBrand();

    boolean deleteBrand();

    BrandDeleteDto getDeletedBrand();
}
