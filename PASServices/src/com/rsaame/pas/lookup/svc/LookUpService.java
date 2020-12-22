/**
 * 
 */
package com.rsaame.pas.lookup.svc;

import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.exception.SystemException;
import com.rsaame.pas.lookup.dao.ILookUpDAO;
import com.rsaame.pas.lookup.dao.LookUpDAO;
import com.rsaame.pas.sample.dao.ITestDAO;
import com.rsaame.pas.vo.app.LookUpListVO;
import com.rsaame.pas.vo.app.LookUpVO;

/**
 * @author m1014594
 *
 */
public class LookUpService extends BaseService{

	ILookUpDAO lookUpDAO = null;

	public Object invokeMethod( String methodName, Object... args ){
		Object returnValue = null;
		if( methodName.equals( "getListOfDescription" ) ){
			returnValue = getListOfDescription( (BaseVO) args[ 0 ] );
		}
		if( methodName.equals( "getDescription" ) ){
			returnValue = getDescription( (BaseVO) args[ 0 ] );
		}
		if( methodName.equals( "getCode" ) ){
			returnValue = getCode( (BaseVO) args[ 0 ] );
		}		
		if( methodName.equals( "refreshCache" ) ){
			refreshCache( (BaseVO) args[ 0 ] );
		}
		return returnValue;
	}

	/**
	 * @param baseVO
	 * @return
	 * @throws SystemException
	 */
	public BaseVO getListOfDescription( BaseVO baseVO ) throws SystemException{
		//lookUpDAO=new LookUpDAO();
		LookUpListVO lookUpL = (LookUpListVO) lookUpDAO.getListOfDescription( baseVO );
		return lookUpL;
	}

	/**
	 * @param baseVO
	 * @return
	 * @throws SystemException
	 */
	public BaseVO getDescription( BaseVO baseVO ) throws SystemException{
		//lookUpDAO = new LookUpDAO();
		LookUpVO lookUpL = (LookUpVO) lookUpDAO.getDescription( baseVO );
		return lookUpL;

	}
	
	/**
	 * @param baseVO
	 * @return
	 * @throws SystemException
	 */
	public BaseVO getCode( BaseVO baseVO ) throws SystemException{
		//lookUpDAO = new LookUpDAO();
		LookUpVO lookUpL = (LookUpVO) lookUpDAO.getCode( baseVO );
		return lookUpL;

	}

	/**
	 * @return the lookUpDAO
	 */
	public ILookUpDAO getLookUpDAO(){
		return lookUpDAO;
	}

	/**
	 * @param lookUpDAO the lookUpDAO to set
	 */
	public void setLookUpDAO( ILookUpDAO lookUpDAO ){
		this.lookUpDAO = lookUpDAO;
	}

	public void refreshCache( BaseVO baseVO ) throws SystemException{
		//lookUpDAO=new LookUpDAO();
		lookUpDAO.refreshCache( baseVO );
	}
}
