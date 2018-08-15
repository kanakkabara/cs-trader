package com.cs.trader.controllers;

import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class OrderControllerTest {

    @LocalServerPort
    private int serverPort;

    @Autowired
    TraderController controller;

    @Before
    public void init() {
        RestAssured.port = serverPort;
    }

    @Test
    public void canPlaceOrderWithLimitType() {

        Map<String, Object> orderRequest = new HashMap<>();
        orderRequest.put("symbol", "COMP2");
        orderRequest.put("instruction", "BUY");
        orderRequest.put("orderType", "LIMIT");
        orderRequest.put("price", 10.04);
        orderRequest.put("volume", 10);
        orderRequest.put("traderId", 1);

        given()
            .auth().basic("john", "smith")
            .contentType("application/json")
            .body(orderRequest)
            .accept(MediaType.APPLICATION_JSON_VALUE).
        when()
            .post("/orders").
        then()
            .statusCode(HttpStatus.SC_OK)
            .body("$", hasKey("orderId"));

        // TODO: *fel* check if stored
    }

    @Test
    public void canPlaceOrderWithMarketType() {

        Map<String, Object> orderRequest = new HashMap<>();
        orderRequest.put("symbol", "COMP2");
        orderRequest.put("instruction", "SELL");
        orderRequest.put("orderType", "MARKET");
        orderRequest.put("price", null);
        orderRequest.put("volume", 10);
        orderRequest.put("traderId", 1);

        given()
            .auth().basic("john", "smith")
            .contentType("application/json")
            .body(orderRequest)
            .accept(MediaType.APPLICATION_JSON_VALUE).
        when()
            .post("/orders").
        then()
            .statusCode(HttpStatus.SC_OK)
            .body("$", hasKey("orderId"));
    }

    @Test
    public void returnBadRequestCodeOnInvalidRequest() {

        Map<String, Object>  orderRequest = new HashMap<>();
        orderRequest.put("symbol", "COMP2");
        orderRequest.put("instruction", "XXXX");
        orderRequest.put("orderType", "LIMIT");
        orderRequest.put("price", 10.04);
        orderRequest.put("volume", 10);
        orderRequest.put("traderId", 1);

        given()
            .auth().basic("john", "smith")
            .contentType("application/json")
            .body(orderRequest)
            .accept(MediaType.APPLICATION_JSON_VALUE).
        when()
            .post("/orders").
        then()
            .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

}
