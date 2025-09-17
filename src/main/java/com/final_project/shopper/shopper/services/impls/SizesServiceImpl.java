package com.final_project.shopper.shopper.services.impls;

import com.final_project.shopper.shopper.dtos.sizes.SizeProductDetailDto;
import com.final_project.shopper.shopper.models.Sizes;
import com.final_project.shopper.shopper.repositories.SizesRepository;
import com.final_project.shopper.shopper.services.SizesService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SizesServiceImpl implements SizesService {
    private final SizesRepository sizesRepository;
    private final ModelMapper modelMapper;

    public SizesServiceImpl(SizesRepository sizesRepository, ModelMapper modelMapper) {
        this.sizesRepository = sizesRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public void deleteSize(Long id) {
        sizesRepository.deleteAllByProductId(id);
    }

    @Override
    public List<SizeProductDetailDto> findByProductId(Long id) {
        List<Sizes> findSizes = sizesRepository.findByProductId(id);
        List<SizeProductDetailDto> sizeProductDetailDtosList = findSizes.stream().map(sizes -> modelMapper.map(sizes, SizeProductDetailDto.class)).toList();

        return sizeProductDetailDtosList;
    }
}
