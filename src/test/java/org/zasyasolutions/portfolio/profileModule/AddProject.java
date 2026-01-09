package org.zasyasolutions.portfolio.profileModule;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.zasyasolutions.portfolio.baseTestPackage.BaseTest;
import org.zasyasolutions.portfolio.baseTestPackage.GotoPage;
import org.zasyasolutions.portfolio.login.Login;
import org.zasyasolutions.portfolio.pageObjectModel.LoginPage;
import org.zasyasolutions.portfolio.pageObjectModel.ProfilePage;
import org.zasyasolutions.portfolio.utils.ConfigReader;
import org.zasyasolutions.portfolio.utils.ReusableCode;

public class AddProject extends BaseTest {
    
	  private LoginPage loginPage;
	  private ProfilePage profilePage;
	  private ReusableCode reusable;
	  
	  @FindBy(xpath="//span[@class='cursor-pointer']")
	    private WebElement starButton;
	  


	    @BeforeMethod(dependsOnMethods = "setUp")
	    public void initializePageObjects() {
	    			System.out.println("=== Initializing Page Objects for Login Test ===");

			// Verify driver is not null
			if (driver == null) {
				throw new IllegalStateException("WebDriver is null! BaseTest.setUp() did not run properly.");
			}

			// Initialize LoginPage helper
			loginPage = new LoginPage();
			profilePage = new ProfilePage();
			reusable = new ReusableCode(driver);
			loginPage.driver = this.driver; // Share the same driver instance
			PageFactory.initElements(driver, loginPage);
			loginPage.initializeLoginHelper();
			

			System.out.println("✓ Page Objects Initialized Successfully for Login Test");
	    }
    
    @Test(description = "Verify profile settings toggle and save functionality")
    public void verifyProfileSettingsToggle() throws InterruptedException {
        // Perform login
        loginPage.performLogin();
        
        profilePage.navigateToProfile();
        
        profilePage.addProject();
        
    }
}