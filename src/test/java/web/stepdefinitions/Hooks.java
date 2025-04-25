package web.stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Hooks {
    private static WebDriver driver;

    @Before
    public void setUp() {
        if (driver == null) {
            ChromeOptions options = new ChromeOptions();

            // Ambil argumen dari sistem properti jika tersedia (untuk CI)
            String chromeOptionsArgs = System.getProperty("chromeoptions.args");
            if (chromeOptionsArgs != null && !chromeOptionsArgs.isEmpty()) {
                options.addArguments(chromeOptionsArgs.split(" "));
            } else {
                // Default options untuk lokal development
                options.addArguments("--start-maximized");
                options.addArguments("--remote-allow-origins=*");
            }

            // Setup chromedriver via WebDriverManager
            WebDriverManager.chromedriver().setup();

            System.out.println("Initializing WebDriver...");
            driver = new ChromeDriver(options);
            System.out.println("WebDriver initialized successfully.");
        }
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed() && driver != null) {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "screenshot");
        }

        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    public static WebDriver getDriver() {
        return driver;
    }
}
