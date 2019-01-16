package customStream;

import java.util.logging.Logger;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.Level;

/*
 * Passes the test String to be alternatively capitalized.
 */

public class FileHandling_two {
	
	private final static Logger LOGGER =  Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public static void main(String args[])  throws IOException {
		 try {

             File inputFile = new File("input_file.txt");

             if (inputFile.createNewFile())
                     LOGGER.log(Level.INFO, "File created");
             else
                     LOGGER.log(Level.INFO, "Error in creating file");
             
             FileReader fileReader = new FileReader(inputFile);

             CustomClass customClass= new CustomClass(fileReader);
             BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(inputFile));
             String testString = "helloworld";
             bufferedWriter.write(testString);
             bufferedWriter.close();
             customClass.readLine();

     }catch(IOException e){
             e.printStackTrace();
     }
	}
}
