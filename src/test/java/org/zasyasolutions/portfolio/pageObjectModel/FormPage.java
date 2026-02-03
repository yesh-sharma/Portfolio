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
	@FindBy(xpath="(//span[contains(normalize-space(),'Create')])[1]")
	private WebElement createButton;
	@FindBy(xpath="//div[@class='ant-select-selection-overflow-item']")
	private List<WebElement> skillList;
	@FindBy(xpath="(//div[@class='ant-select-selection-overflow'])[1]")
	private WebElement skillField;

    @FindBy(xpath = "//input[contains(@placeholder,'Example')]")
    private List<WebElement> linkInputTitle;
    
    @FindBy(xpath = "//input[contains(@placeholder,'www.example.com')]")
    private List<WebElement> linkInput;

    @FindBy(xpath = "//div[contains(@class,'border rounded p-4')]")
    private List<WebElement>  linkContainer;

	@FindBy(id = "languages")
	private WebElement languages;
	
	@FindBy(xpath= "//span[normalize-space()='Add Link']")
	private WebElement addLinkButton;
	
	@FindBy(id="designation")
	private WebElement designation;
	
	@FindBy(id="fileInput")
	private WebElement fileInput;

	public Object socialLinkURL;

	public static  String skillName1;
	public static String skillName2;
	
	public void initializePageObjects() {
		if (driver == null) {
			throw new IllegalStateException("Driver not initialized");
		}

		reusable = new ReusableCode(driver);
		PageFactory.initElements(driver, this);
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
		reusable.waitForVisible(saveButton).click();
	}

	public void updateJobData() {
		WebElement summaryBox = reusable.waitForVisible(summary);
		summaryBox.clear();
		summaryBox.sendKeys(" Updated job data for the Employee A");
		reusable.waitForVisible(saveButton).click();
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
		reusable.waitForVisible(saveButton).click();
	}
	
	public void updateCertificateData() {
		WebElement summaryBox = reusable.waitForVisible(certificateId);
		summaryBox.clear();
		summaryBox.sendKeys(" Updated certificate ID for the Employee A");
		reusable.waitForVisible(saveButton).click();
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
		reusable.waitForVisible(saveButton).click();
	}
	
	public void updateAwardData() {
		WebElement summaryBox = reusable.waitForVisible(description);
		summaryBox.clear();
		summaryBox.sendKeys(" Updated  Award description for the Employee A");
		reusable.waitForVisible(saveButton).click();
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
		reusable.waitForVisible(saveButton).click();
	}
	
	
	
	public void updateEducationData() {
		WebElement summaryBox = reusable.waitForVisible(instituteName);
		summaryBox.clear();
		summaryBox.sendKeys(" UIIT Shimnla Updated");
		reusable.waitForVisible(saveButton).click();
	}
	
	
	public void addPublicationData() throws InterruptedException {
		// TODO Auto-generated method stub
		reusable.waitForVisible(name).sendKeys("Research Paper on Faster Web Driver");

		

		// Start Date
		reusable.waitForVisible(publicationDate).click();
		waitForInputToBeActive(publicationDate);
		selectRandomDate(2025, "Oct");

		Thread.sleep(2000);
		reusable.waitForVisible(description).sendKeys("This is publication description for Faster Web Driver and threir optimal Use");
		
		reusable.waitForVisible(saveButton).click();
	}
	
	
	
	public void updatePublicationData() {
		WebElement summaryBox = reusable.waitForVisible(description);
		summaryBox.clear();
		summaryBox.sendKeys(" How a Web Driver can wait auomaticaly and search for the element with the optima efficiency");
		reusable.waitForVisible(saveButton).click();
	}
	

	public void addTagsData(String skill) throws InterruptedException {
	    String skillName = skill;
	    String skillNameUpper = skillName.toUpperCase();
	    
	    WebElement fieldArea = reusable.waitForVisible(skillField);
	    fieldArea.click();
	    
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
	    
	    WebElement addedSkill = null;
	    String addedSkillUpper = null;
	    
	    // Search for skill case-insensitively in dropdown
	    try {
	       
	    	addedSkill = driver.findElement(By.xpath("(//div[normalize-space()='"+skillName+"'])[5]")); 
	    	addedSkillUpper = addedSkill.getText().toUpperCase();
	        
	    } catch (Exception e) {}
	    
	    Thread.sleep(1000);
	    
	    if (addedSkill != null && addedSkill.isDisplayed() && addedSkillUpper.equals(skillNameUpper)) {
	        System.out.println(">> Skill already exists (case-insensitive match found).");
	        
	        // Check if Create button appears
	        boolean createButtonVisible = false;
	        try {
	            WebElement createBtn = reusable.waitForVisibleTime(createButton);
	            createButtonVisible = createBtn.isDisplayed();
	        } catch (Exception e) {}
	        
	        if (createButtonVisible) {
	            System.out.println(">> Create button appeared. Clicking create, then removing duplicate.");
	            
	            // Click create button
	            reusable.waitForVisible(createButton).click();
	            Thread.sleep(800);
	            
	            // Get the newly created skill and remove it
	            List<WebElement> skillsList = reusable.waitForListElement(skillList);
	            WebElement lastSkill = skillsList.get(skillsList.size() - 1);
	            String tempSkillName = lastSkill.getText();
	            System.out.println(">> Temporary skill created: " + tempSkillName);
	            
	            // Remove the last added tag
	            lastSkill.findElement(By.xpath(".//span[@aria-label='close']")).click();
	            Thread.sleep(500);
	            System.out.println(">> Removed temporary duplicate skill.");
	            
	            // CRITICAL FIX: Click on skill field again to open the dropdown
//	            reusable.waitForVisible(skillField).click();
	            Thread.sleep(700);
	        }
	        
	        // Now click on Select label to see all options
	        
	        boolean dropDownVisible = false;
	        try {
	            WebElement dropDown = reusable.waitForVisibleTimeBy(By.xpath("//div[@class='ant-select-dropdown ant-select-dropdown-hidden css-ooninv ant-select-dropdown-placement-bottomLeft']"));
	            dropDownVisible = dropDown.isDisplayed();
	        } catch (Exception e) {}
	        if(dropDownVisible) {
	        	reusable.waitForVisibleBy(By.xpath("(//label[contains(normalize-space(),'Select')])[1]")).click();
	        	Thread.sleep(500);
	        }
	        
//	        
	        WebElement listedSkill = reusable.waitForVisibleBy(By.xpath("(//div[normalize-space()='"+skillName+"'])[1]"));
	        skillName1 = listedSkill.getText();
	        System.out.println(">> Desired Skill: " + skillName1);
	        
	    } else {
	        System.out.println(">> Skill is new. Adding to the list.");
	        Thread.sleep(700);
	        reusable.waitForVisible(createButton).click();
	        Thread.sleep(800);
	        
	        List<WebElement> skillsList = reusable.waitForListElement(skillList);
	        WebElement lastSkill = skillsList.get(skillsList.size() - 1);
	        skillName1 = lastSkill.getText();
	        reusable.waitForVisible(skillField).click();
	        System.out.println(">> Added Skill: " + skillName1);
	    }
	    
	    Thread.sleep(800);
	    reusable.waitForVisible(saveButton).click();
	}

	@SuppressWarnings("unused")
	public void updateTagsData(String skill) throws InterruptedException {
	    String skillName = skill;
	    String skillNameUpper = skillName.toUpperCase();
	    
	    WebElement fieldArea = reusable.waitForVisible(skillField);
	    fieldArea.click();
	    
	    WebElement languageArea = null;
	    WebElement skillArea = null;
	    
	    try {
	        Thread.sleep(500);
	        skillArea = reusable.waitForVisibleTime(skills);
	    } catch (Exception e) {}
	    
	    try {
	        Thread.sleep(500);
	        languageArea = reusable.waitForVisibleTime(languages);
	    } catch (Exception e) {}
	    
	    if (skillArea != null && skillArea.isDisplayed()) {
	        skillArea.sendKeys(skillName);
	    } else if (languageArea != null && languageArea.isDisplayed()) {
	        languageArea.sendKeys(skillName);
	    } else {
	        throw new RuntimeException("Neither skillArea nor languageArea is visible");
	    }
	    
	    WebElement addedSkill = null;
	    WebElement lastSkill = null;
	    String addedSkillUpper = null;
	    boolean createButtonVisible = false;
	    
	    // Search for skill case-insensitively in dropdown
	    try {
	    	 WebElement createBtn = reusable.waitForVisibleTime(createButton);
	            createButtonVisible = createBtn.isDisplayed();
	    } catch (Exception e) {}
	    
	    Thread.sleep(1000);
	    
	    if (addedSkill != null && addedSkill.isDisplayed() && addedSkillUpper.equals(skillNameUpper)) {
	        System.out.println(">> Skill already exists (case-insensitive match found).");
	        
	        // Check if Create button appears
	        try {
	            WebElement createBtn = reusable.waitForVisibleTime(createButton);
	            createButtonVisible = createBtn.isDisplayed();
	        } catch (Exception e) {}
	        
	        if (createButtonVisible) {
	            System.out.println(">> Create button appeared. Clicking create, then removing duplicate.");
	            
	            // Click create button
	            reusable.waitForVisible(createButton).click();
	            Thread.sleep(800);
	            
	            // Get the newly created skill and remove it
	            List<WebElement> skillsList = reusable.waitForListElement(skillList);
	            lastSkill = skillsList.get(skillsList.size() - 1);
	            String tempSkillName = lastSkill.getText();
	            System.out.println(">> Temporary skill created: " + tempSkillName);
	            
	            // Remove the last added tag
	            lastSkill.findElement(By.xpath(".//span[@aria-label='close']")).click();
	            Thread.sleep(500);
	            System.out.println(">> Removed temporary duplicate skill.");
	            
	            // CRITICAL FIX: Click on skill field again to open the dropdown
//	            reusable.waitForVisible(skillField).click();
	            Thread.sleep(700);
	        }
	        
	        boolean dropDownVisible = false;
	        try {
	            WebElement dropDown = reusable.waitForVisibleTimeBy(By.xpath("//div[@class='ant-select-dropdown ant-select-dropdown-hidden css-ooninv ant-select-dropdown-placement-bottomLeft']"));
	            dropDownVisible = dropDown.isDisplayed();
	        } catch (Exception e) {}
	        if(dropDownVisible) {
	        	reusable.waitForVisibleBy(By.xpath("(//label[contains(normalize-space(),'Select')])[1]")).click();
	        	Thread.sleep(500);
	        }
	        
//	        
	        WebElement listedSkill = reusable.waitForVisibleBy(By.xpath("(//div[normalize-space()='"+skillName+"'])[1]"));
	        skillName1 = listedSkill.getText();
	        System.out.println(">> Desired Skill: " + skillName1);
	        
	    } else {
	        System.out.println(">> Skill is new. Adding to the list.");
	        reusable.waitForVisible(createButton).click();
	        Thread.sleep(800);
	        
	        List<WebElement> skillsList = reusable.waitForListElement(skillList);
	        lastSkill = skillsList.get(skillsList.size() - 1);
	        skillName2 = lastSkill.getText();
	        reusable.waitForVisible(skillField).click();
	        System.out.println(">> Added Skill: " + skillName2);
	    }
	    
	    lastSkill.findElement(By.xpath(".//span[@aria-label='close']")).click();
	    reusable.waitForVisibleBy(By.xpath("(//span[normalize-space()='" + skillName2 + "'])[2]")).click();
	    reusable.waitForVisible(saveButton).click();
	}

	public void deleteTagsData() throws InterruptedException {
	    // Search case-insensitively for skills to delete
	    WebElement addedSkill = findSelectedSkillCaseInsensitive(skillName1);
	    if (addedSkill != null) {
	        addedSkill.findElement(By.xpath(".//span[@aria-label='close']")).click();
	        Thread.sleep(800);
	        reusable.waitForInvisibleBy(By.xpath("//span[contains(@class,'ant-select-selection-item-content')][normalize-space()='" + skillName1 + "']"));
	    }
	    
	    WebElement updatedSkill = findSelectedSkillCaseInsensitive(skillName2);
	    if (updatedSkill != null) {
	        updatedSkill.findElement(By.xpath(".//span[@aria-label='close']")).click();
	        Thread.sleep(800);
	        reusable.waitForInvisibleBy(By.xpath("//span[contains(@class,'ant-select-selection-item-content')][normalize-space()='" + skillName2 + "']"));
	    }
	    
	    reusable.waitForVisible(saveButton).click();
	    
	    WebElement errorMessage = null;
	    try {
	        errorMessage = reusable.waitForVisibleTimeBy(By.xpath("//div[@class='ant-form-item-explain-error']"));
	        System.out.println("++ Error Message : " + errorMessage.getText());
	        driver.findElement(By.xpath("(//span[@aria-label='close'])[1]")).click();
	    } catch (Exception e) {}
	}

	// Helper method for case-insensitive search
	private WebElement findSelectedSkillCaseInsensitive(String skillName) {
	    try {
	        List<WebElement> selectedSkills = driver.findElements(By.xpath("//span[@class='ant-select-selection-item-content']/.."));
	        for (WebElement skill : selectedSkills) {
	            String skillText = skill.findElement(By.xpath(".//span[@class='ant-select-selection-item-content']")).getText();
	            if (skillText.equalsIgnoreCase(skillName)) {
	                return skill;
	            }
	        }
	    } catch (Exception e) {}
	    return null;
	}
	
	public void addSocialLinkData() throws InterruptedException {
		
		System.out.println(">> Adding Publication Link Data 1");
		
		List<WebElement> linksContainer = reusable.waitForListElement(linkContainer);
		
		System.out.println(">> Adding Publication Link Data 2");
		WebElement lastLinkContainer = linksContainer.get(linksContainer.size() - 1);
		System.out.println(">> Adding Publication Link Data 3");
		reusable.scrollIntoView(lastLinkContainer);
		System.out.println(">> Adding Publication Link Data 4");
		WebElement linkField = lastLinkContainer.findElement(By.xpath("//input[contains(@placeholder,'www.example.com')]"));
		System.out.println(">> Adding Publication Link Data 4.5");
		String textInLinkField = linkField.getAttribute("value");
		System.out.println(">> Adding Publication Link Data 5");
		
		System.out.println(">> Text in link field: '" + textInLinkField + "'");
		
		
		if(textInLinkField.equals("")) {
		
		System.out.println(">> Adding Social Link Data into the fields available");
		}else {
			System.out.println(">> Link field is NOT empty, proceeding to fill it.");
			WebElement validationText = null;
			try {
			validationText = driver.findElement(By.xpath("//p[@class='text-red-500 my-2']"));
			Assert.assertEquals(validationText.getText(), "You can only add up to 5 links.");
			}catch(Exception e) {
				System.out.println(">> No validation text found, proceeding to add new link fields.");
			}
			if(validationText != null && validationText.isDisplayed()) {
				System.out.println(">> Maximum link limit reached, cannot add more links.");
				lastLinkContainer.findElement(By.xpath(".//span[normalize-space()='Remove']")).click();
				Thread.sleep(500);
				WebElement addLinkBtn = reusable.waitForVisible(addLinkButton);
				addLinkBtn.click();
				Thread.sleep(1500);
				
			}else {
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
		
        reusable.waitForVisible(saveButton).click();
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
		
		reusable.waitForVisible(saveButton).click();
		System.out.println(">> Updated Social Link Data");
		
	}

	public void deleteSocialLinkData() {
		// TODO Auto-generated method stub
		System.out.println(">> Deleting Social Link Data");
		List<WebElement> linksContainer = reusable.waitForListElement(linkContainer);
		WebElement lastLinkContainer = linksContainer.get(0);
		lastLinkContainer.findElement(By.xpath(".//span[normalize-space()='Remove']")).click();
		System.out.println(">> Deleted Social Link Data");
		reusable.waitForVisible(saveButton).click();
		
	}

	public void addProfileData() {
		// TODO Auto-generated method stub
		String filePath = System.getProperty("user.dir") + "/src/test/resources/files/profile_picture.png";
		
		 try {
	            WebElement uploadInput = reusable.waitForVisible(fileInput);
	            uploadInput.sendKeys(filePath);

	            System.out.println("[DEBUG] File upload triggered...");

	         reusable.waitForInvisibleBy(By.xpath("(//div[normalize-space()='Uploading...'])[3]"));
	         
	            System.out.println("[DEBUG] Upload completed and SVG rendered.");

	        } catch (Exception e) {
	            System.err.println("[ERROR] Upload failed: " + e.getMessage());
	            e.printStackTrace();
	            throw e;
	        }
		 
		reusable.waitForVisible(designation).sendKeys("Mobile App Developer");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}