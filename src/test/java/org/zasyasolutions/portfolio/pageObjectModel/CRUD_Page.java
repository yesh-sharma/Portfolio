package org.zasyasolutions.portfolio.pageObjectModel;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.zasyasolutions.portfolio.baseTestPackage.BaseTest;
import org.zasyasolutions.portfolio.baseTestPackage.GotoPage;
import org.zasyasolutions.portfolio.utils.ReusableCode;

public class CRUD_Page extends BaseTest {

	private GotoPage goTo;
	private LoginPage loginPage;
	public ReusableCode reusable;

	@FindBy(xpath = "//span[@aria-label='edit']")
	private WebElement editButton;

	By editButtonBy = By.xpath("//span[@aria-label='edit']");

	@FindBy(xpath = "(//span[normalize-space()='Add Experience']")
	private WebElement addButton;

	@FindBy(xpath = "//div[@class='ant-message-notice-content']")
	private WebElement messageToaster;
	
	
	



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
		loginPage = new LoginPage();
		
		
		
		loginPage.driver = this.driver; // Share the same driver instance
		
		
		PageFactory.initElements(driver, loginPage);
		PageFactory.initElements(driver, reusable);
		
		loginPage.initializeLoginHelper();

		System.out.println("✓ Page Objects Initialized Successfully");
	}

	
	public String successNotificationMessage() throws InterruptedException {

		return reusable.waitForVisible(messageToaster).getText();

	}
	
	
	public WebElement openFormModal(String addText) {
		return driver.findElement(By.xpath("//span[normalize-space()='" + addText + "']"));
	}
	
	

	public WebElement updateFormModal() {
		return driver.findElement(By.xpath("(//span[@class='anticon anticon-edit text-lg cursor-pointer'])[1]"));
	}
	
	public WebElement deleteFormModal() {
		return driver.findElement(By.xpath("(//button[@class='ant-btn css-ooninv ant-btn-default filter-input p-2 !rounded-lg min-h-10 border-0'])[1]"));
	}

	public WebElement openEditModal(String sectionName) {

		return driver.findElement(By.xpath(
				"//article[normalize-space()='" + sectionName + "']/parent::*/parent::*//span[@aria-label='edit']"));
	}

	private By By(WebElement editButton2) {
		// TODO Auto-generated method stub
		return null;
	}

	public WebElement sectionParentElement(String sectionName) {
		return driver.findElement(By.xpath("//article[normalize-space()='" + sectionName + "']/../.."));
	}



}
