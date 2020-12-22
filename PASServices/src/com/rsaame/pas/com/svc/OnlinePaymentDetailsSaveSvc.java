/**
 * 
 */
package com.rsaame.pas.com.svc;

import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.rsaame.pas.dao.cmn.IOnlinePaymentDetailsSaveDAO;
import org.apache.log4j.Logger;


/**
 * @author Sarath
 *
 */
public class OnlinePaymentDetailsSaveSvc extends BaseService{
	
	private final static Logger LOGGER = Logger.getLogger(OnlinePaymentDetailsSaveSvc.class);
	
	IOnlinePaymentDetailsSaveDAO onlinePaymentDetailsSaveDao;

	@Override
	public Object invokeMethod( String methodName, Object... args ){
		
		if( methodName.equals( "saveOnlinePaymentDetails" ) ){
			return saveOnlinePaymentDetails((BaseVO)args[0]);
		}
		
		if( methodName.equals( "saveOnlineRequestPaymentDetails" ) ){
			return saveOnlineRequestPaymentDetails((BaseVO)args[0]);
		}
		return null;
	}
	
	private BaseVO saveOnlinePaymentDetails(BaseVO paymentDetailsVO){
		LOGGER.info( "Entered OnlinePaymentDetailsSaveSvc:saveOnlinePaymentDetails method." );
		onlinePaymentDetailsSaveDao.savePaymentDetails( paymentDetailsVO );
		
		return paymentDetailsVO;
		
	}
	/**
	 * Request 131378 To insert request before payment gateway
	 */
	
	private BaseVO saveOnlineRequestPaymentDetails(BaseVO paymentDetailsvo){
		
		LOGGER.info( "Entered OnlinePaymentDetailsSaveSvc:saveOnlineRequestPaymentDetails method." );
		onlinePaymentDetailsSaveDao.savePaymentRequestDetails( paymentDetailsvo );
		
		return paymentDetailsvo;
		
	}
	/**
	 * @return the onlinePaymentDetailsSaveDao
	 */
	public IOnlinePaymentDetailsSaveDAO getOnlinePaymentDetailsSaveDao(){
		return onlinePaymentDetailsSaveDao;
	}

	/**
	 * @param onlinePaymentDetailsSaveDao the onlinePaymentDetailsSaveDao to set
	 */
	public void setOnlinePaymentDetailsSaveDao( IOnlinePaymentDetailsSaveDAO onlinePaymentDetailsSaveDao ){
		this.onlinePaymentDetailsSaveDao = onlinePaymentDetailsSaveDao;
	}
	
	

}
