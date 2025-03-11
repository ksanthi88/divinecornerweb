package com.santhi.divinecornerweb.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name="products")
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String imageName;
    private String category;
    private double price;
}
