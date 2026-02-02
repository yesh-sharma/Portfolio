package org.zasyasolutions.portfolio.utils;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
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
    private WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(1));
    private JavascriptExecutor js;
    
    public ReusableCode(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        this.js = (JavascriptExecutor) driver;
    }

    public WebElement waitForVisible(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }
    public WebElement waitForVisibleTime(WebElement element) {
        return wait1.until(ExpectedConditions.visibilityOf(element));
    }
    public WebElement waitForVisibleTimeBy(By locator) {
        return wait1.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    
    public WebElement waitForVisibleBy(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement waitForClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }
    public WebElement waitForClickableBy(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    
    public void waitForAtLeastOne(By locator) {
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }
    
    public List<WebElement> waitForListElement(List<WebElement> element) {
        return element;
    }
    
    public List<WebElement> waitForListElementBy(By locator) {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }
    
    public void scrollIntoView(WebElement element) throws InterruptedException {
		js.executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
		Thread.sleep(1000);
    }
    
    public void waitUntil(java.util.function.Function<WebDriver, Boolean> condition) {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(condition);
    }
    
    
    public void clickAlertOk() {
      
       try { Alert alert = driver.switchTo().alert();
        alert.accept(); }catch(Exception e){}
    }

	public Object isElementPresent(Object successNotificationBy) {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean waitForInvisibleBy(By xpath) {
		
		return wait.until(ExpectedConditions.invisibilityOfElementLocated(xpath));
	}
}