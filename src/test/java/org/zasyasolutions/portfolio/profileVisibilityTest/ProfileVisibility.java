package org.zasyasolutions.portfolio.profileVisibilityTest;

import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.zasyasolutions.portfolio.baseTestPackage.BaseTest;
import org.zasyasolutions.portfolio.pageObjectModel.CRUD_Page;
import org.zasyasolutions.portfolio.pageObjectModel.FormPage;
import org.zasyasolutions.portfolio.pageObjectModel.LoginPage;
import org.zasyasolutions.portfolio.pageObjectModel.ProfilePage;
import org.zasyasolutions.portfolio.pageObjectModel.VisibilityPage;
import org.zasyasolutions.portfolio.utils.RetryAnalyzer;
import org.zasyasolutions.portfolio.utils.ReusableCode;

public class ProfileVisibility extends BaseTest {

	private LoginPage loginPage;
	private ProfilePage profilePage;
	private ReusableCode reusable;
	private CRUD_Page crudPage;
	private FormPage formPage;
	private VisibilityPage visibilityPage;

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
	    crudPage = new CRUD_Page();
	    formPage = new FormPage();
	    visibilityPage = new VisibilityPage();

	    loginPage.driver = this.driver;
	    profilePage.driver = this.driver;
	    profilePage.reusable = this.reusable;
	    crudPage.driver = this.driver;
	    crudPage.reusable = this.reusable;
	    formPage.driver = this.driver;
	    formPage.reusable = this.reusable;
	    visibilityPage.driver = this.driver;
	    visibilityPage.reusable = this.reusable;
	    visibilityPage.crudPage = this.crudPage;  // ← ADD THIS LINE

	    PageFactory.initElements(driver, loginPage);
	    PageFactory.initElements(driver, profilePage);
	    PageFactory.initElements(driver, crudPage);
	    PageFactory.initElements(driver, formPage);
	    PageFactory.initElements(driver, visibilityPage);

	    loginPage.initializeLoginHelper();

	    System.out.println("✓ Page Objects Initialized Successfully for Login Test");
	}



//	@Test(description = "Adding Data to Skill Section in Profile Page", priority = 1, retryAnalyzer = RetryAnalyzer.class)
	@Test
	public void visibilityTest() throws InterruptedException {
	    String section[] = {
	        "Job Position",  
	        "Skills", 
	        "Project",
	        "Testimonial",
	        "Education",
	        "Language",
	        "Licenses & Certifications",
	        "Awards & Honors",
	        "Publications",
	        "Social & Links",
	        "Additional & Links"
	    };
	    
	    loginPage.performLogin();
	    profilePage.navigateToProfile();
	    
	    // Wait for page to fully load
	    Thread.sleep(3000); 
	    
	    for(int i = 0; i < section.length; i++) { 
	        System.out.println("Processing section: " + section[i]);
	        
	        WebElement skillSection = crudPage.sectionParentElement(section[i]);
	        System.out.println("Section found: " + (skillSection != null));
	        
	        reusable.scrollIntoView(skillSection);
	        
	        WebElement visibilityButton = crudPage.openVisibilityModal(section[i]);
	        reusable.scrollIntoView(visibilityButton);
	        System.out.println("Visibility button found: " + (visibilityButton != null));
	        
	        Thread.sleep(2000);
	        visibilityPage.checkVisibilityOfSection(section[i]);
	        Thread.sleep(1000);
	        
	        Assert.assertEquals(crudPage.successNotificationMessage(), 
	                           "Profile updated successfully");
	        System.out.println("Section " + section[i] + " processed successfully");
	    }
	}
}
