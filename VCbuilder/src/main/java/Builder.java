import java.io.UnsupportedEncodingException;
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

import io.jsonwebtoken.Jwts;

public class Builder {
	
	public String createVC(JSONObject config, String sub){
		
		String issuer = (String) config.get("iss");
		String keyString = (String) config.get("privateKey");
		keyString = keyString.replaceAll("\\s+", "");
		
		String jws = Jwts.builder()
				
				.setIssuer(issuer)
			    .setSubject(sub)
			    .setExpiration(new Date())
			    .setIssuedAt(Date.from(Instant.now().plus(500, ChronoUnit.DAYS)))
			    .claim("csu", getCSU())
			    .signWith(parseKey(keyString))          
			     
			    .compact();
		
		return jws;
	}

	public PrivateKey parseKey(String keyString){
		try {
			byte [] pkcs8EncodedBytes = Base64.getDecoder().decode(keyString.getBytes("UTF-8"));
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pkcs8EncodedBytes);
			KeyFactory kf;
			kf = KeyFactory.getInstance("RSA");
			PrivateKey privKey = kf.generatePrivate(keySpec);
			return privKey;
		} catch (NoSuchAlgorithmException | InvalidKeySpecException | UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}		
	}
	
	public JSONObject getCSU(){
		JSONObject csu = new JSONObject();
		csu.put("type", "DigitalIdentityConfirmationCredential");
		csu.put("name", "Address Validation");
		return csu;
	}
	

}
