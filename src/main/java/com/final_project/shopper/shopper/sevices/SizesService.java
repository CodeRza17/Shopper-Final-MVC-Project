package com.final_project.shopper.shopper.sevices;

import com.final_project.shopper.shopper.dtos.sizes.SizeProductDetailDto;

import java.util.List;

public interface SizesService {
    void deleteSize(Long id);

    List<SizeProductDetailDto> findByProductId(Long id);
}
