package com.final_project.shopper.shopper.dtos.adPaymentInfo;

import com.final_project.shopper.shopper.enums.TimeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdPaymentInfoDashboardDto {
    private Long id;
    private Integer timeCount;
    private TimeType timeType;
    private Double amount;
}
