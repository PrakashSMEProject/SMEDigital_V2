package com.rsaame.pas.quote.ui;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Redirection;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.kaizen.framework.model.ServiceContext;
import com.rsaame.pas.com.svc.BaseLoadSvc;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.vo.app.CopyQuoteHelperVO;
import com.rsaame.pas.vo.app.CopyQuoteVO;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.bus.LOB;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.SchemeVO;
import com.rsaame.pas.vo.bus.SourceOfBusinessVO;
import com.rsaame.pas.vo.cmn.CommonVO;
import com.rsaame.pas.vo.cmn.LoadDataInputVO;
import com.rsaame.pas.vo.cmn.TableData;

/**
 * @author M1006438
 * 
 * This class is used for copy quote functionality for Home/Travel
 *
 */

public class CopyQuoteCommomRH implements IRequestHandler{

	private static final Logger logger = Logger.getLogger( CopyQuoteRH.class );
	private static String COPY_TO_SAME_INSURED = "COPY_TO_SAME_INSURED";
	private static String COPY_TO_EXISTING_INSURED = "COPY_TO_EXISTING_INSURED";
	private static String COPY_TO_NEW_INSURED = "COPY_TO_NEW_INSURED";
	private static String COPY_QUOTE_OP_TYPE = "COPY_QUOTE_COMMON_INVSVC";

	@Override
	public Response execute( HttpServletRequest request, HttpServletResponse responseObj ){
		HttpSession session = request.getSession();
		Redirection redirection = null;
		PolicyContext policyContext = PolicyContextUtil.getPolicyContext( request );
		CommonVO commonVO = policyContext.getCommonDetails();
		Long polPolicyId = null;
		Long insuredId = null;
		boolean isNewCust;
		Integer userId = null;
		String location = null;
		Integer locationCode = null;
		String searchedInsuredId = null;
		String existingInsuredFlg = null;
		Long deletePolicyId = null;
		/* Identifier will be used further in TaskExecutor to identify request processing */

		String action = request.getParameter( "action" );
		String oldPolPoliocyId = request.getParameter( "oldPolPoliocyId" );
		searchedInsuredId = request.getParameter( "searchedInsuredId" );
		existingInsuredFlg = request.getParameter( "existingInsuredFlag" );
		Object existingPolIdObj = session.getAttribute( "copyExistingPolId" );
		String newInsuredFlg = request.getParameter( "newInsuredFlag" );
		String policyType = request.getParameter( "cpQuotePolicyType");//policyType" );

		// Insured search flow loads the general Info from SBS RH. Hence commonVO will not be available via policy context
		//Hence take it from session
		if( "Y".equalsIgnoreCase( existingInsuredFlg ) ){
			commonVO = (CommonVO) session.getAttribute( com.Constant.CONST_COPYEXISTINGCOMMONVO );
			session.removeAttribute( com.Constant.CONST_COPYEXISTINGCOMMONVO );
		}

		/* To check if the request is directly coming in from UI if so use action from request as
		 * parameter else use it from request attribute
		 */
		if( Utils.isEmpty( action ) ){
			action = (String) request.getAttribute( "action" );
		}

		locationCode =  Integer.valueOf(ServiceContext.getLocation());
		if( !Utils.isEmpty( location, true ) ) locationCode = Integer.valueOf( location );

		PolicyDataVO polDataVO = null;

		/** If a new Insured has been chosen, setting the policyLinkingId to be set in CopyQuoteVo to the original PolicyLinkingId for replication */

		//if( !Utils.isEmpty( newInsuredId, true ) ) insuredId = new Long( newInsuredId );

		if( COPY_TO_SAME_INSURED.equalsIgnoreCase( action ) ){
			logger.info( "***** Inside COPY_TO_SAME_INSURED section *****" );
			
			if(!Utils.isEmpty(policyType)){
				insuredId = Utils.isEmpty(request.getParameter("insuredCode"))?null:Long.valueOf(request.getParameter("insuredCode"));
			}else{
				polDataVO = loadData( commonVO );
				polPolicyId = polDataVO.getPolicyId();				
			}
			
			userId = AppUtils.getUserDetailsFromSession(request).getRsaUser().getUserId();
			if( !Utils.isEmpty( newInsuredFlg, true ) && "Y".equals( newInsuredFlg ) ) deletePolicyId = polPolicyId;
			if( !Utils.isEmpty( oldPolPoliocyId, true ) ) polPolicyId = new Long( oldPolPoliocyId );
			
			if(!Utils.isEmpty(polDataVO)&& !Utils.isEmpty( polDataVO.getGeneralInfo() ) && !Utils.isEmpty( polDataVO.getGeneralInfo().getInsured() ) )
				insuredId = polDataVO.getGeneralInfo().getInsured().getInsuredCode();

			if( "Y".equalsIgnoreCase( existingInsuredFlg ) ){
				if( !Utils.isEmpty( searchedInsuredId ) ) insuredId = Long.valueOf( searchedInsuredId );
				if( !Utils.isEmpty( existingPolIdObj ) ) polPolicyId = commonVO.getPolicyId();//Long) existingPolIdObj;
			}
		}
		else if( COPY_TO_EXISTING_INSURED.equalsIgnoreCase( action ) ){

			logger.info( "***** Inside COPY_TO_EXISTING_INSURED section *****" );
			Response response = new Response();

			request.setAttribute( com.Constant.CONST_OPERATION, COPY_TO_EXISTING_INSURED );
			request.setAttribute( "curLob",commonVO.getLob().toString() );
			request.getSession().setAttribute( "copyExistingFlow", COPY_TO_EXISTING_INSURED );
			request.getSession().setAttribute( com.Constant.CONST_COPYEXISTINGCOMMONVO, commonVO );
			redirection = new Redirection( "SEARCH_INSURED_SCREEN", Redirection.Type.TO_NEW_OPERATION );
			response.setRedirection( redirection );

			return response;
		} else if(COPY_TO_NEW_INSURED.equalsIgnoreCase( action )){
			polDataVO = loadData( commonVO );
			SourceOfBusinessVO sourceofBus = polDataVO.getGeneralInfo().getSourceOfBus();
			SchemeVO scheme = polDataVO.getScheme();
			CopyQuoteHelperVO  copyHelper = new CopyQuoteHelperVO();
			
			copyHelper.setBrokerName( sourceofBus.getBrokerName());		
			copyHelper.setDistributionChannel( sourceofBus.getDistributionChannel() );
			copyHelper.setNewInsuredFlag( "Y" );
			copyHelper.setOldPolicyId( polDataVO.getPolicyId() );
			copyHelper.setPolicyCode( polDataVO.getPolicyType() );
			copyHelper.setSchemeCode( scheme.getSchemeCode() );
			copyHelper.setTariffCode( scheme.getTariffCode() );	
			copyHelper.setExpiryDate(scheme.getExpiryDate( ));
			copyHelper.setEffDate(scheme.getEffDate());
			copyHelper.setPolicyTerm(polDataVO.getPolicyTerm());
			request.setAttribute( "copyQuoteData", copyHelper );
			request.getSession().setAttribute( com.Constant.CONST_OPERATION, COPY_TO_NEW_INSURED );
			request.setAttribute( "appFlow", Flow.CREATE_QUO.toString() );
			request.setAttribute("vatCodeToNewCust", polDataVO.getVatCode());
			String polType = String.valueOf( polDataVO.getPolicyType() );
			if(polType.equals( SvcConstants.HOME_POL_TYPE )){
				request.setAttribute( "LOB", LOB.HOME.toString() );
			} else if(polType.equals( SvcConstants.SHORT_TRAVEL_POL_TYPE ) || polType.equals( SvcConstants.LONG_TRAVEL_POL_TYPE )) {
				request.setAttribute( "LOB", LOB.TRAVEL.toString() );
			}
			else
			{
				request.setAttribute( "LOB", commonVO.getLob().toString() );
			}
			request.setAttribute("isNewCust", "Y");
			if(polType.equals( SvcConstants.HOME_POL_TYPE) || polType.equals( SvcConstants.SHORT_TRAVEL_POL_TYPE )
					|| polType.equals( SvcConstants.LONG_TRAVEL_POL_TYPE ) )
			{
				redirection = new Redirection( "LOAD_COMMON_GENERAL_INFO_PAGE", Redirection.Type.TO_NEW_OPERATION );
			}
			else
			{
				request.setAttribute("oldQuoteNo", commonVO.getQuoteNo());
				request.setAttribute("policyType", commonVO.getLob());
				redirection = new Redirection( "COMMON_FUNCTIONALITY&appFlow="+Flow.CREATE_QUO.toString()+"&lob="+commonVO.getLob().toString(), Redirection.Type.TO_NEW_OPERATION );
			}
		}

		if( !Utils.isEmpty( request.getSession().getAttribute( com.Constant.CONST_OPERATION ) ) && COPY_TO_NEW_INSURED.equals( request.getSession().getAttribute( com.Constant.CONST_OPERATION ) ) ){
			isNewCust = true;
		}
		else{
			isNewCust = false;
		}

		/** Calling the Stored Procedure  copy quote only for the case of same insured */
		if( !Utils.isEmpty( insuredId ) ){
			String polType = (!Utils.isEmpty(policyType) && Utils.isEmpty(polDataVO))? policyType : String.valueOf( polDataVO.getPolicyType() );
			CopyQuoteVO copyQuoteVO = new CopyQuoteVO();
			copyQuoteVO.setPolPolicyId(polPolicyId);
			copyQuoteVO.setInsuredId( insuredId );
			copyQuoteVO.setUserId( userId );
			copyQuoteVO.setPolType( polType );
			copyQuoteVO.setLocationCode( locationCode );
			copyQuoteVO.setDeletePolicyId( deletePolicyId );
			copyQuoteVO.setIsNewCustomer( isNewCust );
			CommonVO loadData = (CommonVO) TaskExecutor.executeTasks(
					COPY_QUOTE_OP_TYPE, copyQuoteVO);

			request.setAttribute( "copyQuoteResult", loadData );
			// Check home/travel
			if(polType.equals( SvcConstants.HOME_POL_TYPE )){
				request.setAttribute( "LOB", LOB.HOME.toString() );
				loadData.setLob( LOB.HOME );
			} else if(polType.equals( SvcConstants.SHORT_TRAVEL_POL_TYPE ) || polType.equals( SvcConstants.LONG_TRAVEL_POL_TYPE )) {
				request.setAttribute( "LOB", LOB.TRAVEL.toString() );
				loadData.setLob( LOB.TRAVEL );
			} 
			else
			{
				request.setAttribute( "LOB",commonVO.getLob().toString() );
				loadData.setLob( commonVO.getLob() );
			}

			if( !Utils.isEmpty( loadData.getQuoteNo() ) ) responseObj.setHeader( "newQuoteNumber", loadData.getQuoteNo().toString() );
			request.setAttribute( "appFlow", Flow.VIEW_QUO.toString() );
			PolicyContextUtil.createPolicyContext( request, Flow.VIEW_QUO.toString(), loadData.getLob() );
			PolicyContextUtil.getPolicyContext(request).setCommonDetails(loadData);
			if( !Utils.isEmpty( request.getSession().getAttribute( com.Constant.CONST_OPERATION ) ) && COPY_TO_NEW_INSURED.equals( request.getSession().getAttribute( com.Constant.CONST_OPERATION ) ) ){
				request.getSession().removeAttribute( com.Constant.CONST_OPERATION );
			}
			
			if( LOB.TRAVEL.equals( loadData.getLob() ) ){
				redirection = new Redirection( "LOAD_RISK_PAGE", Redirection.Type.TO_NEW_OPERATION );
			}else if( LOB.HOME.equals( loadData.getLob() ) ){
				redirection = new Redirection( "HOME_INSURANCE_PAGE&action=LOAD_DATA", Redirection.Type.TO_NEW_OPERATION );
			}else {
				redirection = new Redirection( "COMMON_FUNCTIONALITY&appFlow="+Flow.VIEW_QUO.toString()+"&lob="+commonVO.getLob().toString()+"&navigation=LOAD&pageType=riskPage", Redirection.Type.TO_NEW_OPERATION );
			}

		}

		/*
		 * Response object which will be used by framework after request
		 * processing is completed
		 */
		Response response = new Response();
		response.setRedirection( redirection );
		return response;

	}


	/**
	 * @param baseVO
	 * @return PolicyDataVO
	 * This method loads the policy data from the database
	 */
	private PolicyDataVO loadData( BaseVO baseVO ){
		//PolicyContext policyContext = PolicyContextUtil.getPolicyContext( request );
		Map<String, Class<? extends BaseVO>> dataMap = new LinkedHashMap<String, Class<? extends BaseVO>>();
		BaseLoadSvc baseLoadSvc = (BaseLoadSvc) Utils.getBean( "baseLoad" );
		dataMap.put( SvcConstants.T_TRN_POLICY, PolicyDataVO.class );
		CommonVO commonVO = (CommonVO) baseVO;
		LoadDataInputVO loadDataInputVO = new LoadDataInputVO();
		loadDataInputVO.setIsQuote( commonVO.getIsQuote() );
		loadDataInputVO.setQuoteNo( commonVO.getQuoteNo() );
		loadDataInputVO.setEndtId( commonVO.getEndtId() );
		loadDataInputVO.setLocCode( commonVO.getLocCode() );
		DataHolderVO<LinkedHashMap<String, List<TableData<BaseVO>>>> dataHolder = (DataHolderVO) baseLoadSvc.invokeMethod( "baseLoad", loadDataInputVO, dataMap );
		List<TableData<BaseVO>> polTableData = dataHolder.getData().get( SvcConstants.T_TRN_POLICY );
		PolicyDataVO policyDataVO = (PolicyDataVO) polTableData.get( 0 ).getTableData();
		return policyDataVO;
	}

}
