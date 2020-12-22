package com.rsaame.pas.quote.svc;

import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.rsaame.pas.quote.dao.ICopyQuoteDAO;

public class CopyQuoteSvc extends BaseService{

	ICopyQuoteDAO copyQuoteDAO;

	@Override
	public Object invokeMethod( String methodName, Object... args ){
		BaseVO returnValue = null;
		if( "copyQuote".equals( methodName ) ){
			returnValue = copyQuote( (BaseVO) args[ 0 ] );
		}
		if( "copyQuoteCommon".equals( methodName ) ){
			returnValue = copyQuoteCommon( (BaseVO) args[ 0 ] );
		}
		return returnValue;
	}

	public BaseVO copyQuote( BaseVO baseVO ){
		BaseVO returnValue = copyQuoteDAO.copyQuote( baseVO );
		return returnValue;
	}
	
	public BaseVO copyQuoteCommon( BaseVO baseVO ){
		BaseVO returnValue = copyQuoteDAO.copyQuoteCommon( baseVO );
		return returnValue;
	}

	/**
	 * @param copyQuoteDAO the copyQuoteDAO to set
	 */
	public void setCopyQuoteDAO( ICopyQuoteDAO copyQuoteDAO ){
		this.copyQuoteDAO = copyQuoteDAO;
	}
	


}
