package com.final_project.shopper.shopper.repositories;


import com.final_project.shopper.shopper.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);


    List<User> findAllByBrandId(Long brandId);
}
