package com.rsaame.pas.b2b.ws.mapper;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.b2b.ws.constant.ServiceConstant;
import com.rsaame.pas.b2b.ws.handler.RuleHandler;
import com.rsaame.pas.b2b.ws.handler.SaveQuoteHandler;
import com.rsaame.pas.b2b.ws.util.SBSWsAppConstants;
import com.rsaame.pas.b2b.ws.util.WSAppUtils;
import com.rsaame.pas.b2b.ws.util.WSBusinessValidatorUtils;
import com.rsaame.pas.b2b.ws.util.WSDAOUtils;
import com.rsaame.pas.b2b.ws.validators.SBSWSValidators;
import com.rsaame.pas.b2b.ws.vo.CreateSBSQuoteRequest;
import com.rsaame.pas.b2b.ws.vo.CreateSBSQuoteResponse;
import com.rsaame.pas.b2b.ws.vo.request.ClaimInformation;
import com.rsaame.pas.b2b.ws.vo.request.Location;
import com.rsaame.pas.b2b.ws.vo.request.NameOfFreeZoneAuthority;
import com.rsaame.pas.b2b.ws.vo.request.NamedEmployeesDetail;
import com.rsaame.pas.b2b.ws.vo.request.NamedEmployeesDetail_;
import com.rsaame.pas.b2b.ws.vo.request.NamedEmployeesDetail__;
import com.rsaame.pas.b2b.ws.vo.response.Cover;
import com.rsaame.pas.b2b.ws.vo.response.CoverPremium;
import com.rsaame.pas.b2b.ws.vo.response.GrossPremium;
import com.rsaame.pas.b2b.ws.vo.response.Premium;
import com.rsaame.pas.b2b.ws.vo.response.Premium_;
import com.rsaame.pas.b2b.ws.vo.response.SelectedPlan;
import com.rsaame.pas.b2b.ws.vo.response.SumInsured;
import com.rsaame.pas.b2b.ws.vo.response.VatOnPremium;
import com.rsaame.pas.b2c.cmn.utils.AppUtils;
import com.rsaame.pas.cmn.currency.Currency;
import com.rsaame.pas.rules.mapper.RulesConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.app.Contents;
import com.rsaame.pas.vo.bus.BIVO;
import com.rsaame.pas.vo.bus.CashResidenceVO;
import com.rsaame.pas.vo.bus.DeteriorationOfStockDetailsVO;
import com.rsaame.pas.vo.bus.DeteriorationOfStockVO;
import com.rsaame.pas.vo.bus.EEVO;
import com.rsaame.pas.vo.bus.EmpTypeDetailsVO;
import com.rsaame.pas.vo.bus.EquipmentVO;
import com.rsaame.pas.vo.bus.FidelityNammedEmployeeDetailsVO;
import com.rsaame.pas.vo.bus.FidelityUnnammedEmployeeVO;
import com.rsaame.pas.vo.bus.FidelityVO;
import com.rsaame.pas.vo.bus.GPANammedEmpVO;
import com.rsaame.pas.vo.bus.GroupPersonalAccidentVO;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.MBVO;
import com.rsaame.pas.vo.bus.MachineDetailsVO;
import com.rsaame.pas.vo.bus.MoneyVO;
import com.rsaame.pas.vo.bus.ParVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.PropertyRiskDetails;
import com.rsaame.pas.vo.bus.PropertyRisks;
import com.rsaame.pas.vo.bus.PublicLiabilityVO;
import com.rsaame.pas.vo.bus.ReferralLocKey;
import com.rsaame.pas.vo.bus.ReferralVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.SectionVO;
import com.rsaame.pas.vo.bus.SumInsuredVO;
import com.rsaame.pas.vo.bus.TravelBaggageVO;
import com.rsaame.pas.vo.bus.TravellingEmployeeVO;
import com.rsaame.pas.vo.bus.WCVO;

public class SBSCreateQuoteResponseMapper {

	private final static Logger LOGGER = Logger.getLogger(SBSCreateQuoteResponseMapper.class);
	DecimalFormat decForm = new DecimalFormat(RulesConstants.DECIMAL_FORMAT);
	public void mapRequestToVO(CreateSBSQuoteRequest createSBSQuoteRequest, Object source1, Object target, HttpServletRequest request, Long internalReferenceId) {
		LOGGER.info("Inside SBSCreateQuoteResponseMapper...");
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		String currency = Currency.getUnitName();
		LOGGER.info("currency:"+currency);
		if (!Utils.isEmpty(createSBSQuoteRequest) && !Utils.isEmpty(source1) && !Utils.isEmpty(target)) {
			PolicyVO policyVO = (PolicyVO) source1;
			CreateSBSQuoteResponse createSBSQuoteResponse = (CreateSBSQuoteResponse) target;

			initializeObjects(createSBSQuoteResponse);

			createSBSQuoteResponse.setQuoteId("" + policyVO.getQuoteNo());
			createSBSQuoteResponse.setQuoteInternalReference(""+internalReferenceId);
			
			
			mapPolicySchedule(createSBSQuoteRequest,createSBSQuoteResponse,policyVO);
			mapLiabilityInformation(createSBSQuoteRequest,createSBSQuoteResponse, policyVO);
			mapPolicyHolder(createSBSQuoteRequest,createSBSQuoteResponse);
			mapSelectedPlan(createSBSQuoteRequest, createSBSQuoteResponse, policyVO);
			createSBSQuoteResponse.setQuoteStatus("" + policyVO.getStatus());
			mapAgent(createSBSQuoteRequest,createSBSQuoteResponse);
			createSBSQuoteResponse.setquoteExpiryDate(WSAppUtils.getQuoteExpiryAddedDate(createSBSQuoteResponse.getPolicySchedule().getCreationDate()));
			//CTS JLT API Change - 23.07.2020 ADD policyYear and policyNo for create, update and retrieve Quote - Starts
			if(policyVO.getIsRenewal()){
				createSBSQuoteResponse.setPolicyId(policyVO.getPolicyNo().toString());
			}
			createSBSQuoteResponse.getPolicySchedule().setPolicyYear(String.valueOf(SvcUtils.getYearFromDate(policyVO.getPolEffectiveDate())));
			//CTS JLT API Change - 23.07.2020 ADD policyYear and policyNo for create, update and retrieve Quote - Ends

			
		}
		stopWatch.stop();
		LOGGER.info("Response time for SBSCreateQuoteResponseMapper : " + stopWatch.getTime() + " milisecond");
	}
	public void mapPolicySchedule(CreateSBSQuoteRequest createSBSQuoteRequest, CreateSBSQuoteResponse createSBSQuoteResponse ,PolicyVO policyVO) {
		createSBSQuoteResponse.setPolicySchedule(createSBSQuoteRequest.getPolicySchedule());
		if(createSBSQuoteResponse.getPolicySchedule().getCreationDate()==null && policyVO.getAuthInfoVO().getCreatedOn()!=null) {
			Date createDate = (Date)policyVO.getAuthInfoVO().getCreatedOn();
			String polCreateDate=null;
			polCreateDate=WSAppUtils.dateFormatter( createDate );
			createSBSQuoteResponse.getPolicySchedule().setCreationDate(polCreateDate);
		}
		
		if(createSBSQuoteResponse.getPolicySchedule().getExpirationDate()==null && policyVO.getPolExpiryDate()!=null) {
			Date expDate = (Date)policyVO.getPolExpiryDate();
			String polExpDate=null;
			polExpDate=WSAppUtils.dateFormatter( expDate );
			createSBSQuoteResponse.getPolicySchedule().setExpirationDate(polExpDate);
		}
			
	}
	
	public void mapPolicyHolder(CreateSBSQuoteRequest createSBSQuoteRequest, CreateSBSQuoteResponse createSBSQuoteResponse) {
		createSBSQuoteResponse.setPolicyHolder(createSBSQuoteRequest.getPolicyHolder());
	}	
	
	public void mapLiabilityInformation(CreateSBSQuoteRequest createSBSQuoteRequest, CreateSBSQuoteResponse createSBSQuoteResponse, PolicyVO policyVO) {
		createSBSQuoteResponse.setLiabilityInformation(createSBSQuoteRequest.getLiabilityInformation());
		
		//Freezone and Location 
		
		for (SectionVO sectionVO : policyVO.getRiskDetails()) {

			if (!Utils.isEmpty(sectionVO.getRiskGroupDetails())) {

				Map<? extends RiskGroup, ? extends RiskGroupDetails> riskGroupDetailsMap = sectionVO
						.getRiskGroupDetails();
				for (Map.Entry<? extends RiskGroup, ? extends RiskGroupDetails> entry : riskGroupDetailsMap
						.entrySet()) {
					
					Object object = entry.getKey();
					if (!Utils.isEmpty(object) && object instanceof LocationVO) {
						
						LocationVO locationVO = (LocationVO)object;
						
						if(Utils.isEmpty(createSBSQuoteResponse.getLiabilityInformation().getNameOfFreeZoneAuthority())) {
							createSBSQuoteResponse.getLiabilityInformation().setNameOfFreeZoneAuthority(new NameOfFreeZoneAuthority());
						}						
						if(createSBSQuoteRequest.getLiabilityInformation().getFreeZone()) {
							createSBSQuoteResponse.getLiabilityInformation().getNameOfFreeZoneAuthority().setCode(locationVO.getFreeZone());
							createSBSQuoteResponse.getLiabilityInformation().setFreeZone(true);
						}
						else {
							createSBSQuoteResponse.getLiabilityInformation().getNameOfFreeZoneAuthority().setCode(locationVO.getDirectorate().toString());
							createSBSQuoteResponse.getLiabilityInformation().setFreeZone(false);
						}
						
						return;
					}
					
				}
			}	
		}
	}
	
	public void mapAgent(CreateSBSQuoteRequest createSBSQuoteRequest, CreateSBSQuoteResponse createSBSQuoteResponse) {
		createSBSQuoteResponse.setAgent(createSBSQuoteRequest.getAgent());
		
	}
	public void mapSelectedPlan(CreateSBSQuoteRequest createSBSQuoteRequest, CreateSBSQuoteResponse createSBSQuoteResponse, PolicyVO policyVO) {
		Double fgBasePremium = 0.0;
		Double totalPremium = 0.0;
		Double totalPrm=0.0;
		if (!Utils.isEmpty(policyVO.getRiskDetails())) {
			List<Cover> covers = new ArrayList<Cover>();

			for (SectionVO sectionVO : policyVO.getRiskDetails()) {

				if (!Utils.isEmpty(sectionVO.getRiskGroupDetails())) {

					Map<? extends RiskGroup, ? extends RiskGroupDetails> riskGroupDetailsMap = sectionVO
							.getRiskGroupDetails();
					for (Map.Entry<? extends RiskGroup, ? extends RiskGroupDetails> entry : riskGroupDetailsMap
							.entrySet()) {
						RiskGroupDetails groupDetails = entry.getValue();
						if (!Utils.isEmpty(groupDetails) && groupDetails instanceof ParVO) {
							ParVO parVO = (ParVO) groupDetails;

							// Setting Building Cover Details
							Cover buildingCover = new Cover();
							buildingCover.setId(ServiceConstant.SECTION_BUILDING_ID);
							buildingCover.setName(ServiceConstant.SECTION_BUILDING_DESC);
							CoverPremium coverPremium = new CoverPremium();
							Double buildingPremium = 0.0;
							if(!Utils.isEmpty(parVO.getBldPremium())) {
								LOGGER.info("Building::parVO.getBldPremium().getPremiumAmt()-->"+parVO.getBldPremium().getPremiumAmt());
								buildingPremium = parVO.getBldPremium().getPremiumAmt(); 
								//Adding two decimal values to the premium
								//buildingPremium = WSBusinessValidatorUtils.getFormattedDoubleWithTwoDecimals(parVO.getBldPremium().getPremiumAmt());
							}
							if(buildingPremium != 0) {
								buildingCover.setIsIncludedInPremium("Y");
								totalPremium= totalPremium + buildingPremium;
							} else {
								buildingCover.setIsIncludedInPremium("N");
							}
							//reverted premium changes after currency class fix	
							coverPremium.setAmount(new Double(Currency.getUnformattedScaledCurrency(new BigDecimal(buildingPremium),"SBS")));
							//Handled while setting the value no use of formated Currency
							//coverPremium.setAmount(buildingPremium);//changed BigDecimal to Double for premium issue
							coverPremium.setCurrencyCode("AED"); //TODO:Currency.getUnitName()
							
							if(!Utils.isEmpty(parVO.getBldDeductibles()))
								buildingCover.setDeductible(WSBusinessValidatorUtils.getFormatForDecimalWithSingleDigit(parVO.getBldDeductibles()).toString());  // Added for Deductible change
							else 
								buildingCover.setDeductible(WSBusinessValidatorUtils.getFormatForDecimalWithSingleDigit(Double.parseDouble(Utils.getSingleValueAppConfig("JLT_PAR_BUILDING_DEDUCTIBLE"))).toString());
							
							buildingCover.setCoverPremium(coverPremium);
							SumInsured sumInsured = new SumInsured();
							Double siBuilding = 0.00;
							if (!Utils.isEmpty(parVO.getBldCover())) {
								LOGGER.info("Building::parVO.getBldCover()-->"+parVO.getBldCover());
								siBuilding = parVO.getBldCover();
							}
							sumInsured.setAmount(siBuilding);
							sumInsured.setCurrencyCode("AED");//TODO:Currency.getUnitName()
							buildingCover.setSumInsured(sumInsured);
							if(siBuilding != 0) {
								covers.add(buildingCover);
							}
							// Setting Building Cover Details ends here

							// Setting Content Cover Details
							PropertyRisks propertyRisks = parVO.getCovers();
							if (!Utils.isEmpty(propertyRisks)) {
								List<PropertyRiskDetails> propertyRiskDetailsList = propertyRisks
										.getPropertyCoversDetails();
								for (PropertyRiskDetails propertyRiskDetails : propertyRiskDetailsList) {
									if(propertyRiskDetails.getRiskType() == 999) {
										Cover contentCover = new Cover();
										contentCover.setId(ServiceConstant.SECTION_CONTENT_ID);
										contentCover.setName(ServiceConstant.SECTION_CONTENT_DESC);
										CoverPremium contentCoverPremium = new CoverPremium();
										Double contentPremium = 0.0;
										if (!Utils.isEmpty(propertyRiskDetails.getPremium())) {
											LOGGER.info("Content::propertyRiskDetails.getPremium().getPremiumAmt()--_1"+propertyRiskDetails.getPremium().getPremiumAmt());
											contentPremium = propertyRiskDetails.getPremium().getPremiumAmt();
											//Adding two decimal values to the premium
											//contentPremium = WSBusinessValidatorUtils.getFormattedDoubleWithTwoDecimals(propertyRiskDetails.getPremium().getPremiumAmt());
										}
										if(contentPremium != 0) {
											contentCover.setIsIncludedInPremium("Y");
											totalPremium= totalPremium + contentPremium;
										} else {
											contentCover.setIsIncludedInPremium("N");
										}										
										//reverted premium changes after currency class fix
										contentCoverPremium.setAmount(new Double(Currency.getUnformattedScaledCurrency(new BigDecimal(contentPremium),"SBS")));
										//Handled while setting the value no use of formated Currency
										//contentCoverPremium.setAmount(contentPremium);//changed BigDecimal to Double for premium issue
										contentCoverPremium.setCurrencyCode("AED");//TODO:Currency.getUnitName()
										if(!Utils.isEmpty(propertyRiskDetails.getDeductibles()))
											contentCover.setDeductible(WSBusinessValidatorUtils.getFormatForDecimalWithSingleDigit(propertyRiskDetails.getDeductibles()).toString());  // for deductible change
										else
											contentCover.setDeductible(WSBusinessValidatorUtils.getFormatForDecimalWithSingleDigit(Double.parseDouble(Utils.getSingleValueAppConfig("JLT_PAR_CONTENT_DEDUCTIBLE"))).toString());
										
										contentCover.setCoverPremium(contentCoverPremium);
										SumInsured contentSumInsured = new SumInsured();
										Double siContent = 0.0;
										if (!Utils.isEmpty(propertyRiskDetails.getCover())) {
											LOGGER.info("Content::propertyRiskDetails.getCover()--_1"+propertyRiskDetails.getCover());
											siContent = propertyRiskDetails.getCover();
										}
										contentSumInsured.setAmount(siContent);
										contentSumInsured.setCurrencyCode("AED");//TODO: Currency.getUnitName()
										contentCover.setSumInsured(contentSumInsured);
										covers.add(contentCover);
									}
									//Rent Payable
									if(propertyRiskDetails.getRiskType() == 13) {
										Cover contentCover = new Cover();
										contentCover.setId(ServiceConstant.SECTION_RENT_PAYABLE_ID);
										contentCover.setName(ServiceConstant.SECTION_RENT_PAYABLE_DESC);
										CoverPremium contentCoverPremium = new CoverPremium();
										Double rentPayablePremium = 0.0;
										if (!Utils.isEmpty(propertyRiskDetails.getPremium())) {
											LOGGER.info("Content::propertyRiskDetails.getPremium().getPremiumAmt()--_2"+propertyRiskDetails.getPremium().getPremiumAmt());
											//Adding two decimal values to the premium
											rentPayablePremium = propertyRiskDetails.getPremium().getPremiumAmt();
											//rentPayablePremium = WSBusinessValidatorUtils.getFormattedDoubleWithTwoDecimals(propertyRiskDetails.getPremium().getPremiumAmt());
										}
										if(rentPayablePremium != 0) {
											contentCover.setIsIncludedInPremium("Y");
											totalPremium= totalPremium + rentPayablePremium;
										} else {
											contentCover.setIsIncludedInPremium("N");
										}										
										//reverted premium changes after currency class fix
										contentCoverPremium.setAmount(new Double(Currency.getUnformattedScaledCurrency(new BigDecimal(rentPayablePremium),"SBS")));
										//Handled while setting the value no use of formated Currency
										//contentCoverPremium.setAmount(rentPayablePremium);//changed BigDecimal to Double for premium issue
										contentCoverPremium.setCurrencyCode("AED");//TODO:Currency.getUnitName()
										
										if(!Utils.isEmpty(propertyRiskDetails.getDeductibles()))
											contentCover.setDeductible(WSBusinessValidatorUtils.getFormatForDecimalWithSingleDigit(propertyRiskDetails.getDeductibles()).toString());  // for deductible change
										else
											contentCover.setDeductible(WSBusinessValidatorUtils.getFormatForDecimalWithSingleDigit(Double.parseDouble(Utils.getSingleValueAppConfig("JLT_PAR_RENT_DEDUCTIBLE"))).toString());
										
										contentCover.setCoverPremium(contentCoverPremium);
										SumInsured contentSumInsured = new SumInsured();
										Double contentSI = 0.0;
										if (!Utils.isEmpty(propertyRiskDetails.getCover())) {
											LOGGER.info("Content::propertyRiskDetails.getCover()--_2"+propertyRiskDetails.getCover());
											contentSI = propertyRiskDetails.getCover();
										}
										contentSumInsured.setAmount(contentSI);
										contentSumInsured.setCurrencyCode("AED");//TODO: Currency.getUnitName()
										contentCover.setSumInsured(contentSumInsured);
										covers.add(contentCover);
									}
									
									//Stock
									if(propertyRiskDetails.getRiskType() == 9) {
										Cover contentCover = new Cover();
										contentCover.setId(ServiceConstant.SECTION_STOCK_ID);
										contentCover.setName(ServiceConstant.SECTION_STOCK_DESC);
										CoverPremium contentCoverPremium = new CoverPremium();
										Double StockPremium = 0.0;
										if (!Utils.isEmpty(propertyRiskDetails.getPremium())) {
											LOGGER.info("Content::propertyRiskDetails.getPremium().getPremiumAmt()--_3"+propertyRiskDetails.getPremium().getPremiumAmt());
											//Adding two decimal values to the premium
											StockPremium = propertyRiskDetails.getPremium().getPremiumAmt();
											//rentPayablePremium = WSBusinessValidatorUtils.getFormattedDoubleWithTwoDecimals(propertyRiskDetails.getPremium().getPremiumAmt());
										}
										if(StockPremium != 0) {
											contentCover.setIsIncludedInPremium("Y");
											totalPremium= totalPremium + StockPremium;
										} else {
											contentCover.setIsIncludedInPremium("N");
										}										
										//reverted premium changes after currency class fix
										contentCoverPremium.setAmount(new Double(Currency.getUnformattedScaledCurrency(new BigDecimal(StockPremium),"SBS")));
										//Handled while setting the value no use of formated Currency
										//contentCoverPremium.setAmount(rentPayablePremium);//changed BigDecimal to Double for premium issue
										contentCoverPremium.setCurrencyCode("AED");//TODO:Currency.getUnitName()
										
										if(!Utils.isEmpty(propertyRiskDetails.getDeductibles()))
											contentCover.setDeductible(WSBusinessValidatorUtils.getFormatForDecimalWithSingleDigit(propertyRiskDetails.getDeductibles()).toString());  // for deductible change
										else
											contentCover.setDeductible(WSBusinessValidatorUtils.getFormatForDecimalWithSingleDigit(Double.parseDouble(Utils.getSingleValueAppConfig("JLT_PAR_RENT_DEDUCTIBLE"))).toString());
										
										contentCover.setCoverPremium(contentCoverPremium);
										SumInsured contentSumInsured = new SumInsured();
										Double contentSI = 0.0;
										if (!Utils.isEmpty(propertyRiskDetails.getCover())) {
											LOGGER.info("Content::propertyRiskDetails.getCover()--_3"+propertyRiskDetails.getCover());
											contentSI = propertyRiskDetails.getCover();
										}
										contentSumInsured.setAmount(contentSI);
										contentSumInsured.setCurrencyCode("AED");//TODO: Currency.getUnitName()
										contentCover.setSumInsured(contentSumInsured);
										covers.add(contentCover);
									}
								}
							}
							// Setting Content Cover Details ends here
						}
						//Workmen Compensation
						if (!Utils.isEmpty(groupDetails) && groupDetails instanceof WCVO) {
							WCVO wcVO = (WCVO) groupDetails;

							Cover wcCover = new Cover();

							wcCover.setId(ServiceConstant.SECTION_WC_ID);
							wcCover.setName(ServiceConstant.SECTION_WC_DESC);
							
							CoverPremium wcCoverPremium = new CoverPremium();
							Double wcPremium = 0.00;
							if (!Utils.isEmpty(wcVO.getPremium())) {
								LOGGER.info("WC::wcVO.getPremium().getPremiumAmt()-->"+wcVO.getPremium().getPremiumAmt());
								wcPremium = wcVO.getPremium().getPremiumAmt();
								//Adding two decimal values to the premium
								//wcPremium = WSBusinessValidatorUtils.getFormattedDoubleWithTwoDecimals(wcVO.getPremium().getPremiumAmt());
							}
							if(wcPremium != 0) {
								wcCover.setIsIncludedInPremium("Y");
								totalPremium= totalPremium + wcPremium;
							} else {
								wcCover.setIsIncludedInPremium("N");
							}										
							//reverted premium changes after currency class fix
							wcCoverPremium.setAmount(new Double(Currency.getUnformattedScaledCurrency(new BigDecimal(wcPremium),"SBS")));
							//Handled while setting the value no need to convert
							//wcCoverPremium.setAmount(wcPremium);//changed BigDecimal to Double for premium issue
							wcCoverPremium.setCurrencyCode("AED");
							wcCover.setDeductible(WSBusinessValidatorUtils.getFormatForDecimalWithSingleDigit(Double.parseDouble(Utils.getSingleValueAppConfig("WC_Deductibles"))).toString());
							wcCover.setCoverPremium(wcCoverPremium);
							Double amount = 0.0;
							for(EmpTypeDetailsVO empTypeDetailsVO : wcVO.getEmpTypeDetails()) {
								amount = amount + empTypeDetailsVO.getWageroll();
							}
							LOGGER.info("WC::Sum insured-->"+amount);
							SumInsured contentSumInsured = new SumInsured();
							contentSumInsured.setAmount(amount);
							contentSumInsured.setCurrencyCode("AED");//TODO:Currency.getUnitName()
							wcCover.setSumInsured(contentSumInsured);
							covers.add(wcCover);
						
						}
						//Public Liability
						if (!Utils.isEmpty(groupDetails) && groupDetails instanceof PublicLiabilityVO) {
							PublicLiabilityVO publicLiabilityVO = (PublicLiabilityVO) groupDetails;

							Cover plCover = new Cover();

							plCover.setId(ServiceConstant.SECTION_PL_ID);
							plCover.setName(ServiceConstant.SECTION_PL_DESC);
							CoverPremium plCoverPremium = new CoverPremium();
							Double plPremium = 0.0;
							
							Double plBaseWAPremium=0.0;
							int studentCountAmt=0;
							Double workAwayAmt=0.0;
							
							if (!Utils.isEmpty(publicLiabilityVO.getPremium())) {
								LOGGER.info("PL::publicLiabilityVO.getPremium().getPremiumAmt()-->"+publicLiabilityVO.getPremium().getPremiumAmt());
								plPremium = publicLiabilityVO.getPremium().getPremiumAmt();
								//Adding two decimal values to the premium
								//plPremium =WSBusinessValidatorUtils.getFormattedDoubleWithTwoDecimals(publicLiabilityVO.getPremium().getPremiumAmt());
							}
							if(plPremium != 0) {
								plCover.setIsIncludedInPremium("Y");
								totalPremium= totalPremium + plPremium;
							} else {
								plCover.setIsIncludedInPremium("N");
							}										
							//reverted premium changes after currency class fix
						//	plCoverPremium.setAmount(new Double(Currency.getUnformattedScaledCurrency(new BigDecimal(plPremium),"SBS")));
							//Handled while setting the value no need to convert
							
							/*  plPremium = PLPremium + WorkAwayPremium
							 *  plPremium = PLPremium + (0.25 * PLPremium)
							 *  plPremium = 1.25 * PLPremium
							 *  PLPremium = (100/125) * plPremium
							 *  PLPremium = 0.8 * plPremium
							 *  and WorkAwayPremium = 0.2 * plPremium
							 */

							if(createSBSQuoteRequest.getLiabilityInformation().getWorkAwayRisksLimit()!=null && 
									createSBSQuoteRequest.getLiabilityInformation().getWorkAwayRisksLimit()>0 && 
									(createSBSQuoteRequest.getLiabilityInformation().getStudentCount()==null)    ) {
								LOGGER.info("Calculating PL premium when only Work away extenstion is present ");
							//	plCoverPremium.setAmount(WSBusinessValidatorUtils.getFormattedDoubleWithTwoDecimals(plPremium * 0.8)); // multiplying with 0.8 to remove WorkAwayRiskLimit Premium from PL M1043209
								//reverted premium changes after currency class fix
								plCoverPremium.setAmount(new Double(Currency.getUnformattedScaledCurrency(new BigDecimal(plPremium * 0.8),"SBS"))); 
								
							} else if(							
									createSBSQuoteRequest.getLiabilityInformation().getWorkAwayRisksLimit()!=null && 
									createSBSQuoteRequest.getLiabilityInformation().getWorkAwayRisksLimit()>0 && 
									(createSBSQuoteRequest.getLiabilityInformation().getStudentCount()!=null && 
											createSBSQuoteRequest.getLiabilityInformation().getStudentCount()>0) && ( !Utils.isEmpty(publicLiabilityVO.getPremium()) &&  publicLiabilityVO.getPremium().getPremiumAmt() >0)	) {
								LOGGER.info("Calculating base PL  premium ,student liability and work away extension when both work away extension and  student liability  is present  ");
								
								if(Double.parseDouble(createSBSQuoteRequest.getLiabilityInformation().getPublicLiabilityLimit().getAmount())==(Double.parseDouble(Utils.getSingleValueAppConfig( "PL_LIMIT_1")))) {
									studentCountAmt = createSBSQuoteRequest.getLiabilityInformation().getStudentCount() * (Integer.parseInt(Utils.getSingleValueAppConfig( "Student_Liability_Limit_1")));
								}else if (Double.parseDouble(createSBSQuoteRequest.getLiabilityInformation().getPublicLiabilityLimit().getAmount()) ==(Double.parseDouble(Utils.getSingleValueAppConfig( "PL_LIMIT_2")))) {
									studentCountAmt = createSBSQuoteRequest.getLiabilityInformation().getStudentCount() * (Integer.parseInt(Utils.getSingleValueAppConfig( "Student_Liability_Limit_2")));
								}else if (Double.parseDouble(createSBSQuoteRequest.getLiabilityInformation().getPublicLiabilityLimit().getAmount()) ==(Double.parseDouble(Utils.getSingleValueAppConfig( "PL_LIMIT_3")))) {
									studentCountAmt = createSBSQuoteRequest.getLiabilityInformation().getStudentCount() * (Integer.parseInt(Utils.getSingleValueAppConfig( "Student_Liability_Limit_3")));
								}else if (Double.parseDouble(createSBSQuoteRequest.getLiabilityInformation().getPublicLiabilityLimit().getAmount()) ==(Double.parseDouble(Utils.getSingleValueAppConfig( "PL_LIMIT_4")))) {
									studentCountAmt = createSBSQuoteRequest.getLiabilityInformation().getStudentCount() * (Integer.parseInt(Utils.getSingleValueAppConfig( "Student_Liability_Limit_4")));
								}else if (Double.parseDouble(createSBSQuoteRequest.getLiabilityInformation().getPublicLiabilityLimit().getAmount()) ==(Double.parseDouble(Utils.getSingleValueAppConfig( "PL_LIMIT_5")))) {
									studentCountAmt = createSBSQuoteRequest.getLiabilityInformation().getStudentCount() * (Integer.parseInt(Utils.getSingleValueAppConfig( "Student_Liability_Limit_5")));
								}else if (Double.parseDouble(createSBSQuoteRequest.getLiabilityInformation().getPublicLiabilityLimit().getAmount()) ==(Double.parseDouble(Utils.getSingleValueAppConfig( "PL_LIMIT_6")))) {
									studentCountAmt = createSBSQuoteRequest.getLiabilityInformation().getStudentCount() * (Integer.parseInt(Utils.getSingleValueAppConfig( "Student_Liability_Limit_6")));
								}else if (Double.parseDouble(createSBSQuoteRequest.getLiabilityInformation().getPublicLiabilityLimit().getAmount()) ==(Double.parseDouble(Utils.getSingleValueAppConfig( "PL_LIMIT_7")))) {
									studentCountAmt = createSBSQuoteRequest.getLiabilityInformation().getStudentCount() * (Integer.parseInt(Utils.getSingleValueAppConfig( "Student_Liability_Limit_7")));
								}else if (Double.parseDouble(createSBSQuoteRequest.getLiabilityInformation().getPublicLiabilityLimit().getAmount()) ==(Double.parseDouble(Utils.getSingleValueAppConfig( "PL_LIMIT_8")))) {
									studentCountAmt = createSBSQuoteRequest.getLiabilityInformation().getStudentCount() * (Integer.parseInt(Utils.getSingleValueAppConfig( "Student_Liability_Limit_8")));
								}else if (Double.parseDouble(createSBSQuoteRequest.getLiabilityInformation().getPublicLiabilityLimit().getAmount()) ==(Double.parseDouble(Utils.getSingleValueAppConfig( "PL_LIMIT_9")))) {
									studentCountAmt = createSBSQuoteRequest.getLiabilityInformation().getStudentCount() * (Integer.parseInt(Utils.getSingleValueAppConfig( "Student_Liability_Limit_9")));
								}else if (Double.parseDouble(createSBSQuoteRequest.getLiabilityInformation().getPublicLiabilityLimit().getAmount()) ==(Double.parseDouble(Utils.getSingleValueAppConfig( "PL_LIMIT_10")))) {
									studentCountAmt = createSBSQuoteRequest.getLiabilityInformation().getStudentCount() * (Integer.parseInt(Utils.getSingleValueAppConfig( "Student_Liability_Limit_10")));
								}
								
								//plBasePremium inclusive of PL base premium + work away extension
								plBaseWAPremium=plPremium-studentCountAmt;
								//Extract work away extension 
								workAwayAmt= plBaseWAPremium * 0.2 ;
								// PL cover includes PL Base premium + student Liability amount
								//plCoverPremium.setAmount(WSBusinessValidatorUtils.getFormattedDoubleWithTwoDecimals(plBaseWAPremium -workAwayAmt + studentCountAmt));
								//reverted premium changes after currency class fix
								plCoverPremium.setAmount(new Double(Currency.getUnformattedScaledCurrency(new BigDecimal(plBaseWAPremium -workAwayAmt + studentCountAmt))));
								
							}else {
								//reverted premium changes after currency class fix
								plCoverPremium.setAmount(new Double(Currency.getUnformattedScaledCurrency(new BigDecimal(plPremium))));
							}
							
														
							plCoverPremium.setCurrencyCode("AED");//TODO:Currency.getUnitName()
							
							/*
							 * For Deductible
							 */
							String deductible = Utils.getSingleValueAppConfig( "JLT_PL_DEDUCTIBLE" );
							if(!Utils.isEmpty(publicLiabilityVO.getSumInsuredDets())) {
								deductible = publicLiabilityVO.getSumInsuredDets().getDeductible().toString();
							}
							plCover.setDeductible(WSBusinessValidatorUtils.getFormatForDecimalWithSingleDigit(Double.parseDouble(deductible)).toString());  
							
							plCover.setCoverPremium(plCoverPremium);
							SumInsured plSumInsured = new SumInsured();
							Double plSI = 0.00;
							if(!Utils.isEmpty(publicLiabilityVO.getIndemnityAmtLimit())) {
								Integer siCode = publicLiabilityVO.getIndemnityAmtLimit();
								LOGGER.info("PL::publicLiabilityVO.getIndemnityAmtLimit():siCode-->"+publicLiabilityVO.getIndemnityAmtLimit());
								if(siCode==8) {		// For Showing limit in the resposne more than 10M
									plSI = publicLiabilityVO.getLiabilityLimit();
								}else {
									plSI = new Double(SvcUtils.getLookUpDescription("JLT_PL_LIM", policyVO.getScheme().getTariffCode().toString(), "ALL",siCode));
								}
								
								LOGGER.info("PL::plSI from lookup-->"+plSI);
							}
							plSumInsured.setAmount(plSI);
							plSumInsured.setCurrencyCode("AED");//TODO:Currency.getUnitName()
							plCover.setSumInsured(plSumInsured);
							covers.add(plCover);
							
							// work away limit for JLT 
							if (null != createSBSQuoteRequest.getLiabilityInformation().getWorkAwayRisksLimit()) {
								if (createSBSQuoteRequest.getLiabilityInformation().getWorkAwayRisksLimit() >= 0)
										 {

									Cover workAwayRisksLimit = new Cover();
									workAwayRisksLimit.setId(ServiceConstant.SECTION_WORK_AWAY_RISK_ID);
									workAwayRisksLimit.setName(ServiceConstant.SECTION_WORK_AWAY_RISK_ID_DESC);

									SumInsured workAwayRisksLimitSumInsured = new SumInsured();
									workAwayRisksLimitSumInsured.setCurrencyCode("AED");
									
									CoverPremium workAwayRisksLimitCoverPremium = new CoverPremium();
									workAwayRisksLimitCoverPremium.setCurrencyCode("AED");
									/*
									 * setting deductible as 0 because we are sending deductible in PL cover
									 * work away extension is coming under PL section, deductible will apply on whole PL section 
									 */
									workAwayRisksLimit.setDeductible("0.00");									
									
									
									//Only work away extension is present not student liability 
									
									if(createSBSQuoteRequest.getLiabilityInformation().getWorkAwayRisksLimit()>0
											&& (createSBSQuoteRequest.getLiabilityInformation().getStudentCount()==null)			
											) {
										LOGGER.info("Calculating work away extension premium when only Work away extenstion is present ");
										workAwayRisksLimit.setIsIncludedInPremium("Y");
										//workAwayRisksLimitCoverPremium.setAmount(WSBusinessValidatorUtils.getFormattedDoubleWithTwoDecimals(0.2*publicLiabilityVO.getPremium().getPremiumAmt())); // multiplying with 0.2 to get WorkAwayRiskLimit Premium from PL M1043209										
										//reverted premium changes after currency class fix
										workAwayRisksLimitCoverPremium.setAmount(new Double(Currency.getUnformattedScaledCurrency(new BigDecimal(0.2*publicLiabilityVO.getPremium().getPremiumAmt())))); 
										if(createSBSQuoteRequest.getLiabilityInformation().getWorkAwayRisksLimit().doubleValue() <= Double.parseDouble(createSBSQuoteRequest.getLiabilityInformation().getPublicLiabilityLimit().getAmount())) {
											workAwayRisksLimitSumInsured.setAmount(
													createSBSQuoteRequest.getLiabilityInformation().getWorkAwayRisksLimit().doubleValue());
										}
										else {
											workAwayRisksLimitSumInsured.setAmount(Double.parseDouble(
													createSBSQuoteRequest.getLiabilityInformation().getPublicLiabilityLimit().getAmount()));
										}
									} else if (	createSBSQuoteRequest.getLiabilityInformation().getWorkAwayRisksLimit()!=null && 
											createSBSQuoteRequest.getLiabilityInformation().getWorkAwayRisksLimit()>0 && 
											(createSBSQuoteRequest.getLiabilityInformation().getStudentCount()!=null && 
													createSBSQuoteRequest.getLiabilityInformation().getStudentCount()>0) ) {
										LOGGER.info("Assigning  calculated woraway extension premium when only Workaway extenstion is present ");
										workAwayRisksLimit.setIsIncludedInPremium("Y");
										//workAwayRisksLimitCoverPremium.setAmount(WSBusinessValidatorUtils.getFormattedDoubleWithTwoDecimals(workAwayAmt)); 
										//reverted premium changes after currency class fix
										workAwayRisksLimitCoverPremium.setAmount(new Double(Currency.getUnformattedScaledCurrency(new BigDecimal(workAwayAmt))));
										
										if(createSBSQuoteRequest.getLiabilityInformation().getWorkAwayRisksLimit().doubleValue() <= Double.parseDouble(createSBSQuoteRequest.getLiabilityInformation().getPublicLiabilityLimit().getAmount())) {
											workAwayRisksLimitSumInsured.setAmount(
													createSBSQuoteRequest.getLiabilityInformation().getWorkAwayRisksLimit().doubleValue());
										}
										else {
											workAwayRisksLimitSumInsured.setAmount(Double.parseDouble(
													createSBSQuoteRequest.getLiabilityInformation().getPublicLiabilityLimit().getAmount()));
										}
									}							
									else {
										workAwayRisksLimit.setIsIncludedInPremium("N");
										workAwayRisksLimitCoverPremium.setAmount(0.00);
									}
									
									workAwayRisksLimit.setSumInsured(workAwayRisksLimitSumInsured);
									workAwayRisksLimit.setCoverPremium(workAwayRisksLimitCoverPremium);

									covers.add(workAwayRisksLimit);
								}
							}
						}
						//Business Interruption
						if (!Utils.isEmpty(groupDetails) && groupDetails instanceof BIVO) {
							BIVO biVO = (BIVO) groupDetails;
							if(Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getRentAndIcowLimit())
									||createSBSQuoteRequest.getLiabilityInformation().getRentAndIcowLimit().getAmount() == 0 ) {
								Cover biCover = new Cover();
								/*Cover rentAndICOW = new Cover();
								rentAndICOW.setId(ServiceConstant.SECTION_ICOW_ID);
								rentAndICOW.setName(ServiceConstant.SECTION_ICOW_DESC);
								CoverPremium rentAndICOWCoverPremium = new CoverPremium();
								Double amount1 = 0.0;
								SumInsured rentAndICOWSumInsured = new SumInsured();
								rentAndICOWSumInsured.setAmount(amount1);
								rentAndICOWSumInsured.setCurrencyCode("AED");//TODO:Currency.getUnitName()
								rentAndICOW.setSumInsured(rentAndICOWSumInsured);
								Double rentAndICOWPremiumAmount = 0.0;
								rentAndICOWCoverPremium.setAmount(new Double(Currency.getUnformattedScaledCurrency(new BigDecimal(rentAndICOWPremiumAmount),"SBS")));
								rentAndICOWCoverPremium.setCurrencyCode("AED");//TODO:Currency.getUnitName()
								rentAndICOW.setIsIncludedInPremium("N");
								rentAndICOW.setDeductible(SvcUtils.getLookUpDescription(com.Constant.CONST_DEDUCTIBLES, com.Constant.CONST_BI_DEDUCTIBLES, "204", (int)biVO.getDeductible()));
								rentAndICOW.setCoverPremium(rentAndICOWCoverPremium);
								covers.add(rentAndICOW);*/
								
								
								
								
								biCover.setId(ServiceConstant.SECTION_BI_ID);
								biCover.setName(ServiceConstant.SECTION_BI_DESC);
								CoverPremium biCoverPremium = new CoverPremium();
								Double amount = 0.0;
								if(!Utils.isEmpty(biVO.getEstimatedGrossIncome())) {
									amount = amount + biVO.getEstimatedGrossIncome();
								}
								if(!Utils.isEmpty(biVO.getRentRecievable())) {
									amount = amount + biVO.getRentRecievable();
								}	
								if(!Utils.isEmpty(biVO.getWorkingLimit())) {
									amount = amount + biVO.getWorkingLimit();
								}
								SumInsured biSumInsured = new SumInsured();
								LOGGER.info("BI::biVO.getSumInsured()-->"+amount);
								biSumInsured.setAmount(amount);
								biSumInsured.setCurrencyCode("AED");//TODO:Currency.getUnitName()
								biCover.setSumInsured(biSumInsured);
								//Checking for warning message
								if(hasWarning(createSBSQuoteResponse, com.Constant.CONST_SBSWS_BI)) {
									LOGGER.info("4. Warning in BI section..setting premium to 0");
									if (!Utils.isEmpty(biVO.getPremium())) {
										biVO.getPremium().setPremiumAmt(0);
									}
								} 
								Double biPremium = 0.0;
								if (!Utils.isEmpty(biVO.getPremium())) {
									LOGGER.info("1. BI::biVO.getPremium().getPremiumAmt()-->"+biVO.getPremium().getPremiumAmt());
									biPremium = biVO.getPremium().getPremiumAmt();
									//Adding two decimal values to the premium
									//biPremium =WSBusinessValidatorUtils.getFormattedDoubleWithTwoDecimals(biVO.getPremium().getPremiumAmt());
								}
								
								if(biPremium != 0) {
									biCover.setIsIncludedInPremium("Y");
									totalPremium= totalPremium + biPremium;
								} else {
									biCover.setIsIncludedInPremium("N");
								}
								//reverted premium changes after currency class fix
								biCoverPremium.setAmount(new Double(Currency.getUnformattedScaledCurrency(new BigDecimal(biPremium),"SBS")));
								//Handled while setting the value no need to convert
								//biCoverPremium.setAmount(biPremium);//changed BigDecimal to Double for premium issue
								biCoverPremium.setCurrencyCode("AED");//TODO:Currency.getUnitName()
								/*
								 * For Deductible change
								 */
								biCover.setDeductible(SvcUtils.getLookUpDescription(com.Constant.CONST_DEDUCTIBLES, com.Constant.CONST_BI_DEDUCTIBLES, policyVO.getScheme().getTariffCode().toString(), (int)biVO.getDeductible()));
								
								biCover.setCoverPremium(biCoverPremium);
								covers.add(biCover);
							}
							else if((Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getAnnualRentReceivable()) || 
									createSBSQuoteRequest.getLiabilityInformation().getAnnualRentReceivable() == 0) && (Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getLossOfGrossProfitLimit()) || 
									createSBSQuoteRequest.getLiabilityInformation().getLossOfGrossProfitLimit().getAmount()==0)) {
								Cover rentAndICOW = new Cover();
								rentAndICOW.setId(ServiceConstant.SECTION_ICOW_ID);
								rentAndICOW.setName(ServiceConstant.SECTION_ICOW_DESC);
								CoverPremium rentAndICOWCoverPremium = new CoverPremium();
								Double amount = 0.0;
								if(!Utils.isEmpty(biVO.getEstimatedGrossIncome())) {
									amount = amount + biVO.getEstimatedGrossIncome();
								}
								if(!Utils.isEmpty(biVO.getRentRecievable())) {
									amount = amount + biVO.getRentRecievable();
								}	
								if(!Utils.isEmpty(biVO.getWorkingLimit())) {
									amount = amount + biVO.getWorkingLimit();
								}
								SumInsured rentAndICOWSumInsured = new SumInsured();
								rentAndICOWSumInsured.setAmount(amount);
								rentAndICOWSumInsured.setCurrencyCode("AED");//TODO:Currency.getUnitName()
								rentAndICOW.setSumInsured(rentAndICOWSumInsured);
								//Checking for warning message
								if(hasWarning(createSBSQuoteResponse, com.Constant.CONST_SBSWS_BI)) {
									LOGGER.info("1. Warning in BI section..setting premium to 0");
									if (!Utils.isEmpty(biVO.getPremium())) {
										biVO.getPremium().setPremiumAmt(0);
									}
								} 
								Double rentAndICOWPremiumAmount = 0.0;
								if (!Utils.isEmpty(biVO.getPremium())) {
									LOGGER.info("2. BI::biVO.getPremium().getPremiumAmt()-->"+biVO.getPremium().getPremiumAmt());
									rentAndICOWPremiumAmount = biVO.getPremium().getPremiumAmt();
									//Adding two decimal values to the premium
									//biPremium =WSBusinessValidatorUtils.getFormattedDoubleWithTwoDecimals(biVO.getPremium().getPremiumAmt());
								}
								
								if(rentAndICOWPremiumAmount != 0) {
									rentAndICOW.setIsIncludedInPremium("Y");
									totalPremium= totalPremium + rentAndICOWPremiumAmount;
								} else {
									rentAndICOW.setIsIncludedInPremium("N");
								}
								rentAndICOWCoverPremium.setAmount(new Double(Currency.getUnformattedScaledCurrency(new BigDecimal(rentAndICOWPremiumAmount),"SBS")));
								rentAndICOWCoverPremium.setCurrencyCode("AED");
								rentAndICOW.setDeductible(SvcUtils.getLookUpDescription(com.Constant.CONST_DEDUCTIBLES, com.Constant.CONST_BI_DEDUCTIBLES, policyVO.getScheme().getTariffCode().toString(), (int)biVO.getDeductible()));
								rentAndICOW.setCoverPremium(rentAndICOWCoverPremium);
								covers.add(rentAndICOW);
							}
							else {
								Cover biCover = new Cover();
								Cover rentAndICOW = new Cover();
								rentAndICOW.setId(ServiceConstant.SECTION_ICOW_ID);
								rentAndICOW.setName(ServiceConstant.SECTION_ICOW_DESC);
								CoverPremium rentAndICOWCoverPremium = new CoverPremium();
								Double amount1 = 0.0;
								if(!Utils.isEmpty(biVO.getWorkingLimit())) {
								amount1 = amount1 + biVO.getWorkingLimit();
								}
								SumInsured rentAndICOWSumInsured = new SumInsured();
								rentAndICOWSumInsured.setAmount(amount1);
								rentAndICOWSumInsured.setCurrencyCode("AED");//TODO:Currency.getUnitName()
								rentAndICOW.setSumInsured(rentAndICOWSumInsured);
								if(hasWarning(createSBSQuoteResponse, com.Constant.CONST_SBSWS_BI)) {
									LOGGER.info("2. Warning in BI section..setting premium to 0");
									if (!Utils.isEmpty(biVO.getPremium())) {
										biVO.getPremium().setPremiumAmt(0);
									}
								} 
								Double rentAndICOWPremiumAmount = 0.0;
								rentAndICOWCoverPremium.setAmount(new Double(Currency.getUnformattedScaledCurrency(new BigDecimal(rentAndICOWPremiumAmount),"SBS")));
								rentAndICOWCoverPremium.setCurrencyCode("AED");//TODO:Currency.getUnitName()
								rentAndICOW.setIsIncludedInPremium("Y");
								rentAndICOW.setDeductible(SvcUtils.getLookUpDescription(com.Constant.CONST_DEDUCTIBLES, com.Constant.CONST_BI_DEDUCTIBLES, policyVO.getScheme().getTariffCode().toString(), (int)biVO.getDeductible()));
								rentAndICOW.setCoverPremium(rentAndICOWCoverPremium);
								covers.add(rentAndICOW);
								
								
								
								
								biCover.setId(ServiceConstant.SECTION_BI_ID);
								biCover.setName(ServiceConstant.SECTION_BI_DESC);
								CoverPremium biCoverPremium = new CoverPremium();
								Double amount = 0.0;
								if(!Utils.isEmpty(biVO.getEstimatedGrossIncome())) {
									amount = amount + biVO.getEstimatedGrossIncome();
								}
								if(!Utils.isEmpty(biVO.getRentRecievable())) {
									amount = amount + biVO.getRentRecievable();
								}	
								/*if(!Utils.isEmpty(biVO.getWorkingLimit())) {
									amount = amount + biVO.getWorkingLimit();
								}*/
								SumInsured biSumInsured = new SumInsured();
								LOGGER.info("BI::biVO.getSumInsured()-->"+amount);
								biSumInsured.setAmount(amount);
								biSumInsured.setCurrencyCode("AED");//TODO:Currency.getUnitName()
								biCover.setSumInsured(biSumInsured);
								//Checking for warning message
								if(hasWarning(createSBSQuoteResponse, com.Constant.CONST_SBSWS_BI)) {
									LOGGER.info("3. Warning in BI section..setting premium to 0");
									if (!Utils.isEmpty(biVO.getPremium())) {
										biVO.getPremium().setPremiumAmt(0);
									}
								} 
								Double biPremium = 0.0;
								if (!Utils.isEmpty(biVO.getPremium())) {
									LOGGER.info("3. BI::biVO.getPremium().getPremiumAmt()-->"+biVO.getPremium().getPremiumAmt());
									biPremium = biVO.getPremium().getPremiumAmt();
									//Adding two decimal values to the premium
									//biPremium =WSBusinessValidatorUtils.getFormattedDoubleWithTwoDecimals(biVO.getPremium().getPremiumAmt());
								}
								
								if(biPremium != 0) {
									biCover.setIsIncludedInPremium("Y");
									totalPremium= totalPremium + biPremium;
								} else {
									biCover.setIsIncludedInPremium("N");
								}
								//reverted premium changes after currency class fix
								biCoverPremium.setAmount(new Double(Currency.getUnformattedScaledCurrency(new BigDecimal(biPremium),"SBS")));
								//Handled while setting the value no need to convert
								//biCoverPremium.setAmount(biPremium);//changed BigDecimal to Double for premium issue
								biCoverPremium.setCurrencyCode("AED");//TODO:Currency.getUnitName()
								/*
								 * For Deductible change
								 */
								biCover.setDeductible(SvcUtils.getLookUpDescription(com.Constant.CONST_DEDUCTIBLES, com.Constant.CONST_BI_DEDUCTIBLES, policyVO.getScheme().getTariffCode().toString(), (int)biVO.getDeductible()));
								
								biCover.setCoverPremium(biCoverPremium);
								covers.add(biCover);
							}
							
						}
						//Money
						if (!Utils.isEmpty(groupDetails) && groupDetails instanceof MoneyVO) {
							MoneyVO moneyVO = (MoneyVO) groupDetails;
							
							// Money section
							Cover moneyCover = new Cover();

							moneyCover.setId(ServiceConstant.SECTION_MONEY_ID);
							moneyCover.setName(ServiceConstant.SECTION_MONEY_DESC);
							CoverPremium moneyCoverPremium = new CoverPremium();
							SumInsured moneySumInsured = new SumInsured();
							Double moneySI = 0.0;
							if(!Utils.isEmpty(moneyVO.getSumInsuredDets())){
								List<SumInsuredVO> moneySIList = moneyVO.getSumInsuredDets();
								for(SumInsuredVO sumInsuredVO: moneySIList) {
									if(!Utils.isEmpty(sumInsuredVO)) {
										moneySI = moneySI + sumInsuredVO.getSumInsured();
									}
										
								}
								
							}
							// Added to give sum insured for cash residence
							if(!Utils.isEmpty(moneyVO.getCashResDetails())) {
								for (CashResidenceVO cashResidenceVO: moneyVO.getCashResDetails()) {
									if(!Utils.isEmpty(cashResidenceVO.getSumInsuredDets())) {
										moneySI = moneySI + cashResidenceVO.getSumInsuredDets().getSumInsured();
									}
								}
							}
							
							LOGGER.info("Money::moneySI-->"+moneySI);
							moneySumInsured.setAmount(moneySI);
							
							moneySumInsured.setCurrencyCode("AED");//TODO:Currency.getUnitName()
							moneyCover.setSumInsured(moneySumInsured);
							//Checking for warning message
							if(hasWarning(createSBSQuoteResponse, "SBSWS_MNY")) {
								LOGGER.info("Warning in Money section..setting premium to 0");
								if (!Utils.isEmpty(moneyVO.getPremium())) {
									moneyVO.getPremium().setPremiumAmt(0);
									if(!Utils.isEmpty(moneyVO.getContentsList())) {
										for(Contents content : moneyVO.getContentsList()) {
											if (!Utils.isEmpty(content.getPremium())) {
												content.getPremium().setPremiumAmt(0);
											}
										}
									}
								}
								if(!Utils.isEmpty(moneyVO.getCashResidencePremium())) {
									moneyVO.getCashResidencePremium().setPremiumAmt(0);
								}
								
							} 
							Double moneyPremium = 0.0;
							if (!Utils.isEmpty(moneyVO.getPremium())) {
								LOGGER.info("Money::moneyVO.getPremium()-->"+moneyVO.getPremium().getPremiumAmt());	
								moneyPremium = moneyVO.getPremium().getPremiumAmt();
								//Adding two decimal values to the premium
								//moneyPremium = WSBusinessValidatorUtils.getFormattedDoubleWithTwoDecimals(moneyVO.getPremium().getPremiumAmt());
								
							}
							
							if(moneyPremium != 0) {
								moneyCover.setIsIncludedInPremium("Y");
								totalPremium= totalPremium + moneyPremium;
							} else {
								moneyCover.setIsIncludedInPremium("N");
							}
							/*
							 * Added for deductible change
							 */
							Double deductible = 0.00;
							if(!Utils.isEmpty(moneyVO.getContentsList())) {
								for(Contents content : moneyVO.getContentsList()) {
									if (!Utils.isEmpty(content.getDeductibles())) {
										deductible = content.getDeductibles().doubleValue();
										break;
									}
								}
							}
							
							//reverted premium changes after currency class fix
							moneyCoverPremium.setAmount(new Double(Currency.getUnformattedScaledCurrency(new BigDecimal(moneyPremium),"SBS")));
							//Handled while setting the value no need to convert
							//moneyCoverPremium.setAmount(moneyPremium);//changed BigDecimal to Double for premium issue
							moneyCoverPremium.setCurrencyCode("AED");//TODO:Currency.getUnitName()
							moneyCover.setDeductible(WSBusinessValidatorUtils.getFormatForDecimalWithSingleDigit(deductible).toString());
							
							moneyCover.setCoverPremium(moneyCoverPremium);
							covers.add(moneyCover);										
							/*
							if(!Utils.isEmpty(moneyVO.getContentsList())) {
								for(Contents content : moneyVO.getContentsList()) {
									Integer riskType = content.getRiskType();
									Integer riskCat = content.getRiskCat();
									//Estimated	Annual Carryings
									if(riskType == 1 && riskCat == 1) {
										Cover annualTransitMoneyCover = new Cover();

										annualTransitMoneyCover.setId(ServiceConstant.SECTION_ANNUAL_MONEY_IN_TRANSIT_ID);
										annualTransitMoneyCover.setName(ServiceConstant.SECTION_ANNUAL_MONEY_IN_TRANSIT_DESC);
										annualTransitMoneyCover.setIsIncludedInPremium("Y");
										CoverPremium annualTransitMoneyCoverPremium = new CoverPremium();
										Double estimatedAnnualPremium = 0.0;
										if (!Utils.isEmpty(content.getPremium())) {
											LOGGER.info("Estimated Annual Cash in Transit::content.getPremium().getPremiumAmt()-->"+content.getPremium().getPremiumAmt());
											estimatedAnnualPremium = content.getPremium().getPremiumAmt();
										}
										annualTransitMoneyCoverPremium.setAmount(estimatedAnnualPremium);
										annualTransitMoneyCoverPremium.setCurrencyCode("AED");//TODO:Currency.getUnitName()
										annualTransitMoneyCover.setCoverPremium(annualTransitMoneyCoverPremium);
										SumInsured annualTransitSumInsured = new SumInsured();
										Double amount = 0.0;
										if(!Utils.isEmpty(content.getCover())) {
											amount = content.getCover().doubleValue();
										}
										LOGGER.info("Annual Transit Sum Insured::content.getCover()-->"+amount);
										annualTransitSumInsured.setAmount(amount);
										annualTransitSumInsured.setCurrencyCode("AED");//TODO:Currency.getUnitName()
										annualTransitMoneyCover.setSumInsured(annualTransitSumInsured);
										covers.add(annualTransitMoneyCover);										
									}
									//Cash In Transit (Single Transit Limit)
									if(riskType == 1 && riskCat == 2) {
										Cover cashInTransitCover = new Cover();

										cashInTransitCover.setId(ServiceConstant.SECTION_MONEY_IN_TRANSIT_ID);
										cashInTransitCover.setName(ServiceConstant.SECTION_MONEY_IN_TRANSIT_DESC);
										cashInTransitCover.setIsIncludedInPremium("Y");
										CoverPremium cashInTransitCoverPremium = new CoverPremium();
										Double cashInTransitPremium = 0.0;
										if (!Utils.isEmpty(content.getPremium())) {
											LOGGER.info("Cash in Transit::content.getPremium().getPremiumAmt()-->"+content.getPremium().getPremiumAmt());
											cashInTransitPremium = content.getPremium().getPremiumAmt();
										}
										cashInTransitCoverPremium.setAmount(cashInTransitPremium);
										cashInTransitCoverPremium.setCurrencyCode("AED");//TODO:Currency.getUnitName()
										cashInTransitCover.setCoverPremium(cashInTransitCoverPremium);
										SumInsured cashInTransitSumInsured = new SumInsured();
										Double amount = 0.0;
										if(!Utils.isEmpty(content.getCover())) {
											amount = content.getCover().doubleValue();
										}
										LOGGER.info("Cash in Transit Sum Insured::content.getCover()-->"+amount);
										cashInTransitSumInsured.setAmount(amount);
										cashInTransitSumInsured.setCurrencyCode("AED");//TODO:Currency.getUnitName()
										cashInTransitCover.setSumInsured(cashInTransitSumInsured);

										covers.add(cashInTransitCover);										
									}
									//Cash locked in safe during business hour
									if(riskType == 3 && riskCat == 0) {
										Cover cashInLockedSafeCover = new Cover();

										cashInLockedSafeCover.setId(ServiceConstant.SECTION_MONEY_IN_LOCKED_SAFE_ID);
										cashInLockedSafeCover.setName(ServiceConstant.SECTION_MONEY_IN_LOCKED_SAFE_DESC);
										cashInLockedSafeCover.setIsIncludedInPremium("Y");
										CoverPremium cashInLockedSafeCoverPremium = new CoverPremium();
										Double cashInLockedSafe = 0.0;
										if (!Utils.isEmpty(content.getPremium())) {
											LOGGER.info("Cash in Locked Safe::content.getPremium().getPremiumAmt()-->"+content.getPremium().getPremiumAmt());
											cashInLockedSafe = content.getPremium().getPremiumAmt();
										}
										cashInLockedSafeCoverPremium.setAmount(cashInLockedSafe);
										cashInLockedSafeCoverPremium.setCurrencyCode("AED");//TODO:Currency.getUnitName()
										cashInLockedSafeCover.setCoverPremium(cashInLockedSafeCoverPremium);
										SumInsured cashInLockedSafeSumInsured = new SumInsured();
										Double amount = 0.0;
										if(!Utils.isEmpty(content.getCover())) {
											amount = content.getCover().doubleValue();
										}
										LOGGER.info("Cash in Locked Safe Sum Insured::content.getCover()-->"+amount);
										cashInLockedSafeSumInsured.setAmount(amount);
										cashInLockedSafeSumInsured.setCurrencyCode("AED");//TODO:Currency.getUnitName()
										cashInLockedSafeCover.setSumInsured(cashInLockedSafeSumInsured);
										covers.add(cashInLockedSafeCover);										
									}
									//Cash locked in drawer during business hour
									if(riskType == 8 && riskCat == 0) {
										Cover cashInLockedDrawerCover = new Cover();

										cashInLockedDrawerCover.setId(ServiceConstant.SECTION_MONEY_IN_LOCKED_DRAWER_ID);
										cashInLockedDrawerCover.setName(ServiceConstant.SECTION_MONEY_IN_LOCKED_DRAWER_DESC);
										cashInLockedDrawerCover.setIsIncludedInPremium("Y");
										CoverPremium cashInLockedDrawerCoverPremium = new CoverPremium();
										Double cashInLockedDrawerPremium = 0.0;
										if (!Utils.isEmpty(content.getPremium())) {
											LOGGER.info("Cash in Locked Safe::content.getPremium().getPremiumAmt()-->"+content.getPremium().getPremiumAmt());
											cashInLockedDrawerPremium = content.getPremium().getPremiumAmt();
										}
										cashInLockedDrawerCoverPremium.setAmount(cashInLockedDrawerPremium);
										cashInLockedDrawerCoverPremium.setCurrencyCode("AED");//TODO:Currency.getUnitName()
										cashInLockedDrawerCover.setCoverPremium(cashInLockedDrawerCoverPremium);
										SumInsured cashInLockedDrawerSumInsured = new SumInsured();
										Double amount = 0.0;
										if(!Utils.isEmpty(content.getCover())) {
											amount = content.getCover().doubleValue();
										}
										LOGGER.info("Cash in Locked Drawer Sum Insured::content.getCover()-->"+amount);
										cashInLockedDrawerSumInsured.setAmount(amount);
										cashInLockedDrawerSumInsured.setCurrencyCode("AED");//TODO:Currency.getUnitName()
										cashInLockedDrawerCover.setSumInsured(cashInLockedDrawerSumInsured);
										covers.add(cashInLockedDrawerCover);										
									}								
								}
							}
							//Cash Residence
							List<CashResidenceVO> cashResidenceVOList = moneyVO.getCashResDetails();
							for(CashResidenceVO cashResidenceVO: cashResidenceVOList) {
								Cover cashInResidenceCover = new Cover();

								cashInResidenceCover.setId(ServiceConstant.SECTION_CASH_IN_RESIDENCE_ID);
								cashInResidenceCover.setName(ServiceConstant.SECTION_CASH_IN_RESIDENCE_DESC);
								cashInResidenceCover.setIsIncludedInPremium("Y");
								CoverPremium cashInResidenceCoverPremium = new CoverPremium();
								Double cashInResidencePremium = 0.0;
								if (!Utils.isEmpty(moneyVO.getCashResidencePremium())) {
									LOGGER.info("Cash in Employee Residence::moneyVO.getCashResidencePremium().getPremiumAmt()-->"+moneyVO.getCashResidencePremium().getPremiumAmt());
									cashInResidencePremium = moneyVO.getCashResidencePremium().getPremiumAmt();
								}
								cashInResidenceCoverPremium.setAmount(cashInResidencePremium);
								cashInResidenceCoverPremium.setCurrencyCode("AED");//TODO:Currency.getUnitName()
								cashInResidenceCover.setCoverPremium(cashInResidenceCoverPremium);
								SumInsured cashInResidenceSumInsured = new SumInsured();
								Double cashInResidenceSI = 0.0;
								if(!Utils.isEmpty(cashResidenceVO.getSumInsuredDets())){
									LOGGER.info("Cash in Employee Residence::cashResidenceVO.getSumInsuredDets().getSumInsured()-->"+cashResidenceVO.getSumInsuredDets().getSumInsured());
									cashInResidenceSI = cashResidenceVO.getSumInsuredDets().getSumInsured();
									cashInResidenceSumInsured.setAmount(cashInResidenceSI);
								}
								cashInResidenceSumInsured.setCurrencyCode("AED");//TODO:Currency.getUnitName()
								cashInResidenceCover.setSumInsured(cashInResidenceSumInsured);
								covers.add(cashInResidenceCover);										
							}*/
							

						}							
						//Fidelity Gurantee
						if (!Utils.isEmpty(groupDetails) && groupDetails instanceof FidelityVO) {
							FidelityVO fidelityVO = (FidelityVO) groupDetails;
							Cover fidelityCover = new Cover();

							fidelityCover.setId(ServiceConstant.SECTION_FIDELITY_ID);
							fidelityCover.setName(ServiceConstant.SECTION_FIDELITY_DESC);

							
							Double fgSI = 0.00;
							
							for(FidelityNammedEmployeeDetailsVO fidelityNammedEmployeeDetailsVO : fidelityVO.getFidelityEmployeeDetails()) {
								LOGGER.info("Fidelity Gurantee Named Employee::fidelityNammedEmployeeDetailsVO.getLimitPerPerson()-->"+fidelityNammedEmployeeDetailsVO.getLimitPerPerson());
								fgSI = fgSI + fidelityNammedEmployeeDetailsVO.getLimitPerPerson();
							}
							for(FidelityUnnammedEmployeeVO fidelityUnnammedEmployeeVO : fidelityVO.getUnnammedEmployeeDetails()) {
								LOGGER.info("Fidelity Gurantee UnNamed Employee::fidelityUnnammedEmployeeVO.getLimitPerPerson()-->"+fidelityUnnammedEmployeeVO.getLimitPerPerson());
								fgSI = fgSI + fidelityUnnammedEmployeeVO.getLimitPerPerson();
							}
							SumInsured fidelityGuranteeSumInsured = new SumInsured();
							LOGGER.info("Fidelity Gurantee::Sum Insured -->"+fgSI);
							fidelityGuranteeSumInsured.setAmount(fgSI);
							fidelityGuranteeSumInsured.setCurrencyCode("AED");//TODO:Currency.getUnitName()
							fidelityCover.setSumInsured(fidelityGuranteeSumInsured);
							
							//Checking warning message
							boolean hasWarning = hasWarning(createSBSQuoteResponse, "SBSWS_FG");
							CoverPremium fidelityCoverPremium = new CoverPremium();
							if(hasWarning) {
								LOGGER.info("Warning in FG section.. setting premium to 0");
								fidelityVO.setFidAggregateBasePremium(0);
							}
							
							LOGGER.info("Fidelity Gurantee::fidelityVO.getFidAggregateBasePremium()-->"+fidelityVO.getFidAggregateBasePremium());
							Double fidelityPremium = new Double(fidelityVO.getFidAggregateBasePremium());
							fgBasePremium = fidelityPremium;
							System.out.println("Before adding to the premium-------------------"+fidelityPremium);
							LOGGER.info("Fidelity Gurantee Named Employee::fidelityNammedEmployeeDetailsVO.getPremium()--_1"+fidelityPremium);
							for(FidelityNammedEmployeeDetailsVO fidelityNammedEmployeeDetailsVO : fidelityVO.getFidelityEmployeeDetails()) {
								if(!Utils.isEmpty(fidelityNammedEmployeeDetailsVO.getPremium())){
									if(hasWarning) {
										fidelityNammedEmployeeDetailsVO.getPremium().setPremiumAmt(0);
									}
									LOGGER.info("Fidelity Gurantee Named Employee::fidelityNammedEmployeeDetailsVO.getPremium()--_2"+fidelityNammedEmployeeDetailsVO.getPremium().getPremiumAmt());
									//Adding two decimal values to the premium
								fidelityPremium = fidelityPremium + fidelityNammedEmployeeDetailsVO.getPremium().getPremiumAmt();
								//	fidelityPremium = WSBusinessValidatorUtils.getFormattedDoubleWithTwoDecimals(fidelityPremium + fidelityNammedEmployeeDetailsVO.getPremium().getPremiumAmt());
									System.out.println("After adding the premium-------------------"+fidelityPremium);
									LOGGER.info("Fidelity Gurantee Named Employee::fidelityNammedEmployeeDetailsVO.getPremium()--_3"+fidelityPremium);
								}
							}
							for(FidelityUnnammedEmployeeVO fidelityUnnammedEmployeeVO : fidelityVO.getUnnammedEmployeeDetails()) {
								if(!Utils.isEmpty(fidelityUnnammedEmployeeVO.getPremium())){
									if(hasWarning) {
										fidelityUnnammedEmployeeVO.getPremium().setPremiumAmt(0);
									}
									LOGGER.info("Fidelity Gurantee UnNamed Employee::fidelityUnnammedEmployeeVO.getPremium()-->"+fidelityUnnammedEmployeeVO.getPremium().getPremiumAmt());
									fidelityPremium = fidelityPremium + fidelityUnnammedEmployeeVO.getPremium().getPremiumAmt();
									//Adding two decimal values to the premium
									//fidelityPremium = fidelityPremium + WSBusinessValidatorUtils.getFormattedDoubleWithTwoDecimals(fidelityUnnammedEmployeeVO.getPremium().getPremiumAmt());
								}
							}
							if(fidelityPremium != 0) {
								fidelityCover.setIsIncludedInPremium("Y");
								totalPremium= totalPremium + fidelityPremium;
							} else {
								fidelityCover.setIsIncludedInPremium("N");
							}
							//reverted premium changes after currency class fix
							fidelityCoverPremium.setAmount(new Double(Currency.getUnformattedScaledCurrency(new BigDecimal(fidelityPremium),"SBS")));
							//Handled while setting the value no need to convert
							//fidelityCoverPremium.setAmount(fidelityPremium);//changed BigDecimal to Double for premium issue
							fidelityCoverPremium.setCurrencyCode("AED");//TODO:Currency.getUnitName()
							
							/*
							 *  For Deductible change
							 */
							Double deductible= 0.00 ;
							if(!Utils.isEmpty(fidelityVO.getDeductible()))
								deductible = fidelityVO.getDeductible();
							
							fidelityCover.setDeductible(WSBusinessValidatorUtils.getFormatForDecimalWithSingleDigit(deductible).toString());
							fidelityCover.setCoverPremium(fidelityCoverPremium);

							covers.add(fidelityCover);
						}
						//Electronic Equipment
						if (!Utils.isEmpty(groupDetails) && groupDetails instanceof EEVO) {
							EEVO eeVO = (EEVO) groupDetails;
							Double deductible = 0.00;
							for(EquipmentVO equipmentVO : eeVO.getEquipmentDtls()) {
								//Portable
								if(!Utils.isEmpty(equipmentVO.getEquipmentType()) && equipmentVO.getEquipmentType().equals("4")) {
									Cover portableEquipmentCover = new Cover();
									portableEquipmentCover.setId(ServiceConstant.SECTION_PORTABLE_EQUIPMENT_ID);
									portableEquipmentCover.setName(ServiceConstant.SECTION_PORTABLE_EQUIPMENT_DESC);//Need confirmation on the cover id and description mapping

									SumInsured portableEquipmentSumInsured = new SumInsured();
									Double eeSI = 0.0;
									if(!Utils.isEmpty(equipmentVO.getSumInsuredDetails())) {
										LOGGER.info("Portable Equipment::equipmentVO.getSumInsuredDetails().getSumInsured()-->"+equipmentVO.getSumInsuredDetails().getSumInsured());
										eeSI = equipmentVO.getSumInsuredDetails().getSumInsured();
										if(!Utils.isEmpty(equipmentVO.getSumInsuredDetails().getDeductible()))
											deductible = equipmentVO.getSumInsuredDetails().getDeductible();
									}
									portableEquipmentSumInsured.setAmount(eeSI);
									portableEquipmentSumInsured.setCurrencyCode("AED");//TODO:Currency.getUnitName()
									portableEquipmentCover.setSumInsured(portableEquipmentSumInsured);
									
									//Checking warnings
									if(hasWarning(createSBSQuoteResponse, "SBSWS_EE")) {
										LOGGER.info("Warning in Portable equipment section..setting premium to 0");
										if(!Utils.isEmpty(equipmentVO.getPremium())) {
											equipmentVO.getPremium().setPremiumAmt(0.00);
										}
									}
									
									CoverPremium portableEquipmentCoverPremium = new CoverPremium();
									Double eePremium =0.0;
									if(!Utils.isEmpty(equipmentVO.getPremium())) {
										portableEquipmentCover.setIsIncludedInPremium("Y");
										LOGGER.info("Portable Equipment::equipmentVO.getPremium().getPremiumAmt()-->"+equipmentVO.getPremium().getPremiumAmt());
										eePremium = equipmentVO.getPremium().getPremiumAmt();
										//Adding two decimal values to the premium
									//	eePremium = WSBusinessValidatorUtils.getFormattedDoubleWithTwoDecimals(equipmentVO.getPremium().getPremiumAmt());
									}
									
									if(eePremium != 0) {
										portableEquipmentCover.setIsIncludedInPremium("Y");
										totalPremium= totalPremium + eePremium;
									}else {
										portableEquipmentCover.setIsIncludedInPremium("N");

									}
									//reverted premium changes after currency class fix
									portableEquipmentCoverPremium.setAmount(new Double(Currency.getUnformattedScaledCurrency(new BigDecimal(eePremium),"SBS")));
									//Handled while setting the value no need to convert
									//portableEquipmentCoverPremium.setAmount(eePremium);//changed BigDecimal to Double for premium issue
									portableEquipmentCoverPremium.setCurrencyCode("AED");//TODO:Currency.getUnitName()
									
									portableEquipmentCover.setDeductible(WSBusinessValidatorUtils.getFormatForDecimalWithSingleDigit(deductible).toString()); // for deductible change
									portableEquipmentCover.setCoverPremium(portableEquipmentCoverPremium);

									covers.add(portableEquipmentCover);

								}
								//Non-Portable
								if(!Utils.isEmpty(equipmentVO.getEquipmentType()) && equipmentVO.getEquipmentType().equals("6")) {
									Cover portableEquipmentCover = new Cover();
									portableEquipmentCover.setId(ServiceConstant.SECTION_NON_PORTABLE_EQUIPMENT_ID);
									portableEquipmentCover.setName(ServiceConstant.SECTION_NON_PORTABLE_EQUIPMENT_DESC);//Need confirmation on the cover id and description mapping
									SumInsured portableEquipmentSumInsured = new SumInsured();
									Double eeSI = 0.0;
									if(!Utils.isEmpty(equipmentVO.getSumInsuredDetails())) {
										LOGGER.info("Portable Equipment::equipmentVO.getSumInsuredDetails().getSumInsured()-->"+equipmentVO.getSumInsuredDetails().getSumInsured());
										eeSI = equipmentVO.getSumInsuredDetails().getSumInsured();
										if(!Utils.isEmpty(equipmentVO.getSumInsuredDetails().getDeductible()))
											deductible = equipmentVO.getSumInsuredDetails().getDeductible();
									}
									portableEquipmentSumInsured.setAmount(eeSI);
									portableEquipmentSumInsured.setCurrencyCode("AED");//TODO:Currency.getUnitName()
									portableEquipmentCover.setSumInsured(portableEquipmentSumInsured);

									//Checking warnings
									if(hasWarning(createSBSQuoteResponse, "SBSWS_EE")) {
										if(!Utils.isEmpty(equipmentVO.getPremium())) {
											equipmentVO.getPremium().setPremiumAmt(0);
										}
									}
									
									CoverPremium portableEquipmentCoverPremium = new CoverPremium();
									Double eePremium = 0.0;
									if(!Utils.isEmpty(equipmentVO.getPremium())) {
										portableEquipmentCover.setIsIncludedInPremium("Y");
										LOGGER.info("Portable Equipment::equipmentVO.getPremium().getPremiumAmt()-->"+equipmentVO.getPremium().getPremiumAmt());
										eePremium = equipmentVO.getPremium().getPremiumAmt();
										//Adding two decimal values to the premium
										//eePremium = WSBusinessValidatorUtils.getFormattedDoubleWithTwoDecimals(equipmentVO.getPremium().getPremiumAmt());
									} 
									
									if(eePremium != 0) {
										portableEquipmentCover.setIsIncludedInPremium("Y");
										totalPremium= totalPremium + eePremium;
									}
									else {
										portableEquipmentCover.setIsIncludedInPremium("N");
									}
									//reverted premium changes after currency class fix
									portableEquipmentCoverPremium.setAmount(new Double(Currency.getUnformattedScaledCurrency(new BigDecimal(eePremium),"SBS")));
									//Handled while setting the value no need to convert
									//portableEquipmentCoverPremium.setAmount(eePremium);//changed BigDecimal to Double for premium issue
									portableEquipmentCoverPremium.setCurrencyCode("AED");//TODO:Currency.getUnitName()
									portableEquipmentCover.setDeductible(WSBusinessValidatorUtils.getFormatForDecimalWithSingleDigit(deductible).toString()); // for deductible
									
									portableEquipmentCover.setCoverPremium(portableEquipmentCoverPremium);

									covers.add(portableEquipmentCover);

								}
							}
						}							
						//Machinery Breakdown
						if (!Utils.isEmpty(groupDetails) && groupDetails instanceof MBVO) {
							MBVO mbVO = (MBVO) groupDetails;
							Double deductible = 0.00;
							
							for(MachineDetailsVO mbDetailsVO : mbVO.getMachineryDetails()) {
								if(mbDetailsVO.getMachineryType() == 3) {
									Cover machineryBreakddownCover = new Cover();
									machineryBreakddownCover.setId(ServiceConstant.SECTION_MACHINERY_BREAKDOWN_ID);
									machineryBreakddownCover.setName(ServiceConstant.SECTION_MACHINERY_BREAKDOWN_DESC);
									SumInsured machineryBreakdownSumInsured = new SumInsured();
									Double mbSI = 0.0;
									if(!Utils.isEmpty(mbDetailsVO.getSumInsuredVO())) {
										LOGGER.info("Machinery Breakdown::mbDetailsVO.getSumInsuredVO().getSumInsured()-->"+mbDetailsVO.getSumInsuredVO().getSumInsured());
										mbSI = mbDetailsVO.getSumInsuredVO().getSumInsured();
										
										if(!Utils.isEmpty(mbDetailsVO.getSumInsuredVO().getDeductible()))
											deductible = mbDetailsVO.getSumInsuredVO().getDeductible();
									}
									machineryBreakdownSumInsured.setAmount(mbSI);//If JLT sends multiple cover then it should be taken from MBVO
									machineryBreakdownSumInsured.setCurrencyCode("AED");//TODO:Currency.getUnitName()
									machineryBreakddownCover.setSumInsured(machineryBreakdownSumInsured);

									//Checking warnings
									if(hasWarning(createSBSQuoteResponse, "SBSWS_MB")) {
										LOGGER.info("Warning in Machinery Breakdown section..setting premium to 0");
										if(!Utils.isEmpty(mbDetailsVO.getPremium())) {
											mbDetailsVO.getPremium().setPremiumAmt(0.00);
										}
									}
									CoverPremium machineryBreakDowntCoverPremium = new CoverPremium();
									Double mbPremium = 0.0;
									if(!Utils.isEmpty(mbDetailsVO.getPremium())) {
										machineryBreakddownCover.setIsIncludedInPremium("Y");
										LOGGER.info("Machinery Breakdown::mbDetailsVO.getPremium().getPremiumAmt()-->"+mbDetailsVO.getPremium().getPremiumAmt());
										mbPremium = mbDetailsVO.getPremium().getPremiumAmt();
										//Adding two decimal values to the premium
									//	mbPremium = WSBusinessValidatorUtils.getFormattedDoubleWithTwoDecimals(mbDetailsVO.getPremium().getPremiumAmt());
									} 
									if(mbPremium != 0) {
										machineryBreakddownCover.setIsIncludedInPremium("Y");
										totalPremium= totalPremium + mbPremium;
									}
									else {
										machineryBreakddownCover.setIsIncludedInPremium("N");
									}
									//reverted premium changes after currency class fix
									machineryBreakDowntCoverPremium.setAmount(new Double(Currency.getUnformattedScaledCurrency(new BigDecimal(mbPremium),"SBS")));
									//Handled while setting the value no need to convert
									//machineryBreakDowntCoverPremium.setAmount(mbPremium);//changed BigDecimal to Double for premium issue
									machineryBreakDowntCoverPremium.setCurrencyCode("AED");//TODO:Currency.getUnitName()
									machineryBreakddownCover.setDeductible(WSBusinessValidatorUtils.getFormatForDecimalWithSingleDigit(deductible).toString());  // for deductible change
									
									machineryBreakddownCover.setCoverPremium(machineryBreakDowntCoverPremium);

									covers.add(machineryBreakddownCover);
								}
							}
						}
						//Deterioration of Stock
						if (!Utils.isEmpty(groupDetails) && groupDetails instanceof DeteriorationOfStockVO) {
							DeteriorationOfStockVO dosVO = (DeteriorationOfStockVO) groupDetails;
							Double deductible = 0.00;
							
							if(!Utils.isEmpty(dosVO.getDeteriorationOfStockDetails()) && !Utils.isEmpty(dosVO.getDeteriorationOfStockDetails().get(0))) {//Only frozen food is getting set. If more cover comes we have to check DOS type
								DeteriorationOfStockDetailsVO deteriorationOfStockDetailsVO = (DeteriorationOfStockDetailsVO)dosVO.getDeteriorationOfStockDetails().get(0);	
								Cover dosCover = new Cover();
								dosCover.setId(ServiceConstant.SECTION_DETERIORATION_OF_STOCK_ID);
								dosCover.setName(ServiceConstant.SECTION_DETERIORATION_OF_STOCK_DESC);
								SumInsured dosSumInsured = new SumInsured();
								Double dosSI = 0.0;
								if(!Utils.isEmpty(deteriorationOfStockDetailsVO.getSumInsuredDetails())) {
									LOGGER.info("Deterioration of stock Sum Insured::deteriorationOfStockDetailsVO.getSumInsuredDetails().getSumInsured()-->"+deteriorationOfStockDetailsVO.getSumInsuredDetails().getSumInsured());
									dosSI = deteriorationOfStockDetailsVO.getSumInsuredDetails().getSumInsured();
									/*
									 * For deductible change
									 */
									if(!Utils.isEmpty(deteriorationOfStockDetailsVO.getSumInsuredDetails().getDeductible()))
										deductible = deteriorationOfStockDetailsVO.getSumInsuredDetails().getDeductible();
								}
								dosSumInsured.setAmount(dosSI);
								dosSumInsured.setCurrencyCode("AED");//TODO:Currency.getUnitName()
								dosCover.setSumInsured(dosSumInsured);
								//Checking warnings
								if(hasWarning(createSBSQuoteResponse, "SBSWS_DOS")) {
									LOGGER.info("Warning in Deterioration of Stock section.. setting premium to 0");
									if(!Utils.isEmpty(deteriorationOfStockDetailsVO.getPremium())) {
										deteriorationOfStockDetailsVO.getPremium().setPremiumAmt(0);
									}
								}
								CoverPremium deteriorarationOfStockCoverPremium = new CoverPremium();
								Double dosPremium = 0.0;
								if(!Utils.isEmpty(deteriorationOfStockDetailsVO.getPremium())) {
									dosCover.setIsIncludedInPremium("Y");
									LOGGER.info("Deterioration of Stock::deteriorationOfStockDetailsVO.getPremium().getPremiumAmt()-->"+deteriorationOfStockDetailsVO.getPremium().getPremiumAmt());
									dosPremium = deteriorationOfStockDetailsVO.getPremium().getPremiumAmt();
									//Adding two decimal values to the premium
								//	dosPremium = WSBusinessValidatorUtils.getFormattedDoubleWithTwoDecimals(deteriorationOfStockDetailsVO.getPremium().getPremiumAmt());
								} 
								
								if(dosPremium != 0) {
									dosCover.setIsIncludedInPremium("Y");
									totalPremium= totalPremium + dosPremium;
								}
								else {
									dosCover.setIsIncludedInPremium("N");
								}
								//reverted premium changes after currency class fix
								deteriorarationOfStockCoverPremium.setAmount(new Double(Currency.getUnformattedScaledCurrency(new BigDecimal(dosPremium),"SBS")));
								//Handled while setting the value no need to convert
								//deteriorarationOfStockCoverPremium.setAmount(dosPremium);//changed BigDecimal to Double for premium issue
								deteriorarationOfStockCoverPremium.setCurrencyCode("AED");//TODO:Currency.getUnitName()
								dosCover.setDeductible(WSBusinessValidatorUtils.getFormatForDecimalWithSingleDigit(deductible).toString()); // for deductible change
								dosCover.setCoverPremium(deteriorarationOfStockCoverPremium);
								covers.add(dosCover);
							}
						}
						//Travel Baggage
						if (!Utils.isEmpty(groupDetails) && groupDetails instanceof TravelBaggageVO) {
							TravelBaggageVO travelBaggageVO = (TravelBaggageVO) groupDetails;
							
							Cover travelBaggageCover = new Cover();
							Double deductible = 0.00;	
							
							travelBaggageCover.setId(ServiceConstant.SECTION_TRAVEL_BAGGAGE_ID);
							travelBaggageCover.setName(ServiceConstant.SECTION_TRAVEL_BAGGAGE_DESC);
							//travelBaggageCover.setAdditionalProperty("nameflag", "true");
							SumInsured travelBaggageSumInsured = new SumInsured();
							LOGGER.info("Travel Baggage::travelBaggageVO.getSumInsured()-->"+travelBaggageVO.getSumInsured());
							travelBaggageSumInsured.setAmount(travelBaggageVO.getSumInsured());
							travelBaggageSumInsured.setCurrencyCode("AED");//TODO:Currency.getUnitName()
							travelBaggageCover.setSumInsured(travelBaggageSumInsured);

							//Checking warnings
							if(hasWarning(createSBSQuoteResponse, "SBSWS_TRL")) {
								LOGGER.info("Warning in Travel Baggage section...setting premium to 0");
								if (!Utils.isEmpty(travelBaggageVO.getPremium())) {
									travelBaggageVO.getPremium().setPremiumAmt(0.00);
								}
							}
							CoverPremium travelBaggagePremium = new CoverPremium();
							Double travelPremium = 0.0;
							if (!Utils.isEmpty(travelBaggageVO.getPremium())) {
								travelBaggageCover.setIsIncludedInPremium("Y");
								LOGGER.info("Travel Baggage::travelBaggageVO.getPremium().getPremiumAmt()-->"+travelBaggageVO.getPremium().getPremiumAmt());
								travelPremium = travelBaggageVO.getPremium().getPremiumAmt();
								//Adding two decimal values to the premium
								//travelPremium = WSBusinessValidatorUtils.getFormattedDoubleWithTwoDecimals(travelBaggageVO.getPremium().getPremiumAmt());
							} 
							
							if(travelPremium != 0) {
								travelBaggageCover.setIsIncludedInPremium("Y");
								totalPremium= totalPremium + travelPremium;
							}
							else if(Double.parseDouble(travelBaggageSumInsured.getAmount()) >=1 && Double.parseDouble(travelBaggageSumInsured.getAmount()) <=10000) {
								travelBaggageCover.setIsIncludedInPremium("Y");
							}
							else {
								travelBaggageCover.setIsIncludedInPremium("N");
							}
							/*
							 * For deductible change
							 */
							if(!Utils.isEmpty(travelBaggageVO.getTravellingEmpDets())) {
								List<TravellingEmployeeVO> employeeVOs = travelBaggageVO.getTravellingEmpDets();
								for (TravellingEmployeeVO travellingEmployeeVO : employeeVOs) {
									if(!Utils.isEmpty(travellingEmployeeVO.getSumInsuredDtl())) {
										deductible = travellingEmployeeVO.getSumInsuredDtl().getDeductible();
										break;
									}
								}
							}
							//reverted premium changes after currency class fix
							travelBaggagePremium.setAmount(new Double(Currency.getUnformattedScaledCurrency(new BigDecimal(travelPremium),"SBS")));
							//Handled while setting the value no need to convert
							//travelBaggagePremium.setAmount(travelPremium);//changed BigDecimal to Double for premium issue
							travelBaggagePremium.setCurrencyCode("AED");//TODO:Currency.getUnitName()
							travelBaggageCover.setDeductible(WSBusinessValidatorUtils.getFormatForDecimalWithSingleDigit(deductible).toString()); // for deductible
							travelBaggageCover.setCoverPremium(travelBaggagePremium);
							covers.add(travelBaggageCover);
						}
						//Group Personal Accident
						if (!Utils.isEmpty(groupDetails) && groupDetails instanceof GroupPersonalAccidentVO) {
							GroupPersonalAccidentVO groupPersonalAccidentVO = (GroupPersonalAccidentVO) groupDetails;
							Cover gpaCover = new Cover();

							gpaCover.setId(ServiceConstant.SECTION_GPA_ID);
							gpaCover.setName(ServiceConstant.SECTION_GPA_DESC);

							//Checking warnings
							boolean hasWarnings = hasWarning(createSBSQuoteResponse, "SBSWS_GPA");
							if(hasWarnings) {
								LOGGER.info("Warning in Group Personal Accident section.. setting premium to 0");
								if (!Utils.isEmpty(groupPersonalAccidentVO.getPremium())) {
									groupPersonalAccidentVO.getPremium().setPremiumAmt(0);
								}
							}
							CoverPremium gpaPremium = new CoverPremium();
							Double gpaPrm = 0.0;
							if (!Utils.isEmpty(groupPersonalAccidentVO.getPremium())) {
								gpaCover.setIsIncludedInPremium("Y");
								LOGGER.info("Group Personal Accident::groupPersonalAccidentVO.getPremium().getPremiumAmt()-->"+groupPersonalAccidentVO.getPremium().getPremiumAmt());
								gpaPrm = groupPersonalAccidentVO.getPremium().getPremiumAmt();
								//Adding two decimal values to the premium
								//gpaPrm = WSBusinessValidatorUtils.getFormattedDoubleWithTwoDecimals(groupPersonalAccidentVO.getPremium().getPremiumAmt());
							} 
							
							if(gpaPrm != 0) {
								gpaCover.setIsIncludedInPremium("Y");
								totalPremium= totalPremium + gpaPrm;
							}
							else {
								gpaCover.setIsIncludedInPremium("N");
							}
							//reverted premium changes after currency class fix
							gpaPremium.setAmount(new Double(Currency.getUnformattedScaledCurrency(new BigDecimal(gpaPrm),"SBS")));
							//Handled while setting the value no need to convert
							//gpaPremium.setAmount(gpaPrm);//changed BigDecimal to Double for premium issue
							gpaPremium.setCurrencyCode("AED");//TODO:Currency.getUnitName()
							gpaCover.setDeductible(WSBusinessValidatorUtils.getFormatForDecimalWithSingleDigit(groupPersonalAccidentVO.getGpaDeductible()).toString());
							
							gpaCover.setCoverPremium(gpaPremium);
							
							SumInsured gpaSumInsured = new SumInsured();
							Double gpaSI = 0.0;
							List<GPANammedEmpVO> gpaNameEmpList = groupPersonalAccidentVO.getGpaNammedEmpVO();
							for(GPANammedEmpVO gpaNammedEmpVO : gpaNameEmpList) {
								if(hasWarnings) {
									if(!Utils.isEmpty(gpaNammedEmpVO.getPremium())) {
										gpaNammedEmpVO.getPremium().setPremiumAmt(0);
									}
								}
								if(!Utils.isEmpty(gpaNammedEmpVO.getSumInsuredDetails())) {
									gpaSI = gpaSI + gpaNammedEmpVO.getSumInsuredDetails().getSumInsured();
								}
							}
							LOGGER.info("Group Personal Accident::gpaSI-->" + gpaSI);
							gpaSumInsured.setAmount(gpaSI);
							gpaSumInsured.setCurrencyCode("AED");//TODO:Currency.getUnitName()
							gpaCover.setSumInsured(gpaSumInsured);

							covers.add(gpaCover);
						}
					}

				}

			}
			
			// dandOLimit JLT specific Plan
			if (null != createSBSQuoteRequest.getLiabilityInformation().getDandOLimit()) {
				if (!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getDandOLimit().getAmount())
						&& createSBSQuoteRequest.getLiabilityInformation().getDandOLimit().getAmount() >= 0) {

					Cover dandoCover = new Cover();
					dandoCover.setId(ServiceConstant.SECTION_DANDO_ID);
					dandoCover.setName(ServiceConstant.SECTION_DANDO_DESC);

					SumInsured dandoSumInsured = new SumInsured();
					dandoSumInsured.setAmount(new Double(
							createSBSQuoteRequest.getLiabilityInformation().getDandOLimit().getAmount()));
					dandoSumInsured.setCurrencyCode("AED");
					dandoCover.setSumInsured(dandoSumInsured);

					CoverPremium dandoCoverPremium = new CoverPremium();
					dandoCover.setIsIncludedInPremium("N");
					dandoCoverPremium.setAmount(0.00);//changed BigDecimal to Double for premium issue
					dandoCoverPremium.setCurrencyCode("AED");// TODO:Currency.getUnitName()
					dandoCover.setCoverPremium(dandoCoverPremium);

					covers.add(dandoCover);
				}
			}
			
			// professionalIndemnityLimit JLT specific Plan
			if (null != createSBSQuoteRequest.getLiabilityInformation().getProfessionalIndemnityLimit()) {
				if (!Utils.isEmpty(
						createSBSQuoteRequest.getLiabilityInformation().getProfessionalIndemnityLimit().getAmount())
						&& createSBSQuoteRequest.getLiabilityInformation().getProfessionalIndemnityLimit()
								.getAmount() >= 0) {
					Cover professionalCover = new Cover();
					professionalCover.setId(ServiceConstant.SECTION_PROFESSIONAL_INDEMNITY_ID);
					professionalCover.setName(ServiceConstant.SECTION_PROFESSIONAL_INDEMNITY_DESC);

					SumInsured professionalSumInsured = new SumInsured();
					professionalSumInsured.setAmount(new Double(
							createSBSQuoteRequest.getLiabilityInformation().getProfessionalIndemnityLimit().getAmount()));
					professionalSumInsured.setCurrencyCode("AED");
					professionalCover.setSumInsured(professionalSumInsured);

					CoverPremium professionalCoverPremium = new CoverPremium();
					professionalCover.setIsIncludedInPremium("N");
					professionalCoverPremium.setAmount(0.00);//changed BigDecimal to Double for premium issue
					professionalCoverPremium.setCurrencyCode("AED");
					professionalCover.setCoverPremium(professionalCoverPremium);

					covers.add(professionalCover);
				}
			}
			
			// productLiability JLT specific Plan
			if (null != createSBSQuoteRequest.getLiabilityInformation().getProductLiability()) {
				if (!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getProductLiability().getAmount())
						&& createSBSQuoteRequest.getLiabilityInformation().getProductLiability().getAmount() >= 0) {

					Cover productCover = new Cover();
					productCover.setId(ServiceConstant.SECTION_PRODUCT_LIABILITY_ID);
					productCover.setName(ServiceConstant.SECTION_PRODUCT_LIABILITY_DESC);

					SumInsured productSumInsured = new SumInsured();
					productSumInsured.setAmount(new Double(
							createSBSQuoteRequest.getLiabilityInformation().getProductLiability().getAmount()));
					productSumInsured.setCurrencyCode("AED");
					productCover.setSumInsured(productSumInsured);

					productCover.setIsIncludedInPremium("N");

					CoverPremium productCoverPremium = new CoverPremium();
					productCoverPremium.setAmount(0.00);//changed BigDecimal to Double for premium issue
					productCoverPremium.setCurrencyCode("AED");
					productCover.setCoverPremium(productCoverPremium);

					covers.add(productCover);
				}
			}
			
			// personalEffectLimit JLT specific Plan
			if (null != createSBSQuoteRequest.getLiabilityInformation().getPersonalEffectLimit()) {
				if (!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getPersonalEffectLimit().getAmount())
						&& createSBSQuoteRequest.getLiabilityInformation().getPersonalEffectLimit().getAmount() >= 0) {

					Cover personalEffectCover = new Cover();
					personalEffectCover.setId(ServiceConstant.SECTION_PERSONAL_EFFECT_ID);
					personalEffectCover.setName(ServiceConstant.SECTION_PERSONAL_EFFECT_DESC);

					SumInsured personalEffectSumInsured = new SumInsured();
					personalEffectSumInsured.setAmount(new Double(
							createSBSQuoteRequest.getLiabilityInformation().getPersonalEffectLimit().getAmount()));
					personalEffectSumInsured.setCurrencyCode("AED");
					personalEffectCover.setSumInsured(personalEffectSumInsured);

					personalEffectCover.setIsIncludedInPremium("N");

					CoverPremium personalEffectCoverPremium = new CoverPremium();
					personalEffectCoverPremium.setAmount(0.00);//changed BigDecimal to Double for premium issue
					personalEffectCoverPremium.setCurrencyCode("AED");
					personalEffectCover.setCoverPremium(personalEffectCoverPremium);

					covers.add(personalEffectCover);
				}
			}
		
			// goods in transit JLT specific Plan
			if (null != createSBSQuoteRequest.getLiabilityInformation().getGoodsInTransitLimit()) {
				if (!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getGoodsInTransitLimit().getAmount())
						&& createSBSQuoteRequest.getLiabilityInformation().getGoodsInTransitLimit().getAmount() >= 0) {

					Cover goodsInTransitCover = new Cover();
					goodsInTransitCover.setId(ServiceConstant.SECTION_GOODS_IN_TRANSIT_ID);
					goodsInTransitCover.setName(ServiceConstant.SECTION_GOODS_IN_TRANSIT_DESC);

					SumInsured goodsInTransitSumInsured = new SumInsured();
					goodsInTransitSumInsured.setAmount(new Double(
							createSBSQuoteRequest.getLiabilityInformation().getGoodsInTransitLimit().getAmount()));
					goodsInTransitSumInsured.setCurrencyCode("AED");
					goodsInTransitCover.setSumInsured(goodsInTransitSumInsured);

					goodsInTransitCover.setIsIncludedInPremium("N");

					CoverPremium personalEffectCoverPremium = new CoverPremium();
					personalEffectCoverPremium.setAmount(0.00);//changed BigDecimal to Double for premium issue
					personalEffectCoverPremium.setCurrencyCode("AED");
					goodsInTransitCover.setCoverPremium(personalEffectCoverPremium);

					covers.add(goodsInTransitCover);
				}
			}
			//As per krishna's mail commented the annualCarringEstimate it is part of money section
			// annualCarryingEstimate JLT specific Plan
		/*	if (null != createSBSQuoteRequest.getLiabilityInformation().getAnnualCarryingEstimate()) {
				if (!Utils.isEmpty(createSBSQuoteRequest.getLiabilityInformation().getAnnualCarryingEstimate())
						&& createSBSQuoteRequest.getLiabilityInformation().getAnnualCarryingEstimate() >= 0) {

					Cover annualCarryingCover = new Cover();
					annualCarryingCover.setId(ServiceConstant.SECTION_ANNUAL_CARRYING_ESTIMATE_ID);
					annualCarryingCover.setName(ServiceConstant.SECTION_ANNUAL_CARRYING_ESTIMATE_DESC);

					SumInsured annualCarryingSumInsured = new SumInsured();
					annualCarryingSumInsured.setAmount(new BigDecimal(
							createSBSQuoteRequest.getLiabilityInformation().getAnnualCarryingEstimate().intValue()));
					annualCarryingSumInsured.setCurrencyCode("AED");
					annualCarryingCover.setSumInsured(annualCarryingSumInsured);

					annualCarryingCover.setIsIncludedInPremium("N");

					CoverPremium personalEffectCoverPremium = new CoverPremium();
					personalEffectCoverPremium.setAmount(0.0);//changed BigDecimal to Double for premium issue
					personalEffectCoverPremium.setCurrencyCode("AED");
					annualCarryingCover.setCoverPremium(personalEffectCoverPremium);

					covers.add(annualCarryingCover);
				}
			}*/
			
			createSBSQuoteResponse.getSelectedPlan().setCovers(covers);

		}
		if (!Utils.isEmpty(policyVO.getPremiumVO())) {
			Double polVatRate = 0.0;
			if(!Utils.isEmpty(policyVO.getPremiumVO().getVatTaxPerc())) {
				polVatRate = policyVO.getPremiumVO().getVatTaxPerc();
			}
			LOGGER.info("polVatRate-->" + polVatRate);
			//fgBasePremium = fgBasePremium.doubleValue() + fgBasePremium * polVatRate;
			//LOGGER.info("fgBasePremium-->" + fgBasePremium);
			//Double totalPremium = policyVO.getPremiumVO().getPremiumAmt() + fgBasePremium.doubleValue();
			
			SaveQuoteHandler saveQuoteHandler = new SaveQuoteHandler();
			policyVO.getPremiumVO().setPremiumAmt(totalPremium);
			//Adding two decimal values to the premium
			//policyVO.getPremiumVO().setPremiumAmt(WSBusinessValidatorUtils.getFormattedDoubleWithTwoDecimals(totalPremium));
			
			createSBSQuoteResponse.getSelectedPlan().getPremium().getPremium()
			.setAmount(new Double(Currency.getUnformattedScaledCurrency(new BigDecimal(totalPremium),"SBS")));
			//Handled while setting the value no need to convert
			//createSBSQuoteResponse.getSelectedPlan().getPremium().getPremium().setAmount(WSBusinessValidatorUtils.getFormattedDoubleWithTwoDecimals(totalPremium));
			createSBSQuoteResponse.getSelectedPlan().getPremium().getPremium().setCurrencyCode("AED");
			
			
			
			policyVO = saveQuoteHandler.calculateVatAndGovtTaxAmount(policyVO);
			
			
/*			Double vatTax = Double.parseDouble(Currency.getUnformattedScaledCurrency( new BigDecimal((totalPremium * .05))));
			
			totalPremium = totalPremium + vatTax;
			policyVO.getPremiumVO().setVatTax(vatTax);*/
			
			totalPrm=totalPremium;
			//Checking the null values
			if(policyVO.getGeneralInfo().getClaimsHistory().getLossExpQuantum()!=null && policyVO.getGeneralInfo().getClaimsHistory().getLossExpQuantum().intValue()>0) {
			BigDecimal lossExpQuantum = policyVO.getGeneralInfo().getClaimsHistory().getLossExpQuantum();
			double lossExpQuantumValue = ((lossExpQuantum.doubleValue() / 3) * 0.25);
			if (lossExpQuantumValue > 
					totalPrm) {
				RuleHandler handler = new RuleHandler();
				handler.callRuleForCumuliativeLossQuantum(policyVO, lossExpQuantumValue,totalPrm);
				if (!Utils.isEmpty(policyVO.getMapReferralVO())) {
					// Saving in T_TRN_TEMP_PAS_REFERRAL
					saveQuoteHandler.insertReferal(policyVO);
					policyVO = saveQuoteHandler.saveRefTskDetails(policyVO);
					policyVO.setStatus(Integer.parseInt(Utils.getSingleValueAppConfig("POLICY_REFERRED")));
				}
			}
			}
			if(totalPremium != 0 && !Utils.isEmpty(policyVO.getPremiumVO()))
				totalPremium = totalPremium + policyVO.getPremiumVO().getVatTax();
			
			LOGGER.info("totalPremium-->" + totalPremium);
			
			//createSBSQuoteResponse.getSelectedPlan().getPremium().getVatOnPremium().setAmount(WSBusinessValidatorUtils.getFormattedDoubleWithTwoDecimals(policyVO.getPremiumVO().getVatTax()));
			//reverted premium changes after currency class fix
			createSBSQuoteResponse.getSelectedPlan().getPremium().getVatOnPremium().setAmount(AppUtils.getFormattedDoubleWithTwoDecimals(policyVO.getPremiumVO().getVatTax()));
			createSBSQuoteResponse.getSelectedPlan().getPremium().getVatOnPremium().setCurrencyCode("AED");
			
			//createSBSQuoteResponse.getSelectedPlan().getPremium().getGrossPremium().setAmount(WSBusinessValidatorUtils.getFormattedDoubleWithTwoDecimals(totalPremium));
			//reverted premium changes after currency class fix
			createSBSQuoteResponse.getSelectedPlan().getPremium().getGrossPremium().setAmount(AppUtils.getFormattedDoubleWithTwoDecimals(totalPremium));
			createSBSQuoteResponse.getSelectedPlan().getPremium().getGrossPremium().setCurrencyCode("AED");
		}
		//claim information default values are added
		if(createSBSQuoteResponse.getLiabilityInformation().getClaimInformation()==null) {
			createSBSQuoteResponse.getLiabilityInformation().setClaimInformation(new ClaimInformation());
			createSBSQuoteResponse.getLiabilityInformation().getClaimInformation().setNumberOfClaims(policyVO.getGeneralInfo().getClaimsHistory().getTelexNo());
			createSBSQuoteResponse.getLiabilityInformation().getClaimInformation().setRemarks(policyVO.getGeneralInfo().getAdditionalInfo().getRemarks());
			createSBSQuoteResponse.getLiabilityInformation().getClaimInformation().setValueOfClaims(policyVO.getGeneralInfo().getClaimsHistory().getLossExpQuantum().intValue());
		}
	}

	public void initializeObjects(CreateSBSQuoteResponse createSBSQuoteResponse) {

		if (Utils.isEmpty(createSBSQuoteResponse.getSelectedPlan())) {
			createSBSQuoteResponse.setSelectedPlan(new SelectedPlan());
		}
		if (Utils.isEmpty(createSBSQuoteResponse.getSelectedPlan().getPremium())) {
			createSBSQuoteResponse.getSelectedPlan().setPremium(new Premium());
		}
		if (Utils.isEmpty(createSBSQuoteResponse.getSelectedPlan().getPremium().getPremium())) {
			createSBSQuoteResponse.getSelectedPlan().getPremium().setPremium(new Premium_());
		}
		if (Utils.isEmpty(createSBSQuoteResponse.getSelectedPlan().getPremium().getGrossPremium())) {
			createSBSQuoteResponse.getSelectedPlan().getPremium().setGrossPremium(new GrossPremium());
		}
		if (Utils.isEmpty(createSBSQuoteResponse.getSelectedPlan().getPremium().getVatOnPremium())) {
			createSBSQuoteResponse.getSelectedPlan().getPremium().setVatOnPremium(new VatOnPremium());;
		}
		if (Utils.isEmpty(createSBSQuoteResponse.getLiabilityInformation())) {
			createSBSQuoteResponse.setLiabilityInformation(new com.rsaame.pas.b2b.ws.vo.request.LiabilityInformation());
		}
		if(Utils.isEmpty(createSBSQuoteResponse.getLiabilityInformation().getClaimInformation())) {
		createSBSQuoteResponse.getLiabilityInformation().setClaimInformation(new ClaimInformation());
		}
	}
	
	public List<SBSWSValidators> addReferralsToResponse(PolicyVO policyVO,
			CreateSBSQuoteResponse createSBSQuoteResponse) {
		
		List<SBSWSValidators> validators = new ArrayList<>();
		
		if (!Utils.isEmpty(policyVO.getMapReferralVO())) {

			if (Utils.isEmpty(createSBSQuoteResponse.getSbswsValidators())) {
				createSBSQuoteResponse.setSbswsValidators(new ArrayList<>());
			}
			
			Map<ReferralLocKey, ReferralVO> map = (Map<ReferralLocKey, ReferralVO>) policyVO.getMapReferralVO();

			if (!Utils.isEmpty(map)) {
				for (Map.Entry<ReferralLocKey, ReferralVO> referralEntry : map.entrySet()) {
					ReferralVO referralVO = null;
					if(!Utils.isEmpty(referralEntry)) {
						referralVO = referralEntry.getValue();
					}
					if(!Utils.isEmpty(referralVO)) {
						List<SBSWSValidators> sbswsValidators = new ArrayList<>(referralVO.getReferralText().size());
						int index =0;
						
						List<String> referralMessages = new ArrayList<>();
						if (!Utils.isEmpty(referralVO.getReferralText())) {
							referralMessages = referralVO.getReferralText();
						}
						
						for (String message : referralMessages) {
							SBSWSValidators sbswsValidators2 = new SBSWSValidators();
							sbswsValidators2.setCode("SBSWS_ERR_999");
							sbswsValidators2.setCategory("Referral");
							sbswsValidators2.setType("BusinessValidator");
							sbswsValidators2.setMessage(message);
							
							sbswsValidators.add(sbswsValidators2);
						}
						
						if (!Utils.isEmpty(referralVO.getReferalDataMap())) {

							for (Map.Entry<String, String> referralfield : referralVO.getReferalDataMap().entrySet()) {
								sbswsValidators.get(index++).setField(referralfield.getKey());
								
							}
						}
						validators.addAll(sbswsValidators);
						createSBSQuoteResponse.setUwApprovalStatus("Required");
					}
					
				}

			}
		}
		if(!Utils.isEmpty(createSBSQuoteResponse.getSbswsValidators())) {
			createSBSQuoteResponse.getSbswsValidators().addAll(validators);
		}
		else {
			createSBSQuoteResponse.setSbswsValidators(validators);
		}
		return validators;
	}
	
	public boolean hasWarning(CreateSBSQuoteResponse createSBSQuoteResponse, List<String> codes) {
		
		List<SBSWSValidators> sbsWSValidators = createSBSQuoteResponse.getSbswsValidators();
		
		if(!Utils.isEmpty(sbsWSValidators)) {
			for(SBSWSValidators validator : sbsWSValidators) {
				for(String code : codes) {
					if(!Utils.isEmpty(code) && code.equals(validator.getCode())){
						return true;
					}
				}
			}
		}
		
		
		return false;
	}

	public boolean hasWarning(CreateSBSQuoteResponse createSBSQuoteResponse, String prefix) {
		
		List<String> codes = new ArrayList<String>();
		ResourceBundle resourceBundle = ResourceBundle.getBundle("config.sbswsmessages");
		Enumeration<String> keys = resourceBundle.getKeys();
		
		if(!Utils.isEmpty(keys)) {
			while(keys.hasMoreElements()) {
				String key = keys.nextElement();
				
				if(key.toLowerCase().contains(prefix.toLowerCase())) {
					codes.add(key);
				}
			}
		}
		
		
		List<SBSWSValidators> sbsWSValidators = createSBSQuoteResponse.getSbswsValidators();
		
		if(!Utils.isEmpty(sbsWSValidators)) {
			for(SBSWSValidators validator : sbsWSValidators) {
				for(String code : codes) {
					if(!Utils.isEmpty(code) && code.equals(validator.getCode())){
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	
}
