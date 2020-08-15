package com.infy.autoqa.listeners;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;

import com.infy.autoqa.wrappers.LoggerWrapper;



public class customActionListener implements WebDriverEventListener {
	
	static LoggerWrapper log = new LoggerWrapper(customActionListener.class);
	
	//This Method Is Called Before Navigate To Page
	public void beforeNavigateTo(String url, WebDriver driver) {		
		log.info("Navigating to application " + url);
	}
	
	//This Method Is Called After Navigate To Page
	public void afterNavigateTo(String url, WebDriver driver) {
	}
	
	//This Method Is Called Before Navigate Page To Back
	public void beforeNavigateBack(WebDriver driver) {
	}
	
	//This Method Is Called After Navigate Page To Back
	public void afterNavigateBack(WebDriver driver) {
	}

	//This Method Is Called Before Navigate Page To Forward
	public void beforeNavigateForward(WebDriver driver) {
	}

	//This Method Is Called After Navigate Page To Forward
	public void afterNavigateForward(WebDriver driver) {		
		log.info("Navigated forward from current page [" + driver.getCurrentUrl() + "]");
	}

	//This Method Is Called Before Page Refresh
	public void beforeNavigateRefresh(WebDriver driver) {
		log.info("Refreshing current page [" + driver.getCurrentUrl() + "]");
	}
	
	//This Method Is Called After Page Refresh
	public void afterNavigateRefresh(WebDriver driver) {
	}
	
	//This Method Is Called Before WebElement Value Change
	public void beforeChangeValueOf(WebElement element, WebDriver driver) {
	}

	//This Method Is Called After WebElement Value Change
	public void afterChangeValueOf(WebElement element, WebDriver driver) {
		log.debug( element.getText() + " value is set - " + element.getClass().getName());
	}

	//This Method Is Called Before WebElement Click
	public void beforeClickOn(WebElement element, WebDriver driver) {
	}

	//This Method Is Called After WebElement Click
	public void afterClickOn(WebElement element, WebDriver driver) {
		log.info("Clicked on - " + element.getClass().getName());
	}
		
	//This Method Is Called Before WebElement Find
	public void beforeFindBy(By by, WebElement element, WebDriver driver) {

	}

	//This Method Is Called After WebElement Find
	public void afterFindBy(By by, WebElement element, WebDriver driver) {
		log.info("Element found : " + by.toString());
	}
	
	//This Method Is Called Before Alert PopUp Accept
	public void beforeAlertAccept(WebDriver driver) {
	}
	
	//This Method Is Called After Alert PopUp Accept
	public void afterAlertAccept(WebDriver driver) {
		log.info("Alert Accepted");
	}
	
	//This Method Is Called Before Alert PopUp Dismiss
	public void beforeAlertDismiss(WebDriver driver) {
	}
	
	//This Method Is Called After Alert PopUp Dismiss
	public void afterAlertDismiss(WebDriver driver) {
	}
	
	//This Method Is Called Before Script Execute
	public void beforeScript(String script, WebDriver driver) {
	}

	//This Method Is Called After Script Execute
	public void afterScript(String script, WebDriver driver) {
		log.debug("JavaScript executed - " + script);
	}

	//This Method Is Called Before WebElement Value Change
	public void beforeChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
	}	
	
	//This Method Is Called After WebElement Value Change
	public void afterChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
		log.info("Element value set to: " + keysToSend[0]);
	}	

	//This Method Is Called Before Getting WebElement Text 
	public void beforeGetText(WebElement element, WebDriver driver) {
	}
	
	//This Method Is Called After Getting WebElement Text 
	public void afterGetText(WebElement element, WebDriver driver, String text) {	
	}

	//This Method Is Called Before Switching To Window 
	public void beforeSwitchToWindow(String windowName, WebDriver driver) {		
	}	
	
	//This Method Is Called After Switching To Window
	public void afterSwitchToWindow(String windowName, WebDriver driver) {
		log.info("Current window Switched to : " + windowName.toString());
	}
	
	//This Method Is Called Before Selecting DropDown Value
	public void beforeSelectDropDownValue(WebElement element, WebDriver driver, String value) {
	}
	
	//This Method Is Called After Selecting DropDown Value
	public void afterSelectDropDownValue(WebElement element, WebDriver driver, String value) {
		log.info("Selected DropDown value : " + value.toString());
	}
	
	//This Method Is Called Before Focus On WebElement
	public void beforeFocus(WebElement element, WebDriver driver) {
	}
	
	//This Method Is Called After Focus On WebElement
	public void afterFocus(WebElement element, WebDriver driver) {
	}
	
	//This Method Is Called Before Mouse Over On WebElement
	public void beforeMouseOver(WebElement element, WebDriver driver) {
	}
	
	//This Method Is Called After Mouse Over On WebElement
	public void afterMouseOver(WebElement element, WebDriver driver) {
	}
	
	//This Method Is Called Before Screenshot Taken On WebElement
	public <X> void beforeGetScreenshotAs(OutputType<X> target) {		
	}

	//This Method Is Called After Screenshot Taken On WebElement
	public <X> void afterGetScreenshotAs(OutputType<X> target, X screenhot) {
		log.info("Screenshot taken at : " + target);
	}	

	//This Method Is Called On Any Exception
	public void onException(Throwable error, WebDriver driver) {
		log.error("Exception Occured : " + error);
	}
	
}
