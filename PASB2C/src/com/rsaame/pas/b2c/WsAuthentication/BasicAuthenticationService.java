package com.rsaame.pas.b2c.WsAuthentication;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateTemplate;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.b2c.WsAuthentication.filters.WebServiceFilter;
import com.rsaame.pas.dao.model.TMasUser;

public class BasicAuthenticationService {
	private final static Logger LOGGER = Logger.getLogger(BasicAuthenticationService.class);
	public String encodeText(String input)
	{
		LOGGER.info("Starts encodeText");
		byte[] bytes = input.getBytes(StandardCharsets.UTF_8); 
		String base64Encoded = Base64.getEncoder().encodeToString(bytes);
		return "Basic "+base64Encoded;
	}
	public String decodeText(String input)
	{
		LOGGER.info("Starts decodeText");
		String input1 = input.replaceFirst("Basic ","");
		byte[] asBytes = Base64.getDecoder().decode(input1); 
		String base64Decoded = new String(asBytes, StandardCharsets.UTF_8);
		return base64Decoded;
	}
	
	public String[] getUserIdAndPassword(String input)
	{
		LOGGER.info("Starts getUserIdAndPassword");
		String[] s = new String[2];
		StringTokenizer tokenizer = new StringTokenizer(input, ":");
		s[0]=tokenizer.nextToken();
		s[1]=tokenizer.nextToken();
		return s;
	}
	
	public boolean authenticateUser(String authorization)
	{
		LOGGER.info("Starts authenticateUser");
		String s = decodeText(authorization);
		LOGGER.info(s);
		String[] credentials = getUserIdAndPassword(s);
		int userId =Integer.parseInt(credentials[0]);
		LOGGER.info(credentials[0]);
		//LOGGER.info(credentials[1]);
		
		try{
			/*UserProfile user = UserProfileHandler.getUserProfileVo(993);*/
			LOGGER.info("try block authenticateUser()");
			String password = EncryptionUtil.decodeTextUsingHashing(credentials[1],userId);
			LOGGER.info(password);
			HibernateTemplate ht = (HibernateTemplate) Utils.getBean( "hibernateTemplate" );
			List<TMasUser> userRecs = new ArrayList<TMasUser>();
			String query = "from TMasUser where userId = ?";
			userRecs = ht.find( query, userId );
			LOGGER.info("Query in authenticateUser:-- " +userRecs);
			if (userRecs == null) {
				LOGGER.info("authenticateUser false");
				return false;
			}else {
					for (TMasUser user : userRecs) {
						LOGGER.info(user.toString());
						if (user.getUserId() == userId && user.getPassword().equals(password) && user.getStatusId() == 1) {
							LOGGER.info("authenticateUser true :" +user.getUserId());
							return true;
						}
					}
				}
			}
		catch(Exception e)
		{
			LOGGER.info(e);
			return false;
		}
		//System.out.println(userId +" : "+password);
		return false;
	}
}
