package com.final_project.shopper.shopper.repositories;


import com.final_project.shopper.shopper.models.Sizes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SizesRepository extends JpaRepository<Sizes, Long> {
    void deleteAllByProductId(Long id);

    List<Sizes> findByProductId(Long id);
}
