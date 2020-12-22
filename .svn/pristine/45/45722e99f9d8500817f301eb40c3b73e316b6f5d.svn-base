package com.rsaame.pas.quote.ui;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Redirection;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.kaizen.framework.model.ServiceContext;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.vo.app.CopyQuoteVO;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.bus.GeneralInfoVO;
import com.rsaame.pas.vo.bus.LOB;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.SchemeVO;
import com.rsaame.pas.vo.bus.SourceOfBusinessVO;

public class CopyQuoteRH implements IRequestHandler{

	private static final Logger logger = Logger.getLogger( CopyQuoteRH.class );
	private static String COPY_QUOTE_RH = "CopyQuoteRH";
	private static String COPY_QUOTE_OP_TYPE = "COPY_QUOTE_INVSVC";
	private static String COPY_TO_SAME_INSURED = "COPY_TO_SAME_INSURED";
	private static String COPY_TO_EXISTING_INSURED = "COPY_TO_EXISTING_INSURED";
	private static String COPY_TO_NEW_INSURED = "COPY_TO_NEW_INSURED";

	@Override
	public Response execute( HttpServletRequest request, HttpServletResponse responseObj ){
		Long polLinkingId = null;
		Long insuredId = null;
		PolicyVO policyVO = null;
		PolicyContext polContext = null;
		Integer userId = null;
		Integer policyTypeCode = null;
		String location = null;
		Integer locationCode = null;
		SchemeVO scheme = null;
		SourceOfBusinessVO sourceOfBus = null;
		GeneralInfoVO generalInfo = null;
		Long deletePolLinkingId = null;
		String newInsuredFlg = null;
		String searchedInsuredId = null;
		String existingInsuredFlg = null;

		/* Identifier will be used further in TaskExecutor to identify request processing */
		//String identifier = request.getParameter( "opType" );
		String action = request.getParameter( "action" );
		String oldPolLinkingId = request.getParameter( "oldPolLinkingId" );
		newInsuredFlg = request.getParameter( "newInsuredFlag" );
		searchedInsuredId = request.getParameter( "searchedInsuredId" );
		existingInsuredFlg = request.getParameter( "existingInsuredFlag" );
		Object existingPolLinkingIdObj = request.getSession().getAttribute( "copyExistingPolLinkingId" );

		/* To check if the request is directly coming in from UI if so use action from request as
		 * parameter else use it from request attribute
		 */
		if( Utils.isEmpty( action ) ){
			action = (String) request.getAttribute( "action" );
		}

		location = ServiceContext.getLocation();
		if( !Utils.isEmpty( location, true ) ) locationCode = Integer.valueOf( location );

		polContext = PolicyContextUtil.getPolicyContext( request );
		logger.debug( COPY_QUOTE_RH, "polContext obtained" + polContext );

		policyVO = polContext.getPolicyDetails();

		/** Fetching details from PolicyVO */
		if( !Utils.isEmpty( policyVO ) ){
			polLinkingId = policyVO.getPolLinkingId();
			userId = getUserId( policyVO );
			generalInfo = policyVO.getGeneralInfo();
			scheme = policyVO.getScheme();

			if( !Utils.isEmpty( scheme ) ) policyTypeCode = scheme.getPolicyCode();
			if( !Utils.isEmpty( generalInfo ) ) sourceOfBus = generalInfo.getSourceOfBus();
		}

		/** If a new Insured has been chosen, setting the policyLinkingId to be set in CopyQuoteVo to the original PolicyLinkingId for replication */
		if( !Utils.isEmpty( newInsuredFlg, true ) && "Y".equals( newInsuredFlg ) ) deletePolLinkingId = polLinkingId;
		if( !Utils.isEmpty( oldPolLinkingId, true ) ) polLinkingId = new Long( oldPolLinkingId );

		if( COPY_TO_SAME_INSURED.equalsIgnoreCase( action ) ){
			logger.info( "***** Inside COPY_TO_SAME_INSURED section *****" );

			if( !Utils.isEmpty( generalInfo ) && !Utils.isEmpty( generalInfo.getInsured() ) ) insuredId = generalInfo.getInsured().getInsuredId();

			if( "Y".equalsIgnoreCase( existingInsuredFlg ) ){
				if( !Utils.isEmpty( searchedInsuredId ) ) insuredId = Long.valueOf( searchedInsuredId );
				if( !Utils.isEmpty( existingPolLinkingIdObj ) ) polLinkingId = (Long) existingPolLinkingIdObj;
			}
		}
		else if( COPY_TO_EXISTING_INSURED.equalsIgnoreCase( action ) ){
			
			logger.info( "***** Inside COPY_TO_EXISTING_INSURED section *****" );
			Response response = new Response();
		
			request.setAttribute( com.Constant.CONST_OPERATION, COPY_TO_EXISTING_INSURED );
			request.setAttribute( "curLob",LOB.SBS.toString() );

			request.getSession().setAttribute( "copyExistingFlow", COPY_TO_EXISTING_INSURED );
			request.getSession().setAttribute( "copyExistingPolLinkingId", polLinkingId );

			/* Response object which will be used by framework after request processing is completed */

			Redirection redirection = new Redirection( "SEARCH_INSURED_SCREEN", Redirection.Type.TO_NEW_OPERATION );
			response.setRedirection( redirection );

			return response;
		}
		else if( COPY_TO_NEW_INSURED.equalsIgnoreCase( action ) ){
			
			logger.info( "***** Inside COPY_TO_NEW_INSURED section *****" );
			request.setAttribute( com.Constant.CONST_OPERATION, COPY_TO_NEW_INSURED );
			request.setAttribute( "scheme", scheme );
			request.setAttribute( "sourceOfBus", sourceOfBus );
			request.setAttribute( "oldPolLinkingId", polLinkingId.toString() );
			request.setAttribute( "newInsuredFlag", "Y" );
			request.setAttribute( "appFlow", "CREATE_QUO" );
			/*VAT*/
			request.setAttribute("VATCodeForCopyNewCustomer", policyVO.getPolVATCode());
			request.getSession().setAttribute( com.Constant.CONST_OPERATION, COPY_TO_NEW_INSURED );
			
			
			
			
			// Added condition for JLT broker

			if (SvcConstants.DUBAI == Integer.parseInt(Utils.getSingleValueAppConfig(SvcConstants.DEPLOYED_LOCATION))
					&& policyVO.getIsQuote()) {
				String schemeCode = "";
				Date preparedDate = new Date();
				if (!Utils.isEmpty(policyVO.getScheme())) {
					schemeCode = policyVO.getScheme().getSchemeCode().toString();
				}
				if (!Utils.isEmpty(policyVO.getCreated())) {
					preparedDate = policyVO.getCreated();
				}
				SimpleDateFormat s2 = new SimpleDateFormat(com.Constant.CONST_DD_MM_YYYY);
				String d2 = Utils.getSingleValueAppConfig("JLT_LiveDate");
				Date JLTLiveDate = null;
				try {
					JLTLiveDate = s2.parse(d2);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				Date date = new Date();
				SimpleDateFormat s3 = new SimpleDateFormat(com.Constant.CONST_DD_MM_YYYY);
				Date modifiedDate = new Date();

			    String strDateFormat = com.Constant.CONST_DD_MM_YYYY;
			    DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
			     try {
					modifiedDate= s3.parse(dateFormat.format(date));
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}  
				
				
				if (Utils.getSingleValueAppConfig("JLT_SchemeCode").equals(schemeCode)
						&& (JLTLiveDate.compareTo(preparedDate) <= 0 || JLTLiveDate.compareTo(modifiedDate) <= 0)) {
					request.setAttribute("JLTCopyQuote", 1);
				}
			}

		}

		/** Creating the copyQuoteVO object */
		CopyQuoteVO copyQuoteVO = new CopyQuoteVO();
		copyQuoteVO.setPolLinkingId( polLinkingId );
		copyQuoteVO.setInsuredId( insuredId );
		copyQuoteVO.setUserId( userId );
		copyQuoteVO.setPolicyCode( policyTypeCode );
		copyQuoteVO.setLocationCode( locationCode );
		copyQuoteVO.setDeletePolLinkingId( deletePolLinkingId );
		
		if( !Utils.isEmpty( request.getSession().getAttribute( com.Constant.CONST_OPERATION ) ) && COPY_TO_NEW_INSURED.equals( request.getSession().getAttribute( com.Constant.CONST_OPERATION ) )){
			copyQuoteVO.setIsNewCustomer( true );
		}else{
			copyQuoteVO.setIsNewCustomer( false );
		}

		/** Calling the Task executor for copy quote only for the case of same insured */
		if(!Utils.isEmpty(copyQuoteVO.getInsuredId())) {
			copyQuoteVO = (CopyQuoteVO) TaskExecutor.executeTasks(
					COPY_QUOTE_OP_TYPE, copyQuoteVO);

			if (!Utils.isEmpty(copyQuoteVO)
					&& !Utils.isEmpty(copyQuoteVO.getNewPolLinkingId()))
				request.setAttribute("polLinkingId", copyQuoteVO
						.getNewPolLinkingId().toString());

			if (!Utils.isEmpty(copyQuoteVO)
					&& !Utils.isEmpty(copyQuoteVO.getNewQuoteNo()))
				responseObj.setHeader("newQuoteNumber", copyQuoteVO
						.getNewQuoteNo().toString());
			request.setAttribute("appFlow", Flow.EDIT_QUO.toString());

			if (!Utils.isEmpty(request.getSession().getAttribute(
					com.Constant.CONST_OPERATION))  && COPY_TO_NEW_INSURED.equals( request.getSession().getAttribute( com.Constant.CONST_OPERATION ) )){
				request.getSession().removeAttribute(com.Constant.CONST_OPERATION);
			}
		}

		/*
		 * Response object which will be used by framework after request
		 * processing is completed
		 */
		Response response = new Response();

		return response;
	}

	/**
	 * 
	 * @param baseVO
	 * @return
	 */
	private Integer getUserId(BaseVO baseVO) {
		Integer userId = 0;

		if (!Utils.isEmpty(baseVO)
				&& !Utils.isEmpty(baseVO.getLoggedInUser())
				&& !Utils.isEmpty(((UserProfile) baseVO.getLoggedInUser())
						.getRsaUser())
				&& !Utils.isEmpty(((UserProfile) baseVO.getLoggedInUser())
						.getRsaUser().getUserId())) {
			userId = ((UserProfile) baseVO.getLoggedInUser()).getRsaUser()
					.getUserId();
		}
		return userId;
	}

}
