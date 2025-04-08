package web.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class HomePage {
    private WebDriver driver;
    private WebDriverWait wait;
    
    private By cartButton = By.id("cartur");

    // Locator untuk tombol Login dan Welcome Message
    private By loginButton = By.id("login2");
    private By welcomeMessage = By.id("nameofuser");

    // Locator untuk daftar produk
    private By productList = By.cssSelector(".card-title a"); // Link pada produk
    
    // Locator tambahan untuk pembelian
    private By addToCartBtn = By.xpath("//a[contains(text(),'Add to cart')]");
    private By placeOrderBtn = By.xpath("//button[@class='btn btn-success']");
    
    // Locator untuk form checkout
    private By nameField = By.id("name");
    private By countryField = By.id("country");
    private By cityField = By.id("city");
    private By cardField = By.id("card");
    private By monthField = By.id("month");
    private By yearField = By.id("year");
    private By purchaseBtn = By.xpath("//button[contains(text(),'Purchase')]");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void goToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(cartButton)).click();
    }

    public void openHomePage() {
        driver.get("https://www.demoblaze.com/");
    }

    public void openURL(String url) {
        driver.get(url);
    }

    public void clickLoginButton() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }

    public String getWelcomeMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(welcomeMessage)).getText();
    }

    public boolean isUserLoggedIn() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(welcomeMessage)).isDisplayed();
    }

    // Pilih produk berdasarkan nama dengan WebDriverWait
    public void selectProduct(String productName) {
        wait.until(ExpectedConditions.presenceOfElementLocated(productList)); // Tunggu daftar produk muncul
        List<WebElement> products = driver.findElements(productList);
        
        for (WebElement product : products) {
            if (product.getText().equalsIgnoreCase(productName)) {
                wait.until(ExpectedConditions.elementToBeClickable(product)).click();
                break;
            }
        }
    }

    // Klik tombol "Add to Cart" setelah memilih produk
    public void addToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(addToCartBtn)).click();
    }

    // Klik tombol "Place Order" di halaman Cart
    public void proceedToCheckout() {
        wait.until(ExpectedConditions.elementToBeClickable(placeOrderBtn)).click();
    }

    // Mengisi form pemesanan dengan data yang diberikan
    public void fillOrderForm(String name, String country, String city, String card, String month, String year) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(nameField)).sendKeys(name);
        driver.findElement(countryField).sendKeys(country);
        driver.findElement(cityField).sendKeys(city);
        driver.findElement(cardField).sendKeys(card);
        driver.findElement(monthField).sendKeys(month);
        driver.findElement(yearField).sendKeys(year);
    }

    // Klik tombol "Purchase" untuk menyelesaikan pesanan
    public void clickPurchase() {
        wait.until(ExpectedConditions.elementToBeClickable(purchaseBtn)).click();
    }
}
