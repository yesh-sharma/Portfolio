package org.zasyasolutions.portfolio.login;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.zasyasolutions.portfolio.baseTestPackage.BaseTest;
import org.zasyasolutions.portfolio.baseTestPackage.GotoPage;
import org.zasyasolutions.portfolio.utils.ConfigReader;
import org.zasyasolutions.portfolio.utils.RetryAnalyzer;
import org.zasyasolutions.portfolio.utils.ReusableCode;

public class Login extends BaseTest {

    GotoPage goTo;
    ReusableCode reusable;
    String email;
    String password;

    @FindBy(id="email")
    WebElement usernameField;

    @FindBy(id="password")
    WebElement passwordField;

    @FindBy(xpath="//span[normalize-space()='Sign in']")
    WebElement loginButton;

    @FindBy(xpath="//div[@class='ant-message-notice-content']")
    WebElement loginMessageToaster;
    
    @FindBy(xpath="//a[@href='/login']")
    WebElement signInButton;

    @BeforeMethod
    public void initializePageObjects() {
        // Initialize PageFactory and helper classes
        PageFactory.initElements(driver, this);
        goTo = new GotoPage(driver);
        reusable = new ReusableCode(driver);
        
        // Get credentials
        email = ConfigReader.getProperty("login.email2");
        password = ConfigReader.getProperty("login.password2");
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void loginPage() throws InterruptedException {
        goTo.Goto();

        System.out.println("===User Logging in===");
        
        
        reusable.waitForClickable(signInButton).click();
        Thread.sleep(1000);
        reusable.waitForVisible(usernameField).sendKeys(email);
        reusable.waitForVisible(passwordField).sendKeys(password);
        
        reusable.waitForClickable(loginButton).click();
        String message = reusable.waitForVisible(loginMessageToaster).getText();
        Thread.sleep(2000); // Wait for dashboard to load
        Assert.assertEquals(message, "User successfully logged in!");
        reusable.clickAlertOk();
        Thread.sleep(1000);
        System.out.println("===User Logged in Successfully===");
    }
}