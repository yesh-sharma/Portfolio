package org.zasyasolutions.portfolio.report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {
    public static ExtentReports extent;

    public static synchronized ExtentReports getReporter() {
        if (extent == null) {
            String reportPath = System.getProperty("user.dir") + "/test-output/ExtentReportNEW.html";
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
            sparkReporter.config().setDocumentTitle("Automation Report");
            sparkReporter.config().setReportName("Test Results");
            sparkReporter.config().setTheme(Theme.STANDARD);
            
            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("User", System.getProperty("user.name"));
        }
        return extent;
    }
}