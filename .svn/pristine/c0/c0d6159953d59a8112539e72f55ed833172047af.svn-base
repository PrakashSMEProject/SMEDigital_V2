/**
 * 
 */
package com.rsaame.pas.renewals.dao;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.rsaame.pas.dao.model.TTrnPolicyQuo;
import com.rsaame.pas.vo.bus.TravelInsuranceVO;

/**
 * @author m1019193
 *
 */
public interface IRenewalCommonDAO {
	BaseVO getPoliciesToBeRenewed (BaseVO criteria);

	BaseVO generateOnlineRenewal(BaseVO baseVO);

	BaseVO getRenewalQuotations(BaseVO baseVO);

	void savePoliciesForBatchRenewal(BaseVO baseVO);

	BaseVO getClaimCount(BaseVO baseVO);

	BaseVO checkForReprint(BaseVO baseVO);

	void savePoliciesForBatchPrint(BaseVO baseVO);
	
	void updateQuotationStatus(BaseVO baseVO);
	
	void updateRenewalTerm(BaseVO baseVO);
	
	void updatePrmStatusForSoftStop(BaseVO baseVO);
	
	void updateGprStatusForSoftStop(BaseVO baseVO);

	BaseVO checkForResendMail(BaseVO baseVO);	

	BaseVO getRenewalNoticeNotSent(BaseVO baseVO);

	void saveRenewalNotice(BaseVO baseVO);

	void sendRenewalMessage(BaseVO baseVO);
	
	BaseVO getClaimDetails( BaseVO baseVO );
	
	BaseVO getFraudClaim( BaseVO baseVO );
	
	//Long getPolicyId( TravelInsuranceVO travelInsuranceVO );

	BaseVO isMedicalClaimPolicy( Long refPolicyId );

	BaseVO getTravelClaimAmount( Long refPolicyId );

	BaseVO isRenewalPolicyForHomeAndTravel(BaseVO baseVO);

	//added for monoline base- renewal future implementation
	BaseVO generateOnlineRenewalMonoline( BaseVO baseVO );	
	/**
	 * Method to fetch the policy details based on criteria ie policyId
	 * @param input
	 * @return TTrnPolicyQuo
	 * @since WC Monoline
	 */
	public TTrnPolicyQuo getQuoteDetails(Object [] input);

	public void softStopCommon(TravelInsuranceVO travelInsuranceVO);
	// Added for Trade license check for WC monoline renewal after clicking on Convert to Policy - Ticket Id 144816
	public BaseVO getPreviousYearQuoteNo(BaseVO commonVO);

}
