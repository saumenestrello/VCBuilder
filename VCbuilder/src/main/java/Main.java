import org.json.simple.JSONObject;

public class Main {

	public static void main(String[] args) {
		
		Builder builder = new Builder();
		
		String sub = "did:eth:0xcf87ce923fe20968F491556Df7833C948400d68a";
		JSONObject config = FileHandler.getConfig();
		
		String VC = builder.createVC(config, sub);
		
		System.out.println(VC);
		
		FileHandler.writeJWSToFile(VC);
		
	}

}
