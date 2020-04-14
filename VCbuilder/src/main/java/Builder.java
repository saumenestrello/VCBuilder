import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;

import org.json.simple.JSONObject;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Sign;
import org.web3j.crypto.Sign.SignatureData;
import org.web3j.protocol.Web3j;
import org.web3j.utils.Numeric;

public class Builder {
	
	public String createVC(JSONObject config, String sub, String cf){
		
		System.out.println("> getting issuer's public key...");
		
		String keyString = (String) config.get("privateKey");
		keyString = keyString.replaceAll("\\s+", "");
		Credentials credentials = Credentials.create(keyString);
		ECKeyPair keyPair = credentials.getEcKeyPair();
		String iss = keyPair.getPublicKey().toString(16);
		
		System.out.println("public key: " + iss);
		
		String header = this.getHeader();
		String payload = this.getPayload(iss, sub, cf);
		
		String encodedHeader = Base64.getUrlEncoder().encodeToString(header.getBytes());
		String encodedPayload = Base64.getUrlEncoder().encodeToString(payload.getBytes());
		
		Sign.SignatureData sign = Sign.signMessage((encodedHeader + "." + encodedPayload).getBytes(), keyPair);
		
		String signature = this.getSignature(sign);		
		String encodedSignature = Base64.getUrlEncoder().encodeToString(signature.getBytes());
		
		String JWT = encodedHeader + "." + encodedPayload + "." + encodedSignature;
		
		System.out.println("JWT: " + JWT);
		
		return JWT;
		
	}
	
	public String getHeader(){
		System.out.println("> creating header...");
		
		JSONObject header = new JSONObject();
		header.put("typ", "JWT");
		header.put("alg","ES256K-R");
		return header.toString();	
	}
	
	public String getPayload(String iss, String sub, String cf ){
		System.out.println("> creating payload...");
		
		JSONObject payload = new JSONObject();
		payload.put("iss", iss);
		payload.put("sub",sub);
		
		JSONObject csu = new JSONObject();
		csu.put("ownedBy", cf);
		
		payload.put("csu", csu);
		
		return payload.toString();
	}
	
	public String getSignature(SignatureData sign){
		
		System.out.println("> generating signature...");
		
		JSONObject signature = new JSONObject();
		signature.put("r", Numeric.toHexString(sign.getR()));
		signature.put("s", Numeric.toHexString(sign.getS()));
		signature.put("v", new BigInteger(sign.getV()).intValue());
		
		System.out.println("r: " + Numeric.toHexString(sign.getR()));
		System.out.println("s: " + Numeric.toHexString(sign.getS()));
		System.out.println("v: " + new BigInteger(sign.getV()).intValue());
		
		return signature.toString();
	}

	

}
