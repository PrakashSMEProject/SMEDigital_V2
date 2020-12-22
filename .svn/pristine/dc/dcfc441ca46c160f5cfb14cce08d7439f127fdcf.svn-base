/**
 * 
 */
package com.rsaame.pas.vo.bus;

import com.mindtree.ruc.cmn.base.BaseVO;

/**
 * @author
 *
 */
public class ViewTransactionRequestVO extends BaseVO{

	private static final long serialVersionUID = 1L;
	private TransactionVO transaction;

	public Object getFieldValue( String fieldName ){
		Object fieldValue = null;

		if( "transaction".equals( fieldName ) ) fieldValue = getTransaction();

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

}
