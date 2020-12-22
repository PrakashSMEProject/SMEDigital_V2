package com.rsaame.pas.b2c.ws.mapper;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.b2c.cmn.constants.AppConstants;
import com.rsaame.pas.b2c.ws.utilities.WebServiceUtils;
import com.rsaame.pas.b2c.ws.vo.FeesAndTaxes;
import com.rsaame.pas.b2c.ws.vo.MandatoryCovers;
import com.rsaame.pas.b2c.ws.vo.OptionalCovers;
import com.rsaame.pas.b2c.ws.vo.Products;
import com.rsaame.pas.b2c.ws.vo.Travellers;
import com.rsaame.pas.b2c.ws.vo.UpdateTravelQuoteRequest;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.bus.AddressVO;
import com.rsaame.pas.vo.bus.CoverDetailsVO;
import com.rsaame.pas.vo.bus.GeneralInfoVO;
import com.rsaame.pas.vo.bus.InsuredVO;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.bus.SchemeVO;
import com.rsaame.pas.vo.bus.SourceOfBusinessVO;
import com.rsaame.pas.vo.bus.SumInsuredVO;
import com.rsaame.pas.vo.bus.TravelDetailsVO;
import com.rsaame.pas.vo.bus.TravelInsuranceVO;
import com.rsaame.pas.vo.bus.TravelPackageVO;
import com.rsaame.pas.vo.bus.TravelerDetailsVO;
import com.rsaame.pas.vo.cmn.CommonVO;
import com.rsaame.pas.vo.cmn.CoverVO;
import com.rsaame.pas.vo.cmn.RiskVO;

public class TravelUpdateQuoteRequestMapper implements BaseRequestVOMapper {
	
	WebServiceUtils webServiceUtils = new WebServiceUtils();
	@Override
	public void mapRequestToVO(Object requestObj, Object valueObj) throws Exception {

		if (requestObj instanceof UpdateTravelQuoteRequest && valueObj instanceof TravelInsuranceVO) {

			UpdateTravelQuoteRequest updateTravelQuoteRequest = (UpdateTravelQuoteRequest) requestObj;
			TravelInsuranceVO travelInsuranceVO = (TravelInsuranceVO) valueObj;

			// Initialize objects
			initialiseObjects(travelInsuranceVO);

			// Quotation Details
			mapQuotationDetails(updateTravelQuoteRequest, travelInsuranceVO);

			// General Insured Information
			mapGeneralInsuranceDetails(updateTravelQuoteRequest, travelInsuranceVO);
			
			// Transaction Details (Source of bus and Scheme Details)
			mapTransactionDetails(updateTravelQuoteRequest, travelInsuranceVO);
			
			// PartnerDetails
			//mapPartnerDetails(updateTravelQuoteRequest, travelInsuranceVO);
			
			// Travellers Details and UnderWritingQuestions
			mapTravellersDetails(updateTravelQuoteRequest, travelInsuranceVO);

			// Cover Details or Product Details or Mandatory Covers or Optional Covers
			mapMandatoryOptionalCovers(updateTravelQuoteRequest, travelInsuranceVO);
			
			mapUnderWritingQuestions(updateTravelQuoteRequest,travelInsuranceVO);

		} else {
			throw new Exception("Unexpected request or value object");
		}

	}


	private void mapMandatoryOptionalCovers(UpdateTravelQuoteRequest updateTravelQuoteRequest,
			TravelInsuranceVO travelInsuranceVO) {
		List<TravelPackageVO> travelPkgList = new ArrayList<TravelPackageVO>();
		List<CoverDetailsVO> coverDetailsList = new ArrayList<CoverDetailsVO>();
		
		for (Products products : updateTravelQuoteRequest.getProducts()) {
			TravelPackageVO travelPkgVo = new TravelPackageVO();
			travelPkgVo.setTariffCode(products.getProductCode().toString());
			travelPkgVo.setPackageName(products.getProductDesc());
			
			
			/*	if (Utils.isEmpty(products.getFeesAndTaxes())) {
				products.setFeesAndTaxes(new FeesAndTaxes());
			}
			travelInsuranceVO.getPremiumVO().setDiscOrLoadPerc(products.getFeesAndTaxes().getLoadingOrDiscountPercent().doubleValue());
			travelInsuranceVO.getPremiumVO().setVatTaxPerc(products.getFeesAndTaxes().getVatRatePercent().doubleValue());
			travelInsuranceVO.getPremiumVO().setVatTax(products.getFeesAndTaxes().getVatAmount().doubleValue());*/
			
			// FeesAndTaxes
			//if(products.getProductCode().equals(updateTravelQuoteRequest.getTransactionDetails().getTariffCode()) ){
				
				
			//}
			travelInsuranceVO.getPremiumVO().setCurrency(products.getCurrencyType());
			if(updateTravelQuoteRequest.getTransactionDetails().getTariffCode().equals(products.getProductCode())) {
				
				travelInsuranceVO.getPremiumVO().setPremiumAmt(products.getFinalPremium().doubleValue());
				travelInsuranceVO.getPremiumVO().setPremiumAmtActual(products.getPremiumPayable().doubleValue());
				
				mapFeesAndTaxes(products, travelInsuranceVO);
				if (!Utils.isEmpty(products.getMandatoryCovers())) {							// MandatoryCovers
					for (MandatoryCovers mandatoryCovers : products.getMandatoryCovers()) {
						CoverDetailsVO coverDetailsVO = new CoverDetailsVO();
						if (Utils.isEmpty(coverDetailsVO.getCoverCodes())) {
							coverDetailsVO.setCoverCodes(new CoverVO());
						}
						if (Utils.isEmpty(coverDetailsVO.getSumInsured())) {
							coverDetailsVO.setSumInsured(new SumInsuredVO());
						}
						if (Utils.isEmpty(coverDetailsVO.getRiskCodes())) {
							coverDetailsVO.setRiskCodes(new RiskVO());
						}
						coverDetailsVO.setCoverName(mandatoryCovers.getCoverDesc());
						List<Integer> mappingCodes = webServiceUtils.splitCovers(mandatoryCovers.getCoverMappingCode());
						coverDetailsVO.getCoverCodes().setCovCode(mappingCodes.get(0).shortValue());
						coverDetailsVO.getCoverCodes().setCovTypeCode(mappingCodes.get(1).shortValue());
						coverDetailsVO.getCoverCodes().setCovSubTypeCode(mappingCodes.get(2).shortValue());
						if(mandatoryCovers.getCoverIncluded().equals(Boolean.TRUE)){
							coverDetailsVO.setIsCovered(AppConstants.STATUS_ON);
						}else if (Utils.isEmpty(mandatoryCovers.getCoverIncluded() == null) || Utils.isEmpty(mandatoryCovers.getCoverIncluded().equals(Boolean.FALSE))) {
							coverDetailsVO.setIsCovered(null);
						} 
						if (mandatoryCovers.getCoverageLimit() == null) {
							coverDetailsVO.getSumInsured().setSumInsured(0.0);
						} else {
							coverDetailsVO.getSumInsured().setSumInsured(mandatoryCovers.getCoverageLimit().doubleValue());
						}
	
						coverDetailsVO.getRiskCodes().setBasicRskCode(mandatoryCovers.getRiskDetails().getBasicRskCode());
						coverDetailsVO.getRiskCodes().setRiskCode(mandatoryCovers.getRiskDetails().getRiskCode());
						coverDetailsVO.getRiskCodes().setRiskType(mandatoryCovers.getRiskDetails().getRiskType());
						coverDetailsVO.getRiskCodes().setRiskCat(mandatoryCovers.getRiskDetails().getRiskCat());
						if(!Utils.isEmpty(mandatoryCovers.getRiskDetails().getBasicRskId())) {
							coverDetailsVO.getRiskCodes().setBasicRskId(new BigDecimal(mandatoryCovers.getRiskDetails().getBasicRskId()));
						}
						if(!Utils.isEmpty(mandatoryCovers.getRiskDetails().getRskId())) {
							coverDetailsVO.getRiskCodes().setRskId(new BigDecimal(mandatoryCovers.getRiskDetails().getRskId()));
						}
						coverDetailsVO.setMandatoryIndicator(Boolean.TRUE);
						travelPkgVo.getCovers().add(coverDetailsVO);
						//travelPkgVo.setCovers(coverDetailsList);
						//travelPkgList.add(travelPkgVo);
					}
				}
				if (!Utils.isEmpty(products.getOptionalCovers())) {							// MandatoryCovers
					for (OptionalCovers optionalCovers : products.getOptionalCovers()) {
						CoverDetailsVO coverDetailsVO = new CoverDetailsVO();
						if (Utils.isEmpty(coverDetailsVO.getCoverCodes())) {
							coverDetailsVO.setCoverCodes(new CoverVO());
						}
						if (Utils.isEmpty(coverDetailsVO.getSumInsured())) {
							coverDetailsVO.setSumInsured(new SumInsuredVO());
						}
						if (Utils.isEmpty(coverDetailsVO.getRiskCodes())) {
							coverDetailsVO.setRiskCodes(new RiskVO());
						}
						coverDetailsVO.setCoverName(optionalCovers.getCoverDesc());
						List<Integer> mappingCodes = webServiceUtils.splitCovers(optionalCovers.getCoverMappingCode());
						coverDetailsVO.getCoverCodes().setCovCode(mappingCodes.get(0).shortValue());
						coverDetailsVO.getCoverCodes().setCovTypeCode(mappingCodes.get(1).shortValue());
						coverDetailsVO.getCoverCodes().setCovSubTypeCode(mappingCodes.get(2).shortValue());
						if (optionalCovers.getCoverIncluded() == null) {
							coverDetailsVO.setIsCovered(null);
						} else if (optionalCovers.getCoverIncluded().equals(Boolean.TRUE)) {
							coverDetailsVO.setIsCovered(AppConstants.STATUS_ON);
						}
						if (optionalCovers.getCoverageLimit() == null) {
							coverDetailsVO.getSumInsured().setSumInsured(0.0);
						} else {
							coverDetailsVO.getSumInsured().setSumInsured(optionalCovers.getCoverageLimit().doubleValue());
						}
						coverDetailsVO.getRiskCodes().setBasicRskCode(optionalCovers.getRiskDetails().getBasicRskCode());
						coverDetailsVO.getRiskCodes().setRiskCode(optionalCovers.getRiskDetails().getRiskCode());
						coverDetailsVO.getRiskCodes().setRiskType(optionalCovers.getRiskDetails().getRiskType());
						coverDetailsVO.getRiskCodes().setRiskCat(optionalCovers.getRiskDetails().getRiskCat());
						coverDetailsVO.setMandatoryIndicator(Boolean.FALSE);
						travelPkgVo.getCovers().add(coverDetailsVO);
						
					}
				}
			travelPkgList.add(travelPkgVo);
			travelInsuranceVO.setTravelPackageList(travelPkgList);
			/*if(updateTravelQuoteRequest.getTransactionDetails().getTariffCode().equals(products.getProductCode())) {
				travelPkgVo.setIsSelected(Boolean.TRUE);
			}else {
				travelPkgVo.setIsSelected(Boolean.FALSE);
			}*/
			travelPkgVo.setIsSelected(Boolean.TRUE);
			}
		}
		
	}


	
	/**
	 * FeesAndTaxesDetails
	 * 
	 * @param updateTravelQuoteRequest
	 * @param travelInsuranceVO
	 */

	private void mapFeesAndTaxes(Products products,TravelInsuranceVO travelInsuranceVO) {
		
		if (Utils.isEmpty(products.getFeesAndTaxes())) {
			products.setFeesAndTaxes(new FeesAndTaxes());
		}
		if(!Utils.isEmpty(products.getFeesAndTaxes()) && !Utils.isEmpty(travelInsuranceVO.getPremiumVO())) {
		
		if(!Utils.isEmpty(products.getFeesAndTaxes().getLoadingOrDiscountPercent())) {
			travelInsuranceVO.getPremiumVO().setDiscOrLoadPerc(products.getFeesAndTaxes().getLoadingOrDiscountPercent().doubleValue());
		}
		if(!Utils.isEmpty(products.getFeesAndTaxes().getLoadingOrDiscountAmount())) {
			travelInsuranceVO.getPremiumVO().setDiscOrLoadAmt(products.getFeesAndTaxes().getLoadingOrDiscountAmount());
		}
		if(!Utils.isEmpty(products.getFeesAndTaxes().getPromoCodeDiscountPercent())) {
			travelInsuranceVO.getPremiumVO().setPromoDiscPerc(products.getFeesAndTaxes().getPromoCodeDiscountPercent().doubleValue());
		}
		if(!Utils.isEmpty(products.getFeesAndTaxes().getPromoCodeDiscountAmount())) {
				//	mapping is remaining for promo code discount amount
		}
		if(!Utils.isEmpty(products.getFeesAndTaxes().getGovtTaxPercent())) {
			travelInsuranceVO.getPremiumVO().setGovtTax(products.getFeesAndTaxes().getGovtTaxPercent().doubleValue());
		}
		if(!Utils.isEmpty(products.getFeesAndTaxes().getVatRatePercent())) {
			travelInsuranceVO.getPremiumVO().setVatTaxPerc(products.getFeesAndTaxes().getVatRatePercent().doubleValue());
		}
		if(!Utils.isEmpty(products.getFeesAndTaxes().getVatAmount())) {
			travelInsuranceVO.getPremiumVO().setVatTax(products.getFeesAndTaxes().getVatAmount().doubleValue());
		}
		if(!Utils.isEmpty(products.getFeesAndTaxes().getPolicyFees())) {
			travelInsuranceVO.getPremiumVO().setPolicyFees(products.getFeesAndTaxes().getPolicyFees().doubleValue());
		}
		travelInsuranceVO.getPremiumVO().setVatCode(1);
	}
	}

	
	/**
	 * Travellers Details or UnderWritingQuestionsDetails
	 * 
	 * @param updateTravelQuoteRequest
	 * @param travelInsuranceVO
	 * @throws ParseException 
	 */
	private void mapTravellersDetails(UpdateTravelQuoteRequest updateTravelQuoteRequest,
			TravelInsuranceVO travelInsuranceVO) throws ParseException {
		List<TravelerDetailsVO> travelerDetailsVOList = new ArrayList();
		//Date vsdDate = getDateDiffr(updateTravelQuoteRequest.getTransactionDetails().getEffectiveDate());
		for (Travellers travellers : updateTravelQuoteRequest.getTravellers()) {
			TravelerDetailsVO traveller = new TravelerDetailsVO();
			traveller.setName(travellers.getTravellerName());
			traveller.setDateOfBirth(travellers.getTravellerDOB());
			traveller.setRelation(travellers.getRelation()); // Self
			traveller.setNationality(travellers.getTravellerNationality().shortValue());
			if(!Utils.isEmpty(travellers.getTravellerId())) {
				traveller.setGprId(travellers.getTravellerId());
			}			
			//traveller.setVsd(vsdDate);
			if (updateTravelQuoteRequest.getUnderWritingQuestions().getInclUsaCa()) {
				travelInsuranceVO.getTravelDetailsVO().setTravelLocation("Worldwide including USA and Canada");
			} else {
				travelInsuranceVO.getTravelDetailsVO().setTravelLocation("Worldwide excluding USA and Canada");
			}
			travelerDetailsVOList.add(traveller);
		}
		travelInsuranceVO.getTravelDetailsVO().setTravelerDetailsList(travelerDetailsVOList);
	}
	
	/**
	 * UnderWritingQuestions Details
	 * 
	 * @param updateTravelQuoteRequest
	 * @param travelInsuranceVO
	 */
	private void mapUnderWritingQuestions(UpdateTravelQuoteRequest updateTravelQuoteRequest,
			TravelInsuranceVO travelInsuranceVO) {
		//Sonar Fix for removing Methods should not be empty
		//TODO
		// To be implemented later
	}
	
	/**
	 * Partner Details
	 * 
	 * @param updateTravelQuoteRequest
	 * @param travelInsuranceVO
	 */
	private void mapPartnerDetails(UpdateTravelQuoteRequest updateTravelQuoteRequest,
			TravelInsuranceVO travelInsuranceVO) {
		travelInsuranceVO.setCommission(updateTravelQuoteRequest.getPartnerDetails().getCommissionPercentage().doubleValue()); // CommissionPercentage
		travelInsuranceVO.setCommission(updateTravelQuoteRequest.getPartnerDetails().getCommissionAmount().doubleValue()); // CommissionAmount
	}
	
	/**
	 * Transaction Details (Source of bus and Scheme Details)
	 * 
	 * @param updateTravelQuoteRequest
	 * @param travelInsuranceVO
	 */
	private void mapTransactionDetails(UpdateTravelQuoteRequest updateTravelQuoteRequest,
			TravelInsuranceVO travelInsuranceVO) {
		travelInsuranceVO.setPolicyClassCode(updateTravelQuoteRequest.getTransactionDetails().getClassCode()); // Class Code
		travelInsuranceVO.setPolicyType(updateTravelQuoteRequest.getTransactionDetails().getPolicyTypeCode()); // Policy Type Code
		travelInsuranceVO.setPolicyTerm(updateTravelQuoteRequest.getTransactionDetails().getPolicyTerm()); // Policy Term
		travelInsuranceVO.getScheme().setEffDate(updateTravelQuoteRequest.getTransactionDetails().getEffectiveDate());
		/*int policyTerm = updateTravelQuoteRequest.getTransactionDetails().getPolicyTerm();
		Date effectiveDate = updateTravelQuoteRequest.getTransactionDetails().getEffectiveDate();
		try {
			Date expiryDate = getDateDiffr(policyTerm,effectiveDate);
			travelInsuranceVO.getScheme().setExpiryDate(expiryDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}*/
		travelInsuranceVO.getScheme().setExpiryDate(updateTravelQuoteRequest.getTransactionDetails().getExpiryDate());
		travelInsuranceVO.getScheme().setSchemeCode(updateTravelQuoteRequest.getTransactionDetails().getSchemeCode());
		travelInsuranceVO.getScheme().setTariffCode(updateTravelQuoteRequest.getTransactionDetails().getTariffCode());
		travelInsuranceVO.getGeneralInfo().getSourceOfBus().setDistributionChannel(updateTravelQuoteRequest.getTransactionDetails().getDistChannel());
		if (updateTravelQuoteRequest.getTransactionDetails().getDirectSubAgent() == null){
			travelInsuranceVO.getGeneralInfo().getSourceOfBus().setDirectSubAgent(null);
		}
		else{
			travelInsuranceVO.getGeneralInfo().getSourceOfBus().setDirectSubAgent(updateTravelQuoteRequest.getTransactionDetails().getDirectSubAgent().longValue());
		}
		travelInsuranceVO.getGeneralInfo().getSourceOfBus().setCustomerSource(updateTravelQuoteRequest.getTransactionDetails().getBusinessSource().toString());
		travelInsuranceVO.getGeneralInfo().getSourceOfBus().setPromoCode(updateTravelQuoteRequest.getTransactionDetails().getPromocode());
		updateTravelQuoteRequest.getTransactionDetails().getPartnerTrnReferenceNumber(); // remaining
	}
	
	// To calculate the Expiry date based on (Type of Trip) and (Effective Date)
	private Date getDateDiffr(Date effectiveDate) throws ParseException {
		Calendar c = Calendar.getInstance();
		// Setting the date to the given date
		c.setTime(effectiveDate);
		c.add(Calendar.DATE, -1);
		Date vsdDate = c.getTime();
		return vsdDate;
	}


	/**
	 * CustomerDetails or Insured Information Details
	 * 
	 * @param updateTravelQuoteRequest
	 * @param travelInsuranceVO
	 */
	private void mapGeneralInsuranceDetails(UpdateTravelQuoteRequest updateTravelQuoteRequest,
			TravelInsuranceVO travelInsuranceVO) {
		travelInsuranceVO.getGeneralInfo().getInsured().setEmailId(updateTravelQuoteRequest.getCustomerDetails().getEmailId());
		travelInsuranceVO.getGeneralInfo().getInsured().setMobileNo(updateTravelQuoteRequest.getCustomerDetails().getMobileNo());
		travelInsuranceVO.getGeneralInfo().getInsured().setInsuredCode(updateTravelQuoteRequest.getCustomerDetails().getInsuredId());
		travelInsuranceVO.getGeneralInfo().getInsured().setFirstName(updateTravelQuoteRequest.getCustomerDetails().getFirstName());
		travelInsuranceVO.getGeneralInfo().getInsured().setLastName(updateTravelQuoteRequest.getCustomerDetails().getLastName());
		travelInsuranceVO.getGeneralInfo().getInsured().getAddress().setPoBox(updateTravelQuoteRequest.getCustomerDetails().getPoBox());
		travelInsuranceVO.getGeneralInfo().getInsured().setCity(updateTravelQuoteRequest.getCustomerDetails().getCity());
		travelInsuranceVO.getGeneralInfo().getInsured().getAddress().setEmirates(updateTravelQuoteRequest.getCustomerDetails().getNationality()); // Emirates
		travelInsuranceVO.getGeneralInfo().getInsured().getAddress().setCountry(Integer.parseInt(updateTravelQuoteRequest.getCustomerDetails().getNationalID())); // Country
		travelInsuranceVO.getGeneralInfo().getInsured().setVatRegNo(updateTravelQuoteRequest.getCustomerDetails().getVatRegNo());
		travelInsuranceVO.getGeneralInfo().getInsured().setRoyaltyType(updateTravelQuoteRequest.getCustomerDetails().getRewardProgrammeType()); // Reward Programmme
		travelInsuranceVO.getGeneralInfo().getInsured().setGuestCardNo(updateTravelQuoteRequest.getCustomerDetails().getRewardCardNumber()); // Guest Card No
		
	}

	/**
	 * Quotation Details
	 * 
	 * @param updateTravelQuoteRequest
	 * @param travelInsuranceVO
	 */
	private void mapQuotationDetails(UpdateTravelQuoteRequest updateTravelQuoteRequest,
			TravelInsuranceVO travelInsuranceVO) {
		travelInsuranceVO.getCommonVO().setQuoteNo(updateTravelQuoteRequest.getQuotationNo().longValue());
		travelInsuranceVO.getCommonVO().setEndtId(updateTravelQuoteRequest.getEndtId().longValue());
		travelInsuranceVO.getCommonVO().setEndtNo(updateTravelQuoteRequest.getEndtNo().longValue());
		travelInsuranceVO.getCommonVO().setPolicyId(updateTravelQuoteRequest.getPolicyId().longValue());
		travelInsuranceVO.getCommonVO().setStatus(updateTravelQuoteRequest.getQuoteStatus());
		travelInsuranceVO.getCommonVO().setAppFlow(Flow.EDIT_QUO);
	}

	/**
	 * Initialize objects
	 * 
	 * @param travelInsuranceVO
	 */
	private void initialiseObjects(TravelInsuranceVO travelInsuranceVO) {
		if (Utils.isEmpty(travelInsuranceVO.getScheme())) {
			travelInsuranceVO.setScheme(new SchemeVO());
		}
		if (Utils.isEmpty(travelInsuranceVO.getPremiumVO())) {
			travelInsuranceVO.setPremium(new PremiumVO());
		}
		if (Utils.isEmpty(travelInsuranceVO.getTravelDetailsVO())) {
			travelInsuranceVO.setTravelDetailsVO(new TravelDetailsVO());
		}
		if (Utils.isEmpty(travelInsuranceVO.getCommonVO())) {
			travelInsuranceVO.setCommonVO(new CommonVO());
		}
		if (Utils.isEmpty(travelInsuranceVO.getGeneralInfo())) {
			travelInsuranceVO.setGeneralInfo(new GeneralInfoVO());
		}
		if (Utils.isEmpty(travelInsuranceVO.getGeneralInfo().getInsured())) {
			travelInsuranceVO.getGeneralInfo().setInsured(new InsuredVO());
		}
		if (Utils.isEmpty(travelInsuranceVO.getGeneralInfo().getSourceOfBus())) {
			travelInsuranceVO.getGeneralInfo().setSourceOfBus(new SourceOfBusinessVO());
		}
		if (Utils.isEmpty(travelInsuranceVO.getGeneralInfo().getInsured().getAddress())) {
			travelInsuranceVO.getGeneralInfo().getInsured().setAddress(new AddressVO());
		}
		if (Utils.isEmpty(travelInsuranceVO.getPremiumVO())) {
			travelInsuranceVO.setPremiumVO(new PremiumVO());
		}
	}
}
