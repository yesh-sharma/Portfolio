package org.zasyasolutions.portfolio.pageObjectModel;

import java.time.Duration;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.zasyasolutions.portfolio.baseTestPackage.BaseTest;
import org.zasyasolutions.portfolio.utils.ReusableCode;

public class FormPage extends BaseTest {

	public ReusableCode reusable;
	public CRUD_Page crudPage;
	public LoginPage loginPage;

	@FindBy(id = "name")
	private WebElement name;

	@FindBy(id = "employmentType")
	private WebElement employmentType;

	@FindBy(id = "companyName")
	private WebElement companyName;
	@FindBy(id = "organisation")
	private WebElement organisation;

	@FindBy(id = "startDate")
	private WebElement startDate;

	@FindBy(id = "issueDate")
	private WebElement issueDate;

	@FindBy(id = "publicationDate")
	private WebElement publicationDate;

	@FindBy(id = "certificateId")
	private WebElement certificateId;

	@FindBy(id = "instituteName")
	private WebElement instituteName;

	@FindBy(id = "qualification")
	private WebElement qualification;

	@FindBy(id = "endDate")
	private WebElement endDate;

	@FindBy(id = "expiredDate")
	private WebElement expiredDate;

	@FindBy(xpath = "//span[normalize-space()='I currently work here']")
	private WebElement currentlyWorkingCheckbox;

	@FindBy(xpath = "//span[normalize-space()='This credential does not expire']")
	private WebElement credentialDoesNotExpireCheckbox;

	@FindBy(id = "summary")
	private WebElement summary;

	@FindBy(id = "description")
	private WebElement description;

	@FindBy(xpath = "//button[contains(@class,'ant-btn') and .//span[text()='Save']]")
	private WebElement saveButton;

	@FindBy(id = "skills")
	private WebElement skills;
	@FindBy(xpath = "(//span[contains(normalize-space(),'Create')])[1]")
	private WebElement createButton;
	@FindBy(xpath = "//div[@class='ant-select-selection-overflow-item']")
	private List<WebElement> skillList;
	@FindBy(xpath = "(//div[@class='ant-select-selection-overflow'])[1]")
	private WebElement skillField;

	@FindBy(xpath = "//div[@class='ant-form-item-explain-error']")
	private WebElement errorValidation;

	@FindBy(xpath = "//input[contains(@placeholder,'Example')]")
	private List<WebElement> linkInputTitle;

	@FindBy(xpath = "//input[contains(@placeholder,'www.example.com')]")
	private List<WebElement> linkInput;

	@FindBy(xpath = "//div[contains(@class,'border rounded p-4')]")
	private List<WebElement> linkContainer;

	@FindBy(id = "languages")
	private WebElement languages;

	@FindBy(xpath = "//span[normalize-space()='Add Link']")
	private WebElement addLinkButton;

	@FindBy(id = "designation")
	private WebElement designation;

	By fileInput = By.xpath("//input[@type='file']");

	@FindBy(xpath = "//div[@class='ant-message-notice-content']")
	WebElement messageToast;

	public Object socialLinkURL;

	public FormPage() {
		// Empty constructor
	}

	public void initializePageObjects() {
		if (driver == null) {
			throw new IllegalStateException("Driver not initialized");
		}

		this.reusable = new ReusableCode(driver);
		crudPage = new CRUD_Page();
		loginPage = new LoginPage();

		loginPage.driver = this.driver;
		crudPage.driver = this.driver;
		crudPage.reusable = this.reusable;

		PageFactory.initElements(driver, this);
		PageFactory.initElements(driver, loginPage);
		PageFactory.initElements(driver, crudPage);

		loginPage.initializeLoginHelper();
	}

	public void clickSaveButton() throws InterruptedException {
		reusable.scrollIntoView(saveButton);
		reusable.waitForClickable(saveButton).click();
		Thread.sleep(1200);
		// Validate success toast message

		Assert.assertTrue(messageToast.isDisplayed(), "Success message is not displayed");

	}

	public void selectRandomEndDate(int year, String month) throws InterruptedException {

		By activePanelBy = By
				.xpath("(//div[contains(@class,'ant-picker-panel') and not(contains(@style,'display: none'))])[4]");

		WebElement panel = reusable.waitForVisibleBy(activePanelBy);

		// 1️⃣ Click year button
		WebElement yearBtn = panel.findElement(By.xpath(".//button[contains(@class,'ant-picker-year-btn')]"));
		yearBtn.click();

		// 2️⃣ WAIT for year cells to be clickable (not just visible)
		By yearCellBy = By.xpath("//div[contains(@class,'ant-picker-panel') and not(contains(@style,'display: none'))]"
				+ "//div[text()='" + year + "']");

		WebElement yearCell = reusable.waitForClickableBy(yearCellBy);

		// Extra safety: ensure non-zero size
		reusable.waitUntil(driver -> yearCell.getSize().getHeight() > 0 && yearCell.getSize().getWidth() > 0);

		yearCell.click();

		// 3️⃣ Select month (clickable, scoped)
		By monthBy = By.xpath("//div[contains(@class,'ant-picker-panel') and not(contains(@style,'display: none'))]"
				+ "//div[normalize-space()='" + month + "']");

		WebElement monthEl = reusable.waitForClickableBy(monthBy);
		monthEl.click();

		// 4️⃣ Select date from active panel
		List<WebElement> dates = panel.findElements(
				By.xpath(".//td[contains(@class,'ant-picker-cell-in-view') and not(contains(@class,'disabled'))]"));

		if (dates.isEmpty()) {
			throw new RuntimeException("No selectable dates available");
		}

		WebElement randomDate = dates.get(new Random().nextInt(dates.size()));
		System.out.println(">> Selected Date: " + randomDate.getText());
		Thread.sleep(1500);

		reusable.waitUntil(driver -> randomDate.getSize().getHeight() > 0 && randomDate.getSize().getWidth() > 0);

		randomDate.click();
	}

	private void waitForInputToBeActive(WebElement input) {
		reusable.waitUntil(driver -> input.equals(driver.switchTo().activeElement()));
	}

	public void selectRandomDate(int year, String month) throws InterruptedException {

		By activePanelBy = By
				.xpath("//div[contains(@class,'ant-picker-panel') and not(contains(@style,'display: none'))]");

		WebElement panel = reusable.waitForVisibleBy(activePanelBy);

		// 1️⃣ Click year button
		WebElement yearBtn = panel.findElement(By.xpath(".//button[contains(@class,'ant-picker-year-btn')]"));
		yearBtn.click();

		// 2️⃣ WAIT for year cells to be clickable (not just visible)
		By yearCellBy = By.xpath("//div[contains(@class,'ant-picker-panel') and not(contains(@style,'display: none'))]"
				+ "//div[text()='" + year + "']");

		WebElement yearCell = reusable.waitForClickableBy(yearCellBy);

		// Extra safety: ensure non-zero size
		reusable.waitUntil(driver -> yearCell.getSize().getHeight() > 0 && yearCell.getSize().getWidth() > 0);

		yearCell.click();

		// 3️⃣ Select month (clickable, scoped)
		By monthBy = By.xpath("//div[contains(@class,'ant-picker-panel') and not(contains(@style,'display: none'))]"
				+ "//div[normalize-space()='" + month + "']");

		WebElement monthEl = reusable.waitForClickableBy(monthBy);
		monthEl.click();

		// 4️⃣ Select date from active panel
		List<WebElement> dates = panel.findElements(
				By.xpath(".//td[contains(@class,'ant-picker-cell-in-view') and not(contains(@class,'disabled'))]"));

		if (dates.isEmpty()) {
			throw new RuntimeException("No selectable dates available");
		}

		WebElement randomDate = dates.get(new Random().nextInt(dates.size()));
		System.out.println(">> Selected Date: " + randomDate.getText());
		Thread.sleep(1500);

		reusable.waitUntil(driver -> randomDate.getSize().getHeight() > 0 && randomDate.getSize().getWidth() > 0);

		randomDate.click();
	}

	public void addJobData() throws InterruptedException {

		reusable.waitForVisible(name).sendKeys("TestUser");
		reusable.waitForVisible(employmentType).sendKeys("EmployeeTypeA");
		reusable.waitForVisible(companyName).sendKeys("CompanyA");

		// Start Date
		reusable.waitForVisible(startDate).click();
		waitForInputToBeActive(startDate);
		selectRandomDate(2025, "Jul");

		Thread.sleep(2000);

//		// End Date
//        reusable.waitForClickable(endDate).click();
//        waitForInputToBeActive(endDate);
//        selectRandomDate(2026, "Jul");

		Thread.sleep(2000);
		reusable.waitForVisible(currentlyWorkingCheckbox).click();
		reusable.waitForVisible(summary).sendKeys("This is job data for the Employee A");
		clickSaveButton();
	}

	public void updateJobData() throws InterruptedException {
		WebElement summaryBox = reusable.waitForVisible(summary);
		summaryBox.clear();
		summaryBox.sendKeys(" Updated job data for the Employee A");
		clickSaveButton();
	}

	public void addCertificateData() throws InterruptedException {
		// TODO Auto-generated method stub
		reusable.waitForVisible(name).sendKeys("TestUser");

		reusable.waitForVisible(organisation).sendKeys("OrganizationA");

		// Start Date
		reusable.waitForVisible(issueDate).click();
		waitForInputToBeActive(issueDate);
		selectRandomDate(2025, "Jul");

		Thread.sleep(2000);
//		// End Date
//      reusable.waitForClickable(expiredDate).click();
//      waitForInputToBeActive(expiredDate);
//      selectRandomDate(2026, "Jul");

		Thread.sleep(2000);
		reusable.waitForVisible(credentialDoesNotExpireCheckbox).click();
		reusable.waitForVisible(certificateId).sendKeys("This is Certificate Id : 12345");
		clickSaveButton();
	}

	public void updateCertificateData() throws InterruptedException {
		WebElement summaryBox = reusable.waitForVisible(certificateId);
		summaryBox.clear();
		summaryBox.sendKeys(" Updated certificate ID for the Employee A");
		clickSaveButton();
	}

	public void addAwardData() throws InterruptedException {
		// TODO Auto-generated method stub
		reusable.waitForVisible(name).sendKeys("TestUser");

		reusable.waitForVisible(organisation).sendKeys("OrganizationA");

		// Start Date
		reusable.waitForVisible(issueDate).click();
		waitForInputToBeActive(issueDate);
		selectRandomDate(2025, "Jul");

		Thread.sleep(2000);
//		// End Date
//      reusable.waitForClickable(expiredDate).click();
//      waitForInputToBeActive(expiredDate);
//      selectRandomDate(2026, "Jul");

		Thread.sleep(2000);
		reusable.waitForVisible(credentialDoesNotExpireCheckbox).click();
		reusable.waitForVisible(description).sendKeys("This is Award description for the Employee A");
		clickSaveButton();
	}

	public void updateAwardData() throws InterruptedException {
		WebElement summaryBox = reusable.waitForVisible(description);
		summaryBox.clear();
		summaryBox.sendKeys(" Updated  Award description for the Employee A");
		clickSaveButton();
	}

	public void addEducationData() throws InterruptedException {
		// TODO Auto-generated method stub
		reusable.waitForVisible(instituteName).sendKeys("Amity University");

		reusable.waitForVisible(qualification).sendKeys("B.Tech in Computer Science");

		// Start Date
		reusable.waitForVisible(startDate).click();
		waitForInputToBeActive(startDate);
		selectRandomDate(2021, "Oct");

		Thread.sleep(2000);
		// End Date

		reusable.waitForClickable(endDate).sendKeys("2025-05-11");
		reusable.waitForClickable(endDate).sendKeys(Keys.ENTER);
//	  waitForInputToBeActive(endDate);
//	  selectRandomDate(2025, "May");
		clickSaveButton();
	}

	public void updateEducationData() throws InterruptedException {
		WebElement summaryBox = reusable.waitForVisible(instituteName);
		summaryBox.clear();
		summaryBox.sendKeys(" UIIT Shimnla Updated");
		clickSaveButton();
	}

	public void addPublicationData() throws InterruptedException {
		// TODO Auto-generated method stub
		reusable.waitForVisible(name).sendKeys("Research Paper on Faster Web Driver");

		// Start Date
		reusable.waitForVisible(publicationDate).click();
		waitForInputToBeActive(publicationDate);
		selectRandomDate(2025, "Oct");

		Thread.sleep(2000);
		reusable.waitForVisible(description)
				.sendKeys("This is publication description for Faster Web Driver and threir optimal Use");

		clickSaveButton();
	}

	public void updatePublicationData() throws InterruptedException {
		WebElement summaryBox = reusable.waitForVisible(description);
		summaryBox.clear();
		summaryBox.sendKeys(
				" How a Web Driver can wait auomaticaly and search for the element with the optima efficiency");
		clickSaveButton();
	}

	public WebElement lastSkill = null;
	private String skillName1 = null; // New tag created (ADD mode)
	private String skillName2 = null; // Existing tag selected (ADD mode)
	private String skillName3 = null; // New tag created (UPDATE mode)
	private String skillName4 = null; // Existing tag selected (UPDATE mode)
	private int lastCondition = 0; // Track which condition was triggered (1, 2, or 3)

	public void selectingTag(String skill, boolean isUpdateMode) throws InterruptedException {
	    String skillName = skill;
	    
	    // Reset lastSkill and condition at the beginning
	    lastSkill = null;
	    lastCondition = 0;
	    
	    WebElement fieldArea = reusable.waitForVisible(skillField);
	    fieldArea.click();
	    Thread.sleep(500);
	    
	    WebElement languageArea = null;
	    WebElement skillArea = null;
	    
	    try {
	        skillArea = reusable.waitForVisibleTime(skills);
	    } catch (Exception e) {}
	    
	    try {
	        languageArea = reusable.waitForVisibleTime(languages);
	    } catch (Exception e) {}
	    
	    if (skillArea != null && skillArea.isDisplayed()) {
	        skillArea.sendKeys(skillName);
	    } else if (languageArea != null && languageArea.isDisplayed()) {
	        languageArea.sendKeys(skillName);
	    } else {
	        throw new RuntimeException("Neither skillArea nor languageArea is visible");
	    }
	    
	    Thread.sleep(1000);
	    
	    // Check if exact match exists in dropdown
	    WebElement exactMatchSkill = null;
	    try {
	        exactMatchSkill = driver.findElement(By.xpath("(//div[normalize-space()='" + skillName + "'])[5]"));
	    } catch (Exception e) {}
	    
	    // Check if case-insensitive match exists (but not exact)
	    SkillMatch caseInsensitiveMatch = findCaseInsensitiveMatchInDropdown(skillName);
	    
	    // Check if Create button is visible
	    boolean createButtonVisible = false;
	    try {
	        WebElement createBtn = reusable.waitForVisibleTime(createButton);
	        createButtonVisible = createBtn.isDisplayed();
	    } catch (Exception e) {}
	    
	    // **CONDITION 1: New tag (no match at all)**
	    if (exactMatchSkill == null && caseInsensitiveMatch == null && createButtonVisible) {
	        System.out.println(">> CONDITION 1: Skill is new. Adding to the list.");
	        lastCondition = 1;
	        
	        reusable.waitForVisible(createButton).click();
	        Thread.sleep(800);
	        
	        List<WebElement> skillsList = reusable.waitForListElement(skillList);
	        lastSkill = skillsList.get(skillsList.size() - 1);
	        String addedSkillName = lastSkill.getText();
	        
	        if (isUpdateMode) {
	            skillName3 = addedSkillName;
	            System.out.println(">> Added Skill (Update Mode): " + skillName3);
	        } else {
	            skillName1 = addedSkillName;
	            System.out.println(">> Added Skill (Add Mode): " + skillName1);
	        }
	        
	        reusable.waitForVisible(skillField).click();
	        Thread.sleep(500);
	    }
	    // **CONDITION 2: Exact match exists**
	    else if (exactMatchSkill != null && exactMatchSkill.isDisplayed() && !createButtonVisible) {
	        System.out.println(">> CONDITION 2: Exact match found for skill.");
	        lastCondition = 2;
	        
	        // Click on the exact match skill
	        WebElement listedSkill = reusable.waitForVisibleBy(By.xpath("(//div[normalize-space()='" + skillName + "'])[1]"));
	        String selectedSkillName = listedSkill.getText();
	        listedSkill.click();
	        Thread.sleep(800);
	        
	        // Get the newly added skill from the selected tags area
	        List<WebElement> skillsList = reusable.waitForListElement(skillList);
	        lastSkill = skillsList.get(skillsList.size() - 1);
	        
	        if (isUpdateMode) {
	            skillName4 = selectedSkillName;
	            System.out.println(">> Selected Existing Skill (Update Mode): " + skillName4);
	        } else {
	            skillName2 = selectedSkillName;
	            System.out.println(">> Selected Existing Skill (Add Mode): " + skillName2);
	        }
	        
	        Thread.sleep(500);
	    }
	    // **CONDITION 3: Case-insensitive match exists (but not exact)**
	    else if (caseInsensitiveMatch != null && createButtonVisible) {
	        System.out.println(">> CONDITION 3: Case-insensitive match found. Handling duplicate.");
	        lastCondition = 3;

	        String existingSkillName = caseInsensitiveMatch.actualText;
	        System.out.println(">> Existing skill found: " + existingSkillName);

	        // Click create button to add the new variant
	        reusable.waitForVisible(createButton).click();
	        Thread.sleep(800);

	        // Get the newly created skill
	        List<WebElement> skillsList = reusable.waitForListElement(skillList);
	        WebElement newlyCreatedSkill = skillsList.get(skillsList.size() - 1);
	        String tempSkillName = newlyCreatedSkill.getText();
	        System.out.println(">> Temporary skill created: " + tempSkillName);

	        // Remove the newly created duplicate
	        newlyCreatedSkill.findElement(By.xpath(".//span[@aria-label='close']")).click();
	        Thread.sleep(500);
	        System.out.println(">> Removed temporary duplicate skill.");

	        // Reopen dropdown and find the existing case-insensitive match
	        reusable.waitForVisible(skillField).click();
	        Thread.sleep(500);
	        
	        SkillMatch matchResult = findCaseInsensitiveMatchInDropdown(skillName);

	        if (matchResult != null) {
	            existingSkillName = matchResult.actualText;
	            matchResult.element.click();
	            Thread.sleep(800);

	            skillsList = reusable.waitForListElement(skillList);
	            lastSkill = skillsList.get(skillsList.size() - 1);

	            if (isUpdateMode) {
	                skillName4 = existingSkillName;
	                System.out.println(">> Selected Existing Skill (Update Mode - Case Mismatch): " + skillName4);
	            } else {
	                skillName2 = existingSkillName;
	                System.out.println(">> Selected Existing Skill (Add Mode - Case Mismatch): " + skillName2);
	            }

	            reusable.waitForVisible(skillField).click();
	            Thread.sleep(500);
	        } else {
	            throw new RuntimeException("ERROR: Could not find existing skill in dropdown after reopening");
	        }
	    }
	}

	// Helper method to find case-insensitive match in dropdown
	private SkillMatch findCaseInsensitiveMatchInDropdown(String skillName) {
	    String upperCaseSkillName = skillName.toUpperCase();
	    
	    try {
	        List<WebElement> dropdownOptions = driver.findElements(
	            By.xpath("//div[contains(@class,'ant-select-item-option-content')]")
	        );

	        if (dropdownOptions.isEmpty()) {
	            dropdownOptions = driver.findElements(
	                By.xpath("//div[contains(@class,'ant-select-dropdown')]//div[normalize-space()]")
	            );
	        }

	        for (WebElement option : dropdownOptions) {
	            String optionText = option.getText().trim();
	            
	            if (optionText.isEmpty()) {
	                continue;
	            }
	            
	            String upperCaseOptionText = optionText.toUpperCase();

	            if (upperCaseOptionText.equals(upperCaseSkillName) && !optionText.equals(skillName)) {
	                System.out.println(">> Found case-insensitive match: '" + optionText + "' for input: '" + skillName + "'");
	                return new SkillMatch(option, optionText);
	            }
	        }
	    } catch (Exception e) {
	        System.out.println(">> Error finding case-insensitive match: " + e.getMessage());
	    }
	    
	    return null;
	}

	public void updateTagsData(String skill) throws InterruptedException {
	    selectingTag(skill, true); // isUpdateMode = true
	    Thread.sleep(700);
	    
	    String skillToClick = null;
	    
	    // Determine which skill name to use based on the condition
	    if (lastCondition == 1) {
	        // New skill was created in update mode
	        System.out.println(">> CONDITION 1 (Update): Keeping newly created skill: " + skillName3);
	        skillToClick = skillName3;
	    } else if (lastCondition == 2 || lastCondition == 3) {
	        // Existing skill was selected in update mode (exact match or case-insensitive match)
	        System.out.println(">> CONDITION 2/3 (Update): Keeping existing skill: " + skillName4);
	        skillToClick = skillName4;
	    }
	    
	    if (skillToClick == null) {
	        throw new RuntimeException("ERROR: No skill was set during update mode. LastCondition: " + lastCondition);
	    }
	    
	    // Remove the last selected skill
	    if (lastSkill != null) {
	        lastSkill.findElement(By.xpath(".//span[@aria-label='close']")).click();
	        Thread.sleep(500);
	    }
	    
	    // Click on the skill in the main list
	    reusable.waitForVisibleBy(By.xpath("(//span[normalize-space()='" + skillToClick + "'])[2]")).click();
	    Thread.sleep(500);
	    
	    clickSaveButton();
	}

	public void addTagsData(String skill) throws InterruptedException {
	    selectingTag(skill, false); // isUpdateMode = false
	    Thread.sleep(700);
	    clickSaveButton();
	}

	public void deleteTagsData() throws InterruptedException {
	    // Delete all saved tags: skillName1, skillName2, skillName3, skillName4
	    
	    if (skillName1 != null) {
	        removeTagIfExists(skillName1);
	    }
	    
	    if (skillName2 != null) {
	        removeTagIfExists(skillName2);
	    }
	    
	    if (skillName3 != null) {
	        removeTagIfExists(skillName3);
	    }
	    
	    if (skillName4 != null) {
	        removeTagIfExists(skillName4);
	    }
	    
	    reusable.waitForVisible(saveButton).click();
	    Thread.sleep(800);
	    
	    // Handle error message if appears
	    WebElement errorMessage = null;
	    try {
	        errorMessage = reusable.waitForVisibleTime(errorValidation);
	        System.out.println("++ Error Message: " + errorMessage.getText());
	        driver.findElement(By.xpath("(//span[@aria-label='close'])[1]")).click();
	    } catch (Exception e) {}
	    
	    // Reset all skill names after deletion
	    skillName1 = null;
	    skillName2 = null;
	    skillName3 = null;
	    skillName4 = null;
	}

	// Helper method to remove a tag if it exists
	private void removeTagIfExists(String skillName) throws InterruptedException {
	    WebElement skillTag = findSelectedSkillCaseInsensitive(skillName);
	    if (skillTag != null) {
	        System.out.println(">> Removing tag: " + skillName);
	        skillTag.findElement(By.xpath(".//span[@aria-label='close']")).click();
	        Thread.sleep(800);
	        reusable.waitForInvisibleBy(By.xpath("//span[contains(@class,'ant-select-selection-item-content')][normalize-space()='" + skillName + "']"));
	    } else {
	        System.out.println(">> Tag not found to remove: " + skillName);
	    }
	}

	// Helper method for case-insensitive search in selected skills
	private WebElement findSelectedSkillCaseInsensitive(String skillName) {
	    try {
	        List<WebElement> selectedSkills = driver.findElements(
	            By.xpath("//span[@class='ant-select-selection-item-content']/..")
	        );
	        for (WebElement skill : selectedSkills) {
	            String skillText = skill.findElement(
	                By.xpath(".//span[@class='ant-select-selection-item-content']")
	            ).getText();
	            if (skillText.equalsIgnoreCase(skillName)) {
	                return skill;
	            }
	        }
	    } catch (Exception e) {}
	    return null;
	}

	// Helper class for storing skill match results
	private static class SkillMatch {
	    WebElement element;
	    String actualText;
	    
	    SkillMatch(WebElement element, String actualText) {
	        this.element = element;
	        this.actualText = actualText;
	    }
	}

	public void addSocialLinkData() throws InterruptedException {

		System.out.println(">> Adding Publication Link Data 1");

		List<WebElement> linksContainer = reusable.waitForListElement(linkContainer);

		System.out.println(">> Adding Publication Link Data 2");
		WebElement lastLinkContainer = linksContainer.get(linksContainer.size() - 1);
		System.out.println(">> Adding Publication Link Data 3");
		reusable.scrollIntoView(lastLinkContainer);
		System.out.println(">> Adding Publication Link Data 4");
		WebElement linkField = lastLinkContainer
				.findElement(By.xpath("//input[contains(@placeholder,'www.example.com')]"));
		System.out.println(">> Adding Publication Link Data 4.5");
		String textInLinkField = linkField.getAttribute("value");
		System.out.println(">> Adding Publication Link Data 5");

		System.out.println(">> Text in link field: '" + textInLinkField + "'");

		if (textInLinkField.equals("")) {

			System.out.println(">> Adding Social Link Data into the fields available");
		} else {
			System.out.println(">> Link field is NOT empty, proceeding to fill it.");
			WebElement validationText = null;
			try {
				validationText = driver.findElement(By.xpath("//p[@class='text-red-500 my-2']"));
				Assert.assertEquals(validationText.getText(), "You can only add up to 5 links.");
			} catch (Exception e) {
				System.out.println(">> No validation text found, proceeding to add new link fields.");
			}
			if (validationText != null && validationText.isDisplayed()) {
				System.out.println(">> Maximum link limit reached, cannot add more links.");
				lastLinkContainer.findElement(By.xpath(".//span[normalize-space()='Remove']")).click();
				Thread.sleep(500);
				WebElement addLinkBtn = reusable.waitForVisible(addLinkButton);
				addLinkBtn.click();
				Thread.sleep(1500);

			} else {
				WebElement addLinkBtn = reusable.waitForVisible(addLinkButton);
				addLinkBtn.click();
				Thread.sleep(1500);
			}
		}

		List<WebElement> linkTitleInput = reusable.waitForListElement(linkInputTitle);

		linkTitleInput.get(linkTitleInput.size() - 1).sendKeys("LinkedIn");

		System.out.println(">> Adding Publication Link Data 7");

		List<WebElement> input = reusable.waitForListElement(linkInput);
		input.get(input.size() - 1).sendKeys("https://www.linkedin.com");
		System.out.println(">> Adding Publication Link Data 8");
		Thread.sleep(1000);

		clickSaveButton();
		System.out.println(">> Adding Publication Link Data 9");

	}

	public void updateSocialLinkData() throws InterruptedException {

		System.out.println(">> Updating Social Link Data");

		JavascriptExecutor js = (JavascriptExecutor) driver;

		List<WebElement> linkTitleInput = reusable.waitForListElement(linkInputTitle);

		WebElement lastTitleInput = linkTitleInput.get(0);
		lastTitleInput.click();
		js.executeScript("arguments[0].select();", lastTitleInput);

		lastTitleInput.sendKeys("Updated LinkedIn");

		List<WebElement> input = reusable.waitForListElement(linkInput);
		WebElement lastLinkInput = input.get(0);
		js.executeScript("arguments[0].select();", lastLinkInput);

		lastLinkInput.sendKeys("www.linkedin.com");
		Thread.sleep(1000);

		clickSaveButton();
		System.out.println(">> Updated Social Link Data");

	}

	public void deleteSocialLinkData() throws InterruptedException {
		System.out.println(">> Deleting Social Link Data");
		List<WebElement> linksContainer = reusable.waitForListElement(linkContainer);
		WebElement lastLinkContainer = linksContainer.get(0);
		lastLinkContainer.findElement(By.xpath(".//span[normalize-space()='Remove']")).click();
		System.out.println(">> Deleted Social Link Data");
		clickSaveButton();
	}

	public void addProfileData() throws InterruptedException {
		// TODO Auto-generated method stub
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String filePath = System.getProperty("user.dir") + "/src/test/resource/files/profile_picture.png";
		uploadProfilePicture(filePath);
		WebElement designationField = reusable.waitForVisible(designation);
		js.executeScript("arguments[0].select();", designationField);
		designationField.sendKeys("Mobile App Developer");
		WebElement ProfilePictureElem = reusable.waitForPresence(By.xpath("//img[@alt='Profile Picture']"));
		String srcProfilePicture = ProfilePictureElem.getAttribute("src");
		clickSaveButton();
		WebElement userAvatar = reusable.waitForPresence(By.xpath("//img[@alt='user avatar']"));

		reusable.waitForInvisibleBy(By.xpath("(//div[@class='ant-message-notice-content'])[1]"));

		Assert.assertEquals(userAvatar.getAttribute("src"), srcProfilePicture,
				"Profile picture not updated correctly.");
	}

	public void uploadProfilePicture(String filePath) throws InterruptedException {
		try {
			WebElement uploadInput = reusable.waitForPresence(fileInput);
			uploadInput.sendKeys(filePath);

			System.out.println("[DEBUG] File upload triggered...");

			reusable.waitForInvisibleBy(By.xpath("(//div[normalize-space()='Uploading...'])[3]"));

			System.out.println("[DEBUG] Upload completed and SVG rendered.");

		} catch (Exception e) {
			System.err.println("[ERROR] Upload failed: " + e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}

	public void addSummaryData(String string) throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement summaryBox = reusable.waitForVisible(description);
		js.executeScript("arguments[0].select();", summaryBox);
		summaryBox.sendKeys(string);
		enhanceWithAI();
		Thread.sleep(1000);
		clickSaveButton();

	}

	public void enhanceWithAI() throws InterruptedException {

		initializePageObjects();
		WebElement enhanceButton = reusable.waitForVisibleBy(By.xpath("//button[normalize-space()='Enhance with AI']"));
		enhanceButton.click();

		// Wait for the enhancement process to complete

		WebElement generateButton = reusable
				.waitForClickableBy(By.xpath("//button[normalize-space()='Use Generated Variation']"));
		WebElement afterPanel = reusable.waitForVisibleBy(By.xpath("//div[@class='text-white'] /p"));
		String afterText = afterPanel.getText();
		System.out.println(">> AI Generated Text: " + afterText);
		generateButton.click();
		Assert.assertEquals(afterText, description.getAttribute("value"));
		Assert.assertEquals(crudPage.successNotificationMessage(), "Generated description successfully",
				"Enhanced text not applied correctly.");
		reusable.waitForInvisibleBy(By.xpath("(//div[@class='ant-message-notice-content'])[1]"));
	}

	public void emptySummaryData() throws InterruptedException {
		// TODO Auto-generated method stub
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement summaryBox = reusable.waitForVisible(description);
		js.executeScript("arguments[0].select();", summaryBox);
		Thread.sleep(1000);
		summaryBox.sendKeys(Keys.BACK_SPACE);

		System.out.println(">> Clearing description field : " + summaryBox.getAttribute("value"));
		Assert.assertEquals(summaryBox.getAttribute("value"), "");
		reusable.waitForVisible(saveButton);
		emptyFieldValidation();

	}

	public void emptyFieldValidation() throws InterruptedException {
		// TODO Auto-generated method stub
		Thread.sleep(1000);
		WebElement errorMessage = null;
		try {
			errorMessage = reusable.waitForVisibleTime(errorValidation);
//	        Assert.assertEquals(errorMessage.getText(),"Please enter description");
		} catch (Exception e) {
		}
		if (errorMessage != null && errorMessage.isDisplayed()) {
			System.out.println("++ Error Message : " + errorMessage.getText());
			driver.findElement(By.xpath("(//span[@aria-label='close'])[1]")).click();

		} else {
			System.out.println(">> No error message displayed.");
			throw new RuntimeException("Expected error message not displayed for empty description.");

		}
	}

}