package com.final_project.shopper.shopper.sevices;

import com.final_project.shopper.shopper.dtos.ad.AdCreateDto;
import com.final_project.shopper.shopper.dtos.ad.AdDashboardDto;
import com.final_project.shopper.shopper.dtos.ad.AdHomeDto;

import java.util.List;

public interface AdService {
    List<AdDashboardDto> findAllByBrand(String email);

    boolean createAd(String email, AdCreateDto adCreateDto);

    boolean deleteAd(Long id);

    List<AdHomeDto> findAllActiveAds();
}
