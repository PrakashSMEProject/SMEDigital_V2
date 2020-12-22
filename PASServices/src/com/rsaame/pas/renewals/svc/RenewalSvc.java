package com.rsaame.pas.renewals.svc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.task.ITask;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.dao.cmn.CommonOpDAO;
import com.rsaame.pas.dao.cmn.SectionDAO;
import com.rsaame.pas.rating.svc.PremiumCalculator;
import com.rsaame.pas.renewals.dao.IRenewalsDAO;
import com.rsaame.pas.svc.cmn.SectionSvc;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.app.LoadExistingInputVO;
import com.rsaame.pas.vo.app.SectionList;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.RiskGroupingLevel;
import com.rsaame.pas.vo.bus.SectionVO;

public class RenewalSvc extends BaseService{
	IRenewalsDAO renewalsDAO;
	private final static Logger LOGGER = Logger.getLogger( RenewalSvc.class );
	public void setRenewalsDAO( IRenewalsDAO renewalsDAO ){
		this.renewalsDAO = renewalsDAO;
	}

	@Override
	public Object invokeMethod( String methodName, Object... args ){
		BaseVO returnValue = null;
		if( "getPoliciesToBeRenewed".equals( methodName ) ){
			returnValue = getPoliciesToBeRenewed( (BaseVO) args[ 0 ] );
		} else if( "savePoliciesForBatchRenewal".equals( methodName ) ){
			savePoliciesForBatchRenewal( (BaseVO) args[ 0 ] );
		} else if("updateRenewalPremium".equals(methodName)){
			updateRenewalPremium( (BaseVO) args[ 0 ] );
		} else if("updateQuotationStatus".equals(methodName)){
			updateQuotationStatus( (BaseVO) args[ 0 ] );
		} else if("updateRenewalTerms".equals(methodName)){
			updateRenewalTerms( (BaseVO) args[ 0 ] );
		} else if("generateOnlineRenewal".equals(methodName)){
			returnValue = generateOnlineRenewal( (BaseVO) args[ 0 ] );
		} else if("getClaimCount".equals(methodName)){
			returnValue = getClaimCount( (BaseVO) args[ 0 ] );
		}else if("getEndorsementData".equals(methodName)){
			returnValue = getEndorsementData( (BaseVO) args[ 0 ] );
		}else if("getOSPremium".equals(methodName)){
			returnValue = getOSPremium( (BaseVO) args[ 0 ] );
		} else if ("getDisLoadPer".equals(methodName)){
			returnValue = getDisLoadPer( (BaseVO) args[ 0 ] );
		} else if ("getBrAccStatus".equals(methodName)){
			returnValue = getBrAccStatus( (BaseVO) args[ 0 ] );
		} else if( "savePoliciesForBatchPrint".equals( methodName ) ){
			savePoliciesForBatchPrint( (BaseVO) args[ 0 ] );
		} else if("getRenewalQuotations".equals(methodName ) ){
			returnValue = getRenewalQuotations( (BaseVO) args[ 0 ] );
		} else if("checkForReprint".equals(methodName ) ){
			returnValue = checkForReprint( (BaseVO) args[ 0 ] );
		} else if("fetchPolicyExpDate".equals(methodName ) ){
			returnValue = fetchPolicyExpDate( (BaseVO) args[ 0 ] );
		} else if("generateRenewal".equals(methodName ) ){
			returnValue = generateRenewal( (BaseVO) args[ 0 ] );
		} else if("fetchRenewalStatusReport".equals(methodName)){
			returnValue = fetchRenewalStatusReport((BaseVO) args[ 0 ]);
		}else if ("getDisLoadPerFromQuo".equals(methodName)){
			returnValue = getDisLoadPerFromQuo( (BaseVO) args[ 0 ] );
		} else if( "checkRenewalTradeLicense".equals( methodName ) ){
			checkRenewalTradeLicense( (BaseVO) args[ 0 ] );
		} else if("getRenewalQuotationsForEmail".equals(methodName)){
			returnValue =getRenewalQuotationsForEmail((BaseVO)args[0]);
		}
		
		return returnValue;
	}
	
	private BaseVO getDisLoadPerFromQuo(BaseVO baseVO) {
		return renewalsDAO.getDisLoadPerFromQuo(baseVO);
		
	}

	private BaseVO generateRenewal( BaseVO baseVO ){
		ITask rating;
		DataHolderVO<Object[]> inputholderVO = (DataHolderVO<Object[]>) baseVO;
		Object[] inputrenData = inputholderVO.getData();
		//Long polLinkingId = (Long)inputrenData[0];
		Integer userID = (Integer)inputrenData[1]; 
		
		DataHolderVO<Object[]> holderVO = (DataHolderVO<Object[]>) renewalsDAO.generateRenewal(baseVO);
		Object[] renData = holderVO.getData();
		//Long quotationNo = (Long)renData[0];
		Long renLinkingId = (Long)renData[1];
		//Long originalLinkingId = (Long)renData[2];
		PolicyVO policyDetailsVO = (PolicyVO) renData[3];
		
		LOGGER.debug( "Calling rating engine for renewal quotation." );
		boolean ratingSuccess= false;
		PolicyVO  polVO =  createPolicyObject (renLinkingId);
		polVO.setRenewals( policyDetailsVO.getRenewals() );
		PremiumCalculator premiumCalculator= new PremiumCalculator();
		BigDecimal renewalLoading=new BigDecimal(0); 
		try{
			
			polVO = premiumCalculator.calculateRiskPremium( polVO, renewalLoading );
			ratingSuccess= true;
		}catch(BusinessException be){
			throw new BusinessException( "pas.renewal.ratingfail", be, "Renewal quotation generation process is not complete due to rating error. Please contact Administrator." );
		}
		LOGGER.debug( "Calling rating engine for renewal quotation for BI section." );
		// This rating call is for BI
		try{
			SectionVO biSection = getSectionVO(  polVO, Integer.parseInt( Utils.getSingleValueAppConfig( "BI_SECTION" ) ) );
			if(!Utils.isEmpty(biSection)){
				if(!Utils.isEmpty(biSection.getRiskGroupDetails())){
					Map <? extends RiskGroup, ? extends RiskGroupDetails> riskGroupDetailsMap=biSection.getRiskGroupDetails();
						for (Map.Entry<? extends RiskGroup, ? extends RiskGroupDetails> entry : riskGroupDetailsMap.entrySet()) {
								if(!Utils.isEmpty(entry)){
									RiskGroupDetails riskDetails = entry.getValue();
										if(!Utils.isEmpty(riskDetails)){
											riskDetails.setPremium( null);
										}
								}
					
						}
						ratingSuccess= false;
						polVO = premiumCalculator.calculateRiskPremium( polVO, renewalLoading );
						ratingSuccess= true;
				}
			}
		} catch(BusinessException be){
			throw new BusinessException( "pas.renewal.ratingfail", be, "Renewal quotation generation process is not complete due to rating error. Please contact Administrator." );
		}
		
		// Call service to update the premium and policy records with latest premium
		Object renInputData[] = new Object[2];
		DataHolderVO<Object[]> input =  new DataHolderVO<Object[]>();
		if(ratingSuccess){
			renInputData[0] = polVO;
			renInputData[1] = userID;
			input.setData(renInputData);
			LOGGER.debug( "Calling update premium method." );
			updateRenewalPremium( input ); 
		}
		
		return holderVO;
	}
	
	private SectionVO getSectionVO( PolicyVO policy, int sectionId ){
		SectionVO finderSection = new SectionVO( RiskGroupingLevel.LOCATION );
		finderSection.setSectionId( sectionId );
		if(policy.getRiskDetails().contains( finderSection )){
			return policy.getRiskDetails().get( policy.getRiskDetails().indexOf( finderSection ) );
		}
		return null;
	}

	public PolicyVO createPolicyObject( Long linkingId ){
		
		LOGGER.debug( "Calling createPolicyObject method." );
		
		SectionDAO section = (SectionDAO) Utils.getBean("sectionDAO");
		SectionSvc sectionSvc = (SectionSvc) Utils.getBean("sectionSvc");
		CommonOpDAO commonOp = (CommonOpDAO) Utils.getBean("commonOpDAO");
		//PolicyVO policy = new PolicyVO();
		
		PolicyVO policyDetails = null;
		LoadExistingInputVO existingInputVO = new LoadExistingInputVO();
		//existingInputVO.setPolLinkingId( Long.valueOf( linkingId ) );
		existingInputVO.setPolLinkingId( linkingId );
		existingInputVO.setEndtId( (long) 0 );
		existingInputVO.setQuote( true );
		existingInputVO.setAppFlow( Flow.RENEWAL );
		existingInputVO.setPolicyStatus( Integer.parseInt( Utils.getSingleValueAppConfig( "QUOTE_ACTIVE" ) ) );
		existingInputVO.setSectionId( SvcConstants.SECTION_ID_GEN_INFO );
		
		//policyDetails = (PolicyVO) TaskExecutor.executeTasks( SvcConstants.GENERAL_INFO_FETCH, existingInputVO );
		policyDetails = (PolicyVO) sectionSvc.loadSectionData( existingInputVO );
		
		policyDetails.setAppFlow( Flow.RENEWAL );
		policyDetails.setIsQuote( true );
		//policyDetails = (PolicyVO) TaskExecutor.executeTasks( SvcConstants.SET_PRE_PACKAGE_FLAG, policyDetails );
		policyDetails = (PolicyVO)commonOp.setPrepackedFlag ( policyDetails);
		
		//policyDetails.setPolLinkingId( new Long( linkingId ) );
		policyDetails.setPolLinkingId( linkingId );
		policyDetails.setEndtId( (long) 0 );
	
		existingInputVO.setPolicyStatus( policyDetails.getStatus() );
		//SectionList output = (SectionList) TaskExecutor.executeTasks( SvcConstants.FETCH_SELECTED_SECTIONS, existingInputVO );
		SectionList output = (SectionList)section.getSectionListForPolicy( existingInputVO );
		Integer sectionIds[] = null ;
		/* Set the selected sections to the policy context. */
		if( !Utils.isEmpty( output ) && !Utils.isEmpty( output.getSelectedSec() ) ){
			 sectionIds = CopyUtils.toArray( output.getSelectedSec() );
			Arrays.sort( sectionIds );
		
		}
		createSectionsInThePolicy (policyDetails,sectionIds);
		//sonar fix to avoid  Null passed for nonnull parameter on 28-9-2017
		try{
		   fetchSectionDetails(policyDetails,sectionIds);
		}catch (NullPointerException e) {
			LOGGER.debug("Null pointer exception while fetching section details");
		}
		
		LOGGER.info( "Exiting createPolicyObject method of RenewalSvc." );
		return policyDetails;
	}
	
	private void fetchSectionDetails( PolicyVO policyDetails, Integer[] sectionIds ){
		for( Integer sectionId : sectionIds ){
			LoadExistingInputVO input = constructInput(policyDetails, sectionId );
			SectionVO section = loadSectionData( input );
			replaceSection( policyDetails, section );
		}
		
	}
	
	private void replaceSection( PolicyVO policyDetails, SectionVO section ){
		java.util.List<SectionVO> sections = policyDetails.getRiskDetails();
		
		int sectionIndex = sections.indexOf( section );
		
		if( sectionIndex >= 0 ) 
			sections.set( sectionIndex, section );
	}
	
	 /*
	  *  This method creates the VO to be used to load the existing data form the database for given linking Id
	  */
	private LoadExistingInputVO constructInput(PolicyVO policyDetails, Integer sectionId ){
		LoadExistingInputVO existingInputVO = new LoadExistingInputVO();
		
		
			/* If a section Id has been passed, that gets the highest priority. Then to "jumpToSectionId" passed in the request and
			 * finally, to the current section Id from the Policy Context. */
			existingInputVO.setSectionId( sectionId );
			
			if( !Utils.isEmpty( policyDetails ) ){
				existingInputVO.setPolLinkingId( policyDetails.getPolLinkingId() );
				existingInputVO.setEndtId( policyDetails.getEndtId() );
				existingInputVO.setQuote( policyDetails.getIsQuote() );
				existingInputVO.setIsPrepackaged( policyDetails.getIsPrepackaged() );
				existingInputVO.setBasicSectionId( com.rsaame.pas.svc.utils.PolicyUtils.getBasicSectionId( policyDetails ) );
				existingInputVO.setPolicyStatus( policyDetails.getStatus() );
				existingInputVO.setTariffCode(policyDetails.getScheme().getTariffCode());
				existingInputVO.setAppFlow(Flow.RENEWAL);
			}
	
		return existingInputVO;
	}
	/**
	 * Calls SectionSvc.loadSectionData() to get the populated SectionVO for the section with all the saved location data.
	 * 
	 * @param input
	 * @return 
	 */
	private SectionVO loadSectionData( LoadExistingInputVO input ){
		SectionSvc sectionSvc = (SectionSvc) Utils.getBean("sectionSvc");
		/* Calling loadSectionData method of SectionSvc */
		SectionVO section = (SectionVO) sectionSvc.loadSectionData( input );
		Map <? extends RiskGroup, ? extends RiskGroupDetails> riskGroupDetailsMap=section.getRiskGroupDetails();
		for (Map.Entry<? extends RiskGroup, ? extends RiskGroupDetails> entry : riskGroupDetailsMap.entrySet()) {
			RiskGroupDetails riskDetails = entry.getValue();
			if(!Utils.isEmpty(riskDetails)){
				riskDetails.setPremium( null );
			}	
		}
		return section;
	}
	

	/**
	 * Creates SectionVO instances for all selected sections.
	 * 
	 */
	private void createSectionsInThePolicy( PolicyVO pol,Integer[] sectionIds){
		//if( !pc.isSectionVOsCreationDone() ){
			
			for( Integer sectionId : sectionIds ){	
				
				SectionVO section = new SectionVO( RiskGroupingLevel.LOCATION ); /* TODO This may not be correct because there can be a 
																					 * RiskGroupLevel other than LOCATION. */
				section.setSectionId( sectionId );
				section.setSectionName( SvcUtils.getLookUpDescription( "SBS_SECTIONS", "ALL", "ALL", sectionId ) );


				List<SectionVO> riskDetailsList = pol.getRiskDetails();

				/* If the sections list is empty, create a new one here. */
				if( Utils.isEmpty( riskDetailsList ) ){
					riskDetailsList = new com.mindtree.ruc.cmn.utils.List<SectionVO>( SectionVO.class );
					pol.setRiskDetails( riskDetailsList );
				}

				/* If the sections list is not empty, then add this section if it is not already present. */
				if( !riskDetailsList.contains( section ) ){
					//fillLocationsIntoSection( section );
					riskDetailsList.add( section );
				}

				/* This setting back to policyDetails is necessary if the list was instantiated. */
				pol.setRiskDetails( riskDetailsList );
				
			}

			/* Set the flag to indicate that SectionVO creation for all sections is complete. */
			//pc.setSectionVOsCreationDone( true );
		//}
	}

	private BaseVO fetchPolicyExpDate(BaseVO baseVO) {
		return renewalsDAO.fetchPolicyExpDate(baseVO);
	}

	private void updateRenewalTerms(BaseVO baseVO) {
		renewalsDAO.updateRenewalTerms( baseVO );
	}

	private BaseVO getBrAccStatus(BaseVO baseVO) {
		return renewalsDAO.getBrAccStatus(baseVO);
	}

	private BaseVO getDisLoadPer(BaseVO baseVO) {
		return renewalsDAO.getDisLoadPer(baseVO);
	}

	private BaseVO getPoliciesToBeRenewed ( BaseVO baseVO){
		return renewalsDAO.getPoliciesToBeRenewed( baseVO );
	}
	
	private void savePoliciesForBatchRenewal(BaseVO baseVO){
		renewalsDAO.savePoliciesForBatchRenewal( baseVO );
	}
	
	private void updateRenewalPremium(BaseVO baseVO){
		renewalsDAO.updateRenewalPremium( baseVO );
	}
	
	private void updateQuotationStatus(BaseVO baseVO){
		renewalsDAO.updateQuotationStatus( baseVO );
	}
	private BaseVO  generateOnlineRenewal(BaseVO baseVO){
		return renewalsDAO.generateOnlineRenewal( baseVO );
	}
	
	private BaseVO getClaimCount(BaseVO baseVO){
		return renewalsDAO.getClaimCount(baseVO);
	}

	private BaseVO getEndorsementData(BaseVO baseVO){
		return renewalsDAO.getEndorsementData(baseVO);
	}
	private BaseVO getOSPremium(BaseVO baseVO){
		return renewalsDAO.getOSPremium(baseVO);
	}
	
	private void savePoliciesForBatchPrint(BaseVO baseVO){
		renewalsDAO.savePoliciesForBatchPrint( baseVO );
	}
	private BaseVO getRenewalQuotations ( BaseVO baseVO){
		return renewalsDAO.getRenewalQuotations( baseVO );
	}
	private BaseVO checkForReprint ( BaseVO baseVO){
		return renewalsDAO.checkForReprint( baseVO );
	}
	private BaseVO fetchRenewalStatusReport(BaseVO baseVO) {
		return renewalsDAO.fetchRenewalStatusReport( baseVO );
		
	}
	private void checkRenewalTradeLicense(BaseVO baseVO){
		renewalsDAO.checkRenewalTradeLicense( baseVO );
	}
	private BaseVO getRenewalQuotationsForEmail ( BaseVO baseVO){
		return renewalsDAO.getRenewalQuotationsForEmail( baseVO );
	}
	
}
