package org.zasyasolutions.portfolio.dataProvider;

import org.testng.annotations.DataProvider;

public class TestDataProvider {

	 public Object[][] LoginCredentialsLokesh() {
	        return new Object[][] {
	            {"lokesh@zasyasolutions.com", "987654321"}
	        };
	 }
	 public Object[][] LoginCredentialsAvneesh() {
	        return new Object[][] {
	            {"avneesh@zasyasolutions.com", "Avneesh@portfolio"}
	        };
	 }
	 
	 @DataProvider(name = "skillData")
	 public Object[][] skills() {
	        return new Object[][] {
//	            {"Kafka"},
//	            {"Playwright Automation"},
	            {"playWright Automation"}
	        };
	 }
	 @DataProvider(name = "languageData")
	 public Object[][] language() {
	        return new Object[][] {
	            {"French"},
	            {"Punjabi"},
	            {"punjabi"}
	        };
	 }
}
