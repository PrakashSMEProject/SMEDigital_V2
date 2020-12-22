package com.rsaame.pas.ee.svc;

import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.rsaame.pas.dao.cmn.BaseSectionSaveDAO;
import com.rsaame.pas.ee.dao.IEESectionDAO;

/**
 * This is a Service class which calls the Dao's to save and load
 * @author m1014438
 *
 */
public class EESectionSVC extends BaseService{

	IEESectionDAO eeSectionDAO;

	public IEESectionDAO getEeSectionDAO(){
		return eeSectionDAO;
	}

	public void setEeSectionDAO( IEESectionDAO eeSectionDAO ){
		this.eeSectionDAO = eeSectionDAO;
	}

	@Override
	public Object invokeMethod( String methodName, Object... args ){
		BaseVO returnValue = null;
		if( "loadEESectionSvc".equals( methodName ) ){
			returnValue = loadEESectionSvc( (BaseVO) args[ 0 ] );
		}
		if( "saveEESectionSvc".equals( methodName ) ){
			returnValue = saveEESectionSvc( (BaseVO) args[ 0 ] );
		}

		return returnValue;
	}

	private BaseVO saveEESectionSvc( BaseVO baseVO ){

		return ( (BaseSectionSaveDAO) eeSectionDAO ).save( baseVO );
	}

	private BaseVO loadEESectionSvc( BaseVO baseVO ){

		return eeSectionDAO.loadEESection( baseVO );
	}

}
