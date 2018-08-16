package com.cs.trader.controllers;

import com.cs.trader.domain.Company;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class CompanyControllerTest {
    @LocalServerPort
    private int serverPort;

    @Before
    public void init() {
        RestAssured.port = serverPort;
    }

    @Test
    public void findAllCompaniesRequest() {
        Response response =
            given()
                .auth().basic("john", "smith")
                .accept(MediaType.APPLICATION_JSON_VALUE).
            when()
                .get("/companies").
            then()
                .statusCode(HttpStatus.SC_OK).
            and()
                .extract().response();

        Company[] usersResponse = response.as(Company[].class);
        assertEquals("Does not return all companies", 4, usersResponse.length);
    }

    @Test
    public void findCompanyByIDSuccess(){
        given()
            .auth().basic("john", "smith")
            .accept(MediaType.APPLICATION_JSON_VALUE).
        when()
            .get("/companies/1").
        then()
            .statusCode(HttpStatus.SC_OK)
            .body("companyName", equalTo("Company 1"));
    }

    @Test
    public void findCompanyByIDFailure(){
        given()
            .auth().basic("john", "smith")
            .accept(MediaType.APPLICATION_JSON_VALUE).
        when()
            .get("/companies/100").
        then()
            .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void findCompanyStartsWithSuccess(){
        Response response =
            given()
                .auth().basic("john", "smith")
                .accept(MediaType.APPLICATION_JSON_VALUE).
            when()
                .get("/companies?startsWith=COMP1").
            then()
                .statusCode(HttpStatus.SC_OK).
            and()
                .extract().response();

        Company[] usersResponse = response.as(Company[].class);
        assertEquals("Does not return all companies", 1, usersResponse.length);

        response =
            given()
                .auth().basic("john", "smith")
                .accept(MediaType.APPLICATION_JSON_VALUE).
            when()
                .get("/companies?startsWith=COMP").
                then()
                .statusCode(HttpStatus.SC_OK).
            and()
                .extract().response();

        usersResponse = response.as(Company[].class);
        assertEquals("Does not return all companies", 3, usersResponse.length);

        response =
            given()
                .auth().basic("john", "smith")
                .accept(MediaType.APPLICATION_JSON_VALUE).
            when()
                .get("/companies?startsWith=COMP4").
                then()
                .statusCode(HttpStatus.SC_OK).
            and()
                .extract().response();

        usersResponse = response.as(Company[].class);
        assertEquals("Return some companies when it should not", 0, usersResponse.length);
    }

    @Test
    public void addCompanyWithValidSector(){
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("companyName", "Company 4");
        jsonAsMap.put("ticker", "COMP4");
        jsonAsMap.put("sectorID", 2);

        ResponseBody body =
            given()
                .auth().basic("john", "smith")
                .contentType("application/json")
                .body(jsonAsMap)
                .accept(MediaType.APPLICATION_JSON_VALUE).
            when()
                .post("/companies").
            then()
                .statusCode(HttpStatus.SC_OK).
            and()
                .extract().response().body();

        assertTrue("Expecting ID of new entry.", body.asString().equals("4"));
    }

    @Test
    public void addCompanyWithInvalidSector(){
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("companyName", "Company 4");
        jsonAsMap.put("ticker", "COMP4");
        jsonAsMap.put("sectorID", 5);

        given()
                .auth().basic("john", "smith")
                .contentType("application/json")
                .body(jsonAsMap)
                .accept(MediaType.APPLICATION_JSON_VALUE).
                when()
                .post("/companies").
                then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void addCompanyFailsWithDuplicateTicker(){
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("companyName", "Company 4");
        jsonAsMap.put("ticker", "COMP1");
        jsonAsMap.put("sectorID", 1);

        given()
            .auth().basic("john", "smith")
            .contentType("application/json")
            .body(jsonAsMap)
            .accept(MediaType.APPLICATION_JSON_VALUE).
        when()
            .post("/companies").
        then()
            .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void deleteCompanyIfNoOrdersPresent(){
        given()
            .auth().basic("john", "smith")
            .accept(MediaType.APPLICATION_JSON_VALUE).
        when()
            .delete("/companies/2").
        then()
            .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void deleteCompanyFailIfOrdersPresent(){
        given()
            .auth().basic("john", "smith")
            .accept(MediaType.APPLICATION_JSON_VALUE).
        when()
            .delete("/companies/1").
        then()
            .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void deleteCompanyFailIfInvalidID(){
        given()
            .auth().basic("john", "smith")
            .accept(MediaType.APPLICATION_JSON_VALUE).
        when()
            .delete("/companies/100").
        then()
            .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void updateCompanyFailIfInvalidID(){
        given()
            .auth().basic("john", "smith")
            .accept(MediaType.APPLICATION_JSON_VALUE).
        when()
            .put("/companies/100").
        then()
            .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void updateCompanyFailWithInvalidSector(){
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("companyName", "Company 4");
        jsonAsMap.put("ticker", "COMP4");
        jsonAsMap.put("sectorID", 20);

        given()
            .auth().basic("john", "smith")
            .contentType("application/json")
            .body(jsonAsMap)
            .accept(MediaType.APPLICATION_JSON_VALUE).
        when()
            .put("/companies/3").
        then()
            .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void updateCompanySuccess(){
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("companyName", "NewCompany");
        jsonAsMap.put("ticker", "NCOMP");
        jsonAsMap.put("sectorID", 2);

        given()
            .auth().basic("john", "smith")
            .contentType("application/json")
            .body(jsonAsMap)
            .accept(MediaType.APPLICATION_JSON_VALUE).
        when()
            .put("/companies/3").
        then()
            .statusCode(HttpStatus.SC_OK)
            .body("companyName", equalTo("NewCompany"))
            .body("ticker", equalTo("NCOMP"))
            .body("sectorID", equalTo(2));
    }
}