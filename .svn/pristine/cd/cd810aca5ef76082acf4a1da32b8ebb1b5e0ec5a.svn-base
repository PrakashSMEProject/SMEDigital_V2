/**
 * 
 */
package com.rsaame.pas.bi.svc;

import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.rsaame.pas.bi.dao.IBISectionDAO;
import com.rsaame.pas.bi.dao.BISaveDAO;

/**
 * @author m1019703
 * This class is service class for BI.
 */
public class BISectionSvc extends BaseService {

	
	public BISectionSvc() {
		// TODO Auto-generated constructor stub
	}

	IBISectionDAO biSaveDAO,biLoadDAO;
	


	public IBISectionDAO getBiSaveDAO() {
		return biSaveDAO;
	}


	public void setBiSaveDAO(IBISectionDAO biSaveDAO) {
		this.biSaveDAO = biSaveDAO;
	}


	/* (non-Javadoc)
	 * @see com.mindtree.ruc.cmn.reflect.IMethodInvocation#invokeMethod(java.lang.String, java.lang.Object[])
	 */
	@Override
	public Object invokeMethod(String methodName, Object... args) {
		// TODO Auto-generated method stub
		BaseVO returnValue = null;
		if( "saveBISectionSvc".equals( methodName ) ){
			returnValue = saveBISectionSvc( (BaseVO) args[ 0 ] );
		}
		if( "loadBISectionSvc".equals( methodName ) ){
			returnValue = loadBISectionSvc( (BaseVO) args[ 0 ] );
		}
		
		return returnValue;
	}

	
	private BaseVO saveBISectionSvc(BaseVO baseVO) {
		return ((BISaveDAO)biSaveDAO).save(baseVO);
	}

	private BaseVO loadBISectionSvc(BaseVO baseVO) {
		return (biLoadDAO).loadBISection(baseVO);
	}


	public IBISectionDAO getBiLoadDAO(){
		return biLoadDAO;
	}


	public void setBiLoadDAO( IBISectionDAO biLoadDAO ){
		this.biLoadDAO = biLoadDAO;
	}
	
}
