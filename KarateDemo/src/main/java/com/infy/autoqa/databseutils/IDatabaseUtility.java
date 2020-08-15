package com.infy.autoqa.databseutils;

import java.util.HashMap;
import java.util.List;

/***********************************************
*Interface Database Utility
*version 1.0
*@author Mathavan, GopalakrishnanX
*@since 2018-07-29
***********************************************/

public interface IDatabaseUtility {

	/**
	* Get Single attribute value From DB
	* @param
	* query Select productid from product where productname='Intel Core i5'
	* @return String
	* @author Mathavan, GopalakrishnanX
	* @since 2018-07-29
	*/	
	String GetSingleAttributeValue(String query);
	
	/**
	* Get Records/Rows From DB
	* @param query Select * from product
	* @return List<Map<String, Object>>
	* @author Mathavan, GopalakrishnanX
	* @since 2018-07-29
	*/	
	List<HashMap<String, Object>> GetRecords(String query);
	
	
	/**
	* Insert Or Update Table In DB
	* @param query Update product set productname='Intel core i10' where productid='344544'
	* @return void
	* @author Mathavan, GopalakrishnanX
	* @since 2018-07-29
	*/
	void InsertOrUpdateTable(String query, List<String> parameters);
}
