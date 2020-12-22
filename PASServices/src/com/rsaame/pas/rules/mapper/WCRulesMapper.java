package com.rsaame.pas.rules.mapper;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.endorse.svc.ClaimsSvc;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.app.LookUpListVO;
import com.rsaame.pas.vo.bus.EmpTypeDetailsVO;
import com.rsaame.pas.vo.bus.EndorsmentVO;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.RenewalVO;
import com.rsaame.pas.vo.bus.WorkmenCompVO;
import com.rsame.rulesengine.restfulclient.request.Characteristics;
import com.rsame.rulesengine.restfulclient.request.Fact;
import com.rsame.rulesengine.restfulclient.request.RiskDetails;
import com.rsame.rulesengine.restfulclient.request.RuleInfo;

/**
 * This class is used for mapping the WC values to the WC rules related facts.
 * @author M1024781
 *
 */
public class WCRulesMapper implements RuleMapper{
	
	private final static Logger logger = Logger.getLogger( WCRulesMapper.class );
	DecimalFormat decForm = new DecimalFormat( RulesConstants.DECIMAL_FORMAT );
	private static final int DAYDIVIDER = ( 1000 * 60 * 60 * 24 );
	private static final double ZERO = 0.0;

	/**
	 * This method is used to map the WC values to the WC rules fact and give back the response to rules service invoker.
	 * @param baseVO
	 * @param section
	 * @param roleType
	 * @param ruleInfo
	 * @return RuleInfo
	 */
	public RuleInfo createRiskDetails(BaseVO policyBaseVO, int section,
			String roleType, RuleInfo ruleInfo) {

		String empTypeBlackListed = "N";
		int noOfEmp = 0;
		String liabPerLocStr = null;
		BigDecimal liabPerLoc = new BigDecimal(0.00);
		BigDecimal liabPerPolicy = new BigDecimal(0.00);
		double commissionDiff = 0.0;
		String discountPerc = new String( "0.0" );
		BigDecimal discountPercentage;
		WorkmenCompVO workmenCompVO = null;
		Boolean isTotalLiabilitySet = false;
		
		if (Utils.isEmpty(ruleInfo)) {
			ruleInfo = new RuleInfo();
		}
		List<RiskDetails> riskDetailList = ruleInfo.getRiskDetails();

		RiskDetails riskDetails = new RiskDetails();
		riskDetails.setRiskName(RulesConstants.WORK_COMP);

		

		if (!Utils.isEmpty(policyBaseVO)) 
		{
			if(policyBaseVO instanceof WorkmenCompVO){
				workmenCompVO = (WorkmenCompVO) policyBaseVO;
			}
			else if(policyBaseVO instanceof PolicyDataVO){
				PolicyDataVO policyDataVO = (PolicyDataVO) policyBaseVO;
				workmenCompVO = new WorkmenCompVO();
				workmenCompVO.setCommonVO(policyDataVO.getCommonVO());
				workmenCompVO.setAuthenticationInfoVO(policyDataVO.getAuthenticationInfoVO());
				workmenCompVO.setEndorsmentVO(policyDataVO.getEndorsmentVO());
				workmenCompVO.setGeneralInfo(policyDataVO.getGeneralInfo());
				workmenCompVO.setPolicyClassCode(policyDataVO.getPolicyClassCode());
				workmenCompVO.setPolicyType(policyDataVO.getPolicyType());
				workmenCompVO.setPremiumVO(policyDataVO.getPremiumVO());
				workmenCompVO.setScheme(policyDataVO.getScheme());
				workmenCompVO.setReferralVO(policyDataVO.getReferralVO());
				workmenCompVO.setRenewals(policyDataVO.getRenewals());
			}
			if(!Utils.isEmpty(workmenCompVO)){		/* Added null check '!Utils.isEmpty(workmenCompVO) && ' - sonar violation fix */
				if(!Utils.isEmpty( workmenCompVO.getCommonVO() ) && workmenCompVO.getCommonVO().getIsQuote())	
				{
					riskDetails.setRiskId(workmenCompVO.getCommonVO().getQuoteNo().intValue());
					logger.debug("RiskID ----: "+workmenCompVO.getCommonVO().getQuoteNo().intValue());
				}
				else
				{
					riskDetails.setRiskId(workmenCompVO.getCommonVO().getPolicyNo().intValue());		
					logger.debug("RiskID ----: "+workmenCompVO.getCommonVO().getPolicyNo().intValue());
				}
			}
			List<Fact> factList = riskDetails.getFact();
			
			/* Create user fact */
			Fact userFact = new Fact();
			userFact.setFactName(RulesConstants.FACT_USER);

			List<Characteristics> userCharacteristicList = userFact.getCharacteristics();

			Characteristics userCharacteristics = new Characteristics();
			userCharacteristics.setName("role");
			userCharacteristics.setValue(roleType);
			userCharacteristicList.add(userCharacteristics);
			factList.add(userFact);
			
			/*Added try and catch block to avoid null pointer , sonar violation fix on 9-10-2017*/
			try{
					
			if (!Utils.isEmpty(workmenCompVO.getEmpTypeDetails())) 
			{
				List<EmpTypeDetailsVO> empTypeDetsList = workmenCompVO
						.getEmpTypeDetails();
				double configuredCommission = 0.0;
				if( !Utils.isEmpty( workmenCompVO.getScheme() ) && !Utils.isEmpty( workmenCompVO.getScheme().getSchemeCode() ) ){
					LookUpListVO lookupvoList = SvcUtils.getLookUpCodesList("PAS_COMMISSION",  workmenCompVO.getScheme().getSchemeCode().toString(),
							SvcUtils.getKeyForCommisionCacheObj( workmenCompVO ));
					if(!Utils.isEmpty(lookupvoList.getLookUpList()))
					configuredCommission = lookupvoList.getLookUpList().get( 0 ).getCode().doubleValue();
				}
				if(!Utils.isEmpty(workmenCompVO.getCommission()))
				commissionDiff = workmenCompVO.getCommission()-configuredCommission;
				logger.debug("commissionDiff is ----: "+commissionDiff);
				for (EmpTypeDetailsVO empTypeDetailsVO : empTypeDetsList) 
				{
					if (!Utils.isEmpty(empTypeDetailsVO.getEmpType())) 
					{
						noOfEmp += Utils.isEmpty(empTypeDetailsVO.getNoOfEmp()) ? SvcConstants.zeroVal
								: empTypeDetailsVO.getNoOfEmp();
						if(! isTotalLiabilitySet){
							liabPerLocStr = SvcUtils.getLookUpDescription("WC_LIMIT",workmenCompVO.getScheme().getTariffCode().toString(),RulesConstants.LOOKUP_LEVEL2, empTypeDetailsVO.getLimit().intValue());
							liabPerLoc = new BigDecimal(liabPerLocStr);
							liabPerPolicy = new BigDecimal(liabPerLocStr);
							isTotalLiabilitySet = true;
						}
					}
				}
				logger.debug("noOfEmp for employee is ----: "+noOfEmp);

				/* Create WC fact */
				Fact wcFact = new Fact();
				wcFact.setFactName(RulesConstants.FACT_WC);
				List<Characteristics> wcRuleCharacteristicList = wcFact.getCharacteristics();

				Characteristics liabPerLocCharacteristics = new Characteristics();
				liabPerLocCharacteristics.setName("liabilityPerLocation");
				liabPerLocCharacteristics.setValue(new BigDecimal(decForm.format(liabPerLoc)).toString());
				wcRuleCharacteristicList.add(liabPerLocCharacteristics);
				
				Characteristics liabPerPolicyCharacteristics = new Characteristics();
				liabPerPolicyCharacteristics.setName("liabilityPerPolicy");
				liabPerPolicyCharacteristics.setValue(new BigDecimal(decForm.format(liabPerPolicy)).toString());
				wcRuleCharacteristicList.add(liabPerPolicyCharacteristics);

				Characteristics noOfEmployeeCharacteristics = new Characteristics();
				noOfEmployeeCharacteristics.setName("numberOfEmployee");
				noOfEmployeeCharacteristics.setValue(Integer.valueOf( noOfEmp ).toString());
				wcRuleCharacteristicList.add(noOfEmployeeCharacteristics);

				Characteristics empTypeCharacteristics = new Characteristics();
				empTypeCharacteristics.setName("empTypeBlacklisted");
				empTypeCharacteristics.setValue(empTypeBlackListed);
				wcRuleCharacteristicList.add(empTypeCharacteristics);
				
				if( !Utils.isEmpty( workmenCompVO.getPremiumVO() ) && !Utils.isEmpty( workmenCompVO.getPremiumVO().getDiscOrLoadPerc() ) ){

					/* Get the discount */
					if( workmenCompVO.getPremiumVO().getDiscOrLoadPerc() < 0 ){
						discountPercentage = new BigDecimal( decForm.format( workmenCompVO.getPremiumVO().getDiscOrLoadPerc() ) );
						discountPerc = String.valueOf( discountPercentage.multiply( new BigDecimal( -1 ) ) );
					}

					Characteristics discountPercCharacteristics = new Characteristics();
					discountPercCharacteristics.setName( "discountPercentage" );
					discountPercCharacteristics.setValue( discountPerc );
					wcRuleCharacteristicList.add( discountPercCharacteristics );
				}
				
				BigDecimal quotePremium = new BigDecimal( 0);
				
				Characteristics quotePremiumCharacteristics = new Characteristics();
				quotePremiumCharacteristics.setName( "quotePremium" );
				/* In case of convert to policy the Premium Amount from policyData.PremiumVO.premiumAmt will be available in session which is set in endorsementVO.*/
				if( !Utils.isEmpty( workmenCompVO ) && !Utils.isEmpty( workmenCompVO.getEndorsmentVO() ) ){
					quotePremium = new BigDecimal(String.valueOf( workmenCompVO.getEndorsmentVO().get( SvcConstants.zeroVal ).getPayablePremium() ));
				}
				quotePremiumCharacteristics.setValue( new BigDecimal(decForm.format(quotePremium)).toString() );
				wcRuleCharacteristicList.add( quotePremiumCharacteristics );
				Calendar cal = Calendar.getInstance();
				cal.set( Calendar.HOUR_OF_DAY, 0 );
				cal.set( Calendar.MINUTE, 0 );
				cal.set( Calendar.SECOND, 0 );
				cal.set( Calendar.MILLISECOND, 0 );

				Date today = cal.getTime();
				Date endEffectiveDate = workmenCompVO.getCommonVO().getEndtEffectiveDate();
				long endorsementDateBackdating = 0;
				long endorsementDatePostdating = 0;
				
				if( !Utils.isEmpty( endEffectiveDate ) ){
					if( today.after( endEffectiveDate ) )
						endorsementDateBackdating = getDateDifference(today, endEffectiveDate);
					else
						endorsementDatePostdating = getDateDifference(endEffectiveDate, today); 
				}				
				
				String refundPremium;
				BigDecimal extraPremium ;
				String nilEndorsement;
				String extraEndorsement = "N";
				
				if( !Utils.isEmpty( workmenCompVO.getEndorsmentVO() ) && !workmenCompVO.getCommonVO().getIsQuote() ){
					/* Create Endorsement fact */
					Fact endorsementFact = new Fact();
					endorsementFact.setFactName(RulesConstants.FACT_ENDORSEMENT);
					List<Characteristics> endorsementCharacteristicList = endorsementFact.getCharacteristics();
					/* Create Refund fact */
					/*Fact refundFact = new Fact();
					refundFact.setFactName(RulesConstants.FACT_REFUND);
					List<Characteristics> refundCharacteristicList = refundFact.getCharacteristics();*/

					EndorsmentVO endtVO = workmenCompVO.getEndorsmentVO().get( SvcConstants.zeroVal );
					/* Create cancellationEndorsement fact */
					if(endtVO!=null && (endtVO.isPolicyToBeCancelled()))
					{
						Fact cancellationDateFact = new Fact();
						cancellationDateFact.setFactName(RulesConstants.FACT_CANCELLATION_DATING);
						List<Characteristics> cancellationDateCharacteristicList = cancellationDateFact.getCharacteristics();				
						Characteristics cancellationDateBackdatingCharacteristics = new Characteristics();
						cancellationDateBackdatingCharacteristics.setName( "cancellationDateBackdating" );
						cancellationDateBackdatingCharacteristics.setValue( String.valueOf( endorsementDateBackdating ) );
						cancellationDateCharacteristicList.add( cancellationDateBackdatingCharacteristics );
			
						Characteristics cancellationDatePostdatingCharacteristics = new Characteristics();
						cancellationDatePostdatingCharacteristics.setName( "cancellationDatePostdating" );
						cancellationDatePostdatingCharacteristics.setValue( String.valueOf( endorsementDatePostdating ) );
						cancellationDateCharacteristicList.add( cancellationDatePostdatingCharacteristics );
						factList.add(cancellationDateFact);
						if(!Utils.isEmpty(workmenCompVO.getCommonVO().getPolicyNo()))
						{
							Fact cancellationEndorsementFact = new Fact();
							cancellationEndorsementFact.setFactName(RulesConstants.FACT_CANCEL_ENDORSEMENT);
							List<Characteristics> cancellationEndorsementCharacteristicList = cancellationEndorsementFact.getCharacteristics();
							Characteristics claimsExistForEndFactorCharacteristics = new Characteristics();
							claimsExistForEndFactorCharacteristics.setName( "claimsExistForCancelEnd" );
							ClaimsSvc claimsService = (ClaimsSvc) Utils.getBean( "claimsSvc" );
							claimsExistForEndFactorCharacteristics.setValue(  claimsService.checkClaimsExistForPolicyNumber( workmenCompVO.getCommonVO().getPolicyNo() )? "Y" : "N"  );
							cancellationEndorsementCharacteristicList.add( claimsExistForEndFactorCharacteristics );
							factList.add(cancellationEndorsementFact);
						}
					}
					/*else	//Commented empty else condition(Not content inside else) - sonar violation fix
					{*/
						/*Characteristics effectiveDateBackdatingCharacteristics = new Characteristics();
						effectiveDateBackdatingCharacteristics.setName( "endorsementDateBackdating" );
						effectiveDateBackdatingCharacteristics.setValue( String.valueOf( endorsementDateBackdating ) );
						endorsementCharacteristicList.add( effectiveDateBackdatingCharacteristics );
						
						Characteristics effectiveDatePostdatingCharacteristics = new Characteristics();
						effectiveDatePostdatingCharacteristics.setName( "endorsementDatePostdating" );
						effectiveDatePostdatingCharacteristics.setValue( String.valueOf( endorsementDatePostdating ) );
						endorsementCharacteristicList.add( effectiveDatePostdatingCharacteristics );*/
						
					//}
					if( endtVO != null && endtVO.getPayablePremium() > ZERO ){
						extraPremium = new BigDecimal( decForm.format( endtVO.getPayablePremium() ) );
						extraEndorsement = new String( "Y" );
						Characteristics extraPremiumCharacteristics = new Characteristics();
						extraPremiumCharacteristics.setName( "extraPremium" );
						extraPremiumCharacteristics.setValue( String.valueOf( extraPremium ) );
						endorsementCharacteristicList.add( extraPremiumCharacteristics );
						
						Characteristics extraEndorsementCharacteristics = new Characteristics();
						extraEndorsementCharacteristics.setName( "extraEndorsement" );
						extraEndorsementCharacteristics.setValue( extraEndorsement );
						endorsementCharacteristicList.add( extraEndorsementCharacteristics );
					}
					else if(endtVO != null &&  endtVO.getPayablePremium() < ZERO ){		/* Added null check 'endtVO != null &&  ' - sonar violation fix */

						extraPremium = new BigDecimal( decForm.format( endtVO.getPayablePremium() ) );
						refundPremium = new String( "Y" );

						
						Characteristics refundPremiumCharacteristics = new Characteristics();
						refundPremiumCharacteristics.setName( "refundPremium" );
						refundPremiumCharacteristics.setValue( new BigDecimal( decForm.format( extraPremium ) ).toString() );
						endorsementCharacteristicList.add( refundPremiumCharacteristics );
						
						Characteristics refundEndorsementCharacteristics = new Characteristics();
						refundEndorsementCharacteristics.setName( "refundEndorsement" );
						refundEndorsementCharacteristics.setValue( refundPremium );
						endorsementCharacteristicList.add( refundEndorsementCharacteristics );
					}
					else{
						nilEndorsement = new String( "Y" );

						Characteristics nilEndorsementCharacteristics = new Characteristics();
						nilEndorsementCharacteristics.setName( "nilEndorsement" );
						nilEndorsementCharacteristics.setValue( nilEndorsement );
						endorsementCharacteristicList.add( nilEndorsementCharacteristics );

					}
					
					
					factList.add(endorsementFact);
					//factList.add(refundFact);

				}
				
				//factList.add(userFact);
				factList.add(wcFact);
							
				
			}
			}catch(NullPointerException e){
				logger.debug("Null pointer exception");
			}
			if(!Utils.isEmpty(workmenCompVO.getRenewals()))
			{
				/* Create Renewal fact */
				Fact renewalFact = new Fact();
				renewalFact.setFactName(RulesConstants.FACT_REN);
				List<Characteristics> renewalRuleCharacteristicList = renewalFact.getCharacteristics();
				String claimExists;
				String endorsmentExists;
				RenewalVO renewalVO = workmenCompVO.getRenewals();
				Characteristics claimExistsCharacteristics = new Characteristics();
				claimExistsCharacteristics.setName("renewalClaimExist");
				if(renewalVO.getClaimCount()>0){
					claimExists = "Y";
				} else {
					claimExists = "N";
				}
				claimExistsCharacteristics.setValue(claimExists);
				renewalRuleCharacteristicList.add(claimExistsCharacteristics);
				
				Characteristics osPriorPremiumCharacteristics = new Characteristics();
				osPriorPremiumCharacteristics.setName( "outstandingPriorPremium" );
				osPriorPremiumCharacteristics.setValue(decForm.format( renewalVO.getOsPremium() ).toString());
				renewalRuleCharacteristicList.add(osPriorPremiumCharacteristics);
				
				Characteristics endorsmentCharacteristics = new Characteristics();
				endorsmentCharacteristics.setName( "allowEndorsementAfterRenewal" );
				if(!Utils.isEmpty( renewalVO.getEndorsmentList() )){
					endorsmentExists = "Y";
				} else {
					endorsmentExists = "N";
				}
				endorsmentCharacteristics.setValue(endorsmentExists);
				renewalRuleCharacteristicList.add(endorsmentCharacteristics);
				
				factList.add(renewalFact);
				
			}
			
			Fact commonFactor = new Fact();
			commonFactor.setFactName(RulesConstants.FACT_COMMON);
			ArrayList<Characteristics> commonRuleCharacteristicsList = (ArrayList<Characteristics>) commonFactor.getCharacteristics();

			Characteristics commissionCharacteristics = new Characteristics();
			commissionCharacteristics.setName("commissionDifference");
			commissionCharacteristics.setValue(new BigDecimal(decForm.format(commissionDiff)).toString());
			commonRuleCharacteristicsList.add(commissionCharacteristics);
			factList.add(commonFactor);

			riskDetailList.add(riskDetails);
		}
		
		return ruleInfo;
	}
	
	private long getDateDifference(Date latestDate, Date previousDate) {
		return (( latestDate.getTime() - previousDate.getTime() ) / DAYDIVIDER );
	}
}
