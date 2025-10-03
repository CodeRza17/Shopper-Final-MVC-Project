package com.final_project.shopper.shopper.services;

import com.final_project.shopper.shopper.dtos.brand.BrandCreateDto;
import com.final_project.shopper.shopper.dtos.brand.BrandDashboardDto;
import com.final_project.shopper.shopper.dtos.brand.BrandDeleteDto;
import com.final_project.shopper.shopper.dtos.brand.BrandUpdateDto;
import com.final_project.shopper.shopper.dtos.contactInfo.ContactInfoDto;
import com.final_project.shopper.shopper.models.Brand;

import java.util.List;

public interface BrandService {
    boolean create(BrandCreateDto brandCreateDto);

    BrandDashboardDto getBrand();


    boolean UpdateBrand(BrandUpdateDto brandUpdateDto, ContactInfoDto contactInfoDto);

    BrandUpdateDto getUpdatedBrand();

    boolean deleteBrand();

    BrandDeleteDto getDeletedBrand();

    List<Brand> findAll();

    Brand findById(Long id);
}
