/*
* Copyright (c) 2018 EBS Automation Team. All rights reserved.
*/
package com.infy.autoqa.karate.PageClasses;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @Description
 * @Author gmathavx
 * @Since 14-Sep-2018
 */

public class LoginPage {

	public LoginPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//input[@placeholder='Email']")
	private WebElement externalCustomerEmailIdTextBox;

	@FindBy(xpath = "//input[@placeholder='Password']")
	private WebElement externalCustomerPasswordTextBox;

	@FindBy(xpath = "//span[text()='Log in']/parent::button")
	private WebElement externalCustomerLoginButton;

	@FindBy(xpath = "//a[contains(text(),'Alternate sign in')]")
	public WebElement externalAlternateSignIn;

	@FindBy(xpath = "//input[@name='UserID']")
	private WebElement externalUserName;

	@FindBy(xpath = "//input[@name='Password']")
	private WebElement externalPassword;

	@FindBy(xpath = "//input[@value='Sign In']")
	private WebElement externalSignIn;

	@FindBy(xpath = "//a[contains(text(), 'Switch to Lightning Experience')]")
	public WebElement switchToLightningExperienceLink;

	public void setExternalCustomerEmailId(String sEmailId) {
		externalCustomerEmailIdTextBox.sendKeys(sEmailId);
	}

	public void setExternalCustomerPassword(String sPassword) {
		externalCustomerPasswordTextBox.sendKeys(sPassword);
	}

	public void clickExternalCustomerLoginButton() {
		externalCustomerLoginButton.click();
	}

	public void clickAlternateSignIn() {
		externalAlternateSignIn.click();
	}

	public void setExternalUsername(String sEmailId) {
		externalUserName.sendKeys(sEmailId);
	}

	public void setExternalPassword(String sPassword) {
		externalPassword.sendKeys(sPassword);
	}

	public void clickSignIn() {
		externalSignIn.click();
	}

	public void clickSwitchToLightningExperienceLink() {
		switchToLightningExperienceLink.click();
	}
}
