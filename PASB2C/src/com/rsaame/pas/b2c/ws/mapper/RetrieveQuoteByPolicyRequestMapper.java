/**
 * 
 */
package com.rsaame.pas.b2c.ws.mapper;

import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.b2c.cmn.constants.AppConstants;
import com.rsaame.pas.b2c.ws.vo.RetrieveQuoteByPolicyRequest;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.bus.AddressVO;
import com.rsaame.pas.vo.bus.BusinessChannel;
import com.rsaame.pas.vo.bus.GeneralInfoVO;
import com.rsaame.pas.vo.bus.HomeInsuranceVO;
import com.rsaame.pas.vo.bus.InsuredVO;
import com.rsaame.pas.vo.bus.LOB;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.bus.SchemeVO;
import com.rsaame.pas.vo.bus.SourceOfBusinessVO;
import com.rsaame.pas.vo.bus.TravelInsuranceVO;
import com.rsaame.pas.vo.cmn.CommonVO;

/**
 * @author M1037404
 *
 */
public class RetrieveQuoteByPolicyRequestMapper implements BaseRequestVOMapper{
	private final static Logger LOGGER = Logger.getLogger(RetrieveQuoteByPolicyRequestMapper.class);
	
	@Override
	public void mapRequestToVO(Object requestObj, Object valueObj) throws Exception {
		// TODO Auto-generated method stub
		
		LOGGER.info("Enters to RetrieveQuoteByPolicyRequestMapper.mapRequestToVO, maps the request to homeInsuranceVO details..");
		
		
		if (requestObj instanceof RetrieveQuoteByPolicyRequest
				&& valueObj instanceof HomeInsuranceVO) {
			
			RetrieveQuoteByPolicyRequest quoteByPolicyRequest = (RetrieveQuoteByPolicyRequest) requestObj;
			HomeInsuranceVO homeInsuranceVO = (HomeInsuranceVO) valueObj;

			//Objects Null Check
			initialiseObjects(homeInsuranceVO);
			
			mapQuoteDetails(quoteByPolicyRequest, homeInsuranceVO);
		}
		else if (requestObj instanceof RetrieveQuoteByPolicyRequest
				&& valueObj instanceof TravelInsuranceVO) {
			
			RetrieveQuoteByPolicyRequest quoteByPolicyRequest = (RetrieveQuoteByPolicyRequest) requestObj;
			TravelInsuranceVO travelInsuranceVO = (TravelInsuranceVO) valueObj;
			
			initialiseObjects(travelInsuranceVO);
			
			mapQuoteDetails(quoteByPolicyRequest, travelInsuranceVO);
		}
		
		LOGGER.info("RetrieveQuoteByPolicyRequestMapper.mapRequestToVO details is done..");
	}
	private void mapQuoteDetails(RetrieveQuoteByPolicyRequest quoteByPolicyRequest,PolicyDataVO policyDataVO) {
		
		if(!Utils.isEmpty(policyDataVO.getCommonVO()) && policyDataVO instanceof HomeInsuranceVO){
			policyDataVO.getCommonVO().setPolicyNo(quoteByPolicyRequest.getTransactionNumber().longValue());
			policyDataVO.getCommonVO().setLocCode(AppConstants.B2C_DEFAULT_DEPLOYED_LOCATION);
			policyDataVO.getCommonVO().setChannel(BusinessChannel.B2C);
			policyDataVO.getCommonVO().setLob( LOB.HOME );
			policyDataVO.getCommonVO().setAppFlow(Flow.VIEW_QUO);
		}
		else if(!Utils.isEmpty(policyDataVO.getCommonVO()) && policyDataVO instanceof TravelInsuranceVO) {
			policyDataVO.getCommonVO().setPolicyNo(quoteByPolicyRequest.getTransactionNumber().longValue());
			policyDataVO.getCommonVO().setLocCode(AppConstants.B2C_DEFAULT_DEPLOYED_LOCATION);
			policyDataVO.getCommonVO().setChannel(BusinessChannel.B2C);
			policyDataVO.getCommonVO().setLob( LOB.TRAVEL );
			policyDataVO.getCommonVO().setAppFlow(Flow.VIEW_QUO);
		}
		
	}
	private void initialiseObjects(PolicyDataVO policyDataVO){
		if(Utils.isEmpty(policyDataVO.getScheme())) {
			policyDataVO.setScheme(new SchemeVO());
		}
		if(Utils.isEmpty(policyDataVO.getCommonVO())) {
			policyDataVO.setCommonVO(new CommonVO());
		}
	/*	if(Utils.isEmpty(homeInsuranceVO.getBuildingDetails())) {
			homeInsuranceVO.setBuildingDetails(new BuildingDetailsVO());
		}
		if(Utils.isEmpty(homeInsuranceVO.getBuildingDetails().getRiskCodes())) {
			homeInsuranceVO.getBuildingDetails().setRiskCodes(new RiskVO());;
		}
		if(Utils.isEmpty(homeInsuranceVO.getBuildingDetails().getSumInsured())) {
			homeInsuranceVO.getBuildingDetails().setSumInsured(new SumInsuredVO());
		}
		if(Utils.isEmpty(homeInsuranceVO.getBuildingDetails().getCoverCodes())) {
			homeInsuranceVO.getBuildingDetails().setCoverCodes(new CoverVO());;
		}
		if(Utils.isEmpty(homeInsuranceVO.getCovers())) {
			homeInsuranceVO.setCovers(new ArrayList<CoverDetailsVO>());
		}*/
		if(Utils.isEmpty(policyDataVO.getGeneralInfo())) {
			policyDataVO.setGeneralInfo(new GeneralInfoVO());
		}
		if(Utils.isEmpty(policyDataVO.getGeneralInfo().getInsured())) {
			policyDataVO.getGeneralInfo().setInsured(new InsuredVO());
		}
		if(Utils.isEmpty(policyDataVO.getGeneralInfo().getInsured().getAddress())) {
			policyDataVO.getGeneralInfo().getInsured().setAddress(new AddressVO());;
		}
		if(Utils.isEmpty(policyDataVO.getGeneralInfo().getSourceOfBus())) {
			policyDataVO.getGeneralInfo().setSourceOfBus(new SourceOfBusinessVO());
		}
		/*if(Utils.isEmpty(homeInsuranceVO.getStaffDetails())) {
			homeInsuranceVO.setStaffDetails(new ArrayList<StaffDetailsVO>());
		}*/
		if(Utils.isEmpty(policyDataVO.getPremiumVO())) {
			policyDataVO.setPremiumVO(new PremiumVO());
		}
	}

}
