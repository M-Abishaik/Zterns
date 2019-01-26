package com.zilker.taxi.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.zilker.taxi.bean.Route;
import com.zilker.taxi.dao.TaxiDAO;
/*
 * Computes the estimated finish time and price corresponding to the distance between source and destination.
 */

public class ShortestPath {
	
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public ShortestPath() {}
	
	/*
	 * Computes the estimated finish time and price.
	 */
	
	public HashMap<String, Float> calculateTravel(int sourceID, int destinationID, String startTime) {
		
		
		float distance = 0.0f; 
		float price = 0.0f;
		String endTime = "";
		TaxiDAO taxiDAO = null;
		HashMap<String, Float> hashMap = null;
		ArrayList<Route> routesList = null;
		
		try {
			taxiDAO = new TaxiDAO();
			
			hashMap = new HashMap<String, Float>();
			routesList = new ArrayList<Route>();
			
			routesList = taxiDAO.getRoutesList();
			
			for(Route route : routesList) {
				if(route.getSource() == sourceID && route.getDestination() == destinationID) {
					distance = route.getDistance();
					break;
				}
			}
			
			if(distance == 0.0f) {
				return null;
			}
			
			if(distance >= 100 && distance <= 149) {
				endTime = calculateFinishTime(startTime, 50);
				price = 1500;
			} else if(distance >= 150 && distance <= 199) {
				endTime = calculateFinishTime(startTime, 100);
				price = 2500;
			} else if(distance >= 200 && distance <= 300) {
				endTime = calculateFinishTime(startTime, 150);
				price = 3500;
			} else {
				endTime = calculateFinishTime(startTime, 200);
				price = 5000;
			}
			
			hashMap.put(endTime, price);
			return hashMap;
			
		}catch (Exception e) {
				LOGGER.log(Level.SEVERE, "Error in calculating the price and time.");
				return null;
		} finally {
				LOGGER.log(Level.INFO, "Price and endTime calculated.");
		}
	}   
	
	/*
	 * Computes the finish time of a ride. 
	 */
	
	private String calculateFinishTime(String startTime, int minutes) {
		
         SimpleDateFormat df = new SimpleDateFormat("HH:mm");
         String endTime = "";
         Date date = null;
         Calendar calender = null;

         try{
        	 date = df.parse(startTime);
        	 calender = Calendar.getInstance();
        	 calender.setTime(date);
        	 calender.add(Calendar.MINUTE, minutes);
        	 endTime = df.format(calender.getTime());
        	 return endTime;
         }catch(ParseException pe){
        	 LOGGER.log(Level.INFO, "Invalid time format.");
        	 return "";
         }
	}
}
