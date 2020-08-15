/*
* Copyright (c) 2018 EBS Automation Team. All rights reserved.
*/
package com.infy.autoqa.wrappers;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidSelectorException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.infy.autoqa.karate.enums.SelectBy;
import com.infy.autoqa.exceptions.InvalidBrowserException;
import com.infy.autoqa.exceptions.TimeOutException;
import com.infy.autoqa.karate.helperclass.StringUtils;
import com.infy.autoqa.listeners.customEventListener;
//import com.sun.org.apache.xml.internal.security.utils.Base64;
//import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import java.nio.ByteBuffer;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @Description
 * @Author gmathavx
 * @Since 04-Oct-2018
 */
public class SeleniumWrapper {

	static LoggerWrapper log = new LoggerWrapper(customEventListener.class);

	public SeleniumWrapper() {
	}

	public WebDriver driver = null;

	/**
	 * Function to launch the browser which has be passed 
	 * 
	 * @param browser
	 * @param driverPath
	 * @param implicitTimeOutDuration
	 * @param downloadPath - will download any files from application in given path otherwise will download in default path. Currently this feature is supporting only Chrome, Firefox browsers only
	 * @throws InvalidBrowserException
	 */
	@SuppressWarnings("deprecation")
	public WebDriver openBrowser(String browser, String sDriverPath, int implicitTimeOutDuration, String downloadPath) {

//		// try {
//		// driver = new RemoteWebDriver(new URL("http://127.0.0.1:5566/wd/hub"),
//		// getDesiredCapability(browser, sPlatform));
//		System.setProperty("webdriver.chrome.driver", sDriverPath);
//		// Set chrome preferences
//		Map<String, Object> chromePrefs = new HashMap<String, Object>();
//		chromePrefs.put("profile.default_content_settings.popups", 0);
//		if (!StringUtils.isNullOrBlank(downloadPath)) {
//			chromePrefs.put("download.default_directory", downloadPath);
//		}
//		chromePrefs.put("safebrowsing.enabled", "false");
//		// Initalize chrome options
//		ChromeOptions options = new ChromeOptions();
//		options.setCapability(CapabilityType.PLATFORM_NAME, Platform.WINDOWS);
//		options.addArguments("disable-infobars");
//		options.addArguments("--disable-notifications");
//		options.setExperimentalOption("prefs", chromePrefs);
//		// driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),
//		// options);
//		driver = new ChromeDriver(options);
//		// eventFiringDriver = new EventFiringWebDriver(driver);
//
//		// actionListener = new customActionListener();
//		// eventFiringDriver.register(actionListener);
//		/*
//		 * } catch (MalformedURLException e) {
//		 * System.err.print("Invalid selenium server URL passed"); e.printStackTrace();
//		 * }
//		 */
		
		String browserName=browser.toUpperCase().trim();
		try {			
			switch(browser) {
			case "CHROME":			
				System.setProperty("webdriver.chrome.driver", sDriverPath);
				Map<String, Object> chromePrefs = new HashMap<String, Object>();
				chromePrefs.put("profile.default_content_settings.popups", 0);
				if (!StringUtils.isNullOrBlank(downloadPath)) {
					chromePrefs.put("download.default_directory", downloadPath);
				}
				chromePrefs.put("safebrowsing.enabled", "false");
				ChromeOptions options = new ChromeOptions();
				options.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
				options.setCapability(CapabilityType.PLATFORM_NAME, Platform.WINDOWS);
				options.addArguments("disable-infobars");
				options.addArguments("--disable-notifications");
				options.addArguments("--disable-print-preview");
				options.setExperimentalOption("prefs", chromePrefs);
				// driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options);
				driver = new ChromeDriver(options);
				maximizeBrowser();
				break;
			case "EDGE":			
				File file=new File(sDriverPath);
				System.setProperty("webdriver.edge.driver", file.getAbsolutePath());
				DesiredCapabilities desiredCapabilites;
				desiredCapabilites = getDesiredCapability(browserName, "WINDOWS");
				
				desiredCapabilites.setCapability("disable-infobars", true);
				desiredCapabilites.setCapability("--disable-notifications", true);
				desiredCapabilites.setCapability("ms:inPrivate", true);
				driver=new EdgeDriver(desiredCapabilites);
				maximizeBrowser();
				break;
			case "FIREFOX":			
		        System.setProperty("webdriver.gecko.driver", sDriverPath);
		        FirefoxProfile profile = new FirefoxProfile();
		        if (!StringUtils.isNullOrBlank(downloadPath)) {
		            profile.setPreference("browser.download.folderList", 2);		            
		            // Setting custom download directory
		            profile.setPreference("browser.download.dir", downloadPath);		     
		            // Skipping Save As dialog box for types of files with their MIME
		            profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "text/csv,application/java-archive, application/x-msexcel,application/excel,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml,application/vnd.microsoft.portable-executable");
				}
		        // Creating FirefoxOptions to set profile
		        FirefoxOptions option = new FirefoxOptions();
		        option.setProfile(profile);
	            DesiredCapabilities capabilities = DesiredCapabilities.firefox();
	            capabilities.setCapability("marionette", true);
	            driver = new FirefoxDriver(capabilities);
				maximizeBrowser();
				break;
			case "IE":			
				System.setProperty("webdriver.ie.driver", sDriverPath);
	            DesiredCapabilities cap = new DesiredCapabilities();
	            cap.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
	            cap.setCapability("requireWindowFocus", true);
	            cap.setCapability("nativeEvents", false);
	            cap.setCapability("unexpectedAlertBehaviour", "accept");
//	            cap.setCapability("ignoreProtectedModeSetting", true);
	            cap.setCapability("disable-popup-blocking", true);
//	            cap.setCapability("enablePersistentHover", true);
//	            cap.setCapability("ignoreZoomSetting", true);
//	            cap.setCapability("EnableNativeEvents", false);
//	            cap.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
//	            cap.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false);
	            driver = new InternetExplorerDriver(cap);
	            maximizeBrowser();
				break;
			case "SAFARI":			
		        System.setProperty("webdriver.safari.driver", sDriverPath);
		        SafariOptions safariOptions = new SafariOptions();	
		        safariOptions.useCleanSession(true); 
		        driver = new SafariDriver(safariOptions);
				maximizeBrowser();
				break;
			}

			// Call setting implicit time out duration only if it is > 0
//			if (implicitTimeOutDuration > 0)
//				setImplicitWait(implicitTimeOutDuration);
			
		} catch (InvalidBrowserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		return driver;
	}


	/**
	 * @Description Returns the instance of current driver
	 * @Author gmathavx
	 * @Since 05-Oct-2018
	 * @return
	 */
	public WebDriver getDriver() {
		return this.driver;
	}

	/**
	 * @Description Sets the desired capability for the application
	 * @Author gmathavx
	 * @Since 05-Oct-2018
	 * @param sBrowserName
	 * @param sPlatform
	 * @return
	 * @throws InvalidBrowserException
	 */
	private DesiredCapabilities getDesiredCapability(String sBrowserName, String sPlatform)
			throws InvalidBrowserException {
		DesiredCapabilities capability = null;

		switch (sBrowserName.toUpperCase()) {
		case "CHROME":
			capability = DesiredCapabilities.chrome();
			break;
		case "INTERNET_EXPLORER":
			capability = DesiredCapabilities.internetExplorer();
			break;
		case "FIREFOX":
			capability = DesiredCapabilities.firefox();

			break;
		default:
			log.error("Invalid Browser Option");
			throw new InvalidBrowserException(sBrowserName);
		}
		capability.setBrowserName(sBrowserName);
		switch (sPlatform.toUpperCase()) {
		case "WINDOWS":
			capability.setPlatform(Platform.WIN10);
			break;
		case "LINUX":
			capability.setPlatform(Platform.LINUX);
			break;
		default:
			capability.setPlatform(Platform.WIN10);
		}
		return capability;
	}

	/**
	 * @Description To maximize the browser window
	 * @Author gmathavx
	 * @Since 05-Oct-2018
	 */
	public void maximizeBrowser() {
		this.driver.manage().window().maximize();
	}

	/**
	 * @Description Navigate to given URL
	 * @Author gmathavx
	 * @Since 05-Oct-2018
	 * @param sURL
	 */
	public void navigateToURL(String sURL) {
		driver.get(sURL);
	}

	/**
	 * @Description Set the implicit timeout to the driver to wait before it throws
	 *              exception
	 * @Author gmathavx
	 * @Since 05-Oct-2018
	 * @param iWaitTime
	 */
	public void setImplicitWait(int iWaitTime) {
//		log.info("Implicit wait is set to " + iWaitTime + " seconds");
		driver.manage().timeouts().implicitlyWait(iWaitTime, TimeUnit.SECONDS);
	}

	/**
	 * @Description get web driver wait
	 * @Author natarajs
	 * @Since 05-Oct-2018
	 * @param timeOut
	 * @return
	 */
	public WebDriverWait getWebDriverWait(int timeOut) {
		return new WebDriverWait(driver, timeOut);
	}

	/**
	 * @Description close the browser
	 * @Author gmathavx
	 * @Since 05-Oct-2018
	 */
	public void closeBrowser() {
		log.info("Closing the browser");
		driver.close();
		driver.quit();
	}

	/**
	 * @Description To execute synchronous JavaScript commands
	 * @Author natarajs
	 * @Since 05-Oct-2018
	 * @param sQuery
	 * @return
	 */
	public String executeJavaScript(String sQuery) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		// log.info("Executing JS - " + sQuery);
		Object a = js.executeScript(sQuery);
		if (a != null)
			return a.toString();
		return null;
	}

	/**
	 * @Description Checks the whether page load is complete [document.readyState ==
	 *              complete] and wait for maximum of 30 seconds.<br>
	 * @Author natarajs
	 * @Since 05-Oct-2018
	 * @return If the page load is complete within 30 seconds will return true
	 *         otherwise false
	 */
	public boolean waitTillPageLoadIsComplete() {
		int iIteration = 15;
		while (true) {
			if (this.executeJavaScript("return document.readyState").equals("complete")) {
				log.info("Page '" + getPageTitle() + "' loaded successfully");
				return true;
			}
			if (iIteration > 0) {
				waitForSeconds(2);
				iIteration--;
			} else {
				return false;
			}
		}
	}

	public void waitForSeconds(int iSeconds) {
		try {
			Thread.sleep(iSeconds * 1000);
			// log.info("Execution is stopped for - " + iSeconds + " seconds");
		} catch (InterruptedException e) {
			log.error(e.getStackTrace());
			Thread.currentThread().interrupt();
		}
	}

	/**
	 * @Description
	 * @Author gmathavx
	 * @Since 05-Oct-2018
	 * @param element
	 * @param selectBy
	 * @param sValue
	 */
	public void select(WebElement element, SelectBy selectBy, String sValue) {
		try {
			this.waitForWebElementVisible(element, 5);
			Select objSelect = new Select(element);
			switch (selectBy) {
			case Text:
				objSelect.selectByVisibleText(sValue);
				break;
			case Value:
				objSelect.selectByValue(sValue);
				break;
			case Index:
				objSelect.selectByIndex(Integer.parseInt(sValue));
				break;
			default:
				throw new InvalidSelectorException("Invalid Option Selected");
			}
		} catch (TimeOutException ex) {
			log.error(ex.getMessage());
			throw new TimeoutException("waited for 5 seconds");
		} catch (Exception ex) {
			log.error(ex.getMessage());
			throw ex;
		}
	}

	/**
	 * @Description To Take screenshot of the screen
	 * @Author gmathavx
	 * @Since 07-Oct-2018
	 * @param driver
	 * @param sOutputFilePath
	 */
	public void captureScreen(WebDriver driver, String sOutputFilePath) {

		try {
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(sOutputFilePath));
		} catch (IOException e) {
			log.error(e.getStackTrace());
		}
	}

	/**
	 * @Description To Click on a particular element
	 * @param element
	 * @author natarajs
	 * @since Aug 6,2018
	 */
	public void click(WebElement element) {
		try {
			waitForWebElementVisible(element, 5);
			element.click();
		} catch (TimeOutException e) {
			log.error(e.getMessage());
			throw new TimeoutException();
		} catch (Exception e) {
			log.error("Not able to click web element");
			throw e;
		}
	}

	/**
	 * Method to wait for visible for given of time
	 * 
	 * @Description
	 * @Author mohseenx
	 * @Since Aug 5, 2018
	 * @param driver
	 * @param element
	 * @param timeout
	 * @return
	 * @throws TimeOutException
	 */
	public boolean waitForWebElementVisible(WebElement element, int timeout) throws TimeOutException {
		try {
			(new WebDriverWait(driver, timeout)).until(ExpectedConditions.visibilityOf(element));
		} catch (TimeoutException e) {
			throw new TimeOutException("Timeout exception");
		}
		return true;
	}

	/**
	 * @Description Method to wait for element till given time
	 * @Author mohseenx
	 * @Since Aug 6, 2018
	 * @param element
	 * @param waitSeconds
	 * @param waitLoopCount
	 */
	public void waitForElement(WebElement element, int waitSeconds, int waitLoopCount) {
		long totalWaitTimes = waitSeconds * waitLoopCount;
		if (Duration.ofSeconds(totalWaitTimes).getSeconds() > Duration.ofSeconds(600).getSeconds()) {
			waitSeconds = 60;
			waitLoopCount = 10;
		}
		if (waitLoopCount <= 0) {
			waitLoopCount = 1;
		}
		if (waitSeconds <= 1) {
			waitSeconds = 1;
		}
		while (isNumberNotEqualToZero(waitLoopCount)) {
			try {
				if (isWebElementNotEqualToNull(element) && element.isDisplayed()) {
					waitLoopCount = 0;
				} else {
					if (waitLoopCount != 0) {
						waitForSeconds(waitSeconds);
						waitLoopCount--;
					} else if (isNumberEqualToZero(waitLoopCount)) {
						log.info("Element not found : " + element);
					}
				}
			} catch (Exception ex) {
				if (isNumberNotEqualToZero(waitLoopCount)) {
					log.info("Element not found. Retrying one more time : " + ex.getMessage());

					waitForSeconds(waitSeconds);
					waitLoopCount--;
				} else if (isNumberEqualToZero(waitLoopCount)) {
					log.info("Element not found : " + ex.getMessage());
				}
			}
		}
	}

	/**
	 * @Description Method to wait for element till given time
	 * @Author gmathavx
	 * @Since Aug 13, 2018
	 * @param element
	 * @param waitSeconds
	 * @param waitLoopCount
	 */
	public WebElement waitForElement(By element, int waitSeconds, int waitLoopCount) {
		long totalWaitTimes = waitSeconds * waitLoopCount;
		if (Duration.ofSeconds(totalWaitTimes).getSeconds() > Duration.ofSeconds(600).getSeconds()) {
			waitSeconds = 60;
			waitLoopCount = 10;
		}
		if (waitLoopCount <= 0) {
			waitLoopCount = 1;
		}
		if (waitSeconds <= 1) {
			waitSeconds = 1;
		}
		WebElement tmpElement = null;
		while (isNumberNotEqualToZero(waitLoopCount)) {
			try {
				tmpElement = this.driver.findElement(element);
				if (isWebElementNotEqualToNull(tmpElement) && tmpElement.isDisplayed()) {
					waitLoopCount = 0;
				} else {
					if (isNumberNotEqualToZero(waitLoopCount)) {
						log.info("Element not found. Retrying one more time");
						waitForSeconds(waitSeconds);
						waitLoopCount--;
					} else if (isNumberEqualToZero(waitLoopCount)) {
						log.info("Element not found : " + element);
					}
				}
			} catch (Exception ex) {
				if (isNumberNotEqualToZero(waitLoopCount)) {
					log.info("Element not found. Retrying one more time : " + ex.getMessage());
					waitForSeconds(waitSeconds);
					waitLoopCount--;
				} else if (isNumberEqualToZero(waitLoopCount)) {
					log.info("Element not found : " + ex.getMessage());
				}
			}
		}
		return tmpElement;
	}

	/**
	 * @Description Method to wait for element till given time
	 * @Author gmathavx
	 * @Since Aug 13, 2018
	 * @param element
	 * @param waitSeconds
	 * @param waitLoopCount
	 */
	public List<WebElement> waitForElements(By element, int waitSeconds, int waitLoopCount) {
		long totalWaitSeconds = waitSeconds * waitLoopCount;
		if (Duration.ofSeconds(totalWaitSeconds).getSeconds() > Duration.ofSeconds(600).getSeconds()) {
			waitSeconds = 60;
			waitLoopCount = 10;
		}
		if (waitLoopCount <= 0) {
			waitLoopCount = 1;
		}
		if (waitSeconds <= 1) {
			waitSeconds = 1;
		}
		List<WebElement> tmpElement = null;
		while (isNumberNotEqualToZero(waitLoopCount)) {
			try {
				tmpElement = this.driver.findElements(element);
				if (isListWebElementNotEqualToNull(tmpElement) && !tmpElement.isEmpty()) {
					waitLoopCount = 0;
				} else {
					if (isNumberNotEqualToZero(waitLoopCount)) {
						log.info("Element not found. Retrying one more time");
						waitForSeconds(waitSeconds);
						waitLoopCount--;
					} else if (isNumberEqualToZero(waitLoopCount)) {
						log.info("Element not found : " + element);
					}
				}
			} catch (Exception ex) {
				if (isNumberNotEqualToZero(waitLoopCount)) {
					log.info("Element not found. Retrying one more time : " + ex.getMessage());
					waitForSeconds(waitSeconds);
					waitLoopCount--;
				} else if (isNumberEqualToZero(waitLoopCount)) {
					log.info("Element not found : " + ex.getMessage());
				}
			}
		}
		return tmpElement;
	}

	/**
	 * @Description scroll to element
	 * @Author gmathavx
	 * @Since 28-Aug-2018
	 * @param element
	 */
	public void scrollToElement(WebElement element) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView({behavior: \"auto\", block: \"center\", inline: \"nearest\"});",
				element);
	}

	/**
	 * @Description Method to click element by JavascriptExecutor
	 * @Author gmathavx
	 * @Since 04-Oct-2018
	 */
	public void clickByJS(WebElement element) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click()", element);
	}

	/**
	 * @Description Method to set text to the element by JavascriptExecutor
	 * @Author gmathavx
	 * @Since 04-Oct-2018
	 * @param element
	 * @param value
	 */
	public void setTextByJs(WebElement element, String value) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].value='" + value + "';", element);
	}

	/**
	 * @Description Method to scroll by position using JavascriptExecutor
	 * @Author gmathavx
	 * @Since 04-Oct-2018
	 * @param horizontal
	 * @param vertical
	 */
	public void scrollByPosition(String horizontal, String vertical) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("scroll(" + horizontal + ", " + vertical + ");");
	}

	/**
	 * @Description Method for scroll up
	 * @Author gmathavx
	 * @Since 07-Oct-2018
	 */
	public void scrollByUp() {
		this.scrollByPosition("0", "-400");
	}

	/**
	 * @Description Method for scroll down
	 * @Author gmathavx
	 * @Since 07-Oct-2018
	 */
	public void scrollByDown() {
		this.scrollByPosition("0", "400");
	}

	/**
	 * @Description Method for capture screen
	 * @Author gmathavx
	 * @Since 04-Oct-2018
	 * @param outputFilePath
	 */
	public void captureScreen(String outputFilePath) {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scrFile, new File(outputFilePath));
		} catch (IOException ex) {
			log.error("Not able to capture screen . " + ex.getMessage());
		}
	}

	/**
	 * @Description Method to mouse over the web element
	 * @Author gmathavx
	 * @Since 07-Oct-2018
	 * @param element
	 */
	public void mouseOver(WebElement element) {
		try {
			Actions actions = new Actions(driver);
			actions.clickAndHold(element);
			actions.moveByOffset(2, 2).perform();
			actions.release().perform();
		} catch (Exception ex) {
			log.error("Mouse over operation failed. Exception : " + ex.getMessage());
			throw ex;
		}
	}

	/**
	 * @Description Method for click web element by position
	 * @Author gmathavx
	 * @Since 07-Oct-2018
	 * @param element
	 * @param width
	 * @param height
	 */
	public void clickByPosition(WebElement element, int width, int height) {
		try {
			Actions actions = new Actions(driver);
			actions.moveToElement(element, width, height);
		} catch (Exception ex) {
			log.error("Cannot click web element by position");
			throw ex;
		}
	}

	/**
	 * @Description Method for fluent wait for web element by given seconds
	 * @Author gmathavx
	 * @Since 07-Oct-2018
	 * @param element
	 * @param waitSeconds
	 */
	public void fluentWaitForElement(WebElement element, int waitSeconds) {
		FluentWait<WebDriver> fluentWaiter = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(waitSeconds)).pollingEvery(Duration.ofSeconds(5))
				.ignoring(NoSuchElementException.class);
		fluentWaiter.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				return element;
			}
		});
	}

	/**
	 * @Description Method to get the page title
	 * @Author gmathavx
	 * @Since 07-Oct-2018
	 * @return
	 */
	public String getPageTitle() {
		try {
			return driver.getTitle();
		} catch (WebDriverException ex) {
			log.error(ex.getMessage());
			throw ex;
		}
	}

	/**
	 * @Description Method to get page URL
	 * @Author gmathavx
	 * @Since 07-Oct-2018
	 * @return
	 */
	public String getPageUrl() {
		try {
			return driver.getCurrentUrl();
		} catch (WebDriverException ex) {
			log.error(ex.getMessage());
			throw ex;
		}
	}

	/**
	 * @Description Method to get window handle
	 * @Author gmathavx
	 * @Since 07-Oct-2018
	 * @return
	 */
	public String getWindowHandle() {
		return driver.getWindowHandle();
	}

	/**
	 * @Description Method to get set of window handles
	 * @Author gmathavx
	 * @Since 07-Oct-2018
	 * @return
	 */
	public Set<String> getWindowHandles() {
		return driver.getWindowHandles();
	}

	/**
	 * @Description Method to verify is element is exists or not
	 * @Author gmathavx
	 * @Since 05-Oct-2018
	 * @param element
	 * @return
	 */
	public boolean isElementExists(WebElement element) {
		try {
			if (element != null && element.isDisplayed() && element.isEnabled())
				return true;
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	/**
	 * @Description Method to verify is element is displayed or not
	 * @Author prachivx
	 * @Since Aug 13, 2018
	 * @param element
	 * @return
	 */
	public boolean isElementDisplayed(WebElement element) {
		try {
			if (element != null && element.isDisplayed())
				return true;
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	/**
	 * @Description Method to verify is element is elabled or not
	 * @Author prachivx
	 * @Since Aug 13, 2018
	 * @param element
	 * @return
	 */
	public boolean isElementEnabled(WebElement element) {
		try {
			if (element != null && element.isEnabled())
				return true;
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	/**
	 * @Description Method to clear text in web element
	 * @Author gmathavx
	 * @Since 07-Oct-2018
	 * @param element
	 */
	public void clearText(WebElement element) {
		element.clear();
	}

	/**
	 * @Description Method to get text from the web element
	 * @Author gmathavx
	 * @Since 07-Oct-2018
	 * @param String
	 */
	public String getText(WebElement element) {
		return element.getText();
	}

	/**
	 * @Description To set a value for the given element
	 * @author natarajs
	 * @since Aug 6,2018
	 * @param element
	 * @param sValue
	 */
	public void sendKeys(WebElement element, String sValue) {
		try {
			this.waitForWebElementVisible(element, 5);
			if(this.isElementExists(element)) {
				element.click();
				element.clear();
				element.sendKeys(sValue);
			}	
		} catch (NoSuchElementException e) {
			log.error(e.getMessage());
			throw new TimeoutException("NoSuchElementException in send keys");
		}catch (Exception e) {
			log.error(e.getMessage());
			throw new TimeoutException("Exception in send keys");
		}
	}

	/**
	 * @Description Method to set text to the web element
	 * @Author gmathavx
	 * @Since 07-Oct-2018
	 * @param element
	 * @param key
	 */
	public void sendKeys(WebElement element, Keys key) {
		try {
			this.waitForWebElementVisible(element, 5);
			if(this.isElementExists(element)) {
				element.click();
				element.clear();
				element.sendKeys(key);
			}			
		} catch (NoSuchElementException e) {
			log.error(e.getMessage());
			throw new TimeoutException("NoSuchElementException in send keys");
		}catch (Exception e) {
			log.error(e.getMessage());
			throw new TimeoutException("Exception in send keys");
		}
	}
		



	/**
	 * @Description Method to double click the web element
	 * @Author gmathavx
	 * @Since 07-Oct-2018
	 * @param element
	 */
	public void doubleClick(WebElement element) {
		try {
			if (isWebElementEqualToNull(element))
				throw new NoSuchElementException("Element is empty");

			if (element.isDisplayed() && element.isEnabled()) {
				Actions action = new Actions(driver);
				action.doubleClick(element).build().perform();
			}
		} catch (Exception ex) {
			log.error(ex.getMessage());
			throw ex;
		}

	}

	/**
	 * @Description Method to accept alert
	 * @Author gmathavx
	 * @Since 07-Oct-2018
	 */
	public void acceptAlert() {
		try {
			Alert alert = driver.switchTo().alert();
			alert.accept();
		} catch (NoAlertPresentException ex) {
			log.error("Alert is not present on UI or unable to accept" + ex.getStackTrace());
			throw ex;
		}
	}

	/**
	 * @Description Method to dismiss alert
	 * @Author gmathavx
	 * @Since 07-Oct-2018
	 */
	public void dismissAlert() {
		try {
			Alert alert = driver.switchTo().alert();
			alert.dismiss();
		} catch (NoAlertPresentException ex) {
			log.error("Alert is not present on UI or unable to accept" + ex.getStackTrace());
			throw ex;
		}
	}

	/**
	 * @Description Method to check number is not equal to zero
	 * @Author gmathavx
	 * @Since 07-Oct-2018
	 * @param i
	 * @return
	 */
	public boolean isNumberNotEqualToZero(int i) {
		return i != 0;
	}

	/**
	 * @Description Method to check number is equal to zero
	 * @Author gmathavx
	 * @Since 07-Oct-2018
	 * @param i
	 * @return
	 */
	public boolean isNumberEqualToZero(int i) {
		return i == 0;
	}

	/**
	 * @Description Method to check web element is not equal to null
	 * @Author gmathavx
	 * @Since 07-Oct-2018
	 * @param element
	 * @return
	 */
	public boolean isWebElementNotEqualToNull(WebElement element) {
		return element != null;
	}

	/**
	 * @Description Method to check web element is equal to null
	 * @Author gmathavx
	 * @Since 07-Oct-2018
	 * @param element
	 * @return
	 */
	public boolean isWebElementEqualToNull(WebElement element) {
		return element == null;
	}

	/**
	 * @Description Method to check list web element is not equal to zero
	 * @Author gmathavx
	 * @Since 07-Oct-2018
	 * @param element
	 * @return
	 */
	public boolean isListWebElementNotEqualToNull(List<WebElement> element) {
		return element != null;
	}

	/**
	 * @Description Method to check web element is equal to zero
	 * @Author gmathavx
	 * @Since 07-Oct-2018
	 * @param element
	 * @return
	 */
	public boolean isListWebElementEqualToNull(List<WebElement> element) {
		return element == null;
	}

	/**
	 * @Description Method to verify alert is present
	 * @Author gmathavx
	 * @Since 07-Oct-2018
	 * @return
	 */
	public boolean isAlertPresent() {
		boolean isAlertPresent = false;
		try {
			driver.switchTo().alert();
			isAlertPresent = true;
		} catch (NoAlertPresentException ex) {
			log.error("Alert is not present on UI" + ex.getStackTrace());
		}
		return isAlertPresent;
	}

	/**
	 * @Description Method to verify alert is present by given seconds with retry
	 * @Author gmathavx
	 * @Since 07-Oct-2018
	 * @param waitSeconds
	 * @return
	 */
	public boolean isAlertPresent(int waitSeconds) {
		int waitloopCount = 10;
		boolean status = false;
		while (waitloopCount != 0) {
			try {
				if (isAlertPresent()) {
					log.info("Alert Find");
					status = true;
					waitloopCount = 0;
				} else {
					if (isNumberNotEqualToZero(waitloopCount)) {
						log.info("Alert not found. Retrying one more time");
						waitForSeconds(waitSeconds);
						waitloopCount--;
					} else if (isNumberEqualToZero(waitloopCount)) {
						log.error("Alert not found");
					}
				}
			} catch (Exception ex) {
				if (isNumberNotEqualToZero(waitloopCount)) {
					log.error("Alert not found. Retrying one more time :" + ex.getMessage());
					waitForSeconds(waitSeconds);
					waitloopCount--;
				} else if (isNumberEqualToZero(waitloopCount)) {
					log.error("Alert not found : " + ex.getMessage());
				}
			}
		}
		return status;
	}

	/**
	 * @Description Method to refresh the page
	 * @Author gmathavx
	 * @Since 07-Oct-2018
	 */
	public void pageRefresh() {
		try {
			driver.navigate().refresh();
		} catch (Exception ex) {
			log.error("Webpage refresh is not happend" + ex.getStackTrace());

		}
	}

	/**
	 * @Description Method to check checkbox is selected or not
	 * @Author gmathavx
	 * @Since 07-Oct-2018
	 * @param element
	 * @return
	 */
	public boolean isCheckBoxSelected(WebElement element) {
		boolean value = false;
		try {
			value = element.isSelected();
		} catch (Exception ex) {
			log.error(ex.getMessage());
		}
		return value;
	}

	/**
	 * @Description Method to switch to frame
	 * @Author gmathavx
	 * @Since 07-Oct-2018
	 * @param element
	 */
	public void switchToFrame(WebElement element) {
		try {
			driver.switchTo().frame(element);
		} catch (Exception ex) {
			log.error(ex.getMessage());
			throw ex;
		}
	}

	/**
	 * Method to switch to default
	 * 
	 * @Description
	 * @Author gmathavx
	 * @Since 07-Oct-2018
	 */
	public void switchToDefault() {
		try {
			driver.switchTo().defaultContent();
		} catch (Exception ex) {
			log.error(ex);
			throw ex;
		}
	}

	/**
	 * @Description Method to get selected value form drop down
	 * @Author gmathavx
	 * @Since 07-Oct-2018
	 * @param element
	 * @return
	 */
	public String getSelectedValueFromDropDown(WebElement element) {
		String selectedValue = "";
		try {
			if (element.isEnabled()) {
				Select selectElement = new Select(element);
				selectedValue = selectElement.getFirstSelectedOption().getText();
			} else {
				log.warn("Element is not enabled");
			}
		} catch (Exception ex) {
			log.error(ex);
			throw ex;
		}
		return selectedValue;
	}

	/**
	 * @Description Method to get all values from drop down
	 * @Author gmathavx
	 * @Since 07-Oct-2018
	 * @param element
	 * @return
	 */
	public static List<String> getAllValuesFromDropDown(WebElement element) {
		List<String> values = new ArrayList<>();
		try {
			if (element.isEnabled()) {
				Select selectElement = new Select(element);
				List<WebElement> options = selectElement.getOptions();
				for (WebElement option : options) {
					values.add(option.getText());
				}
			}
		} catch (Exception ex) {
			log.error(ex);
			throw ex;
		}
		return values;
	}

	/**
	 * @Description Method to get attribute value from web element
	 * @Author gmathavx
	 * @Since 07-Oct-2018
	 * @param element
	 * @param attribute
	 * @return
	 */
	public String getAttributeValue(WebElement element, String attribute) {
		String value = null;
		try {
			value = element.getAttribute(attribute);
		} catch (Exception ex) {
			log.error(ex);
			throw ex;
		}
		return value;
	}

	/**
	 * @Description Method to set attribute value to the web element by using
	 *              JavascriptExecutor
	 * @Author gmathavx
	 * @Since 07-Oct-2018
	 * @param element
	 * @param attribute
	 * @param value
	 * @return
	 */
	public String setAttributeValue(WebElement element, String attribute, String value) {
		try {
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);", element, attribute, value);
		} catch (Exception ex) {
			log.error(ex);
			throw ex;
		}
		return value;
	}

	/**
	 * @Description Method to get CSS value
	 * @Author gmathavx
	 * @Since 07-Oct-2018
	 * @param element
	 * @param cssAttribute
	 * @return
	 */
	public String getCSSValue(WebElement element, String cssAttribute) {
		return element.getCssValue(cssAttribute);
	}
	
	
	 /**
	  * Check that secret password length is valid
	  *
	  * @param key -characters secret password
	  * @return TRUE if valid, FALSE otherwise
	  */
	 public static boolean isKeyLengthValid(String key) {
	     return key.length() == 16 || key.length() == 24 || key.length() == 32;
	 }
	 
	 /**
	  * Convert Bytes to HEX
	  *
	  * @param bytes Bytes array
	  * @return String with bytes values
	  */
	 
	 public static String bytesToHex(byte[] bytes) {
	    char[] hexArray = "0123456789ABCDEF".toCharArray();
	     char[] hexChars = new char[bytes.length * 2];
	     for (int j = 0; j < bytes.length; j++) {
	         int v = bytes[j] & 0xFF;
	         hexChars[j * 2] = hexArray[v >>> 4];
	         hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	     }
	     return new String(hexChars);
	 }
	 
	 /**
	  * Encrypt input text by AES-128-CBC algorithm
	  *
	  * @param plainText Text for encryption
	  * @param secretKey Secret key's length must be 128, 192 or 256 bits
	  * @return Encoded string or NULL if error
	  */
	    @SuppressWarnings({ "deprecation" })
	   /* public String encrypt(String plainText, String secretKey) {
//	          String secretKey = "EADQACOEAutomate";
	     String initVector = null;
	     String result=null;
	     try {
	         // Check secret length
	         if (!isKeyLengthValid(secretKey)) {
	             throw new Exception("Secret key's length must be 128, 192 or 256 bits");
	         }
	
	         //SecureRandom secureRandom = new SecureRandom();
	         byte[] initVectorBytes = new byte[16 / 2];
	         //secureRandom.nextBytes(initVectorBytes);
	         initVector = bytesToHex(initVectorBytes);
	         initVectorBytes = initVector.getBytes("UTF-8");
	
	         IvParameterSpec ivParameterSpec = new IvParameterSpec(initVectorBytes);
	         SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes("UTF-8"), "AES");
	
	         Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	         cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
	
	         byte[] encrypted = cipher.doFinal(plainText.getBytes("UTF-8"));
	         ByteBuffer byteBuffer = ByteBuffer.allocate(initVectorBytes.length + encrypted.length);
	         byteBuffer.put(initVectorBytes);
	         byteBuffer.put(encrypted);
	         result = Base64.encode(byteBuffer.array());
	       } catch (Throwable t) {
	    	   log.info("encrypt() got exception. " + t.getMessage());
	       }
	     return result;
	 }*/
	
	 /**
	  * Decrypt encoded text by AES-128-CBC algorithm
	  *
	  * @param cipherText Encrypted text
	  * @param secretKey Secret key's length must be 128, 192 or 256 bits
	  * @return Decoded string or NULL if error
	  */
	   /* public String decrypt(String cipherText, String secretKey) {
	          String result =null;
	     try {
	          //String secretKey = "EADQACOEAutomate";
	         // Check secret length
	         if (!isKeyLengthValid(secretKey)) {
	             throw new Exception("Secret key's length must be 128, 192 or 256 bits");
	         }
	         byte[] encrypted = Base64.decode(cipherText);
	         IvParameterSpec ivParameterSpec = new IvParameterSpec(encrypted, 0, 16);
	         SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes("UTF-8"), "AES");
	         Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	         cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
	         result = new String(cipher.doFinal(encrypted, 16, encrypted.length - 16));
	     } catch (Throwable t) {
	         log.info("decrypt() got exception. " + t.getMessage());
	     }
	     return result;
	 }*/
	    
	    
		/**
		 * 
		 * @Description Method to used to execute AutoItScript
		 * @Author rkanurix
		 * @Since Aug 29, 2019
		 * @param filePath
		 * @throws IOException
		 */
		public void executeAutoItScript(String[] filePath) throws IOException {
			try {
				String path=filePath[0].toString()+" "+filePath[1].toString();
				try {
					Process process = Runtime.getRuntime().exec(path);					
					process.waitFor();
					try {
						Runtime.getRuntime().exec("taskkill /F /IM " + filePath[0].toString());
					} catch (Throwable th) {
						log.info(th.getMessage());
						th.getMessage();
					}
				} catch (IOException ioex) {
					log.info(ioex.getMessage());
				} catch (InterruptedException e) {
					log.info(e.getMessage());
				}
			} catch (Exception e) {
				log.info(e.getMessage());
			}
		}

	/**
	 * Returns current machine user email id
	 */
	public String getCurrentUserEmail() {
	    String cmd = "powershell \"Add-Type -AssemblyName System.DirectoryServices.AccountManagement;[System.DirectoryServices.AccountManagement.UserPrincipal]::Current.EmailAddress;\"";
	    String userEmail = "";
	    if (!System.getProperty("os.name").toLowerCase().startsWith("win")) 
	    { 
	    	return userEmail;
	    }
	    Runtime rt = Runtime.getRuntime();
	    Process pr;
	    try {
	        pr = rt.exec(cmd);
	        pr.waitFor();
	        BufferedReader bf = new BufferedReader(new InputStreamReader(pr.getInputStream()));
	        String nextLine = null;
	        while (true) {
	        	nextLine = bf.readLine();
		        if (nextLine == null) break;
		        userEmail = nextLine;
		    }
		    bf.close();
		} catch (Exception e) {
		    log.info("Failed to get logged in user email: " + e.getMessage());
		}
		return userEmail;
	}
	
	/**
	 * @Description Method for click on web element
	 * @Author dinesh2x
	 * @Since 10-Dec-2019
	 * @param element	 
	 * @throws Exception 
	 */
	public void Click(WebElement element, int seconds) throws Exception {
		try
		{			
			WebDriverWait wait = new WebDriverWait(driver,seconds);
			Actions actions = new Actions(driver);				
			actions.moveToElement(wait.until(ExpectedConditions.visibilityOf(element))).click().perform();			
		} catch (Exception ex) {
			log.error("Unable to click web element");
			throw ex;
		}
	}
			
	/**
	 * @Description Method for Press Enter
	 * @Author dinesh2x
	 * @Since 11-Dec-2019 
	 * @throws Exception 
	 */
	public void pressEnter() throws Exception {
		try {			
			Actions action = new Actions(driver);
			action = action.sendKeys(Keys.RETURN);
			action.perform();
			log.info("Pressed Enter key");			
		} catch (Exception ex) {
			log.error("Unable to Press an Enter Key");
			throw ex;
		}
	}
	
	/**
	 * @Description Method to get text from the element by JavascriptExecutor
	 * @Author dinesh2x
	 * @Since 04-Oct-2018
	 * @param element
	 * @param value
	 */
	public void GetTextFromTextbox(WebElement uiObject) {
		try
		{
		  JavascriptExecutor jse = (JavascriptExecutor) driver;
		  String text = (String)jse.executeScript("return arguments[0].value;",uiObject);
		  log.info("TextBox Value is " +text);		 								
		}
		catch (Exception ex) {
			log.error("Unable to Get the Text box value");
			throw ex;
			}
	 }
	
	/**
	 * @Description Method for Press ARROW Down key
	 * @Author dinesh2x
	 * @Since 11-Dec-2019 
	 * @throws Exception 
	 */
	public void sendArrowDownKey(WebElement element) throws Exception {
		try {			
			Actions action = new Actions(driver); 
			action.sendKeys(element,Keys.ARROW_DOWN).perform();	
			log.info("Pressed Enter key");
		} catch (Exception ex) {
			log.error("Unable to Press an Arrow Down Key");
			throw ex;
		}
	}
	
	/**
	 * @Description Method for Switch To New Window
	 * @Author dinesh2x
	 * @Since 12-Dec-2019 
	 * @throws pageTitle 
	 * 
	 */
	public void switchToNewWindow() throws Exception {
		try {	
			
			String parentWindow = driver.getWindowHandle();
			Set<String> handles =  driver.getWindowHandles();
			for(String windowHandle  : handles)
			{
			    if(!windowHandle.equals(parentWindow))
			   {
			     driver.switchTo().window(windowHandle);		 
			     log.info("Switched the Window Focus to New Window");
			   }
			}
		} catch (Exception ex) {
			log.error("Unable to Switch child Window");
			throw ex;
		}
	}
	
	/**
	 * @Description Method for Switch To New Window
	 * @Author dinesh2x
	 * @Since 16-Dec-2019 
	 * @throws pageTitle 
	 * 
	 */
	public void switchToWindowByPageTitle(String pagetitle) throws Exception {
		try {	
			
			String parentWindow = driver.getWindowHandle();
			Set<String> handles =  driver.getWindowHandles();
			for(String windowHandle  : handles)
			{
			    if(!windowHandle.equals(parentWindow))
			   {
			     driver.switchTo().window(pagetitle);		 
			     log.info("Switched the Window Focus to " +pagetitle);
			   }
			}
		} catch (Exception ex) {
			log.error("Unable to Switch child Window");
			throw ex;
		}
	}
	
	/**
	 * @Description Method for Switch To 'n' Window
	 * @Author dinesh2x
	 * @Since 16-Dec-2019 
	 * @throws pageTitle 
	 * 
	 */
	public void switchToWindowByWindowCount(int i) throws Exception {
		try {	
			log.info("Number of Window Opened " +driver.getWindowHandles().size());	
		    //for(String Window  : driver.getWindowHandles()) 				
			{					
			    ArrayList<String> newWindow = new ArrayList<String>(driver.getWindowHandles());
			    driver.switchTo().window(newWindow.get(i));
			    log.info("Switched Focus to Window number "+i+" and Current Window ID is "+driver.getWindowHandle());			    				   
			}
		} catch (Exception ex) {
			log.error("Unable to Switch child Window");
			throw ex;
		}
	}	
	
	/**
	 * @Description Method for Switch To Parent Window
	 * @Author dinesh2x
	 * @Since 12-Dec-2019 
	 * @throws Exception 
	 */
	public void switchToParentWindow() throws Exception {
		try {	
		 	String parentWindow = driver.getWindowHandle();
		 	Set<String> handles =  driver.getWindowHandles();
			for(String windowHandle  : handles)
			   if(windowHandle.equals(parentWindow))
				 {
				   driver.switchTo().window(parentWindow);  			 
				   log.info("Switched Focus to Parent Window");
			     }			  
		} catch (Exception ex) {
			log.error("Unable to Switch Parent Window");
			throw ex;
		}
	}
	
	/**
	 * @Description Method to check vertical scroll is present or not
	 * @Author nshenoyx
	 * @Since 10-Dec-2019
	 * @return True if Vertical scroll is present else returns false
	 */
	public boolean checkPresenceOfVerticalScroll() {
		boolean value = false;
		try {
			JavascriptExecutor verticalScrollBarPresent = (JavascriptExecutor) driver;
			value = (Boolean) verticalScrollBarPresent.executeScript( "return document.documentElement.scrollHeight>document.documentElement.clientHeight;");
		} catch (Exception err) {
			err.printStackTrace();
		}
		return value;
	}
	
	/**
	 * @Description Method to check horizontal scroll is present or not
	 * @Author nshenoyx
	 * @Since 10-Dec-2019
	 * @return True if Horizontal scroll is present else returns false
	 */
	public boolean checkPresenceOfHorizontalScroll() {
		boolean value = false;
		try {
			JavascriptExecutor horizontalScrollBarPresent = (JavascriptExecutor) driver;
			value = (Boolean) horizontalScrollBarPresent.executeScript("return document.documentElement.scrollWidth>document.documentElement.clientWidth;");
		} catch (Exception err) {
			err.printStackTrace();
		}
		return value;
	}
	
	/**
	 * @Description Method to FindWebElements 
	 * @Author nshenoyx
	 * @Since 11-Dec-2019
	 * @param Pass Webelement of UI,like id,name,xpath,linktext,partiallinktext,tagname,classname,css
	   And value for the parameter should be equals to seperated with type eg:"Xpath=//*[]","ID=id of the element"
	 * @return ListofElements if present else returns empty list
	 */
	public List<WebElement> FindWebElements(String locator) {
		List<WebElement> listOfElements = null;
		
		try {
			int index = locator.indexOf("=");
		    String elementType = locator.substring(0, index);
		    String objectData = locator.substring(index + 1);
		    switch(elementType.toLowerCase())
		    {
		    case "id":
		    	listOfElements = driver.findElements(By.id(objectData));
				break;
			case "xpath":
				listOfElements = driver.findElements(By.xpath(objectData));
				break;
			case "name":
				listOfElements = driver.findElements(By.name(objectData));
				break;
			case "linktext":
				listOfElements = driver.findElements(By.linkText(objectData));
				break;
			case "partiallinktext":
				listOfElements = driver.findElements(By.partialLinkText(objectData));
				break;
			case "tagname":
				listOfElements = driver.findElements(By.tagName(objectData));
				break;
			case "classname":
				listOfElements = driver.findElements(By.className(objectData));
				break;
			case "css":
				listOfElements = driver.findElements(By.cssSelector(objectData));
				break;
		    }
		}
		catch (Exception err) {
			err.printStackTrace();
		}
		return listOfElements;
	}
	

	/**
	 * @Description Method to carryout keyboard action 
	 * @Author nshenoyx
	 * @Since 11-Dec-2019
	 * @param WebElement -the element on which the keyboard action needs to be carried out 
     * @param key -The key action eg:ENTER,ALT,SHIFT etc

	 */
	public void click(WebElement element, Keys key) { 
		try {
			element.sendKeys(key);
		}
		catch (Exception err) {
			err.printStackTrace();
		}
	}
	
	/**
	 * @Description Method to FindWebElement 
	 * @Author nshenoyx
	 * @Since 13-Dec-2019
	 * @param Pass Webelement of UI,like id,name,xpath,linktext,partiallinktext,tagname,classname,css
	   And value for the parameter should be equals to seperated with type eg:"Xpath=//*[]","ID=id of the element"
	 * @return WebElement
	 */
	public WebElement FindWebElement(String locator) {
	 WebElement element = null;
		
		try {
			int index = locator.indexOf("=");
		    String elementType = locator.substring(0, index);
		    String objectData = locator.substring(index + 1);
		    switch(elementType.toLowerCase())
		    {
		    case "id":
		    	element = driver.findElement(By.id(objectData));
				break;
			case "xpath":
				element = driver.findElement(By.xpath(objectData));
				break;
			case "name":
				element = driver.findElement(By.name(objectData));
				break;
			case "linktext":
				element = driver.findElement(By.linkText(objectData));
				break;
			case "partiallinktext":
				element = driver.findElement(By.partialLinkText(objectData));
				break;
			case "tagname":
				element = driver.findElement(By.tagName(objectData));
				break;
			case "classname":
				element = driver.findElement(By.className(objectData));
				break;
			case "css":
				element = driver.findElement(By.cssSelector(objectData));
				break;
		    }
		}
		catch (Exception err) {
			err.printStackTrace();
		}
		return element;
	}
	/**
	 * @Description Method to list the running processes  
	 * @Author nshenoyx
	 * @Since 19-Dec-2019
	 */ 
	public static void listRunningProcesses() {
		  String[] processes = new String[] {"chromedriver.exe","IEDriver.exe","geckodriver.exe","MicrosoftWebDriver.exe","safaridriver.exe" };
	    
	    try {
	      String line;
	      Process p = Runtime.getRuntime().exec
	    		    (System.getenv("windir") +"\\system32\\"+"tasklist.exe");
	      BufferedReader input = new BufferedReader
	          (new InputStreamReader(p.getInputStream()));
	      while ((line = input.readLine()) != null) {
	          System.out.println("running processess:" +line);
	          for(String processname : processes) {
	        	  if (line.contains(processname)) {
	        		 killProcess(processname);
	        	  }
	          }
               
	      }
	    }
	    catch (Exception err) {
	      err.printStackTrace();
	    }
	  }
 
 /**
	 * @Description Method to kill the process 
	 * @Author nshenoyx
	 * @Since 19-Dec-2019
	 * @param service name - the name of the process that needs to be killed 
	 */ 
		 public static void killProcess(String servicename) {
			 String KILL = "taskkill /F /IM ";
			 try {
				Runtime.getRuntime().exec(KILL + servicename);
			} catch (IOException e) {
				e.printStackTrace();
			}
			 
		 }
}

	
