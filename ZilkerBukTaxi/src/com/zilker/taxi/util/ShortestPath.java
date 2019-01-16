package com.zilker.taxi.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;

import com.zilker.taxi.bean.Customer;
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
		
		HashMap<String, Float> hashMap= new HashMap<String, Float>();
		ArrayList<Route> routesList = new ArrayList<Route>();
		float distance = 0.0f, price = 0.0f;
		String endTime = "";
		
		try {
			TaxiDAO taxiDAO = new TaxiDAO();
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
				LOGGER.log(Level.SEVERE, e.getMessage());
				return null;
		} finally {
				LOGGER.log(Level.INFO, "Price and endTime calculated.");
		}
	}   
	
	/*
	 * Computes the finish time of a ride. 
	 */
	
	public String calculateFinishTime(String startTime, int minutes) {
		
         SimpleDateFormat df = new SimpleDateFormat("HH:mm");
         String endTime = "";

         try{
        	 Date d = df.parse(startTime);
        	 Calendar cal = Calendar.getInstance();
        	 cal.setTime(d);
        	 cal.add(Calendar.MINUTE, minutes);
        	 endTime = df.format(cal.getTime());
        	 return endTime;
         }catch(ParseException pe){
        	 pe.printStackTrace();
        	 return "";
         }
	}
}
