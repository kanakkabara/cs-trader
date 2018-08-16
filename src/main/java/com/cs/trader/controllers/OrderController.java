package com.cs.trader.controllers;

import com.cs.trader.domain.Order;
import com.cs.trader.domain.OrderSide;
import com.cs.trader.domain.OrderStatus;
import com.cs.trader.domain.OrderType;
import com.cs.trader.services.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@Transactional
public class OrderController {
    @Autowired
    OrderService orderService;

    @PostMapping(value="/orders", produces= {MediaType.APPLICATION_JSON_VALUE})
    public ObjectNode addOrder(Principal principal, @RequestBody Order order) {
        String username = principal.getName();
        long orderId = orderService.placeNewOrder(order, username);

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode response = mapper.createObjectNode();
        response.put("orderId", orderId);
        // TODO: *fel* get traderId from principal
        return response;
    }

    @DeleteMapping(value="/orders/{id}", produces= {MediaType.APPLICATION_JSON_VALUE})
    public void deleteOrder(@PathVariable(value="id") long orderId, Principal principal) {
        String username = principal.getName();
        orderService.cancelOrder(orderId, username);
        return;
    }

    @GetMapping(value="/orders", produces= {MediaType.APPLICATION_JSON_VALUE})
    public Map<Object, List<Order>> findTraders(@RequestParam(value = "groupby", required=false) String groupingField,
                                                @RequestParam(value = "sortby", required=false) String sortingField,
                                                @RequestParam(value = "order", required=false) String order,
                                                @RequestParam(value = "symbol", required=false) String symbolFilter,
                                                @RequestParam(value = "side", required=false) OrderSide sideFilter,
                                                @RequestParam(value = "type", required=false) OrderType typeFilter,
                                                @RequestParam(value = "status", required=false) OrderStatus statusFilter){
        Map<Object, List<Order>> result = orderService.retrieveOrderByCustomConditions(groupingField,
                sortingField, order, symbolFilter, sideFilter, typeFilter, statusFilter);
        return result;
    }

    @PutMapping(value="/orders/{id}", produces= {MediaType.APPLICATION_JSON_VALUE})
    public void updateExistingOrder(Principal principal, @PathVariable(value="id") long orderId,
                                    @RequestBody Order updatedOrder) {
        String username = principal.getName();
        orderService.updateExistingOrder(orderId, updatedOrder, username);
        return;
    }
}
