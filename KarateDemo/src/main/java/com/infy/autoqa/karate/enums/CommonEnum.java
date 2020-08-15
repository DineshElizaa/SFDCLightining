
/*
* Copyright (c) 2018 EBS Automation Team. All rights reserved.
*/
package com.infy.autoqa.karate.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Common Enum in the application
 * 
 * @Author gmathavx
 * @Since 16-Nov-2018
 */

public class CommonEnum {

	/**
	 * Enum for Global Navigator
	 * 
	 * @author gmathavx Since 16-Nov-2018
	 * @ModifiedAuthor mohseenx Since 26-01-2019
	 */
	@AllArgsConstructor
	@Getter
	public enum GlobalNavigator {
	BUDGETS("Budgets"), CAMPAIGNS("Campaigns"), COMARKETING_ACTIVITIES("Co-Marketing Activities"), CLAIMS("Claims"),
	CASES("Cases"), HOME("Home"), ACCOUNT_USERS("Account Users"), ACCOUNTS("Accounts"),
	APPROVAL_REQUESTS("Approval Requests"), ACCOUNT_TRANSFER("Account Transfer"), BANKS("Banks"), CHATTER("Chatter"),
	DASHBOARDS("Dashboards"), FILES("Files"), FORECASTS("Forecasts"), GROUPS("Groups"), NOTES("Notes"),

	PARTNER_FUND_ALLOCATIONS("Partner Fund Allocations"), TASKS("Tasks"), REPORTS("Reports"),
	CCF_CAMPAIGN_NAME("CCF_Campain_Name_"), DCF_CAMPAIGN_NAME("DCF_Campain_Name_"), CCF("campaignCreationCCF"),
	DCF("campaignCreationCCF"), CAMPAIGNNAME("Test_Auto_Campaign_"), CLAIM_REVIEWS("Claim Reviews"), REJECT("Reject"),QUOTE("quote");

		private final String description;
	}

	/**
	 * Enum for Global Search Result Description
	 * 
	 * @author gmathavx Since 16-Nov-2018
	 */
	@AllArgsConstructor
	@Getter
	public enum GlobalSearchResultDescription {

		ACCOUNT("Account"), ACCOUNT_USER("Account User"), BANK("Bank"), BUDGET("Budget"), CAMPAIGN("Campaign"),
		CASE("Case"), COMARKETING_ACTIVITY("Co-Marketing Activity"), CONTACT("Contact"), OPPORTUNITY("Opportunity"),
		CLAIM("Claim"), PRODUCT("Product"), REPORT("Report"), USER("User");

		private final String description;
	}

	/**
	 * Enum for Application Type
	 * 
	 * @author gmathavx Since 16-Nov-2018
	 */
	@AllArgsConstructor
	@Getter
	public enum ApplicationType {

		CCP_INTERNAL_CUSTOMER("cpp"), CCP_EXTERNAL_CUSTOMER("ccpExternalCustomer");

		private final String description;
	}

	/**
	 * @Description Enum for boolean values
	 * @author vveeranx Since 20-Dec-2018
	 */
	@AllArgsConstructor
	@Getter
	public enum BooleanValues {
		TRUE("true"), FALSE("false");

		private final String description;
	}

	/**
	 * Enum for User Role Types
	 * 
	 * @author skatoch Since 27-Dec-2018
	 */
	@AllArgsConstructor
	@Getter
	public enum UserRoleType {
		CCF_DCF("ccf_dcf"),
		Data_Center_Co_MarketingFund("Data Center Co-Marketing Fund"),
		Client_Co_Marketing_Fund("Client Co-Marketing Fund"),
		Service_Provider_Co_Marketing_Fund("Service Provider Co-Marketing Fund");
		
		private final String description;
	}

	/**
	 * 
	 * @author gmathavx
	 * @since Jan 17, 2019
	 */
	@AllArgsConstructor
	@Getter
	public enum ActionButton {
		APPROVE("Approve"), REJECT("Reject");

		private final String description;
	}

	/**
	 * Enum for get prefix name for automation
	 * 
	 * @author gmathavx
	 * @since Jan 18, 2019
	 */
	@AllArgsConstructor
	@Getter
	public enum AutomationPrefixes {
		BUDGET("AutoBudget"), CLAIM("AutoClaim"), RECEIPT("AutoReceipt"), ACCOUNT("AutoAccount"), BANK("AutoBank"),
		CAMPAIGN("AutoCampaign");

		private final String description;
	}

	/**
	 * Enum for get date format
	 * 
	 * @author gmathavx
	 * @since Jan 18, 2019
	 */
	@AllArgsConstructor
	@Getter
	public enum CustomDateFormat {
		MMDDYYYHHMMSS("MM_dd_yyyy_HH_mm_ss"), MMDDYYYY("MM/dd/yyyy"),MMDDYYYYHHMMSS("MMddyyyyhhmmss");

		private final String description;
	}

	/**
	 * Enum for get time zone
	 * 
	 * @author vveeranx
	 * @since Jan 21, 2019
	 */
	@AllArgsConstructor
	@Getter
	public enum TimeZone {
		AMERICAORLOSANGELES("America/Los_Angeles");

		private final String description;
	}

	/**
	 * Enum for Global Action Label Names
	 * 
	 * @author mohseenx Since 28-Nov-2018
	 */
	@AllArgsConstructor
	@Getter
	public enum ObjectHome {

		NEW("New"), CANCEL("Cancel"), SKIP("Skip"), EDIT("Edit");

		private final String description;
	}

	/**
	 * Enum for Record Action Label name
	 * 
	 * @author gmathavx
	 * @since Feb 17, 2019
	 */
	@AllArgsConstructor
	@Getter
	public enum RecordHome {

		CANCEL_CAMPAIGN("Cancel Campaign"),
		SKIP("Skip"),
		EDIT("Edit");
		private final String description;
	}
	/**
	 * Enum for List View
	 * 
	 * @author csingamx
	 * @since Mar 28, 2019
	 */

	@AllArgsConstructor
	@Getter
	public enum ListView {
		CLAIM_REVIEW("Co-Marketing Claim Review Cases"),CCP_FINANCE_MANAGER_QUEUE("CCP Finance Manager Queue"),ALL_BUDGETS("All Budgets"),
		MY_BUDGETS("My Budgets"),RECENTLY_VIEWED("Recently Viewed"),ALL_PARTNER_MARKETING_BUDGETS("All Partner Marketing Budgets"),
		BUDGETS_REQUIRING_APPROVAL("Budgets Requiring Approval"),CCF_BUDGETS("CCF Budgets"),DCF_BUDGETS("DCF Budgets"),Budgets_FY_2019("Budgets FY 2019"),
		TOP_5_BUDGETS("Top 5 Budgets"),
		ALL_BUDGETS_EXT("All  Budgets");
		private final String description;
		
	}
	
	/**
	 * @Description Enum for tab names values
	 * @author prachivx Since 20-Dec-2018
	 */
	@AllArgsConstructor
	@Getter
	public enum tabNamesValues {
		REALTED_TAB("Related"), DETAILS_TAB("Details");

		private final String description;
	}
	
	}

