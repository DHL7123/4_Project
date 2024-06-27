// src/main/java/com/Evo/mainpage/controller/MainpageController.java
package com.Evo.mainpage.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.Evo.mainpage.model.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * MainpageController 컨트롤러는 /showProduct에 대한 GET 요청을 처리.
 */

@Controller
public class MainpageController {

    private static final Logger logger = LogManager.getLogger(MainpageController.class);
    private final RestTemplate restTemplate;

    /**
     * RestTemplate 객체를 주입받음.
     * RestTemplate은 product와 통신을 하기 위한 Spring의 유틸리티.
     */

    @Autowired
    public MainpageController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * /showProduct 대한 GET 요청을 처리.
     * Product로 GET 요청을 보내고, 응답을 받아서 제품 정보를 반환.
     */

    @GetMapping("/showProduct")
    public String showProduct(Model model) {
        // Product URL
        String productUrl = "http://localhost:8092/products";
        try {
            // RestTemplate을 사용하여 Product에 GET 요청을 보내고, 응답을 Product 객체로 전달받음.
            Product product = restTemplate.getForObject(productUrl, Product.class);
            model.addAttribute("product", product);
            logger.info("상품 정보를 가져왔습니다: {}", product);
            return "product";
        } catch (Exception e) {
            // 예외 처리. 위 url 에서 로컬호스트 포트를 잘못 설정하면 예외가 발생함.
            model.addAttribute("errorMessage", "접근 불가: " + e.getMessage());
            logger.error("상품 정보를 가져오는 중 오류 발생", e);
            return "error";
        }
    }
    @PostMapping("/purchase")
    public String purchase(Model model) {
        // Product 정보를 가져오는 URL
        String productUrl = "http://localhost:8092/products";
        // 주문 요청을 보낼 URL
        String orderUrl = "http://localhost:8093/order";
        try {
            // Product 서비스에서 Product 객체를 가져옴
            Product product = restTemplate.getForObject(productUrl, Product.class);
            // 가져온 Product 객체를 사용하여 주문 요청을 보냄
            String response = restTemplate.postForObject(orderUrl, product, String.class);
            model.addAttribute("message", response);
            logger.info("주문이 처리되었습니다: {}", response);
            return "confirmation";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "결제 실패: " + e.getMessage());
            logger.error("주문 처리 중 오류 발생 :", e);
            return "error";
        }
    }
}
