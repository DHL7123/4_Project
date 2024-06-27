package com.Evo.order.model;

import lombok.Setter;
import lombok.Getter;

@Setter
@Getter
public class Product {
    private String name;
    private double price;

    public Product() {}

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

}
