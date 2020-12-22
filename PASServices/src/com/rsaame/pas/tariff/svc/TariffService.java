/**
 * 
 */
package com.rsaame.pas.tariff.svc;

import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.exception.SystemException;
import com.rsaame.pas.lookup.dao.ILookUpDAO;
import com.rsaame.pas.lookup.dao.LookUpDAO;
import com.rsaame.pas.sample.dao.ITestDAO;
import com.rsaame.pas.tariff.dao.ITariffDAO;
import com.rsaame.pas.vo.app.LookUpListVO;
import com.rsaame.pas.vo.app.LookUpVO;

/**
 * @author m1014594
 *
 */
public class TariffService extends BaseService{

	ITariffDAO tariffDAO = null;

	public Object invokeMethod( String methodName, Object... args ){
		Object returnValue = null;
		if( methodName.equals( "getTarLocation" ) ){
			returnValue = getTarLocation( (String) args[ 0 ] );
		}

		return returnValue;
	}

	

	private Object getTarLocation( String tarCode ){
				
		return tariffDAO.getTarLocation( tarCode );
	}



	public ITariffDAO getTariffDAO(){
		return tariffDAO;
	}



	public void setTariffDAO( ITariffDAO tariffDAO ){
		this.tariffDAO = tariffDAO;
	}



	



}
