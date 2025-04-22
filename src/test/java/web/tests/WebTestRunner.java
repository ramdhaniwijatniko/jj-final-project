package web.tests;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;
import static io.cucumber.junit.platform.engine.Constants.*;

@Suite
@IncludeEngines("cucumber") // Gunakan Cucumber Engine untuk JUnit 5
@SelectClasspathResource("features/web") // Path ke fitur

// Format laporan yang lebih baik
@ConfigurationParameter(
    key = PLUGIN_PROPERTY_NAME,
    value = "pretty, html:target/cucumber-reports/web-test-report.html, json:target/cucumber-reports/web-test-report.json"
)

// Package tempat Step Definitions
@ConfigurationParameter(
    key = GLUE_PROPERTY_NAME,
    value = "web.stepdefinitions"
)

// Urutan eksekusi berdasarkan tag (Register → Login → Checkout)
@ConfigurationParameter(
    key = FILTER_TAGS_PROPERTY_NAME,
    value = "@web"
)
// or @register or @login or @checkout
public class WebTestRunner {
    // Jalankan dengan: ./gradlew test --tests "web.tests.WebTestRunner"
}
