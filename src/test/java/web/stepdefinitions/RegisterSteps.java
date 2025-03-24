package web.stepdefinitions;

import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import web.pages.RegisterPage;

public class RegisterSteps {
    private WebDriver driver;
    private RegisterPage registerPage;

    public RegisterSteps() {
        this.driver = CommonSteps.getDriver();
        this.registerPage = new RegisterPage(driver);
    }

    @When("user mengklik menu Sign up")
    public void user_mengklik_menu_sign_up() {
        registerPage.openSignUpForm();
    }

    @When("user memasukkan username {string}")
    public void user_memasukkan_username(String username) {
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
