package com.rsaame.pas.b2c.ws.handler;

import java.math.BigDecimal;
import java.text.ParseException;

import com.rsaame.pas.b2c.homeInsurance.HomeInsuranceSvcHandler;
import com.rsaame.pas.b2c.homeInsurance.IHomeInsuranceSvcHandler;
import com.rsaame.pas.b2c.ws.beans.BuildingDetails;
import com.rsaame.pas.b2c.ws.beans.HomeCreateModifyQuoteResponse;
import com.rsaame.pas.vo.bus.BuildingDetailsVO;
import com.rsaame.pas.vo.bus.HomeInsuranceVO;
import com.rsaame.pas.vo.bus.PolicyDataVO;

public class HomeServiceHandler {

	/**
	 * @param homeInsuranceVO
	 * @return
	 */
	public HomeCreateModifyQuoteResponse saveCreateQuote(HomeInsuranceVO homeInsuranceVO,String contextPath) throws ParseException{
		
		IHomeInsuranceSvcHandler homeInsuranceSvcHandler = new HomeInsuranceSvcHandler();
		HomeCreateModifyQuoteResponse response = new HomeCreateModifyQuoteResponse();
		boolean completePurchaseInd = false;
		//Call service
			// save cover details
		//remove emirates and buildiname
		BuildingDetailsVO buildingDetailsVO = homeInsuranceVO.getBuildingDetails();
		String name = buildingDetailsVO.getBuildingname();
		String emirates = buildingDetailsVO.getEmirates();
		String morgName = buildingDetailsVO.getMortgageeName();
		buildingDetailsVO.setBuildingname(null);
		buildingDetailsVO.setEmirates(null);
		buildingDetailsVO.setMortgageeName(null);
		HomeInsuranceSvcHandler.setDefaultValues(homeInsuranceVO);
		homeInsuranceSvcHandler.saveHomeRiskCoverDetails((PolicyDataVO)homeInsuranceVO, completePurchaseInd, contextPath);
		//save insured details
		homeInsuranceVO.getBuildingDetails().setBuildingname(name);
		homeInsuranceVO.getBuildingDetails().setEmirates(emirates);
		homeInsuranceVO.getBuildingDetails().setMortgageeName(morgName);
		homeInsuranceSvcHandler.saveHomeInsuredDetails((PolicyDataVO)homeInsuranceVO, completePurchaseInd, contextPath, false);
		
		//add response
		BigDecimal premium = new BigDecimal(homeInsuranceVO.getPremiumVO().getPremiumAmt());
		response.setPremiumValue(premium);
		response.setQuoteId(homeInsuranceVO.getCommonVO().getQuoteNo());
		//return
		return response;
	}
}
