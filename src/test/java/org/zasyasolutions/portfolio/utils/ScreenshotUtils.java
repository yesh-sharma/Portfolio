package org.zasyasolutions.portfolio.utils;

import org.openqa.selenium.*;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class ScreenshotUtils {

    public static String takeScreenshot(WebDriver driver, String testName) {
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            String path = System.getProperty("user.dir") +
                    "/reports/screenshots/" + testName + "_" + System.currentTimeMillis() + ".png";

            FileUtils.copyFile(src, new File(path));
            return path;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
