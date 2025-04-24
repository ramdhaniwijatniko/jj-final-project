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
            options.addArguments("--headless=new"); // gunakan headless new untuk lebih stabil
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");
            options.addArguments("--remote-allow-origins=*");

            // Cek apakah sedang di CI environment
            String ci = System.getenv("CI");
            if (ci != null && ci.equalsIgnoreCase("true")) {
                System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
            } else {
                WebDriverManager.chromedriver().setup(); // untuk local
            }

            System.out.println("Initializing WebDriver...");
            driver = new ChromeDriver(options);
            driver.manage().window().maximize();
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
