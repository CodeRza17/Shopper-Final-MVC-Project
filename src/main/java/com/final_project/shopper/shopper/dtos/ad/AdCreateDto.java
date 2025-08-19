package com.final_project.shopper.shopper.dtos.ad;

import com.final_project.shopper.shopper.enums.TimeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdCreateDto {
    private String photoUrl;
    private Long adPaymentInfoId;
}
