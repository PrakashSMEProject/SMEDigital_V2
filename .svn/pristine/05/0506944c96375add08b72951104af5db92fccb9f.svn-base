/**
 * 
 */
package com.rsaame.pas.dao.cmn;

import org.apache.log4j.Logger;

import com.mindtree.ruc.cmn.base.BaseDBDAO;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.rsaame.pas.dao.model.TTrnPaymentDtl;
import com.rsaame.pas.dao.model.TTrnPaymentRequest;
import com.rsaame.pas.vo.bus.PaymentDetailsVO;
import com.rsaame.pas.vo.bus.PolicyDataVO;

/**
 * @author Sarath
 * @since Phase 3
 */
public class OnlinePaymentDetailsSaveDAO extends BaseDBDAO implements IOnlinePaymentDetailsSaveDAO{
	
	private final static Logger LOGGER = Logger.getLogger(OnlinePaymentDetailsSaveDAO.class);
	
	@Override
	public BaseVO savePaymentDetails( BaseVO baseVO ){

		try{
			TTrnPaymentDtl ttrnPaymentDtls = BeanMapper.map( (PaymentDetailsVO) baseVO, TTrnPaymentDtl.class );
			getHibernateTemplate().save( ttrnPaymentDtls );
		}
		catch( BusinessException be ){
			be.printStackTrace();
			throw new BusinessException( be.getErrorKeysList().get( 0 ), be.getCause(), be.getMessage() );
		}
		catch( Exception exp ){
			exp.printStackTrace();
		}
		return baseVO;
	}

	
	@Override
	public BaseVO savePaymentRequestDetails( BaseVO baseVO ){
		LOGGER.info("Entered into OnlinePaymentDetailsSaveDAO.savePaymentRequestDetails method.");
		try{
			
			TTrnPaymentRequest tTrnPaymentRequest = BeanMapper.map( (PaymentDetailsVO) baseVO, TTrnPaymentRequest.class );
			getHibernateTemplate().save( tTrnPaymentRequest );
			System.out.println("************* Im here *************");
			LOGGER.info("Saving tTrnPaymentRequest - completes.");
		}
		catch( BusinessException be ){
			be.printStackTrace();
			throw new BusinessException( be.getErrorKeysList().get( 0 ), be.getCause(), be.getMessage() );
		}
		catch( Exception exp ){
			exp.printStackTrace();
		}
		return baseVO;
	}
}
