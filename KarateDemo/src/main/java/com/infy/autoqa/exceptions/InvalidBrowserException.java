package com.infy.autoqa.exceptions;

public class InvalidBrowserException extends Exception{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidBrowserException(String sExceptionInfo) {
		super("Invalid browser name used - " + sExceptionInfo);
	}

}
