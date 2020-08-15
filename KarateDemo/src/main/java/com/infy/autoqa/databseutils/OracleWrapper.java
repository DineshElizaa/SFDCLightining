package com.infy.autoqa.databseutils;


import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.support.PageFactory;

import com.infy.autoqa.wrappers.LoggerWrapper;
import com.infy.autoqa.databseutils.IDatabaseUtility;
//import com.intel.ebsqa.wrappers.LoggerWrapper;

public class OracleWrapper implements IDatabaseUtility{
	
	static LoggerWrapper log = new LoggerWrapper(OracleWrapper.class);
	
	private Connection connection = null;
	private ResultSet rs = null;
	private static String UserName=null;
	private static String Password=null;
	String driver;
	String connectionURL;


	/**
	* Constructor for OracleWrapper class
	* @param
	* @return Connection
	* @author dinesh2x
	* @since 2019-12-17
	*/
	public OracleWrapper(String server,String portNumber, String dataBase, String userName, String password)
	{
		driver = "oracle.jdbc.driver.OracleDriver";		
		connectionURL = "jdbc:oracle:thin:@"+server+":"+ portNumber+ ":orcl";				
		UserName=userName;
		Password=password;
	}
	
	/**
	* To open database connection
	* @param
	* @return Connection
	* @author Mathavan, GopalakrishnanX
	* @since 2018-07-30
	*/
	public Connection OpenConnection()
	{	
	  try 
	    {
		 log.info("Attempting to connect to DB - " + connectionURL);
		 Class.forName(driver);
		 if(connection==null) {
		 	connection=DriverManager.getConnection(connectionURL, UserName, Password);  	
        }
	} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}  
		catch (Exception e) {
			e.printStackTrace();
		}
		log.info("DB Connection Successful");	
		return connection;
	}
	
	/**
	* To close database connection
	* @param
	* @return
	* @author Mathavan, GopalakrishnanX
	* @since 2018-07-30
	*/
	public void CloseConnection()
	{
		try {
			if(connection!=null) {
				connection.close();				
			}				
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	* Validate database connection open or not. If database connection is not available then close the connection
	* @param
	* @return
	* @author Mathavan, GopalakrishnanX
	* @since 2018-07-30
	*/
	private void ValidateDBConnection()
	{	
		try 
		{	
			if(connection==null || connection.isClosed())
			{
				log.info("There is no Open connection to execute the query, creating new connection...");
				this.OpenConnection();
				this.GetConnection();
			}
			if(!IsResultSetClosed()) {				
				rs.close();
			}				
		}
		catch (SQLException e) {
			e.printStackTrace();
			e.getErrorCode();
		}
	}
	
	/**
	* Validate ResultSet closed or not
	* @param
	* @return Boolean
	* @author Mathavan, GopalakrishnanX
	* @since 2018-07-30
	*/
	private Boolean IsResultSetClosed() throws SQLException
    {
        if (rs == null)
        {
            return true;
        }

        return rs.isClosed();
    }
	
	/**
	* Get database connection
	* @param
	* @return Connection
	* @author Mathavan, GopalakrishnanX
	* @since 2018-07-30
	*/
	public Connection GetConnection()
	{
		return connection;
	}
	
	/**
	* Get Single attribute value From DB
	* @param query : Select Statement
	* @return String
	* @author dinesh2X
	* @since 2019-12-17
	*/
	@Override
	public String GetSingleAttributeValue(String query) {
		String result = "";
		try {
			ValidateDBConnection();
			PreparedStatement stmt=connection.prepareStatement(query);
			rs = stmt.executeQuery();
			while(rs.next()) {
				result=rs.getString(1);
			}
		} catch (Exception e) {
			log.info("Oracle Exception - "+ e.getMessage());
		}
		CloseConnection();		
		return result;
	}
	
	/**
	* Get Records/Rows From DB
	* @param 
	* query Select * from table
	* @return List<Map<String, Object>>
	* @author dinesh2x
	* @since 2019-12-17
	*/
	@Override
	public List<HashMap<String, Object>> GetRecords(String query) {
		try {
			ValidateDBConnection();
			PreparedStatement  stmt = connection.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			if (rs.isBeforeFirst())
			{	
				ResultSetMetaData rm = rs.getMetaData();
				int count = rm.getColumnCount();
				List<HashMap<String, Object>> multipleRowData = new ArrayList<HashMap<String, Object>>();
				while (rs.next()) 
				{
					HashMap<String, Object> singleRowData = new HashMap<String, Object>();
					for(int j=0; j<count; j++)
					{
						singleRowData.put(rm.getColumnName(j+1), rs.getObject(j+1));
					}					   
					multipleRowData.add(singleRowData);
				}							
				return multipleRowData;					
		}
			else
            {
                log.warn("No records returned from DB for the Query:" + query);
                return null;
            }
		
		} catch (Exception e) {
			log.error(e.getMessage());
			log.warn("No records returned from DB for the Query:" + query);
		}
		return null;
	}
	
	/**
	* Insert Or Update Table In DB
	* @param 
	* query Update product set productname='Intel core i10' where productid='344544'
	* @return void
	* @author Mathavan, GopalakrishnanX
	* @since 2018-07-30
	*/
	@Override
	public void InsertOrUpdateTable(String query, List<String> parameters) {		
		try {
			ValidateDBConnection();			
			PreparedStatement stmt = connection.prepareStatement(query);
			for(int i=1; i<=parameters.size(); i++)
			{
				stmt.setString(i, parameters.get(i-1));
			}
			 int rowsUpdated = stmt.executeUpdate();
			 if (rowsUpdated > 0) {
			     log.info("Record updated or inserted successfully");
			 }		
		} catch (Exception e) {
			log.error(e.getMessage());
			log.info("No records updated :" + query);
		}		
	}

}


