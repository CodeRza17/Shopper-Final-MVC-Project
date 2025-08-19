package com.final_project.shopper.shopper.dtos.ad;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdDashboardDto {
    private Long id;
    private String photoUrl;
    private LocalDateTime activationDate;
    private boolean active;
}
