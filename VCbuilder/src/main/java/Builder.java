import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.json.simple.JSONObject;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Hash;
import org.web3j.crypto.Sign;
import org.web3j.crypto.Sign.SignatureData;
import org.web3j.utils.Numeric;

public class Builder {
	
	public JSONObject createVC(JSONObject config, String sub, String cf){
		
		JSONObject JWT = new JSONObject();
		System.out.println("> getting issuer's public key...");
		
		String keyString = (String) config.get("privateKey");
		keyString = keyString.replaceAll("\\s+", "");
		Credentials credentials = Credentials.create(keyString);
		ECKeyPair keyPair = credentials.getEcKeyPair();
		//String iss = keyPair.getPublicKey().toString(16);
		String iss = credentials.getAddress();
		String issPublicKey = keyPair.getPublicKey().toString(16);
		
		System.out.println("issuer: " + iss);
		System.out.println("public key: " + issPublicKey);
		
		JSONObject header = this.getHeader();
		JSONObject payload = this.getPayload(iss, sub, cf);
		
		JWT.put("header", header);
		JWT.put("payload", payload);
		
		/*String encodedHeader = Base64.getUrlEncoder().encodeToString(header.getBytes());
		String encodedPayload = Base64.getUrlEncoder().encodeToString(payload.getBytes());*/
		
		String message = header.toString() + "." + payload.toString();
		byte[] hashedMsg = Hash.sha3(message.getBytes(StandardCharsets.UTF_8));
		
		System.out.println("hash: " + Numeric.toHexString(hashedMsg));
		
		Sign.SignatureData sign = Sign.signMessage(hashedMsg, keyPair, false);
		
		String signature = this.getSignature(sign);	
		JWT.put("signature", signature);
		
		//String JWT = encodedHeader + "." + encodedPayload + "." + encodedSignature;
		
		System.out.println("JWT: " + JWT);
		
		return JWT;
		
	}
	
	public JSONObject getHeader(){
		System.out.println("> creating header...");
		
		JSONObject header = new JSONObject();
		header.put("typ", "JWT");
		header.put("alg","ES256K-R");
		return header;	
	}
	
	public JSONObject getPayload(String iss, String sub, String cf ){
		System.out.println("> creating payload...");
		
		JSONObject payload = new JSONObject();
		payload.put("iss", iss);
		payload.put("sub",sub);
		
		JSONObject csu = new JSONObject();
		csu.put("ownedBy", cf);
		
		payload.put("csu", csu);
		
		return payload;
	}
	
	public String getSignature(SignatureData sign){
		
		System.out.println("> generating signature...");
		
		/*JSONObject signature = new JSONObject();
		
		signature.put("r", Numeric.toHexString(sign.getR()));
		signature.put("s", Numeric.toHexString(sign.getS()));
		signature.put("v", new BigInteger(sign.getV()).intValue());*/
		
		System.out.println("r: " + Numeric.toHexString(sign.getR()));
		System.out.println("s: " + Numeric.toHexString(sign.getS()));
		System.out.println("v: " + new BigInteger(sign.getV()).intValue());
		
		String r = Numeric.toHexString(sign.getR()); 
		String s = Numeric.toHexString(sign.getS()); 
		String v = Integer.toHexString(new BigInteger(sign.getV()).intValue()); 
		
		String signature = r + s.replaceFirst("0x","") + v;
		
		return signature;
	}

	

}
