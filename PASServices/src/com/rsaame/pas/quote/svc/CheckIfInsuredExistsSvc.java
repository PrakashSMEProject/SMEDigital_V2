package com.rsaame.pas.quote.svc;

import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.rsaame.pas.quote.dao.ICheckIfInsuredExistsDAO;

public class CheckIfInsuredExistsSvc extends BaseService{
	
	private ICheckIfInsuredExistsDAO checkIfInsuredExistsDAO;
	
	
	@Override
	public Object invokeMethod(String methodName, Object... args) {
		BaseVO returnValue = null;
		if( "checkIfInsuredExists".equals( methodName ) ){
			returnValue = checkIfInsuredExists( (BaseVO) args[ 0 ] );
		}
		if("commonCheckIfInsuredExists".equals( methodName )){
			returnValue = commonCheckIfInsuredExists( (BaseVO) args[ 0 ] );
		}
		return returnValue;
	}
	
	private BaseVO commonCheckIfInsuredExists( BaseVO baseVO ){
		//BaseVO returnValue = checkIfInsuredExistsDAO.commonCheckIfInsuredExists(baseVO);
		checkIfInsuredExistsDAO.commonCheckIfInsuredExists(baseVO);
		return baseVO;
	}

	public BaseVO checkIfInsuredExists(BaseVO baseVO){
		
		//BaseVO returnValue = checkIfInsuredExistsDAO.checkIfInsuredExists(baseVO);
		checkIfInsuredExistsDAO.checkIfInsuredExists(baseVO);
		return baseVO;
	}
	
	public ICheckIfInsuredExistsDAO getCheckIfInsuredExistsDAO() {
		return checkIfInsuredExistsDAO;
	}

	public void setCheckIfInsuredExistsDAO(
			ICheckIfInsuredExistsDAO checkIfInsuredExistsDAO) {
		this.checkIfInsuredExistsDAO = checkIfInsuredExistsDAO;
	}
	
}
