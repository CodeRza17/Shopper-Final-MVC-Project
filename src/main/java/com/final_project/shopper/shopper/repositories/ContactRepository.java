package com.final_project.shopper.shopper.repositories;


import com.final_project.shopper.shopper.models.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {

    List<Contact> findAllByBrandId(Long id);
}
