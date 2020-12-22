package com.rsaame.pas.svc.cmn;

import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.dao.cmn.IFetchPPPValDAO;
import com.rsaame.pas.vo.app.PPPCriteriaVO;

public class FetchPPPValSvc extends BaseService implements IFetchPPPValSvc{

	private IFetchPPPValDAO  iFetchPPPValDAO;
	
	public IFetchPPPValDAO getiFetchPPPValDAO() {
		return iFetchPPPValDAO;
	}

	public void setiFetchPPPValDAO(IFetchPPPValDAO iFetchPPPValDAO) {
		this.iFetchPPPValDAO = iFetchPPPValDAO;
	}

	@Override
	public Object invokeMethod(String methodName, Object... args) {
		BaseVO returnValue = null;
		
		if( "fetchPPPValSvc".equals( methodName ) ){
 			returnValue = fetchPPPVal( (BaseVO) args[ 0 ] );
		}
		if( "getSectionListForPPP".equals( methodName ) ){
 			returnValue = getSectionListForPPP( (BaseVO) args[ 0 ] );
		}
		
		return returnValue;
	}

	@Override
	public BaseVO fetchPPPVal(BaseVO input) {

		return iFetchPPPValDAO.fetchPPPVal( input );
	}
	
	/**
	 * This method is used to fetch configured section list for a given tariff and PolicyType
	 */
	public BaseVO getSectionListForPPP( BaseVO baseVO ){
	
		return iFetchPPPValDAO.getSectionListForPPP( baseVO );
	}

}
