/**
 * 
 */
package com.rsaame.pas.wc.svc;

import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.rsaame.pas.dao.cmn.BaseSectionSaveDAO;
import com.rsaame.pas.wc.dao.IWCSectionDAO;

/**
 * @author m1014644
 *
 */
public class WCSectionSVC extends BaseService {

	
	IWCSectionDAO wcSectionDAO;
	
	public IWCSectionDAO getWcSectionDAO(){
		return wcSectionDAO;
	}



	public void setWcSectionDAO( IWCSectionDAO wcSectionDAO ){
		this.wcSectionDAO = wcSectionDAO;
	}



	/* (non-Javadoc)
	 * @see com.mindtree.ruc.cmn.reflect.IMethodInvocation#invokeMethod(java.lang.String, java.lang.Object[])
	 */
	@Override
	public Object invokeMethod(String methodName, Object... args) {
		BaseVO returnValue = null;
		if( "loadWCSectionSvc".equals( methodName ) ){
			returnValue = loadWCSectionSvc( (BaseVO) args[ 0 ] );
		}
		if( "saveWCSectionSvc".equals( methodName ) ){
 			returnValue = saveWCSectionSvc( (BaseVO) args[ 0 ] );
		}
		
		return returnValue;
	}
	
	
	
	private BaseVO saveWCSectionSvc(BaseVO baseVO) {
		
		return ( (BaseSectionSaveDAO) wcSectionDAO ).save(baseVO);
	}



	private BaseVO loadWCSectionSvc(BaseVO baseVO) {
		
		 return wcSectionDAO.loadWCSection(baseVO);
	}

}
