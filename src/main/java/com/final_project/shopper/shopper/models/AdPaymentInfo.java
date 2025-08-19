package com.final_project.shopper.shopper.models;

import com.final_project.shopper.shopper.enums.TimeType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Entity
    @Table(name = "adPaymentInfos")
    public class AdPaymentInfo {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private Integer timeCount;
        private TimeType timeType;
        private Double amount;
    }
