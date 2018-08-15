package com.cs.trader.controllers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;

import com.cs.trader.domain.ActivitySummary;
import com.cs.trader.domain.Order;
import com.cs.trader.domain.Trader;
import com.cs.trader.domain.TraderRank;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
public class TraderControllerTest {
	
	@LocalServerPort
	private int serverPort;
	
	@Autowired
	TraderController controller;
	
	@Before
	public void init() {
		RestAssured.port = serverPort;
	}
	
	@Test
	
	public void findTradersRequest() {
		Response response = 
				given()
					.auth().basic("john", "smith")
					.accept(MediaType.APPLICATION_JSON_VALUE).
				when()
					.get("/traders").
				then()
					.statusCode(HttpStatus.SC_OK).
				and()
					.extract().response();
		List<Trader> users = new ArrayList<Trader>();

		Trader[] usersResponse = response.as(Trader[].class);
		assertEquals("First name of first row retrieved should be Ernest",usersResponse[0].getFirstName(), "Ernest");
	}
	
	@Test
	public void findTraderByIdRequest() {
		given()
			.auth().basic("john", "smith")
			.accept(MediaType.APPLICATION_JSON_VALUE).
		when()
			.get("/traders/1").
		then()
			.statusCode(HttpStatus.SC_OK)
			.body("firstName", equalTo("Ernest"));
		
		/*ResponseBody body = 
			given()
			.auth().basic("john", "smith")
			.accept(MediaType.APPLICATION_JSON_VALUE).
		when()
			.get("/traders/2").
		then()
			.statusCode(HttpStatus.SC_OK)
		.and()
			.extract().response().body();
		System.out.println(body.asString());*/
	}
	
	@Test
	public void findTraderByInvalidIdRequest() {
		given()
			.auth().basic("john", "smith")
			.accept(MediaType.APPLICATION_JSON_VALUE).
		when()
			.get("/traders/911").
		then()
			.statusCode(HttpStatus.SC_NOT_FOUND);
		
	}
	
	@Test
	public void deleteTraderRequest() {
			ResponseBody body =
				given()
					.auth().basic("john", "smith")
					.accept(MediaType.APPLICATION_JSON_VALUE).
				when()
					.delete("/traders/2").
				then()
					.statusCode(HttpStatus.SC_OK)
					.extract().response().body();
			assertTrue("Expecting 1 row affected.", body.asString().equals("1"));
	}
	
	@Test
	public void deleteWorkingTraderRequest() {
		given()
			.auth().basic("john", "smith")
			.accept(MediaType.APPLICATION_JSON_VALUE).
		when()
			.delete("/traders/1").
		then()
			.statusCode(HttpStatus.SC_BAD_REQUEST);
	}
	
	@Test
	public void addTraderRequest() {
		
		Map<String, Object>  jsonAsMap = new HashMap<>();
		jsonAsMap.put("firstName", "Adam");
		jsonAsMap.put("lastName", "Apple");
		jsonAsMap.put("email", "adam@gmail.com");
		jsonAsMap.put("phone", "6592345678");
		jsonAsMap.put("address", "Mayhem");
		jsonAsMap.put("username", "adam");
		jsonAsMap.put("password", "appuru");
		
		ResponseBody body = 
			given()
				.auth().basic("john", "smith")
				.contentType("application/json")
				.body(jsonAsMap)
				.accept(MediaType.APPLICATION_JSON_VALUE).
			when()
				.post("/traders").
			then()
				.statusCode(HttpStatus.SC_OK).
			and()
				.extract().response().body();
		assertTrue("Expecting 1 row affected.", body.asString().equals("1"));
	}
	
	@Test
	public void addTraderWithInvalidFieldRequest() {
		
		Map<String, Object>  jsonAsMap = new HashMap<>();
		jsonAsMap.put("firstName", "Adam");
		jsonAsMap.put("lastName", "Apple");
		jsonAsMap.put("email", null);
		jsonAsMap.put("phone", "6592345678");
		jsonAsMap.put("address", "Mayhem");
		jsonAsMap.put("username", "adam");
		jsonAsMap.put("password", "appuru");
		
		given()
			.auth().basic("john", "smith")
			.contentType("application/json")
			.body(jsonAsMap)
			.accept(MediaType.APPLICATION_JSON_VALUE).
		when()
			.post("/traders").
		then()
			.statusCode(HttpStatus.SC_BAD_REQUEST);
	}
	
	@Test
	public void findOrdersByTraderIdRequest() {
		Response response = 
			given()
				//.auth().basic("john", "smith")
				.auth().basic("john", "smith")
				.contentType("application/json")
				.accept(MediaType.APPLICATION_JSON_VALUE).
			when()
				.get("/traders/1/orders").
			then()
				.statusCode(HttpStatus.SC_OK).
			and()
				.extract().response();
		Order[] orders = response.as(Order[].class);
		assertTrue("Number of orders retrieved is incorrect.",orders.length == 8);
	}
	
	@Test
	public void findOrdersByInvalidTraderIdRequest() {
		given()
			.auth().basic("john", "smith")
			.contentType("application/json")
			.accept(MediaType.APPLICATION_JSON_VALUE).
		when()
			.get("/traders/999/orders").
		then()
		.statusCode(HttpStatus.SC_BAD_REQUEST);
	}
	
	@Test
	public void findActivitySummaryByTraderIdRequest() {
		Response response = 
				given()
					.auth().basic("john", "smith")
					.contentType("application/json")
					.accept(MediaType.APPLICATION_JSON_VALUE).
				when()
					.get("/traders/1/activitysummary").
				then()
					.statusCode(HttpStatus.SC_OK).
				and()
					.extract().response();
		ActivitySummary summary = response.as(ActivitySummary.class);
		Map<String, Long> orders = summary.getOrders();
		assertTrue("Data in activity summary is incorrect.",orders.get("OPEN") == 6);
	}
	
	@Test
	public void findActivitySummaryByInvalidTraderIdRequest() {
		given()
			.auth().basic("john", "smith")
			.contentType("application/json")
			.accept(MediaType.APPLICATION_JSON_VALUE).
		when()
			.get("/traders/999/activitysummary").
		then()
			.statusCode(HttpStatus.SC_BAD_REQUEST);
	}
	
	@Test
	public void findTopFiveTradersByNumTrades() {
		Response response = 
				given()
					.auth().basic("john", "smith")
					.contentType("application/json")
					.accept(MediaType.APPLICATION_JSON_VALUE).
				when()
					.get("/traders/topbynumtrades").
				then()
					.statusCode(HttpStatus.SC_OK).
				and()
					.extract().response();
		TraderRank[] traders = response.as(TraderRank[].class);
		Trader trader = traders[0].getTrader();
		assertTrue("Top 5 traders by number of trades is incorrect.",trader.getFirstName().equals("Ernest"));
	}
	
	@Test
	public void findTopFiveTradersByVolume() {
		Response response = 
				given()
					.auth().basic("john", "smith")
					.contentType("application/json")
					.accept(MediaType.APPLICATION_JSON_VALUE).
				when()
					.get("/traders/topbyvolume").
				then()
					.statusCode(HttpStatus.SC_OK).
				and()
					.extract().response();
		TraderRank[] traders = response.as(TraderRank[].class);
		Trader trader = traders[0].getTrader();
		assertTrue("Top 5 traders by volume is incorrect.",trader.getFirstName().equals("Ernest"));
	}
}
