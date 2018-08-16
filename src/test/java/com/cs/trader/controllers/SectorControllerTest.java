package com.cs.trader.controllers;


import com.cs.trader.domain.Sector;
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
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
@Transactional
public class SectorControllerTest {
    @LocalServerPort
    private int serverPort;

    @Before
    public void init() {
        RestAssured.port = serverPort;
    }

    @Test
    public void findAllSectorsReturnsAllSectors() {
        Response response =
            given()
                .auth().basic("john", "smith")
                .accept(MediaType.APPLICATION_JSON_VALUE).
            when()
                .get("/sectors").
            then()
                .statusCode(HttpStatus.SC_OK).
            and()
                .extract().response();

        Sector[] usersResponse = response.as(Sector[].class);
        assertEquals("Does not return all sectors", 3, usersResponse.length);
    }

    @Test
    public void findAllSectorsReturnsCompanyCounts() {
        Response response =
            given()
                .auth().basic("john", "smith")
                .accept(MediaType.APPLICATION_JSON_VALUE).
            when()
                .get("/sectors").
            then()
                .extract().response();

        Sector[] usersResponse = response.as(Sector[].class);
        assertEquals("Sector 1 has count 1", 1, usersResponse[0].getCompanyCount());
        assertEquals("Sector 2 has count 2", 3, usersResponse[1].getCompanyCount());
        assertEquals("Sector 3 has count 0", 0, usersResponse[2].getCompanyCount());
    }

    @Test
    public void findSectorByIDSuccess(){
        given()
            .auth().basic("john", "smith")
            .accept(MediaType.APPLICATION_JSON_VALUE).
        when()
            .get("/sectors/1").
        then()
            .statusCode(HttpStatus.SC_OK)
            .body("sectorName", equalTo("Sector1"));
    }

    @Test
    public void findSectorByIDReturnsAllCompanyDetails(){
        Response response =
            given()
                .auth().basic("john", "smith")
                .accept(MediaType.APPLICATION_JSON_VALUE).
            when()
                .get("/sectors/2").
            then()
                .statusCode(HttpStatus.SC_OK).
            and()
                .extract().response();

        Sector usersResponse = response.as(Sector.class);
        assertEquals(usersResponse.getCompanies().size(), 3);
        assertEquals(usersResponse.getCompanies().get(0).getSectorID(), 2);
        assertEquals(usersResponse.getCompanies().get(1).getSectorID(), 2);
        assertEquals(usersResponse.getCompanies().get(2).getSectorID(), 2);
    }

    @Test
    public void findSectorByIDFailure(){
        given()
            .auth().basic("john", "smith")
            .accept(MediaType.APPLICATION_JSON_VALUE).
        when()
            .get("/sectors/100").
        then()
            .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void addSectorSuccess(){
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("sectorName", "Sector 4");
        jsonAsMap.put("sectorDesc", "Desc for Sector 4");

        ResponseBody body =
            given()
                .auth().basic("john", "smith")
                .contentType("application/json")
                .body(jsonAsMap)
                .accept(MediaType.APPLICATION_JSON_VALUE).
            when()
                .post("/sectors").
            then()
                .statusCode(HttpStatus.SC_OK).
            and()
                .extract().response().body();

        assertTrue("Expecting ID of new entry.", body.asString().equals("4"));

        given()
            .auth().basic("john", "smith")
            .accept(MediaType.APPLICATION_JSON_VALUE).
        when()
            .delete("/sectors/3");
    }

    @Test
    public void deleteSectorSuccessIfNoCompaniesPresent(){
        given()
            .auth().basic("john", "smith")
            .accept(MediaType.APPLICATION_JSON_VALUE).
        when()
            .delete("/sectors/3").
        then()
            .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void deleteSectorFailIfCompaniesPresent(){
        given()
            .auth().basic("john", "smith")
            .accept(MediaType.APPLICATION_JSON_VALUE).
        when()
            .delete("/sectors/1").
        then()
            .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void updateSectorFailIfInvalidID(){
        given()
            .auth().basic("john", "smith")
            .accept(MediaType.APPLICATION_JSON_VALUE).
        when()
            .put("/sectors/100").
        then()
            .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void updateSectorSuccess(){
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("sectorName", "NewSector");
        jsonAsMap.put("sectorDesc", "Sector desc for NewSector");

        given()
            .auth().basic("john", "smith")
            .contentType("application/json")
            .body(jsonAsMap)
            .accept(MediaType.APPLICATION_JSON_VALUE).
        when()
            .put("/sectors/4").
        then()
            .statusCode(HttpStatus.SC_OK)
            .body("sectorName", equalTo("NewSector"))
            .body("sectorDesc", equalTo("Sector desc for NewSector"));
    }

}