package api.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class DeleteUserTest extends BaseTest {

    @Test
    public void deleteUser() {
        // Ganti dengan ID user yang valid dari hasil Create User
        String userId = "67dbc2893c86350d7f90d9db";

        // Set base URI RestAssured
        RestAssured.baseURI = "https://dummyapi.io/data/v1";

        // 🔍 **Langkah 1: Periksa apakah user masih ada sebelum menghapusnya**
        Response checkUser = given()
                .header("app-id", "63a804408eb0cb069b57e43a") // App ID valid
                .when()
                .get("/user/" + userId)
                .then()
                .extract().response();

        System.out.println("Check User Response: " + checkUser.getBody().asPrettyString());

        // Jika user tidak ditemukan (404), tidak perlu lanjut DELETE
        if (checkUser.statusCode() == 404) {
            System.out.println("User tidak ditemukan atau sudah dihapus sebelumnya.");
            return; // Stop eksekusi karena user sudah tidak ada
        }

        // 🗑️ **Langkah 2: Kirim request DELETE jika user masih ada**
        Response deleteResponse = given()
                .header("app-id", "63a804408eb0cb069b57e43a")
                .when()
                .delete("/user/" + userId)
                .then()
                .log().all() // Log detail request & response
                .extract().response();

        // 🔍 **Langkah 3: Validasi hasil DELETE**
        System.out.println("Delete Response: " + deleteResponse.getBody().asPrettyString());

        if (deleteResponse.statusCode() == 200) {
            deleteResponse.then().body("message", containsString("success"));
        } else {
            throw new AssertionError("Unexpected response: " + deleteResponse.statusCode());
        }
    }
}
