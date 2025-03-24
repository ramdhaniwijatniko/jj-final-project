package web.tests;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features/web",  // Lokasi file fitur
    glue = "web.stepdefinitions",                 // Lokasi step definitions
    plugin = {
        "pretty", 
        "html:target/cucumber-reports/web-test-report.html",  // Laporan HTML
        "json:target/cucumber-reports/web-test-report.json"   // Laporan JSON
    },
    tags = "@web", // Jalankan hanya skenario dengan tag @web
    monochrome = true
)
public class WebTestRunner {
}
