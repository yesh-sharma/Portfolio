package org.zasyasolutions.portfolio.login;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.zasyasolutions.portfolio.baseTestPackage.BaseTest;
import org.zasyasolutions.portfolio.baseTestPackage.GotoPage;
import org.zasyasolutions.portfolio.pageObjectModel.LoginPage;
import org.zasyasolutions.portfolio.utils.ConfigReader;
import org.zasyasolutions.portfolio.utils.RetryAnalyzer;
import org.zasyasolutions.portfolio.utils.ReusableCode;

public class Login extends BaseTest {

    private LoginPage loginPage;

    @BeforeMethod(dependsOnMethods = "setUp")
    public void initializePageObjects() {
    			System.out.println("=== Initializing Page Objects for Login Test ===");

		// Verify driver is not null
		if (driver == null) {
			throw new IllegalStateException("WebDriver is null! BaseTest.setUp() did not run properly.");
		}

		// Initialize LoginPage helper
		loginPage = new LoginPage();
		loginPage.driver = this.driver; // Share the same driver instance
		PageFactory.initElements(driver, loginPage);
		loginPage.initializeLoginHelper();

		System.out.println("✓ Page Objects Initialized Successfully for Login Test");
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void loginPage() throws InterruptedException {
        
    	loginPage.performLogin();
    }
    
    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void invalidLogin() throws InterruptedException {
    	loginPage.invalidLogin();
    }
    
    
    @ Test(retryAnalyzer = RetryAnalyzer.class)
    public void logOut() throws InterruptedException {
		
		loginPage.performLogout();
		
	}
}