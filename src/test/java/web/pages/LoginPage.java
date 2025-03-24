package web.pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locator untuk username, password, dan tombol login
    private By usernameField = By.id("loginusername");
    private By passwordField = By.id("loginpassword");
    private By loginButton = By.xpath("//button[text()='Log in']"); // Tombol Login dalam modal

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Tambahkan WebDriverWait
    }

    public void enterUsername(String username) {
        WebElement usernameInput = wait.until(ExpectedConditions.elementToBeClickable(usernameField));
        usernameInput.sendKeys(username);
    }

    public void enterPassword(String password) {
        WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
        passwordInput.sendKeys(password);
    }

    public void clickLoginButton() {
        WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        loginBtn.click();
    }

    // Menangkap pesan error dari alert popup
    public String getErrorMessage() {
        try {
            Alert alert = wait.until(ExpectedConditions.alertIsPresent()); // Tunggu alert muncul
            String errorMessage = alert.getText();   // Ambil teks error
            alert.accept(); // Klik OK untuk menutup alert
            return errorMessage;
        } catch (Exception e) {
            return "No alert found";
        }
    }

    public WebDriver getDriver() {
        return driver;
    }

    public boolean isLoginSuccessful() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout2")));
            return true; // Jika tombol Logout muncul, login berhasil
        } catch (Exception e) {
            return false; // Jika tidak ditemukan, berarti login gagal
        }
    }
    
}
