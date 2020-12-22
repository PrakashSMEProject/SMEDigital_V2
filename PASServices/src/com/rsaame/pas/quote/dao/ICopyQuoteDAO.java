package com.rsaame.pas.quote.dao;

import com.mindtree.ruc.cmn.base.BaseVO;

public interface ICopyQuoteDAO{

	public abstract BaseVO copyQuote( BaseVO baseVO );
	public abstract BaseVO copyQuoteCommon( BaseVO baseVO );
	
}
