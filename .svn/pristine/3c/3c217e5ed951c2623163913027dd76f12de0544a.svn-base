package com.rsaame.pas.dao.cmn;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.rules.invoker.RuleServiceInvoker;
import com.rsaame.pas.rules.mapper.RulesConstants;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.PolicyUtils;
import com.rsaame.pas.vo.app.LoadExistingInputVO;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.ReferralListVO;
import com.rsaame.pas.vo.bus.ReferralVO;
import com.rsaame.pas.vo.bus.ReferralLocKey;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.SectionVO;


public class RuleHandler {
	
	private final static Logger LOGGER = Logger.getLogger(RuleHandler.class);
	DecimalFormat decForm = new DecimalFormat(RulesConstants.DECIMAL_FORMAT);

	@SuppressWarnings("unchecked")
	public boolean callRulesForAllSection( BaseVO input){
		
		PolicyVO policyVO = (PolicyVO) input;
		LOGGER.debug("Entered in callRulesForAllSection method...");
		
		boolean rulesPassed = true;
		
		boolean rulesEnabled = Utils.toDefaultFalseBoolean( Utils.getSingleValueAppConfig( "RULES_ENABLED", "Y" ) );
		
		if( rulesEnabled ){
			ReferralListVO listReferralVO = null;
			try{
				RuleServiceInvoker ruleServiceInvoker = (RuleServiceInvoker)Utils.getBean( "ruleServiceInvoker" );
				LocationVO locationDetails = null;
				String roleType=null;
				int userId=0;
				if(input instanceof PolicyVO) {
					PolicyVO policyVO1 = (PolicyVO) input;
					UserProfile userProfileVO = (UserProfile) policyVO1.getLoggedInUser();
					roleType=userProfileVO.getRsaUser().getHighestRole();
					userId= userProfileVO.getRsaUser().getUserId();       

				}
				LOGGER.info("User Role :: "+roleType +"  User Id :: "+userId);

				List<SectionVO> sectionVOList = null;
				SectionVO sectionVO = null;
				Iterator<SectionVO> sectionListItr = null;
				Map<LocationVO, RiskGroupDetails> locDetails = new LinkedHashMap<LocationVO, RiskGroupDetails>();

				sectionVOList = policyVO.getRiskDetails();
				Integer[] sectionArray= getSectionsList(policyVO);
				Map<? extends RiskGroup, ? extends RiskGroupDetails> riskGroupDetails = null;
				
				SectionVO section =null;
				List<SectionVO> riskDetails = new ArrayList<SectionVO>(); 
				/* Calling loadSectionData method of SectionSvc */
				if (!Utils.isEmpty(sectionVOList)) {
					sectionListItr = sectionVOList.iterator();
					while (sectionListItr.hasNext()) {
						sectionVO = (SectionVO) sectionListItr.next();
						int sectionId = sectionVO.getSectionId().intValue();

						LoadExistingInputVO inputVO = constructInput(sectionId, policyVO);
						section = (SectionVO) TaskExecutor.executeTasks(SvcConstants.LOADSECTIONSDATA, inputVO);

						riskGroupDetails = section.getRiskGroupDetails();

						locationDetails = null;
						for (Map.Entry<? extends RiskGroup, ? extends RiskGroupDetails> locationEntry : riskGroupDetails
								.entrySet()) {
							locationDetails = (LocationVO) locationEntry.getKey();
							locationDetails.setToSave(true);
							locDetails.put(locationDetails, null);
						}
						sectionVO.setRiskGroupDetails(locDetails);
						riskDetails.add(section);

					}
					policyVO.setRiskDetails(riskDetails);

				}

				
			
				StopWatch stopWatch = new StopWatch();
				stopWatch.start();
				BaseVO output = ruleServiceInvoker.callRuleService( policyVO, sectionArray , roleType );
				locationDetails.setToSave( false );

				stopWatch.stop();

				LOGGER.info("Response time for callRulesForAllSection IS : " + stopWatch.getTime() + " milisecond");
				listReferralVO = (ReferralListVO) output;
				if( !Utils.isEmpty( listReferralVO ) && !Utils.isEmpty( listReferralVO.getReferrals() ) ){
					rulesPassed = false;
					policyVO.setReferrals(listReferralVO.getReferrals());

					for( ReferralVO referralVO : listReferralVO.getReferrals() ){
						if( !Utils.isEmpty( referralVO ) ){
							referralVO.setTprUserId(userId);
							referralVO.setTprUserRole(roleType);
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
			if (!Utils.isEmpty(policyVO.getMapReferralVO())) {
				// Saving in T_TRN_TEMP_PAS_REFERRAL
				insertReferal(policyVO);
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
                     else if( !Utils.isEmpty( sectionVO ) && ( SvcConstants.SECTION_ID_BI == ( sectionVO.getSectionId() ).intValue() ) ) {
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
                     }
               }
               
        }
        sectionArray = new Integer[listOfSections.size()];
        for (int i=0;i<listOfSections.size();i++) {
               sectionArray[i] = listOfSections.get(i);
        }
        return sectionArray;
        
 }

	private LoadExistingInputVO constructInput(   Integer sectionId,PolicyVO policyVO ){
		LoadExistingInputVO existingInputVO = new LoadExistingInputVO();
		
		
			existingInputVO.setSectionId( sectionId );
			
			/*
			 * Fetch always latest endt Id and store in existing VO
			 */
			Long endtId = getLatestEndtId( policyVO);
			
			if( !Utils.isEmpty( policyVO ) ){
				existingInputVO.setPolLinkingId( policyVO.getPolLinkingId() );
				existingInputVO.setEndtId( endtId );
				existingInputVO.setQuote( policyVO.getIsQuote() );
				existingInputVO.setIsPrepackaged( policyVO.getIsPrepackaged() );
				Integer basicSectionId  = PolicyUtils.getBasicSectionId(  policyVO );
				existingInputVO.setBasicSectionId( basicSectionId);
				existingInputVO.setPolicyStatus( policyVO.getStatus() );
				existingInputVO.setTariffCode(policyVO.getScheme().getTariffCode());
				existingInputVO.setAppFlow(policyVO.getAppFlow());
			
		}
		return existingInputVO;
	}
	
	
	public static Long getLatestEndtId( PolicyVO policyVO ){
		Long endtId = null;

		if( !Utils.isEmpty( policyVO.getNewEndtId() ) && policyVO.getEndtId() < policyVO.getNewEndtId() ){
			endtId = policyVO.getNewEndtId();
		}
		else{
			if( !Utils.isEmpty( policyVO.getEndtId() ) ){
				endtId = policyVO.getEndtId();
			}
		}

		return endtId;
	}
	
	
	public void insertReferal(PolicyVO policyVO) {
		try {
			if( !Utils.isEmpty( policyVO.getMapReferralVO() ) ){
				for( Entry<ReferralLocKey, ReferralVO> mapRefEntry : policyVO.getMapReferralVO().entrySet() ){
						ReferralVO locreferralVO = mapRefEntry.getValue();
						locreferralVO.setPolLinkingId( policyVO.getPolLinkingId() );
						locreferralVO.setRiskGroupId( mapRefEntry.getValue().getRiskGroupId() );
						if( !Utils.isEmpty( locreferralVO ) 	){
							TempPasReferralDAO insertTempPasReferalDao = (TempPasReferralDAO) Utils.getBean( "tempPasReferralDAO" );
							insertTempPasReferalDao.insertReferal( locreferralVO );
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("Exception in insertReferal method" + e);

		}
	}
	
	
}
