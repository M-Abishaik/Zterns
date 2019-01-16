package com.zilker.taxi.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.zilker.taxi.constant.ConstantVar;

/*
 * Handles the connection information with the database. 
 */

public class DbConnect {
	
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	public DbConnect() {}
	
	/*
	 * Opens the database connection. 
	 */
	
	public static Connection getConnection() {
	    Connection conn = null;
	    try {
	      conn = DriverManager.getConnection(ConstantVar.CONNECTION, ConstantVar.USERNAME, ConstantVar.PASSWORD);
	    } catch (SQLException e) {
	    	LOGGER.log(Level.INFO, "Error in connecting with driver.");
	    }
	    return conn;
	  }
	  
	/*
	 * Closes the database connection. 
	 */
	
	public static void closeConnection(Connection conn, PreparedStatement pst, ResultSet rs) { 
		try { 
			if (rs != null) {
				rs.close();
			}
			if (pst != null) {
				pst.close();
			}
			if (conn != null) {
				conn.close();
			}
	    } catch (SQLException e) {
	    	LOGGER.log(Level.INFO, "Error in closing the connection.");
	    }
	}
}
