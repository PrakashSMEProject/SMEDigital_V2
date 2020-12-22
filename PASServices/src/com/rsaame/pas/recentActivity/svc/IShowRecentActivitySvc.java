package com.rsaame.pas.recentActivity.svc;

import com.mindtree.ruc.cmn.base.BaseVO;

public interface IShowRecentActivitySvc{
	
	/**
	 * This method is used to get recent endorsements done by a user
	 * @param baseVO
	 * @return
	 */
	public abstract BaseVO showRecentEndorsements(BaseVO baseVO);
	
	/**
	 * This method is used to get recent quotes created by a user
	 * @param baseVO
	 * @return
	 */
	public abstract BaseVO showRecentQuotes(BaseVO baseVO);
	
	/**
	 * This method is used to get recent renewals done by a user
	 * @param baseVO
	 * @return
	 */
	public abstract BaseVO showRecentRenewals(BaseVO baseVO);
	
	/**
	 * This method is used to get recent policy created by a user
	 * @param baseVO
	 * @return
	 */
	public abstract BaseVO showRecentNewBusiness(BaseVO baseVO);

	
	public abstract BaseVO showRenewalQuotes( BaseVO baseVO );
		
}
