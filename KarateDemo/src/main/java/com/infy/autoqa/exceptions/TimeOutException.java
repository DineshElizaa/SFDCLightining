/*
* Copyright (c) 2018 EBS Automation Team. All rights reserved.
*/
package com.infy.autoqa.exceptions;

/**
* @Description Method to throw timeout exception
 * @Author mohseenx
* @Since  Aug 5, 2018
*/

public class TimeOutException extends Exception{
	
		private static final long serialVersionUID = 1L;
			public TimeOutException(String exceptionInfo) {
				super("Element is not visible for given of time " + exceptionInfo);
		}
}

