package com.cs.trader.controllers;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

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

import com.cs.trader.domain.Quote;
import com.cs.trader.domain.Trader;

import io.restassured.RestAssured;
import io.restassured.response.Response;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class QuoteControllerTest {
	@LocalServerPort
    private int serverPort;

    @Autowired
    TraderController controller;

    @Before
    public void init() {
        RestAssured.port = serverPort;
    }

    @Test
    public void findQuotesRequest() {
    	Response response = 
	        given()
	            .auth().basic("john", "smith")
	            .contentType("application/json")
	            .accept(MediaType.APPLICATION_JSON_VALUE).
	        when()
	            .get("/quotes").
	        then()
	            .statusCode(HttpStatus.SC_OK)
	            .extract().response();
    	Quote[] quotesResponse = response.as(Quote[].class);
    	assertEquals("Number of quotes acquired should be 5", quotesResponse.length,5);
    }
    
    @Test
    public void findQuotesByTickerRequest() {
    	Response response = 
	        given()
	            .auth().basic("john", "smith")
	            .contentType("application/json")
	            .pathParam("tickersymbol", "GOOGL")
	            .accept(MediaType.APPLICATION_JSON_VALUE).
	        when()
	            .get("/quotes/{tickersymbol}").
	        then()
	            .statusCode(HttpStatus.SC_OK)
	            .extract().response();
    	Quote[] quotesResponse = response.as(Quote[].class);
    	assertEquals("Number of quotes acquired should be 4", quotesResponse.length,4);
    }
    
    @Test
    public void findQuotesByTickerAndTimestampRequest() {
    	Response response = 
	        given()
	            .auth().basic("john", "smith")
	            .contentType("application/json")
	            .pathParam("tickersymbol", "GOOGL")
	            .param("from", "2016-10-11 01:00:55.000")
	            .param("to", "2016-10-11 08:00:55.000")
	            .accept(MediaType.APPLICATION_JSON_VALUE).
	        when()
	            .get("/quotes/{tickersymbol}").
	        then()
	            .statusCode(HttpStatus.SC_OK)
	            .extract().response();
    	Quote[] quotesResponse = response.as(Quote[].class);
    	assertEquals("Number of quotes acquired should be 4", quotesResponse.length,4);
    }
    
    @Test
    public void findQuotesByTimestampRequest() {
    	Response response = 
	        given()
	            .auth().basic("john", "smith")
	            .contentType("application/json")
	            .param("from", "2016-10-11 01:00:55.000")
	            .param("to", "2016-10-11 08:00:55.000")
	            .accept(MediaType.APPLICATION_JSON_VALUE).
	        when()
	            .get("/quotes").
	        then()
	            .statusCode(HttpStatus.SC_OK)
	            .extract().response();
    	Quote[] quotesResponse = response.as(Quote[].class);
    	assertEquals("Number of quotes acquired should be 4", quotesResponse.length,4);
    }


}
