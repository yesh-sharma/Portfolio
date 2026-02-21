package org.zasyasolutions.portfolio.pageObjectModel;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PreviewProfilePage {

	  WebDriver driver;
	    
	    public PreviewProfilePage(WebDriver driver) {
	        this.driver = driver;
	    }
	    
	    public boolean isSectionVisible(String section) {
	        try {
	            WebElement sectionElement = driver.findElement(
	                By.xpath(String.format("//section[@data-section='%s'] | //div[@data-section='%s']", 
	                section.toLowerCase(), section.toLowerCase())));
	            return sectionElement.isDisplayed();
	        } catch (Exception e) {
	            return false;
	        }
	    }
	    
	    public WebElement getSectionContainer(String section) {
	        return driver.findElement(
	            By.xpath(String.format("//section[@data-section='%s']", section.toLowerCase())));
	    }
	    
	    public WebElement getPublicProfileButton() {
	        return driver.findElement(By.xpath("//button[contains(@class, 'bg-white') and contains(text(), 'Public')]"));
	    }
	}

