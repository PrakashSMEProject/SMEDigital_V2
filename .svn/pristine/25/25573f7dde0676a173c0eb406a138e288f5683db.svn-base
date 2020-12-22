/**
 * 
 */
package com.rsaame.pas.vo.bus;

import com.mindtree.ruc.cmn.base.BaseVO;

/**
 * @author
 *
 */
public class TransactionDetailVO extends BaseVO{

	private static final long serialVersionUID = 1L;
	private TransactionVO transaction;
	private PolicyVO policy;

	public Object getFieldValue( String fieldName ){
		Object fieldValue = null;

		if( "transaction".equals( fieldName ) ) fieldValue = getTransaction();
		if( "policy".equals( fieldName ) ) fieldValue = getPolicy();
		
		return fieldValue;
	}

	/**
	 * @return the transaction
	 */
	public TransactionVO getTransaction(){
		return transaction;
	}

	/**
	 * @param transaction the transaction to set
	 */
	public void setTransaction( TransactionVO transaction ){
		this.transaction = transaction;
	}

	/**
	 * @return the policy
	 */
	public PolicyVO getPolicy(){
		return policy;
	}

	/**
	 * @param policy the policy to set
	 */
	public void setPolicy( PolicyVO policy ){
		this.policy = policy;
	}

}
