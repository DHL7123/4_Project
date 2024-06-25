// src/main/java/com/Evo/product/model/Product.java
package com.Evo.product.model;
import lombok.Getter;
import lombok.Setter;

/**
 * Product 모델은 제품의 이름과 가격 정보를 포함하는 데이터 모델
 */

@Getter
@Setter
public class Product {
    private String name;
    private double price;

    /**
     * 생성자로 제품의 이름과 가격을 초기화.
     */
    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }
}