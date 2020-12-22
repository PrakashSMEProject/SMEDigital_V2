package com.rsaame.pas.dos.svc;

import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.rsaame.pas.dao.cmn.BaseSectionSaveDAO;
import com.rsaame.pas.dos.dao.IDeteriorationOfStockDao;

public class DeteriorationOfStockSectionSvc extends BaseService{
	IDeteriorationOfStockDao deteriorationOfStockDao;
	
	public IDeteriorationOfStockDao getDeteriorationOfStockDao(){
		return deteriorationOfStockDao;
	}


	public void setDeteriorationOfStockDao( IDeteriorationOfStockDao deteriorationOfStockDao ){
		this.deteriorationOfStockDao = deteriorationOfStockDao;
	}


	@Override
	public Object invokeMethod( String methodName, Object... args ){
		BaseVO returnValue = null;
		if("loadDeteriorationOfStockSectionSvc".equals( methodName ))
			returnValue = loadDeteriorationOfStockSectionSvc( (BaseVO) args[ 0 ] );
		if("saveDeteriorationOfStockSectionSvc".equals( methodName ))
			returnValue = saveDeteriorationOfStockSectionSvc( (BaseVO) args[ 0 ] );
		return returnValue;
	}
	

	private BaseVO saveDeteriorationOfStockSectionSvc( BaseVO baseVO ){
		return ( (BaseSectionSaveDAO) deteriorationOfStockDao ).save( baseVO );
	}


	private BaseVO loadDeteriorationOfStockSectionSvc( BaseVO baseVO ){
		return deteriorationOfStockDao.loadDeteriorationOfStockSection( baseVO ); 
	}

}
