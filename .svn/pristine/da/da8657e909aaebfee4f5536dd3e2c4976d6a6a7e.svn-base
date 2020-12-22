package com.rsaame.pas.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.exception.SystemException;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.MVCUtils;
import com.mindtree.ruc.mvc.Redirection;
import com.mindtree.ruc.mvc.Response;
import com.mindtree.ruc.mvc.Response.Type;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.rules.invoker.RuleServiceInvoker;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.bus.HomeInsuranceVO;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.ReferralListVO;
import com.rsaame.pas.vo.bus.ReferralVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.SectionVO;
import com.rsaame.pas.vo.bus.TravelInsuranceVO;

public class SectionRHUtils{
	/**
	 * Executes the RULES tasks and checks for referral. Returns false, if rules failed and there are referrals.
	 * @param responseObj
	 * @param action
	 * @param policyVO
	 * @return
	 */
	public static boolean executeReferralTask( Response responseObj, String action, PolicyVO policyVO, String actionIdentifier ){
		boolean rulesPassed = true;

		boolean rulesEnabled = Utils.toDefaultFalseBoolean( Utils.getSingleValueAppConfig( AppConstants.APP_CONFIG_RULES_ENABLED, "Y" ) );

		if( rulesEnabled ){
			ReferralListVO listReferralVO = null;
			try{
				TaskExecutor.executeTasks( action, policyVO );
			}
			catch( BusinessException e ){
				rulesPassed = false;
				listReferralVO = (com.rsaame.pas.vo.bus.ReferralListVO) e.getExceptionData();
				if(null != listReferralVO) {
					responseObj.setData( listReferralVO );
					policyVO.setReferrals( listReferralVO.getReferrals() );

					for( ReferralVO referralVO : listReferralVO.getReferrals() ){
						if( !Utils.isEmpty( referralVO ) ){
							referralVO.setActionIdentifier( actionIdentifier );
							policyVO.setReferral( referralVO );
						}
					}
					/* In case of referrals, set the response type in order to avoid redirection through appconfig.properties
					 * configuration
					 */
					responseObj.setResponseType( Type.JSON );
				}
				else {
					throw new BusinessException( "pas.cmn.rulesException", null, "Error in the rules", "Error in the rules" );
				}
				
				//return rulesPassed;  //SONARFIX -- commented to not to return same value
			}

			if(rulesPassed){  //SONARFIX -- added -- to execute the if block if there is no exception
			policyVO.emptyReferral();
			}
		}
		return rulesPassed;
	}

	/**
	 * For rule invocation for general info common page
	 *  
	 * @param policyDataVO
	 * @param action
	 * @param actionIdentifier
	 * @return
	 */
	public static boolean executeReferralTask( PolicyDataVO policyDataVO, String action, String actionIdentifier,HttpServletRequest request  ){
		boolean rulesPassed = true;

		boolean rulesEnabled = Utils.toDefaultFalseBoolean( Utils.getSingleValueAppConfig( AppConstants.APP_CONFIG_RULES_ENABLED, "Y" ) );

		if( rulesEnabled ){
			ReferralListVO listReferralVO = null;
			try{
				Integer[] intArray = new Integer[ 1 ];
				if( policyDataVO instanceof HomeInsuranceVO ){
					intArray[ 0 ] = 140;
				}
				if( policyDataVO instanceof TravelInsuranceVO ){
					intArray[ 0 ] = 150;
				}
				//TaskExecutor.executeTasks( action, policyDataVO );
				RuleServiceInvoker ruleServiceInvoker = (RuleServiceInvoker) Utils.getBean( com.Constant.CONST_RULESERVICEINVOKER );
				UserProfile userProfile = (UserProfile) request.getSession().getAttribute( AppConstants.SESSION_USER_PROFILE_VO );
				ruleServiceInvoker.callRestFulRuleService( policyDataVO, intArray, userProfile.getRsaUser().getHighestRole( policyDataVO.getCommonVO().getLob().toString() ) );
			}
			catch( BusinessException e ){
				rulesPassed = false;
				listReferralVO = (com.rsaame.pas.vo.bus.ReferralListVO) e.getExceptionData();
				if(null != listReferralVO){
					policyDataVO.setReferralVOList( listReferralVO );
				}
				else 
					//throw new SystemException( "", null, com.Constant.CONST_ERROR_IN_THE_RULES, com.Constant.CONST_ERROR_IN_THE_RULES );
					throw new BusinessException( "pas.cmn.rulesException", null, "Error in the rule_2", "Error in the rule_2" );
			}
		}

		return rulesPassed;
	}

	/**
	 * For rule invocation for home risk cover page
	 * 
	 * @param homeInsuranceVO
	 * @param action
	 * @param actionIdentifier
	 * @param request
	 * @return
	 */
	public static boolean executeReferralTaskHome( HomeInsuranceVO homeInsuranceVO, String action, String actionIdentifier, HttpServletRequest request ,boolean onChangeRef){
		boolean rulesPassed = true;

		boolean rulesEnabled = Utils.toDefaultFalseBoolean( Utils.getSingleValueAppConfig( AppConstants.APP_CONFIG_RULES_ENABLED, "Y" ) );

		String refIndicator = request.getParameter( com.Constant.CONST_REFINDICATOR );
		
		AppUtils.setEndorsementVO( request, homeInsuranceVO );
		
		if( rulesEnabled && Utils.isEmpty( refIndicator ) ){
			ReferralListVO listReferralVO = null;
			
			AppUtils.setPremiumRequest(request,homeInsuranceVO);
			
			try{
				Integer[] intArray = new Integer[ 1 ];
				intArray[ 0 ] = 14;
				RuleServiceInvoker ruleServiceInvoker = (RuleServiceInvoker) Utils.getBean( com.Constant.CONST_RULESERVICEINVOKER );
				UserProfile userProfile = (UserProfile) request.getSession().getAttribute( AppConstants.SESSION_USER_PROFILE_VO );
				ruleServiceInvoker.callRestFulRuleService( homeInsuranceVO, intArray, userProfile.getRsaUser().getHighestRole( homeInsuranceVO.getCommonVO().getLob().toString() ) );
				PolicyContext pc = PolicyContextUtil.getPolicyContext( request );
				// check if there are referrals from general info page
				if(onChangeRef){
					Map<String, String> referralMessages = DAOUtils.getReferralMessages( pc.getCommonDetails().getPolicyId(), pc.getCommonDetails().getEndtId() ,((UserProfile)pc.getCommonDetails().getLoggedInUser() ).getRsaUser().getUserId()  );
					if( !Utils.isEmpty( referralMessages ) ){
						rulesPassed = false;
					}
				}
				
			}
			catch( BusinessException e ){
				rulesPassed = false;
				listReferralVO = (com.rsaame.pas.vo.bus.ReferralListVO) e.getExceptionData();
				if(null != listReferralVO)
					homeInsuranceVO.setReferralVOList( listReferralVO );
				else 
					throw new BusinessException( "pas.cmn.rulesException", null, "Error in the rule_3", "Error in the rule_3" );
			}
		}

		/*
		 * Resetting to null after the rule call since it will not be used during save
		 */
		if(!Utils.isEmpty( homeInsuranceVO ) && !Utils.isEmpty( homeInsuranceVO.getEndorsmentVO() )){
			homeInsuranceVO.setEndorsmentVO( null );
		}
		return rulesPassed;
	}

	/**
	 * Returns the configured location reload JSP (usually the JSON JSP) for the section.
	 * @param sectionId The section Id for the section
	 * @param mandatory Indicates that the configuration is mandatory; if the configuration is not found and this flag is <code>true</code>,
	 * then throws a SystemException
	 * @return The JSP path for the location reload JSP for the section
	 */
	public static String getLocationReloadJSP( int sectionId, boolean mandatory ){
		String locationReloadJSP = Utils.getSingleValueAppConfig( "LOCATION_RELOAD_JSP_" + sectionId );
		if( Utils.isEmpty( locationReloadJSP ) && mandatory ){
			throw new SystemException( "pas.locComp.locReloadJSPNotSet", null, "Location reload JSP has not been set for section [", String.valueOf( sectionId ) );
		}

		return locationReloadJSP;
	}

	/**
	 * This method creates the saved and unsaved locations list for the current section from the section's riskGroupDetails map, and 
	 * sets them to <code>savedLocations</code> and <code>unsavedLocations</code> request attributes, respectively.
	 * 
	 * @param request
	 * @param pc
	 */
	@SuppressWarnings( "unchecked" )
	public static void createSavedAndUnsavedLocList( HttpServletRequest request, PolicyContext pc ){
		java.util.Map<RiskGroup, String> savedLocations = new java.util.LinkedHashMap<RiskGroup, String>();
		java.util.Map<RiskGroup, String> unsavedLocations = new java.util.LinkedHashMap<RiskGroup, String>();
		Integer currentSectionId = pc.getCurrentSectionId();

		java.util.List<Integer> rgIds = pc.getRiskGroupIds( currentSectionId );
		SectionVO sectionVO = pc.getBasicSection();
		Map baseRiskMap = sectionVO.getRiskGroupDetails();
		if( !Utils.isEmpty( rgIds ) ){
			for( Integer rgId : rgIds ){
				RiskGroup rg = pc.getRiskGroup( currentSectionId, rgId.toString() );
				if( Utils.isEmpty( pc.getRiskGroupDetails( currentSectionId, rg ) ) ){
					if( baseRiskMap.containsKey( rg ) ){
						unsavedLocations.put( rg, AppConstants.BASE_LOC );
					}
					else{
						unsavedLocations.put( rg, AppConstants.NEW_LOC );
					}
				}
				else{
					if( baseRiskMap.containsKey( rg ) ){
						savedLocations.put( rg, AppConstants.BASE_LOC );
					}
					else{
						savedLocations.put( rg, AppConstants.NEW_LOC );

					}
				}
			}

			Set<? extends RiskGroup> savedlocationList;
			Set<? extends RiskGroup> unsavedlocationList;
			List<LocationVO> svdlocationList = new ArrayList<LocationVO>();
			List<LocationVO> unsvdlocationList = new ArrayList<LocationVO>();
			if(savedLocations.size()>0)
			{
				savedlocationList =  savedLocations.keySet();
				svdlocationList.addAll( (Collection<? extends LocationVO>) savedlocationList );
				Collections.sort((List<LocationVO>)svdlocationList, new LocationVOComparator() ); // Request ID : 166761 : Changes
			}
			if(unsavedLocations.size()>0)
			{
				unsavedlocationList =  unsavedLocations.keySet();
				unsvdlocationList.addAll( (Collection<? extends LocationVO>) unsavedlocationList );
				Collections.sort((List<LocationVO>)unsvdlocationList, new LocationVOComparator() ); // Request ID : 166761 : Changes
			}		
			
			java.util.Map<RiskGroup, String> sortedSavedLocations = new java.util.LinkedHashMap<RiskGroup, String>();
			java.util.Map<RiskGroup, String> sortedUnsavedLocations = new java.util.LinkedHashMap<RiskGroup, String>();
			for(LocationVO svdlocVo:svdlocationList)
			{
				if(!Utils.isEmpty( savedLocations.get(svdlocVo) ))
				sortedSavedLocations.put(svdlocVo,savedLocations.get(svdlocVo) );				
			}
			for(LocationVO unsvdlocVo:unsvdlocationList)
			{
				if(!Utils.isEmpty( unsavedLocations.get(unsvdlocVo) ))
					sortedUnsavedLocations.put(unsvdlocVo,unsavedLocations.get(unsvdlocVo) );	
			}
			request.setAttribute( "savedLocations", sortedSavedLocations );
			request.setAttribute( "unsavedLocations", sortedUnsavedLocations );
		}
	}

	/**
	 * Reads the configured service task identifier for the section and returns it.
	 * @param sectionId
	 * @return
	 */
	public static String getSectionSaveSvcIdentifier( Integer sectionId, PolicyContext policyContext ){
		String svcIdentifier = "";
		if( policyContext.getPolicyDetails().getAppFlow() == Flow.AMEND_POL ){
			if( !Utils.isEmpty( sectionId ) ) svcIdentifier = Utils.getSingleValueAppConfig( Utils.concat( "SVC_IDENTIFIER_POL_", sectionId.toString() ) );
		}
		else{
			if( !Utils.isEmpty( sectionId ) ) svcIdentifier = Utils.getSingleValueAppConfig( Utils.concat( "SVC_IDENTIFIER_", sectionId.toString() ) );
		}

		return svcIdentifier;
	}

	public static PolicyVO executeRatingTask( String action, PolicyVO policyVo ){
		return (PolicyVO) TaskExecutor.executeTasks( action, policyVo );
	}

	public static boolean isReadOnlyMode( PolicyContext policyContext, HttpServletRequest request ){
		boolean flag = false;
		if( policyContext.getAppFlow().equals( Flow.VIEW_POL ) || policyContext.getAppFlow().equals( Flow.VIEW_QUO ) ){
			flag = true;
		}
		else if( policyContext.getAppFlow().equals( Flow.RESOLVE_REFERAL ) ){
			Flow flow = (Flow) request.getSession( false ).getAttribute( com.Constant.CONST_APPFLOW );
			if( !Utils.isEmpty( flow ) && flow.equals( Flow.VIEW_QUO ) ){
				flag = true;
				request.getSession( false ).removeAttribute( com.Constant.CONST_APPFLOW );
			}
			else if( !Utils.isEmpty( flow ) && flow.equals( Flow.EDIT_QUO ) ){
				flag = false;
				request.getSession( false ).removeAttribute( com.Constant.CONST_APPFLOW );
			}
			else{
				flag = false;
			}
		}
		else{
			flag = false;
		}
		return flag;
	}
	
	
	/**
	 * For rule invocation for travel risk cover page
	 * 
	 * @param homeInsuranceVO
	 * @param action
	 * @param actionIdentifier
	 * @param request
	 * @return
	 */
	public static boolean executeReferralTaskForTravel( PolicyDataVO policyDataVO , String action, String actionIdentifier, Integer[] sectionArray, HttpServletRequest request ){
		boolean rulesPassed = true;
		
		boolean rulesEnabled = Utils.toDefaultFalseBoolean( Utils.getSingleValueAppConfig( AppConstants.APP_CONFIG_RULES_ENABLED, "Y" ) );
		String refIndicator = request.getParameter( com.Constant.CONST_REFINDICATOR );
		
		AppUtils.setEndorsementVO( request, policyDataVO );
		
		if( rulesEnabled && Utils.isEmpty( refIndicator )){
			ReferralListVO listReferralVO = null;
			try{
				RuleServiceInvoker ruleServiceInvoker = (RuleServiceInvoker)Utils.getBean( com.Constant.CONST_RULESERVICEINVOKER );
				UserProfile userProfile = (UserProfile)request.getSession().getAttribute(AppConstants.SESSION_USER_PROFILE_VO);
				ruleServiceInvoker.callRestFulRuleService( policyDataVO, sectionArray , userProfile.getRsaUser().getHighestRole( policyDataVO.getCommonVO().getLob().toString() ) );
				if(!Utils.isEmpty(policyDataVO.getReferralVOList()))
				{
					if(!policyDataVO.getReferralVOList().getReferalType().equalsIgnoreCase("Pass"))
					{
						rulesPassed = false;
					}
				}
			}
			catch( BusinessException e ){
				rulesPassed = false;
				listReferralVO = (com.rsaame.pas.vo.bus.ReferralListVO) e.getExceptionData();
				if(null != listReferralVO) {
					policyDataVO.setReferralVOList(listReferralVO);
				}
				else 
					throw new BusinessException( "pas.cmn.rulesException", null, "Error in the rule_4", "Error in the rule_4" );
			}
		}
		
		/*
		 * Resetting to null after the rule call since it will not be used during save
		 */
		if(!Utils.isEmpty( policyDataVO ) && !Utils.isEmpty( policyDataVO.getEndorsmentVO() )){
			policyDataVO.setEndorsmentVO( null );
		}
		return rulesPassed;
	}
	
	/**
	 * Method ot call the validation rule mapper and service
	 * 
	 * @param policyDataVO
	 * @param action
	 * @param actionIdentifier
	 * @param sectionArray
	 * @param request
	 * @return
	 */
	public static boolean executeReferralTaskValidation( PolicyDataVO policyDataVO, String action, String actionIdentifier, Integer[] sectionArray, HttpServletRequest request ){
		boolean rulesPassed = true;
		
		boolean rulesEnabled = Utils.toDefaultFalseBoolean( Utils.getSingleValueAppConfig( AppConstants.APP_CONFIG_RULES_ENABLED, "Y" ) );
		String refIndicator = request.getParameter( com.Constant.CONST_REFINDICATOR );
		
		AppUtils.setEndorsementVO( request, policyDataVO );
		
		if( rulesEnabled && Utils.isEmpty( refIndicator )){
			ReferralListVO listReferralVO = null;
			try{
				RuleServiceInvoker ruleServiceInvoker = (RuleServiceInvoker)Utils.getBean( com.Constant.CONST_RULESERVICEINVOKER );
				UserProfile userProfile = (UserProfile)request.getSession().getAttribute(AppConstants.SESSION_USER_PROFILE_VO);
				ruleServiceInvoker.callRestFulRuleService( policyDataVO, sectionArray , userProfile.getRsaUser().getHighestRole( policyDataVO.getCommonVO().getLob().toString() ) );
				
				if(!Utils.isEmpty(policyDataVO.getReferralVOList()))
				{
					if(!policyDataVO.getReferralVOList().getReferalType().equalsIgnoreCase("Pass"))
					{
						rulesPassed = false;
					}
				}
			}
			catch( BusinessException e ){
				rulesPassed = false;
				listReferralVO = (com.rsaame.pas.vo.bus.ReferralListVO) e.getExceptionData();
				if(null != listReferralVO) {
					policyDataVO.setReferralVOList(listReferralVO);
				}
				else 
					throw new BusinessException( "pas.cmn.rulesException", null, "Error in the rule_5", "Error in the rule_5" );
			}
		}
		
		/*
		 * Resetting to null after the rule call since it will not be used during save
		 */
		if(!Utils.isEmpty( policyDataVO ) && !Utils.isEmpty( policyDataVO.getEndorsmentVO() )){
			policyDataVO.setEndorsmentVO( null );
		}
		return rulesPassed;
	}
	
	
	public static boolean executeConditionCheckForSBSBroker( HttpServletRequest request,PolicyVO policyVO , String action, String actionIdentifier, Integer[] sectionArray, Response responseObj ){
		boolean rulesPassed = true;
		
		boolean rulesEnabled = Utils.toDefaultFalseBoolean( Utils.getSingleValueAppConfig( AppConstants.APP_CONFIG_RULES_ENABLED, "Y" ) );
		
		if( rulesEnabled ){
			ReferralListVO listReferralVO = null;
			try{
				RuleServiceInvoker ruleServiceInvoker = (RuleServiceInvoker)Utils.getBean( com.Constant.CONST_RULESERVICEINVOKER );
				UserProfile userProfile = (UserProfile)request.getSession().getAttribute(AppConstants.SESSION_USER_PROFILE_VO);
				BaseVO output = ruleServiceInvoker.callRuleService( policyVO, sectionArray , userProfile.getRsaUser().getHighestRole() );
				listReferralVO = (ReferralListVO) output;
				if( !Utils.isEmpty( listReferralVO ) && !Utils.isEmpty( listReferralVO.getReferrals() ) ){
					rulesPassed = false;
					responseObj.setData( listReferralVO );
					policyVO.setReferrals( listReferralVO.getReferrals() );

					for( ReferralVO referralVO : listReferralVO.getReferrals() ){
						if( !Utils.isEmpty( referralVO ) ){
							referralVO.setActionIdentifier( actionIdentifier );
							policyVO.setReferral( referralVO );
						}
					}
					/* In case of referrals, set the response type in order to avoid redirection through appconfig.properties
					 * configuration
					 */
					responseObj.setResponseType( Type.JSON );
				}
			}
			catch( BusinessException e ){
				rulesPassed = false;
				listReferralVO = (com.rsaame.pas.vo.bus.ReferralListVO) e.getExceptionData();
				if(null != listReferralVO) {
					responseObj.setData( listReferralVO );
					policyVO.setReferrals( listReferralVO.getReferrals() );

					for( ReferralVO referralVO : listReferralVO.getReferrals() ){
						if( !Utils.isEmpty( referralVO ) ){
							referralVO.setActionIdentifier( actionIdentifier );
							policyVO.setReferral( referralVO );
						}
					}
					/* In case of referrals, set the response type in order to avoid redirection through appconfig.properties
					 * configuration
					 */
					responseObj.setResponseType( Type.JSON );
				}
				else 
					throw new BusinessException( "pas.cmn.rulesException", null, "Error in the rule_6", "Error in the rule_6" );
				
				return rulesPassed;
			}

		}
		
		
		return rulesPassed;
	}
	
	public static void redirectReferralForMonoline( PolicyDataVO policyDataVO, HttpServletRequest request, HttpServletResponse response, Response responseObj, Redirection redirection )
	{		
	if(!Utils.isEmpty(policyDataVO) && !Utils.isEmpty(policyDataVO.getReferralVOList())
			&& !Utils.isEmpty(policyDataVO.getReferralVOList().getReferalType()) )
		{
		if(!policyDataVO.getReferralVOList().getReferalType().equals("Pass"))
			{
			redirection = new Redirection("/jsp/common/referralPopUp.jsp",Redirection.Type.TO_JSP);
			responseObj.setRedirection( redirection );
			request.setAttribute("referralListVO", policyDataVO.getReferralVOList());
			request.setAttribute("LOB", policyDataVO.getCommonVO().getLob());
			request.setAttribute("isConsolidatedReferral",false);
			Map<String, Map<String, String>> referralMsgMap = new HashMap<String, Map<String, String>>();
			for(ReferralVO referralVO : policyDataVO.getReferralVOList().getReferrals()){
				referralMsgMap.putAll(referralVO.getRefDataTextField());
			}
			
			HttpSession session = request.getSession();
			session.setAttribute("ReferralMap", referralMsgMap);
				
			request.setAttribute("referralDetails", MVCUtils.getAsJsonString(referralMsgMap));
		
			}	
		}

	}
}
