/**
 * 
 */
package com.rsaame.pas.renewals.svc;

import com.mindtree.ruc.cmn.base.BaseVO;

/**
 * To implemt renewals matrix which will be used by renewal framework created for monoline.
 * Each LOB to implement this interface as per need
 * @author Sarath
 * @since WC Monoline implementation
 *
 */
public interface IRenewalMatrix {
	
	/**
	 * Method to implement renewal matrix logic
	 * @param polData
	 * @param policyId
	 * @return
	 */
	 public BaseVO executeRenMatrix( BaseVO polData,Long policyId);

}
