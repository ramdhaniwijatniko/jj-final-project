package web.stepdefinitions;

import io.cucumber.java.en.*;
import java.util.UUID;
import org.openqa.selenium.WebDriver;
import web.pages.RegisterPage;

public class RegisterSteps {
    private WebDriver driver;
    private RegisterPage registerPage;
    private String uniqueUsername;  // Simpan username unik agar bisa digunakan antar step

    // Generate username unik menggunakan UUID
    private String generateUniqueUsername() {
        return "user" + UUID.randomUUID().toString().substring(0, 8);  // Contoh: usera1b2c3d4
    }

    public RegisterSteps() {
        this.driver = CommonSteps.getDriver();
        this.registerPage = new RegisterPage(driver);
    }

    @When("user mengklik menu Sign up")
    public void user_mengklik_menu_sign_up() {
        registerPage.openSignUpForm();
        uniqueUsername = generateUniqueUsername(); // Buat username unik saat membuka form
    }

    @When("user memasukkan username {string}")
    public void user_memasukkan_username(String username) {
        // Jika username yang diberikan adalah "random", gunakan username unik yang sudah dibuat
        if (username.equalsIgnoreCase("random")) {
            username = uniqueUsername;
        }
        registerPage.enterUsername(username);
    }

    @When("user memasukkan password {string}")
    public void user_memasukkan_password(String password) {
        registerPage.enterPassword(password);
    }

    @When("user menekan tombol Sign up")
    public void user_menekan_tombol_sign_up() {
        registerPage.clickSignUp();
    }

    @Then("sistem menampilkan pesan {string}")
    public void sistem_menampilkan_pesan(String expectedMessage) {
        String actualMessage = registerPage.getAlertMessage();
        if (!actualMessage.equals(expectedMessage)) {
            throw new AssertionError("Pesan yang diharapkan: " + expectedMessage + " tetapi yang muncul: " + actualMessage);
        }
    }
}
