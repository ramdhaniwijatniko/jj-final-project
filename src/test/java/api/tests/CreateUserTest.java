package api.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class CreateUserTest extends BaseTest {

    @Test
    public void testCreateUser() {
        // Set base URL untuk RestAssured
        RestAssured.baseURI = "https://dummyapi.io/data/v1";

        // Membuat JSON payload untuk request body
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("firstName", "reni");
        requestBody.addProperty("lastName", "astuti");
        requestBody.addProperty("email", "renni.das" + System.currentTimeMillis() + "@example.com");

        // Debug: Cetak request body
        System.out.println("[DEBUG] Request Body: " + requestBody);

        // Kirim request POST untuk membuat user
        Response response = given()
            .header("app-id", "63a804408eb0cb069b57e43a") // Pastikan App-ID valid
            .contentType("application/json")
            .body(requestBody.toString())
        .when()
            .post("/user/create") //Ubah endpoint menjadi "/user"
        .then()
            .statusCode(200) // Gunakan 200 Created
            .body("firstName", equalTo("reni"))
            .body("lastName", equalTo("astuti"))
            .extract()
            .response();

        // Debug: Cetak respons JSON
        

        System.out.println("[DEBUG] Response Status Code: " + response.getStatusCode());
        System.out.println("[DEBUG] Response Body: " + response.getBody().asPrettyString());

        System.out.println("[DEBUG] Created User Response: " + response.getBody().asPrettyString());
    }
}
