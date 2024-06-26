// src/main/java/com/Evo/mainpage/model/Product.java
package com.Evo.mainpage.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
    private double price;
    private String name;

    // 생성자
    public Product() {}

    public Product(double price, String name) {
        this.price = price;
        this.name = name;
    }

}
