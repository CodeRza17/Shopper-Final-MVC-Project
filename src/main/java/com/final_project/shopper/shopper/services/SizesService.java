package com.final_project.shopper.shopper.services;

import com.final_project.shopper.shopper.dtos.sizes.SizeProductDetailDto;

import java.util.List;

public interface SizesService {
    void deleteSize(Long id);

    List<SizeProductDetailDto> findByProductId(Long id);
}
