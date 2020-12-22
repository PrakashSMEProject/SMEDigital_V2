/**
 * 
 */
package com.rsaame.pas.homeInsurance.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.beanmap.BeanMapperFactory;
import com.mindtree.ruc.cmn.exception.BusinessException;
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
import com.rsaame.pas.kaizen.vo.PASServiceContext;
import com.rsaame.pas.request.vo.mapper.RequestToUWQVOMapper;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.util.SectionRHUtils;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.bus.CoverDetails;
import com.rsaame.pas.vo.bus.CoverDetailsVO;
import com.rsaame.pas.vo.bus.HomeInsuranceVO;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.ReferralListVO;
import com.rsaame.pas.vo.bus.ReferralVO;
import com.rsaame.pas.vo.bus.StaffDetailsVO;
import com.rsaame.pas.vo.bus.TaskVO;
import com.rsaame.pas.vo.bus.UWQuestionsVO;
import com.rsaame.pas.vo.cmn.CommonVO;

/**
 * @author M1021201
 * 
 */
public class HomeInsuranceSaveRH implements IRequestHandler{

	private final static Logger LOGGER = Logger.getLogger( HomeInsuranceSaveRH.class );
	
	@SuppressWarnings( "unchecked" )
	@Override
	public Response execute( HttpServletRequest request, HttpServletResponse response ){

		LOGGER.info( "Start saving home insurance details." );
		Response responseObj = null;
		PolicyContext policyContext = null;
		CoverDetails coverDetails = null;
		HomeInsuranceVO homeInsuranceVO = null;
		
		try{
			coverDetails = new CoverDetails();
			responseObj = new Response();
			coverDetails = new CoverDetails();
			homeInsuranceVO = new HomeInsuranceVO();
			String operation = request.getParameter( "operation" );
			
			/* Fetch commonVO from PolicyContext. */
			policyContext = PolicyContextUtil.getPolicyContext( request );
			CommonVO commonVO = policyContext.getCommonDetails();
			coverDetails = policyContext.getCoverDetails();
			
			List<StaffDetailsVO> staffDetailsVO = policyContext.getStaffDetailsVO();
			
			homeInsuranceVO = BeanMapper.map( request, HomeInsuranceVO.class );
			if( !Utils.isEmpty( coverDetails ) && !Utils.isEmpty( coverDetails.getCoverDetails() ) ){
				homeInsuranceVO.getCovers().addAll( coverDetails.getCoverDetails() );
			}
			
			if( !Utils.isEmpty( staffDetailsVO ) ){
				homeInsuranceVO.getStaffDetails().addAll( staffDetailsVO );
			}
			

			/* Map the UWQuestionsVO from request */
			BaseBeanToBeanMapper<HttpServletRequest, UWQuestionsVO> requestToUWQuestionsMapper = BeanMapperFactory.getMapperInstance( RequestToUWQVOMapper.class );
			UWQuestionsVO uwQuestionsVO = null;
			uwQuestionsVO = requestToUWQuestionsMapper.mapBean( request, uwQuestionsVO );
			homeInsuranceVO.setUwQuestions( uwQuestionsVO );

			LOGGER.info( "Request to homeIsnuranceVO mapping success" );

			homeInsuranceVO.setCommonVO( commonVO );
			LOGGER.info("######## before AppFlow ="+commonVO.getAppFlow());
			buildObjectForSave( homeInsuranceVO, operation );
			LOGGER.info("######## after AppFlow ="+commonVO.getAppFlow());
			// TaskExecutor.executeTasks( "VALIDATE_HOME_RISK", homeInsuranceVO );

			/*
			 * checking operation need to be done, if 'POPULATE', save operation
			 * should not be done
			 */
			
			DataHolderVO<Object[]> dataHolder = new DataHolderVO<Object[]>();
			Redirection redirection = null;
			LOGGER.info("Operation value = "+operation);
			if( !Utils.isEmpty( operation ) && operation.equals( AppConstants.POPULATE ) ){
				homeInsuranceVO.setPopulateOperation( true );
				if( SectionRHUtils.executeReferralTaskHome( homeInsuranceVO, "", "Home Risk Cover", request,false ) ){
					doPopulatePremium( request, response, responseObj, homeInsuranceVO );
				}
				else{
					
					redirectReferral(homeInsuranceVO,request,response,responseObj,redirection);
					
					return responseObj;
				}

			} 
			else{
				
			/* Save all details in General Info. Second parameter: True - only call rating service, False - rating service call + save operation */
				
				request.getSession(false).removeAttribute( com.Constant.CONST_DISPLAYEDREFERRAL );
				
				
				LOGGER.info("Enters Referral Task for HOME ... ");
				if( SectionRHUtils.executeReferralTaskHome( homeInsuranceVO, "", "Home Risk Cover", request, true )){
					/*if(SectionRHUtils.executeReferralTaskValidation( homeInsuranceVO, "", "", new Integer[]{ new Integer(497) }, request )){*/
						Object[] inpObjects = { homeInsuranceVO, false };
						dataHolder.setData( inpObjects );
						
						String assignTO = null;
						String referalLoc = null;
						String referalComments = null;
						if( !Utils.isEmpty( (Map<String, Map<String, String>>) request.getSession().getAttribute( com.Constant.CONST_REFERRALMAP ) ) 
								|| AppUtils.isRefMsgForGenInfo(policyContext)){
							if( !Utils.isEmpty( request.getParameter( "assignto" ) ) ){
								assignTO = request.getParameter( "assignto" );
							}
	
							referalLoc = PASServiceContext.getLocation();
							if( !Utils.isEmpty( request.getParameter( "referalComments" ) ) ){
								referalComments = request.getParameter( "referalComments" );
							}
							ReferralListVO referralListVO = new ReferralListVO();
							ReferralVO referralVO = new ReferralVO();
							List<ReferralVO> refVOList = new ArrayList<ReferralVO>();
							if(Utils.isEmpty( (Map<String, Map<String, String>>) request.getSession().getAttribute( com.Constant.CONST_REFERRALMAP ) )){
								//In case there is referral in general info and no referral in risk page 
								HashMap<String, Map<String, String>> emptyHolder =  new HashMap<String, Map<String, String>>();
								emptyHolder.put( null, null );
								referralVO.setRefDataTextField(emptyHolder );
							}else{
								referralVO.setRefDataTextField( (Map<String, Map<String, String>>) request.getSession().getAttribute( com.Constant.CONST_REFERRALMAP ) );
							}
							
							
							
							request.getSession().removeAttribute( com.Constant.CONST_REFERRALMAP );
							referralVO.setLocationCode( PASServiceContext.getLocation() );
							refVOList.add( referralVO );
							referralListVO.setReferrals( refVOList );
							
							homeInsuranceVO.setPolicyId( homeInsuranceVO.getCommonVO().getPolicyId() );
							PolicyDataVO policyDataVO = (PolicyDataVO) TaskExecutor.executeTasks( "POLICY_DATAVO_FROM_COMMONVO", commonVO );
	
							
							homeInsuranceVO.setPolicyClassCode( policyDataVO.getPolicyClassCode() );
							homeInsuranceVO.setPolicyType( policyDataVO.getPolicyType() );
							TaskVO taskVO = AppUtils.populateTaskVO( assignTO, referalLoc, referalComments, homeInsuranceVO,request,commonVO );
							
							referralListVO.setTaskVO( taskVO );
							homeInsuranceVO.setReferralVOList( referralListVO );
							
							UserProfile userProfile = (UserProfile) request.getSession().getAttribute( AppConstants.SESSION_USER_PROFILE_VO );
							homeInsuranceVO.setLoggedInUser( userProfile );
						}
						LOGGER.info("After ReferralMap : Operation value = "+operation);
						if(!Utils.isEmpty(operation) && operation.equals( AppConstants.APPROVE_QUO )){
							TaskExecutor.executeTasks( "HOME_INSURANCE_APPROVE_QUO", dataHolder );
							String message = "";
							if(commonVO.getIsQuote()){
								message="pas.approveQuoteSuccessful";
							} else {
								 message="pas.approvePolicySuccessful";
							}
							commonVO.setStatus( AppConstants.QUOTE_ACCEPT );
							response.setHeader( "isApprove", "true" );
							responseObj.setResponseType( Response.Type.JSON );
							AppUtils.addErrorMessage( request, message );
							
							AppUtils.sendMail( (PolicyDataVO)homeInsuranceVO, "APPROVE" );
							
							/* send mail when the referral related to broker credit limit is approved */
							if( !Utils.isEmpty( homeInsuranceVO.getCommonVO() ) && !Utils.isEmpty( homeInsuranceVO.getCommonVO().getTaskDetails() ) 
									&& !Utils.isEmpty( homeInsuranceVO.getCommonVO().getTaskDetails().getDesc() )
									&& homeInsuranceVO.getCommonVO().getTaskDetails().getDesc().contains( Utils.getSingleValueAppConfig( "BROKER_CREDIT_LIMIT_APPROVAL" ) ) ){
								homeInsuranceVO.setLoggedInUser( (UserProfile)request.getSession().getAttribute(AppConstants.SESSION_USER_PROFILE_VO) );
								 
								AppUtils.sendCreditLimitMail( homeInsuranceVO, "APPROVE_CREDIT_LIMIT" , request );
							}
							
							return responseObj;
						} else {
							LOGGER.debug("**********App Flow = "+commonVO.getAppFlow());
							
							if(Flow.VIEW_QUO.equals(commonVO.getAppFlow())){
								dataHolder = (DataHolderVO<Object[]>) TaskExecutor.executeTasks( "HOME_RENEWAL_REFERRAL_SAVE", dataHolder );
							}
							else{
								dataHolder = (DataHolderVO<Object[]>) TaskExecutor.executeTasks( "HOME_INSURANCE_SAVE", dataHolder );
							}
						}
	
						homeInsuranceVO = (HomeInsuranceVO) dataHolder.getData()[ 0 ];
						
						Map<String, Map<String, String>> referralMessage =  (Map<String, Map<String, String>>) request.getSession().getAttribute( com.Constant.CONST_REFERRALMESSAGE ) ;
						
						request.getSession().removeAttribute( com.Constant.CONST_REFERRALMESSAGE ) ;
						
						/* Send mail when the broker credit limit exceeds 100%*/
						if(!Utils.isEmpty( referralMessage ) && referralMessage.containsKey( "brokerMaxCreditLimit" )){
							homeInsuranceVO.setLoggedInUser( (UserProfile)request.getSession().getAttribute(AppConstants.SESSION_USER_PROFILE_VO) );
							AppUtils.sendCreditLimitMail( homeInsuranceVO, "REFERRAL_CREDIT_LIMIT" , request );
						}
						
						
						/* Send mail when the broker credit limit exceeds 85%*/
						String sendMail = request.getParameter( "sendMail" );
						
						if(!Utils.isEmpty( sendMail )){
							if(!Utils.isEmpty( referralMessage ) && referralMessage.containsKey( "brokerMinCreditLimit" )){
								homeInsuranceVO.setLoggedInUser( (UserProfile)request.getSession().getAttribute(AppConstants.SESSION_USER_PROFILE_VO) );
								AppUtils.sendCreditLimitMail( homeInsuranceVO, "MESSAGE_CREDIT_LIMIT", request );
							}
						}
						
						//saveReferralMessage( homeInsuranceVO, request );
						LOGGER.info( "Save homeIsnuranceVO success" );
						AppUtils.addErrorMessage( request, "pas.saveSuccessful" );
						responseObj.setResponseType( Response.Type.JSON );
						responseObj.setSuccess( true );
						String result = "Success";
						responseObj.setData( result );
						response.setHeader( "isJSON", "true" );
						if(!commonVO.getAppFlow().equals( Flow.AMEND_POL ) && !commonVO.getAppFlow().equals( Flow.RESOLVE_REFERAL ))
							commonVO.setStatus( AppConstants.QUOTE_ACTIVE );
						if( commonVO.getIsQuote() ){
							commonVO.setAppFlow( Flow.VIEW_QUO );
						}
						else if(!commonVO.getAppFlow().equals( Flow.AMEND_POL ) && !commonVO.getAppFlow().equals( Flow.RESOLVE_REFERAL )){
							commonVO.setAppFlow( Flow.VIEW_POL );
						}
					/*}*/
				} else {
					
					/*
					 * Check if referral to be shown or only the the pop-up message. If it is only the pop-up then isMessage in ReferralVO will be set to true.
					 */
					boolean isMessage = false;
					if( !Utils.isEmpty( homeInsuranceVO.getReferralVOList() ) && !Utils.isEmpty( homeInsuranceVO.getReferralVOList().getReferrals() )
							&& !Utils.isEmpty( homeInsuranceVO.getReferralVOList().getReferrals().size() > 0 )
							&& !Utils.isEmpty( homeInsuranceVO.getReferralVOList().getReferrals().get( 0 ).getRefDataTextField() ) ){
						
						isMessage = homeInsuranceVO.getReferralVOList().getReferrals().get( 0 ).isMessage();
					}
				
					if(isMessage){
						request.setAttribute( "displayMessage", homeInsuranceVO.getReferralVOList().getReferrals().get( 0 ).getReferralText().get(0) );
						request.getSession(false).setAttribute( com.Constant.CONST_REFERRALMESSAGE, homeInsuranceVO.getReferralVOList().getReferrals().get( 0 ).getRefDataTextField() );
						request.setAttribute( "isEndt", true );
						response.setHeader( "isRef", "true" );
						
						if(!Utils.isEmpty(operation) && operation.equals( AppConstants.APPROVE_QUO )){
							request.setAttribute( "flow", "approve_ref" );
						}
						redirection = new Redirection( "/jsp/referalAlert.jsp", Type.TO_JSP );
						responseObj.setRedirection( redirection );
					}else{
						redirectReferral(homeInsuranceVO,request,response,responseObj,redirection);
					}
					
					

					return responseObj;
					
				}
				
					
				
			}
		}
		catch( BusinessException be ){
			throw new BusinessException( be.getErrorKeysList().get( 0 ), be.getCause(), be.getMessage() );
		}
		catch( Exception exp ){
			exp.printStackTrace();
		}
		LOGGER.info( "Exiting from saving home insurance details." );
		return responseObj;
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
	private void redirectReferral( HomeInsuranceVO homeInsuranceVO, HttpServletRequest request, HttpServletResponse response, Response responseObj, Redirection redirection ){
		LOGGER.info("Entered HomeInsuranceSaveRH.redirectReferral() method");
		@SuppressWarnings( "unchecked" )
		Map<String,Map<String, String>> displayedReferral = (Map<String,Map<String, String>>)request.getSession().getAttribute( com.Constant.CONST_DISPLAYEDREFERRAL );
		Map<String,Map<String, String>> ruleReferral = null;
		
		if( !Utils.isEmpty( homeInsuranceVO.getReferralVOList() ) && !Utils.isEmpty( homeInsuranceVO.getReferralVOList().getReferrals() )
						&& !Utils.isEmpty( homeInsuranceVO.getReferralVOList().getReferrals().size() > 0 )
						&& !Utils.isEmpty( homeInsuranceVO.getReferralVOList().getReferrals().get( 0 ).getRefDataTextField() ) ){
			ruleReferral = homeInsuranceVO.getReferralVOList().getReferrals().get( 0 ).getRefDataTextField();
		}
		
		boolean popUpDisplay = true;
		
		List<String> removeReferralList = new ArrayList<String>(0);
		
		/* To filter already shown referrals */
		if( !Utils.isEmpty( displayedReferral ) && !Utils.isEmpty( ruleReferral )){
			for( String displayedReferralFieldName : displayedReferral.keySet() ){
				Map<String, String> displayedReferralValues = displayedReferral.get( displayedReferralFieldName );

				for( String fieldName : ruleReferral.keySet() ){
					Map<String, String> referralValues = ruleReferral.get( fieldName );

					for( String displayedValue : displayedReferralValues.keySet() ){
						for( String referralValue : referralValues.keySet() ){
							
							if( displayedReferralFieldName.equalsIgnoreCase( fieldName ) && displayedValue.equalsIgnoreCase( referralValue ) ){
								removeReferralList.add( fieldName );
							}
						}
					}
				}
			}
			
			for( String fieldName : ruleReferral.keySet() ){
				
				if( !removeReferralList.contains( fieldName ) ){
					popUpDisplay = true;
					break;
				}else{
					popUpDisplay = false;
				}
			}
		}
		
		if(popUpDisplay){
			List<String> messageList = new ArrayList<String>();
			ReferralListVO refListVO = homeInsuranceVO.getReferralVOList();
			if( !Utils.isEmpty( refListVO ) ){
				for( ReferralVO refVO : refListVO.getReferrals() ){
					for( String refText : refVO.getReferralText() ){
						String message = new String( refText );
						messageList.add( message );
					}
				}
			}
	
			String nextaction = request.getParameter( "onClickAction" );
			if( !Utils.isEmpty( homeInsuranceVO.getReferralVOList() ) && !Utils.isEmpty( homeInsuranceVO.getReferralVOList().getReferrals() )
					&& !Utils.isEmpty( homeInsuranceVO.getReferralVOList().getReferrals().size() > 0 )
					&& !Utils.isEmpty( homeInsuranceVO.getReferralVOList().getReferrals().get( 0 ).getRefDataTextField() ) ){
				HttpSession session = request.getSession();
				//Holding the referral messages map in session
				session.setAttribute( com.Constant.CONST_REFERRALMAP, homeInsuranceVO.getReferralVOList().getReferrals().get( 0 ).getRefDataTextField() );
				session.setAttribute( com.Constant.CONST_REFERRALMESSAGE, homeInsuranceVO.getReferralVOList().getReferrals().get( 0 ).getRefDataTextField() );
				request.getSession( false ).removeAttribute( com.Constant.CONST_DISPLAYEDREFERRAL );
			}
			request.setAttribute( "nextAction", nextaction );
			request.setAttribute( "referralListVO", refListVO );
			response.setHeader( "isRef", "true" );
			redirection = new Redirection( "/jsp/quote/consolidatedReferralCommon.jsp", Type.TO_JSP );
			responseObj.setRedirection( redirection );
		}
		LOGGER.info("Exiting from HomeInsuranceSaveRH.redirectReferral() method");
	}	
	

	/**
	 * @param request
	 * @param response
	 * @param responseObj
	 * This method is executed on change of value of SI in home risk
	 * page
	 * @param homeInsuranceVO
	 */
	@SuppressWarnings( "unchecked" )
	private void doPopulatePremium( HttpServletRequest request, HttpServletResponse response, Response responseObj, HomeInsuranceVO homeInsuranceVO ){
		LOGGER.info("Entered HomeInsuranceSaveRH.doPopulatePremium() method");
		LOGGER.info( "Fields need to be populated " + request.getParameter( "CommonValueFieldNames" ) );
		HomeInsuranceVO actualHomeInsuranceVO = null;
		Double premCalculated = 0.0;
		/* sSecond parameter: True - only call rating service, False - rating service call + save operation */
		DataHolderVO<Object[]> dataHolder = new DataHolderVO<Object[]>();
		Object[] inpObjects = { homeInsuranceVO, true };
		dataHolder.setData( inpObjects );
		dataHolder = (DataHolderVO<Object[]>) TaskExecutor.executeTasks( "HOME_INSURANCE_SAVE", dataHolder );
		homeInsuranceVO = (HomeInsuranceVO) dataHolder.getData()[ 0 ];

		// Getting the fieldNames that contains the premium, totalPremium,
		// riskType and coverCode
		String totalPremiumFieldName = request.getParameter( "PremiumFieldName" );
		String[] premiumFieldNames = request.getParameterValues( "CommonValueFieldNames" );
		String[] riskTypeFieldNames = request.getParameterValues( "riskType" );
		String[] coverCodeFieldNames = request.getParameterValues( "coverCode" );
		String amount = request.getParameter( "Amount" );

		short coverCode = 0;
		Integer riskType = null;

		if( !Utils.isEmpty( homeInsuranceVO ) ){

			String premiumDetail = "";

			// populating total premium
			if( !Utils.isEmpty( homeInsuranceVO.getPremiumVO() ) ){
				premiumDetail += totalPremiumFieldName + "~" + Currency.getFormattedCurrency( homeInsuranceVO.getPremiumVO().getPremiumAmt(),homeInsuranceVO.getCommonVO().getLob().toString() ) + "|";
				//premiumDetail += totalPremiumFieldName + "~" + Currency.getFormattedCurrency( homeInsuranceVO.getPremiumVO().getPremiumAmt(),homeInsuranceVO.getCommonVO().getLob().toString());
				premCalculated = homeInsuranceVO.getPremiumVO().getPremiumAmt();
			}

			boolean coverFound = false;
			// populating premium amount for cover
			for( int item = 0; item < premiumFieldNames.length; item++ ){
				coverCode = Short.valueOf( request.getParameter( coverCodeFieldNames[ item ] ) );
				riskType = Integer.valueOf( request.getParameter( riskTypeFieldNames[ item ] ) );
				coverFound = false;

				// populating premium amount for building
				if( coverCode == SvcConstants.DEFAULT_HOME_COVER_CODE && SvcConstants.BUILDING_RISK_TYPE_CODE.equals( riskType ) ){
					if( !Utils.isEmpty( homeInsuranceVO.getBuildingDetails().getSumInsured() )
							&& !Utils.isEmpty( homeInsuranceVO.getBuildingDetails().getSumInsured().getSumInsured() )
							&& homeInsuranceVO.getBuildingDetails().getSumInsured().getSumInsured() > 0){
						premiumDetail += premiumFieldNames[ item ] + "~" + Currency.getFormattedCurrency( homeInsuranceVO.getBuildingDetails().getPremiumAmt(),homeInsuranceVO.getCommonVO().getLob().toString() ) + "|";
						coverFound = true;
					}
				}
				// populating premium amount for covers
				else{
					for( CoverDetailsVO coverDetailsVO : homeInsuranceVO.getCovers() ){
						if( !Utils.isEmpty( coverDetailsVO.getRiskCodes() )
								&& coverCode == coverDetailsVO.getCoverCodes().getCovCode()
								&& !Utils.isEmpty( coverDetailsVO.getRiskCodes() )
								&& !Utils.isEmpty( coverDetailsVO.getRiskCodes().getRiskType() )
								&& coverDetailsVO.getRiskCodes().getRiskType().equals( riskType )
								&& !Utils.isEmpty( coverDetailsVO.getRiskCodes().getRiskCat() )
								&& ( coverDetailsVO.getRiskCodes().getRiskCat().equals( SvcConstants.DEFAULT_RISK_CATEGORY )
										|| coverDetailsVO.getRiskCodes().getRiskCat().equals( SvcConstants.CONTENT_MAIN_RISK_CATEGORY ) 
										|| coverDetailsVO.getRiskCodes().getRiskCat().equals( SvcConstants.PP_MAIN_RISK_CATEGORY ) ) 
								&& !Utils.isEmpty( coverDetailsVO.getSumInsured() )
								&& !Utils.isEmpty( coverDetailsVO.getSumInsured().getSumInsured() )
								&& coverDetailsVO.getSumInsured().getSumInsured() > 0){
								premiumDetail += premiumFieldNames[ item ]
									+ "~"
									+ Currency.getFormattedCurrency( coverDetailsVO.getPremiumAmt()
											+ getHomeSubContentRate( homeInsuranceVO, coverDetailsVO.getRiskCodes().getRiskType() ),homeInsuranceVO.getCommonVO().getLob().toString() ) + "|";
							coverFound = true;
						}
					}
				}

				if( !coverFound ){
					premiumDetail += premiumFieldNames[ item ] + "~" + Currency.getFormattedCurrency( 0.00,homeInsuranceVO.getCommonVO().getLob().toString() ) + "|";
				}
			}

			//populating refund/payable amount
			actualHomeInsuranceVO = (HomeInsuranceVO) TaskExecutor.executeTasks( "HOME_INSURANCE_LOAD", homeInsuranceVO.getCommonVO() );
			if( !Utils.isEmpty( actualHomeInsuranceVO ) ){
				//This will have saved premium
				Double premActual = actualHomeInsuranceVO.getPremiumVO().getPremiumAmt();
				Double payablePrem = premCalculated - premActual;
				request.setAttribute( "payablePremium", payablePrem );
				premiumDetail += amount + "~" + Currency.getFormattedCurrency( payablePrem,homeInsuranceVO.getCommonVO().getLob().toString() ) + "|";
			}
			
			/* This is done to calculate the prorated premium without cover level discount */
			if(!Utils.isEmpty( homeInsuranceVO.getPremiumVO().getActualProratedPremium() )){
				premiumDetail += "actualProratedPremium"+"~"+Currency.getUnformttedScaledCurrency(  homeInsuranceVO.getPremiumVO().getActualProratedPremium() ,homeInsuranceVO.getCommonVO().getLob().toString() )+"|";
			}
			
			if(!Utils.isEmpty( homeInsuranceVO.getPremiumVO().getMinPremiumApplied() )){
				premiumDetail += "minPremiumApplied"+"~"+Currency.getUnformttedScaledCurrency(  homeInsuranceVO.getPremiumVO().getMinPremiumApplied().doubleValue() ,homeInsuranceVO.getCommonVO().getLob().toString() )+"|";
			}else{
				premiumDetail += "minPremiumApplied"+"~"+0+"|";
			}
			
			if( premiumDetail.length() > 1 ){
				response.setHeader( "premiumDetailHeader", premiumDetail.substring( 0, premiumDetail.length() - 1 ) );
			}
			LOGGER.info( "Premium Sequence Generated : " + premiumDetail );
		}
		LOGGER.info("Exiting from HomeInsuranceSaveRH.doPopulatePremium() method");
	}
	
	private double getHomeSubContentRate( HomeInsuranceVO homeInsuranceVO, Integer rskType ){
		double totalScSi = 0.0;
		for( CoverDetailsVO coverDetailsVO : homeInsuranceVO.getCovers() ){

			if( !Utils.isEmpty( coverDetailsVO.getRiskCodes().getRiskType() ) && ( coverDetailsVO.getCoverCodes().getCovCode() != 0 ) ){
				if( ( rskType ).equals( coverDetailsVO.getRiskCodes().getRiskType() )
						&& ( SvcConstants.DEFAULT_HOME_COVER_CODE == coverDetailsVO.getCoverCodes().getCovCode() )
						&& ( SvcConstants.CONTENT_SUB_RISK_CATEGORY.equals( coverDetailsVO.getRiskCodes().getRiskCat() ) && SvcConstants.PP_SUB_RISK_CATEGORY
								.equals( coverDetailsVO.getRiskCodes().getRiskCat() ) ) ){
					if( !Utils.isEmpty( coverDetailsVO.getSumInsured().getSumInsured() ) ){
						//factor.setFactorValue( String.valueOf( coverDetailsVO.getSumInsured().getSumInsured().longValue() ) );
						totalScSi += coverDetailsVO.getPremiumAmt();
					}
				}
			}
		}
		
		return totalScSi;
	}

	/**
	 * Cleans object by removing unexpected elements
	 * 
	 * @param homeInsuranceVO
	 * @return void
	 */
	private void buildObjectForSave( HomeInsuranceVO homeInsuranceVO, String operation ){
		LOGGER.info("Entered HomeInsuranceSaveRH.buildObjectForSave() method");
		HomeInsuranceVO object = homeInsuranceVO;
		Map<String, CoverDetailsVO> coverMap = new HashMap<String, CoverDetailsVO>();
		List<CoverDetailsVO> subItemMap = new ArrayList<CoverDetailsVO>();
		List<Integer> riskTypeList = new ArrayList<Integer>();
		Iterator<CoverDetailsVO> iter = object.getCovers().iterator();

		while( iter.hasNext() ){
			CoverDetailsVO coverVO = iter.next();
			if( ( !AppConstants.STATUS_ON.equals( coverVO.getIsCovered() ) && !AppConstants.POPULATE.equals( operation ) ) ||
			( AppConstants.STATUS_ON.equals( coverVO.getIsCovered() ) && !AppConstants.POPULATE.equals( operation ) &&
			Utils.isEmpty( coverVO.getCoverName() ) && Utils.isEmpty( coverVO.getSumInsured().getSumInsured() ) ) ||
			( !AppConstants.STATUS_ON.equals( coverVO.getIsCovered() ) && AppConstants.POPULATE.equals( operation ) &&
			Utils.isEmpty( coverVO.getRiskCodes().getRskId() ) && Utils.isEmpty( coverVO.getVsd() ) ) ){
				iter.remove();
			}
			else if( coverVO.getRiskCodes().getRiskCat().equals( AppConstants.HOME_COVER_RISK_CATEGORY ) ){
				if( !AppConstants.STATUS_ON.equals( coverVO.getIsCovered() ) && AppConstants.POPULATE.equals( operation ) ){
					coverVO.setPremiumAmt(0.00);
				}
				String key = coverVO.getCoverCodes().getCovCode() + coverVO.getRiskCodes().getRiskType().toString();
				coverMap.put( key, coverVO );
			}
			else if( coverVO.getRiskCodes().getRiskCat().equals( AppConstants.HOME_LIST_ITEM_RISK_CATEGORY ) ){
				if( !AppConstants.STATUS_ON.equals( coverVO.getIsCovered() ) && AppConstants.POPULATE.equals( operation ) ){
					coverVO.setPremiumAmt(0.00);
				}
				String key = coverVO.getRiskCodes().getRiskType().toString() + coverVO.getRiskCodes().getRiskCat().toString();
				subItemMap.add( coverVO );
			}
		}

		Iterator<CoverDetailsVO> coverIter = object.getCovers().iterator();
		while( coverIter.hasNext() ){
			CoverDetailsVO coverVO = coverIter.next();
			if( coverVO.getRiskCodes().getRiskCat().equals( AppConstants.HOME_LIST_ITEM_RISK_CATEGORY ) ){
				CoverDetailsVO parentCover = coverMap.get( coverVO.getCoverCodes().getCovCode() + coverVO.getRiskCodes().getRiskType().toString() );
				if( !Utils.isEmpty( parentCover ) && AppConstants.STATUS_ON.equals( parentCover.getIsCovered() ) && !Utils.isEmpty( coverVO.getSumInsured().getSumInsured()) && !coverVO.getSumInsured().getSumInsured().equals( Double.valueOf( 0 ) ) ){
					// check si validation
					if( !riskTypeList.contains( coverVO.getRiskCodes().getRiskType() ) ){
						Double SI = getListItemSumInsured( subItemMap, coverVO.getRiskCodes().getRiskType() );
						riskTypeList.add( coverVO.getRiskCodes().getRiskType() );
						if( parentCover.getRiskCodes().getRiskType() == 31 && SI > coverMap.get( coverVO.getCoverCodes().getCovCode() + coverVO.getRiskCodes().getRiskType().toString() ).getSumInsured().getSumInsured() ){
							throw new BusinessException( "homeInsurance.listItem.SI.validation", null, "List item total Sum Insured validation failed" );
						} else if( parentCover.getRiskCodes().getRiskType() == 32 && SI > coverMap.get( coverVO.getCoverCodes().getCovCode() + coverVO.getRiskCodes().getRiskType().toString() ).getSumInsured().getSumInsured() ){
							throw new BusinessException( "homeInsurance.listItemPP.SI.validation", null, "List item total Sum Insured validation failed" );
						}
					}
				}
				else{
					coverIter.remove();
				}
			}
		}
		LOGGER.info("Exiting from HomeInsuranceSaveRH.buildObjectForSave() method");
	}

	private Double getListItemSumInsured( List<CoverDetailsVO> covers, Integer riskType ){
		Double sumInsured = 00.00;
		for( CoverDetailsVO cover : covers ){
			if(riskType.equals( cover.getRiskCodes().getRiskType() )){
				if(!Utils.isEmpty( cover.getSumInsured() ) && !Utils.isEmpty( cover.getSumInsured().getSumInsured() ))
						sumInsured += cover.getSumInsured().getSumInsured();
			}
		}
		return sumInsured;
	}

	
}
