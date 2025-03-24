package api.tests;

import io.restassured.RestAssured; // ✅ Digunakan dalam kode
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class GetUserTest {

    @Test
    public void testGetUserById() {
        RestAssured.baseURI = "https://dummyapi.io/data/v1"; // Menggunakan RestAssured

        Response response = given()
                .header("app-id", "63a804408eb0cb069b57e43a")
                .when()
                .get("/user/67da69c034b1cb0949a56051")
                .then()
                .statusCode(200)
                .extract().response();

        // Validasi respons
        assertNotNull(response);
        assertEquals(200, response.statusCode());
        assertNotNull(response.jsonPath().getString("id"));

        //System.out.println("User Updated Response: " + response.prettyPrint());
    }
}
