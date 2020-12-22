/**
 * 
 */
package com.rsaame.pas.endorse.dao;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.rsaame.pas.dao.model.TTrnPolicyQuo;

/**
 * @author m1014241
 *
 */
public interface IAmendPolicyDao {

	
	public BaseVO isEndorsePending(BaseVO baseVO) ;
	public boolean getInsuredChangeDetails(BaseVO baseVO);
	
	public BaseVO getEndorsementSummary(BaseVO baseVO);
	public BaseVO getCancelPolRefundPremium(BaseVO baseVO);
	public BaseVO isInsuredChanged(BaseVO baseVO) ;
	public BaseVO deletePendingPolicy(BaseVO baseVO);
	public BaseVO updateInsurePol(BaseVO baseVO);	
	public BaseVO processCancelPolicy(BaseVO baseVO);
	public BaseVO getActivePolicy(BaseVO baseVO);
	public BaseVO isEndtEffectiveDateValid(BaseVO baseVO);
	public BaseVO isRenewalQuoteExists(BaseVO baseVO);
	public TTrnPolicyQuo getLatestEndorsedPolicyDataVO( BaseVO baseVO );
	public void checkForEffectiveDate( BaseVO baseVO, Object[] data );
	public void checkForEffectiveDateShortTerm( BaseVO baseVO, Object[] data );
	
	
	
}
