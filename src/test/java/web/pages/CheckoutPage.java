package web.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class CheckoutPage {
    WebDriver driver;
    WebDriverWait wait;

    private By purchaseButton = By.xpath("//*[@id='orderModal']//button[contains(text(), 'Purchase')]");
    private By placeOrderButton = By.xpath("//*[@id='page-wrapper']/div/div[2]/button");
    private By successMessageLocator = By.xpath("//h2[contains(text(), 'Thank you for your purchase!')]");
    private By okButton = By.cssSelector(".confirm.btn.btn-lg.btn-primary");
    private By orderModal = By.id("orderModal");
    private By errorMessageLocator = By.xpath("//div[contains(@class, 'modal-body')]");

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); 
    }

    // ✅ Klik tombol "Place Order"
    public void clickPlaceOrder() {
        try {
            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(placeOrderButton));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", button);
            button.click();
        } catch (TimeoutException e) {
            System.out.println("Tombol Place Order tidak bisa diklik langsung, mencoba dengan JavaScript...");
            try {
                WebElement button = driver.findElement(placeOrderButton);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
            } catch (NoSuchElementException ex) {
                throw new RuntimeException("Tombol Place Order tidak ditemukan!", ex);
            }
        }
    }

    // ✅ Pastikan modal checkout muncul
    public boolean isOrderModalDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(orderModal)).isDisplayed();
    }

    // ✅ Mengisi form checkout setelah modal muncul
    public void fillOrderForm(String name, String country, String city, String card, String month, String year) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(orderModal));

        if (!name.isEmpty()) driver.findElement(By.id("name")).sendKeys(name);
        if (!country.isEmpty()) driver.findElement(By.id("country")).sendKeys(country);
        if (!city.isEmpty()) driver.findElement(By.id("city")).sendKeys(city);
        if (!card.isEmpty()) driver.findElement(By.id("card")).sendKeys(card);
        if (!month.isEmpty()) driver.findElement(By.id("month")).sendKeys(month);
        if (!year.isEmpty()) driver.findElement(By.id("year")).sendKeys(year);
    }

    // ✅ Klik tombol Purchase
    public void clickPurchase() {
        try {
            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(purchaseButton));
            button.click();
        } catch (TimeoutException e) {
            System.out.println("Tombol Purchase tidak bisa diklik langsung, mencoba dengan JavaScript...");
            WebElement button = driver.findElement(purchaseButton);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
        }
    }

    // ✅ Checkout dengan mengisi form dan klik tombol "Purchase"
    public void checkout(String name, String country, String city, String card, String month, String year) {
        fillOrderForm(name, country, city, card, month, year);

        WebElement checkoutButton = wait.until(ExpectedConditions.elementToBeClickable(purchaseButton));
        checkoutButton.click();

        // ✅ Tunggu sampai modal checkout menghilang
        wait.until(ExpectedConditions.invisibilityOfElementLocated(orderModal));
    }

    // ✅ Mengambil pesan error saat checkout gagal (bisa dari modal atau alert)
    public String getErrorMessage() {
        try {
            return handleAlertIfPresent(); // Cek alert error
        } catch (Exception e) {
            try {
                WebElement errorMsg = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(errorMessageLocator)
                );
                return errorMsg.getText();
            } catch (TimeoutException ex) {
                return "Pesan error tidak ditemukan.";
            }
        }
    }

    // ✅ Mengambil pesan sukses setelah checkout berhasil
    public String getSuccessMessage() {
        try {
            WebElement successMessageElement = driver.findElement(By.xpath("//div[@class='sweet-alert showSweetAlert visible']//h2"));
            String successMessage = successMessageElement.getText();
            System.out.println("Pesan sukses ditemukan: " + successMessage);
            return successMessage;
        } catch (Exception e) {
            System.out.println("Error: Tidak dapat menemukan pesan sukses - " + e.getMessage());
            return "";
        }
    }


    // ✅ Menekan tombol "OK" setelah checkout berhasil
    public void confirmPurchase() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(successMessageLocator));

        WebElement btnOK = wait.until(ExpectedConditions.elementToBeClickable(okButton));
        btnOK.click();

        // ✅ Tunggu hingga modal checkout hilang
        wait.until(ExpectedConditions.invisibilityOfElementLocated(orderModal));
    }

    // ✅ Handle alert jika muncul
    public String handleAlertIfPresent() {
        try {
            wait.until(ExpectedConditions.alertIsPresent());
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            alert.accept(); // Klik OK pada alert
            return alertText;
        } catch (TimeoutException e) {
            return "No alert present.";
        }
    }
}
