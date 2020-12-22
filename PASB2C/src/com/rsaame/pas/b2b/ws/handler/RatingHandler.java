/**
 * 
 */
package com.rsaame.pas.b2b.ws.handler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.rating.svc.PremiumCalculator;
import com.rsaame.pas.vo.bus.GeneralInfoVO;
import com.rsaame.pas.vo.bus.InsuredVO;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.PARUWDetailsVO;
import com.rsaame.pas.vo.bus.PLUWDetails;
import com.rsaame.pas.vo.bus.ParVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.PropertyRiskDetails;
import com.rsaame.pas.vo.bus.PropertyRisks;
import com.rsaame.pas.vo.bus.PublicLiabilityVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.RiskGroupingLevel;
import com.rsaame.pas.vo.bus.SectionVO;
import com.rsaame.pas.vo.bus.SumInsuredVO;
import com.rsaame.pas.vo.bus.UWDetails;
import com.rsaame.pas.vo.bus.UWQuestionRespType;
import com.rsaame.pas.vo.bus.UWQuestionVO;
import com.rsaame.pas.vo.bus.UWQuestionsVO;

/**
 * @author M1037404
 *
 */
public class RatingHandler {
	
	private static final Logger LOGGER = Logger.getLogger(RatingHandler.class);
	
	public BaseVO invokeRating(PolicyVO policyVO) {

		LOGGER.info("Entered invokeRating() method to call rating for all Sections ");
		PremiumCalculator premiumCalculator = new PremiumCalculator();
		BigDecimal renewalLoading = new BigDecimal(0);
		
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		policyVO = premiumCalculator.calculateRiskPremium(policyVO, renewalLoading);
		stopWatch.stop();

		LOGGER.info("Response time for invokeRating IS : " + stopWatch.getTime() + " milisecond");
		
		printPremium(policyVO);
		
		return policyVO;
	}
	private void printPremium(PolicyVO policyVO) {
		
		if(!Utils.isEmpty(policyVO.getRiskDetails())) {
			
			for(SectionVO sectionVO : policyVO.getRiskDetails()) {
				LOGGER.debug("Section ID::::::"+sectionVO.getSectionId()+" Name:::"+sectionVO.getSectionName());
				
				if(!Utils.isEmpty(sectionVO.getRiskGroupDetails())) {
					
					Map<? extends RiskGroup, ? extends RiskGroupDetails> riskGroupDetailsMap = sectionVO.getRiskGroupDetails();
					for( Map.Entry<? extends RiskGroup, ? extends RiskGroupDetails> entry : riskGroupDetailsMap.entrySet() ){
						RiskGroupDetails groupDetails = entry.getValue();
						
						if(!Utils.isEmpty(groupDetails.getPremium())) {
							LOGGER.debug("Premium for section Level ::: "+sectionVO.getSectionId()+":::"+groupDetails.getPremium().getPremiumAmt());
						}
					}
					
				}
				
			}
			
		}
		if(!Utils.isEmpty(policyVO.getPremiumVO())) {
			LOGGER.debug("Premium for Policy Level ::: "+policyVO.getPremiumVO());
		}
		
	}
	
}
