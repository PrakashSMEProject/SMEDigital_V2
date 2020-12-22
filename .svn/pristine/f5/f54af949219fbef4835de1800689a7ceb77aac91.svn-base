package com.rsaame.pas.home.svc;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.cmn.currency.Currency;
import com.rsaame.pas.com.svc.BaseLoadSvc;
import com.rsaame.pas.com.svc.CommonOpSvc;
import com.rsaame.pas.com.svc.PasReferralSaveCommonSvc;
import com.rsaame.pas.com.svc.PremiumSaveCommonSvc;
import com.rsaame.pas.com.svc.UWQASaveCommonSvc;
import com.rsaame.pas.dao.cmn.PASStoredProcedure;
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
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.app.PolicyCommentsHolder;
import com.rsaame.pas.vo.bus.AdditionalInsuredInfoVO;
import com.rsaame.pas.vo.bus.BuildingDetailsVO;
import com.rsaame.pas.vo.bus.CoverDetailsVO;
import com.rsaame.pas.vo.bus.EndorsmentVO;
import com.rsaame.pas.vo.bus.GeneralInfoVO;
import com.rsaame.pas.vo.bus.HomeInsuranceVO;
import com.rsaame.pas.vo.bus.LOB;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.bus.StaffDetailsVO;
import com.rsaame.pas.vo.bus.TaskVO;
import com.rsaame.pas.vo.bus.UWQuestionsVO;
import com.rsaame.pas.vo.cmn.CommonVO;
import com.rsaame.pas.vo.cmn.LoadDataInputVO;
import com.rsaame.pas.vo.cmn.TableData;
import com.rsaame.pas.vo.svc.TTrnGaccPersonVOHolder;
import com.rsaame.pas.vo.svc.TTrnPremiumVOHolder;

public class HomeInsuranceSVC extends BaseService{

	private final static com.mindtree.ruc.cmn.log.Logger LOGGER = com.mindtree.ruc.cmn.log.Logger.getLogger( HomeInsuranceSVC.class );
	private static Long zeroVal = 0L;

	private BaseLoadSvc baseLoadSvc;
	private HomeCoverDetailsLoadSvc baseCoverDetailsLoadSvc;
	private HomeBuildingLoadSvc baseHomeBuildingLoadSvc;
	private UWQuestionsLoadSvc uwQuestionsLoadSvc;
	private GeneralInfoCommonSvc commonGenSvcBean;
	private BuildingDetailsSvc buildingDetailsSvc;
	private HomeContentSaveSVC contentSvc;
	private PremiumSaveCommonSvc premiumSvc;
	private UWQASaveCommonSvc uwqaSaveCommonSvc;
	private PasReferralSaveCommonSvc pasReferralSaveCmnSvc; /* Referral Service */
	private TaskSvc taskSvc; /* Task service */
	private PolicyActionCommonSvc polComnSvc; /* approve quote or policy */
	private CommonOpSvc commonOpSvc;

	@Override
	public Object invokeMethod( String methodName, Object... args ){
		BaseVO returnValue = null;

		if( SvcConstants.SAVE_HOME_INSURANCE.equals( methodName ) ){
			returnValue = saveHomeInsurance( (BaseVO) args[ 0 ] );
		}
		else if( SvcConstants.LOAD_HOME_INSURANCE.equals( methodName ) ){
			returnValue = loadHomeInsurance( (BaseVO) args[ 0 ] );
		}
		else if( SvcConstants.APPROVE_QUO_HOME_INSURANCE.equals( methodName ) ){
			returnValue = approveQuoHomeInsurance( (BaseVO) args[ 0 ] );
		}
		else if( SvcConstants.GET_PROMO_CODES.equals( methodName ) ){
			returnValue = loadPromotionalCovers( (BaseVO) args[ 0 ] );
		}else if( SvcConstants.SAVE_RENEWAL_REFERRAL.equals( methodName ) ){
			returnValue = saveRenewalReferal( (BaseVO) args[ 0 ] );
		}
		else if( SvcConstants.LOAD_HOME_VAT_RATE_AND_CODE.equals( methodName ) ){
			returnValue = fetchVatRateAndCode( (BaseVO) args[ 0 ] );
		}
		
		return returnValue;

	}

	/**
	 * @param baseVO
	 *            Calls the DAOUtils method for executing stored proc for issue
	 *            quote
	 */
	private void issueQuoteHomeInsurance( BaseVO baseVO ){
		PolicyDataVO policyDataVo = (PolicyDataVO) baseVO;
		if( Utils.isEmpty( policyDataVo.getCommonVO().getLob() ) ){
			throw new BusinessException( "cmn.unknownError", null, "LOB cannot be empty in CommonVO" );
		}
		LOGGER.info( "Home Issue Quote Procedure called" );
		DAOUtils.callUpdateStatusProcedureForHomeTravel( policyDataVo );
		LOGGER.info( "Home Issue Quote Procedure executed successfully" );
	}

	/**
	 * @param homeInsuranceVO
	 * @return
	 */
	private BaseVO saveHomeInsurance( BaseVO holder ){
		LOGGER.info( "Entering Home Insurance SVC" );

		DataHolderVO<Object[]> dataHolder = (DataHolderVO<Object[]>) holder;
		BaseVO homeInsuranceVO = (BaseVO) dataHolder.getData()[ 0 ];

		// if True, only call rating service to populate premium on tab out,
		// else do save also
		Boolean isPopulateOperation = Boolean.valueOf( dataHolder.getData()[ 1 ].toString() );

		HomeInsuranceVO homeVO = (HomeInsuranceVO) homeInsuranceVO;

		PolicyDataVO policyDataVo = (PolicyDataVO) commonGenSvcBean.invokeMethod( SvcConstants.LOAD_GEN_INFO, homeInsuranceVO );
		LOGGER.debug( "Value fetched from TTrnPolicy--->" + policyDataVo.getPolicyId() );

		policyDataVo.setCommonVO( homeVO.getCommonVO() );
		policyDataVo.setCommission( homeVO.getCommission() );
		CommonVO commonVO = policyDataVo.getCommonVO();
		homeInsuranceVO = invokeRating( homeInsuranceVO, homeVO, policyDataVo );
		
		java.util.List<Object> legacyRecord = null;
		
		if( !Utils.isEmpty( homeVO.getCommonVO().getPolicyNo() ) ){
			legacyRecord = DAOUtils.getSqlResultSingleColumn( QueryConstants.FETCH_LEGACY_RECORD, (HibernateTemplate) Utils.getBean( com.Constant.CONST_HIBERNATETEMPLATE ), homeVO.getCommonVO()
					.getPolicyNo(),homeVO.getCommonVO().getPolicyId());
		}
		

		if( !Utils.isEmpty( commonVO ) && !commonVO.getIsQuote() && isPopulateOperation){
			proratePremium( homeInsuranceVO, policyDataVo );
		}
		
		if(isPopulateOperation && Utils.isEmpty(legacyRecord))
		{
			applyMinPrmForDisplay( homeVO, policyDataVo );
		}
		
		if(!Utils.isEmpty( homeVO.getPremiumVO() )){
			policyDataVo.getPremiumVO().setGovtTax( homeVO.getPremiumVO().getGovtTax() );
			policyDataVo.getPremiumVO().setPolicyFees( homeVO.getPremiumVO().getPolicyFees() );
		}
		if(!Utils.isEmpty(policyDataVo.getGeneralInfo().getAdditionalInfo())){
			if(Utils.isEmpty(homeVO.getGeneralInfo())){
				homeVO.setGeneralInfo(new GeneralInfoVO());
				homeVO.getGeneralInfo().setAdditionalInfo(new AdditionalInsuredInfoVO());
			}
			if(Utils.isEmpty(homeVO.getGeneralInfo().getAdditionalInfo())){
				homeVO.getGeneralInfo().setAdditionalInfo(new AdditionalInsuredInfoVO());
			}
			homeVO.getGeneralInfo().getAdditionalInfo().setAffinityRefNo(policyDataVo.getGeneralInfo()
					.getAdditionalInfo().getAffinityRefNo());
		}

		if( !isPopulateOperation ){
			//Call saveBuildingDetailsSection() to save building details to T_TRN_BUILDING/QUO table
			buildingDetailsSvc.invokeMethod( "buildingSaveService", homeInsuranceVO, policyDataVo );
			//If Contents are not empty, only then invoking content save service -- Null check has been removed to accommodate cover removal scenario
			//Call saveContents() to save cover details to T_TRN_CONTENT/QUO table
			contentSvc.invokeMethod( SvcConstants.SAVE_CONTENTS, homeInsuranceVO, policyDataVo );
			//Call saveOrUpdateTtrnPrmTable() to save cover details to T_TRN_PREMIUM/QUO table
			premiumSvc.invokeMethod( SvcConstants.SAVE_PREMIUM, homeInsuranceVO, policyDataVo );
			//Call saveUWQuestionsAns() to save uwquestions to T_TRN_UWQUESTIONS/QUO table
			uwqaSaveCommonSvc.invokeMethod( SvcConstants.SAVE_UW_QUES_ANS, homeInsuranceVO, policyDataVo );
			policyDataVo.getCommonVO().setLob( homeVO.getCommonVO().getLob() );
			

			if( ( !Utils.isEmpty( homeVO ) && !Utils.isEmpty( homeVO.getReferralVOList() ) ) ){
				saveReferralMessage( homeVO );
				policyDataVo.getCommonVO().setStatus( SvcConstants.POL_STATUS_REFERRED );
			}
			
			if( commonVO.getIsQuote() ){
				PASStoredProcedure sp = null;
				sp = (PASStoredProcedure) Utils.getBean( "commonUpdPrmQuoEndt" );
				
				if( LOGGER.isInfo() ) LOGGER.info( "Invoking COMMON_UPDATE_PREMIUM_QUOTE_ENDT procedure with inputs {[" );
				sp.call( policyDataVo.getCommonVO().getPolicyId(), policyDataVo.getCommonVO().getEndtId(),Integer.valueOf(Utils.getSingleValueAppConfig( commonVO.getLob()+"_CLASS_CODE" ) ));
				if(Utils.isEmpty(legacyRecord))
					applyMinPrm( homeInsuranceVO, homeVO, policyDataVo );
				issueQuoteHomeInsurance( policyDataVo );
				commonVO.setStatus( SvcConstants.POL_STATUS_ACTIVE );
				if(!Utils.isEmpty( policyDataVo.getCommonVO() ) && !Utils.isEmpty(  policyDataVo.getCommonVO().getVsd()  )){
					commonVO.setVsd( policyDataVo.getCommonVO().getVsd() );
				}
			
			}
			else{
				if(Utils.isEmpty(legacyRecord))
					applyMinPrm( homeInsuranceVO, homeVO, policyDataVo );
				PASStoredProcedure sp = null;
				sp = (PASStoredProcedure) Utils.getBean( "commonUpdPrmPolEndt" );
				if( LOGGER.isInfo() ) LOGGER.info( "Invoking COMMON_UPDATE_PREMIUM_POILCY_ENDT procedure with inputs {[" );
				sp.call( policyDataVo.getCommonVO().getPolicyId(), policyDataVo.getCommonVO().getEndtId(),Integer.valueOf(Utils.getSingleValueAppConfig( commonVO.getLob()+"_CLASS_CODE" )));
				//sp.call( policyDataVo.getCommonVO().getPolicyId(), policyDataVo.getCommonVO().getEndtId(),Integer.valueOf(Utils.getSingleValueAppConfig( commonVO.getLob()+"_CLASS_CODE" )));
				
			
				
			}
			
			//Generate Endt Text
			SvcUtils.generateEndtText(policyDataVo);


		}

		LOGGER.info( "Exiting saveHomeInsurance" );

		return holder;
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
		//	double discLoadAmt = ( homeInsuranceVO.getPremiumVO().getDiscOrLoadPerc() * homeInsuranceVO.getPremiumVO().getPremiumAmt() ) / 100;
		//	minPrmToApply = minPrmToApply - discLoadAmt;
		//	if( minPrmToApply > 0 ){
				homeInsuranceVO.getPremiumVO().setMinPremiumApplied( BigDecimal.valueOf( minPrmToApply ) );
				if( !( homeInsuranceVO.getCommonVO().getIsQuote() && homeInsuranceVO.getPremiumVO().getPremiumAmt() == 0) ){
					homeInsuranceVO.getPremiumVO().setPremiumAmt( homeInsuranceVO.getPremiumVO().getPremiumAmt() + minPrmToApply );
				}
		//	}
		}

	}

	/**
	 * Method to get the prorate the premium
	 * 
	 * @param homeInsuranceVO
	 */
	private void proratePremium( BaseVO homeInsuranceVO, PolicyDataVO policyDataVO ){
		HomeInsuranceVO homeVo = (HomeInsuranceVO) homeInsuranceVO;

		List<CoverDetailsVO> covers = homeVo.getCovers();
		BuildingDetailsVO buildingDetailsVO = homeVo.getBuildingDetails();

		Double proratedPremium = null;
		Double totalPremium = 0.0;

		Double actualProratedPremium = 0.0;
		
		for( CoverDetailsVO cover : covers ){
			Short coverCode = cover.getCoverCodes().getCovCode();
			Integer rtCode = cover.getRiskCodes().getRiskType();
			Integer rskCat = cover.getRiskCodes().getRiskCat();
			Double currentPremium = cover.getPremiumAmt() + ( ( cover.getPremiumAmt() * ( cover.getDiscOrLoadPerc() / 100 ) ) );

			proratedPremium = getProratedPremium( homeVo, policyDataVO, coverCode, rtCode , rskCat, currentPremium);

			/* This is done to calculate the prorated premium without cover level discount */
			actualProratedPremium += getProratedPremium( homeVo, policyDataVO, coverCode, rtCode , rskCat, cover.getPremiumAmt());
			
			if( !Utils.isEmpty( proratedPremium ) ){
				totalPremium+= proratedPremium;
				cover.setPremiumAmt( proratedPremium );
			}
			
			proratedPremium = null;
		}

		proratedPremium = null;
		if(!Utils.isEmpty( buildingDetailsVO )){
			Double currentPremium = buildingDetailsVO.getPremiumAmt() + ( ( buildingDetailsVO.getPremiumAmt() * ( buildingDetailsVO.getDiscOrLoadPerc() / 100 ) ) );
			proratedPremium = getProratedPremium( homeVo,policyDataVO,SvcConstants.DEFAULT_HOME_COVER_CODE, SvcConstants.BUILDING_RISK_TYPE_CODE, buildingDetailsVO.getRiskCodes().getRiskCat(), currentPremium );
			
			/* This is done to calculate the prorated premium without cover level discount */
			actualProratedPremium += getProratedPremium( homeVo,policyDataVO,SvcConstants.DEFAULT_HOME_COVER_CODE, SvcConstants.BUILDING_RISK_TYPE_CODE, buildingDetailsVO.getRiskCodes().getRiskCat(), buildingDetailsVO.getPremiumAmt() );
			
			if( !Utils.isEmpty( proratedPremium ) ){
				totalPremium+= proratedPremium;
				buildingDetailsVO.setPremiumAmt( proratedPremium );
			}
		}
		
		
		
		
		
		homeVo.getPremiumVO().setPremiumAmt( totalPremium );
		/* This is done to calculate the prorated premium without cover level discount */
		homeVo.getPremiumVO().setActualProratedPremium( actualProratedPremium );
		//Add the deleted cover premiums to total premium if policy journey
		LoadDataInputVO loadDataInputVO = new LoadDataInputVO();
		loadDataInputVO.setIsQuote( homeVo.getCommonVO().getIsQuote() );
		loadDataInputVO.setQuoteNo( homeVo.getCommonVO().getQuoteNo() );
		loadDataInputVO.setEndtId( homeVo.getCommonVO().getEndtId() );
		loadDataInputVO.setLocCode( homeVo.getCommonVO().getLocCode() );
		loadDataInputVO.setPolicyNo( homeVo.getCommonVO().getPolicyNo() );
		loadDataInputVO.setDocCode( homeVo.getCommonVO().getDocCode() );
		loadDataInputVO.setPolEffectiveDate( homeVo.getCommonVO().getPolEffectiveDate() );
		addDeletedCoverPremium(homeVo, loadDataInputVO);
	}

	/**
	 * Method to get the prorated premium
	 * 
	 * @param homeVo
	 * @param policyDataVO 
	 * @param coverCode
	 * @param rtCode
	 * @return
	 */
	private Double getProratedPremium( HomeInsuranceVO homeVo, PolicyDataVO policyDataVO, Short coverCode, Integer rtCode, Integer rskCat, Double currentPremium ){
		CommonVO commonVO = homeVo.getCommonVO();
		List<Object[]> previousData = null;
		Double proratedPremium = null;
		BigDecimal previousPremium = BigDecimal.ZERO;
		BigDecimal prevAnnualPrm = BigDecimal.ZERO;

		Date polStartDate = policyDataVO.getScheme().getEffDate();
		Long newPolExpiryDays = zeroVal;
		Long oldPolExpiryDays = zeroVal;

		if( !Utils.isEmpty( policyDataVO.getEndEffectiveDate() ) ){
			newPolExpiryDays = PremiumHelper.getDifference( policyDataVO.getPolExpiryDate(), policyDataVO.getEndEffectiveDate() );
		}
		else{
			newPolExpiryDays = PremiumHelper.getDifference( policyDataVO.getPolExpiryDate(), polStartDate );
		}

		
		List<Object[]>  result = DAOUtils.getSqlResultForPas (  QueryConstants.POLICY_EXT,commonVO.getPolicyId(),
				commonVO.getEndtId());
		
		int policyExtDays = 0;
		if(!Utils.isEmpty( result ) && result.size() > 0 ){
			policyExtDays = ( Integer.valueOf( result.get( 0 )[0].toString() ) ).intValue() ;
			policyExtDays = policyExtDays < 0 ? policyExtDays * -1 : policyExtDays;
			/*if(policyExtDays > 0){
				policyExtDays=policyExtDays+1;
			}*/
		}
		
		//Assuming policy extension is not done
		oldPolExpiryDays = newPolExpiryDays;
		
		if(policyExtDays>0){
			oldPolExpiryDays = oldPolExpiryDays - policyExtDays;
		}

		if( !Utils.isEmpty( coverCode ) && !Utils.isEmpty( rtCode ) ){
			previousData = DAOUtils.getPreviousData( commonVO.getPolicyId(), commonVO.getEndtId(), coverCode, rtCode, rskCat );
		}

		if( !Utils.isEmpty( previousData ) && !Utils.isEmpty( previousData.get( 0 ) ) ){
			
			BigDecimal prevPremium = (BigDecimal) previousData.get( 0 )[ 0 ];
			prevAnnualPrm = (BigDecimal) previousData.get( 0 )[ 2 ];
			
			//While re adding the deleted covers, pro rated premium should be calculated without considering previous premium
			BigDecimal status = (BigDecimal) previousData.get( 0 )[ 3 ];
			if(status.equals( BigDecimal.valueOf( 4 ) )){
				prevPremium = BigDecimal.ZERO;
				prevAnnualPrm = BigDecimal.ZERO;
				previousPremium = BigDecimal.ZERO;
			}
			if(!Utils.isEmpty( prevPremium )){
				previousPremium = prevPremium;
			}
		}

		BigDecimal currPremium =  BigDecimal.valueOf( currentPremium );
		proratedPremium = PremiumHelper.getProratedPrm( oldPolExpiryDays, newPolExpiryDays, previousPremium,prevAnnualPrm, currPremium, polStartDate ).doubleValue();
		proratedPremium = Double.parseDouble( Currency.getUnformattedScaledCurrency( BigDecimal.valueOf( proratedPremium ), homeVo.getCommonVO().getLob().name() ) );
		LOGGER.debug( "PRORATE PREM "+proratedPremium + " cov code "+ coverCode + " rt code "+ rtCode );
		return proratedPremium;
	}

	private BaseVO approveQuoHomeInsurance( BaseVO holder ){
		LOGGER.info( "Entering Home Insurance Approve Quote" );
		saveHomeInsurance( holder );
		DataHolderVO<Object[]> dataHolder = (DataHolderVO<Object[]>) holder;
		HomeInsuranceVO homeInsuranceVO = (HomeInsuranceVO) dataHolder.getData()[ 0 ];
		PolicyCommentsHolder polCommHoler = new PolicyCommentsHolder();
		polCommHoler.setCommonDetails( homeInsuranceVO.getCommonVO() );
		approveQuote( polCommHoler );
		return holder;
	}

	private void approveQuote( BaseVO baseVo ){
		polComnSvc.invokeMethod( "approveQuote", baseVo );
	}

	/**
	 * @param homeInsuranceVO
	 * @param homeVO
	 * @param policyDataVo
	 * @return
	 * 
	 *         It invokes the rating engine to get the premium value
	 */
	private BaseVO invokeRating( BaseVO homeInsuranceVO, HomeInsuranceVO homeVO, PolicyDataVO policyDataVo ){

		homeVO.setPolicyType( policyDataVo.getPolicyType() );
		homeVO.setScheme( policyDataVo.getScheme() );
		homeVO.setPolicyClassCode( policyDataVo.getPolicyClassCode() );
		HomeRatingInvoker ratingService = (HomeRatingInvoker) Utils.getBean( "homeRatingInvoker" );
		homeInsuranceVO = ratingService.invokeRating( homeInsuranceVO );
		return homeInsuranceVO;
	}

	private BaseVO loadHomeInsurance( BaseVO args ){

		CommonVO commonVO = (CommonVO) args;
		HomeInsuranceVO homeInsuranceVO = new HomeInsuranceVO();
		homeInsuranceVO.setCommonVO( commonVO );

		PolicyDataVO policyDataVo = (PolicyDataVO) commonGenSvcBean.invokeMethod( SvcConstants.LOAD_GEN_INFO, homeInsuranceVO );
		LOGGER.debug( "General Info loaded - Policy Id : " + policyDataVo.getPolicyId() );

		LoadDataInputVO loadDataInputVO = new LoadDataInputVO();
		loadDataInputVO.setIsQuote( commonVO.getIsQuote() );
		loadDataInputVO.setQuoteNo( commonVO.getQuoteNo() );
		loadDataInputVO.setEndtId( commonVO.getEndtId() );
		loadDataInputVO.setLocCode( commonVO.getLocCode() );
		loadDataInputVO.setPolicyNo( commonVO.getPolicyNo() );
		loadDataInputVO.setDocCode( commonVO.getDocCode() );
		loadDataInputVO.setPolEffectiveDate( commonVO.getPolEffectiveDate() );

		BuildingDetailsVO buildingDetails = (BuildingDetailsVO) baseHomeBuildingLoadSvc.invokeMethod( "homeBuildingDetailsLoadService", loadDataInputVO );
		homeInsuranceVO = (HomeInsuranceVO) baseCoverDetailsLoadSvc.invokeMethod( "homeCoverDetailsLoadService", loadDataInputVO, homeInsuranceVO );
		UWQuestionsVO uwQuestionsVO = (UWQuestionsVO) uwQuestionsLoadSvc.invokeMethod( "uwQuestionsLoadService", loadDataInputVO );
		homeInsuranceVO.setBuildingDetails( buildingDetails );
		homeInsuranceVO.setUwQuestions( uwQuestionsVO );
		homeInsuranceVO.setCommonVO( commonVO );
		homeInsuranceVO.setGeneralInfo( policyDataVo.getGeneralInfo() );
		homeInsuranceVO.setScheme( policyDataVo.getScheme() );
		homeInsuranceVO.setClassCode( policyDataVo.getPolicyClassCode() );
		homeInsuranceVO.setEndEffectiveDate( policyDataVo.getEndEffectiveDate() );
		homeInsuranceVO.setAuthenticationInfoVO( policyDataVo.getAuthenticationInfoVO() );
		homeInsuranceVO.getPremiumVO().setVatTaxPerc(policyDataVo.getPremiumVO().getVatTaxPerc());// 142244
		if(!Utils.isEmpty( policyDataVo.getPremiumVO().getViewVatAmount())){
		homeInsuranceVO.getPremiumVO().setViewVatAmount(policyDataVo.getPremiumVO().getViewVatAmount());// 142244 In View mode for correcT vat amount display check between legacy transaction and new vat  transaction
		}
		else{
			
			homeInsuranceVO.getPremiumVO().setViewVatAmount(0.0);
		}
		if(!Utils.isEmpty( policyDataVo.getVatCode())){
			
			LOGGER.debug( "General Info loaded - IN if block" +policyDataVo.getVatCode());
			homeInsuranceVO.setVatCode(policyDataVo.getVatCode());// 142244
			
			homeInsuranceVO.getPremiumVO().setVatCode(policyDataVo.getPremiumVO().getVatCode());// 142244
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date polExpiryDate = null;
            try {   polExpiryDate = dateFormat.parse(homeInsuranceVO.getScheme().getExpiryDate().toString());
                            LOGGER.debug("Date formatted - Policy Expiry Date: "+ polExpiryDate);
            } catch (ParseException e) {
          e.printStackTrace();
            }
            homeInsuranceVO.getCommonVO().setPolExpiryDate(polExpiryDate);

        	homeInsuranceVO.getPremiumVO().setVatablePrm(policyDataVo.getPremiumVO().getVatablePrm());
		
		}

		
		/*
		 * Start - Fetch the gacc Person details 
		 */
		LoadDataInputVO dataInputVO = new LoadDataInputVO();
		dataInputVO.setIsQuote( commonVO.getIsQuote() );
		if(commonVO.getIsQuote()){
			dataInputVO.setQuoteNo( commonVO.getQuoteNo() );
		}
		else{
			dataInputVO.setPolicyNo( commonVO.getPolicyNo() );
		}
		dataInputVO.setEndtId( commonVO.getEndtId() );
		dataInputVO.setLocCode( commonVO.getLocCode() );
		dataInputVO.setPolEffectiveDate( commonVO.getPolEffectiveDate() );
		dataInputVO.setDocCode( commonVO.getDocCode() );
		
		Map<String, Class<? extends BaseVO>> OldDataMap = new LinkedHashMap<String, Class<? extends BaseVO>>();

		OldDataMap.put( SvcConstants.T_TRN_GACC_PERSON, TTrnGaccPersonVOHolder.class );

		DataHolderVO<LinkedHashMap<String, List<TableData<BaseVO>>>> oldDataHolder = (DataHolderVO) baseLoadSvc.invokeMethod( "baseLoad", dataInputVO, OldDataMap );
		List<TableData<BaseVO>> polTableData = oldDataHolder.getData().get( SvcConstants.T_TRN_GACC_PERSON );
		List<StaffDetailsVO> staffDetails = new ArrayList<StaffDetailsVO>( 0 );
		if( !Utils.isEmpty( polTableData ) ){
			for( TableData<BaseVO> tableData : polTableData ){
				TTrnGaccPersonVOHolder trnGaccPersonVOHolder = (TTrnGaccPersonVOHolder) tableData.getTableData();
				StaffDetailsVO staffDetailsVO = new StaffDetailsVO();
				staffDetailsVO.setEmpId(trnGaccPersonVOHolder.getGprId());
				staffDetailsVO.setEmpName(trnGaccPersonVOHolder.getGprEName());
				staffDetailsVO.setEmpDob(trnGaccPersonVOHolder.getGprDateOfBirth());
				staffDetailsVO.setEmpVsd(trnGaccPersonVOHolder.getGprValidityStartDate());
				staffDetails.add(staffDetailsVO);
				
			}

		}
		homeInsuranceVO.setStaffDetails(staffDetails);
		/*
		 * End - Fetch the gacc Person details 
		 */
		populatePackagePremium( homeInsuranceVO, loadDataInputVO );
		

		//populate EndorsmentVo in case of endorsement no greater than 0
		if( commonVO.getEndtNo() > 0
				&& ( Flow.AMEND_POL.equals( commonVO.getAppFlow() ) || Flow.VIEW_POL.equals( commonVO.getAppFlow() ) || ( Flow.RESOLVE_REFERAL.equals( commonVO.getAppFlow() ) && !commonVO
						.getIsQuote() ) ) ){
			populateEndorsmentVO( homeInsuranceVO );
		}
		
		/*if(!Utils.isEmpty(homeInsuranceVO.getAuthenticationInfoVO().getRefPolicyNo()) && homeInsuranceVO.getAuthenticationInfoVO().getRefPolicyNo().longValue()!=0)
		{
			homeInsuranceVO.getCommonVO().setOldContentPPCode(DAOUtils.checkOldContentPPPED(homeInsuranceVO.getAuthenticationInfoVO().getRefPolicyNo()));
		}
		else */if(!homeInsuranceVO.getCommonVO().getIsQuote())
		{
			homeInsuranceVO.getCommonVO().setOldContentPPCode(DAOUtils.checkOldContentPPPED(homeInsuranceVO.getCommonVO().getPolicyNo()));
		}
		else if(homeInsuranceVO.getCommonVO().getIsQuote() && !Utils.getSingleValueAppConfig("EMIRATES_SCH_CODE").equals(homeInsuranceVO.getScheme().getSchemeCode().toString()))
		{
			DAOUtils.checkOldContentPPCode(homeInsuranceVO.getCommonVO().getPolicyId(),homeInsuranceVO.getCommonVO().getEndtId(),homeInsuranceVO.getCommonVO().getIsQuote());
		}
				
		return homeInsuranceVO;
	}

	/**
	 * @param homeInsuranceVO
	 */
	private void populateEndorsmentVO( HomeInsuranceVO homeInsuranceVO ){

		com.mindtree.ruc.cmn.utils.List<EndorsmentVO> endorsements = new com.mindtree.ruc.cmn.utils.List<EndorsmentVO>( EndorsmentVO.class );
		/* Update endt no and id before loading premium page*/

		EndorsmentVO endorsmentVO = null;
		Long endtId = homeInsuranceVO.getCommonVO().getEndtId();
		Long polId = homeInsuranceVO.getCommonVO().getPolicyId();

		if( !Utils.isEmpty( endtId ) ){

			if( Flow.VIEW_POL.equals( homeInsuranceVO.getCommonVO().getAppFlow() ) || Flow.RESOLVE_REFERAL.equals( homeInsuranceVO.getCommonVO().getAppFlow() ) ){
				BaseVO baseVO1 = TaskExecutor.executeTasks( "CAPTURE_AMEND_POLICY_ENDT_TEXT", homeInsuranceVO );
				homeInsuranceVO = (HomeInsuranceVO) baseVO1;
				if( !Utils.isEmpty( homeInsuranceVO.getEndorsmentVO() ) ){
					endorsmentVO = homeInsuranceVO.getEndorsmentVO().get( 0 );
				}
				else{
					endorsmentVO = new EndorsmentVO();
					endorsmentVO.setEndNo( homeInsuranceVO.getCommonVO().getEndtNo() );
					endorsmentVO.setEndtId( homeInsuranceVO.getCommonVO().getEndtId() );
					endorsmentVO.setPolicyId( homeInsuranceVO.getCommonVO().getPolicyId() );
					endorsmentVO.setSlNo( 1 );
				}
			}
			else{
				endorsmentVO = new EndorsmentVO();
				endorsmentVO.setEndNo( homeInsuranceVO.getCommonVO().getEndtNo() );
				endorsmentVO.setEndtId( homeInsuranceVO.getCommonVO().getEndtId() );
				endorsmentVO.setPolicyId( homeInsuranceVO.getCommonVO().getPolicyId() );
				endorsmentVO.setSlNo( 1 );
			}

			PremiumVO oldPremiumVO = new PremiumVO();
			PremiumVO newPremiumVO = new PremiumVO();

			/*
			 * After cancellation of policy the premium will be 0, and since the need is to display the 
			 * refund amount before actually cancellation of policy new premium for calculation is taken as 0.0
			 */
			Double newPremiumAmt = 0.0;
			/*
			 * Caching Issue    
			 * Ticket - 117462/117605/117549/120595 : BEGIN
			 */
			DataHolderVO<HashMap<String, Double>> premiumHolder = PremiumHelper.getPremiumForProrate( polId, endtId,homeInsuranceVO.getCommonVO().getLob() );
			/*
			 * Caching Issue    
			 * Ticket - 117462/117605/117549/120595 : END
			 */
			
			newPremiumAmt = premiumHolder.getData().get( SvcConstants.NEW_ANNUALIZED_PREMIUM );

			Double currentPremiumAmt = premiumHolder.getData().get( SvcConstants.OLD_ANNUALIZED_PREMIUM );

			newPremiumVO.setPremiumAmt( newPremiumAmt ); // New premium amount.
			oldPremiumVO.setPremiumAmt( currentPremiumAmt ); // Old premium amount.

			endorsmentVO.setOldPremiumVO( oldPremiumVO );
			endorsmentVO.setPremiumVO( newPremiumVO );
			endorsmentVO.setEndEffDate( homeInsuranceVO.getEndEffectiveDate() );
			endorsmentVO.setEndDate( homeInsuranceVO.getScheme().getExpiryDate() );
			endorsmentVO.setEndNo( homeInsuranceVO.getCommonVO().getEndtNo() );

			endorsements.add( endorsmentVO );

			homeInsuranceVO.setEndorsmentVO( endorsements );

		}

	}

	/**
	 * @param homeInsuranceVO
	 * calculates the package premium that need to be displayed in home risk page
	 */
	private void populatePackagePremium( HomeInsuranceVO homeInsuranceVO, LoadDataInputVO loadDataInputVO ){

		double packagePremium = 0;
		double packageActualPremium = 0;
		double vatAmt = 0;
		double totalPremium = 0.0, totalDiscountAmount=0.0, promoDiscountAmt=0.0;
		if( !Utils.isEmpty( homeInsuranceVO ) ){
			if( !Utils.isEmpty( homeInsuranceVO.getBuildingDetails() ) ){
				packagePremium += homeInsuranceVO.getBuildingDetails().getPremiumAmt();
				packageActualPremium += homeInsuranceVO.getBuildingDetails().getPremiumAmtActual();
			}

			for( CoverDetailsVO coverDetailsVO : homeInsuranceVO.getCovers() ){
				packagePremium += coverDetailsVO.getPremiumAmt();
				packageActualPremium += coverDetailsVO.getPremiumAmtActual();
			}
			if( Utils.isEmpty( homeInsuranceVO.getPremiumVO() ) ){
				homeInsuranceVO.setPremiumVO( new PremiumVO() );
			}
			//142244
			if( Utils.isEmpty( homeInsuranceVO.getCommonVO().getPremiumVO() ) ){
				homeInsuranceVO.getCommonVO().setPremiumVO( new PremiumVO() );
				homeInsuranceVO.getCommonVO().setPremiumVO(homeInsuranceVO.getPremiumVO()) ;
				
			}
			
			
			homeInsuranceVO.getPremiumVO().setMinPremium( (BigDecimal) premiumSvc.invokeMethod( SvcConstants.GET_CONFIG_MIN_PRM, homeInsuranceVO ) ) ;
			
			if( homeInsuranceVO.getCommonVO().getStatus() == SvcConstants.DEL_SEC_LOC_STATUS && !homeInsuranceVO.getCommonVO().getIsQuote() ){
				/*
				 * Caching Issue    
				 * Ticket - 117462/117605/117549/120595 : BEGIN
				 */
				DataHolderVO<HashMap<String, Double>> canceledPrm = PremiumHelper.getPremiumForProrate( homeInsuranceVO.getCommonVO().getPolicyId(), homeInsuranceVO.getCommonVO()
						.getEndtId(),homeInsuranceVO.getCommonVO().getLob() );
				/*
				 * Caching Issue    
				 * Ticket - 117462/117605/117549/120595 : END
				 */
				//142244 Vat
				Double discAmt = PremiumHelper.getSplPrm( homeInsuranceVO.getCommonVO(), homeInsuranceVO.getCommonVO().getEndtId(), new Short[]{ SvcConstants.SC_PRM_COVER_LOADING,
						SvcConstants.SC_PRM_COVER_DISCOUNT, Short.valueOf( SvcConstants.SPECIAL_COVER_MIN_PRM ), Short.valueOf( SvcConstants.SC_PRM_COVER_GOVT_TAX ), Short.valueOf( SvcConstants.SC_PRM_COVER_POLICY_FEE )}, (HibernateTemplate) Utils.getBean( com.Constant.CONST_HIBERNATETEMPLATE ) );
				LOGGER.debug( "**In populatePackagePremium()**  Spl cover amount" +discAmt);
				homeInsuranceVO.getPremiumVO().setPremiumAmt( canceledPrm.getData().get( SvcConstants.NEW_ANNUALIZED_PREMIUM ) - discAmt );
				homeInsuranceVO.getPremiumVO().setDiscOrLoadAmt( BigDecimal.valueOf( discAmt ));
				//142244 Vat primary Initial Implementation
			/*	if(!Utils.isEmpty(homeInsuranceVO.getPremiumVO().getVatTax())){
					double vatAmount=0.0;
					double prmAmt=0.0;
					double prmAmtExclusiveVat=0.0;
					 vatAmount=homeInsuranceVO.getPremiumVO().getVatTax();
					 prmAmt=	homeInsuranceVO.getPremiumVO().getPremiumAmt();
					 prmAmtExclusiveVat=prmAmt-vatAmount;
					 homeInsuranceVO.getPremiumVO().setPremiumAmt(prmAmtExclusiveVat);
				}*/
				// added by Anveshan. For canceled policy, premium percentage is set to be zero
			}else{
				homeInsuranceVO.getPremiumVO().setPremiumAmt( packagePremium );
				addDeletedCoverPremium(homeInsuranceVO, loadDataInputVO);
				Double minPrm = PremiumHelper.getSplPrm( homeInsuranceVO.getCommonVO(), homeInsuranceVO.getCommonVO().getEndtId(), new Short[]{  Short.valueOf( SvcConstants.SPECIAL_COVER_MIN_PRM ) }, (HibernateTemplate) Utils.getBean( com.Constant.CONST_HIBERNATETEMPLATE ) );
				homeInsuranceVO.getPremiumVO().setMinPremiumApplied( BigDecimal.valueOf( minPrm ) );
				homeInsuranceVO.getPremiumVO().setPremiumAmt( homeInsuranceVO.getPremiumVO().getPremiumAmt() + minPrm);
			}
			//142244 Vat 
			if(!Utils.isEmpty(homeInsuranceVO.getCommonVO().getIsRenewals())){
			if( !Utils.isEmpty(homeInsuranceVO.getCommonVO().getPremiumVO().getVatTax()) && homeInsuranceVO.getCommonVO().getIsRenewals())
			{

				if (!Utils.isEmpty(homeInsuranceVO.getPremiumVO()
						.getDiscOrLoadPerc())) {
					totalDiscountAmount += Double
							.parseDouble(Currency
									.getFormattedCurrency(
											(homeInsuranceVO.getPremiumVO()
													.getDiscOrLoadPerc() * packagePremium) / 100,
											LOB.HOME.name()).replace(",", ""));
				}
		totalPremium=	totalDiscountAmount+packagePremium;
		vatAmt=(homeInsuranceVO.getCommonVO().getPremiumVO().getVatTaxPerc() * totalPremium) / 100;
		homeInsuranceVO.getPremiumVO().setVatTax(Double.parseDouble(Currency
				.getUnformattedScaledCurrency(new BigDecimal(vatAmt),"SBS")));
		
		LOGGER.debug( "In populatePackagePremium() packagePremium " +packagePremium );
		LOGGER.debug( "In populatePackagePremium() with all components totalPremium " +totalPremium );
		LOGGER.debug( "In populatePackagePremium() vatAmt " +vatAmt );

			}
			
			}
			homeInsuranceVO.getPremiumVO().setPremiumAmtActual( packageActualPremium );
			//Add the deleted cover premiums to total premium if policy journey
		}
	}
	
	/**
	 * @author Sarath Varier
	 * Method to fetch the deleted covers and add up the permium to total premium
	 */
	private void addDeletedCoverPremium(HomeInsuranceVO homeInsuranceVO, LoadDataInputVO loadDataInputVO){
		
		double packagePremium = homeInsuranceVO.getPremiumVO().getPremiumAmt();
		double actualProratedPremium = 0.0;
		
		if(!Utils.isEmpty( homeInsuranceVO.getPremiumVO() ) && !Utils.isEmpty( homeInsuranceVO.getPremiumVO().getActualProratedPremium() )){
			actualProratedPremium = homeInsuranceVO.getPremiumVO().getActualProratedPremium();
		}
		
		if( !homeInsuranceVO.getCommonVO().getIsQuote()){
			
			loadDataInputVO.setStatus(Integer.valueOf(4));
			Map<String, Class<? extends BaseVO>> dataToLoad = new LinkedHashMap<String, Class<? extends BaseVO>>();
			dataToLoad.put( SvcConstants.TABLE_ID_T_TRN_PREMIUM_QUO, TTrnPremiumVOHolder.class );
			@SuppressWarnings({ "unchecked", "rawtypes" })
			DataHolderVO<LinkedHashMap<String, List<TableData>>> dataHolderVO = (DataHolderVO<LinkedHashMap<String, List<TableData>>>) 
						baseLoadSvc.invokeMethod( "baseLoad", loadDataInputVO, dataToLoad );
			@SuppressWarnings("rawtypes")
			List<TableData> premiumList = dataHolderVO.getData().get( SvcConstants.TABLE_ID_T_TRN_PREMIUM_QUO );
			
			if( !Utils.isEmpty( premiumList ) ){
				@SuppressWarnings("rawtypes")
				Iterator<TableData> iterator = premiumList.iterator();
				while( iterator.hasNext() ){
					TTrnPremiumVOHolder premiumVOHolder = (TTrnPremiumVOHolder) iterator.next().getTableData();
					packagePremium += (premiumVOHolder.getPrmPremium()).doubleValue();
					/* This is done to calculate the prorated premium without cover level discount */
					actualProratedPremium += (premiumVOHolder.getPrmPremium()).doubleValue();
				}
			}
		}
		
		homeInsuranceVO.getPremiumVO().setActualProratedPremium( actualProratedPremium );
		homeInsuranceVO.getPremiumVO().setPremiumAmt( packagePremium );
	}

	/**
	 * To load the promotional covers for the selected scheme
	 * @param homeInsuranceVO
	 */
	private BaseVO loadPromotionalCovers( BaseVO baseVO ){

		Map<String, Object> result = new HashMap<String, Object>();
		HomeInsuranceVO homeInsuranceVO = (HomeInsuranceVO) baseVO;
		DataHolderVO<Object[]> dataHolder = new DataHolderVO<Object[]>();

		List<Object> promoCoverCodesWithDesc = DAOUtils.getPromotionalCodeCover( homeInsuranceVO.getScheme().getSchemeCode(), homeInsuranceVO.getGeneralInfo().getSourceOfBus()
				.getPromoCode(), homeInsuranceVO.getClassCode(), homeInsuranceVO.getScheme().getEffDate(),homeInsuranceVO.getCommonVO().getIsQuote() );
		List<Object> promoDiscountWithDesc = DAOUtils.getPromotionalCodeDiscount( homeInsuranceVO.getScheme().getSchemeCode(), homeInsuranceVO.getGeneralInfo().getSourceOfBus().getPromoCode(),
				homeInsuranceVO.getClassCode(), homeInsuranceVO.getScheme().getEffDate(),homeInsuranceVO.getCommonVO().getIsQuote() );
		
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
	//142244 Vat implementation
	/**
	 * To load the vat rate and vat code, vat effective date
	 * @param homeInsuranceVO
	 */
	private BaseVO fetchVatRateAndCode( BaseVO baseVO ){
		
		
		
		Map<String, Object> result = new HashMap<String, Object>();
		HomeInsuranceVO homeInsuranceVO = (HomeInsuranceVO) baseVO;
		DataHolderVO<Object[]> dataHolder = new DataHolderVO<Object[]>();
		Double vatRate=0.0;
		String defaultDateFormat = Utils.getSingleValueAppConfig("DEFAULT_DATE_FORMAT"); // MM/dd/yyyy
		Date vatEffDate = null;
		Date issueDate=null;
		Date polIssueDate=null;
		double oldVatAmt=0.0;
		double oldVatablePrm=0.0;
		Map<Integer, Double> vatResults = new HashMap<Integer, Double>();
		try {
			
			//SvcUtils.populateVatDt();
			vatEffDate = new SimpleDateFormat(defaultDateFormat).parse(SvcUtils.populateVatDt());
		} catch (ParseException e) {
	
			e.printStackTrace();
		}  
		
		if(!Utils.isEmpty(homeInsuranceVO.getVatCode())){
		 vatRate=  DAOUtils.VatCodeAndVatRateFromCodeMaster(homeInsuranceVO.getVatCode(),homeInsuranceVO.getAuthenticationInfoVO().getAccountingDate());
		}

		if( !Utils.isEmpty( vatRate ) ){
			result.put( "vatRate", vatRate ); // Vat Percent
			LOGGER.debug( "**In fetchVatRateAndCode()**  vat Rate" +vatRate);
		}
		else{
			
			result.put( "vatRate", 0.0 );
		}
		 if( !Utils.isEmpty( vatEffDate ) ){
			result.put( "vatEffDate", vatEffDate ); // Vat Effective Date
			LOGGER.debug( "**In fetchVatRateAndCode()** vatEffDate" +vatEffDate);
		}
			if(!homeInsuranceVO.getCommonVO().getIsQuote()){
				polIssueDate = DAOUtils.getPreparedDateofQuoHome(homeInsuranceVO.getCommonVO().getPolicyNo(),homeInsuranceVO.getCommonVO().getPolicyId());
				if( !Utils.isEmpty( polIssueDate ) && !Utils.isEmpty( vatEffDate ) ){
					SimpleDateFormat s2 = new SimpleDateFormat("MM-dd-yyyy");
					String vatEffetiveDate = s2.format(vatEffDate);
					String policyIssueDate = s2.format(polIssueDate);
				if(policyIssueDate.compareTo(vatEffetiveDate) < 0){
					homeInsuranceVO.getCommonVO().setIslegacyPolicy(true);
					LOGGER.debug( "**In fetchVatRateAndCode()** is Policy legacy : Yes" );
				}
			}
		}
			if(!homeInsuranceVO.getCommonVO().getIsQuote()){
					vatResults = DAOUtils.getVatAmountHome(homeInsuranceVO.getCommonVO().getPolicyId(), homeInsuranceVO.getCommonVO().getEndtId());
					if( !Utils.isEmpty( vatResults ) ){
					oldVatAmt =(Double)vatResults.get(0);
					oldVatablePrm =(Double)vatResults.get(1);
					homeInsuranceVO.getPremiumVO().setOldVatAmt(oldVatAmt);
					homeInsuranceVO.getPremiumVO().setOldVatablePrm(oldVatablePrm);
				}
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
	private void saveReferralMessage( HomeInsuranceVO homeInsuranceVO ){

		homeInsuranceVO.getReferralVOList().getReferrals().get( 0 ).setConsolidated( true );

		TaskVO taskVO = homeInsuranceVO.getReferralVOList().getTaskVO();
		//Sonar fix
		//TTrnPasReferralDetails pasReferralDetails = (TTrnPasReferralDetails) 
		pasReferralSaveCmnSvc.invokeMethod( "saveReferralData", homeInsuranceVO );
		taskVO.setDesc( DAOUtils.getTaskDescription(homeInsuranceVO.getCommonVO().getPolicyId(),homeInsuranceVO.getCommonVO().getEndtId()));
		taskVO.setPolEndId( homeInsuranceVO.getCommonVO().getEndtId() );
		taskSvc.invokeMethod( "saveRefferalTask", taskVO );
		
		DataHolderVO<Object[]> dataHolderVO = new DataHolderVO<Object[]>();
		Object[] data = {homeInsuranceVO,"REFERRAL_MAIL_TRIGGER"};
		dataHolderVO.setData( data );
		commonOpSvc.invokeMethod( "sendReferralMail", dataHolderVO );
	}
	
	/**
	 * Method to save the referral message for renewal
	 * 
	 * @param holder
	 * @return
	 */
	private BaseVO saveRenewalReferal(BaseVO holder) {
		DataHolderVO<Object[]> dataHolder = (DataHolderVO<Object[]>) holder;
		BaseVO homeInsuranceVO = (BaseVO) dataHolder.getData()[ 0 ];
		HomeInsuranceVO homeVO = (HomeInsuranceVO) homeInsuranceVO;
		PolicyDataVO policyDataVo = (PolicyDataVO) commonGenSvcBean.invokeMethod( SvcConstants.LOAD_GEN_INFO, homeInsuranceVO );
		policyDataVo.setCommonVO( homeVO.getCommonVO() );
		policyDataVo.setCommission( homeVO.getCommission() );
		if( ( !Utils.isEmpty( homeVO ) && !Utils.isEmpty( homeVO.getReferralVOList() ) ) ){
			saveReferralMessage( homeVO );
			policyDataVo.getCommonVO().setStatus( SvcConstants.POL_STATUS_REFERRED );
		}
		CommonVO commonVO = policyDataVo.getCommonVO();
		
		if(!Utils.isEmpty( commonVO ) && commonVO.getDocCode().shortValue() == SvcConstants.RENEWAL_POL_DOC_CODE){
			PASStoredProcedure sp = (PASStoredProcedure)Utils.getBean( "renewalReferalStatusUpdateHome" );
			sp.call( commonVO.getPolicyId(),commonVO.getEndtId(),commonVO.getStatus(),SvcConstants.POL_STATUS_PENDING );
		}
		return holder;
	}
	
	public BaseLoadSvc getBaseLoadSvc(){
		return baseLoadSvc;
	}

	public void setBaseLoadSvc( BaseLoadSvc baseLoadSvc ){
		this.baseLoadSvc = baseLoadSvc;
	}

	public void setBaseCoverDetailsLoadSvc( HomeCoverDetailsLoadSvc baseCoverDetailsLoadSvc ){
		this.baseCoverDetailsLoadSvc = baseCoverDetailsLoadSvc;
	}

	public void setBaseHomeBuildingLoadSvc( HomeBuildingLoadSvc baseHomeBuildingLoad ){
		this.baseHomeBuildingLoadSvc = baseHomeBuildingLoad;
	}

	public void setUwQuestionsLoadSvc( UWQuestionsLoadSvc uwQuestionsLoadSvc ){
		this.uwQuestionsLoadSvc = uwQuestionsLoadSvc;
	}

	public GeneralInfoCommonSvc getCommonGenSvcBean(){
		return commonGenSvcBean;
	}

	public void setCommonGenSvcBean( GeneralInfoCommonSvc generalInfoLoadSvc ){
		this.commonGenSvcBean = generalInfoLoadSvc;
	}

	public BuildingDetailsSvc getBuildingDetailsSvc(){
		return buildingDetailsSvc;
	}

	public void setBuildingDetailsSvc( BuildingDetailsSvc buildingDetailsSvc ){
		this.buildingDetailsSvc = buildingDetailsSvc;
	}

	public HomeContentSaveSVC getContentSvc(){
		return contentSvc;
	}

	public void setContentSvc( HomeContentSaveSVC contentSvc ){
		this.contentSvc = contentSvc;
	}

	public PremiumSaveCommonSvc getPremiumSvc(){
		return premiumSvc;
	}

	public void setPremiumSvc( PremiumSaveCommonSvc premiumSvc ){
		this.premiumSvc = premiumSvc;
	}

	public UWQASaveCommonSvc getUwqaSaveCommonSvc(){
		return uwqaSaveCommonSvc;
	}

	public void setUwqaSaveCommonSvc( UWQASaveCommonSvc uwqaSaveCommonSvc ){
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

	/**
	 * @return the commonOpSvc
	 */
	public CommonOpSvc getCommonOpSvc(){
		return commonOpSvc;
	}

	/**
	 * @param commonOpSvc the commonOpSvc to set
	 */
	public void setCommonOpSvc( CommonOpSvc commonOpSvc ){
		this.commonOpSvc = commonOpSvc;
	}
	
}
