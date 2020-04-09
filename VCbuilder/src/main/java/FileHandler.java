import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class FileHandler {

	public static JSONObject getConfig(){

		try {

			File file = new File("config.txt"); 
			BufferedReader br = new BufferedReader(new FileReader(file)); 
			String st; 
			String fileString = "";

			while ((st = br.readLine()) != null) {
				fileString = fileString + st; 
			}
			
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(fileString);
			
			return obj;
			
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}

	} 
	
	public static void writeJWSToFile(String fileString){
		fileString.replaceAll("\\s+", "");
	    try {
	    	FileWriter writer = new FileWriter("jws.txt");
			writer.write(fileString.toCharArray());
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}


