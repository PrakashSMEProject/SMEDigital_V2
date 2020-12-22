package com.rsaame.pas.b2c.ws.mapper;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.b2c.cmn.constants.AppConstants;
import com.rsaame.pas.b2c.ws.vo.CustomerDetails;
import com.rsaame.pas.b2c.ws.vo.Extras;
import com.rsaame.pas.b2c.ws.vo.FeesAndTaxes;
import com.rsaame.pas.b2c.ws.vo.MandatoryCovers;
import com.rsaame.pas.b2c.ws.vo.OptionalCovers;
import com.rsaame.pas.b2c.ws.vo.PartnerDetails;
import com.rsaame.pas.b2c.ws.vo.Products;
import com.rsaame.pas.b2c.ws.vo.RiskDetails;
import com.rsaame.pas.b2c.ws.vo.TransactionDetails;
import com.rsaame.pas.b2c.ws.vo.Travellers;
import com.rsaame.pas.b2c.ws.vo.UnderWritingQuestions;
import com.rsaame.pas.b2c.ws.vo.UpdateTravelQuoteResponse;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.bus.CoverDetailsVO;
import com.rsaame.pas.vo.bus.TravelInsuranceVO;
import com.rsaame.pas.vo.bus.TravelPackageVO;
import com.rsaame.pas.vo.bus.TravelerDetailsVO;

public class TravelUpdateQuoteResponseMapper implements BaseResponseVOMapper {

	@Override
	public void mapVOToResponse(Object valueObj, Object requestObj) throws Exception {
		if (requestObj instanceof UpdateTravelQuoteResponse && valueObj instanceof TravelInsuranceVO) {

			UpdateTravelQuoteResponse updateTravelQuoteResponse = (UpdateTravelQuoteResponse) requestObj;
			TravelInsuranceVO travelInsuranceVO = (TravelInsuranceVO) valueObj;

			// Quotation Details
			mapQuotationDetails(updateTravelQuoteResponse, travelInsuranceVO);

			// General Insured Information
			mapGeneralInsuranceDetails(updateTravelQuoteResponse, travelInsuranceVO);

			// Transaction Details (Source of bus and Scheme Details)
			mapTransactionDetails(updateTravelQuoteResponse, travelInsuranceVO);

			// PartnerDetails
			mapPartnerDetails(updateTravelQuoteResponse, travelInsuranceVO);

			// Travellers Details
			mapTravellersDetails(updateTravelQuoteResponse, travelInsuranceVO);

			// Cover Details or Product Details or Mandatory Covers or Optional Covers
			mapMandatoryOptionalCovers(updateTravelQuoteResponse, travelInsuranceVO);

			// UnderWritingQuestions
			mapUnderWritingQuestions(updateTravelQuoteResponse,travelInsuranceVO);
			
			// map Extra details
			//mapExtraDetails(updateTravelQuoteResponse,travelInsuranceVO);

		} else {
			throw new Exception("Unexpected request or value object");
		}
	}

	private void mapExtraDetails(UpdateTravelQuoteResponse updateTravelQuoteResponse,
			TravelInsuranceVO travelInsuranceVO) {
		if (Utils.isEmpty(updateTravelQuoteResponse.getExtras())) {
			updateTravelQuoteResponse.setExtras(new Extras());
		}
		if(!Utils.isEmpty(travelInsuranceVO.getScheme())){
	    	Date effDate;
	    	effDate = travelInsuranceVO.getScheme().getEffDate();
	    	if(!Utils.isEmpty( effDate )){
                Calendar effCalendar = Calendar.getInstance();
                effCalendar.setTime( effDate );
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
          
                //Quote should to be valid exactly for 30 days from the date of Issue.
                effCalendar.add( Calendar.DATE, AppConstants.QUOTE_VALID_DAYS - 2 );
                effCalendar.add( Calendar.MONTH,0);
                updateTravelQuoteResponse.setQuoteValidTill(effCalendar.getTime());
                updateTravelQuoteResponse.getExtras().setExpiry("This quote is valid until "+dateFormat.format(effCalendar.getTime()).toString()
                            +". Subject to no known or reported claims.");
	    	}
		}
	}

	private void mapUnderWritingQuestions(UpdateTravelQuoteResponse updateTravelQuoteResponse,
			TravelInsuranceVO travelInsuranceVO) {
		if (Utils.isEmpty(updateTravelQuoteResponse.getUnderWritingQuestions())) {
			updateTravelQuoteResponse.setUnderWritingQuestions(new UnderWritingQuestions());
		}
		if(travelInsuranceVO.getTravelDetailsVO().getTravelLocation().equalsIgnoreCase("Worldwide including USA and Canada")) {
			updateTravelQuoteResponse.getUnderWritingQuestions().setInclUsaCa(Boolean.TRUE);
		}else if(travelInsuranceVO.getTravelDetailsVO().getTravelLocation().equalsIgnoreCase("Worldwide excluding USA and Canada")){
			updateTravelQuoteResponse.getUnderWritingQuestions().setInclUsaCa(Boolean.FALSE);
		}
		updateTravelQuoteResponse.getUnderWritingQuestions().setClaim(Boolean.FALSE);
		updateTravelQuoteResponse.getUnderWritingQuestions().setWarZone(Boolean.FALSE);
	}

	private void mapPartnerDetails(UpdateTravelQuoteResponse updateTravelQuoteResponse,
			TravelInsuranceVO travelInsuranceVO) {
		if(Utils.isEmpty(updateTravelQuoteResponse.getPartnerDetails())) {
			updateTravelQuoteResponse.setPartnerDetails(new PartnerDetails());
		}
		if(!Utils.isEmpty(travelInsuranceVO.getCommission())) {
			updateTravelQuoteResponse.getPartnerDetails().setCommissionPercentage(new BigDecimal(travelInsuranceVO.getCommission()));
			updateTravelQuoteResponse.getPartnerDetails().setCommissionAmount(new BigDecimal(travelInsuranceVO.getCommission() * travelInsuranceVO.getPremiumVO().getPremiumAmt() /100));
		}
		
	}

	private void mapMandatoryOptionalCovers(UpdateTravelQuoteResponse updateTravelQuoteResponse,
			TravelInsuranceVO travelInsuranceVO) {
		List<Products> products = new ArrayList<Products>();
		double vatTaxPerc = SvcUtils.getLookUpCode("VATTAX",
				"7", "151", "5");
		if(!Utils.isEmpty(vatTaxPerc)){
			travelInsuranceVO.getPremiumVO().setVatTaxPerc(vatTaxPerc);
			travelInsuranceVO.setVatTaxPerc(vatTaxPerc);
		}
		for (TravelPackageVO travelPackageVO : travelInsuranceVO.getTravelPackageList()) {
			
			List<MandatoryCovers> mandatoryCoversList = new ArrayList<MandatoryCovers>();
			List<OptionalCovers> optionalCoversList = new ArrayList<OptionalCovers>();
			Products productsDetails = new Products();
			productsDetails.setProductCode(Integer.parseInt(travelPackageVO.getTariffCode()));
			productsDetails.setProductDesc(travelPackageVO.getPackageName());
			if(travelPackageVO.getIsSelected()){
				productsDetails.setPremiumPayable(new BigDecimal(travelInsuranceVO.getPremiumVO().getPremiumAmtActual()));
				productsDetails.setFinalPremium(new BigDecimal(travelInsuranceVO.getPremiumVO().getPremiumAmt()));
				mapFeesAndTaxes(productsDetails, travelInsuranceVO);
			}else{
				productsDetails.setPremiumPayable(new BigDecimal(travelPackageVO.getPremiumAmt()));
				productsDetails.setFinalPremium(new BigDecimal(travelPackageVO.getPremiumAmtActual()));
				mapFeesAndTaxes(productsDetails, travelInsuranceVO);
			}
			productsDetails.setCurrencyType(travelInsuranceVO.getPremiumVO().getCurrency());
			
			// FeesAndTaxes
			
			
			for (CoverDetailsVO coverDetailsVO : travelPackageVO.getCovers()) {
				if (!Utils.isEmpty(coverDetailsVO.getMandatoryIndicator()) && coverDetailsVO.getMandatoryIndicator().equals(Boolean.TRUE)) {	
					MandatoryCovers mandatoryCovers = new MandatoryCovers();
					mandatoryCovers.setCoverDesc(coverDetailsVO.getCoverName());
					mandatoryCovers.setCoverMappingCode(coverDetailsVO.getCoverCodes().getCovCode()
							+"-"+coverDetailsVO.getCoverCodes().getCovTypeCode()
							+"-"+coverDetailsVO.getCoverCodes().getCovSubTypeCode());
					if(Utils.isEmpty(mandatoryCovers.getRiskDetails())){
						mandatoryCovers.setRiskDetails(new RiskDetails());
					}
					if(!Utils.isEmpty(coverDetailsVO.getRiskCodes().getRskId())) {
						mandatoryCovers.getRiskDetails().setRskId(coverDetailsVO.getRiskCodes().getRskId().intValue());
					}
					if(!Utils.isEmpty(coverDetailsVO.getRiskCodes().getBasicRskId())) {
						mandatoryCovers.getRiskDetails().setBasicRskId(coverDetailsVO.getRiskCodes().getBasicRskId().intValue());
					}
					if(Utils.isEmpty(coverDetailsVO.getIsCovered())){
						mandatoryCovers.setCoverIncluded(Boolean.FALSE);
					}else {
						mandatoryCovers.setCoverIncluded(Boolean.TRUE);
					}
					mandatoryCovers.getRiskDetails().setBasicRskCode(coverDetailsVO.getRiskCodes().getBasicRskCode());
					mandatoryCovers.getRiskDetails().setRiskCode(coverDetailsVO.getRiskCodes().getRiskCode());
					mandatoryCovers.getRiskDetails().setRiskType(coverDetailsVO.getRiskCodes().getRiskType());
					mandatoryCovers.getRiskDetails().setRiskCat(coverDetailsVO.getRiskCodes().getRiskCat());
					mandatoryCovers.setCoverageLimit(new BigDecimal(coverDetailsVO.getSumInsured().getSumInsured()));
					mandatoryCoversList.add(mandatoryCovers);
					productsDetails.setMandatoryCovers(mandatoryCoversList);
				}
				if (!Utils.isEmpty(coverDetailsVO.getMandatoryIndicator()) && coverDetailsVO.getMandatoryIndicator().equals(Boolean.FALSE)) {
					OptionalCovers optionalCovers = new OptionalCovers();
					optionalCovers.setCoverDesc(coverDetailsVO.getCoverName());
					optionalCovers.setCoverMappingCode(coverDetailsVO.getCoverCodes().getCovCode()
							+"-"+coverDetailsVO.getCoverCodes().getCovTypeCode()
							+"-"+coverDetailsVO.getCoverCodes().getCovSubTypeCode());
					if(Utils.isEmpty(optionalCovers.getRiskDetails())){
						optionalCovers.setRiskDetails(new RiskDetails());
					}
					if(!Utils.isEmpty(coverDetailsVO.getRiskCodes().getRskId())) {
						optionalCovers.getRiskDetails().setRskId(coverDetailsVO.getRiskCodes().getRskId().intValue());
					}
					if(!Utils.isEmpty(coverDetailsVO.getRiskCodes().getBasicRskId())) {
						optionalCovers.getRiskDetails().setBasicRskId(coverDetailsVO.getRiskCodes().getBasicRskId().intValue());
					}
					if(Utils.isEmpty(coverDetailsVO.getIsCovered())){
						optionalCovers.setCoverIncluded(Boolean.FALSE);
					}else {
						optionalCovers.setCoverIncluded(Boolean.TRUE);
					}
					optionalCovers.getRiskDetails().setBasicRskCode(coverDetailsVO.getRiskCodes().getBasicRskCode());
					optionalCovers.getRiskDetails().setRiskCode(coverDetailsVO.getRiskCodes().getRiskCode());
					optionalCovers.getRiskDetails().setRiskType(coverDetailsVO.getRiskCodes().getRiskType());
					optionalCovers.getRiskDetails().setRiskCat(coverDetailsVO.getRiskCodes().getRiskCat());
					optionalCovers.setCoverageLimit(new BigDecimal(coverDetailsVO.getSumInsured().getSumInsured()));
					optionalCoversList.add(optionalCovers);
					productsDetails.setOptionalCovers(optionalCoversList);
					
				}
				//products.add(productsDetails);
			}
			products.add(productsDetails);
		}
		updateTravelQuoteResponse.setProducts(products);
	}
	
	private void mapFeesAndTaxes(Products productsDetails,
			TravelInsuranceVO travelInsuranceVO) {
			
			FeesAndTaxes feesAndTaxes = new FeesAndTaxes();
			
			if(!Utils.isEmpty(travelInsuranceVO.getPremiumVO().getDiscOrLoadPerc())) {
				feesAndTaxes.setLoadingOrDiscountPercent(new BigDecimal(travelInsuranceVO.getPremiumVO().getDiscOrLoadPerc()));
			}
		    Double amount = (double) Math.round(Math.abs((travelInsuranceVO.getPremiumVO().getPremiumAmtActual()/travelInsuranceVO.getPremiumVO().getDiscOrLoadPerc())));
		    Double discOrLoadAmt = Math.abs(amount) * -1;
			if(!Utils.isEmpty(travelInsuranceVO.getPremiumVO().getDiscOrLoadAmt())) {
				feesAndTaxes.setLoadingOrDiscountAmount(new BigDecimal(discOrLoadAmt));
			}
			
			if(!Utils.isEmpty(travelInsuranceVO.getPremiumVO().getPromoDiscPerc())) {
				feesAndTaxes.setPromoCodeDiscountPercent(new BigDecimal(travelInsuranceVO.getPremiumVO().getPromoDiscPerc()));
				feesAndTaxes.setPromoCodeDiscountAmount(new BigDecimal(travelInsuranceVO.getPremiumVO().getPremiumAmt()*travelInsuranceVO.getPremiumVO().getPromoDiscPerc()/100));
			}
			
			if(!Utils.isEmpty(travelInsuranceVO.getPremiumVO().getGovtTax())) {
				feesAndTaxes.setGovtTaxPercent(new BigDecimal(travelInsuranceVO.getPremiumVO().getGovtTax()));
				
			}
			if(!Utils.isEmpty(travelInsuranceVO.getPremiumVO().getVatTaxPerc())){
				feesAndTaxes.setVatRatePercent(new BigDecimal(travelInsuranceVO.getPremiumVO().getVatTaxPerc()));
				feesAndTaxes.setVatAmount(new BigDecimal(travelInsuranceVO.getPremiumVO().getVatTax()));
			}
			if(!Utils.isEmpty(travelInsuranceVO.getPremiumVO().getPolicyFees())) {
				feesAndTaxes.setPolicyFees(new BigDecimal(travelInsuranceVO.getPremiumVO().getPolicyFees()));
			}
			productsDetails.setFeesAndTaxes(feesAndTaxes);
	}
	
	private void mapTravellersDetails(UpdateTravelQuoteResponse updateTravelQuoteResponse,
			TravelInsuranceVO travelInsuranceVO) {
		List<Travellers> travellersList = new ArrayList<Travellers>();
		for (TravelerDetailsVO travelerDetailsVO : travelInsuranceVO.getTravelDetailsVO().getTravelerDetailsList()) {
			Travellers travellers = new Travellers();
			travellers.setTravellerName(travelerDetailsVO.getName());
			travellers.setTravellerDOB(travelerDetailsVO.getDateOfBirth());
			travellers.setRelation(travelerDetailsVO.getRelation());
			travellers.setTravellerNationality(travelerDetailsVO.getNationality().intValue());
			travellers.setTravellerId(travelerDetailsVO.getGprId());
			travellersList.add(travellers);
		}
		updateTravelQuoteResponse.setTravellers(travellersList);
	}

	private void mapTransactionDetails(UpdateTravelQuoteResponse updateTravelQuoteResponse,
			TravelInsuranceVO travelInsuranceVO) {
		if(Utils.isEmpty(updateTravelQuoteResponse.getTransactionDetails())) {
			updateTravelQuoteResponse.setTransactionDetails(new TransactionDetails());
		}
		updateTravelQuoteResponse.getTransactionDetails().setClassCode(travelInsuranceVO.getPolicyClassCode()); // Class Code
		updateTravelQuoteResponse.getTransactionDetails().setPolicyTypeCode(travelInsuranceVO.getPolicyType()); // Policy Type Code
		updateTravelQuoteResponse.getTransactionDetails().setPolicyTerm(travelInsuranceVO.getPolicyTerm()); // Policy Term
		updateTravelQuoteResponse.getTransactionDetails().setEffectiveDate(travelInsuranceVO.getScheme().getEffDate());
		updateTravelQuoteResponse.getTransactionDetails().setExpiryDate(travelInsuranceVO.getScheme().getExpiryDate());

		updateTravelQuoteResponse.getTransactionDetails().setSchemeCode(travelInsuranceVO.getScheme().getSchemeCode());
		updateTravelQuoteResponse.getTransactionDetails().setTariffCode(travelInsuranceVO.getScheme().getTariffCode());
		updateTravelQuoteResponse.getTransactionDetails().setDistChannel(travelInsuranceVO.getGeneralInfo().getSourceOfBus().getDistributionChannel());
		if (!Utils.isEmpty(travelInsuranceVO.getGeneralInfo().getSourceOfBus().getDirectSubAgent())){
			updateTravelQuoteResponse.getTransactionDetails().setDirectSubAgent(travelInsuranceVO.getGeneralInfo().getSourceOfBus().getDirectSubAgent().intValue());
		}
		if (!Utils.isEmpty(travelInsuranceVO.getGeneralInfo().getSourceOfBus().getCustomerSource())){
			updateTravelQuoteResponse.getTransactionDetails().setBusinessSource(Integer.parseInt(travelInsuranceVO.getGeneralInfo().getSourceOfBus().getCustomerSource()));
		}
		updateTravelQuoteResponse.getTransactionDetails().setPromocode(travelInsuranceVO.getGeneralInfo().getSourceOfBus().getPromoCode());
		updateTravelQuoteResponse.getTransactionDetails().getPartnerTrnReferenceNumber(); // remaining
	}

	private void mapGeneralInsuranceDetails(UpdateTravelQuoteResponse updateTravelQuoteResponse,
			TravelInsuranceVO travelInsuranceVO) {
		if(Utils.isEmpty(updateTravelQuoteResponse.getCustomerDetails())) {
			updateTravelQuoteResponse.setCustomerDetails(new CustomerDetails());
		}
		TravelerDetailsVO travelerDetailsVO =  travelInsuranceVO.getTravelDetailsVO().getTravelerDetailsList().get(0);
		updateTravelQuoteResponse.getCustomerDetails().setEmailId(travelInsuranceVO.getGeneralInfo().getInsured().getEmailId());
		updateTravelQuoteResponse.getCustomerDetails().setMobileNo(travelInsuranceVO.getGeneralInfo().getInsured().getMobileNo());
		updateTravelQuoteResponse.getCustomerDetails().setInsuredId(travelInsuranceVO.getGeneralInfo().getInsured().getInsuredCode());
		updateTravelQuoteResponse.getCustomerDetails().setFirstName(travelInsuranceVO.getGeneralInfo().getInsured().getFirstName());
		updateTravelQuoteResponse.getCustomerDetails().setLastName(travelInsuranceVO.getGeneralInfo().getInsured().getLastName());
		updateTravelQuoteResponse.getCustomerDetails().setPoBox(travelInsuranceVO.getGeneralInfo().getInsured().getAddress().getPoBox());
		updateTravelQuoteResponse.getCustomerDetails().setCity(travelInsuranceVO.getGeneralInfo().getInsured().getCity());
		updateTravelQuoteResponse.getCustomerDetails().setNationality(travelerDetailsVO.getNationality().intValue()); // Emirates
		updateTravelQuoteResponse.getCustomerDetails().setNationalID(travelInsuranceVO.getGeneralInfo().getInsured().getAddress().getCountry().toString()); // Country
		updateTravelQuoteResponse.getCustomerDetails().setVatRegNo(travelInsuranceVO.getGeneralInfo().getInsured().getVatRegNo());
		updateTravelQuoteResponse.getCustomerDetails().setRewardProgrammeType(travelInsuranceVO.getGeneralInfo().getInsured().getRoyaltyType()); // Reward Programmme
		updateTravelQuoteResponse.getCustomerDetails().setRewardCardNumber(travelInsuranceVO.getGeneralInfo().getInsured().getGuestCardNo()); // Guest Card No

	}

	private void mapQuotationDetails(UpdateTravelQuoteResponse updateTravelQuoteResponse,
			TravelInsuranceVO travelInsuranceVO) {
		updateTravelQuoteResponse.setQuotationNo(travelInsuranceVO.getCommonVO().getQuoteNo());
		updateTravelQuoteResponse.setEndtId(travelInsuranceVO.getCommonVO().getEndtId());
		updateTravelQuoteResponse.setEndtNo(travelInsuranceVO.getCommonVO().getEndtNo());
		updateTravelQuoteResponse.setPolicyId(travelInsuranceVO.getCommonVO().getPolicyId());
		updateTravelQuoteResponse.setQuoteStatus(travelInsuranceVO.getCommonVO().getStatus());
		mapExtraDetails(updateTravelQuoteResponse, travelInsuranceVO);
	}

}
