/**
 * 
 */
package com.rsaame.pas.renewals.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.vo.bus.PrintRenewalsSearchCriteriaVO;
import com.rsaame.pas.vo.bus.RenewalSearchSummaryVO;

/**
 * @author m1019193
 *This class is used to search policies for printing for which renewal quotes have been generated for Home/Travel-phase 3
 */
public class PrintRenewalSearchCommonRH implements IRequestHandler {

	private final static Logger LOGGER = Logger.getLogger(PrintRenewalSearchRH.class);
	private final static String EMPTY_STRING = "";
	
	@Override
	public Response execute(HttpServletRequest request, HttpServletResponse responseObj) {

		Response response = new Response();
		String identifier = null;

		// Identifier will be used further in TaskExecutor to identify
		// request processing
		identifier = request.getParameter("opType");
			
		if (LOGGER.isDebug()) {
			LOGGER.debug("opType-->" + identifier);
			LOGGER.debug("Inside printRenewalSearchRH");
			LOGGER.debug("transClazz: " + request.getParameter("transClazz"));
			LOGGER.debug("transPolicyNo: " + request.getParameter("transPolicyNo"));
			LOGGER.debug("transactionFrom: " + request.getParameter("transTransactionFrom"));
			LOGGER.debug("transactionTo: " + request.getParameter("transTransactionTo"));
			LOGGER.debug("transBrokerName: " + request.getParameter("transBrokerName"));
			LOGGER.debug("transInsuredName: " + request.getParameter("transInsuredName"));
			LOGGER.debug("transScheme: " + request.getParameter("transScheme"));
			LOGGER.debug("transAllDirect: " + request.getParameter("transAllDirect"));		
			LOGGER.debug("transBranch: " + request.getParameter("transBranch"));
			LOGGER.debug("transWithEmailID: " + request.getParameter("transWithEmailID"));
			LOGGER.debug("notYetPrinted: " + request.getParameter("notYetPrinted"));
			LOGGER.debug("transLOB: "+ request.getParameter("transLOB"));
		}
		
		/*
		 * As part of processing convert the HTTP request object obtained to
		 * required VO by using request to bean mapper available as part of
		 * framework
		 */

		PrintRenewalsSearchCriteriaVO renCriteriaVO = BeanMapper.map(request, PrintRenewalsSearchCriteriaVO.class);

		if(null != renCriteriaVO.getClazz()
				&& (EMPTY_STRING.equals(renCriteriaVO.getClazz()) || (com.Constant.CONST_SELECT).equalsIgnoreCase(renCriteriaVO.getClazz()))) {
			renCriteriaVO.setClazz(null);
		}

		if(null != renCriteriaVO.getPolicyNo() 
				&& (EMPTY_STRING.equals(renCriteriaVO.getPolicyNo()) || (com.Constant.CONST_SELECT).equalsIgnoreCase(renCriteriaVO.getPolicyNo()))) {
			renCriteriaVO.setPolicyNo(null);
		}

		if(null != renCriteriaVO.getInsuredName()
				&& (EMPTY_STRING.equals(renCriteriaVO.getInsuredName()) || (com.Constant.CONST_SELECT).equalsIgnoreCase(renCriteriaVO.getInsuredName()))) {
			renCriteriaVO.setInsuredName(null);
		}

		if(null != renCriteriaVO.getBrokerName()
				&& (EMPTY_STRING.equals(renCriteriaVO.getBrokerName()) || (com.Constant.CONST_SELECT).equalsIgnoreCase(renCriteriaVO.getBrokerName()))) {
			renCriteriaVO.setBrokerName(null);
		}
		
		if(null != renCriteriaVO.getScheme()
				&& (EMPTY_STRING.equals(renCriteriaVO.getScheme()) || (com.Constant.CONST_SELECT).equalsIgnoreCase(renCriteriaVO.getScheme()))) {
			renCriteriaVO.setScheme(null);
		}
		
		if(null != renCriteriaVO.getBranch()
				&& (EMPTY_STRING.equals(renCriteriaVO.getBranch()) || (com.Constant.CONST_SELECT).equalsIgnoreCase(renCriteriaVO.getBranch()))) {
			renCriteriaVO.setBranch(null);
		}


		UserProfile userProfile = (UserProfile)request.getSession().getAttribute(AppConstants.SESSION_USER_PROFILE_VO);
		if(!Utils.isEmpty(userProfile)){
			renCriteriaVO.setLoggedInUser(userProfile);	
		}
		
		BaseVO baseVO = TaskExecutor.executeTasks(identifier, renCriteriaVO);
		LOGGER.debug("*****Executed taskExecutor*****");

		RenewalSearchSummaryVO  summaryVO = (RenewalSearchSummaryVO) baseVO;

		if (Utils.isEmpty(summaryVO) || Utils.isEmpty(summaryVO.getRenPolList()))
			throw new BusinessException("pas.renewal.noRecordsFound", null, "No records found for given search criteria");
		
		if(!Utils.isEmpty(summaryVO)) {
			response.setSuccess(true);
			response.setData(summaryVO);
		}

		return response;

	}

}

