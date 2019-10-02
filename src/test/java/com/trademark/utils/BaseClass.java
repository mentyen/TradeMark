package com.trademark.utils;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.trademark.pages.HomePage;

public class BaseClass {
	
	public static ExtentHtmlReporter htmlReport;
	public static ExtentReports report;
	public static ExtentTest test;
	
	public static WebDriver driver; 
	
	@BeforeMethod(alwaysRun=true)
	public void setUp() {
		
		String url=ConfigsReader.getProperty("url");
		String browser=ConfigsReader.getProperty("browser");
		
		if(browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", Constant.CHROME_PATH);
			driver=new ChromeDriver();
			driver.manage().window().fullscreen();
			driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			
			driver.get(url);	
			
		}else if(browser.equalsIgnoreCase("firefox")){
			System.setProperty("webdriver.gecko.driver", Constant.FIREFOX_PATH);
			driver=new FirefoxDriver();
			driver.manage().window().fullscreen();
			driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			
			driver.get(url);
		}
		
	
		
		
	}
	
	
	
	@AfterMethod(alwaysRun=true)
	public void tearDown() {
		
		driver.close();
		
	}
	
	
	@BeforeTest(alwaysRun=true)
	public void generateReport() {
		ConfigsReader.getProperty(Constant.EXCELL_PATH);
		//create an object of extentReport and htmlReporter
		htmlReport=new ExtentHtmlReporter(Constant.REPORT_FILEPATH);
		report = new ExtentReports();
		report.attachReporter(htmlReport);
		//provide some info (optional)
		report.setSystemInfo("OS", Constant.OS_NAME);
		report.setSystemInfo("Environment", "Test");
		report.setSystemInfo("Browser", ConfigsReader.getProperty("browser"));
		report.setSystemInfo("QA Engineer", Constant.USER_NAME);
	}
	
	@AfterTest (alwaysRun = true)
	public void flushReport() {
		report.flush();
	}
	

}
