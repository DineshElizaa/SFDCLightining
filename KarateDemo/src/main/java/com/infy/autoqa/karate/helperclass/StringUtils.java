/*
* Copyright (c) 2018 EBS Automation Team. All rights reserved.
*/
package com.infy.autoqa.karate.helperclass;
import org.apache.commons.lang3.RandomStringUtils;

/**
* @Description To perform all string manipulation 
 * @Author mohseenx
* @Since  Aug 6, 2018
*/

public class StringUtils {
	
	public static boolean isNullOrBlank(String s)
	{
	  return (s==null || s.trim().equals("") || s.trim().isEmpty());
	}
	
	public static String GetRandomString(int iLengthOfString)
	{		
		return RandomStringUtils.randomAlphabetic(iLengthOfString).toUpperCase();
	}
	
	public static String GetRandomNumber(int iLengthOfNumber)
	{
		return RandomStringUtils.randomNumeric(iLengthOfNumber);
	}
}

