package api.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class UpdateUserTest extends BaseTest {

    @Test
    public void updateUser() {
        String userId = "67da684b34b1cbbc0ca55fe1"; // Ganti dengan ID user yang valid

        // Set baseURI secara eksplisit agar import RestAssured digunakan
        RestAssured.baseURI = "https://dummyapi.io/data/v1";

        // Membuat JSON payload untuk update user
        JSONObject updateData = new JSONObject();
        updateData.put("firstName", "reni");
        updateData.put("lastName", "dwiastuti");

        // Kirim request PUT untuk memperbarui user
        Response response = given()
                .header("app-id", "63a804408eb0cb069b57e43a") // Tambahkan app-id untuk autentikasi
                .contentType("application/json")
                .body(updateData.toString())
                .when()
                .put("/user/" + userId)
                .then()
                .statusCode(200) // Pastikan response 200 OK
                .body("firstName", equalTo("reni"))
                .body("lastName", equalTo("dwiastuti"))
                .extract().response();

        // Cetak respons JSON untuk debugging
        System.out.println("User Updated Response: " + response.prettyPrint());
    }
}
