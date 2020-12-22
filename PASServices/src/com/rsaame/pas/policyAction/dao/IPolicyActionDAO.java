/**
 * 
 */
package com.rsaame.pas.policyAction.dao;

import com.mindtree.ruc.cmn.base.BaseVO;

/**
 * @author m1014644
 *
 */
public interface IPolicyActionDAO{

	BaseVO declineQuote( BaseVO baseVO );

	BaseVO rejectQuote( BaseVO baseVO );

	BaseVO approveQuote( BaseVO baseVO );

	BaseVO issueQuote( BaseVO baseVO );
	
	BaseVO getBrAccStatus( BaseVO baseVO );
	
	void updateDiscOnDemandReferral( BaseVO baseVO );

}
