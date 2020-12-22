package com.rsaame.pas.reports.svc;

import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.rsaame.pas.reports.dao.IReportsDAO;

public class ReportsSearchSvc extends BaseService {
	IReportsDAO reportsDAO;
	@Override
	public Object invokeMethod(String methodName, Object... args) {
		BaseVO returnValue = null;
		if( "reportsBrAcctService".equals( methodName ) ){
			returnValue = reportsBrAcctService( (BaseVO) args[ 0 ] );
		}
		if("reportsLivePolService".equals( methodName)){
			returnValue = reportsLivePolService((BaseVO) args[ 0 ]);
		}
		if("reportsClassPrmService".equals( methodName)){
			returnValue = reportsClassPrmService((BaseVO) args[ 0 ]);
		}
		if("reportsPaymentService".equals(methodName)){
			returnValue = reportsPaymentService((BaseVO) args[ 0 ]);
		}
		if("reportsRenewalPayService".equalsIgnoreCase(methodName)){
			returnValue = reportsRenewalPayService((BaseVO) args[ 0 ]);
		}
		if("transReportsSearch".equals( methodName)){
			returnValue = transReportsSearch((BaseVO) args[ 0 ]);
		}
		if("transReportsDetailSearch".equals( methodName)){
			returnValue = transReportsDetailSearch((BaseVO) args[ 0 ]);
		}
		if("reportsPromotionalCodeService".equals(methodName)){
			returnValue = reportsPromotionalCodeService((BaseVO) args[ 0 ]);
		}
		/*
		 * 81120 New Quote report
		 * as part of 3.2 release
		 */
		if("reportsQuoteService".equals(methodName)){
			returnValue = reportsQuoteService((BaseVO) args[ 0 ]);
		}
		return returnValue;
	}
	
	/*
	 * 81120 New Quote report
	 * as part of 3.2 release
	 */
	private BaseVO reportsQuoteService(BaseVO baseVO) {
		
		 return reportsDAO.quoteSearch(baseVO);
	}

	private BaseVO reportsPromotionalCodeService(BaseVO baseVO) {
		return reportsDAO.promoCodeReportSearch(baseVO);
	}

	private BaseVO reportsRenewalPayService(BaseVO baseVO) {
		return reportsDAO.renewalPaymentSearch(baseVO);
	}

	private BaseVO reportsPaymentService(BaseVO baseVO) {
		return reportsDAO.paymentSearch(baseVO);
	}

	private BaseVO reportsBrAcctService(BaseVO baseVO) {
		
		return reportsDAO.acctSearch(baseVO);
	}
	
	private BaseVO reportsLivePolService(BaseVO baseVO){
		return reportsDAO.livePolSearch(baseVO);
	}
	
	private BaseVO reportsClassPrmService(BaseVO baseVO){
		return reportsDAO.classPrmSearch(baseVO);
	}

	private BaseVO transReportsSearch(BaseVO baseVO){
		return reportsDAO.transReportsSearch(baseVO);
	}
	
	private BaseVO transReportsDetailSearch(BaseVO baseVO){
		return reportsDAO.transReportsDetailSearch(baseVO);
	}
	/**
	 * @param reportsDAO the reportsDAO to set
	 */
	public void setReportsDAO(IReportsDAO reportsDAO) {
		this.reportsDAO = reportsDAO;
	}
	
}
