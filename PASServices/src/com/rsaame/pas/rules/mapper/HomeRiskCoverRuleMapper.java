package com.rsaame.pas.rules.mapper;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.cmn.currency.Currency;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.endorse.svc.AmendPolicySvc;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.bus.BusinessChannel;
import com.rsaame.pas.vo.bus.CoverDetailsVO;
import com.rsaame.pas.vo.bus.EndorsmentVO;
import com.rsaame.pas.vo.bus.GeneralInfoVO;
import com.rsaame.pas.vo.bus.HomeInsuranceVO;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.bus.ReferralListVO;
import com.rsaame.pas.vo.bus.SchemeVO;
import com.rsaame.pas.vo.bus.SourceOfBusinessVO;
import com.rsaame.pas.vo.bus.StaffDetailsVO;
import com.rsaame.pas.vo.bus.UWQuestionRespType;
import com.rsaame.pas.vo.bus.UWQuestionVO;
import com.rsame.rulesengine.restfulclient.request.Characteristics;
import com.rsame.rulesengine.restfulclient.request.Fact;
import com.rsame.rulesengine.restfulclient.request.RiskDetails;
import com.rsame.rulesengine.restfulclient.request.RuleInfo;

public class HomeRiskCoverRuleMapper implements RuleMapper{

	private static final double ZERO = 0.0;

	@Override
	public RuleInfo createRiskDetails( BaseVO policyBaseVO, int section, String roleType, RuleInfo ruleInfo ){

		List<RiskDetails> riskDetailList = ruleInfo.getRiskDetails();

		RiskDetails riskDetails = new RiskDetails();
		riskDetails.setRiskName( "HOME" );

		riskDetails.setRiskId( new Integer( 1000 ) );
		List<Fact> factList = riskDetails.getFact();
		DecimalFormat decForm = new DecimalFormat( RulesConstants.DECIMAL_FORMAT );

		BigDecimal buildingSumInsured = new BigDecimal( decForm.format( 0 ) );
		BigDecimal contentsSumInsured = new BigDecimal( decForm.format( 0 ) );
		BigDecimal personalSumInsured = new BigDecimal( decForm.format( 0 ) );
		BigDecimal contentsArticleSumInsured = new BigDecimal( decForm.format( 0 ) );
		BigDecimal personalArticleSumInsured = new BigDecimal( decForm.format( 0 ) );
		
		BigDecimal personalGreaterThanContent = new BigDecimal( decForm.format( 0 ) );
		BigDecimal extraPremium;
		
		//Variables for cover and building level discount/loading 
		BigDecimal buildingDiscount = null;
		BigDecimal contentDiscount = null;
		BigDecimal personalPossessionDiscount = null;

		String generalQuestionClaim = new String( "no" );
		String generalQuestionHousehold = new String( "no" );
		String generalQuestionConcrete = null;
		String generalQuestionTerms = null;
		//String extraPremium = new String( "N" );
		
		String refundPremium;
		String nilEndorsement;
		String discountPerc = new String( "0.0" );
		String commissionPerc = new String( "N" );
		String oSPremium;
		String priorClaim;
		String endorseAfterRenewal;
		boolean isPopulateOperation = false;
		HomeInsuranceVO homeInsuranceVO = null;
		
		/*Create general info fact */
		Fact homeFact = new Fact();
		homeFact.setFactName( "Home" );
		
		List<Characteristics> homeCharacteristicList = homeFact.getCharacteristics();
		if( !Utils.isEmpty( policyBaseVO ) ){
			homeInsuranceVO = (HomeInsuranceVO) policyBaseVO;
			isPopulateOperation = homeInsuranceVO.isPopulateOperation();
			getRequiredDetails( homeInsuranceVO );
			
			if( !Utils.isEmpty( homeInsuranceVO.getBuildingDetails() ) && !Utils.isEmpty( homeInsuranceVO.getBuildingDetails().getSumInsured() )
					&& !Utils.isEmpty( homeInsuranceVO.getBuildingDetails().getSumInsured().getSumInsured() ) ){
				buildingSumInsured = new BigDecimal( decForm.format( homeInsuranceVO.getBuildingDetails().getSumInsured().getSumInsured() ) );
			}
			
			if( !Utils.isEmpty( homeInsuranceVO.getBuildingDetails() ) && !Utils.isEmpty( homeInsuranceVO.getBuildingDetails().getDiscOrLoadPerc() ) ){
				// The fact value to be used for building discount rule
				if(homeInsuranceVO.getBuildingDetails().getDiscOrLoadPerc() .compareTo( 0.0 ) < 0){
					buildingDiscount = new BigDecimal( decForm.format( homeInsuranceVO.getBuildingDetails().getDiscOrLoadPerc() ) ).abs();
				}
			}

			for( CoverDetailsVO coverDetailsVO : homeInsuranceVO.getCovers() ){

				if( coverDetailsVO.getCoverCodes().getCovCode() == 1 && coverDetailsVO.getRiskCodes().getRiskType() == 31 &&
						!Utils.isEmpty( coverDetailsVO.getSumInsured().getSumInsured() ) ){
					if( coverDetailsVO.getRiskCodes().getRiskCat() == 2 ){
						BigDecimal conArticleSumInsured = new BigDecimal( decForm.format( coverDetailsVO.getSumInsured().getSumInsured() ) );

						if( contentsArticleSumInsured.compareTo( conArticleSumInsured ) == -1 ){
							contentsArticleSumInsured = new BigDecimal( decForm.format( coverDetailsVO.getSumInsured().getSumInsured() ) );
						}
					}
					else{
						contentsSumInsured = new BigDecimal( decForm.format( coverDetailsVO.getSumInsured().getSumInsured() ) );
					}
				}

				//else if(coverDetailsVO.getCoverCodes().getCovCode() == SvcConstants.DEFAULT_COVER_CODE && coverDetailsVO.getRiskCodes().getRiskType() == SvcConstants.PP_RSK_TYPE_CODE && coverDetailsVO.getCoverCodes().getCovSubTypeCode() == 2){
				if( coverDetailsVO.getCoverCodes().getCovCode() == 1 && coverDetailsVO.getRiskCodes().getRiskType() ==  32  &&
						!Utils.isEmpty( coverDetailsVO.getSumInsured().getSumInsured() ) ){
					if( coverDetailsVO.getRiskCodes().getRiskCat() == 2 ){
						BigDecimal perArticleSumInsured = new BigDecimal( decForm.format( coverDetailsVO.getSumInsured().getSumInsured() ) );

						if( personalArticleSumInsured.compareTo( perArticleSumInsured ) == -1 ){

							personalArticleSumInsured = new BigDecimal( decForm.format( coverDetailsVO.getSumInsured().getSumInsured() ) );
						}
					}
					else{
						personalSumInsured = new BigDecimal( decForm.format( coverDetailsVO.getSumInsured().getSumInsured() ) );
					}
				}
				
				// The fact value to be used for content discount rule
			  if(Utils.isEmpty( contentDiscount ))
				{
				  contentDiscount = getContentDiscount( decForm, coverDetailsVO );
				}
			 

			// The fact value to be used for personal possession discount rule
			  if(Utils.isEmpty( personalPossessionDiscount )){
				  personalPossessionDiscount = getPersonalPossessionDiscount( decForm,coverDetailsVO );
			  }
			}

			personalGreaterThanContent = getDifference( contentsSumInsured, personalSumInsured );			
			
			//if( homeInsuranceVO.getScheme().getSchemeCode().equals( SvcConstants.GARGASH_FIXED_SI_SCHEME ) ){
				//double contentsArticleCompareVal = SvcConstants.CONTENT_SI_MULTIPLIER * contentsSumInsured.doubleValue();
				/*
				 * 
				 * Commented by Himanish : 146276 
				 */
			/*	contentsArticleCompareVal = ( contentsArticleCompareVal < SvcConstants.CONTENTS_SI_COMPARE_VAL ) ? contentsArticleCompareVal : SvcConstants.CONTENTS_SI_COMPARE_VAL;
				String contentArticleExceedsLimit = new String("N");
				String personalArticleExceedsLimit = new String("N");*/
				
				/*
				 * 
				 * Commented by Himanish : 146276 
				 */
			/*	if( contentsArticleSumInsured.doubleValue() > contentsArticleCompareVal ){
					contentArticleExceedsLimit = new String("Y");
				}*/
				
				
				/*
				 * 
				 * Commented by Himanish : 146276 
				 */
				
/*				Characteristics contentArticleExceedsLimitCharacteristics = new Characteristics();
				contentArticleExceedsLimitCharacteristics.setName( "contentArticleExceedsLimit" );
				contentArticleExceedsLimitCharacteristics.setValue( contentArticleExceedsLimit );
				homeCharacteristicList.add( contentArticleExceedsLimitCharacteristics );*/
			//	String contentSumAndArticleSum=null;
				
				/*
				 * 
				 * Himanish : Rules Modification : 146276 
				 * 
				 */
				Characteristics businessDescCharacteristics = new Characteristics();
				businessDescCharacteristics.setName( "contentsSumInsured" );
				businessDescCharacteristics.setValue(String.valueOf(contentsSumInsured));
				homeCharacteristicList.add( businessDescCharacteristics );
				
				Characteristics businessDescCharacteristics2 = new Characteristics();
				businessDescCharacteristics2.setName( "homeContentsArticleSumInsured" );
				businessDescCharacteristics2.setValue(String.valueOf(contentsArticleSumInsured));
				homeCharacteristicList.add( businessDescCharacteristics2 );
				
				
				
				/*
				 * 
				 * Himanish : Rules Modification : 146276 
				 * 
				 */
				Characteristics personalArticleExceedsLimitCharacteristics = new Characteristics();
				personalArticleExceedsLimitCharacteristics.setName( "personalSumInsured" );
				personalArticleExceedsLimitCharacteristics.setValue(String.valueOf(personalSumInsured));
				homeCharacteristicList.add( personalArticleExceedsLimitCharacteristics );
				
				Characteristics personalArticleExceedsLimitCharacteristics2 = new Characteristics();
				personalArticleExceedsLimitCharacteristics2.setName( "homePersonalArticleSumInsured" );
				personalArticleExceedsLimitCharacteristics2.setValue(String.valueOf(personalArticleSumInsured));
				homeCharacteristicList.add( personalArticleExceedsLimitCharacteristics2 );
				
				
				
				
				/*
				 * 
				 * Commented by Himanish : 146276 
				 */
			/*	double personalArticleCompareVal = SvcConstants.PERSONAL_SI_MULTIPLIER * personalSumInsured.doubleValue();
				
				personalArticleCompareVal = ( personalArticleCompareVal < SvcConstants.PERSONAL_SI_COMPARE_VAL ) ? personalArticleCompareVal : SvcConstants.PERSONAL_SI_COMPARE_VAL;
				if( personalArticleSumInsured.doubleValue() > personalArticleCompareVal ){
					personalArticleExceedsLimit = new String("Y");
				}*/
				
			/*	Characteristics personalArticleExceedsLimitCharacteristics = new Characteristics();
				personalArticleExceedsLimitCharacteristics.setName( "personalArticleExceedsLimit" );
				personalArticleExceedsLimitCharacteristics.setValue( personalArticleExceedsLimit );
				homeCharacteristicList.add( personalArticleExceedsLimitCharacteristics );
			}*/
			
				
				
				/*
				 * 
				 * Commented by Himanish : 146276 
				 * 
				 */
			/*if( homeInsuranceVO.getScheme().getTariffCode().equals( SvcConstants.EMIRATES_HOME_TARIFF )
					|| homeInsuranceVO.getScheme().getTariffCode().equals( SvcConstants.GARGASH_TARIFF ) ){
				addTariffSIBasedRule( contentsSumInsured, personalSumInsured, homeCharacteristicList, SvcConstants.CONTENT_SI_MULTIPLIER );
			}
			else if( homeInsuranceVO.getScheme().getTariffCode().equals( SvcConstants.STANDARD_TARIFF ) ){
				addTariffSIBasedRule( contentsSumInsured, personalSumInsured, homeCharacteristicList, SvcConstants.oneVal );
			}*/
			
			if( !Utils.isEmpty( homeInsuranceVO.getUwQuestions() ) && !Utils.isEmpty( homeInsuranceVO.getUwQuestions().getQuestions() ) ){
				for( UWQuestionVO uwQuesVO : homeInsuranceVO.getUwQuestions().getQuestions() ){
					if( uwQuesVO.getQId() == SvcConstants.UW_GENERAL_QUESTION_CLAIM ){
						if( uwQuesVO.getResponseType().equals( UWQuestionRespType.TEXT ) ){
							generalQuestionClaim = Utils.getSingleValueAppConfig( com.Constant.CONST_UW_ANSWER_TEXT_RESPONSE_TYPE );
						}
						else if( uwQuesVO.getResponseType().equals( UWQuestionRespType.RADIO ) ){
							generalQuestionClaim = uwQuesVO.getResponse();
						}
					}
					if( uwQuesVO.getQId() == SvcConstants.UW_GENERAL_QUESTION_HOUSEHOLD ){
						if( uwQuesVO.getResponseType().equals( UWQuestionRespType.TEXT ) ){
							generalQuestionHousehold = Utils.getSingleValueAppConfig( com.Constant.CONST_UW_ANSWER_TEXT_RESPONSE_TYPE );
						}
						else if( uwQuesVO.getResponseType().equals( UWQuestionRespType.RADIO ) ){
							generalQuestionHousehold = uwQuesVO.getResponse();
						}
					}
					
					/* General question rule for ANA home scheme */
					//if( homeInsuranceVO.getScheme().getTariffCode().equals( SvcConstants.ANA_HOME_TARIFF ) ){
						if( uwQuesVO.getQId() == SvcConstants.UW_GENERAL_QUESTION_CONCRETE ){
							generalQuestionConcrete = new String( "yes" );
							if( uwQuesVO.getResponseType().equals( UWQuestionRespType.TEXT ) ){
								generalQuestionConcrete = Utils.getSingleValueAppConfig( com.Constant.CONST_UW_ANSWER_TEXT_RESPONSE_TYPE );
							}
							else if( uwQuesVO.getResponseType().equals( UWQuestionRespType.RADIO ) ){
								generalQuestionConcrete = uwQuesVO.getResponse();
							}
						}

						if( uwQuesVO.getQId() == SvcConstants.UW_GENERAL_QUESTION_TERMS ){
							generalQuestionTerms = new String( "no" );
							if( uwQuesVO.getResponseType().equals( UWQuestionRespType.TEXT ) ){
								generalQuestionTerms = Utils.getSingleValueAppConfig( com.Constant.CONST_UW_ANSWER_TEXT_RESPONSE_TYPE );
							}
							else if( uwQuesVO.getResponseType().equals( UWQuestionRespType.RADIO ) ){
								generalQuestionTerms = uwQuesVO.getResponse();
							}
						}
					//}
				}
			}

			if( !Utils.isEmpty( homeInsuranceVO.getEndorsmentVO() ) && !homeInsuranceVO.getCommonVO().getIsQuote() ){

				EndorsmentVO endtVO = homeInsuranceVO.getEndorsmentVO().get( SvcConstants.zeroVal );
				isPopulateOperation = homeInsuranceVO.isPopulateOperation();
				
				if(!isPopulateOperation){
					if( endtVO.getPayablePremium() > ZERO ){
						extraPremium = new BigDecimal( decForm.format( endtVO.getPayablePremium() ) );
						Characteristics extraPremiumCharacteristics = new Characteristics();
						extraPremiumCharacteristics.setName("extraPremium");
						extraPremiumCharacteristics.setValue( String.valueOf( extraPremium ) );
						homeCharacteristicList.add( extraPremiumCharacteristics );
					}
					else if( endtVO.getPayablePremium() < ZERO ){
	
						refundPremium = new String( "Y" );
	
						Characteristics refundPremiumCharacteristics = new Characteristics();
						refundPremiumCharacteristics.setName( "refundPremium" );
						refundPremiumCharacteristics.setValue( refundPremium );
						homeCharacteristicList.add( refundPremiumCharacteristics );
					}
					else{
	
						nilEndorsement = new String( "Y" );
	
						Characteristics nilEndorsementCharacteristics = new Characteristics();
						nilEndorsementCharacteristics.setName( "nilEndorsement" );
						nilEndorsementCharacteristics.setValue( nilEndorsement );
						homeCharacteristicList.add( nilEndorsementCharacteristics );
					}
				}
			}

			if( !Utils.isEmpty( homeInsuranceVO.getCommission() ) ){

				Short configuredCommission = null;

				if( !Utils.isEmpty( homeInsuranceVO.getScheme() ) && !Utils.isEmpty( homeInsuranceVO.getScheme().getSchemeCode() ) ){
					configuredCommission = SvcUtils.getLookUpCodeForLOneLTwo( "PAS_COMMISSION", String.valueOf( homeInsuranceVO.getScheme().getSchemeCode() ),
							SvcUtils.getKeyForCommisionCacheObj( homeInsuranceVO ) );
				}

				if( !Utils.isEmpty( configuredCommission ) ){
					Double diffCommission = homeInsuranceVO.getCommission() - configuredCommission;
					if( diffCommission != 0.0 ){
						commissionPerc = new String( "Y" );
					}
					else{
						commissionPerc = new String( "N" );
					}
				}
			}
			
			if( !Utils.isEmpty( homeInsuranceVO.getRenewals()) &&  !Utils.isEmpty(homeInsuranceVO.getRenewals().getEndorsmentList()) && homeInsuranceVO.getRenewals().getEndorsmentList().size() > 0 ) {
				
				endorseAfterRenewal = new String("Y");
				Characteristics endorsementCharacteristics = new Characteristics();
				endorsementCharacteristics.setName("endorsementAfterRenewalQuote");
				endorsementCharacteristics.setValue(endorseAfterRenewal);
				homeCharacteristicList.add(endorsementCharacteristics);
			}			
			
			
			if( !Utils.isEmpty( homeInsuranceVO.getRenewals() ) && homeInsuranceVO.getRenewals().getClaimCount() > 0 ){
				
				priorClaim = new String("Y");								
				Characteristics claimExistCharacteristics = new Characteristics();
				claimExistCharacteristics.setName( "priorYearClaimAfterRenewalQuote" );
				claimExistCharacteristics.setValue( priorClaim );
				homeCharacteristicList.add( claimExistCharacteristics );
			}
			
			if( !Utils.isEmpty( homeInsuranceVO.getRenewals() ) && homeInsuranceVO.getRenewals().getClaimCount() > 0 ){

				String claimCount = homeInsuranceVO.getRenewals().getClaimCount().toString();					
				Characteristics claimCountCharacteristics = new Characteristics();
				claimCountCharacteristics.setName( "claimCount" );
				claimCountCharacteristics.setValue( claimCount );
				homeCharacteristicList.add( claimCountCharacteristics );
			}
			
			if( !Utils.isEmpty( homeInsuranceVO.getRenewals() ) && homeInsuranceVO.getRenewals().getOsPremium() > 0 ){

				oSPremium = new String( "Y" );

				Characteristics oSPremiumCharacteristics = new Characteristics();
				oSPremiumCharacteristics.setName( "isPriorYearPremiumOutstanding" );
				oSPremiumCharacteristics.setValue( oSPremium );
				homeCharacteristicList.add( oSPremiumCharacteristics );				
			}
			
			/**
			 * Changes to display referral pop-up if domestic staff age is <18 years and >60 years.
			 */

			/* Commented by Sarath
			 * if(((UserProfile)homeInsuranceVO.getCommonVO().getLoggedInUser()).getRsaUser().getUserId()!=991 &&
					((UserProfile)homeInsuranceVO.getCommonVO().getLoggedInUser()).getRsaUser().getUserId()!=30959){*/
			if( !BusinessChannel.B2C.equals(homeInsuranceVO.getCommonVO().getChannel())){
				if( !Utils.isEmpty( homeInsuranceVO.getStaffDetails() ) && 
						!Utils.isEmpty( homeInsuranceVO.getStaffDetails().get(0).getEmpDob() ) ){
					BigDecimal domesticStaffMaxAge = null;
					BigDecimal domesticStaffMinAge = null;
					BigDecimal newAge;
					Date effictiveDate = homeInsuranceVO.getCommonVO().getPolEffectiveDate();

					for( StaffDetailsVO staffDetailsVO : homeInsuranceVO.getStaffDetails() ){
						newAge = SvcUtils.getAgeForHome( staffDetailsVO.getEmpDob(), effictiveDate );
						if( Utils.isEmpty( domesticStaffMaxAge ) && Utils.isEmpty( domesticStaffMinAge ) )
							domesticStaffMaxAge = domesticStaffMinAge = newAge;

						else if( newAge.compareTo( domesticStaffMaxAge ) == 1 )
							domesticStaffMaxAge = newAge;

						else if( newAge.compareTo( domesticStaffMaxAge ) == -1 ) domesticStaffMinAge = newAge;

					}
					domesticStaffMaxAge = domesticStaffMaxAge.setScale( 4, BigDecimal.ROUND_CEILING );
					Characteristics domesticStaffMaxAgeCharacteristics = new Characteristics();
					domesticStaffMaxAgeCharacteristics.setName( "domesticStaffMaxAge" );
					domesticStaffMaxAgeCharacteristics.setValue( String.valueOf( domesticStaffMaxAge ) );
					homeCharacteristicList.add( domesticStaffMaxAgeCharacteristics );
					domesticStaffMinAge = domesticStaffMinAge.setScale( 4, BigDecimal.ROUND_CEILING );
					Characteristics domesticStaffMinAgeCharacteristics = new Characteristics();
					domesticStaffMinAgeCharacteristics.setName( "domesticStaffMinAge" );
					domesticStaffMinAgeCharacteristics.setValue( String.valueOf( domesticStaffMinAge ) );
					homeCharacteristicList.add( domesticStaffMinAgeCharacteristics );
				}
			}
		//	}
			
			/* Called only on save quote after the premium is available for all covers */
			if( !isPopulateOperation ){
				
				discountPerc = getPolicyLevelDiscount( decForm,homeInsuranceVO );
				
				/*Integer licensedBy = null;
				
				if( !Utils.isEmpty( homeInsuranceVO ) && !Utils.isEmpty( homeInsuranceVO.getAuthenticationInfoVO() ) ){
					licensedBy = homeInsuranceVO.getAuthenticationInfoVO().getLicensedBy();
				}
				 Filter the rule if the licensed by selected is the CREDIT_MANAGER role 
				if( !SvcUtils.checkCreditLimitRule( licensedBy ) ){*/
				/*if( !Utils.isEmpty( (UserProfile) homeInsuranceVO.getLoggedInUser() ) && 
						!Utils.isEmpty( ( (UserProfile) homeInsuranceVO.getLoggedInUser() ).getRsaUser() ) && 
								( (UserProfile) homeInsuranceVO.getLoggedInUser() ).getRsaUser().getUserId() != 991 && 
						( (UserProfile) homeInsuranceVO.getLoggedInUser()).getRsaUser().getUserId() != 30959 ){*/
				if( !BusinessChannel.B2C.equals(homeInsuranceVO.getCommonVO().getChannel())){	
					setBrokerCreditLimit( decForm, homeInsuranceVO, riskDetailList, roleType );
				}
				//}
			}
			homeInsuranceVO.setReferralVOList( null ); // This is explicitly set to null because when save quote is clicked referral dtails need not be saved it has to be saved only on yes of referral popup
		}

		/* Create user fact */
		Fact userFact = new Fact();
		userFact.setFactName( RulesConstants.FACT_USER );

		List<Characteristics> userCharacteristicList = userFact.getCharacteristics();

		Characteristics userCharacteristics = new Characteristics();
		userCharacteristics.setName( "role" );
		userCharacteristics.setValue( roleType );
		userCharacteristicList.add( userCharacteristics );

		Characteristics buildingSumInsuredCharacteristics = new Characteristics();
		buildingSumInsuredCharacteristics.setName( "buildingSumInsured" );
		buildingSumInsuredCharacteristics.setValue( String.valueOf( buildingSumInsured ) );

		homeCharacteristicList.add( buildingSumInsuredCharacteristics );
		
		
		
		
		/*
		 * 
		 * Commented by Himanish
		 * 
		 */
		/*Characteristics contentsSumInsuredCharacteristics = new Characteristics();
		contentsSumInsuredCharacteristics.setName( "contentsSumInsured" );
		contentsSumInsuredCharacteristics.setValue( String.valueOf( contentsSumInsured ) );

		homeCharacteristicList.add( contentsSumInsuredCharacteristics );

		Characteristics personalSumInsuredCharacteristics = new Characteristics();
		personalSumInsuredCharacteristics.setName( "personalSumInsured" );

		personalSumInsuredCharacteristics.setValue( String.valueOf( personalSumInsured ) );

		homeCharacteristicList.add( personalSumInsuredCharacteristics );

		*/
		
	
		/*Characteristics contentsArticleSumInsuredCharacteristics = new Characteristics();
		contentsArticleSumInsuredCharacteristics.setName( "contentsArticleSumInsured" );

		contentsArticleSumInsuredCharacteristics.setValue( String.valueOf( contentsArticleSumInsured ) );

		homeCharacteristicList.add( contentsArticleSumInsuredCharacteristics );*/

		

	/*	
		Characteristics personalArticleSumInsuredCharacteristics = new Characteristics();
		personalArticleSumInsuredCharacteristics.setName( "personalArticleSumInsured" );
		personalArticleSumInsuredCharacteristics.setValue( String.valueOf( personalArticleSumInsured ) );
		homeCharacteristicList.add( personalArticleSumInsuredCharacteristics );*/

		
		
		/*
		 * 
		 * Himanish : Rules Modification : 146276 
		 * 
		 */
/*		Characteristics businessDescCharacteristics = new Characteristics();
		
		businessDescCharacteristics.setName( "contentsSumInsured" );
		businessDescCharacteristics.setValue(String.valueOf(contentsSumInsured));
		businessDescCharacteristics.setName( "contentsArticleSumInsured" );
		businessDescCharacteristics.setValue(String.valueOf(contentsArticleSumInsured));
		homeCharacteristicList.add( businessDescCharacteristics );*/
		
		
		
		/*
		 * 
		 * Himanish : Rules Modification : 146276 
		 * 
		 */
	/*	Characteristics personalArticleExceedsLimitCharacteristics = new Characteristics();
	
		personalArticleExceedsLimitCharacteristics.setName( "personalSumInsured" );
		personalArticleExceedsLimitCharacteristics.setValue(String.valueOf(personalSumInsured));
		personalArticleExceedsLimitCharacteristics.setName( "personalArticleSumInsured" );
		personalArticleExceedsLimitCharacteristics.setValue(String.valueOf(personalArticleSumInsured));
		homeCharacteristicList.add( personalArticleExceedsLimitCharacteristics );*/
		
		
		Characteristics personalGreaterThanContentCharacteristics = new Characteristics();
		personalGreaterThanContentCharacteristics.setName( "personalGreaterThanContent" );
		personalGreaterThanContentCharacteristics.setValue( String.valueOf( personalGreaterThanContent ) );
		homeCharacteristicList.add( personalGreaterThanContentCharacteristics );

		Characteristics generalQuestionClaimCharacteristics = new Characteristics();
		generalQuestionClaimCharacteristics.setName( "generalQuestionClaim" );
		generalQuestionClaimCharacteristics.setValue( generalQuestionClaim );
		homeCharacteristicList.add( generalQuestionClaimCharacteristics );

		Characteristics generalQuestionHouseholdCharacteristics = new Characteristics();
		generalQuestionHouseholdCharacteristics.setName( "generalQuestionHousehold" );
		generalQuestionHouseholdCharacteristics.setValue( generalQuestionHousehold );
		homeCharacteristicList.add( generalQuestionHouseholdCharacteristics );

		//discountPerc = mergeCoverLevelDiscount(buildingDiscount,contentDiscount,personalPossessionDiscount,discountPercentage);
		
		Characteristics discountPercCharacteristics = new Characteristics();
		discountPercCharacteristics.setName( "discountPercentage" );
		discountPercCharacteristics.setValue( discountPerc );
		homeCharacteristicList.add( discountPercCharacteristics );

		Characteristics commissionPercCharacteristics = new Characteristics();
		commissionPercCharacteristics.setName( "commissionPercentage" );
		commissionPercCharacteristics.setValue( commissionPerc );
		homeCharacteristicList.add( commissionPercCharacteristics );
		
		if( Utils.getSingleValueAppConfig( SvcConstants.RULE_DEPLOYED_LOC ).equals( "50" ) && !Utils.isEmpty( homeInsuranceVO ) && !Utils.isEmpty( homeInsuranceVO.getBuildingDetails() ) && !Utils.isEmpty( homeInsuranceVO.getBuildingDetails().getGeoAreaCode() ) ){
			Characteristics areaCharacteristics = new Characteristics();
			areaCharacteristics.setName( "riskArea" );
			areaCharacteristics.setValue( homeInsuranceVO.getBuildingDetails().getGeoAreaCode().toString() );
			homeCharacteristicList.add( areaCharacteristics );
		}
		//Create fact if the values are present
		if( !Utils.isEmpty( buildingDiscount ) && buildingDiscount.compareTo( BigDecimal.ZERO ) != 0){
			Characteristics buildingDiscountCharacteristics = new Characteristics();
			buildingDiscountCharacteristics.setName( "buildingDiscount" );
			buildingDiscountCharacteristics.setValue( String.valueOf( buildingDiscount ) );
			homeCharacteristicList.add( buildingDiscountCharacteristics );
		}

		//Create fact if the values are present
		if( !Utils.isEmpty( contentDiscount ) && contentDiscount.compareTo( BigDecimal.ZERO ) != 0){
			Characteristics contentDiscountCharacteristics = new Characteristics();
			contentDiscountCharacteristics.setName( "contentDiscount" );
			contentDiscountCharacteristics.setValue( String.valueOf( contentDiscount ) );
			homeCharacteristicList.add( contentDiscountCharacteristics );
		}
		
		//Create fact if the values are present
		if( !Utils.isEmpty( personalPossessionDiscount ) && personalPossessionDiscount.compareTo( BigDecimal.ZERO ) != 0){
			Characteristics personalPossessionDiscountCharacteristics = new Characteristics();
			personalPossessionDiscountCharacteristics.setName( "personalPossessionDiscount" );
			personalPossessionDiscountCharacteristics.setValue( String.valueOf( personalPossessionDiscount ) );
			homeCharacteristicList.add( personalPossessionDiscountCharacteristics );
		}
		
		if( !Utils.isEmpty( generalQuestionConcrete ) ){
			Characteristics generalQuestionConcreteCharacteristics = new Characteristics();
			generalQuestionConcreteCharacteristics.setName( "generalQuestionConcrete" );
			generalQuestionConcreteCharacteristics.setValue( generalQuestionConcrete );
			homeCharacteristicList.add( generalQuestionConcreteCharacteristics );
		}

		if( !Utils.isEmpty( generalQuestionTerms ) ){
			Characteristics generalQuestionTermsCharacteristics = new Characteristics();
			generalQuestionTermsCharacteristics.setName( "generalQuestionTerms" );
			generalQuestionTermsCharacteristics.setValue( generalQuestionTerms );
			homeCharacteristicList.add( generalQuestionTermsCharacteristics );
		}

		/*if( Utils.getSingleValueAppConfig( SvcConstants.RULE_DEPLOYED_LOC ).equals( "50" ) && !isPopulateOperation  && homeInsuranceVO.getCommonVO().getIsQuote()){
			if(!Utils.isEmpty(((UserProfile)homeInsuranceVO.getCommonVO().getLoggedInUser()).getRsaUser().getBrokerId())) {
				String brCode = ((UserProfile)homeInsuranceVO.getCommonVO().getLoggedInUser()).getRsaUser().getBrokerId().toString() +"-"+homeInsuranceVO.getCommonVO().getEndtId().toString();
				Characteristics generalQuestionTermsCharacteristics = new Characteristics();
				generalQuestionTermsCharacteristics.setName( "brokerCode" );
				generalQuestionTermsCharacteristics.setValue(String.valueOf( brCode ) );
				homeCharacteristicList.add( generalQuestionTermsCharacteristics );
			}
		}*/
		
		
		// Request id 120343 Location Bahrain to all discount for specific broker user
		if( Utils.getSingleValueAppConfig( SvcConstants.RULE_DEPLOYED_LOC ).equals( "50" ) && homeInsuranceVO.getCommonVO().getIsQuote()){
            
            String brCode = "0";
            
            if(!Utils.isEmpty(((UserProfile)homeInsuranceVO.getCommonVO().getLoggedInUser()).getRsaUser().getBrokerId())) {
                            brCode = ((UserProfile)homeInsuranceVO.getCommonVO().getLoggedInUser()).getRsaUser().getBrokerId().toString();                 
            }                                              
            Characteristics generalQuestionTermsCharacteristics = new Characteristics();
            generalQuestionTermsCharacteristics.setName( "brokerCode" );
            generalQuestionTermsCharacteristics.setValue(String.valueOf( brCode ) );
            homeCharacteristicList.add( generalQuestionTermsCharacteristics );
		}



		factList.add( userFact );
		factList.add( homeFact );
		riskDetailList.add( riskDetails );

		
		return ruleInfo;
	}

	/**
	 * Method to get the policy level discount
	 * 
	 * @param homeInsuranceVO
	 * @param actualPremium2 
	 * @return
	 */
	private String getPolicyLevelDiscount( DecimalFormat decForm, HomeInsuranceVO homeInsuranceVO ){
		String discountPerc = new String("0.0");
		ReferralListVO referralListVO = homeInsuranceVO.getReferralVOList();
		
		if(!Utils.isEmpty( referralListVO )){
			PremiumVO premiumVO = referralListVO.getPremiumVO();
			
			double payablePremium = premiumVO.getPremiumAmt();//contains payable premium
			double actualPremium = premiumVO.getPremiumAmtActual(); // contains total premium
			double discount = 0.0;
			
			//if(payablePremium < actualPremium)
			if( (payablePremium < actualPremium) || 
					( payablePremium > actualPremium && "Broker".equalsIgnoreCase( ((UserProfile)homeInsuranceVO.getCommonVO().getLoggedInUser()).getRsaUser().getProfile() ) ) ){

				/* Changes to make discount and loading percentage as 5% for brokers (handled through rules).
				 * As the change is only for brokers, loading check is not to be done for RSA users.
				 * */
				discount = Math.abs( 100 * (1- payablePremium/actualPremium) );
				if(!Utils.isEmpty( homeInsuranceVO.getPremiumVO().getDiscOrLoadPerc())){
					discount =  Math.abs(homeInsuranceVO.getPremiumVO().getDiscOrLoadPerc());
				}
				
				discountPerc = String.valueOf( new BigDecimal( decForm.format(discount ) ));
				//System.out.println("Vishwa######## discountPerc "+discountPerc);
			}
			
		}
		
		return discountPerc;
	}

	/**
	 * Method to merge the discount level
	 * 
	 * @param buildingDiscount
	 * @param contentDiscount
	 * @param personalPossessionDiscount
	 * @param discountPercentage
	 * @param discountPerc 
	 */
	/*private String mergeCoverLevelDiscount( BigDecimal buildingDiscount, BigDecimal contentDiscount, BigDecimal personalPossessionDiscount, BigDecimal discountPercentage ){
		String discountPerc = new String("0.00");
		if(!Utils.isEmpty( discountPercentage )){
			if(!Utils.isEmpty( buildingDiscount )  && buildingDiscount.compareTo( BigDecimal.ZERO ) != 0){
				discountPercentage = discountPercentage.add( buildingDiscount );
			}
			
			if(!Utils.isEmpty( contentDiscount )  && contentDiscount.compareTo( BigDecimal.ZERO ) != 0){
				discountPercentage = discountPercentage.add( contentDiscount );
			}
			
			if(!Utils.isEmpty( personalPossessionDiscount )  && personalPossessionDiscount.compareTo( BigDecimal.ZERO ) != 0){
				discountPercentage = discountPercentage.add( personalPossessionDiscount );
			}
			
			if(discountPercentage.compareTo( new BigDecimal( 0.0 )) < 0 ){
				discountPercentage = discountPercentage.abs();
				discountPerc = String.valueOf( discountPercentage );
			}
		}
		
		return discountPerc;
		
	}*/

	private BigDecimal getPersonalPossessionDiscount( DecimalFormat decForm, CoverDetailsVO coverDetailsVO ){
		BigDecimal personalPossessionDiscount = null;
		if( !Utils.isEmpty( coverDetailsVO.getRiskCodes().getRiskType() ) ){
			if( ( ( SvcConstants.PP_RSK_TYPE_CODE ) ).equals( coverDetailsVO.getRiskCodes().getRiskType() )
					&& ( SvcConstants.PP_MAIN_RISK_CATEGORY.equals( coverDetailsVO.getRiskCodes().getRiskCat() ) ) ){
				if( !Utils.isEmpty( coverDetailsVO.getDiscOrLoadPerc() ) && coverDetailsVO.getDiscOrLoadPerc().compareTo( 0.0 ) < 0 ){
					personalPossessionDiscount = new BigDecimal( decForm.format( coverDetailsVO.getDiscOrLoadPerc() ) ).abs();
				}
			}
		}
		return personalPossessionDiscount;
	}
	
	/**
	 * @param decForm
	 * @param coverDetailsVO
	 * @return 
	 */
	private BigDecimal getContentDiscount( DecimalFormat decForm, CoverDetailsVO coverDetailsVO ){
		BigDecimal contentDiscount = null;
		if( !Utils.isEmpty( coverDetailsVO.getRiskCodes().getRiskType() ) && ( coverDetailsVO.getCoverCodes().getCovCode() != 0 ) ){
			if( ( ( SvcConstants.CONTENT_RISK_TYPE_CODE ) ).equals( coverDetailsVO.getRiskCodes().getRiskType() )
					&& ( SvcConstants.DEFAULT_HOME_COVER_CODE == coverDetailsVO.getCoverCodes().getCovCode() )
					&& ( SvcConstants.CONTENT_MAIN_RISK_CATEGORY.equals( coverDetailsVO.getRiskCodes().getRiskCat() ) ) ){
				if( !Utils.isEmpty( coverDetailsVO.getDiscOrLoadPerc() ) && coverDetailsVO.getDiscOrLoadPerc().compareTo( 0.0 ) < 0 ){
					contentDiscount = new BigDecimal( decForm.format( coverDetailsVO.getDiscOrLoadPerc() ) ).abs();
				}
			}
		}
		return contentDiscount;
	}
	
	/**
	 * @param contentsSumInsured
	 * @param personalSumInsured
	 * @param homeCharacteristicList
	 */
	private void addTariffSIBasedRule( BigDecimal contentsSumInsured, BigDecimal personalSumInsured, List<Characteristics> homeCharacteristicList, double multiplier ){
		String personalExceedsLimit = new String( "N" );
		if( personalSumInsured.doubleValue() > ( multiplier * contentsSumInsured.doubleValue() ) ){
			personalExceedsLimit = new String( "Y" );
		}
		Characteristics personalExceedsLimitCharacteristics = new Characteristics();
		personalExceedsLimitCharacteristics.setName( "personalExceedsLimit" );
		personalExceedsLimitCharacteristics.setValue( personalExceedsLimit );
		homeCharacteristicList.add( personalExceedsLimitCharacteristics );
	}

	/**
	 * @param homeInsuranceVO
	 * populates values which are not present in HomeInsuranceVO from DB.
	 */
	private void getRequiredDetails( HomeInsuranceVO homeInsuranceVO ){
		AmendPolicySvc amendPolicySvc;
		SchemeVO schemeVO = null;
		if( homeInsuranceVO.getCommonVO().getIsQuote() ){
			amendPolicySvc = (AmendPolicySvc) Utils.getBean( "amendPolicySvc" );
		}
		else{
			amendPolicySvc = (AmendPolicySvc) Utils.getBean( "amendPolicySvc_POL" );
		}
		if(!Utils.isEmpty( homeInsuranceVO.getCommonVO().getQuoteNo() )){
			PolicyDataVO policyDataVO = (PolicyDataVO) amendPolicySvc.invokeMethod( "retrievePolicyDataVO", homeInsuranceVO.getCommonVO() );
			if( !Utils.isEmpty( homeInsuranceVO.getScheme() )){
				schemeVO = homeInsuranceVO.getScheme();
			}else{
				schemeVO = new SchemeVO();
			}
			schemeVO.setSchemeCode( policyDataVO.getScheme().getSchemeCode() );
			schemeVO.setTariffCode( policyDataVO.getScheme().getTariffCode() );
			homeInsuranceVO.setScheme( schemeVO );
		}
		
		
	}

	private BigDecimal getDifference( BigDecimal contentsSumInsured, BigDecimal personalSumInsured ){
		return ( personalSumInsured.subtract( contentsSumInsured ) );
	}
	
	/**
	 * Method to get the the broker credit limit
	 * 
	 * @param decForm
	 * @param homeInsuranceVO
	 */
	private void setBrokerCreditLimit( DecimalFormat decForm, HomeInsuranceVO homeInsuranceVO,List<RiskDetails> riskDetails,String roleType ){
		double premium = 0;
		AmendPolicySvc amendPolicySvc;
		
		double brokerCreditLimit = 0;
		boolean callCreditRule = true;
		if( homeInsuranceVO.getCommonVO().getIsQuote() ){
			amendPolicySvc = (AmendPolicySvc) Utils.getBean( "amendPolicySvc" );
		}
		else{
			amendPolicySvc = (AmendPolicySvc) Utils.getBean( "amendPolicySvc_POL" );
		}
		
		PolicyDataVO policyDataVO = (PolicyDataVO) amendPolicySvc.invokeMethod( "retrievePolicyDataVO", homeInsuranceVO.getCommonVO() );
		
		Integer licensedBy = null;
		
		if( !Utils.isEmpty( policyDataVO ) && !Utils.isEmpty( policyDataVO.getAuthenticationInfoVO() ) ){
			licensedBy = policyDataVO.getAuthenticationInfoVO().getLicensedBy();
		}
		
		
		if( SvcUtils.checkCreditLimitRule( licensedBy ) && homeInsuranceVO.getCommonVO().getAppFlow().equals( Flow.RESOLVE_REFERAL )
				&& !Utils.isEmpty( homeInsuranceVO.getCommonVO().getTaskDetails() ) && homeInsuranceVO.getCommonVO().getTaskDetails().getStatus().equals( String.valueOf( 1 ) ) ){
			callCreditRule = false;
		}
		/*Filter the rule if the licensed by selected is the CREDIT_MANAGER role*/ 
		if(callCreditRule){
		
			RiskDetails riskDetailGen = new RiskDetails();
			riskDetailGen.setRiskName( "GEN" );
	
			riskDetailGen.setRiskId( new Integer( 1001 ) );
			List<Fact> factList = riskDetailGen.getFact();
			
			/* Create user fact */
			Fact userFact = new Fact();
			userFact.setFactName( RulesConstants.FACT_USER );
	
			List<Characteristics> userCharacteristicList = userFact.getCharacteristics();
	
			Characteristics userCharacteristics = new Characteristics();
			userCharacteristics.setName( "role" );
			userCharacteristics.setValue( roleType );
			userCharacteristicList.add( userCharacteristics );
			
			/*Create general info fact */
			Fact genFact = new Fact();
			genFact.setFactName( RulesConstants.FACT_VALIDATION );
	
			List<Characteristics> genCharacteristicList = genFact.getCharacteristics();
			
			
			/*
			 * If it is endorsement pick the premium endorsement else pick the premium from policy data
			 */
			if( ( !Utils.isEmpty( homeInsuranceVO.getEndorsmentVO() ) && !homeInsuranceVO.getCommonVO().getIsQuote() ) ){
				EndorsmentVO endtVO = homeInsuranceVO.getEndorsmentVO().get( SvcConstants.zeroVal );
	
				if( endtVO.getPayablePremium() != 0 && endtVO.getPayablePremium() > 0 ){
					premium = endtVO.getPayablePremium();
				}
			}
			else if( !Utils.isEmpty( homeInsuranceVO.getCommonVO() ) && homeInsuranceVO.getCommonVO().getAppFlow().equals(Flow.RESOLVE_REFERAL)  
					&& !Utils.isEmpty( homeInsuranceVO.getCommonVO().getTaskDetails() ) && homeInsuranceVO.getCommonVO().getTaskDetails().getStatus().equals(String.valueOf(1)) ){
				PremiumVO premiumVO = homeInsuranceVO.getPremiumVO();
	
				if( !Utils.isEmpty( premiumVO ) ){
					premium = premiumVO.getPremiumAmt();
				}
			}
					
			
			
			GeneralInfoVO generalInfoVO = null;
			
			if(!Utils.isEmpty( policyDataVO )) {
				generalInfoVO = policyDataVO.getGeneralInfo();
			}
	
			if( !Utils.isEmpty( generalInfoVO ) ){
				SourceOfBusinessVO sourceOfBusinessVO = generalInfoVO.getSourceOfBus();
	
				if( !Utils.isEmpty( sourceOfBusinessVO ) ){
					Integer brokerName = sourceOfBusinessVO.getBrokerName();
	
					if( !Utils.isEmpty( brokerName ) && premium > 0){
						brokerCreditLimit = SvcUtils.getBrokerCreditLimitPercentage( premium, brokerName );
					}
				}
			}
				
			
				
			BigDecimal brokerLimit =  new BigDecimal(Currency.getFormattedCurrency( brokerCreditLimit ) ) ;
			
			if(brokerLimit.compareTo( new BigDecimal( 100 ) ) == -1  || brokerLimit.compareTo( new BigDecimal( 100 ) ) == 0){
				Characteristics brokerMinCreditLimit = new Characteristics();
				brokerMinCreditLimit.setName( "brokerMinCreditLimit" );
				brokerMinCreditLimit.setValue( String.valueOf(brokerLimit) );
				
				genCharacteristicList.add( brokerMinCreditLimit );
			}
			
			Characteristics brokerMaxCreditLimit = new Characteristics();
			brokerMaxCreditLimit.setName( "brokerMaxCreditLimit" );
			brokerMaxCreditLimit.setValue( String.valueOf(brokerLimit) );
			genCharacteristicList.add( brokerMaxCreditLimit );
			
			factList.add( genFact );
			factList.add( userFact );
			riskDetails.add(riskDetailGen);
		}
		
	}

}
