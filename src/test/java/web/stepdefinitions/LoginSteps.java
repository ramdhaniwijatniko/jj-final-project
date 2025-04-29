package web.stepdefinitions;

import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import web.pages.HomePage;
import web.pages.LoginPage;

public class LoginSteps {
    private WebDriver driver;
    private HomePage homePage;
    private LoginPage loginPage;

    public LoginSteps() {
        this.driver = Hooks.getDriver();
        this.homePage = new HomePage(driver);
    }

    @When("user mengklik menu Log in") 
    public void user_mengklik_menu_log_in() {
        homePage.clickLoginButton();
        loginPage = new LoginPage(driver);
    }

    @When("user memasukkan username {string} dan password {string}")
    public void user_memasukkan_username_dan_password(String username, String password) {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
    }

    @When("user menekan tombol Log in")
    public void user_menekan_tombol_log_in() {
        loginPage.clickLoginButton();
    }

    @Then("sistem menampilkan halaman utama")
    public void sistem_menampilkan_halaman_utama() {
        Assert.assertTrue("Login gagal, tombol Logout tidak ditemukan!", loginPage.isLoginSuccessful());
    }
}
