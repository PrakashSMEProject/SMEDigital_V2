package com.rsaame.pas.b2b.ws.handler;

import java.math.BigDecimal;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.rsaame.pas.b2b.ws.constant.ServiceConstant;
import com.rsaame.pas.b2b.ws.vo.response.Cover;
import com.rsaame.pas.b2b.ws.vo.response.CoverPremium;
import com.rsaame.pas.b2b.ws.vo.response.SumInsured;
import com.rsaame.pas.cmn.currency.Currency; 
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.rules.invoker.RuleServiceInvoker;
import com.rsaame.pas.rules.mapper.RulesConstants;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.app.RuleContext;
import com.rsaame.pas.vo.bus.FidelityNammedEmployeeDetailsVO;
import com.rsaame.pas.vo.bus.FidelityUnnammedEmployeeVO;
import com.rsaame.pas.vo.bus.FidelityVO;
import com.rsaame.pas.vo.bus.GeneralInfoVO;
import com.rsaame.pas.vo.bus.InsuredVO;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.PARUWDetailsVO;
import com.rsaame.pas.vo.bus.ParVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.bus.PropertyRiskDetails;
import com.rsaame.pas.vo.bus.PropertyRisks;
import com.rsaame.pas.vo.bus.PublicLiabilityVO;
import com.rsaame.pas.vo.bus.ReferralListVO;
import com.rsaame.pas.vo.bus.ReferralLocKey;
import com.rsaame.pas.vo.bus.ReferralVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.RiskGroupingLevel;
import com.rsaame.pas.vo.bus.SectionVO;
import com.rsaame.pas.vo.bus.SumInsuredVO;
import com.rsaame.pas.vo.bus.UWQuestionRespType;
import com.rsaame.pas.vo.bus.UWQuestionVO;
import com.rsaame.pas.vo.bus.UWQuestionsVO;

public class RuleHandler {
	
	private final static Logger LOGGER = Logger.getLogger(RuleHandler.class);
	DecimalFormat decForm = new DecimalFormat(RulesConstants.DECIMAL_FORMAT);

	public boolean callRulesForAllSection( BaseVO input ){
		
		PolicyVO policyVO = (PolicyVO) input;
		LOGGER.debug("Entered in callRulesForAllSection method...");
		
		boolean rulesPassed = true;
		
		boolean rulesEnabled = Utils.toDefaultFalseBoolean( Utils.getSingleValueAppConfig( com.Constant.CONST_RULES_ENABLED, "Y" ) );
		
		if( rulesEnabled ){
			ReferralListVO listReferralVO = null;
			try{
				RuleServiceInvoker ruleServiceInvoker = (RuleServiceInvoker)Utils.getBean( com.Constant.CONST_RULESERVICEINVOKER );
				String roleType = com.Constant.CONST_BROKER_USER;
				
				LOGGER.info("User Role :_1"+roleType);
				
				Integer[] sectionArray= getSectionsList(policyVO);
				
				StopWatch stopWatch = new StopWatch();
				stopWatch.start();
				BaseVO output = ruleServiceInvoker.callRuleService( policyVO, sectionArray , roleType );
				stopWatch.stop();

				LOGGER.info("Response time for callRulesForAllSection IS : " + stopWatch.getTime() + " milisecon_1");
				listReferralVO = (ReferralListVO) output;
				if( !Utils.isEmpty( listReferralVO ) && !Utils.isEmpty( listReferralVO.getReferrals() ) ){
					rulesPassed = false;
					policyVO.setReferrals(listReferralVO.getReferrals());

					for( ReferralVO referralVO : listReferralVO.getReferrals() ){
						if( !Utils.isEmpty( referralVO ) ){
							policyVO.setReferral( referralVO );
						}
					}
				}
			}
			catch( BusinessException e ){
				rulesPassed = false;
				listReferralVO = (com.rsaame.pas.vo.bus.ReferralListVO) e.getExceptionData();
				if(null != listReferralVO) {
					policyVO.setReferrals(listReferralVO.getReferrals());

					for( ReferralVO referralVO : listReferralVO.getReferrals() ){
						if( !Utils.isEmpty( referralVO ) ){
							referralVO.setActionIdentifier( "" );
							policyVO.setReferral( referralVO );
						}
					}
				}
				else 
					throw new BusinessException( "pas.cmn.rulesException", null, "Error in the rules", "Error in the rules" );
				return rulesPassed;
			}

		}
		
		return rulesPassed;
	}
	
	public boolean callRuleForIssueQuote(BaseVO baseVO) {

		LOGGER.debug("Entered in callRuleForIssueQuote method...");
		double premiumAmt=0.0;
		boolean isfgPresent = true;
		boolean rulesPassed = true;
		PolicyVO policyVO = (PolicyVO) baseVO;

		boolean rulesEnabled = Utils.toDefaultFalseBoolean(Utils.getSingleValueAppConfig(com.Constant.CONST_RULES_ENABLED, "Y"));
		
		if (rulesEnabled) {
			ReferralListVO listReferralVO = null;
			try {
				RuleServiceInvoker ruleServiceInvoker = (RuleServiceInvoker) Utils.getBean(com.Constant.CONST_RULESERVICEINVOKER);
				String roleType = com.Constant.CONST_BROKER_USER;
				LOGGER.info("User Role :_2" + roleType);

				Integer[] sectionArray = new Integer[1];

				sectionArray[0] = Integer.parseInt(Utils.getSingleValueAppConfig("QUOTE_PREMIUM_RULES_TASK_INPUT"));

				policyVO.setRuleContext(RuleContext.QUOTE_PREMIUM);
				StopWatch stopWatch = new StopWatch();
				stopWatch.start();
				BaseVO output = ruleServiceInvoker.callRuleService(policyVO, sectionArray, roleType);
				stopWatch.stop();

				LOGGER.info("Response time for callRuleForIssueQuote IS : " + stopWatch.getTime() + " milisecon_2");
				listReferralVO = (ReferralListVO) output;
				if (!Utils.isEmpty(listReferralVO) && !Utils.isEmpty(listReferralVO.getReferrals())) {
					rulesPassed = false;
					policyVO.setReferrals(listReferralVO.getReferrals());

					for (ReferralVO referralVO : listReferralVO.getReferrals()) {
						if (!Utils.isEmpty(referralVO)) {
							policyVO.setReferral(referralVO);
						}
					}
				}
			} catch (BusinessException e) {
				rulesPassed = false;
				listReferralVO = (com.rsaame.pas.vo.bus.ReferralListVO) e.getExceptionData();
				if (null != listReferralVO) {
					policyVO.setReferrals(listReferralVO.getReferrals());

					for (ReferralVO referralVO : listReferralVO.getReferrals()) {
						if (!Utils.isEmpty(referralVO)) {
							referralVO.setActionIdentifier("");
							policyVO.setReferral(referralVO);
						}
					}
				}
			}
		}

		return rulesPassed;
	}

	
	
	//
	public boolean callRuleForCumuliativeLossQuantum(BaseVO baseVO,Double lossExpQuantumValue,Double totalPrm) {

		LOGGER.debug("Entered in callRuleForIssueQuote method...");
		double premiumAmt=0.0;
		boolean isfgPresent = true;
		boolean rulesPassed = true;
		PolicyVO policyVO = (PolicyVO) baseVO;

		boolean rulesEnabled = Utils.toDefaultFalseBoolean(Utils.getSingleValueAppConfig(com.Constant.CONST_RULES_ENABLED, "Y"));
		
		
		//Added for cumulative claim ratio
		
	/*	if (!Utils.isEmpty(policyVO)
				&& !Utils.isEmpty(policyVO.getGeneralInfo().getClaimsHistory().getLossExpQuantum())) {
		for(SectionVO sectionVO : policyVO.getRiskDetails()) {
			if(sectionVO.getSectionId()==9) {
				Map<? extends RiskGroup, ? extends RiskGroupDetails> riskGroupDetailsMap = sectionVO.getRiskGroupDetails();
		for (Map.Entry<? extends RiskGroup, ? extends RiskGroupDetails> entry : riskGroupDetailsMap
				.entrySet()) {
			RiskGroupDetails groupDetails = entry.getValue();
			//Fidelity Gurantee
			if (!Utils.isEmpty(groupDetails) && groupDetails instanceof FidelityVO) {
				FidelityVO fidelityVO = (FidelityVO) groupDetails;
				isfgPresent=false;
				Double premiumVal=SvcUtils.getTotalPremium(policyVO)+fidelityVO.getFidAggregateBasePremium();
				premiumAmt=premiumVal*100;
				System.out.println("The total premium is "+premiumAmt);
				
			}
			System.out.println("The section Ids present are :"+sectionVO.getSectionId());
		}
		}
		}
			BigDecimal lossExpQuantum = policyVO.getGeneralInfo().getClaimsHistory().getLossExpQuantum();
			if(isfgPresent) {
			premiumAmt = SvcUtils.getTotalPremium(policyVO) * 100.0;
			}*/
			//double lossExpQuantumValue = ((lossExpQuantum.doubleValue() / 3) * 0.25);
			/*if (lossExpQuantumValue > Double
					.valueOf(decForm.format(Math.round(premiumAmt) / 100.0))) {
*/
				if (rulesEnabled) {
					ReferralListVO listReferralVO = null;
					try {
						RuleServiceInvoker ruleServiceInvoker = (RuleServiceInvoker) Utils
								.getBean(com.Constant.CONST_RULESERVICEINVOKER);
						String roleType = com.Constant.CONST_BROKER_USER;
						LOGGER.info("User Role :_3" + roleType);

						Integer[] sectionArray = new Integer[1];

						sectionArray[0] = Integer
								.parseInt(Utils.getSingleValueAppConfig("QUOTE_PREMIUM_RULES_TASK_INPUT"));

						policyVO.setRuleContext(RuleContext.CUMULATIVE_LOSS_QUANTUM);
						StopWatch stopWatch = new StopWatch();
						stopWatch.start();
						BaseVO output = ruleServiceInvoker.callRuleService(policyVO, sectionArray, roleType);
						stopWatch.stop();

						LOGGER.info(
								"Response time for callRuleForIssueQuote IS : " + stopWatch.getTime() + com.Constant.CONST_MILISECOND);
						listReferralVO = (ReferralListVO) output;
						if (!Utils.isEmpty(listReferralVO) && !Utils.isEmpty(listReferralVO.getReferrals())) {
							rulesPassed = false;
							policyVO.setReferrals(listReferralVO.getReferrals());

							for (ReferralVO referralVO : listReferralVO.getReferrals()) {
								if (!Utils.isEmpty(referralVO)) {
								
									referralVO.getReferralText().clear();
									referralVO.getReferralText().add("Your role does not allow to have cumulative loss of quantum "+(Currency.getFormattedCurrency(lossExpQuantumValue, "SBS"))  +" is greather than premium " + totalPrm); 
									policyVO.setReferral(referralVO);
								}
							}
						}
					} catch (BusinessException e) {
						rulesPassed = false;
						listReferralVO = (com.rsaame.pas.vo.bus.ReferralListVO) e.getExceptionData();
						if (null != listReferralVO) {
							policyVO.setReferrals(listReferralVO.getReferrals());

							for (ReferralVO referralVO : listReferralVO.getReferrals()) {
								if (!Utils.isEmpty(referralVO)) {
									referralVO.setActionIdentifier("");
									policyVO.setReferral(referralVO);
								}
							}
						}
					}
				}
			//}
				return rulesPassed;
		// End
		
	
	}

	
	
	
	
	
	public boolean callRuleConvertToPolicy(BaseVO baseVO) {

		boolean rulesPassed = true;
		LOGGER.debug("Entered in callRuleConvertToPolicy method...");
		PolicyVO policyVO = (PolicyVO) baseVO;

		boolean rulesEnabled = Utils.toDefaultFalseBoolean(Utils.getSingleValueAppConfig(com.Constant.CONST_RULES_ENABLED, "Y"));

		if (rulesEnabled) {
			ReferralListVO listReferralVO = null;
			try {
				RuleServiceInvoker ruleServiceInvoker = (RuleServiceInvoker) Utils.getBean(com.Constant.CONST_RULESERVICEINVOKER);
				String roleType = com.Constant.CONST_BROKER_USER;
				LOGGER.info("User Role :_4" + roleType);

				Integer[] sectionArray = new Integer[2];

				sectionArray[0] = Integer.parseInt(Utils.getSingleValueAppConfig("CONV_TO_POLICY_RULES_TASK_INPUT"));
				sectionArray[1] = Integer
						.parseInt(Utils.getSingleValueAppConfig("CONV_TO_POLICY_REF_RULES_TASK_INPUT"));
				
				StopWatch stopWatch = new StopWatch();
				stopWatch.start();
				BaseVO output = ruleServiceInvoker.callRuleService(policyVO, sectionArray, roleType);
				stopWatch.stop();

				LOGGER.info("Response time for callRuleConvertToPolicy IS : " + stopWatch.getTime() + " milisecon_3");
				listReferralVO = (ReferralListVO) output;
				if (!Utils.isEmpty(listReferralVO) && !Utils.isEmpty(listReferralVO.getReferrals())) {
					rulesPassed = false;
					policyVO.setReferrals(listReferralVO.getReferrals());

					for (ReferralVO referralVO : listReferralVO.getReferrals()) {
						if (!Utils.isEmpty(referralVO)) {
							policyVO.setReferral(referralVO);
						}
					}
				}
			} catch (BusinessException e) {
				rulesPassed = false;
				listReferralVO = (com.rsaame.pas.vo.bus.ReferralListVO) e.getExceptionData();
				if (null != listReferralVO) {
					policyVO.setReferrals(listReferralVO.getReferrals());

					for (ReferralVO referralVO : listReferralVO.getReferrals()) {
						if (!Utils.isEmpty(referralVO)) {
							referralVO.setActionIdentifier("");
							policyVO.setReferral(referralVO);
						}
					}
				}
			}
		}

		return rulesPassed;
	}
		
	private Integer[] getSectionsList(BaseVO baseVO) {
        
        LOGGER.debug("Entered in getSectionsList method...");
        Integer[] sectionArray = null;
        PolicyVO policyVO = (PolicyVO) baseVO;
        Iterator<SectionVO> sectionListItr = null;
        SectionVO sectionVO = null;
        ArrayList<Integer> listOfSections = new ArrayList<Integer>();
        listOfSections.add(new Integer(0));
        
        List <SectionVO> sectionList=policyVO.getRiskDetails();
        if(!Utils.isEmpty(sectionList))
        {
               sectionListItr = sectionList.iterator();

               while( sectionListItr.hasNext() ){
                     sectionVO = (SectionVO) sectionListItr.next();
                     if( !Utils.isEmpty( sectionVO ) && ( SvcConstants.SECTION_ID_PAR == ( sectionVO.getSectionId() ).intValue() ) ){
                            LOGGER.info( "PAR section present." );
                            listOfSections.add(new Integer(1));
                     }
                     else if( !Utils.isEmpty( sectionVO ) && ( SvcConstants.SECTION_ID_PL == ( sectionVO.getSectionId() ).intValue() ) ) {
                            LOGGER.info( "PL section present." );
                            listOfSections.add(new Integer(6));
                     }
                     else if( !Utils.isEmpty( sectionVO ) && ( SvcConstants.SECTION_ID_WC == ( sectionVO.getSectionId() ).intValue() ) ) {
                            LOGGER.info( "WC section present." );
                            listOfSections.add(new Integer(7));
                     }
                     /* Commenting rule call because we are not going to call rules for Non-Core section for JLT web service
                     * else if( !Utils.isEmpty( sectionVO ) && ( SvcConstants.SECTION_ID_BI == ( sectionVO.getSectionId() ).intValue() ) ) {
                            LOGGER.info( "BI section present." );
                            listOfSections.add(new Integer(2));
                     }
                     else if( !Utils.isEmpty( sectionVO ) && ( SvcConstants.SECTION_ID_MB == ( sectionVO.getSectionId() ).intValue() ) ) {
                            LOGGER.info( "MB section present." );
                            listOfSections.add(new Integer(3));
                     }
                     else if( !Utils.isEmpty( sectionVO ) && ( SvcConstants.SECTION_ID_DOS == ( sectionVO.getSectionId() ).intValue() ) ) {
                            LOGGER.info( "DOS section present." );
                            listOfSections.add(new Integer(4));
                     }
                     else if( !Utils.isEmpty( sectionVO ) && ( SvcConstants.SECTION_ID_EE == ( sectionVO.getSectionId() ).intValue() ) ) {
                            LOGGER.info( "EE section present." );
                            listOfSections.add(new Integer(5));
                     }
                     else if( !Utils.isEmpty( sectionVO ) && ( SvcConstants.SECTION_ID_MONEY == ( sectionVO.getSectionId() ).intValue() ) ) {
                            LOGGER.info( "MONEY section present." );
                            listOfSections.add(new Integer(8));
                     }
                     else if( !Utils.isEmpty( sectionVO ) && ( SvcConstants.SECTION_ID_FIDELITY == ( sectionVO.getSectionId() ).intValue() ) ) {
                            LOGGER.info( "FIDELITY section present." );
                            listOfSections.add(new Integer(9));
                     }
                     else if( !Utils.isEmpty( sectionVO ) && ( SvcConstants.SECTION_ID_TB == ( sectionVO.getSectionId() ).intValue() ) ) {
                            LOGGER.info( "TRAVEL Baggage section present." );
                            listOfSections.add(new Integer(10));
                     }
                     else if( !Utils.isEmpty( sectionVO ) && ( SvcConstants.SECTION_ID_GIT == ( sectionVO.getSectionId() ).intValue() ) ) {
                            LOGGER.info( "GIT section present." );
                            listOfSections.add(new Integer(11));
                     }
                     else if( !Utils.isEmpty( sectionVO ) && ( SvcConstants.SECTION_ID_GPA == ( sectionVO.getSectionId() ).intValue() ) ) {
                            LOGGER.info( "GPA section present." );
                            listOfSections.add(new Integer(12));
                     }*/
               }
               
        }
        sectionArray = new Integer[listOfSections.size()];
        for (int i=0;i<listOfSections.size();i++) {
               sectionArray[i] = listOfSections.get(i);
        }
        return sectionArray;
        
 }

	/*
	 * For referral case for controlling the premium 
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void checkReferrals(PolicyVO policyVO) {
		if(!Utils.isEmpty(policyVO.getMapReferralVO())) {
			double referralSectionPremium =0.0;
			Map<ReferralLocKey, ReferralVO> map = (Map<ReferralLocKey, ReferralVO>) policyVO.getMapReferralVO();
			List<ReferralLocKey> keys = new ArrayList<>();
			if(!Utils.isEmpty(map)) {
				for( Map.Entry<ReferralLocKey, ReferralVO> referralEntry : map.entrySet() ){
					
					ReferralVO referralVO = referralEntry.getValue();
					
					java.util.List<SectionVO> sectionVOList = null;
					Iterator<SectionVO> sectionListItr = null;
					SectionVO sectionVO = null;
					RiskGroupDetails riskDetail = null;
					HashMap<RiskGroup, RiskGroupDetails> riskGroupDetails = null;
					
					sectionVOList = policyVO.getRiskDetails();
					if( null != sectionVOList ){
						sectionListItr = sectionVOList.iterator();

						while( sectionListItr.hasNext() ){
							sectionVO = sectionListItr.next();
							
							if( referralVO.getSectionId() == sectionVO.getSectionId().intValue() || (
									referralVO.getSectionId() == Integer.valueOf( Utils.getSingleValueAppConfig("GEN"))
									 || referralVO.getSectionId() == Integer.valueOf( Utils.getSingleValueAppConfig("QUO")) )
									){
								riskGroupDetails = (HashMap<RiskGroup, RiskGroupDetails>) sectionVO.getRiskGroupDetails();

								if( !Utils.isEmpty( riskGroupDetails ) ){
									for( Map.Entry<RiskGroup, RiskGroupDetails> riskGroupDetailsEntry : riskGroupDetails.entrySet() ){

										riskDetail = riskGroupDetailsEntry.getValue();
										if(!Utils.isEmpty(riskDetail.getPremium())) {
											referralSectionPremium = referralSectionPremium + riskDetail.getPremium().getPremiumAmt();
											LOGGER.debug(sectionVO.getSectionName() +" Referral Section Premium "+riskDetail.getPremium().getPremiumAmt()+" going to set 0._1");
											riskDetail.getPremium().setPremiumAmt(0.0);
										}
										
										if(riskDetail instanceof ParVO) {
											ParVO parVO = (ParVO) riskDetail;
											
											if(!Utils.isEmpty(parVO.getBldPremium())) {
//												referralSectionPremium = referralSectionPremium + parVO.getBldPremium().getPremiumAmt();
												LOGGER.debug(sectionVO.getSectionName() +" Building Premium "+ parVO.getBldPremium().getPremiumAmt()+" going to set 0._2");
												parVO.getBldPremium().setPremiumAmt(0.0);
											}
											
											if(!Utils.isEmpty(parVO.getCovers())) {
												List<PropertyRiskDetails> propertyRiskDetailsList = parVO.getCovers()
														.getPropertyCoversDetails();
												for (PropertyRiskDetails propertyRiskDetails : propertyRiskDetailsList) {
													
													if(!Utils.isEmpty(propertyRiskDetails.getPremium().getPremiumAmt())) {
//														referralSectionPremium = referralSectionPremium + propertyRiskDetails.getPremium().getPremiumAmt();
														LOGGER.debug(sectionVO.getSectionName() +" Content Premium "+ propertyRiskDetails.getPremium().getPremiumAmt()+" going to set 0._3");
														propertyRiskDetails.getPremium().setPremiumAmt(0.0);
													}
													
													
												}
												
											}
										}
										
									}
								}
								
							}
							
							
						}
						if(referralVO.getSectionId() != Integer.valueOf( Utils.getSingleValueAppConfig("PAR_SECTION"))
								&& referralVO.getSectionId() != Integer.valueOf( Utils.getSingleValueAppConfig("PL_SECTION")) 
								&& referralVO.getSectionId() != Integer.valueOf( Utils.getSingleValueAppConfig("WC_SECTION"))
								&& referralVO.getSectionId() != Integer.valueOf( Utils.getSingleValueAppConfig("GEN"))
								&& referralVO.getSectionId() != Integer.valueOf( Utils.getSingleValueAppConfig("QUO")) 
								) {
							keys.add(referralEntry.getKey());
							
						}
					}
					/*if(referralVO.getSectionId() == Integer.valueOf( Utils.getSingleValueAppConfig("GEN"))
									 || referralVO.getSectionId() == Integer.valueOf( Utils.getSingleValueAppConfig("QUO"))) {
						policyVO.setPremiumVO(null);
					}		*/
				}
				if(!Utils.isEmpty(keys)) {
					for (ReferralLocKey referralLocKey : keys) {
						map.remove(referralLocKey);
					}
				}
				
				double totalPremium = policyVO.getPremiumVO().getPremiumAmt() - referralSectionPremium;
				LOGGER.debug("Total Premium Before resetting without referral section premium :::"+policyVO.getPremiumVO().getPremiumAmt());
				LOGGER.debug("Total Referral section premium :::"+referralSectionPremium);
				LOGGER.debug("Total Prmeium After resetting without referral section premium ::::"+totalPremium);
				policyVO.getPremiumVO().setPremiumAmt(totalPremium);
			}
			
		}
	}
}
