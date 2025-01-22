package com.enigmacamp.enigshop.entity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@Entity
@Table(name = "products")
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, columnDefinition = "BIGINT CHECK (price >=0)")
    private Long price;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false, columnDefinition = "INT CHECK (stock >=0)")
    private Integer stock;

}
