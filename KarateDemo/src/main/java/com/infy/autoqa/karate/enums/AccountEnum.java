package com.infy.autoqa.karate.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Account Page Enum in the application
 * 
 * @author mohseenx
 * @Since 11-Jan-2019
 */
public class AccountEnum {
	/**
	 * @Description Enum for Account User Page Fields
	 * @author mohseenx
	 * @Since 11-Jan-2019
	 */
	@AllArgsConstructor
	@Getter
	public enum AccountUserFieldsName {
	ACCOUNT("Account"), USER("User");

		private final String description;
	}

	/**
	 * @Description Enum for Account List Page Fields
	 * @author mohseenx
	 * @Since 11-Jan-2019
	 */
	@AllArgsConstructor
	@Getter
	public enum AccountListFieldsName {
		ACCOUNT_NAME("Account Name"), CHANNEL_PROGRAM("Channel Program"), PARENT_FUND_ACCOUNT("Parent Fund Account"),
		PARENT_ACCOUNT("Parent Account"), ACCOUNT_RECORD_TYPE("Account Record Type"), ALL("All");
		private final String description;
	}

	/**
	 * Enum for storing view names in Account page
	 * 
	 * @author ubijux
	 * @Since 11-Jan-2019
	 */
	@AllArgsConstructor
	@Getter
	public enum AccountViews {
		CO_MARKETING_ACCOUNT_VIEW("Co-Marketing Account View"), ALL_PARTNER_ACCOUNTS("All Partner Accounts");

		private final String description;
	}

	/**
	 * Enum for storing column names in Account grid
	 * 
	 * @author ubijux
	 * @Since 18-Jan-2019
	 */
	@AllArgsConstructor
	@Getter
	public enum AccountColumnNames {
		CHANNEL_PROGRAM("Channel Program");

		private final String description;
	}
	
	/**
	 * Enum for Account Name.
	 * 
	 * @author dravix
	 * @Since 21-May-2019
	 */
	@AllArgsConstructor
	@Getter
	
	public enum AccountName {
		ACCOUNT_NAME("Auto Dell CCF Activity APJ"), ;
		private final String description;
	}
}