/*
 * AMEBeanUtils.java
 *
 * Copyright (c) 2007-2012 Royal and Sun Alliance Insurance Group plc.
 * St.Marks Court, Chart Way, Horsham, West Sussex RH12 1XL, U.K.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of Royal
 * and Sun Alliance Insurance Group plc.("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Royal and Sun Alliance Insurance Group plc.
 */
package com.rsaame.pas.web;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import org.springframework.dao.DataAccessException;
import org.acegisecurity.providers.encoding.BasePasswordEncoder;


/**
 * This class is used to handle password encryption and decryption when
 * interacting with a secure database. Class provides simple encryption
 * capabilities via static methods.
 * @author Cognizant Technology Solution
 */
public class EncryptionUtil extends BasePasswordEncoder {

    
    private static javax.crypto.SecretKey desKey = null;

    private static final String MD5KEY = "MD5KEY";

    private static String saltSource = "R&SA_AME";
    
    private static javax.crypto.Cipher cipher = null;
    
    
    private static final String ENCRYPT_UTIL = "EncryptionUtil";

    private static void generateKeys() {
        try {
             cipher = javax.crypto.Cipher.getInstance("DES/CBC/PKCS5Padding");
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] hash = md5.digest(saltSource.getBytes());
            DESKeySpec desKeySpec;
            desKeySpec = new DESKeySpec(hash);
            SecretKeyFactory keyFact = SecretKeyFactory.getInstance("DES");
            desKey = keyFact.generateSecret(desKeySpec);
        } catch (InvalidKeyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Method encrypts the passed string data and returns encrypted result
     * 
     * @param data to be encrypted
     * @return encrypted data
     * @exception FCException error
     */
    public static String encrypt(String data) throws Exception {
        generateKeys();
        final byte[] ivBytes = new byte[] { 00, 00, 00, 00, 00, 00, 00, 00 };
        final IvParameterSpec iv = new IvParameterSpec(ivBytes);
        cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, desKey, iv);
        byte[] encrypted = cipher.doFinal(data.getBytes());
        return (byteToHex(encrypted));
    }

    /**
     * Method decrypts data
     * @param data encrypted data
     * @return decrypted data
     * @exception FCException error
     */
    public static String decrypt(String encrypted) throws Exception {
        generateKeys();
        //final byte[] ivBytes = new byte[cipher.getBlockSize()];
        final byte[] ivBytes = new byte[] { 00, 00, 00, 00, 00, 00, 00, 00 };
        final IvParameterSpec iv = new IvParameterSpec(ivBytes);
        byte[] encbytes = hexToByte(encrypted);
        cipher.init(javax.crypto.Cipher.DECRYPT_MODE, desKey, iv);
        byte[] decbytes = cipher.doFinal(encbytes);
        return (new String(decbytes));

    }

    //
    // -------------------------------------------------------------------------------------------------------
    // EncryptUtil Implementation
    // -------------------------------------------------------------------------------------------------------
    //

    private static String byteToHex(byte[] hash) {
        StringBuffer buf = new StringBuffer(hash.length * 2);
        int i;
        for (i = 0; i < hash.length; i++) {
            if ((hash[i] & 0xff) < 0x10) {
                buf.append("0");
            }
            buf.append(Integer.toHexString(hash[i] & 0xff));
        }
        return (buf.toString());
    }

    private static byte[] hexToByte(String hexStr) {
        if (hexStr == null) {
            return (null);
        }
        byte[] barray = new byte[hexStr.length() / 2];
        int j = 0;
        for (int i = 0; i < hexStr.length(); i += 2) {
            String tempStr = hexStr.substring(i, i + 2);
            barray[j] = Integer.valueOf(tempStr, 16).byteValue();
            j++;
        }
        return (barray);
    }

   
    /*
     * (non-Javadoc)
     * @see org.acegisecurity.providers.encoding.PasswordEncoder#encodePassword(java.lang.String,
     *      java.lang.Object)
     */
    @Override
	public String encodePassword(String rawPassword, Object salt) throws DataAccessException {
        String encodedPassword = null;
        try {
            encodedPassword = encrypt(rawPassword);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return encodedPassword;
    }

    /*
     * (non-Javadoc)
     * @see org.acegisecurity.providers.encoding.PasswordEncoder#isPasswordValid(java.lang.String,
     *      java.lang.String, java.lang.Object)
     */
    @Override
	public boolean isPasswordValid(String encodedPassword, String rawPassword, Object salt) throws DataAccessException {
        boolean result = false;
        if (encodedPassword.equalsIgnoreCase(encodePassword(rawPassword, salt))) {
            result = true;
        } else {
            result = false;
        }
        return result;
    }

    public synchronized static void setSaltSource(String saltSource) {
        EncryptionUtil.saltSource = saltSource;
    }
    
    public static void main( String[] args ) throws Exception{
		System.out.println( EncryptionUtil.decrypt( "aca3cff0605c35787389906b11fec67e" ) );
	}
}