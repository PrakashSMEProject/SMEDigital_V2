package com.rsaame.pas.recentActivity.dao;

import com.mindtree.ruc.cmn.base.BaseVO;

public interface IShowRecentActivityDAO{
	
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
	
	/**
	 * This method is used to get renewal Quotes created by a user
	 * @param baseVO
	 * @return
	 */
	public abstract BaseVO showRenewalQuotes(BaseVO baseVO);
	

}
