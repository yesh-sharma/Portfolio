package org.zasyasolutions.portfolio.profileModule;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.zasyasolutions.portfolio.baseTestPackage.BaseTest;
import org.zasyasolutions.portfolio.pageObjectModel.LoginPage;
import org.zasyasolutions.portfolio.pageObjectModel.ProfilePage;
import org.zasyasolutions.portfolio.utils.RetryAnalyzer;
import org.zasyasolutions.portfolio.utils.ReusableCode;

public class AddTestimonial extends BaseTest {

	  private LoginPage loginPage;
	  private ProfilePage profilePage;
	  private ReusableCode reusable;

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
			 profilePage.driver = this.driver;
			 profilePage.reusable = this.reusable; 
			 
			PageFactory.initElements(driver, loginPage);
			PageFactory.initElements(driver, profilePage);
			
			loginPage.initializeLoginHelper();
			

			System.out.println("✓ Page Objects Initialized Successfully for Login Test");
	    }
    
    @Test(description = "Verify profile settings toggle and save functionality", retryAnalyzer = RetryAnalyzer.class)
    public void verifyProfileSettingsToggle() throws InterruptedException {
        // Perform login
        loginPage.performLogin();
        
        profilePage.navigateToProfile();
        
        profilePage.addTestimonial();
        profilePage.clickSaveButton();
    }
}
