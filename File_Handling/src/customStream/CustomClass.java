package customStream;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/*
 * A custom writer class.
 */

public class CustomClass extends BufferedReader {

	CustomClass(FileReader file){
			super(file);
	}

	@Override
	public String readLine() throws IOException {
		// TODO Auto-generated method stub
		File outputFile = new File("output_file.txt");
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFile));
		
		String i =super.readLine(); 
		char array[] = i.toCharArray();
		for(int j=0 ; j<i.length();j++){
			if(j % 2 == 0){
				bufferedWriter.write(Character.toUpperCase(array[j]));
			}else{
				bufferedWriter.write(array[j]);
			}
		}
		bufferedWriter.close();
		return " ";
	}

}
