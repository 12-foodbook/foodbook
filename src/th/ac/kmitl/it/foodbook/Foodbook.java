package th.ac.kmitl.it.foodbook;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.apache.tomcat.util.codec.binary.Base64;

public class Foodbook {
    
    public static final String NANE = "Foodbook";
    public static final String VERSION = "0.0.1";
    
    private static final int DIGEST_ITERATION = 1000;
    
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
			for (int i = 0; i < DIGEST_ITERATION; i++) {
				digest.reset();
				hashedPassword = digest.digest(hashedPassword);
			}
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
