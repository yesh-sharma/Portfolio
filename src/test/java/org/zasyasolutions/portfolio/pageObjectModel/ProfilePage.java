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

public class ProfilePage extends BaseTest {

	private GotoPage goTo;
	public ReusableCode reusable;
	private LoginPage loginPage;

	@FindBy(id = "menu-settings")
	private WebElement settingsMenu;

	@FindBy(xpath = "//span[normalize-space()='Profile']")
	private WebElement profileMenu;

	@FindBy(xpath = "(//div[@class='cursor-pointer'])[1]")
	private WebElement starButton;

	@FindBy(xpath = "(//span[@class='cursor-pointer'])[1]")
	private WebElement testimonailButton;

	@FindBy(xpath = "//div[@class='ant-message-notice-content']")
	WebElement messageToast;

	@FindBy(xpath = "//button[contains(@class,'ant-btn') and .//span[text()='Save']]")
	WebElement saveButton;

	@FindBy(xpath = "//button[@class='ant-switch css-ooninv ant-switch-checked']")
	List<WebElement> toggleSwitchChecked;

	@FindBy(xpath = "//button[contains(@class,'ant-switch')]")
	List<WebElement> allToggleSwitch;

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
		Thread.sleep(2000);
		// Navigate to profile
		reusable.waitForVisible(profileMenu).click();

		Assert.assertTrue(driver.getCurrentUrl().contains("/profile"), "User is not navigated to Profile page");
		Thread.sleep(2000);
	}

	public void addProject() throws InterruptedException {

		Thread.sleep(2000);
		// Scroll to star button and click

		reusable.scrollIntoView(starButton);

		reusable.waitForVisible(starButton).click();

		Thread.sleep(3000);
		// Scroll to toggle section (Ant Switch)
		List<WebElement> allToggles = reusable.waitForListElement(allToggleSwitch);
		List<WebElement> checkedToggles = reusable.waitForListElement(toggleSwitchChecked);

		// STEP 1: Ensure exactly 3 toggles are selected
		if (checkedToggles.size() < 3) {
			for (WebElement toggle : allToggles) {
				if (!toggle.getAttribute("class").contains("ant-switch-checked")) {
					reusable.scrollIntoView(toggle);
					toggle.click();
					if (reusable.waitForListElement(toggleSwitchChecked).size() == 3) {
						break;
					}
				}
			}
		}

		// Refresh checked list after setup
		checkedToggles = reusable.waitForListElement(toggleSwitchChecked);
		Assert.assertEquals(checkedToggles.size(), 3, "Precondition failed: 3 toggles not selected");

		// STEP 2: Try selecting 4th toggle
		WebElement fourthToggle = null;
		for (WebElement toggle : allToggles) {
			if (!toggle.getDomAttribute("aria-checked").equals("true")) {
				fourthToggle = toggle;
				break;
			}
		}

		Assert.assertNotNull(fourthToggle, "No unselected toggle available for validation");

		reusable.scrollIntoView(fourthToggle);
		fourthToggle.click();

		// STEP 3: Assert validation toast
		String actualToast = reusable.waitForVisible(messageToast).getText();
		Assert.assertEquals(actualToast, "You can select up to 3 projects only", "Validation message mismatch");

		// STEP 4: Unselect last selected toggle
		checkedToggles = reusable.waitForListElement(toggleSwitchChecked);
		WebElement lastSelectedToggle = checkedToggles.get(checkedToggles.size() - 1);

		reusable.scrollIntoView(lastSelectedToggle);
		lastSelectedToggle.click();

		// STEP 5: Select previously unselected toggle
		fourthToggle.click();

		// Final assertion (optional but recommended)
		Assert.assertEquals(reusable.waitForListElement(toggleSwitchChecked).size(), 3,
				"Final toggle count should be 3");

//	           Scroll to and click Save button

		reusable.scrollIntoView(saveButton);
		reusable.waitForClickable(saveButton).click();

		// Validate success toast message

		Assert.assertTrue(messageToast.isDisplayed(), "Success message is not displayed");
	}

	public void clickSaveButton() throws InterruptedException {
		reusable.scrollIntoView(saveButton);
		reusable.waitForClickable(saveButton).click();
		Thread.sleep(2000);
		// Validate success toast message

		Assert.assertTrue(messageToast.isDisplayed(), "Success message is not displayed");

	}

	public void addTestimonial() throws InterruptedException {

		// Avoid Thread.sleep where possible
		reusable.scrollIntoView(testimonailButton);
		reusable.waitForVisible(testimonailButton).click();

		List<WebElement> allToggles = reusable.waitForListElement(allToggleSwitch);

		int selectedCount = 0;

		for (WebElement toggle : allToggles) {

			if (selectedCount == 5) {
				break;
			}

			boolean isChecked = toggle.getAttribute("class").contains("ant-switch-checked");

			if (!isChecked) {
				reusable.scrollIntoView(toggle);
				reusable.waitForClickable(toggle).click();
				selectedCount++;
			}
		}
		Thread.sleep(1000);
	}
}
