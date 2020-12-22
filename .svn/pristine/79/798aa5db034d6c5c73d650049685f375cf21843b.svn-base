/**
 * 
 */
package com.rsaame.pas.reports.svc;

import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.rsaame.pas.reports.dao.IReportsDAO;

/**
 * @author m1019193
 *
 */
public class SmsSvc extends BaseService{
	
	IReportsDAO reportsDAO;

	

	@Override
	public Object invokeMethod(String methodName, Object... args) {

		BaseVO returnValue = null;
		
		if( "getsmsList".equals( methodName ) ){
			returnValue = getSmsListSvc( (BaseVO) args[ 0 ] );
		}
		if( "saveNewSms".equals( methodName ) ){
			returnValue = saveNewSmsSvc( (BaseVO) args[ 0 ] );
		}
		if("deleteSms".equals(methodName)){
			returnValue = deleteSmsSvc( (BaseVO) args[ 0 ] );
		}		
		
		return returnValue;
	}	
	
	private BaseVO getSmsListSvc(BaseVO baseVO) {
		
		return reportsDAO.getViewSmsList(baseVO);
	}
	
	private BaseVO saveNewSmsSvc(BaseVO baseVO) {
		
		return reportsDAO.saveNewSms(baseVO);
	}
	
	private BaseVO deleteSmsSvc(BaseVO baseVO) {
		return reportsDAO.deleteSms(baseVO);
	}
	
	/**
	 * @param reportsDAO the reportsDAO to set
	 */
	public void setReportsDAO(IReportsDAO reportsDAO) {
		this.reportsDAO = reportsDAO;
	}

}
