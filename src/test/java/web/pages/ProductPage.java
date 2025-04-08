package web.pages;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductPage {
    private WebDriver driver;
    WebDriverWait wait; // ✅ Deklarasi variabel wait
    // ✅ Constructor untuk menerima WebDriver
    public ProductPage(WebDriver driver) {
        this.driver = driver;
    }

    // Locator tombol "Add to Cart"
    private By addToCartButton = By.xpath("//a[@class='btn btn-success btn-lg']");


    // ✅ Metode untuk menambahkan produk ke keranjang
    public void clickAddToCart() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));
        button.click();
    }

    public void acceptAlert() {
    try {
        Alert alert = driver.switchTo().alert();
        alert.accept();
    } catch (NoAlertPresentException e) {
        System.out.println("No alert present.");
    }
}

}
