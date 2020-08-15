package com.infy.autoqa.wrappers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

import com.google.gson.JsonObject;
import com.mysql.cj.core.util.StringUtils;
import com.rallydev.rest.RallyRestApi;
import com.rallydev.rest.request.CreateRequest;
import com.rallydev.rest.request.GetRequest;
import com.rallydev.rest.request.QueryRequest;
import com.rallydev.rest.util.Fetch;
import com.rallydev.rest.response.CreateResponse;
import com.rallydev.rest.response.GetResponse;
import com.rallydev.rest.response.QueryResponse;
import com.rallydev.rest.util.QueryFilter;



public class RallyAPIConnector {
	
	static LoggerWrapper log = new LoggerWrapper(RallyAPIConnector.class);
	public final String host = "https://rally1.rallydev.com";
	static SeleniumWrapper sw=new SeleniumWrapper();
	
	/**
	 * Update TC Results in Rally in batch Wise
	 * 
	 * @param ITestContext 
	 * @param rallyApiKey
	 */
	public void UpdateBatchTCResultsInRall(ITestContext context) {
		try {
			this.UpdatePassedTestCaseResultsInRally(context);
			this.UpdateFailedTestCaseResultsInRally(context);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Update All Passed TC Results in Rally in batch Wise
	 * 
	 * @param ITestContext 
	 * @param rallyApiKey
	 */
	@SuppressWarnings("unused")
	private void UpdatePassedTestCaseResultsInRally(ITestContext context) throws IOException {
	  	RallyRestApi restApi=null;
 	   	String result=null;
 	   	String testCaseId=null;
 	   	String applicationName = log.getRallyApplicationName();
 	   	String workspaceRef=null;
 	   	String userRef=null; 	   	
		try {
			userRef=GetUserRef(applicationName);	
			workspaceRef=GetWorkspaceRef(this.getRallyAPIKey(), applicationName);
			
			if(!StringUtils.isNullOrEmpty(applicationName) && !StringUtils.isNullOrEmpty(workspaceRef)&& !StringUtils.isNullOrEmpty(userRef)) {
				restApi=this.ConnectRally(host, this.getRallyAPIKey());
				restApi.setApplicationName(applicationName);
				
		        //update all passed test results in rally
		        Collection<ITestNGMethod> allPassedMethodsCollection=context.getPassedTests().getAllMethods();
				List<ITestNGMethod> allPassedMethods=new ArrayList<ITestNGMethod>(allPassedMethodsCollection);
				if (context.getPassedTests().getAllResults().size() > 0) {
		            for (ITestNGMethod method : allPassedMethods) {
		            	result="Pass";
		               long end = Long.MIN_VALUE;
		               long start = Long.MAX_VALUE;
		               long startMS=0;
		               for (ITestResult testResult : context.getPassedTests().getResults(method)) {
		                   if (testResult.getEndMillis() > end) {
		                       end = testResult.getEndMillis()/1000;
		                   }
		                   if (testResult.getStartMillis() < start) {
		                       startMS = testResult.getStartMillis();
		                       start =startMS/1000;
		                   }                            
		                   long executionTime=end - start;
		                   log.info("For method "+method.getMethodName()+" the execution Time is  "+ executionTime);
		                   testCaseId=method.getMethodName();
			       	        // Query for Test Case to which we want to add results
			       	        QueryRequest testCaseRequest = new QueryRequest("TestCase");
			       	        testCaseRequest.setWorkspace(workspaceRef);
			       	        testCaseRequest.setFetch(new Fetch("FormattedID","Name"));
			       	        testCaseRequest.setQueryFilter(new QueryFilter("FormattedID", "=", testCaseId));
			       	        
			       	        QueryResponse testCaseQueryResponse = restApi.query(testCaseRequest);
			       	        if (testCaseQueryResponse.getTotalResultCount() == 0) {
			       	        	log.info("Unable to Find TC Id '"+testCaseId+"' in Rally. Reason either \n (1) TC  is not exists in Rally Workspace '"+applicationName+"' \n OR (2) The User '"+this.getRallyAPIKey()+"' may not have write access on Rally Workspace.  Please ensure on it.");
			       	        }
			       	        else 
			       	        {
			       	        	JsonObject testCaseJsonObject = testCaseQueryResponse.getResults().get(0).getAsJsonObject();
			       			    String testCaseRef = testCaseQueryResponse.getResults().get(0).getAsJsonObject().get("_ref").getAsString();
			       			    //Add a Test Case Result
		       			    	String getDate=getDate();
						    	String Notes="The Test Case Id : '"+testCaseId+"' has passed through Automation Run on '"+getDate+"' in IST. \n "
						    			+ "The Result is '"+result+"'.";
		       			        JsonObject newTestCaseResult = new JsonObject();
		       			        newTestCaseResult.addProperty("Build", "ThroughAutomationRun_"+getRandomNumber(3));
		       			        newTestCaseResult.addProperty("Date", getDate);
		       			        newTestCaseResult.addProperty("Verdict", result);
		       			        newTestCaseResult.addProperty("Duration", executionTime);
		       			        newTestCaseResult.addProperty("Tester", userRef);
		       			        newTestCaseResult.addProperty("Notes", Notes);
		       			        newTestCaseResult.addProperty("TestCase", testCaseRef);	     
		       			        newTestCaseResult.addProperty("Workspace", workspaceRef);
		       			        CreateRequest createRequest = new CreateRequest("testcaseresult", newTestCaseResult);
		       			        CreateResponse createResponse = restApi.create(createRequest);
		       			        if (createResponse.wasSuccessful()) 
		       			        {	
		       			        	log.info("For The TC Id '"+testCaseId+"', the result '"+result+"' has been updated in Rally through jSmart Library.");
	//	       			            addAttachment(restApi, createResponse.getObject().get("_ref").getAsString(), currentUserRef); //workspaceRef
		       			        }
		       			        else {
		       			        	String[] createErrors;
		       			        	createErrors = createResponse.getErrors();
		       			         log.info("Error occurred creating while updating Test Case Results in Rally for TCId "+testCaseId+". Please check Users '"+this.getRallyAPIKey()+"' Access on Rally Workspace. User Must have Read & Write access in Rally Workspace "+applicationName);
		       			        	for (int i = 0; i < createErrors.length; i++) {
		       			        		log.info(createErrors[i]);
		       			        	}
		       			        }			       			    	
			       	        }
		               }
		           }
		       }
			}
			else {
				log.info("Unable to update TC results in Rally. Please check the parameter that you are passing.");
			}      
		}catch(Exception e) {
			log.info("Exception in Method UpdatePassedTestCaseResultsInRally() -> "+e.getMessage());
		}	
		finally {
			//Release all resources
		    restApi.close();
		}		
	}
	
	/**
	 * Update All Failed TC Results in Rally in batch Wise
	 * 
	 * @param ITestContext 
	 * @param rallyApiKey
	 */
	@SuppressWarnings("unused")
	private void UpdateFailedTestCaseResultsInRally(ITestContext context) throws IOException {
	  	RallyRestApi restApi=null;
 	   	String result=null;
 	   	String testCaseId=null;
 	   	String applicationName = log.getRallyApplicationName();
 	   	String workspaceRef=null;
 	   	String userRef=null;
 	   	
		try {
			userRef=GetUserRef(applicationName);	
			workspaceRef=GetWorkspaceRef(this.getRallyAPIKey(), applicationName);
			
			if(!StringUtils.isNullOrEmpty(applicationName) && !StringUtils.isNullOrEmpty(workspaceRef)&& !StringUtils.isNullOrEmpty(userRef)) {
				restApi=this.ConnectRally(host, this.getRallyAPIKey());
				restApi.setApplicationName(applicationName);
				
		        //update all passed test results in rally
				Collection<ITestNGMethod> allFailedMethodsCollection=context.getFailedTests().getAllMethods();
				List<ITestNGMethod> allFailedMethods=new ArrayList<ITestNGMethod>(allFailedMethodsCollection);
				if (context.getFailedTests().getAllResults().size() > 0) {
		            for (ITestNGMethod method : allFailedMethods) {
		            	result="Fail";
		               long end = Long.MIN_VALUE;
		               long start = Long.MAX_VALUE;
		               long startMS=0;
		               for (ITestResult testResult : context.getPassedTests().getResults(method)) {
		                   if (testResult.getEndMillis() > end) {
		                       end = testResult.getEndMillis()/1000;
		                   }
		                   if (testResult.getStartMillis() < start) {
		                       startMS = testResult.getStartMillis();
		                       start =startMS/1000;
		                   }                            
		                   long executionTime=end - start;
		                   log.info("For method "+method.getMethodName()+" the execution Time is  "+ executionTime);
		                   testCaseId=method.getMethodName();
			       	        // Query for Test Case to which we want to add results
			       	        QueryRequest testCaseRequest = new QueryRequest("TestCase");
			       	        testCaseRequest.setWorkspace(workspaceRef);
			       	        testCaseRequest.setFetch(new Fetch("FormattedID","Name"));
			       	        testCaseRequest.setQueryFilter(new QueryFilter("FormattedID", "=", testCaseId));
			       	        
			       	        QueryResponse testCaseQueryResponse = restApi.query(testCaseRequest);
			       	        if (testCaseQueryResponse.getTotalResultCount() == 0) {
			       	        	log.info("Unable to Find TC Id '"+testCaseId+"' in Rally. Reason either \n (1) TC  is not exists in Rally Workspace '"+applicationName+"' \n OR (2) The User '"+this.getRallyAPIKey()+"' may not have write access on Rally Workspace.  Please ensure on it.");
			       	        }
			       	        else 
			       	        {
			       	        	JsonObject testCaseJsonObject = testCaseQueryResponse.getResults().get(0).getAsJsonObject();
			       			    String testCaseRef = testCaseQueryResponse.getResults().get(0).getAsJsonObject().get("_ref").getAsString();
			       			    //Add a Test Case Result
		       			    	String getDate=getDate();
						    	String Notes="The Test Case Id : '"+testCaseId+"' has passed through Automation Run on '"+getDate+"' in IST. \n "
						    			+ "The Result is '"+result+"'.";
		       			        JsonObject newTestCaseResult = new JsonObject();
		       			        newTestCaseResult.addProperty("Build", "ThroughAutomationRun_"+getRandomNumber(3));
		       			        newTestCaseResult.addProperty("Date", getDate);
		       			        newTestCaseResult.addProperty("Verdict", result);
		       			        newTestCaseResult.addProperty("Duration", executionTime);
		       			        newTestCaseResult.addProperty("Tester", userRef);
		       			        newTestCaseResult.addProperty("Notes", Notes);
		       			        newTestCaseResult.addProperty("TestCase", testCaseRef);	     
		       			        newTestCaseResult.addProperty("Workspace", workspaceRef);
		       			        CreateRequest createRequest = new CreateRequest("testcaseresult", newTestCaseResult);
		       			        CreateResponse createResponse = restApi.create(createRequest);
		       			        if (createResponse.wasSuccessful()) 
		       			        {	
		       			        	log.info("For The TC Id '"+testCaseId+"', the result '"+result+"' has been updated in Rally through jSmart Library.");
	//	       			            addAttachment(restApi, createResponse.getObject().get("_ref").getAsString(), currentUserRef); //workspaceRef
		       			        }
		       			        else {
		       			        	String[] createErrors;
		       			        	createErrors = createResponse.getErrors();
		       			         log.info("Error occurred creating while updating Test Case Results in Rally for TCId "+testCaseId+". Please check Users '"+this.getRallyAPIKey()+"' Access on Rally Workspace. User Must have Read & Write access in Rally Workspace "+applicationName);
		       			        	for (int i = 0; i < createErrors.length; i++) {
		       			        		log.info(createErrors[i]);
		       			        	}
		       			        }			       			    	
			       	        }
		               }
		           }
		       }
			}
			else {
				log.info("Unable to update TC results in Rally. Please check the parameter that you are passing.");
			}      
		}catch(Exception e) {
			log.info("Exception in Method UpdateFailedTestCaseResultsInRally() -> "+e.getMessage());
		}	
		finally {
			//Release all resources
		    restApi.close();
		}		
	}

	/**
	 * Update Test Results in Rally in TC wise
	 * 
	 * @param testCaseId 
	 * @param rallyApiKey
	 * @param result
	 * @param duration
	 */
	public void UpdateTestCaseResultInRally(String testCaseId, String result, String duration) {
 	   	RallyRestApi restApi=null;
 	   	String applicationName = log.getRallyApplicationName();
 	   	String workspaceRef=null;
 	   	String userRef=null;
		try {
			userRef=GetUserRef(applicationName);
			workspaceRef=GetWorkspaceRef(this.getRallyAPIKey(), applicationName);

			if(!StringUtils.isNullOrEmpty(applicationName) && !StringUtils.isNullOrEmpty(workspaceRef) && !StringUtils.isNullOrEmpty(userRef)) {
				restApi=this.ConnectRally(host, this.getRallyAPIKey());
				restApi.setApplicationName(applicationName);

				// Query for Test Case to which we want to add results
		        QueryRequest testCaseRequest = new QueryRequest("TestCase");
		        testCaseRequest.setWorkspace(workspaceRef);
		        testCaseRequest.setFetch(new Fetch("FormattedID","Name"));
		        testCaseRequest.setQueryFilter(new QueryFilter("FormattedID", "=", testCaseId));
		        QueryResponse testCaseQueryResponse = restApi.query(testCaseRequest);
		        if (testCaseQueryResponse.getTotalResultCount() == 0) {
		        	log.info("Unable to Find TC Id '"+testCaseId+"' in Rally. Reason either \n (1) TC  is not exists in Rally Workspace '"+applicationName+"' \n OR (2) The User '"+this.getRallyAPIKey()+"' may not have write access on Rally Workspace.  Please ensure on it.");
		        }
		        else 
		        {
//		        	JsonObject testCaseJsonObject = testCaseQueryResponse.getResults().get(0).getAsJsonObject();
				    String testCaseRef = testCaseQueryResponse.getResults().get(0).getAsJsonObject().get("_ref").getAsString();
				    try {
				    	//Add a Test Case Result
				    	String getDate=getDate();
				    	String Notes="The Test Case Id : '"+testCaseId+"' has passed through Automation on '"+getDate+"' in IST. The Result is '"+result;
				        JsonObject newTestCaseResult = new JsonObject();
	   			        newTestCaseResult.addProperty("Build", "ThroughAutomationRun_"+getRandomNumber(3));
	   			        newTestCaseResult.addProperty("Date", getDate);
	   			        newTestCaseResult.addProperty("Verdict", result);
	   			        newTestCaseResult.addProperty("Duration", duration);
	   			        newTestCaseResult.addProperty("Tester", userRef);
	   			        newTestCaseResult.addProperty("Notes", Notes);
	   			        newTestCaseResult.addProperty("TestCase", testCaseRef);	
	   			        newTestCaseResult.addProperty("Workspace", workspaceRef);	
				        CreateRequest createRequest = new CreateRequest("testcaseresult", newTestCaseResult);
				        CreateResponse createResponse = restApi.create(createRequest);
				        if (createResponse.wasSuccessful()) {
				        	log.info("For The TC Id '"+testCaseId+"', the result '"+result+"' has been updated in Rally through jSmart Library.");
//				        	String folderPath=log.getTestResultsFolder()+"\\"+testCaseId+"\\";
//				            addAttachment(restApi, createResponse.getObject().get("_ref").getAsString(), currentUserRef, workspaceRef, folderPath); //workspaceRef
				        }
				        else {
				           String[] createErrors;
				           createErrors = createResponse.getErrors();
				           log.info("Error occurred creating while updating Test Case Results in Rally for TCId "+testCaseId+". Please check Users '"+this.getRallyAPIKey()+"' Access on Rally Workspace. User Must have Read & Write access in Rally Workspace "+applicationName);
				           for (int i = 0; i < createErrors.length; i++) {
				        	   log.info(createErrors[i]);
				           }
				        }

				    } finally {
				        //Release all resources
				        restApi.close();
				    }				
		        }					
			}else {
				log.info("Unable to update TC results in Rally. Please check the parameter that you are passing.");
			}
		} catch (IOException e) {			
			log.info("IO Exception in Method UpdateTestCaseResultInRally() -> "+e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			log.info("Exception in Method UpdateTestCaseResultInRally() -> "+e.getMessage());
			e.printStackTrace();
		}		
	}
	
	/**
	 * Return Rally Workspace Reference using ProjectName/ WorkspaceName
	 * 
	 * @param host 
	 * @param rallyApiKey
	 */
	private String  GetWorkspaceRef(String rallyApiKey, String workspaceName) {
		RallyRestApi restApi=null;
		String workspaceRef=null;
		try {
			if(!StringUtils.isNullOrEmpty(workspaceName) && !StringUtils.isNullOrEmpty(rallyApiKey)) {
				restApi=this.ConnectRally(host, rallyApiKey);
				int limit = 10000;
				restApi.setApplicationName(workspaceName);
				QueryRequest workspaceRequest = new QueryRequest("Workspace");
				workspaceRequest.setFetch(new Fetch(new String[] {"Name", "Owner", "Projects"}));
				workspaceRequest.setLimit(limit);
				QueryResponse workspaceQueryResponse = restApi.query(workspaceRequest);
				System.out.println("workspaceQueryResponse "+ workspaceQueryResponse);
				if(workspaceQueryResponse.getTotalResultCount()>0) {
					String workspaceRefUrl = workspaceQueryResponse.getResults().get(0).getAsJsonObject().get("_ref").toString();
					String[] array = workspaceRefUrl.split("\\/"); 
					String WorkspaceNumber=array[array.length-1].toString();
					workspaceRef="/workspace/"+WorkspaceNumber.substring(0, WorkspaceNumber.length()-1);
				}else {
					log.info("Throwing Rally Worspace Ref id as null for Rally Worspace Name "+workspaceName+". Please check whether Rally API key user has access to Workspace Name "+workspaceName);
				}
	            restApi.close();
			}else {
				log.info("You are passing empty workspace name. Plese check your project config properties.");
			}
		}catch(Throwable e) {
			System.out.println("Error while retrieving workspace reference from rally : "+e.getMessage());
		}
		return workspaceRef;
	}

	/**
	 * Return Rally Rally User Reference
	 * 
	 * @param rallyApiKey 
	 * @param workspaceName
	 */
	private String  GetUserRef(String workspaceName) {
		RallyRestApi currentUserRestApi=null;
		RallyRestApi facelessUserRestApi=null;
		String machineUserEmail=sw.getCurrentUserEmail();
		String userRef=null;
		String currentUserRallyAPIKey=log.getCurrentUserRallyAPIKey();
 		String facelessUserRallyAPIKey=log.getFacelessUserRallyAPIKey();
				
		try {
			if(!StringUtils.isNullOrEmpty(workspaceName)) {
				
				if(!StringUtils.isNullOrEmpty(currentUserRallyAPIKey)) {
					currentUserRestApi=this.ConnectRally(host, currentUserRallyAPIKey);
					currentUserRestApi.setApplicationName(workspaceName);					
			        //Get Current UserRef
			        GetRequest getRequest = new GetRequest("/user"); 
			        GetResponse getResponse = currentUserRestApi.get(getRequest);
			        JsonObject currentUser = getResponse.getObject();
//			        String systemUserName = systemUser.get("EmailAddress").getAsString();
//			        String currentUserRef = systemUser.get("_ref").getAsString();		        
			        if(!currentUser.get("EmailAddress").getAsString().trim().toString().equalsIgnoreCase(machineUserEmail.trim())) {			        	
			        	if(!StringUtils.isNullOrEmpty(facelessUserRallyAPIKey)) {
			        		//Get Faceless UserRef
			        		this.setRallyAPIKey(facelessUserRallyAPIKey);
			        		facelessUserRestApi=this.ConnectRally(host, facelessUserRallyAPIKey);
			        		facelessUserRestApi.setApplicationName(workspaceName);
					        GetRequest getRequest1 = new GetRequest("/user"); 
					        GetResponse getResponse1 = facelessUserRestApi.get(getRequest1);
					        JsonObject facelessUser = getResponse1.getObject();
//					        String facelessUserName = systemUser.get("EmailAddress").getAsString();
//					        String facelessUserRef = systemUser.get("_ref").getAsString();	
					        userRef=facelessUser.get("_ref").getAsString();
					        facelessUserRestApi.close();
							
						}else {
							log.info("You are passing empty API Key for the field 'Faceless_User_rally_API_Key' in Configuration Properties.");
						}	        	
			        }else {
			        	this.setRallyAPIKey(currentUserRallyAPIKey);
			        	userRef=currentUser.get("_ref").getAsString();
			        }
			        currentUserRestApi.close();
				}else {
					log.info("You are passing empty API Key for the field 'CurrentUser_rally_API_Key' in Configuration Properties.");
					if(!StringUtils.isNullOrEmpty(facelessUserRallyAPIKey)) {
						this.setRallyAPIKey(facelessUserRallyAPIKey);
		        		facelessUserRestApi=this.ConnectRally(host, facelessUserRallyAPIKey);
		        		facelessUserRestApi.setApplicationName(workspaceName);
				        GetRequest getRequest1 = new GetRequest("/user"); 
				        GetResponse getResponse1 = facelessUserRestApi.get(getRequest1);
				        JsonObject facelessUser = getResponse1.getObject();
//				        String facelessUserName = systemUser.get("EmailAddress").getAsString();
//				        String facelessUserRef = systemUser.get("_ref").getAsString();	
				        userRef=facelessUser.get("_ref").getAsString();
				        facelessUserRestApi.close();
					}else {
						log.info("You are passing empty API Key for the field 'Faceless_User_rally_API_Key' in Configuration Properties.");
					}
				}
			}else {
				log.info("You are passing empty workspace name. Plese check your project config properties.");
			}		        
		}catch(Throwable e) {
			log.info("Error while retrieving User Reference from Rally : "+e.getMessage());
		}
		return userRef;
	}
	
	
	private static String Rally_API_Key=null;
	private void setRallyAPIKey(String rallyAPIKey) {
		Rally_API_Key=rallyAPIKey;
	}
	private String getRallyAPIKey() {
		return Rally_API_Key;
	}
	
	

	
	/**
	 * Connect Rally using proxy
	 * 
	 * @param host 
	 * @param rallyApiKey
	 */
	private RallyRestApi ConnectRally(String host, String rallyApiKey) {
		RallyRestApi restApi=null;
		try {			 
   			restApi = new RallyRestApi(new URI(host), rallyApiKey);
	        restApi.setProxy(new URI("http://proxy-chain.intel.com:911"));
		}catch(Exception e) {
			System.out.println("Error while connecting rally : "+e.getMessage());
		}
		return restApi;
	}
	
	/**
	 * Returns Date in IST Format
	 */
    private String getDate()
    {
    	Date date = new Date();
    	return formatDateToString(date, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "IST");
    }

	/**
	 * Returns Random Number based on length
	 * @param length of Random Number
	 */
	private String getRandomNumber(int length) {
		return RandomStringUtils.randomNumeric(length);
	}

	/**
	 * Returns Date in specified format
	 * @param Date
	 * @param format
	 * @param timeZone
	 */ 
	private static String formatDateToString(Date date, String format, String timeZone) {
		if (date == null) return null;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		if (timeZone == null || "".equalsIgnoreCase(timeZone.trim())) {
			timeZone = Calendar.getInstance().getTimeZone().getID();
		}
		sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
		return sdf.format(date);
	}
	
	
	
    @SuppressWarnings("unused")
	private void addAttachment(RallyRestApi restApi, String testCaseResultRef, String userRef, String workspaceRef, String filePath) throws URISyntaxException,IOException {
        String smartLogger = filePath+"\\SmartLogger.txt";
        
//        // Creating Zip Floder and adding logs into it
//        String attachment=filePath+"\\TestResults.zip";
//        FileOutputStream fos = new FileOutputStream(attachment);
//        ZipOutputStream zipOS = new ZipOutputStream(fos);
//        writeToZipFile(smartLogger, zipOS);
//        zipOS.close();
//        fos.close();
        
        System.out.println("restApi : "+restApi);
        System.out.println("testCaseResultRef : "+testCaseResultRef);
        System.out.println("userRef : "+userRef);
        System.out.println("workspaceRef : "+workspaceRef);
        System.out.println("filePath : "+filePath);
        String imageBase64String;
        long attachmentSize;
        
        try { 
    		BufferedReader reader = new BufferedReader(new FileReader(smartLogger));
    		StringBuilder stringBuilder = new StringBuilder();
    		String line = null;
    		String ls = System.getProperty("line.separator");
    		while ((line = reader.readLine()) != null) {
    			stringBuilder.append(line);
    			stringBuilder.append(ls);
    		}
    		// delete the last new line separator
    		stringBuilder.deleteCharAt(stringBuilder.length() - 1);
    		reader.close();		
    		String content = stringBuilder.toString();		
    		System.out.println(content);
    		
    		File originalFile=new File(smartLogger);
    		String encodedBase64 = null;
    		FileInputStream fileInputStreamReader = new FileInputStream(originalFile);
            byte[] bytes = new byte[(int)originalFile.length()];
            fileInputStreamReader.read(bytes);
            encodedBase64 = new String(Base64.encodeBase64(bytes));
    		
            long longLength = content.length();
            System.out.println(longLength);
            if (longLength >= 5000000) {
            	throw new IOException("File size >= 5 MB Upper limit for Rally.");
            }
            int fileLength = (int) longLength;
            System.out.println(fileLength);

            // Now create the Attachment itself
            JsonObject myAttachment = new JsonObject();
            myAttachment.addProperty("TestCaseResult", testCaseResultRef);
            myAttachment.addProperty("Content", encodedBase64);
            myAttachment.addProperty("Name", "SmartLog.txt");
            myAttachment.addProperty("Description", "Execution Log");
            myAttachment.addProperty("ContentType","text/plain");
            myAttachment.addProperty("Size", fileLength);
            myAttachment.addProperty("User", userRef);
            myAttachment.addProperty("Workspace", workspaceRef);

            CreateRequest attachmentCreateRequest = new CreateRequest("Attachment", myAttachment);
            System.out.println("attachmentCreateRequest : " + attachmentCreateRequest);
            //java.lang.NullPointerException if workspace parameter is not set as below
            attachmentCreateRequest.addParam("workspace", workspaceRef);
            System.out.println("workspaceRef : " + workspaceRef);
            CreateResponse attachmentResponse = restApi.create(attachmentCreateRequest);
            System.out.println("attachmentResponse : " + attachmentResponse);
            String myAttachmentRef = attachmentResponse.getObject().get("_ref").getAsString();
            System.out.println("Attachment  created: " + myAttachmentRef);

            if (attachmentResponse.wasSuccessful()) {
                System.out.println("Successfully created Attachment");
            } else {
                String[] attachmentContentErrors;
                attachmentContentErrors = attachmentResponse.getErrors();
                System.out.println("Error occurred creating Attachment: ");
                for (int j=0; j<attachmentContentErrors.length;j++) {
                    System.out.println(attachmentContentErrors[j]);
                }
            }
        }catch (Exception e) {
            System.out.println("Exception occurred while attempting to create Content and/or Attachment: ");
            e.printStackTrace();
        }
    }

}
