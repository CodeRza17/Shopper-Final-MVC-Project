package com.final_project.shopper.shopper.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "contactInfos")
public class ContactInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String addressMap;
    private String address;
    private String email;
    private String phoneNumber;

    @OneToOne(mappedBy = "contactInfo")
    private Brand brand;
}
