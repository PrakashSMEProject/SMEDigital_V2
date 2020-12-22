package com.rsaame.pas.vo.app;

import java.util.List;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.reflect.IFieldValue;

public class AccountHistoryListVO extends BaseVO implements IFieldValue{

	private static final long serialVersionUID = 1L;

	private List<AccountHistoryVO> accountHistoryList;

	public Object getFieldValue( String fieldName ){
		Object fieldValue = null;

		if( "accountHistoryList".equals( fieldName ) ) fieldValue = getAccountHistoryList();

		return fieldValue;
	}

	/**
	 * @return the accountHistoryList
	 */
	public List<AccountHistoryVO> getAccountHistoryList(){
		return accountHistoryList;
	}

	/**
	 * @param accountHistoryList the accountHistoryList to set
	 */
	public void setAccountHistoryList( List<AccountHistoryVO> accountHistoryList ){
		this.accountHistoryList = accountHistoryList;
	}

}
