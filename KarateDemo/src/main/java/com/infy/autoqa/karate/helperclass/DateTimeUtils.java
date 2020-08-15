/*
* Copyright (c) 2018 EBS Automation Team. All rights reserved.
*/
package com.infy.autoqa.karate.helperclass;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Description To perform all date time manipulation
 * @Author gmathavx
 * @Since 18-Aug-2018
 */

public class DateTimeUtils {

	/**
	 * @Description To get current date
	 * @Author gmathavx
	 * @Since 18-Aug-2018
	 */
	public static String GetCurrentDate() {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();
		return dateFormat.format(date); // 8/18/2018
	}

	/**
	 * @Description To get next week date
	 * @Author gmathavx
	 * @Since 18-Aug-2018
	 */
	public static String GetDateAfterAWeek() {
		final DateFormat dateFormat1 = new SimpleDateFormat("MM/dd/yyyy");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date()); // Now use today date.
		c.add(Calendar.DATE, 7); // Adding 7 days
		return dateFormat1.format(c.getTime());
	}

	/**
	 * Method to get current date and time
	 * 
	 * @Author gmathavx
	 * @Since 09-Jan-2019
	 * @return String Example date format : 01_09_2019 12_45_47_892
	 */
	public static String getCurrentDateAndTime() {
		DateFormat dateFormat = new SimpleDateFormat("MM_dd_yyyy HH_mm_ss_SSS");
		Date date = new Date();
		return dateFormat.format(date);
	}
}
