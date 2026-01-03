package org.zasyasolutions.portfolio.baseTestPackage;

import java.time.Duration;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GotoPage {
    
    private WebDriver driver;
    
    public GotoPage(WebDriver driver) {
        this.driver = driver;
    }
    
    public void Goto() {
        long startTime = System.currentTimeMillis();
        driver.get("https://stagingportfolio.zasyasolutions.com/");
        long loadTime = System.currentTimeMillis() - startTime;
        System.out.println("Page load time: " + loadTime + "ms");
        
        WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(40));
        try {
            longWait.until(webDriver -> 
                ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete")
            );
        } catch (TimeoutException e) {
            System.out.println("⚠ Page readyState check timed out, continuing anyway...");
        }
    }
}