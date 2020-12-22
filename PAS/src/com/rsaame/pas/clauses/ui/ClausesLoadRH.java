package com.rsaame.pas.clauses.ui;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.util.SectionRHUtils;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.bus.NonStandardClause;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.RiskGroupingLevel;
import com.rsaame.pas.vo.bus.SectionVO;
import com.rsaame.pas.vo.bus.StandardClause;

public class ClausesLoadRH implements IRequestHandler{

	/* (non-Javadoc)
	 * @see com.mindtree.ruc.mvc.IRequestHandler#execute(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */

	private final static Integer PAR_SECTION_ID = Integer.valueOf( Utils.getSingleValueAppConfig( "PAR_SECTION" ) );
	private final static Integer PL_SECTION_ID = Integer.valueOf( Utils.getSingleValueAppConfig( "PL_SECTION" ) );

	@Override
	public Response execute( HttpServletRequest request, HttpServletResponse response ){
		Response responseObj = new Response();
		Integer sectionId = null;
		Redirection redirection = new Redirection();

		String action = request.getParameter( "action" );
		PolicyContext policyContext = PolicyContextUtil.getPolicyContext( request );
		
		request.setAttribute( AppConstants.MODE, VisibilityLevel.EDITABLE );
		request.setAttribute( AppConstants.FUNTION_NAME, policyContext.getAppFlow().toString() );
		
		PolicyVO policyVO = policyContext.getPolicyDetails();

		if( action.equalsIgnoreCase( "LOAD_TABS" ) ){

			java.util.List<SectionVO> riskDetailsList = policyVO.getRiskDetails();
			java.util.List<SectionVO> sectionVOs = new java.util.ArrayList<SectionVO>();
			DataHolderVO<List<SectionVO>> riskDetailsHolder = new DataHolderVO();

			for( SectionVO sectionVO : riskDetailsList ){
				//sectionVO.setSectionName( getSectionName( sectionVO.getSectionId() ) );
				sectionVO.setSectionName( SvcUtils.getLookUpDescription( "SBS_SECTIONS", "ALL", "ALL", sectionVO.getSectionId() ) );
				sectionVOs.add( sectionVO );
			}
			
			request.setAttribute( "LOB", "SBS");
			riskDetailsHolder.setData( sectionVOs );
			request.setAttribute( "sections", riskDetailsHolder );
			redirection = new Redirection( "/jsp/policy/viewclauses.jsp", Type.TO_JSP );
			responseObj.setRedirection( redirection );
		}

		if( action.equalsIgnoreCase( "LOAD_FIRST_CLAUSE" ) ){

			if( isSectionPresent( PAR_SECTION_ID, policyVO ) ){
				sectionId = PAR_SECTION_ID;
				request.setAttribute( "loadedSection", PAR_SECTION_ID );
			}
			else if( isSectionPresent( PL_SECTION_ID, policyVO ) ){
				sectionId = PL_SECTION_ID;
				request.setAttribute( "loadedSection", PL_SECTION_ID );

			}

			
			DataHolderVO<Object[]> inputData = new DataHolderVO<Object[]>();
			
			Flow appFlow = policyVO.getAppFlow();
			PolicyVO pol = AppUtils.setNewAppflow(policyContext);	
			Object[] input = { pol, sectionId };
			inputData.setData( input );
			
			DataHolderVO<List<StandardClause>> holderVO = (DataHolderVO<List<StandardClause>>) TaskExecutor.executeTasks( action, inputData );
			AppUtils.setOldAppFlow(policyVO,appFlow,policyContext);
			request.setAttribute( "standardClauses", holderVO.getData() );
			request.setAttribute( "LOB", "SBS");
			/**
			 * Set standard clauses into session. 
			 * They will be used during endorsement for matching with previous clauses and endorsed clauses.
			 */
			request.getSession( false ).setAttribute( AppConstants.GET_STD_CLAUSES, holderVO.getData());
			redirection = new Redirection( "/jsp/policy/clause.jsp", Type.TO_JSP );
			responseObj.setRedirection( redirection );
			
			
		}

		else if( action.equalsIgnoreCase( "LOAD_CLAUSES" ) ){

			sectionId = Integer.parseInt( request.getParameter( com.Constant.CONST_SECTIONID ) );
			
			DataHolderVO<Object[]> inputData = new DataHolderVO<Object[]>();
			
			Flow appFlow = policyVO.getAppFlow();
			PolicyVO pol = AppUtils.setNewAppflow(policyContext);	
			Object[] input = { pol, sectionId };
			inputData.setData( input );
			
			DataHolderVO<List<StandardClause>> holderVO = (DataHolderVO<List<StandardClause>>) TaskExecutor.executeTasks( "LOAD_FIRST_CLAUSE", inputData );
			
			AppUtils.setOldAppFlow(policyVO,appFlow,policyContext);
			request.setAttribute( "standardClauses", holderVO.getData() );
			request.setAttribute( "LOB", "SBS");
			/**
			 * Set standard clauses into session. 
			 * They will be used during endorsement for matching with previous clauses and endorsed clauses.
			 */
			request.getSession( false ).setAttribute( AppConstants.GET_STD_CLAUSES, holderVO.getData());
			redirection = new Redirection( "/jsp/policy/clause.jsp", Type.TO_JSP );
			responseObj.setRedirection( redirection );
			if(	!Utils.isEmpty(request.getParameter(com.Constant.CONST_ERRORMESSAGE)) ){
				request.setAttribute(com.Constant.CONST_ERRORMESSAGE,request.getParameter(com.Constant.CONST_ERRORMESSAGE) );
			}
		}
		else if( action.equalsIgnoreCase( "LOAD_NON_STD_CLAUSES" ) ){
			DataHolderVO<List<NonStandardClause>> holderVO = (DataHolderVO<List<NonStandardClause>>) TaskExecutor.executeTasks( "LOAD_NONSTD_CLAUSE", policyVO );
			request.setAttribute( "nonStandardClauses", holderVO.getData() );
			request.setAttribute( "LOB", "SBS");
			redirection = new Redirection( "/jsp/policy/nonStandardClauses.jsp", Type.TO_JSP );
			responseObj.setRedirection( redirection );
		}
		else if( action.equalsIgnoreCase( "SAVE_NON_STD_CLAUSES" ) ){
			sectionId = Integer.parseInt( request.getParameter( com.Constant.CONST_SECTIONID ) );
			BeanMapper.map( request, policyVO);
			request.setAttribute( "LOB", "SBS");
			System.out.println("In side save clause");
			TaskExecutor.executeTasks( "SAVE_NON_STD_CLAUSES", policyVO );
			String message = "pas.saveSuccessful";
			AppUtils.addErrorMessage( request, message );
		}
		else if( action.equalsIgnoreCase( "SAVE_CLAUSES" ) ){
			sectionId = Integer.parseInt( request.getParameter( com.Constant.CONST_SECTIONID ) );
			SectionVO sectionVo =  getSection(sectionId ,policyVO.getRiskDetails());
			UserProfile userProfile = (UserProfile)request.getSession().getAttribute(AppConstants.SESSION_USER_PROFILE_VO);
			if(!Utils.isEmpty(userProfile)){
				policyVO.setLoggedInUser(userProfile);	
			}

			/**
			 * Fetch standard clauses from session. 
			 * They will be used during endorsement for matching with previous clauses and endorsed clauses.
			 */
			List<StandardClause> stdClausesList = 	(List<StandardClause>) request.getSession(false).getAttribute(AppConstants.GET_STD_CLAUSES);
			
			
			/*
			 * Resolve the referral flow to edit quote or amend policy
			 */
			Flow oldAppFlow = policyVO.getAppFlow();
			PolicyVO pol = AppUtils.setNewAppflow(policyContext);	
			
			BeanMapper.map( request, sectionVo);
			Object[] input = { sectionVo, pol, stdClausesList };
			DataHolderVO<Object[]> inputData = new DataHolderVO<Object[]>();
			inputData.setData( input );
			Integer[] standardCondition = { Integer.valueOf( Utils.getSingleValueAppConfig( "COND" )+sectionVo.getSectionId())  };
			if(!SectionRHUtils.executeConditionCheckForSBSBroker(request, policyVO, "", "Standard", standardCondition, responseObj ))
			{
				policyVO.setSelectedSectionId( sectionVo.getSectionId() );
				policyContext.setPolicyDetails( policyVO );
				return responseObj;
			}
			pol = (PolicyVO) TaskExecutor.executeTasks( "SAVE_CLAUSES", inputData );
			AppUtils.setOldAppFlow(pol,oldAppFlow,policyContext);
			
			String message = "pas.saveSuccessful";
			//AppUtils.addErrorMessage( request, message );
		    //String[] data = {sectionId.toString(),Utils.getAppErrorMessage( message )};
		    List<String>  data = new  ArrayList<String>();
		    data.add(sectionId.toString());
		    data.add(Utils.getAppErrorMessage( message ));
		    responseObj.setSuccess( true );
		    responseObj.setData( data );
		}

		return responseObj;
	}

	private boolean isSectionPresent( int sectionId, PolicyVO policyDetails ){
		SectionVO section = new SectionVO( RiskGroupingLevel.LOCATION );
		section.setSectionId( sectionId );
		return policyDetails.getRiskDetails().contains( section );
	}

	/*private String getSectionName( Integer sectionId ){
		String sectionName = null;
		switch( sectionId ){
			case 1:
				sectionName = SvcConstants.PAR_NAME;
				break;
			case 6:
				sectionName = SvcConstants.PL_NAME;
				break;
			case 8:
				sectionName = SvcConstants.MONEY_NAME;
				break;
			case 7:
				sectionName = SvcConstants.WC_NAME;
				break;
		}

		return sectionName;
	}*/

	private SectionVO getSection( Integer sectionId, List<SectionVO> riskDetails ){
		SectionVO finderSection = new SectionVO( RiskGroupingLevel.LOCATION );
		finderSection.setSectionId( sectionId );
		int indexOfSection = riskDetails.indexOf( finderSection );
		return riskDetails.get( indexOfSection );
	}
	
	
}
