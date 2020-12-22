/*
 * FileName.java
 *
 * Copyright (c) 2011-2012 Royal and Sun Alliance Insurance Group plc.
 * St.Marks Court, Chart Way, Horsham, West Sussex RH12 1XL, U.K.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of Royal
 * and Sun Alliance Insurance Group plc.("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Royal and Sun Alliance Insurance Group plc.
 */
package com.rsaame.pas.rating.svc;

import java.util.List;

import com.mindtree.ruc.cmn.log.Logger;
import com.rsaame.kaizen.framework.constants.AMEConstants;
import com.rsaame.pas.dao.utils.NextSequenceValue;
import com.rsaame.pas.vo.bus.RiskGroupDetails;

/**
 * @author M1017952
 *
 */
public class PremiumUpdater{
	private final static Logger logger = Logger.getLogger(PremiumUpdater.class);
	
	
	/**
	 * @param risk
	 * @param premiumListFromRating
	 */
	public RiskGroupDetails updatePremium( RiskGroupDetails risk, List premiumListFromRating ){
		
		try {
            logger.debug(AMEConstants.CTX_GET_COVERS_LIST,
                    "Method Start covers" + " policyPremiumList "
                            + premiumListFromRating + " risk" + risk);

            //TODO :Need to get the correct requirement  
            /*CoversDAO coversDAO = (CoversDAO) getBean(DAO_POL_COVERS);
            //Check for the status set in the UI is 'Yes' and the
            // policyPremiumList from writerate is not null
            if (AMEConstants.PREMIUM_STATUS.equalsIgnoreCase(policy
                    .getStatusForCovers())) {
                policy = coversDAO.getCoversList(policy);
                policy = compareCovers(policyPremiumList, policy);
            }*/
            
            //Check for the status set in the UI is 'No' and the
            // policyPremiumList from writerate is not null
            
            risk = compareCovers(premiumListFromRating, risk);
            
            logger.debug(AMEConstants.CTX_GET_COVERS_LIST, "Method End");
        } catch (Exception dataAccessException) {
            //TODO: Change the Excetion to DataException
        	//Implement exception Handling 
        	logger.debug("Exception : "+dataAccessException); /* Added logger statement - sonar violation fix */
        }
        return risk;
		
	}
	/**
	 * @param premiumListFromRating
	 * @param risk
	 * @return
	 */
	private RiskGroupDetails compareCovers( List premiumListFromRating, RiskGroupDetails risk ){
		// TODO Auto-generated method stub
		// This methode to be implemented as in GetCoversListBF.compareCovers(). But need to update risk instead of updating policy itself. 
		// risk in PAS= policy of KAIZEN. 
		// Update the premium in risk.
		// Need to update the PremiumVO for this. 
		
		return null;
	}
	
	
	

}
