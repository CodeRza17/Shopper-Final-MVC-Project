package com.final_project.shopper.shopper.config;

import com.final_project.shopper.shopper.dtos.sizes.SizesDto;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Configure {
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
    @Bean
    public SizesDto sizesDto() {
        return new SizesDto();
    }
}
