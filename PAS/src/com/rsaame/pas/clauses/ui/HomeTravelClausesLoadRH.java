package com.rsaame.pas.clauses.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Redirection;
import com.mindtree.ruc.mvc.Redirection.Type;
import com.mindtree.ruc.mvc.Response;
import com.mindtree.ruc.mvc.tags.util.VisibilityLevel;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.com.svc.PasReferralSaveCommonSvc;
import com.rsaame.pas.kaizen.vo.PASServiceContext;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.util.SectionRHUtils;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.bus.NonStandardClause;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.ReferralListVO;
import com.rsaame.pas.vo.bus.ReferralVO;
import com.rsaame.pas.vo.bus.SectionVO;
import com.rsaame.pas.vo.bus.StandardClause;
import com.rsaame.pas.vo.cmn.CommonVO;

/**
 * 
 * @author m1016996
 *	Class handles all clauses related operations in Phase3
 *	Such as loading and saving of Standard and non standard clauses
 *
 */
public class HomeTravelClausesLoadRH implements IRequestHandler{

	@SuppressWarnings( "unchecked" )
	@Override
	public Response execute( HttpServletRequest request, HttpServletResponse response ){
		Response responseObj = new Response();
		Integer sectionId = null;
		Redirection redirection = new Redirection();

		String action = request.getParameter( "action" );
		PolicyContext policyContext = PolicyContextUtil.getPolicyContext( request );

		request.setAttribute( AppConstants.MODE, VisibilityLevel.EDITABLE );
		request.setAttribute( AppConstants.FUNTION_NAME, policyContext.getAppFlow().toString() );

		CommonVO commonVO = policyContext.getCommonDetails();

		if( action.equalsIgnoreCase( "LOAD_TABS" ) ){

			if( !Utils.isEmpty( commonVO.getLob() ) ){

				request.setAttribute( "LOB", commonVO.getLob().toString() );
			}

			redirection = new Redirection( "/jsp/policy/homeTravelViewClauses.jsp", Type.TO_JSP );
			responseObj.setRedirection( redirection );
		}
		else if( action.equalsIgnoreCase( "LOAD_FIRST_CLAUSE" ) ){

			DataHolderVO<Object[]> inputData = new DataHolderVO<Object[]>();

			if( !Utils.isEmpty( commonVO.getLob() ) ){

				sectionId = Integer.valueOf( Utils.getSingleValueAppConfig( commonVO.getLob() + "_SEC_ID" ) );
			}

			Object[] input = { commonVO, sectionId,false,true};
			inputData.setData( input );

			DataHolderVO<List<StandardClause>> holderVO = (DataHolderVO<List<StandardClause>>) TaskExecutor.executeTasks( action, inputData );
			// AppUtils.setOldAppFlow(policyVO,appFlow,policyContext);
			request.setAttribute( "standardClauses", holderVO.getData() );
			request.setAttribute( "LOB", commonVO.getLob().toString() );

			/**
			 * Set standard clauses into session. They will be used during
			 * endorsement for matching with previous clauses and endorsed
			 * clauses.
			 */
			request.getSession( false ).setAttribute( AppConstants.GET_STD_CLAUSES, holderVO.getData() );
			redirection = new Redirection( "/jsp/policy/clause.jsp", Type.TO_JSP );
			responseObj.setRedirection( redirection );

		}
		else if( action.equalsIgnoreCase( "LOAD_CLAUSES" ) ){

			sectionId = Integer.parseInt( request.getParameter( com.Constant.CONST_SECTIONID ) );

			DataHolderVO<Object[]> inputData = new DataHolderVO<Object[]>();

			Flow appFlow = commonVO.getAppFlow();
			// PolicyVO pol = AppUtils.setNewAppflow(policyContext);
			if( !Utils.isEmpty( commonVO.getLob() ) ){

				sectionId = Integer.valueOf( Utils.getSingleValueAppConfig( commonVO.getLob() + "_SEC_ID" ) );
			}

			Object[] input = { commonVO, sectionId,false,true};
			inputData.setData( input );

			DataHolderVO<List<StandardClause>> holderVO = (DataHolderVO<List<StandardClause>>) TaskExecutor.executeTasks( "LOAD_FIRST_CLAUSE", inputData );

			// AppUtils.setOldAppFlow(policyVO,appFlow,policyContext);
			request.setAttribute( "standardClauses", holderVO.getData() );

			if( !Utils.isEmpty( commonVO.getLob() ) ){
				request.setAttribute( "LOB", commonVO.getLob().toString() );
			}

			/**
			 * Set standard clauses into session. They will be used during
			 * endorsement for matching with previous clauses and endorsed
			 * clauses.
			 */
			request.getSession( false ).setAttribute( AppConstants.GET_STD_CLAUSES, holderVO.getData() );
			redirection = new Redirection( "/jsp/policy/clause.jsp", Type.TO_JSP );
			responseObj.setRedirection( redirection );

			if( !Utils.isEmpty( request.getParameter( com.Constant.CONST_ERRORMESSAGE ) ) ){
				request.setAttribute( com.Constant.CONST_ERRORMESSAGE, request.getParameter( com.Constant.CONST_ERRORMESSAGE ) );
			}
		}

		else if( action.equalsIgnoreCase( "LOAD_NON_STD_CLAUSES" ) ){
			DataHolderVO<List<NonStandardClause>> holderVO = (DataHolderVO<List<NonStandardClause>>) TaskExecutor.executeTasks( "LOAD_NONSTD_CLAUSE", commonVO );
			request.setAttribute( "nonStandardClauses", holderVO.getData() );
			if( !Utils.isEmpty( commonVO.getLob() ) ){
				request.setAttribute( "LOB", commonVO.getLob().toString() );
			}
			redirection = new Redirection( "/jsp/policy/nonStandardClauses.jsp", Type.TO_JSP );
			responseObj.setRedirection( redirection );
		}
		else if( action.equalsIgnoreCase( com.Constant.CONST_SAVE_CLAUSES ) ){

			sectionId = Integer.parseInt( request.getParameter( com.Constant.CONST_SECTIONID ) );
			// getSection(sectionId ,policyVO.getRiskDetails());
			UserProfile userProfile = (UserProfile) request.getSession().getAttribute( AppConstants.SESSION_USER_PROFILE_VO );

			if( !Utils.isEmpty( userProfile ) ){
				commonVO.setLoggedInUser( userProfile );
			}

			/**
			 * Fetch standard clauses from session. They will be used during
			 * endorsement for matching with previous clauses and endorsed
			 * clauses.
			 */
			List<StandardClause> stdClausesList = (List<StandardClause>) request.getSession( false ).getAttribute( AppConstants.GET_STD_CLAUSES );

			/*
			 * Resolve the referral flow to edit quote or amend policy
			 */
			Flow oldAppFlow = commonVO.getAppFlow();

			SectionVO sectionVo = new SectionVO( null );
			BeanMapper.map( request, sectionVo );

			Object[] input = { sectionVo, commonVO, stdClausesList,false };

			DataHolderVO<Object[]> inputData = new DataHolderVO<Object[]>();
			inputData.setData( input );
			
			PolicyDataVO policyDataVO = new PolicyDataVO();
			
			policyDataVO.setCommonVO( commonVO );
			policyDataVO.setStandardClauses( sectionVo.getStandardClauses() );
			
			if(!Utils.isEmpty( userProfile )){
				policyDataVO.setLoggedInUser( userProfile );
			}
			
			Integer[] standardCondition = { Integer.valueOf( Utils.getSingleValueAppConfig( "STANDARD_CLAUSES" ) ) };
			
			/**
			 * Trigger referral in case of default checked conditions are changed.
			 */
			if( SectionRHUtils.executeReferralTaskValidation( policyDataVO, "", "Standard condition", standardCondition, request ) ){
				TaskExecutor.executeTasks( com.Constant.CONST_SAVE_CLAUSES, inputData );
				storeReferral( request, policyDataVO );
				String message = "pas.saveSuccessful";
				AppUtils.addErrorMessage( request, message );
				// String[] data = {sectionId.toString(),Utils.getAppErrorMessage(
				// message )};
				List<String> data = new ArrayList<String>();
				data.add( sectionId.toString() );
				data.add( Utils.getAppErrorMessage( message ) );
				responseObj.setResponseType( Response.Type.JSON );
				responseObj.setSuccess( true );
				responseObj.setData( data );
				response.setHeader( "isJSON", "true" );
			}
			else if(policyDataVO.getCommonVO().getLob().toString().equalsIgnoreCase("HOME") ||  policyDataVO.getCommonVO().getLob().toString().equalsIgnoreCase("TRAVEL")){
				redirectReferral( policyDataVO, request, response, responseObj, redirection );
			}
			else
			{
				SectionRHUtils.redirectReferralForMonoline( policyDataVO, request, response, responseObj, redirection );
			}

			// AppUtils.setOldAppFlow(pol,oldAppFlow,policyContext);

			
		}
		else if( action.equalsIgnoreCase( "SAVE_NON_STD_CLAUSES" ) ){
			PolicyVO policyVO = new PolicyVO();
			sectionId = Integer.parseInt( request.getParameter( com.Constant.CONST_SECTIONID ) );
			BeanMapper.map( request, policyVO );
			System.out.println( "In side save clause" );

			PolicyDataVO policyDataVO = new PolicyDataVO();
			policyDataVO.setNonStandardClauses( policyVO.getNonStandardClause() );
			policyDataVO.setCommonVO( commonVO );
			TaskExecutor.executeTasks( "SAVE_NON_STD_CLAUSES", policyDataVO );

			String message = "pas.saveSuccessful";
			AppUtils.addErrorMessage( request, message );
		}

		return responseObj;
	}

	@SuppressWarnings( "unchecked" )
	private void storeReferral(HttpServletRequest request,PolicyDataVO policyDataVO){
		if( !Utils.isEmpty( (Map<String, Map<String, String>>) request.getSession( false ).getAttribute( com.Constant.CONST_REFERRALMAP ) ) ){
			ReferralListVO referralListVO = new ReferralListVO();
			ReferralVO referralVO = new ReferralVO();
			List<ReferralVO> refVOList = new ArrayList<ReferralVO>();
			referralVO.setRefDataTextField( (Map<String, Map<String, String>>) request.getSession( false ).getAttribute( com.Constant.CONST_REFERRALMAP ) );
			referralVO.setLocationCode( PASServiceContext.getLocation() );

			request.getSession( false ).removeAttribute( com.Constant.CONST_REFERRALMAP );

			refVOList.add( referralVO );
			referralListVO.setReferrals( refVOList );

			policyDataVO.setReferralVOList( referralListVO );
			CommonVO commonVO = policyDataVO.getCommonVO();
			if(!Utils.isEmpty( commonVO ) && !Utils.isEmpty( commonVO.getPolicyId() ) && !Utils.isEmpty( commonVO.getEndtId() )){
				policyDataVO.setPolicyId( commonVO.getPolicyId() );
				policyDataVO.setEndtId( policyDataVO.getEndtId() );
			}
			
			PasReferralSaveCommonSvc pasReferralSaveCommonSvc = null;
			
			if(AppUtils.isQuote( commonVO )){
				pasReferralSaveCommonSvc = (PasReferralSaveCommonSvc)Utils.getBean( "pasReferralCmnSvc" );
			}else{
				pasReferralSaveCommonSvc = (PasReferralSaveCommonSvc)Utils.getBean( "pasReferralCmnSvc_POL" );
			}
			
			pasReferralSaveCommonSvc.invokeMethod( "saveReferralData", policyDataVO );
			//TaskExecutor.executeTasks( "SAVE_CLAUSES_REFERRAL", policyDataVO );

		}
		
	}

	/**
	 * Method to redirect to page in case of referral
	 * 
	 * @param policyDataVO
	 * @param request
	 * @param response
	 * @param responseObj
	 * @param redirection
	 */
	private void redirectReferral( PolicyDataVO policyDataVO, HttpServletRequest request, HttpServletResponse response, Response responseObj, Redirection redirection ){
		List<String> messageList = new ArrayList<String>();
		ReferralListVO refListVO = policyDataVO.getReferralVOList();
		if( !Utils.isEmpty( refListVO ) ){
			for( ReferralVO refVO : refListVO.getReferrals() ){
				for( String refText : refVO.getReferralText() ){
					String message = new String( refText );
					messageList.add( message );
				}
			}
		}
		
		CommonVO commonVO = policyDataVO.getCommonVO();
		String nextaction = com.Constant.CONST_SAVE_CLAUSES;
		if( !Utils.isEmpty( policyDataVO.getReferralVOList() ) && !Utils.isEmpty( policyDataVO.getReferralVOList().getReferrals() )
				&& !Utils.isEmpty( policyDataVO.getReferralVOList().getReferrals().size() > 0 )
				&& !Utils.isEmpty( policyDataVO.getReferralVOList().getReferrals().get( 0 ).getRefDataTextField() ) ){
			HttpSession session = request.getSession();
			//Holding the referral messages map in session
			session.setAttribute( com.Constant.CONST_REFERRALMAP, policyDataVO.getReferralVOList().getReferrals().get( 0 ).getRefDataTextField() );
		}
		request.setAttribute( "nextAction", nextaction );
		request.setAttribute( "referralListVO", refListVO );
		
		if(!Utils.isEmpty( commonVO )){
			request.setAttribute( "lob", String.valueOf( commonVO.getLob() ) );
			request.setAttribute( com.Constant.CONST_SECTIONID, "14" );
		}
		response.setHeader( "isRef", "true" );
		redirection = new Redirection( "/jsp/quote/referralCommon.jsp", Type.TO_JSP );
		responseObj.setRedirection( redirection );

	}
	
	

}
