package web.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CartPage {
    private WebDriver driver;

    // Constructor
    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    // Locator tombol checkout (Place Order)
    private By placeOrderButton = By.xpath("//button[text()='Place Order']");

    // Metode untuk klik tombol checkout
    public void clickCheckoutButton() {
        WebElement checkoutBtn = driver.findElement(placeOrderButton);
        checkoutBtn.click();
    }
}
