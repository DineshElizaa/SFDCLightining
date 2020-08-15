package com.infy.autoqa.listeners;
import java.util.concurrent.TimeUnit;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.infy.autoqa.wrappers.LoggerWrapper;
import com.infy.autoqa.wrappers.RallyAPIConnector;
import com.infy.autoqa.karate.helperclass.TestExecutionRecorder;

public class customEventListener implements ITestListener {

	static LoggerWrapper log = new LoggerWrapper(customEventListener.class);
	static RallyAPIConnector rallyAPIConnector=new RallyAPIConnector();

	//This Method is Called On Every Test Start
	public void onTestStart(ITestResult result) {
		//System.out.println("The name of the test case Started is :" + result.getName());	
		log.setTCName(result);
		log.info("*******************************************************************");
		log.info("TEST NAME - " + result.getName());
		log.info("TEST PARAMETERS - " + result.getParameters().toString());
		log.info("*******************************************************************");
		try {
			if(log.isRecordTestExecutionFlag()) {
			TestExecutionRecorder.startRecording(result.getName());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//This Method is Called On Every Test Passed
	public void onTestSuccess(ITestResult result) {
		//System.out.println("The name of the test case Passed is :" + result.getName());
		long seconds = TimeUnit.MILLISECONDS.toSeconds(result.getEndMillis() - result.getStartMillis());
		log.info("*******************************************************************");
		log.info("* \t TEST EXECUTION RESULT :\t PASSED");
		log.info("* \t EXECUTED IN: "+ seconds +" Seconds");
		log.info("*******************************************************************");
		if(log.getRallyTCUpdateValue()) {
			rallyAPIConnector.UpdateTestCaseResultInRally(result.getName(),"Pass", String.valueOf(seconds));
		}
		
	}
	//This Method is Called On Every Test Failure
	public void onTestFailure(ITestResult result) {
		//System.out.println("The name of the test case failed is :" + result.getName());
		long seconds = TimeUnit.MILLISECONDS.toSeconds(result.getEndMillis() - result.getStartMillis());
		log.info("*******************************************************************");
		log.info("* \t TEST EXECUTION RESULT :\t FAILED");
		log.info("* \t EXECUTED IN : "+ seconds +" Seconds");
		log.info("*******************************************************************");
		if(log.getRallyTCUpdateValue()) {
			rallyAPIConnector.UpdateTestCaseResultInRally(result.getName(), "Fail", String.valueOf(seconds));
		}
		
	}
	//This Method is Called On Every Test Skipped
	public void onTestSkipped(ITestResult result) {
		//System.out.println("The name of the test case Skipped is :" + result.getName());
		log.info("*******************************************************************");
		log.info("* \t TEST SKIPPED : " + result.getTestName());	
		log.info("*******************************************************************");
		
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {		
		
	}
	//This Method is Called On Before All Tests Start
	public void onStart(ITestContext context) {
//		log.info("***********************Test Execution Started***********************");
//		log.info("* \t HOST NAME        : " + context.getHost() + "\t *");
//		log.info("* \t TEST STARTED ON  : " + context.getStartDate() + "\t *");
//		log.info("* \t OUTPUT DIRECTORY : " + context.getOutputDirectory() + "\t *");
		
	}
	//This Method is Called On After All Tests Start
	public void onFinish(ITestContext context) {		
		if(log.getRallyBatchUpdateValue()) {	
			rallyAPIConnector.UpdateBatchTCResultsInRall(context);
		}
		log.info("***********************Test Execution Completed***********************");
		log.info("***************************Test Summary******************************");
		log.info("TOTAL TEST CASES PASSED :" + context.getPassedTests().size());
		log.info("TOTAL TEST CASES FAILED :" + context.getFailedTests().size());
		log.info("TOTAL TEST CASES SKIPPED:" + context.getSkippedTests().size());
		log.info("*********************************************************************");
		
	}
}
