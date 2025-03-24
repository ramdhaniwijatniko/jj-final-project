package web.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {
    private WebDriver driver;

    // Locator untuk tombol Login
    private By loginButton = By.id("login2");

    // Locator untuk welcome message setelah login berhasil
    private By welcomeMessage = By.id("nameofuser");

    // Constructor
    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    // Method untuk membuka URL
    public void openURL(String url) {
        driver.get(url);
    }

    // Method untuk klik tombol Login
    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

    // Method untuk mendapatkan welcome message setelah login
    public String getWelcomeMessage() {
        return driver.findElement(welcomeMessage).getText();
    }

    public boolean isUserLoggedIn() {
        return driver.findElement(By.id("welcome-message")).isDisplayed(); // Sesuaikan dengan locator yang benar
    }
    
}
