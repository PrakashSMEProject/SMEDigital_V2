/**
 * 
 */
package com.rsaame.pas.quote.scheduler.svc;

import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.kaizen.framework.model.ServiceContext;
import com.rsaame.pas.dao.cmn.PASStoredProcedure;

/**
 * @author M1014320
 * This service will get executed once in a day and it will updates the status of the quotes 
 * which are pending from last 30 day to 5(Expired)
 */
public class QuoteExpirySchedulerSvc extends TimerTask {

	private final static Logger LOGGER = Logger.getLogger( QuoteExpirySchedulerSvc.class );
	
	@Override
	public void run() {
		if( LOGGER.isInfo() ) LOGGER.info( "--------Start 30 days quote expiry scheduler execution---------" );
		
		String location = ServiceContext.getLocation();;
		String locations = Utils.getSingleValueAppConfig("LOCATION_CODE",0);
		List<String> locationsToAppend = CopyUtils.asList(locations.split(","));
		
		for(String loc : locationsToAppend){
			ServiceContext.setLocation(loc);
			PASStoredProcedure sp = (PASStoredProcedure) Utils.getBean("expireQuoteProc");
			Map results = sp.call();
			
			if( LOGGER.isInfo() ) LOGGER.info( "--------QuoteExpiryScheduler - Number of quotes expired"+ results +"---------" );
		}
		
		ServiceContext.setLocation(location);
		
		
		if( LOGGER.isInfo() ) LOGGER.info( "--------30 days quote expiry scheduler execution completed successfully---------" );
	}

}
