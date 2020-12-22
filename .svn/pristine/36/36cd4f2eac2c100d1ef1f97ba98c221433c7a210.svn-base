package com.rsaame.pas.dao.cmn;

import org.springframework.jdbc.object.StoredProcedure;

import com.mindtree.ruc.cmn.utils.Map;
import com.mindtree.ruc.cmn.utils.Utils;

public class StoredProcUtils{
	private static final java.util.Map sps = new Map( String.class, StoredProcedure.class );
	
	/**
	 * Executes a Stored Procedure and returns the OUT parameters as a Map<String, Object>. The caller is expected to 
	 * know what the IN and OUT parameters for the Stored Procedure are.
	 * 
	 * @param spName An identifier for the Stored Procedure
	 * @param spArgs The IN parameter values in the order of their declaration in the Stored Procedure
	 * @return
	 */
	public static Map<String, Object> executeSP( String spName, Object... spArgs ){
		/* TODO Needs to be made generic to call any SP. Currently only DEL_LOCATION SP is supported here. */
		if( spName.equalsIgnoreCase( "DEL_LOCATION" ) ){
			DelLocationProc dl = (DelLocationProc) Utils.getBean( "delLocationProc" );
			dl.call( spArgs );
		}
		
		return null;
	}
}
