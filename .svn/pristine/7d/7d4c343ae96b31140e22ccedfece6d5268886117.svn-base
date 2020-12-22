/**
 * 
 */
package com.rsaame.pas.cmn.vo;

import com.mindtree.ruc.cmn.vo.User;

/**
 * Represents the profile of a user in the application.
 */
public class UserProfile extends User {

	private static final long serialVersionUID = 1L;

	IRSAUser rsaUser;
	
	
	
	public UserProfile() {
		this.rsaUser = RSAUserFactory.getRSAUserInstance();		
	}



	@Override
	public Object getFieldValue(String fieldName) {
		// TODO Auto-generated method stub
		return null;
	}



	public IRSAUser getRsaUser() {
		return rsaUser;
	}



	/**
	 * @param rsaUser the rsaUser to set
	 */
	public void setRsaUser( IRSAUser rsaUser ){
		this.rsaUser = rsaUser;
	}
	

}
