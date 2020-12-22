package com.rsaame.pas.auth.dao;

import java.util.HashMap;
import java.util.Map;

/**
 * A convenience implementation for bypassing authorization load. This is for Dev environments
 * only!
 */
public class NoDBAuthorizationDAO implements IAuthorizationDAO{

	@Override
	public Map<String, Map<String, String>> getAuthenticationDetails(){
		return new HashMap<String, Map<String, String>>();
	}

}
