package com.rsaame.pas.b2b.ws.mapper;

import java.math.BigDecimal;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.b2b.ws.constant.ServiceConstant;
import com.rsaame.pas.b2b.ws.util.WSAppUtils;
import com.rsaame.pas.b2b.ws.util.WSBusinessValidatorUtils;
import com.rsaame.pas.b2b.ws.util.WSDAOUtils;
import com.rsaame.pas.b2b.ws.validators.SBSWSValidators;
import com.rsaame.pas.b2b.ws.vo.RetrieveSBSQuoteResponse;
import com.rsaame.pas.b2b.ws.vo.request.ClaimInformation;
import com.rsaame.pas.b2b.ws.vo.request.FidelityGuarantee;
import com.rsaame.pas.b2b.ws.vo.request.GroupPersonalAccident;
import com.rsaame.pas.b2b.ws.vo.request.NamedEmployeesDetail;
import com.rsaame.pas.b2b.ws.vo.request.NamedEmployeesDetail_;
import com.rsaame.pas.b2b.ws.vo.request.NamedEmployeesDetail__;
import com.rsaame.pas.b2b.ws.vo.request.Revenue;
import com.rsaame.pas.b2b.ws.vo.request.TravelBaggage;
import com.rsaame.pas.b2b.ws.vo.request.UnnamedEmployeesDetail;
import com.rsaame.pas.b2b.ws.vo.response.AnnualRentReceivable;
import com.rsaame.pas.b2b.ws.vo.response.City;
import com.rsaame.pas.b2b.ws.vo.response.Company;
import com.rsaame.pas.b2b.ws.vo.response.ComputerBreakdownLimit;
import com.rsaame.pas.b2b.ws.vo.response.ContactMethods;
import com.rsaame.pas.b2b.ws.vo.response.Cover;
import com.rsaame.pas.b2b.ws.vo.response.CoverPremium;
import com.rsaame.pas.b2b.ws.vo.response.EmailContact;
import com.rsaame.pas.b2b.ws.vo.response.EmployerLiabilityLimit;
import com.rsaame.pas.b2b.ws.vo.response.GrossPremium;
import com.rsaame.pas.b2b.ws.vo.response.LiabilityInformation;
import com.rsaame.pas.b2b.ws.vo.response.LossOfGrossProfitLimit;
import com.rsaame.pas.b2b.ws.vo.response.MachineryBreakdownLimit;
import com.rsaame.pas.b2b.ws.vo.response.MoneyInTransitLimit;
import com.rsaame.pas.b2b.ws.vo.response.NameOfFreeZoneAuthority;
import com.rsaame.pas.b2b.ws.vo.response.NatureOfBusiness;
import com.rsaame.pas.b2b.ws.vo.response.PhoneContact;
import com.rsaame.pas.b2b.ws.vo.response.PolicyHolder;
import com.rsaame.pas.b2b.ws.vo.response.PolicySchedule;
import com.rsaame.pas.b2b.ws.vo.response.PortableEquipmentLimit;
import com.rsaame.pas.b2b.ws.vo.response.PostMailContact;
import com.rsaame.pas.b2b.ws.vo.response.Premium;
import com.rsaame.pas.b2b.ws.vo.response.Premium_;
import com.rsaame.pas.b2b.ws.vo.response.PropertyContentValue;
import com.rsaame.pas.b2b.ws.vo.response.PropertyValue;
import com.rsaame.pas.b2b.ws.vo.response.PublicLiabilityLimit;
import com.rsaame.pas.b2b.ws.vo.response.RentAndIcowLimit;
import com.rsaame.pas.b2b.ws.vo.response.SelectedPlan;
import com.rsaame.pas.b2b.ws.vo.response.StockGuaranteeLimit;
import com.rsaame.pas.b2b.ws.vo.response.SumInsured;
import com.rsaame.pas.b2b.ws.vo.response.VatOnPremium;
import com.rsaame.pas.b2b.ws.vo.response.WorkmenAdminCompensation;
import com.rsaame.pas.b2b.ws.vo.response.WorkmenNonAdminCompensation;
import com.rsaame.pas.b2c.cmn.utils.AppUtils;
import com.rsaame.pas.cmn.currency.Currency;
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
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.SectionVO;
import com.rsaame.pas.vo.bus.SumInsuredVO;
import com.rsaame.pas.vo.bus.TravelBaggageVO;
import com.rsaame.pas.vo.bus.TravellingEmployeeVO;
import com.rsaame.pas.vo.bus.UWQuestionVO;
import com.rsaame.pas.vo.bus.WCVO;

public class SBSRetrieveQuoteResponseMapper {

	private final static Logger LOGGER = Logger.getLogger(SBSRetrieveQuoteResponseMapper.class);

	public void mapRequestToVO(Object source1, Object target, LocationVO locationVO) {
		LOGGER.info("Inside SBSRetrieveQuoteResponseMapper...");
		if (!Utils.isEmpty(source1) && !Utils.isEmpty(target)) {
			PolicyVO policyVO = (PolicyVO) source1;
			RetrieveSBSQuoteResponse retrieveSBSQuoteResponse = (RetrieveSBSQuoteResponse) target;

			initializeObjects(retrieveSBSQuoteResponse);

			retrieveSBSQuoteResponse.setQuoteId("" + policyVO.getQuoteNo());
			retrieveSBSQuoteResponse.setQuoteStatus("" + policyVO.getStatus());

			mapUwApprovalStatus(policyVO, retrieveSBSQuoteResponse);
			mapPolicyHolder(policyVO, retrieveSBSQuoteResponse);
			mapLiabilityInformation(policyVO, retrieveSBSQuoteResponse, locationVO);
			mapSelectedPlan(retrieveSBSQuoteResponse, policyVO);
			mapPolicySchedule(policyVO, retrieveSBSQuoteResponse);
			
			retrieveSBSQuoteResponse.setquoteExpiryDate(WSAppUtils.getQuoteExpiryAddedDate(retrieveSBSQuoteResponse.getPolicySchedule().getCreationDate()));

		}
	}

	private void mapUwApprovalStatus(PolicyVO policyVO, RetrieveSBSQuoteResponse retrieveSBSQuoteResponse) {
		if(null != policyVO.getStatus()){
			if (policyVO.getStatus() == 20)
				retrieveSBSQuoteResponse.setUwApprovalStatus("Required");
			if (policyVO.getStatus() == 22)
				retrieveSBSQuoteResponse.setUwApprovalStatus("Rejected");
			if (policyVO.getStatus() == 23)
				retrieveSBSQuoteResponse.setUwApprovalStatus("Approved");
		}
	}

	public void mapLiabilityInformation(PolicyVO policyVO, RetrieveSBSQuoteResponse retrieveSBSQuoteResponse,
			LocationVO locationVO) {
		if(Integer.parseInt(locationVO.getFreeZone())==55035) {
			if(locationVO.getDirectorate()!=null) {
			if(locationVO.getDirectorate()==16002) {
				retrieveSBSQuoteResponse.getLiabilityInformation().getNameOfFreeZoneAuthority()
				.setCode(locationVO.getFreeZone());
				retrieveSBSQuoteResponse.getLiabilityInformation()
				.setIsFreezone(true);
			}
			
			else {
				retrieveSBSQuoteResponse.getLiabilityInformation().getNameOfFreeZoneAuthority()
				.setCode(locationVO.getDirectorate().toString());
				retrieveSBSQuoteResponse.getLiabilityInformation()
				.setIsFreezone(false);
			}
			}
		}
		else {
			retrieveSBSQuoteResponse.getLiabilityInformation().getNameOfFreeZoneAuthority()
			.setCode(locationVO.getFreeZone());
			retrieveSBSQuoteResponse.getLiabilityInformation()
			.setIsFreezone(true);
		}
		
		retrieveSBSQuoteResponse.getLiabilityInformation().setTerritorialLimit("None");
		retrieveSBSQuoteResponse.getLiabilityInformation().setJurisdiction("None");
		List<NamedEmployeesDetail_> travellingBaggageDtls =  new ArrayList<NamedEmployeesDetail_>();
		 TravelBaggage travelBaggage=new TravelBaggage();
		if (!Utils.isEmpty(policyVO.getRiskDetails())) {
			List<Cover> covers = new ArrayList<Cover>();
			for (SectionVO sectionVO : policyVO.getRiskDetails()) {
				if (!Utils.isEmpty(sectionVO.getRiskGroupDetails())) {
					Map<? extends RiskGroup, ? extends RiskGroupDetails> riskGroupDetailsMap = sectionVO
							.getRiskGroupDetails();
					for (Map.Entry<? extends RiskGroup, ? extends RiskGroupDetails> entry : riskGroupDetailsMap
							.entrySet()) {
						RiskGroupDetails groupDetails = entry.getValue();
						if (!Utils.isEmpty(groupDetails) && groupDetails instanceof WCVO) {
							WCVO wcVO = (WCVO) groupDetails;
							for (EmpTypeDetailsVO empTypeDetailsVO : wcVO.getEmpTypeDetails()) {
								if (empTypeDetailsVO.getEmpType() == (ServiceConstant.WC_ADMIN_CODE)) {
									Double amount = 0.0;
									amount = empTypeDetailsVO.getWageroll();
									WorkmenAdminCompensation wac = new WorkmenAdminCompensation();
									wac.setAmount(amount.intValue());
									wac.setCurrency("AED");
									retrieveSBSQuoteResponse.getLiabilityInformation().setWorkmenAdminCompensation(wac);

									BigDecimal adminLimit = empTypeDetailsVO.getLimit();
									EmployerLiabilityLimit ell = new EmployerLiabilityLimit();
									ell.setAmount(new Double((adminLimit.intValue())*1000000));
									ell.setCurrency("AED");
									retrieveSBSQuoteResponse.getLiabilityInformation().setEmployerLiabilityLimit(ell);
									retrieveSBSQuoteResponse.getLiabilityInformation().setAdminHeadCount(empTypeDetailsVO.getNoOfEmp());
								}
								if (empTypeDetailsVO.getEmpType() == (ServiceConstant.WC_NON_ADMIN_CODE)) {
									Double amount = 0.0;
									amount = empTypeDetailsVO.getWageroll();
									WorkmenNonAdminCompensation wnac = new WorkmenNonAdminCompensation();
									wnac.setAmount(amount.intValue());
									wnac.setCurrency("AED");
									retrieveSBSQuoteResponse.getLiabilityInformation()
											.setWorkmenNonAdminCompensation(wnac);

									BigDecimal adminLimit = empTypeDetailsVO.getLimit();
									EmployerLiabilityLimit ell = new EmployerLiabilityLimit();
									ell.setAmount(new Double((adminLimit.intValue())*1000000));
									ell.setCurrency("AED");
									retrieveSBSQuoteResponse.getLiabilityInformation().setEmployerLiabilityLimit(ell);
									retrieveSBSQuoteResponse.getLiabilityInformation().setNonAdminHeadCount(empTypeDetailsVO.getNoOfEmp());
								}
							}
						}//wcvoend
						
						if (!Utils.isEmpty(groupDetails) && groupDetails instanceof TravelBaggageVO) {
							TravelBaggageVO travelBaggageVO = (TravelBaggageVO) groupDetails;
							com.rsaame.pas.b2b.ws.vo.request.TravelBaggage travelBaggage1 = new com.rsaame.pas.b2b.ws.vo.request.TravelBaggage();
							List<NamedEmployeesDetail_> travellerList = new ArrayList<NamedEmployeesDetail_>();
							List<TravellingEmployeeVO> travellerList1 = travelBaggageVO.getTravellingEmpDets();
							//CTS-26.08.20 - CR#11424 Update a name flag and old name to denote the change in name between base policy and renewal quote -- Starts
							HashMap<String, List<String>> namedflag = new HashMap<String,List<String>>();
							if(policyVO.getIsRenewal() && policyVO.getEndtId() == Long.valueOf( Utils.getSingleValueAppConfig("JLT_RENEWAL_DEFAULT_ENDT_ID"))){
								namedflag=WSDAOUtils.getnameflagdetails(policyVO.getPolicyNo(), Integer.valueOf(Utils.getSingleValueAppConfig("GPR_RSK_CODE_TRAVEL")), policyVO.getPolicyYear().toString());
							}
							//CTS-26.08.20 - CR#11424 Update a name flag and old name to denote the change in name between base policy and renewal quote -- Ends
							for(TravellingEmployeeVO t : travellerList1)
							{
								NamedEmployeesDetail_ employeeDetails =new NamedEmployeesDetail_();
								employeeDetails.setName(t.getName());
								employeeDetails.setDateOfBirth(t.getDateOfBirth());
								employeeDetails.setSumInsured(t.getSumInsuredDtl().getSumInsured().intValue());
								//CTS-26.08.20 - CR#11424 Update a name flag and old name to denote the change in name between base policy and renewal quote -- Starts
								if(policyVO.getIsRenewal() && policyVO.getEndtId() == Long.valueOf( Utils.getSingleValueAppConfig("JLT_RENEWAL_DEFAULT_ENDT_ID")) && !Utils.isEmpty(namedflag)){
									if(namedflag.containsKey(employeeDetails.getName().toUpperCase().trim())){
										List<String> oldNameAndFlagList = new ArrayList<String>();
										oldNameAndFlagList = namedflag.get(employeeDetails.getName().toUpperCase().trim());
										String oldName = oldNameAndFlagList.get(0);
										String nameUpdateFlag = oldNameAndFlagList.get(1);
										employeeDetails.setAdditionalProperty("nameUpdateFlag",nameUpdateFlag);
										if(nameUpdateFlag.equals(Utils.getSingleValueAppConfig("JLT_RENEWAL_NAME_FLAG_YES"))){
											employeeDetails.setAdditionalProperty("oldName",oldName);
										}

								}
								//CTS-26.08.20 - CR#11424 Update a name flag and old name to denote the change in name between base policy and renewal quote -- Ends
								travellerList.add(employeeDetails);
							}
							}
							System.out.println(travellerList.toString());
							travelBaggage1.setNamedEmployeesDetail(travellerList);
							retrieveSBSQuoteResponse.getLiabilityInformation().setTravelBaggage(travelBaggage1);
						}//TBVO
						if (!Utils.isEmpty(groupDetails) && groupDetails instanceof FidelityVO) {
							FidelityVO fidelityVO = (FidelityVO) groupDetails;
							FidelityGuarantee fidelityGuarantee = new FidelityGuarantee(); 
                            List<NamedEmployeesDetail__> namedList = new ArrayList<NamedEmployeesDetail__>();
							List<FidelityNammedEmployeeDetailsVO> namedList1 = fidelityVO.getFidelityEmployeeDetails();
							List<FidelityUnnammedEmployeeVO> unnamedList1 = fidelityVO.getUnnammedEmployeeDetails();
							//CTS-26.08.20 - CR#11424 Update a name flag and old name to denote the change in name between base policy and renewal quote -- Starts
							HashMap<String, List<String>> fidelityFlag = new HashMap<String,List<String>>();
							if(policyVO.getIsRenewal() && policyVO.getEndtId() == Long.valueOf( Utils.getSingleValueAppConfig("JLT_RENEWAL_DEFAULT_ENDT_ID"))){
								fidelityFlag=WSDAOUtils.getnameflagdetails(policyVO.getPolicyNo(), Integer.valueOf(Utils.getSingleValueAppConfig("FIDELITY_RISK_CODE")), policyVO.getPolicyYear().toString());
							}
							//CTS-26.08.20 - CR#11424 Update a name flag and old name to denote the change in name between base policy and renewal quote -- Ends
							UnnamedEmployeesDetail unnamed=new UnnamedEmployeesDetail();
							for(FidelityNammedEmployeeDetailsVO t : namedList1)
							{
								if(t.getEmpName()!=null)
								{
								NamedEmployeesDetail__ employeeDetails =new NamedEmployeesDetail__();
								employeeDetails.setName(t.getEmpName());
								employeeDetails.setCategory(t.getEmpType().toString());
								employeeDetails.setDesignation(t.getEmpDesignation());
								employeeDetails.setSumInsured(t.getLimitPerPerson().intValue());
								//CTS-26.08.20 - CR#11424 Update a name flag and old name to denote the change in name between base policy and renewal quote -- Starts
								if(policyVO.getIsRenewal() && policyVO.getEndtId() == Long.valueOf( Utils.getSingleValueAppConfig("JLT_RENEWAL_DEFAULT_ENDT_ID")) && !Utils.isEmpty(fidelityFlag)){
								if(fidelityFlag.containsKey(employeeDetails.getName().toUpperCase().trim())){
									List<String> oldNameAndFlagList = new ArrayList<String>();
									oldNameAndFlagList = fidelityFlag.get(employeeDetails.getName().toUpperCase().trim());
									String oldName = oldNameAndFlagList.get(0);
									String nameUpdateFlag = oldNameAndFlagList.get(1);
									employeeDetails.setAdditionalProperty("nameUpdateFlag",nameUpdateFlag);
									if(nameUpdateFlag.equals(Utils.getSingleValueAppConfig("JLT_RENEWAL_NAME_FLAG_YES"))){
										employeeDetails.setAdditionalProperty("oldName",oldName);
									}
								}
								}
								//CTS-26.08.20 - CR#11424 Update a name flag and old name to denote the change in name between base policy and renewal quote -- Ends
								/*if(t.getEmpType()==16)
								{
									employeeDetails.setAdditionalProperty("categoryName", "Cash Handling");
								}
								else
								{
									employeeDetails.setAdditionalProperty("categoryName", "Non Cash Handling");
								}*/
								namedList.add(employeeDetails);
							}
							}
							
							for(FidelityUnnammedEmployeeVO t : unnamedList1)
							{
								if(t.getEmpType()!=null && t.getEmpType()==16)
								{
								
								unnamed.setCashHandelingEmployeesCount(t.getTotalNumberOfEmployee());
								unnamed.setCashHandelingInsured(t.getLimitPerPerson().intValue());
								}
								else if(t.getEmpType()!=null && t.getEmpType()==17)
								{
								unnamed.setNonCashHandelingEmployeesCount(t.getTotalNumberOfEmployee());
								unnamed.setNonCashHandelingInsured(t.getLimitPerPerson().intValue());
								}
							
							}
							

							System.out.println(namedList.toString());
							fidelityGuarantee.setNamedEmployeesDetail(namedList);
							fidelityGuarantee.setUnnamedEmployeesDetail(unnamed);
							retrieveSBSQuoteResponse.getLiabilityInformation().setFidelityGuarantee(fidelityGuarantee);
						}//FGVO
						if (!Utils.isEmpty(groupDetails) && groupDetails instanceof GroupPersonalAccidentVO) {
							GroupPersonalAccidentVO groupPersonalAccidentVO = (GroupPersonalAccidentVO) groupDetails;
							GroupPersonalAccident groupPersonalAccident = new GroupPersonalAccident(); 
							List<NamedEmployeesDetail> namedEmployeesDetail = new ArrayList<NamedEmployeesDetail>();
							List<GPANammedEmpVO> gpaNammedEmpVO = groupPersonalAccidentVO.getGpaNammedEmpVO();
							//CTS-26.08.20 - CR#11424 Update a name flag and old name to denote the change in name between base policy and renewal quote -- Starts
							HashMap<String, List<String>> gpaFlag = new HashMap<String,List<String>>();
							if(policyVO.getIsRenewal() && policyVO.getEndtId() == Long.valueOf( Utils.getSingleValueAppConfig("JLT_RENEWAL_DEFAULT_ENDT_ID"))){
								gpaFlag=WSDAOUtils.getnameflagdetails(policyVO.getPolicyNo(), Integer.valueOf(Utils.getSingleValueAppConfig("GROUP_PERSONAL_ACCIDENT_RISK_CODE")), policyVO.getPolicyYear().toString());
							}
							//CTS-26.08.20 - CR#11424 Update a name flag and old name to denote the change in name between base policy and renewal quote -- Ends
							for(GPANammedEmpVO t : gpaNammedEmpVO)
							{
								if(t.getNameOfEmployee()!=null)
								{
									NamedEmployeesDetail namedEmployeesDetail1=new NamedEmployeesDetail();
									namedEmployeesDetail1.setSalary(t.getNammedEmpAnnualSalary().intValue());
									namedEmployeesDetail1.setCategory(t.getEmployeeType().toString());
									/*if(t.getEmployeeType()==8)
									{
										namedEmployeesDetail1.setCategory("ADMIN");
									}
									else
									{
										namedEmployeesDetail1.setCategory("NON ADMIN");
									}*/
									namedEmployeesDetail1.setDateOfBirth(t.getNammedEmpDob());
									namedEmployeesDetail1.setName(t.getNameOfEmployee());
									if(t.getNammedEmpGender()=='M')
									{
										namedEmployeesDetail1.setGender("MALE");
									}
									else
									{
										namedEmployeesDetail1.setGender("FEMALE");
									}
									namedEmployeesDetail1.setSumInsured(t.getSumInsuredDetails().getSumInsured().intValue());
									//CTS-26.08.20 - CR#11424 Update a name flag and old name to denote the change in name between base policy and renewal quote -- Starts
									if(policyVO.getIsRenewal() && policyVO.getEndtId() == Long.valueOf( Utils.getSingleValueAppConfig("JLT_RENEWAL_DEFAULT_ENDT_ID")) && !Utils.isEmpty(gpaFlag)){

										List<String> oldNameAndFlagList = new ArrayList<String>();
										oldNameAndFlagList = gpaFlag.get(namedEmployeesDetail1.getName().toUpperCase().trim());
										String oldName = oldNameAndFlagList.get(0);
										String nameUpdateFlag = oldNameAndFlagList.get(1);
										namedEmployeesDetail1.setAdditionalProperty("nameUpdateFlag",nameUpdateFlag);
										if(nameUpdateFlag.equals(Utils.getSingleValueAppConfig("JLT_RENEWAL_NAME_FLAG_YES"))){
											namedEmployeesDetail1.setAdditionalProperty("oldName",oldName);
										}
									}
									//CTS-26.08.20 - CR#11424 Update a name flag and old name to denote the change in name between base policy and renewal quote -- Ends
									namedEmployeesDetail.add(namedEmployeesDetail1);
								}
							}
							
							groupPersonalAccident.setNamedEmployeesDetail(namedEmployeesDetail);
							retrieveSBSQuoteResponse.getLiabilityInformation().setGroupPersonalAccident(groupPersonalAccident);
						}//GPAVO
						
						//claim ratio
						
						if(!Utils.isEmpty(policyVO.getGeneralInfo().getClaimsHistory())&&!Utils.isEmpty(policyVO.getGeneralInfo().getClaimsHistory().getTelexNo())&& policyVO.getGeneralInfo().getClaimsHistory().getTelexNo()>=0)
						retrieveSBSQuoteResponse.getLiabilityInformation().getClaimInformation().setNumberOfClaims(policyVO.getGeneralInfo().getClaimsHistory().getTelexNo());
						if(!Utils.isEmpty(policyVO.getGeneralInfo().getAdditionalInfo())&&!Utils.isEmpty(policyVO.getGeneralInfo().getAdditionalInfo().getRemarks()))
						retrieveSBSQuoteResponse.getLiabilityInformation().getClaimInformation().setRemarks(policyVO.getGeneralInfo().getAdditionalInfo().getRemarks());
						if(!Utils.isEmpty(policyVO.getGeneralInfo().getClaimsHistory())&&!Utils.isEmpty(policyVO.getGeneralInfo().getClaimsHistory().getLossExpQuantum())) {
							retrieveSBSQuoteResponse.getLiabilityInformation().getClaimInformation().setValueOfClaims(Integer.valueOf(policyVO.getGeneralInfo().getClaimsHistory().getLossExpQuantum().intValue()));
						}
					}
				}
			}
		}
	}

	public void mapSelectedPlan(RetrieveSBSQuoteResponse retrieveSBSQuoteResponse, PolicyVO policyVO) {
		Double fgBasePremium = 0.0;

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
							buildingCover.setIsIncludedInPremium("Y");
							CoverPremium coverPremium = new CoverPremium();
							Double buildingPremium = 0.0;
							if (!Utils.isEmpty(parVO.getBldPremium())) {
								LOGGER.info("Building::parVO.getBldPremium().getPremiumAmt()-->"
										+ parVO.getBldPremium().getPremiumAmt());
								//Adding two decimal values to the premium
								buildingPremium = parVO.getBldPremium().getPremiumAmt();
								//buildingPremium = WSBusinessValidatorUtils.getFormattedDoubleWithTwoDecimals(parVO.getBldPremium().getPremiumAmt());
							}
							//reverted premium changes after currency class fix
							coverPremium.setAmount(new Double(Currency.getUnformattedScaledCurrency(new BigDecimal(buildingPremium),"SBS")));
							//coverPremium.setAmount(buildingPremium);
							coverPremium.setCurrencyCode("AED"); // TODO:Currency.getUnitName()
							if(!Utils.isEmpty(parVO.getBldDeductibles()))
								buildingCover.setDeductible(WSBusinessValidatorUtils.getFormatForDecimalWithSingleDigit(parVO.getBldDeductibles()).toString());  // Added for Deductible change
							else 
								buildingCover.setDeductible(WSBusinessValidatorUtils.getFormatForDecimalWithSingleDigit(Double.parseDouble(Utils.getSingleValueAppConfig("JLT_PAR_BUILDING_DEDUCTIBLE"))).toString());
							
							buildingCover.setCoverPremium(coverPremium);
							SumInsured sumInsured = new SumInsured();
							Double siBuilding = 0.0;
							if (!Utils.isEmpty(parVO.getBldCover())) {
								LOGGER.info("Building::parVO.getBldCover()-->" + parVO.getBldCover());
								siBuilding = parVO.getBldCover();
							}
							sumInsured.setAmount(siBuilding);
							sumInsured.setCurrencyCode("AED");// TODO:Currency.getUnitName()
							buildingCover.setSumInsured(sumInsured);
							covers.add(buildingCover);
							// Setting Building Cover Details ends here

							// Setting the property value here to optimize the
							// code
							PropertyValue pv = new PropertyValue();
							pv.setAmount(siBuilding.intValue());
							pv.setCurrency("AED");
							retrieveSBSQuoteResponse.getLiabilityInformation().setPropertyValue(pv);

							// Setting Content Cover Details
							PropertyRisks propertyRisks = parVO.getCovers();
							if (!Utils.isEmpty(propertyRisks)) {
								List<PropertyRiskDetails> propertyRiskDetailsList = propertyRisks
										.getPropertyCoversDetails();
								for (PropertyRiskDetails propertyRiskDetails : propertyRiskDetailsList) {
									if (propertyRiskDetails.getRiskType() == 999) {
										Cover contentCover = new Cover();
										contentCover.setId(ServiceConstant.SECTION_CONTENT_ID);
										contentCover.setName(ServiceConstant.SECTION_CONTENT_DESC);
										contentCover.setIsIncludedInPremium("Y");
										CoverPremium contentCoverPremium = new CoverPremium();
										Double contentPremium = 0.0;
										if (!Utils.isEmpty(propertyRiskDetails.getPremium())) {
											LOGGER.info("Content::propertyRiskDetails.getPremium().getPremiumAmt()--_1"
													+ propertyRiskDetails.getPremium().getPremiumAmt());
											//Adding two decimal values to the premium
											contentPremium = propertyRiskDetails.getPremium().getPremiumAmt();
											//contentPremium = WSBusinessValidatorUtils.getFormattedDoubleWithTwoDecimals(propertyRiskDetails.getPremium().getPremiumAmt());
										}
										//Handled while setting the value no need to convert
										//reverted premium changes after currency class fix
										contentCoverPremium.setAmount(new Double(Currency.getUnformattedScaledCurrency(new BigDecimal(contentPremium),"SBS")));
										//contentCoverPremium.setAmount(contentPremium);
										contentCoverPremium.setCurrencyCode("AED");// TODO:Currency.getUnitName()
										
										if(!Utils.isEmpty(propertyRiskDetails.getDeductibles()))
											contentCover.setDeductible(WSBusinessValidatorUtils.getFormatForDecimalWithSingleDigit(propertyRiskDetails.getDeductibles()).toString());  // for deductible change
										else
											contentCover.setDeductible(WSBusinessValidatorUtils.getFormatForDecimalWithSingleDigit(Double.parseDouble(Utils.getSingleValueAppConfig("JLT_PAR_CONTENT_DEDUCTIBLE"))).toString());
										
										contentCover.setCoverPremium(contentCoverPremium);
										SumInsured contentSumInsured = new SumInsured();
										Double siContent = 0.0;
										if (!Utils.isEmpty(propertyRiskDetails.getCover())) {
											LOGGER.info("Content::propertyRiskDetails.getCover()--_1"
													+ propertyRiskDetails.getCover());
											siContent = propertyRiskDetails.getCover();
										}
										contentSumInsured.setAmount(siContent);
										contentSumInsured.setCurrencyCode("AED");// TODO:
																					// Currency.getUnitName()
										contentCover.setSumInsured(contentSumInsured);
										covers.add(contentCover);

										// Setting the property value here to
										// optimize the code
										PropertyContentValue pv1 = new PropertyContentValue();
										pv1.setAmount(siContent.intValue());
										pv1.setCurrency("AED");
										retrieveSBSQuoteResponse.getLiabilityInformation().setPropertyContentValue(pv1);
									}

									// Rent Payable
									if (propertyRiskDetails.getRiskType() == 13) {
										Cover contentCover = new Cover();
										contentCover.setId(ServiceConstant.SECTION_RENT_PAYABLE_ID);
										contentCover.setName(ServiceConstant.SECTION_RENT_PAYABLE_DESC);
										contentCover.setIsIncludedInPremium("Y");
										CoverPremium contentCoverPremium = new CoverPremium();
										Double rentPayablePremium = 0.0;
										if (!Utils.isEmpty(propertyRiskDetails.getPremium())) {
											LOGGER.info("Content::propertyRiskDetails.getPremium().getPremiumAmt()--_2"
													+ propertyRiskDetails.getPremium().getPremiumAmt());
											//Adding two decimal values to the premium
											rentPayablePremium = propertyRiskDetails.getPremium().getPremiumAmt();
											//rentPayablePremium =WSBusinessValidatorUtils.getFormattedDoubleWithTwoDecimals(propertyRiskDetails.getPremium().getPremiumAmt());
										}
										//reverted premium changes after currency class fix
										contentCoverPremium.setAmount(new Double(Currency.getUnformattedScaledCurrency(new BigDecimal(rentPayablePremium),"SBS")));
										//Handled while setting the value no need to convert
										//contentCoverPremium.setAmount(rentPayablePremium);
										contentCoverPremium.setCurrencyCode("AED");// TODO:Currency.getUnitName()
										
										if(!Utils.isEmpty(propertyRiskDetails.getDeductibles()))
											contentCover.setDeductible(WSBusinessValidatorUtils.getFormattedDoubleWithTwoDecimals(propertyRiskDetails.getDeductibles()).toString());  // for deductible change
										else
											contentCover.setDeductible(WSBusinessValidatorUtils.getFormatForDecimalWithSingleDigit(Double.parseDouble(Utils.getSingleValueAppConfig("JLT_PAR_RENT_DEDUCTIBLE"))).toString());
										
										contentCover.setCoverPremium(contentCoverPremium);
										SumInsured contentSumInsured = new SumInsured();
										Double contentSI = 0.0;
										if (!Utils.isEmpty(propertyRiskDetails.getCover())) {
											LOGGER.info("Content::propertyRiskDetails.getCover()--_2"
													+ propertyRiskDetails.getCover());
											contentSI = propertyRiskDetails.getCover();
										}
										contentSumInsured.setAmount(contentSI);
										contentSumInsured.setCurrencyCode("AED");// TODO:
																					// Currency.getUnitName()
										contentCover.setSumInsured(contentSumInsured);
										covers.add(contentCover);
									}
									
									
									// Stock
									if (propertyRiskDetails.getRiskType() == 9) {
										Cover contentCover = new Cover();
										contentCover.setId(ServiceConstant.SECTION_STOCK_ID);
										contentCover.setName(ServiceConstant.SECTION_STOCK_DESC);
										contentCover.setIsIncludedInPremium("Y");
										CoverPremium contentCoverPremium = new CoverPremium();
										Double stockPremium = 0.0;
										if (!Utils.isEmpty(propertyRiskDetails.getPremium())) {
											LOGGER.info("Content::propertyRiskDetails.getPremium().getPremiumAmt()--_3"
													+ propertyRiskDetails.getPremium().getPremiumAmt());
											//Adding two decimal values to the premium
											stockPremium = propertyRiskDetails.getPremium().getPremiumAmt();
											//rentPayablePremium =WSBusinessValidatorUtils.getFormattedDoubleWithTwoDecimals(propertyRiskDetails.getPremium().getPremiumAmt());
										}
										//reverted premium changes after currency class fix
										contentCoverPremium.setAmount(new Double(Currency.getUnformattedScaledCurrency(new BigDecimal(stockPremium),"SBS")));
										//Handled while setting the value no need to convert
										//contentCoverPremium.setAmount(rentPayablePremium);
										contentCoverPremium.setCurrencyCode("AED");// TODO:Currency.getUnitName()
										
										if(!Utils.isEmpty(propertyRiskDetails.getDeductibles()))
											contentCover.setDeductible(WSBusinessValidatorUtils.getFormattedDoubleWithTwoDecimals(propertyRiskDetails.getDeductibles()).toString());  // for deductible change
										else
											contentCover.setDeductible(WSBusinessValidatorUtils.getFormatForDecimalWithSingleDigit(Double.parseDouble(Utils.getSingleValueAppConfig("JLT_PAR_RENT_DEDUCTIBLE"))).toString());
										
										contentCover.setCoverPremium(contentCoverPremium);
										SumInsured contentSumInsured = new SumInsured();
										Double contentSI = 0.0;
										if (!Utils.isEmpty(propertyRiskDetails.getCover())) {
											LOGGER.info("Content::propertyRiskDetails.getCover()--_3"
													+ propertyRiskDetails.getCover());
											contentSI = propertyRiskDetails.getCover();
										}
										contentSumInsured.setAmount(contentSI);
										contentSumInsured.setCurrencyCode("AED");// TODO:
																					// Currency.getUnitName()
										contentCover.setSumInsured(contentSumInsured);
										covers.add(contentCover);
									}
								}
							}
							// Setting Content Cover Details ends here
						}
						// Workmen Compensation
						if (!Utils.isEmpty(groupDetails) && groupDetails instanceof WCVO) {
							WCVO wcVO = (WCVO) groupDetails;
							Cover wcCover = new Cover();

							wcCover.setId(ServiceConstant.SECTION_WC_ID);
							wcCover.setName(ServiceConstant.SECTION_WC_DESC);
							wcCover.setIsIncludedInPremium("Y");

							CoverPremium wcCoverPremium = new CoverPremium();
							Double wcPremium = 0.00;
							if (!Utils.isEmpty(wcVO.getPremium())) {
								LOGGER.info(
										"WC::wcVO.getPremium().getPremiumAmt()-->" + wcVO.getPremium().getPremiumAmt());
								wcPremium = wcVO.getPremium().getPremiumAmt();
								//Adding two decimal values to the premium
								//wcPremium = WSBusinessValidatorUtils.getFormattedDoubleWithTwoDecimals(wcVO.getPremium().getPremiumAmt());
							}
							//reverted premium changes after currency class fix
							wcCoverPremium.setAmount(new Double(Currency.getUnformattedScaledCurrency(new BigDecimal(wcPremium),"SBS")));
							//Handled while setting the value no need to convert
							//wcCoverPremium.setAmount(wcPremium);
							wcCoverPremium.setCurrencyCode("AED");
							wcCover.setDeductible(WSBusinessValidatorUtils.getFormatForDecimalWithSingleDigit(Double.parseDouble(Utils.getSingleValueAppConfig("WC_Deductibles"))).toString());
							
							wcCover.setCoverPremium(wcCoverPremium);
							Double amount = 0.0;
							for (EmpTypeDetailsVO empTypeDetailsVO : wcVO.getEmpTypeDetails()) {
								if (empTypeDetailsVO.getEmpType() != (ServiceConstant.WC_ADMIN_CODE) && empTypeDetailsVO.getEmpType() != (ServiceConstant.WC_NON_ADMIN_CODE))
									amount = amount + empTypeDetailsVO.getWageroll();
							}
							LOGGER.info("WC::Sum insured-->" + amount);
							SumInsured contentSumInsured = new SumInsured();
							contentSumInsured.setAmount(amount);
							contentSumInsured.setCurrencyCode("AED");// TODO:Currency.getUnitName()
							wcCover.setSumInsured(contentSumInsured);
							covers.add(wcCover);
						}
						
						// Public Liability
						if (!Utils.isEmpty(groupDetails) && groupDetails instanceof PublicLiabilityVO) {
							PublicLiabilityVO publicLiabilityVO = (PublicLiabilityVO) groupDetails;
							Cover plCover = new Cover();

							plCover.setId(ServiceConstant.SECTION_PL_ID);
							plCover.setName(ServiceConstant.SECTION_PL_DESC);
							plCover.setIsIncludedInPremium("Y");
							CoverPremium plCoverPremium = new CoverPremium();
							Double plPremium = 0.0;
							
							if (!Utils.isEmpty(publicLiabilityVO.getPremium())) {
								LOGGER.info("PL::publicLiabilityVO.getPremium().getPremiumAmt()-->"
										+ publicLiabilityVO.getPremium().getPremiumAmt());
								//Adding two decimal values to the premium
								plPremium = publicLiabilityVO.getPremium().getPremiumAmt();
								//plPremium =WSBusinessValidatorUtils.getFormattedDoubleWithTwoDecimals(publicLiabilityVO.getPremium().getPremiumAmt());
							}
							//plCoverPremium.setAmount(new BigDecimal(Currency.getUnformattedScaledCurrency(new BigDecimal(plPremium),"SBS")));
							//Handled while setting the value no need to convert
							/*  plPremium = PLPremium + WorkAwayPremium
							 *  plPremium = PLPremium + (0.25 * PLPremium)
							 *  plPremium = 1.25 * PLPremium
							 *  PLPremium = (100/125) * plPremium
							 *  PLPremium = 0.8 * plPremium
							 *  and WorkAwayPremium = 0.2 * plPremium
							 */
							Double plBaseWAPremium=0.0;
							int studentCountAmt=0;
							Double workAwayAmt=0.0;
							int studentCount =0;
							boolean waflag =false;
							
							if (publicLiabilityVO.getUwQuestions().getQuestions().size()!=0) {
								for(UWQuestionVO uwQuestionVO : publicLiabilityVO.getUwQuestions().getQuestions()) {
									if(uwQuestionVO.getQId()==7)
										studentCount = Integer.parseInt(uwQuestionVO.getResponse());
									if(uwQuestionVO.getQId()==6)
										waflag = true;
								}
							}
							
							if(publicLiabilityVO.getUwQuestions().getQuestions().size() == 0) {
								//plCoverPremium.setAmount(WSBusinessValidatorUtils.getFormattedDoubleWithTwoDecimals(plPremium * 0.8));// multiplying with 0.8 to remove WorkAwayRiskLimit Premium from PL M1043209
								//reverted premium changes after currency class fix
								plCoverPremium.setAmount(new Double(Currency.getUnformattedScaledCurrency(new BigDecimal(plPremium * 0.8))));
							}else if((publicLiabilityVO.getUwQuestions().getQuestions().size()!=0)
									&& ( !Utils.isEmpty(publicLiabilityVO.getPremium()) &&  publicLiabilityVO.getPremium().getPremiumAmt() >0)){
								LOGGER.info("Calculating base PL  premium ,student liability and work away extension when both work away extension and  student liability  is present  ");
								
								if(studentCount == 1) {
									studentCountAmt = studentCount * (Integer.parseInt(Utils.getSingleValueAppConfig( "Student_Liability_Limit_1")));
								}else if (studentCount == 2) {
									studentCountAmt = studentCount * (Integer.parseInt(Utils.getSingleValueAppConfig( "Student_Liability_Limit_2")));
								}else if (studentCount == 3) {
									studentCountAmt = studentCount * (Integer.parseInt(Utils.getSingleValueAppConfig( "Student_Liability_Limit_3")));
								}else if (studentCount == 4) {
									studentCountAmt = studentCount * (Integer.parseInt(Utils.getSingleValueAppConfig( "Student_Liability_Limit_4")));
								}else if (studentCount == 5) {
									studentCountAmt = studentCount * (Integer.parseInt(Utils.getSingleValueAppConfig( "Student_Liability_Limit_5")));
								}else if (studentCount == 6) {
									studentCountAmt = studentCount * (Integer.parseInt(Utils.getSingleValueAppConfig( "Student_Liability_Limit_6")));
								}else if (studentCount == 7) {
									studentCountAmt = studentCount * (Integer.parseInt(Utils.getSingleValueAppConfig( "Student_Liability_Limit_7")));
								}else if (studentCount == 8) {
									studentCountAmt = studentCount * (Integer.parseInt(Utils.getSingleValueAppConfig( "Student_Liability_Limit_8")));
								}else if (studentCount == 9) {
									studentCountAmt = studentCount * (Integer.parseInt(Utils.getSingleValueAppConfig( "Student_Liability_Limit_9")));
								}else if (studentCount == 10) {
									studentCountAmt = studentCount * (Integer.parseInt(Utils.getSingleValueAppConfig( "Student_Liability_Limit_10")));
								}
								
								//plBasePremium inclusive of PL base premium + work away extension
								plBaseWAPremium=plPremium-studentCountAmt;
								
								//Extract work away extension 
								if(waflag)
									workAwayAmt= plBaseWAPremium * 0.2 ;
								else
									workAwayAmt= 0.0 ;
								// PL cover includes PL Base premium + student Liability amount
								//plCoverPremium.setAmount(WSBusinessValidatorUtils.getFormattedDoubleWithTwoDecimals(plBaseWAPremium -workAwayAmt + studentCountAmt));
								//reverted premium changes after currency class fix
								plCoverPremium.setAmount(new Double(Currency.getUnformattedScaledCurrency(new BigDecimal(plBaseWAPremium -workAwayAmt + studentCountAmt))));
							}
							else {
								//reverted premium changes after currency class fix
								plCoverPremium.setAmount(new Double(Currency.getUnformattedScaledCurrency(new BigDecimal(plPremium))));
							}
							plCoverPremium.setCurrencyCode("AED");// TODO:Currency.getUnitName()
							
							/*
							 * For Deductible
							 */
							String deductible = Utils.getSingleValueAppConfig( "JLT_PL_DEDUCTIBLE" );
							if(!Utils.isEmpty(publicLiabilityVO.getSumInsuredDets())) {
								deductible = publicLiabilityVO.getSumInsuredDets().getDeductible().toString();
							}
							plCover.setDeductible(WSBusinessValidatorUtils.getFormattedDoubleWithTwoDecimals(Double.parseDouble(deductible)).toString());  
							
							plCover.setCoverPremium(plCoverPremium);
							SumInsured plSumInsured = new SumInsured();
							Double plSI = 0.00;

							/*
							 * if(!Utils.isEmpty(publicLiabilityVO.
							 * getSumInsuredDets())) { LOGGER.info(
							 * "PL::publicLiabilityVO.getSumInsuredDets().getSumInsured()-->"
							 * +publicLiabilityVO.getSumInsuredDets().
							 * getSumInsured()); plSI =
							 * publicLiabilityVO.getSumInsuredDets().
							 * getSumInsured(); }
							 */

							if (!Utils.isEmpty(publicLiabilityVO.getIndemnityAmtLimit())) {
								Integer siCode = publicLiabilityVO.getIndemnityAmtLimit();
								LOGGER.info("PL::publicLiabilityVO.getIndemnityAmtLimit():siCode-->"
										+ publicLiabilityVO.getIndemnityAmtLimit());
								plSI = new Double(SvcUtils.getLookUpDescription("JLT_PL_LIM", policyVO.getScheme().getTariffCode().toString(), "ALL", siCode));
								LOGGER.info("PL::plSI from lookup-->" + plSI);
							}
							plSumInsured.setAmount(plSI);
							plSumInsured.setCurrencyCode("AED");// TODO:Currency.getUnitName()
							plCover.setSumInsured(plSumInsured);
							covers.add(plCover);
							
							//Code added for WorkAwayRiskLimit
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
							
							if ((publicLiabilityVO.getUwQuestions().getQuestions().size()!=0) && waflag) {
									workAwayRisksLimit.setIsIncludedInPremium("Y");
									//workAwayRisksLimitCoverPremium.setAmount(WSBusinessValidatorUtils.getFormattedDoubleWithTwoDecimals(workAwayAmt));  // multiplying with 0.2 to get WorkAway Premium from PL
									//reverted premium changes after currency class fix
									workAwayRisksLimitCoverPremium.setAmount(new Double(Currency.getUnformattedScaledCurrency(new BigDecimal(workAwayAmt))));
									
									for(UWQuestionVO uwQuestionVO : publicLiabilityVO.getUwQuestions().getQuestions()) {
										if(uwQuestionVO.getQId()==6) {
											workAwayRisksLimitSumInsured.setAmount(new Double(uwQuestionVO.getResponse()));
										}
									}
									workAwayRisksLimit.setSumInsured(workAwayRisksLimitSumInsured);
									workAwayRisksLimit.setCoverPremium(workAwayRisksLimitCoverPremium);
									
							} 
							else {
										workAwayRisksLimit.setIsIncludedInPremium("N");
										workAwayRisksLimitCoverPremium.setAmount(0.00);
										workAwayRisksLimitSumInsured.setAmount(new Double(0.00));
										workAwayRisksLimit.setSumInsured(workAwayRisksLimitSumInsured);
										workAwayRisksLimit.setCoverPremium(workAwayRisksLimitCoverPremium);
							}
							covers.add(workAwayRisksLimit);
							retrieveSBSQuoteResponse.getLiabilityInformation().setWorkAwayRisksLimit(new Double(workAwayRisksLimit.getSumInsured().getAmount()).intValue());
							
							//Code ended for WorkAwayRiskLimit

							// Setting the property value here to optimize the
							// code
							PublicLiabilityLimit pll = new PublicLiabilityLimit();
							if (!Utils.isEmpty(publicLiabilityVO.getIndemnityAmtLimit())) {
								pll.setAmount(new Double(((publicLiabilityVO.getIndemnityAmtLimit()).intValue())*1000000));
								pll.setCurrency("AED");
							}
							retrieveSBSQuoteResponse.getLiabilityInformation().setPublicLiabilityLimit(pll);
						}
						// Business Interruption
						if (!Utils.isEmpty(groupDetails) && groupDetails instanceof BIVO) {
							BIVO biVO = (BIVO) groupDetails;
							if(Utils.isEmpty(biVO.getWorkingLimit())
									||biVO.getWorkingLimit()== 0 ) {
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
								
								Cover biCover = new Cover();
								biCover.setId(ServiceConstant.SECTION_BI_ID);
								biCover.setName(ServiceConstant.SECTION_BI_DESC);
								CoverPremium biCoverPremium = new CoverPremium();
								Double amount = 0.0;
								if (!Utils.isEmpty(biVO.getWorkingLimit())) {
									amount = amount + biVO.getWorkingLimit();
								}
								if (!Utils.isEmpty(biVO.getEstimatedGrossIncome())) {
									amount = amount + biVO.getEstimatedGrossIncome();
								}
								if (!Utils.isEmpty(biVO.getRentRecievable())) {
									amount = amount + biVO.getRentRecievable();
								}
								SumInsured biSumInsured = new SumInsured();
								LOGGER.info("1. BI::biVO.getSumInsured()-->" + amount);
								biSumInsured.setAmount(amount);
								biSumInsured.setCurrencyCode("AED");// TODO:Currency.getUnitName()
								biCover.setSumInsured(biSumInsured);

								// Checking for warning message
								List<String> codes = new ArrayList<String>();
								codes.add(com.Constant.CONST_SBSWS_BI_001);
								if (hasWarning(retrieveSBSQuoteResponse, codes)) {
									LOGGER.info("1. Warning in BI section..setting premium to 0");
									if (!Utils.isEmpty(biVO.getPremium())) {
										biVO.getPremium().setPremiumAmt(0);
									}
								}

								Double biPremium = 0.0;
								if (!Utils.isEmpty(biVO.getPremium())) {
									LOGGER.info(
											"1. BI::biVO.getPremium().getPremiumAmt()-->" + biVO.getPremium().getPremiumAmt());
									biPremium = biVO.getPremium().getPremiumAmt();
									//Adding two decimal values to the premium
									//biPremium = WSBusinessValidatorUtils.getFormattedDoubleWithTwoDecimals(biVO.getPremium().getPremiumAmt());
								}

								if (biPremium != 0) {
									biCover.setIsIncludedInPremium("Y");
								} else {
									biCover.setIsIncludedInPremium("N");
								}
								//reverted premium changes after currency class fix
								biCoverPremium.setAmount(new Double(Currency.getUnformattedScaledCurrency(new BigDecimal(biPremium),"SBS")));
								//Handled while setting the value no need to convert
								//biCoverPremium.setAmount(biPremium);
								biCoverPremium.setCurrencyCode("AED");// TODO:Currency.getUnitName()
								/*
								 * For Deductible change
								 */
								biCover.setDeductible(SvcUtils.getLookUpDescription(com.Constant.CONST_DEDUCTIBLES, com.Constant.CONST_BI_DEDUCTIBLES, policyVO.getScheme().getTariffCode().toString(), (int)biVO.getDeductible()));
							
								biCover.setCoverPremium(biCoverPremium);
								covers.add(biCover);

								// liabilityInformation:LossOfGrossProfitLimit
								LossOfGrossProfitLimit logpl = new LossOfGrossProfitLimit();
								if (!Utils.isEmpty(biVO.getEstimatedGrossIncome())) {
									logpl.setAmount((biVO.getEstimatedGrossIncome()).intValue());
								}
								logpl.setCurrency("AED");
								retrieveSBSQuoteResponse.getLiabilityInformation().setLossOfGrossProfitLimit(logpl);

								// liabilityInformation:rentAndIcowLimit
								RentAndIcowLimit rail = new RentAndIcowLimit();
								if (!Utils.isEmpty(biVO.getWorkingLimit())) {
									rail.setAmount((biVO.getWorkingLimit()).intValue());
								}
								rail.setCurrency("AED");
								retrieveSBSQuoteResponse.getLiabilityInformation().setRentAndIcowLimit(rail);
								
								// liabilityInformation:rentAndIcowLimit
								AnnualRentReceivable rr = new AnnualRentReceivable();
								if (!Utils.isEmpty(biVO.getRentRecievable())) {
									rr.setAmount((biVO.getRentRecievable()).intValue());
								}
								rr.setCurrency("AED");
								retrieveSBSQuoteResponse.getLiabilityInformation().setAnnualRentReceivable(rr);
							}
							else if((Utils.isEmpty(biVO.getEstimatedGrossIncome()) || 
									biVO.getEstimatedGrossIncome() == 0) && (Utils.isEmpty(biVO.getRentRecievable()) || 
											biVO.getRentRecievable()==0)) {
								Cover rentAndICOWCover = new Cover();
								rentAndICOWCover.setId(ServiceConstant.SECTION_ICOW_ID);
								rentAndICOWCover.setName(ServiceConstant.SECTION_ICOW_DESC);
								CoverPremium iCOWCoverPremium = new CoverPremium();
								Double amount = 0.0;
								if (!Utils.isEmpty(biVO.getWorkingLimit())) {
									amount = amount + biVO.getWorkingLimit();
								}
								if (!Utils.isEmpty(biVO.getEstimatedGrossIncome())) {
									amount = amount + biVO.getEstimatedGrossIncome();
								}
								if (!Utils.isEmpty(biVO.getRentRecievable())) {
									amount = amount + biVO.getRentRecievable();
								}
								SumInsured iCOWSumInsured = new SumInsured();
								LOGGER.info("2. BI::biVO.getSumInsured()-->" + amount);
								iCOWSumInsured.setAmount(amount);
								iCOWSumInsured.setCurrencyCode("AED");// TODO:Currency.getUnitName()
								rentAndICOWCover.setSumInsured(iCOWSumInsured);

								// Checking for warning message
								List<String> codes = new ArrayList<String>();
								codes.add(com.Constant.CONST_SBSWS_BI_001);
								if (hasWarning(retrieveSBSQuoteResponse, codes)) {
									LOGGER.info("2. Warning in BI section..setting premium to 0");
									if (!Utils.isEmpty(biVO.getPremium())) {
										biVO.getPremium().setPremiumAmt(0);
									}
								}

								Double iCOWPremium = 0.0;
								if (!Utils.isEmpty(biVO.getPremium())) {
									LOGGER.info(
											"2. BI::biVO.getPremium().getPremiumAmt()-->" + biVO.getPremium().getPremiumAmt());
									iCOWPremium = biVO.getPremium().getPremiumAmt();
									//Adding two decimal values to the premium
									//biPremium = WSBusinessValidatorUtils.getFormattedDoubleWithTwoDecimals(biVO.getPremium().getPremiumAmt());
								}

								if (iCOWPremium != 0) {
									rentAndICOWCover.setIsIncludedInPremium("Y");
								} else {
									rentAndICOWCover.setIsIncludedInPremium("N");
								}
								//reverted premium changes after currency class fix
								iCOWCoverPremium.setAmount(new Double(Currency.getUnformattedScaledCurrency(new BigDecimal(iCOWPremium),"SBS")));
								//Handled while setting the value no need to convert
								//biCoverPremium.setAmount(biPremium);
								iCOWCoverPremium.setCurrencyCode("AED");// TODO:Currency.getUnitName()
								/*
								 * For Deductible change
								 */
								rentAndICOWCover.setDeductible(SvcUtils.getLookUpDescription(com.Constant.CONST_DEDUCTIBLES, com.Constant.CONST_BI_DEDUCTIBLES, policyVO.getScheme().getTariffCode().toString(), (int)biVO.getDeductible()));
							
								rentAndICOWCover.setCoverPremium(iCOWCoverPremium);
								covers.add(rentAndICOWCover);

								// liabilityInformation:LossOfGrossProfitLimit
								LossOfGrossProfitLimit logpl = new LossOfGrossProfitLimit();
								if (!Utils.isEmpty(biVO.getEstimatedGrossIncome())) {
									logpl.setAmount((biVO.getEstimatedGrossIncome()).intValue());
								}
								logpl.setCurrency("AED");
								retrieveSBSQuoteResponse.getLiabilityInformation().setLossOfGrossProfitLimit(logpl);

								// liabilityInformation:rentAndIcowLimit
								RentAndIcowLimit rail = new RentAndIcowLimit();
								if (!Utils.isEmpty(biVO.getWorkingLimit())) {
									rail.setAmount((biVO.getWorkingLimit()).intValue());
								}
								rail.setCurrency("AED");
								retrieveSBSQuoteResponse.getLiabilityInformation().setRentAndIcowLimit(rail);
								
								// liabilityInformation:rentAndIcowLimit
								AnnualRentReceivable rr = new AnnualRentReceivable();
								if (!Utils.isEmpty(biVO.getRentRecievable())) {
									rr.setAmount((biVO.getRentRecievable()).intValue());
								}
								rr.setCurrency("AED");
								retrieveSBSQuoteResponse.getLiabilityInformation().setAnnualRentReceivable(rr);
							}
							else {
								Cover biCover = new Cover();
								biCover.setId(ServiceConstant.SECTION_BI_ID);
								biCover.setName(ServiceConstant.SECTION_BI_DESC);
								CoverPremium biCoverPremium = new CoverPremium();
								Double amount = 0.0;
								if (!Utils.isEmpty(biVO.getEstimatedGrossIncome())) {
									amount = amount + biVO.getEstimatedGrossIncome();
								}
								if (!Utils.isEmpty(biVO.getRentRecievable())) {
									amount = amount + biVO.getRentRecievable();
								}
								SumInsured biSumInsured = new SumInsured();
								LOGGER.info("3. BI::biVO.getSumInsured()-->" + amount);
								biSumInsured.setAmount(amount);
								biSumInsured.setCurrencyCode("AED");// TODO:Currency.getUnitName()
								biCover.setSumInsured(biSumInsured);

								// Checking for warning message
								List<String> codes = new ArrayList<String>();
								codes.add(com.Constant.CONST_SBSWS_BI_001);
								if (hasWarning(retrieveSBSQuoteResponse, codes)) {
									LOGGER.info("3. Warning in BI section..setting premium to 0");
									if (!Utils.isEmpty(biVO.getPremium())) {
										biVO.getPremium().setPremiumAmt(0);
									}
								}

								Double biPremium = 0.0;
								if (!Utils.isEmpty(biVO.getPremium())) {
									LOGGER.info(
											"3. BI::biVO.getPremium().getPremiumAmt()-->" + biVO.getPremium().getPremiumAmt());
									biPremium = biVO.getPremium().getPremiumAmt();
									//Adding two decimal values to the premium
									//biPremium = WSBusinessValidatorUtils.getFormattedDoubleWithTwoDecimals(biVO.getPremium().getPremiumAmt());
								}

								if (biPremium != 0) {
									biCover.setIsIncludedInPremium("Y");
								} else {
									biCover.setIsIncludedInPremium("N");
								}
								//reverted premium changes after currency class fix
								biCoverPremium.setAmount(new Double(Currency.getUnformattedScaledCurrency(new BigDecimal(biPremium),"SBS")));
								//Handled while setting the value no need to convert
								//biCoverPremium.setAmount(biPremium);
								biCoverPremium.setCurrencyCode("AED");// TODO:Currency.getUnitName()
								/*
								 * For Deductible change
								 */
								biCover.setDeductible(SvcUtils.getLookUpDescription(com.Constant.CONST_DEDUCTIBLES, com.Constant.CONST_BI_DEDUCTIBLES, policyVO.getScheme().getTariffCode().toString(), (int)biVO.getDeductible()));
							
								biCover.setCoverPremium(biCoverPremium);
								covers.add(biCover);
								
								Cover rentAndICOWCover = new Cover();
								rentAndICOWCover.setId(ServiceConstant.SECTION_ICOW_ID);
								rentAndICOWCover.setName(ServiceConstant.SECTION_ICOW_DESC);
								CoverPremium iCOWCoverPremium = new CoverPremium();
								Double amount1 = 0.0;
								if (!Utils.isEmpty(biVO.getWorkingLimit())) {
									amount1 = amount1 + biVO.getWorkingLimit();
								}
								SumInsured iCOWSumInsured = new SumInsured();
								LOGGER.info("4. BI::biVO.getSumInsured()-->" + amount);
								iCOWSumInsured.setAmount(amount1);
								iCOWSumInsured.setCurrencyCode("AED");// TODO:Currency.getUnitName()
								rentAndICOWCover.setSumInsured(iCOWSumInsured);

								// Checking for warning message
								if (hasWarning(retrieveSBSQuoteResponse, codes)) {
									LOGGER.info("4. Warning in BI section..setting premium to 0");
									if (!Utils.isEmpty(biVO.getPremium())) {
										biVO.getPremium().setPremiumAmt(0);
									}
								}

								Double iCOWPremium = 0.0;
								rentAndICOWCover.setIsIncludedInPremium("Y");
								
								//reverted premium changes after currency class fix
								iCOWCoverPremium.setAmount(new Double(Currency.getUnformattedScaledCurrency(new BigDecimal(iCOWPremium),"SBS")));
								//Handled while setting the value no need to convert
								//biCoverPremium.setAmount(biPremium);
								iCOWCoverPremium.setCurrencyCode("AED");// TODO:Currency.getUnitName()
								/*
								 * For Deductible change
								 */
								rentAndICOWCover.setDeductible(SvcUtils.getLookUpDescription(com.Constant.CONST_DEDUCTIBLES, com.Constant.CONST_BI_DEDUCTIBLES, policyVO.getScheme().getTariffCode().toString(), (int)biVO.getDeductible()));
							
								rentAndICOWCover.setCoverPremium(iCOWCoverPremium);
								covers.add(rentAndICOWCover);

								// liabilityInformation:LossOfGrossProfitLimit
								LossOfGrossProfitLimit logpl = new LossOfGrossProfitLimit();
								if (!Utils.isEmpty(biVO.getEstimatedGrossIncome())) {
									logpl.setAmount((biVO.getEstimatedGrossIncome()).intValue());
								}
								logpl.setCurrency("AED");
								retrieveSBSQuoteResponse.getLiabilityInformation().setLossOfGrossProfitLimit(logpl);

								// liabilityInformation:rentAndIcowLimit
								RentAndIcowLimit rail = new RentAndIcowLimit();
								if (!Utils.isEmpty(biVO.getWorkingLimit())) {
									rail.setAmount((biVO.getWorkingLimit()).intValue());
								}
								rail.setCurrency("AED");
								retrieveSBSQuoteResponse.getLiabilityInformation().setRentAndIcowLimit(rail);
								
								// liabilityInformation:rentAndIcowLimit
								AnnualRentReceivable rr = new AnnualRentReceivable();
								if (!Utils.isEmpty(biVO.getRentRecievable())) {
									rr.setAmount((biVO.getRentRecievable()).intValue());
								}
								rr.setCurrency("AED");
								retrieveSBSQuoteResponse.getLiabilityInformation().setAnnualRentReceivable(rr);
							}
							
						}

						// Money
						if (!Utils.isEmpty(groupDetails) && groupDetails instanceof MoneyVO) {
							MoneyVO moneyVO = (MoneyVO) groupDetails;

							// Money section
							Cover moneyCover = new Cover();

							moneyCover.setId(ServiceConstant.SECTION_MONEY_ID);
							moneyCover.setName(ServiceConstant.SECTION_MONEY_DESC);
							CoverPremium moneyCoverPremium = new CoverPremium();
							SumInsured moneySumInsured = new SumInsured();
							Double moneySI = 0.0;
							if (!Utils.isEmpty(moneyVO.getSumInsuredDets())) {
								List<SumInsuredVO> moneySIList = moneyVO.getSumInsuredDets();
								for (SumInsuredVO sumInsuredVO : moneySIList) {
									if (!Utils.isEmpty(sumInsuredVO)) {
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
							LOGGER.info("Money::moneySI-->" + moneySI);
							moneySumInsured.setAmount(moneySI);
							
							moneySumInsured.setCurrencyCode("AED");// TODO:Currency.getUnitName()
							moneyCover.setSumInsured(moneySumInsured);

							// Checking for warning message
							List<String> codes = new ArrayList<String>();
							codes.add("SBSWS_MNY_01");
							codes.add("SBSWS_MNY_02");
							codes.add("SBSWS_MNY_03");
							codes.add("SBSWS_MNY_04");
							codes.add("SBSWS_MNY_05");
							codes.add("SBSWS_MNY_06");
							codes.add("SBSWS_MNY_07");
							codes.add("SBSWS_MNY_08");
							codes.add("SBSWS_MNY_09");
							codes.add("SBSWS_MNY_10");
							if (hasWarning(retrieveSBSQuoteResponse, codes)) {
								LOGGER.info("Warning in Money section..setting premium to 0");
								if (!Utils.isEmpty(moneyVO.getPremium())) {
									moneyVO.getPremium().setPremiumAmt(0);
								}
							}
							Double moneyPremium = 0.0;
							if (!Utils.isEmpty(moneyVO.getPremium())) {
								LOGGER.info("Money::moneyVO.getPremium()-->" + moneyVO.getPremium().getPremiumAmt());
								moneyPremium = moneyVO.getPremium().getPremiumAmt();
								//Adding two decimal values to the premium
								//moneyPremium = WSBusinessValidatorUtils.getFormattedDoubleWithTwoDecimals(moneyVO.getPremium().getPremiumAmt());
							}

							if (moneyPremium != 0) {
								moneyCover.setIsIncludedInPremium("Y");
							} else {
								moneyCover.setIsIncludedInPremium("N");
							}
							//reverted premium changes after currency class fix
							moneyCoverPremium.setAmount(new Double(Currency.getUnformattedScaledCurrency(new BigDecimal(moneyPremium),"SBS")));
							//Handled while setting the value no need to convert
							//moneyCoverPremium.setAmount(moneyPremium);
							moneyCoverPremium.setCurrencyCode("AED");// TODO:Currency.getUnitName()
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
							moneyCover.setCoverPremium(moneyCoverPremium);
							moneyCover.setDeductible(WSBusinessValidatorUtils.getFormattedDoubleWithTwoDecimals(deductible).toString());
							
							covers.add(moneyCover);

							if (!Utils.isEmpty(moneyVO.getContentsList())) {
								for (Contents content : moneyVO.getContentsList()) {
									Integer riskType = content.getRiskType();
									Integer riskCat = content.getRiskCat();
									Double amount = 0.0;
									// Cash In Transit (Single Transit Limit)
									if (riskType == 1 && riskCat == 2) {
										if (!Utils.isEmpty(content.getCover())) {
											amount = content.getCover().doubleValue();
										}
									}
									// Setting the property value here to
									// optimize the code
									MoneyInTransitLimit mitl = new MoneyInTransitLimit();
									mitl.setAmount(amount.intValue());
									mitl.setCurrency("AED");
									retrieveSBSQuoteResponse.getLiabilityInformation().setMaxValuePerTransit(amount.intValue());
								}
							}
							//CTS - 04.09.2020 - CR#11424-JLT API UAT Change - Money attributes need to be populated in Retrieve renewal Quote -- Starts
							List<SumInsuredVO> sumInsuredList = moneyVO.getSumInsuredDets();
							for(SumInsuredVO sumInsured : sumInsuredList){
								if(!Utils.isEmpty(sumInsured) && !Utils.isEmpty(sumInsured.geteDesc())){
								if(sumInsured.geteDesc().equals(Utils.getSingleValueAppConfig("JLT_MONEY_SECTION_ANNUAL_CARRYINGS"))&& !Utils.isEmpty(sumInsured.getSumInsured())){
									retrieveSBSQuoteResponse.getLiabilityInformation().setAnnualCarryingEstimate(sumInsured.getSumInsured().intValue());
								}else if(sumInsured.geteDesc().equals(Utils.getSingleValueAppConfig("JLT_MONEY_SECTION_LOCKED_SAFE_DB")) && !Utils.isEmpty(sumInsured.getSumInsured())){
									retrieveSBSQuoteResponse.getLiabilityInformation().setMoneyInLockedSafe(sumInsured.getSumInsured().intValue());
								}else if(sumInsured.geteDesc().equals(Utils.getSingleValueAppConfig("JLT_MONEY_SECTION_LOCKED_SAFE_AB")) && !Utils.isEmpty(sumInsured.getSumInsured())){
									retrieveSBSQuoteResponse.getLiabilityInformation().setMoneyInLockedSafe(sumInsured.getSumInsured().intValue());
								}else if(sumInsured.geteDesc().equals(Utils.getSingleValueAppConfig("JLT_MONEY_SECTION_LOCKED_DRAWER_AB")) && !Utils.isEmpty(sumInsured.getSumInsured())){
									retrieveSBSQuoteResponse.getLiabilityInformation().setMoneyInLockedDrawer(sumInsured.getSumInsured().intValue());
								}else if(sumInsured.geteDesc().equals(Utils.getSingleValueAppConfig("JLT_MONEY_SECTION_LOCKED_DRAWER_DB")) && !Utils.isEmpty(sumInsured.getSumInsured())){
									retrieveSBSQuoteResponse.getLiabilityInformation().setMoneyInLockedDrawer(sumInsured.getSumInsured().intValue());
								}else if(sumInsured.geteDesc().equals(Utils.getSingleValueAppConfig("JLT_MONEY_SECTION_CASH_IN_RESIDENCE")) && !Utils.isEmpty(sumInsured.getSumInsured())){
									retrieveSBSQuoteResponse.getLiabilityInformation().setMoneyInEmployeePremises(sumInsured.getSumInsured().intValue());
								}
								}
							}
							//CTS - 04.09.2020 - CR#11424-JLT API UAT Change - Money attributes need to be populated in Retrieve renewal Quote -- Ends
						}

						// Fidelity Gurantee
						if (!Utils.isEmpty(groupDetails) && groupDetails instanceof FidelityVO) {
							FidelityVO fidelityVO = (FidelityVO) groupDetails;
							Cover fidelityCover = new Cover();

							fidelityCover.setId(ServiceConstant.SECTION_FIDELITY_ID);
							fidelityCover.setName(ServiceConstant.SECTION_FIDELITY_DESC);

							SumInsured fidelityGuranteeSumInsured = new SumInsured();
							LOGGER.info("Fidelity Gurantee::fidelityVO.getAggregateLimit()-->"
									+ fidelityVO.getAggregateLimit());
							fidelityGuranteeSumInsured.setAmount(fidelityVO.getAggregateLimit());
							fidelityGuranteeSumInsured.setCurrencyCode("AED");// TODO:Currency.getUnitName()
							fidelityCover.setSumInsured(fidelityGuranteeSumInsured);

							// Checking warning message
							List<String> codes = new ArrayList<String>();
							codes.add("SBSWS_FG_001");
							boolean hasWarning = hasWarning(retrieveSBSQuoteResponse, codes);

							CoverPremium fidelityCoverPremium = new CoverPremium();
							if (hasWarning) {
								LOGGER.info("Warning in FG section.. setting premium to 0");
								fidelityVO.setFidAggregateBasePremium(0);
							}

							LOGGER.info("Fidelity Gurantee::fidelityVO.getFidAggregateBasePremium()-->"
									+ fidelityVO.getFidAggregateBasePremium());
							Double fidelityPremium = new Double(fidelityVO.getFidAggregateBasePremium());
							fgBasePremium = fidelityPremium;
							for (FidelityNammedEmployeeDetailsVO fidelityNammedEmployeeDetailsVO : fidelityVO
									.getFidelityEmployeeDetails()) {
								if (!Utils.isEmpty(fidelityNammedEmployeeDetailsVO.getPremium())) {
									if (hasWarning) {
										fidelityNammedEmployeeDetailsVO.getPremium().setPremiumAmt(0);
									}
									LOGGER.info(
											"Fidelity Gurantee Named Employee::fidelityNammedEmployeeDetailsVO.getPremium()-->"
													+ fidelityNammedEmployeeDetailsVO.getPremium().getPremiumAmt());
									//Adding two decimal values to the premium
									fidelityPremium = fidelityPremium
											+ fidelityNammedEmployeeDetailsVO.getPremium().getPremiumAmt();
								//	fidelityPremium = fidelityPremium
										//	+ WSBusinessValidatorUtils.getFormattedDoubleWithTwoDecimals(fidelityNammedEmployeeDetailsVO.getPremium().getPremiumAmt());
								}
							}
							for (FidelityUnnammedEmployeeVO fidelityUnnammedEmployeeVO : fidelityVO
									.getUnnammedEmployeeDetails()) {
								if (!Utils.isEmpty(fidelityUnnammedEmployeeVO.getPremium())) {
									if (hasWarning) {
										fidelityUnnammedEmployeeVO.getPremium().setPremiumAmt(0);
									}
									LOGGER.info(
											"Fidelity Gurantee UnNamed Employee::fidelityUnnammedEmployeeVO.getPremium()-->"
													+ fidelityUnnammedEmployeeVO.getPremium().getPremiumAmt());
									fidelityPremium = fidelityPremium
											+ fidelityUnnammedEmployeeVO.getPremium().getPremiumAmt();
									//Adding two decimal values to the premium
									/*fidelityPremium = fidelityPremium
											+ WSBusinessValidatorUtils.getFormattedDoubleWithTwoDecimals(fidelityUnnammedEmployeeVO.getPremium().getPremiumAmt());*/
								}
							}
							if (fidelityPremium != 0) {
								fidelityCover.setIsIncludedInPremium("Y");
							} else {
								fidelityCover.setIsIncludedInPremium("N");
							}
							//reverted premium changes after currency class fix
							fidelityCoverPremium.setAmount(new Double(Currency.getUnformattedScaledCurrency(new BigDecimal(fidelityPremium),"SBS")));
							//Handled while setting the value no need to convert
							//fidelityCoverPremium.setAmount(fidelityPremium);
							fidelityCoverPremium.setCurrencyCode("AED");// TODO:Currency.getUnitName()

							/*
							 *  For Deductible change
							 */
							Double deductible= 0.00 ;
							if(!Utils.isEmpty(fidelityVO.getDeductible()))
								deductible = fidelityVO.getDeductible();
							
							fidelityCover.setDeductible(WSBusinessValidatorUtils.getFormattedDoubleWithTwoDecimals(deductible).toString());
							
							fidelityCover.setCoverPremium(fidelityCoverPremium);
							//m1043116-added to send FG details in response
							
							//m1043116-added to send FG details in response ended
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
									List<String> codes = new ArrayList<String>();
									codes.add("SBSWS_EE_001");
									if(hasWarning(retrieveSBSQuoteResponse, codes)) {
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
										//eePremium = WSBusinessValidatorUtils.getFormattedDoubleWithTwoDecimals(equipmentVO.getPremium().getPremiumAmt());
									}
									
									if(eePremium != 0) {
										portableEquipmentCover.setIsIncludedInPremium("Y");
									}else {
										portableEquipmentCover.setIsIncludedInPremium("N");

									}
									//Handled while setting the value no need to convert
									//reverted premium changes after currency class fix
									portableEquipmentCoverPremium.setAmount(new Double(Currency.getUnformattedScaledCurrency(new BigDecimal(eePremium),"SBS")));
									//portableEquipmentCoverPremium.setAmount(eePremium);
									portableEquipmentCoverPremium.setCurrencyCode("AED");//TODO:Currency.getUnitName()
									portableEquipmentCover.setDeductible(WSBusinessValidatorUtils.getFormattedDoubleWithTwoDecimals(deductible).toString()); // for deductible change
									portableEquipmentCover.setCoverPremium(portableEquipmentCoverPremium);
									portableEquipmentCover.setCoverPremium(portableEquipmentCoverPremium);

									covers.add(portableEquipmentCover);
									
									// Setting the property value here to
									// optimize the code
									PortableEquipmentLimit pel = new PortableEquipmentLimit();
									pel.setAmount(eeSI.intValue());
									pel.setCurrencyCode("AED");
									retrieveSBSQuoteResponse.getLiabilityInformation().setPortableEquipmentLimit(pel);
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
									List<String> codes = new ArrayList<String>();
									codes.add("SBSWS_EE_001");
									if(hasWarning(retrieveSBSQuoteResponse, codes)) {
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
									}
									else {
										portableEquipmentCover.setIsIncludedInPremium("N");
									}
									//reverted premium changes after currency class fix
									portableEquipmentCoverPremium.setAmount(new Double(Currency.getUnformattedScaledCurrency(new BigDecimal(eePremium),"SBS")));
									//Handled while setting the value no need to convert
									//portableEquipmentCoverPremium.setAmount(eePremium);
									portableEquipmentCoverPremium.setCurrencyCode("AED");//TODO:Currency.getUnitName()
									portableEquipmentCover.setDeductible(WSBusinessValidatorUtils.getFormattedDoubleWithTwoDecimals(deductible).toString()); // for deductible
									portableEquipmentCover.setCoverPremium(portableEquipmentCoverPremium);

									covers.add(portableEquipmentCover);

									// Setting the property value here to
									// optimize the code
									ComputerBreakdownLimit cbl = new ComputerBreakdownLimit();
									cbl.setAmount(eeSI.intValue());
									cbl.setCurrency("AED");
									retrieveSBSQuoteResponse.getLiabilityInformation().setComputerBreakdownLimit(cbl);
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
									List<String> codes = new ArrayList<String>();
									codes.add("SBSWS_MB_001");
									if(hasWarning(retrieveSBSQuoteResponse, codes)) {
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
										//mbPremium =WSBusinessValidatorUtils.getFormattedDoubleWithTwoDecimals(mbDetailsVO.getPremium().getPremiumAmt());
									} 
									if(mbPremium != 0) {
										machineryBreakddownCover.setIsIncludedInPremium("Y");
									}
									else {
										machineryBreakddownCover.setIsIncludedInPremium("N");
									}
									//reverted premium changes after currency class fix
									machineryBreakDowntCoverPremium.setAmount(new Double(Currency.getUnformattedScaledCurrency(new BigDecimal(mbPremium),"SBS")));
									//Handled while setting the value no need to convert
									//machineryBreakDowntCoverPremium.setAmount(mbPremium);
									machineryBreakDowntCoverPremium.setCurrencyCode("AED");//TODO:Currency.getUnitName()
									machineryBreakddownCover.setDeductible(WSBusinessValidatorUtils.getFormattedDoubleWithTwoDecimals(deductible).toString());  // for deductible change
									
									machineryBreakddownCover.setCoverPremium(machineryBreakDowntCoverPremium);

									covers.add(machineryBreakddownCover);
									
									// Setting the property value here to
									// optimize the code
									MachineryBreakdownLimit mbl = new MachineryBreakdownLimit();
									mbl.setAmount(mbSI.intValue());
									mbl.setCurrency("AED");
									retrieveSBSQuoteResponse.getLiabilityInformation().setMachineryBreakdownLimit(mbl);
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
								List<String> codes = new ArrayList<String>();
								codes.add("SBSWS_DOS_001");
								if(hasWarning(retrieveSBSQuoteResponse, codes)) {
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
									//dosPremium = WSBusinessValidatorUtils.getFormattedDoubleWithTwoDecimals(deteriorationOfStockDetailsVO.getPremium().getPremiumAmt());
								} 
								
								if(dosPremium != 0) {
									dosCover.setIsIncludedInPremium("Y");
								}
								else {
									dosCover.setIsIncludedInPremium("N");
								}
								//reverted premium changes after currency class fix
								deteriorarationOfStockCoverPremium.setAmount(new Double(Currency.getUnformattedScaledCurrency(new BigDecimal(dosPremium),"SBS")));
								//Handled while setting the value no need to convert
								//deteriorarationOfStockCoverPremium.setAmount(dosPremium);
								deteriorarationOfStockCoverPremium.setCurrencyCode("AED");//TODO:Currency.getUnitName()
								dosCover.setDeductible(WSBusinessValidatorUtils.getFormattedDoubleWithTwoDecimals(deductible).toString()); // for deductible change
								dosCover.setCoverPremium(deteriorarationOfStockCoverPremium);
								covers.add(dosCover);
								
								// Setting the property value here to optimize
								// the code
								StockGuaranteeLimit sgl = new StockGuaranteeLimit();
								sgl.setAmount(dosSI.intValue());
								sgl.setCurrency("AED");
								retrieveSBSQuoteResponse.getLiabilityInformation().setStockGuaranteeLimit(sgl);
							}
						}

						// Travel Baggage
						if (!Utils.isEmpty(groupDetails) && groupDetails instanceof TravelBaggageVO) {
							TravelBaggageVO travelBaggageVO = (TravelBaggageVO) groupDetails;
							Cover travelBaggageCover = new Cover();
							Double deductible = 0.00;
							
							travelBaggageCover.setId(ServiceConstant.SECTION_TRAVEL_BAGGAGE_ID);
							travelBaggageCover.setName(ServiceConstant.SECTION_TRAVEL_BAGGAGE_DESC);
							SumInsured travelBaggageSumInsured = new SumInsured();
							LOGGER.info("Travel Baggage::travelBaggageVO.getSumInsured()-->"+travelBaggageVO.getSumInsured());
							travelBaggageSumInsured.setAmount(travelBaggageVO.getSumInsured());
							travelBaggageSumInsured.setCurrencyCode("AED");//TODO:Currency.getUnitName()
							travelBaggageCover.setSumInsured(travelBaggageSumInsured);

							//Checking warnings
							List<String> codes = new ArrayList<String>();
							codes.add("SBSWS_TRL_001");
							if(hasWarning(retrieveSBSQuoteResponse, codes)) {
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
							
							//Handled while setting the value no need to convert
							//reverted premium changes after currency class fix
							travelBaggagePremium.setAmount(new Double(Currency.getUnformattedScaledCurrency(new BigDecimal(travelPremium),"SBS")));
							//travelBaggagePremium.setAmount(travelPremium);
							travelBaggagePremium.setCurrencyCode("AED");//TODO:Currency.getUnitName()
							travelBaggageCover.setDeductible(WSBusinessValidatorUtils.getFormattedDoubleWithTwoDecimals(deductible).toString()); // for deductible
							
							travelBaggageCover.setCoverPremium(travelBaggagePremium);
							covers.add(travelBaggageCover);
						}

						// Group Personal Accident
						if (!Utils.isEmpty(groupDetails) && groupDetails instanceof GroupPersonalAccidentVO) {
							GroupPersonalAccidentVO groupPersonalAccidentVO = (GroupPersonalAccidentVO) groupDetails;
							Cover gpaCover = new Cover();

							gpaCover.setId(ServiceConstant.SECTION_GPA_ID);
							gpaCover.setName(ServiceConstant.SECTION_GPA_DESC);
							SumInsured gpaSumInsured = new SumInsured();
							Double gpaSI = 0.0;
							List<GPANammedEmpVO> gpaNameEmpList = groupPersonalAccidentVO.getGpaNammedEmpVO();
							for(GPANammedEmpVO gpaNammedEmpVO : gpaNameEmpList) {
								if(!Utils.isEmpty(gpaNammedEmpVO.getSumInsuredDetails())) {
									gpaSI = gpaSI + gpaNammedEmpVO.getSumInsuredDetails().getSumInsured();
								}
							}
							LOGGER.info("Group Personal Accident::gpaSI-->" + gpaSI);
							gpaSumInsured.setAmount(gpaSI);
							gpaSumInsured.setCurrencyCode("AED");//TODO:Currency.getUnitName()
							
							gpaCover.setSumInsured(gpaSumInsured);

							//Checking warnings
							List<String> codes = new ArrayList<String>();
							codes.add("SBSWS_GPA_001");
							if(hasWarning(retrieveSBSQuoteResponse, codes)) {
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
								//Adding two decimal values to the premium
								gpaPrm = groupPersonalAccidentVO.getPremium().getPremiumAmt();
								//gpaPrm =WSBusinessValidatorUtils.getFormattedDoubleWithTwoDecimals(groupPersonalAccidentVO.getPremium().getPremiumAmt());
							} 
							
							if(gpaPrm != 0) {
								gpaCover.setIsIncludedInPremium("Y");
							}
							else {
								gpaCover.setIsIncludedInPremium("N");
							}
							//reverted premium changes after currency class fix
							gpaPremium.setAmount(new Double(Currency.getUnformattedScaledCurrency(new BigDecimal(gpaPrm),"SBS")));
							//Handled while setting the value no need to convert
							//gpaPremium.setAmount(gpaPrm);
							gpaPremium.setCurrencyCode("AED");//TODO:Currency.getUnitName()
							gpaCover.setDeductible(WSBusinessValidatorUtils.getFormattedDoubleWithTwoDecimals(groupPersonalAccidentVO.getGpaDeductible()).toString());
							
							gpaCover.setCoverPremium(gpaPremium);
							covers.add(gpaCover);
						}
					}

				}

			}
			retrieveSBSQuoteResponse.getSelectedPlan().setCovers(covers);

		}
		
		if (!Utils.isEmpty(policyVO.getPremiumVO())) {
			Double polVatRate = 0.0;
			Double totalPremium = 0.0;
			
			//Adding two decimal values to the premium
			//reverted premium changes after currency class fix
					retrieveSBSQuoteResponse.getSelectedPlan().getPremium().getPremium()
			.setAmount(new Double(Currency.getUnformattedScaledCurrency(new BigDecimal(policyVO.getPremiumVO().getPremiumAmt()),"SBS")));
					/*retrieveSBSQuoteResponse.getSelectedPlan().getPremium().getPremium()
					.setAmount(WSBusinessValidatorUtils.getFormattedDoubleWithTwoDecimals(policyVO.getPremiumVO().getPremiumAmt()));*/
			retrieveSBSQuoteResponse.getSelectedPlan().getPremium().getPremium().setCurrencyCode("AED");
			
			if(!Utils.isEmpty(policyVO.getPolVatRate())) {
				polVatRate = policyVO.getPolVatRate().doubleValue();
				
				if(policyVO.getPremiumVO().getPremiumAmt() != 0){
					totalPremium = policyVO.getPremiumVO().getPremiumAmt() + polVatRate;
				}
				LOGGER.info("polVatRate-->" + polVatRate);
				LOGGER.info("totalPremium-->" + totalPremium);
			}else{
				//Adding two decimal values to the premium
				totalPremium = policyVO.getPremiumVO().getPremiumAmt();
				//totalPremium = WSBusinessValidatorUtils.getFormattedDoubleWithTwoDecimals(policyVO.getPremiumVO().getPremiumAmt());
		
			}

			//retrieveSBSQuoteResponse.getSelectedPlan().getPremium().getVatOnPremium().setAmount(WSBusinessValidatorUtils.getFormattedDoubleWithTwoDecimals(polVatRate));
			//reverted premium changes after currency class fix
			retrieveSBSQuoteResponse.getSelectedPlan().getPremium().getVatOnPremium().setAmount(AppUtils.getFormattedDoubleWithTwoDecimals(polVatRate));
			retrieveSBSQuoteResponse.getSelectedPlan().getPremium().getVatOnPremium().setCurrencyCode("AED");
			
			//retrieveSBSQuoteResponse.getSelectedPlan().getPremium().getGrossPremium().setAmount(WSBusinessValidatorUtils.getFormattedDoubleWithTwoDecimals(totalPremium));
			//reverted premium changes after currency class fix
			retrieveSBSQuoteResponse.getSelectedPlan().getPremium().getGrossPremium().setAmount(AppUtils.getFormattedDoubleWithTwoDecimals(totalPremium));
			retrieveSBSQuoteResponse.getSelectedPlan().getPremium().getGrossPremium().setCurrencyCode("AED");
			
		}
	}

	public void initializeObjects(RetrieveSBSQuoteResponse retrieveSBSQuoteResponse) {

		if (Utils.isEmpty(retrieveSBSQuoteResponse.getSelectedPlan())) {
			retrieveSBSQuoteResponse.setSelectedPlan(new SelectedPlan());
		}
		if (Utils.isEmpty(retrieveSBSQuoteResponse.getSelectedPlan().getPremium())) {
			retrieveSBSQuoteResponse.getSelectedPlan().setPremium(new Premium());
		}
		if (Utils.isEmpty(retrieveSBSQuoteResponse.getSelectedPlan().getPremium().getPremium())) {
			retrieveSBSQuoteResponse.getSelectedPlan().getPremium().setPremium(new Premium_());
		}
		if (Utils.isEmpty(retrieveSBSQuoteResponse.getSelectedPlan().getPremium().getGrossPremium())) {
			retrieveSBSQuoteResponse.getSelectedPlan().getPremium().setGrossPremium(new GrossPremium());
		}
		if (Utils.isEmpty(retrieveSBSQuoteResponse.getSelectedPlan().getPremium().getVatOnPremium())) {
			retrieveSBSQuoteResponse.getSelectedPlan().getPremium().setVatOnPremium(new VatOnPremium());;
		}

		if (Utils.isEmpty(retrieveSBSQuoteResponse.getLiabilityInformation())) {
			retrieveSBSQuoteResponse.setLiabilityInformation(new LiabilityInformation());
			retrieveSBSQuoteResponse.getLiabilityInformation()
					.setNameOfFreeZoneAuthority(new NameOfFreeZoneAuthority());
			retrieveSBSQuoteResponse.getLiabilityInformation().setClaimInformation(new ClaimInformation());
		}

	}

	public void mapPolicyHolder(PolicyVO policyVO, RetrieveSBSQuoteResponse retrieveSBSQuoteResponse) {

		/* Start default Object Initialization */
		retrieveSBSQuoteResponse.setPolicyHolder(new PolicyHolder());
		retrieveSBSQuoteResponse.getPolicyHolder().setCompany(new Company());
		retrieveSBSQuoteResponse.getPolicyHolder().getCompany().setNatureOfBusiness(new NatureOfBusiness());
		retrieveSBSQuoteResponse.getPolicyHolder().getCompany().setRevenue(new Revenue());
		retrieveSBSQuoteResponse.getPolicyHolder().setContactMethods(new ContactMethods());

		List<PostMailContact> psList = new ArrayList<PostMailContact>(1);
		PostMailContact pmc1 = new PostMailContact();
		psList.add(pmc1);
		retrieveSBSQuoteResponse.getPolicyHolder().getContactMethods().setPostMailContact(psList);
		retrieveSBSQuoteResponse.getPolicyHolder().getContactMethods().getPostMailContact().get(0).setCity(new City());

		List<PhoneContact> pcList = new ArrayList<PhoneContact>(2);
		PhoneContact pc1 = new PhoneContact();
		PhoneContact pc2 = new PhoneContact();
		pcList.add(pc1);
		pcList.add(pc2);
		retrieveSBSQuoteResponse.getPolicyHolder().getContactMethods().setPhoneContacts(pcList);

		List<EmailContact> ecList = new ArrayList<EmailContact>();
		EmailContact ec1 = new EmailContact();
		ecList.add(ec1);
		retrieveSBSQuoteResponse.getPolicyHolder().getContactMethods().setEmailContact(ecList);

		/*retrieveSBSQuoteResponse.getPolicyHolder().setPrimaryContact(new PrimaryContact());
		retrieveSBSQuoteResponse.getPolicyHolder().getPrimaryContact().setContactMethods(new ContactMethods_());
		retrieveSBSQuoteResponse.getPolicyHolder().getPrimaryContact().getContactMethods()
				.setPostMailContact(new ArrayList<PostMailContact_>());
		retrieveSBSQuoteResponse.getPolicyHolder().getPrimaryContact().getContactMethods()
				.setPhoneContacts(new ArrayList<PhoneContact_>());
		retrieveSBSQuoteResponse.getPolicyHolder().getPrimaryContact().getContactMethods()
				.setEmailContact(new ArrayList<EmailContact_>());*/
		
		/* End default Object Initialization */

		// policyHolder: company details
		retrieveSBSQuoteResponse.getPolicyHolder().getCompany()
				.setName(policyVO.getGeneralInfo().getInsured().getName());
		
	    //CTS - 05.10.2020 - JLT UAT CHANGE - REVENUE NAME CHANGE / DATA TYPE CHANGE
		if(!Utils.isEmpty(policyVO.getGeneralInfo().getInsured().getTurnover())){
			retrieveSBSQuoteResponse.getPolicyHolder().getCompany()
			.getRevenue().setAmount(policyVO.getGeneralInfo().getInsured().getTurnover().intValue());
		}
		
		retrieveSBSQuoteResponse.getPolicyHolder().getCompany()
				.setCompanyVATRegistrationNumber(policyVO.getGeneralInfo().getInsured().getVatRegNo());
		
		if(!Utils.isEmpty(policyVO.getGeneralInfo().getInsured().getNoOfEmployees())){
			retrieveSBSQuoteResponse.getPolicyHolder().getCompany()
			.setNumberOfEmployee(policyVO.getGeneralInfo().getInsured().getNoOfEmployees());
		}
		
		retrieveSBSQuoteResponse.getPolicyHolder().getCompany()
		.getNatureOfBusiness().setCode(policyVO.getGeneralInfo().getInsured().getBusDescription());

		// policyHolder: contactMethods: postMailContact details
		retrieveSBSQuoteResponse.getPolicyHolder().getContactMethods().getPostMailContact().get(0)
				.setAddressLine2(policyVO.getGeneralInfo().getInsured().getAddress().getAddress());
		retrieveSBSQuoteResponse.getPolicyHolder().getContactMethods().getPostMailContact().get(0).getCity()
				.setValue((policyVO.getGeneralInfo().getInsured().getCity()).toString());
		retrieveSBSQuoteResponse.getPolicyHolder().getContactMethods().getPostMailContact().get(0)
				.setCountry(policyVO.getGeneralInfo().getInsured().getAddress().getCountry().toString());
		retrieveSBSQuoteResponse.getPolicyHolder().getContactMethods().getPostMailContact().get(0)
				.setPoBox(policyVO.getGeneralInfo().getInsured().getAddress().getPoBox());

		// policyHolder: contactMethods: phoneContacts details
		if(!Utils.isEmpty(policyVO.getGeneralInfo().getInsured().getMobileNo())){
			retrieveSBSQuoteResponse.getPolicyHolder().getContactMethods().getPhoneContacts().get(0)
			.setInternationalFullNumber(policyVO.getGeneralInfo().getInsured().getMobileNo());
		}
		
		if(!Utils.isEmpty(policyVO.getGeneralInfo().getInsured().getPhoneNo())){
			retrieveSBSQuoteResponse.getPolicyHolder().getContactMethods().getPhoneContacts().get(1)
			.setInternationalFullNumber(policyVO.getGeneralInfo().getInsured().getPhoneNo());
		}

		// policyHolder: contactMethods: emailContact details
		if(!Utils.isEmpty(policyVO.getGeneralInfo().getInsured().getEmailId())){
			retrieveSBSQuoteResponse.getPolicyHolder().getContactMethods().getEmailContact().get(0)
			.setUrl(policyVO.getGeneralInfo().getInsured().getEmailId());
		}
	}

	public void mapPolicySchedule(PolicyVO policyVO, RetrieveSBSQuoteResponse retrieveSBSQuoteResponse) {
		retrieveSBSQuoteResponse.setPolicySchedule(new PolicySchedule());
		Format formatter = new SimpleDateFormat("yyyy-MM-dd");
		retrieveSBSQuoteResponse.getPolicySchedule().setPolicyType(policyVO.getScheme().getPolicyType());
		//CTS JLT API Change - 23.07.2020 ADD policyYear and policyNo for create, update and retrieve Quote - Starts
			retrieveSBSQuoteResponse.getPolicySchedule().setPolicyYear(String.valueOf(SvcUtils.getYearFromDate(policyVO.getPolEffectiveDate())));	
		//CTS JLT API Change - 23.07.2020 ADD policyYear and policyNo for create, update and retrieve Quote - Ends
		retrieveSBSQuoteResponse.getPolicySchedule().setCreationDate(formatter.format(policyVO.getProcessedDate()));
		retrieveSBSQuoteResponse.getPolicySchedule()
				.setEffectiveDate(formatter.format(policyVO.getScheme().getEffDate()));
		retrieveSBSQuoteResponse.getPolicySchedule()
				.setExpirationDate(formatter.format(policyVO.getScheme().getExpiryDate()));

		int monthsCount;
		int yearscount = policyVO.getPolicyTerm();
		if (yearscount == 1 || yearscount == 2 || yearscount == 3 || yearscount == 4 || yearscount == 5) {
			monthsCount = yearscount * 12;
			retrieveSBSQuoteResponse.getPolicySchedule().setDuration(monthsCount + " " + "months");
		} else if (yearscount == 13) {
			retrieveSBSQuoteResponse.getPolicySchedule().setDuration(yearscount + " " + "months");
		}
	}

	public boolean hasWarning(RetrieveSBSQuoteResponse retrieveSBSQuoteResponse, List<String> codes) {

		List<SBSWSValidators> sbsWSValidators = retrieveSBSQuoteResponse.getSbswsValidators();

		if (!Utils.isEmpty(sbsWSValidators)) {
			for (SBSWSValidators validator : sbsWSValidators) {
				for (String code : codes) {
					if (!Utils.isEmpty(code) && code.equals(validator.getCode())) {
						return true;
					}
				}
			}
		}

		return false;
	}
}
