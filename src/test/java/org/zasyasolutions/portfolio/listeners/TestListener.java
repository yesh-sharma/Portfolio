package org.zasyasolutions.portfolio.listeners;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.zasyasolutions.portfolio.report.ExtentManager;
import org.zasyasolutions.portfolio.utils.ScreenshotUtils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;



import java.lang.reflect.Field;

public class TestListener implements ITestListener {

    private static ExtentReports extent = ExtentManager.getReporter();
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    @Override
    public synchronized void onStart(ITestContext context) {
        System.out.println("Test Suite Started: " + context.getName());
    }

    @Override
    public synchronized void onTestStart(ITestResult result) {
        ExtentTest test = extent.createTest(
            result.getTestClass().getName() + " :: " + result.getMethod().getMethodName()
        );
        extentTest.set(test);
        System.out.println("Test Started: " + result.getMethod().getMethodName());
    }

    @Override
    public synchronized void onTestSuccess(ITestResult result) {
        extentTest.get().log(Status.PASS, "Test Passed");
        extentTest.remove();
    }

    @Override
    public synchronized void onTestFailure(ITestResult result) {
        extentTest.get().log(Status.FAIL, "Test Failed");
        extentTest.get().fail(result.getThrowable());

        WebDriver driver = getDriver(result);
        
        if (driver != null) {
            try {
                String path = ScreenshotUtils.takeScreenshot(driver, result.getMethod().getMethodName());
                if (path != null) {
                    extentTest.get().addScreenCaptureFromPath(path, result.getMethod().getMethodName());
                    System.out.println("Screenshot captured successfully");
                }
            } catch (Exception e) {
                extentTest.get().log(Status.WARNING, "Could not capture screenshot: " + e.getMessage());
                System.err.println("Screenshot capture failed: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            extentTest.get().log(Status.WARNING, "Driver instance not found, screenshot not captured");
            System.err.println("Driver is null for test: " + result.getMethod().getMethodName());
        }
        System.out.println("ScreenShot functionality done");
        extentTest.remove();
    }

    @Override
    public synchronized void onTestSkipped(ITestResult result) {
        ExtentTest test = extent.createTest(
            result.getTestClass().getName() + " :: " + result.getMethod().getMethodName()
        );
        extentTest.set(test);
        extentTest.get().log(Status.SKIP, "Test Skipped");
        extentTest.get().skip(result.getThrowable());
        extentTest.remove();
    }

    @Override
    public synchronized void onFinish(ITestContext context) {
        System.out.println("Test Suite Finished: " + context.getName());
        System.out.println("Total Tests: " + context.getAllTestMethods().length);
        System.out.println("Passed: " + context.getPassedTests().size());
        System.out.println("Failed: " + context.getFailedTests().size());
        System.out.println("Skipped: " + context.getSkippedTests().size());
        
        if (extent != null) {
            extent.flush();
        }
    }

    /**
     * Enhanced method to get WebDriver from test instance
     */
    private WebDriver getDriver(ITestResult result) {
        Object testInstance = result.getInstance();
        WebDriver driver = null;

        System.out.println("Attempting to get driver from: " + testInstance.getClass().getName());

        // Method 1: Try public field 'driver'
        try {
            Field field = testInstance.getClass().getField("driver");
            driver = (WebDriver) field.get(testInstance);
            System.out.println("Driver found using public field");
            return driver;
        } catch (NoSuchFieldException e) {
            System.out.println("No public 'driver' field found, trying declared fields...");
        } catch (Exception e) {
            System.err.println("Error accessing public driver field: " + e.getMessage());
        }

        // Method 2: Try private/protected field 'driver'
        try {
            Field field = testInstance.getClass().getDeclaredField("driver");
            field.setAccessible(true);
            driver = (WebDriver) field.get(testInstance);
            System.out.println("Driver found using declared field");
            return driver;
        } catch (NoSuchFieldException e) {
            System.out.println("No declared 'driver' field found, checking superclass...");
        } catch (Exception e) {
            System.err.println("Error accessing declared driver field: " + e.getMessage());
        }

        // Method 3: Try from superclass
        try {
            Class<?> superclass = testInstance.getClass().getSuperclass();
            if (superclass != null) {
                Field field = superclass.getDeclaredField("driver");
                field.setAccessible(true);
                driver = (WebDriver) field.get(testInstance);
                System.out.println("Driver found in superclass");
                return driver;
            }
        } catch (Exception e) {
            System.err.println("Error accessing driver from superclass: " + e.getMessage());
        }

        System.err.println("Driver not found in any accessible field!");
        return null;
    }
}