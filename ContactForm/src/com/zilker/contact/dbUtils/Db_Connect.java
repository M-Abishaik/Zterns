package com.zilker.contact.dbUtils;

import java.util.logging.Level;

import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/* 
 * Responsible for handling DB connection.
 */

public class Db_Connect {
	
	private final static Logger LOGGER =  
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	private static final String connectionString = "jdbc:mysql://localhost:3306/contact";
	private static final String username = "root";
	private static final String password = "";
	
	public Db_Connect() {}
	
	public static Connection getConnection() {
	    Connection conn = null;
	    try {
	      conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/contact", "root", "");
	    } catch (SQLException e) {
	    	LOGGER.log(Level.INFO, "Error connecting with Driver");
	    }
	    return conn;
	  }
	  
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
	    	LOGGER.log(Level.INFO, "Error closing the connection");
	    }
	}
}