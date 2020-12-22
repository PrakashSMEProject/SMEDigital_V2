/**
 * 
 */
package com.rsaame.pas.b2c.ws.mapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.b2c.cmn.constants.AppConstants;
import com.rsaame.pas.b2c.ws.vo.ListedItems;
import com.rsaame.pas.b2c.ws.vo.MandatoryCovers;
import com.rsaame.pas.b2c.ws.vo.OptionalCovers;
import com.rsaame.pas.b2c.ws.vo.Staff;
import com.rsaame.pas.b2c.ws.vo.StaffDetails;
import com.rsaame.pas.b2c.ws.vo.TLLimit;
import com.rsaame.pas.b2c.ws.vo.UpdateHomeQuoteRequest;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.bus.AddressVO;
import com.rsaame.pas.vo.bus.BuildingDetailsVO;
import com.rsaame.pas.vo.bus.BusinessChannel;
import com.rsaame.pas.vo.bus.CoverDetailsVO;
import com.rsaame.pas.vo.bus.GeneralInfoVO;
import com.rsaame.pas.vo.bus.HomeInsuranceVO;
import com.rsaame.pas.vo.bus.InsuredVO;
import com.rsaame.pas.vo.bus.LOB;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.bus.SchemeVO;
import com.rsaame.pas.vo.bus.SourceOfBusinessVO;
import com.rsaame.pas.vo.bus.StaffDetailsVO;
import com.rsaame.pas.vo.bus.SumInsuredVO;
import com.rsaame.pas.vo.cmn.CommonVO;
import com.rsaame.pas.vo.cmn.CoverVO;
import com.rsaame.pas.vo.cmn.RiskVO;

/**
 * @author M1037404
 *
 */
public class HomeUpdateQuoteRequestMapper implements BaseRequestVOMapper{

	private final static Logger LOGGER = Logger.getLogger(UpdateHomeQuoteRequest.class);
	
	@Override
	public void mapRequestToVO(Object requestObj, Object valueObj) throws Exception {
		// TODO Auto-generated method stub
		
		LOGGER.info("Enters to HomeUpdateQuoteRequestMapper.mapRequestToVO, maps the request to homeInsuranceVO details..");
		if (requestObj instanceof UpdateHomeQuoteRequest
				&& valueObj instanceof HomeInsuranceVO) {
			UpdateHomeQuoteRequest updateHomeQuoteRequest = (UpdateHomeQuoteRequest) requestObj;
			HomeInsuranceVO homeInsuranceVO = (HomeInsuranceVO) valueObj;

			//Objects Null Check
			initialiseObjects(homeInsuranceVO);
			
			// Quote Details
			mapQuoteDetails(updateHomeQuoteRequest, homeInsuranceVO);
			
			//Customer details
			mapGeneralInsuranceDetails(updateHomeQuoteRequest,homeInsuranceVO);
			
			//Transaction Details
			mapTransactionDetails(updateHomeQuoteRequest,homeInsuranceVO);
			
			//Fees and Discount mapping 
			mapFeesAndTaxes(updateHomeQuoteRequest,homeInsuranceVO);
			
			//Partner Details
			mapPartnerDetails(updateHomeQuoteRequest,homeInsuranceVO);
			
			//
			mapMandatoryCovers(updateHomeQuoteRequest,homeInsuranceVO);
			
			//Building Details
			mapBuildingDetails(updateHomeQuoteRequest, homeInsuranceVO);
			
			//Listed Items in Mandatory Covers
			mapListedItems(updateHomeQuoteRequest, homeInsuranceVO);
			
			//Cover Details
			mapOptionalCoversDetails(updateHomeQuoteRequest, homeInsuranceVO);
			
			//Staff Details
			//mapStaffDetails(updateHomeQuoteRequest, homeInsuranceVO);
			
			LOGGER.info("Successfully mapped to HomeUpdateQuoteRequestMapper object to HomeInsuranceVO details..");
		} else {
			throw new Exception("Unexpected request or value object");
		}

		
	}
	
	private void mapMandatoryCovers(UpdateHomeQuoteRequest updateHomeQuoteRequest, HomeInsuranceVO homeInsuranceVO) {
		// TODO Auto-generated method stub
		if(!Utils.isEmpty(updateHomeQuoteRequest.getMandatoryCovers())) {
			
			for (MandatoryCovers mandatoryCovers : updateHomeQuoteRequest.getMandatoryCovers()) {
				String[] riskCodes= mandatoryCovers.getRiskMappingCode().split("-");
				if (mandatoryCovers.getCoverIncluded() && Integer.parseInt(riskCodes[1]) ==SvcConstants.HOME_BUILDING_RISK_TYPE
						&& updateHomeQuoteRequest.getBuildingDetails().getOwnershipStatus()==SvcConstants.oneVal) {
					
					homeInsuranceVO.getBuildingDetails().setRiskCodes(new RiskVO());
					homeInsuranceVO.getBuildingDetails().setSumInsured(new SumInsuredVO());
					homeInsuranceVO.getBuildingDetails().setCoverCodes(new CoverVO());
					
					homeInsuranceVO.getBuildingDetails().getSumInsured().setSumInsured(mandatoryCovers.getCoverageLimit().doubleValue());
					
					//String[] coverCodes= mandatoryCovers.getCoverMappingCode().split("-");
					
					homeInsuranceVO.getBuildingDetails().getCoverCodes().setCovCode((short) SvcConstants.oneVal);
					homeInsuranceVO.getBuildingDetails().getCoverCodes().setCovTypeCode((short) SvcConstants.zeroVal);   
					homeInsuranceVO.getBuildingDetails().getCoverCodes().setCovSubTypeCode((short) SvcConstants.zeroVal);
					
					homeInsuranceVO.getBuildingDetails().getRiskCodes().setRskId(Utils.isEmpty(mandatoryCovers.getRiskDetails().getRskId()) ? null : BigDecimal.valueOf(mandatoryCovers.getRiskDetails().getRskId()));
					homeInsuranceVO.getBuildingDetails().getRiskCodes().setBasicRskId(Utils.isEmpty(mandatoryCovers.getRiskDetails().getBasicRskId()) ? null : BigDecimal.valueOf(mandatoryCovers.getRiskDetails().getBasicRskId()));
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
					
					//String[] coverCodes= mandatoryCovers.getCoverMappingCode().split("-");
					
					coverDetailsVO.getCoverCodes().setCovCode((short) SvcConstants.oneVal);
					coverDetailsVO.getCoverCodes().setCovTypeCode((short) SvcConstants.zeroVal);
					coverDetailsVO.getCoverCodes().setCovSubTypeCode((short) SvcConstants.zeroVal);

					coverDetailsVO.getRiskCodes().setRskId(Utils.isEmpty(mandatoryCovers.getRiskDetails().getRskId()) ? null : BigDecimal.valueOf(mandatoryCovers.getRiskDetails().getRskId()));
					coverDetailsVO.getRiskCodes().setBasicRskId(Utils.isEmpty(mandatoryCovers.getRiskDetails().getBasicRskId()) ? null : BigDecimal.valueOf(mandatoryCovers.getRiskDetails().getBasicRskId()));
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
	private void mapPartnerDetails(UpdateHomeQuoteRequest updateHomeQuoteRequest, HomeInsuranceVO homeInsuranceVO) {
		// TODO Auto-generated method stub
		
		if(!Utils.isEmpty(updateHomeQuoteRequest.getPartnerDetails())) {
			
			if(Utils.isEmpty(updateHomeQuoteRequest.getPartnerDetails().getCommissionPercentage())) {
				homeInsuranceVO.setCommission(null);
			}else{
				homeInsuranceVO.setCommission(updateHomeQuoteRequest.getPartnerDetails().getCommissionPercentage().doubleValue());
			}
		}
	}
	private void mapFeesAndTaxes(UpdateHomeQuoteRequest updateHomeQuoteRequest, HomeInsuranceVO homeInsuranceVO) {
		// TODO Auto-generated method stub
		
		if(!Utils.isEmpty(updateHomeQuoteRequest.getFeesAndTaxes()) && !Utils.isEmpty(homeInsuranceVO.getPremiumVO())) {
			
			if(!Utils.isEmpty(updateHomeQuoteRequest.getFeesAndTaxes().getLoadingOrDiscountPercent())) {
				homeInsuranceVO.getPremiumVO().setDiscOrLoadPerc(updateHomeQuoteRequest.getFeesAndTaxes().getLoadingOrDiscountPercent().doubleValue());
			}
			if(!Utils.isEmpty(updateHomeQuoteRequest.getFeesAndTaxes().getLoadingOrDiscountAmount())) {
				homeInsuranceVO.getPremiumVO().setDiscOrLoadAmt(updateHomeQuoteRequest.getFeesAndTaxes().getLoadingOrDiscountAmount());
			}
			if(!Utils.isEmpty(updateHomeQuoteRequest.getFeesAndTaxes().getPromoCodeDiscountPercent())) {
				homeInsuranceVO.getPremiumVO().setPromoDiscPerc(updateHomeQuoteRequest.getFeesAndTaxes().getPromoCodeDiscountPercent().doubleValue());
			}
			if(!Utils.isEmpty(updateHomeQuoteRequest.getFeesAndTaxes().getPromoCodeDiscountAmount())) {
//				mapping is remaining for promo code discount amount
			}
			if(!Utils.isEmpty(updateHomeQuoteRequest.getFeesAndTaxes().getGovtTaxPercent())) {
				homeInsuranceVO.getPremiumVO().setGovtTax(updateHomeQuoteRequest.getFeesAndTaxes().getGovtTaxPercent().doubleValue());
			}
			if(!Utils.isEmpty(updateHomeQuoteRequest.getFeesAndTaxes().getVatRatePercent())) {
				homeInsuranceVO.getPremiumVO().setVatTaxPerc(updateHomeQuoteRequest.getFeesAndTaxes().getVatRatePercent().doubleValue());
			}
			if(!Utils.isEmpty(updateHomeQuoteRequest.getFeesAndTaxes().getVatAmount())) {
				homeInsuranceVO.getPremiumVO().setVatTax(updateHomeQuoteRequest.getFeesAndTaxes().getVatAmount().doubleValue());
			}
			if(!Utils.isEmpty(updateHomeQuoteRequest.getFeesAndTaxes().getPolicyFees())) {
				homeInsuranceVO.getPremiumVO().setPolicyFees(updateHomeQuoteRequest.getFeesAndTaxes().getPolicyFees().doubleValue());
			}
		}
		
		
	}
	/**
	 * @Description : Maps Home InsuranceDetails
	 * @param homeInsuranceDetails
	 * @param homeInsuranceVO
	 */
	private void mapGeneralInsuranceDetails(UpdateHomeQuoteRequest updateHomeQuoteRequest,HomeInsuranceVO homeInsuranceVO) {
		
		if(!Utils.isEmpty(homeInsuranceVO.getGeneralInfo().getInsured()) && !Utils.isEmpty(updateHomeQuoteRequest.getCustomerDetails())
				&& !Utils.isEmpty(homeInsuranceVO.getGeneralInfo().getInsured().getAddress())){
			
			homeInsuranceVO.getGeneralInfo().getInsured().setEmailId(updateHomeQuoteRequest.getCustomerDetails().getEmailId());
			homeInsuranceVO.getGeneralInfo().getInsured().setMobileNo(updateHomeQuoteRequest.getCustomerDetails().getMobileNo().toString());
			homeInsuranceVO.getGeneralInfo().getInsured().setFirstName(updateHomeQuoteRequest.getCustomerDetails().getFirstName());
			homeInsuranceVO.getGeneralInfo().getInsured().setInsuredCode(updateHomeQuoteRequest.getCustomerDetails().getInsuredId());
			homeInsuranceVO.getGeneralInfo().getInsured().setLastName(updateHomeQuoteRequest.getCustomerDetails().getLastName());
			homeInsuranceVO.getGeneralInfo().getInsured().setCity(updateHomeQuoteRequest.getCustomerDetails().getCity());
			
			if(!Utils.isEmpty(updateHomeQuoteRequest.getCustomerDetails().getNationality())) {
				homeInsuranceVO.getGeneralInfo().getInsured().setNationality(updateHomeQuoteRequest.getCustomerDetails().getNationality());
			}
			
			if(!Utils.isEmpty(updateHomeQuoteRequest.getCustomerDetails().getVatRegNo())) {
				homeInsuranceVO.getGeneralInfo().getInsured().setVatRegNo(updateHomeQuoteRequest.getCustomerDetails().getVatRegNo());
			}
			if(!Utils.isEmpty(updateHomeQuoteRequest.getCustomerDetails().getRewardProgrammeType())) {
				homeInsuranceVO.getGeneralInfo().getInsured().setRoyaltyType(updateHomeQuoteRequest.getCustomerDetails().getRewardProgrammeType());
			}
			
			if(!Utils.isEmpty(updateHomeQuoteRequest.getCustomerDetails().getRewardCardNumber())) {
				homeInsuranceVO.getGeneralInfo().getInsured().setGuestCardNo((updateHomeQuoteRequest.getCustomerDetails().getRewardCardNumber()));
			}
			// Mapping Address Object values
			homeInsuranceVO.getGeneralInfo().getInsured().getAddress().setEmirates(updateHomeQuoteRequest.getCustomerDetails().getCity());
			//homeInsuranceVO.getGeneralInfo().getInsured().getAddress().setPoBox(updateHomeQuoteRequest.getCustomerDetails().getPoBox());
			
			if(!Utils.isEmpty(updateHomeQuoteRequest.getCustomerDetails().getNationalID())) {
				homeInsuranceVO.getGeneralInfo().getInsured().getAddress().setCountry(Integer.parseInt(updateHomeQuoteRequest.getCustomerDetails().getNationalID()));
			}
			
			
		}
		
	}	
	
	/**
	 * @Description : Maps Transaction Details
	 * @param transactionDetails
	 * @param homeInsuranceVO
	 */
	private void mapTransactionDetails(UpdateHomeQuoteRequest updateHomeQuoteRequest,HomeInsuranceVO homeInsuranceVO) {
		Object[] result = (Object[]) DAOUtils.fetchDefaultValues(updateHomeQuoteRequest.getPmmId());
		if(!Utils.isEmpty(homeInsuranceVO.getScheme()) && !Utils.isEmpty(updateHomeQuoteRequest.getTransactionDetails())
				&&	!Utils.isEmpty(homeInsuranceVO.getGeneralInfo().getSourceOfBus())){
			
			homeInsuranceVO.setClassCode(((BigDecimal) result[0]).intValue());
			homeInsuranceVO.setPolicyType(((BigDecimal) result[1]).intValue());
			if(result[2]==null) {
				homeInsuranceVO.setPolicyTerm(null);
			}else {
				homeInsuranceVO.setPolicyTerm(((BigDecimal) result[2]).intValue());
			}
			if(Utils.isEmpty(updateHomeQuoteRequest.getTransactionDetails().getEffectiveDate())) {
				homeInsuranceVO.getScheme().setEffDate(null);
			}else {
				homeInsuranceVO.getScheme().setEffDate(updateHomeQuoteRequest.getTransactionDetails().getEffectiveDate());
			}
			if(Utils.isEmpty(updateHomeQuoteRequest.getTransactionDetails().getExpiryDate())) {
				homeInsuranceVO.getScheme().setExpiryDate(null);
			}else {
				homeInsuranceVO.getScheme().setExpiryDate(updateHomeQuoteRequest.getTransactionDetails().getExpiryDate());
			}						
			homeInsuranceVO.getScheme().setSchemeCode(((BigDecimal) result[3]).intValue());
			homeInsuranceVO.getScheme().setTariffCode(((BigDecimal) result[4]).intValue());
			if(result[5]==null) {
				homeInsuranceVO.getGeneralInfo().getSourceOfBus().setDistributionChannel(null);
			}else {
				homeInsuranceVO.getGeneralInfo().getSourceOfBus().setDistributionChannel(((BigDecimal) result[5]).intValue());
			}
			
			if (updateHomeQuoteRequest.getTransactionDetails().getDirectSubAgent() == null){
				homeInsuranceVO.getGeneralInfo().getSourceOfBus().setDirectSubAgent(null);
			}
			else{
				homeInsuranceVO.getGeneralInfo().getSourceOfBus().setDirectSubAgent(updateHomeQuoteRequest.getTransactionDetails().getDirectSubAgent().longValue());
			}
			
			homeInsuranceVO.getGeneralInfo().getSourceOfBus().setPromoCode(updateHomeQuoteRequest.getTransactionDetails().getPromocode());
			homeInsuranceVO.getGeneralInfo().getSourceOfBus().setCustomerSource(((BigDecimal) result[6]).toString()); 
			//homeInsuranceVO "PartnerTrnReferenceNumber": null
			
			
		}
		
	}
	
	/**
	 * @Description : Maps Building Details Section
	 * @param : buildingDetails
	 * @param : homeInsuranceVO
	 */
	private void mapBuildingDetails(UpdateHomeQuoteRequest updateHomeQuoteRequest,HomeInsuranceVO homeInsuranceVO) {
		
		if(!Utils.isEmpty(homeInsuranceVO.getBuildingDetails()) && !Utils.isEmpty(updateHomeQuoteRequest.getBuildingDetails())){
			homeInsuranceVO.getBuildingDetails().setOwnershipStatus(updateHomeQuoteRequest.getBuildingDetails().getOwnershipStatus().shortValue());
			Integer emiratesCode = SvcUtils.getLookUpCode( "CITY", "ALL", "ALL", updateHomeQuoteRequest.getBuildingDetails().getEmirate());
			homeInsuranceVO.getBuildingDetails().setEmirates(Utils.isEmpty(emiratesCode.toString()) ? null : emiratesCode.toString());
			homeInsuranceVO.getBuildingDetails().setArea(Utils.isEmpty(updateHomeQuoteRequest.getBuildingDetails().getArea()) ? null : Utils.getSingleValueAppConfig("AREA_OTHERS"));
			if(!Utils.isEmpty(updateHomeQuoteRequest.getBuildingDetails().getAreaOthers())) {
				homeInsuranceVO.getBuildingDetails().setOtherDetails(updateHomeQuoteRequest.getBuildingDetails().getArea().toString());
			}
			homeInsuranceVO.getBuildingDetails().setTypeOfProperty(Utils.isEmpty(updateHomeQuoteRequest.getBuildingDetails().getPropertyType()) ? null : updateHomeQuoteRequest.getBuildingDetails().getPropertyType().shortValue());
			homeInsuranceVO.getBuildingDetails().setBuildingname(Utils.isEmpty(updateHomeQuoteRequest.getBuildingDetails().getBuildingName()) ? null : updateHomeQuoteRequest.getBuildingDetails().getBuildingName());
			homeInsuranceVO.getBuildingDetails().setFlatVillaNo(Utils.isEmpty(updateHomeQuoteRequest.getBuildingDetails().getFlatVillaNo()) ? null : updateHomeQuoteRequest.getBuildingDetails().getFlatVillaNo());
			homeInsuranceVO.getGeneralInfo().getInsured().getAddress().setPoBox(Utils.isEmpty(updateHomeQuoteRequest.getBuildingDetails().getPoBox()) ? null : updateHomeQuoteRequest.getBuildingDetails().getPoBox());
			if(!Utils.isEmpty(updateHomeQuoteRequest.getBuildingDetails().getMortgageeCode())) {
				homeInsuranceVO.getBuildingDetails().setMortgageeName(updateHomeQuoteRequest.getBuildingDetails().getMortgageeCode().toString());
			}
			homeInsuranceVO.getBuildingDetails().setGeoAreaCode(Utils.isEmpty(updateHomeQuoteRequest.getBuildingDetails().getArea()) ? null : Short.valueOf(Utils.getSingleValueAppConfig("AREA_OTHERS")));
			
			homeInsuranceVO.getBuildingDetails().setTotalNoFloors(Utils.isEmpty(updateHomeQuoteRequest.getBuildingDetails().getTotalFloors()) ? null : updateHomeQuoteRequest.getBuildingDetails().getTotalFloors());
			homeInsuranceVO.getBuildingDetails().setTotalNoRooms(Utils.isEmpty(updateHomeQuoteRequest.getBuildingDetails().getTotalRooms()) ? null :updateHomeQuoteRequest.getBuildingDetails().getTotalRooms());
			homeInsuranceVO.getBuildingDetails().setLatitude(Utils.isEmpty(updateHomeQuoteRequest.getBuildingDetails().getLatitude()) ? null :updateHomeQuoteRequest.getBuildingDetails().getLatitude());
			homeInsuranceVO.getBuildingDetails().setLongitude(Utils.isEmpty(updateHomeQuoteRequest.getBuildingDetails().getLongitude()) ? null :updateHomeQuoteRequest.getBuildingDetails().getLongitude());
			if(updateHomeQuoteRequest.getBuildingDetails().getInfoMapStatus() == null && Utils.isEmpty(updateHomeQuoteRequest.getBuildingDetails().getGrl())) {
				homeInsuranceVO.getBuildingDetails().setInforMapStatus("0");
			}else if(updateHomeQuoteRequest.getBuildingDetails().getInfoMapStatus() == null && !Utils.isEmpty(updateHomeQuoteRequest.getBuildingDetails().getGrl())) {
				homeInsuranceVO.getBuildingDetails().setInforMapStatus("1");
			}
			else {
				homeInsuranceVO.getBuildingDetails().setInforMapStatus(updateHomeQuoteRequest.getBuildingDetails().getInfoMapStatus());
			}
			// <FLAT#> , <Building Name>, <Street>, <Area>, <Zone>, <City>, <Emirate>, “GRL-” <GRL>
			String flatVilla = updateHomeQuoteRequest.getBuildingDetails().getFlatVillaNo();
			String buildingName = updateHomeQuoteRequest.getBuildingDetails().getBuildingName();
			String street = updateHomeQuoteRequest.getBuildingDetails().getStreet();
			String area = updateHomeQuoteRequest.getBuildingDetails().getArea();
			String zone = updateHomeQuoteRequest.getBuildingDetails().getZone();
			String emirates = updateHomeQuoteRequest.getBuildingDetails().getEmirate();
			String grl = updateHomeQuoteRequest.getBuildingDetails().getGrl();
			String address = buildingName+","+street+","+area+","+zone+","+emirates+",GRL-"+grl;
			if(Utils.isEmpty(updateHomeQuoteRequest.getBuildingDetails().getFlatVillaNo())) {
				homeInsuranceVO.getBuildingDetails().setAddress(address);
			}else {
				address = "FLAT#"+flatVilla+","+ address;
				homeInsuranceVO.getBuildingDetails().setAddress(address);
			}
			LOGGER.info("Address : "+address);
		}
	
		
	}

	/**
	 * @Description : Maps Cover Details
	 * @param : homeInsurance CoverDetails
	 * @param : homeInsuranceVO
	 */
	private void mapOptionalCoversDetails(UpdateHomeQuoteRequest updateHomeQuoteRequest,HomeInsuranceVO homeInsuranceVO) {

		if(!Utils.isEmpty(updateHomeQuoteRequest.getOptionalCovers())) {
				
				for (OptionalCovers optionalCovers : updateHomeQuoteRequest.getOptionalCovers()) {
					
					if(optionalCovers.getCoverIncluded()) {
					
						CoverDetailsVO coverDetailsVO = new CoverDetailsVO();
						coverDetailsVO.setCoverCodes(new CoverVO());
						coverDetailsVO.setRiskCodes(new RiskVO());
						coverDetailsVO.setSumInsured(new SumInsuredVO());
						coverDetailsVO.setIsCovered(AppConstants.STATUS_ON);
						coverDetailsVO.setCoverName(optionalCovers.getCoverDesc());
						
						String[] coverCodes= optionalCovers.getCoverMappingCode().split("-");
						
						coverDetailsVO.getCoverCodes().setCovCode(Short.parseShort(coverCodes[0]));
						coverDetailsVO.getCoverCodes().setCovTypeCode(Short.parseShort(coverCodes[1]));
						coverDetailsVO.getCoverCodes().setCovSubTypeCode(Short.parseShort(coverCodes[2]));
						
						coverDetailsVO.getRiskCodes().setRskId(Utils.isEmpty(optionalCovers.getRiskDetails().getRskId()) ? null : BigDecimal.valueOf(optionalCovers.getRiskDetails().getRskId()));
						coverDetailsVO.getRiskCodes().setBasicRskId(Utils.isEmpty(optionalCovers.getRiskDetails().getBasicRskId()) ? null : BigDecimal.valueOf(optionalCovers.getRiskDetails().getBasicRskId()));
						coverDetailsVO.getRiskCodes().setBasicRskCode(1);
						coverDetailsVO.getRiskCodes().setRiskCode(2);
						coverDetailsVO.getRiskCodes().setRiskType(31);
						coverDetailsVO.getRiskCodes().setRiskCat(1);
						//CTS - 24.09.2020 - Home digital UAT fix -Domestic Staff Cover not populating if quote is retieved and retrival response is sent into update
						boolean isTLL = true;
						if(coverDetailsVO.getCoverCodes().getCovCode() == SvcConstants.EL_COVER_CODE){
							isTLL = false;
						}else if(coverDetailsVO.getCoverCodes().getCovCode() == SvcConstants.LOD_COVER_CODE){
							isTLL = false;
						}
						if(!Utils.isEmpty(optionalCovers.getCoverageLimit()) && isTLL) {
							coverDetailsVO.getSumInsured().setSumInsured(optionalCovers.getCoverageLimit().doubleValue());
						}						
						//CTS - 24.09.2020 - Home digital UAT fix -Domestic Staff Cover not populating if quote is retieved and retrival response is sent into update
						if(Integer.parseInt(coverCodes[0])==3){
							mapStaffDetails(homeInsuranceVO,optionalCovers,coverCodes);
						} else if(Integer.parseInt(coverCodes[0])==4) {
							mapTLLimits(homeInsuranceVO,optionalCovers,coverDetailsVO,coverCodes);
						}
						homeInsuranceVO.getCovers().add(coverDetailsVO);
						
						// adding staff size in SumInsured eDesc
						String[] covers = Utils.getMultiValueAppConfig("HOME_ADDTL_COVERS");
						if(coverDetailsVO.getCoverCodes().getCovCode()==Short.parseShort(covers[1])) {
							coverDetailsVO.getSumInsured().seteDesc(""+optionalCovers.getStaffDetails().getStaff().size());
						}
					}
				}
		}

	}
	
	/**
	 * @Description : Maps Listed Cover Details
	 * @param : homeInsurance CoverDetails
	 * @param : homeInsuranceVO
	 */
	private void mapListedItems(UpdateHomeQuoteRequest updateHomeQuoteRequest, HomeInsuranceVO homeInsuranceVO) {
		if (!Utils.isEmpty(updateHomeQuoteRequest.getListedItems())) {

			for (ListedItems listedItemsCovers : updateHomeQuoteRequest.getListedItems()) {
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

					if (!Utils.isEmpty(listedItemsCovers.getCoverageLimit())) {
						coverDetailsVO.getSumInsured().setSumInsured(listedItemsCovers.getCoverageLimit().doubleValue());
					}
					coverDetailsVO.getRiskCodes().setRskId(Utils.isEmpty(listedItemsCovers.getRiskDetails().getRskId()) ? null : BigDecimal.valueOf(listedItemsCovers.getRiskDetails().getRskId()));
					coverDetailsVO.getRiskCodes().setBasicRskId(Utils.isEmpty(listedItemsCovers.getRiskDetails().getBasicRskId()) ? null : BigDecimal.valueOf(listedItemsCovers.getRiskDetails().getBasicRskId()));
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
	 * @param optionalCovers 
	 * @param coverCodes 
	 * @Description : Maps Staff Details
	 * @param : staffDetails
	 * @param : homeInsuranceVO
	 */
	private void mapStaffDetails(HomeInsuranceVO homeInsuranceVO, OptionalCovers optionalCovers, String[] coverCodes) {
		
		if(!Utils.isEmpty(optionalCovers.getStaffDetails()) && Integer.parseInt(coverCodes[0])==3) {
			
			List<StaffDetailsVO> staffDetailsVOs = new ArrayList<StaffDetailsVO>();
			StaffDetails staffDetails = optionalCovers.getStaffDetails();
			if(!Utils.isEmpty(optionalCovers.getStaffDetails().getStaff())) {
				for (Staff staff : staffDetails.getStaff()) {
					StaffDetailsVO staffDetailsVO = new StaffDetailsVO();
					staffDetailsVO.setEmpName(staff.getStaffName());
					staffDetailsVO.setEmpDob(staff.getStaffDob());
					if(!Utils.isEmpty(staff.getStaffId())) {
						staffDetailsVO.setEmpId(staff.getStaffId());
					}
					staffDetailsVOs.add(staffDetailsVO);
				}
			}
			homeInsuranceVO.setStaffDetails(staffDetailsVOs);
		}
	}
	
	private void mapTLLimits(HomeInsuranceVO homeInsuranceVO, OptionalCovers optionalCovers, CoverDetailsVO coverDetailsVO, String[] coverCodes) {
		if(!Utils.isEmpty(optionalCovers.getTllLimit()) && optionalCovers.getCoverIncluded().equals(true) && Integer.parseInt(coverCodes[0])==4) {
			for (TLLimit tlLimit : optionalCovers.getTllLimit()) {
				if(tlLimit.getSelected().equals(true)) {
					coverDetailsVO.setCoverName(optionalCovers.getCoverDesc());
					if(tlLimit.getSelected().equals(true)) {
						coverDetailsVO.setIsCovered(AppConstants.STATUS_ON);
					}
					coverDetailsVO.getSumInsured().setSumInsured(tlLimit.getCoverageLimit().doubleValue());
				}
			}
		}
	}
	
	private void mapQuoteDetails(UpdateHomeQuoteRequest updateHomeQuoteRequest,HomeInsuranceVO homeInsuranceVO) {
		
		if(!Utils.isEmpty(homeInsuranceVO.getCommonVO())){
			homeInsuranceVO.getCommonVO().setQuoteNo(updateHomeQuoteRequest.getQuotationNo().longValue());
			homeInsuranceVO.getCommonVO().setStatus(updateHomeQuoteRequest.getQuoteStatus());
			homeInsuranceVO.getCommonVO().setEndtId(updateHomeQuoteRequest.getEndtId());
			homeInsuranceVO.getCommonVO().setEndtNo(updateHomeQuoteRequest.getEndtNo());
			homeInsuranceVO.getCommonVO().setPolicyId(updateHomeQuoteRequest.getPolicyId());
			homeInsuranceVO.getCommonVO().setLocCode(AppConstants.B2C_DEFAULT_DEPLOYED_LOCATION);
			homeInsuranceVO.getCommonVO().setChannel(BusinessChannel.B2C);
			homeInsuranceVO.getCommonVO().setLob( LOB.HOME );
			homeInsuranceVO.getCommonVO().setAppFlow(Flow.EDIT_QUO);
		}
		
	}
	
	/**
	 * @description : Null Check Objects
	 * @param homeInsuranceVO
	 */
	private void initialiseObjects(HomeInsuranceVO homeInsuranceVO){
		if(Utils.isEmpty(homeInsuranceVO.getScheme())) {
			homeInsuranceVO.setScheme(new SchemeVO());
		}
		if(Utils.isEmpty(homeInsuranceVO.getCommonVO())) {
			homeInsuranceVO.setCommonVO(new CommonVO());
		}
		if(Utils.isEmpty(homeInsuranceVO.getBuildingDetails())) {
			homeInsuranceVO.setBuildingDetails(new BuildingDetailsVO());
		}
		if(Utils.isEmpty(homeInsuranceVO.getBuildingDetails().getRiskCodes())) {
			homeInsuranceVO.getBuildingDetails().setRiskCodes(new RiskVO());;
		}
		if(Utils.isEmpty(homeInsuranceVO.getBuildingDetails().getSumInsured())) {
			homeInsuranceVO.getBuildingDetails().setSumInsured(new SumInsuredVO());
		}
		if(Utils.isEmpty(homeInsuranceVO.getBuildingDetails().getCoverCodes())) {
			homeInsuranceVO.getBuildingDetails().setCoverCodes(new CoverVO());;
		}
		if(Utils.isEmpty(homeInsuranceVO.getCovers())) {
			homeInsuranceVO.setCovers(new ArrayList<CoverDetailsVO>());
		}
		if(Utils.isEmpty(homeInsuranceVO.getGeneralInfo())) {
			homeInsuranceVO.setGeneralInfo(new GeneralInfoVO());
		}
		if(Utils.isEmpty(homeInsuranceVO.getGeneralInfo().getInsured())) {
			homeInsuranceVO.getGeneralInfo().setInsured(new InsuredVO());
		}
		if(Utils.isEmpty(homeInsuranceVO.getGeneralInfo().getInsured().getAddress())) {
			homeInsuranceVO.getGeneralInfo().getInsured().setAddress(new AddressVO());;
		}
		if(Utils.isEmpty(homeInsuranceVO.getGeneralInfo().getSourceOfBus())) {
			homeInsuranceVO.getGeneralInfo().setSourceOfBus(new SourceOfBusinessVO());
		}
		if(Utils.isEmpty(homeInsuranceVO.getStaffDetails())) {
			homeInsuranceVO.setStaffDetails(new ArrayList<StaffDetailsVO>());
		}
		if(Utils.isEmpty(homeInsuranceVO.getPremiumVO())) {
			homeInsuranceVO.setPremiumVO(new PremiumVO());
		}
	}
}
