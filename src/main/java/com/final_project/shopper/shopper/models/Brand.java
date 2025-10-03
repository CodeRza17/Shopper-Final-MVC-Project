package com.final_project.shopper.shopper.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "brands")
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String logoUrl;
    private Long salesCount;

    private String bradOwnerCode;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contact_info_id")
    private ContactInfo contactInfo;

    @OneToMany(mappedBy = "brand")
    private List<Product> products = new ArrayList<>();

    @OneToMany(mappedBy = "brand")
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL)
    private List<Ad> ads;

    @OneToMany(mappedBy = "brand")
    private List<Contact> contacts = new ArrayList<>();
}
