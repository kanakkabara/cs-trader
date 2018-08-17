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
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UserControllerTest {
    @LocalServerPort
    private int serverPort;

    @Autowired
    UserController userController;

    @Before
    public void init() {
        RestAssured.port = serverPort;
    }

    @Test
    public void addNewUserFailsForDuplicateUser(){
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("username", "admin");
        jsonAsMap.put("password", "admin");

        given()
            .auth().basic("admin", "admin")
            .contentType("application/json")
            .body(jsonAsMap).
        when()
            .post("/user").
        then()
            .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void addNewUserSuccess(){
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("username", "admin2");
        jsonAsMap.put("password", "admin2");

        String[] auths = {"ADMIN"};
        jsonAsMap.put("authorities", auths);

        given()
            .auth().basic("admin", "admin")
            .contentType("application/json")
            .body(jsonAsMap).
        when()
            .post("/user").
        then()
            .statusCode(HttpStatus.SC_OK);
    }

}
