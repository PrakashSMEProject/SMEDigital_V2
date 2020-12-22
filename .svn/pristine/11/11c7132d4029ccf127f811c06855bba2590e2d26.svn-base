/**
 * 
 */
package com.rsaame.pas.b2c.ws.mapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.b2c.cmn.constants.AppConstants;
import com.rsaame.pas.b2c.ws.utilities.WebServiceUtils;
import com.rsaame.pas.b2c.ws.vo.BuildingDetails;
import com.rsaame.pas.b2c.ws.vo.CreateHomeQuoteResponse;
import com.rsaame.pas.b2c.ws.vo.CustomerDetails;
import com.rsaame.pas.b2c.ws.vo.Extras;
import com.rsaame.pas.b2c.ws.vo.FeesAndTaxes;
import com.rsaame.pas.b2c.ws.vo.ListedItems;
import com.rsaame.pas.b2c.ws.vo.MandatoryCovers;
import com.rsaame.pas.b2c.ws.vo.OptionalCovers;
import com.rsaame.pas.b2c.ws.vo.PartnerDetails;
import com.rsaame.pas.b2c.ws.vo.RiskDetails;
import com.rsaame.pas.b2c.ws.vo.Staff;
import com.rsaame.pas.b2c.ws.vo.StaffDetails;
import com.rsaame.pas.b2c.ws.vo.TransactionDetails;
import com.rsaame.pas.b2c.ws.vo.UnderWritingQuestions;
import com.rsaame.pas.dao.model.TMasPolicyRating;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.vo.bus.CoverDetailsVO;
import com.rsaame.pas.vo.bus.HomeInsuranceVO;
import com.rsaame.pas.vo.bus.StaffDetailsVO;

/**
 * @author M1044786
 *
 */
public class HomeCreateQuoteResponseMapper implements BaseResponseVOMapper{

	private final static Logger LOGGER = Logger.getLogger(HomeCreateQuoteResponseMapper.class);
	@Override
	public void mapVOToResponse(Object valueObj, Object responseObj) throws Exception {
		LOGGER.info("Enters to HomeCreateQuoteResponseMapper.mapRequestToVO, maps the homeInsuranceVO details to response...");
		if (responseObj instanceof CreateHomeQuoteResponse
				&& valueObj instanceof HomeInsuranceVO) {
			
			CreateHomeQuoteResponse createHomeQuoteResponse = (CreateHomeQuoteResponse) responseObj;
			HomeInsuranceVO homeInsuranceVO = (HomeInsuranceVO) valueObj;
			
			//Quote Details
			mapQuoteDetails(createHomeQuoteResponse, homeInsuranceVO);
			
			//Customer details
			mapGeneralInsuranceDetails(createHomeQuoteResponse, homeInsuranceVO);
			
			//Transaction Details
			mapTransactionDetails(createHomeQuoteResponse,homeInsuranceVO);
			
			//Partner Details
			//mapPartnerDetails(createHomeQuoteResponse,homeInsuranceVO);
			
			//Cover Details
			mapCoversDetails(createHomeQuoteResponse, homeInsuranceVO);
			
			//Optional Cover Details
			mapOptionalCoversDetails(createHomeQuoteResponse, homeInsuranceVO);
			
			//Building Details
			mapBuildingDetails(createHomeQuoteResponse, homeInsuranceVO);
			
			//FeesAndTaxes Details
			mapFeesAndTaxes(createHomeQuoteResponse,homeInsuranceVO);
			
			//Staff Details
			//mapStaffDetails(createHomeQuoteResponse, homeInsuranceVO);
			
			//Cover Details
			//mapUnderWritingQuestions(createHomeQuoteResponse, homeInsuranceVO);
		    /********** Code Ends for Response*********/
	        LOGGER.info("Successfully mapped to HomeInsuranceVO details to response object..");
		} else {
			throw new Exception("Unexpected request or value object");
		}
	}
	
	/**
	 * @Description : Maps Quote Details
	 * @param homeInsuranceDetails
	 * @param homeInsuranceVO
	 */
	private void mapQuoteDetails(CreateHomeQuoteResponse createHomeQuoteResponse,HomeInsuranceVO homeInsuranceVO) {
		
		createHomeQuoteResponse.setQuotationNo(homeInsuranceVO.getCommonVO().getQuoteNo().intValue());
		createHomeQuoteResponse.setEndtId(homeInsuranceVO.getCommonVO().getEndtId());
		createHomeQuoteResponse.setPolicyId(homeInsuranceVO.getCommonVO().getPolicyId());
		createHomeQuoteResponse.setEndtNo(homeInsuranceVO.getCommonVO().getEndtNo());
		if(!Utils.isEmpty(homeInsuranceVO.getScheme())){
        	Date effDate;
        	effDate = homeInsuranceVO.getScheme().getEffDate();
        	if(!Utils.isEmpty( effDate )){
				Calendar effCalendar = Calendar.getInstance();
				effCalendar.setTime( effDate );
				
				//Quote should to be valid exactly for 30 days from the date of Issue.
				effCalendar.add( Calendar.DATE, AppConstants.QUOTE_VALID_DAYS - 1 );
				effCalendar.add( Calendar.MONTH,0);
				createHomeQuoteResponse.setQuoteValidTill(effCalendar.getTime());
        	}
        }
		createHomeQuoteResponse.setQuoteStatus(homeInsuranceVO.getCommonVO().getStatus());
	//07.09.2020 CTS  TFS#42738 - Incorrect premium on B2C for minimum premium case - start
		if(!Utils.isEmpty(homeInsuranceVO.getPremiumVO().getPremiumAmtActual())) {
				
				if(!Utils.isEmpty(homeInsuranceVO.getPremiumVO().getMinPremiumApplied())) {
					createHomeQuoteResponse.setFinalPremium(new BigDecimal(homeInsuranceVO.getPremiumVO().getPremiumAmtActual()).add(homeInsuranceVO.getPremiumVO().getMinPremiumApplied()));
				}
				else
					createHomeQuoteResponse.setFinalPremium(new BigDecimal(homeInsuranceVO.getPremiumVO().getPremiumAmtActual()));
			}
	//07.09.2020 CTS  TFS#42738 - ENDS
		//set scale for SAT issues
		createHomeQuoteResponse.setPremiumPayable(new BigDecimal(homeInsuranceVO.getPremiumVO().getPremiumAmt()).setScale(2,RoundingMode.HALF_UP));
		//set scale for SAT issues
		createHomeQuoteResponse.setCurrencyType("AED");
		if(Utils.isEmpty(createHomeQuoteResponse.getExtras()))
		{
		  	createHomeQuoteResponse.setExtras(new Extras());
		}
		if(!Utils.isEmpty(homeInsuranceVO.getScheme())){
		 	Date effDate;
		  	effDate = homeInsuranceVO.getScheme().getEffDate();
		   	if(!Utils.isEmpty( effDate )){
		   		Calendar effCalendar = Calendar.getInstance();
		   		effCalendar.setTime( effDate );
		   		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		   	
				//Quote should to be valid exactly for 30 days from the date of Issue.
				effCalendar.add( Calendar.DATE, AppConstants.QUOTE_VALID_DAYS - 1 );
				effCalendar.add( Calendar.MONTH,0);
		   		createHomeQuoteResponse.getExtras().setExpiry("This quote is valid until "+dateFormat.format(effCalendar.getTime()).toString()
		   				+". Subject to no known or reported claims.");
		     }
		 }
		 createHomeQuoteResponse.getExtras().setPromotional_Message(homeInsuranceVO.getGeneralInfo().getSourceOfBus().getPromoCodeDesc());
	}	
	
	
	/**
	 * @Description : Maps Home InsuranceDetails
	 * @param homeInsuranceDetails
	 * @param homeInsuranceVO
	 */
	private void mapGeneralInsuranceDetails(CreateHomeQuoteResponse createHomeQuoteResponse,HomeInsuranceVO homeInsuranceVO) {
		
		if (Utils.isEmpty(createHomeQuoteResponse.getCustomerDetails())) {
			createHomeQuoteResponse.setCustomerDetails(new CustomerDetails());
		}
		createHomeQuoteResponse.getCustomerDetails().setEmailId(homeInsuranceVO.getGeneralInfo().getInsured().getEmailId());
		createHomeQuoteResponse.getCustomerDetails().setMobileNo(homeInsuranceVO.getGeneralInfo().getInsured().getMobileNo());
		createHomeQuoteResponse.getCustomerDetails().setInsuredId(homeInsuranceVO.getGeneralInfo().getInsured().getInsuredCode());
		createHomeQuoteResponse.getCustomerDetails().setFirstName(homeInsuranceVO.getGeneralInfo().getInsured().getFirstName());
		createHomeQuoteResponse.getCustomerDetails().setLastName(homeInsuranceVO.getGeneralInfo().getInsured().getLastName());
		//createHomeQuoteResponse.getCustomerDetails().setPoBox(homeInsuranceVO.getGeneralInfo().getInsured().getAddress().getPoBox());
		//createHomeQuoteResponse.getCustomerDetails().setCity(homeInsuranceVO.getGeneralInfo().getInsured().getCity());
		//createHomeQuoteResponse.getCustomerDetails().setNationality(homeInsuranceVO.getGeneralInfo().getInsured().getNationality());
		createHomeQuoteResponse.getCustomerDetails().setNationalID(homeInsuranceVO.getGeneralInfo().getInsured().getAddress().getCountry().toString());
		//createHomeQuoteResponse.getCustomerDetails().setVatRegNo(homeInsuranceVO.getGeneralInfo().getInsured().getVatRegNo());
		//createHomeQuoteResponse.getCustomerDetails().setRewardProgrammeType(homeInsuranceVO.getGeneralInfo().getInsured().getRoyaltyType());
		//createHomeQuoteResponse.getCustomerDetails().setRewardCardNumber(homeInsuranceVO.getGeneralInfo().getInsured().getGuestCardNo());
	}	
	
	/**
	 * @Description : Maps Transaction Details
	 * @param transactionDetails
	 * @param homeInsuranceVO
	 */
	private void mapTransactionDetails(CreateHomeQuoteResponse createHomeQuoteResponse,HomeInsuranceVO homeInsuranceVO) {
		
		
		if (Utils.isEmpty(createHomeQuoteResponse.getTransactionDetails())) {
			createHomeQuoteResponse.setTransactionDetails(new TransactionDetails());
		}
		createHomeQuoteResponse.getTransactionDetails().setEffectiveDate(homeInsuranceVO.getScheme().getEffDate());
		createHomeQuoteResponse.getTransactionDetails().setExpiryDate(homeInsuranceVO.getScheme().getExpiryDate());
		createHomeQuoteResponse.getTransactionDetails().setSchemeCode(homeInsuranceVO.getScheme().getSchemeCode());
		createHomeQuoteResponse.getTransactionDetails().setTariffCode(homeInsuranceVO.getScheme().getTariffCode());
		createHomeQuoteResponse.getTransactionDetails().setDistChannel(homeInsuranceVO.getGeneralInfo().getSourceOfBus().getDistributionChannel());
		//createHomeQuoteResponse.getTransactionDetails().setClassCode(homeInsuranceVO.getClassCode());
		//createHomeQuoteResponse.getTransactionDetails().setPolicyTypeCode(homeInsuranceVO.getPolicyType());
		//createHomeQuoteResponse.getTransactionDetails().setPolicyTerm(homeInsuranceVO.getPolicyTerm());
		//if(!Utils.isEmpty(homeInsuranceVO.getGeneralInfo().getSourceOfBus().getDirectSubAgent())){
		//	createHomeQuoteResponse.getTransactionDetails().setDirectSubAgent(homeInsuranceVO.getGeneralInfo().getSourceOfBus().getDirectSubAgent().intValue());
		//}else{
		//	createHomeQuoteResponse.getTransactionDetails().setDirectSubAgent(null);
		//}
		//createHomeQuoteResponse.getTransactionDetails().setBusinessSource(Integer.parseInt(homeInsuranceVO.getGeneralInfo().getSourceOfBus().getCustomerSource()));
		//createHomeQuoteResponse.getTransactionDetails().setPromocode(homeInsuranceVO.getGeneralInfo().getSourceOfBus().getPromoCode());
		createHomeQuoteResponse.getTransactionDetails().setPartnerTrnReferenceNumber(createHomeQuoteResponse.getTransactionDetails().getPartnerTrnReferenceNumber());
	}	
	
	/**
	 * @Description : FeesAndTaxes Details
	 * @param feesAndTaxes Details
	 * @param homeInsuranceVO
	 * @throws ParseException 
	 */
	private void mapFeesAndTaxes(CreateHomeQuoteResponse createHomeQuoteResponse,HomeInsuranceVO homeInsuranceVO) throws ParseException {
		
		if (Utils.isEmpty(createHomeQuoteResponse.getFeesAndTaxes())) {
			createHomeQuoteResponse.setFeesAndTaxes(new FeesAndTaxes());
		}
		createHomeQuoteResponse.getFeesAndTaxes().setLoadingOrDiscountPercent(new BigDecimal(homeInsuranceVO.getPremiumVO().getDiscOrLoadPerc()));
		Double premiumAmt = null;
		Double vatAmt,discAmt,amt;
		
		if(!Utils.isEmpty(homeInsuranceVO.getPremiumVO().getPromoDiscPerc())){
			createHomeQuoteResponse.getFeesAndTaxes().setPromoCodeDiscountPercent(new BigDecimal(homeInsuranceVO.getPremiumVO().getPromoDiscPerc()*-1));
			createHomeQuoteResponse.getFeesAndTaxes().setPromoCodeDiscountAmount(new BigDecimal(homeInsuranceVO.getPremiumVO().getPremiumAmtActual()*homeInsuranceVO.getPremiumVO().getPromoDiscPerc()/100));
			/*discAmt = Math.abs(homeInsuranceVO.getPremiumVO().getDiscOrLoadPerc())+homeInsuranceVO.getPremiumVO().getPromoDiscPerc();
			premiumAmt = (double) Math.round(Math.abs((homeInsuranceVO.getPremiumVO().getPremiumAmtActual()*discAmt/100)));
			amt = homeInsuranceVO.getPremiumVO().getPremiumAmtActual() - premiumAmt;
			vatAmt = ((amt*5)/100);*/
			//set scale for SAT issues
			createHomeQuoteResponse.getFeesAndTaxes().setVatAmount(new BigDecimal(homeInsuranceVO.getPremiumVO().getVatTax()).setScale(2,RoundingMode.HALF_UP));
		}else{
			createHomeQuoteResponse.getFeesAndTaxes().setPromoCodeDiscountPercent(new BigDecimal(0.0));
			createHomeQuoteResponse.getFeesAndTaxes().setPromoCodeDiscountAmount(new BigDecimal(0.0));
			/*discAmt = Math.abs(homeInsuranceVO.getPremiumVO().getPremiumAmtActual()) * homeInsuranceVO.getPremiumVO().getDiscOrLoadPerc() / 100;
			amt = homeInsuranceVO.getPremiumVO().getPremiumAmtActual() - discAmt;
			vatAmt = ((amt*5)/100);*/
			//set scale for SAT issues
			createHomeQuoteResponse.getFeesAndTaxes().setVatAmount(new BigDecimal(homeInsuranceVO.getPremiumVO().getVatTax()).setScale(2,RoundingMode.HALF_UP));
		}
		//Double discA = Math.abs(homeInsuranceVO.getPremiumVO().getPremiumAmtActual()*homeInsuranceVO.getPremiumVO().getDiscOrLoadPerc()/100);
		// CTS 07/08/2020 - CR#11645 Discount or Loading issue
		Double discorLoadAmt= homeInsuranceVO.getPremiumVO().getDiscOrLoadAmt().doubleValue();
		createHomeQuoteResponse.getFeesAndTaxes().setLoadingOrDiscountAmount(new BigDecimal(discorLoadAmt));
		//createHomeQuoteResponse.getFeesAndTaxes().setGovtTaxPercent(new BigDecimal(homeInsuranceVO.getPremiumVO().getGovtTax()));
		createHomeQuoteResponse.getFeesAndTaxes().setVatRatePercent(new BigDecimal(homeInsuranceVO.getPremiumVO().getVatTaxPerc()));
		//createHomeQuoteResponse.getFeesAndTaxes().setPolicyFees(new BigDecimal(homeInsuranceVO.getPremiumVO().getPolicyFees()));
		//createHomeQuoteResponse.setFinalPremium(new BigDecimal(homeInsuranceVO.getPremiumVO().getPremiumAmtActual()));
		//set scale for SAT issues
		//createHomeQuoteResponse.setPremiumPayable(new BigDecimal(homeInsuranceVO.getPremiumVO().getPremiumAmt()).setScale(2, RoundingMode.HALF_UP));
		//set scale for SAT issues
	}	
	
	/**
	 * @Description : Maps Partner Details
	 * @param partnerDetails
	 * @param homeInsuranceVO
	 */
	private void mapPartnerDetails(CreateHomeQuoteResponse createHomeQuoteResponse,HomeInsuranceVO homeInsuranceVO) {
		
		if (Utils.isEmpty(createHomeQuoteResponse.getPartnerDetails())) {
			createHomeQuoteResponse.setPartnerDetails(new PartnerDetails());
		}
		createHomeQuoteResponse.getPartnerDetails().setCommissionAmount(null);
		createHomeQuoteResponse.getPartnerDetails().setCommissionPercentage(null);
	}	
	
	
	/**
	 * @Description : Covers Details
	 * @param mapCovers
	 * @param homeInsuranceVO
	 */
	private void mapCoversDetails(CreateHomeQuoteResponse createHomeQuoteResponse,HomeInsuranceVO homeInsuranceVO) {
		
		if(Utils.isEmpty(createHomeQuoteResponse.getMandatoryCovers())) {
			createHomeQuoteResponse.setMandatoryCovers(new ArrayList<MandatoryCovers>());
		}
		
		MandatoryCovers mandatoryBuildingCovers = new MandatoryCovers();
		
		if(!Utils.isEmpty(homeInsuranceVO.getBuildingDetails().getSumInsured().getSumInsured())) {
			mandatoryBuildingCovers.setCoverDesc("Building");
			mandatoryBuildingCovers.setPremium(new BigDecimal(homeInsuranceVO.getBuildingDetails().getPremiumAmt()));
			mandatoryBuildingCovers.setCoverIncluded(true);
			mandatoryBuildingCovers.setCoverageLimit(new BigDecimal(homeInsuranceVO.getBuildingDetails().getSumInsured().getSumInsured()));
			//mandatoryBuildingCovers.setCoverMappingCode(""+homeInsuranceVO.getBuildingDetails().getCoverCodes().getCovCode()+"-"+homeInsuranceVO.getBuildingDetails().getCoverCodes().getCovTypeCode()+"-"+homeInsuranceVO.getBuildingDetails().getCoverCodes().getCovSubTypeCode()+"");
			
			if(Utils.isEmpty(mandatoryBuildingCovers.getRiskDetails())){
				mandatoryBuildingCovers.setRiskDetails(new RiskDetails());
			}
			mandatoryBuildingCovers.getRiskDetails().setRskId(homeInsuranceVO.getBuildingDetails().getRiskCodes().getRskId().intValue());
			mandatoryBuildingCovers.getRiskDetails().setBasicRskId(homeInsuranceVO.getBuildingDetails().getRiskCodes().getBasicRskId().intValue());
			if(homeInsuranceVO.getBuildingDetails().getRiskCodes().getRiskCat() == null) {	homeInsuranceVO.getBuildingDetails().getRiskCodes().setRiskCat(0); }
//			mandatoryBuildingCovers.getRiskDetails().setBasicRskCode(homeInsuranceVO.getBuildingDetails().getRiskCodes().getBasicRskCode());
//			mandatoryBuildingCovers.getRiskDetails().setRiskCode(homeInsuranceVO.getBuildingDetails().getRiskCodes().getRiskCode());
//			mandatoryBuildingCovers.getRiskDetails().setRiskType(homeInsuranceVO.getBuildingDetails().getRiskCodes().getRiskType());
//			mandatoryBuildingCovers.getRiskDetails().setRiskCat(homeInsuranceVO.getBuildingDetails().getRiskCodes().getRiskCat());
			mandatoryBuildingCovers.setRiskMappingCode(""+homeInsuranceVO.getBuildingDetails().getRiskCodes().getRiskCode()+"-"+homeInsuranceVO.getBuildingDetails().getRiskCodes().getRiskType()+"-"+homeInsuranceVO.getBuildingDetails().getRiskCodes().getRiskCat()+"");
			createHomeQuoteResponse.getMandatoryCovers().add(mandatoryBuildingCovers);	
		}
		
		/*if(Utils.isEmpty(createHomeQuoteResponse.getOptionalCovers())) {
			createHomeQuoteResponse.setOptionalCovers(new ArrayList<OptionalCovers>());
		}*/
		if(Utils.isEmpty(createHomeQuoteResponse.getListedItems())) {
			createHomeQuoteResponse.setListedItems(new ArrayList<ListedItems>());
		}
		if(!Utils.isEmpty(homeInsuranceVO.getCovers())) {
			
			for (CoverDetailsVO coverDetailsVO : homeInsuranceVO.getCovers()) {
				
				if(coverDetailsVO.getCoverCodes().getCovCode()==SvcConstants.oneVal && coverDetailsVO.getRiskCodes().getRiskCat()==SvcConstants.oneVal) {
					
					MandatoryCovers covers = new MandatoryCovers();
					covers.setRiskDetails(new RiskDetails());
					
					covers.setCoverDesc(coverDetailsVO.getCoverName());
					covers.setPremium(new BigDecimal(coverDetailsVO.getPremiumAmt()));
					covers.setCoverIncluded(true);
					covers.setCoverageLimit(new BigDecimal(coverDetailsVO.getSumInsured().getSumInsured()));
//					covers.setCoverMappingCode(""+coverDetailsVO.getCoverCodes().getCovCode()+"-"+coverDetailsVO.getCoverCodes().getCovTypeCode()+"-"+coverDetailsVO.getCoverCodes().getCovSubTypeCode()+"");
					covers.getRiskDetails().setRskId(coverDetailsVO.getRiskCodes().getRskId().intValue());
					covers.getRiskDetails().setBasicRskId(coverDetailsVO.getRiskCodes().getBasicRskId().intValue());
					if(homeInsuranceVO.getBuildingDetails().getRiskCodes().getRiskCat() == null) {	homeInsuranceVO.getBuildingDetails().getRiskCodes().setRiskCat(0); }
//					covers.getRiskDetails().setBasicRskCode(coverDetailsVO.getRiskCodes().getBasicRskCode());
//					covers.getRiskDetails().setRiskCode(coverDetailsVO.getRiskCodes().getRiskCode());
//					covers.getRiskDetails().setRiskType(coverDetailsVO.getRiskCodes().getRiskType());
//					covers.getRiskDetails().setRiskCat(coverDetailsVO.getRiskCodes().getRiskCat());
					covers.setRiskMappingCode(""+coverDetailsVO.getRiskCodes().getRiskCode()+"-"+coverDetailsVO.getRiskCodes().getRiskType()+"-"+coverDetailsVO.getRiskCodes().getRiskCat()+"");
					createHomeQuoteResponse.getMandatoryCovers().add(covers);
				}
				else {
					ListedItems listedCovers = new ListedItems();
					listedCovers.setRiskDetails(new RiskDetails());					
					listedCovers.setCoverDesc(coverDetailsVO.getCoverName());
//					listedCovers.setPremium(new BigDecimal(coverDetailsVO.getPremiumAmt()));
					listedCovers.setCoverIncluded(true);
					listedCovers.setCoverageLimit(new BigDecimal(coverDetailsVO.getSumInsured().getSumInsured()));
//					listedCovers.setCoverMappingCode(""+coverDetailsVO.getCoverCodes().getCovCode()+"-"+coverDetailsVO.getCoverCodes().getCovTypeCode()+"-"+coverDetailsVO.getCoverCodes().getCovSubTypeCode()+"");
					listedCovers.getRiskDetails().setRskId(coverDetailsVO.getRiskCodes().getRskId().intValue());
					listedCovers.getRiskDetails().setBasicRskId(coverDetailsVO.getRiskCodes().getBasicRskId().intValue());
					listedCovers.setRiskMappingCode(""+coverDetailsVO.getRiskCodes().getRiskCode()+"-"+coverDetailsVO.getRiskCodes().getRiskType()+"-"+coverDetailsVO.getRiskCodes().getRiskCat()+"");
					createHomeQuoteResponse.getListedItems().add(listedCovers);
				}
			}
		}
	}	
	
	/**
	 * @Description : Method used to retrieve optional covers from T_MAS_POLICY_RATING table for Home lob
	 * @param createHomeQuoteResponse
	 * @param homeInsuranceVO
	 * @throws ParseException 
	 */
	private void mapOptionalCoversDetails(CreateHomeQuoteResponse createHomeQuoteResponse,
			HomeInsuranceVO homeInsuranceVO) throws ParseException {
		// condition to Display Optional Covers if Content cover is selected	
		for (CoverDetailsVO coverDetailsVO : homeInsuranceVO.getCovers()) {
			if(coverDetailsVO.getCoverCodes().getCovCode() == SvcConstants.oneVal && coverDetailsVO.getRiskCodes().getRiskCode()==2 && coverDetailsVO.getRiskCodes().getRiskType()==SvcConstants.CONTENT_RSK_TYPE_CODE) {
				List<TMasPolicyRating> coverList = null;
				coverList = DAOUtils.getHomeOptionalCovers(homeInsuranceVO.getCommonVO(),homeInsuranceVO.getClassCode(),homeInsuranceVO.getPolicyType(),null,homeInsuranceVO.getScheme().getTariffCode());
				List<OptionalCovers> optionalCoversList = null;
				WebServiceUtils webServiceUtils = new WebServiceUtils();
				optionalCoversList = webServiceUtils.retrieveOptionalCoversFromRating(coverList,optionalCoversList,homeInsuranceVO);
				createHomeQuoteResponse.setOptionalCovers(optionalCoversList);
			}
		}
	}

	/**
	 * @Description : Maps Building Details Section
	 * @param : buildingDetails
	 * @param : homeInsuranceVO
	 */
	private void mapBuildingDetails(CreateHomeQuoteResponse createHomeQuoteResponse,HomeInsuranceVO homeInsuranceVO) {
		if (Utils.isEmpty(createHomeQuoteResponse.getBuildingDetails())) {
			createHomeQuoteResponse.setBuildingDetails(new BuildingDetails());
		}
		/*if(!Utils.isEmpty(homeInsuranceVO.getBuildingDetails().getEmirates()))
		{
			createHomeQuoteResponse.getBuildingDetails().setEmirate(Integer.parseInt(homeInsuranceVO.getBuildingDetails().getEmirates()));
		}
		else{
			createHomeQuoteResponse.getBuildingDetails().setEmirate(null);
		}
		if(!Utils.isEmpty(homeInsuranceVO.getBuildingDetails().getArea()))
		{
			createHomeQuoteResponse.getBuildingDetails().setArea(Integer.parseInt(homeInsuranceVO.getBuildingDetails().getArea()));
		}else{
			createHomeQuoteResponse.getBuildingDetails().setArea(null);
		}
		if(!Utils.isEmpty(homeInsuranceVO.getBuildingDetails().getTypeOfProperty())){
			createHomeQuoteResponse.getBuildingDetails().setPropertyType(homeInsuranceVO.getBuildingDetails().getTypeOfProperty().intValue());
		}else{
			createHomeQuoteResponse.getBuildingDetails().setPropertyType(null);
		}
		createHomeQuoteResponse.getBuildingDetails().setBuildingName(homeInsuranceVO.getBuildingDetails().getBuildingname());
		if(!Utils.isEmpty(homeInsuranceVO.getBuildingDetails().getFlatVillaNo())){
			createHomeQuoteResponse.getBuildingDetails().setFlatVillaNo(homeInsuranceVO.getBuildingDetails().getFlatVillaNo());
		}else{
			createHomeQuoteResponse.getBuildingDetails().setFlatVillaNo(null);
		}
		if(!Utils.isEmpty(homeInsuranceVO.getBuildingDetails().getMortgageeName())) {
			createHomeQuoteResponse.getBuildingDetails().setMortgageeCode(Integer.parseInt(homeInsuranceVO.getBuildingDetails().getMortgageeName()));
		}else{
			createHomeQuoteResponse.getBuildingDetails().setMortgageeCode(null);
		}*/
		if(!Utils.isEmpty(homeInsuranceVO.getBuildingDetails().getOwnershipStatus())){
			createHomeQuoteResponse.getBuildingDetails().setOwnershipStatus(homeInsuranceVO.getBuildingDetails().getOwnershipStatus().intValue());
		}else{
			createHomeQuoteResponse.getBuildingDetails().setOwnershipStatus(null);
		}
		
	}
	
	/**
	 * @Description : Maps Staff Details
	 * @param : staffDetails
	 * @param : homeInsuranceVO
	 */
	private void mapStaffDetails(CreateHomeQuoteResponse createHomeQuoteResponse,HomeInsuranceVO homeInsuranceVO) {
		if (Utils.isEmpty(createHomeQuoteResponse.getStaffDetails())) {
			createHomeQuoteResponse.setStaffDetails(new StaffDetails());
		}
		List<Staff> staffDetailsVOList=new ArrayList<Staff>();
		for(StaffDetailsVO staffDetailsVO:homeInsuranceVO.getStaffDetails()){
			Staff staff = new Staff();
			staff.setStaffName(staffDetailsVO.getEmpName());
			staff.setStaffDob(staffDetailsVO.getEmpDob());
			staff.setStaffId(new Double(staffDetailsVO.getEmpId()).intValue());
			staffDetailsVOList.add(staff);
		}
		createHomeQuoteResponse.getStaffDetails().setStaff(staffDetailsVOList);
	}
	
	/**
	 * @Description : UnderWriting Questions Details
	 * @param underWritingQuestions
	 * @param homeInsuranceVO
	 */
	private void mapUnderWritingQuestions(CreateHomeQuoteResponse createHomeQuoteResponse,HomeInsuranceVO homeInsuranceVO) {
		
		if (Utils.isEmpty(createHomeQuoteResponse.getUnderWritingQuestions())) {
			createHomeQuoteResponse.setUnderWritingQuestions(new UnderWritingQuestions());
		}
		createHomeQuoteResponse.getUnderWritingQuestions().setHomeUnattended(false);
		createHomeQuoteResponse.getUnderWritingQuestions().setClaim(false);
	}	
	
	
	
}
