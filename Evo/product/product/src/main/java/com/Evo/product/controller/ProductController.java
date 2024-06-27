// src/main/java/com/Evo/product/controller/ProductController.java
package com.Evo.product.controller;

import com.Evo.product.model.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * ProductController 컨트롤러는 /products 엔드포인트에 대한 HTTP GET 요청을 처리.
 * 이 컨트롤러는 제품에 대한 정보를 제공.
 */
@RestController
public class ProductController {
    private static final Logger logger = LogManager.getLogger(ProductController.class);
    /**
     * 제품의 세부 정보를 반환.
     */
    @GetMapping("/products")
    public Product getProductDetails() {
        Product product = new Product("람보르기니", 34568);
        logger.info("상품에 대한 요청: {}", product.getName());
        return product;
    }
}
