// src/main/java/com/Evo/mainpage/controller/MainpageController.java
package com.Evo.mainpage.controller;

import com.Evo.mainpage.model.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * MainpageController 컨트롤러는 /showProduct 엔드포인트에 대한 HTTP GET 요청을 처리.
 */

@RestController
public class MainpageController {

    private final RestTemplate restTemplate;

    /**
     * RestTemplate 객체를 주입받음.
     * RestTemplate은 product와 HTTP 통신을 하기 위한 Spring의 유틸리티 클래스.
     */

    @Autowired
    public MainpageController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * /showProduct 엔드포인트에 대한 GET 요청을 처리.
     * Product로 HTTP GET 요청을 보내고, 응답을 받아서 제품 정보를 반환.
     */

    @GetMapping("/showProduct")
    public String showProduct() {
        // Product URL
        String productUrl = "http://localhost:8091/products";
        try {
            // RestTemplate을 사용하여 Product에 GET 요청을 보내고, 응답을 Product 객체로 전달받음.
            Product product = restTemplate.getForObject(productUrl, Product.class);
            return "Product: " + product.getName();
        } catch (Exception e) {
            // 예외가 발생하면 에러 메시지를 반환합니다. 위 url 에서 로컬호스트 포트를 잘못 설정하면 예외가 발생함.
            return "접근 불가 " + e.getMessage();
        }
    }
}
