package com.infy.autoqa.karate.helperclass;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigHelper {

	Properties configFile;

	/**
	 * Constructor which load the data from property file for any given path
	 * 
	 * @param sPath
	 */
	public ConfigHelper(String sPath) {
		configFile = new Properties();
		try {
			// configFile.load(this.getClass().getClassLoader().getResourceAsStream(sPath));
			configFile.load(new FileInputStream(sPath));
			System.out.println("Configuration file loaded successfully..");
		} catch (IOException e) {
			System.out.println("Error in loading the config file from location : " + sPath);
			e.printStackTrace();
		}
	}

	/**
	 * Method to return value for the given key
	 * 
	 * @param sKey
	 * @return
	 */
	private String getValue(String sKey) {
		return configFile.getProperty(sKey);
	}

	// ----------------Following method represents corresponding variables in config
	// file -----------------

	/**
	 * Returns name of the browser to be used for execution
	 * 
	 * @return
	 */
	public String getApplicationName() {
		return getValue("APPLICATION_NAME");
	}

	/**
	 * Returns name of the browser to be used for execution
	 * 
	 * @return
	 */
	public String getBrowserName() {
		return getValue("browser_name");
	}

	/**
	 * Returns the path of CHROME driver
	 * 
	 * @return
	 */
	public String getChromeDriverPath() {
		return getValue("chrome_driver_path");
	}

	/**
	 * Returns name of the browser to be used for execution
	 * 
	 * @return
	 */
	public String getPlatformName() {
		return getValue("platform");
	}

	/**
	 * Return Implicit wait time
	 * 
	 * @return
	 */
	public int getImplicitWaitTime() {
		return Integer.parseInt(getValue("implicit_wait_time"));
	}

	/**
	 * Returns application URL to be launched
	 * 
	 * @return
	 */
	public String getApplicationURL() {
		String url = null;
		if (getValue("environment").equals("DEV")) {
			url = getValue("application_url");
		} else if (getValue("environment").equals("DEVINT")) {
			url = getValue("application_url_int");
		} else if (getValue("environment").equals("DEVEXT")) {
			url = getValue("application_url_ext");
		} else if (getValue("environment").equals("QA")) {
			url = getValue("application_url_int");
		} /*
			 * else if(getValue("environment").equals("QAEXT")) {
			 * url=getValue("external_application_url_qa"); }
			 */

		return url;
	}

	/**
	 * @Description
	 * @Author gmathavx
	 * @Since 14-Sep-2018
	 * @param applicationType
	 * @return
	 */
	public String getApplicationURL(String applicationType) {
		String url = null;
		if (getValue("environment").equals("DEVINT") && applicationType.equals("ccpExternalCustomer")) {
			url = getValue("external_application_url_int");
		} else if (getValue("environment").equals("QA") && applicationType.equals("ccpExternalCustomer")) {
			url = getValue("external_application_url_qa");
		}
		return url;
	}

	public String getTestDBServerIP() {
		return getValue("SFDC_COMMON_DB_IP");
	}

	public String getTestDBPORTNo() {
		return getValue("SFDC_COMMON_DB_PORT");
	}

	public String getTestDBUserName() {
		return getValue("SFDC_COMMON_DB_USERNAME");
	}

	public String getTestDBPassword() {
		return getValue("SFDC_COMMON_DB_PASSWORD");
	}

	public String getTestDBName() {
		return getValue("SFDC_COMMON_DB_NAME");
	}

	public String getEnvironment() {
		return getValue("environment");
	}

	public String getConfigKeyValue(String apiUrlKey) {
		return getValue(apiUrlKey);
	}

	public String getAutoITFileUploadExePath() {
		return getValue("AutoITFileUploadExePath");
	}

	public String getFileNameToAttach() {
		return getValue("FileNameToAttach");
	}

	public String getFileDownLoadPath() {
		return getValue("fileDownloadPath");
	}

	public String getAutoITBulkFileUploadExePath() {
		return getValue("AutoITBulkFileUploadExePath");
	}

	public String getAuthTokenURL() {
		return getValue("API_TOKEN_URL");
	}

	public String getResultFolderPath() {
		return getValue("resultFolderPath");
	}

	public String getDevintURL() {
		return getValue("application_url_devint");
	}

	public String getRecordFormFilePath() {
		return getValue("recordFormFilePath");
	}
}