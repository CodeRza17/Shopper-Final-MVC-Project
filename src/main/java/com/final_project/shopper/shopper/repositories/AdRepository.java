package com.final_project.shopper.shopper.repositories;


import com.final_project.shopper.shopper.dtos.ad.AdDashboardDto;
import com.final_project.shopper.shopper.models.Ad;
import com.final_project.shopper.shopper.models.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdRepository extends JpaRepository<Ad, Long> {
    List<Ad> findAllByBrand(Brand brand);

}
