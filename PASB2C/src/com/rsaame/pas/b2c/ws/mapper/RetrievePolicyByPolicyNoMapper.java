/**
 * 
 */
package com.rsaame.pas.b2c.ws.mapper;

import com.rsaame.pas.b2c.ws.vo.RetrievePolicyByPolicyNo;
import com.rsaame.pas.vo.bus.HomeInsuranceVO;
import com.rsaame.pas.vo.bus.TravelInsuranceVO;

/**
 * @author M1044786
 *
 */
public class RetrievePolicyByPolicyNoMapper implements BaseRequestVOMapper{
	
	@Override
	public void mapRequestToVO(Object requestObj, Object valueObj) throws Exception {
		RetrievePolicyByPolicyNo retrievePolicyByPolicyNo = null;
		if (requestObj instanceof RetrievePolicyByPolicyNo
				&& valueObj instanceof HomeInsuranceVO) {
			retrievePolicyByPolicyNo = (RetrievePolicyByPolicyNo) requestObj;
			HomeInsuranceVO homeInsuranceVO = (HomeInsuranceVO) valueObj;
			mapRequestToHomeInsuranceVO(retrievePolicyByPolicyNo, homeInsuranceVO);
		}else {
			retrievePolicyByPolicyNo = (RetrievePolicyByPolicyNo) requestObj;
			TravelInsuranceVO travelInsuranceVO = (TravelInsuranceVO) valueObj;
			mapRequestToTravelInsuranceVO(retrievePolicyByPolicyNo, travelInsuranceVO);
		}
		
	}

	/**
	 * @description : Value Object = HomeInsuranceVO
	 * @param HomeInsuranceVO
	 */
	private void mapRequestToHomeInsuranceVO(RetrievePolicyByPolicyNo retrievePolicyByPolicyNo,HomeInsuranceVO homeInsuranceVO){
		homeInsuranceVO.getCommonVO().setPolicyNo(retrievePolicyByPolicyNo.getPolicyNo().longValue());
		homeInsuranceVO.getGeneralInfo().getInsured().setEmailId(retrievePolicyByPolicyNo.getEmailId());
	}
	
	/**
	 * @description : Value Object = TravelInsuranceVO
	 * @param TravelInsuranceVO
	 */
	private void mapRequestToTravelInsuranceVO(RetrievePolicyByPolicyNo retrieveQuoteByQuoteId,TravelInsuranceVO travelInsuranceVO){
		travelInsuranceVO.getCommonVO().setPolicyNo(retrieveQuoteByQuoteId.getPolicyNo().longValue());
		travelInsuranceVO.getCommonVO().setCreatedBy(retrieveQuoteByQuoteId.getDob().toString());
	}
	

}
