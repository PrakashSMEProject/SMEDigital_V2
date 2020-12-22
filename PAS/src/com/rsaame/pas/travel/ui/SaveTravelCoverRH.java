package com.rsaame.pas.travel.ui;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Redirection;
import com.mindtree.ruc.mvc.Redirection.Type;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.cmn.currency.Currency;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.kaizen.vo.PASServiceContext;
import com.rsaame.pas.query.constants.QueryConstants;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.util.SectionRHUtils;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.bus.ReferralListVO;
import com.rsaame.pas.vo.bus.ReferralVO;
import com.rsaame.pas.vo.bus.SchemeVO;
import com.rsaame.pas.vo.bus.TaskVO;
import com.rsaame.pas.vo.bus.TravelInsuranceVO;
import com.rsaame.pas.vo.bus.TravelPackageVO;
import com.rsaame.pas.vo.cmn.CommonVO;

public class SaveTravelCoverRH implements IRequestHandler{

	private final static Logger logger = Logger.getLogger( SaveTravelCoverRH.class );
	private static final String SPACE = " ";

	@Override
	public Response execute( HttpServletRequest request, HttpServletResponse response ){

		Response responseObj = new Response();

		String opType = request.getParameter( "opType" );
		/* Fetch commonVO from PolicyContext.*/
		PolicyContext policyContext = PolicyContextUtil.getPolicyContext( request );
		CommonVO commonVO = policyContext.getCommonDetails();

		/* Map the travel insurance VO from request.*/
		TravelInsuranceVO travelInsuranceDetailsVo = new TravelInsuranceVO();
		travelInsuranceDetailsVo.setCommonVO(commonVO);
		List<TravelPackageVO> packages = new ArrayList<TravelPackageVO>();
		TravelPackageVO travelPackageVO = BeanMapper.map( request, TravelPackageVO.class );

		packages.add( travelPackageVO );
		travelInsuranceDetailsVo.setTravelPackageList( packages );
		
		TaskExecutor.executeTasks( "CHECK_BASIC_COVERS", travelInsuranceDetailsVo );
		PremiumVO premiumVO = new PremiumVO();
		travelInsuranceDetailsVo.setPremiumVO( premiumVO );
		AppUtils.mapPermiumSummary( travelInsuranceDetailsVo, request );

		SchemeVO schemeVO = new SchemeVO();
		schemeVO.setSchemeCode( Integer.parseInt( request.getParameter( "schemeCode" ) ) );
		schemeVO.setTariffCode( Integer.parseInt( request.getParameter( "tariffCode" ) ) );

		/* Save all details.*/
		travelInsuranceDetailsVo.setCommonVO( commonVO );
		travelInsuranceDetailsVo.setScheme( schemeVO );

		String operation = request.getParameter( "operation" );
		
		/*Mapping the required values from request for rules call.*/
		/*if( !commonVO.getIsQuote() ){
			mapEndorsementVOFromRequest( request, travelInsuranceDetailsVo );
		}*/

		DataHolderVO<Object[]> dataHolder = new DataHolderVO<Object[]>();
		if( !Utils.isEmpty( operation ) && operation.equals( AppConstants.POPULATE ) ){
			Object[] inpObjects = { travelInsuranceDetailsVo, true };
			dataHolder.setData( inpObjects );
			travelInsuranceDetailsVo.setPopulateOperation(true);
		}
		else{
			/* Save all details in General Info. Second parameter: True - only call rating service, False - rating service call + save operation */
			Object[] inpObjects = { travelInsuranceDetailsVo, false };
			dataHolder.setData( inpObjects );
		}

		TravelInsuranceVO travelInsuranceVO = null;

		Integer[] sectionArray = AppUtils.getIntegerArray( Utils.getMultiValueAppConfig( AppConstants.TRAVEL_SEC_ID ) );
		
		/*
		 * If referral is present redirect to show referral pop - up
		 */
	//	if( !Utils.isEmpty( operation ) && !operation.equals( AppConstants.POPULATE ) ){
			if( SectionRHUtils.executeReferralTaskForTravel( travelInsuranceDetailsVo, "", "", sectionArray, request ) ){
				String assignTO = null;
				String referalLoc = null;
				String referalComments = null;

				if( !Utils.isEmpty( (Map<String, Map<String, String>>) request.getSession().getAttribute( com.Constant.CONST_REFERRALMAP ) ) || AppUtils.isRefMsgForGenInfo( policyContext ) ){
					if( !Utils.isEmpty( request.getParameter( "assignto" ) ) ){
						assignTO = request.getParameter( "assignto" );
					}
					if( !Utils.isEmpty( request.getParameter( "referalLoc" ) ) ){
						referalLoc = request.getParameter( "referalLoc" );
					}
					if( !Utils.isEmpty( request.getParameter( "referalComments" ) ) ){
						referalComments = request.getParameter( "referalComments" );
					}
					ReferralListVO referralListVO = new ReferralListVO();
					ReferralVO referralVO = new ReferralVO();
					List<ReferralVO> refVOList = new ArrayList<ReferralVO>();
					if( Utils.isEmpty( (Map<String, Map<String, String>>) request.getSession().getAttribute( com.Constant.CONST_REFERRALMAP ) ) ){
						//In case there is referral in general info and no referral in risk page 
						HashMap<String, Map<String, String>> emptyHolder = new HashMap<String, Map<String, String>>();
						emptyHolder.put( null, null );
						referralVO.setRefDataTextField( emptyHolder );
					}
					else{
						referralVO.setRefDataTextField( (Map<String, Map<String, String>>) request.getSession().getAttribute( com.Constant.CONST_REFERRALMAP ) );
					}

					request.getSession().removeAttribute( com.Constant.CONST_REFERRALMAP );
					referralVO.setLocationCode( PASServiceContext.getLocation() );
					refVOList.add( referralVO );
					referralListVO.setReferrals( refVOList );

					travelInsuranceDetailsVo.setPolicyId( travelInsuranceDetailsVo.getCommonVO().getPolicyId() );

					PolicyDataVO policyDataVO = (PolicyDataVO) TaskExecutor.executeTasks( "POLICY_DATAVO_FROM_COMMONVO", commonVO );

					travelInsuranceDetailsVo.setPolicyClassCode( policyDataVO.getPolicyClassCode() );
					travelInsuranceDetailsVo.setPolicyType( policyDataVO.getPolicyType() );
					TaskVO taskVO = AppUtils.populateTaskVO( assignTO, referalLoc, referalComments, travelInsuranceDetailsVo, request, commonVO );
					referralListVO.setTaskVO( taskVO );
					travelInsuranceDetailsVo.setReferralVOList( referralListVO );

					UserProfile userProfile = (UserProfile) request.getSession().getAttribute( AppConstants.SESSION_USER_PROFILE_VO );
					travelInsuranceDetailsVo.setLoggedInUser( userProfile );
				}

				travelInsuranceVO = (TravelInsuranceVO) TaskExecutor.executeTasks( opType, dataHolder );
			}
			else{
			if( Utils.isEmpty( operation ) || ( !Utils.isEmpty( operation ) && !operation.equals( AppConstants.POPULATE ) ) ){
					Redirection redirection = null;
					redirectReferral( travelInsuranceDetailsVo, request, response, responseObj, redirection );

					return responseObj;
				}
			}
	//	}
		
		if(Utils.isEmpty( travelInsuranceVO )){
			travelInsuranceVO = travelInsuranceDetailsVo;
		}
		if( !Utils.isEmpty( operation ) && operation.equals( AppConstants.POPULATE ) ){
			populatePremium( request, response, responseObj, travelInsuranceVO );
		} else  if(!Utils.isEmpty(operation) && operation.equals( AppConstants.APPROVE_REF )){
			String message = "";
			responseObj.setResponseType( Response.Type.JSON );
			response.setHeader( "isApprove", "true" );
			
			if(commonVO.getIsQuote()){
				message="pas.approveQuoteSuccessful";
				//commonVO.setAppFlow( Flow.VIEW_QUO );
			} else {
				 message="pas.approvePolicySuccessful";
				//commonVO.setAppFlow( Flow.VIEW_POL );
			}
			

		}
		else if( !Utils.isEmpty( operation ) && operation.equals( AppConstants.APPROVE_QUO ) ){
			String message = "";
			responseObj.setResponseType( Response.Type.JSON );
			response.setHeader( "isApprove", "true" );
			if(commonVO.getIsQuote()){
				message="pas.approveQuoteSuccessful";
				//commonVO.setAppFlow( Flow.VIEW_QUO );
			} else {
				 message="pas.approvePolicySuccessful";
				//commonVO.setAppFlow( Flow.VIEW_POL );
			}

			commonVO.setStatus( AppConstants.QUOTE_ACCEPT );
			AppUtils.addErrorMessage( request, message );
		}
		else{
			if(!commonVO.getAppFlow().equals( Flow.AMEND_POL ) && !commonVO.getAppFlow().equals( Flow.RESOLVE_REFERAL ))
				commonVO.setStatus( AppConstants.QUOTE_ACTIVE );
			if( commonVO.getIsQuote() ){
				commonVO.setAppFlow( Flow.VIEW_QUO );
			}
			else if(!commonVO.getAppFlow().equals( Flow.AMEND_POL ) && !commonVO.getAppFlow().equals( Flow.RESOLVE_REFERAL )){
				commonVO.setAppFlow( Flow.VIEW_POL );
			}
			
		}

		if(  !Utils.isEmpty( operation ) && ( operation.equals( AppConstants.APPROVE_QUO ) || operation.equals( AppConstants.APPROVE_REF ) ) ){
			travelInsuranceVO.getPremiumVO().setPremiumAmt( travelInsuranceVO.getSelectedPackage().getPremiumAmt() );
			AppUtils.sendMail( (PolicyDataVO)travelInsuranceVO , "APPROVE" );
		}
		return responseObj;
	}

	/**
	 * Method will populate the premium back to the response
	 * 
	 * @param request
	 * @param response
	 * @param responseObj
	 * @param travelInsuranceVO
	 */
	private void populatePremium( HttpServletRequest request, HttpServletResponse response, Response responseObj, TravelInsuranceVO travelInsuranceVO ){
		List<TravelPackageVO> travelPackageList = travelInsuranceVO.getTravelPackageList();

		TravelPackageVO travelPackageVO = null;
		CommonVO commonVO = travelInsuranceVO.getCommonVO();
		String premium = null;

		// Added >=0 to avoid >-1 to fix sonar violation
	//	if( !Utils.isEmpty( travelPackageList ) && travelPackageList.size() >-1 ){
		if( !Utils.isEmpty( travelPackageList ) && travelPackageList.size() >=0 ){
			for( TravelPackageVO packageVO : travelPackageList ){
				if( packageVO.getIsSelected() ){
					travelPackageVO = packageVO;
				}
			}
		}
		//Setting Policy Fees for VASCO Scheme
		String schemeCode = Utils.getSingleValueAppConfig( "VASCO_SCH_CODE" );
		double VascoPolFeesPerc = 0.0;
		double VascoPolFees = 0.0;
		Boolean percentCheck = true;
		boolean vascoCheck = false;
		if( !Utils.isEmpty( travelInsuranceVO ) && !Utils.isEmpty( travelInsuranceVO.getScheme() ) && !Utils.isEmpty( travelInsuranceVO.getScheme().getSchemeCode() ) && schemeCode.equals( travelInsuranceVO.getScheme().getSchemeCode().toString() ) ){
			java.util.List<Object[]> resultSet = DAOUtils.getSqlResultForPas( QueryConstants.FETCH_T_MAS_GROUP_UP, schemeCode );
			if( !Utils.isEmpty( resultSet ) && resultSet.size() > 0 ){
				for( Object[] result : resultSet ){
			
				
				String pOrF = (String) result[0];
				if(pOrF.equals("P") ){
					percentCheck = true;
				}
				else{
					percentCheck = false;
				}
				VascoPolFeesPerc = ( (BigDecimal) result[1] ).doubleValue();
				
				if(!Utils.isEmpty( commonVO ) && commonVO.getIsQuote()){
					if(percentCheck){
						VascoPolFees = travelPackageVO.getPremiumAmt() * VascoPolFeesPerc / 100;
					}
					else{
						VascoPolFees = VascoPolFeesPerc;
					}
					vascoCheck = true;
					travelPackageVO.setPolicyFees(VascoPolFees);
					
					travelInsuranceVO.getPremiumVO().setPolicyFees(VascoPolFees);
				}
				}
			}
		}
		if( !Utils.isEmpty( travelPackageVO ) ){
			premium = travelPackageVO.getTariffCode() + "~" + Currency.getUnitName() + SPACE + Currency.getFormattedCurrency( travelPackageVO.getPremiumAmtActual(),travelInsuranceVO.getCommonVO().getLob().name() ) + "~"
					+ Currency.getFormattedCurrency( travelPackageVO.getPremiumAmt(),travelInsuranceVO.getCommonVO().getLob().name() ) + "~" + Currency.getFormattedCurrency( travelPackageVO.getPremiumAmtActual(),travelInsuranceVO.getCommonVO().getLob().name() );
		}

		//populating refund/payable amount
		TravelInsuranceVO actualTravelInsuranceVO = (TravelInsuranceVO) TaskExecutor.executeTasks( "TRAVEL_PACKAGE_PREMIUM", travelInsuranceVO );
		if( !Utils.isEmpty( actualTravelInsuranceVO ) ){
			//This will have saved premium
			Double premActual = actualTravelInsuranceVO.getPremiumVO().getPremiumAmt();
			if(!Utils.isEmpty( commonVO ) && commonVO.getIsQuote()){
				if(percentCheck){
					VascoPolFees = premActual * VascoPolFeesPerc / 100;
				}
				else{
					VascoPolFees = VascoPolFeesPerc;
				}
			}else {
				VascoPolFees = travelInsuranceVO.getPremiumVO().getPolicyFees();
			}
			premActual+= VascoPolFees;
			Double payablePrem = travelPackageVO.getPremiumAmt() + VascoPolFees - premActual;
		
			request.setAttribute( "payablePremium", payablePrem );
			premium += "~amount" + "~" + Currency.getFormattedCurrency( payablePrem,travelInsuranceVO.getCommonVO().getLob().name() );
			premium +="~policyFees" + "~" + Currency.getFormattedCurrency( travelPackageVO.getPolicyFees(),travelInsuranceVO.getCommonVO().getLob().name() );
		}

		if( !Utils.isEmpty( premium ) ){
			response.setHeader( "premiumDetail", premium );
		}

	}

	/**
	 * Method to redirect to referral
	 * 
	 * @param homeInsuranceVO
	 * @param request
	 * @param response
	 * @param responseObj
	 * @param redirection
	 */
	private void redirectReferral( TravelInsuranceVO travelInsuranceVO, HttpServletRequest request, HttpServletResponse response, Response responseObj, Redirection redirection ){
		List<String> messageList = new ArrayList<String>();
		ReferralListVO refListVO = travelInsuranceVO.getReferralVOList();
		if( !Utils.isEmpty( refListVO ) ){
			for( ReferralVO refVO : refListVO.getReferrals() ){
				for( String refText : refVO.getReferralText() ){
					String message = new String( refText );
					messageList.add( message );
				}
			}
		}

		String nextaction = request.getParameter( "onClickAction" );
		if( !Utils.isEmpty( travelInsuranceVO.getReferralVOList() ) && !Utils.isEmpty( travelInsuranceVO.getReferralVOList().getReferrals() )
				&& !Utils.isEmpty( travelInsuranceVO.getReferralVOList().getReferrals().size() > 0 )
				&& !Utils.isEmpty( travelInsuranceVO.getReferralVOList().getReferrals().get( 0 ).getRefDataTextField() ) ){
			HttpSession session = request.getSession();
			//Holding the referral messages map in session
			session.setAttribute( com.Constant.CONST_REFERRALMAP, travelInsuranceVO.getReferralVOList().getReferrals().get( 0 ).getRefDataTextField() );
		}
		request.setAttribute( "nextAction", nextaction );
		request.setAttribute( "referralListVO", refListVO );
		response.setHeader( "isRef", "true" );
		redirection = new Redirection( "/jsp/quote/consolidatedReferralCommon.jsp", Type.TO_JSP );
		responseObj.setRedirection( redirection );

	}
	
	/* @param request
	 * @param travelInsuranceDetailsVo
	 */
	/*private void mapEndorsementVOFromRequest( HttpServletRequest request, TravelInsuranceVO travelInsuranceDetailsVo ){
		
		String oldPremiumValue = request.getParameter( "oldPremiumValue" ).replaceAll( "[,]", "" );
		String newPremiumValue = request.getParameter( "newPremiumValue" ).replaceAll( "[,]", "" );
		
		if( !Utils.isEmpty( oldPremiumValue ) && !Utils.isEmpty( newPremiumValue )){

			com.mindtree.ruc.cmn.utils.List<EndorsmentVO> endorsementVOs = new com.mindtree.ruc.cmn.utils.List<EndorsmentVO>( EndorsmentVO.class );
			PremiumVO oldPremiumVO = new PremiumVO();
			PremiumVO newPremiumVO = new PremiumVO();
			EndorsmentVO endorsmentVO = new EndorsmentVO();
			
			oldPremiumVO.setPremiumAmt( Double.valueOf( oldPremiumValue ) );
			newPremiumVO.setPremiumAmt( Double.valueOf( newPremiumValue ) );
			endorsmentVO.setOldPremiumVO( oldPremiumVO );
			endorsmentVO.setPremiumVO( newPremiumVO );
			
			endorsementVOs.add( endorsmentVO );
			travelInsuranceDetailsVo.setEndorsmentVO( endorsementVOs );
		}
		
	}*/
}
