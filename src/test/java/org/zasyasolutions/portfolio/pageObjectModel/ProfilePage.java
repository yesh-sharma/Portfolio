package org.zasyasolutions.portfolio.pageObjectModel;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.zasyasolutions.portfolio.baseTestPackage.BaseTest;
import org.zasyasolutions.portfolio.baseTestPackage.GotoPage;
import org.zasyasolutions.portfolio.utils.ReusableCode;

public class ProfilePage extends BaseTest{
	  private GotoPage goTo;
	    private ReusableCode reusable;
	    private LoginPage loginPage;
	   
	    @FindBy(id="menu-settings")
	    private WebElement settingsMenu;
	    
	    @FindBy(xpath="//span[normalize-space()='Profile']")
	    private WebElement profileMenu;
	    
	    @FindBy(xpath="//span[@class='cursor-pointer']")
	    private WebElement starButton;
	    
	  @FindBy(xpath="//button[contains(@class,'ant-switch')]")
	  WebElement toggleSwitch;
	  
	  @FindBy(xpath="//button[@class='ant-switch css-ooninv ant-switch-checked']")
	  List<WebElement> toggleSwitchChecked;
	  @FindBy(xpath="//button[Contains(@class,'ant-switch css-ooninv ')]")
	  List<WebElement> allToggleSwitch;
	  
	  
	  
	    
	    @BeforeMethod(dependsOnMethods = "setUp")
	    public void initializePageObjects() {
	        System.out.println("=== Initializing Page Objects for AddProject ===");
	        
	        // Verify driver is not null
	        if (driver == null) {
	            throw new IllegalStateException("WebDriver is null! BaseTest.setUp() did not run properly.");
	        }
	        
	        // Initialize PageFactory
	        PageFactory.initElements(driver, this);
	        
	        // Initialize helper objects
	        goTo = new GotoPage(driver);
	        reusable = new ReusableCode(driver);
	        
	        // Initialize Login page object (not test class)
	        // We'll create a LoginPage helper class below
	        loginPage = new LoginPage();
	        loginPage.driver = this.driver; // Share the same driver instance
	        PageFactory.initElements(driver, loginPage);
	        loginPage.initializeLoginHelper();
	        
	        System.out.println("✓ Page Objects Initialized Successfully");
	    }    
	    
	    public void navigateToProfile() throws InterruptedException {
	    	
	    	 reusable.waitForVisible(settingsMenu).click();
	         Thread.sleep(1000);
	         // Navigate to profile
	         reusable.waitForVisible(profileMenu).click();
	         
	         Assert.assertTrue(driver.getCurrentUrl().contains("/profile"),
	                 "User is not navigated to Profile page");
	    }
	    
	    public void addProject() throws InterruptedException {
	    	
	    	   reusable.scrollIntoView(starButton);
	           reusable.waitForVisible(starButton).click();
	           
	           // Scroll to toggle section (Ant Switch)
	           List<WebElement> toggle = reusable.waitForListElement(allToggleSwitch);
	           List<WebElement> togglesChecked = reusable.waitForListElement(toggleSwitchChecked);
	          
	           if (togglesChecked.size() > 0) {
	               System.out.println("Selecting One More Project");
	               for (WebElement toggleElement : togglesChecked) {
	                   js.executeScript("arguments[0].scrollIntoView({block:'center'});", toggleElement);
	                   toggleElement.click();
	               }
	           }
	           else if (togglesChecked.size() == toggle.size()) {
	               
	           }
	           else {
	        	   
	               System.out.println("Toggle is in 'Off' state. Proceeding to toggle it.");
	           }
	           
	           reusable.waitForVisible(toggleSwitch).click();
//	           WebElement switchElement = wait.until(ExpectedConditions.visibilityOfElementLocated(toggleSwitch));
//	           js.executeScript("arguments[0].scrollIntoView({block:'center'});", switchElement);
	           
//	           // Capture initial state
//	           boolean initialState = switchElement.getAttribute("class").contains("ant-switch-checked");
//	           
//	           // Click toggle
//	           switchElement.click();
//	           
//	           // Assertion: Toggle state changed
//	           wait.until(driver ->
//	                   switchElement.getAttribute("class").contains("ant-switch-checked") != initialState
//	           );
//	           
//	           // Click Save button
//	           By saveButton = By.xpath("//button[contains(@class,'ant-btn') and .//span[text()='Save']]");
//	           WebElement saveBtn = wait.until(ExpectedConditions.elementToBeClickable(saveButton));
//	           js.executeScript("arguments[0].scrollIntoView({block:'center'});", saveBtn);
//	           saveBtn.click();
//	           
//	           // Validate success toast message
//	           By successToast = By.xpath("//div[contains(@class,'ant-message-notice-content')]");
//	           WebElement toast = wait.until(ExpectedConditions.visibilityOfElementLocated(successToast));
//	           Assert.assertTrue(toast.isDisplayed(), "Success message is not displayed");
//	         
	         
	    }

}
