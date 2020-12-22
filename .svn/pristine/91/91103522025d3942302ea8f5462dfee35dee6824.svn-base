/**
 * 
 */
package com.rsaame.pas.paymentoption.svc;

import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.rsaame.pas.paymentoption.dao.IPaymentOptionDAO;

/**
 * @author m1016303
 *
 */
public class PaymentOptionSvc extends BaseService {

	IPaymentOptionDAO paymentOptionDAO;
	@Override
	public Object invokeMethod(String methodName, Object... args) {
		
		BaseVO returnValue = null;
		if( "savePaymentDetails".equals( methodName ) ){
			returnValue = savePaymentDetails( (BaseVO) args[ 0 ] );
		}
		return returnValue;
	}
	
	public BaseVO savePaymentDetails(BaseVO baseVO) 
	{
		return paymentOptionDAO.savePaymentDetails(baseVO);
	}

	 

	/**
	 * @param paymentOptionDAO the paymentOptionDAO to set
	 */
	public void setPaymentOptionDAO( IPaymentOptionDAO paymentOptionDAO ){
		this.paymentOptionDAO = paymentOptionDAO;
	}

}
