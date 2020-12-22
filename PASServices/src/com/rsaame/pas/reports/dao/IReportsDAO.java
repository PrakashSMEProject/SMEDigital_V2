/**
 * 
 */
package com.rsaame.pas.reports.dao;

import com.mindtree.ruc.cmn.base.BaseVO;

/**
 * @author M1014957
 *
 */
public interface IReportsDAO {
	public abstract BaseVO acctSearch(BaseVO baseVO);
	public abstract BaseVO livePolSearch(BaseVO baseVO);
	public abstract BaseVO classPrmSearch(BaseVO baseVO);
	
	public abstract BaseVO getViewSmsList(BaseVO baseVO);
	public abstract BaseVO saveNewSms(BaseVO baseVO);
	public abstract BaseVO deleteSms(BaseVO baseVO);
	public abstract BaseVO paymentSearch(BaseVO baseVO);
	public abstract BaseVO renewalPaymentSearch(BaseVO baseVO);
	public abstract BaseVO transReportsDetailSearch(BaseVO baseVO);
	public abstract BaseVO transReportsSearch(BaseVO baseVO);
	public abstract BaseVO promoCodeReportSearch(BaseVO baseVO);
	public abstract BaseVO quoteSearch(BaseVO baseVO);
	
}