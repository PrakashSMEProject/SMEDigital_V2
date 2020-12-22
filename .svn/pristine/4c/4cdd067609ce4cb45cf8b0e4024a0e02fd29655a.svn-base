package com.rsaame.pas.web;

/**
 * @author Seema Ramakrishna
 *
 */
import java.io.UnsupportedEncodingException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import com.mindtree.ruc.cmn.log.Logger;

public class DesEncrypter {
   private static final Logger logger=Logger.getLogger(DesEncrypter.class);
   static Cipher ecipher;
   static  Cipher dcipher;

    // 8-byte Salt
    byte[] salt = {
        (byte)0xA9, (byte)0x9B, (byte)0xC8, (byte)0x32,
        (byte)0x56, (byte)0x35, (byte)0xE3, (byte)0x03
    };

    // Iteration count
    //int iterationCount = 19;

 public  DesEncrypter(String passPhrase) {
        try {
            // Create the key
            KeySpec keySpec = new PBEKeySpec(passPhrase.toCharArray(), salt, passPhrase.length());
            SecretKey key = SecretKeyFactory.getInstance(
                "PBEWithMD5AndDES").generateSecret(keySpec);
          //Added class name for static field to fix sonar violation 0n 19-9-2017 
          //  ecipher = Cipher.getInstance(key.getAlgorithm());
			ecipherMethod(Cipher.getInstance(key.getAlgorithm()));
			
			dcipherMethod(Cipher.getInstance(key.getAlgorithm()));
			//dcipher = Cipher.getInstance(key.getAlgorithm());

            // Prepare the parameter to the ciphers
            AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, passPhrase.length());

            // Create the ciphers
            ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
            dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
        } catch (java.security.InvalidAlgorithmParameterException e) {
            logger.debug("Invalid Algorithm Parameter Exception" +e );
        } catch (java.security.spec.InvalidKeySpecException e) {
            logger.debug("Invalid Key SpecException" +e );
        } catch (javax.crypto.NoSuchPaddingException e) {
            logger.debug("No Such Padding Exception" +e );
        } catch (java.security.NoSuchAlgorithmException e) {
            logger.debug("No Such Algorithm Exception" +e );
        } catch (java.security.InvalidKeyException e) {
            logger.debug("Invalid Key Exception" +e );
        }
    }

 
	private synchronized static void setCipher(Cipher cipher) {
		DesEncrypter.ecipher = cipher;
	}

	public void ecipherMethod(Cipher cipher) {
		setCipher(cipher);
	}
	
	
	private synchronized static void setDcipher(Cipher dcipher) {
		DesEncrypter.dcipher = dcipher;
	}

	public void dcipherMethod(Cipher dcipher) {
		setDcipher(dcipher);
	}
    public String encrypt(String str) {
        try {
            // Encode the string into bytes using utf-8
            byte[] utf8 = str.getBytes("UTF8");

            // Encrypt
            byte[] enc = ecipher.doFinal(utf8);

            // Encode bytes to base64 to get a string
            return new sun.misc.BASE64Encoder().encode(enc);
        } catch (javax.crypto.BadPaddingException e) {
            logger.debug("Bad Padding Exception"+e);
        } catch (IllegalBlockSizeException e) {
            logger.debug("Illegal Block Size Exception" +e );
        } catch (UnsupportedEncodingException e) {
            logger.debug("Unsupported Encoding Exception" +e );
        }
        return null;
    }

    public String decrypt(String str) {
        try {
            // Decode base64 to get bytes
            byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(str);

            // Decrypt
            byte[] utf8 = dcipher.doFinal(dec);

            // Decode using utf-8
            return new String(utf8, "UTF8");
        } catch (javax.crypto.BadPaddingException e) {
            logger.debug("Bad Padding Exception"+e);
        } catch (IllegalBlockSizeException e) {
            logger.debug("Illegal Block Size Exception" +e );
        } catch (UnsupportedEncodingException e) {
            logger.debug("Unsupported Encoding Exception" +e );
        } catch (java.io.IOException e) {
            logger.debug("IO Exception" +e );
        }
        return null;
    }



}

