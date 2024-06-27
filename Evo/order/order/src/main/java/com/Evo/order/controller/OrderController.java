package com.Evo.order.controller;

import com.Evo.order.model.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    private static final Logger logger = LogManager.getLogger(OrderController.class);
    @PostMapping("/order")
    public String createOrder(@RequestBody Product product) {
        // 여기서 주문 처리를 수행합니다.
        logger.info("상품에 대한 주문이 접수: {}", product.getName());
        return "결제가 완료되었습니다";
    }
}
