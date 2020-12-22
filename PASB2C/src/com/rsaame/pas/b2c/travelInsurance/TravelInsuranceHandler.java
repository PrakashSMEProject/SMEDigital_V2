package com.rsaame.pas.b2c.travelInsurance;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.LoginLocation;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.b2c.cmn.constants.AppConstants;
import com.rsaame.pas.b2c.cmn.handlers.B2CEmailTriggers;
import com.rsaame.pas.b2c.cmn.handlers.CommonHandler;
import com.rsaame.pas.b2c.cmn.utils.AppUtils;
import com.rsaame.pas.b2c.cmn.utils.ApplicationContextUtils;
import com.rsaame.pas.b2c.cmn.utils.ReferralUtils;
import com.rsaame.pas.b2c.exception.SystemException;
import com.rsaame.pas.cmn.currency.Currency;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.com.svc.CommonOpSvc;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.uwq.svc.UWQService;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.app.UWQInputsVO;
import com.rsaame.pas.vo.bus.AdditionalInsuredInfoVO;
import com.rsaame.pas.vo.bus.AddressVO;
import com.rsaame.pas.vo.bus.AuthenticationInfoVO;
import com.rsaame.pas.vo.bus.ClaimsSummaryVO;
import com.rsaame.pas.vo.bus.CoverDetailsVO;
import com.rsaame.pas.vo.bus.GeneralInfoVO;
import com.rsaame.pas.vo.bus.InsuredVO;
import com.rsaame.pas.vo.bus.LOB;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.bus.SchemeVO;
import com.rsaame.pas.vo.bus.SourceOfBusinessVO;
import com.rsaame.pas.vo.bus.SumInsuredVO;
import com.rsaame.pas.vo.bus.TravelDetailsVO;
import com.rsaame.pas.vo.bus.TravelInsuranceVO;
import com.rsaame.pas.vo.bus.TravelPackageVO;
import com.rsaame.pas.vo.bus.TravelerDetailsVO;
import com.rsaame.pas.vo.bus.UWQuestionVO;
import com.rsaame.pas.vo.bus.UWQuestionsVO;
import com.rsaame.pas.vo.cmn.CommonVO;
import com.rsaame.pas.web.UserProfileHandler;

/**
 * Handler class to populate TravelInsuranceVO object and call service layer
 * 
 * @author M1020859
 *
 */
public class TravelInsuranceHandler {
	
	/** Logger instance */
	private static final Logger logger = Logger.getLogger(TravelInsuranceHandler.class);
	private static final Double ZERO_VAL = 0.0;
	
	/**
	 * @return
	 */
	public TravelInsuranceVO populateTravelInsForLoad() {
		
		TravelInsuranceVO travelInsuranceVO = (TravelInsuranceVO) ApplicationContextUtils.getBean(com.Constant.CONST_VO_TRAVEL);
		CommonVO commonVO = new CommonVO();
		commonVO.setLob( LOB.TRAVEL );
		travelInsuranceVO.setCommonVO(commonVO);
		
		// Default value has to been set for effective date and expire date for validation purpose
		SchemeVO schemeVO = new SchemeVO();
		schemeVO.setEffDate(null);
		schemeVO.setExpiryDate(null);
		travelInsuranceVO.setScheme(schemeVO);
		
		AppUtils.setScaleForLOB( commonVO.getLob() );
		
		createDefaultTravellerDetails( travelInsuranceVO );
		
		//CreateDefaultUWQuestions(travelInsuranceVO);
		
		populateUwqDetails(travelInsuranceVO,null,false);
		
		return travelInsuranceVO;
	}


	/**
	 * @param travelInsuranceVO
	 */
	private void createDefaultTravellerDetails( TravelInsuranceVO travelInsuranceVO ){
		
		if( Utils.isEmpty( travelInsuranceVO.getTravelDetailsVO() ) ){
			
			TravelDetailsVO travelDetailsVO = new TravelDetailsVO(); 
			List<TravelerDetailsVO> travelerDetailsList = new ArrayList<TravelerDetailsVO>();
			TravelerDetailsVO travelerDetailsVO = new TravelerDetailsVO();
			travelerDetailsVO.setRelation( Byte.valueOf( Utils.getSingleValueAppConfig( "RELATION_SELF" ) ) );
			travelerDetailsList.add( travelerDetailsVO );
			travelDetailsVO.setTravelerDetailsList( travelerDetailsList );
			travelInsuranceVO.setTravelDetailsVO( travelDetailsVO );
		}
		
	}

	/**
	 * Method to save the travel general info details
	 * 
	 * @param travelInsuranceVO
	 * @param request
	 * @return
	 */
	public static TravelInsuranceVO populateTravelInsuranceForSave(TravelInsuranceVO travelInsuranceVO, HttpServletRequest request) {
		
		logger.info("Populating the object of TravelInsuraneVO for quote creation");
		CommonVO commonVO = travelInsuranceVO.getCommonVO();
		if (Utils.isEmpty(commonVO) || Utils.isEmpty(commonVO.getPolicyId())) {
			commonVO.setIsQuote(Boolean.TRUE);
			commonVO = AppUtils.populateCommonVO(commonVO);
			commonVO.setLob( LOB.TRAVEL );
		} else {
			travelInsuranceVO.getCommonVO().setAppFlow(Flow.EDIT_QUO);
		}
		if (!Utils.isEmpty(travelInsuranceVO.getScheme().getEffDate()) && Utils.isEmpty(commonVO.getVsd())) {
			travelInsuranceVO.getScheme().setEffDate(getFormattedDate(travelInsuranceVO.getScheme().getEffDate()));
			commonVO.setVsd(getFormattedDate(travelInsuranceVO.getScheme().getEffDate()));
		}
		/** Check for if traveler has a self relation */ 
		populateTravellers(travelInsuranceVO);
		
		populateUwqDetails(travelInsuranceVO,request,false);
		
		/** Start setting the user profile */
		UserProfile userProfile = null;
		
		//Modified by Vishwa for Online renewal issue in 3.7 and 3.8 for the bug 5838	
		if(!Utils.isEmpty( travelInsuranceVO.getGeneralInfo() ) && 
				!Utils.isEmpty( travelInsuranceVO.getGeneralInfo().getSourceOfBus() )&& 
				!Utils.isEmpty(travelInsuranceVO.getGeneralInfo().getSourceOfBus().getPartnerName())) {
				logger.debug("Setting Userprofile  to travelInsuranceVO if Partner Exist");
				userProfile = UserProfileHandler.getUserProfileVo(travelInsuranceVO.getGeneralInfo().getExtAccExecCode());
				request.getSession( false ).setAttribute( AppConstants.SESSION_USER_PROFILE_VO, userProfile );
				travelInsuranceVO.getCommonVO().setLoggedInUser(userProfile);
			}
		
		else
		{
			if (!Utils.isEmpty(request)) {
				userProfile = (UserProfile) request.getSession(false)
						.getAttribute(AppConstants.SESSION_USER_PROFILE_VO);
			} else {
				// create userProfile for web services
				userProfile = new UserProfile();
			}
			commonVO.setLoggedInUser(userProfile);
			travelInsuranceVO.setCommonVO(commonVO);
		}
		travelInsuranceVO = populateTravelInsuranceVO(travelInsuranceVO);
		logger.info("Exiting saveTravelGeneralInfo to pass travelInsuranceVO to service layer");
		
		return travelInsuranceVO;
	}

	
	/**
	 * @param travelInsuranceVO
	 * @param request
	 * @return
	 */
	public TravelInsuranceVO populateTravelInsForSearch(TravelInsuranceVO travelInsuranceVO, HttpServletRequest request) {
		
		TravelInsuranceVO travelSearResultObj = (TravelInsuranceVO)Utils.getBean(com.Constant.CONST_VO_TRAVEL);
		try {
			//Added for Oman location check
			LoginLocation location = (LoginLocation) Utils.getBean("location");
			String deployedLocation = location.getLocation();
			
			logger.debug("Populating the object of TravelInsuranceVO for search");
			CommonVO commonVO = new CommonVO();
			UserProfile userProfile = null;
			if (!Utils.isEmpty(request)) {
				userProfile = (UserProfile) request.getSession(false)
						.getAttribute(AppConstants.SESSION_USER_PROFILE_VO); 
			} else {// create userProfile for web services
				userProfile = new UserProfile();
			}
			userProfile = new UserProfile();
			commonVO.setLoggedInUser(userProfile);
			commonVO.setIsQuote(true); //retrieving quote
			SourceOfBusinessVO sourceOfBusinessVO = new SourceOfBusinessVO();
			sourceOfBusinessVO.setDistributionChannel(AppConstants.B2C_DEFAULT_DIST_CHANNEL);
			GeneralInfoVO generalInfoVO = (GeneralInfoVO)Utils.getBean("generalInfoVO");
			generalInfoVO.setSourceOfBus(sourceOfBusinessVO);		
			
			travelInsuranceVO.setGeneralInfo(generalInfoVO);
			if (!Utils.isEmpty(travelInsuranceVO.getCommonVO()) &&
					!Utils.isEmpty(travelInsuranceVO.getCommonVO().getQuoteNo())) {
				commonVO.setQuoteNo(travelInsuranceVO.getCommonVO().getQuoteNo());
				
			} 
			// If click here link has been clicked from email below code will be executed start 
			else if (!Utils.isEmpty(request.getParameter(AppConstants.QUOTE_NUM_REQ_PARAM)) && !Utils.isEmpty(request.getParameter(AppConstants.EMAIL_REQ_PARAM))) {
				Long quoteNumber = null;
				String emailid = null;
				if (!Utils.isEmpty(request.getParameter(AppConstants.DRUPAL_REQ_PARAM))){
					quoteNumber = new Long(request.getParameter(AppConstants.QUOTE_NUM_REQ_PARAM));
					emailid = request.getParameter(AppConstants.EMAIL_REQ_PARAM);
				} else {
				
					quoteNumber = new Long(AppUtils.encryptAndDecryptData(request.getParameter(AppConstants.QUOTE_NUM_REQ_PARAM), Boolean.FALSE));
					emailid = AppUtils.encryptAndDecryptData(request.getParameter(AppConstants.EMAIL_REQ_PARAM), Boolean.FALSE);
				}
				commonVO.setQuoteNo(quoteNumber);
				if (Utils.isEmpty(travelInsuranceVO.getGeneralInfo().getInsured())) {
					InsuredVO insuredVO = (InsuredVO)Utils.getBean("VO_INSURED");
					insuredVO.setEmailId(emailid);
					travelInsuranceVO.getGeneralInfo().setInsured(insuredVO);
				}
			}
			
			// To retrieve the quote with mobile number for Oman D2C
			else if (null != deployedLocation && deployedLocation.equals(AppConstants.LOCATION_CODE) && !Utils.isEmpty(request.getParameter(AppConstants.QUOTE_NUM_REQ_PARAM)) && 
					!Utils.isEmpty(request.getParameter(AppConstants.MOBILE_NUM_PARAM))) {
				Long quoteNumber = null;
				String mobileNumber = null;
				if (!Utils.isEmpty(request.getParameter(AppConstants.DRUPAL_REQ_PARAM))){
					quoteNumber = new Long(request.getParameter(AppConstants.QUOTE_NUM_REQ_PARAM));
					mobileNumber = request.getParameter(AppConstants.MOBILE_NUM_PARAM);
				} else {
					quoteNumber = new Long(AppUtils.encryptAndDecryptData(request.getParameter(AppConstants.QUOTE_NUM_REQ_PARAM), Boolean.FALSE));
					mobileNumber = AppUtils.encryptAndDecryptData(request.getParameter(AppConstants.MOBILE_NUM_PARAM), Boolean.FALSE);
				}
				commonVO.setQuoteNo(quoteNumber);
				if (Utils.isEmpty(travelInsuranceVO.getGeneralInfo().getInsured())) {
					InsuredVO insuredVO = (InsuredVO)Utils.getBean("VO_INSURED");
					insuredVO.setMobileNo(mobileNumber);
					travelInsuranceVO.getGeneralInfo().setInsured(insuredVO);
				}
			}
			// end
			else {
				logger.debug("Default quote number has been set as - 0 which will always going to throw business exception");
				/* Default quote number which will always going to throw business exception */
				commonVO.setQuoteNo(0L);
			}
			SchemeVO schemeVO = new SchemeVO();
			AppUtils.populateCommonVO(commonVO);
			if( Utils.isEmpty( commonVO.getPolicyId() ) || Utils.isEmpty( commonVO.getEndtId() ) ){
				if (null != deployedLocation && deployedLocation.equals(AppConstants.LOCATION_CODE)) 
					travelInsuranceVO.setGeneralInfo(null);
				throw new BusinessException( "errorMessage", null, Utils.getAppErrorMessage( "pasb2c.noRecords.found" ) );
			} 
			travelInsuranceVO.setScheme(schemeVO);
			travelInsuranceVO.setCommonVO(commonVO);
			
			populateUwqDetails(travelInsuranceVO,request,false);
			
			logger.debug("Going to call service layer for getting the search results");
			travelSearResultObj = loadGeneralInfoDets(travelInsuranceVO);
		}catch (SystemException exception) {
			throw new SystemException(exception.getCause(), exception.getMessage());
		}
		return travelSearResultObj;
	}
	
	/**
	 * This method will call the service layer for saving general info
	 * 
	 * @param policyDataVO
	 * @return
	 */
	public static TravelInsuranceVO saveTravelGeneralInfo( PolicyDataVO policyDataVO, String contextPath ) {
		PolicyDataVO policyData = new PolicyDataVO();
		try{
			logger.debug( "Object of PolicyDataVO is passed to service layer from saveTravelGeneralInfo() of TravelInsuranceHandler" );
			/* Invoke the rule engine */
			boolean rulePassed = ReferralUtils.executeReferralTaskTravel( (TravelInsuranceVO) policyDataVO, "", "Travel Insured Details", "",
					AppConstants.TRAVEL_INSURED_SECTION_ID );
			

			/* Populate the task vo if referral is present */
			if( !rulePassed ){
				ReferralUtils.populateTaskVO( policyDataVO );
			}
			
			
		
			
			policyDataVO.getGeneralInfo().getInsured().setUpdateMaster( true );
			policyDataVO.getGeneralInfo().getInsured().setCity( AppConstants.DEFAULT_CUSTOMER_CITY );
			if( Short.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_REN_QUO_DOC_CODE ) ).equals( policyDataVO.getCommonVO().getDocCode()) ){
				policyDataVO.getGeneralInfo().getInsured().setBusType( AppConstants.BUS_TYPE_RENEWAL );
			} else {
				policyDataVO.getGeneralInfo().getInsured().setBusType( AppConstants.BUS_TYPE_NEW );
			}
			AddressVO address = new AddressVO();
			address.setCountry( AppConstants.DEFAULT_CUSTOMER_COUNTRY );
			policyDataVO.getGeneralInfo().getInsured().setAddress( address );
			policyDataVO.getGeneralInfo().getAdditionalInfo().setAffinityRefNo(policyDataVO.getGeneralInfo().getSourceOfBus().getPartnerName());
			
			AppUtils.updateTravellersName( (TravelInsuranceVO) policyDataVO );
			AppUtils.updateInsuredName( policyDataVO );
			policyData = (PolicyDataVO) TaskExecutor.executeTasks( "GEN_INFO_COMMON_SAVE", policyDataVO );
			
			if(!rulePassed && !Utils.isEmpty(policyData.getCommonVO().getPolicyId())) { //If general info is not saved then don't trigger any email for referral
				// trigger referral email when rule is failed
				CommonHandler.populateAndTriggerEmail(policyData, null, B2CEmailTriggers.REFERRAL, null);
			} /*else {
				// TRAVEL Email trigger on GET QUOTE start
				if (!Utils.isEmpty(policyData.getCommonVO().getPolicyId()) && !Utils.isEmpty(contextPath)) {
					CommonHandler.populateAndTriggerEmail(policyDataVO, contextPath, B2CEmailTriggers.TRAVEL_CREATE_QUOTE, null);
				}
				// TRAVEL Email trigger on GET QUOTE end
			}*/
		}
		catch (BusinessException exception) {
			if (!Utils.isEmpty(exception.getMessage())) {
				logger.debug("Error occured while saving quote details - "+exception.getMessage());
				throw new SystemException(exception.getCause(), exception.getMessage());
			}
		}
		return (TravelInsuranceVO)policyData;
	}
	
	/**
	 * This method will call the service layer to get the quotation number details
	 * 
	 * @param insuranceVO
	 * @return
	 */
	public static TravelInsuranceVO loadGeneralInfoDets(TravelInsuranceVO insuranceVO) {
		logger.debug("Going to call service layer for searching entered quotation number");
		try {
			CommonOpSvc commonOpSvc = (CommonOpSvc)Utils.getBean(com.Constant.CONST_GECOMSVCBEAN);
			CommonVO commonVO = (CommonVO)commonOpSvc.invokeMethod("populateCommonDetails", insuranceVO.getCommonVO());
			if (!Utils.isEmpty(commonVO)) {
				insuranceVO.setCommonVO(commonVO);
			}
	
			
			PolicyDataVO policyDataVO = (PolicyDataVO)TaskExecutor.executeTasks( "GEN_INFO_COMMON_LOAD", insuranceVO );
			TravelInsuranceVO travelInsuranceVO = (TravelInsuranceVO) policyDataVO;
			
			/*VAT Dileep*/
			insuranceVO.setVatCode(travelInsuranceVO.getVatCode());
			
			if(Utils.isEmpty(insuranceVO.getPremiumVO())){
				insuranceVO.setPremiumVO(new PremiumVO());				
			}
			
			if(!Utils.isEmpty(travelInsuranceVO.getPremiumVO())){
				insuranceVO.getPremiumVO().setVatTaxPerc(travelInsuranceVO.getPremiumVO().getVatTaxPerc());
				insuranceVO.getPremiumVO().setVatCode(travelInsuranceVO.getPremiumVO().getVatCode());
			}
			
			
			insuranceVO.setTravelDetailsVO(travelInsuranceVO.getTravelDetailsVO());
			insuranceVO.setTravelPackageList(travelInsuranceVO.getTravelPackageList());
			insuranceVO.setGeneralInfo(policyDataVO.getGeneralInfo());  //Since general info contents needs to be displayed set the fetched general info content 
			
			 System.out.println("insuranceVO.getGeneralInfo().getInsured().getVatRegNo();..."+insuranceVO.getGeneralInfo().getInsured().getVatRegNo()); 
			
			insuranceVO.setScheme(policyDataVO.getScheme());
			insuranceVO.getGeneralInfo().getAdditionalInfo().setAffinityRefNo(insuranceVO.getGeneralInfo().getSourceOfBus().getPartnerName());
			//added for renewal
			if( Short.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_REN_QUO_DOC_CODE ) ).equals( policyDataVO.getCommonVO().getDocCode()) ){			
				insuranceVO.setAuthenticationInfoVO( policyDataVO.getAuthenticationInfoVO() );				
			}
			insuranceVO.setPolicyTerm( policyDataVO.getPolicyTerm() );
			populateUwqDetails(insuranceVO,null,false);
		} catch (BusinessException exception) {
			if (!Utils.isEmpty(exception.getMessage())) {
				logger.debug("Exception occured while searching quote number - "+exception.getMessage());
				throw new SystemException(exception.getCause(), exception.getMessage());
			}
		}
		return insuranceVO;
	}
	
	/**	
	 * 
	 * @param date
	 * @return
	 */
	private static Date getFormattedDate(Date date) {
		Calendar calender = Calendar.getInstance();
		calender.setTime(date);
		calender.set(Calendar.HOUR_OF_DAY, 0);  
		calender.set(Calendar.MINUTE, 0);  
		calender.set(Calendar.SECOND, 0);  
		calender.set(Calendar.MILLISECOND, 0);
		Date target = calender.getTime();
		return target;
	}
	
	/**
	 * Setting the default values for quote creation
	 * 
	 * @param policyDataVO
	 * @return
	 */
	private static TravelInsuranceVO populateTravelInsuranceVO( TravelInsuranceVO travelInsuranceVO ){
		AuthenticationInfoVO authenticationInfoVO = travelInsuranceVO.getAuthenticationInfoVO();
		if( Utils.isEmpty( authenticationInfoVO ) ){
			authenticationInfoVO = new AuthenticationInfoVO();
		}
		authenticationInfoVO.setAccountingDate( new Date() );
		try
			{
				UserProfile profile = (UserProfile)travelInsuranceVO.getCommonVO().getLoggedInUser();
								
				if(!Utils.isEmpty(profile.getRsaUser().getUserId()))
				{
					authenticationInfoVO.setIntAccExecCode( profile.getRsaUser().getUserId() );
					logger.debug("UserProfile of populateTravelInsuranceVO() of TravelInsuranceHandler class: "+profile.getRsaUser().getUserId());
				}
				travelInsuranceVO.setAuthenticationInfoVO( authenticationInfoVO );
				AdditionalInsuredInfoVO addiionalInfo =  new AdditionalInsuredInfoVO();
				addiionalInfo.setProcessingLoc( AppConstants.B2C_DEFAULT_DEPLOYED_LOCATION );
				travelInsuranceVO.getGeneralInfo().setAdditionalInfo( addiionalInfo );
				travelInsuranceVO.getGeneralInfo().setIntAccExecCode( profile.getRsaUser().getUserId() );
				
				travelInsuranceVO.getGeneralInfo().getInsured().setUpdateMaster( false ); //TODO check if this has to taken from somewhere else
				if(!Utils.isEmpty(travelInsuranceVO.getScheme()) && Utils.isEmpty(travelInsuranceVO.getScheme().getSchemeCode())){
					travelInsuranceVO.getScheme().setSchemeCode( AppConstants.B2C_TRAVEL_DEFAULT_SCHEME_CODE );
				}
				SourceOfBusinessVO sourceOfBusinessVO = travelInsuranceVO.getGeneralInfo().getSourceOfBus();
				if(Utils.isEmpty(sourceOfBusinessVO)){
					sourceOfBusinessVO = new SourceOfBusinessVO();
				}
				if(Utils.isEmpty(sourceOfBusinessVO.getCustomerSource())){
					sourceOfBusinessVO.setCustomerSource( AppConstants.B2C_DEFAULT_CUST_SRC );
				}
				if(Utils.isEmpty(sourceOfBusinessVO.getDistributionChannel())){
					sourceOfBusinessVO.setDistributionChannel( AppConstants.B2C_DEFAULT_DIST_CHANNEL );
				}
				//sourceOfBusinessVO.setBrokerName( AppConstants.B2C_DEFAULT_BROKER_NAME );
				travelInsuranceVO.getGeneralInfo().setSourceOfBus( sourceOfBusinessVO );
				ClaimsSummaryVO claimsSummaryVO = travelInsuranceVO.getGeneralInfo().getClaimsHistory();
				if( Utils.isEmpty( claimsSummaryVO ) ){
					claimsSummaryVO = new ClaimsSummaryVO();
				}
				if(Utils.isEmpty(travelInsuranceVO.getGeneralInfo().getSourceOfBus().getSourceOfBusiness())){
					claimsSummaryVO.setSourceOfBusiness(AppConstants.B2C_DEFAULT_SRC_OF_BUS);
				}
				else
				{
					claimsSummaryVO.setSourceOfBusiness(travelInsuranceVO.getGeneralInfo().getSourceOfBus().getSourceOfBusiness());
				}
				travelInsuranceVO.getGeneralInfo().setClaimsHistory( claimsSummaryVO );
				travelInsuranceVO.setPolicyClassCode( AppConstants.TRAVEL_CLASS_CODE );
				if( !Utils.isEmpty( travelInsuranceVO.getPolicyTerm() ) ){
					if( travelInsuranceVO.getPolicyTerm() > AppConstants.LONG_TERM_TRAVEL_DAYS ){
						travelInsuranceVO.setPolicyType( AppConstants.TRAVEL_LONG_TERM_POLICY_TYPE );
					}
					else{
						travelInsuranceVO.setPolicyType( AppConstants.TRAVEL_SHORT_TERM_POLICY_TYPE );
					}
			}
			travelInsuranceVO.setStatus( AppConstants.QUOTE_PENDING );
		}
		catch (BusinessException e){
			if (!Utils.isEmpty(e.getMessage())) {
				logger.debug("Exception occured while populateTravelInsuranceVO - "+e.getMessage());
				throw new SystemException(e.getCause(), e.getMessage());
			}
		}
	
		return travelInsuranceVO;
	}

	public static void preProcess( TravelInsuranceVO travelInsuranceVO, List<TravelPackageVO> packagesToBeDeleted ){
		
		if( !Utils.isEmpty( travelInsuranceVO.getScheme() ) && !Utils.isEmpty( travelInsuranceVO.getScheme().getTariffCode() )
				&& !Utils.isEmpty( travelInsuranceVO.getScheme().getSchemeCode() ) ){
			
			for( TravelPackageVO packageVO : travelInsuranceVO.getTravelPackageList() ){
				if( travelInsuranceVO.getScheme().getTariffCode().toString().equals( packageVO.getTariffCode() ) ){
					packageVO.setIsSelected( Boolean.TRUE );
					setSIValuesForCheckBoxes( packageVO.getCovers() );
				}
				else {
					packageVO.setIsSelected( Boolean.FALSE );
					packagesToBeDeleted.add( packageVO );
				}
			}

			//TODO : set proper values by mapping values from UI.
			if( Utils.isEmpty( travelInsuranceVO.getPremiumVO() ) ){
				travelInsuranceVO.setPremiumVO( new PremiumVO() );
			}
			
		}
		else{
			throw new BusinessException( null, "Tariff Code or scheme code not found." );
		}

	}

	private static void setSIValuesForCheckBoxes( List<CoverDetailsVO> covers ){
		
		for( CoverDetailsVO coverDetailsVO : covers ){

			SumInsuredVO sumInsuredVO = null;
			if( Utils.isEmpty( coverDetailsVO.getSumInsured() ) ){
				sumInsuredVO = new SumInsuredVO();
			}
			else{
				sumInsuredVO = coverDetailsVO.getSumInsured();
			}

			if( Utils.isEmpty( sumInsuredVO.getSumInsured() ) && AppConstants.STATUS_ON.equalsIgnoreCase( coverDetailsVO.getIsCovered() ) ){
				sumInsuredVO.setSumInsured( Double.valueOf( Long.valueOf( sumInsuredVO.getaDesc() ) ) );
			}
			else if( Utils.isEmpty( sumInsuredVO.getSumInsured() ) ){
				
				sumInsuredVO.setSumInsured( ZERO_VAL );
			}
			coverDetailsVO.setSumInsured( sumInsuredVO );
		}
	}

	public static TravelInsuranceVO saveTravelRisk( TravelInsuranceVO travelInsuranceVO ){
		
		try{
		    		    
			/* Load travel general info details into travelInsVO.*/
			TravelInsuranceVO travelInsVO = (TravelInsuranceVO)TaskExecutor.executeTasks( "GEN_INFO_COMMON_LOAD", travelInsuranceVO );
			
			/*Set the important attributes from existing travelInsuranceVO.*/
			travelInsVO.setPremiumVO( travelInsuranceVO.getPremiumVO() );
			travelInsVO.setTravelPackageList( travelInsuranceVO.getTravelPackageList() );
			travelInsVO.getScheme().setSchemeCode( travelInsuranceVO.getScheme().getSchemeCode() );
			travelInsVO.getScheme().setPolicyType( String.valueOf(travelInsVO.getScheme().getPolicyCode()) ); //Added after SIT exception
			travelInsVO.getScheme().setTariffCode( travelInsuranceVO.getScheme().getTariffCode() );

			if( !Utils.isEmpty( travelInsuranceVO.getGeneralInfo().getInsured() ) ){
				travelInsVO.getGeneralInfo().getInsured().setRoyaltyType( travelInsuranceVO.getGeneralInfo().getInsured().getRoyaltyType() );
				travelInsVO.getGeneralInfo().getInsured().setGuestCardNo( travelInsuranceVO.getGeneralInfo().getInsured().getGuestCardNo() );
			}
			
			//Added for Oman D2C
			for(TravelPackageVO travelPackageVO : travelInsVO.getTravelPackageList()) {
				if(travelPackageVO.getIsSelected()) {
					for(CoverDetailsVO cover : travelPackageVO.getCovers()) {
						if(null != cover && null != cover.getCoverName() && cover.getCoverName().equalsIgnoreCase(AppConstants.PERSONAL_ACCIDENT_COVER) && null != cover.getIsCovered() && cover.getIsCovered().equalsIgnoreCase("ON")) {
							if(null != cover.getSumInsured()) {
								cover.getSumInsured().setSumInsured(Double.valueOf( Utils.getSingleValueAppConfig("PA_OPTION2_SI")));
							}
						}
					}
				}
			}
			
			DataHolderVO<Object[]> dataHolder = new DataHolderVO<Object[]>();
			Object[] inpObjects = { travelInsVO, false };
			dataHolder.setData( inpObjects );
			
			/* Set processing location code for save, which is a must for convert to policy.*/
			travelInsVO.getGeneralInfo().getAdditionalInfo().setProcessingLoc( AppConstants.B2C_DEFAULT_DEPLOYED_LOCATION );
			
			
			
						
			 			
			/* @SuppressWarnings("unchecked")
             DataHolderVO<Object[]> vatRateAndCodeholder =(DataHolderVO<Object[]>) TaskExecutor.executeTasks("LOAD_TRAVEL_VAT_RATE_AND_CODE", travelInsVO);
             @SuppressWarnings("unchecked")
             Map<String, Object> vatRateAndCode = (Map<String, Object>) vatRateAndCodeholder.getData()[0];
         
             logger.debug("**********vatRate**********"+vatRateAndCode.get("vatRate"));
             logger.debug("**********vatCode**********"+vatRateAndCode.get("vatCode"));
         
             travelInsVO.getPremiumVO().setVatTaxPerc((Double) vatRateAndCode.get("vatRate"));
             travelInsVO.getPremiumVO().setVatCode((Integer) vatRateAndCode.get("vatCode"));*/      
			
			/*VAT Dileep*/						
			//populatePremium(travelInsVO,Boolean.TRUE);			
			
			travelInsVO = (TravelInsuranceVO) TaskExecutor.executeTasks( "SAVE_QUOTE_TRAVEL", dataHolder );
			if (!Utils.isEmpty(travelInsuranceVO.getGeneralInfo()
					.getSourceOfBus())
					&& !Utils.isEmpty(travelInsuranceVO.getGeneralInfo()
							.getSourceOfBus().getPartnerName())) {
				travelInsVO.getGeneralInfo().getSourceOfBus().setPartnerId( travelInsuranceVO.getGeneralInfo().getSourceOfBus().getPartnerId() );
				travelInsVO.getGeneralInfo().getSourceOfBus().setPartnerName( travelInsuranceVO.getGeneralInfo().getSourceOfBus().getPartnerName());
				travelInsVO.getGeneralInfo().getSourceOfBus().setCallCentreNo( travelInsuranceVO.getGeneralInfo().getSourceOfBus().getCallCentreNo() );
				travelInsVO.getGeneralInfo().getSourceOfBus().setCcEmailId( travelInsuranceVO.getGeneralInfo().getSourceOfBus().getCcEmailId() );
				travelInsVO.getGeneralInfo().getSourceOfBus().setReplyToEmailId( travelInsuranceVO.getGeneralInfo().getSourceOfBus().getReplyToEmailId() );
				travelInsVO.getGeneralInfo().getAdditionalInfo().setAffinityRefNo(travelInsuranceVO.getGeneralInfo().getSourceOfBus().getPartnerName());
				travelInsVO.getGeneralInfo().getSourceOfBus().setFromEmailID( travelInsuranceVO.getGeneralInfo().getSourceOfBus().getFromEmailID() );
				travelInsVO.getGeneralInfo().getSourceOfBus().setSourceOfBusiness( travelInsuranceVO.getGeneralInfo().getSourceOfBus().getSourceOfBusiness() );
				travelInsVO.getGeneralInfo().getSourceOfBus().setDefaultOnlineDiscount(travelInsuranceVO.getGeneralInfo().getSourceOfBus().getDefaultOnlineDiscount());
				travelInsVO.getGeneralInfo().getSourceOfBus().setDefaultAssignToUser(travelInsuranceVO.getGeneralInfo().getSourceOfBus().getDefaultAssignToUser());
				travelInsVO.getGeneralInfo().getSourceOfBus().setFaqUrl(travelInsuranceVO.getGeneralInfo().getSourceOfBus().getFaqUrl());
				travelInsVO.getGeneralInfo().getSourceOfBus().setPolicyTermUrl(travelInsuranceVO.getGeneralInfo().getSourceOfBus().getPolicyTermUrl());	
				
			}
			return travelInsVO;
		}
		catch( BusinessException e ){
			throw new SystemException( e.getCause(), e.getMessage() );
		}
		
	}

	/**
	 * 
	 * This method will make entries into the T_MAS_INSURED
	 * 
	 * @param travelInsuranceVO
	 */
	public static void populateTravellers(TravelInsuranceVO travelInsuranceVO) {
		if (!Utils.isEmpty(travelInsuranceVO) && !Utils.isEmpty(travelInsuranceVO.getTravelDetailsVO().getTravelerDetailsList())) {
			for(TravelerDetailsVO travelerDetails:travelInsuranceVO.getTravelDetailsVO().getTravelerDetailsList()) {
				if (travelerDetails.getRelation().toString().equals(Utils.getSingleValueAppConfig("RELATION_SELF"))) {
					travelerDetails.setName( travelerDetails.getName().trim() );
					int indexOfSpace = travelerDetails.getName().lastIndexOf( " " );
					if( indexOfSpace != -1 ){
						travelInsuranceVO.getGeneralInfo().getInsured().setFirstName( travelerDetails.getName().substring( 0, indexOfSpace ) );
						travelInsuranceVO.getGeneralInfo().getInsured().setLastName( travelerDetails.getName().substring( indexOfSpace + 1 ) );
					}else{
						travelInsuranceVO.getGeneralInfo().getInsured().setFirstName( travelerDetails.getName() );
					}
					travelInsuranceVO.getGeneralInfo().getInsured().setName( travelerDetails.getName() );
					travelInsuranceVO.getGeneralInfo().getInsured().setNationality(travelerDetails.getNationality().intValue());
				}
			}
		}
	}
	
	
	/**
	 * Method deletes unselected packages from travelInsuranceVO. 
	 * @param travelInsuranceVO
	 * @param packagesToBeDeleted
	 */
	public static void prepareForRatingCall( TravelInsuranceVO travelInsuranceVO, List<TravelPackageVO> packagesToBeDeleted ){
		if( !Utils.isEmpty( packagesToBeDeleted ) ){
			for( TravelPackageVO packageToDelete : packagesToBeDeleted ){
				travelInsuranceVO.getTravelPackageList().remove( packageToDelete );
			}
		}
	}
	
	/**
	 * @param travelInsuranceVO
	 * @return
	 */
	public static PolicyDataVO loadDataForPayment( TravelInsuranceVO travelInsuranceVO ){
		logger.info("Entered TravelInsuranceHandler.loadDataForPayment method.");
		travelInsuranceVO = (TravelInsuranceVO) TaskExecutor.executeTasks( "TRAVEL_PACKAGE_PREMIUM", travelInsuranceVO );
		populatePremium(travelInsuranceVO,null);
		return (PolicyDataVO)travelInsuranceVO;
	}
	
	/**
	 * Method to populate premium details in TravelInsuranceVO object
	 * @param travelInsuranceVO
	 */
	public static void populatePremium( TravelInsuranceVO travelInsuranceVO, HttpServletRequest request ){

		double discountAmount = 0.0;
		double totalPremium = 0.0;
		double promoDiscountAmount = 0.0;
		double govTax = 0.0;
		double vatTax = 0.0;
		double vatTaxPerc = 0.0;
		double vatablePremium=0.0;
		
		if( !Utils.isEmpty( travelInsuranceVO.getTravelDetailsVO() ) && !Utils.isEmpty( travelInsuranceVO.getTravelDetailsVO().getTravelerDetailsList() ) ){

			for( TravelerDetailsVO travelerDetailsVO : travelInsuranceVO.getTravelDetailsVO().getTravelerDetailsList() ){
				totalPremium += travelerDetailsVO.getPremiumAmtActual();
			}
			
			/*VAT Renewal */
			/*
			if(Utils.isEmpty(totalPremium) || totalPremium == 0){
				
				String tariffVat = travelInsuranceVO.getScheme().getTariffCode().toString();
				logger.info("TravelInsuranceHandler.PopulatePremium method, tariff: "+tariffVat);
				for(TravelPackageVO travelPackageVO :travelInsuranceVO.getTravelPackageList()) {
					if(travelPackageVO.getTariffCode().equals(tariffVat)) {
						totalPremium = travelPackageVO.getPremiumAmt();
						logger.info("TravelInsuranceHandler.PopulatePremium method Premium amount without TAX : "+travelPackageVO.getPremiumAmt());
					}
				}								
			}*/

			LoginLocation location = (LoginLocation) Utils.getBean("location");
			String deployedLocation = location.getLocation();
			
			if( !Utils.isEmpty( travelInsuranceVO.getPremiumVO().getDiscOrLoadPerc() ) ){
				discountAmount = ( travelInsuranceVO.getPremiumVO().getDiscOrLoadPerc() * totalPremium ) / 100;
				//Added for Radar Defect Unique ID: 444536; Reason: To remove "," from discountAmount as it was throwing error  
				discountAmount = Double.valueOf( Currency.getFormattedCurrency( discountAmount , travelInsuranceVO.getCommonVO().getLob().toString() ).replaceAll(",", "") );
				if(null != deployedLocation && deployedLocation.equals(AppConstants.LOCATION_CODE))
					discountAmount = Double.valueOf( Currency.getFormattedCurrency( getRoundOffCalculationOmanTravel (discountAmount ), travelInsuranceVO.getCommonVO().getLob().toString() ).replaceAll(",", "") );
				else
					discountAmount = Double.valueOf( Currency.getFormattedCurrency( discountAmount , travelInsuranceVO.getCommonVO().getLob().toString() ).replaceAll(",", "") );
			}
			
			logger.info("TravelInsuranceHandler.populatePremium method, deployedLocation : "+deployedLocation+" , discountAmount : "+ discountAmount);

			if( !Utils.isEmpty( travelInsuranceVO.getPremiumVO().getPromoDiscPerc() ) ){
				promoDiscountAmount = ( travelInsuranceVO.getPremiumVO().getPromoDiscPerc() * totalPremium ) / 100;
				/*
				 * Removed the duplicate code as it had duplicate blocks thus
				 * throwing critical SONAR violation, introduced due to fix
				 * Added for Radar Defect Unique ID: 444536
				 */
				//Added for Radar Defect Unique ID: 444536; Reason: To remove "," from discountAmount as it was throwing error
					promoDiscountAmount = Double.valueOf( Currency.getFormattedCurrency( promoDiscountAmount  , travelInsuranceVO.getCommonVO().getLob().toString() ).replaceAll(",", "") );
					logger.info("TravelInsuranceHandler.populatePremium method, promoDiscountAmount : "+promoDiscountAmount);
			}
			
			
			// For 142244 VAT implementation
			Date effectiveDate = travelInsuranceVO.getScheme().getEffDate();
			Date expiryDate = travelInsuranceVO.getScheme().getExpiryDate();
			long policyPeriod = AppUtils.getDateDifference(expiryDate, effectiveDate);
			
			Date vatStartDate = null;
			String defaultDateFormat = Utils.getSingleValueAppConfig("DEFAULT_DATE_FORMAT"); // MM/dd/yyyy
			try {
				
				//SvcUtils.populateVatDt();
				vatStartDate = new SimpleDateFormat(defaultDateFormat).parse(SvcUtils.populateVatDt());
			} catch (ParseException e) {
		
				e.printStackTrace();
			} 
			Date maxDate = null;
			if(AppUtils.getDateDifference(effectiveDate,vatStartDate) > 1){
				maxDate = effectiveDate;
			}
			else{
				maxDate = vatStartDate; 
			}
			
			/*VAT - Dileep*/
			if( !Utils.isEmpty( travelInsuranceVO.getVatTaxPerc())){
				if(Utils.isEmpty(travelInsuranceVO.getPremiumVO())){
					travelInsuranceVO.setPremiumVO(new PremiumVO());
					vatTaxPerc = travelInsuranceVO.getVatTaxPerc();
				}
				else 
					vatTaxPerc = travelInsuranceVO.getPremiumVO().getVatTaxPerc();
				
				vatTax = ((((totalPremium + discountAmount) - promoDiscountAmount ) *( AppUtils.getDateDifference(expiryDate,maxDate) ) / policyPeriod) * vatTaxPerc) / 100;
				
				vatablePremium = ((totalPremium + discountAmount) - promoDiscountAmount ) *( AppUtils.getDateDifference(expiryDate,maxDate) ) / policyPeriod ;
//				vatTax =  vatTaxPerc * ((totalPremium + discountAmount) - promoDiscountAmount) / 100;
				
				//vatTax = Double.valueOf( Currency.getFormattedCurrency( vatTax , travelInsuranceVO.getCommonVO().getLob().toString() ).replaceAll(",", "") );
				
				
				//vatTax = Double.parseDouble(AppUtils.getFormattedDoubleWithDecimals( vatTax , 100 ).toString());				
				vatTax = Double.parseDouble( AppUtils.getFormattedNumberWithDecimals(vatTax));				
				
				if(!Utils.isEmpty(request) && !Utils.isEmpty(request.getAttribute("vatTaxTravel"))) {
					vatTax = Double.parseDouble( AppUtils.getFormattedNumberWithDecimals(Double.parseDouble(request.getParameter("vatTaxTravel"))));					
				}else{
					travelInsuranceVO.getPremiumVO().setVatTax(vatTax);
					travelInsuranceVO.getPremiumVO().setVatablePrm(vatablePremium);
				}
				travelInsuranceVO.getPremiumVO().setVatablePrm(vatablePremium);
				travelInsuranceVO.getPremiumVO().setVatTaxPerc(vatTaxPerc);
				logger.info("TravelInsuranceHandler.populatePremium method, vatTax : "+vatTax);
			}
			/*End*/
			
			//Added Govt tax for Oman
			if(null != deployedLocation && deployedLocation.equals(AppConstants.LOCATION_CODE)) {
				if( !Utils.isEmpty( travelInsuranceVO.getGovtTaxPerc() ) ){
					govTax = ( travelInsuranceVO.getGovtTaxPerc() * ((totalPremium + discountAmount) - promoDiscountAmount) ) / 100;
					govTax = Double.valueOf( Currency.getFormattedCurrency( govTax , travelInsuranceVO.getCommonVO().getLob().toString() ).replaceAll(",", "") );
					logger.info("TravelInsuranceHandler.populatePremium method, govTax : "+govTax);
				}
				travelInsuranceVO.getPremiumVO().setPremiumAmt( getRoundOffCalculationOmanTravel (totalPremium + govTax + discountAmount - promoDiscountAmount ));
			}else 
				travelInsuranceVO.getPremiumVO().setPremiumAmt( totalPremium + vatTax + discountAmount - promoDiscountAmount );			
			travelInsuranceVO.getPremiumVO().setPremiumAmtActual( totalPremium );
		}
	}
	
	/**
	 * 
	 * @param govtTax 
	 * @param travelInsuranceVO
	 * @param premiunFromRatingEngine
	 * @param travelerDetailsVO
	 * @return
	 */
	public static double getRoundOffCalculationOmanTravel(
			double actualValue) {
	
			double formatedValue = 0;
			DecimalFormat df2 = new DecimalFormat("0.0");
			if(actualValue>0)
				df2.setRoundingMode(RoundingMode.CEILING);
			else
				df2.setRoundingMode(RoundingMode.FLOOR);
			DecimalFormat df3 = new DecimalFormat("0.000");
			
			formatedValue = new Double(df2.format(actualValue)).doubleValue();
			formatedValue = new Double(df3.format(formatedValue)).doubleValue();
			return formatedValue;
			
	}
	
	public TravelInsuranceVO populateCommonVOForenewal(TravelInsuranceVO travelInsuranceVO, HttpServletRequest request) throws Exception {
		
		TravelInsuranceVO travelInsObj = (TravelInsuranceVO)Utils.getBean(com.Constant.CONST_VO_TRAVEL);
		String renQuote = null;
		String dob = null;
		try {
			logger.debug("Populating the object of TravelInsuranceVO for loading the renewal data");
			CommonVO commonVO = travelInsuranceVO.getCommonVO();
			
			String policy = request.getParameter("policyNo");
			dob = request.getParameter("dob");
			
			if (! Utils.isEmpty( policy ))
			{
				
				try{			
					System.out.println("Inside the null check "+policy);
					
					CommonVO common = new CommonVO();
					DataHolderVO policyIdHolder = new DataHolderVO();
					common.setPolicyNo(Long.parseLong(policy));
					common.setCreatedBy(dob);
							
					CommonOpSvc commonOpSvc = (CommonOpSvc) Utils.getBean( com.Constant.CONST_GECOMSVCBEAN );
					policyIdHolder = (DataHolderVO) commonOpSvc.invokeMethod( "getRenQuoteForPolicy", common);
					
					renQuote = policyIdHolder.getData().toString();
					
					common = null;
					policyIdHolder = null;
					System.out.println("The renewal Quote is "+renQuote);
				}
				catch(Exception e)
				{
					request.setAttribute("RSA_DIRECT_ERROR", "RSA_DIRECT_ERROR");
					throw e;
					
				}	
			}
			else
			{
				renQuote = request.getParameter("renQuote");
				if(!Utils.isEmpty(renQuote) && !StringUtils.isNumeric(renQuote)){
					renQuote = AppUtils.encryptAndDecryptData( renQuote, Boolean.FALSE );
				}
			}
			
			if(Utils.isEmpty(commonVO)){
				//commonVO = populateCommonVO();
				commonVO = new CommonVO();
				commonVO.setIsQuote(true);
				commonVO.setQuoteNo(Long.parseLong(renQuote));
				commonVO = AppUtils.populateCommonVO(commonVO);
				//commonVO.setLob( LOB.TRAVEL );
				commonVO.setDocCode(Short.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_REN_QUO_DOC_CODE ) ));
			}
			commonVO.setLob( LOB.TRAVEL );
			UserProfile userProfile = null;
			if (!Utils.isEmpty(request)) {
				userProfile = (UserProfile) request.getSession(false)
						.getAttribute(AppConstants.SESSION_USER_PROFILE_VO);
			} else {// create userProfile for web services
				userProfile = new UserProfile();
			}
			//String renQuote = request.getParameter("renQuote");
			//commonVO.setIsQuote(true);
			commonVO.setLoggedInUser(userProfile);
			SourceOfBusinessVO sourceOfBusinessVO = new SourceOfBusinessVO();
			sourceOfBusinessVO.setDistributionChannel(AppConstants.B2C_DEFAULT_DIST_CHANNEL);
			GeneralInfoVO generalInfoVO = new GeneralInfoVO();
			generalInfoVO.setSourceOfBus(sourceOfBusinessVO);
			travelInsuranceVO.setGeneralInfo(generalInfoVO);
			if(Utils.isEmpty(commonVO.getQuoteNo())){
				commonVO.setQuoteNo(Long.parseLong(renQuote));
			}
					
			SchemeVO schemeVO = new SchemeVO();
			travelInsuranceVO.setScheme(schemeVO);
			travelInsuranceVO.setCommonVO(commonVO);
			travelInsObj = loadGeneralInfoDets(travelInsuranceVO);
			CommonOpSvc commonOpSvc = (CommonOpSvc)Utils.getBean(com.Constant.CONST_GECOMSVCBEAN);
			commonVO = (CommonVO)commonOpSvc.invokeMethod("populateCommonDetails", commonVO);
			if (!Utils.isEmpty(commonVO)) {
				travelInsuranceVO.setCommonVO(commonVO);
			}
		} catch (SystemException exception) {
			throw new SystemException(exception.getCause(), exception.getMessage());
		}
		return travelInsuranceVO;
	}

	/**
	 * This method will call the service layer for saving general info section for renewal
	 * 
	 * @param policyDataVO
	 * @return
	 */


	/**
	 * @param travelInsuranceVO
	 * @param modelAndView
	 * @return
	 */
	public static TravelInsuranceVO loadTravelRiskPage( TravelInsuranceVO travelInsuranceVO ){
		travelInsuranceVO = (TravelInsuranceVO) TaskExecutor.executeTasks( "TRAVEL_PACKAGE_PREMIUM", travelInsuranceVO );
		/*VAT - Dileep*/
		//populatePremium(travelInsuranceVO);
		return travelInsuranceVO;
		
	}
	
	
	public static PolicyDataVO loadPartnerMgmtDetails(TravelInsuranceVO travelInsuranceData){
		logger.info("Entered TravelInsuranceHandler.loadPartnerMgmtDetails method.");
		try{
			travelInsuranceData.getCommonVO().setLoggedInUser(travelInsuranceData.getCommonVO().getLoggedInUser());
			logger.info("TravelInsuranceHandler.loadPartnerMgmtDetails method, calling TravelInsuranceSVC.loadPartnerMgmt method");
				TaskExecutor.executeTasks( "TRAVEL_LOAD_PARTNERMGMT_DATA", travelInsuranceData );
		}
		catch(BusinessException be){
			throw new BusinessException(be.getErrorKeysList().get(0), be.getCause(), be.getMessage());
		}
		catch(Exception exp){
			exp.printStackTrace();
		}
		logger.info("Exiting TravelInsuranceHandler.loadPartnerMgmtDetails method.");
		return travelInsuranceData;
	}	
	
	public static void populateUwqDetails(TravelInsuranceVO travelInsuranceVO,
			HttpServletRequest request, boolean check) {
		
		UWQuestionsVO questionL = null;
		String ans = "no";
		
		if (!Utils.isEmpty(request) && !Utils.isEmpty(request.getParameter("uqQuestions"))){
			ans = request.getParameter("uqQuestions").toLowerCase();
		}
		
		//String ans = Utils.isEmpty(request)?"no":request.getParameter("uqQuestions").toLowerCase();
		
		UWQuestionsVO updatedUWQList =new UWQuestionsVO();
		List<UWQuestionVO> uwQuestVOList= new ArrayList<UWQuestionVO>();	
		
		Date polPreparedDate = null;
		UWQService uwqService = new UWQService();
		UWQInputsVO uwqVO = new UWQInputsVO();
		uwqVO.setSectionId(Integer.valueOf( Utils.getSingleValueAppConfig(AppConstants.TRAVEL_SEC_ID)));
		uwqVO.setTarCode(1);
		
		
		
		if(check){
			logger.debug("*******On Quote Retrival no data found*******");
			polPreparedDate = new Date();
		}
			
		else{
		if(!Utils.isEmpty(travelInsuranceVO.getCommonVO().getQuoteNo() )) {
			polPreparedDate = SvcUtils.getPreparedDate(travelInsuranceVO.getCommonVO().getQuoteNo() );
			if(polPreparedDate == null) {
				polPreparedDate = new Date();
			}			
		}
		}
		questionL = (UWQuestionsVO)uwqService.invokeMethod("getListOfDescription",uwqVO);
		
		
		
		if(!Utils.isEmpty( questionL ))
		{
			if(!Utils.isEmpty( questionL.getQuestions() ))
			{
				for(UWQuestionVO uwqQuestVO:questionL.getQuestions()){
					if(!Utils.isEmpty(uwqQuestVO))
					{
						
						/*if(!Utils.isEmpty(polPreparedDate)&&(polPreparedDate.compareTo((Date)uwqQuestVO.getPreparedDate())<0)){
							questionL.getQuestions().remove(uwqQuestVO);
						}*/
						
						if(Utils.isEmpty(polPreparedDate) || (!Utils.isEmpty(polPreparedDate)&&(polPreparedDate.compareTo((Date)uwqQuestVO.getPreparedDate())>0) ) ){
													
							if(!Utils.isEmpty(uwqQuestVO.getQDesc())){
								uwqQuestVO.setResponse(ans);
								
								uwQuestVOList.add(uwqQuestVO);															
								
							}
						}
					}
				}				
				updatedUWQList.setQuestions(uwQuestVOList);				
			}
		}	
		travelInsuranceVO.setUwQuestions(updatedUWQList);
	}
	
	public static TravelInsuranceVO populateVatCodeAndVatTax(TravelInsuranceVO travelInsuranceVO, HttpServletRequest request) {
		
		List<Object> vatData = Collections.emptyList();
		Integer policyClassCode = null;
		Integer travelPolicyTypeCode = null;
		policyClassCode = Integer.valueOf( Utils.getSingleValueAppConfig("TRAVEL_CLASS_CODE"));
		travelPolicyTypeCode = Integer.valueOf( Utils.getSingleValueAppConfig("POLICY_TYPE_TRAVEL"));
		
		if(Utils.isEmpty(travelInsuranceVO.getVatCode()) && Utils.isEmpty(travelInsuranceVO.getPremiumVO())){
			travelInsuranceVO.setPremiumVO(new PremiumVO());
			
			vatData =  DAOUtils.VatCodeAndVatRateForTravel(policyClassCode, travelPolicyTypeCode, travelPolicyTypeCode, 
																				SvcConstants.SC_PRM_COVER_VAT_TAX);
			if(!Utils.isEmpty(vatData)) {
				travelInsuranceVO.setVatCode(Integer.parseInt(vatData.get(1).toString()));
				travelInsuranceVO.setVatTaxPerc(Double.parseDouble(vatData.get(0).toString()));
				if(!Utils.isEmpty(travelInsuranceVO.getPremiumVO())){
					travelInsuranceVO.getPremiumVO().setVatCode(Integer.parseInt(vatData.get(1).toString()));
					travelInsuranceVO.getPremiumVO().setVatTaxPerc(Double.parseDouble(vatData.get(0).toString()));
				}
			}		
		}
		return travelInsuranceVO;
	}
	
	public static  void updateVATPremium(TravelInsuranceVO travelInsuranceVO){
		
		populatePremium(travelInsuranceVO,null);
				
		if(!Utils.isEmpty(travelInsuranceVO.getPremiumVO()) && !Utils.isEmpty(travelInsuranceVO.getCommonVO())){
			
			Long policyId = travelInsuranceVO.getCommonVO().getPolicyId();
			Double vatTax = travelInsuranceVO.getPremiumVO().getVatTax();
		
			DAOUtils.updateVATPremium(vatTax, policyId,travelInsuranceVO.getCommonVO().getEndtId());
		}
	}
}


