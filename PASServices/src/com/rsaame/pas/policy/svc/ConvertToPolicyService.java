package com.rsaame.pas.policy.svc;

import java.util.List;

import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.paymentoption.dao.IPaymentOptionDAO;
import com.rsaame.pas.policy.dao.IConvertToPolicyDAO;
import com.rsaame.pas.vo.bus.LOB;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.cmn.CommonVO;
import org.apache.log4j.Logger;



public class ConvertToPolicyService extends BaseService{
	
	private final static Logger LOGGER = Logger.getLogger(ConvertToPolicyService.class);
	
	IConvertToPolicyDAO convPolDao;
	
	public Object invokeMethod(String methodName, Object... args) {
		Object returnValue = null;
		if(methodName.equals("getPolicyNumber")){
			returnValue = getPolicyNumber((BaseVO)args[ 0 ]);
		} else if(methodName.equals("isRenewalQuote")){
			returnValue = isRenewalQuote((BaseVO)args[ 0 ]);
		}else if(methodName.equals("isRenewalQuoteForHomeAndTravel")){
			returnValue = isRenewalQuoteForHomeAndTravel((BaseVO)args[ 0 ]);
		}
		return returnValue;
	}

	private BaseVO getPolicyNumber( BaseVO baseVO ){
		LOGGER.info("Entered ConvertToPolicyService:getPolicyNumber method");
		
		DataHolderVO<List> dataHolderVO = (DataHolderVO<List>) baseVO;

		List inputData = dataHolderVO.getData();
		/*Get the PolicyVO and PaymentVO from input VO*/
		PolicyVO policyVO = (PolicyVO) inputData.get( 0 );
		CommonVO commonVO = (CommonVO) inputData.get( 2 );
		
		if( !Utils.isEmpty( commonVO.getLob() ) && !( commonVO.getLob().equals( LOB.SBS )) ){
			//Radar fix
			convPolDao.getPolicyNumberForMonoline( commonVO );
		}
		else{
			//Radar fix
			convPolDao.getPolicyNumber( policyVO );
		}
		
		IPaymentOptionDAO paymentOptionDAO = (IPaymentOptionDAO) Utils.getBean( "paymentOptionDAO_POL" );
		LOGGER.info("Calling PaymentOptionDAO.savePaymentDetails method.");
		return paymentOptionDAO.savePaymentDetails( dataHolderVO );

	}
	private BaseVO isRenewalQuote(BaseVO baseVO){
		LOGGER.info("Entered ConvertToPolicyService:isRenewalQuote method");
		return convPolDao.isRenewalQuote(baseVO);
	}

	/**
	 * @param convPolDao the convPolDao to set
	 */
	public void setConvPolDao(IConvertToPolicyDAO convPolDao) {
		this.convPolDao = convPolDao;
	}

	private BaseVO isRenewalQuoteForHomeAndTravel(BaseVO baseVO){
		return convPolDao.isRenewalQuoteForHomeAndTravel(baseVO);
	}
	
}
