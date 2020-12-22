package com.rsaame.pas.web;

import java.util.ArrayList;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.rsaame.pas.cmn.vo.UserProfile;

public class UserValidationSvc implements UserDetailsService{
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {
		
		//call user handler
		UserProfile userProfile = UserProfileHandler.getUserProfileVo(username);
		ArrayList<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
		//dummy role
		list.add(new GrantedAuthorityImpl("ROLE_USER"));
		
		UserDetails user =
			new User(username, userProfile.getPassword(), true, true, true, true, list); 
	
		return user;
	}

}
