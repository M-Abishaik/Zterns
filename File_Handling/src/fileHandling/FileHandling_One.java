package fileHandling;

import java.io.BufferedWriter;

import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.*; 

/*
 * Creates two files, writes content into them, merges the content into a third file and deletes the old files.
 */
 
public class FileHandling_One {
	
	private final static Logger LOGGER =  
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	public static void main(String args[]) throws IOException {
		BufferedWriter bufferedWriter_one = null, bufferedWriter_two = null;
		BufferedReader bufferedReader_one = null, bufferedReader_two = null;
		PrintWriter printWriter = null;
		String line = "";
				
		try {
	            FileWriter writer_one = new FileWriter("File_One.txt", true);
	            bufferedWriter_one = new BufferedWriter(writer_one);
	 
	            bufferedWriter_one.write("Hello World!");
	            bufferedWriter_one.newLine();
	            bufferedWriter_one.write("See You Again!");
	            
	            bufferedWriter_one.close();
	            
	            FileWriter writer_two = new FileWriter("File_Two.txt", true);
	            bufferedWriter_two = new BufferedWriter(writer_two);
	       	 
	            bufferedWriter_two.write("Hey, there!");
	            bufferedWriter_two.newLine();
	            bufferedWriter_two.write("See You Soon!");
	            
	            bufferedWriter_two.close();
	            
	            
	            printWriter = new PrintWriter("file3.txt"); 
	            
	            bufferedReader_one = new BufferedReader(new FileReader("File_One.txt")); 
	            
	            line = bufferedReader_one.readLine(); 
	            
	            while (line != null) 
	            { 
	            	System.out.println(line);
	                printWriter.println(line); 
	                line = bufferedReader_one.readLine(); 
	            } 
	            
	            bufferedReader_one.close();
	            
	            bufferedReader_two = new BufferedReader(new FileReader("File_Two.txt")); 
	            
	            line = bufferedReader_two.readLine(); 
	              
	            while(line != null) 
	            { 
	            	System.out.println(line);
	                printWriter.println(line);
	                line = bufferedReader_two.readLine(); 
	            } 
	            
	            bufferedReader_two.close();
	            
	        	printWriter.flush();
		        printWriter.close();
	            
	            try
	            { 
	                Files.deleteIfExists(Paths.get("File_One.txt")); 
	                Files.deleteIfExists(Paths.get("File_Two.txt"));            
	            } 
	            catch(NoSuchFileException e) 
	            { 
	                System.out.println("No such file/directory exists"); 
	            } 
	            catch(DirectoryNotEmptyException e) 
	            { 
	                System.out.println("Directory is not empty."); 
	            } 
	            catch(IOException e) 
	            { 
	                System.out.println("Invalid permissions."); 
	            } 	         
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            
	        }
	 
	    }
}
