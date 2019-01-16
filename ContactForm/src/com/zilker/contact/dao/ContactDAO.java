package com.zilker.contact.dao;

import com.zilker.contact.dbUtils.Db_Connect;
import com.zilker.contact.beanClass.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Responsible for DB CRUD operations.
 */

public class ContactDAO {
	
	private final static Logger LOGGER =  
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	private Connection conn = null;
	private PreparedStatement pst = null;
	private ResultSet rs = null;
	
	public ContactDAO() {}
	
	public boolean checkMailExists(String relation, String mail) {
		boolean valid = false;
		try {
			conn = Db_Connect.getConnection();
		    pst = conn.prepareStatement("select * from " + relation + " where mail=?");
		    pst.setString(1, mail);
		    rs = pst.executeQuery();
		    if(rs!=null) {
		    	LOGGER.log(Level.INFO, mail + " exists");
		    	valid = true;
		    }
		    else 
		    	valid = false;
		    } catch (SQLException e) {
		    LOGGER.log(Level.INFO, "Error in connecting with DB");
		    } finally {
		    Db_Connect.closeConnection(conn, pst, rs);
		    }
		return valid;
	}
	
	public void insertPersonalDetails(Contact_Detail contact) {
		try {
			conn = Db_Connect.getConnection();
		    pst = conn.prepareStatement("insert into contactDetail(firstname, lastname, mail) values(?,?,?)");
		    pst.setString(1, contact.getFirstName());
		    pst.setString(2, contact.getLastName());
		    pst.setString(3, contact.getMail());
		    int count = pst.executeUpdate();
		    LOGGER.log(Level.INFO, "Number of rows affected " + count);
		    } catch (SQLException e) {
		    LOGGER.log(Level.INFO, "Error inserting personal details");
		    } finally {
		    Db_Connect.closeConnection(conn, pst, rs);
		    }
	}
	
	public void insertMobileDetails(Mobile_Detail mobile) {
		int contactID= -1;
		
		try {
			conn = Db_Connect.getConnection();
			pst = conn.prepareStatement("select * from contactDetail" + " where mail=?");
			pst.setString(1, mobile.getMail());
			rs = pst.executeQuery();
			
			if(rs.next()) {
				if(rs.getString(4).equals(mobile.getMail()))
				{
					String test = rs.getString(1);
					contactID= Integer.parseInt(test);
				}
			}
			
			pst = conn.prepareStatement("insert into mobileDetail(mobilenum, mobileext, contactID) values(?,?,?)");
		    pst.setString(1, mobile.getMobileNum());
		    pst.setInt(2, mobile.getMobileExt());
		    pst.setInt(3, contactID);
		    int count = pst.executeUpdate();
		    LOGGER.log(Level.INFO, "Number of rows affected " + count);
		    } catch (SQLException e) {
		    LOGGER.log(Level.INFO, "Error inserting mobile details");
		    } finally {
		    Db_Connect.closeConnection(conn, pst, rs);
		    }
	}
	
	public void insertOfficeDetails(Office_Detail office) {
		int contactID= -1;
		
		try {
			conn = Db_Connect.getConnection();
			pst = conn.prepareStatement("select * from contactDetail" + " where mail=?");
			pst.setString(1, office.getMail());
			rs = pst.executeQuery();
			
			if(rs.next()) {
				if(rs.getString(4).equals(office.getMail()))
				{
					String test = rs.getString(1);
					contactID= Integer.parseInt(test);
				}
			}
			
			pst = conn.prepareStatement("insert into officeDetail(officenum, officeext, contactID) values(?,?,?)");
		    pst.setString(1, office.getOfficeNum());
		    pst.setInt(2, office.getOfficeExt());
		    pst.setInt(3, contactID);
		    int count = pst.executeUpdate();
		    LOGGER.log(Level.INFO, "Number of rows affected " + count);
		    } catch (SQLException e) {
		    LOGGER.log(Level.INFO, "Error inserting office details");
		    } finally {
		    Db_Connect.closeConnection(conn, pst, rs);
		    }
	}
	
	public void insertHomeDetails(Home_Detail home) {
		int contactID= -1;
		
		try {
			conn = Db_Connect.getConnection();
			pst = conn.prepareStatement("select * from contactDetail" + " where mail=?");
			pst.setString(1, home.getMail());
			rs = pst.executeQuery();
			
			if(rs.next()) {
				if(rs.getString(4).equals(home.getMail()))
				{
					String test = rs.getString(1);
					contactID= Integer.parseInt(test);
				}
			}
			
			pst = conn.prepareStatement("insert into homeDetail(homenum, areacode, countrycode, contactID) values(?,?,?,?)");
		    pst.setString(1, home.getHomeNum());
		    pst.setInt(2, home.getAreaCode());
		    pst.setInt(3, home.getCountryCode());
		    pst.setInt(4, contactID);
		    int count = pst.executeUpdate();
		    LOGGER.log(Level.INFO, "Number of rows affected " + count);
		    } catch (SQLException e) {
		    LOGGER.log(Level.INFO, "Error inserting mobile details");
		    } finally {
		    Db_Connect.closeConnection(conn, pst, rs);
		    }
	}
	
	public void displayDetails(String mail) {
		int contactID = -1;
		
		try {
		      conn = Db_Connect.getConnection();
		      pst = conn.prepareStatement("select * from contactDetail" + " where mail=?");
		      pst.setString(1, mail);
		      rs = pst.executeQuery();
		      if(rs.next()) {
		    	  if(rs.getString(4).equals(mail))
		    	  {
		    		  String test = rs.getString(1);
		    		  contactID= Integer.parseInt(test);
		    	  }
		      }
		      
		      pst = conn.prepareStatement("select * from contactDetail" + " where contactID=?");
		      pst.setInt(1, contactID);
		      rs = pst.executeQuery();
		      
		      while (rs.next()) {
					LOGGER.log(Level.INFO, "First name: " + rs.getString(2) + "\nLast name: " + rs.getString(3) 
					+ "\nEmail: " + rs.getString(4));
		      }
		       
		      pst = conn.prepareStatement("select * from mobileDetail" + " where contactID=?");
		      pst.setInt(1, contactID);
		      rs = pst.executeQuery();
		      while (rs.next()) {
					LOGGER.log(Level.INFO, "Mobile number: " + rs.getString(2) + "\nMobile extension: " + rs.getString(3));
		      }
		      
		      pst = conn.prepareStatement("select * from officeDetail" + " where contactID=?");
		      pst.setInt(1, contactID);
		      rs = pst.executeQuery();
		      while (rs.next()) {
					LOGGER.log(Level.INFO, "Office number: " + rs.getString(2) + "\nOffice extension: " + rs.getString(3));
		      }
		      
		      pst = conn.prepareStatement("select * from homeDetail" + " where contactID=?");
		      pst.setInt(1, contactID);
		      rs = pst.executeQuery();
		      while (rs.next()) {
					LOGGER.log(Level.INFO, "Home number: " + rs.getString(2) + "\nArea code: " + rs.getString(3)
							+ "\nCountry code: " + rs.getString(4));
		      }  
		      
		} catch (Exception e) {
		    	LOGGER.log(Level.SEVERE, "Error displaying details");
		    } finally {
		      Db_Connect.closeConnection(conn, pst, rs);
		    }
	}
	
	public void updatePersonalDetails(Contact_Detail contact) {
		try {
			conn = Db_Connect.getConnection();
		    pst = conn.prepareStatement("update contactDetail set firstname=?, lastname=? where mail=?");
		    pst.setString(1, contact.getFirstName());
		    pst.setString(2, contact.getLastName());
		    pst.setString(3, contact.getMail());
		    int count = pst.executeUpdate();
		    LOGGER.log(Level.INFO, "Number of rows affected " + count);
		    } catch (SQLException e) {
		    LOGGER.log(Level.INFO, "Error updating personal details");
		    } finally {
		    Db_Connect.closeConnection(conn, pst, rs);
		    }
	}
	
	public void updateMobileDetails(Mobile_Detail mobile, String oldMobile) {
		int contactID= -1;
		
		try {
			conn = Db_Connect.getConnection();
			pst = conn.prepareStatement("select * from contactDetail" + " where mail=?");
			pst.setString(1, mobile.getMail());
			rs = pst.executeQuery();
			
			if(rs.next()) {
				if(rs.getString(4).equals(mobile.getMail()))
				{
					String test = rs.getString(1);
					contactID= Integer.parseInt(test);
				}
			}
			
			pst = conn.prepareStatement("update mobileDetail set mobilenum=?,mobileext=? where contactID=? and mobilenum=? ");
		    pst.setString(1, mobile.getMobileNum());
		    pst.setInt(2, mobile.getMobileExt());
		    pst.setInt(3, contactID);
		    pst.setString(4, oldMobile);
		    int count = pst.executeUpdate();
		    LOGGER.log(Level.INFO, "Number of rows affected " + count);
		    } catch (SQLException e) {
		    LOGGER.log(Level.INFO, "Error updating mobile details");
		    } finally {
		    Db_Connect.closeConnection(conn, pst, rs);
		    }
	}
	
	public void updateOfficeDetails(Office_Detail office, String oldOffice) {
		int contactID= -1;
		
		try {
			conn = Db_Connect.getConnection();
			pst = conn.prepareStatement("select * from contactDetail" + " where mail=?");
			pst.setString(1, office.getMail());
			rs = pst.executeQuery();
			
			if(rs.next()) {
				if(rs.getString(4).equals(office.getMail()))
				{
					String test = rs.getString(1);
					contactID= Integer.parseInt(test);
				}
			}
			
			pst = conn.prepareStatement("update officeDetail set officenum=?,officeext=? where contactID=? and officenum=? ");
		    pst.setString(1, office.getOfficeNum());
		    pst.setInt(2, office.getOfficeExt());
		    pst.setInt(3, contactID);
		    pst.setString(4, oldOffice);
		    int count = pst.executeUpdate();
		    LOGGER.log(Level.INFO, "Number of rows affected " + count);
		    } catch (SQLException e) {
		    LOGGER.log(Level.INFO, "Error updating office details");
		    } finally {
		    Db_Connect.closeConnection(conn, pst, rs);
		    }
	}
	
	public void updateHomeDetails(Home_Detail home, String oldHome) {
		int contactID= -1;
		
		try {
			conn = Db_Connect.getConnection();
			pst = conn.prepareStatement("select * from contactDetail" + " where mail=?");
			pst.setString(1, home.getMail());
			rs = pst.executeQuery();
			
			if(rs.next()) {
				if(rs.getString(4).equals(home.getMail()))
				{
					String test = rs.getString(1);
					contactID= Integer.parseInt(test);
				}
			}
			
			pst = conn.prepareStatement("update homeDetail set homenum=?,areacode=?,countrycode=? where contactID=? and homenum=?");
		    pst.setString(1, home.getHomeNum());
		    pst.setInt(2, home.getAreaCode());
		    pst.setInt(3, home.getCountryCode());
		    pst.setInt(4, contactID);
		    pst.setString(1, oldHome);
		    int count = pst.executeUpdate();
		    LOGGER.log(Level.INFO, "Number of rows affected " + count);
		    } catch (SQLException e) {
		    LOGGER.log(Level.INFO, "Error updating home details");
		    } finally {
		    Db_Connect.closeConnection(conn, pst, rs);
		    }
	}
	
	public void deleteDetails(String mail) {
		int contactID = -1;
		
		try {
		      conn = Db_Connect.getConnection();
		      pst = conn.prepareStatement("select * from contactDetail" + " where mail=?");
		      pst.setString(1, mail);
		      rs = pst.executeQuery();
				
		      if(rs.next()) {
		    	  if(rs.getString(4).equals(mail))
		    	  {
		    		  String test = rs.getString(1);
		    		  contactID= Integer.parseInt(test);
		    	  }
		      }
				
		      pst = conn.prepareStatement("delete from contactDetail where contactID=?");
		      pst.setInt(1, contactID);
		      int count = pst.executeUpdate();
		      if(count>0) {
			      LOGGER.log(Level.INFO, "Contact successfully deleted");
		      }
		      LOGGER.log(Level.INFO, "Number of rows affected " + count);
		    } catch (SQLException e) {
		    	LOGGER.log(Level.INFO, "Error deleting details");
		    } finally {
		      Db_Connect.closeConnection(conn, pst, rs);
		      }
	}	
}
