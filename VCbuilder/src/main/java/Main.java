import org.json.simple.JSONObject;

public class Main {

	public static void main(String[] args) {
		
		Builder builder = new Builder();
		
		String sub = "0xcf87ce923fe20968F491556Df7833C948400d68a";
		String cf = "BLRVQM40H43L753F";
		JSONObject config = FileHandler.getConfig();
		
		JSONObject VC = builder.createVC(config,sub,cf);
		
		FileHandler.writeJWSToFile(VC);
		
	}

}
