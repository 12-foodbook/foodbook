package th.ac.kmitl.it.foodbook;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.apache.tomcat.util.codec.binary.Base64;

public class Foodbook {
    
    public static final String NANE = "Foodbook";
    
    private Foodbook() {}
    
    public static void main(String[] args) {
    	byte[] salt = getSalt();
    	byte[] hashedPassword = hashPassword("password1@#$", salt);
    	String saltString = bytesToString(salt);
    	String hashedPasswordString = bytesToString(hashedPassword);
    	System.out.println(saltString);
    	System.out.println(hashedPasswordString);
    }

    public static byte[] hashPassword(String password, byte[] salt) {
    	byte[] hashedPassword = null;
    	MessageDigest digest = null;
    	try {
			digest = MessageDigest.getInstance("SHA-256");
			digest.reset();
			digest.update(salt);
			hashedPassword = digest.digest(password.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	return hashedPassword;
    }
    
    public static byte[] getSalt() {
    	SecureRandom random = null;
    	byte[] salt = null;
		try {
			random = SecureRandom.getInstance("SHA1PRNG");
	    	salt = new byte[8];
	    	random.nextBytes(salt);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
    	return salt;
    }
    
    public static String bytesToString(byte[] bytes) {
    	return Base64.encodeBase64String(bytes);
    }
    
    public static byte[] stringToBytes(String string) {
    	return Base64.decodeBase64(string);
    }

}
