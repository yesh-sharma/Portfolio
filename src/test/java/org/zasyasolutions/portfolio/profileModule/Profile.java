package org.zasyasolutions.portfolio.profileModule;

import org.openqa.selenium.By;
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
import org.zasyasolutions.portfolio.utils.ReusableCode;
import org.zasyasolutions.portfolio.utils.RetryAnalyzer;

public class Profile extends BaseTest {

	private LoginPage loginPage;
	private ProfilePage profilePage;
	private CRUD_Page crudPage;
	private FormPage formPage;
	private ReusableCode reusable;


	@BeforeMethod(dependsOnMethods = "setUp")
	public void initialize() {

		loginPage = new LoginPage();
		profilePage = new ProfilePage();
		crudPage = new CRUD_Page();
		formPage = new FormPage();
		reusable = new ReusableCode(driver);

		loginPage.driver = driver;
		profilePage.driver = driver;
		crudPage.driver = driver;
		formPage.driver = driver;

		profilePage.reusable = reusable;
		crudPage.reusable = reusable;
		formPage.reusable = reusable;

		PageFactory.initElements(driver, loginPage);
		PageFactory.initElements(driver, profilePage);
		PageFactory.initElements(driver, crudPage);
		PageFactory.initElements(driver, formPage);

		loginPage.initializeLoginHelper();

		System.out.println("✓ Page Objects Initialized Successfully for Login Test");

	}
	


	@Test(description = "Verify user can add PROFILE DATA link successfully", priority = 1, retryAnalyzer = RetryAnalyzer.class)
 	public void updateProfile() throws InterruptedException {

		loginPage.performLogin();
		profilePage.navigateToProfile();

		WebElement publicationSection = reusable.waitForVisibleBy(By.xpath("//div[@class='bg-white rounded-2xl shadow p-6 flex flex-col md:flex-row items-center justify-between gap-4']"));

		reusable.scrollIntoView(publicationSection);

		WebElement editButton = publicationSection.findElement(By.xpath("//span[@aria-label='edit']"));
		editButton.click();

		Thread.sleep(2000);

		formPage.addProfileData();
		Assert.assertEquals(crudPage.successNotificationMessage(), "Profile updated successfully",
				"Social Link URL not found in the section after adding.");

	}



}
