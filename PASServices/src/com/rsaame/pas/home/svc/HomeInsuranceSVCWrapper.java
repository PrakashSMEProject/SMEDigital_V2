/**
 * 
 */
package com.rsaame.pas.home.svc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.com.svc.BaseLoadSvc;
import com.rsaame.pas.com.svc.PasReferralSaveCommonSvc;
import com.rsaame.pas.com.svc.PremiumSaveCommonSvc;
import com.rsaame.pas.com.svc.UWQASaveCommonSvc;
import com.rsaame.pas.dao.cmn.PASStoredProcedure;
import com.rsaame.pas.dao.model.TMasPartnerMgmt;
import com.rsaame.pas.dao.model.TTrnPasReferralDetails;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.endorse.svc.GeneralInfoCommonSvc;
import com.rsaame.pas.policyAction.svc.PolicyActionCommonSvc;
import com.rsaame.pas.premiumHelper.PremiumHelper;
import com.rsaame.pas.query.constants.QueryConstants;
import com.rsaame.pas.rating.svc.home.HomeRatingInvoker;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.tasks.svc.TaskSvc;
import com.rsaame.pas.vo.app.PolicyCommentsHolder;
import com.rsaame.pas.vo.bus.AuthenticationInfoVO;
import com.rsaame.pas.vo.bus.BuildingDetailsVO;
import com.rsaame.pas.vo.bus.CoverDetailsVO;
import com.rsaame.pas.vo.bus.HomeInsuranceVO;
import com.rsaame.pas.vo.bus.InsuredVO;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.bus.ReferralListVO;
import com.rsaame.pas.vo.bus.TaskVO;
import com.rsaame.pas.vo.cmn.CommonVO;
import com.rsaame.pas.vo.cmn.LoadDataInputVO;

/**
 * @author Sarath
 * @since Phase 3
 *
 */
public class HomeInsuranceSVCWrapper extends com.mindtree.ruc.cmn.base.BaseService {

	private final static com.mindtree.ruc.cmn.log.Logger LOGGER = com.mindtree.ruc.cmn.log.Logger
			.getLogger(HomeInsuranceSVCWrapper.class);

	private BaseLoadSvc baseLoadSvc;
	private HomeCoverDetailsLoadSvc baseCoverDetailsLoadSvc;
	private HomeBuildingLoadSvc baseHomeBuildingLoadSvc;
	private UWQuestionsLoadSvc uwQuestionsLoadSvc;
	private GeneralInfoCommonSvc commonGenSvcBean;
	private BuildingDetailsSvc buildingDetailsSvc;
	private HomeContentSaveSVC contentSvc;
	private PremiumSaveCommonSvc premiumSvc;
	private UWQASaveCommonSvc uwqaSaveCommonSvc;
	private PasReferralSaveCommonSvc pasReferralSaveCmnSvc;
	private TaskSvc taskSvc;
	private PolicyActionCommonSvc polComnSvc;
	
	private static final String CONVERT_TO_POLICY = "CONVERT_TO_POLICY";
	private static final String ALL = "ALL";

	@Override
	public Object invokeMethod(String methodName, Object... args) {
		BaseVO returnValue = null;

		if (SvcConstants.SAVE_HOME_RISK_COVER_DETAILS.equals(methodName)) {
			returnValue = saveHomeInsuranceRiskCoverDetails((BaseVO) args[0]);
		}
		else if (SvcConstants.SAVE_HOME_INSURED_DETAILS.equals(methodName)) {
			returnValue = saveHomeInsuranceInsuredDetails((BaseVO) args[0]);
		}
		else if (SvcConstants.LOAD_HOME_INSURANCE.equals(methodName)) {
			returnValue = loadHomeInsurance((BaseVO) args[0]);
		}
		else if( SvcConstants.APPROVE_QUO_HOME_INSURANCE.equals( methodName ) ){
			returnValue = approveQuoHomeInsurance( (BaseVO) args[ 0 ] );
		}
		else if ( SvcConstants.GET_PROMO_CODES.equals(methodName)){
			returnValue = loadPromotionalCovers( ( BaseVO) args[0] );
		}		
		else if ( SvcConstants.INVOKE_HOME_RENEWAL_RATING.equals(methodName)){			
			HomeInsuranceVO homeInsuranceVO = (HomeInsuranceVO) ((BaseVO)args[0]);
			PolicyDataVO policyDataVo = homeInsuranceVO;
			homeInsuranceVO = (HomeInsuranceVO)invokeRating((BaseVO) args[0],homeInsuranceVO, policyDataVo);
			/*Set minimum premium for display after rating call.*/
			applyMinPrmForDisplay( homeInsuranceVO, policyDataVo );
			returnValue = homeInsuranceVO;
		}
		else if (SvcConstants.SAVE_RENEWAL_HOME_INSURANCE_DETAILS.equals(methodName)) {
			returnValue = saveRenewalHomeInsuranceDetails((BaseVO) args[0]);
		}
		else if(SvcConstants.LOAD_PARTNER_MGMT.equals(methodName)){
			returnValue =  loadPartnerMgmt((BaseVO) args[ 0 ] );
	
		}
		/*
		 * For VAT 142244 
		 * Getting vat rate and vat code
		 */
		else if( SvcConstants.LOAD_HOME_VAT_RATE_AND_CODE.equals( methodName ) ){
			returnValue =  fetchVatRateAndCode((BaseVO) args[ 0 ] );
	
		}
		return returnValue;
	}

	/**
	 * @param baseVO
	 * Calls the DAOUtils method for executing stored proc for issue quote
	 */
	private void issueQuoteHomeInsurance(BaseVO baseVO) {
		PolicyDataVO policyDataVo = (PolicyDataVO) baseVO;
		if (Utils.isEmpty(policyDataVo.getCommonVO().getLob())) {
			throw new BusinessException("cmn.unknownError", null,
					"LOB cannot be empty in CommonVO");
		}
		LOGGER.info("Home Issue Quote Procedure called");
		DAOUtils.callUpdateStatusProcedureForHomeTravel(policyDataVo);
		LOGGER.info("Home Issue Quote Procedure executed successfully");
	}

	/**
	 * @param homeInsuranceVO
	 * @return
	 */
	private BaseVO saveHomeInsuranceRiskCoverDetails(BaseVO baseVO) {

		LOGGER.info("Entering Home Insurance SVC");

		HomeInsuranceVO homeInsuranceVO = (HomeInsuranceVO) baseVO;
		
//		fetchVatRateAndCode(homeInsuranceVO);
		
		/*Start - Added for B2C referrals - making referral list vo as null since we should not store it during general indo save*/
		ReferralListVO referralListVO = null;
		if( !Utils.isEmpty( homeInsuranceVO.getReferralVOList() ) ){
			referralListVO = CopyUtils.copySerializableObject( homeInsuranceVO.getReferralVOList() );
			homeInsuranceVO.setReferralVOList( null );
		}
		/*End - Added for B2C referrals - making referral list vo as null since we should not store it during general indo save*/
		
		//CommonVO commonVO = homeInsuranceVO.getCommonVO();
		commonGenSvcBean.invokeMethod(SvcConstants.SAVE_GEN_INFO, homeInsuranceVO);
		PolicyDataVO policyDataVo = homeInsuranceVO;
		
		if( !Utils.isEmpty( policyDataVo.getScheme() ) && !Utils.isEmpty( policyDataVo.getScheme().getSchemeCode() ) ){
			policyDataVo.setCommission( SvcUtils.getCommission( policyDataVo, policyDataVo.getScheme().getSchemeCode().toString() ) );
		}
		
		baseVO = invokeRating(baseVO, homeInsuranceVO, policyDataVo);
		
		//Call saveBuildingDetailsSection() to save building details to T_TRN_BUILDING/QUO table
		buildingDetailsSvc.invokeMethod( com.Constant.CONST_BUILDINGSAVESERVICE, baseVO, policyDataVo );
		//Call saveContents() to save cover details to T_TRN_CONTENT/QUO table
		contentSvc.invokeMethod( SvcConstants.SAVE_CONTENTS, baseVO, policyDataVo );
		//Call saveOrUpdateTtrnPrmTable() to save cover details to T_TRN_PREMIUM/QUO table
		premiumSvc.invokeMethod( SvcConstants.SAVE_PREMIUM, baseVO, policyDataVo );

		policyDataVo.getCommonVO().setLob( homeInsuranceVO.getCommonVO().getLob() );
		
		/*Start - Added for B2C referrals - making referral list vo as null since we should not store it during general indo save*/
		if(!Utils.isEmpty( referralListVO )){
			homeInsuranceVO.setReferralVOList( referralListVO );
		}
		/*End - Added for B2C referrals - making referral list vo as null since we should not store it during general indo save*/
		
		applyMinPrm( baseVO, homeInsuranceVO, policyDataVo );
		applyMinPrmForDisplay( homeInsuranceVO, policyDataVo );
		if((!Utils.isEmpty( homeInsuranceVO ) && !Utils.isEmpty( homeInsuranceVO.getReferralVOList()))){
			SvcUtils.updateTaskVO(homeInsuranceVO);
			saveReferralMessage( homeInsuranceVO);
			policyDataVo.getCommonVO().setStatus( SvcConstants.POL_STATUS_REFERRED );
		}
		
		//update policy_quo with updated premium
		java.util.List<Object> valueHolder = DAOUtils.getSqlResultSingleColumn( QueryConstants.FETCH_TOTAL_POLICY_PRM_QUO_TOTAL,(HibernateTemplate) Utils.getBean( com.Constant.CONST_HIBERNATETEMPLATE ), policyDataVo.getCommonVO()
				.getPolicyId(), policyDataVo.getCommonVO().getEndtId() );
		double polPrm = 0.0;
		if( !Utils.isEmpty( valueHolder ) && valueHolder.size() > 0 && !Utils.isEmpty( valueHolder.get( 0 ) ) ){
			polPrm = ( (BigDecimal) valueHolder.get( 0 ) ).doubleValue();
		}
		DAOUtils.updateSql( QueryConstants.UPDATE_PRM_QUO,(HibernateTemplate) Utils.getBean( com.Constant.CONST_HIBERNATETEMPLATE ), BigDecimal.valueOf( polPrm ),  policyDataVo.getCommonVO().getEndtId() , policyDataVo.getCommonVO()
				.getPolicyId() );
		
		//
		//commonVO.setStatus( SvcConstants.POL_STATUS_ACTIVE );
		
/*		if( commonVO.getIsQuote() ){
			PASStoredProcedure sp = null;
			sp = (PASStoredProcedure) Utils.getBean( "commonUpdPrmQuoEndt" );
			if( LOGGER.isInfo() ) LOGGER.info( "Invoking COMMON_UPDATE_PREMIUM_QUOTE_ENDT procedure with inputs {[" );
			sp.call( policyDataVo.getCommonVO().getPolicyId(), policyDataVo.getCommonVO().getEndtId(),policyDataVo.getClassCode() );

			commonVO.setStatus( SvcConstants.POL_STATUS_ACTIVE );
		}
		else{
			PASStoredProcedure sp = null;
			sp = (PASStoredProcedure) Utils.getBean( "commonUpdPrmPolEndt" );
			if( LOGGER.isInfo() ) LOGGER.info( "Invoking COMMON_UPDATE_PREMIUM_POILCY_ENDT procedure with inputs {[" );
			sp.call( policyDataVo.getCommonVO().getPolicyId(), policyDataVo.getCommonVO().getEndtId(),policyDataVo.getClassCode() );
		}*/
		LOGGER.info("Exiting saveHomeInsurance");

		return baseVO;
	}
	
	private BaseVO saveHomeInsuranceInsuredDetails(BaseVO baseVO) {
		LOGGER.info("Entering HomeInsuranceSVCWrapper.saveHomeInsuranceInsuredDetails method");

		HomeInsuranceVO homeInsuranceVO = (HomeInsuranceVO) baseVO;
		CommonVO commonVO = homeInsuranceVO.getCommonVO();
		ReferralListVO referralListVO = null;
		if( !Utils.isEmpty( homeInsuranceVO.getReferralVOList() ) ){
			referralListVO = CopyUtils.copySerializableObject( homeInsuranceVO.getReferralVOList() );
			homeInsuranceVO.setReferralVOList( null );
		}
		LOGGER.info("HomeInsuranceSVCWrapper.loadHomeInsurance method -starts");
		PolicyDataVO savedData = (PolicyDataVO) loadHomeInsurance(commonVO);
		LOGGER.info("HomeInsuranceSVCWrapper.loadHomeInsurance method -completes");
		
		PolicyDataVO policyDataVO = mapValuesToSave(savedData, homeInsuranceVO);
		
		LOGGER.info("HomeInsuranceSVCWrapper.loadHomeInsurance method, calling GeneralInfoCommonSvc.saveGeneralInfo method.");
		commonGenSvcBean.invokeMethod(SvcConstants.SAVE_GEN_INFO, policyDataVO);
		
		//PolicyDataVO policyDataVo = homeInsuranceVO;
		
		LOGGER.info("HomeInsuranceSVCWrapper.loadHomeInsurance method, calling BuildingDetailsSvc.saveBuildingDetailsSection method.");
		//Call saveBuildingDetailsSection() to save building details to T_TRN_BUILDING/QUO table
		buildingDetailsSvc.invokeMethod( com.Constant.CONST_BUILDINGSAVESERVICE, policyDataVO, policyDataVO );
	
		/*saveClauses( (HomeInsuranceVO)policyDataVO );*/
		
		if( !Utils.isEmpty( referralListVO ) ){
			homeInsuranceVO.setReferralVOList( referralListVO );
		}
		if( ( !Utils.isEmpty( homeInsuranceVO ) && !Utils.isEmpty( homeInsuranceVO.getReferralVOList() ) ) ){
			SvcUtils.updateTaskVO( homeInsuranceVO );
			saveReferralMessage( homeInsuranceVO );
			homeInsuranceVO.getCommonVO().setStatus( SvcConstants.POL_STATUS_REFERRED );
			policyDataVO.setReferralVOList(homeInsuranceVO.getReferralVOList());
		}
		if( homeInsuranceVO.getCommonVO().getIsQuote() ){
		issueQuoteHomeInsurance( policyDataVO );
		
		if(!Utils.isEmpty( homeInsuranceVO.getCommonVO().getStatus() ) &&  homeInsuranceVO.getCommonVO().getStatus().intValue() !=  SvcConstants.POL_STATUS_REFERRED  ){
			policyDataVO.getCommonVO().setStatus( SvcConstants.POL_STATUS_ACTIVE );
		}
		if( !Utils.isEmpty(((HomeInsuranceVO)savedData).getBuildingDetails().getMortgageeName() ) 
				&& !(((HomeInsuranceVO)savedData).getBuildingDetails().getMortgageeName().equalsIgnoreCase( "select" ) ) ){
			Integer mortgaeeName = SvcUtils.getLookUpCode( com.Constant.CONST_PAS_MORTGAGEE_NAME, "ALL", "ALL", 
					((HomeInsuranceVO)savedData).getBuildingDetails().getMortgageeName() );
			if(Utils.isEmpty( mortgaeeName ) ){
				Integer others = SvcUtils.getLookUpCode( com.Constant.CONST_PAS_MORTGAGEE_NAME, "ALL", "ALL", 
						"Others" );
				((HomeInsuranceVO)policyDataVO).getBuildingDetails().setMortgageeName(others+"#"+((HomeInsuranceVO)savedData).getBuildingDetails().getMortgageeName());
			}
		}
		}
			
		LOGGER.info("Exiting HomeInsuranceSVCWrapper.saveHomeInsuranceInsuredDetails method");

		return policyDataVO;
	}
	
	/*private void saveClauses( HomeInsuranceVO homeInsuranceVO ){

		List<DataHolderVO<List<String>>> dataHolderVOs = new ArrayList<DataHolderVO<List<String>>>();
		boolean contentSelected = false;
		boolean personalPosSelected = false;

		for( CoverDetailsVO coverDetailsVO : homeInsuranceVO.getCovers() ){

			if( ( coverDetailsVO.getRiskCodes().getRiskType().equals( SvcConstants.HOME_CONTENT_RISK_TYPE ) || coverDetailsVO.getRiskCodes().getRiskType()
					.equals( SvcConstants.HOME_PERSONAL_POS_RISK_TYPE ) )
					&& coverDetailsVO.getRiskCodes().getRiskCat().equals( SvcConstants.DEFAULT_COVER_RSK_CAT )
					&& coverDetailsVO.getCoverCodes().getCovCode() == SvcConstants.DEFAULT_COVER_CODE ){
				if( coverDetailsVO.getRiskCodes().getRiskType().equals( SvcConstants.HOME_CONTENT_RISK_TYPE ) ){
					contentSelected = true;
				}
				else if( coverDetailsVO.getRiskCodes().getRiskType().equals( SvcConstants.HOME_PERSONAL_POS_RISK_TYPE ) ){
					personalPosSelected = true;
				}
			}
			
		}

		if( !contentSelected ){
			DataHolderVO<List<String>> buildDataHolder = new DataHolderVO<List<String>>();
			List<String> strings = new ArrayList<String>();
			strings.add( SvcConstants.HOME_CONTENT_RISK_TYPE.toString() );
			strings.add( "false" );
			strings.add( null );
			buildDataHolder.setData( strings );
			dataHolderVOs.add( buildDataHolder );
		}
		if( !personalPosSelected ){
			DataHolderVO<List<String>> buildDataHolder = new DataHolderVO<List<String>>();
			List<String> strings = new ArrayList<String>();
			strings.add( SvcConstants.HOME_PERSONAL_POS_RISK_TYPE.toString() );
			strings.add( "false" );
			strings.add( null );
			buildDataHolder.setData( strings );
			dataHolderVOs.add( buildDataHolder );
		}

		//premiumSvc.storeExcessInTTrnCondition( dataHolderVOs, (PolicyDataVO) homeInsuranceVO );
	}*/

	private BaseVO approveQuoHomeInsurance(BaseVO holder){
		LOGGER.info( "Entering Home Insurance Approve Quote" );
		saveHomeInsuranceInsuredDetails(holder);
		DataHolderVO<Object[]> dataHolder = (DataHolderVO<Object[]>) holder;
		HomeInsuranceVO homeInsuranceVO = (HomeInsuranceVO) dataHolder.getData()[0];	
		PolicyCommentsHolder polCommHoler= new PolicyCommentsHolder();
		polCommHoler.setCommonDetails(homeInsuranceVO.getCommonVO());
		approveQuote(polCommHoler);
		return holder;
	}
	
	private void approveQuote(BaseVO baseVo){
		polComnSvc.invokeMethod( "approveQuote", baseVo);
	}
	
	/**
	 * @param homeInsuranceVO
	 * @param homeVO
	 * @param policyDataVo
	 * @return
	 * 
	 *         It invokes the rating engine to get the premium value
	 */
	private BaseVO invokeRating(BaseVO homeInsuranceVO, HomeInsuranceVO homeVO,
			PolicyDataVO policyDataVo) {

		homeVO.setPolicyType(policyDataVo.getPolicyType());
		homeVO.setScheme(policyDataVo.getScheme());
		homeVO.setPolicyClassCode(policyDataVo.getPolicyClassCode());
		HomeRatingInvoker ratingService = (HomeRatingInvoker) Utils
				.getBean("homeRatingInvoker");
		homeInsuranceVO = (HomeInsuranceVO) ratingService
				.invokeRating(homeInsuranceVO);
		return homeInsuranceVO;
	}

	private BaseVO loadHomeInsurance(BaseVO args) {
		LOGGER.info("Entered HomeInsuranceSVCWrapper.loadHomeInsurance method.");
		String promoCodeDesc = null;
		CommonVO commonVO = (CommonVO) args;
		HomeInsuranceVO homeInsuranceVO = new HomeInsuranceVO();
		homeInsuranceVO.setCommonVO(commonVO);
		
		LOGGER.info("HomeInsuranceSVCWrapper.loadHomeInsurance method, calling GeneralInfoCommonSvc.loadGeneralInfo method - starts");
		PolicyDataVO policyDataVo = (PolicyDataVO) commonGenSvcBean.invokeMethod( SvcConstants.LOAD_GEN_INFO, homeInsuranceVO);
		LOGGER.info("HomeInsuranceSVCWrapper.loadHomeInsurance method, calling GeneralInfoCommonSvc.loadGeneralInfo method - ends");
		LOGGER.debug("General Info loaded - Policy Id : "+ policyDataVo.getPolicyId());

		LoadDataInputVO loadDataInputVO = new LoadDataInputVO();
		loadDataInputVO.setIsQuote(commonVO.getIsQuote());
		loadDataInputVO.setQuoteNo(commonVO.getQuoteNo());
		loadDataInputVO.setEndtId(commonVO.getEndtId());
		loadDataInputVO.setLocCode(commonVO.getLocCode());
		loadDataInputVO.setPolicyNo(commonVO.getPolicyNo());
		loadDataInputVO.setDocCode(commonVO.getDocCode());
		loadDataInputVO.setPolEffectiveDate(commonVO.getPolEffectiveDate());
		
		LOGGER.info("HomeInsuranceSVCWrapper.loadHomeInsurance method, calling HomeBuildingLoadSvc.loadHomeBuildingDetails method - starts");
		BuildingDetailsVO buildingDetails = (BuildingDetailsVO) baseHomeBuildingLoadSvc.invokeMethod("homeBuildingDetailsLoadService", loadDataInputVO);
		LOGGER.info("HomeInsuranceSVCWrapper.loadHomeInsurance method, calling HomeBuildingLoadSvc.loadHomeBuildingDetails method - completes.");
		
		LOGGER.info("HomeInsuranceSVCWrapper.loadHomeInsurance method, calling HomeCoverDetailsLoadSvc.loadHomeCoverDetails method - starts.");
		/*buildingDetails.setArea(!Utils.isEmpty(buildingDetails.getGeoAreaCode())? buildingDetails.getGeoAreaCode().toString() : null);*/
		homeInsuranceVO = (HomeInsuranceVO) baseCoverDetailsLoadSvc.invokeMethod("homeCoverDetailsLoadService", loadDataInputVO, homeInsuranceVO);
		LOGGER.info("HomeInsuranceSVCWrapper.loadHomeInsurance method, calling HomeCoverDetailsLoadSvc.loadHomeCoverDetails method - completes.");
		
		homeInsuranceVO.setBuildingDetails(buildingDetails);
		commonVO.setPolicyId( policyDataVo.getPolicyId() );
		homeInsuranceVO.setCommonVO(commonVO);
		policyDataVo.getGeneralInfo().getInsured().getAddress().setEmirates(policyDataVo.getGeneralInfo().getInsured().getCity());
		if( !Utils.isEmpty( homeInsuranceVO ) && !Utils.isEmpty( homeInsuranceVO.getGeneralInfo() ) && !Utils.isEmpty( homeInsuranceVO.getGeneralInfo().getSourceOfBus() ) ){
			promoCodeDesc = homeInsuranceVO.getGeneralInfo().getSourceOfBus().getPromoCodeDesc();
		}
		homeInsuranceVO.setGeneralInfo(policyDataVo.getGeneralInfo());
		homeInsuranceVO.getGeneralInfo().getSourceOfBus().setPromoCodeDesc( promoCodeDesc );
		homeInsuranceVO.setAuthenticationInfoVO( policyDataVo.getAuthenticationInfoVO() );
		policyDataVo.getScheme().setPolicyType(policyDataVo.getScheme().getPolicyCode().toString());
		homeInsuranceVO.setScheme(policyDataVo.getScheme());
		homeInsuranceVO.setClassCode(policyDataVo.getPolicyClassCode());
		homeInsuranceVO.setStaffDetails( ( ( HomeInsuranceVO ) policyDataVo ).getStaffDetails() );
		populatePackagePremium(homeInsuranceVO);
		
		/*if(!Utils.isEmpty(homeInsuranceVO.getAuthenticationInfoVO().getRefPolicyNo()) && homeInsuranceVO.getAuthenticationInfoVO().getRefPolicyNo().longValue()!=0)
		{
			homeInsuranceVO.getCommonVO().setOldContentPPCode(DAOUtils.checkOldContentPPPED(homeInsuranceVO.getAuthenticationInfoVO().getRefPolicyNo()));
		}
		else*/ if(!homeInsuranceVO.getCommonVO().getIsQuote())
		{
			homeInsuranceVO.getCommonVO().setOldContentPPCode(DAOUtils.checkOldContentPPPED(homeInsuranceVO.getCommonVO().getPolicyNo()));
		}
		else if(homeInsuranceVO.getCommonVO().getIsQuote() && !Utils.getSingleValueAppConfig("EMIRATES_SCH_CODE").equals(homeInsuranceVO.getScheme().getSchemeCode().toString()))
		{
			DAOUtils.checkOldContentPPCode(homeInsuranceVO.getCommonVO().getPolicyId(),homeInsuranceVO.getCommonVO().getEndtId(),homeInsuranceVO.getCommonVO().getIsQuote());
		}
		
		//For Vat 142244
		if(!Utils.isEmpty(policyDataVo.getPremiumVO().getVatCode())){
			homeInsuranceVO.getCommonVO().getPremiumVO().setVatCode(policyDataVo.getPremiumVO().getVatCode());
		}
		if(!Utils.isEmpty(policyDataVo.getPremiumVO().getVatTaxPerc()) && !Utils.isEmpty(policyDataVo.getPremiumVO().getVatCode())){
			homeInsuranceVO.getCommonVO().getPremiumVO().setVatTaxPerc(policyDataVo.getPremiumVO().getVatTaxPerc());
		}
		
		LOGGER.info("Exiting HomeInsuranceSVCWrapper.loadHomeInsurance method.");
		return homeInsuranceVO;
	}
		
	/**
	 * @param homeInsuranceVO
	 * calculates the package premium that need to be displayed in home risk page
	 */
	private void populatePackagePremium(HomeInsuranceVO homeInsuranceVO) {

		double packagePremium = 0;
		double packageActualPremium = 0;

		if (!Utils.isEmpty(homeInsuranceVO)) {
			if (!Utils.isEmpty(homeInsuranceVO.getBuildingDetails())) {
				packagePremium += homeInsuranceVO.getBuildingDetails()
						.getPremiumAmt();
				packageActualPremium += homeInsuranceVO.getBuildingDetails()
						.getPremiumAmtActual();
			}

			for (CoverDetailsVO coverDetailsVO : homeInsuranceVO.getCovers()) {
				packagePremium += coverDetailsVO.getPremiumAmt();
				packageActualPremium += coverDetailsVO.getPremiumAmtActual();
			}

			if (Utils.isEmpty(homeInsuranceVO.getPremiumVO())) {
				homeInsuranceVO.setPremiumVO(new PremiumVO());
			}
			Double minPrm = PremiumHelper.getSplPrm( homeInsuranceVO.getCommonVO(), homeInsuranceVO.getCommonVO().getEndtId(), new Short[]{  Short.valueOf( SvcConstants.SPECIAL_COVER_MIN_PRM ) }, (HibernateTemplate) Utils.getBean( com.Constant.CONST_HIBERNATETEMPLATE ) );
			homeInsuranceVO.getPremiumVO().setMinPremiumApplied( BigDecimal.valueOf( minPrm ) );
			homeInsuranceVO.getPremiumVO().setPremiumAmt(packagePremium);
			
			homeInsuranceVO.getPremiumVO().setPremiumAmtActual(packageActualPremium);
		}

	}

	/**
	 * To load the promotional covers for the selected scheme
	 * @param homeInsuranceVO
	 */
	private BaseVO loadPromotionalCovers(BaseVO baseVO){
		
		Map <String, Object> result = new HashMap<String, Object>();
		HomeInsuranceVO homeInsuranceVO = (HomeInsuranceVO) baseVO;
		DataHolderVO<Object[]> dataHolder = new DataHolderVO<Object[]>();
		
		List<Object> promoCoverCodesWithDesc = DAOUtils.getPromotionalCodeCover( homeInsuranceVO.getScheme().getSchemeCode(), homeInsuranceVO.getGeneralInfo().getSourceOfBus()
				.getPromoCode(), homeInsuranceVO.getClassCode(), homeInsuranceVO.getScheme().getEffDate(),homeInsuranceVO.getCommonVO().getIsQuote()  );
		List<Object> promoDiscountWithDesc = DAOUtils.getPromotionalCodeDiscount( homeInsuranceVO.getScheme().getSchemeCode(), homeInsuranceVO.getGeneralInfo().getSourceOfBus().getPromoCode(),
				homeInsuranceVO.getClassCode(), homeInsuranceVO.getScheme().getEffDate(),homeInsuranceVO.getCommonVO().getIsQuote()  );
		
		if( !Utils.isEmpty( promoCoverCodesWithDesc ) ){
			if( !Utils.isEmpty( promoCoverCodesWithDesc.get( 0 ) ) ){
				result.put( "promotionalCodes", promoCoverCodesWithDesc.get( 0 ) );
			}
			if( !Utils.isEmpty( promoCoverCodesWithDesc.get( 1 ) ) ){
				result.put( "promotionalCodeDesc", promoCoverCodesWithDesc.get( 1 ) );
			}
		}else{
			result.put( "promotionalCodes", new ArrayList<Short>() );
		}
		
		if( !Utils.isEmpty( promoDiscountWithDesc ) ){
			if( !Utils.isEmpty( promoDiscountWithDesc.get( 0 ) ) ){
				result.put( "promoDiscount", ( !Utils.isEmpty( promoDiscountWithDesc.get( 0 ) ) ? promoDiscountWithDesc.get( 0 ) : "0.0" ) );
			}
			if( !Utils.isEmpty( promoDiscountWithDesc.get( 1 ) ) ){
				result.put( "promotionalCodeDesc", promoDiscountWithDesc.get( 1 ) );
			}
		}else{
			result.put( "promoDiscount", "0.0" );
		}
		
		Object[] inpObjects = { result };
		dataHolder.setData( inpObjects );
		return dataHolder;
	}

	
	/**
	 * Method to store the referral Message
	 * 
	 * @param homeInsuranceVO
	 * @param referralMessage 
	 * @param request 
	 */
	private void saveReferralMessage( HomeInsuranceVO homeInsuranceVO){
	
		homeInsuranceVO.getReferralVOList().getReferrals().get( 0 ).setConsolidated( true );

		TaskVO taskVO = homeInsuranceVO.getReferralVOList().getTaskVO();

		//TTrnPasReferralDetails pasReferralDetails = (TTrnPasReferralDetails) pasReferralSaveCmnSvc.invokeMethod("saveReferralData", homeInsuranceVO);
		 pasReferralSaveCmnSvc.invokeMethod("saveReferralData", homeInsuranceVO);
		
		taskVO.setDesc( DAOUtils.getTaskDescription(homeInsuranceVO.getCommonVO().getPolicyId(),homeInsuranceVO.getCommonVO().getEndtId()));
		// Added by Anveshan. taskVo is not having latest endt id
		if( !Utils.isEmpty(homeInsuranceVO.getCommonVO().getEndtId()))
		{
			taskVO.setPolEndId(homeInsuranceVO.getCommonVO().getEndtId());
		}
		taskSvc.invokeMethod("saveRefferalTask", taskVO);
	}
	
	/**
	 * Method to map screen values to VO for saving
	 * @author Sarath
	 * @param homeInsuranceVO
	 * @return
	 */
	private PolicyDataVO mapValuesToSave( PolicyDataVO savedData, HomeInsuranceVO homeInsuranceVO){
		
		//PolicyDataVO policyDataVO = null;
		
		try{
			/*policyDataVO = (PolicyDataVO) commonGenSvcBean.invokeMethod( SvcConstants.LOAD_GEN_INFO, homeInsuranceVO);
			LoadDataInputVO loadDataInputVO = new LoadDataInputVO();
			loadDataInputVO.setIsQuote(homeInsuranceVO.getCommonVO().getIsQuote());
			loadDataInputVO.setQuoteNo(homeInsuranceVO.getCommonVO().getQuoteNo());
			loadDataInputVO.setEndtId(homeInsuranceVO.getCommonVO().getEndtId());
			loadDataInputVO.setLocCode(homeInsuranceVO.getCommonVO().getLocCode());
			loadDataInputVO.setPolicyNo(homeInsuranceVO.getCommonVO().getPolicyNo());
			loadDataInputVO.setDocCode(homeInsuranceVO.getCommonVO().getDocCode());
			loadDataInputVO.setPolEffectiveDate(homeInsuranceVO.getCommonVO().getPolEffectiveDate());
			BuildingDetailsVO buildingDetails = (BuildingDetailsVO) baseHomeBuildingLoadSvc.invokeMethod( "homeBuildingDetailsLoadService", loadDataInputVO );*/

			AuthenticationInfoVO authenticationInfoVO = savedData.getAuthenticationInfoVO();//new AuthenticationInfoVO();
			authenticationInfoVO.setAccountingDate( new Date() );
			savedData.setAuthenticationInfoVO(authenticationInfoVO);
			savedData.setStatus( 6 );
			savedData.setPolicyClassCode(Integer.valueOf( Utils.getSingleValueAppConfig( "HOME_CLASS_CODE" ) ) );
			savedData.setPolicyType(Integer.valueOf( SvcConstants.HOME_POL_TYPE ) );
			savedData.setEndtId( homeInsuranceVO.getCommonVO().getEndtId() );
			savedData.getScheme().setPolicyType( "7" );
			
			savedData.setCommonVO( homeInsuranceVO.getCommonVO() );
			savedData.getScheme().setEffDate( homeInsuranceVO.getScheme().getEffDate() );
			savedData.getScheme().setExpiryDate( homeInsuranceVO.getScheme().getExpiryDate() );
			InsuredVO insured = savedData.getGeneralInfo().getInsured(); 
			insured.setFirstName( homeInsuranceVO.getGeneralInfo().getInsured().getFirstName() );
			insured.setLastName( homeInsuranceVO.getGeneralInfo().getInsured().getLastName() );
			insured.getAddress().setPoBox( homeInsuranceVO.getGeneralInfo().getInsured().getAddress().getPoBox() );
			insured.getAddress().setEmirates( homeInsuranceVO.getGeneralInfo().getInsured().getAddress().getEmirates() );
			insured.getAddress().setCountry( homeInsuranceVO.getGeneralInfo().getInsured().getAddress().getCountry() );
			insured.setRoyaltyType( homeInsuranceVO.getGeneralInfo().getInsured().getRoyaltyType() );
			insured.setGuestCardNo( homeInsuranceVO.getGeneralInfo().getInsured().getGuestCardNo() );
			/*
			 * For VAT 142244
			 * VAT Reg No
			 */
			if(!Utils.isEmpty( homeInsuranceVO.getGeneralInfo().getInsured().getVatRegNo() )){
				insured.setVatRegNo( homeInsuranceVO.getGeneralInfo().getInsured().getVatRegNo() );	
				}
			if( !Utils.isEmpty( homeInsuranceVO.getGeneralInfo().getInsured().getAddress().getEmirates() )
					&& !Utils.isEmpty( homeInsuranceVO.getGeneralInfo().getInsured().getAddress().getEmirates() ) ){
				insured.setCity( homeInsuranceVO.getGeneralInfo().getInsured().getAddress().getEmirates() );
			}
			insured.setUpdateMaster( true );
			
			BuildingDetailsVO buildingDetails = ((HomeInsuranceVO)savedData).getBuildingDetails();
			if(!Utils.isEmpty(homeInsuranceVO.getBuildingDetails().getEmirates())){
			String emirates = SvcUtils.getLookUpDescription( "CITY", ALL, ALL, Integer.valueOf( homeInsuranceVO.getBuildingDetails().getEmirates() ) );
			buildingDetails.setEmirates( emirates );
			}else{
				String emirates = SvcUtils.getLookUpDescription( "CITY", ALL, ALL, Integer.valueOf("1" ) );
				buildingDetails.setEmirates( emirates );
			}
			if(!Utils.isEmpty( homeInsuranceVO.getBuildingDetails().getArea() )){
				String area = SvcUtils.getLookUpDescription( "PAS_AREA", ALL, ALL, Integer.valueOf( homeInsuranceVO.getBuildingDetails().getArea() ) );
				buildingDetails.setArea( area.trim() );
			} else {
				buildingDetails.setArea( null);
			}
			
			buildingDetails.setTypeOfProperty( homeInsuranceVO.getBuildingDetails().getTypeOfProperty() );
			buildingDetails.setBuildingname( homeInsuranceVO.getBuildingDetails().getBuildingname() );
			// 11645 - Home Digital API
			if(!Utils.isEmpty(homeInsuranceVO.getBuildingDetails().getTotalNoRooms()) ) {
				buildingDetails.setTotalNoRooms( homeInsuranceVO.getBuildingDetails().getTotalNoRooms() );
			}else {
				buildingDetails.setTotalNoRooms(null);
			}
			if(!Utils.isEmpty(homeInsuranceVO.getBuildingDetails().getTotalNoFloors()) ) {
				buildingDetails.setTotalNoFloors( homeInsuranceVO.getBuildingDetails().getTotalNoFloors() );
			}else {
				buildingDetails.setTotalNoFloors(null);
			}
			
			buildingDetails.setFlatVillaNo( homeInsuranceVO.getBuildingDetails().getFlatVillaNo() );
			//Added two fields Home Revamp 4.1 
			buildingDetails.setBldTotalNoFloors(homeInsuranceVO.getBuildingDetails().getBldTotalNoFloors());
			buildingDetails.setBldTotalNoRooms(homeInsuranceVO.getBuildingDetails().getBldTotalNoRooms());
			if(!Utils.isEmpty( homeInsuranceVO.getBuildingDetails().getMortgageeName() )){
				String mortgage[] = homeInsuranceVO.getBuildingDetails().getMortgageeName().split("#");
				if(mortgage.length == 1){
					String mortgagee = SvcUtils.getLookUpDescription( com.Constant.CONST_PAS_MORTGAGEE_NAME, ALL, ALL, Integer.valueOf( mortgage[0] ) );
					buildingDetails.setMortgageeName(mortgagee.trim());
				}
				else{
					buildingDetails.setMortgageeName(mortgage[1].trim());
				}
			}else{
				buildingDetails.setMortgageeName( null );
			}
			buildingDetails.setGeoAreaCode( Short.valueOf( homeInsuranceVO.getBuildingDetails().getArea() ) );
			buildingDetails.setOtherDetails(homeInsuranceVO.getBuildingDetails().getOtherDetails());
			//((HomeInsuranceVO)savedData).setBuildingDetails( buildingDetails );
			if(!Utils.isEmpty( homeInsuranceVO.getGeneralInfo()) && !Utils.isEmpty( homeInsuranceVO.getGeneralInfo().getSourceOfBus()))
			{
				savedData.getGeneralInfo().getSourceOfBus().setPartnerId(homeInsuranceVO.getGeneralInfo().getSourceOfBus().getPartnerId());
				savedData.getGeneralInfo().getSourceOfBus().setPartnerName(homeInsuranceVO.getGeneralInfo().getSourceOfBus().getPartnerName());
				savedData.getGeneralInfo().getSourceOfBus().setCallCentreNo( homeInsuranceVO.getGeneralInfo().getSourceOfBus().getCallCentreNo());
				savedData.getGeneralInfo().getSourceOfBus().setReplyToEmailId(homeInsuranceVO.getGeneralInfo().getSourceOfBus().getReplyToEmailId());
				savedData.getGeneralInfo().getSourceOfBus().setCcEmailId(homeInsuranceVO.getGeneralInfo().getSourceOfBus().getCcEmailId());
				savedData.getGeneralInfo().getSourceOfBus().setSourceOfBusiness(homeInsuranceVO.getGeneralInfo().getSourceOfBus().getSourceOfBusiness());
				savedData.getGeneralInfo().getSourceOfBus().setFromEmailID(homeInsuranceVO.getGeneralInfo().getSourceOfBus().getFromEmailID());
				savedData.getGeneralInfo().getSourceOfBus().setDefaultOnlineDiscount(homeInsuranceVO.getGeneralInfo().getSourceOfBus().getDefaultOnlineDiscount());
				savedData.getGeneralInfo().getSourceOfBus().setDefaultAssignToUser(homeInsuranceVO.getGeneralInfo().getSourceOfBus().getDefaultAssignToUser());
				savedData.getGeneralInfo().getSourceOfBus().setFaqUrl(homeInsuranceVO.getGeneralInfo().getSourceOfBus().getFaqUrl());
				savedData.getGeneralInfo().getSourceOfBus().setPolicyTermUrl(homeInsuranceVO.getGeneralInfo().getSourceOfBus().getPolicyTermUrl());
							
			}
		}
		catch(Exception exp){
			LOGGER.debug("Exception occured while mapping values to save home insured details - "+exp.getMessage());
		}
		
		return savedData;
	}
	
	
	private BaseVO saveRenewalHomeInsuranceDetails(BaseVO baseVO) {

		LOGGER.info("Entering Home Insurance SVC");
		
		HomeInsuranceVO homeVO = (HomeInsuranceVO) baseVO;
		ReferralListVO referralListVO = null;
		if( !Utils.isEmpty( homeVO.getReferralVOList() ) ){
			referralListVO = CopyUtils.copySerializableObject( homeVO.getReferralVOList() );
			homeVO.setReferralVOList( null );
		}
		PolicyDataVO policyDataVO = (PolicyDataVO) commonGenSvcBean.invokeMethod( SvcConstants.SAVE_GEN_INFO, baseVO );
		policyDataVO.setCommonVO( homeVO.getCommonVO() );	
		CommonVO commonVO = policyDataVO.getCommonVO();
		baseVO = invokeRating( baseVO, homeVO, policyDataVO );
		
		
		
		/*String emirates = SvcUtils.getLookUpDescription( "CITY", ALL, ALL, Integer.valueOf( homeVO.getBuildingDetails().getEmirates() ) );
		homeVO.getBuildingDetails().setEmirates( emirates );
		String area = SvcUtils.getLookUpDescription( "PAS_AREA", ALL, ALL, Integer.valueOf( homeVO.getBuildingDetails().getArea() ) );
		homeVO.getBuildingDetails().setArea( area.trim() );*/
		
		
		//Call saveBuildingDetailsSection() to save building details to T_TRN_BUILDING/QUO table
		buildingDetailsSvc.invokeMethod( com.Constant.CONST_BUILDINGSAVESERVICE, baseVO, policyDataVO );
		//If Contents are not empty, only then invoking content save service -- Null check has been removed to accommodate cover removal scenario
		//Call saveContents() to save cover details to T_TRN_CONTENT/QUO table
		contentSvc.invokeMethod( SvcConstants.SAVE_CONTENTS, baseVO, policyDataVO );
		//Call saveOrUpdateTtrnPrmTable() to save cover details to T_TRN_PREMIUM/QUO table
		premiumSvc.invokeMethod( SvcConstants.SAVE_PREMIUM, baseVO, policyDataVO );		
		policyDataVO.getCommonVO().setLob( homeVO.getCommonVO().getLob() );	
		
		/*Start - Added for B2C referrals - making referral list vo as null since we should not store it during general indo save*/
		if(!Utils.isEmpty( referralListVO )){
			homeVO.setReferralVOList( referralListVO );
		}
		/*End - Added for B2C referrals - making referral list vo as null since we should not store it during general indo save*/
		
		
		if((!Utils.isEmpty( homeVO ) && !Utils.isEmpty( homeVO.getReferralVOList()))){
			SvcUtils.updateTaskVO(homeVO);
			saveReferralMessage( homeVO);
			policyDataVO.getCommonVO().setStatus( SvcConstants.POL_STATUS_REFERRED );
			policyDataVO.setReferralVOList(homeVO.getReferralVOList());
		}
		if( homeVO.getCommonVO().getIsQuote() ){
			PASStoredProcedure sp = null;
			sp = (PASStoredProcedure) Utils.getBean( "commonUpdPrmQuoEndt" );
			if( LOGGER.isInfo() ) LOGGER.info( "Invoking COMMON_UPDATE_PREMIUM_QUOTE_ENDT procedure with inputs {[" );
			sp.call( policyDataVO.getCommonVO().getPolicyId(), policyDataVO.getCommonVO().getEndtId(),Integer.valueOf(Utils.getSingleValueAppConfig( commonVO.getLob()+"_CLASS_CODE" ) ));
			applyMinPrm( policyDataVO );
		issueQuoteHomeInsurance( policyDataVO );
		
		applyMinPrmForDisplay( (HomeInsuranceVO)policyDataVO , policyDataVO );
		
		//For calculating vat amount for quote versioning in renewal quote
		double vatTax=0.0;
		if(!Utils.isEmpty( (policyDataVO.getCommonVO().getPremiumVO()))){
				vatTax = SvcUtils.getVatTax(policyDataVO);
				DAOUtils.updatePolVATPremium(vatTax, policyDataVO.getCommonVO().getPolicyId(), policyDataVO.getCommonVO().getEndtId());
		}
		
		if(!Utils.isEmpty( homeVO.getCommonVO().getStatus() ) &&  homeVO.getCommonVO().getStatus().intValue() !=  SvcConstants.POL_STATUS_REFERRED  ){
			policyDataVO.getCommonVO().setStatus( SvcConstants.POL_STATUS_ACTIVE );
		}
		if( !Utils.isEmpty(((HomeInsuranceVO)homeVO).getBuildingDetails().getMortgageeName() ) 
				&& !(((HomeInsuranceVO)homeVO).getBuildingDetails().getMortgageeName().equalsIgnoreCase( "select" ) ) ){
			Integer mortgaeeName = SvcUtils.getLookUpCode( com.Constant.CONST_PAS_MORTGAGEE_NAME, "ALL", "ALL", 
					((HomeInsuranceVO)homeVO).getBuildingDetails().getMortgageeName() );
			if(Utils.isEmpty( mortgaeeName ) ){
				Integer others = SvcUtils.getLookUpCode( com.Constant.CONST_PAS_MORTGAGEE_NAME, "ALL", "ALL", 
						"Others" );
				((HomeInsuranceVO)policyDataVO).getBuildingDetails().setMortgageeName(others+"#"+((HomeInsuranceVO)homeVO).getBuildingDetails().getMortgageeName());
			}
		}
		}
		LOGGER.info("Exiting saveHomeInsurance");

		return policyDataVO;
		//return baseVO;
	}

	/**
	 * @param homeInsuranceVO
	 * @param homeVO
	 * @param policyDataVo
	 */
	private void applyMinPrm( BaseVO homeInsuranceVO ){
		DAOUtils.flushTransaction();
		premiumSvc.invokeMethod( SvcConstants.APPLY_MIN_PRM_HOME, homeInsuranceVO );
	}
	
	/**
	 * @param homeInsuranceVO
	 * @param homeVO
	 * @param policyDataVo
	 */
	private void applyMinPrm( BaseVO homeInsuranceVO, HomeInsuranceVO homeVO, PolicyDataVO policyDataVo ){
		DAOUtils.flushTransaction();
		homeVO.getScheme().setPolicyType( policyDataVo.getScheme().getPolicyCode().toString() );
		premiumSvc.invokeMethod( SvcConstants.APPLY_MIN_PRM_HOME, homeInsuranceVO );
	}
	
	private void applyMinPrmForDisplay( HomeInsuranceVO homeInsuranceVO, PolicyDataVO policyDataVo ){

		homeInsuranceVO.getScheme().setPolicyType( policyDataVo.getScheme().getPolicyCode().toString() );
		double minPrmToApply = ( (BigDecimal) premiumSvc.invokeMethod( SvcConstants.GET_MIN_PRM_TO_APPLY_HOME, homeInsuranceVO ) ).doubleValue();
		if( minPrmToApply > 0 ){
			/*double discLoadAmt = ( homeInsuranceVO.getPremiumVO().getDiscOrLoadPerc() * homeInsuranceVO.getPremiumVO().getPremiumAmt() ) / 100;
			minPrmToApply = minPrmToApply - discLoadAmt;
			if( minPrmToApply > 0 ){*/
				homeInsuranceVO.getPremiumVO().setMinPremiumApplied( BigDecimal.valueOf( minPrmToApply ) );
				homeInsuranceVO.getPremiumVO().setPremiumAmt( homeInsuranceVO.getPremiumVO().getPremiumAmt() );
			//}
		}

	}
	
	public BaseLoadSvc getBaseLoadSvc() {
		return baseLoadSvc;
	}

	public void setBaseLoadSvc(BaseLoadSvc baseLoadSvc) {
		this.baseLoadSvc = baseLoadSvc;
	}

	public void setBaseCoverDetailsLoadSvc( HomeCoverDetailsLoadSvc baseCoverDetailsLoadSvc ) {
		this.baseCoverDetailsLoadSvc = baseCoverDetailsLoadSvc;
	}

	public void setBaseHomeBuildingLoadSvc(
			HomeBuildingLoadSvc baseHomeBuildingLoad) {
		this.baseHomeBuildingLoadSvc = baseHomeBuildingLoad;
	}

	public void setUwQuestionsLoadSvc(UWQuestionsLoadSvc uwQuestionsLoadSvc) {
		this.uwQuestionsLoadSvc = uwQuestionsLoadSvc;
	}
	
	public GeneralInfoCommonSvc getCommonGenSvcBean() {
		return commonGenSvcBean;
	}

	public void setCommonGenSvcBean(GeneralInfoCommonSvc generalInfoLoadSvc) {
		this.commonGenSvcBean = generalInfoLoadSvc;
	}

	public BuildingDetailsSvc getBuildingDetailsSvc() {
		return buildingDetailsSvc;
	}

	public void setBuildingDetailsSvc(BuildingDetailsSvc buildingDetailsSvc) {
		this.buildingDetailsSvc = buildingDetailsSvc;
	}

	public HomeContentSaveSVC getContentSvc() {
		return contentSvc;
	}

	public void setContentSvc(HomeContentSaveSVC contentSvc) {
		this.contentSvc = contentSvc;
	}

	public PremiumSaveCommonSvc getPremiumSvc() {
		return premiumSvc;
	}

	public void setPremiumSvc(PremiumSaveCommonSvc premiumSvc) {
		this.premiumSvc = premiumSvc;
	}

	public UWQASaveCommonSvc getUwqaSaveCommonSvc() {
		return uwqaSaveCommonSvc;
	}

	public void setUwqaSaveCommonSvc(UWQASaveCommonSvc uwqaSaveCommonSvc) {
		this.uwqaSaveCommonSvc = uwqaSaveCommonSvc;
	}

	/**
	 * @return the pasReferralSaveCmnSvc
	 */
	public PasReferralSaveCommonSvc getPasReferralSaveCmnSvc(){
		return pasReferralSaveCmnSvc;
	}

	/**
	 * @param pasReferralSaveCmnSvc the pasReferralSaveCmnSvc to set
	 */
	public void setPasReferralSaveCmnSvc( PasReferralSaveCommonSvc pasReferralSaveCmnSvc ){
		this.pasReferralSaveCmnSvc = pasReferralSaveCmnSvc;
	}

	/**
	 * @return the taskSvc
	 */
	public TaskSvc getTaskSvc(){
		return taskSvc;
	}

	/**
	 * @param taskSvc the taskSvc to set
	 */
	public void setTaskSvc( TaskSvc taskSvc ){
		this.taskSvc = taskSvc;
	}

	public PolicyActionCommonSvc getPolComnSvc(){
		return polComnSvc;
	}

	public void setPolComnSvc( PolicyActionCommonSvc polComnSvc ){
		this.polComnSvc = polComnSvc;
	}
	

	private BaseVO loadPartnerMgmt(BaseVO holder) {
		LOGGER.info("Entered HomeInsuranceSVCWrapper.loadPartnerMgmt method.");
		HomeInsuranceVO homeVO = (HomeInsuranceVO) holder;
		DAOUtils.getPartnerMgmtDetail(homeVO.getCommonVO(), homeVO.getGeneralInfo(), homeVO.getScheme());
		LOGGER.info("Exiting HomeInsuranceSVCWrapper.loadPartnerMgmt method.");
		return homeVO;
	}
	/*
	 * For VAT 142244
	 * For fetching vat rate and vat code
	 */
	private BaseVO fetchVatRateAndCode( BaseVO baseVO ){ 
		
		Map<String, Object> result = new HashMap<String, Object>();
		HomeInsuranceVO homeInsuranceVO = (HomeInsuranceVO) baseVO;
		DataHolderVO<Object[]> dataHolder = new DataHolderVO<Object[]>();

		/*Date preparedDate=null;
		if(homeInsuranceVO.getCommonVO().getIsQuote()){
			preparedDate = DAOUtils.getPreparedDateofQuo(homeInsuranceVO.getCommonVO().getQuoteNo(),homeInsuranceVO.getCommonVO().getIsQuote());
		}
		else{
			preparedDate = DAOUtils.getPreparedDateofQuo(homeInsuranceVO.getCommonVO().getPolicyNo(),homeInsuranceVO.getCommonVO().getIsQuote());
		}*/

		List<Object> vatData=  DAOUtils.VatCodeAndVatRate(homeInsuranceVO.getClassCode(), homeInsuranceVO.getScheme().getPolicyCode(),SvcConstants.SC_PRM_COVER_VAT_TAX);
		
		if( !Utils.isEmpty( vatData ) ){
		
		if( !Utils.isEmpty( vatData.get( 0 ) ) ){
			result.put( "vatRate", vatData.get( 0 ) );
			
			LOGGER.debug( "**In fetchVatRateAndCode()**  vat Rate" +vatData.get( 0 ));
		}
		if( !Utils.isEmpty( vatData.get( 1 ) ) ){
			result.put( "vatCode", vatData.get( 1 ) );
			
			
			LOGGER.debug( "**In fetchVatRateAndCode()**  vat Rate" +vatData.get( 1 ));
		}
		
		}
		
		else{
			result.put( "vatRate", "0.0" );
			result.put( "vatCode", "0");
			
			
		}
		Object[] inpObjects = { result };
		dataHolder.setData( inpObjects );
		return dataHolder;
		
	}
}
