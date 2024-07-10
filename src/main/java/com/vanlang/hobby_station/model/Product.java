package com.vanlang.hobby_station.model;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double price;
    private String description;
    private Boolean isDeleted = false; // dữ liệu mặc định của soft delete là false

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
