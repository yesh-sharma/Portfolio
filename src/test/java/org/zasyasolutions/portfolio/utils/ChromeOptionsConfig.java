package org.zasyasolutions.portfolio.utils;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeOptionsConfig {
    public static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.setBinary(
        	    "/Applications/Google Chrome for Testing.app/Contents/MacOS/Google Chrome for Testing"
        	);
       // options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage"); // Use this to prevent resource problems
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--blink-settings=imagesEnabled=false"); // Disable images to speed up rendering
        options.addArguments("--disable-extensions"); // Disable extensions for faster startup
        options.addArguments("--disable-infobars"); // Prevent extra UI components from rendering
        options.addArguments("--disable-notifications"); // Disable notifications
        options.setAcceptInsecureCerts(true);
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);

        return options;
    }
}
