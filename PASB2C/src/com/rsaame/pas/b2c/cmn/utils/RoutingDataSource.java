package com.rsaame.pas.b2c.cmn.utils;

import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
/**
 * @author m1020637
 *
 */
public class RoutingDataSource extends AbstractRoutingDataSource {

    
    protected Object determineCurrentLookupKey() {/*
        logger.debug(CTX_SET_LOOKUP_KEY, "Location ::" + ServiceContext.getLocation());
        
        if(!Utils.isEmpty( ServiceContext.getLocation() )){
        	return ServiceContext.getLocation();
        }else{
        	return Utils.getSingleValueAppConfig( "DEPLOYED_LOCATION" ); // Added for Oman in case of initial load the location is fetched from loc specific property file
        }
    */
    	return "20";
    }

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

}
