package com.infy.autoqa.wrappers;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.testng.ITestResult;

import com.mysql.cj.core.util.StringUtils;

public class LoggerWrapper {

	private Logger logger = null;
	private static int log_index = 1;
	private static String PROJECT_DIR = "";
	private static String TC_NAME = "";
	private static String LOG_FILE_NAME = "";
	private static String LOG_FOLDER_NAME = "";
	private static String EVENT_TIME = "";
	private static String MACHINE_NAME = "";
	private static String USER_NAME = "";
	private static String logfile_name = "";
	private static String TEST_RESULTS_FOLDER = "";
	private static String RALLY_APPLICATION_NAME = "";
	private static boolean CUSTOM_LOGGERS = false;
	private static boolean RALLY_TC_UPDATE = false;
	private static boolean RALLY_BATCH_UPDATE = false;
	private static String RALLY_API_KEY = null;
	private static DataOutputStream dos = null;	
	private static boolean RECORD_TEST_EXECUTION_FAILED = false;

	@SuppressWarnings("unchecked")
	public LoggerWrapper(Class clazz)
	{
		this.logger = Logger.getLogger(clazz);
	}

	public void fatal(Object message) {
		if(this.getCustomLogger()) {
			this.writeLogText(message.toString());			
		}else {
			logger.fatal(message);
		}		
	}

	public void fatal (Object message, Throwable t) 
	{
		if(this.getCustomLogger()) {
			this.writeErrorText(message, t);
		}else {
			logger.fatal(message, t);
		}		
	}

	public void error (Object message)
	{
		if(this.getCustomLogger()) {
			this.writeLogText(message.toString());
		}else {
			logger.error(message);
		}		
	}

	public void error (Object message, Throwable t)
	{
		if(this.getCustomLogger()) {
			this.writeErrorText(message, t);
		}else {
			logger.error(message, t);
		}		
	}

	public void warn (Object message)
	{
		if(this.getCustomLogger()) {
			this.writeLogText(message.toString());
		}else {
			logger.warn(message);
		}		
	}

	public void warn (Object message, Throwable t)
	{
		if(this.getCustomLogger()) {
			this.writeErrorText(message, t);
		}else {
			logger.warn(message, t);
		}		
	}

	public void info (Object message)
	{
		if(this.getCustomLogger()) {
			this.writeLogText(message.toString());
		}else {
			logger.info(message);
		}		
	}

	public void info (Object message, Throwable t)
	{
		if(this.getCustomLogger()) {
			this.writeLogText(message.toString());
		}else {
			logger.info(message, t);
		}		
	}

	public void debug (Object message)
	{
		if(this.getCustomLogger()) {
			this.writeLogText(message.toString());
		}else {
			logger.debug(message);
		}		
	}

	public void debug (Object message, Throwable t)
	{
		if(this.getCustomLogger()) {
			this.writeErrorText(message, t);
		}else {
			logger.debug(message, t);
		}		
	}

	public void trace (Object message, Throwable t)
	{
		if(this.getCustomLogger()) {
			this.writeErrorText(message, t);
		}else {
			logger.trace(message, t);
		}		
	}

	public void trace (Object message)
	{
		if(this.getCustomLogger()) {
			this.writeLogText(message.toString());
		}else {
			logger.trace(message);
		}		
	}


	public boolean isDebugEnabled ()
	{
		return logger.isDebugEnabled();
	}

	
	public void setConfigurations(String projectDir, String customLogger, String rallyTCUpdate, String rallyBatchUpdate , String currentuserrallyApiKey, String facelessuserrallyApiKey, String setRallyApplicationName,String recordExecutionFlag) {
		PROJECT_DIR=projectDir;
		setEventTime();
		setCustomLogger(customLogger);
		setRallyTCUpdate(rallyTCUpdate);
		setRallyBatchUpdate(rallyBatchUpdate);
		setCurrentUserRallyAPIKey(currentuserrallyApiKey);
		setFacelessUserRallyAPIKey(facelessuserrallyApiKey);
		setRallyApplicationName(setRallyApplicationName);
		setCreateTestResultsFolder();
		setRecordTestExecutionFlag(recordExecutionFlag);
	}	
	
	private void setProjectDir(String projectDir, String customLogger) {
		PROJECT_DIR=projectDir;
		setCustomLogger(customLogger);
	}
	
	public String getProjectDir() {
		return PROJECT_DIR;
	}
	
	private void setEventTime() {
		if(StringUtils.isNullOrEmpty(EVENT_TIME)) {
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
			EVENT_TIME= sdf.format(cal.getTime());
		}
	}
	
	public String getEventTime() {
		return EVENT_TIME;
	}	
	
	private void setCustomLogger(String customLogger) {
		if(customLogger.trim().toLowerCase().equalsIgnoreCase("true")) {
			CUSTOM_LOGGERS=true;
		}
	}
	
	public boolean getCustomLogger() {
		return CUSTOM_LOGGERS;
	}
	
	private void setRallyTCUpdate(String value) {
		if(value.trim().toLowerCase().equalsIgnoreCase("true")) {
			RALLY_TC_UPDATE=true;
		}
	}
	
	public boolean getRallyTCUpdateValue() {
		return RALLY_TC_UPDATE;
	}
	
	private void setRallyBatchUpdate(String value) {
		if(value.trim().toLowerCase().equalsIgnoreCase("true")) {
			RALLY_BATCH_UPDATE=true;
		}
	}
	
	public boolean getRallyBatchUpdateValue() {
		return RALLY_BATCH_UPDATE;
	}
	
	private void setRallyAPIKey(String rallyApiKey) {
		RALLY_API_KEY=rallyApiKey;
	}
	
	public String getRallyAPIKey() {
		return RALLY_API_KEY;
	}
	
	private void setRallyApplicationName(String rallyApplicationName) {
		RALLY_APPLICATION_NAME=rallyApplicationName;
	}
	
	public String getRallyApplicationName() {
		return RALLY_APPLICATION_NAME;
	}	
	
	private void setCreateTestResultsFolder() {
		if(getCustomLogger()) {		
			File file = new File(getProjectDir());
			if (!file.exists()) {
				new File(getProjectDir()).mkdir();
				TEST_RESULTS_FOLDER=getProjectDir()+"TestResultsFolder_"+getEventTime();
				new File(TEST_RESULTS_FOLDER).mkdir();
			}else {
				TEST_RESULTS_FOLDER=getProjectDir()+"TestResultsFolder_"+getEventTime();
				new File(TEST_RESULTS_FOLDER).mkdir();
			}
		}else {
			File file = new File(getProjectDir());
			if (!file.exists()) {
				new File(getProjectDir()).mkdir();
			}
			TEST_RESULTS_FOLDER=getProjectDir();
		}
	}
	
	public String getTestResultsFolder() {
		return TEST_RESULTS_FOLDER;
	}
	
	public void setTCName(ITestResult result) {
		TC_NAME=result.getName();
		setCreateTCFolder(result);
	}	
	
	public String getTCName() {
		return TC_NAME;
	}
	
	private static String CURRENT_USER_RALLY_API_KEY = null;
	public void setCurrentUserRallyAPIKey(String rallyApiKey) {
		CURRENT_USER_RALLY_API_KEY=rallyApiKey;
	}	

	public String getCurrentUserRallyAPIKey() {
		return CURRENT_USER_RALLY_API_KEY;
	}
	
	private static String FACELESS_USER_RALLY_API_KEY = null;	
	public void setFacelessUserRallyAPIKey(String rallyApiKey) {
		FACELESS_USER_RALLY_API_KEY=rallyApiKey;
	}
	
	public String getFacelessUserRallyAPIKey() {
		return FACELESS_USER_RALLY_API_KEY;
	}
	
	private void setCreateTCFolder(ITestResult result) {
		setMachineName();
		setUserName();
		if(getCustomLogger()) {			
			String[] array = result.getTestClass().getName().split("\\."); 
			String className=array[array.length-1].toString();
			File file = new File(getTestResultsFolder()+"\\"+className);
			if (file.exists()) {
				LOG_FOLDER_NAME=getTestResultsFolder()+"\\"+className+"\\"+result.getName();
				new File(LOG_FOLDER_NAME).mkdir();
				setLogFileName();
			}else {
				new File(getTestResultsFolder()+"\\"+className).mkdir();
				LOG_FOLDER_NAME=getTestResultsFolder()+"\\"+className+"\\"+result.getName();
				new File(LOG_FOLDER_NAME).mkdir();
				setLogFileName();
			}
			this.writeLogText("***********************Test Execution Started***********************");
			this.writeLogText("* \t TEST NAME : " + result.getName());
			this.writeLogText("* \t USER NAME : " + getUserName());
			this.writeLogText("* \t Machine Name  : " + getMachineName());
			this.writeLogText("* \t TEST STARTED ON : " + getDate() + " in IST ");			
			this.writeLogText("*******************************************************************");
		}else {
			LOG_FOLDER_NAME=getTestResultsFolder();
			setLogFileName();
//			this.writeLogText("***********************Test Execution Started***********************");
//			this.writeLogText("* \t TEST NAME : " + result.getName());
//			this.writeLogText("* \t USER NAME : " + getUserName());
//			this.writeLogText("* \t Machine Name  : " + getMachineName());
//			this.writeLogText("* \t TEST STARTED ON : " + getDate() + " in IST ");			
//			this.writeLogText("*******************************************************************");
		}
	}	

	public String getLogFolder() {
		return LOG_FOLDER_NAME;
	}	
	
	private static void setMachineName() {
		try {
			MACHINE_NAME = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			System.out.println(e.getMessage());
		}
	}
	
	private String getMachineName() {
		return MACHINE_NAME;
	}
	
	private static void setUserName() {
		USER_NAME = System.getProperty("user.name");
	}
	
	private String getUserName() {
		return USER_NAME;
	}
	
	private void setLogFileName() {
		LOG_FILE_NAME=getLogFolder()+"\\SmartLogger.txt";
	}
	
	public String getLogFileName() {
		return LOG_FILE_NAME;
	}
	
    private String getDate()
    {
    	Date date = new Date();
    	return formatDateToString(date, "yyyy-MM-dd HH:mm:ss.SSS", "IST");
    }

    private static String formatDateToString(Date date, String format, String timeZone) {
		if (date == null) return null;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		if (timeZone == null || "".equalsIgnoreCase(timeZone.trim())) {
			timeZone = Calendar.getInstance().getTimeZone().getID();
		}
		sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
		return sdf.format(date);
	}
    
	public void writeLogText(String msg) {
		String logMsg = "[EventTime: " + getTime() + "] -> " + msg;
		try {
			if(new File(getTestResultsFolder()).exists()) {
				if (new File(getLogFileName()).exists()) {
					Long fileSize_in_Bytes = new File(getLogFileName()).length();
					Long fileSize_in_KB = fileSize_in_Bytes / 1024;
					Long fileSize_in_MB = fileSize_in_KB / 1024;

					if (fileSize_in_MB > 5) {
						logfile_name =getLogFileName()+ "_" + log_index+ ".txt";
						log_index++;

						dos = new DataOutputStream(new FileOutputStream(logfile_name, true));
						dos.writeBytes(logMsg);
						dos.writeBytes(System.getProperty("line.separator"));
					} else {
						dos.writeBytes(logMsg);
						dos.writeBytes(System.getProperty("line.separator"));
					}
				} else {
					if(!StringUtils.isNullOrEmpty(getLogFileName())) {
						dos = new DataOutputStream(new FileOutputStream(getLogFileName(), true));
						dos.writeBytes(logMsg);
						dos.writeBytes(System.getProperty("line.separator"));
					}else {
						dos = new DataOutputStream(new FileOutputStream(getTestResultsFolder()+"\\SmartLogger.txt", true));
						dos.writeBytes(logMsg);
						dos.writeBytes(System.getProperty("line.separator"));
					}
					
				}
			}			

		} catch (IOException e) {
			error(e);
		} catch (Exception ex) {
			error(ex);
		}
	}	
	
	public void writeErrorText(Throwable Obj) {
		String logMsg = "[EventTime: " + getTime() + "] -> " + Obj.getMessage();
		try {
			if(new File(getTestResultsFolder()).exists()) {
				if (new File(logfile_name).exists()) {
					StringWriter sWriter = new StringWriter();
					PrintWriter pw = new PrintWriter(sWriter);
					Obj.printStackTrace(pw);
					
					Long fileSize_in_Bytes = new File(getLogFileName()).length();
					Long fileSize_in_KB = fileSize_in_Bytes / 1024;
					Long fileSize_in_MB = fileSize_in_KB / 1024;

					if (fileSize_in_MB > 5) {
						logfile_name =getLogFileName()+ "_" + log_index+ ".txt";
						log_index++;

						dos = new DataOutputStream(new FileOutputStream(logfile_name, true));
						dos.writeBytes(logMsg);
						dos.writeBytes(System.getProperty("line.separator"));
					} else {
						dos.writeBytes(logMsg);
						dos.writeBytes(System.getProperty("line.separator"));
					}
				} else {
					if(!StringUtils.isNullOrEmpty(getLogFileName())) {
						dos = new DataOutputStream(new FileOutputStream(getLogFileName(), true));
						dos.writeBytes(logMsg);
						dos.writeBytes(System.getProperty("line.separator"));
					}else {
						dos = new DataOutputStream(new FileOutputStream(getTestResultsFolder()+"\\SmartLogger.txt", true));
						dos.writeBytes(logMsg);
						dos.writeBytes(System.getProperty("line.separator"));
					}
					
				}
			}			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void writeErrorText(Object message, Throwable Obj) {
		String logMsg = "[EventTime: " + getTime() + "] -> " +message.toString()+ " -> "+ Obj.getMessage();
		try {
			if(new File(getTestResultsFolder()).exists()) {
				if (new File(logfile_name).exists()) {
					StringWriter sWriter = new StringWriter();
					PrintWriter pw = new PrintWriter(sWriter);
					Obj.printStackTrace(pw);
					
					Long fileSize_in_Bytes = new File(getLogFileName()).length();
					Long fileSize_in_KB = fileSize_in_Bytes / 1024;
					Long fileSize_in_MB = fileSize_in_KB / 1024;

					if (fileSize_in_MB > 5) {
						logfile_name =getLogFileName()+ "_" + log_index+ ".txt";
						log_index++;

						dos = new DataOutputStream(new FileOutputStream(logfile_name, true));
						dos.writeBytes(logMsg);
						dos.writeBytes(System.getProperty("line.separator"));
					} else {
						dos.writeBytes(logMsg);
						dos.writeBytes(System.getProperty("line.separator"));
					}
				} else {
					if(!StringUtils.isNullOrEmpty(getLogFileName())) {
						dos = new DataOutputStream(new FileOutputStream(getLogFileName(), true));
						dos.writeBytes(logMsg);
						dos.writeBytes(System.getProperty("line.separator"));
					}else {
						dos = new DataOutputStream(new FileOutputStream(getTestResultsFolder()+"\\SmartLogger.txt", true));
						dos.writeBytes(logMsg);
						dos.writeBytes(System.getProperty("line.separator"));
					}
					
				}
			}	

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private static String getTime() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		return sdf.format(cal.getTime());
	}
	

	/**
	 * @return the rECORD_TEST_EXECUTION_FAILED
	 */
	public boolean isRecordTestExecutionFlag() {
		return RECORD_TEST_EXECUTION_FAILED;
	}

	/**
	 * @param rECORD_TEST_EXECUTION_FAILED the rECORD_TEST_EXECUTION_FAILED to set
	 */
	private void setRecordTestExecutionFlag(String value) {

		if(value.trim().toLowerCase().equalsIgnoreCase("true")) {
			RECORD_TEST_EXECUTION_FAILED=true;
		}
	
	}
}
