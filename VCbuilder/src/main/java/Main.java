import java.util.Scanner;

import org.json.simple.JSONObject;

public class Main {

	public static void main(String[] args) {
		
		Builder builder = new Builder();
		Scanner sc = new Scanner (System.in);
		
		//String sub = "0xcf87ce923fe20968F491556Df7833C948400d68a";
		//String cf = "BLRVQM40H43L753F";
		
		System.out.println("Insert address:");
		String sub = sc.nextLine(); //address to be verified
		
		System.out.println("Insert fiscal code:");
		String cf = sc.nextLine(); //address owner fiscal code
		
		JSONObject config = FileHandler.getConfig(); //load issuer's private key from config.txt
		
		JSONObject VC = builder.createVC(config,sub,cf); //create VC
		
		FileHandler.writeJWSToFile(VC); //write VC to file
		
	}

}
