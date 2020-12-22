package com.rsaame.pas.cmn.vo;

import com.mindtree.ruc.cmn.exception.SystemException;
import com.mindtree.ruc.cmn.utils.Utils;

/**
 * A factory class to get instance of IRSAUser implementation. The onus of controlling duplicity or
 * thread-safety is left to the implementation of the interface.
 */
public class RSAUserFactory {
	/** 
	 * Returns a new instance of the implementation of IRSAUser interface as configured in
	 * app config. */
	public static IRSAUser getRSAUserInstance(){
		String rsaUserImplClassName = Utils.getSingleValueAppConfig( "RSAUSER_IMPL_CLASS" );
		if( Utils.isEmpty( rsaUserImplClassName ) ){
			throw new SystemException( "pas.rsaUserImplNotFound", null, "No class that implements IRSAUser has been specified" );
		}
		
		return (IRSAUser) Utils.newInstance( rsaUserImplClassName );
	}
}
