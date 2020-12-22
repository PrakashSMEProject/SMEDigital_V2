/**
 * 
 */
package com.rsaame.pas.b2c.homeInsurance;

import java.text.ParseException;

import com.rsaame.pas.vo.bus.HomeInsuranceVO;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.cmn.CommonVO;

/**
 * Interface to define the Home Insurance Save/Load operations
 * @author Sarath
 * @since Phase 3
 *
 */

public interface IHomeInsuranceSvcHandler {
	
	/**
	 * 
	 * @param homeInsuranceData
	 * @return
	 * @throws ParseException 
	 */
	public PolicyDataVO saveHomeRiskCoverDetails(PolicyDataVO homeInsuranceData, boolean completePurchaseInd, String contextPath) throws ParseException;
	
	/**
	 * 
	 * @param homeInsuranceData
	 * @return
	 */
	public PolicyDataVO saveHomeInsuredDetails(PolicyDataVO homeInsuranceData, boolean completePurchaseInd, String contextPath, boolean isPrintCase);
	
	/**
	 * 
	 * @param commonVO
	 * @return
	 */
	public PolicyDataVO loadHomeInsuranceDetails(HomeInsuranceVO homeInsuranceData);

	/**
	 * 
	 * @param homeInsuranceData
	 * @return
	 */
	public PolicyDataVO convertToPolicy( PolicyDataVO homeInsuranceData );
	
	
	/**
	 * @param homeInsuranceData
	 * @throws ParseException
	 */
	public void populatePackagePremium(PolicyDataVO homeInsuranceData)throws ParseException;

	//public void populateHomePackagePremium( HomeInsuranceVO homeInsuranceVO ) throws ParseException;	

	//public PolicyDataVO loadCommonDetailsForRenewal( HomeInsuranceVO homeinInsuranceVO );

	public PolicyDataVO getRevisedHomeRenewalPremium( PolicyDataVO homeInsuranceVO );

	PolicyDataVO saveHomeRenewalInsuranceDetails( PolicyDataVO homeInsuranceData, boolean completePurchaseInd, String contextPath , boolean isPrintCase);
	
	public String getRenewalQuoteFromPolicy(long parseLong,String emailId);
	
	public String getQuoteFromPolicy(long parseLong,String emailId);
}
