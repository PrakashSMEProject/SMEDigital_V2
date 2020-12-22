package com.rsaame.pas.vo.app;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.reflect.IFieldValue;
import com.rsaame.pas.vo.bus.TransactionVO;

/**
 * @author M1020278
 * 
 */
public class SearchTransactionVO extends BaseVO implements IFieldValue {

	private static final long serialVersionUID = 1L;
	private TransactionVO transaction;
	private String insuredName;
	private String agent;
	private String nationality;
	private boolean allDirect;
	private String callStatus;

	

	@Override
	public Object getFieldValue(String fieldName) {
		// TODO Auto-generated method stub
		return null;
	}

	public TransactionVO getTransaction() {
		return transaction;
	}


	public void setTransaction(TransactionVO transaction) {
		this.transaction = transaction;
	}
	
	public String getInsuredName() {
		return insuredName;
	}

	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}

	public String getAgent() {
		return agent;
	}


	public void setAgent(String agent) {
		this.agent = agent;
	}


	public String getNationality() {
		return nationality;
	}


	public void setNationality(String nationality) {
		this.nationality = nationality;
	}


	public boolean isAllDirect() {
		return allDirect;
	}


	public void setAllDirect(boolean allDirect) {
		this.allDirect = allDirect;
	}


	public String getCallStatus() {
		return callStatus;
	}


	public void setCallStatus(String callStatus) {
		this.callStatus = callStatus;
	}



}
