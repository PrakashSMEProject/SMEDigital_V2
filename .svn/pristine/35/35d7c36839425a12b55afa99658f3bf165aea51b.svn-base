/*
 * Copyright (c) 2007-2012 Royal and Sun Alliance Insurance Group plc.
 * St.Mark’s Court, Chart Way, Horsham, West Sussex RH12 1XL, U.K.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of Royal
 * and Sun Alliance Insurance Group plc.("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Royal and Sun Alliance Insurance Group plc.
 */
package com.rsaame.kaizen.framework.util;

import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.kaizen.framework.model.ServiceContext;
import com.rsaame.kaizen.framework.logger.AMELogger;
import com.rsaame.kaizen.framework.logger.AMELoggerFactory;


/**
 * @author Cognizant Technology Solutions
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class RoutingDataSource extends AbstractRoutingDataSource {

    private static final AMELogger logger = AMELoggerFactory.getInstance().getLogger(RoutingDataSource.class);

    private static final String CTX_SET_LOOKUP_KEY = "determineCurrentLookupKey()";

    protected Object determineCurrentLookupKey() {
        logger.debug(CTX_SET_LOOKUP_KEY, "Location ::" + ServiceContext.getLocation());
        
        if(!Utils.isEmpty( ServiceContext.getLocation() )){
        	return ServiceContext.getLocation();
        }else{
        	return Utils.getSingleValueAppConfig( "DEPLOYED_LOCATION" ); // Added for Oman in case of initial load the location is fetched from loc specific property file
        }
    }

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

}
