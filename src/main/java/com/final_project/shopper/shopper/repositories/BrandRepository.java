package com.final_project.shopper.shopper.repositories;


import com.final_project.shopper.shopper.models.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BrandRepository extends JpaRepository<Brand, Long> {

    Brand findByName(String company);

    List<Brand> findTop6ByOrderByIdAsc();

    Brand findByUsersId(Long id);
}
