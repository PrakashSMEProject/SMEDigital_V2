package com.rsaame.pas.sms.svc;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.base.BaseService;
import com.rsaame.pas.sms.dao.ISmsDao;

/**
 * @author M1020278
 * 
 */
public class SendSmsSvc extends BaseService {

	ISmsDao smsDAO;
	//Injection thru setter
	public void setSmsDAO(ISmsDao smsDAO) {
		this.smsDAO = smsDAO;
	}
	
	@Override
	public Object invokeMethod( String methodName, Object... args ){
		BaseVO returnValue = null;
		if( "viewTransactionSearchSms".equals( methodName ) ){
			returnValue = viewTransactionSearchSms( (BaseVO) args[ 0 ] );
		}
		else if( "sendSms".equals( methodName ) ){
			returnValue = sendSms( (BaseVO) args[ 0 ] );
		}

		return returnValue;
	}

	private BaseVO viewTransactionSearchSms(BaseVO baseVO) {
		return smsDAO.searchTransactionSms(baseVO);
	}
	
	private BaseVO sendSms(BaseVO baseVO) {
		return smsDAO.sendSms(baseVO);
	}

}
