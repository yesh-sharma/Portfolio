package org.zasyasolutions.portfolio.pageObjectModel;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.zasyasolutions.portfolio.baseTestPackage.BaseTest;
import org.zasyasolutions.portfolio.baseTestPackage.GotoPage;
import org.zasyasolutions.portfolio.utils.ReusableCode;

public class VisibilityPage extends BaseTest {

	
	private LoginPage loginPage;
	public ReusableCode reusable;
	public CRUD_Page crudPage;
    public PreviewProfilePage previewPage;
    public PublicProfilePage publicPage;

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
	

		// Initialize helper objects
		
		reusable = new ReusableCode(driver);
		loginPage = new LoginPage();
		crudPage = new CRUD_Page();
		previewPage = new  PreviewProfilePage(driver);
		publicPage = new PublicProfilePage(driver);
		
		
		loginPage.driver = this.driver; // Share the same driver instance
		crudPage.driver = this.driver; 
		crudPage.reusable = this.reusable;
		publicPage.driver = this.driver;
		previewPage.driver = this.driver;

		
		
		PageFactory.initElements(driver, this);
		PageFactory.initElements(driver, loginPage);
		PageFactory.initElements(driver, crudPage);
		PageFactory.initElements(driver, reusable);
		PageFactory.initElements(driver, publicPage);
		PageFactory.initElements(driver, previewPage);
		
		
		loginPage.initializeLoginHelper();

		System.out.println("✓ Page Objects Initialized Successfully");
	}
	
	public VisibilityPage() {
		
	}
	
	 public static String PREVIEW_URL = "https://stagingportfolio.zasyasolutions.com/public-profile/8af852a3-7629-4b10-b227-7d2e30fa0962?type=preview";
	 public static String PUBLIC_URL = "https://stagingportfolio.zasyasolutions.com/public-profile/8af852a3-7629-4b10-b227-7d2e30fa0962?type=Public";
	    

	public void checkVisibilityOfSection(String section) throws InterruptedException{
		
		 try {
	            // ============================================================
	            // STEP 1: Check and handle visibility modal if present
	            // ============================================================
	            System.out.println("Step 1: Checking for visibility modal for section: " + section);
	            
	            try {
	                WebElement visibilityModal = crudPage.openVisibilityModal(section);
	                
	                if (visibilityModal != null && visibilityModal.isDisplayed()) {
	                    System.out.println("Visibility modal found. Clicking it...");
//	                  WebElement visibilityButton =  reusable.waitForVisible(visibilityModal);
	                  visibilityModal.click();
	                    Thread.sleep(500); // Brief wait for modal action
	                    System.out.println("Visibility modal clicked successfully");
	                } else {
	                    System.out.println("Visibility modal not displayed. Moving to step 2...");
	                }
	            } catch (Exception e) {
	                System.out.println("Visibility modal not present. Proceeding to step 2...");
	            }
	            
	            // ============================================================
	            // STEP 2: Toggle section visibility based on section type
	            // ============================================================
	            System.out.println("\nStep 2: Toggling sections visibility for section: " + section);
	            
	            WebElement visibilityToggleSection;
	            boolean isToggleVisible = false;
	            
	            // Determine which visibility element to use based on section type
	            if (section.equalsIgnoreCase("Project") || section.equalsIgnoreCase("Testimonial")) {
	                System.out.println("Using noVisibilityOfToggleModal for " + section);
	                visibilityToggleSection = crudPage.noVisibilityOfToggleModal(section);
	            } else {
	                System.out.println("Using noVisibilityModal for " + section);
	                visibilityToggleSection = crudPage.noVisibilityModal(section);
	            }
	            
	            // Verify visibility toggle is present and visible
	            try {
	            	 reusable.waitForVisible(visibilityToggleSection);
	                isToggleVisible = visibilityToggleSection.isDisplayed();
	                
	                Assert.assertTrue(isToggleVisible, 
	                    "Visibility toggle for section '" + section + "' should be visible");
	                
	                System.out.println("Visibility toggle found and verified");
	                
	                // Click the visibility toggle to hide the section
	                reusable.waitForVisible(visibilityToggleSection);
	                visibilityToggleSection.click();
	                Thread.sleep(1000); // Wait for toggle action to complete
	                System.out.println("Visibility toggled OFF for section: " + section);
	                
	            } catch (Exception e) {
	                Assert.fail("Failed to find or click visibility toggle for section: " + section + 
	                           ". Error: " + e.getMessage());
	            }
	            
	            // Click Preview Profile Button
	            System.out.println("Clicking Preview Profile button...");
	            WebElement previewButton = crudPage.getPreviewProfileButton();
	            reusable.waitForVisible(previewButton);
	            previewButton.click();
	            
	            // Wait for navigation to preview page
	            wait.until(ExpectedConditions.urlContains("type=preview"));
	            String currentUrl = driver.getCurrentUrl();
	            Assert.assertEquals(currentUrl, PREVIEW_URL, 
	                "Should navigate to preview profile page");
	            System.out.println("Successfully navigated to: " + currentUrl);
	            
	            // ============================================================
	            // STEP 3: Verify section is NOT visible on Preview page
	            // ============================================================
	            System.out.println("\nStep 3: Verifying section '" + section + "' is NOT visible on Preview page");
	            
	            Thread.sleep(2000); // Wait for page to fully load
	            
	            boolean isSectionVisibleOnPreview = previewPage.isSectionVisible(section);
	            
	            Assert.assertFalse(isSectionVisibleOnPreview, 
	                "Section '" + section + "' should NOT be visible on Preview page after toggling visibility OFF");
	            
	            if (!isSectionVisibleOnPreview) {
	                System.out.println("✓ PASSED: Section '" + section + "' is correctly hidden on Preview page");
	            } else {
	                System.out.println("✗ FAILED: Section '" + section + "' is still visible on Preview page");
	            }
	            
	            // Additional verification - check section container
	            try {
	                WebElement sectionContainer = previewPage.getSectionContainer(section);
	                Assert.assertFalse(sectionContainer.isDisplayed(), 
	                    "Section container for '" + section + "' should not be displayed");
	            } catch (Exception e) {
	                System.out.println("✓ Section container not found (expected behavior)");
	            }
	            
	            // Click button to navigate to Public profile
	            System.out.println("\nNavigating to Public profile...");
	            WebElement publicProfileButton = previewPage.getPublicProfileButton();
	            reusable.waitForVisible(publicProfileButton);
	            publicProfileButton.click();
	            
	            // Wait for navigation to public page
	            wait.until(ExpectedConditions.urlContains("type=Public"));
	            currentUrl = driver.getCurrentUrl();
	            Assert.assertEquals(currentUrl, PUBLIC_URL, 
	                "Should navigate to public profile page");
	            System.out.println("Successfully navigated to: " + currentUrl);
	            
	            // ============================================================
	            // STEP 4: Verify section is NOT present on Public page
	            // ============================================================
	            System.out.println("\nStep 4: Verifying section '" + section + "' is NOT present on Public page");
	            
	            Thread.sleep(2000); // Wait for page to fully load
	            
	            boolean isSectionPresentOnPublic = publicPage.isSectionPresent(section);
	            
	            Assert.assertFalse(isSectionPresentOnPublic, 
	                "Section '" + section + "' should NOT be present on Public profile page");
	            
	            if (!isSectionPresentOnPublic) {
	                System.out.println("✓ PASSED: Section '" + section + "' is correctly absent from Public page");
	            } else {
	                System.out.println("✗ FAILED: Section '" + section + "' is present on Public page");
	            }
	            
	            // Additional verification - ensure no DOM elements exist
	            int sectionElementCount = publicPage.getSectionElementCount(section);
	            Assert.assertEquals(sectionElementCount, 0, 
	                "No DOM elements for section '" + section + "' should exist on Public page");
	            System.out.println("✓ Verified: Zero DOM elements found for section '" + section + "'");
	            
	            // Final assertion summary
	            System.out.println("\n" + "=".repeat(60));
	            System.out.println("TEST SUMMARY for Section: " + section);
	            System.out.println("=".repeat(60));
	            System.out.println("✓ Visibility toggle clicked successfully");
	            System.out.println("✓ Section hidden on Preview page: PASSED");
	            System.out.println("✓ Section absent from Public page: PASSED");
	            System.out.println("=".repeat(60));
	            
	        } catch (AssertionError ae) {
	            System.err.println("Assertion Failed: " + ae.getMessage());
	            throw ae;
	        } catch (Exception e) {
	            System.err.println("Test execution failed: " + e.getMessage());
	            e.printStackTrace();
	            Assert.fail("Test failed due to exception: " + e.getMessage());
	        }
	}
	
}
