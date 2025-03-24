package web.stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebSteps {
    protected WebDriver driver;

    @Before
    public void setUp() {
        driver = new ChromeDriver();
    }

    @Given("user membuka website Demoblaze")
    public void userMembukaWebsiteDemoblaze() {
        driver.get("https://www.demoblaze.com/");
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
