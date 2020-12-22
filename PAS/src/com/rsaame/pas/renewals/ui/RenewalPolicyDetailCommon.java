/**
 * 
 */
package com.rsaame.pas.renewals.ui;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.GrantedAuthorityImpl;

import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.com.svc.CommonOpSvc;
import com.rsaame.pas.dao.cmn.PASStoredProcedure;
import com.rsaame.pas.dao.model.TMasUser;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.kaizen.vo.DefaultSchedulerUser;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.app.MailVO;
import com.rsaame.pas.vo.bus.AuthenticationInfoVO;
import com.rsaame.pas.vo.bus.CoverDetailsVO;
import com.rsaame.pas.vo.bus.GeneralInfoVO;
import com.rsaame.pas.vo.bus.HomeInsuranceVO;
import com.rsaame.pas.vo.bus.InsuredVO;
import com.rsaame.pas.vo.bus.LOB;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.bus.SchemeVO;
import com.rsaame.pas.vo.bus.TravelInsuranceVO;
import com.rsaame.pas.vo.cmn.CommonVO;

/**
 * @author m1019193
 *This class created  the PolicyDataVO object for renewal quote
 */
public class RenewalPolicyDetailCommon  {

	private final static Logger LOGGER = Logger.getLogger(RenewalPolicyDetailCommon.class);
	private final static String GET_CLAIM_COUNT_COMMON = "GET_CLAIM_COUNT_COMMON";	
	private final static String UPDATE_RENEWAL_STATUS_FOR_SOFT_STOP = "UPDATE_RENEWAL_STATUS_FOR_SOFT_STOP";
	private final static String UPDATE_RENEWAL_QUOTE_STATUS_COMMON = "UPDATE_RENEWAL_QUOTE_STATUS_COMMON";
	private final static String GET_CLAIM_DETAILS = "GET_CLAIM_DETAILS";
	private final static String CHECK_FOR_FRAUD_CLAIM = "CHECK_FOR_FRAUD_CLAIM";
	
	private final static String USER_NAME = com.Constant.CONST_OTHER;
	private final static String EMAIL_ID = "Other@ae.rsagroup.com";
	//private final static String password ="test";  //SONARFIX -- 25-apr-2018 -- commented this as this is private and replaced the variable with string value below
	private final static Short COUNTRY_CODE = 0;
	private final static Integer BRANCH = 0;
	private final static Integer EMPLOYEE_ID = 0;
	private final static Short BROKER_ID = 0;
	private final static Long AGENT_ID = 0L;
	private final static Integer DEFAULT_MODULE = 0;
	private final static Integer DEFAULT_APPROVER = 0;
	private final static Integer LOGIN_ATTEMPTS = 0;
	private final static Integer STATUS_ID = 0;
	private final static String PROFILE = "other";
	private final static String USER_ARABIC_NAME =com.Constant.CONST_OTHER;
	private final static String USER_ENG_NAME = com.Constant.CONST_OTHER;
	private final static String MOBILE_NO = "1111111111";
	
	@SuppressWarnings("unchecked")
	public PolicyDataVO createPolicyObject(Object[] renData, Long policyId,Boolean isScheduler, String originApplication)  {
		
		PolicyDataVO policyVO = new PolicyDataVO();			
		Long quotationNo = (Long)renData[0];
		DataHolderVO<Object[]> input = new DataHolderVO<Object[]>();
		//UserProfile up = new UserProfile();
		Object renInputData[] = new Object[2];
		LOGGER.debug("***********renQuotationNo = "+renData[0]);
		LOGGER.debug("***********renPolicyId = "+renData[1]);
		Long renPolicyId = (Long)renData[1];
		
		CommonVO commonVO = new CommonVO();		
		commonVO.setLocCode(Integer.parseInt(renData[2].toString()));
		commonVO.setPolEffectiveDate((Date) renData[4]);
		commonVO.setEndtNo((Long) renData[5]);
		commonVO.setDocCode(Short.valueOf(renData[6].toString()));
		commonVO.setVsd((Date) renData[7]);
		commonVO.setIsQuote(true);
		commonVO.setStatus(AppConstants.QUOTE_PENDING);
		commonVO.setEndtId(0L);
		commonVO.setPolicyId(renPolicyId);
		commonVO.setQuoteNo(quotationNo);
		commonVO.setAppFlow(Flow.CREATE_QUO);
		commonVO.setPolicyNo((Long) renData[10]);
		commonVO.setConcatPolicyNo(renData[11].toString());
		commonVO.setIsRenewals(true);
		
		InsuredVO insuredVO = new InsuredVO();
		if(!Utils.isEmpty(renData[12]))  insuredVO.setVatRegNo(renData[12].toString());
		
		BigDecimalDoubleConverter converter = ConverterFactory.getInstance( BigDecimalDoubleConverter.class, "", "" );
		PremiumVO premiumVO = new PremiumVO();
		if(!Utils.isEmpty(renData[13]))  premiumVO.setVatTaxPerc(converter.getBFromA( (BigDecimal) renData[13]) );
		if(!Utils.isEmpty(renData[14]))  premiumVO.setVatTax(converter.getBFromA( (BigDecimal) renData[14]) );
		if(!Utils.isEmpty(renData[15]))  premiumVO.setVatCode((Integer) renData[15]);

		UserProfile userProfile = new UserProfile();
		
		userProfile.setUserId(renData[9].toString());			
		commonVO.setLoggedInUser(userProfile);
		String lob = renData[8].toString();
	
		HomeInsuranceVO homeInsuranceVO = new HomeInsuranceVO();
		
		TravelInsuranceVO travelInsuranceVO = new TravelInsuranceVO();
		if(isScheduler){
			/*int userId=SvcConstants.MISLIVE_USER;
			CommonOpSvc commonOpSvc = (CommonOpSvc) Utils.getBean("geComSvcBean");
			DataHolderVO userDetailsData=(DataHolderVO)commonOpSvc.invokeMethod("getUserDetails",userId);
			TMasUser userDetails=(TMasUser)userDetailsData.getData();
			
			if(!Utils.isEmpty(userDetails)){
				if(Utils.isEmpty(userDetails.getUserEmailId())){					
					userDetails.setUserEmailId(emailId);
				}
				if(Utils.isEmpty(userDetails.getUserEName()) ){
					//userName = userDetails.getUserEName();
					//userEngName = userDetails.getUserEName();
					userDetails.setUserEName( userEngName );
				}
				if(Utils.isEmpty(userDetails.getUserAName()) ){					
					userDetails.setUserAName(userArabicName);
				}
				if(Utils.isEmpty( userDetails.getUserEmailId() )){
					userDetails.setUserEmailId( emailId );					
				}
				if(Utils.isEmpty( userDetails.getPassword() )){					
					userDetails.setPassword(password);
				}
				if(Utils.isEmpty( userDetails.getCountry() )){					
					userDetails.setCountry(countryCode);
				}
				if(Utils.isEmpty( userDetails.getBranch() )){
					userDetails.setBranch(branch);					
				}
				if(Utils.isEmpty( userDetails.getEmployeeId() )){
					userDetails.setEmployeeId( employeeId );
				}
				if(Utils.isEmpty( userDetails.getBrokerId() )){					
					userDetails.setBrokerId( brokerId );
				}
				if(Utils.isEmpty( userDetails.getAgentId() )){
					userDetails.setAgentId(agentId);					
				}
				if(Utils.isEmpty( userDetails.getDefaultModule() )){
					userDetails.setDefaultModule( defaultModule.byteValue() );
					//defaultModule = Integer.parseInt( String.valueOf( userDetails.getDefaultModule() ) );
				}
				if(Utils.isEmpty( userDetails.getDefaultApprover() )){
					userDetails.setDefaultApprover( defaultApprover );					
				}
				if(!Utils.isEmpty( userDetails.getLoginAttempts() )){
					loginAttempts = userDetails.getLoginAttempts();
				}
				if(Utils.isEmpty( userDetails.getStatusId() )){
					 userDetails.setStatusId(statusId.byteValue());					
				}
				if(Utils.isEmpty( userDetails.getProfile() ) ){
					userDetails.setProfile( profile );				
				}
				if(Utils.isEmpty( userDetails.getUserMobNo() )){
					userDetails.setUserMobNo(mobileNo);					
				}
			}		
		
			GrantedAuthority [] grantedAuth = new GrantedAuthorityImpl[1];
			grantedAuth[0] = new GrantedAuthorityImpl(Utils.getSingleValueAppConfig( "RSA_PL_USER_1" ) );
			//UserProfile up = new UserProfile();
			
			DefaultSchedulerUser defaultUser = new DefaultSchedulerUser( userDetails.getUserEName(), userDetails.getPassword(), true, grantedAuth, 
					Integer.parseInt( userDetails.getCountry().toString() ),  userDetails.getBranch(),userDetails.getEmployeeId(), userDetails.getBrokerId().intValue(), 
					Integer.parseInt( String.valueOf( userDetails.getAgentId() )),Integer.parseInt( String.valueOf( userDetails.getDefaultModule())), 
					userDetails.getDefaultApprover(), 0, userDetails.getStatusId().intValue(), userDetails.getProfile(),
        			userId, userDetails.getUserAName(), userDetails.getUserEName(), userDetails.getUserEmailId(), userDetails.getUserMobNo() );
        	up.setRsaUser( defaultUser );*/
        	commonVO.setLoggedInUser( createDefaultUser("RSA_PL_USER_1") );
		}
		
		if (lob.equals(SvcConstants.HOME_POL_TYPE)) {		
			commonVO.setLob(LOB.HOME);
			homeInsuranceVO.setCommonVO(commonVO);			
			homeInsuranceVO = (HomeInsuranceVO) TaskExecutor.executeTasks("GEN_INFO_COMMON_LOAD_RENEWAL", homeInsuranceVO);
			
			
			//Change for Renewal CR starts
			
			//Boolean isPopulate = true;
			Boolean isPopulate = false;
			DataHolderVO<Object[]> claimInput = new DataHolderVO<Object[]>();
			Double buildingClaimsAmt = 0.0;
			Double contentClaimsAmt = 0.0;
			Double ppClaimAmt = 0.0;
			Integer buildingClaimCount = 0;
			Integer contentClaimCount = 0;
			Integer ppClaimCount = 0;
			Double loadPercentage = 0.0;
			Object claimInputData[] = new Object[3];			
			claimInputData[0] = policyId;
			claimInputData[1] = null;			
			
			claimInput.setData(claimInputData);			
			DataHolderVO<Object> claimsOutput = (DataHolderVO<Object>) TaskExecutor.executeTasks(GET_CLAIM_DETAILS, claimInput);
			
			List<Object[]> claimsList = (List<Object[]>) claimsOutput.getData();			
			
			for(Object[] obj : claimsList){				
					
				if(obj[1].toString().equals( AppConstants.HOME_BUILDING_RISK_TYPE.toString() )){
					buildingClaimsAmt = buildingClaimsAmt + Double.valueOf(  obj[2].toString() );
					buildingClaimCount++;
				}
				else if(String.valueOf( obj[1] ).equals( AppConstants.HOME_CONTENT_RISK_TYPE )){
					contentClaimsAmt = contentClaimsAmt + Double.valueOf(  obj[2].toString() );
					contentClaimCount++;
				}
				else if(String.valueOf( obj[1] ).equals( AppConstants.HOME_PERSONAL_POSSESSION_RISK_TYPE.toString() )){
					ppClaimAmt = ppClaimAmt + Double.valueOf( String.valueOf( obj[2] ) );
					ppClaimCount++;
				}
					
			}
			
			if( (buildingClaimCount == AppConstants.CLAIM_COUNT_ONE || buildingClaimCount == AppConstants.CLAIM_COUNT_TWO) 
					&& (buildingClaimCount + contentClaimCount + ppClaimCount) <= AppConstants.CLAIM_COUNT_TWO 
					&& !Utils.isEmpty( homeInsuranceVO.getBuildingDetails().getSumInsured() ) && !Utils.isEmpty( homeInsuranceVO.getBuildingDetails().getSumInsured().getSumInsured() ) ) {
			
				if(buildingClaimCount == 1 ){
					if( buildingClaimsAmt < ( homeInsuranceVO.getBuildingDetails().getSumInsured().getSumInsured() * AppConstants.VALUE_FOR_CLAIM_CHECK_ONE) ){
						//homeInsuranceVO.getBuildingDetails().setDiscOrLoadPerc( 15.0 );
						loadPercentage = AppConstants.LOAD_FOR_CLAIM_PERCENTAGE_VALUE_ONE;
						
					}
					else if(buildingClaimsAmt <= ( homeInsuranceVO.getBuildingDetails().getSumInsured().getSumInsured() * AppConstants.VALUE_FOR_CLAIM_CHECK_TWO) ){						
						loadPercentage = AppConstants.LOAD_FOR_CLAIM_PERCENTAGE_VALUE_TWO;
					}
					else{						
						loadPercentage = AppConstants.LOAD_FOR_CLAIM_PERCENTAGE_VALUE_THREE;
					}				
				}
				else if(buildingClaimCount == 2){
					if( buildingClaimsAmt < ( homeInsuranceVO.getBuildingDetails().getSumInsured().getSumInsured() * AppConstants.VALUE_FOR_CLAIM_CHECK_ONE) ){						
						loadPercentage = AppConstants.LOAD_FOR_CLAIM_PERCENTAGE_VALUE_TWO;
					}
					else if(buildingClaimsAmt <= ( homeInsuranceVO.getBuildingDetails().getSumInsured().getSumInsured() * AppConstants.VALUE_FOR_CLAIM_CHECK_TWO) ){						
						loadPercentage = AppConstants.LOAD_FOR_CLAIM_PERCENTAGE_VALUE_THREE;
					}
					else{						
						loadPercentage = AppConstants.LOAD_FOR_CLAIM_PERCENTAGE_VALUE_FOUR;
					}	
				}
				homeInsuranceVO.getBuildingDetails().setDiscOrLoadPerc( loadPercentage );
				//homeInsuranceVO.getBuildingDetails().setPremiumAmt( homeInsuranceVO.getBuildingDetails().getPremiumAmtActual() + (homeInsuranceVO.getBuildingDetails().getPremiumAmtActual() * loadPercentage / 100) );
				isPopulate = false;
			}
			
			if( ( contentClaimCount == AppConstants.CLAIM_COUNT_ONE || contentClaimCount == AppConstants.CLAIM_COUNT_TWO 
					|| ppClaimCount == AppConstants.CLAIM_COUNT_ONE || ppClaimCount == AppConstants.CLAIM_COUNT_TWO ) 
					&& (buildingClaimCount + contentClaimCount + ppClaimCount ) <= AppConstants.CLAIM_COUNT_TWO ){			
				for( CoverDetailsVO coverDetailsVO : homeInsuranceVO.getCovers() ){
					if( ( coverDetailsVO.getRiskCodes().getRiskType().equals( SvcConstants.HOME_CONTENT_RISK_TYPE ) 
						|| coverDetailsVO.getRiskCodes().getRiskType().equals( SvcConstants.HOME_PERSONAL_POS_RISK_TYPE ) )
						&& coverDetailsVO.getRiskCodes().getRiskCat().equals( SvcConstants.DEFAULT_COVER_RSK_CAT ) 
						&& coverDetailsVO.getCoverCodes().getCovCode() == SvcConstants.DEFAULT_COVER_CODE 
						&& !Utils.isEmpty( coverDetailsVO.getSumInsured() ) && !Utils.isEmpty( coverDetailsVO.getSumInsured().getSumInsured() ) ){
						
						if(coverDetailsVO.getRiskCodes().getRiskType().equals( SvcConstants.HOME_CONTENT_RISK_TYPE )){
							loadPercentage = 0.0;
							if(contentClaimCount == AppConstants.CLAIM_COUNT_ONE ){
								if( contentClaimsAmt < ( coverDetailsVO.getSumInsured().getSumInsured() * AppConstants.VALUE_FOR_CLAIM_CHECK_ONE) ){							
									//coverDetailsVO.setDiscOrLoadPerc( 15.0 );
									loadPercentage = AppConstants.LOAD_FOR_CLAIM_PERCENTAGE_VALUE_ONE;
								}
								else if(contentClaimsAmt <= ( coverDetailsVO.getSumInsured().getSumInsured() * AppConstants.VALUE_FOR_CLAIM_CHECK_TWO) ){								
									loadPercentage = AppConstants.LOAD_FOR_CLAIM_PERCENTAGE_VALUE_TWO;
								}
								else{								
									loadPercentage = AppConstants.LOAD_FOR_CLAIM_PERCENTAGE_VALUE_THREE;
								}				
							}
							else if(contentClaimCount == AppConstants.CLAIM_COUNT_TWO ){
								if( contentClaimsAmt < ( coverDetailsVO.getSumInsured().getSumInsured() * AppConstants.VALUE_FOR_CLAIM_CHECK_ONE) ){								
									loadPercentage = AppConstants.LOAD_FOR_CLAIM_PERCENTAGE_VALUE_TWO;
								}
								else if(contentClaimsAmt <= ( coverDetailsVO.getSumInsured().getSumInsured() * AppConstants.VALUE_FOR_CLAIM_CHECK_TWO) ){								
									loadPercentage = AppConstants.LOAD_FOR_CLAIM_PERCENTAGE_VALUE_THREE;
								}
								else{								
									loadPercentage = AppConstants.LOAD_FOR_CLAIM_PERCENTAGE_VALUE_FOUR;
								}	
							}
							coverDetailsVO.setDiscOrLoadPerc(loadPercentage );
						}
						else if( coverDetailsVO.getRiskCodes().getRiskType().equals( SvcConstants.HOME_PERSONAL_POS_RISK_TYPE )) {
							
								loadPercentage = 0.0;
								if(ppClaimCount == AppConstants.CLAIM_COUNT_ONE ){
									if( ppClaimAmt < ( coverDetailsVO.getSumInsured().getSumInsured() * AppConstants.VALUE_FOR_CLAIM_CHECK_ONE) ){							
										//coverDetailsVO.setDiscOrLoadPerc( 15.0 );
										loadPercentage = AppConstants.LOAD_FOR_CLAIM_PERCENTAGE_VALUE_ONE;
									}
									else if(ppClaimAmt <= ( coverDetailsVO.getSumInsured().getSumInsured() * AppConstants.VALUE_FOR_CLAIM_CHECK_TWO) ){								
										loadPercentage = AppConstants.LOAD_FOR_CLAIM_PERCENTAGE_VALUE_TWO;
									}
									else{								
										loadPercentage = AppConstants.LOAD_FOR_CLAIM_PERCENTAGE_VALUE_THREE;
									}				
								}
								else if(ppClaimCount == AppConstants.CLAIM_COUNT_TWO ){
									if( ppClaimAmt < ( coverDetailsVO.getSumInsured().getSumInsured() * AppConstants.VALUE_FOR_CLAIM_CHECK_ONE) ){								
										loadPercentage = AppConstants.LOAD_FOR_CLAIM_PERCENTAGE_VALUE_TWO;
									}
									else if(ppClaimAmt <= ( coverDetailsVO.getSumInsured().getSumInsured() * AppConstants.VALUE_FOR_CLAIM_CHECK_TWO) ){								
										loadPercentage = AppConstants.LOAD_FOR_CLAIM_PERCENTAGE_VALUE_THREE;
									}
									else{								
										loadPercentage = AppConstants.LOAD_FOR_CLAIM_PERCENTAGE_VALUE_FOUR;
									}	
								}
								coverDetailsVO.setDiscOrLoadPerc(loadPercentage );
							}
							
							//coverDetailsVO.setDiscOrLoadPerc(loadPercentage );
						//coverDetailsVO.setPremiumAmt( coverDetailsVO.getPremiumAmtActual() + (coverDetailsVO.getPremiumAmtActual() * loadPercentage / 100) );
					}
				}
				isPopulate = false;					
			}	
			
			//DataHolderVO<Long> oldPolicyId = new DataHolderVO<Long>();
			//oldPolicyId.setData( policyId );
			//DataHolderVO<Object> legacyDataHolder = (DataHolderVO<Object>) TaskExecutor.executeTasks("GET_LEGACY_POLICIES", oldPolicyId);
			//isPopulate = !Utils.isEmpty(  legacyDataHolder ) && !Utils.isEmpty(  legacyDataHolder.getData() )? false : true;
			/*Fix - renewal quote was generated without loading even though the original policy had claims - Change true to isPopulate */
			//isPopulate = !Utils.isEmpty(  originApplication ) ? false : isPopulate;
			isPopulate = !Utils.isEmpty(  originApplication ) ? true : isPopulate;
			
			DataHolderVO<Object[]> dataHolder = new DataHolderVO<Object[]>();		
			//Object[] inpObjects =  {homeInsuranceVO,true};
			for(CoverDetailsVO cover : homeInsuranceVO.getCovers()){
				if( !Utils.isEmpty(cover.getSumInsured().getSumInsured() ) || !Utils.isEmpty(cover.getPremiumAmt()) ){					
					cover.setIsCovered( "on" );
					if( !Utils.isEmpty(cover.getPremiumAmt()) && Double.valueOf( AppConstants.zeroVal ).equals(cover.getSumInsured().getSumInsured() ) ){
						cover.getSumInsured().setSumInsured(null);
					}
				}				
			}
			
			/*if(isScheduler){
				GrantedAuthority [] grantedAuth = new GrantedAuthorityImpl[1];
				grantedAuth[0] = new GrantedAuthorityImpl(Utils.getSingleValueAppConfig( "RSA_PL_USER_1" ) );
				UserProfile up = new UserProfile();
	        	DefaultSchedulerUser defaultUser = new DefaultSchedulerUser( "MISLIVE - RSA Dubai", "fcdbf28eb2e8cf3a1", true, grantedAuth , 2, 20, 0, 0, 0, 0, 0, 0, 0, "EMPLOYEE",
	        			17, "aaa", "aaa", "rrr@gmail.com", "111111111" );
	        	up.setRsaUser( defaultUser );
	        	homeInsuranceVO.getCommonVO().setLoggedInUser( up );
			}*/
			//Change for Renewal CR ends
			if(!Utils.isEmpty(homeInsuranceVO.getBuildingDetails()))
			{
				String emirates = homeInsuranceVO.getBuildingDetails().getEmirates();
				try
				{
					Integer emirate = Integer.parseInt(emirates);
					emirates = SvcUtils.getLookUpDescription( "CITY", "ALL", "ALL", emirate );
					if(!Utils.isEmpty( emirates ) )
					{
						homeInsuranceVO.getBuildingDetails().setEmirates(emirates);
					}
				}
				catch (Exception e) 
				{
					System.out.println(" Emirates is not a Integer");
				}
			}
			
			Object[] inpObjects =  {homeInsuranceVO,isPopulate};
			dataHolder.setData(inpObjects);
			dataHolder = (DataHolderVO<Object[]>) TaskExecutor.executeTasks("HOME_INSURANCE_SAVE", dataHolder);
			homeInsuranceVO = (HomeInsuranceVO) dataHolder.getData()[ 0 ];
			policyVO.setCommonVO(commonVO);
			policyVO.setPolicyId(renPolicyId);
			policyVO.setEndtId(commonVO.getEndtId());			
			policyVO.setGeneralInfo(homeInsuranceVO.getGeneralInfo());
			LOGGER.info("Home Issue Quote Procedure called");			
			DAOUtils.callUpdateStatusProcedureForHomeTravel(policyVO);
			LOGGER.info("Home Issue Quote Procedure executed successfully");
			commonVO.setStatus(SvcConstants.POL_STATUS_ACTIVE);	
			//Change for Renewal CR starts
			
			DataHolderVO<Long> inputData = new DataHolderVO<Long>();
			inputData.setData(policyId);			
			DataHolderVO<Boolean> output =  (DataHolderVO<Boolean>) TaskExecutor.executeTasks(CHECK_FOR_FRAUD_CLAIM, inputData);
			if(output.getData().booleanValue() || (buildingClaimCount + contentClaimCount) > AppConstants.CLAIM_COUNT_TWO ) {
				//TODO - set the status as HARD STOP
				renInputData[0] = policyVO;
				renInputData[1] = Utils.getSingleValueAppConfig("QUOTE_HARD_STOP");
				input.setData(renInputData);
				TaskExecutor.executeTasks(UPDATE_RENEWAL_QUOTE_STATUS_COMMON, input);
				commonVO.setStatus( Integer.parseInt( Utils.getSingleValueAppConfig("QUOTE_HARD_STOP") ) );
			}
			policyVO.setCommonVO( commonVO );
			//CTS - CR - 3066 Putting all renewals on soft stop with discount 50 percent or more - Starts	
			Integer location = policyVO.getCommonVO().getLocCode();
			if(location == 20 || location == 21 || location ==50)
				discountCheckForSoftStop(policyVO);
			//CTS - CR - 3066 Putting all renewals on soft stop with discount 50 percent or more - Ends
		
			
			//change for renewal CR ends
			
		} else {
			
			commonVO.setLob(LOB.TRAVEL);
			travelInsuranceVO.setCommonVO(commonVO);			
			SchemeVO schemeVO = new SchemeVO();
			AuthenticationInfoVO authenticationInfoVO = new AuthenticationInfoVO();
			authenticationInfoVO.setRefPolicyId( policyId );
			travelInsuranceVO.setScheme(schemeVO);
			travelInsuranceVO.setAuthenticationInfoVO( authenticationInfoVO );
			GeneralInfoVO generalInfoVO = new GeneralInfoVO();
			travelInsuranceVO.setGeneralInfo(generalInfoVO);
			travelInsuranceVO.getGeneralInfo().setInsured(insuredVO);
			travelInsuranceVO.setPremiumVO(premiumVO);
			policyVO = (PolicyDataVO)TaskExecutor.executeTasks("LOAD_SAVE_TRVEL_DETAILS_RENEWAL", travelInsuranceVO);
			//CTS - CR - 3066 Putting all renewals on soft stop with discount 50 percent or more - Starts	
			Integer location = policyVO.getCommonVO().getLocCode();
			if(location == 20 || location == 21 || location ==50)
				discountCheckForSoftStop(policyVO);
			//CTS - CR - 3066 Putting all renewals on soft stop with discount 50 percent or more - Ends

		}
		
		//commonVO.setStatus(SvcConstants.POL_STATUS_ACTIVE);		
		return policyVO;
	}
	
	@SuppressWarnings("unchecked")
	public PolicyDataVO createPolicyObjectforSMS(Object[] renData)  {
		
		PolicyDataVO policyVO = new PolicyDataVO();			
		Long quotationNo = (Long)renData[0];		
		LOGGER.debug("***********renQuotationNo = "+renData[0]);
		LOGGER.debug("***********renPolicyId = "+renData[1]);
		Long renPolicyId = (Long)renData[1];
		
		CommonVO commonVO = new CommonVO();		
		commonVO.setLocCode(Integer.parseInt(renData[2].toString()));
		commonVO.setPolEffectiveDate((Date) renData[4]);
		commonVO.setEndtNo((Long) renData[5]);
		commonVO.setDocCode(Short.valueOf(renData[6].toString()));
		commonVO.setVsd((Date) renData[7]);
		commonVO.setIsQuote(true);
		commonVO.setStatus(AppConstants.QUOTE_PENDING);
		commonVO.setEndtId(0L);
		commonVO.setPolicyId(renPolicyId);
		commonVO.setQuoteNo(quotationNo);
		commonVO.setAppFlow(Flow.CREATE_QUO);
		commonVO.setPolicyNo((Long) renData[10]);
		commonVO.setConcatPolicyNo(renData[11].toString());
	
		UserProfile userProfile = new UserProfile();
		
		userProfile.setUserId(renData[9].toString());			
		commonVO.setLoggedInUser(userProfile);
		String lob = renData[8].toString();
	
		HomeInsuranceVO homeInsuranceVO = new HomeInsuranceVO();
		
		TravelInsuranceVO travelInsuranceVO = new TravelInsuranceVO();		
		if (lob.equals(SvcConstants.HOME_POL_TYPE)) {		
			commonVO.setLob(LOB.HOME);
			homeInsuranceVO.setCommonVO(commonVO);			
			homeInsuranceVO = (HomeInsuranceVO) TaskExecutor.executeTasks("GEN_INFO_COMMON_LOAD_RENEWAL", homeInsuranceVO);			
			policyVO.setCommonVO(commonVO);
			policyVO.setPolicyId(renPolicyId);
			policyVO.setEndtId(commonVO.getEndtId());			
			policyVO.setGeneralInfo(homeInsuranceVO.getGeneralInfo());
		} else {
			
			commonVO.setLob(LOB.TRAVEL);
			travelInsuranceVO.setCommonVO(commonVO);	
			
			SchemeVO schemeVO = new SchemeVO();
			travelInsuranceVO.setScheme(schemeVO);
			//policyVO = (PolicyDataVO)TaskExecutor.executeTasks("LOAD_SAVE_TRVEL_DETAILS_RENEWAL", travelInsuranceVO);
			travelInsuranceVO = (TravelInsuranceVO)TaskExecutor.executeTasks( "TRAVEL_PACKAGE_PREMIUM", travelInsuranceVO ); 
			policyVO.setCommonVO(commonVO);
			policyVO.setPolicyId(renPolicyId);
			policyVO.setEndtId(commonVO.getEndtId());
			policyVO.setGeneralInfo(travelInsuranceVO.getGeneralInfo());
			discountCheckForSoftStop(policyVO);		
			}			
		
		return policyVO;
	}
	
	public UserProfile createDefaultUser(String grantedUserName){

		UserProfile up = new UserProfile();
		int userId=SvcConstants.MISLIVE_USER;
		CommonOpSvc commonOpSvc = (CommonOpSvc) Utils.getBean("geComSvcBean");
		@SuppressWarnings("rawtypes")
		DataHolderVO userDetailsData=(DataHolderVO)commonOpSvc.invokeMethod("getUserDetails",userId);
		TMasUser userDetails=(TMasUser)userDetailsData.getData();
		
		if(!Utils.isEmpty(userDetails)){
			if(Utils.isEmpty(userDetails.getUserEmailId())){					
				userDetails.setUserEmailId(EMAIL_ID);
			}
			if(Utils.isEmpty(userDetails.getUserEName()) ){
				userDetails.setUserEName( USER_ENG_NAME );
			}
			if(Utils.isEmpty(userDetails.getUserAName()) ){					
				userDetails.setUserAName(USER_ARABIC_NAME);
			}
			if(Utils.isEmpty( userDetails.getUserEmailId() )){
				userDetails.setUserEmailId( EMAIL_ID );					
			}
			if(Utils.isEmpty( userDetails.getPassword() )){					
				userDetails.setPassword("test");           //SONARFIX -- 25-apr-2018 -- commented this as this is private and replaced the variable with string value
			}
			if(Utils.isEmpty( userDetails.getCountry() )){					
				userDetails.setCountry(COUNTRY_CODE);
			}
			if(Utils.isEmpty( userDetails.getBranch() )){
				userDetails.setBranch(BRANCH);					
			}
			if(Utils.isEmpty( userDetails.getEmployeeId() )){
				userDetails.setEmployeeId( EMPLOYEE_ID );
			}
			if(Utils.isEmpty( userDetails.getBrokerId() )){					
				userDetails.setBrokerId( BROKER_ID );
			}
			if(Utils.isEmpty( userDetails.getAgentId() )){
				userDetails.setAgentId(AGENT_ID);					
			}
			if(Utils.isEmpty( userDetails.getDefaultModule() )){
				userDetails.setDefaultModule( DEFAULT_MODULE.byteValue() );
				//defaultModule = Integer.parseInt( String.valueOf( userDetails.getDefaultModule() ) );
			}
			if(Utils.isEmpty( userDetails.getDefaultApprover() )){
				userDetails.setDefaultApprover( DEFAULT_APPROVER );					
			}
			/*if(!Utils.isEmpty( userDetails.getLoginAttempts() )){
				loginAttempts = userDetails.getLoginAttempts();
			}*/
			if(Utils.isEmpty( userDetails.getStatusId() )){
				 userDetails.setStatusId(STATUS_ID.byteValue());					
			}
			if(Utils.isEmpty( userDetails.getProfile() ) ){
				userDetails.setProfile( PROFILE );				
			}
			if(Utils.isEmpty( userDetails.getUserMobNo() )){
				userDetails.setUserMobNo(MOBILE_NO);					
			}
		}		
	
		GrantedAuthority [] grantedAuth = new GrantedAuthorityImpl[1];
		grantedAuth[0] = new GrantedAuthorityImpl(Utils.getSingleValueAppConfig( grantedUserName ) );
		//UserProfile up = new UserProfile();
		
		DefaultSchedulerUser defaultUser = new DefaultSchedulerUser( userDetails.getUserEName(), userDetails.getPassword(), true, grantedAuth, 
				Integer.parseInt( userDetails.getCountry().toString() ),  userDetails.getBranch(),userDetails.getEmployeeId(), userDetails.getBrokerId().intValue(), 
				Integer.parseInt( String.valueOf( userDetails.getAgentId() )),Integer.parseInt( String.valueOf( userDetails.getDefaultModule())), 
				userDetails.getDefaultApprover(), 0, userDetails.getStatusId().intValue(), userDetails.getProfile(),
    			userId, userDetails.getUserAName(), userDetails.getUserEName(), userDetails.getUserEmailId(), userDetails.getUserMobNo() );
    	up.setRsaUser( defaultUser );
    	
    	return up;
	}
//CTS - CR - 3066 Putting all renewals on soft stop with discount 50 percent or more - Starts	
	private void discountCheckForSoftStop(PolicyDataVO policyVO){
		
		double totalDiscount;
		DataHolderVO<Object[]> inputData = new DataHolderVO<Object[]>();
		Object renInputData[] = new Object[2];
		totalDiscount = calculateTotalDiscountValue(policyVO);
		if((Math.abs(totalDiscount) >= Double.valueOf(Utils.getSingleValueAppConfig("RENEWAL_SOFTSTOP_DISCOUNT_PERC")) && totalDiscount < Double.valueOf(0.0))){
			renInputData[0] = policyVO;
			renInputData[1] = Utils.getSingleValueAppConfig("QUOTE_SOFT_STOP");
			inputData.setData(renInputData);
			TaskExecutor.executeTasks(UPDATE_RENEWAL_QUOTE_STATUS_COMMON, inputData);
			TaskExecutor.executeTasks(UPDATE_RENEWAL_STATUS_FOR_SOFT_STOP, inputData);
			policyVO.getCommonVO().setStatus( Integer.parseInt( Utils.getSingleValueAppConfig("QUOTE_SOFT_STOP") ) );
						
		}
	}
	
	private double calculateTotalDiscountValue(PolicyDataVO policyVO){
		Double promoDiscount = 0.0;
		Double discLoadPerc = 0.0;
		Double splDiscount = 0.0;
		if(policyVO.getCommonVO().getLob().equals(LOB.HOME)){
			promoDiscount = policyVO.getCommonVO().getPremiumVO().getPromoDiscPerc() ;
			discLoadPerc =  policyVO.getCommonVO().getPremiumVO().getDiscOrLoadPerc();
			splDiscount =   policyVO.getCommonVO().getPremiumVO().getSpecialDiscount();
			
		}else if(policyVO.getCommonVO().getLob().equals(LOB.TRAVEL)) {
			promoDiscount = policyVO.getPremiumVO().getPromoDiscPerc();
			discLoadPerc =  policyVO.getPremiumVO().getDiscOrLoadPerc();
			splDiscount =   policyVO.getPremiumVO().getSpecialDiscount();
		}
		if(promoDiscount == null){
			promoDiscount = 0.0;
		}else if(discLoadPerc == null){
			discLoadPerc = 0.0;
		}
		return (promoDiscount + discLoadPerc + splDiscount);
	}
	//CTS - CR - 3066 Putting all renewals on soft stop with discount 50 percent or more - Ends

}
