package com.infy.autoqa.karate.helperclass;

import java.util.HashMap;

import org.apache.log4j.PropertyConfigurator;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.infy.autoqa.exceptions.InvalidBrowserException;
import com.infy.autoqa.karate.library.common.SFCommon;
import com.infy.autoqa.wrappers.SeleniumWrapper;
import com.infy.autoqa.databseutils.*;
import com.infy.autoqa.karate.helperclass.DateTimeUtils;
import com.infy.autoqa.listeners.customEventListener;
import com.infy.autoqa.wrappers.LoggerWrapper;

@Listeners(customEventListener.class)
public class TestBase {
	final String sConfigFilePath = "configuration/Config_DEV.cfg";
	final String sLoggerConfigFilePath = "configuration/log4j.properties";

	public static ConfigHelper configObj = null;
	public static LoggerWrapper log = null;
	public static SeleniumWrapper seleniumObj = null;
	public static MySqlWrapper mySQLObj = null;
	public static SFCommon sfcommonObj = null;
	public static BaseTest baseTestObj = null;
	public static HashMap<String, Object> userCredentials = new HashMap<String, Object>();

	@BeforeSuite(alwaysRun = true)
	@Parameters({ "configpath" })
	public void initialize(@Optional(sConfigFilePath) String sCfgPath) {
		PropertyConfigurator.configure(sLoggerConfigFilePath);
		log = new LoggerWrapper(TestBase.class);

		configObj = new ConfigHelper(sCfgPath);
		seleniumObj = new SeleniumWrapper();
		//baseTestObj = new BaseTest();
		sfcommonObj = new SFCommon();
		mySQLObj = new MySqlWrapper(configObj.getTestDBServerIP(), configObj.getTestDBPORTNo(),
				configObj.getTestDBName(), configObj.getTestDBUserName(), configObj.getTestDBPassword());
	}

	@BeforeMethod(alwaysRun = true)
	public void launchBrowser() throws InvalidBrowserException, com.infy.autoqa.exceptions.InvalidBrowserException {
		String downloadPath = System.getProperty("user.dir") + configObj.getFileDownLoadPath();
		seleniumObj.openBrowser(configObj.getBrowserName(), configObj.getChromeDriverPath(),
				configObj.getImplicitWaitTime(), downloadPath);
	}

	@AfterMethod(alwaysRun = true)
	public void closeBrowserAndKillUpDriver(ITestResult result) {
		try {		
		if (result.getStatus() == ITestResult.FAILURE) {
			String outputFilePath = System.getProperty("user.dir") + "\\result\\Failed_" + result.getName() + "_"
					+ DateTimeUtils.getCurrentDateAndTime() + ".png";
			seleniumObj.captureScreen(outputFilePath);
			}
		   }catch(Exception e) {
		}
		finally {
			seleniumObj.getDriver().manage().deleteAllCookies();
			seleniumObj.closeBrowser();
		}
	}

	@AfterTest(alwaysRun = true)
	public void AfterTestcase() {

	}

	@AfterSuite(alwaysRun = true)
	public void cleanUp() {

	}
}