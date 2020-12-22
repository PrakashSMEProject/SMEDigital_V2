package com.rsaame.pas.b2c.ws.mapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import com.rsaame.pas.vo.bus.AuthenticationInfoVO;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.b2c.cmn.constants.AppConstants;
import com.rsaame.pas.b2c.ws.utilities.WebServiceUtils;
import com.rsaame.pas.b2c.ws.vo.CreateHomeQuoteRequest;
import com.rsaame.pas.b2c.ws.vo.ListedItems;
import com.rsaame.pas.b2c.ws.vo.MandatoryCovers;
import com.rsaame.pas.b2c.ws.vo.OptionalCovers;
import com.rsaame.pas.b2c.ws.vo.Staff;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.vo.bus.AddressVO;
import com.rsaame.pas.vo.bus.BuildingDetailsVO;
import com.rsaame.pas.vo.bus.CoverDetailsVO;
import com.rsaame.pas.vo.bus.GeneralInfoVO;
import com.rsaame.pas.vo.bus.HomeInsuranceVO;
import com.rsaame.pas.vo.bus.InsuredVO;
import com.rsaame.pas.vo.bus.SchemeVO;
import com.rsaame.pas.vo.bus.SourceOfBusinessVO;
import com.rsaame.pas.vo.bus.StaffDetailsVO;
import com.rsaame.pas.vo.bus.SumInsuredVO;
import com.rsaame.pas.vo.cmn.CoverVO;
import com.rsaame.pas.vo.cmn.RiskVO;

/**
 * @author m1020637
 * 
 */
public class HomeCreateQuoteRequestMapper implements BaseRequestVOMapper {
	
	private final static Logger LOGGER = Logger.getLogger(HomeCreateQuoteRequestMapper.class);
	WebServiceUtils webServiceUtils = new WebServiceUtils();
	@Override
	public void mapRequestToVO(Object requestObj, Object valueObj)
			throws Exception {
		LOGGER.info("Enters to HomeCreateQuoteRequestMapper.mapRequestToVO, maps the request to homeInsuranceVO details..");
		if (requestObj instanceof CreateHomeQuoteRequest
				&& valueObj instanceof HomeInsuranceVO) {
			CreateHomeQuoteRequest createHomeQuoteRequest = (CreateHomeQuoteRequest) requestObj;
			HomeInsuranceVO homeInsuranceVO = (HomeInsuranceVO) valueObj;

			//Objects Null Check
			nullCheckObjects(homeInsuranceVO);
			
			//Customer details
			mapGeneralInsuranceDetails(createHomeQuoteRequest,homeInsuranceVO);
			
			//Transaction Details
			mapTransactionDetails(createHomeQuoteRequest,homeInsuranceVO);
			
			//Partner Details
			//mapPartnerDetails(createHomeQuoteRequest,homeInsuranceVO);
			
			mapBuildingDetails(createHomeQuoteRequest, homeInsuranceVO);
			
			//Mandatory Cover Details
			mapMandatoryCoversDetails(createHomeQuoteRequest, homeInsuranceVO);
			
			//Optional Covers Details
			mapOptionalCoversDetails(createHomeQuoteRequest, homeInsuranceVO);
			
			//Staff Details
			//mapStaffDetails(createHomeQuoteRequest, homeInsuranceVO);
			
			//Listed Items in Mandatory Covers
			mapListedItems(createHomeQuoteRequest, homeInsuranceVO);
			
			LOGGER.info("Successfully mapped to request object to HomeInsuranceVO details..");
		} else {
			throw new Exception("Unexpected request or value object");
		}

	}


	




	private void mapGeneralInsuranceDetails(CreateHomeQuoteRequest createHomeQuoteRequest,HomeInsuranceVO homeInsuranceVO) {
		
		if (createHomeQuoteRequest != null && createHomeQuoteRequest.getCustomerDetails() != null) {
			homeInsuranceVO.getGeneralInfo().getInsured().setFirstName(createHomeQuoteRequest.getCustomerDetails().getFirstName());
			homeInsuranceVO.getGeneralInfo().getInsured().setLastName(createHomeQuoteRequest.getCustomerDetails().getLastName());
			homeInsuranceVO.getGeneralInfo().getInsured().getAddress().setPoBox(createHomeQuoteRequest.getCustomerDetails().getPoBox());
			homeInsuranceVO.getGeneralInfo().getInsured().setCity(createHomeQuoteRequest.getCustomerDetails().getCity());
			homeInsuranceVO.getGeneralInfo().getInsured().setMobileNo(createHomeQuoteRequest.getCustomerDetails().getMobileNo().toString());
			homeInsuranceVO.getGeneralInfo().getInsured().setEmailId(createHomeQuoteRequest.getCustomerDetails().getEmailId());
			homeInsuranceVO.getGeneralInfo().getInsured().setNationality(createHomeQuoteRequest.getCustomerDetails().getNationality());
			homeInsuranceVO.getGeneralInfo().getInsured().setVatRegNo(createHomeQuoteRequest.getCustomerDetails().getVatRegNo());		
			//CTS - 02.09.2020 - TFS#42533 - Retrieve quote in B2B showing errors - Starts
			homeInsuranceVO.getGeneralInfo().getInsured().getAddress().setPoBox(AppConstants.zeroVal);// Set as zero initally during create quote
			if(Utils.isEmpty(homeInsuranceVO.getGeneralInfo().getInsured().getCity())){
				homeInsuranceVO.getGeneralInfo().getInsured().setCity(Integer.valueOf(Utils.getSingleValueAppConfig("DEFAULT_CUSTOMER_CITY")));
			}
			//CTS - 02.09.2020 - TFS#42533 - Retrieve quote in B2B showing errors - Ends
			if(createHomeQuoteRequest.getCustomerDetails().getNationalID() ==null){
				homeInsuranceVO.getGeneralInfo().getInsured().getAddress().setCountry(null);
			}else{
				homeInsuranceVO.getGeneralInfo().getInsured().getAddress().setCountry(Integer.parseInt(createHomeQuoteRequest.getCustomerDetails().getNationalID()));

			}
			homeInsuranceVO.getGeneralInfo().getInsured().setRoyaltyType(createHomeQuoteRequest.getCustomerDetails().getRewardProgrammeType());
			//CTS - 02.09.2020 - TFS#42533 - Retrieve quote in B2B showing errors - Starts
			if(!Utils.isEmpty(createHomeQuoteRequest.getCustomerDetails().getRewardCardNumber()))
				homeInsuranceVO.getGeneralInfo().getInsured().setGuestCardNo(createHomeQuoteRequest.getCustomerDetails().getRewardCardNumber().toString());
			//CTS - 02.09.2020 - TFS#42533 - Retrieve quote in B2B showing errors - Ends
		}
	}	
	
	/**
	 * @Description : Maps Transaction Details
	 * @param transactionDetails
	 * @param homeInsuranceVO
	 */
	private void mapTransactionDetails(CreateHomeQuoteRequest createHomeQuoteRequest,HomeInsuranceVO homeInsuranceVO) {
		Object[] result = (Object[]) DAOUtils.fetchDefaultValues(createHomeQuoteRequest.getPmmId());
		if (createHomeQuoteRequest != null && createHomeQuoteRequest.getTransactionDetails() != null) {
			
			homeInsuranceVO.setClassCode(((BigDecimal) result[0]).intValue());
			homeInsuranceVO.setPolicyType(((BigDecimal) result[1]).intValue());
			if(result[2]==null) {
				homeInsuranceVO.setPolicyTerm(null);
			}else {
				homeInsuranceVO.setPolicyTerm(((BigDecimal) result[2]).intValue());
			}
			if(Utils.isEmpty(createHomeQuoteRequest.getTransactionDetails().getEffectiveDate())) {
				homeInsuranceVO.getScheme().setEffDate(null);
			}else {
				homeInsuranceVO.getScheme().setEffDate(createHomeQuoteRequest.getTransactionDetails().getEffectiveDate());
			}
			if(Utils.isEmpty(createHomeQuoteRequest.getTransactionDetails().getExpiryDate())) {
				homeInsuranceVO.getScheme().setExpiryDate(null);
			}else {
				homeInsuranceVO.getScheme().setExpiryDate(createHomeQuoteRequest.getTransactionDetails().getExpiryDate());
			}						
			homeInsuranceVO.getScheme().setSchemeCode(((BigDecimal) result[3]).intValue());
			homeInsuranceVO.getScheme().setTariffCode(((BigDecimal) result[4]).intValue());
			if(result[5]==null) {
				homeInsuranceVO.getGeneralInfo().getSourceOfBus().setDistributionChannel(null);
			}else {
				homeInsuranceVO.getGeneralInfo().getSourceOfBus().setDistributionChannel(((BigDecimal) result[5]).intValue());
			}
			
			if (createHomeQuoteRequest.getTransactionDetails().getDirectSubAgent() == null){
				homeInsuranceVO.getGeneralInfo().getSourceOfBus().setDirectSubAgent(null);
			}
			else{
				homeInsuranceVO.getGeneralInfo().getSourceOfBus().setDirectSubAgent(createHomeQuoteRequest.getTransactionDetails().getDirectSubAgent().longValue());
			}
			
			homeInsuranceVO.getGeneralInfo().getSourceOfBus().setPromoCode(createHomeQuoteRequest.getTransactionDetails().getPromocode());
			homeInsuranceVO.getGeneralInfo().getSourceOfBus().setCustomerSource(((BigDecimal) result[6]).toString()); 
			//homeInsuranceVO "PartnerTrnReferenceNumber": null
			if(result[9] == null) {
				homeInsuranceVO.getGeneralInfo().setIntAccExecCode(null);
			}else{
				homeInsuranceVO.getGeneralInfo().setIntAccExecCode(((BigDecimal) result[9]).intValue());
				//CTS - 17.09.2020 - TFS#42533 - Retrieve quote in B2B showing errors - Starts
				homeInsuranceVO.getAuthenticationInfoVO().setIntAccExecCode(((BigDecimal) result[9]).intValue());
				//CTS - 17.09.2020 - TFS#42533 - Retrieve quote in B2B showing errors - Ends
			}
			
		}
	}
	
	/**
	 * @Description : Partner Details
	 * @param : partnerDetails
	 * @param : homeInsuranceVO
	 */
	private void mapPartnerDetails(CreateHomeQuoteRequest createHomeQuoteRequest, HomeInsuranceVO homeInsuranceVO) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * @Description : Maps Building Details Section
	 * @param : buildingDetails
	 * @param : homeInsuranceVO
	 */
	private void mapBuildingDetails(CreateHomeQuoteRequest createHomeQuoteRequest,HomeInsuranceVO homeInsuranceVO) {
		if(createHomeQuoteRequest.getBuildingDetails().getEmirate() == null){
			homeInsuranceVO.getBuildingDetails().setEmirates(null);
		}
		else{
			homeInsuranceVO.getBuildingDetails().setEmirates(createHomeQuoteRequest.getBuildingDetails().getEmirate().toString());
		}
		if(!Utils.isEmpty(createHomeQuoteRequest.getBuildingDetails().getArea()) && Utils.isEmpty(createHomeQuoteRequest.getBuildingDetails().getAreaOthers())){
			homeInsuranceVO.getBuildingDetails().setArea(createHomeQuoteRequest.getBuildingDetails().getArea().toString());
		}
		else{
			if(createHomeQuoteRequest.getBuildingDetails().getArea() == null)
			{
				homeInsuranceVO.getBuildingDetails().setArea(null);
			}
			else{
				homeInsuranceVO.getBuildingDetails().setArea(createHomeQuoteRequest.getBuildingDetails().getArea().toString());
			}
		}
		if(createHomeQuoteRequest.getBuildingDetails().getPropertyType() == null)
		{
			homeInsuranceVO.getBuildingDetails().setTypeOfProperty(null);
		}
		else{
			homeInsuranceVO.getBuildingDetails().setTypeOfProperty(createHomeQuoteRequest.getBuildingDetails().getPropertyType().shortValue());
		}
		homeInsuranceVO.getBuildingDetails().setBuildingname(createHomeQuoteRequest.getBuildingDetails().getBuildingName());
		if(createHomeQuoteRequest.getBuildingDetails().getFlatVillaNo() == null){
			homeInsuranceVO.getBuildingDetails().setFlatVillaNo("");
		}
		else{
			homeInsuranceVO.getBuildingDetails().setFlatVillaNo(createHomeQuoteRequest.getBuildingDetails().getFlatVillaNo());
		}
		if(!Utils.isEmpty(createHomeQuoteRequest.getBuildingDetails().getMortgageeCode()) && Utils.isEmpty(createHomeQuoteRequest.getBuildingDetails().getMortgageeOthers())){
			homeInsuranceVO.getBuildingDetails().setMortgageeName(createHomeQuoteRequest.getBuildingDetails().getMortgageeCode().toString());
		}
		else{
			if(createHomeQuoteRequest.getBuildingDetails().getMortgageeOthers()== null)
			{
				homeInsuranceVO.getBuildingDetails().setMortgageeName(null);
			}
			else{
				homeInsuranceVO.getBuildingDetails().setMortgageeName(createHomeQuoteRequest.getBuildingDetails().getMortgageeOthers());
			}
		}
		homeInsuranceVO.getBuildingDetails().setOwnershipStatus(createHomeQuoteRequest.getBuildingDetails().getOwnershipStatus().shortValue());
	}

	/**
	 * @Description : Mandatory Cover Details
	 * @param : homeInsurance CoverDetails
	 * @param : homeInsuranceVO
	 */
	private void mapMandatoryCoversDetails(CreateHomeQuoteRequest createHomeQuoteRequest,HomeInsuranceVO homeInsuranceVO) {
		if(!Utils.isEmpty(createHomeQuoteRequest.getMandatoryCovers())) {
			
			for (MandatoryCovers mandatoryCovers : createHomeQuoteRequest.getMandatoryCovers()) {
				String[] riskCodes= mandatoryCovers.getRiskMappingCode().split("-");
				if (mandatoryCovers.getCoverIncluded() && Integer.parseInt(riskCodes[1]) ==SvcConstants.HOME_BUILDING_RISK_TYPE
						&& createHomeQuoteRequest.getBuildingDetails().getOwnershipStatus()==SvcConstants.oneVal) {
					
					homeInsuranceVO.getBuildingDetails().setRiskCodes(new RiskVO());
					homeInsuranceVO.getBuildingDetails().setSumInsured(new SumInsuredVO());
					homeInsuranceVO.getBuildingDetails().setCoverCodes(new CoverVO());
					
					homeInsuranceVO.getBuildingDetails().getSumInsured().setSumInsured(mandatoryCovers.getCoverageLimit().doubleValue());
					
					//String[] coverCodes= mandatoryCovers.getCoverMappingCode().split("-");
					
					
					homeInsuranceVO.getBuildingDetails().getCoverCodes().setCovCode((short) SvcConstants.oneVal);
					homeInsuranceVO.getBuildingDetails().getCoverCodes().setCovTypeCode((short) SvcConstants.zeroVal);   
					homeInsuranceVO.getBuildingDetails().getCoverCodes().setCovSubTypeCode((short) SvcConstants.zeroVal);
					
					/*if (!Utils.isEmpty(mandatoryCovers.getRiskDetails().getRskId())) {
						homeInsuranceVO.getBuildingDetails().getRiskCodes()
								.setRskId(BigDecimal.valueOf(mandatoryCovers.getRiskDetails().getRskId()));
					}
					if(!Utils.isEmpty(mandatoryCovers.getRiskDetails().getBasicRskId())) {
						homeInsuranceVO.getBuildingDetails().getRiskCodes().setBasicRskId(BigDecimal.valueOf(mandatoryCovers.getRiskDetails().getBasicRskId()));
					}*/
					
					
					homeInsuranceVO.getBuildingDetails().getRiskCodes().setBasicRskCode(1);
					homeInsuranceVO.getBuildingDetails().getRiskCodes().setRiskCode(Integer.parseInt(riskCodes[0]));
					homeInsuranceVO.getBuildingDetails().getRiskCodes().setRiskType(Integer.parseInt(riskCodes[1]));
					homeInsuranceVO.getBuildingDetails().getRiskCodes().setRiskCat(Integer.parseInt(riskCodes[2]));
					
//					homeInsuranceVO.getBuildingDetails().getSumInsured().setSumInsured(updateHomeQuoteRequest.getBuildingDetails().getSumInsured()); 
					
				}else if(mandatoryCovers.getCoverIncluded() && Integer.parseInt(riskCodes[1]) !=SvcConstants.HOME_BUILDING_RISK_TYPE){
					CoverDetailsVO coverDetailsVO = new CoverDetailsVO();
					coverDetailsVO.setCoverCodes(new CoverVO());
					coverDetailsVO.setRiskCodes(new RiskVO());
					coverDetailsVO.setSumInsured(new SumInsuredVO());
					coverDetailsVO.setIsCovered(AppConstants.STATUS_ON);
					coverDetailsVO.setCoverName(mandatoryCovers.getCoverDesc());
					
					coverDetailsVO.getCoverCodes().setCovCode((short) SvcConstants.oneVal);
					coverDetailsVO.getCoverCodes().setCovTypeCode((short) SvcConstants.zeroVal);
					coverDetailsVO.getCoverCodes().setCovSubTypeCode((short) SvcConstants.zeroVal);

					/*if (!Utils.isEmpty(mandatoryCovers.getRiskDetails().getRskId())) {
						coverDetailsVO.getRiskCodes()
								.setRskId(BigDecimal.valueOf(mandatoryCovers.getRiskDetails().getRskId()));
					}
					if(!Utils.isEmpty(mandatoryCovers.getRiskDetails().getBasicRskId())) {
						coverDetailsVO.getRiskCodes().setBasicRskId(BigDecimal.valueOf(mandatoryCovers.getRiskDetails().getBasicRskId()));
					}*/
					coverDetailsVO.getRiskCodes().setBasicRskCode(1);
					coverDetailsVO.getRiskCodes().setRiskCode(Integer.parseInt(riskCodes[0]));
					coverDetailsVO.getRiskCodes().setRiskType(Integer.parseInt(riskCodes[1]));
					coverDetailsVO.getRiskCodes().setRiskCat(Integer.parseInt(riskCodes[2]));

					coverDetailsVO.getSumInsured().setSumInsured(mandatoryCovers.getCoverageLimit().doubleValue());

					homeInsuranceVO.getCovers().add(coverDetailsVO);
				}
			}
		}

	}
	
	/**
	 * @Description : Maps Cover Details
	 * @param : homeInsurance CoverDetails
	 * @param : homeInsuranceVO
	 */
	private void mapListedItems(CreateHomeQuoteRequest createHomeQuoteRequest, HomeInsuranceVO homeInsuranceVO) {
		if (!Utils.isEmpty(createHomeQuoteRequest.getListedItems())) {

			for (ListedItems listedItemsCovers : createHomeQuoteRequest.getListedItems()) {
				String[] riskCodes= listedItemsCovers.getRiskMappingCode().split("-");
				if (listedItemsCovers.getCoverIncluded()) {

					CoverDetailsVO coverDetailsVO = new CoverDetailsVO();
					coverDetailsVO.setCoverCodes(new CoverVO());
					coverDetailsVO.setRiskCodes(new RiskVO());
					coverDetailsVO.setSumInsured(new SumInsuredVO());
					
					
					//String[] coverCodes = optionalCovers.getCoverMappingCode().split("-");
					coverDetailsVO.setCoverName(listedItemsCovers.getCoverDesc());
					coverDetailsVO.setIsCovered(AppConstants.STATUS_ON);
					coverDetailsVO.getCoverCodes().setCovCode((short) SvcConstants.oneVal);
					coverDetailsVO.getCoverCodes().setCovTypeCode((short) SvcConstants.zeroVal);
					coverDetailsVO.getCoverCodes().setCovSubTypeCode((short) SvcConstants.zeroVal);

					/*if (!Utils.isEmpty(optionalCovers.getRiskDetails().getRskId())) {
						coverDetailsVO.getRiskCodes()
								.setRskId(BigDecimal.valueOf(optionalCovers.getRiskDetails().getRskId()));
					}
					if (!Utils.isEmpty(optionalCovers.getRiskDetails().getBasicRskId())) {
						coverDetailsVO.getRiskCodes()
								.setBasicRskId(BigDecimal.valueOf(optionalCovers.getRiskDetails().getBasicRskId()));
					}*/

					if (!Utils.isEmpty(listedItemsCovers.getCoverageLimit())) {
						coverDetailsVO.getSumInsured().setSumInsured(listedItemsCovers.getCoverageLimit().doubleValue());
					}
					coverDetailsVO.getRiskCodes().setBasicRskCode(1);
					coverDetailsVO.getRiskCodes().setRiskCode(Integer.parseInt(riskCodes[0]));
					coverDetailsVO.getRiskCodes().setRiskType(Integer.parseInt(riskCodes[1]));
					coverDetailsVO.getRiskCodes().setRiskCat(Integer.parseInt(riskCodes[2]));
					/*if (optionalCovers.getCoverDesc().equals("Domestic Staff Cover")) {
						Integer size = createHomeQuoteRequest.getStaffDetails().getStaff().size();
						coverDetailsVO.getSumInsured().seteDesc(size.toString());
					}*/
					homeInsuranceVO.getCovers().add(coverDetailsVO);
				}
			}

		}
	}
	
	/**
	 * @Description : Maps Cover Details
	 * @param : homeInsurance CoverDetails
	 * @param : homeInsuranceVO
	 */
	private void mapOptionalCoversDetails(CreateHomeQuoteRequest createHomeQuoteRequest,HomeInsuranceVO homeInsuranceVO) {
		if (!Utils.isEmpty(createHomeQuoteRequest.getOptionalCovers())) {

			for (OptionalCovers optionalCovers : createHomeQuoteRequest.getOptionalCovers()) {
				String[] riskCodes= optionalCovers.getRiskMappingCode().split("-");
				if (optionalCovers.getCoverIncluded()) {

					CoverDetailsVO coverDetailsVO = new CoverDetailsVO();
					coverDetailsVO.setCoverCodes(new CoverVO());
					coverDetailsVO.setRiskCodes(new RiskVO());
					coverDetailsVO.setSumInsured(new SumInsuredVO());
					
					
					//String[] coverCodes = optionalCovers.getCoverMappingCode().split("-");
					coverDetailsVO.setCoverName(optionalCovers.getCoverDesc());
					coverDetailsVO.setIsCovered(AppConstants.STATUS_ON);
					coverDetailsVO.getCoverCodes().setCovCode((short) SvcConstants.oneVal);
					coverDetailsVO.getCoverCodes().setCovTypeCode((short) SvcConstants.zeroVal);
					coverDetailsVO.getCoverCodes().setCovSubTypeCode((short) SvcConstants.zeroVal);

					/*if (!Utils.isEmpty(optionalCovers.getRiskDetails().getRskId())) {
						coverDetailsVO.getRiskCodes()
								.setRskId(BigDecimal.valueOf(optionalCovers.getRiskDetails().getRskId()));
					}
					if (!Utils.isEmpty(optionalCovers.getRiskDetails().getBasicRskId())) {
						coverDetailsVO.getRiskCodes()
								.setBasicRskId(BigDecimal.valueOf(optionalCovers.getRiskDetails().getBasicRskId()));
					}*/

					if (!Utils.isEmpty(optionalCovers.getCoverageLimit())) {
						coverDetailsVO.getSumInsured().setSumInsured(optionalCovers.getCoverageLimit().doubleValue());
					}
					coverDetailsVO.getRiskCodes().setBasicRskCode(1);
					coverDetailsVO.getRiskCodes().setRiskCode(Integer.parseInt(riskCodes[0]));
					coverDetailsVO.getRiskCodes().setRiskType(Integer.parseInt(riskCodes[1]));
					coverDetailsVO.getRiskCodes().setRiskCat(Integer.parseInt(riskCodes[2]));
					/*if (optionalCovers.getCoverDesc().equals("Domestic Staff Cover")) {
						Integer size = createHomeQuoteRequest.getStaffDetails().getStaff().size();
						coverDetailsVO.getSumInsured().seteDesc(size.toString());
					}*/
					homeInsuranceVO.getCovers().add(coverDetailsVO);
				}
			}
		}
	}
	
	
	
	/**
	 * @Description : Maps Staff Details
	 * @param : staffDetails
	 * @param : homeInsuranceVO
	 */
	private void mapStaffDetails(CreateHomeQuoteRequest createHomeQuoteRequest,HomeInsuranceVO homeInsuranceVO) {
		List<StaffDetailsVO> staffDetailsVOList=new ArrayList<StaffDetailsVO>();
		
		for(Staff staff:createHomeQuoteRequest.getStaffDetails().getStaff()){
			StaffDetailsVO staffDetailsVO = new StaffDetailsVO();
			staffDetailsVO.setEmpName(staff.getStaffName());
			staffDetailsVO.setEmpDob(staff.getStaffDob());
			staffDetailsVOList.add(staffDetailsVO);
		}
		homeInsuranceVO.setStaffDetails(staffDetailsVOList);	
	}
	
	/**
	 * @description : Null Check Objects
	 * @param homeInsuranceVO
	 */
	private void nullCheckObjects(HomeInsuranceVO homeInsuranceVO){
		if(Utils.isEmpty(homeInsuranceVO.getGeneralInfo())){
			homeInsuranceVO.setGeneralInfo(new GeneralInfoVO());
		}
		if(Utils.isEmpty(homeInsuranceVO.getGeneralInfo().getInsured())){
			homeInsuranceVO.getGeneralInfo().setInsured(new InsuredVO());
		}
		if(Utils.isEmpty(homeInsuranceVO.getGeneralInfo().getInsured().getAddress()))
		{
			homeInsuranceVO.getGeneralInfo().getInsured().setAddress(new AddressVO());
		}
		if(Utils.isEmpty(homeInsuranceVO.getGeneralInfo().getSourceOfBus())){
			homeInsuranceVO.getGeneralInfo().setSourceOfBus(new SourceOfBusinessVO());
		}
		if(Utils.isEmpty(homeInsuranceVO.getBuildingDetails())){
			homeInsuranceVO.setBuildingDetails(new BuildingDetailsVO());
		}
		if(Utils.isEmpty(homeInsuranceVO.getBuildingDetails().getCoverCodes())){
			homeInsuranceVO.getBuildingDetails().setCoverCodes(new CoverVO());
		}
		if(Utils.isEmpty(homeInsuranceVO.getBuildingDetails().getRiskCodes())){
			homeInsuranceVO.getBuildingDetails().setRiskCodes(new RiskVO());
		}
		if(Utils.isEmpty(homeInsuranceVO.getScheme())){
			homeInsuranceVO.setScheme(new SchemeVO());
		}
		if(Utils.isEmpty(homeInsuranceVO.getBuildingDetails().getSumInsured())){
			homeInsuranceVO.getBuildingDetails().setSumInsured(new SumInsuredVO());
		}
		//CTS - 17.09.2020 - TFS#42533 - Retrieve quote in B2B showing errors - Starts

 if(Utils.isEmpty(homeInsuranceVO.getAuthenticationInfoVO())){

 homeInsuranceVO.setAuthenticationInfoVO(new AuthenticationInfoVO());

 }

 //CTS - 17.09.2020 - TFS#42533 - Retrieve quote in B2B showing errors - Ends
		
	}

}
