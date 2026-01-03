package org.zasyasolutions.portfolio.utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    private int retryCount = 0;
    private static final int maxRetries = 1;  // retry failed test 2 times

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < maxRetries) {
            retryCount++;
            System.out.println("Retrying test: " + result.getName() + " | Attempt: " + retryCount);
            return true;  // retry test
        }
        return false; // stop retrying
    }
}
