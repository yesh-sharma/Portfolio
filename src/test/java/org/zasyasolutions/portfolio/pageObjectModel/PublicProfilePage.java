package org.zasyasolutions.portfolio.pageObjectModel;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PublicProfilePage {

WebDriver driver;
    
    public PublicProfilePage(WebDriver driver) {
        this.driver = driver;
    }
    
    public boolean isSectionPresent(String section) {
        try {
            driver.findElement(
                By.xpath(String.format("//section[@data-section='%s'] | //div[@data-section='%s']", 
                section.toLowerCase(), section.toLowerCase())));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public int getSectionElementCount(String section) {
        return driver.findElements(
            By.xpath(String.format("//section[@data-section='%s'] | //div[@data-section='%s']", 
            section.toLowerCase(), section.toLowerCase()))).size();
    }
}
