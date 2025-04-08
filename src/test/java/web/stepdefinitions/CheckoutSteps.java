package web.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.After;
import io.cucumber.datatable.DataTable;
import web.pages.HomePage;
import web.pages.CheckoutPage;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;

import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;


public class CheckoutSteps {
    WebDriver driver;
    HomePage homePage;
    CheckoutPage checkoutPage;

    @Given("User membuka halaman Demoblaze")
    public void user_membuka_halaman_demoblaze() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        homePage = new HomePage(driver);
        checkoutPage = new CheckoutPage(driver);

        homePage.openHomePage();

        new WebDriverWait(driver, Duration.ofSeconds(10))
            .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='navbarExample']/ul/li[1]/a")));
    }

    @When("User menambahkan produk {string} ke keranjang")
    public void user_menambahkan_produk_ke_keranjang(String productName) {
        homePage.selectProduct(productName);
        homePage.addToCart();
    }

    @When("User pergi ke halaman cart")
    public void user_pergi_ke_halaman_cart() {
        homePage.goToCart();
    }

    @When("User mencoba melakukan checkout tanpa mengisi data")
    public void user_mencoba_melakukan_checkout_tanpa_mengisi_data() {
        checkoutPage.clickPlaceOrder();
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.alertIsPresent());
            Alert alert = driver.switchTo().alert();
            System.out.println("Alert muncul: " + alert.getText());
            alert.accept();
        } catch (Exception e) {
            System.out.println("Tidak ada alert yang muncul.");
        }
    }

    @Then("Checkout gagal dan muncul pesan error {string}")
public void checkout_gagal_dan_muncul_pesan_error(String expectedErrorMessage) {
    try {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15)); // Ubah ke 15 detik
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());

        String actualErrorMessage = alert.getText();
        System.out.println("⚠️ Alert muncul: " + actualErrorMessage);
        alert.accept();

        Assert.assertEquals("Pesan error tidak sesuai!", expectedErrorMessage, actualErrorMessage);

    } catch (TimeoutException e) {
        System.out.println("❌ ERROR: Alert tidak muncul dalam batas waktu.");
        captureScreenshot(driver, "checkout_alert_error.png");
        throw new AssertionError("Alert tidak muncul dalam waktu yang diharapkan.");
    }
}


    @When("User melakukan checkout dengan data berikut:")
    public void user_melakukan_checkout_dengan_data_berikut(DataTable data) {
        List<Map<String, String>> dataList = data.asMaps(String.class, String.class);

        checkoutPage.clickPlaceOrder();
        Assert.assertTrue("Modal checkout tidak muncul", checkoutPage.isOrderModalDisplayed());

        new WebDriverWait(driver, Duration.ofSeconds(10))
            .until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));

        checkoutPage.fillOrderForm(
            dataList.get(0).get("name"),
            dataList.get(0).get("country"),
            dataList.get(0).get("city"),
            dataList.get(0).get("card"),
            dataList.get(0).get("month"),
            dataList.get(0).get("year")
        );
    }

    @When("User menekan tombol Purchase")
    public void userMenekanTombolPurchase() {
        checkoutPage.clickPurchase();
    }

    @Then("User melihat alert dengan pesan {string}")
    public void userMelihatAlertDenganPesan(String expectedMessage) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        if (wait.until(ExpectedConditions.alertIsPresent()) != null) {
            Alert alert = driver.switchTo().alert();
            String actualAlertMessage = alert.getText();
            alert.accept();
            Assert.assertEquals("Pesan alert tidak sesuai", expectedMessage, actualAlertMessage);
        } else {
            Assert.fail("Alert tidak muncul!");
        }
    }

    @Then("Order berhasil diproses dan muncul pesan sukses {string}")
public void order_berhasil_diproses_dan_muncul_pesan_sukses(String expectedMessage) {
    try {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // Tunggu hingga alert sukses muncul
        WebElement successMessageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class, 'sweet-alert')]//h2")
        ));

        String actualMessage = successMessageElement.getText();
        System.out.println("Pesan sukses muncul: " + actualMessage);

        Assert.assertTrue("Pesan sukses tidak sesuai!", actualMessage.contains(expectedMessage));

    } catch (TimeoutException e) {
        System.out.println("❌ ERROR: Pesan sukses tidak muncul dalam batas waktu.");
        captureScreenshot(driver, "checkout_success_error.png");
        throw e;
    }
}

// Fungsi untuk menangkap screenshot saat terjadi error
    public void captureScreenshot(WebDriver driver, String fileName) {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenshot, new File("screenshots/" + fileName));
            System.out.println("📸 Screenshot disimpan: screenshots/" + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Then("User menutup pop-up konfirmasi pembelian")
    public void userMenutupPopupKonfirmasi() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

            // Pastikan pesan sukses muncul
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class, 'sweet-alert') and contains(@class, 'showSweetAlert')]//h2[contains(text(), 'Thank you for your purchase!')]")
            ));

            System.out.println("Pesan sukses ditemukan!");

            // Tunggu tombol "OK" bisa diklik
            WebElement okButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(@class, 'confirm') and text()='OK']")
            ));
            okButton.click();

            System.out.println("Tombol OK berhasil diklik.");
        } catch (Exception e) {
            System.out.println("Error: Tidak dapat menutup pop-up konfirmasi - " + e.getMessage());
            throw e;
        }
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
