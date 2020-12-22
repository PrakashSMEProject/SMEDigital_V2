/**
 * 
 */
package com.rsaame.pas.transaction.svc;

import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.rsaame.pas.transaction.dao.IViewTransactionDAO;

/**
 * @author M1016284
 *
 */
public class ViewTransactionSvc extends BaseService{

	IViewTransactionDAO viewTransDAO;

	@Override
	public Object invokeMethod( String methodName, Object... args ){
		BaseVO returnValue = null;
		if( "viewTransactionService".equals( methodName ) ){
			returnValue = viewTransactionService( (BaseVO) args[ 0 ] );
		}

		return returnValue;
	}

	private BaseVO viewTransactionService( BaseVO baseVO ){

		return viewTransDAO.viewTransactionDetails( baseVO );
	}

	public void setViewTransDAO(IViewTransactionDAO viewTransDAO) {
		this.viewTransDAO = viewTransDAO;
	}

}
