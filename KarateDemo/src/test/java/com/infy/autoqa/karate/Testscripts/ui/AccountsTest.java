/*
* Copyright (c) 2018 EBS Automation Team. All rights reserved.
*/
package com.infy.autoqa.karate.Testscripts.ui;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.infy.autoqa.karate.BusinessFunction.ui.AccountsOperations;
//import com.intel.ebsqa.ccp.BusinessFunction.ui.ExternalApplicationNavigation;
import com.infy.autoqa.karate.BusinessFunction.ui.Navigation;
import com.infy.autoqa.karate.DataClass.AccountsData;
import com.infy.autoqa.karate.DataClass.AccountsData.AccountsDetails;
import com.infy.autoqa.karate.PageClasses.AccountsPageClass;
import com.infy.autoqa.karate.enums.AccountEnum.AccountViews;
import com.infy.autoqa.karate.enums.CommonEnum.ApplicationType;
import com.infy.autoqa.karate.enums.CommonEnum.AutomationPrefixes;
import com.infy.autoqa.karate.enums.CommonEnum.BooleanValues;
import com.infy.autoqa.karate.enums.CommonEnum.GlobalNavigator;
import com.infy.autoqa.karate.enums.CommonEnum.GlobalSearchResultDescription;
import com.infy.autoqa.karate.enums.CommonEnum.ObjectHome;
//import com.intel.ebsqa.ccp.enums.ExternalUserEnum.ExternalUserSubMenu;
import com.infy.autoqa.karate.helperclass.*;
import com.infy.autoqa.karate.utility.MongoDBRepository;
import com.infy.autoqa.exceptions.TimeOutException;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

/**
 * @Author gmathavx
 * @Since 14-Sep-2018
 */

public class AccountsTest extends TestBase {
	MongoDBRepository<AccountsTest> objMongoDBContext;
	DBCollection objAccountsCollData;
	AccountsData objAccountsData;
	AccountsDetails objAccountsDetails;
	Navigation objNavigation;
	AccountsOperations objAccountsOperations;
	//ExternalApplicationNavigation objExternalApplicationNavigation;
	AccountsPageClass objAccountsPageClass;

	public AccountsTest() {
		objMongoDBContext = new MongoDBRepository<AccountsTest>("Accounts");
		objAccountsCollData = objMongoDBContext._collection;
		objAccountsData = new AccountsData();
		objAccountsDetails = objAccountsData.new AccountsDetails();
		objAccountsPageClass = new AccountsPageClass();
	}

	@BeforeTest(alwaysRun = true)
	public void BeforeTestcase() {		
		objMongoDBContext = new MongoDBRepository<AccountsTest>("Accounts");
		objAccountsCollData = objMongoDBContext._collection;
		objAccountsData = new AccountsData();
		objAccountsDetails = objAccountsData.new AccountsDetails();
	}

	@BeforeMethod(alwaysRun = true)
	public void methodObjects() {
		//sfcommonObj.setUp(com.salesforce.lpop.framework.markers.ApplicationType.SALES_LEX);
		objNavigation = new Navigation();
		objAccountsOperations = new AccountsOperations();
		//objExternalApplicationNavigation = new ExternalApplicationNavigation();
	}

	/**
	 * Read data from database
	 * 
	 * @Author gmathavx
	 * @Since 21-Sep-2018
	 * @param testCaseID The id of the test case selected for execution test
	 */
	public void ReadDataFromMongoCollection(String testCaseID) {
		BasicDBObject search = new BasicDBObject();
		search.put("TestCaseID", testCaseID);
		DBCursor dbc = objAccountsCollData.find(search);
		while (dbc.hasNext()) {
			DBObject mongoObject = dbc.next();
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE);
			Gson gson = gsonBuilder.create();
			objAccountsData = gson.fromJson(mongoObject.toString(), AccountsData.class);

			BasicDBList PMB = (BasicDBList) mongoObject.get("AccountsDetails");
			for (int i = 0; i < PMB.size(); i++) {
				BasicDBObject PMBobj = (BasicDBObject) PMB.get(i);
				if (PMBobj.getString("Environment").equals(configObj.getEnvironment())) {
					objAccountsDetails = gson.fromJson(PMBobj.toString(), AccountsDetails.class);
				}
			}
		}
	}

	/**
	 * Validates Partner fund account values in corresponding to selected Channel
	 * Program of DCF
	 * 
	 * @Author gmathavx
	 * @Since 14-Sep-2018
	 * @throws InterruptedException Thrown when a thread is waiting, sleeping, or
	 *                              otherwise occupied, and the thread is
	 *                              interrupted, either before or during the
	 *                              activity.
	 * @throws TimeOutException     Exception thrown when a blocking operation times
	 *                              out.
	 * @throws IOException          Exception thrown when there has been an
	 *                              Input/Output (usually when working with files)
	 *                              error.
	 */
	@Test(description = "Validates Partner fund account values in corresponding to selected Channel Program of DCF", groups = {
			"smoke" })
	public void TC1() throws InterruptedException, TimeOutException, IOException {
						
		String methodName = new Object() {
		}.getClass().getEnclosingMethod().getName();
		ReadDataFromMongoCollection(methodName);
		String accountName = sfcommonObj.getRandomName(AutomationPrefixes.ACCOUNT);
		sfcommonObj.loginToApplicationAs(ApplicationType.CCP_INTERNAL_CUSTOMER.getDescription(), objAccountsData.role);
		sfcommonObj.pageAppNavigation(GlobalNavigator.ACCOUNTS);
		
		
		/*
		objAccountsOperations.verifyAccountHomePage();
		sfcommonObj.clickOnOHButton(ObjectHome.NEW);
		objAccountsOperations.clickOnComarketingButton();
		objAccountsOperations.clickOnNextButton();
		objAccountsOperations.verifyAccountCreationPage();
		objAccountsOperations.enterAccountFormDetails(objAccountsDetails, accountName);
		objAccountsOperations.verifyParentFundAccount(objAccountsDetails.parentFundAccount);
		objAccountsOperations.clickOnOHCancelButton(ObjectHome.CANCEL);
		objNavigation.logoutFromApplication();*/
	}

}	

	
	


