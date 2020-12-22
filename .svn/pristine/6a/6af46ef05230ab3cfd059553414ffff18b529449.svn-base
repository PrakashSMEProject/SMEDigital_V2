package com.rsaame.pas.b2c.ws.handler;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.transaction.SystemException;

import org.apache.log4j.Logger;

import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.b2c.cmn.constants.AppConstants;
import com.rsaame.pas.b2c.cmn.handlers.CommonHandler;
import com.rsaame.pas.b2c.cmn.utils.AppUtils;
import com.rsaame.pas.b2c.cmn.utils.ApplicationContextUtils;
import com.rsaame.pas.b2c.cmn.utils.CommonUtils;
import com.rsaame.pas.b2c.exception.ValidationException;
import com.rsaame.pas.b2c.homeInsurance.HomeInsuranceSvcHandler;
import com.rsaame.pas.b2c.homeInsurance.IHomeInsuranceSvcHandler;
import com.rsaame.pas.b2c.travelInsurance.TravelInsuranceHandler;
import com.rsaame.pas.b2c.ws.beans.CoverDetailsList;
import com.rsaame.pas.b2c.ws.beans.HomeInsuranceDetails;
import com.rsaame.pas.b2c.ws.beans.LookUpResponse;
import com.rsaame.pas.b2c.ws.beans.ProductDetailsResponse;
import com.rsaame.pas.b2c.ws.beans.SearchQuotePolicyRequest;
import com.rsaame.pas.b2c.ws.beans.SearchQuotePolicyResponse;
import com.rsaame.pas.b2c.ws.beans.SendNotificationMailRequest;
import com.rsaame.pas.b2c.ws.beans.SendNotificationMailResponse;
import com.rsaame.pas.b2c.ws.beans.TravelInsuranceDetails;
import com.rsaame.pas.b2c.ws.beans.TravelPackage;
import com.rsaame.pas.b2c.ws.mapper.HomeInsuranceDetailsMapper;
import com.rsaame.pas.b2c.ws.mapper.TravelInsuranceDetailsMapper;
import com.rsaame.pas.cmn.currency.Currency;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.lookup.svc.LookUpService;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.taglib.svc.LoadCoverSvc;
import com.rsaame.pas.vo.app.LookUpListVO;
import com.rsaame.pas.vo.app.LookUpVO;
import com.rsaame.pas.vo.app.MailVO;
import com.rsaame.pas.vo.bus.CoverDetails;
import com.rsaame.pas.vo.bus.CoverDetailsVO;
import com.rsaame.pas.vo.bus.GeneralInfoVO;
import com.rsaame.pas.vo.bus.HomeInsuranceVO;
import com.rsaame.pas.vo.bus.InsuredVO;
import com.rsaame.pas.vo.bus.LOB;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.SchemeVO;
import com.rsaame.pas.vo.bus.TravelInsuranceVO;
import com.rsaame.pas.vo.bus.TravelPackageVO;
import com.rsaame.pas.vo.cmn.CommonVO;
import com.rsaame.pas.web.UserProfileHandler;

/**
 * @author m1020637
 *
 */
public class CommonServiceHandler {
	
	
	/** Logger instance */
	private static final Logger logger = Logger.getLogger(CommonServiceHandler.class);
	
	/**
	 * @param lob
	 * @return
	 */
	public LookUpResponse getLookupDetails(String lob) throws ValidationException{
		
		LookUpResponse response = new LookUpResponse();
		
        if(lob.equalsIgnoreCase(LOB.TRAVEL.toString())){
        	getTravelLookupDetails(response);
        	
        } else if(lob.equalsIgnoreCase(LOB.HOME.toString())){
        	getHomeLookupDetails(response);
        	
        } else {
        	throw new ValidationException("Provide a valid LOB");
        }
        
        return response;
		
	}
	
	public ProductDetailsResponse getProductDetails(String lob,Long tariffCd,Long schemeCd){
		
		LoadCoverSvc hw = (LoadCoverSvc) ApplicationContextUtils.getBean("loadCoverSvc");
		
		SchemeVO schemeVO = new SchemeVO();
		schemeVO.setTariffCode(tariffCd.intValue());
		schemeVO.setSchemeCode(schemeCd.intValue());
		
		
		ProductDetailsResponse productDetailsResponse = new ProductDetailsResponse();
		TravelInsuranceDetailsMapper travelInsuranceDetailsMapper = new TravelInsuranceDetailsMapper();
		
		if (lob.equalsIgnoreCase("travel")) {
			schemeVO.setId(5);
			List<TravelPackageVO> packageList =  hw.getTravelPackages(schemeVO);
			TravelPackage travelPackage = travelInsuranceDetailsMapper.mapTravelPackage(packageList);
			productDetailsResponse.setTravelPackage(travelPackage);
						
		} else if (lob.equalsIgnoreCase("home")){
			schemeVO.setPolicyType("7");
			CoverDetails coverLst = hw.getRiskCoverDetails(schemeVO);
			
			CoverDetailsList coverDetailsList = travelInsuranceDetailsMapper.mapCoversList(coverLst);
			productDetailsResponse.setCoverDetailsList(coverDetailsList);
			
			
		} else {
			throw new com.rsaame.pas.b2c.exception.ValidationException("Invalid LOB");
		}
			
		return productDetailsResponse;
	}
	
	/**
	 * @param response
	 */
	private void getTravelLookupDetails(LookUpResponse response) {
		
		ArrayList typeOfTrip = new ArrayList();
		ArrayList relation = new ArrayList();
		ArrayList nationality = new ArrayList();
		LookUpVO reqLookUpVO = new LookUpVO();
		LookUpListVO lookUpListVO = null;
		
		LookUpService hw = (LookUpService) ApplicationContextUtils.getBean("lookUpService");
		
		//trip details
		reqLookUpVO.setCategory("PAS_TR_PRD");
		reqLookUpVO.setLevel1("ALL");
		reqLookUpVO.setLevel2("ALL");
		lookUpListVO = (LookUpListVO)hw.invokeMethod(com.Constant.CONST_GETLISTOFDESCRIPTION, reqLookUpVO);
		
		if (!CommonUtils.isEmpty(lookUpListVO.getLookUpList())) {
			for (LookUpVO lookUpVO : lookUpListVO.getLookUpList()) {
				typeOfTrip.add(lookUpVO.getDescription()+"|"+lookUpVO.getCode());
			}
			response.setTypeOfTripList(typeOfTrip);
		}
        
        //relation
		reqLookUpVO.setCategory("PAS_RELATN");
		reqLookUpVO.setLevel1("ALL");
		reqLookUpVO.setLevel2("ALL");
		lookUpListVO = (LookUpListVO)hw.invokeMethod(com.Constant.CONST_GETLISTOFDESCRIPTION, reqLookUpVO);

		if (!CommonUtils.isEmpty(lookUpListVO.getLookUpList())) {
			for (LookUpVO lookUpVO : lookUpListVO.getLookUpList()) {
				relation.add(lookUpVO.getDescription()+"|"+lookUpVO.getCode());
			}
			response.setRelationList(relation);
		}
		
		//nationality
		reqLookUpVO.setCategory("NATIONALITY");
        reqLookUpVO.setLevel1("ALL");
        reqLookUpVO.setLevel2("ALL");
        lookUpListVO = (LookUpListVO)hw.invokeMethod(com.Constant.CONST_GETLISTOFDESCRIPTION, reqLookUpVO);

        if (!CommonUtils.isEmpty(lookUpListVO.getLookUpList())) {
			for (LookUpVO lookUpVO : lookUpListVO.getLookUpList()) {
				nationality.add(lookUpVO.getDescription()+"|"+lookUpVO.getCode());
			}
			response.setNationalityList(nationality);
		}

	}
	
	
	/**
	 * @param response
	 */
	private void getHomeLookupDetails(LookUpResponse response) {
		
		//ArrayList ownerStatus = new ArrayList();			/* commented unused variable - sonar violation fix */
		ArrayList contentValue = new ArrayList();
		//ArrayList personalPossesion = new ArrayList();	/* commented unused variable - sonar violation fix */
		//ArrayList emirates = new ArrayList();				/* commented unused variable - sonar violation fix */
		//ArrayList rewardProg = new ArrayList();			/* commented unused variable - sonar violation fix */
		ArrayList area = new ArrayList();
		//ArrayList propertyType = new ArrayList();			/* commented unused variable - sonar violation fix */
		ArrayList mortgageeName = new ArrayList();
		
		LookUpVO reqLookUpVO = new LookUpVO();
		LookUpListVO lookUpListVO = null;
		
		LookUpService hw = (LookUpService) ApplicationContextUtils.getBean("lookUpService");
		
		//TODO
		//ownerStatus
		/*reqLookUpVO.setCategory("");
		reqLookUpVO.setLevel1("ALL");
		reqLookUpVO.setLevel2("ALL");
		lookUpListVO = (LookUpListVO)hw.invokeMethod(com.Constant.CONST_GETLISTOFDESCRIPTION, reqLookUpVO);

		for(LookUpVO lookUpVO : lookUpListVO.getLookUpList()){
			ownerStatus.add(lookUpVO.getDescription());
		}
		response.setOwnershipStatusList(ownerStatus);*/
		
		//contentValue
		reqLookUpVO.setCategory("PAR_CONTENT_SI");
		reqLookUpVO.setLevel1("ALL");
		reqLookUpVO.setLevel2("ALL");
		lookUpListVO = (LookUpListVO)hw.invokeMethod(com.Constant.CONST_GETLISTOFDESCRIPTION, reqLookUpVO);

		if (!CommonUtils.isEmpty(lookUpListVO.getLookUpList())) {
			for (LookUpVO lookUpVO : lookUpListVO.getLookUpList()) {
				contentValue.add(lookUpVO.getDescription()+"|"+lookUpVO.getCode());
			}
			response.setContentList(contentValue);
		}
		//emirates
		reqLookUpVO.setCategory("PAR_CONTENT_SI");
		reqLookUpVO.setLevel1("ALL");
		reqLookUpVO.setLevel2("ALL");
		lookUpListVO = (LookUpListVO)hw.invokeMethod(com.Constant.CONST_GETLISTOFDESCRIPTION, reqLookUpVO);

		if (!CommonUtils.isEmpty(lookUpListVO.getLookUpList())) {
			for (LookUpVO lookUpVO : lookUpListVO.getLookUpList()) {
				contentValue.add(lookUpVO.getDescription()+"|"+lookUpVO.getCode());
			}
			response.setContentList(contentValue);
		}
		//area
		reqLookUpVO.setCategory("PAS_AREA");
		reqLookUpVO.setLevel1("ALL");
		reqLookUpVO.setLevel2("ALL");
		lookUpListVO = (LookUpListVO)hw.invokeMethod(com.Constant.CONST_GETLISTOFDESCRIPTION, reqLookUpVO);

		if (!CommonUtils.isEmpty(lookUpListVO.getLookUpList())) {
			for (LookUpVO lookUpVO : lookUpListVO.getLookUpList()) {
				area.add(lookUpVO.getDescription()+"|"+lookUpVO.getCode());
			}
			response.setAreaList(area);
		}
		//mortgageeName
		reqLookUpVO.setCategory("PAS_MORTGAGEE_NAME");
		reqLookUpVO.setLevel1("ALL");
		reqLookUpVO.setLevel2("ALL");
		lookUpListVO = (LookUpListVO)hw.invokeMethod(com.Constant.CONST_GETLISTOFDESCRIPTION, reqLookUpVO);

		if (!CommonUtils.isEmpty(lookUpListVO.getLookUpList())) {
			for (LookUpVO lookUpVO : lookUpListVO.getLookUpList()) {
				mortgageeName.add(lookUpVO.getDescription()+"|"+lookUpVO.getCode());
			}
			response.setMortgageeNameList(mortgageeName); 
		}

	}
	
	
	/**
	 * @param request
	 * @return
	 * @throws SystemException 
	 */
	public SearchQuotePolicyResponse searchQuotePolicy(SearchQuotePolicyRequest request,String user) throws Exception {
		SearchQuotePolicyResponse searchQuotePolicyResponse = null;
		
		if (request.getSearchType().equalsIgnoreCase(LOB.TRAVEL.toString())) { //TRAVEL
			
			//add to response
			searchQuotePolicyResponse = new SearchQuotePolicyResponse();
			TravelInsuranceDetails travelInsuranceDetails = retrieveTravelInsurance(request.getIdNumber(),user);
			if (travelInsuranceDetails!=null && 
					travelInsuranceDetails.getGeneralInfo().getInsured().getEmailId().equalsIgnoreCase(request.getEmailId())) {
				searchQuotePolicyResponse
						.setTravelInsuranceDetails(travelInsuranceDetails);
			} else {
				throw new SystemException("Quote not found");
			}
		} else if(request.getSearchType().equalsIgnoreCase(LOB.HOME.toString())){ //HOME
			
			//add to response
			searchQuotePolicyResponse = new SearchQuotePolicyResponse();
			HomeInsuranceDetails homeInsuranceDetails = retrieveHomeInsurance(request.getIdNumber(),user);
			
			if (homeInsuranceDetails!=null && 
					homeInsuranceDetails.getGeneralInfo().getInsured().getEmailId().equalsIgnoreCase(request.getEmailId())) {
				searchQuotePolicyResponse
						.setHomeInsuranceDetails(homeInsuranceDetails);
			} else {
				throw new SystemException("Quote not found");
			}
			
		} else {
			throw new ValidationException("Invalid value for SearchType");
		}
		
		return searchQuotePolicyResponse;
	}
	

	/**
	 * @param idNumber
	 * @return
	 */
	private TravelInsuranceDetails retrieveTravelInsurance(long idNumber,String user) {
		
		TravelInsuranceVO travelInsuranceVO = retrieveTravelInsuranceVO(idNumber,user);
		//map
		TravelInsuranceDetails travelInsuranceDetails = new TravelInsuranceDetails();
		TravelInsuranceDetailsMapper travelInsuranceDetailsMapper = (TravelInsuranceDetailsMapper) ApplicationContextUtils
				.getBean("travelInsuranceDetailsMapper");
		travelInsuranceDetailsMapper.mapTravelInsuranceVOToTraveldetails(
				travelInsuranceVO, travelInsuranceDetails);
		
		return travelInsuranceDetails;
	}
	
	/**
	 * @param idNumber
	 * @return
	 */
	public TravelInsuranceVO retrieveTravelInsuranceVO(long idNumber,String user) {
		
		CommonVO commonVO = new CommonVO();
		UserProfile userProfile = null;
		TravelInsuranceVO travelInsuranceVO = new TravelInsuranceVO();
		TravelInsuranceHandler travelInsHandler = (TravelInsuranceHandler) ApplicationContextUtils
				.getBean("travelInsuranceHandler");
		//user profile
		if(!Utils.isEmpty(user)){
			userProfile = UserProfileHandler.getUserProfileVo(user);
			commonVO.setLoggedInUser(userProfile);
		}
		
		//set search values
		travelInsuranceVO.setQuoteNo(idNumber);
		commonVO.setQuoteNo(idNumber);
		
		travelInsuranceVO.setCommonVO(commonVO);
		//call service to get
		travelInsuranceVO = travelInsHandler.populateTravelInsForSearch(
				travelInsuranceVO, null);
		travelInsuranceVO = (TravelInsuranceVO) TaskExecutor.executeTasks( "TRAVEL_PACKAGE_PREMIUM", travelInsuranceVO );
		
		return travelInsuranceVO;
	}
	

	/**
	 * @param idNumber
	 * @return
	 */
	private HomeInsuranceDetails retrieveHomeInsurance(long idNumber,String user) {
		
		HomeInsuranceVO homeInsuranceVO = retrieveHomeInsuranceVO(idNumber,user);
		//map
		HomeInsuranceDetails homeInsuranceDetails = new HomeInsuranceDetails();
		HomeInsuranceDetailsMapper homeInsuranceDetailsMapper = (HomeInsuranceDetailsMapper) ApplicationContextUtils
		.getBean("homeInsuranceDetailsMapper");
		homeInsuranceDetailsMapper.mapHomeInsuranceDetailsToHomeInsuranceVO(homeInsuranceDetails, homeInsuranceVO);
		
		return homeInsuranceDetails;
	}
	
	/**
	 * @param idNumber
	 * @return
	 */
	public HomeInsuranceVO retrieveHomeInsuranceVO(long idNumber,String user) {
		
		CommonVO commonVO = new CommonVO();
		HomeInsuranceVO homeInsuranceVO = new HomeInsuranceVO();
		IHomeInsuranceSvcHandler homeInsuranceSvcHandler = new HomeInsuranceSvcHandler();
		GeneralInfoVO generalInfo = new GeneralInfoVO();
		InsuredVO insuredVO = new InsuredVO();
		UserProfile userProfile = null;
		//user profile
		if(!Utils.isEmpty(user)){
			userProfile = UserProfileHandler.getUserProfileVo(user);
			commonVO.setLoggedInUser(userProfile);
		}
		
		//set search values
		commonVO.setQuoteNo(idNumber);
		commonVO.setLob(LOB.HOME);
		homeInsuranceVO.setCommonVO(commonVO);
		homeInsuranceVO.setQuoteNo(idNumber);
		generalInfo.setInsured(insuredVO);
		homeInsuranceVO.setGeneralInfo(generalInfo);
		//call service to get
		homeInsuranceVO = (HomeInsuranceVO) homeInsuranceSvcHandler.loadHomeInsuranceDetails(homeInsuranceVO);
		
		if (homeInsuranceVO.getCommonVO() != null) {
			int brokerID = UserProfileHandler.getBrokerDetails(homeInsuranceVO
					.getCommonVO().getCreatedBy());
			//check for broker id match
			if ( userProfile.getRsaUser().getBrokerId() != brokerID) {
				homeInsuranceVO = null;
			}
		}
		
		return homeInsuranceVO;
	}
	
	/**
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
	public SendNotificationMailResponse sendMailNotification(SendNotificationMailRequest request,String contextPath) throws ParseException {
		
		SendNotificationMailResponse sendNotificationMailResponse = new SendNotificationMailResponse();
		//identify home or travel
		if(request.getType().equalsIgnoreCase(LOB.HOME.toString())){
			
			//retrieve policy
			HomeInsuranceVO homeInsuranceVO = retrieveHomeInsuranceVO(request.getIdNumber(),null);
			IHomeInsuranceSvcHandler homeInsuranceSvcHandler = new HomeInsuranceSvcHandler();
			
			//call appropriate trigger
			if(request.getTriggerName().equalsIgnoreCase("Create_Quote")){


				MailVO mailVO = (MailVO) Utils.getBean(com.Constant.CONST_MAILVO);
				homeInsuranceSvcHandler.populatePackagePremium(homeInsuranceVO); // Load the premium back
				String emailContent = AppUtils.getTempalteContentAsString(AppConstants.B2C_STEP1_ONLY_HOME_TEMPLATE);
				if (!Utils.isEmpty(emailContent)) {
					String clickHereLink = AppUtils.constructClickHereURL(homeInsuranceVO.getCommonVO().getQuoteNo(),
							homeInsuranceVO.getGeneralInfo().getInsured().getEmailId(),
							contextPath,homeInsuranceVO.getCommonVO().getLob(),null);
					emailContent = emailContent.replace(AppConstants.B2C_EMAIL_CLICK_HERE_TAG_IDF, clickHereLink);
					emailContent = emailContent.replace(AppConstants.B2C_EMAIL_INSURED_NAME_IDENTIFIER,AppConstants.B2C_DEFAULT_CUST_NAME);
					emailContent = emailContent.replace(AppConstants.B2C_EMAIL_QUOTE_NO_IDENTIFIER, String.valueOf(homeInsuranceVO.getCommonVO().getQuoteNo()));
					emailContent = emailContent.replace(AppConstants.B2C_EMAIL_PREMIUM_IDENTIFIER, AppUtils.getFormattedNumberWithDecimals(homeInsuranceVO.getPremiumVO().getPremiumAmt()));
					mailVO.setToAddress(homeInsuranceVO.getGeneralInfo().getInsured().getEmailId());
					mailVO.setFromAddress(AppConstants.B2C_DEFAULT_FROM_EMAILID);
					mailVO.setSubjectText(AppConstants.B2C_HOME_QUOTE_EMAIL_SUBJECT);
					mailVO.setMailContent(new StringBuilder(emailContent));
					mailVO.setCreatedOn( new Timestamp( Calendar.getInstance().getTime().getTime() ) ); //Setting the current time stamp
					mailVO.setMailType( SvcConstants.MAIL_TYPE_HTML );
					CommonHandler.sendGeneralEmail(mailVO, "QUOTE");
				}

			} else if(request.getTriggerName().equalsIgnoreCase("Complete_Purchase")){
				
				sendEmail((PolicyDataVO)homeInsuranceVO);
			}
			
			
		} else if(request.getType().equalsIgnoreCase(LOB.TRAVEL.toString())){
			
			//retrieve policy
			TravelInsuranceVO travelInsuranceVO = retrieveTravelInsuranceVO(request.getIdNumber(),null);
			
			//call appropriate trigger
			if(request.getTriggerName().equalsIgnoreCase("Create_Quote")){
				
				// Trigger an email on successful quote create start
				MailVO mailVO = (MailVO)Utils.getBean(com.Constant.CONST_MAILVO);
				if (!Utils.isEmpty(travelInsuranceVO.getCommonVO().getPolicyId()) && !Utils.isEmpty(contextPath)) {
					String emailContent = AppUtils.getTempalteContentAsString( AppConstants.B2C_STEP1_ONLY_TRAVEL_TEMPLATE );
					if( !Utils.isEmpty( emailContent ) ){
						emailContent = emailContent.replace(AppConstants.B2C_EMAIL_INSURED_NAME_IDENTIFIER, travelInsuranceVO.getGeneralInfo().getInsured().getName());
						emailContent = emailContent.replace(AppConstants.B2C_EMAIL_QUOTE_NO_IDENTIFIER, String.valueOf(travelInsuranceVO.getCommonVO().getQuoteNo()));
						// Start constructing dynamic URL
						String clickHereTag = AppUtils.constructClickHereURL(travelInsuranceVO.getCommonVO().getQuoteNo(), travelInsuranceVO.getGeneralInfo().getInsured().getEmailId(), contextPath, travelInsuranceVO.getCommonVO().getLob(),null);
						emailContent = emailContent.replace(AppConstants.B2C_EMAIL_CLICK_HERE_TAG_IDF, clickHereTag);
						// End constructing dynamic URL
						mailVO.setFromAddress( AppConstants.B2C_DEFAULT_FROM_EMAILID );
						mailVO.setToAddress( travelInsuranceVO.getGeneralInfo().getInsured().getEmailId() );
						mailVO.setSubjectText( AppConstants.B2C_TRAVEL_QUOTE_EMAIL_SUBJECT );
						mailVO.setSignature( "RSA" );
						mailVO.setCreatedOn( new Timestamp( Calendar.getInstance().getTime().getTime() ) );
						mailVO.setMailType( SvcConstants.MAIL_TYPE_HTML );
						mailVO.setMailContent( new StringBuilder(emailContent) );
						logger.debug("Email Message ::::: "+emailContent);
						if( !CommonHandler.sendGeneralEmail(mailVO, "QUOTE") ){ 
							logger.debug("Create quote email could not be sent.");
						}
					}
				}
			} else if(request.getTriggerName().equalsIgnoreCase("Complete_Purchase")){
				
				sendEmail((PolicyDataVO)travelInsuranceVO);
			}
			
		} else {
			sendNotificationMailResponse.setResponse("Select either Home or Travel as type");
			return sendNotificationMailResponse;
		}
		
		
		//form response and return
		sendNotificationMailResponse.setResponse("Email sent successfully");
		return sendNotificationMailResponse;
	}
	
	private void sendEmail(PolicyDataVO policyDataVO) {
		

		String emailTemplateContent = null;
		MailVO mailVO = (MailVO)Utils.getBean(com.Constant.CONST_MAILVO);
		boolean successFlag = false;
		if (policyDataVO.getCommonVO().getLob().equals(LOB.TRAVEL)) {
			emailTemplateContent = AppUtils.getTempalteContentAsString(AppConstants.B2C_TRAVEL_SUCCESS_POLICY_PURCHASE_TEMPLATE);
			mailVO.setSubjectText(AppConstants.B2C_TRAVEL_POLICY_EMAIL_SUBJECT);
		} else if (policyDataVO.getCommonVO().getLob().equals(LOB.HOME)) {
			emailTemplateContent = AppUtils.getTempalteContentAsString(AppConstants.B2C_HOME_SUCCESS_POLICY_PURCHASE_TEMPLATE);
			mailVO.setSubjectText(AppConstants.B2C_HOME_POLICY_EMAIL_SUBJECT);
		}
		if (!Utils.isEmpty(emailTemplateContent)) {
			emailTemplateContent = emailTemplateContent.replace(AppConstants.B2C_EMAIL_INSURED_NAME_IDENTIFIER, policyDataVO.getGeneralInfo().getInsured().getName());
			StringBuilder emailContent = new StringBuilder(emailTemplateContent);
			mailVO.setFromAddress( AppConstants.B2C_DEFAULT_FROM_EMAILID );
			mailVO.setToAddress( policyDataVO.getGeneralInfo().getInsured().getEmailId() );
			mailVO.setSignature( "RSA" );
			mailVO.setCreatedOn( new Timestamp( Calendar.getInstance().getTime().getTime() ) ); //Setting the current time stamp
			mailVO.setMailType( SvcConstants.MAIL_TYPE_HTML );
			mailVO.setMailContent( new StringBuilder(emailContent) );
			successFlag =  CommonHandler.sendEmail(mailVO, policyDataVO);
		}
		if (!successFlag) {
			logger.debug("Policy documents cannot be sent for policy number - "+policyDataVO.getCommonVO().getPolicyNo());
		}
	

	}
	
	public static PolicyDataVO loadInsuredDetails(PolicyDataVO policyDataVO){
		try{
			//homeInsuranceData.getCommonVO().setLoggedInUser(homeInsuranceData.getCommonVO().getLoggedInUser());
			CommonVO commonVO = new CommonVO();
			commonVO.setIsQuote(true);
			policyDataVO.setCommonVO(commonVO);
			TaskExecutor.executeTasks("VIEW_INSURED_DETAILS_COMMON",policyDataVO);
			/*
				if (!Utils.isEmpty(policyDataVO.getGeneralInfo().getSourceOfBus().getPartnerId())) {
					homeInsuranceData.getGeneralInfo().getSourceOfBus().setPartnerId(policyDataVO.getGeneralInfo().getSourceOfBus().getPartnerId());
					homeInsuranceData.getScheme().setSchemeCode((policyDataVO.getScheme().getSchemeCode()));
					homeInsuranceData.getScheme().setTariffCode((policyDataVO.getScheme().getTariffCode()));
				}*/
		}
		catch(BusinessException be){
			throw new BusinessException(be.getErrorKeysList().get(0), be.getCause(), be.getMessage());
		}
		catch(Exception exp){
			exp.printStackTrace();
		}
		return policyDataVO;
	}

}
