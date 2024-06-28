// src/main/java/com/Evo/mainpage/controller/MainpageController.java
package com.Evo.mainpage.controller;

import com.Evo.mainpage.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.Evo.mainpage.model.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.HashMap;
import java.util.Map;
/**
 * MainpageController 컨트롤러는 /showProduct에 대한 GET 요청을 처리.
 */

@Controller
public class MainpageController {

    private static final Logger logger = LogManager.getLogger(MainpageController.class);
    private final RestTemplate restTemplate;

    private final Map<String, User> users = new HashMap<>();
    /**
     * RestTemplate 객체를 주입받음.
     * RestTemplate은 product와 통신을 하기 위한 Spring의 유틸리티.
     */

    @Autowired
    public MainpageController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        // 하드코딩된 유저 추가
        users.put("1", new User("1", "페루치오 "));
        users.put("2", new User("2", "엔초"));
        users.put("3", new User("3", "퍼디난드"));
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
            Map<String, Integer> products = restTemplate.getForObject(productUrl, Map.class);
            model.addAttribute("products", products);
            model.addAttribute("users", users);
            model.addAttribute("successMessage", "상품 정보를 가져왔습니다!");
            logger.info("상품 정보를 가져왔습니다: {}", products);
            return "product";
        } catch (Exception e) {
            // 예외 처리. 위 url 에서 로컬호스트 포트를 잘못 설정하면 예외가 발생함.
            model.addAttribute("errorMessage", "접근 불가: " + e.getMessage());
            logger.error("상품 정보를 가져오는 중 오류 발생", e);
            return "error";
        }
    }
    @PostMapping("/purchase")
    public String purchase(String userId,String productName, Model model) {
        // Product 정보를 가져오는 URL
        String productUrl = "http://localhost:8092/products";
        // 주문 요청을 보낼 URL
        String orderUrl = "http://localhost:8093/order";
        try {
            // product 객체 가져오기
            Map<String, Integer> products = restTemplate.getForObject(productUrl, Map.class);

            Integer productPrice = products.get(productName);
            Product product = new Product(productName, productPrice);

            // 유저 정보 가져오기
            User user = users.get(userId);

            // 가져온 Product 객체를 사용하여 주문 요청을 보냄
            String response = restTemplate.postForObject(orderUrl, product, String.class);

            String logMessage = user.getUserName() + " 이 " + product.getName() + " 구매 하였습니다" + product.getPrice()+" 원에";
            System.out.println(logMessage);

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
