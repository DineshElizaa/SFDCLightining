package com.infy.autoqa.databseutils;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.infy.autoqa.wrappers.LoggerWrapper;

/***********************************************
*Database Utility Implementation
*version 1.0
*@author Mathavan, GopalakrishnanX
*@since 2018-07-30
***********************************************/
public class MySqlWrapper implements IDatabaseUtility{
	
	static LoggerWrapper log = new LoggerWrapper(MySqlWrapper.class);
	
	private Connection connection = null;
	private ResultSet rs = null;
	String driver;
	String connectionURL;
	private static String UserName = null;
	private static String Password = null;
	
	/**
	* Constructor for MySqlWrapper class
	* @param
	* @return Connection
	* @author Mathavan, GopalakrishnanX
	* @since 2018-07-30
	*/
	public MySqlWrapper(String server, String portNumber, String dataBase, String userName, String password)
	{
		driver = "com.mysql.cj.jdbc.Driver";
		connectionURL = "jdbc:mysql://"+server+":"+ portNumber+ "/"+dataBase+"?useSSL=false";
		UserName = userName;
		Password = password;			
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
			if(connection==null)
				connection=DriverManager.getConnection(connectionURL, UserName, Password);
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch (SQLException e) {
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
			if(connection!=null)
				connection.close();
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
			if(!IsResultSetClosed())
				rs.close();
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
	* @param
	* query Select productid from product where productname='Intel Core i5'
	* @return String
	* @author Mathavan, GopalakrishnanX
	* @since 2018-07-30
	*/
	
	public String GetSingleAttributeValue(String query) {
		String result = "";
		try {
			ValidateDBConnection();
			Statement stmt = connection.createStatement();
			rs = stmt.executeQuery(query);
			if (rs.next())
				result = rs.getString(1);
		} catch (Exception e) {
			System.out.println(e);
		}
		return result;
	}

	/**
	* Get Records/Rows From DB
	* @param 
	* query Select * from product
	* @return List<Map<String, Object>>
	* @author Mathavan, GopalakrishnanX
	* @since 2018-07-30
	*/	
	public List<HashMap<String, Object>> GetRecords(String query) {	
		try {
			ValidateDBConnection();
			Statement stmt = connection.createStatement();
			rs = stmt.executeQuery(query);
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
	public void InsertOrUpdateTable(String query, List<String> parameters) {
		// TODO Auto-generated method stub
		
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

