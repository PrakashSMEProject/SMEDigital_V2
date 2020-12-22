/**
 * 
 */
package com.rsaame.pas.b2c.ws.mapper;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.b2c.ws.vo.RetrieveQuoteByQuoteId;
import com.rsaame.pas.vo.bus.HomeInsuranceVO;
import com.rsaame.pas.vo.bus.TravelInsuranceVO;

/**
 * @author M1044786
 *
 */
public class RetrieveQuoteByQuoteIDMapper implements BaseRequestVOMapper{

	@Override
	public void mapRequestToVO(Object requestObj, Object valueObj) throws Exception {
		RetrieveQuoteByQuoteId retrieveQuoteByQuoteId = null;
		if (requestObj instanceof RetrieveQuoteByQuoteId
				&& valueObj instanceof HomeInsuranceVO) {
			retrieveQuoteByQuoteId = (RetrieveQuoteByQuoteId) requestObj;
			HomeInsuranceVO homeInsuranceVO = (HomeInsuranceVO) valueObj;
			mapRequestToHomeInsuranceVO(retrieveQuoteByQuoteId, homeInsuranceVO);
		}else {
			retrieveQuoteByQuoteId = (RetrieveQuoteByQuoteId) requestObj;
			TravelInsuranceVO travelInsuranceVO = (TravelInsuranceVO) valueObj;
			mapRequestToTravelInsuranceVO(retrieveQuoteByQuoteId, travelInsuranceVO);
		}
		
	}

	/**
	 * @description : Value Object = HomeInsuranceVO
	 * @param HomeInsuranceVO
	 */
	private void mapRequestToHomeInsuranceVO(RetrieveQuoteByQuoteId retrieveQuoteByQuoteId,HomeInsuranceVO homeInsuranceVO){
		homeInsuranceVO.getCommonVO().setQuoteNo(retrieveQuoteByQuoteId.getQuotationNo().longValue());
		homeInsuranceVO.getCommonVO().setEndtId(retrieveQuoteByQuoteId.getEndtId());
		homeInsuranceVO.getCommonVO().setEndtNo(retrieveQuoteByQuoteId.getEndtNo());
		homeInsuranceVO.getGeneralInfo().getInsured().setEmailId(retrieveQuoteByQuoteId.getEmailId());
	}
	
	/**
	 * @description : Value Object = TravelInsuranceVO
	 * @param TravelInsuranceVO
	 */
	private void mapRequestToTravelInsuranceVO(RetrieveQuoteByQuoteId retrieveQuoteByQuoteId,TravelInsuranceVO travelInsuranceVO){
		travelInsuranceVO.getCommonVO().setQuoteNo(retrieveQuoteByQuoteId.getQuotationNo().longValue());
		if(Utils.isEmpty(retrieveQuoteByQuoteId.getEndtId()) || Utils.isEmpty(retrieveQuoteByQuoteId.getEndtNo()) ){
			travelInsuranceVO.getCommonVO().setEndtId(0L);
			travelInsuranceVO.getCommonVO().setEndtNo(0L);
		}else{
			travelInsuranceVO.getCommonVO().setEndtId(retrieveQuoteByQuoteId.getEndtId());
			travelInsuranceVO.getCommonVO().setEndtNo(retrieveQuoteByQuoteId.getEndtNo());
		}
		travelInsuranceVO.getGeneralInfo().getInsured().setEmailId(retrieveQuoteByQuoteId.getEmailId());
	}
	
	//Sonar Fix for removing Methods should not be empty
	/**
	 * @description : Null Check Objects
	 * @param homeInsuranceVO
	 */
	/*public void initialize(Object nullCheckObject){
		
	
	}*/
}
