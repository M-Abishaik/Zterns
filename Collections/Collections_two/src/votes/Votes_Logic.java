package votes;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Lexicographically sorts the votes.
 */

public class Votes_Logic {
	
	private final static Logger LOGGER =  
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	static void printFreq(String arr[]) { 
		
        TreeMap<String, Integer> treeMap = new TreeMap<String, Integer>(); 
        TreeSet<String> treeSet = new TreeSet<String>();
        
        for (int i = 0; i < arr.length; i++) 
        { 
            Integer c = treeMap.get(arr[i]);
            if (treeMap.get(arr[i]) == null) 
               treeMap.put(arr[i], 1); 
            else
              treeMap.put(arr[i], ++c); 
        } 
 
        int max = treeMap.values().stream().findFirst().get();
        for (Map.Entry m:treeMap.entrySet()){	
        			if((int)m.getValue() == max){
        				treeSet.add(String.valueOf(m.getKey()));
        			}
        }
        LOGGER.log(Level.INFO, treeSet.first());   
    } 
 
    public static void main (String[] args) { 
    	Scanner scanner = new Scanner(System.in);
    	LOGGER.log(Level.INFO, "Enter the number of names: ");
    	
    	int number=scanner.nextInt();
    	scanner.nextLine();
    	
    	String arr[] = new String[number];
    	LOGGER.log(Level.INFO, "Enter the names: ");
    	
    	for(int i=0;i<number;i++) {
    		arr[i] = scanner.nextLine();
    	}      
        printFreq(arr); 
    } 
}
