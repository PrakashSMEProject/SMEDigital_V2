/**
 * 
 */
package com.rsaame.pas.b2c.homeInsurance;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.b2c.cmn.constants.AppConstants;
import com.rsaame.pas.b2c.cmn.handlers.B2CEmailTriggers;
import com.rsaame.pas.b2c.cmn.handlers.CommonHandler;
import com.rsaame.pas.b2c.cmn.utils.AppUtils;
import com.rsaame.pas.b2c.cmn.utils.ReferralUtils;
import com.rsaame.pas.b2c.exception.SystemException;
import com.rsaame.pas.cmn.currency.Currency;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.com.svc.CommonOpSvc;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.app.LookUpListVO;
import com.rsaame.pas.vo.bus.AdditionalInsuredInfoVO;
import com.rsaame.pas.vo.bus.AddressVO;
import com.rsaame.pas.vo.bus.AuthenticationInfoVO;
import com.rsaame.pas.vo.bus.BuildingDetailsVO;
import com.rsaame.pas.vo.bus.ClaimsSummaryVO;
import com.rsaame.pas.vo.bus.CoverDetailsVO;
import com.rsaame.pas.vo.bus.GeneralInfoVO;
import com.rsaame.pas.vo.bus.HomeInsuranceVO;
import com.rsaame.pas.vo.bus.InsuredVO;
import com.rsaame.pas.vo.bus.LOB;
import com.rsaame.pas.vo.bus.PaymentVO;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.bus.ReferralVO;
import com.rsaame.pas.vo.bus.SchemeVO;
import com.rsaame.pas.vo.bus.SourceOfBusinessVO;
import com.rsaame.pas.vo.bus.SumInsuredVO;
import com.rsaame.pas.vo.cmn.CommonVO;
import com.rsaame.pas.vo.cmn.CoverVO;
import com.rsaame.pas.vo.cmn.RiskVO;

/**
 * Class to implement Home Insurance Save/Load operations
 * @author Sarath Varier
 * @since Phase 3
 *
 */

public class HomeInsuranceSvcHandler implements IHomeInsuranceSvcHandler {
	
	private static final String ALL = "ALL";
	private final static Logger LOGGER = Logger.getLogger( HomeInsuranceSvcHandler.class );
			
	@Override
public PolicyDataVO saveHomeRiskCoverDetails(PolicyDataVO homeInsuranceData, boolean completePurchaseInd, String contextPath) throws ParseException{

			//HomeInsuranceVO homeVO = testGenerateHomeInsuranceVO();
			PolicyDataVO homeInsuranceVO =  new HomeInsuranceVO();
			homeInsuranceData.getCommonVO().setIsQuote(Boolean.TRUE);
			AppUtils.populateCommonVO( homeInsuranceData.getCommonVO() );
			homeInsuranceData.getCommonVO().setLoggedInUser(homeInsuranceData.getCommonVO().getLoggedInUser());
			homeInsuranceData.setLoggedInUser( homeInsuranceData.getCommonVO().getLoggedInUser() );
			PremiumVO premiumVO = new PremiumVO();
			if(!homeInsuranceData.getGeneralInfo().getSourceOfBus().getDistributionChannel().equals( 10 ))
			{
				if(!Utils.isEmpty(homeInsuranceData.getGeneralInfo().getSourceOfBus().getDefaultOnlineDiscount()))
				{
					premiumVO.setDiscOrLoadPerc( homeInsuranceData.getGeneralInfo().getSourceOfBus().getDefaultOnlineDiscount() );
				}
			}
			else
			{
				premiumVO.setDiscOrLoadPerc( Double.valueOf( Utils.getSingleValueAppConfig( "HOME_POLICY_LEVEL_DISCOUNT" ) ) );
			}
			// For 142244 VAT
			if (!Utils.isEmpty(homeInsuranceData.getPremiumVO().getVatTaxPerc())) {
				premiumVO.setVatTaxPerc(homeInsuranceData.getPremiumVO()
						.getVatTaxPerc());
			}
			
			if(Utils.isEmpty(homeInsuranceData.getCommonVO().getPremiumVO().getVatCode()) && Utils.isEmpty(homeInsuranceData.getPremiumVO().getVatCode())){
				@SuppressWarnings("unchecked")
				DataHolderVO<Object[]> holder1 = (DataHolderVO<Object[]>) TaskExecutor
						.executeTasks("LOAD_HOME_VAT_RATE_AND_CODE",
								homeInsuranceData);
				@SuppressWarnings("unchecked")
				Map<String, Object> vat = (Map<String, Object>) holder1
						.getData()[0];

				LOGGER.debug("**********vatRate**********"
						+ vat.get(com.Constant.CONST_VATRATE));
				LOGGER.debug("**********vatCode**********"
						+ vat.get(com.Constant.CONST_VATCODE));

				homeInsuranceData.getPremiumVO().setVatTaxPerc(
						(Double) vat.get(com.Constant.CONST_VATRATE));
				homeInsuranceData.getPremiumVO().setVatCode(
						(Integer) vat.get(com.Constant.CONST_VATCODE));

				homeInsuranceData.getCommonVO().getPremiumVO()
						.setVatTaxPerc((Double) vat.get(com.Constant.CONST_VATRATE));
				homeInsuranceData.getCommonVO().getPremiumVO()
						.setVatCode((Integer) vat.get(com.Constant.CONST_VATCODE));
			}
			
			
			if (!Utils.isEmpty(homeInsuranceData.getCommonVO().getPremiumVO().getVatCode())) {
				premiumVO.setVatCode(homeInsuranceData.getCommonVO().getPremiumVO()
						.getVatCode());
			}
			homeInsuranceData.setPremiumVO( premiumVO );
			
			if(!Utils.isEmpty( homeInsuranceData.getCommonVO().getQuoteNo() ) && !(Short.valueOf( Utils.getSingleValueAppConfig( "REN_QUO_DOC_CODE" ) ).equals( homeInsuranceData.getCommonVO().getDocCode() )) ){
				homeInsuranceVO = (PolicyDataVO) TaskExecutor.executeTasks( com.Constant.CONST_HOME_INSURANCE_LOAD, homeInsuranceData.getCommonVO() );
				( ( HomeInsuranceVO )homeInsuranceVO ).setStaffDetails( ( ( HomeInsuranceVO ) homeInsuranceData ).getStaffDetails() );
				populatePackagePremium(homeInsuranceVO);
				mergeHomeInsuranceDetails(homeInsuranceVO, (HomeInsuranceVO)homeInsuranceData,false);
			}else{
				if( Utils.isEmpty( homeInsuranceData.getGeneralInfo().getInsured().getFirstName() ) 
						&& Utils.isEmpty( homeInsuranceData.getGeneralInfo().getInsured().getLastName() ) 
						&& ( Utils.isEmpty( homeInsuranceData.getGeneralInfo().getInsured().getAddress() ) || 
								Utils.isEmpty( homeInsuranceData.getGeneralInfo().getInsured().getAddress().getPoBox() ) ) ){
					homeInsuranceData.getGeneralInfo().getInsured().setFirstName(AppConstants.HOME_FIRST_NAME_DUMMY);
					homeInsuranceData.getGeneralInfo().getInsured().setLastName(AppConstants.HOME_LAST_NAME_DUMMY);
					if(Utils.isEmpty(homeInsuranceData.getGeneralInfo().getInsured().getAddress())){
						homeInsuranceData.getGeneralInfo().getInsured().setAddress(new AddressVO());
					}
					homeInsuranceData.getGeneralInfo().getInsured().getAddress().setPoBox(AppConstants.HOME_PO_BOX_DUMMY);
				}
			
				homeInsuranceVO = homeInsuranceData;
				homeInsuranceVO.getGeneralInfo().getAdditionalInfo().setAffinityRefNo(homeInsuranceVO.getGeneralInfo().getSourceOfBus().getPartnerName());
			}
			handlePromotionalCode(homeInsuranceData);
			updateCoverDetails(homeInsuranceVO);
			/* Request ID121899   Incorrect Premium Calculation  when Promo Code Applied during edition of quote.*/
			homeInsuranceVO.getPremiumVO().setPromoDiscPerc(homeInsuranceData.getPremiumVO().getPromoDiscPerc());
			/* For VAT 142244*/
			homeInsuranceVO.getPremiumVO().setVatCode(homeInsuranceData.getPremiumVO().getVatCode());
			/* Invoke the rule engine */
			boolean rulePassed = ReferralUtils.executeReferralTaskHome( (HomeInsuranceVO)homeInsuranceVO, "", "Home Risk Cover", "",AppConstants.HOME_RISK_SECTION_ID );
			
			/* Populate the task vo if referral is present */
			if(!rulePassed){
				ReferralUtils.populateTaskVO(homeInsuranceVO);
				homeInsuranceVO.setLoggedInUser( homeInsuranceData.getCommonVO().getLoggedInUser() );
				homeInsuranceData.setReferralVOList( homeInsuranceVO.getReferralVOList() );
			}
			
			homeInsuranceVO = (PolicyDataVO)TaskExecutor.executeTasks( "HOME_INSURANCE_SAVE", homeInsuranceVO );
			// Email trigger for referral start
			if (!rulePassed && !Utils.isEmpty(homeInsuranceVO.getCommonVO().getPolicyId())) {
				CommonHandler.populateAndTriggerEmail(homeInsuranceVO, contextPath, B2CEmailTriggers.REFERRAL, null);
			}
			// Email trigger for referral end
			//populatePackagePremium(homeInsuranceData);
			
			// HOME Email trigger on GET QUOTE start
			/*if (rulePassed && !Utils.isEmpty(homeInsuranceVO.getCommonVO().getPolicyId()) && !completePurchaseInd && !Utils.isEmpty(contextPath)) {
				populatePackagePremium(homeInsuranceVO); // Load the premium back
				CommonHandler.populateAndTriggerEmail(homeInsuranceVO, contextPath, B2CEmailTriggers.HOME_CREATE_QUOTE, null);
			}*/
			// end
			Calendar cal = Calendar.getInstance();  
	        cal.add(Calendar.DATE, 30);
	 		SimpleDateFormat dateFormat = new SimpleDateFormat(com.Constant.CONST_MM_DD_YYYY);
	 		homeInsuranceVO.setEndDate( dateFormat.parse(dateFormat.format(cal.getTime() ) ) );
	 		
		return homeInsuranceData;
	}

	private void handlePromotionalCode(PolicyDataVO homeInsuranceData) {
		if(!Utils.isEmpty(homeInsuranceData.getGeneralInfo().getSourceOfBus()) && !Utils.isEmpty(homeInsuranceData.getGeneralInfo().getSourceOfBus().getPromoCode())){
			
			Boolean isContentEmpty = false;
			Date effDate  = homeInsuranceData.getScheme().getEffDate();
			
			homeInsuranceData.getScheme().setEffDate(new Date());
			
			@SuppressWarnings( "unchecked" )
			DataHolderVO<Object[]> holder = (DataHolderVO<Object[]>) TaskExecutor.executeTasks( "HOME_INSURANCE_LOAD_PROMO_CODES", homeInsuranceData );
			
			homeInsuranceData.getScheme().setEffDate(effDate);
			
			@SuppressWarnings( "unchecked" )
			Map<String, Object> promoCodeDetails = (Map<String, Object>) holder.getData()[0];
			
			@SuppressWarnings("unchecked")
			List<Short> freeCoverCodes = (List<Short>) promoCodeDetails.get( "promotionalCodes" );
			
			Double promoDisc = Double.valueOf( promoCodeDetails.get( "promoDiscount" ).toString());
			
			String promoCodeDescription = promoCodeDetails.get( "promotionalCodeDesc" ).toString();
			
			//to check content for free cover
			for (CoverDetailsVO coverDetailsVO : ((HomeInsuranceVO) homeInsuranceData)
					.getCovers()) {
				//to check content
				if (!Utils.isEmpty(coverDetailsVO.getCoverName()) && coverDetailsVO.getCoverCodes().getCovCode() == SvcConstants.DEFAULT_HOME_COVER_CODE
						&& coverDetailsVO.getRiskCodes().getRiskType().equals( ( SvcConstants.CONTENT_RISK_TYPE_CODE ))) {
					
					if(Utils.isEmpty( coverDetailsVO.getSumInsured() ) || Utils.isEmpty( coverDetailsVO.getSumInsured().getSumInsured() ) 
					|| ( coverDetailsVO.getSumInsured().getSumInsured() <= 0	
					&& Utils.isEmpty( coverDetailsVO.getSumInsured().geteDesc() ) ) ){
						//content is empty
						isContentEmpty = true;
					}
				}
			}
				
			for (CoverDetailsVO coverDetailsVO : ((HomeInsuranceVO) homeInsuranceData)
					.getCovers()) {

				if (freeCoverCodes.contains(coverDetailsVO.getCoverCodes().getCovCode()) && !isContentEmpty) {//if content is not empty
					
					coverDetailsVO.getSumInsured().setPromoCover(Boolean.TRUE);
					coverDetailsVO.setIsCovered("on");
					if (coverDetailsVO.getCoverCodes().getCovCode() != 2) {
						String category = "PAS_HOME_"
								+ coverDetailsVO.getRiskCodes().getRiskType() + "_"
								+ coverDetailsVO.getCoverCodes().getCovCode() + "_1";
						
						LookUpListVO lookUpListVO = SvcUtils.getLookUpCodesList(category, AppConstants.B2C_HOME_DEFAULT_SCHEME, AppConstants.B2C_HOME_DEFAULT_TARIFF);
						lookUpListVO.getLookUpList().get(0).getCode();
								

						if (coverDetailsVO.getCoverCodes().getCovCode() == 4) {
							Double sumInsuredValue = Double.valueOf(lookUpListVO.getLookUpList().get(0).getCode().toString());
							coverDetailsVO.getSumInsured().setSumInsured(sumInsuredValue);
						}
						else {
							String sumInsuredValue = lookUpListVO.getLookUpList().get(0).getCode().toString();
							coverDetailsVO.getSumInsured().seteDesc(sumInsuredValue);
						}
					}

				}
				else {
					coverDetailsVO.getSumInsured().setPromoCover(Boolean.FALSE);
				}
			}
			
			homeInsuranceData.getPremiumVO().setPromoDiscPerc(promoDisc);
			homeInsuranceData.getGeneralInfo().getSourceOfBus().setPromoCodeDesc( promoCodeDescription );
		}
	}
	
	@Override
	public PolicyDataVO saveHomeInsuredDetails(PolicyDataVO homeInsuranceData, boolean completePurchaseInd, String contextPath , boolean isPrintCase){
		LOGGER.info("Entered HomeInsuranceSvcHandler.saveHomeInsuredDetails method.");
		try{
			//updateCoverDetails(homeInsuranceData);
			setDefaultValues(homeInsuranceData);
			homeInsuranceData.getCommonVO().setLoggedInUser(homeInsuranceData.getCommonVO().getLoggedInUser());
			homeInsuranceData.setLoggedInUser( homeInsuranceData.getCommonVO().getLoggedInUser() );
			String partnerName = null;
			if(!Utils.isEmpty( homeInsuranceData.getGeneralInfo() )&& !Utils.isEmpty( homeInsuranceData.getGeneralInfo().getSourceOfBus() ))
			{
				partnerName = homeInsuranceData.getGeneralInfo().getSourceOfBus().getPartnerName();
			}
			
			LOGGER.info("HomeInsuranceSvcHandler.saveHomeInsuredDetails method, before invoking the rule engine.");
			
			/* Invoke the rule engine */
			boolean rulePassed = ReferralUtils.executeReferralTaskHome( (HomeInsuranceVO)homeInsuranceData, "", "Home Insured Details", "",AppConstants.HOME_INSURED_SECTION_ID );
			LOGGER.info("HomeInsuranceSvcHandler.saveHomeInsuredDetails method, invoking the rule engine, -completes.");
			/* Populate the task vo if referral is present */
			if(!rulePassed){
				ReferralUtils.populateTaskVO(homeInsuranceData);
			}
			LOGGER.info("HomeInsuranceSvcHandler.saveHomeInsuredDetails method, calling HomeInsuranceSVCWrapper.saveHomeInsuranceInsuredDetails, - starts.");
			homeInsuranceData = (PolicyDataVO) TaskExecutor.executeTasks( "HOME_INSURANCE_SAVE_B2C_INSURED", homeInsuranceData );
			LOGGER.info("Calling HomeInsuranceSVCWrapper.saveHomeInsuranceInsuredDetails, - completes.");
			if(!Utils.isEmpty( partnerName ))
			{
				if (Utils.isEmpty(homeInsuranceData.getGeneralInfo())) {
					GeneralInfoVO generalInfo = new GeneralInfoVO();
					homeInsuranceData.setGeneralInfo(generalInfo);
				}
				if(Utils.isEmpty(homeInsuranceData.getGeneralInfo().getSourceOfBus())){
					SourceOfBusinessVO sourceOfBusinessVO = new SourceOfBusinessVO();
					homeInsuranceData.getGeneralInfo().setSourceOfBus(sourceOfBusinessVO);
				}
				homeInsuranceData.getGeneralInfo().getSourceOfBus().setPartnerName(partnerName);
				LOGGER.info("HomeInsuranceSVCWrapper.saveHomeInsuredDetails method, calling HomeInsuranceSvcHandler.loadPartnerMgmtDetails method, - starts.");
				homeInsuranceData = (HomeInsuranceVO) HomeInsuranceSvcHandler
						.loadPartnerMgmtDetails((HomeInsuranceVO)homeInsuranceData);
				
			}
			populatePackagePremium(homeInsuranceData);
			
			LOGGER.info("HomeInsuranceSVCWrapper.saveHomeInsuredDetails method, calling CommonHandler.populateAndTriggerEmail method - starts.");
			
			//Trigger email if referral has happened start
			if (!rulePassed && !Utils.isEmpty(homeInsuranceData.getCommonVO().getPolicyId())) {
				CommonHandler.populateAndTriggerEmail(homeInsuranceData,null,B2CEmailTriggers.REFERRAL,null);
			}
			//Trigger email if referral has happened end
			
			// If rule is passed then trigger the email with proposal document start
			if (rulePassed && !isPrintCase && !Utils.isEmpty(homeInsuranceData.getCommonVO().getPolicyId()) && !completePurchaseInd && !Utils.isEmpty(contextPath)) {
				CommonHandler.populateAndTriggerEmail(homeInsuranceData, contextPath, B2CEmailTriggers.HOME_SAVE_FOR_LATER, null);
			}
			// If rule is passed then trigger the email with proposal document end
			LOGGER.info("HomeInsuranceSVCWrapper.saveHomeInsuredDetails method, calling CommonHandler.populateAndTriggerEmail method - completes.");
			
		}
		catch(BusinessException be){
			LOGGER.error("HomeInsuranceSVCWrapper.saveHomeInsuredDetails method, BusinessException:"+be.getMessage());
			throw new BusinessException(be.getErrorKeysList().get(0), be.getCause(), be.getMessage());
		}
		catch(Exception exp){
			LOGGER.debug("Exception occured while saving home insured details - "+exp.getMessage());
		}
		LOGGER.info("Exiting HomeInsuranceSvcHandler.saveHomeInsuredDetails method.");
		return homeInsuranceData;
	}
	
	
	@Override
	public PolicyDataVO convertToPolicy(PolicyDataVO homeInsuranceData){

		try{

			List<BaseVO> inputVoList = new ArrayList<BaseVO>();
			inputVoList.add( new PolicyVO() );

			PaymentVO paymentvo = new PaymentVO();
			if( !Utils.isEmpty( paymentvo ) ){
				inputVoList.add( paymentvo );
			}

			if( !Utils.isEmpty( homeInsuranceData.getCommonVO() ) ){
				inputVoList.add( homeInsuranceData.getCommonVO() );
			}
			else{
				inputVoList.add( new CommonVO() );
			}

			DataHolderVO<List<BaseVO>> dataHolderVO = new DataHolderVO<List<BaseVO>>();

			dataHolderVO.setData( inputVoList );
			CommonVO commonVO = (CommonVO) TaskExecutor.executeTasks( "CONVERT_TO_POLICY", dataHolderVO );
		}
		catch(BusinessException be){
			throw new BusinessException(be.getErrorKeysList().get(0), be.getCause(), be.getMessage());
		}
		catch(Exception exp){
			exp.printStackTrace();
		}
		return homeInsuranceData;
	}
	
	@Override
	public PolicyDataVO loadHomeInsuranceDetails(HomeInsuranceVO homeInsuranceData){
		
		PolicyDataVO policyDataVO = null;
		try{
			setDefaultValues(homeInsuranceData);
			homeInsuranceData.getCommonVO().setLoggedInUser(homeInsuranceData.getCommonVO().getLoggedInUser());
			if( LOB.HOME.equals( homeInsuranceData.getCommonVO().getLob() ) ){
				policyDataVO = (PolicyDataVO) TaskExecutor.executeTasks( com.Constant.CONST_HOME_INSURANCE_LOAD, homeInsuranceData.getCommonVO() );
				if (policyDataVO.getPolicyType() == null && homeInsuranceData.getPolicyType()!=null) {
					policyDataVO.setPolicyType(homeInsuranceData.getPolicyType());
				}
				if( policyDataVO.getGeneralInfo().getInsured().getFirstName().equals(AppConstants.HOME_FIRST_NAME_DUMMY)
						&& policyDataVO.getGeneralInfo().getInsured().getLastName().equals(AppConstants.HOME_LAST_NAME_DUMMY) 
						&& ( Utils.isEmpty(policyDataVO.getGeneralInfo().getInsured().getAddress()) || 
								policyDataVO.getGeneralInfo().getInsured().getAddress().getPoBox().equals(AppConstants.HOME_PO_BOX_DUMMY) ) ){ 
					policyDataVO.getGeneralInfo().getInsured().setFirstName(null);
					homeInsuranceData.getGeneralInfo().getInsured().setFirstName(null);
					policyDataVO.getGeneralInfo().getInsured().setLastName(null);
					homeInsuranceData.getGeneralInfo().getInsured().setLastName(null);
					if(Utils.isEmpty(homeInsuranceData.getGeneralInfo().getInsured().getAddress())){
						homeInsuranceData.getGeneralInfo().getInsured().setAddress(new AddressVO());
					}
					policyDataVO.getGeneralInfo().getInsured().getAddress().setPoBox(null);
					homeInsuranceData.getGeneralInfo().getInsured().getAddress().setPoBox(null);
				}
				if(Utils.isEmpty( policyDataVO.getGeneralInfo().getSourceOfBus().getPromoCodeDesc() )){
					policyDataVO.getGeneralInfo().getSourceOfBus().setPromoCodeDesc(homeInsuranceData.getGeneralInfo().getSourceOfBus().getPromoCodeDesc());
				}
				/*// For VAT 142244
				if(!Utils.isEmpty(policyDataVO.getCommonVO().getPremiumVO())&& policyDataVO.getCommonVO().getPremiumVO().getVatTaxPerc()==0.0 && policyDataVO.getPremiumVO().getVatTaxPerc()!=0.0 ) {
					homeInsuranceData.getCommonVO().getPremiumVO().setVatTaxPerc(policyDataVO.getPremiumVO().getVatTaxPerc());
				}*/
				
				
				populatePackagePremium(policyDataVO);
			}
		}
		catch(BusinessException be){
			throw new BusinessException(be.getErrorKeysList().get(0), be.getCause(), be.getMessage());
		}
		catch(Exception exp){
			exp.printStackTrace();
		}
		return policyDataVO;
	}
	
	/**
	 * Method to default the required attributes for B2C
	 * @param homeInsuranceData
	 * @throws ParseException 
	 */
public static void setDefaultValues(PolicyDataVO homeInsuranceData) throws ParseException{
		
		LOGGER.info( "Setting default values for B2C Home Insurance " );
		CommonVO commonVO = homeInsuranceData.getCommonVO();
		if(Utils.isEmpty( homeInsuranceData.getGeneralInfo() )){
			GeneralInfoVO generalInfo =  new GeneralInfoVO();
			generalInfo.setInsured( new InsuredVO() );
			homeInsuranceData.setGeneralInfo(generalInfo);
		}
		AdditionalInsuredInfoVO additionalInfo = new AdditionalInsuredInfoVO();
		additionalInfo.setProcessingLoc( AppConstants.B2C_DEFAULT_DEPLOYED_LOCATION );
		homeInsuranceData.getGeneralInfo().setAdditionalInfo(additionalInfo);
		
		UserProfile profile = (UserProfile)commonVO.getLoggedInUser();
		//CTS - 02.09.2020 - TFS#42533 - Retrieve quote in B2B showing errors - Starts
		if (commonVO.getLoggedInUser().getUserId() == null) {
			homeInsuranceData.getGeneralInfo().setIntAccExecCode(profile.getRsaUser().getUserId());
		} else {
			// Fix given for Wunderman WebService for user 993 
			//CTS - 17.09.2020 - TFS#42533 - Retrieve quote in B2B showing errors - Starts
			if(Utils.isEmpty(homeInsuranceData.getGeneralInfo().getIntAccExecCode())){
			homeInsuranceData.getGeneralInfo().setIntAccExecCode(new Integer(commonVO.getLoggedInUser().getUserId()));
			}
			//CTS - 17.09.2020 - TFS#42533 - Retrieve quote in B2B showing errors - Ends
		}
		//CTS - 02.09.2020 - TFS#42533 - Retrieve quote in B2B showing errors - Ends
		//AuthenticationInfoVO authenticationInfoVO = new AuthenticationInfoVO();
		AuthenticationInfoVO authenticationInfoVO = homeInsuranceData.getAuthenticationInfoVO();
		if(Utils.isEmpty( authenticationInfoVO )){
			authenticationInfoVO = new AuthenticationInfoVO();
		}
		authenticationInfoVO.setAccountingDate( new Date() );
		//CTS - 02.09.2020 - TFS#42533 - Retrieve quote in B2B showing errors - Starts
			if (commonVO.getLoggedInUser().getUserId() == null) {
		
		authenticationInfoVO.setIntAccExecCode(profile.getRsaUser().getUserId());
		 }else {
			// Fix given for Wunderman WebService for user 993
			//CTS - 17.09.2020 - TFS#42533 - Retrieve quote in B2B showing errors - Starts
				if(Utils.isEmpty(authenticationInfoVO.getIntAccExecCode())){
					authenticationInfoVO.setIntAccExecCode(new Integer(commonVO.getLoggedInUser().getUserId()));
				}
				//CTS - 17.09.2020 - TFS#42533 - Retrieve quote in B2B showing errors - Ends
			//authenticationInfoVO.setIntAccExecCode(new Integer(commonVO.getLoggedInUser().getUserId()));
		}
				//CTS - 02.09.2020 - TFS#42533 - Retrieve quote in B2B showing errors - Ends
		homeInsuranceData.setAuthenticationInfoVO(authenticationInfoVO);
		homeInsuranceData.getGeneralInfo().getInsured().setUpdateMaster(true);
		
		if(!Utils.isEmpty( homeInsuranceData.getGeneralInfo().getInsured().getAddress() ) ){
			 homeInsuranceData.getGeneralInfo().getInsured().getAddress().setCountry( AppConstants.DEFAULT_CUSTOMER_COUNTRY );
		}
		if(Utils.isEmpty( homeInsuranceData.getGeneralInfo().getSourceOfBus() )){
			SourceOfBusinessVO sourceOfBusinessVO = new SourceOfBusinessVO();
			homeInsuranceData.getGeneralInfo().setSourceOfBus(sourceOfBusinessVO);
		}
		
		if(Utils.isEmpty(homeInsuranceData.getGeneralInfo().getSourceOfBus().getCustomerSource())){
			homeInsuranceData.getGeneralInfo().getSourceOfBus().setCustomerSource(AppConstants.B2C_DEFAULT_CUST_SRC);
		}
		if(Utils.isEmpty(homeInsuranceData.getGeneralInfo().getSourceOfBus().getDistributionChannel())){
			homeInsuranceData.getGeneralInfo().getSourceOfBus().setDistributionChannel(AppConstants.B2C_DEFAULT_DIST_CHANNEL);
		}
		if(Utils.isEmpty(homeInsuranceData.getGeneralInfo().getSourceOfBus().getBrokerName()) && 
				Utils.isEmpty(homeInsuranceData.getGeneralInfo().getSourceOfBus().getDirectSubAgent())){
			homeInsuranceData.getGeneralInfo().getSourceOfBus().setBrokerName(AppConstants.B2C_DEFAULT_BROKER_NAME);
		}
		//homeInsuranceData.getGeneralInfo().getSourceOfBus().setBrokerName(43);
		ClaimsSummaryVO claimsSummaryVO = new ClaimsSummaryVO();
		if(Utils.isEmpty(homeInsuranceData.getGeneralInfo().getSourceOfBus().getSourceOfBusiness())){
			claimsSummaryVO.setSourceOfBusiness(AppConstants.B2C_DEFAULT_SRC_OF_BUS);
		}
		else
		{
			claimsSummaryVO.setSourceOfBusiness(homeInsuranceData.getGeneralInfo().getSourceOfBus().getSourceOfBusiness());
		}
		
		homeInsuranceData.getGeneralInfo().setClaimsHistory(claimsSummaryVO);
		homeInsuranceData.setPolicyClassCode(AppConstants.HOME_CLASS_CODE);
		homeInsuranceData.setClassCode(AppConstants.HOME_CLASS_CODE);
		homeInsuranceData.setPolicyType(AppConstants.HOME_POLICY_TYPE);

		homeInsuranceData.setReferralVO(new ReferralVO());
		PremiumVO premiumVO = homeInsuranceData.getPremiumVO();
		if(Utils.isEmpty( premiumVO )){
			homeInsuranceData.setPremiumVO(new PremiumVO());
		}
		//homeInsuranceData.setPremiumVO(new PremiumVO());
		
		if(Utils.isEmpty( homeInsuranceData.getScheme() )){
			homeInsuranceData.setScheme( new SchemeVO() );
		}
		SchemeVO schemeVO = homeInsuranceData.getScheme();
		
		if(!Utils.isEmpty( schemeVO ) && Utils.isEmpty( schemeVO.getSchemeCode() )){
			schemeVO.setSchemeCode( Integer.valueOf(AppConstants.B2C_HOME_DEFAULT_SCHEME ));
		}
		
		if(!Utils.isEmpty( schemeVO ) && Utils.isEmpty( schemeVO.getTariffCode() )){
			schemeVO.setTariffCode(  Integer.valueOf(AppConstants.B2C_HOME_DEFAULT_TARIFF ) );
		}
		schemeVO.setPolicyType( AppConstants.HOME_POLICY_TYPE.toString() );
		schemeVO.setPolicyCode( AppConstants.HOME_POLICY_TYPE );
		if(Utils.isEmpty( schemeVO.getEffDate() )){
			Calendar cal = Calendar.getInstance();  
			cal.add(Calendar.DATE, 1);
			SimpleDateFormat dateFormat = new SimpleDateFormat(com.Constant.CONST_MM_DD_YYYY);
			schemeVO.setEffDate( dateFormat.parse(dateFormat.format(cal.getTime() ) ) );
			cal.add( Calendar.YEAR, 1 );
			cal.add( Calendar.DATE, -1 );
			schemeVO.setExpiryDate( dateFormat.parse(dateFormat.format(cal.getTime() ) ) );
		}

		//setCommonDetails(homeInsuranceData);
		// CTS 20.07.2020 - CR#11645 Retrieve Policy Details  SAT Issue-start
		if(!Utils.isEmpty(homeInsuranceData.getCommonVO()) && !Utils.isEmpty(homeInsuranceData.getCommonVO().getIsQuote())&& !homeInsuranceData.getCommonVO().getIsQuote()){
			AppUtils.populateCommonVOForPolicy( homeInsuranceData.getCommonVO() );
		}else{
			homeInsuranceData.getCommonVO().setIsQuote(Boolean.TRUE);
			AppUtils.populateCommonVO( homeInsuranceData.getCommonVO() );
		}
		
		if( !Utils.isEmpty( homeInsuranceData.getCommonVO() ) && !Utils.isEmpty( homeInsuranceData.getCommonVO().getPolicyId() ) && homeInsuranceData.getCommonVO().getIsQuote()){
                // CTS 20.07.2020 - CR#11645 Retrieve Policy Details  SAT Issue-end
			CommonOpSvc commonOpSvc = (CommonOpSvc) Utils.getBean( com.Constant.CONST_GECOMSVCBEAN );

			commonVO = (CommonVO) commonOpSvc.invokeMethod( "populateCommonDetails", homeInsuranceData.getCommonVO() );
		}
		
		if (!Utils.isEmpty(commonVO)) {
			homeInsuranceData.setCommonVO(commonVO);
		}
		homeInsuranceData.setEndtId( homeInsuranceData.getCommonVO().getEndtId() );
		homeInsuranceData.setStatus(homeInsuranceData.getCommonVO().getStatus());
		if( Utils.isEmpty( homeInsuranceData.getCommonVO().getLob() ) ){
			homeInsuranceData.getCommonVO().setLob( LOB.HOME );
		}
		//homeInsuranceData.setCommonVO(commonVO);
		//homeInsuranceData.setStatus(6);
		// 142244 For VAT
		if (Utils.isEmpty(homeInsuranceData.getCommonVO().getPremiumVO())) {
			homeInsuranceData.getCommonVO().setPremiumVO(new PremiumVO());
		}
		AppUtils.updateInsuredName( homeInsuranceData );
	}
	
	private void setCommonDetails(PolicyDataVO homeInsuranceData){

		CommonVO commonVO = homeInsuranceData.getCommonVO();
		commonVO.setAppFlow( Flow.CREATE_QUO );
		commonVO.setIsQuote( true );
		commonVO.setLocCode( AppConstants.B2C_DEFAULT_DEPLOYED_LOCATION );
		commonVO.setEndtId( 0L );
		commonVO.setEndtNo(0L);
		//commonVO.setStatus( 6 );
		commonVO.setLob(LOB.HOME);

	}
	
	@SuppressWarnings("rawtypes")
	private void updateCoverDetails(PolicyDataVO homeInsuranceData){
		
		Iterator it = ((HomeInsuranceVO)homeInsuranceData).getCovers().iterator();
		while(it.hasNext()){
			CoverDetailsVO cover = (CoverDetailsVO) it.next();
			if( (Utils.isEmpty( cover.getSumInsured() ) || Utils.isEmpty( cover.getSumInsured().getSumInsured() ) 
					|| ( cover.getSumInsured().getSumInsured() <= 0	
					&& Utils.isEmpty( cover.getSumInsured().geteDesc() ) ) ) 
					&& !AppConstants.STATUS_ON.equalsIgnoreCase( cover.getIsCovered() ) ){
				it.remove();
			}
		}
	}
	
	/**
	 * 
	 * @param homeInsuranceData
	 * @throws ParseException 
	 */
	public void populatePackagePremium(PolicyDataVO homeInsuranceData) {

		double onlineDiscOrLoad = ( homeInsuranceData.getPremiumVO().getPremiumAmt() + homeInsuranceData.getPremiumVO().getMinPremiumApplied().doubleValue() )  * homeInsuranceData.getPremiumVO().getDiscOrLoadPerc() / 100 ;
		double promoDisc = 0.0;

		if(!Utils.isEmpty(homeInsuranceData.getPremiumVO().getPromoDiscPerc())){
			promoDisc =  ( homeInsuranceData.getPremiumVO().getPremiumAmt() + homeInsuranceData.getPremiumVO().getMinPremiumApplied().doubleValue() ) * homeInsuranceData.getPremiumVO().getPromoDiscPerc() / 100 ;
		}
		
		onlineDiscOrLoad = Double.valueOf( Currency.getFormattedCurrency( onlineDiscOrLoad ));
		//CTS - 21.07.2020 - SAT issue Transaction amount and VAT amount difference in B2B and API values - Fixed for B2C VAT difference
		BigDecimal onlineDisc =BigDecimal.valueOf(onlineDiscOrLoad);
		onlineDisc = onlineDisc.setScale(0,RoundingMode.HALF_UP);	
		promoDisc = Double.valueOf( Currency.getFormattedCurrency( promoDisc ) );
		homeInsuranceData.getPremiumVO().setPremiumAmt( homeInsuranceData.getPremiumVO().getPremiumAmt() + homeInsuranceData.getPremiumVO().getMinPremiumApplied().doubleValue() + onlineDisc.doubleValue() - promoDisc);
		
		homeInsuranceData.getPremiumVO().setDiscOrLoadAmt( onlineDisc.abs());
		homeInsuranceData.getPremiumVO().setSpecialDiscount( promoDisc );
		
		
		// For 142244 VAT implementation
		Date effectiveDate = homeInsuranceData.getScheme().getEffDate();
		Date expiryDate = homeInsuranceData.getScheme().getExpiryDate();
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
		double vatTax = 0.0;
		double vatablePremium=0.0;
		
		
		if (!Utils.isEmpty(homeInsuranceData.getCommonVO().getPremiumVO()
				.getVatTaxPerc())) {
			homeInsuranceData.getPremiumVO().setVatTaxPerc(
					homeInsuranceData.getCommonVO().getPremiumVO()
							.getVatTaxPerc());
		/*	vatTax = (homeInsuranceData.getPremiumVO().getPremiumAmt() * homeInsuranceData
					.getCommonVO().getPremiumVO().getVatTaxPerc()) / 100;*/
			
			vatTax = ((homeInsuranceData.getPremiumVO().getPremiumAmt()*
					(AppUtils.getDateDifference(expiryDate,maxDate) ) / policyPeriod) * homeInsuranceData
					.getCommonVO().getPremiumVO().getVatTaxPerc()) / 100;
			
			vatTax = Double.parseDouble( AppUtils.getFormattedNumberWithDecimals(vatTax));
			
			vatablePremium = ((homeInsuranceData.getPremiumVO().getPremiumAmt()*
						(AppUtils.getDateDifference(expiryDate,maxDate) ) / policyPeriod)) ;
					
			
			homeInsuranceData.getCommonVO().getPremiumVO().setVatablePrm(vatablePremium);
			homeInsuranceData.getPremiumVO().setVatablePrm(vatablePremium);
			
			homeInsuranceData.getPremiumVO().setVatTax(vatTax);
			homeInsuranceData.getPremiumVO().setPremiumAmt(
					homeInsuranceData.getPremiumVO().getPremiumAmt() + vatTax);
			homeInsuranceData.getCommonVO().getPremiumVO().setVatTax(vatTax);
			/*
			 * homeInsuranceData.getCommonVO().getPremiumVO().setPremiumAmt(
			 * homeInsuranceData.getPremiumVO().getPremiumAmt());
			 */
		}
		//DAOUtils.updateVATPremiumHome(vatTax, homeInsuranceData.getCommonVO().getPolicyId(), null);
		// CTS 20.07.2020 - CR#11645 Retrieve Policy Details  SAT Issue-start
	if(!Utils.isEmpty(homeInsuranceData.getCommonVO()) && (Utils.isEmpty(homeInsuranceData.getCommonVO().getIsQuote())||( !Utils.isEmpty(homeInsuranceData.getCommonVO().getIsQuote()) && homeInsuranceData.getCommonVO().getIsQuote()))){
		DAOUtils.updateVATPremiumHome(vatTax, homeInsuranceData.getCommonVO());
	}
	// CTS 20.07.2020 - CR#11645 Retrieve Policy Details  SAT Issue-end
		LOGGER.debug("Actual Premium  ================="
				+ homeInsuranceData.getPremiumVO().getPremiumAmtActual());
		LOGGER.debug("vat Tax =================" + vatTax);
		LOGGER.debug("VatablePremium =================" + vatablePremium);
		LOGGER.debug("Premium Amount including Vat Tax ================="
				+ homeInsuranceData.getPremiumVO().getPremiumAmt());
	}
	
	private void mergeHomeInsuranceDetails(PolicyDataVO policyDataVO, HomeInsuranceVO dataToSave,boolean isRenewal){
		
		HomeInsuranceVO savedData = (HomeInsuranceVO) policyDataVO;
		AuthenticationInfoVO authenticationInfoVO = policyDataVO.getAuthenticationInfoVO();
		if(Utils.isEmpty( authenticationInfoVO )){		
			authenticationInfoVO = new AuthenticationInfoVO();
		}
		authenticationInfoVO.setAccountingDate( new Date() );
		savedData.setAuthenticationInfoVO(authenticationInfoVO);
		savedData.setStatus( 6 );
		savedData.setPolicyClassCode(Integer.valueOf( Utils.getSingleValueAppConfig( "HOME_CLASS_CODE" ) ) );
		savedData.setPolicyType(Integer.valueOf( SvcConstants.HOME_POL_TYPE ) );
		savedData.setEndtId( dataToSave.getCommonVO().getEndtId() );
		savedData.getScheme().setPolicyType( "7" );
		savedData.setCommonVO( dataToSave.getCommonVO() );
		savedData.getGeneralInfo().getInsured().setUpdateMaster( true );
		
		savedData.getGeneralInfo().getInsured().setMobileNo( dataToSave.getGeneralInfo().getInsured().getMobileNo() );
		savedData.getGeneralInfo().getInsured().setEmailId( dataToSave.getGeneralInfo().getInsured().getEmailId() );
		//CTS - TFS# 41983 - 27.08.2020 - CSH_CITY population in T_MAS_CASH_CUSTOMER_QUO table and City value under Customer details Retrieve Quote Response - Starts
		savedData.getGeneralInfo().getInsured().setCity(dataToSave.getGeneralInfo().getInsured().getCity());
		//CTS - TFS# 41983 - 27.08.2020 - CSH_CITY population in T_MAS_CASH_CUSTOMER_QUO table and City value under Customer details Retrieve Quote Response - Ends
		savedData.getGeneralInfo().getAdditionalInfo().setAffinityRefNo(dataToSave.getGeneralInfo().getSourceOfBus().getPartnerName());
		savedData.getBuildingDetails().setOwnershipStatus( dataToSave.getBuildingDetails().getOwnershipStatus() );
		if(!Utils.isEmpty(  dataToSave.getBuildingDetails().getSumInsured() ) && !Utils.isEmpty(  dataToSave.getBuildingDetails().getSumInsured().getSumInsured() )){
			savedData.getBuildingDetails().getSumInsured().setSumInsured( dataToSave.getBuildingDetails().getSumInsured().getSumInsured() );
		}
		else{
			savedData.getBuildingDetails().getSumInsured().setSumInsured( null );
		}		
		if(!Utils.isEmpty( savedData ) && !Utils.isEmpty( savedData.getBuildingDetails() ) 
				&& !Utils.isEmpty( savedData.getBuildingDetails().getMortgageeName() ) &&
				!savedData.getBuildingDetails().getMortgageeName().equals("Select")){
			String mortgage[] = savedData.getBuildingDetails().getMortgageeName().split("#");
			if(mortgage.length > 1){
				savedData.getBuildingDetails().setMortgageeName(mortgage[1].trim());
			}
			else{
				String mortgagee = SvcUtils.getLookUpDescription( "PAS_MORTGAGEE_NAME", "ALL", "ALL", Integer.valueOf( savedData.getBuildingDetails().getMortgageeName() ) );
				savedData.getBuildingDetails().setMortgageeName( mortgagee.trim() );
			}			
		}
		else {
			savedData.getBuildingDetails().setMortgageeName( null );
		}
		savedData.getBuildingDetails().getRiskCodes().setRiskCat( dataToSave.getBuildingDetails().getRiskCodes().getRiskCat() );
		savedData.getBuildingDetails().getRiskCodes().setRiskCode( dataToSave.getBuildingDetails().getRiskCodes().getRiskCode() );
		savedData.getBuildingDetails().getRiskCodes().setRiskType( dataToSave.getBuildingDetails().getRiskCodes().getRiskType() );
		savedData.getBuildingDetails().setCoverCodes( dataToSave.getBuildingDetails().getCoverCodes() );
		if( !Utils.isEmpty( ( (HomeInsuranceVO)policyDataVO).getBuildingDetails().getArea() ) ){
		String area = SvcUtils.getLookUpDescription( "PAS_AREA", ALL, ALL, Integer.valueOf( ((HomeInsuranceVO)policyDataVO).getBuildingDetails().getArea() ) );
		savedData.getBuildingDetails().setArea( area.trim() );
		}
		savedData.setCovers( dataToSave.getCovers() );
		savedData.getGeneralInfo().getSourceOfBus().setPromoCode( dataToSave.getGeneralInfo().getSourceOfBus().getPromoCode() );
		if(isRenewal){
			savedData.getGeneralInfo().getInsured().setFirstName( dataToSave.getGeneralInfo().getInsured().getFirstName() );
			savedData.getGeneralInfo().getInsured().setLastName( dataToSave.getGeneralInfo().getInsured().getLastName() );
			savedData.getGeneralInfo().getInsured().getAddress().setPoBox( dataToSave.getGeneralInfo().getInsured().getAddress().getPoBox() );
			savedData.getGeneralInfo().getInsured().getAddress().setEmirates( dataToSave.getGeneralInfo().getInsured().getAddress().getEmirates() );
			savedData.getGeneralInfo().getInsured().setRoyaltyType( dataToSave.getGeneralInfo().getInsured().getRoyaltyType() );
			savedData.getGeneralInfo().getInsured().setGuestCardNo( dataToSave.getGeneralInfo().getInsured().getGuestCardNo() );
			savedData.getBuildingDetails().setBuildingname( dataToSave.getBuildingDetails().getBuildingname() );
			savedData.getBuildingDetails().setFlatVillaNo( dataToSave.getBuildingDetails().getFlatVillaNo() );
			savedData.getBuildingDetails().setTypeOfProperty( dataToSave.getBuildingDetails().getTypeOfProperty() );
			savedData.getBuildingDetails().setEmirates( dataToSave.getBuildingDetails().getEmirates() );
			String emirates = SvcUtils.getLookUpDescription( "CITY", ALL, ALL, Integer.valueOf( ((HomeInsuranceVO)policyDataVO).getBuildingDetails().getEmirates() ) );
			savedData.getBuildingDetails().setEmirates( emirates );
			//savedData.getBuildingDetails().setArea( dataToSave.getBuildingDetails().getArea() );
			mapInfoMapDetailsForWS(dataToSave,savedData);
			if(!Utils.isEmpty( dataToSave ) && !Utils.isEmpty( dataToSave.getBuildingDetails() ) 
					&& !Utils.isEmpty( dataToSave.getBuildingDetails().getMortgageeName() ) &&
					!dataToSave.getBuildingDetails().getMortgageeName().equals("Select")){
				String mortgage[] = dataToSave.getBuildingDetails().getMortgageeName().split("#");
				if(mortgage.length > 1){
					savedData.getBuildingDetails().setMortgageeName(mortgage[1].trim());
				}
				else{
					String mortgagee = SvcUtils.getLookUpDescription( "PAS_MORTGAGEE_NAME", "ALL", "ALL", Integer.valueOf( dataToSave.getBuildingDetails().getMortgageeName() ) );
					savedData.getBuildingDetails().setMortgageeName( mortgagee.trim() );
				}			
			}
			else {
				savedData.getBuildingDetails().setMortgageeName( null );
			}
			
			if( !Utils.isEmpty( dataToSave.getBuildingDetails().getArea() ) ){
				String area = SvcUtils.getLookUpDescription( "PAS_AREA", ALL, ALL, Integer.valueOf( dataToSave.getBuildingDetails().getArea() ) );
				savedData.getBuildingDetails().setArea( area.trim() );
			}
			if( !Utils.isEmpty( dataToSave.getBuildingDetails().getOtherDetails() ) ){
				savedData.getBuildingDetails().setOtherDetails( dataToSave.getBuildingDetails().getOtherDetails() );
			}else{
				savedData.getBuildingDetails().setOtherDetails( null );
			}
		}
	}
	
	private void mapInfoMapDetailsForWS(HomeInsuranceVO dataToSave, HomeInsuranceVO savedData) {
		if(!Utils.isEmpty(dataToSave.getBuildingDetails().getTotalNoFloors())) {
			savedData.getBuildingDetails().setTotalNoFloors(dataToSave.getBuildingDetails().getTotalNoFloors());
		}
		if(!Utils.isEmpty(dataToSave.getBuildingDetails().getTotalNoRooms())) {
			savedData.getBuildingDetails().setTotalNoRooms(dataToSave.getBuildingDetails().getTotalNoRooms());
		}
		if(!Utils.isEmpty(dataToSave.getBuildingDetails().getLatitude())) {
			savedData.getBuildingDetails().setLatitude(dataToSave.getBuildingDetails().getLatitude());
		}
		if(!Utils.isEmpty(dataToSave.getBuildingDetails().getLongitude())) {
			savedData.getBuildingDetails().setLongitude(dataToSave.getBuildingDetails().getLongitude());
		}
		if(!Utils.isEmpty(dataToSave.getBuildingDetails().getAddress())) {
			savedData.getBuildingDetails().setAddress(dataToSave.getBuildingDetails().getAddress());
		}
		if(!Utils.isEmpty(dataToSave.getBuildingDetails().getInforMapStatus())) {
			savedData.getBuildingDetails().setInforMapStatus(dataToSave.getBuildingDetails().getInforMapStatus());
		}
		if(!Utils.isEmpty(dataToSave.getGeneralInfo().getInsured().getVatRegNo())) {
			savedData.getGeneralInfo().getInsured().setVatRegNo(dataToSave.getGeneralInfo().getInsured().getVatRegNo());
		}
		if(!Utils.isEmpty(dataToSave.getGeneralInfo().getInsured().getNationality())) {
			savedData.getGeneralInfo().getInsured().setNationality(dataToSave.getGeneralInfo().getInsured().getNationality());
		}
	}

	/**
	 * Test Method to create VO for save simulation 
	 * @author Sarath
	 * @param req
	 * @return HomeInsuranceVO
	 * @throws ParseException
	 */
	private HomeInsuranceVO testGenerateHomeInsuranceVO() throws ParseException{
		
		LOGGER.info("Inside test VO generater for Home");
		HomeInsuranceVO homeInsuranceVO = new HomeInsuranceVO();
		
		BuildingDetailsVO buildingDetailsVO =new BuildingDetailsVO();
		CoverVO coverCodes =  new CoverVO();
		coverCodes.setCovCode(Short.parseShort("1"));
		// Added Integer.valueOf() to avoid sonar violation on 18-9-2017
		//coverCodes.setCovCriteriaCode(new Integer(1));
		coverCodes.setCovCriteriaCode(Integer.valueOf((1)));
		coverCodes.setCovTypeCode(Short.parseShort("0"));
		buildingDetailsVO.setCoverCodes(coverCodes);

		RiskVO riskCodes =  new RiskVO();
		// Added Integer.valueOf() to avoid sonar violation on 18-9-2017
		//riskCodes.setRiskCat(new Integer(1));
		riskCodes.setRiskCat(Integer.valueOf(1));
		riskCodes.setRiskType(Integer.valueOf(1));
		riskCodes.setRiskCode(Integer.valueOf(1));
		riskCodes.setBasicRskCode( 1 );
		buildingDetailsVO.setRiskCodes(riskCodes);
		
		SumInsuredVO sumInsuredVO = new SumInsuredVO();
		sumInsuredVO.setSumInsured( (double) 5000000 );
		buildingDetailsVO.setSumInsured( sumInsuredVO );
		homeInsuranceVO.setBuildingDetails(buildingDetailsVO);
		
		List<CoverDetailsVO> coversList = new ArrayList<CoverDetailsVO>() ;
		CoverVO coverCodes1 = new CoverVO();
		CoverVO coverCodes2 = new CoverVO();
		RiskVO riskCodes2 = new RiskVO();
		RiskVO riskCodes1 = new RiskVO();
		SumInsuredVO sumInsuredVO1 = new SumInsuredVO();
		sumInsuredVO1.setSumInsured( 2000.99 );
		riskCodes1.setBasicRskId( BigDecimal.valueOf( 12345 ));
		
		
		CoverDetailsVO cover1 = new CoverDetailsVO();
		coverCodes1.setCovCode( Short.valueOf( "1" ) );
		coverCodes1.setCovTypeCode( Short.valueOf( "0" ) );
		coverCodes1.setCovSubTypeCode( Short.valueOf( "0" ) );
		riskCodes1.setBasicRskCode( 1 );
		riskCodes1.setRiskCode( 2 );
		riskCodes1.setRiskType( 31 );
		riskCodes1.setRiskCat( 1 );

		cover1.setCoverName( "Contents" );
		cover1.setCoverCodes( coverCodes1 );
		cover1.setRiskCodes( riskCodes1 );
		cover1.setSumInsured( sumInsuredVO1 );
		coversList.add( cover1 );
		
		coverCodes2.setCovCode( Short.valueOf( "1" ) );
		coverCodes2.setCovTypeCode( Short.valueOf( "0" ) );
		coverCodes2.setCovSubTypeCode( Short.valueOf( "0" ) );
		riskCodes2.setBasicRskCode( 1 );
		riskCodes2.setRiskCode( 2 );
		riskCodes2.setRiskType( 32 );
		riskCodes2.setRiskCat( 1 );
		
		CoverDetailsVO cover = new CoverDetailsVO();
		cover.setCoverName( "Personal Possessions" );
		cover.setCoverCodes( coverCodes );
		cover.setRiskCodes( riskCodes2 );
		cover.setSumInsured( sumInsuredVO1 );
		coversList.add( cover );
		homeInsuranceVO.setCovers( coversList );
		
		SchemeVO schemeVO = new SchemeVO();
		schemeVO.setSchemeCode( 1070 );
		schemeVO.setPolicyType( "7" );
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		schemeVO.setEffDate( dateFormat.parse(dateFormat.format(new Date())) );
		homeInsuranceVO.setPolicyClassCode( 2 );
		homeInsuranceVO.setScheme( schemeVO );
		
		
		GeneralInfoVO generalInfo = new GeneralInfoVO();
		AdditionalInsuredInfoVO additionalInfo = new AdditionalInsuredInfoVO();
		generalInfo.setAdditionalInfo(additionalInfo);
		InsuredVO insured = new InsuredVO();
		insured.setEmailId("aa@bb.com");
		insured.setFirstName("first");
		insured.setLastName("last");
		insured.setMobileNo("95919658754");
		insured.setUpdateMaster(true);
		generalInfo.setInsured(insured);
		SourceOfBusinessVO sourceOfBusinessVO = new SourceOfBusinessVO();
		sourceOfBusinessVO.setDistributionChannel(Integer.valueOf( Utils.getSingleValueAppConfig( "DIST_CHANNEL_DIRECT" )));
		generalInfo.setSourceOfBus(sourceOfBusinessVO);
		generalInfo.setClaimsHistory(new ClaimsSummaryVO());
		homeInsuranceVO.setGeneralInfo(generalInfo);
		
		CommonVO commonVO = new CommonVO();
		commonVO.setAppFlow( Flow.CREATE_QUO );
		commonVO.setIsQuote( true );
		commonVO.setLocCode( 30 );
		commonVO.setEndtId( 0L );
		commonVO.setEndtNo(0L);
		commonVO.setStatus( 6 );
		commonVO.setLob(LOB.HOME);


		homeInsuranceVO.setCommonVO(commonVO);
		homeInsuranceVO.setStatus(6);
		homeInsuranceVO.setReferralVO(new ReferralVO());
		homeInsuranceVO.setAuthenticationInfoVO(new AuthenticationInfoVO());
		homeInsuranceVO.setPolicyType(7);
		homeInsuranceVO.setPremiumVO(new PremiumVO());

		return homeInsuranceVO;
	}	
	
	@Override
	public PolicyDataVO getRevisedHomeRenewalPremium( PolicyDataVO homeInsuranceVO ){
		homeInsuranceVO.getCommonVO().setIsQuote(Boolean.TRUE);
		AppUtils.populateCommonVO( homeInsuranceVO.getCommonVO() );
		homeInsuranceVO.getCommonVO().setLoggedInUser(homeInsuranceVO.getCommonVO().getLoggedInUser());
		homeInsuranceVO.setLoggedInUser( homeInsuranceVO.getCommonVO().getLoggedInUser() );
		
		//to be removed
		/*List<CoverDetailsVO> coversList = ((HomeInsuranceVO)homeInsuranceVO).getCovers();
		//for(CoverDetailsVO cover : ((HomeInsuranceVO)homeInsuranceVO).getCovers()){
		for(int i=0;i<coversList.size();i++){
			if(coversList(i).getCovCode() == 4 && !Utils.isEmpty( cover.getSumInsured( ) )){
				cover.getSumInsured( ).setSumInsured( null );
				((HomeInsuranceVO)homeInsuranceVO).getCovers().set( index, element )
			}
		}
		*/
		updateCoverDetails(homeInsuranceVO);
		homeInsuranceVO = (PolicyDataVO)TaskExecutor.executeTasks( "HOME_RENEWAL_RATING", homeInsuranceVO );
		return homeInsuranceVO;
	}
	
	
	@Override
	public PolicyDataVO saveHomeRenewalInsuranceDetails(PolicyDataVO homeInsuranceData, boolean completePurchaseInd, String contextPath, boolean isPrintCase){

		PolicyDataVO homeInsuranceVO =  new HomeInsuranceVO();
		try{
			//HomeInsuranceVO homeVO = testGenerateHomeInsuranceVO();
			//PolicyDataVO homeInsuranceVO =  new HomeInsuranceVO();
			homeInsuranceData.getCommonVO().setIsQuote(Boolean.TRUE);
			
			if(Utils.isEmpty(homeInsuranceData.getCommonVO().getPremiumVO().getVatCode()) && Utils.isEmpty(homeInsuranceData.getPremiumVO().getVatCode())){
				@SuppressWarnings("unchecked")
				DataHolderVO<Object[]> holder1 = (DataHolderVO<Object[]>) TaskExecutor
						.executeTasks("LOAD_HOME_VAT_RATE_AND_CODE",
								homeInsuranceData);
				@SuppressWarnings("unchecked")
				Map<String, Object> vat = (Map<String, Object>) holder1
						.getData()[0];

				LOGGER.debug("**********vatRate**********"
						+ vat.get(com.Constant.CONST_VATRATE));
				LOGGER.debug("**********vatCode**********"
						+ vat.get(com.Constant.CONST_VATCODE));

				homeInsuranceData.getPremiumVO().setVatTaxPerc(
						(Double) vat.get(com.Constant.CONST_VATRATE));
				homeInsuranceData.getPremiumVO().setVatCode(
						(Integer) vat.get(com.Constant.CONST_VATCODE));

				homeInsuranceData.getCommonVO().getPremiumVO()
						.setVatTaxPerc((Double) vat.get(com.Constant.CONST_VATRATE));
				homeInsuranceData.getCommonVO().getPremiumVO()
						.setVatCode((Integer) vat.get(com.Constant.CONST_VATCODE));
			}
			
			setDefaultValues(homeInsuranceData);
			AppUtils.populateCommonVO( homeInsuranceData.getCommonVO() );
			homeInsuranceData.getCommonVO().setLoggedInUser(homeInsuranceData.getCommonVO().getLoggedInUser());
			homeInsuranceData.setLoggedInUser( homeInsuranceData.getCommonVO().getLoggedInUser() );						
			//updateCoverDetails(homeInsuranceVO);			
			homeInsuranceVO = (PolicyDataVO) TaskExecutor.executeTasks( com.Constant.CONST_HOME_INSURANCE_LOAD, homeInsuranceData.getCommonVO() );
			( ( HomeInsuranceVO )homeInsuranceVO ).setStaffDetails( ( ( HomeInsuranceVO ) homeInsuranceData ).getStaffDetails() );

			//homeInsuranceVO.getPremiumVO().setDiscOrLoadPerc(homeInsuranceVO.getPremiumVO().getDiscOrLoadPerc());
			populatePackagePremium(homeInsuranceVO);
			mergeHomeInsuranceDetails(homeInsuranceVO, (HomeInsuranceVO)homeInsuranceData,true);		
			updateCoverDetails(homeInsuranceVO);	
			// Added by Anveshan...
			if(!Utils.isEmpty(homeInsuranceData.getScheme()))
			{
				homeInsuranceVO.setScheme(homeInsuranceData.getScheme());
			}
			
			/* Invoke the rule engine */
			boolean riskRulePassed = ReferralUtils.executeReferralTaskHome( (HomeInsuranceVO)homeInsuranceVO, "", "Home Risk Cover", "",AppConstants.HOME_RISK_SECTION_ID );
			
			boolean insuredRulePassed = ReferralUtils.executeReferralTaskHome( (HomeInsuranceVO)homeInsuranceVO, "", "Home Insured Details", "",AppConstants.HOME_INSURED_SECTION_ID );
			
			/* Populate the task vo if referral is present */
			if(!riskRulePassed || !insuredRulePassed){
				ReferralUtils.populateTaskVO(homeInsuranceVO);
				homeInsuranceVO.setLoggedInUser( homeInsuranceData.getCommonVO().getLoggedInUser() );
				homeInsuranceData.setReferralVOList( homeInsuranceVO.getReferralVOList() );
				completePurchaseInd = true;
			}
			
			/*Reset min prm before save so that loaded values are not passed to save again.*/
			homeInsuranceVO.getPremiumVO().setMinPremiumApplied( null );
			homeInsuranceVO.getGeneralInfo().getInsured().setBusType( AppConstants.BUS_TYPE_RENEWAL );
			
			// For VAT 142244
			if(!Utils.isEmpty(homeInsuranceVO.getCommonVO().getPremiumVO().getVatCode())){
				homeInsuranceVO.getPremiumVO().setVatCode(homeInsuranceVO.getCommonVO().getPremiumVO().getVatCode());
			}
			homeInsuranceVO = (PolicyDataVO)TaskExecutor.executeTasks( "RENEWAL_HOME_INSURANCE_SAVE", homeInsuranceVO );
			populatePackagePremium(homeInsuranceVO);
			// Email trigger for referral start
			if ( ( !riskRulePassed || !insuredRulePassed ) && !Utils.isEmpty(homeInsuranceVO.getCommonVO().getPolicyId())) {
				CommonHandler.populateAndTriggerEmail(homeInsuranceVO, contextPath, B2CEmailTriggers.REFERRAL, null);
			}
			
			// If rule is passed then trigger the email with proposal document start
			/* Commented as per Srini's requirement - Anveshan*/
			/*if ( !isPrintCase && ( riskRulePassed && insuredRulePassed ) && !Utils.isEmpty(homeInsuranceVO.getCommonVO().getPolicyId()) && !completePurchaseInd && !Utils.isEmpty(contextPath)) {
					CommonHandler.populateAndTriggerEmail(homeInsuranceVO, contextPath, B2CEmailTriggers.HOME_SAVE_FOR_LATER, null);
			}*/
			// If rule is passed then trigger the email with proposal document end			
			
			Calendar cal = Calendar.getInstance();  
	        cal.add(Calendar.DATE, 30);
	 		SimpleDateFormat dateFormat = new SimpleDateFormat(com.Constant.CONST_MM_DD_YYYY);
	 		homeInsuranceVO.setEndDate( dateFormat.parse(dateFormat.format(cal.getTime() ) ) );	 		
	 		
		}
		catch(BusinessException be){
			throw new BusinessException(be.getErrorKeysList().get(0), be.getCause(), be.getMessage());
		}
		catch(Exception exp){
			LOGGER.error( exp.getMessage(), exp );
			throw new SystemException( exp.getMessage() );
		}
		return homeInsuranceVO;
	}
	
	@Override
	public String getRenewalQuoteFromPolicy(long policy, String emailId)
	{
		String renQuote = null;
		CommonVO commonVO = new CommonVO();
		DataHolderVO policyIdHolder = new DataHolderVO();
		commonVO.setPolicyNo(policy);
		commonVO.setConcatPolicyNo(emailId);
				
		CommonOpSvc commonOpSvc = (CommonOpSvc) Utils.getBean( com.Constant.CONST_GECOMSVCBEAN );
		policyIdHolder = (DataHolderVO) commonOpSvc.invokeMethod( "getRenQuoteForPolicy", commonVO);
		
		renQuote = policyIdHolder.getData().toString();
		policyIdHolder = null;
		commonVO = null;
		
		return renQuote;
	}
	
	public static PolicyDataVO loadPartnerMgmtDetails(HomeInsuranceVO homeInsuranceData){
		LOGGER.info("Entered HomeInsuranceSvcHandler.loadPartnerMgmtDetails");
		try{
			homeInsuranceData.getCommonVO().setLoggedInUser(homeInsuranceData.getCommonVO().getLoggedInUser());
			LOGGER.info("HomeInsuranceSvcHandler.loadPartnerMgmtDetails method, calling ");
				TaskExecutor.executeTasks( "LOAD_PARTNERMGMT_DATA", homeInsuranceData );
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
		LOGGER.info("Exiting HomeInsuranceSvcHandler.loadPartnerMgmtDetails");
		return homeInsuranceData;
	}
	
	@Override
	public String getQuoteFromPolicy(long policy, String emailId)
	{
		String renQuote = null;
		CommonVO commonVO = new CommonVO();
		DataHolderVO policyIdHolder = new DataHolderVO();
		commonVO.setPolicyNo(policy);
		commonVO.setConcatPolicyNo(emailId);
				
		CommonOpSvc commonOpSvc = (CommonOpSvc) Utils.getBean( com.Constant.CONST_GECOMSVCBEAN );
		policyIdHolder = (DataHolderVO) commonOpSvc.invokeMethod( "getQuoteForPolicy", commonVO);
		
		renQuote = policyIdHolder.getData().toString();
		policyIdHolder = null;
		commonVO = null;
		
		return renQuote;
	}

	
	
}
