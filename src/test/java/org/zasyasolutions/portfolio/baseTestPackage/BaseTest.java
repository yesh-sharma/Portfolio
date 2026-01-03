package org.zasyasolutions.portfolio.baseTestPackage;

import java.time.Duration;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.zasyasolutions.portfolio.utils.ChromeOptionsConfig;
import org.zasyasolutions.portfolio.utils.ConfigReader;

public class BaseTest {
    public WebDriver driver;
    public static WebDriverWait wait;
    public JavascriptExecutor js;

    @BeforeMethod
    public void setUp() throws Exception {
        System.out.println("=== SETUP STARTED ===");
        try {
            Thread.sleep(1000);
            String browser = System.getProperty("browser") != null ? System.getProperty("browser")
                    : ConfigReader.getProperty("browser");
            System.out.println("Browser: " + browser);
            
            // Initialize the driver based on the browser value
            if (browser.equalsIgnoreCase("chrome")) {
                ChromeOptions options = ChromeOptionsConfig.getChromeOptions();
                driver = new ChromeDriver(options);
            } else if (browser.equalsIgnoreCase("firefox")) {
                driver = new FirefoxDriver();
            } else if (browser.equalsIgnoreCase("edge")) {
                driver = new EdgeDriver();
            } else if (browser.equalsIgnoreCase("safari")) {
                driver = new SafariDriver();
            } else {
                throw new IllegalArgumentException("Browser " + browser + " is not supported.");
            }
            
            driver.manage().deleteAllCookies();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
            
            // Initialize WebDriverWait
            wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            js = (JavascriptExecutor) driver;
            System.out.println("✓ Driver initialized successfully");
        } catch (Exception e) {
            System.err.println("✗ Failed to initialize driver: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
        System.out.println("=== SETUP COMPLETED ===");
    }

    @AfterMethod
    public void tearDown() {
        System.out.println("=== TEARDOWN STARTED ===");
        if (driver != null) {
            driver.quit();
            System.out.println("Driver quit successfully");
            System.out.println("=== TEARDOWN COMPLETED ===");
        }
    }
}