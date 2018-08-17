package com.cs.trader.controllers;

import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.DEFINED_PORT)
public class TransactionControllerTest {
    @LocalServerPort
    private int serverPort;

    @Autowired
    TransactionController transactionController;

    @Before
    public void init() {
        RestAssured.port = serverPort;
    }
}
