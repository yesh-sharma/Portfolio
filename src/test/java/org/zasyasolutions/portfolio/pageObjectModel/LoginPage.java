package org.zasyasolutions.portfolio.pageObjectModel;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.zasyasolutions.portfolio.baseTestPackage.BaseTest;
import org.zasyasolutions.portfolio.baseTestPackage.GotoPage;
import org.zasyasolutions.portfolio.utils.ConfigReader;
import org.zasyasolutions.portfolio.utils.ReusableCode;

public class LoginPage extends BaseTest {


    private ReusableCode reusable;
    private String email;
    private String password;

    @FindBy(id="email")
    private WebElement usernameField;

    @FindBy(id="password")
    private WebElement passwordField;

    @FindBy(xpath="//span[normalize-space()='Sign in']")
    private WebElement loginButton;

    @FindBy(xpath="//div[@class='ant-message-notice-content']")
    private WebElement loginMessageToaster;

    @FindBy(xpath="//a[@href='/login']")
    private WebElement signInButton;
    
    @FindBy(xpath="//button[normalize-space()='Logout']")
    private WebElement logoutButton;

    @BeforeMethod(dependsOnMethods = "setUp")
    public void initializePageObjects() {
        initializeLoginHelper();
    }

    // Public method to initialize when used as helper
    public void initializeLoginHelper() {
        System.out.println("=== Initializing Login Helper ===");

        if (driver == null) {
            throw new IllegalStateException("WebDriver is null!");
        }

        // *** CRITICAL: Initialize PageFactory to bind @FindBy elements ***
        PageFactory.initElements(driver, this);

        // Initialize helper objects
       
        reusable = new ReusableCode(driver);

        // Load credentials
        email = ConfigReader.getProperty("login.email2");
        password = ConfigReader.getProperty("login.password2");

        System.out.println("✓ Login Helper Initialized");
    }

    // Public method to perform login (can be called from other tests)
    public void performLogin() throws InterruptedException {
        System.out.println("===User Logging in===");

        reusable.waitForClickable(signInButton).click();
        Thread.sleep(1000);

        reusable.waitForVisible(usernameField).sendKeys(email);
        reusable.waitForVisible(passwordField).sendKeys(password);

        reusable.waitForClickable(loginButton).click();

        String message = reusable.waitForVisible(loginMessageToaster).getText();
        Thread.sleep(2000);

        Assert.assertEquals(message, "User successfully logged in!");
        reusable.clickAlertOk();
        Thread.sleep(1000);

        System.out.println("===User Logged in Successfully===");
    }

	public void performLogout() {
		// TODO Auto-generated method stub
		reusable.waitForClickable(logoutButton).click();
		
	}

	public void invalidLogin() {
		// TODO Auto-generated method stub
		System.out.println("===User Logging in with invalid credentials===");
		
		reusable.waitForClickable(signInButton).click();
		
		reusable.waitForVisible(usernameField).sendKeys("lokesh@zasyasolutions.com");
		reusable.waitForVisible(passwordField).sendKeys("Invalid@Password123");
		reusable.waitForClickable(loginButton).click();
		String message = reusable.waitForVisible(loginMessageToaster).getText();	
			Assert.assertEquals(message, "Invalid email or password.");
			reusable.clickAlertOk();
			
			Assert.assertTrue(driver.getCurrentUrl().contains("/login"),
	                "User is not navigated to Login page after invalid login attempt");
			System.out.println("===Invalid Login Attempt Handled Successfully===");
	}
}
