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

public class Skill extends BaseTest {

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
    
	  
	   public String section = "Skills";
	    
  @Test(description = "Adding Data to Skill Section in Profile Page",priority = 1,retryAnalyzer = RetryAnalyzer.class)

	    public void addSkillPosition() throws InterruptedException {
	        loginPage.performLogin();
	        profilePage.navigateToProfile();
	        
	        WebElement skillSection = crudPage.sectionParentElement(section);
	        System.out.println("Job section found: " + (skillSection != null));
	        
	        reusable.scrollIntoView(skillSection);
	        
	        WebElement editButton = crudPage.openEditModal(section);
	        reusable.scrollIntoView(editButton);
	        editButton.click();
	        System.out.println("Edit button found: " + (editButton != null));
	        Thread.sleep(2000);
	      
	        formPage.addTagsData("Rus berry pie");
	        Thread.sleep(1000);
	        Assert.assertEquals(crudPage.successNotificationMessage(), "Profile updated successfully");
	        System.out.println("data added successfully");
	    }
	    
	
	    
	    
    @Test(description = "Updating Data to Skill Section in Profile Page",priority=2,retryAnalyzer = RetryAnalyzer.class)
	
	    public void updateSkillPosition() throws InterruptedException {
	        loginPage.performLogin();
	        profilePage.navigateToProfile();
	        
	        WebElement skillSection = crudPage.sectionParentElement(section);
	        System.out.println(" section found: " + (skillSection != null));
	        
	        reusable.scrollIntoView(skillSection);
	        
	        WebElement editButton = crudPage.openEditModal(section);
	        reusable.scrollIntoView(editButton);
	        editButton.click();
	        System.out.println("Edit button found: " + (editButton != null));
	        Thread.sleep(2000);
	       
	     
	        formPage.updateTagsData("Playwrite Automation");
	        Thread.sleep(1000);
	        Assert.assertEquals(crudPage.successNotificationMessage(), "Profile updated successfully");
	 	    System.out.println("data updated successfully");
	    }
	    
	    @Test(description = "Deleting Data from Skill Section in Profile Page",priority=3,retryAnalyzer = RetryAnalyzer.class,dependsOnMethods = {"addSkillPosition","updateSkillPosition"})
	 
	    public void deleteSkillPosition() throws InterruptedException {
	        loginPage.performLogin();
	        profilePage.navigateToProfile();
	        
	        WebElement skillSection = crudPage.sectionParentElement(section);
	        System.out.println("Job section found: " + (skillSection != null));
	        
	        reusable.scrollIntoView(skillSection);
	        
	        WebElement editButton = crudPage.openEditModal(section);
	        reusable.scrollIntoView(editButton);
	        editButton.click();
	        System.out.println("Edit button found: " + (editButton != null));
	        Thread.sleep(2000);
	       
	     
	        formPage.deleteTagsData();
	      
	        Thread.sleep(500);
	        try {
	        Assert.assertEquals(crudPage.successNotificationMessage(), "Profile updated successfully");
	        } catch (Exception e) {
	        	System.out.println("No data present to delete");
	        }
	 	    System.out.println("data deleted successfully");
	    }
	
}
