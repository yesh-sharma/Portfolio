package org.zasyasolutions.portfolio.profileModule;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.zasyasolutions.portfolio.baseTestPackage.BaseTest;
import org.zasyasolutions.portfolio.pageObjectModel.CRUD_Page;
import org.zasyasolutions.portfolio.pageObjectModel.FormPage;
import org.zasyasolutions.portfolio.pageObjectModel.LoginPage;
import org.zasyasolutions.portfolio.pageObjectModel.ProfilePage;
import org.zasyasolutions.portfolio.utils.RetryAnalyzer;
import org.zasyasolutions.portfolio.utils.ReusableCode;

public class Education extends BaseTest {

	  private LoginPage loginPage;
	  private ProfilePage profilePage;
	  private ReusableCode reusable;
	  private CRUD_Page crudPage;
	  private FormPage formPage;

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
			
			
			loginPage.driver = this.driver; // Share the same driver instance
			 profilePage.driver = this.driver;
			 profilePage.reusable = this.reusable; 
			 crudPage.driver = this.driver;
			 crudPage.reusable = this.reusable;
			 formPage.driver = this.driver;
			 formPage.reusable = this.reusable; 
			 
			PageFactory.initElements(driver, loginPage);
			PageFactory.initElements(driver, profilePage);
			PageFactory.initElements(driver, crudPage);
			PageFactory.initElements(driver, formPage);
			
			
			loginPage.initializeLoginHelper();
			

			System.out.println("✓ Page Objects Initialized Successfully for Login Test");
	    }
    
	  
	   public String section = "Education";
	   public String addButtonLabel = "Add Education";
	    
	    @Test(description = "Adding Data to Education Section in Profile Page",priority = 1,retryAnalyzer = RetryAnalyzer.class)
	    public void addAwaardPosition() throws InterruptedException {
	        loginPage.performLogin();
	        profilePage.navigateToProfile();
	        
	        WebElement jobSection = crudPage.sectionParentElement(section);
	        System.out.println("Job section found: " + (jobSection != null));
	        
	        reusable.scrollIntoView(jobSection);
	        
	        WebElement editButton = crudPage.openEditModal(section);
	        reusable.scrollIntoView(editButton);
	        editButton.click();
	        System.out.println("Edit button found: " + (editButton != null));
	        Thread.sleep(2000);
	       try {
	        crudPage.openFormModal(addButtonLabel).click();
	       }catch(Exception e) {}
	        Thread.sleep(2000);
	        formPage.addEducationData();
	        Thread.sleep(1000);
	        Assert.assertEquals(crudPage.successNotificationMessage(), "Profile updated successfully");
	        System.out.println("data added successfully");
	    }
	    
	    @Test(description = "Updating Data to Education Section in Profile Page",priority=2,retryAnalyzer = RetryAnalyzer.class)
	    public void updateAwardPosition() throws InterruptedException {
	        loginPage.performLogin();
	        profilePage.navigateToProfile();
	        
	        WebElement jobSection = crudPage.sectionParentElement(section);
	        System.out.println("Job section found: " + (jobSection != null));
	        
	        reusable.scrollIntoView(jobSection);
	        
	        WebElement editButton = crudPage.openEditModal(section);
	        reusable.scrollIntoView(editButton);
	        editButton.click();
	        System.out.println("Edit button found: " + (editButton != null));
	        Thread.sleep(2000);
	       
	     
	        crudPage.updateFormModal().click();
	        Thread.sleep(2000);
	        formPage.updateEducationData();
	        Thread.sleep(1000);
	        Assert.assertEquals(crudPage.successNotificationMessage(), "Profile updated successfully");
	 	    System.out.println("data updated successfully");
	    }
	    
	    @Test(description = "Deleting Data from Education Section in Profile Page",priority=3,retryAnalyzer = RetryAnalyzer.class)
	    public void deleteAwardPosition() throws InterruptedException {
	        loginPage.performLogin();
	        profilePage.navigateToProfile();
	        
	        WebElement jobSection = crudPage.sectionParentElement(section);
	        System.out.println("Job section found: " + (jobSection != null));
	        
	        reusable.scrollIntoView(jobSection);
	        
	        WebElement editButton = crudPage.openEditModal(section);
	        reusable.scrollIntoView(editButton);
	        editButton.click();
	        System.out.println("Edit button found: " + (editButton != null));
	        Thread.sleep(2000);
	       
	     
	        crudPage.deleteFormModal().click();
	      
	        Thread.sleep(1000);
	        Assert.assertEquals(crudPage.successNotificationMessage(), "Record Deleted Successfully");
	 	    System.out.println("data deleted successfully");
	    }
	
}
