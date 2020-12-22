/**
 * 
 */
package com.rsaame.pas.b2c.cmn.handlers;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.mindtree.ruc.cmn.utils.Utils;

import sun.misc.BASE64Encoder;


/**
 * @author Sarath
 *
 */
public class PaymentSecurity{
	
	private static final String HMAC_SHA256 = "HmacSHA256";
    
    protected String sign(HashMap<?, ?> params,String securityKey) throws InvalidKeyException, NoSuchAlgorithmException {
    	//String lob = params.get("merchant_defined_data9").toString();	/* commented unused varibale  - sonar violation fix */

    	final String SECRET_KEY = securityKey;
    	return sign(buildDataToSign(params), SECRET_KEY);
    }

    private String sign(String data, String secretKey) throws InvalidKeyException, NoSuchAlgorithmException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), HMAC_SHA256);
        Mac mac = Mac.getInstance(HMAC_SHA256);
        mac.init(secretKeySpec);
        byte[] rawHmac = mac.doFinal(data.getBytes());
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encodeBuffer(rawHmac).replace("\n", "");
    }

    private String buildDataToSign(HashMap<? ,?> params) {
        String[] signedFieldNames = String.valueOf(params.get("signed_field_names")).split(",");
        ArrayList<String> dataToSign = new ArrayList<String>();
        for (String signedFieldName : signedFieldNames) {
            dataToSign.add(signedFieldName + "=" + String.valueOf(params.get(signedFieldName)));
        }
        return commaSeparate(dataToSign);
    }

    private String commaSeparate(ArrayList<String> dataToSign) {
        StringBuilder csv = new StringBuilder();
        for (Iterator<String> it = dataToSign.iterator(); it.hasNext(); ) {
            csv.append(it.next());
            if (it.hasNext()) {
                csv.append(",");
            }
        }
        return csv.toString();
    }
}
