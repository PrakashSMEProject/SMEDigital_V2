package com.rsaame.pas.com.svc;

import java.util.ArrayList;
import java.util.List;

import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.exception.SystemException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.endorse.svc.GeneralInfoCommonSvc;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.vo.bus.HomeInsuranceVO;
import com.rsaame.pas.vo.bus.LOB;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.TravelInsuranceVO;

/**
 * This service class is for ConvertToPolicyReminderScheduler for B2C application
 * 
 * @author M1020859
 *
 */

public class ConvertToPolicyReminderCmnSvc extends BaseService {
	
	private final static Logger logger = Logger.getLogger( ConvertToPolicyReminderCmnSvc.class );
	private GeneralInfoCommonSvc genrlInfoCmnSvc;
	
	/* (non-Javadoc)
	 * @see com.mindtree.ruc.cmn.reflect.IMethodInvocation#invokeMethod(java.lang.String, java.lang.Object[])
	 */
	@Override
	public Object invokeMethod(String methodName, Object... args) {
		List<PolicyDataVO> returnValue = null;
		boolean isTwentyFourHrsScheduler = Boolean.FALSE;
		if (methodName.equals("getLastDaysPendingQuotes")) {
			logger.debug("Going to call getPendingQuotesForToday method to get the pending quotes for today");
			isTwentyFourHrsScheduler = Boolean.TRUE;
			returnValue = getLastDaysPendingQuotes(isTwentyFourHrsScheduler);
		} else if (methodName.equals("getQuoteExpiryReminderData")) {
			returnValue = getLastDaysPendingQuotes(isTwentyFourHrsScheduler);
			logger.debug("Going to call scheduler for ");
		}
		return returnValue;
	}
	
	/**
	 * This method will fetch the quotes created 24 Hours back created from B2C application only
	 * and return the policyDataVO list back to the ConvertToPolicyReminderScheduler in B2C application
	 * 
	 * @return
	 */
	private List<PolicyDataVO> getLastDaysPendingQuotes(boolean isTwentyFourHrsScheduler) {
		
		List<PolicyDataVO> policyDataVOList = new ArrayList<PolicyDataVO>();
		List<PolicyDataVO> policyDataList = new ArrayList<PolicyDataVO>();
		PolicyDataVO polDataVO = null;
		
		try {
			policyDataVOList = DAOUtils.getLastDaysPendingQuotes(isTwentyFourHrsScheduler);
		} catch (SystemException systemException) {
			logger.debug(systemException.getMessage());
			return policyDataVOList;
		}
		
		if (!Utils.isEmpty(policyDataVOList)) {
			for (PolicyDataVO policyDataVO : policyDataVOList) {
				/** Generalized try catch block has been kept so that if any exception occurs during 
				 *  general info load or any exception caused due to any data load, Scheduler should not
				 *  get terminated
				 */
				try {
					polDataVO = (PolicyDataVO)Utils.getBean("POL_DATA_VO");
					if ((policyDataVO.getPolicyType() == Integer.valueOf(SvcConstants.SHORT_TRAVEL_POL_TYPE)) 
							|| (policyDataVO.getPolicyType() == Integer.valueOf(SvcConstants.LONG_TRAVEL_POL_TYPE))) { //For Travel Insurance
						TravelInsuranceVO travelInsuranceVO = (TravelInsuranceVO)Utils.getBean("VO_TRAVEL");
						travelInsuranceVO.setCommonVO(policyDataVO.getCommonVO());
						travelInsuranceVO.getCommonVO().setQuoteNo(policyDataVO.getQuoteNo());
						travelInsuranceVO.getCommonVO().setLocCode(policyDataVO.getAuthenticationInfoVO().getLocationCode());
						travelInsuranceVO.getCommonVO().setPolEffectiveDate(policyDataVO.getScheme().getEffDate());
						travelInsuranceVO.getCommonVO().setDocCode(policyDataVO.getAuthenticationInfoVO().getTxnType().shortValue());
						travelInsuranceVO.getCommonVO().setIsQuote(Boolean.TRUE);
						travelInsuranceVO.getCommonVO().setEndtId(policyDataVO.getEndtId());
						polDataVO = (PolicyDataVO) genrlInfoCmnSvc.invokeMethod(SvcConstants.LOAD_GEN_INFO, travelInsuranceVO);
						if( policyDataVO.getGeneralInfo().getSourceOfBus().getDistributionChannel().equals(SvcConstants.DIST_CHANNEL_BROKER) &&
								Utils.isEmpty(polDataVO.getGeneralInfo().getAdditionalInfo().getAffinityRefNo()) ){
							continue;
						}
						if (!Utils.isEmpty(polDataVO)) {
							polDataVO.getCommonVO().setLob(LOB.TRAVEL); //Setting explicitly the LOB based on policy type
							policyDataList.add(polDataVO);	
						}
						if(!Utils.isEmpty(polDataVO.getGeneralInfo().getAdditionalInfo().getAffinityRefNo())){
							polDataVO.getGeneralInfo().getSourceOfBus().setPartnerName(polDataVO.getGeneralInfo().getAdditionalInfo().getAffinityRefNo());
							DAOUtils.getPartnerMgmtDetail(polDataVO.getCommonVO(), polDataVO.getGeneralInfo(), 
									polDataVO.getScheme());
						}
					} // Added equals() instead of == to avoid sonar violation on 25-9-2017
					else if (policyDataVO.getPolicyType().equals(Integer.valueOf(SvcConstants.HOME_POL_TYPE))) { //For Home Insurance
						HomeInsuranceVO homeInsuranceVO = (HomeInsuranceVO)Utils.getBean("VO_HOME");
						homeInsuranceVO.setCommonVO(policyDataVO.getCommonVO());
						homeInsuranceVO.getCommonVO().setQuoteNo(policyDataVO.getQuoteNo());
						homeInsuranceVO.getCommonVO().setLocCode(Integer.valueOf(policyDataVO.getAuthenticationInfoVO().getLocationCode()));
						homeInsuranceVO.getCommonVO().setPolEffectiveDate(policyDataVO.getScheme().getEffDate());
						homeInsuranceVO.getCommonVO().setDocCode(policyDataVO.getAuthenticationInfoVO().getTxnType().shortValue());
						homeInsuranceVO.getCommonVO().setIsQuote(Boolean.TRUE);
						homeInsuranceVO.getCommonVO().setEndtId(policyDataVO.getEndtId());
						polDataVO = (PolicyDataVO) genrlInfoCmnSvc.invokeMethod(SvcConstants.LOAD_GEN_INFO, homeInsuranceVO);
						if( policyDataVO.getGeneralInfo().getSourceOfBus().getDistributionChannel().equals(SvcConstants.DIST_CHANNEL_BROKER) &&
								Utils.isEmpty(polDataVO.getGeneralInfo().getAdditionalInfo().getAffinityRefNo()) ){
							continue;
						}
						if (!Utils.isEmpty(polDataVO)) {
							polDataVO.getCommonVO().setLob(LOB.HOME); //Setting explicitly the LOB based on policy type
							policyDataList.add(polDataVO);
						}
						if(!Utils.isEmpty(polDataVO.getGeneralInfo().getAdditionalInfo().getAffinityRefNo())){
							polDataVO.getGeneralInfo().getSourceOfBus().setPartnerName(polDataVO.getGeneralInfo().getAdditionalInfo().getAffinityRefNo());
							DAOUtils.getPartnerMgmtDetail(polDataVO.getCommonVO(), polDataVO.getGeneralInfo(), 
									polDataVO.getScheme());
						}
					}
			  } catch(Exception exception) {
					logger.debug("General Info load failed for quotation number - "+String.valueOf(policyDataVO.getQuoteNo()) + " Reason - "+exception.getMessage());
				}
			} 
		} 
		logger.debug("returning the list of PolicyDataVO to B2C ConvertToPolicyReminderScheduler for trigerring email");
		return policyDataList;
	}
	
	/**
	 * @return the genrlInfoCmnSvc
	 */
	public GeneralInfoCommonSvc getGenrlInfoCmnSvc() {
		return genrlInfoCmnSvc;
	}

	/**
	 * @param genrlInfoCmnSvc the genrlInfoCmnSvc to set
	 */
	public void setGenrlInfoCmnSvc(GeneralInfoCommonSvc genrlInfoCmnSvc) {
		this.genrlInfoCmnSvc = genrlInfoCmnSvc;
	}


}
