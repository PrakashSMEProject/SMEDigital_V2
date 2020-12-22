package com.rsaame.pas.cmn.access;

import java.util.HashMap;

import com.mindtree.ruc.cmn.utils.Utils;


/**
 * This enum represents all a role.
 */
public enum Role{
	RSA_USER( "RSA User" ),
	BROKER( "Broker" );
	
	private String roleName;
	private static HashMap<String, Role> allEntries;
	
	private Role( String roleName ) {
		this.roleName = roleName;
		addEntry( this );
	}
	
	private void addEntry( Role entry ){
		if( Utils.isEmpty( allEntries ) ){
			allEntries = new HashMap<String, Role>();
		}
		
		allEntries.put( entry.getRoleName(), entry );
	}
	
	public String getRoleName(){
		return this.roleName;
	}
	
	public static Role get( String roleName ){
		return allEntries.get( roleName );
	}
}

