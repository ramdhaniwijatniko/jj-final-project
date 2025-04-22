package web.stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import web.pages.HomePage;

import java.time.Duration;

public class CommonSteps {

    private static WebDriver driver;
    private static HomePage homePage;

    @Before
    public void setUp() {
        if (driver == null) {
            WebDriverManager.chromedriver().setup(); // otomatis download driver
            ChromeOptions options = new ChromeOptions();

            // Kamu bisa sesuaikan ini tergantung environment
            options.addArguments("--headless"); // hapus jika ingin lihat UI
            options.addArguments("window-size=1920,1080");

            driver = new ChromeDriver(options);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
            homePage = new HomePage(driver);
        }
    }

    public static WebDriver getDriver() {
        return driver;
    }

    @Given("user membuka halaman utama Demoblaze")
    public void user_membuka_halaman_utama_demoblaze() {
        if (homePage == null) {
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
