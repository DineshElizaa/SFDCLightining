package com.infy.autoqa.databseutils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.infy.autoqa.wrappers.LoggerWrapper;

import java.sql.PreparedStatement;

//import com.intel.ebsqa.wrappers.LoggerWrapper;

public class TeradataWrapper implements IDatabaseUtility{
	
	static LoggerWrapper log = new LoggerWrapper(TeradataWrapper.class);
	
	private Connection connection = null;
	private ResultSet rs = null;
	private static String UserName=null;
	private static String Password=null;
	String driver;
	String connectionURL;
	
	
	/*
	 * Intel machine is restricted to install & run Tera data S/W so written pseduo code for Terdata operation, 
	 * in future needs to correct all code w.r.to Teradata operations
	 */
	
	
	/**
	* Constructor for TeradataWrapper class
	* @param
	* @return Connection
	* @author Kanuri, RaghuramaX
	* @since 2019-12-10
	*/
	public TeradataWrapper(String server, String portNumber, String dataBase, String userName, String password)
	{
		driver = "com.teradata.jdbc.TeraDriver";
		connectionURL = "jdbc:teradata://"+server+":"+ portNumber+ "/"+dataBase;
		UserName=userName;
		Password=password;
	}
	
	/**
	* To open database connection
	* @param
	* @return Connection
	* @author Kanuri, RaghuramaX
	* @since 2019-12-10
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
	* @author Kanuri, RaghuramaX
	* @since 2019-12-10
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
	* @author Kanuri, RaghuramaX
	* @since 2019-12-10
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
	* @author Kanuri, RaghuramaX
	* @since 2019-12-10
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
	* @author Kanuri, RaghuramaX
	* @since 2019-12-10
	*/
	public Connection GetConnection()
	{
		return connection;
	}

	/**
	* Get Single attribute value From DB
	* @param query : Select Statement
	* @return String
	* @author Kanuri, RaghuramaX
	* @since 2019-12-10
	*/
	@Override
	public String GetSingleAttributeValue(String query) {
		String result = "";
		try {
			ValidateDBConnection();
			PreparedStatement stmt=connection.prepareStatement(query);
			rs=stmt.executeQuery();
			while(rs.next()) {
				result=rs.getString(1);
			}
		} catch (Exception e) {
			log.info("Teradata Exception - "+ e.getMessage());
		}
		CloseConnection();
		return result;
	}

	/**
	* Get Records/Rows From DB
	* @param 
	* query Select * from table
	* @return List<Map<String, Object>>
	* @author Kanuri, RaghuramaX
	* @since 2019-12-10
	*/
	@Override
	public List<HashMap<String, Object>> GetRecords(String query) {
		try {
			ValidateDBConnection();
			PreparedStatement stmt = connection.prepareStatement(query);
			rs = stmt.executeQuery();
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
	* @author Kanuri, RaghuramaX
	* @since 2019-12-10
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
