package com.cs.trader.config;

import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.DEFINED_PORT)
public class WebSecurityConfigTest {
    @LocalServerPort
    private int serverPort;

    @Before
    public void init() {
            RestAssured.port = serverPort;
        }

    @Test
    public void adminCanCreateCompany() {
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("companyName", "Company 4");
        jsonAsMap.put("ticker", "COMP4");
        jsonAsMap.put("sectorID", 2);

        given()
            .auth().basic("admin", "admin")
            .contentType("application/json")
            .body(jsonAsMap).
        when()
            .post("/companies").
        then()
            .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void nonAdminRolesCannotCreateCompany() {
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("companyName", "Company 4");
        jsonAsMap.put("ticker", "COMP4");
        jsonAsMap.put("sectorID", 2);

        given()
            .auth().basic("kanak", "kanak")
            .contentType("application/json")
            .body(jsonAsMap).
        when()
            .post("/companies").
        then()
            .statusCode(HttpStatus.SC_FORBIDDEN);
    }

    @Test
    public void adminCanUpdateCompany() {
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("companyName", "Company 4");

        given()
            .auth().basic("admin", "admin")
            .contentType("application/json")
            .body(jsonAsMap).
        when()
            .put("/companies/1").
        then()
            .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void nonAdminRolesCannotUpdateCompany() {
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("companyName", "Company 4");

        given()
            .auth().basic("kanak", "kanak")
            .contentType("application/json")
            .body(jsonAsMap).
        when()
            .put("/companies/1").
        then()
            .statusCode(HttpStatus.SC_FORBIDDEN);
    }

    @Test
    public void adminCanDeleteCompany() {
        given()
            .auth().basic("admin", "admin")
            .contentType("application/json").
        when()
            .delete("/companies/3").
        then()
            .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void nonAdminRolesCannotDeleteCompany() {
        given()
            .auth().basic("kanak", "kanak")
            .contentType("application/json").
        when()
            .delete("/companies/3").
        then()
            .statusCode(HttpStatus.SC_FORBIDDEN);
    }

    @Test
    public void adminCanAddSector(){
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("sectorName", "Sector 4");
        jsonAsMap.put("sectorDesc", "Desc for Sector 4");

        given()
            .auth().basic("john", "smith")
            .contentType("application/json")
            .body(jsonAsMap)
            .accept(MediaType.APPLICATION_JSON_VALUE).
        when()
            .post("/sectors").
        then()
            .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void adminCanDeleteSector(){
        given()
            .auth().basic("john", "smith")
            .accept(MediaType.APPLICATION_JSON_VALUE).
        when()
            .delete("/sectors/3").
        then()
            .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void adminCanUpdateSector(){
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("sectorName", "NewSector");
        jsonAsMap.put("sectorDesc", "Sector desc for NewSector");

        given()
            .auth().basic("john", "smith")
            .contentType("application/json")
            .body(jsonAsMap)
            .accept(MediaType.APPLICATION_JSON_VALUE).
        when()
            .put("/sectors/1").
        then()
            .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void nonAdminCannotAddSector(){
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("sectorName", "Sector 4");
        jsonAsMap.put("sectorDesc", "Desc for Sector 4");

        given()
            .auth().basic("kanak", "kanak")
            .contentType("application/json")
            .body(jsonAsMap)
            .accept(MediaType.APPLICATION_JSON_VALUE).
        when()
            .post("/sectors").
        then()
            .statusCode(HttpStatus.SC_FORBIDDEN);
    }

    @Test
    public void nonAdminCannotDeleteSector(){
        given()
            .auth().basic("kanak", "kanak")
            .accept(MediaType.APPLICATION_JSON_VALUE).
        when()
            .delete("/sectors/3").
        then()
            .statusCode(HttpStatus.SC_FORBIDDEN);
    }

    @Test
    public void nonAdminCannotUpdateSector(){
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("sectorName", "NewSector");
        jsonAsMap.put("sectorDesc", "Sector desc for NewSector");

        given()
            .auth().basic("kanak", "kanak")
            .contentType("application/json")
            .body(jsonAsMap)
            .accept(MediaType.APPLICATION_JSON_VALUE).
        when()
            .put("/sectors/4").
        then()
            .statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
