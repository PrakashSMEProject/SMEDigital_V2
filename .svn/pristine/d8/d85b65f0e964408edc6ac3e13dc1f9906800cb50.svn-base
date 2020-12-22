package com.rsaame.pas.b2b.ws.mapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.b2b.ws.constant.ServiceConstant; 
import com.rsaame.pas.b2b.ws.util.SBSWsAppConstants;
import com.rsaame.pas.b2b.ws.vo.CreateSBSQuoteRequest;
import com.rsaame.pas.b2b.ws.vo.request.FirePreventiveMeasure;
import com.rsaame.pas.b2b.ws.vo.request.GroupPersonalAccident;
import com.rsaame.pas.b2b.ws.vo.request.NamedEmployeesDetail;
import com.rsaame.pas.b2b.ws.vo.request.NamedEmployeesDetail_;
import com.rsaame.pas.b2b.ws.vo.request.NamedEmployeesDetail__;
import com.rsaame.pas.b2b.ws.vo.request.RiskContructionType;
import com.rsaame.pas.b2b.ws.vo.request.UnnamedEmployeesDetail;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.app.Contents;
import com.rsaame.pas.vo.bus.BIUWDetailsVO;
import com.rsaame.pas.vo.bus.BIVO;
import com.rsaame.pas.vo.bus.CashResidenceVO;
import com.rsaame.pas.vo.bus.DeteriorationOfStockDetailsVO;
import com.rsaame.pas.vo.bus.DeteriorationOfStockUWDetailsVO;
import com.rsaame.pas.vo.bus.DeteriorationOfStockVO;
import com.rsaame.pas.vo.bus.EEUWDetailsVO;
import com.rsaame.pas.vo.bus.EEVO;
import com.rsaame.pas.vo.bus.EmpTypeDetailsVO;
import com.rsaame.pas.vo.bus.EquipmentVO;
import com.rsaame.pas.vo.bus.FidelityNammedEmployeeDetailsVO;
import com.rsaame.pas.vo.bus.FidelityUnnammedEmployeeVO;
import com.rsaame.pas.vo.bus.FidelityVO;
import com.rsaame.pas.vo.bus.GPANammedEmpVO;
import com.rsaame.pas.vo.bus.GroupPersonalAccidentVO;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.MBUWDetailsVO;
import com.rsaame.pas.vo.bus.MBVO;
import com.rsaame.pas.vo.bus.MachineDetailsVO;
import com.rsaame.pas.vo.bus.MoneyVO;
import com.rsaame.pas.vo.bus.PARUWDetailsVO;
import com.rsaame.pas.vo.bus.PLUWDetails;
import com.rsaame.pas.vo.bus.ParVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.bus.PropertyRiskDetails;
import com.rsaame.pas.vo.bus.PropertyRisks;
import com.rsaame.pas.vo.bus.PublicLiabilityVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.RiskGroupingLevel;
import com.rsaame.pas.vo.bus.SectionVO;
import com.rsaame.pas.vo.bus.SumInsuredVO;
import com.rsaame.pas.vo.bus.TravelBaggageVO;
import com.rsaame.pas.vo.bus.TravellingEmployeeVO;
import com.rsaame.pas.vo.bus.UWQuestionRespType;
import com.rsaame.pas.vo.bus.UWQuestionVO;
import com.rsaame.pas.vo.bus.UWQuestionsVO;
import com.rsaame.pas.vo.bus.WCCoversVO;
import com.rsaame.pas.vo.bus.WCNammedEmployeeVO;
import com.rsaame.pas.vo.bus.WCVO;

public class SectionRequestMapper  {
	
	private final static Logger LOGGER = Logger.getLogger(SectionRequestMapper.class);

	public void mapRequestToVO(Object requestObj, Object valueObj,LocationVO locationVO , HttpServletRequest request) {
		
		CreateSBSQuoteRequest createSBSQuoteRequest = (CreateSBSQuoteRequest) requestObj;
		PolicyVO policyVO = (PolicyVO) valueObj;
		Boolean checkWcSectionAlone = false;
		
		List<Integer> listOfSections = getApplicableSections(createSBSQuoteRequest);
		
		//checkWcSectionAlone = checkWcBaseSection(listOfSections);
		
		checkWcSectionAlone =checkIsWCOnlyBaseSection(listOfSections);
		
		locationVO.setToSave(true);
		
		if(checkWcSectionAlone) {	// PAR Dummy Values in case of WC section alone
			SectionVO sectionVo = new SectionVO(RiskGroupingLevel.LOCATION);// as of now set to LOCATION as per debug
			sectionVo.setSectionId(SvcConstants.SECTION_ID_PAR); // we cannot set a class code explicitly. So this setSectionId setter will in- turn set the applicable class code
			sectionVo.setCommission(new Double(SvcUtils.getLookUpCodeForLOneLTwo("PAS_COMMISSION",policyVO.getScheme().getSchemeCode().toString(),policyVO.getGeneralInfo().getSourceOfBus().getPartnerId()))); 
			sectionVo.setSectionName(SvcUtils.getLookUpDescription( "SBS_SECTIONS", "ALL", "ALL", SvcConstants.SECTION_ID_PAR));
			java.util.LinkedHashMap<RiskGroup, RiskGroupDetails> riskGroupDetails = new LinkedHashMap<RiskGroup, RiskGroupDetails>();
			
			LOGGER.info("Request mapping for PAR section with sectionID : " + SvcConstants.SECTION_ID_PAR);
			locationVO.setRiskGroupId("L1"); //analyze and set proper value to this. this changes for each section
			
			riskGroupDetails.put(locationVO, getPARDummySection(locationVO,createSBSQuoteRequest));
			sectionVo.setRiskGroupDetails(riskGroupDetails);
			
			policyVO.getRiskDetails().add(sectionVo);
		}
		
		for( Integer secId :listOfSections) {
			
		SectionVO sectionVo = new SectionVO(RiskGroupingLevel.LOCATION);// as of now set to LOCATION as per debug
		sectionVo.setSectionId(secId); // we cannot set a class code explicitly. So this setSectionId setter will in- turn set the applicable class code
		sectionVo.setCommission(new Double(SvcUtils.getLookUpCodeForLOneLTwo("PAS_COMMISSION",policyVO.getScheme().getSchemeCode().toString(),policyVO.getGeneralInfo().getSourceOfBus().getPartnerId()))); 
		sectionVo.setSectionName(SvcUtils.getLookUpDescription( "SBS_SECTIONS", "ALL", "ALL", secId));
		
		
		java.util.LinkedHashMap<RiskGroup, RiskGroupDetails> riskGroupDetails = new LinkedHashMap<RiskGroup, RiskGroupDetails>();
		Integer buildingId = null;
		if(secId==1) { //PAR
			LOGGER.info("Request mapping for PAR section with sectionID : " + secId);
			locationVO.setRiskGroupId("L1"); //analyze and set proper value to this. this changes for each section
			riskGroupDetails.put(locationVO, getPARVO(createSBSQuoteRequest,policyVO,locationVO));
		}
		
		if(secId==6) { //PL
			
			LOGGER.info("Request mapping for PL section with sectionID : " + secId);
			locationVO.setRiskGroupId("L2"); //analyze and set proper value to this. this changes for each section
			riskGroupDetails.put(locationVO, getPLVO(createSBSQuoteRequest,policyVO));
		}
		
		if(secId==7) { //WC
			
			LOGGER.info("Request mapping for WC section with sectionID : " + secId);

			locationVO.setRiskGroupId("L3"); //analyze and set proper value to this. this changes for each section
			riskGroupDetails.put(locationVO, getWCVO(createSBSQuoteRequest,policyVO));
		}
		if(secId==2) { //BI
			
			LOGGER.info("Request mapping for BI section with sectionID : " + secId);

			locationVO.setRiskGroupId("L4"); //analyze and set proper value to this. this changes for each section
			riskGroupDetails.put(locationVO, getBIVO(createSBSQuoteRequest,policyVO));
		}
		if(secId==8) {
			LOGGER.info("Request mapping for Money section with sectionID : " + secId);

			locationVO.setRiskGroupId("L5"); //analyze and set proper value to this. this changes for each section
			riskGroupDetails.put(locationVO, getMoneyVO(createSBSQuoteRequest, policyVO));
		}
		if(secId==9) {
			LOGGER.info("Request mapping for FG section with sectionID : " + secId);

			locationVO.setRiskGroupId("L6"); //analyze and set proper value to this. this changes for each section
			riskGroupDetails.put(locationVO, getFidelityVO(createSBSQuoteRequest, policyVO));
		}
		if(secId==5) {
			LOGGER.info("Request mapping for Electronic Equipment section with sectionID : " + secId);

			locationVO.setRiskGroupId("L7"); //analyze and set proper value to this. this changes for each section
			riskGroupDetails.put(locationVO, getEEVO(createSBSQuoteRequest, policyVO));
		}
		if(secId==3) {
			LOGGER.info("Request mapping for Machinery Breakdown section with sectionID : " + secId);

			locationVO.setRiskGroupId("L8"); //analyze and set proper value to this. this changes for each section
			riskGroupDetails.put(locationVO, getMBVO(createSBSQuoteRequest, policyVO));
		}
		if(secId==12) {
			LOGGER.info("Request mapping for Group Personal Accident section with sectionID : " + secId);

			locationVO.setRiskGroupId("L9"); //analyze and set proper value to this. this changes for each section
			riskGroupDetails.put(locationVO, getGPAVO(createSBSQuoteRequest, policyVO));
		}
		if(secId==4) {
			LOGGER.info("Request mapping for Deterioration Of Stock section with sectionID : " + secId);

			locationVO.setRiskGroupId("L10"); //analyze and set proper value to this. this changes for each section
			riskGroupDetails.put(locationVO, getDOSVO(createSBSQuoteRequest, policyVO));
		}
		
		if(secId==10) {
			LOGGER.info("Request mapping for Travel Baggage section with sectionID : " + secId);

			locationVO.setRiskGroupId("L11"); //analyze and set proper value to this. this changes for each section L11
			riskGroupDetails.put(locationVO, getTBVO(createSBSQuoteRequest, policyVO));
		}
		sectionVo.setRiskGroupDetails(riskGroupDetails);

		
		policyVO.getRiskDetails().add(sectionVo);
		
		}
	}
	
	
	public List<Integer> getApplicableSections(CreateSBSQuoteRequest createSBSQuoteRequest){
		
		List<Integer> listOfSections = new ArrayList<Integer>();
		
		//PAR section
		if ((createSBSQuoteRequest.getLiabilityInformation().getPropertyValue()!=null && createSBSQuoteRequest.getLiabilityInformation().getPropertyValue().getAmount()!=null && createSBSQuoteRequest.getLiabilityInformation().getPropertyValue().getAmount() > 0)
				|| (createSBSQuoteRequest.getLiabilityInformation().getPropertyContentValue()!=null && createSBSQuoteRequest.getLiabilityInformation().getPropertyContentValue().getAmount()!=null  && createSBSQuoteRequest.getLiabilityInformation().getPropertyContentValue().getAmount()>0) 
				|| (createSBSQuoteRequest.getLiabilityInformation().getStock()!=null && createSBSQuoteRequest.getLiabilityInformation().getStock().getAmount()!=null  && createSBSQuoteRequest.getLiabilityInformation().getStock().getAmount()>0) 
				|| (createSBSQuoteRequest.getLiabilityInformation().getAnnualRentPayable()!=null  && createSBSQuoteRequest.getLiabilityInformation().getAnnualRentPayable() > 0)) {
			
		
					listOfSections.add(1);
		}
		
		//PL section
		if (createSBSQuoteRequest.getLiabilityInformation().getPublicLiabilityLimit()!=null 
				&& createSBSQuoteRequest.getLiabilityInformation().getPublicLiabilityLimit().getAmount()!=null 
				&& Double.parseDouble(createSBSQuoteRequest.getLiabilityInformation().getPublicLiabilityLimit().getAmount()) > 0
					&& createSBSQuoteRequest.getPolicyHolder().getCompany()!=null 
					&& createSBSQuoteRequest.getPolicyHolder().getCompany().getRevenue()!=null 
					&& createSBSQuoteRequest.getPolicyHolder().getCompany().getRevenue().getAmount()!=null
					&& createSBSQuoteRequest.getPolicyHolder().getCompany().getRevenue().getAmount() > 0) {
					
					listOfSections.add(6);
		}
		
		//WC section
		if ( (!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getWorkmenAdminCompensation()) && createSBSQuoteRequest.getLiabilityInformation().getWorkmenAdminCompensation().getAmount()>0 )
			|| (!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getWorkmenNonAdminCompensation()) && createSBSQuoteRequest.getLiabilityInformation().getWorkmenNonAdminCompensation().getAmount()>0)) {
							
					listOfSections.add(7);
		}
		
		//BI section
		if ((createSBSQuoteRequest.getLiabilityInformation().getLossOfGrossProfitLimit()!=null 
				&& createSBSQuoteRequest.getLiabilityInformation().getLossOfGrossProfitLimit().getAmount()!=null 
					&& createSBSQuoteRequest.getLiabilityInformation().getLossOfGrossProfitLimit().getAmount() > 0)
				|| (createSBSQuoteRequest.getLiabilityInformation().getAnnualRentReceivable()!=null 
				      && createSBSQuoteRequest.getLiabilityInformation().getAnnualRentReceivable() > 0 )
				|| (createSBSQuoteRequest.getLiabilityInformation().getRentAndIcowLimit()!=null
					  && createSBSQuoteRequest.getLiabilityInformation().getRentAndIcowLimit().getAmount()!=null
					  && createSBSQuoteRequest.getLiabilityInformation().getRentAndIcowLimit().getAmount()>0)) {
							
					listOfSections.add(2);
		}
		//EE section
		if (((createSBSQuoteRequest.getLiabilityInformation().getPortableEquipmentLimit()!=null 
				&& createSBSQuoteRequest.getLiabilityInformation().getPortableEquipmentLimit().getAmount()!=null
					&& createSBSQuoteRequest.getLiabilityInformation().getPortableEquipmentLimit().getAmount() > 0)) 
				|| ((createSBSQuoteRequest.getLiabilityInformation().getComputerBreakdownLimit()!=null 
						&& createSBSQuoteRequest.getLiabilityInformation().getComputerBreakdownLimit().getAmount()!=null
						&& createSBSQuoteRequest.getLiabilityInformation().getComputerBreakdownLimit().getAmount() > 0))) {
								
						listOfSections.add(5);
			}
		
		//MB section
		if (createSBSQuoteRequest.getLiabilityInformation().getMachineryBreakdownLimit()!=null && createSBSQuoteRequest.getLiabilityInformation().getMachineryBreakdownLimit().getAmount()!=null
				&& createSBSQuoteRequest.getLiabilityInformation().getMachineryBreakdownLimit().getAmount() > 0) {
								
						listOfSections.add(3);
			}
		//DOS section
		if (createSBSQuoteRequest.getLiabilityInformation().getStockGuaranteeLimit()!=null && createSBSQuoteRequest.getLiabilityInformation().getStockGuaranteeLimit().getAmount()!=null 
				&& createSBSQuoteRequest.getLiabilityInformation().getStockGuaranteeLimit().getAmount() > 0) {
								
						listOfSections.add(4);
			}
		//Travel Baggage section
				if (createSBSQuoteRequest.getLiabilityInformation().getTravelBaggage()!=null && createSBSQuoteRequest.getLiabilityInformation().getTravelBaggage().getNamedEmployeesDetail()!=null) {
									
					boolean allEmpDetailsPresent = false;
					
					for(NamedEmployeesDetail_ namedEmployee: createSBSQuoteRequest.getLiabilityInformation().getTravelBaggage().getNamedEmployeesDetail()) {
						
						if(namedEmployee.getSumInsured()!=null && namedEmployee.getSumInsured() > 0
								&& namedEmployee.getName()!=null
								&& namedEmployee.getDateOfBirth()!=null) {
							
							allEmpDetailsPresent = true;
						}
						else {
							
							allEmpDetailsPresent = false;
							break;
						}
						
								
						}
					
					if(allEmpDetailsPresent) {
						
						listOfSections.add(10);
					}
					
					
				}
		
		
		// Money Section
		if((!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getAnnualCarryingEstimate()) 
			&& createSBSQuoteRequest.getLiabilityInformation().getAnnualCarryingEstimate() > 0) ||
				(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getMaxValuePerTransit()) 
				&& createSBSQuoteRequest.getLiabilityInformation().getMaxValuePerTransit() > 0) ||
				(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getMaxValuePerTransit()) 
				&& createSBSQuoteRequest.getLiabilityInformation().getMaxValuePerTransit() > 0)
				) {
			listOfSections.add(8);
		}
		
		// Fidelity Guarantee
		if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee()) ) {
			
			boolean allNamedEmpDetailsPresent = false;
			boolean allUnNamedEmpDetailsPresent = false;


			if(createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee().getNamedEmployeesDetail()!=null) {
				
				for(NamedEmployeesDetail__ namedEmployee : createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee().getNamedEmployeesDetail() ) {
					
					if(namedEmployee.getName()!=null && namedEmployee.getCategory()!=null && namedEmployee.getDesignation()!=null &&  namedEmployee.getSumInsured()!=null && namedEmployee.getSumInsured()>0) {
						// commented namedEmployee.getDateOfBirth()!=null && namedEmployee.getGender()!=null && ;; as this is not required.
						allNamedEmpDetailsPresent = true;
					}
					else {
						allNamedEmpDetailsPresent = false;
					}
				}
			}
			
			if(createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee().getUnnamedEmployeesDetail()!=null) {
				
				if((createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee().getUnnamedEmployeesDetail().getCashHandelingInsured()!=null && createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee().getUnnamedEmployeesDetail().getCashHandelingInsured()>0 && createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee().getUnnamedEmployeesDetail().getCashHandelingEmployeesCount()!=null && createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee().getUnnamedEmployeesDetail().getCashHandelingEmployeesCount()>0)
					|| createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee().getUnnamedEmployeesDetail().getNonCashHandelingInsured()!=null && createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee().getUnnamedEmployeesDetail().getNonCashHandelingInsured()>0 && createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee().getUnnamedEmployeesDetail().getNonCashHandelingEmployeesCount()!=null && createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee().getUnnamedEmployeesDetail().getNonCashHandelingEmployeesCount()>0) {
					
					allUnNamedEmpDetailsPresent = true;
				}
				
			}
			if (allNamedEmpDetailsPresent || allUnNamedEmpDetailsPresent) {
				listOfSections.add(9);
			}
		}
		if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getGroupPersonalAccident()) ) {
			listOfSections.add(12);
		}
		return listOfSections;
	}
	
	public String getSectionName(int sectionId) {
		
		String sectionName = null;
		
		switch( sectionId ){
		case 1:
			sectionName = SvcConstants.PAR_NAME;
			break;
		case 6:
			sectionName = SvcConstants.PL_NAME;
			break;
		case 8:
			sectionName = SvcConstants.MONEY_NAME;
			break;
		case 7:
			sectionName = SvcConstants.WC_NAME;
			break;
		case 2:
			sectionName = SvcConstants.BI_NAME;
			break;
		case 5:
			sectionName = SvcConstants.EE_NAME;
			break;
			//sonar fix
		default:
			break;
			
		
	}
		return sectionName;
	}

	@SuppressWarnings("unlikely-arg-type")
	public ParVO getPARVO(CreateSBSQuoteRequest createSBSQuoteRequest,PolicyVO policyVO,LocationVO locationVO) {
		
		ParVO parVO = new ParVO();
		
		Double buildingSI;
		//Map building cover - 1st row on PAR page -- begin
		if((createSBSQuoteRequest.getLiabilityInformation().getPropertyValue()!=null && createSBSQuoteRequest.getLiabilityInformation().getPropertyValue().getAmount()!=null && createSBSQuoteRequest.getLiabilityInformation().getPropertyValue().getAmount() > 0)) {
		
			parVO.setBuilCovered(1); 
						
			
			
		buildingSI = new Double( createSBSQuoteRequest.getLiabilityInformation().getPropertyValue().getAmount());
		parVO.setBldCover(buildingSI);
		parVO.setBldDeductibles(new Double( Utils.getSingleValueAppConfig( "JLT_PAR_BUILDING_DEDUCTIBLE" ))); //For JLT
		parVO.setBldDesc(com.Constant.CONST_TO_BE_PROVIDED);
		}
		else {
			parVO.setBuilCovered(0); //needed for batch
			buildingSI = new Double(0.0);
		}
		//Map building cover - 1st row on PAR page -- end

		parVO.setBldPremium(new PremiumVO());
		
		//map PropertyRisks - other 3 rows on PAR page (list of 3 objects) 1 for each cover.
		parVO.setCovers(getPropertyRisks(createSBSQuoteRequest));
		
		//mapping UWQuestions for PAR
		UWQuestionsVO uwQuestionsVO = new UWQuestionsVO();
		java.util.List<UWQuestionVO> questions = new com.mindtree.ruc.cmn.utils.List<UWQuestionVO>(UWQuestionVO.class);
		
		UWQuestionVO question1 = new UWQuestionVO();
		question1.setQId((short)1);
		question1.setQDesc("100% Sprinklers Coverage Installed");
		question1.setResponseType(UWQuestionRespType.RADIO);
		//passing yes by default
		question1.setResponse("yes");
		/*if(createSBSQuoteRequest.getLiabilityInformation().getFirePreventiveMeasures()!=null)
		{
			 if(createSBSQuoteRequest.getLiabilityInformation().getFirePreventiveMeasures().contains(new FirePreventiveMeasure().withCode("1")))
			 {
			
			question1.setResponse("yes");

		}
		else {
			
			question1.setResponse("yes");

		}
		}
		else
       {
			
			question1.setResponse("yes");

		}*/
			
		questions.add(question1);

		UWQuestionVO question2 = new UWQuestionVO();
		question2.setQId((short)2);
		question2.setQDesc("Storage Exposure(hazardous goods/chemicals)");
		question2.setResponseType(UWQuestionRespType.RADIO);
		question2.setResponse("no");
		questions.add(question2);
		
		UWQuestionVO question3 = new UWQuestionVO();
		question3.setQId((short)3);
		question3.setQDesc("Alarms Installed");
		question3.setResponseType(UWQuestionRespType.RADIO);
		//passing yes by default
		question3.setResponse("yes");
		/*if(createSBSQuoteRequest.getLiabilityInformation().getFirePreventiveMeasures()!=null)
		{
			if(createSBSQuoteRequest.getLiabilityInformation().getFirePreventiveMeasures().contains(new FirePreventiveMeasure().withCode("3")))
					{
				
			question3.setResponse("yes");

		}
		else {
				
				question3.setResponse("no");

		}
		}
		else
		{
			question3.setResponse("yes");
		}*/
		questions.add(question3);
		
		UWQuestionVO question7 = new UWQuestionVO();
		question7.setQId((short)7);
		question7.setQDesc("Construction Type RCC ");
		question7.setResponseType(UWQuestionRespType.RADIO);
		//passing yes by default
		question7.setResponse("yes");
		/*if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getRiskContructionType()) && (createSBSQuoteRequest.getLiabilityInformation().getRiskContructionType().getCode()) !=null)
		{
				if(createSBSQuoteRequest.getLiabilityInformation().getRiskContructionType().getCode().equals("7")){
				
			question7.setResponse("yes");

		}
		else {
					
			question7.setResponse("no");

		}
		}
		else { 
				
			question7.setResponse("no");

		}	*/
				
	
		questions.add(question7);
		
		uwQuestionsVO.setQuestions(questions);
		uwQuestionsVO.setCascaded(false);
		
		parVO.setUwQuestions(uwQuestionsVO);
		
		
		//mapping PARUWDetails
		PARUWDetailsVO parUWDetailsVO = new PARUWDetailsVO();
		
		Integer hazardlevel =3;		 //hardcoded as of now. Need analysis to calculate level.
		if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getbusinessType())) {
			hazardlevel= SvcUtils.getLookUpCodeForLOneLTwo("HAZARD_CODE",createSBSQuoteRequest.getLiabilityInformation().getbusinessType().getCode() , "ALL").intValue();
		}
		
		parUWDetailsVO.setHazardLevel(hazardlevel);
		parUWDetailsVO.setHazardousNature(3); //hardcoded as of now. Need analysis to calculate level.
		parUWDetailsVO.setCategoryRI(202);
		parUWDetailsVO.setDirectorate(16002);
		parUWDetailsVO.setEmlPrc(new Double( Utils.getSingleValueAppConfig( "BLD_MPL_FIRE_PERC" ) )); //required for batch
		
		Double contentSI;
		if(createSBSQuoteRequest.getLiabilityInformation().getPropertyContentValue()!=null && createSBSQuoteRequest.getLiabilityInformation().getPropertyContentValue().getAmount()!=null) {
			
			contentSI = new Double(createSBSQuoteRequest.getLiabilityInformation().getPropertyContentValue().getAmount());
		}
		
		else {
			
			contentSI = new Double(0.0);
		}
		
		Double rentSI;
		
		if(createSBSQuoteRequest.getLiabilityInformation().getAnnualRentPayable()!=null) {
			
			rentSI = new Double(createSBSQuoteRequest.getLiabilityInformation().getAnnualRentPayable());
		}
		else {
			
			rentSI = new Double(0.0);
		}
		
		
		Double stockSI;
		if(createSBSQuoteRequest.getLiabilityInformation().getStock()!=null && createSBSQuoteRequest.getLiabilityInformation().getStock().getAmount()!=null) {
			
			stockSI = new Double(createSBSQuoteRequest.getLiabilityInformation().getStock().getAmount());
		}
		
		else {
			
			stockSI = new Double(0.0);
		}
		
		parUWDetailsVO.setEmlSI(buildingSI+contentSI+rentSI+stockSI); //required for batch
		if(createSBSQuoteRequest.getLiabilityInformation().getNameOfFreeZoneAuthority()!=null && createSBSQuoteRequest.getLiabilityInformation().getNameOfFreeZoneAuthority().getCode()!=null) {
			
			parUWDetailsVO.setDirectorate(locationVO.getDirectorate());
		}
		
		parVO.setUwDetails(parUWDetailsVO);
		
		return parVO;
	}
	public PublicLiabilityVO getPLVO(CreateSBSQuoteRequest createSBSQuoteRequest,PolicyVO policyVO) {
		
		PublicLiabilityVO publicLiabilityVO= new PublicLiabilityVO();
		
		SumInsuredVO sumInsuredVO = new SumInsuredVO();
		sumInsuredVO.setSumInsured(new Double(createSBSQuoteRequest.getPolicyHolder().getCompany().getRevenue().getAmount()));
		sumInsuredVO.setDeductible(new Double(Utils.getSingleValueAppConfig( "JLT_PL_DEDUCTIBLE" ))); //For JLT.
		//sumInsuredVO.setCash_Id(new Long(1)); // not sure of this
		sumInsuredVO.setPromoCover(false);
		publicLiabilityVO.setPremium(new PremiumVO());
		publicLiabilityVO.setSumInsuredDets(sumInsuredVO);
		/*
		 * Ceiling the value to 3M if request comes with 2.5M
		 */
		if(Double.parseDouble(Utils.getSingleValueAppConfig(com.Constant.CONST_JLT_LIMIT))==Double.parseDouble(createSBSQuoteRequest.getLiabilityInformation().getPublicLiabilityLimit().getAmount().toString())) {
			createSBSQuoteRequest.getLiabilityInformation().getPublicLiabilityLimit().setAmount(3000000.00);
			publicLiabilityVO.setIndemnityAmtLimit(SvcUtils.getLookUpCode("JLT_PL_LIM", policyVO.getScheme().getTariffCode().toString(), "ALL", new Double(createSBSQuoteRequest.getLiabilityInformation().getPublicLiabilityLimit().getAmount()).intValue()+""));
		}
		else if(Double.parseDouble(createSBSQuoteRequest.getLiabilityInformation().getPublicLiabilityLimit().getAmount()) > Double.parseDouble(Utils.getSingleValueAppConfig(com.Constant.CONST_JLT_PL_WC_LIMIT))) {
			publicLiabilityVO.setIndemnityAmtLimit(8);  // setting 15M code to avoid rating exception
			publicLiabilityVO.setLiabilityLimit(Double.parseDouble(createSBSQuoteRequest.getLiabilityInformation().getPublicLiabilityLimit().getAmount()));
		}
		else 
			publicLiabilityVO.setIndemnityAmtLimit(SvcUtils.getLookUpCode("JLT_PL_LIM", policyVO.getScheme().getTariffCode().toString(), "ALL", new Double(createSBSQuoteRequest.getLiabilityInformation().getPublicLiabilityLimit().getAmount()).intValue()+""));
	
		publicLiabilityVO.setSumInsuredBasis(1); //1 for first value in the dropdown and 2 for next
		//publicLiabilityVO.setWbdId(1L); //not sure of this.
		
		//mapping UWQuestions for PL
				UWQuestionsVO plUwQuestionsVO = new UWQuestionsVO();
				java.util.List<UWQuestionVO> plQuestions = new com.mindtree.ruc.cmn.utils.List<UWQuestionVO>(UWQuestionVO.class);
				
				UWQuestionVO question5 = new UWQuestionVO();
				question5.setQId((short)5);
				question5.setQDesc("Do you need work away extension?");
				question5.setResponseType(UWQuestionRespType.RADIO);
				if(createSBSQuoteRequest.getLiabilityInformation().getWorkAwayRisksLimit()!=null && createSBSQuoteRequest.getLiabilityInformation().getWorkAwayRisksLimit()>0) {
					
						question5.setResponse("yes");
					

				}
				else {
					
					question5.setResponse("no");
				}
				
				plQuestions.add(question5);

				UWQuestionVO question6 = new UWQuestionVO();
				question6.setQId((short)6);
				question6.setQDesc("If yes, please provide details.");
				question6.setResponseType(UWQuestionRespType.TEXT);
				
				if(createSBSQuoteRequest.getLiabilityInformation().getWorkAwayRisksLimit()!=null && createSBSQuoteRequest.getLiabilityInformation().getWorkAwayRisksLimit()>0) {
					// WorkAwayLimit cannot be greater than PublicLiabiltyLimit,if it is greater then setting it to PublicLiabiltyLimit M1043209
					if(createSBSQuoteRequest.getLiabilityInformation().getWorkAwayRisksLimit().doubleValue() > Double.parseDouble(createSBSQuoteRequest.getLiabilityInformation().getPublicLiabilityLimit().getAmount())) {
						question6.setResponse(createSBSQuoteRequest.getLiabilityInformation().getPublicLiabilityLimit().getAmount().toString());
					}
					else {
						question6.setResponse(createSBSQuoteRequest.getLiabilityInformation().getWorkAwayRisksLimit().toString());
					}
				}
				else {
						
					question6.setResponse("");
				}
					
				plQuestions.add(question6);
				
				UWQuestionVO question57 = new UWQuestionVO();
				question57.setQId((short)57);
				question57.setQDesc("Do you need Student liability extension?");
				question57.setResponseType(UWQuestionRespType.RADIO);
				
				if (createSBSQuoteRequest.getLiabilityInformation().getStudentCount() != null && createSBSQuoteRequest.getLiabilityInformation().getStudentCount() > 0) {
					
					question57.setResponse("yes");
				} else {

					question57.setResponse("no");

				}
				
				plQuestions.add(question57);
				
				UWQuestionVO question58 = new UWQuestionVO();
				question58.setQId((short)58);
				question58.setQDesc("If yes, please provide number of students(up to a maximum of 200)");
				question58.setResponseType(UWQuestionRespType.TEXT);
				
				if (createSBSQuoteRequest.getLiabilityInformation().getStudentCount() != null && createSBSQuoteRequest.getLiabilityInformation().getStudentCount() > 0) {
					
					question58.setResponse(createSBSQuoteRequest.getLiabilityInformation().getStudentCount().toString());
				} else {

					question58.setResponse("");

				}
				plQuestions.add(question58);
				
				plUwQuestionsVO.setQuestions(plQuestions);
				publicLiabilityVO.setUwQuestions(plUwQuestionsVO);
				
				//mapping PLUWDetails
				
				PLUWDetails plUWDetailsVO = new PLUWDetails();
				plUWDetailsVO.setHazardLevel(3); //hardcoded as of now. Need analysis to calculate level.
				plUWDetailsVO.setCategoryRI(202); // need analysis
				
				publicLiabilityVO.setUwDetails(plUWDetailsVO);
				
		
		return publicLiabilityVO;
	}
	
public BIVO getBIVO(CreateSBSQuoteRequest createSBSQuoteRequest,PolicyVO policyVO) {
		Double estimatedGrossIncome = new Double(0);
		Double rentRecievable = new Double(0);
		Double rentAndIcowLimit = new Double(0);
		com.mindtree.ruc.cmn.utils.List<SumInsuredVO> sumInsuredVOs = new com.mindtree.ruc.cmn.utils.List<SumInsuredVO>(SumInsuredVO.class);
		SumInsuredVO sumInsuredVO=new SumInsuredVO();
		if(createSBSQuoteRequest.getLiabilityInformation().getLossOfGrossProfitLimit()!=null && createSBSQuoteRequest.getLiabilityInformation().getLossOfGrossProfitLimit().getAmount()!=null) {
			estimatedGrossIncome = new Double(createSBSQuoteRequest.getLiabilityInformation().getLossOfGrossProfitLimit().getAmount());
			sumInsuredVO.setaDesc(SvcConstants.BI_ESTIMATED_GROSS_INCOME); 
			sumInsuredVOs.add(sumInsuredVO);
		}
		
		if(createSBSQuoteRequest.getLiabilityInformation().getRentAndIcowLimit()!=null && createSBSQuoteRequest.getLiabilityInformation().getRentAndIcowLimit().getAmount()!=null) {
			
			rentAndIcowLimit = new Double(createSBSQuoteRequest.getLiabilityInformation().getRentAndIcowLimit().getAmount());
			sumInsuredVO.setaDesc(SvcConstants.BI_INCR_COST_WORKING_LIMIT); 
			sumInsuredVOs.add(sumInsuredVO);

		}  
		
		if(createSBSQuoteRequest.getLiabilityInformation().getAnnualRentReceivable()!=null) {
			
			rentRecievable = new Double(createSBSQuoteRequest.getLiabilityInformation().getAnnualRentReceivable());
			sumInsuredVO.setaDesc(SvcConstants.BI_RENT_RECEIVABLE); 
			sumInsuredVOs.add(sumInsuredVO);

		}
		
	
		BIVO biVO= new BIVO();
		biVO.setIndemnityPeriod(12); //hardcoded as per mapping sheet
		biVO.setDeductible(3); //hardcoded as per debug log
		biVO.setEstimatedGrossIncome(estimatedGrossIncome); 
		
		if(createSBSQuoteRequest.getLiabilityInformation().getAnnualRentReceivable()!=null 
				&& createSBSQuoteRequest.getLiabilityInformation().getAnnualRentReceivable()>0) {
			
		biVO.setRentRecievable(rentRecievable);
		
		}
		
		if(createSBSQuoteRequest.getLiabilityInformation().getRentAndIcowLimit()!=null  && createSBSQuoteRequest.getLiabilityInformation().getRentAndIcowLimit().getAmount()!=null
				&& createSBSQuoteRequest.getLiabilityInformation().getRentAndIcowLimit().getAmount()>0) {
			
		biVO.setWorkingLimit(rentAndIcowLimit);
		
		}
		biVO.setWorkingLimitCommited(false); //as per debug log
		//biVO.setHazardLevel(3); //hardcoded for time being. Need more analysis
		biVO.setPremiumCommited(false); //as per debug log
		
		biVO.setBiCwsEGIncomeId(null); //As required by Batch
		biVO.setBiCwsRentId(null); //As required by Batch
		biVO.setBiCwsAcwlId(null); //As required by Batch
		biVO.setSumInsured(estimatedGrossIncome + rentAndIcowLimit + rentRecievable); //add all covers at the end to the base
		
		
		
		//NO UW Questions for BI section in the application UI
		UWQuestionsVO biUwQuestionsVO = new UWQuestionsVO(); //required for batch - only instantiation
		biVO.setUwQuestions(biUwQuestionsVO);
		
		//UWDetails as reuired by Batch
		BIUWDetailsVO biUWDetailsVO = new BIUWDetailsVO();
		biUWDetailsVO.setEmlPrc(new Double(100.00)); //default for broker - as per debug log
		biUWDetailsVO.setEmlSI(new Double(biVO.getSumInsured())); // required by batch - its always 100% for broker JS calculation
		biVO.setUwDetails(biUWDetailsVO);
		
		return biVO;
	}

	public EEVO getEEVO (CreateSBSQuoteRequest createSBSQuoteRequest,PolicyVO policyVO) {
	
		EEVO eeVO = new EEVO();
		java.util.List<EquipmentVO> equipmentDtlsList = new com.mindtree.ruc.cmn.utils.List<EquipmentVO>(EquipmentVO.class);
		
		Double portableSI=0.0;
		//For potable equipment -- begin 
		if(createSBSQuoteRequest.getLiabilityInformation().getPortableEquipmentLimit()!=null && createSBSQuoteRequest.getLiabilityInformation().getPortableEquipmentLimit().getAmount()!=null && createSBSQuoteRequest.getLiabilityInformation().getPortableEquipmentLimit().getAmount()>0) {
		EquipmentVO portableEquipVO = new EquipmentVO();
		SumInsuredVO sumInsuredDetailsPortable = new SumInsuredVO();
		
		portableSI = new Double(createSBSQuoteRequest.getLiabilityInformation().getPortableEquipmentLimit().getAmount());
		
		sumInsuredDetailsPortable.setSumInsured(portableSI);
		sumInsuredDetailsPortable.setDeductible(new Double(750));
		sumInsuredDetailsPortable.setPromoCover(false);
		
		portableEquipVO.setEquipmentType("4"); //4 for portable
		portableEquipVO.setSumInsuredDetails(sumInsuredDetailsPortable);
		
		equipmentDtlsList.add(portableEquipVO);
		}
		//For potable equipment -- end
		
		Double nonPortableSI=0.0;
		if(createSBSQuoteRequest.getLiabilityInformation().getComputerBreakdownLimit()!=null && createSBSQuoteRequest.getLiabilityInformation().getComputerBreakdownLimit().getAmount()!=null && createSBSQuoteRequest.getLiabilityInformation().getComputerBreakdownLimit().getAmount()>0) {
		//For NON- potable equipment -- begin (this is mapped from ComputerBreakdown Limit from JSON)
		EquipmentVO nonPortableEquipVO = new EquipmentVO();
		SumInsuredVO sumInsuredDetailsNonPortable = new SumInsuredVO();
		
		nonPortableSI = new Double(createSBSQuoteRequest.getLiabilityInformation().getComputerBreakdownLimit().getAmount());
		
		sumInsuredDetailsNonPortable.setSumInsured(nonPortableSI);
		sumInsuredDetailsNonPortable.setDeductible(new Double(750));
		sumInsuredDetailsNonPortable.setPromoCover(false);
		
		nonPortableEquipVO.setEquipmentType("6"); //6 for non - portable
		nonPortableEquipVO.setSumInsuredDetails(sumInsuredDetailsNonPortable);
		
		equipmentDtlsList.add(nonPortableEquipVO);
	}
		//For NON- potable equipment -- end
		
		eeVO.setEquipmentDtls(equipmentDtlsList);
		//eeVO.setHazardLevel(3);
		eeVO.setSumInsured(portableSI+nonPortableSI);
		eeVO.setPremium(new PremiumVO());// just instantiate to avoid null
		
		EEUWDetailsVO eeuwDetailsVO = new EEUWDetailsVO();
		eeuwDetailsVO.setEmlSI(nonPortableSI+portableSI);
		eeuwDetailsVO.setEmlPrc(100.0);    			// Hard coding to 100 as it is constant value set in JSP
		eeuwDetailsVO.setEmlBI(0.0);
		eeuwDetailsVO.setEmlBIPrc(0.0);
		
		eeVO.setUwDetails(eeuwDetailsVO);
		
		return eeVO;
	}
	
	
	
	
	public MBVO getMBVO(CreateSBSQuoteRequest createSBSQuoteRequest,PolicyVO policyVO) {
		
		MBVO mbVO = new MBVO();
		
		List<MachineDetailsVO> machineryDetailsList = new com.mindtree.ruc.cmn.utils.List<MachineDetailsVO>(MachineDetailsVO.class);
		
		//Other machinery -- begin
		MachineDetailsVO machineryOther = new MachineDetailsVO();
		machineryOther.setMachineryType(3);
		
		SumInsuredVO otherMachinerySumInsuredVO = new SumInsuredVO();
		Double otherMachineryValue = new Double(createSBSQuoteRequest.getLiabilityInformation().getMachineryBreakdownLimit().getAmount());
		otherMachinerySumInsuredVO.setSumInsured(otherMachineryValue);
		otherMachinerySumInsuredVO.setDeductible(new Double(750));
		machineryOther.setSumInsuredVO(otherMachinerySumInsuredVO);
		
		Contents otherMachineryContent = new Contents();
		otherMachineryContent.setBasicRiskCode( new Integer( Utils.getSingleValueAppConfig( "MB_BASIC_RISK_CODE" ) ) );
		otherMachineryContent.setClassCode( new Short( Utils.getSingleValueAppConfig( "MB_CLASS" ) ) );
		otherMachineryContent.setSecId( new Short( Utils.getSingleValueAppConfig( "MB_SECTION" ) ) );
		otherMachineryContent.setRiskType( new Integer( Utils.getSingleValueAppConfig( "MB_RISK_TYPE" ) ) );
		otherMachineryContent.setRiskCode( Integer.valueOf( Utils.getSingleValueAppConfig( "MB_CONTENT_RISK" ) ) );
		otherMachineryContent.setRiskDtl( Long.valueOf( Integer.valueOf( Utils.getSingleValueAppConfig( "MB_RISK_DTL" ) ) ) );	
		otherMachineryContent.setCoverId(Long.valueOf( Integer.valueOf( Utils.getSingleValueAppConfig( "MB_COVER" ) ) )); //required by batch
		otherMachineryContent.setCoverId(null);
		otherMachineryContent.setCover(new BigDecimal(otherMachineryValue)); //required by batch
		machineryOther.setContents(otherMachineryContent);
		//machineryOther.setIsToBeDeleted(false);// required by batch async
				
		machineryDetailsList.add(machineryOther);
		
		//Other machinery -- end

		
		mbVO.setMachineryDetails(machineryDetailsList);
		mbVO.setSumInsured(otherMachineryValue); //need to sum it up if any other cover is mapped later
		mbVO.setPremium(new PremiumVO());
		
		//Set UW questions
		
		
		UWQuestionsVO mbUwQuestionsVO = new UWQuestionsVO();
		java.util.List<UWQuestionVO> mbQuestionsList = new com.mindtree.ruc.cmn.utils.List<UWQuestionVO>(UWQuestionVO.class);
		
		UWQuestionVO question10 = new UWQuestionVO();
		question10.setQId((short)10);
		question10.setQDesc("Is there a maintenance programme in place?");
		question10.setResponseType(UWQuestionRespType.RADIO);
		question10.setResponse("yes");
		mbQuestionsList.add(question10);

		UWQuestionVO question11 = new UWQuestionVO();
		question11.setQId((short)11);
		question11.setQDesc("Is there a backup facility?");
		question11.setResponseType(UWQuestionRespType.RADIO);
		question11.setResponse("yes");
		mbQuestionsList.add(question11);
		
		UWQuestionVO question12 = new UWQuestionVO();
		question12.setQId((short)12);
		question12.setQDesc("Is a log-book maintained?");
		question12.setResponseType(UWQuestionRespType.RADIO);
		question12.setResponse("yes");
		mbQuestionsList.add(question12);
		
		mbUwQuestionsVO.setQuestions(mbQuestionsList);
		mbUwQuestionsVO.setCascaded(false);
		mbVO.setUwQuestions(mbUwQuestionsVO);
		
		
		//UWDetails as reuired by Batch
		MBUWDetailsVO mbUWDetailsVO = new MBUWDetailsVO();
		mbUWDetailsVO.setEmlPrc(new Double(100.00));
		mbUWDetailsVO.setEmlSI(new Double(otherMachineryValue)); //always 100% for broker
		mbUWDetailsVO.setEmlBIPrc(new Double(0.0)); //always 0.0 for broker as per debug
		mbUWDetailsVO.setEmlBI(new Double(0.0)); //always 0.0 for broker as per debug
		mbVO.setUwDetails(mbUWDetailsVO);
		return mbVO;
	}

	
	public DeteriorationOfStockVO getDOSVO(CreateSBSQuoteRequest createSBSQuoteRequest,PolicyVO policyVO) {
		
		DeteriorationOfStockVO dosVO = new DeteriorationOfStockVO();
		
		dosVO.setRiskCode(Integer.parseInt(Utils.getSingleValueAppConfig("DOS_RISK_CODE"))); 	
		dosVO.setBasicRiskcode(Integer.parseInt(Utils.getSingleValueAppConfig("DOS_BASIC_RISK_CODE")));				 	
		dosVO.setBasicRiskId(1L);
		dosVO.setRiskCategory(Integer.parseInt(Utils.getSingleValueAppConfig("DOS_COVER_TYPE")));						
		dosVO.setRiskCode(Integer.parseInt(Utils.getSingleValueAppConfig("DOS_PRM_BASIC_RISK_CODE"))); 					
		dosVO.setRiskSubCategory(Integer.parseInt(Utils.getSingleValueAppConfig("DOS_COVER_SUB_TYPE")));  
		dosVO.setRiskType(Integer.parseInt(Utils.getSingleValueAppConfig("DOS_RISK_TYPE_CODE")));						

				

		
		List<DeteriorationOfStockDetailsVO> deteriorationOfStockDetailsList = new com.mindtree.ruc.cmn.utils.List<DeteriorationOfStockDetailsVO>(DeteriorationOfStockDetailsVO.class);
		
		//for frozen food = type=1
		DeteriorationOfStockDetailsVO frozenfoodDOSVO = new DeteriorationOfStockDetailsVO();
		frozenfoodDOSVO.setDeteriorationOfStockType("1");
	
		SumInsuredVO forzenfoodSumInsured = new SumInsuredVO();
		Double frozenfoodSI = new Double(createSBSQuoteRequest.getLiabilityInformation().getStockGuaranteeLimit().getAmount());
		forzenfoodSumInsured.setSumInsured(frozenfoodSI);
		forzenfoodSumInsured.setDeductible(new Double(750));
		
		frozenfoodDOSVO.setSumInsuredDetails(forzenfoodSumInsured);
		frozenfoodDOSVO.setPremium(new PremiumVO());
		frozenfoodDOSVO.setContentId(-9999L); //required by Batch
		
		deteriorationOfStockDetailsList.add(frozenfoodDOSVO);
		dosVO.setDeteriorationOfStockDetails(deteriorationOfStockDetailsList);
		
		
		//mapping UWQuestions for DOS
		UWQuestionsVO dosUwQuestionsVO = new UWQuestionsVO();
		java.util.List<UWQuestionVO> dosQuestionsList = new com.mindtree.ruc.cmn.utils.List<UWQuestionVO>(UWQuestionVO.class);
		
		UWQuestionVO question14 = new UWQuestionVO();
		question14.setQId((short)14);
		question14.setQDesc("Is the Stock Compartmentalized");
		question14.setResponseType(UWQuestionRespType.RADIO);
		question14.setResponse("yes");
		dosQuestionsList.add(question14);

		UWQuestionVO question15 = new UWQuestionVO();
		question15.setQId((short)15);
		question15.setQDesc("Is there a Maintenance Programme");
		question15.setResponseType(UWQuestionRespType.RADIO);
		question15.setResponse("yes");
		dosQuestionsList.add(question15);
		
		UWQuestionVO question16 = new UWQuestionVO();
		question16.setQId((short)16);
		question16.setQDesc("Is there a Backup Facility");
		question16.setResponseType(UWQuestionRespType.RADIO);
		question16.setResponse("yes");
		dosQuestionsList.add(question16);
		
		UWQuestionVO question17 = new UWQuestionVO();
		question17.setQId((short)17);
		question17.setQDesc("Is  a Log-book Maintained");
		question17.setResponseType(UWQuestionRespType.RADIO);
		question17.setResponse("yes");
		dosQuestionsList.add(question17);
		
		dosUwQuestionsVO.setQuestions(dosQuestionsList);
		dosUwQuestionsVO.setCascaded(false); //required for Batch
		
		dosVO.setUwQuestions(dosUwQuestionsVO);
		dosVO.setSumInsured(frozenfoodSI);
		dosVO.setPremium(new PremiumVO());
		
		DeteriorationOfStockUWDetailsVO deteriorationOfStockUWDetailsVO = new DeteriorationOfStockUWDetailsVO();
		deteriorationOfStockUWDetailsVO.setEmlPercentage(100.0); 	// Hard coding the percentage value, as it is hard coded in JSP
		deteriorationOfStockUWDetailsVO.setEmlSI(frozenfoodSI);
		
		dosVO.setDeteriorationOfStockUWDetails(deteriorationOfStockUWDetailsVO);
		
		return dosVO;
	}
	
	
	public WCVO getWCVO(CreateSBSQuoteRequest createSBSQuoteRequest,PolicyVO policyVO) {
		
		WCVO wcVO = new WCVO();
	//	LOGGER.debug("Entered in WCVO mapping method...");
		if (!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getWorkmenAdminCompensation())
				|| !Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getWorkmenNonAdminCompensation())) {

			
			com.mindtree.ruc.cmn.utils.List<EmpTypeDetailsVO> empTypeDetailsVOs = new com.mindtree.ruc.cmn.utils.List<EmpTypeDetailsVO>(EmpTypeDetailsVO.class);
			WCCoversVO coversVO = new WCCoversVO();
			List<WCNammedEmployeeVO> nammedEmployeeVOs = new ArrayList<WCNammedEmployeeVO>();
			int noOfEmp=0;
			if (!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getWorkmenAdminCompensation())) {
				EmpTypeDetailsVO empTypeDetailsVO = new EmpTypeDetailsVO();
				empTypeDetailsVO.setDeductibles(new BigDecimal(Double.parseDouble(Utils.getSingleValueAppConfig(com.Constant.CONST_WC_DEDUCTIBLES)))); 
				empTypeDetailsVO.setEmpType(ServiceConstant.WC_ADMIN_CODE); 										
				empTypeDetailsVO.setRiskId(ServiceConstant.WC_RISK_ID);
				empTypeDetailsVO.setWageroll(createSBSQuoteRequest.getLiabilityInformation()
						.getWorkmenAdminCompensation().getAmount().doubleValue());
				empTypeDetailsVO.setNoOfEmp(createSBSQuoteRequest.getLiabilityInformation().getAdminHeadCount());

				Integer limit = new Double(createSBSQuoteRequest.getLiabilityInformation().getEmployerLiabilityLimit().getAmount()).intValue();
				/*
				 * Ceiling the value to 3M if request comes with 2.5M
				 */
				Integer code = 8;
				if (limit== Integer.parseInt(Utils.getSingleValueAppConfig(com.Constant.CONST_JLT_LIMIT).toString())) {
					limit = 3000000;
					code = SvcUtils.getLookUpCode(com.Constant.CONST_JLT_WC_LIM, "" + policyVO.getScheme().getTariffCode(), "ALL",
							limit.toString());
					createSBSQuoteRequest.getLiabilityInformation().getEmployerLiabilityLimit().setAmount(3000000.00);
				}else if(limit > Integer.parseInt(Utils.getSingleValueAppConfig(com.Constant.CONST_JLT_PL_WC_LIMIT))) {
					empTypeDetailsVO.setLiabilityLimit(new Double(limit));
				}
				else {
					code = SvcUtils.getLookUpCode(com.Constant.CONST_JLT_WC_LIM, "" + policyVO.getScheme().getTariffCode(), "ALL",
							limit.toString());
				}
				
				empTypeDetailsVO.setLimit(new BigDecimal(code));
				empTypeDetailsVOs.add(empTypeDetailsVO);
				noOfEmp = createSBSQuoteRequest.getLiabilityInformation().getAdminHeadCount();
			}
			if (!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getWorkmenNonAdminCompensation())) {
				EmpTypeDetailsVO empTypeDetailsVO = new EmpTypeDetailsVO();
				empTypeDetailsVO.setDeductibles(new BigDecimal(Double.parseDouble(Utils.getSingleValueAppConfig(com.Constant.CONST_WC_DEDUCTIBLES)))); 					
				empTypeDetailsVO.setEmpType(ServiceConstant.WC_NON_ADMIN_CODE); 
				empTypeDetailsVO.setRiskId(ServiceConstant.WC_RISK_ID);
				empTypeDetailsVO.setWageroll(createSBSQuoteRequest.getLiabilityInformation()
						.getWorkmenNonAdminCompensation().getAmount().doubleValue());
				empTypeDetailsVO.setNoOfEmp(createSBSQuoteRequest.getLiabilityInformation().getNonAdminHeadCount());
				if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getEmployerLiabilityLimit())) {
					Integer limit = new Double (createSBSQuoteRequest.getLiabilityInformation().getEmployerLiabilityLimit().getAmount()).intValue();
					/*
					 * Ceiling the value to 3M if request comes with 2.5M
					 */
					Integer code = 8;		 // setting 15M code to avoid rating exception
					if(limit== Integer.parseInt(Utils.getSingleValueAppConfig(com.Constant.CONST_JLT_LIMIT).toString())) {
						limit = 3000000;
						createSBSQuoteRequest.getLiabilityInformation().getEmployerLiabilityLimit().setAmount(3000000.00);
						code = SvcUtils.getLookUpCode(com.Constant.CONST_JLT_WC_LIM, "" + policyVO.getScheme().getTariffCode(), "ALL",
								limit.toString());
					}else if(limit > Integer.parseInt(Utils.getSingleValueAppConfig(com.Constant.CONST_JLT_PL_WC_LIMIT))) {
						empTypeDetailsVO.setLiabilityLimit(new Double(limit));
					}
					else {
						code = SvcUtils.getLookUpCode(com.Constant.CONST_JLT_WC_LIM, "" + policyVO.getScheme().getTariffCode(), "ALL",
								limit.toString());
					}
					empTypeDetailsVO.setLimit(new BigDecimal(code));
				}
				empTypeDetailsVOs.add(empTypeDetailsVO);
				
				noOfEmp += createSBSQuoteRequest.getLiabilityInformation().getNonAdminHeadCount();
			}

			coversVO.setPACover(true);			// hard coding for time being

			wcVO.setDeductibles(Double.parseDouble(Utils.getSingleValueAppConfig(com.Constant.CONST_WC_DEDUCTIBLES))); 
			wcVO.setWcCovers(coversVO);
			wcVO.setEmpTypeDetails((com.mindtree.ruc.cmn.utils.List<EmpTypeDetailsVO>) empTypeDetailsVOs);
			
			if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getNameOfFreeZoneAuthority())) {
				WCNammedEmployeeVO employeeVO = new WCNammedEmployeeVO();
				/*
				 * For Auto Populating no of employees to employeeDetails whenever DMCC freezone it is there
				 */
//				if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getNameOfFreeZoneAuthority().getCode()) && Integer.parseInt(createSBSQuoteRequest.getLiabilityInformation().getNameOfFreeZoneAuthority().getCode())==55017){
					employeeVO.setEmpName("No. of the Employees : "+noOfEmp);
					employeeVO.setSumInsured(0);
//				}
				nammedEmployeeVOs.add(employeeVO);
			}
			
			wcVO.setWcEmployeeDetails(nammedEmployeeVOs); 			// Named employees mapping is remaining			

		}
	//	LOGGER.debug("Existing from WCVO mapping method...");
		return wcVO;
	}
	
	public TravelBaggageVO getTBVO(CreateSBSQuoteRequest createSBSQuoteRequest,PolicyVO policyVO) {
		
		TravelBaggageVO tbVO = new TravelBaggageVO();
		
		java.util.List<TravellingEmployeeVO> travellingEmpDetsList =  new com.mindtree.ruc.cmn.utils.List<TravellingEmployeeVO>(TravellingEmployeeVO.class);
		
		 Double TotalSI = new Double(0.0);
		for(NamedEmployeesDetail_ namedEmployee: createSBSQuoteRequest.getLiabilityInformation().getTravelBaggage().getNamedEmployeesDetail()) {
			
			TravellingEmployeeVO travellingEmpVO = new TravellingEmployeeVO();
			travellingEmpVO.setName(namedEmployee.getName());
			travellingEmpVO.setDateOfBirth(namedEmployee.getDateOfBirth());
			SumInsuredVO sumInsuredVO = new SumInsuredVO();
			sumInsuredVO.setSumInsured(new Double(namedEmployee.getSumInsured()));
			sumInsuredVO.setDeductible(new Double(500)); //to resolve TB section DOB format issue
			travellingEmpVO.setSumInsuredDtl(sumInsuredVO);
			travellingEmpVO.setSumInsured(new Double(namedEmployee.getSumInsured()));
			TotalSI+= namedEmployee.getSumInsured();
			travellingEmpDetsList.add(travellingEmpVO);
		}
		
		tbVO.setTravellingEmpDets(travellingEmpDetsList);
		tbVO.setSumInsured(TotalSI);
		
		return tbVO;
	}
	
	public MoneyVO getMoneyVO(CreateSBSQuoteRequest createSBSQuoteRequest, PolicyVO policyVO) {

		MoneyVO moneyVO = new MoneyVO();
		//LOGGER.debug("Entered in MoneyVO mapping method...");
		
		moneyVO.setContentsList(new ArrayList<>());
		List<SumInsuredVO> sumInsuredVOs = new ArrayList<>();

		// Annual carrying estimate mapping
		Contents annualCarryingEstimateContents = new Contents();
		SumInsuredVO annualCarryingEstimateSumInsuredVO = new SumInsuredVO();
		if (!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getAnnualCarryingEstimate())) {
			annualCarryingEstimateContents.setCover(
					new BigDecimal(createSBSQuoteRequest.getLiabilityInformation().getAnnualCarryingEstimate()));
			annualCarryingEstimateSumInsuredVO.setSumInsured(
					createSBSQuoteRequest.getLiabilityInformation().getAnnualCarryingEstimate().doubleValue());
		} else {
			annualCarryingEstimateContents.setCover(new BigDecimal(0.0));
			annualCarryingEstimateSumInsuredVO.setSumInsured(0.0);
		}
		annualCarryingEstimateContents.setRiskType(1); // Hard coding for time being
		annualCarryingEstimateContents.setRiskCat(1); // Hard coding for time being
		annualCarryingEstimateContents.setRiskSubCat(0); // Hard coding for time being
		annualCarryingEstimateContents.setDeductibles(new BigDecimal(0.0)); // Hard coding for time being
		annualCarryingEstimateContents.setRiskId(-9999L); // Hard coding for time being

		annualCarryingEstimateSumInsuredVO.setDeductible(0.0);

		moneyVO.getContentsList().add(annualCarryingEstimateContents);
		sumInsuredVOs.add(annualCarryingEstimateSumInsuredVO);

		// Money In transit Mapping
		Contents moneyInTransitLimitContents = new Contents();
		SumInsuredVO moneyInTransitLimitSumInsuredVO = new SumInsuredVO();

		if (!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getMaxValuePerTransit())) {
			moneyInTransitLimitContents.setCover(new BigDecimal(
					createSBSQuoteRequest.getLiabilityInformation().getMaxValuePerTransit()));
			moneyInTransitLimitSumInsuredVO.setSumInsured(
					createSBSQuoteRequest.getLiabilityInformation().getMaxValuePerTransit().doubleValue());
		} else {
			moneyInTransitLimitContents.setCover(new BigDecimal(0.0));
			moneyInTransitLimitSumInsuredVO.setSumInsured(0.0);
		}
		moneyInTransitLimitContents.setRiskType(1); // Hard coding for time being
		moneyInTransitLimitContents.setRiskCat(2); // Hard coding for time being
		moneyInTransitLimitContents.setRiskSubCat(0); // Hard coding for time being
		moneyInTransitLimitContents.setDeductibles(new BigDecimal(0.0)); // Hard coding for time being
		moneyInTransitLimitContents.setRiskId(-9999L); // Hard coding for time being
		moneyVO.getContentsList().add(moneyInTransitLimitContents);

		moneyInTransitLimitSumInsuredVO.setDeductible(0.0);
		sumInsuredVOs.add(moneyInTransitLimitSumInsuredVO);

		// Cash locked in Safe During Business hour mapping

		Contents contentsSafeDuringHour = new Contents();
		contentsSafeDuringHour.setCover(new BigDecimal(0));
		contentsSafeDuringHour.setRiskType(3); // Hard coding for time being for cash during business hour
		contentsSafeDuringHour.setRiskCat(0); // Hard coding for time being
		contentsSafeDuringHour.setRiskSubCat(0); // Hard coding for time being
		contentsSafeDuringHour.setDeductibles(new BigDecimal(0.0)); // Hard coding for time being
		contentsSafeDuringHour.setRiskId(-9999L); // Hard coding for time being
		moneyVO.getContentsList().add(contentsSafeDuringHour);

		SumInsuredVO sumInsuredVOSafeDuringHour = new SumInsuredVO();
		sumInsuredVOSafeDuringHour.setSumInsured(0.0);
		sumInsuredVOSafeDuringHour.setDeductible(0.0);

		sumInsuredVOs.add(sumInsuredVOSafeDuringHour);

		// Cash locked in Drawer During Business hour mapping

		Contents contentsDuringBusinessHour = new Contents();
		contentsDuringBusinessHour.setCover(new BigDecimal(0));
		contentsDuringBusinessHour.setRiskType(8); // Hard coding for time being for cash during business hour
		contentsDuringBusinessHour.setRiskCat(0); // Hard coding for time being
		contentsDuringBusinessHour.setRiskSubCat(0); // Hard coding for time being
		contentsDuringBusinessHour.setDeductibles(new BigDecimal(0.0)); // Hard coding for time being
		contentsDuringBusinessHour.setRiskId(-9999L); // Hard coding for time being
		moneyVO.getContentsList().add(contentsDuringBusinessHour);

		SumInsuredVO sumInsuredVODrawerDuringHour = new SumInsuredVO();
		sumInsuredVODrawerDuringHour.setSumInsured(0.0);
		sumInsuredVODrawerDuringHour.setDeductible(0.0);

		sumInsuredVOs.add(sumInsuredVODrawerDuringHour);

		// Cash locked in Safe After Business hour mapping
		Contents contentsSafeAfterBusinessHour = new Contents();
		SumInsuredVO safeAfterBusinessHourSumInsuredVO = new SumInsuredVO();

		if (!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getMoneyInLockedSafe())) {
			contentsSafeAfterBusinessHour
					.setCover(new BigDecimal(createSBSQuoteRequest.getLiabilityInformation().getMoneyInLockedSafe()));
			safeAfterBusinessHourSumInsuredVO.setSumInsured(
					createSBSQuoteRequest.getLiabilityInformation().getMoneyInLockedSafe().doubleValue());
		} else {
			contentsSafeAfterBusinessHour.setCover(new BigDecimal(0.0));
			safeAfterBusinessHourSumInsuredVO.setSumInsured(0.0);
		}
		contentsSafeAfterBusinessHour.setRiskType(2); // Hard coding for time being for cash after business hour
		contentsSafeAfterBusinessHour.setRiskCat(1); // Hard coding for time being
		contentsSafeAfterBusinessHour.setRiskSubCat(0); // Hard coding for time being
		contentsSafeAfterBusinessHour.setDeductibles(new BigDecimal(0.0)); // Hard coding for time being
		contentsSafeAfterBusinessHour.setRiskId(-9999L); // Hard coding for time being
		moneyVO.getContentsList().add(contentsSafeAfterBusinessHour);

		safeAfterBusinessHourSumInsuredVO.setDeductible(0.0);
		sumInsuredVOs.add(safeAfterBusinessHourSumInsuredVO);

		// Cash locked in Drawer After Business hour mapping

		Contents contentsDrawerAfterBusinessHour = new Contents();
		SumInsuredVO sumInsuredVODrawerAfterBusinessHour = new SumInsuredVO();

		if (!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getMoneyInLockedDrawer())) {
			contentsDrawerAfterBusinessHour
					.setCover(new BigDecimal(createSBSQuoteRequest.getLiabilityInformation().getMoneyInLockedDrawer()));
			sumInsuredVODrawerAfterBusinessHour.setSumInsured(
					createSBSQuoteRequest.getLiabilityInformation().getMoneyInLockedDrawer().doubleValue());
		} else {
			contentsDrawerAfterBusinessHour.setCover(new BigDecimal(0.0));
			sumInsuredVODrawerAfterBusinessHour.setSumInsured(0.0);
		}

		contentsDrawerAfterBusinessHour.setRiskType(2); // Hard coding for time being for cash After business hour
		contentsDrawerAfterBusinessHour.setRiskCat(3); // Hard coding for time being
		contentsDrawerAfterBusinessHour.setRiskSubCat(0); // Hard coding for time being
		contentsDrawerAfterBusinessHour.setDeductibles(new BigDecimal(0.0)); // Hard coding for time being
		contentsDrawerAfterBusinessHour.setRiskId(-9999L); // Hard coding for time being
		moneyVO.getContentsList().add(contentsDrawerAfterBusinessHour);

		sumInsuredVODrawerAfterBusinessHour.setDeductible(0.0);
		sumInsuredVOs.add(sumInsuredVODrawerAfterBusinessHour);

		moneyVO.setSumInsuredDets(sumInsuredVOs);

		moneyVO.setCashResDetails(new ArrayList<>());
		if (!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getMoneyInEmployeePremises())
				&& createSBSQuoteRequest.getLiabilityInformation().getMoneyInEmployeePremises() > 0) {

			moneyVO.setCashInResidence(true); // Hard coding for time being
			CashResidenceVO cashResidenceVO = new CashResidenceVO(); // Hard coding for time being
			cashResidenceVO.setEmpName("To Be Provided"); // This cant be blank otherwise UI will not populate this
															// section
			cashResidenceVO.setId(new Long(-9999)); // Hard coding for time being
			cashResidenceVO.setIndex(0); // Hard coding for time being
			cashResidenceVO.setOccupation("Manager"); // Hard coding for time being, We don't find mapping in request

			SumInsuredVO sumInsuredVO = new SumInsuredVO();
			sumInsuredVO.setSumInsured(
					createSBSQuoteRequest.getLiabilityInformation().getMoneyInEmployeePremises().doubleValue());
			sumInsuredVO.setPromoCover(false);
			cashResidenceVO.setSumInsuredDets(sumInsuredVO);

			moneyVO.getCashResDetails().add(cashResidenceVO);
		} else
			moneyVO.setIsCashResNotSelected(true);

		moneyVO.setSafeDetails(new ArrayList<>()); // Mapping is not done as We don't find mapping in request

		return moneyVO;

	}
	public FidelityVO getFidelityVO(CreateSBSQuoteRequest createSBSQuoteRequest,PolicyVO policyVO) {
		
		FidelityVO fidelityVO = new FidelityVO();
		com.mindtree.ruc.cmn.utils.List<FidelityNammedEmployeeDetailsVO> fidelityNammedEmployeeDetailsVOs = new com.mindtree.ruc.cmn.utils.List<>(FidelityNammedEmployeeDetailsVO.class);
		com.mindtree.ruc.cmn.utils.List<FidelityUnnammedEmployeeVO> fidelityUnnammedEmployeeVOs = new com.mindtree.ruc.cmn.utils.List<>(FidelityUnnammedEmployeeVO.class);
		Double aggregateLimit = new Double(0.0);
		
		if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee())) {
			
			if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee().getNamedEmployeesDetail())) {
				
				List<NamedEmployeesDetail__> namedEmployeesDetail__s = createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee().getNamedEmployeesDetail();
				int index=0;
				for (NamedEmployeesDetail__ namedEmployeesDetail__ : namedEmployeesDetail__s) {
					FidelityNammedEmployeeDetailsVO employeeDetailsVO = new FidelityNammedEmployeeDetailsVO();
					employeeDetailsVO.setEmpName(namedEmployeesDetail__.getName());
					LOGGER.debug("Employee Category Code Either CashHandling/CashHandling(16/17)::::"+namedEmployeesDetail__.getCategory());
					employeeDetailsVO.setEmpType(Integer.parseInt(namedEmployeesDetail__.getCategory()));   		// 16 Cash handling hard coding for time being
					employeeDetailsVO.setEmpDesignation(namedEmployeesDetail__.getDesignation());
					employeeDetailsVO.setLimitPerPerson(namedEmployeesDetail__.getSumInsured().doubleValue());
					employeeDetailsVO.setSumInsured(0.0);					// Hard coding for time being
					employeeDetailsVO.setIndex(index++);
					aggregateLimit += namedEmployeesDetail__.getSumInsured().doubleValue();
					employeeDetailsVO.setGprFidelityId(-9999L);					// hard coding for time being
					
					SumInsuredVO sumInsuredVO = new SumInsuredVO();
					sumInsuredVO.setPromoCover(false);							// hard coding for time being
					
					employeeDetailsVO.setSumInsuredDetails(sumInsuredVO);
					fidelityNammedEmployeeDetailsVOs.add(employeeDetailsVO);
				}
			}
			if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee().getUnnamedEmployeesDetail())) {
				
				UnnamedEmployeesDetail unnamedEmployeesDetail = createSBSQuoteRequest.getLiabilityInformation().getFidelityGuarantee().getUnnamedEmployeesDetail();
				
				int index =0;
				
				if(!Utils.isEmpty(unnamedEmployeesDetail.getCashHandelingEmployeesCount()) &&  unnamedEmployeesDetail.getCashHandelingEmployeesCount()>0) {
					
					FidelityUnnammedEmployeeVO fidelityUnnammedEmployeeVO = new FidelityUnnammedEmployeeVO();
					
					fidelityUnnammedEmployeeVO.setEmpType(16);    	// hard coding for time being
					fidelityUnnammedEmployeeVO.setTotalNumberOfEmployee(unnamedEmployeesDetail.getCashHandelingEmployeesCount());
					fidelityUnnammedEmployeeVO.setLimitPerPerson(unnamedEmployeesDetail.getCashHandelingInsured().doubleValue());
					fidelityUnnammedEmployeeVO.setGupFidelityId(-9999L);		// Hard coding for time being
					fidelityUnnammedEmployeeVO.setIndex(index++);
					aggregateLimit+=(unnamedEmployeesDetail.getCashHandelingInsured().doubleValue() * unnamedEmployeesDetail.getCashHandelingEmployeesCount());
					
					fidelityUnnammedEmployeeVO.setSumInsured(0.0);				// Hard coding for time being
					
					SumInsuredVO sumInsuredVO = new SumInsuredVO();
					sumInsuredVO.setPromoCover(false);							// hard coding for time being
					
					fidelityUnnammedEmployeeVO.setSumInsuredDetails(sumInsuredVO);
					
					fidelityUnnammedEmployeeVOs.add(fidelityUnnammedEmployeeVO);
				}
				if(!Utils.isEmpty(unnamedEmployeesDetail.getNonCashHandelingEmployeesCount()) &&  unnamedEmployeesDetail.getNonCashHandelingEmployeesCount()>0) {
					
					FidelityUnnammedEmployeeVO fidelityUnnammedEmployeeVO = new FidelityUnnammedEmployeeVO();
					
					fidelityUnnammedEmployeeVO.setEmpType(17);    	// hard coding for time being
					fidelityUnnammedEmployeeVO.setTotalNumberOfEmployee(unnamedEmployeesDetail.getNonCashHandelingEmployeesCount());
					fidelityUnnammedEmployeeVO.setLimitPerPerson(unnamedEmployeesDetail.getNonCashHandelingInsured().doubleValue());
					fidelityUnnammedEmployeeVO.setGupFidelityId(-9999L);		// Hard coding for time being
					fidelityUnnammedEmployeeVO.setIndex(index++);
					aggregateLimit+=( unnamedEmployeesDetail.getNonCashHandelingInsured().doubleValue() * unnamedEmployeesDetail.getNonCashHandelingEmployeesCount());
					
					fidelityUnnammedEmployeeVO.setSumInsured(0.0);				// Hard coding for time being
					
					SumInsuredVO sumInsuredVO = new SumInsuredVO();
					sumInsuredVO.setPromoCover(false);							// hard coding for time being
					
					fidelityUnnammedEmployeeVO.setSumInsuredDetails(sumInsuredVO);
					
					fidelityUnnammedEmployeeVOs.add(fidelityUnnammedEmployeeVO);
					
				}
				
				
			}
			if(aggregateLimit>SBSWsAppConstants.maxFGAggregateLimit) {
				fidelityVO.setAggregateLimit(new Double(1000000.00));
			}else {
				fidelityVO.setAggregateLimit(aggregateLimit);
			}
			fidelityVO.setFidelityEmployeeDetails(fidelityNammedEmployeeDetailsVOs);
			fidelityVO.setUnnammedEmployeeDetails(fidelityUnnammedEmployeeVOs);
			fidelityVO.setDeductible(5000.0);				// Hard coding for time being
			fidelityVO.setFidAggregateBasePremium(0);		// Hard coding for time being
			fidelityVO.setFidAggregateMonthlyPremium(0);	// Hard coding for time being
			fidelityVO.setFidAggregateQuaterlyPremium(0);	// Hard coding for time being
			fidelityVO.setFidAggregateTermPremium(0);		// Hard coding for time being
			fidelityVO.setSumInsured(0.0);					// Hard coding for time being
			
			
		/*
		 *  UW Question required only if per person limit extend beyond 250000 for JLT it will not extend beyond this
		 *  if required then need to call getUWQuestionForFidelitySection method 
		 *  
		 *  LookUpListVO codes = SvcUtils.getLookUpCodesList("UWQ_REF_MSG", "ALL", "9");   // for getting UW Codes from lookup
		 */
		UWQuestionsVO uwQuestionsVO = new UWQuestionsVO();
		uwQuestionsVO=getUWQuestionForFidelitySection();	
		uwQuestionsVO.setCascaded(false);
		fidelityVO.setUwQuestions(uwQuestionsVO);
			
		}
		
		return fidelityVO;
	}
	
	private GroupPersonalAccidentVO getGPAVO(CreateSBSQuoteRequest createSBSQuoteRequest, PolicyVO policyVO) {
		// TODO Auto-generated method stub
		GroupPersonalAccidentVO groupPersonalAccidentVO = new GroupPersonalAccidentVO();
		
		if(!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getGroupPersonalAccident())) {
			
			Double aggregateLimit = new Double(0.0);
			double sumInsured =0.0;
			java.util.List<GPANammedEmpVO> gpaNammedEmpVO = new com.mindtree.ruc.cmn.utils.List<GPANammedEmpVO>(GPANammedEmpVO.class);
			GroupPersonalAccident groupPersonalAccident = new GroupPersonalAccident();
			List<NamedEmployeesDetail> namedEmployeesDetail = new ArrayList<NamedEmployeesDetail>();
			
			groupPersonalAccident=createSBSQuoteRequest.getLiabilityInformation().getGroupPersonalAccident();
			if(!Utils.isEmpty(groupPersonalAccident.getNamedEmployeesDetail())) {
				namedEmployeesDetail = groupPersonalAccident.getNamedEmployeesDetail();
				int index =0;
				for (NamedEmployeesDetail namedEmployeesDetail2 : namedEmployeesDetail) {
					GPANammedEmpVO nammedEmpVO = new GPANammedEmpVO();
					
					/*if(namedEmployeesDetail2.getCategory().equalsIgnoreCase("Admin")) {
						nammedEmpVO.setEmployeeType(8);					// hard coded for time being
					}
					if(namedEmployeesDetail2.getCategory().equalsIgnoreCase("NonAdmin")) {
						nammedEmpVO.setEmployeeType(9);					// hard coded for time being
					}*/
					LOGGER.debug("Category Code Either Admin/NonAdmin(8/9)::::"+namedEmployeesDetail2.getCategory());
					nammedEmpVO.setEmployeeType(Integer.parseInt(namedEmployeesDetail2.getCategory()));	// Assuming that employee code will get in request
					nammedEmpVO.setgpaIndex(index++);
					nammedEmpVO.setGprId("");
					nammedEmpVO.setNameOfEmployee(namedEmployeesDetail2.getName());
					nammedEmpVO.setNammedEmpAnnualSalary(namedEmployeesDetail2.getSalary().doubleValue());
					//nammedEmpVO.setNammedEmpDesignation(namedEmployeesDetail2.getCategory()); //commented and editied as below
					nammedEmpVO.setNammedEmpDesignation("Manager"); //changed as per the new requirment of making the designation default to Manager
					nammedEmpVO.setNammedEmpDob(namedEmployeesDetail2.getDateOfBirth());
					
					if(namedEmployeesDetail2.getGender().equalsIgnoreCase("Male")) {
						nammedEmpVO.setNamedEmpGender('2');				// hard coded for time being
					}
					if(namedEmployeesDetail2.getGender().equalsIgnoreCase("Female")) {
						nammedEmpVO.setNamedEmpGender('1');				// hard coded for time being
					}
				/*	LOGGER.debug("Gender Code Either Male/Female(2/1)::::"+namedEmployeesDetail2.getGender());
					nammedEmpVO.setNamedEmpGender(namedEmployeesDetail2.getGender().charAt(0));			// Assuming that employee code will get in request
*/					nammedEmpVO.setSumInsured(0.0);						// hard coded for time being
					SumInsuredVO  sumInsuredDetails = new SumInsuredVO();
					sumInsuredDetails.setSumInsured(namedEmployeesDetail2.getSumInsured().doubleValue());
					sumInsuredDetails.setPromoCover(false);				// hard coded for time being
					sumInsured+=namedEmployeesDetail2.getSumInsured().doubleValue();
					nammedEmpVO.setSumInsuredDetails(sumInsuredDetails);
					
					gpaNammedEmpVO.add(nammedEmpVO);
				}
			}
			aggregateLimit = sumInsured /2;
			groupPersonalAccidentVO.setGpaNammedEmpVO(gpaNammedEmpVO);
			groupPersonalAccidentVO.setSumInsured(sumInsured);			// hard coded for time being	
			groupPersonalAccidentVO.setGpaDeductible(0.0);				// hard coded for time being
			groupPersonalAccidentVO.setAggregateLimit(aggregateLimit);
		}
		
		
		return groupPersonalAccidentVO;
	}
	
	public PropertyRisks getPropertyRisks(CreateSBSQuoteRequest createSBSQuoteRequest) {
		
		PropertyRisks propertyRisks = new PropertyRisks();
		
		java.util.List<PropertyRiskDetails> propertyCoversDetailsList =  new com.mindtree.ruc.cmn.utils.List<PropertyRiskDetails>(PropertyRiskDetails.class);
		
		//PAR Content -- begin -- as of now only content is coming from json. So only one cover mapping.
		if(createSBSQuoteRequest.getLiabilityInformation().getPropertyContentValue()!=null 
				&& createSBSQuoteRequest.getLiabilityInformation().getPropertyContentValue().getAmount()!=null
				   && createSBSQuoteRequest.getLiabilityInformation().getPropertyContentValue().getAmount()>0) {
			
		PropertyRiskDetails propertyRiskDetailsContent = new PropertyRiskDetails();
		
		propertyRiskDetailsContent.setPremium(new PremiumVO()); // Just Instantiation will suffice.
		
		propertyRiskDetailsContent.setBuildingId(-9999L); //
		propertyRiskDetailsContent.setCoverId(null);  //setting it to null for Batch purpose for CREATE_QUO flow
		propertyRiskDetailsContent.setSetValidityStartDate(new Date());
		propertyRiskDetailsContent.setCover(new Double(createSBSQuoteRequest.getLiabilityInformation().getPropertyContentValue().getAmount())); //From request JSON
		propertyRiskDetailsContent.setDesc(com.Constant.CONST_TO_BE_PROVIDED);
		propertyRiskDetailsContent.setDeductibles(new Double(Utils.getSingleValueAppConfig( "JLT_PAR_CONTENT_DEDUCTIBLE" ))); //Only for JLT
		propertyRiskDetailsContent.setCoverCode(1);  //Check with DB person and set it as per db 1
		propertyRiskDetailsContent.setCoverType(0); //Check with DB person and set it
		propertyRiskDetailsContent.setCoverSubType(0); //Check with DB person and set it
		
		propertyRiskDetailsContent.setRiskCode(2); //as per db sheet
		propertyRiskDetailsContent.setRiskType(999); //as per debug log in PAS
		propertyRiskDetailsContent.setRiskCat(2); //NEED Analysis
		propertyRiskDetailsContent.setRiskSubCat(2); //NEED Analysis
		propertyRiskDetailsContent.setCoverOpted(1);
		
		propertyCoversDetailsList.add(propertyRiskDetailsContent);  //add content cover to list

		}
		//PAR Content -- end
		
		//PAR Rent cover -- begin
		if(createSBSQuoteRequest.getLiabilityInformation().getAnnualRentPayable()!=null
				&& createSBSQuoteRequest.getLiabilityInformation().getAnnualRentPayable() > 0) {
			
			PropertyRiskDetails propertyRiskDetailsRent = new PropertyRiskDetails();
			
			propertyRiskDetailsRent.setPremium(new PremiumVO()); // Just Instantiation will suffice.
			
			//propertyRiskDetailsRent.setBuildingId(-9999L); //autogen
			propertyRiskDetailsRent.setCoverId(null);  //setting it to null for Batch purpose for CREATE_QUO flow
			propertyRiskDetailsRent.setSetValidityStartDate(new Date());
			propertyRiskDetailsRent.setCover(new Double(createSBSQuoteRequest.getLiabilityInformation().getAnnualRentPayable())); //From request JSON
			propertyRiskDetailsRent.setDesc(com.Constant.CONST_TO_BE_PROVIDED);
			propertyRiskDetailsRent.setDeductibles(new Double(Utils.getSingleValueAppConfig( "JLT_PAR_RENT_DEDUCTIBLE" ))); //Only for JLT
			propertyRiskDetailsRent.setCoverCode(1);  //Check with DB person and set it as per db 1
			propertyRiskDetailsRent.setCoverType(0); //Check with DB person and set it
			propertyRiskDetailsRent.setCoverSubType(0); //Check with DB person and set it
			
			propertyRiskDetailsRent.setRiskCode(3); //as per satadal
			propertyRiskDetailsRent.setRiskType(13); //as per debug log in PAS
			propertyRiskDetailsRent.setRiskCat(3); //NEED Analysis
			propertyRiskDetailsRent.setRiskSubCat(3); //NEED Analysis
			propertyRiskDetailsRent.setCoverOpted(1);
			
			propertyCoversDetailsList.add(propertyRiskDetailsRent);  //add Rent cover to list
			
		}
		//PAR Rent cover -- end
		
		
		//PAR Stock -- begin -- as of now only stock is coming from json. So only one cover mapping.
				if(createSBSQuoteRequest.getLiabilityInformation().getStock()!=null 
						&& createSBSQuoteRequest.getLiabilityInformation().getStock().getAmount()!=null
						   && createSBSQuoteRequest.getLiabilityInformation().getStock().getAmount()>0) {
					
				PropertyRiskDetails propertyRiskDetailsStock = new PropertyRiskDetails();
				
				propertyRiskDetailsStock.setPremium(new PremiumVO()); // Just Instantiation will suffice.
				
				//propertyRiskDetailsContent.setBuildingId(-9999L); // as per debug log in pas
				propertyRiskDetailsStock.setCoverId(null);  //setting it to null for Batch purpose for CREATE_QUO flow
				propertyRiskDetailsStock.setSetValidityStartDate(new Date());
				propertyRiskDetailsStock.setCover(new Double(createSBSQuoteRequest.getLiabilityInformation().getStock().getAmount())); //From request JSON
				propertyRiskDetailsStock.setDesc(com.Constant.CONST_TO_BE_PROVIDED);
				propertyRiskDetailsStock.setDeductibles(new Double(Utils.getSingleValueAppConfig( "JLT_PAR_CONTENT_DEDUCTIBLE" ))); //Only for JLT
				propertyRiskDetailsStock.setCoverCode(1);  //Check with DB person and set it as per db 1
				propertyRiskDetailsStock.setCoverType(0); //Check with DB person and set it
				propertyRiskDetailsStock.setCoverSubType(0); //Check with DB person and set it
				
				propertyRiskDetailsStock.setRiskCode(0); //as per debug
				propertyRiskDetailsStock.setRiskType(9); //as per debug log in PAS
				propertyRiskDetailsStock.setRiskCat(2); //NEED Analysis
				propertyRiskDetailsStock.setRiskSubCat(2); //NEED Analysis
				propertyRiskDetailsStock.setCoverOpted(1);
				
				propertyCoversDetailsList.add(propertyRiskDetailsStock);  //add content cover to list

				}
				//PAR Content -- end
		propertyRisks.setPropertyCoversDetails(propertyCoversDetailsList);
		
		
		
		return propertyRisks;
		
		
	}
	
	private UWQuestionsVO getUWQuestionForFidelitySection() {
		
		UWQuestionsVO  uwQuestionsVO = new UWQuestionsVO();
		List<UWQuestionVO> questions = new ArrayList<>();
		uwQuestionsVO.setCascaded(false);
		
		UWQuestionVO uwQuestionVO_18 = new UWQuestionVO();
		uwQuestionVO_18.setQId(new Short("18"));
		uwQuestionVO_18.setResponse("");
		uwQuestionVO_18.setResponseType(UWQuestionRespType.TEXT);
		questions.add(uwQuestionVO_18);
		
		UWQuestionVO uwQuestionVO_19 = new UWQuestionVO();
		uwQuestionVO_19.setQId(new Short("19"));
		uwQuestionVO_19.setResponse("yes");
		uwQuestionVO_19.setResponseType(UWQuestionRespType.RADIO);
		questions.add(uwQuestionVO_19);
		
		UWQuestionVO uwQuestionVO_20 = new UWQuestionVO();
		uwQuestionVO_20.setQId(new Short("20"));
		uwQuestionVO_20.setResponse("yes");
		uwQuestionVO_20.setResponseType(UWQuestionRespType.RADIO);
		questions.add(uwQuestionVO_20);
		
		UWQuestionVO uwQuestionVO_21 = new UWQuestionVO();
		uwQuestionVO_21.setQId(new Short("21"));
		uwQuestionVO_21.setResponse("yes");
		uwQuestionVO_21.setResponseType(UWQuestionRespType.RADIO);
		questions.add(uwQuestionVO_21);
		
		UWQuestionVO uwQuestionVO_22 = new UWQuestionVO();
		uwQuestionVO_22.setQId(new Short("22"));
		uwQuestionVO_22.setResponse("no");
		uwQuestionVO_22.setResponseType(UWQuestionRespType.RADIO);
		questions.add(uwQuestionVO_22);
		
		UWQuestionVO uwQuestionVO_23 = new UWQuestionVO();
		uwQuestionVO_23.setQId(new Short("23"));
		uwQuestionVO_23.setResponse("");
		uwQuestionVO_23.setResponseType(UWQuestionRespType.TEXT);
		questions.add(uwQuestionVO_23);
		
		UWQuestionVO uwQuestionVO_24 = new UWQuestionVO();
		uwQuestionVO_24.setQId(new Short("24"));
		uwQuestionVO_24.setResponse("yes");
		uwQuestionVO_24.setResponseType(UWQuestionRespType.RADIO);
		questions.add(uwQuestionVO_24);
		
		UWQuestionVO uwQuestionVO_25 = new UWQuestionVO();
		uwQuestionVO_25.setQId(new Short("25"));
		uwQuestionVO_25.setResponse("");
		uwQuestionVO_25.setResponseType(UWQuestionRespType.TEXT);
		questions.add(uwQuestionVO_25);
		
		UWQuestionVO uwQuestionVO_26 = new UWQuestionVO();
		uwQuestionVO_26.setQId(new Short("26"));
		uwQuestionVO_26.setResponse("");
		uwQuestionVO_26.setResponseType(UWQuestionRespType.TEXT);
		questions.add(uwQuestionVO_26);
		
		UWQuestionVO uwQuestionVO_27 = new UWQuestionVO();
		uwQuestionVO_27.setQId(new Short("27"));
		uwQuestionVO_27.setResponse("yes");
		uwQuestionVO_27.setResponseType(UWQuestionRespType.RADIO);
		questions.add(uwQuestionVO_27);
		
		UWQuestionVO uwQuestionVO_28 = new UWQuestionVO();
		uwQuestionVO_28.setQId(new Short("28"));
		uwQuestionVO_28.setResponse("yes");
		uwQuestionVO_28.setResponseType(UWQuestionRespType.RADIO);
		questions.add(uwQuestionVO_28);
		
		UWQuestionVO uwQuestionVO_29 = new UWQuestionVO();
		uwQuestionVO_29.setQId(new Short("29"));
		uwQuestionVO_29.setResponse("yes");
		uwQuestionVO_29.setResponseType(UWQuestionRespType.RADIO);
		questions.add(uwQuestionVO_29);
		
		UWQuestionVO uwQuestionVO_30 = new UWQuestionVO();
		uwQuestionVO_30.setQId(new Short("30"));
		uwQuestionVO_30.setResponse("yes");
		uwQuestionVO_30.setResponseType(UWQuestionRespType.RADIO);
		questions.add(uwQuestionVO_30);
		
		UWQuestionVO uwQuestionVO_31 = new UWQuestionVO();
		uwQuestionVO_31.setQId(new Short("31"));
		uwQuestionVO_31.setResponse("yes");
		uwQuestionVO_31.setResponseType(UWQuestionRespType.RADIO);
		questions.add(uwQuestionVO_31);
		
		UWQuestionVO uwQuestionVO_32 = new UWQuestionVO();
		uwQuestionVO_32.setQId(new Short("32"));
		uwQuestionVO_32.setResponse("");
		uwQuestionVO_32.setResponseType(UWQuestionRespType.TEXT);
		questions.add(uwQuestionVO_32);
		
		UWQuestionVO uwQuestionVO_33 = new UWQuestionVO();
		uwQuestionVO_33.setQId(new Short("33"));
		uwQuestionVO_33.setResponse("");
		uwQuestionVO_33.setResponseType(UWQuestionRespType.TEXT);
		questions.add(uwQuestionVO_33);
		
		UWQuestionVO uwQuestionVO_34 = new UWQuestionVO();
		uwQuestionVO_34.setQId(new Short("34"));
		uwQuestionVO_34.setResponse("");
		uwQuestionVO_34.setResponseType(UWQuestionRespType.TEXT);
		questions.add(uwQuestionVO_34);
		
		/*UWQuestionVO uwQuestionVO_35 = new UWQuestionVO();
		uwQuestionVO_18.setQId(new Short("35"));
		uwQuestionVO_18.setResponse("");
		uwQuestionVO_18.setResponseType(UWQuestionRespType.TEXT);
		questions.add(uwQuestionVO_35);*/
		
		
		uwQuestionsVO.setQuestions(questions);
		
		return uwQuestionsVO;
	}
	private boolean checkWcBaseSection(List<Integer> sectionList) {
		
		for (Integer sectionId : sectionList) {
			if(sectionId==SvcConstants.SECTION_ID_WC && sectionList.size()==1) {
				return true;
			}
		}
		
		return false;
	}
	
	private boolean checkIsWCOnlyBaseSection(List<Integer> sectionList) {
		
		boolean isWConlyBaseSection = false;
		
		for (Integer sectionId : sectionList) {
			
			if(sectionId==1 || sectionId==6) {
				
				isWConlyBaseSection = false;
				break;
			}
			else {
				isWConlyBaseSection = true;
			}
		}
		
		return isWConlyBaseSection;
	}

	private ParVO getPARDummySection(LocationVO locationVO,CreateSBSQuoteRequest createSBSQuoteRequest) {
		ParVO parVO = new ParVO();
		parVO.setLocationVO(new LocationVO());
		//55035 -- freezone = Others
		locationVO.setFreeZone("55035");
		
		//16002 -- location -- Others
	    locationVO.setDirectorate(16002);
	    //mapping for free-zone and location when WC standalone
	    
	   /* if(createSBSQuoteRequest.getLiabilityInformation().getNameOfFreeZoneAuthority()!=null && createSBSQuoteRequest.getLiabilityInformation().getNameOfFreeZoneAuthority().getCode()!=null && !createSBSQuoteRequest.getLiabilityInformation().getNameOfFreeZoneAuthority().getCode().equals("")
				&& createSBSQuoteRequest.getLiabilityInformation().getNameOfFreeZoneAuthority().getIsFreezone()!=null) {
				if(createSBSQuoteRequest.getLiabilityInformation().getNameOfFreeZoneAuthority().getIsFreezone()) {
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
			

		}*/
	    
        
	    parVO.getLocationVO().setFreeZone((locationVO.getFreeZone()));
	    parVO.getLocationVO().setDirectorate((locationVO.getDirectorate()));
	   // parVO.setLocationVO(locationVO.getDirectorate());
		parVO.setBuilCovered(1);
		parVO.setBldCover(0.0);			// setting bld cover 0.0 as sum insured for 0 premium
		parVO.setBldDeductibles(new Double(750));
		// Map building cover - 1st row on PAR page -- end

		parVO.setBldPremium(new PremiumVO());
        
		// map PropertyRisks - other 3 rows on PAR page (list of 3 objects) 1 for each
		// cover.
		PropertyRisks propertyRisks = new PropertyRisks();
		java.util.List<PropertyRiskDetails> propertyCoversDetailsList =  new com.mindtree.ruc.cmn.utils.List<PropertyRiskDetails>(PropertyRiskDetails.class);
		propertyRisks.setPropertyCoversDetails(propertyCoversDetailsList);
		parVO.setCovers(propertyRisks);

		// mapping UWQuestions for PAR
		UWQuestionsVO uwQuestionsVO = new UWQuestionsVO();
		java.util.List<UWQuestionVO> questions = new com.mindtree.ruc.cmn.utils.List<UWQuestionVO>(UWQuestionVO.class);

		UWQuestionVO question1 = new UWQuestionVO();
		question1.setQId((short) 1);
		question1.setQDesc("100% Sprinklers Coverage Installed");
		question1.setResponseType(UWQuestionRespType.RADIO);
		question1.setResponse("yes");
		questions.add(question1);

		UWQuestionVO question2 = new UWQuestionVO();
		question2.setQId((short) 2);
		question2.setQDesc("Storage Exposure(hazardous goods/chemicals)");
		question2.setResponseType(UWQuestionRespType.RADIO);
		question2.setResponse("no");
		questions.add(question2);

		UWQuestionVO question3 = new UWQuestionVO();
		question3.setQId((short) 3);
		question3.setQDesc("Alarms Installed");
		question3.setResponseType(UWQuestionRespType.RADIO);
		question3.setResponse("yes");
		questions.add(question3);

		UWQuestionVO question7 = new UWQuestionVO();
		question7.setQId((short) 7);
		question7.setQDesc("Construction Type RCC ");
		question7.setResponseType(UWQuestionRespType.RADIO);
		question7.setResponse("yes");
		questions.add(question7);

		uwQuestionsVO.setQuestions(questions);
		uwQuestionsVO.setCascaded(false);

		parVO.setUwQuestions(uwQuestionsVO);

		// mapping PARUWDetails
		PARUWDetailsVO parUWDetailsVO = new PARUWDetailsVO();
		parUWDetailsVO.setHazardLevel(3); // hardcoded as of now. Need analysis to calculate level.
		parUWDetailsVO.setHazardousNature(3); // hardcoded as of now. Need analysis to calculate level.
		parUWDetailsVO.setCategoryRI(202);
		parVO.setUwDetails(parUWDetailsVO);
		

		return parVO;
	}
}
