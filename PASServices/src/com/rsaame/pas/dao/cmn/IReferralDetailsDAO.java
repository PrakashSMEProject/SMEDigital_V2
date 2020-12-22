package com.rsaame.pas.dao.cmn;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.rsaame.pas.vo.bus.PolicyVO;

public interface IReferralDetailsDAO{
	public void insertReferalData( BaseVO input );
	public BaseVO isReferralNeeded( BaseVO input );
	public void storeRenewalReferrals( BaseVO baseVO );
	public void deleteRenewalReferral(BaseVO baseVO);
	public BaseVO getEndorsementText();
	public BaseVO isReferralNeededForHomeAndTravel(BaseVO baseVO);
	public void insertReferalDataDisc(PolicyVO policyVO);	
	public BaseVO deleteReferral(BaseVO baseVO);

}
