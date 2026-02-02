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
import org.zasyasolutions.portfolio.utils.ReusableCode;
import org.zasyasolutions.portfolio.utils.RetryAnalyzer;

public class SocialLinks extends BaseTest {

	private LoginPage loginPage;
	private ProfilePage profilePage;
	private CRUD_Page crudPage;
	private FormPage formPage;
	private ReusableCode reusable;

	private final String section = "Social & Links";
	

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

	@Test(description = "Verify user can add social Links link successfully", priority = 1, retryAnalyzer = RetryAnalyzer.class)
	public void addSocialLinksLink() throws InterruptedException {

		loginPage.performLogin();
		profilePage.navigateToProfile();

		WebElement publicationSection = crudPage.sectionParentElement(section);

		reusable.scrollIntoView(publicationSection);

		WebElement editButton = crudPage.openEditModal(section);
		editButton.click();

		Thread.sleep(2000);

		formPage.addSocialLinkData();

		}
}
