/**
 * 
 */
package com.rsaame.pas.b2c.ws.mapper;

import java.util.ArrayList;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.b2c.ws.vo.RetrieveHomeQuoteByPolicyResponse;
import com.rsaame.pas.b2c.ws.vo.RetrieveTravelQuoteByPolicyResponse;
import com.rsaame.pas.b2c.ws.vo.UpdateHomeQuoteResponse;
import com.rsaame.pas.b2c.ws.vo.UpdateTravelQuoteResponse;
import com.rsaame.pas.vo.bus.HomeInsuranceVO;
import com.rsaame.pas.vo.bus.TravelInsuranceVO;

/**
 * @author M1037404
 *
 */
public class RetrieveQuoteByPolicyResponseMapper implements BaseResponseVOMapper{

	@Override
	public void mapVOToResponse(Object valueObj, Object requestObj) throws Exception {
		// TODO Auto-generated method stub
		if(valueObj instanceof HomeInsuranceVO && requestObj instanceof RetrieveHomeQuoteByPolicyResponse) {
			
			HomeInsuranceVO homeInsuranceVO = (HomeInsuranceVO) valueObj;
			RetrieveHomeQuoteByPolicyResponse policyResponse = (RetrieveHomeQuoteByPolicyResponse) requestObj;
			BaseResponseVOMapper baseResponseVOMapper = new HomeUpdateQuoteResponseMapper();
			UpdateHomeQuoteResponse updateHomeQuoteResponse = new UpdateHomeQuoteResponse();
			
			if(Utils.isEmpty(policyResponse.getQuotes())) {
				policyResponse.setQuotes(new ArrayList<UpdateHomeQuoteResponse>());
			}
			policyResponse.getQuotes().add(updateHomeQuoteResponse);
			baseResponseVOMapper.mapVOToResponse(homeInsuranceVO, updateHomeQuoteResponse);
			
		}else if(valueObj instanceof TravelInsuranceVO && requestObj instanceof RetrieveTravelQuoteByPolicyResponse) {
			TravelInsuranceVO travelInsuranceVO = (TravelInsuranceVO) valueObj;
			RetrieveTravelQuoteByPolicyResponse policyResponse = (RetrieveTravelQuoteByPolicyResponse) requestObj;
			BaseResponseVOMapper baseResponseVOMapper = new TravelUpdateQuoteResponseMapper();
			UpdateTravelQuoteResponse updateTravelQuoteResponse = new UpdateTravelQuoteResponse();			
			if(Utils.isEmpty(policyResponse.getQuotes())) {
				policyResponse.setQuotes(new ArrayList<UpdateTravelQuoteResponse>());
			}
			policyResponse.getQuotes().add(updateTravelQuoteResponse);
			baseResponseVOMapper.mapVOToResponse(travelInsuranceVO, updateTravelQuoteResponse);
		}
	}

}
