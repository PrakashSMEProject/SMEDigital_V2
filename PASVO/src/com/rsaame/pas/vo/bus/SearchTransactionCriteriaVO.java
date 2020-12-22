/**
 * 
 */
package com.rsaame.pas.vo.bus;

import com.mindtree.ruc.cmn.base.BaseVO;

/**
 * @author
 *
 */
public class SearchTransactionCriteriaVO extends BaseVO{

	private static final long serialVersionUID = 1L;

	private TransactionVO transaction;

	private String quoteEntered;
	private String policyEntered;
	private String quotePolicy;
	private boolean searchQuote;
	private boolean searchPolicy;
	private boolean exactSearch;
	private boolean lastTransaction;
	private String forHistoryView;

	public Object getFieldValue( String fieldName ){
		Object fieldValue = null;

		if( "transaction".equals( fieldName ) ) fieldValue = getTransaction();
		if( "searchQuote".equals( fieldName ) ) fieldValue = getSearchQuote();
		if( "searchPolicy".equals( fieldName ) ) fieldValue = getSearchPolicy();
		if( "exactSearch".equals( fieldName ) ) fieldValue = getExactSearch();
		if( "quoteEntered".equals( fieldName ) ) fieldValue = getQuoteEntered();
		if( "policyEntered".equals( fieldName ) ) fieldValue = getPolicyEntered();
		if( "quotePolicy".equals( fieldName ) ) fieldValue = getQuotePolicy();
		if( "lastTransaction".equals( fieldName ) ) fieldValue = getLastTransaction();

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
	 * @return the searchQuote
	 */
	public boolean getSearchQuote(){
		return searchQuote;
	}

	/**
	 * @param searchQuote the searchQuote to set
	 */
	public void setSearchQuote( boolean searchQuote ){
		this.searchQuote = searchQuote;
	}

	/**
	 * @return the searchPolicy
	 */
	public boolean getSearchPolicy(){
		return searchPolicy;
	}

	/**
	 * @param searchPolicy the searchPolicy to set
	 */
	public void setSearchPolicy( boolean searchPolicy ){
		this.searchPolicy = searchPolicy;
	}

	/**
	 * @return the exactSearch
	 */
	public boolean getExactSearch(){
		return exactSearch;
	}

	/**
	 * @param exactSearch the exactSearch to set
	 */
	public void setExactSearch( boolean exactSearch ){
		this.exactSearch = exactSearch;
	}

	/**
	 * @return the lastTransaction
	 */
	public boolean getLastTransaction(){
		return lastTransaction;
	}

	/**
	 * @param lastTransaction the lastTransaction to set
	 */
	public void setLastTransaction( boolean lastTransaction ){
		this.lastTransaction = lastTransaction;
	}

	public void setQuoteEntered( String quoteEntered ){
		this.quoteEntered = quoteEntered;
	}

	public String getQuoteEntered(){
		return quoteEntered;
	}

	public void setPolicyEntered( String policyEntered ){
		this.policyEntered = policyEntered;
	}

	public String getPolicyEntered(){
		return policyEntered;
	}

	public void setQuotePolicy( String quotePolicy ){
		this.quotePolicy = quotePolicy;
	}

	public String getQuotePolicy(){
		return quotePolicy;
	}

	public String getForHistoryView() {
		return forHistoryView;
	}

	public void setForHistoryView(String forHistoryView) {
		this.forHistoryView = forHistoryView;
	}

}
