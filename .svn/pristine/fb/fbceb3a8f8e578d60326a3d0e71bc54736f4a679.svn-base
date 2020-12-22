package com.rsaame.pas.b2b.ws.mapper;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.User;
import com.rsaame.kaizen.framework.model.RSAUser;
import com.rsaame.pas.b2b.ws.util.WSAppUtils;
import com.rsaame.pas.b2b.ws.util.WSRequestMapperUtils;
import com.rsaame.pas.b2b.ws.vo.CreateSBSQuoteRequest;
import com.rsaame.pas.b2c.cmn.constants.AppConstants;
import com.rsaame.pas.b2c.cmn.utils.CommonUtils;
import com.rsaame.pas.b2c.controllers.SBSQuotationController;
import com.rsaame.pas.b2c.user.B2CRSAUserWrapper;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.kaizen.vo.RSAUserWrapper;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.bus.AdditionalInsuredInfoVO;
import com.rsaame.pas.vo.bus.AddressVO;
import com.rsaame.pas.vo.bus.AuthenticationInfoVO;
import com.rsaame.pas.vo.bus.ClaimsSummaryVO;
import com.rsaame.pas.vo.bus.GeneralInfoVO;
import com.rsaame.pas.vo.bus.InsuredVO;
import com.rsaame.pas.vo.bus.LocationAddressVO;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.bus.ReferralLocKey;
import com.rsaame.pas.vo.bus.ReferralVO;
import com.rsaame.pas.vo.bus.RiskGroupingLevel;
import com.rsaame.pas.vo.bus.SchemeVO;
import com.rsaame.pas.vo.bus.SectionVO;
import com.rsaame.pas.vo.bus.SourceOfBusinessVO;
import com.rsaame.pas.web.UserProfileHandler;

/* This mapper is to map the SBS CreateQuote Request to existing object of PolicyVo.
 * All fields in PolicyVO needs to be identified and mapped here.
 * Any default value setting should be mapped here.
 * Any value from DB needs to be queried here. 
*/

public class SBSCreateQuoteRequestMapper   {

	private final static Logger LOGGER = Logger.getLogger(SBSCreateQuoteRequestMapper.class);

	public void mapRequestToPolicyVO(Object source1, Object target,HttpServletRequest request) {

		LOGGER.info("Entered mapRequestToVO() method");
		
		if (source1!=null) {
			
			LOGGER.info("requestObj is NOT null ");
		}
		if (target!=null) {
			
			LOGGER.info("valueObj is NOT null ");
		}
		
		

		CreateSBSQuoteRequest createSBSQuoteRequest = (CreateSBSQuoteRequest) source1;
		PolicyVO policyVO = (PolicyVO) target;
		
		LocationVO locationVO = new LocationVO();

		//Initialize all associated objects here.
		initializePolicyVoObjects (policyVO,request);
		
		initializeLocationVoObjects(locationVO,request);

		mapLoggedInUser(policyVO);

		policyVO.setIsQuote(true);
		policyVO.setProcessedDate(new Date());
		policyVO.setPolicyTerm(1);
		policyVO.setIsPrepackaged(new Boolean(false));
		
		try{
			Date effDate = new SimpleDateFormat(com.Constant.CONST_YYYY_MM_DD).parse(createSBSQuoteRequest.getPolicySchedule().getEffectiveDate());
			LOGGER.info("For policyVO Policy Effective Date is " + effDate );
			policyVO.setPolEffectiveDate(effDate);
			
			}
		catch (Exception e) {
			LOGGER.info("Exception in parsing effective dat_1");
			e.printStackTrace();
		}
		
			try {
			Date effDate = new SimpleDateFormat(com.Constant.CONST_YYYY_MM_DD)
					.parse(createSBSQuoteRequest.getPolicySchedule().getEffectiveDate());
			LOGGER.info("For policyVO Policy Effective Date is " + effDate);
			policyVO.setPolEffectiveDate(effDate);

		} catch (Exception e) {
			LOGGER.info("Exception in parsing effective dat_2");
			e.printStackTrace();
		}

		try {
			Date expDate = null;
			Calendar expiryDate = Calendar.getInstance();
			if (policyVO.getPolEffectiveDate() != null && createSBSQuoteRequest.getPolicySchedule().getExpirationDate() == null) {
				Date effectiveDate = WSAppUtils.getEffectiveDate(policyVO.getPolEffectiveDate());
				int totalDays = WSAppUtils.isLeapYear(policyVO.getPolEffectiveDate())
						? AppConstants.NO_OF_DAYS_LEAP_YEAR
						: AppConstants.NO_OF_DAYS_YEAR;
				expiryDate.setTime(effectiveDate);
				expiryDate.add(Calendar.DATE, totalDays - 1);
			}

			if (createSBSQuoteRequest.getPolicySchedule().getExpirationDate()!=null) {
				expDate = new SimpleDateFormat(com.Constant.CONST_YYYY_MM_DD)
						.parse(createSBSQuoteRequest.getPolicySchedule().getExpirationDate());
				LOGGER.info("For policyVO Policy Expiry Date is " + expDate);
				policyVO.setPolExpiryDate(expDate);
			} else {
				LOGGER.info("For policyVO Policy Expiry Date is(calulated ) " + expiryDate.getTime());
				policyVO.setPolExpiryDate(expiryDate.getTime());

			}

		} catch (Exception e) {
			LOGGER.info("Exception in parsing effective dat_3");
			e.printStackTrace();
		}
		
		policyVO.setPolVatRegNo(createSBSQuoteRequest.getPolicyHolder().getCompany().getCompanyVATRegistrationNumber());
		policyVO.setPolVATCode(1);//Hardcode 1
		policyVO.setPolicyTypeCode(50); //hardcode - 50
		
		policyVO.getGeneralInfo().setIsChannelChanged(false); //Hardcode

		mapGeneralInfoVo(createSBSQuoteRequest,policyVO);
		mapSchemeVo(createSBSQuoteRequest,policyVO);
		mapAuthenticationVO(createSBSQuoteRequest,policyVO);
		mapLocationVO(createSBSQuoteRequest,locationVO,request);
		mapRisksSectionVO(createSBSQuoteRequest,policyVO,request,locationVO); //uncomment only when a section is completely mapped.
	}

	public void mapLocationVO(CreateSBSQuoteRequest createSBSQuoteRequest,LocationVO locationVO,HttpServletRequest request) {
		
		
		//mapping LocationVO.LocationAddressVO
			if(createSBSQuoteRequest.getPolicyHolder().getContactMethods().getPostMailContact().get(0).getAddressLine1()!=null && !createSBSQuoteRequest.getPolicyHolder().getContactMethods().getPostMailContact().get(0).getAddressLine1().equals("")) {
               
				locationVO.getAddress().setStreetName(createSBSQuoteRequest.getPolicyHolder().getContactMethods().getPostMailContact().get(0).getAddressLine1().getValue());
               }
			
		//locationVO.getAddress().setArea(""); //sample hardcode
		locationVO.getAddress().setBlockNo(""); //sample hardcode
		locationVO.getAddress().setPoBoxChanged(false); //sample hardcode
		locationVO.getAddress().setWayNo("");//sample hardcode
		if(!Utils.isEmpty(createSBSQuoteRequest.getPolicyHolder().getContactMethods().getPostMailContact().get(0).getPostalCode())) {
			locationVO.getAddress().setPoBox(createSBSQuoteRequest.getPolicyHolder().getContactMethods().getPostMailContact().get(0).getPostalCode()); //clarification recieved
		}
		else {
			locationVO.getAddress().setPoBox("00000"); //clarification recieved

		}
				//locationVO.getAddress().setStreetName(""); //sample hardcode
		locationVO.getAddress().setBuildingName("Dubai"); //This is set to actual location at the below 
		locationVO.getAddress().setFloor(""); //sample hardcode
		locationVO.getAddress().setOfficeShopNo(""); //sample hardcode
		locationVO.getAddress().setLocOverrideJur(1);
		locationVO.getAddress().setLocOverrideTer(4); //To fix territory drop down issue in edit mode
		
		//mapping LocationVO
		locationVO.setAddress(locationVO.getAddress());
		locationVO.setOccTradeGroup(Integer.parseInt(createSBSQuoteRequest.getLiabilityInformation().getbusinessType().getCode())); ////JLT should add a new businessType tag in the req JSON
		
		//16002 -- location -- Others
		locationVO.setDirectorate(16002); //First set to others. Later based on logic set to actual. ss_v_mas_lookup  where category like '%DIRECTORATE%'; 
		
        //55035 -- freezone = Others
		//locationVO.setFreeZone(createSBSQuoteRequest.getLiabilityInformation().getNameOfFreeZoneAuthority().getCode()); //ss_v_mas_lookup  where category like '%FREE_ZONE%' and level_1 = 204; 
		locationVO.setFreeZone("55035"); //First set to others. Later based on logic set to actual -- ss_v_mas_lookup  where category like '%FREE_ZONE%' and level_1 = 204; 
		//locationVO.setFreeZoneOthers(createSBSQuoteRequest.getLiabilityInformation().getNameOfFreeZoneAuthority().getCode()); //ss_v_mas_lookup  where category like '%FREE_ZONE%' and level_1 = 204; 
		
		locationVO.setEmirates(4); //sample hardcode
		locationVO.setValiditySrtDt(new Date()); //sample hardcode
		//locationVO.setHazardLevel(3L); //sample hardcode
		locationVO.setRiskGroupId("L1");//sample hardcode
		locationVO.setModified(false); //sample hardcode
		locationVO.setActiveStatus("1"); //sample hardcode
		
		
		if(createSBSQuoteRequest.getLiabilityInformation().getNameOfFreeZoneAuthority()!=null && createSBSQuoteRequest.getLiabilityInformation().getNameOfFreeZoneAuthority().getCode()!=null && !createSBSQuoteRequest.getLiabilityInformation().getNameOfFreeZoneAuthority().getCode().equals("")
				&& createSBSQuoteRequest.getLiabilityInformation().getFreeZone()!=null) {
				if(createSBSQuoteRequest.getLiabilityInformation().getFreeZone()) {
					locationVO.setFreeZone(createSBSQuoteRequest.getLiabilityInformation().getNameOfFreeZoneAuthority().getCode());
					if(locationVO.getFreeZone().equals("55010")) locationVO.setDirectorate(1045);
					if(locationVO.getFreeZone().equals("55012")) locationVO.setDirectorate(1052);
					if(locationVO.getFreeZone().equals("56000")) locationVO.setDirectorate(1017);
				}
				else {
					locationVO.setDirectorate(Integer.parseInt(createSBSQuoteRequest.getLiabilityInformation().getNameOfFreeZoneAuthority().getCode())); //ss_v_mas_lookup  where category like '%FREE_ZONE%' and level_1 = 204;
					if(locationVO.getDirectorate()==1045) locationVO.setFreeZone("55010");
					if(locationVO.getDirectorate()==1052) locationVO.setFreeZone("55012");
					if(locationVO.getDirectorate()==1017) locationVO.setFreeZone("56000");
				}
			

		}

	/*	//If location is set to 'Others', then check if it can be mapped from Freezone. Refer mapping email trail
		if(locationVO.getDirectorate() == 16002) {
			
			if(locationVO.getFreeZone().equals("55010")) locationVO.setDirectorate(1045);
			if(locationVO.getFreeZone().equals("55012")) locationVO.setDirectorate(1052);
			if(locationVO.getFreeZone().equals("56000")) locationVO.setDirectorate(1017);
				
		}
		//If Freezone is set to 'Others', then check if it can be mapped from Location. Refer mapping email trail
		if(locationVO.getFreeZone().equals("55035")) {
			
			if(locationVO.getDirectorate()==1045) locationVO.setFreeZone("55010");
			if(locationVO.getDirectorate()==1052) locationVO.setFreeZone("55012");
			if(locationVO.getDirectorate()==1017) locationVO.setFreeZone("56000");

		}
		commented because now we are getting location and freezone in single field based on the flag 
		*/
		
		//locationVO.getAddress().setBuildingName(SvcUtils.getLookUpDescription("DIRECTORATE", "ALL", "ALL", locationVO.getDirectorate())); //required for batch
		String addressLine1 = "";
        String addressLine2 = "";

        if(createSBSQuoteRequest.getPolicyHolder().getContactMethods().getPostMailContact().get(0).getAddressLine1()!=null && !createSBSQuoteRequest.getPolicyHolder().getContactMethods().getPostMailContact().get(0).getAddressLine1().equals("")) {
               
               addressLine1 = createSBSQuoteRequest.getPolicyHolder().getContactMethods().getPostMailContact().get(0).getAddressLine1().getValue();
               }
        
        if(createSBSQuoteRequest.getPolicyHolder().getContactMethods().getPostMailContact().get(0).getAddressLine2()!=null && !createSBSQuoteRequest.getPolicyHolder().getContactMethods().getPostMailContact().get(0).getAddressLine2().equals("")) {
               
               addressLine2 = createSBSQuoteRequest.getPolicyHolder().getContactMethods().getPostMailContact().get(0).getAddressLine2();
               }
       
        //locationVO.getAddress().setBuildingName(addressLine1 + " " + addressLine2); //required for batch
        locationVO.getAddress().setBuildingName(addressLine2); //required for batch
	}

	
	public void mapGeneralInfoVo(CreateSBSQuoteRequest createSBSQuoteRequest,PolicyVO policyVO) {

		//generalInfo (GeneralInfoVO)
	
	//	policyVO.getGeneralInfo().setJurisdiction(createSBSQuoteRequest.getPolicyData().getJurisdiction());
		policyVO.getGeneralInfo().setVatCode(1); //Hardcode
		policyVO.getGeneralInfo().setIsChannelChanged(false); //Hardcode
		
		//insured (InsuredVO)
		
		//policyVO.getGeneralInfo().getInsured().setName(createSBSQuoteRequest.getPolicyHolder().getPrimaryContact().getGivenName());
		policyVO.getGeneralInfo().getInsured().setName(createSBSQuoteRequest.getPolicyHolder().getCompany().getName());
		policyVO.getGeneralInfo().getInsured().setMobileNo(createSBSQuoteRequest.getPolicyHolder().getContactMethods().getPhoneContacts().get(0).getInternationalFullNumber());
		policyVO.getGeneralInfo().getInsured().setPhoneNo(createSBSQuoteRequest.getPolicyHolder().getContactMethods().getPhoneContacts().get(1).getInternationalFullNumber());
		policyVO.getGeneralInfo().getInsured().setEmailId(createSBSQuoteRequest.getPolicyHolder().getContactMethods().getEmailContact().get(0).getUrl());
		policyVO.getGeneralInfo().getInsured().setUpdateMaster(false);
		policyVO.getGeneralInfo().getInsured().setTradeLicenseNo(""); //will be updated later with new json
		if(createSBSQuoteRequest.getPolicyHolder().getCompany().getRevenue()!=null && createSBSQuoteRequest.getPolicyHolder().getCompany().getRevenue().getAmount()!=null) {
		policyVO.getGeneralInfo().getInsured().setTurnover(new Long(createSBSQuoteRequest.getPolicyHolder().getCompany().getRevenue().getAmount())); //will be updated later with new json
		}
		policyVO.getGeneralInfo().getInsured().setNoOfEmployees(createSBSQuoteRequest.getPolicyHolder().getCompany().getNumberOfEmployee());
		policyVO.getGeneralInfo().getInsured().setVatRegNo(createSBSQuoteRequest.getPolicyHolder().getCompany().getCompanyVATRegistrationNumber());
		policyVO.getGeneralInfo().getInsured().setCcgCode(11); //By default 11 - given by supreetha
		policyVO.getGeneralInfo().getInsured().setBusType(1); //Hardcoded - as confirmed by RSA business - Always Representative Office
		policyVO.getGeneralInfo().getInsured().setPolBusType(1); //Hardcoded - as confirmed by RSA business - Always Representative Office
		policyVO.getGeneralInfo().getInsured().setBusDescription(createSBSQuoteRequest.getLiabilityInformation().getBusinessActivity().getCode()); //Hardcoded for time being - until we get final json.
		policyVO.getGeneralInfo().getInsured().setCity(Integer.parseInt(createSBSQuoteRequest.getPolicyHolder().getContactMethods().getPostMailContact().get(0).getCity().getCode()));

		//insured (InsuredVO).AddressVO
		if(!Utils.isEmpty(createSBSQuoteRequest.getPolicyHolder().getContactMethods().getPostMailContact().get(0).getPostalCode())){
			policyVO.getGeneralInfo().getInsured().getAddress().setPoBox(createSBSQuoteRequest.getPolicyHolder().getContactMethods().getPostMailContact().get(0).getPostalCode());
		}
		else {
			policyVO.getGeneralInfo().getInsured().getAddress().setPoBox("00000");
		}
		
		policyVO.getGeneralInfo().getInsured().getAddress().setCountry(Integer.parseInt(createSBSQuoteRequest.getPolicyHolder().getContactMethods().getPostMailContact().get(0).getCountry()));
		
		String addressLine1 = "";
		String addressLine2 = "";

		if(createSBSQuoteRequest.getPolicyHolder().getContactMethods().getPostMailContact().get(0).getAddressLine1()!=null && !createSBSQuoteRequest.getPolicyHolder().getContactMethods().getPostMailContact().get(0).getAddressLine1().equals("")) {
			
			addressLine1 = createSBSQuoteRequest.getPolicyHolder().getContactMethods().getPostMailContact().get(0).getAddressLine1().getValue();
			
		}
		
		if(createSBSQuoteRequest.getPolicyHolder().getContactMethods().getPostMailContact().get(0).getAddressLine2()!=null && !createSBSQuoteRequest.getPolicyHolder().getContactMethods().getPostMailContact().get(0).getAddressLine2().equals("")) {
			
			addressLine2 = createSBSQuoteRequest.getPolicyHolder().getContactMethods().getPostMailContact().get(0).getAddressLine2();
			
		}
		

		policyVO.getGeneralInfo().getInsured().getAddress().setAddress(addressLine2);  // removing address line 1 because address line 1 is not complete address, its area drop down value from JLT portal
		policyVO.getGeneralInfo().getInsured().getAddress().setTerritory(4);//Hardcoded for UAE
		
		//additionalInfo (AdditionalInsuredInfoVO)
		policyVO.getGeneralInfo().getAdditionalInfo().setIssueLoc(20);//Hardcoded Dubai location code
		
		//sourceOfBus (SourceOfBusinessVO)
		policyVO.getGeneralInfo().getSourceOfBus().setSourceOfBusiness(12); //Taken from ss_v_mas_lookup
		policyVO.getGeneralInfo().getSourceOfBus().setDistributionChannel(4); //Taken from ss_v_mas_lookup
		/*policyVO.getGeneralInfo().getSourceOfBus().setBrokerName(83); // Hardcoded for InsuredDirect broker (JLT)
		policyVO.getGeneralInfo().getSourceOfBus().setPartnerId("83"); //Hardcoded for InsuredDirect broker (JLT)
*/		
		//Claims Information
		//if(createSBSQuoteRequest.getLiabilityInformation().getClaimInformation()!=null && createSBSQuoteRequest.getLiabilityInformation().getClaimInformation().getValueOfClaims()!=null && createSBSQuoteRequest.getLiabilityInformation().getClaimInformation().getValueOfClaims()>0) {
		
		//}
	//	if(createSBSQuoteRequest.getLiabilityInformation()!=null && createSBSQuoteRequest.getLiabilityInformation().getClaimInformation()!=null && createSBSQuoteRequest.getLiabilityInformation().getClaimInformation().getNumberOfClaims()!=null && createSBSQuoteRequest.getLiabilityInformation().getClaimInformation().getNumberOfClaims()>0) {
			
	//	}
		//Claims Information details
		if(createSBSQuoteRequest.getLiabilityInformation().getClaimInformation()!=null) {
			if(createSBSQuoteRequest.getLiabilityInformation().getClaimInformation().getNumberOfClaims()!=null && createSBSQuoteRequest.getLiabilityInformation().getClaimInformation().getNumberOfClaims()>0) {
				policyVO.getGeneralInfo().getClaimsHistory().setTelexNo(createSBSQuoteRequest.getLiabilityInformation().getClaimInformation().getNumberOfClaims());
			}
			if(createSBSQuoteRequest.getLiabilityInformation().getClaimInformation().getValueOfClaims()!=null && createSBSQuoteRequest.getLiabilityInformation().getClaimInformation().getValueOfClaims()>0) {
			policyVO.getGeneralInfo().getClaimsHistory().setLossExpQuantum(new BigDecimal(createSBSQuoteRequest.getLiabilityInformation().getClaimInformation().getValueOfClaims()));
			}
			if(createSBSQuoteRequest.getLiabilityInformation().getClaimInformation().getRemarks()!=null) {
			policyVO.getGeneralInfo().getAdditionalInfo().setRemarks(createSBSQuoteRequest.getLiabilityInformation().getClaimInformation().getRemarks());
			}
			}
			if(createSBSQuoteRequest.getLiabilityInformation().getClaimInformation()==null) {
				policyVO.getGeneralInfo().getClaimsHistory().setTelexNo(0);
			policyVO.getGeneralInfo().getClaimsHistory().setLossExpQuantum(new BigDecimal(0));
			policyVO.getGeneralInfo().getAdditionalInfo().setRemarks("");
			}
		policyVO.getGeneralInfo().setIntAccExecCode(Integer.parseInt(policyVO.getLoggedInUser().getUserId()));
		policyVO.getGeneralInfo().getInsured().setCity(Integer.parseInt(createSBSQuoteRequest.getPolicyHolder().getContactMethods().getPostMailContact().get(0).getCity().getCode()));
		policyVO.setDefaultClassCode(2);
		policyVO.getGeneralInfo().setCustomerSaveReq("N");
		policyVO.getGeneralInfo().setNewCustomer("Y");
		policyVO.getGeneralInfo().getAdditionalInfo().setProcessingLoc(20);
		
	}
	
	public void mapSchemeVo(CreateSBSQuoteRequest createSBSQuoteRequest,PolicyVO policyVO){
		
		/*policyVO.getScheme().setSchemeCode(1016); //Hardcoded
		policyVO.getScheme().setSchemeName("INSURE DIR Flexi SBS Scheme"); //Hardcoded
		policyVO.getScheme().setTariffCode(204); //Hardcoded
		policyVO.getScheme().setTariffName("SBS - Flexi"); //Hardcoded
*/		policyVO.getScheme().setPolicyType("50"); //Hardcoded
		policyVO.getScheme().setPolicyCode(50); //Hardcoded
//		policyVO.getScheme().setId(1016);
		
	try {
			Date effDate = new SimpleDateFormat(com.Constant.CONST_YYYY_MM_DD)
					.parse(createSBSQuoteRequest.getPolicySchedule().getEffectiveDate());
			LOGGER.info("For schemeVO Policy Effective Date is " + effDate);
			policyVO.getScheme().setEffDate(effDate);
		} catch (Exception e) {
			LOGGER.info("Exception in parsing effective dat_4");
			e.printStackTrace();
		}

		try {
			Date expDate = null;
			Calendar expiryDate = Calendar.getInstance();
			if (policyVO.getPolEffectiveDate() != null && createSBSQuoteRequest.getPolicySchedule().getExpirationDate() == null) {
				Date effectiveDate = WSAppUtils.getEffectiveDate(policyVO.getPolEffectiveDate());
				int totalDays = WSAppUtils.isLeapYear(policyVO.getPolEffectiveDate())
						? AppConstants.NO_OF_DAYS_LEAP_YEAR
						: AppConstants.NO_OF_DAYS_YEAR;
				expiryDate.setTime(effectiveDate);
				expiryDate.add(Calendar.DATE, totalDays - 1);
			}
			
			if (createSBSQuoteRequest.getPolicySchedule().getExpirationDate() != null) {
				expDate = new SimpleDateFormat(com.Constant.CONST_YYYY_MM_DD)
						.parse(createSBSQuoteRequest.getPolicySchedule().getExpirationDate());
				LOGGER.info("For policyVO Policy Expiry Date is " + expDate);
				policyVO.getScheme().setExpiryDate(expDate);
			} else {
				LOGGER.info("For policyVO Policy Expiry Date is(calulated) " + expiryDate.getTime());
				policyVO.getScheme().setExpiryDate(expiryDate.getTime());

			}

		} catch (Exception e) {
			LOGGER.info("Exception in parsing effective dat_5");
			e.printStackTrace();
		}
	}
	
	public void mapAuthenticationVO(CreateSBSQuoteRequest createSBSQuoteRequest,PolicyVO policyVO) {
		
		policyVO.getAuthInfoVO().setLicensedBy(Integer.parseInt(Utils.getSingleValueAppConfig("JLT_DEFAULT_LICENCED_BY"))); //Hardcoded from t_mas_scheme table
		policyVO.getAuthInfoVO().setApprovedBy(Integer.parseInt(Utils.getSingleValueAppConfig("JLT_DEFAULT_APPROVED_BY"))); //Hardcoded from t_mas_scheme table
		policyVO.getAuthInfoVO().setLocationCode(Integer.parseInt(Utils.getSingleValueAppConfig("DEPLOYED_LOCATION")));//Hardcode 20 for Dubai
		policyVO.getAuthInfoVO().setExtAccExecCode(Integer.parseInt(policyVO.getLoggedInUser().getUserId()));
		policyVO.getAuthInfoVO().setIntAccExecCode(Integer.parseInt(policyVO.getLoggedInUser().getUserId()));
		
	}
	
	public void mapLoggedInUser(PolicyVO policyVO){
		
		policyVO.getLoggedInUser().setUserId(policyVO.getLoggedInUser().getUserId());
		
		
	}

	public void mapRisksSectionVO(CreateSBSQuoteRequest createSBSQuoteRequest,PolicyVO policyVO,HttpServletRequest request,LocationVO locationVO){
		

		SectionRequestMapper sectionRequestMapper = new SectionRequestMapper();
		
		sectionRequestMapper.mapRequestToVO(createSBSQuoteRequest, policyVO,locationVO,request);
	}

	
	public void initializePolicyVoObjects (PolicyVO policyVO,HttpServletRequest request){
		
	
		
		/*if(Utils.isEmpty(policyVO.getLoggedInUser())) {
		
			UserProfile userProfile = WSAppUtils.getWSUserProfileVo("jltuser_idb");
	
			request.getSession().setAttribute( AppConstants.SESSION_USER_PROFILE_VO, userProfile);
			
			policyVO.setLoggedInUser(userProfile);
		}*/
		
		if(Utils.isEmpty(policyVO.getGeneralInfo())) {
			policyVO.setGeneralInfo(new GeneralInfoVO());
		}
		if(Utils.isEmpty(policyVO.getGeneralInfo().getInsured())) {
			policyVO.getGeneralInfo().setInsured(new InsuredVO());
		}
		if(Utils.isEmpty(policyVO.getGeneralInfo().getInsured().getAddress())) {
			policyVO.getGeneralInfo().getInsured().setAddress(new AddressVO());;
		}
		
		if(Utils.isEmpty(policyVO.getGeneralInfo().getAdditionalInfo())) {
			policyVO.getGeneralInfo().setAdditionalInfo(new AdditionalInsuredInfoVO());
		}
		if(Utils.isEmpty(policyVO.getGeneralInfo().getSourceOfBus())) {
			policyVO.getGeneralInfo().setSourceOfBus(new SourceOfBusinessVO());
		}
		if(Utils.isEmpty(policyVO.getGeneralInfo().getClaimsHistory())) {
			policyVO.getGeneralInfo().setClaimsHistory(new ClaimsSummaryVO());
		}
		if(Utils.isEmpty(policyVO.getScheme())) {
			policyVO.setScheme(new SchemeVO());
		}
		if(Utils.isEmpty(policyVO.getPremiumVO())) {
			policyVO.setPremiumVO(new PremiumVO());
		}
		
		if(Utils.isEmpty(policyVO.getAuthInfoVO())) {
			policyVO.setAuthInfoVO(new AuthenticationInfoVO());
		}
		if(Utils.isEmpty(policyVO.getRiskDetails())) {
			policyVO.setRiskDetails(new com.mindtree.ruc.cmn.utils.List<SectionVO>(SectionVO.class));
		}
		
		if(Utils.isEmpty(policyVO.getMapReferralVO())) {
			policyVO.setMapReferralVO(new com.mindtree.ruc.cmn.utils.Map<ReferralLocKey,ReferralVO>(ReferralLocKey.class,ReferralVO.class));
		}
		if(Utils.isEmpty(policyVO.getAppFlow())) {
			policyVO.setAppFlow(Flow.CREATE_QUO); //For Create Quote
		}
		
	}
	
	public void initializeLocationVoObjects(LocationVO locationVO,HttpServletRequest request){
		
	
		
		if(Utils.isEmpty(locationVO.getAddress())) {
			locationVO.setAddress(new LocationAddressVO());
		}
	}

	

}
