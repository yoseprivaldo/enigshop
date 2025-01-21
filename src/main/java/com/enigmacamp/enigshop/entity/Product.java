package com.enigmacamp.enigshop.entity;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
public class Product {
    private UUID id;
    private String name;
    private Double price;
    private Integer stock;

    public Product(String name, Double price, Integer stock){
        this.id = UUID.randomUUID();
        this.name = name;
        this.price= price;
        this.stock=stock;
    }

}
