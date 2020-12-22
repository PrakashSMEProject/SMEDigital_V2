package com.rsaame.pas.tradelicense.svc;

import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.rsaame.pas.tradelicense.dao.ITradeLicenceDAO;
import com.rsaame.pas.transaction.dao.IViewTransactionDAO;

public class TradeLicenseSvc extends BaseService{
	
	ITradeLicenceDAO tradeLicence;
	
	@Override
	public Object invokeMethod(String methodName, Object... args) {
		BaseVO returnValue = null;
		if( "getQuoteDetails".equals( methodName ) ){
			returnValue = getQuoteDetails( (BaseVO) args[ 0 ] );
		}

		return returnValue;
	}

	private BaseVO getQuoteDetails(BaseVO baseVO) {
		return tradeLicence.getQuoteDetails(baseVO);
	}

	/**
	 * @param tradeLicence the tradeLicence to set
	 */
	public void setTradeLicence(ITradeLicenceDAO tradeLicence) {
		this.tradeLicence = tradeLicence;
	}
	


}
