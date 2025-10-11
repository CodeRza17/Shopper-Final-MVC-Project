package com.final_project.shopper.shopper.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double rewardPoints;
    private String productCode;
    private Integer countStock;
    private Double price;
    private String photoUrl;
    private Double discountPrice;
    private Long salesCount;


    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sizes> sizeQuantities;


    private String color;

    @Column(length = 10000)
    private String description;

    @ManyToOne
    private Brand brand;

    @ManyToOne
    private AudienceCategory audienceCategory;

    @ManyToOne
    private ProductCategory productCategory;

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<OrderProduct> orderProducts;

}
