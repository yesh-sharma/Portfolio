package org.zasyasolutions.portfolio.utils;

import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.zasyasolutions.portfolio.baseTestPackage.BaseTest;

import java.time.Duration;
import java.util.List;

public class ReusableCode extends BaseTest {
    
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;
    
    public ReusableCode(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        this.js = (JavascriptExecutor) driver;
    }

    public WebElement waitForVisible(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public WebElement waitForClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }
    
    public List<WebElement> waitForListElement(List<WebElement> element) {
        return wait.until(ExpectedConditions.visibilityOfAllElements(element));
    }
    
    public void scrollIntoView(WebElement element) {
		js.executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
	}
    
    public void clickAlertOk() {
      
       try { Alert alert = driver.switchTo().alert();
        alert.accept(); }catch(Exception e){}
    }
}