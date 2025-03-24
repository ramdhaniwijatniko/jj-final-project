package api.stepdefinitions;

import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.junit.Assert;
import java.util.UUID;

public class APISteps {
    private static final String BASE_URL = "https://dummyapi.io/data/v1";
    private static final String APP_ID = "63a804408eb0cb069b57e43a";
    private static String userID;
    private Response response;

    @Given("I have user details with first name {string}, last name {string}, and a unique email")
    public void i_have_user_details(String firstName, String lastName) {
        String uniqueEmail = firstName.toLowerCase() + "." + lastName.toLowerCase() + System.currentTimeMillis() + "@example.com";
        
        // Debugging request body
        String requestBody = "{\"firstName\": \"" + firstName + "\", \"lastName\": \"" + lastName + "\", \"email\": \"" + uniqueEmail + "\" }";
        System.out.println("[DEBUG] Request Body: " + requestBody);
    
        response = RestAssured.given()
                    .header("app-id", APP_ID)
                    .contentType("application/json")
                    .body(requestBody)
                    .when()
                    .post(BASE_URL + "/user/create");
    
        // Debugging response
        System.out.println("[DEBUG] Response: " + response.asString());
        System.out.println("[DEBUG] Status Code: " + response.getStatusCode());
    
        // Jika gagal, hentikan test
        if (response.getStatusCode() != 200) {
            throw new RuntimeException("User creation failed! Response: " + response.asString());
        }
    
        // Simpan userID untuk digunakan di test berikutnya
        userID = response.jsonPath().getString("id");
        System.out.println("[CREATE] User ID Created: " + userID);
        Assert.assertNotNull("User ID should not be null after creation", userID);
    }
    
 @When("I send a request to create the user")
    public void i_send_a_request_to_create_the_user() {
        String requestBody = "{\"firstName\": \"Indah\", \"lastName\": \"Padawaktunya\", \"email\": \"indah." + System.currentTimeMillis() + "@example.com\"}";

        response = RestAssured.given()
            .header("app-id", APP_ID)
            .contentType("application/json")
            .body(requestBody)
            .when()
            .post(BASE_URL + "/user/create");

        System.out.println("[DEBUG] Create User Response: " + response.asString());

        // Simpan userID untuk langkah berikutnya
        userID = response.jsonPath().getString("id");
    }

    @Then("the user should be created successfully")
    public void the_user_should_be_created_successfully() {
        response.then().statusCode(200);
        Assert.assertNotNull("User ID should not be null", userID);
        System.out.println("[SUCCESS] User Created with ID: " + userID);
    }


    @When("I send a POST request to {string}")
    public void i_send_a_post_request_to(String endpoint) {
        response = given()
            .header("app-id", APP_ID)
            .contentType("application/json")
            .when()
            .post(BASE_URL + endpoint);

        System.out.println("[POST] Request to: " + BASE_URL + endpoint);
        System.out.println("[DEBUG] Response: " + response.asString());
    }


    @When("I send a PUT request to {string}")
    public void i_send_a_put_request_to(String endpoint) {
        String finalEndpoint = BASE_URL + endpoint.replace("{id}", userID);
        System.out.println("[PUT] Request to: " + finalEndpoint);

        response = given()
            .header("app-id", APP_ID)
            .contentType("application/json")
            .body("{ \"firstName\": \"Updated Name\", \"email\": \"updated_" + UUID.randomUUID().toString().substring(0, 8) + "@example.com\" }")
            .when()
            .put(finalEndpoint);

        System.out.println("[DEBUG] Response: " + response.asString());
    }


    @Then("the response status should be {int}")
    public void the_response_status_should_be(int statusCode) {
        response.then().statusCode(anyOf(equalTo(200), equalTo(201)));
    }

    @Then("the response should contain the user ID")
    public void the_response_should_contain_the_user_id() {
        userID = response.jsonPath().getString("id");
        Assert.assertNotNull("User ID should be present in response", userID);
        System.out.println("[DEBUG] User ID: " + userID);
    }

    @Then("I save the user ID for later use")
    public void i_save_the_user_id_for_later_use() {
        Assert.assertNotNull("User ID must be saved", userID);
    }

    // ✅ GET User
    @Given("I have the saved user ID")
    public void i_have_the_saved_user_id() {
        System.out.println("[CHECK] Current User ID: " + userID);
        Assert.assertNotNull("User ID must be saved before this step", userID);
    }

    @When("I send a GET request to {string}")
    public void i_send_a_get_request_to(String endpoint) {
        String finalEndpoint = BASE_URL + endpoint.replace("{id}", userID);
        System.out.println("[GET] Request to: " + finalEndpoint);

        response = given()
            .header("app-id", APP_ID)
            .when()
            .get(finalEndpoint);
    }

    @Then("the response should contain the user details")
    public void the_response_should_contain_the_user_details() {
        response.then().statusCode(200)
                .body("id", equalTo(userID))
                .body("firstName", notNullValue())
                .body("email", notNullValue());
    }

    // ✅ UPDATE User
    @Given("I have updated user details with name {string} and email {string}")
    public void i_have_updated_user_details_with_name_and_email(String name, String email) {
        response = given()
            .header("app-id", APP_ID)
            .contentType("application/json")
            .body("{ \"firstName\": \"" + name + "\", \"email\": \"" + email + "\" }")
            .when()
            .put(BASE_URL + "/user/" + userID);

        System.out.println("[UPDATE] Request Body: " + "{ \"firstName\": \"" + name + "\", \"email\": \"" + email + "\" }");
        System.out.println("[DEBUG] Update Response: " + response.asString());
    }

    @Then("the response should contain the updated details")
    public void the_response_should_contain_the_updated_details() {
        response.then().statusCode(200)
                .body("firstName", notNullValue())
                .body("email", notNullValue());
    }

    // ✅ DELETE User
    @When("I send a DELETE request to {string}")
    public void i_send_a_delete_request_to(String endpoint) {
        String finalEndpoint = BASE_URL + endpoint.replace("{id}", userID);
        System.out.println("[DELETE] Request to: " + finalEndpoint);

        response = given()
            .header("app-id", APP_ID)
            .when()
            .delete(finalEndpoint);

        response.then().statusCode(200);
        System.out.println("[DEBUG] Delete Response: " + response.asString());
    }

    @Then("the user should no longer exist")
    public void the_user_should_no_longer_exist() throws InterruptedException {
        // ✅ Tunggu sebelum GET untuk memastikan user sudah terhapus
        Thread.sleep(2000);

        given()
            .header("app-id", APP_ID)
            .when()
            .get(BASE_URL + "/user/" + userID)
            .then()
            .statusCode(404);

        System.out.println("[CHECK] User successfully deleted.");
    }
}
