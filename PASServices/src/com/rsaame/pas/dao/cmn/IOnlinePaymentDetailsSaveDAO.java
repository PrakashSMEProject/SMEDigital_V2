/**
 * 
 */
package com.rsaame.pas.dao.cmn;

import com.mindtree.ruc.cmn.base.BaseVO;

/**
 * Interface to handle the payment details save operations
 * @author Sarath
 * @since Phase 3
 *
 */
public interface IOnlinePaymentDetailsSaveDAO{
	
	/**
	 * Method to save the payment details
	 * @param baseVO
	 * @return
	 */
	public BaseVO savePaymentDetails(BaseVO baseVO);
	
	public BaseVO savePaymentRequestDetails(BaseVO baseVO);

}
