package web.stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import web.pages.HomePage;

public class CommonSteps {
    private static WebDriver driver;
    private static HomePage homePage;

    @Before
    public void setUp() {
        if (driver == null) {
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            homePage = new HomePage(driver); // Inisialisasi homePage di sini
        }
    }

    public static WebDriver getDriver() {
        return driver;
    }

    @Given("user membuka halaman utama Demoblaze")
    public void user_membuka_halaman_utama_demoblaze() {
        if (homePage == null) { // Pastikan homePage tidak null
            homePage = new HomePage(driver);
        }
        homePage.openURL("https://www.demoblaze.com/");
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
