package com.rsaame.pas.b2c.WsAuthentication;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import org.springframework.dao.DataAccessException;

import com.mindtree.ruc.cmn.utils.Utils;

import org.acegisecurity.providers.encoding.BasePasswordEncoder;


	/**
	 * This class is used to handle password encryption and decryption when
	 * interacting with a secure database. Class provides simple encryption
	 * capabilities via static methods.
	 * @author Cognizant Technology Solution
	 */
	public class EncryptionUtil {

	    /*
	     * Method to encode password
	     * @input - plain text password	  
	     */
	    public static String[] encodeTextUsingHashing(String input)
		{

			StringBuilder hash = new StringBuilder();
			String[] encodedPasswordHash = new String[2];
			byte salt[] = null;
			try {
				
				MessageDigest md = MessageDigest.getInstance("SHA-512");
				
				salt = getSalt();
				md.update(salt);
				byte[] hashedPassword = md.digest(input.getBytes(StandardCharsets.UTF_8));
				
				for(byte b : hashedPassword) {
					hash.append(String.format("%02x", b));
				}
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchProviderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String saltTo = "";
			for (byte b : salt) {
				saltTo+=" "+b;
			}
			encodedPasswordHash[0] = hash.toString();
			encodedPasswordHash[1] = saltTo;
			return encodedPasswordHash;
		}
	    /*
	     * Method to decrypt the password
	     *  @input - User entered password in plain text and will generate the password using existing hash
	     */
	    public static String decodeTextUsingHashing(String input,int userId)
		{
	    	StringBuilder hash = new StringBuilder();
			try {
				
				MessageDigest md = MessageDigest.getInstance("SHA-512");
				String salfFrom = null;  // get salt from DB stored for particular user
				salfFrom =	Utils.getSingleValueAppConfig("HashPasswordSalt_".concat(String.valueOf(userId)));
				String[] saltsplitted = salfFrom.split(" ");
				byte[] salt = new byte[saltsplitted.length];
				int index=0;
				for (String string : saltsplitted) {
					byte _byte = new Byte(string);
					salt[index++]=_byte;
				}
				
				md.update(salt);
				byte[] hashedPassword = md.digest(input.getBytes(StandardCharsets.UTF_8));
				
				for(byte b : hashedPassword) {
					hash.append(String.format("%02x", b));
				}
				return hash.toString();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return hash.toString();
		}
	    private static byte[] getSalt() throws NoSuchAlgorithmException, NoSuchProviderException {
	    	SecureRandom random = SecureRandom.getInstance("SHA1PRNG","SUN");
			byte salt[] = new byte[16];
			random.nextBytes(salt);
			return salt;
	    }
	    private static boolean isPasswordValid(String input, int userId, String storedPassword) {
	    	
	    	String hashedPassword = EncryptionUtil.decodeTextUsingHashing(input,userId);  // generate hash for user entered password
	    	
	    	if(hashedPassword.equals(storedPassword))   // compare the generated hash and stored hash
	    		return true;
	    	else 
	    		return false;
	    	
	    }
	    public static void main( String[] args ) throws Exception{
	    	BasicAuthenticationService service = new BasicAuthenticationService();
			//System.out.println( service.encodeText("994:test12345" ));
			System.out.println( service.decodeText("Basic OTk0OnRlc3QxMjM0NQ=="));
			System.out.println("Password Encyprtion Using Hashing::::");
			for (String string : EncryptionUtil.encodeTextUsingHashing("test12345")) {
				System.out.println(string);
			}
			System.out.println("Password Descryption Using Hashing::::"+EncryptionUtil.decodeTextUsingHashing("test12345",994));
			System.out.println(isPasswordValid("test12345",994, Utils.getSingleValueAppConfig("GetPassword")));
		}
	}

