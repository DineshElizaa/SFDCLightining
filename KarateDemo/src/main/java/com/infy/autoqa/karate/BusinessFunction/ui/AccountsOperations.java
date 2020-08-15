package com.infy.autoqa.karate.BusinessFunction.ui;

import org.testng.Assert;

import com.infy.autoqa.exceptions.TimeOutException;
import com.infy.autoqa.karate.BusinessFunction.ui.AccountsOperations;
import com.infy.autoqa.karate.BusinessFunction.ui.Navigation;
//import com.intel.ebsqa.ccp.DataClass.PartnerFundClaimData;
//import com.intel.ebsqa.ccp.DataClass.PartnerFundClaimData.PartnerFundClaimDetails;
import com.infy.autoqa.karate.PageClasses.AccountsPageClass;
import com.infy.autoqa.karate.helperclass.TestBase;


/**
 * Account Operations class
 * 
 * @Author gmathavx
 * @Since 14-Sep-2018
 */

public class AccountsOperations extends TestBase {

	AccountsPageClass objAccountsPageClass = null;
	AccountsOperations objAccountsOperations;
	/*
	 * PartnerFundClaimDetails objPartnerFundClaimDetails; PartnerFundClaimData
	 * objPartnerFundClaimData;
	 */
	Navigation objNavigation;	

	public AccountsOperations() {
		objAccountsPageClass = new AccountsPageClass();
		
	}
	
	/**
	 * Method for click on new button
	 * 
	 * @Author gmathavx
	 * @Since 21-Sep-2018
	 * @throws TimeOutException Exception thrown when a blocking operation times
	 */
	public void clickOnNewButton() throws TimeOutException {
		try {
			seleniumObj.waitForElement(objAccountsPageClass.newButton, 10, 10);
			Assert.assertTrue(seleniumObj.isElementExists(objAccountsPageClass.newButton), "New button does not exist");
			objAccountsPageClass.clickOnNewButton();
			log.info("Clicked on new button");
		} catch (Exception ex) {
			Assert.fail("Not able to click on new button " + ex.getMessage());
		}
	}
	
}
