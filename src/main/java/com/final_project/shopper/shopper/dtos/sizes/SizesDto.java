package com.final_project.shopper.shopper.dtos.sizes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SizesDto {
    private Long id;
    private String size;
    private Integer quantity;
}
