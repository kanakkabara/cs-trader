package com.cs.trader.controllers;

import com.cs.trader.domain.Order;
import com.cs.trader.services.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    @Autowired
    OrderService orderService;

    @PostMapping(value="/orders", produces= {MediaType.APPLICATION_JSON_VALUE})
    public ObjectNode addTrader(@RequestBody Order order) {
        long orderId = orderService.placeNewOrder(order);
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode response = mapper.createObjectNode();
        response.put("orderId", orderId);
        return response;
    }

}
