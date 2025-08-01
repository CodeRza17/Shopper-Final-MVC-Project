package com.final_project.shopper.shopper.repositories;


import com.final_project.shopper.shopper.models.ContactInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactInfoRepository extends JpaRepository<ContactInfo, Long> {

    ContactInfo findByBrandId(Long id);
}
