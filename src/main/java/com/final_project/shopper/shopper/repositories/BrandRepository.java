package com.final_project.shopper.shopper.repositories;


import com.final_project.shopper.shopper.models.Brand;
import com.final_project.shopper.shopper.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {

    Brand findByName(String company);

}
