package com.rsaame.pas.b2c.ws.mapper;

import java.util.ArrayList;
import java.util.List;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.b2c.ws.beans.BuildingDetails;
import com.rsaame.pas.b2c.ws.beans.Cover;
import com.rsaame.pas.b2c.ws.beans.CoverDetails;
import com.rsaame.pas.b2c.ws.beans.GeneralInsuranceDetails;
import com.rsaame.pas.b2c.ws.beans.HomeInsuranceDetails;
import com.rsaame.pas.b2c.ws.beans.InsuredDetails;
import com.rsaame.pas.b2c.ws.beans.PaymentDetails;
import com.rsaame.pas.b2c.ws.beans.PremiumDetails;
import com.rsaame.pas.b2c.ws.beans.RiskDetails;
import com.rsaame.pas.b2c.ws.beans.SchemeDetails;
import com.rsaame.pas.b2c.ws.beans.SumInsuredDetails;
import com.rsaame.pas.b2c.ws.beans.TravelInsuranceDetails;
import com.rsaame.pas.b2c.ws.beans.TravelPackageDetails;
import com.rsaame.pas.b2c.ws.beans.UWQuestionDetails;
import com.rsaame.pas.vo.bus.BuildingDetailsVO;
import com.rsaame.pas.vo.bus.CoverDetailsVO;
import com.rsaame.pas.vo.bus.GeneralInfoVO;
import com.rsaame.pas.vo.bus.HomeInsuranceVO;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.bus.SchemeVO;
import com.rsaame.pas.vo.bus.SumInsuredVO;
import com.rsaame.pas.vo.bus.TravelInsuranceVO;
import com.rsaame.pas.vo.bus.TravelPackageVO;
import com.rsaame.pas.vo.bus.UWQuestionVO;
import com.rsaame.pas.vo.cmn.CoverVO;
import com.rsaame.pas.vo.cmn.RiskVO;

public class HomeInsuranceDetailsMapper {

	/**
	 * @param homeInsuranceDetails
	 * @param homeInsuranceVO
	 */
	public void mapHomeInsuranceDetailsToHomeInsuranceVO(HomeInsuranceDetails homeInsuranceDetails,HomeInsuranceVO homeInsuranceVO) {
		
		mapBuildingDetails(homeInsuranceDetails, homeInsuranceVO);
		mapCovers(homeInsuranceDetails, homeInsuranceVO);
		
		homeInsuranceDetails.setCreated(homeInsuranceVO.getCreated());
		homeInsuranceDetails.setEndDate(homeInsuranceVO.getEndDate());
		homeInsuranceDetails.setEndEffectiveDate(homeInsuranceVO.getEndEffectiveDate());
		homeInsuranceDetails.setEndStartDate(homeInsuranceVO.getEndStartDate());
		homeInsuranceDetails.setEndtEffectiveDate(homeInsuranceVO.getEndEffectiveDate());
		homeInsuranceDetails.setEndtId(homeInsuranceVO.getEndtId());
		homeInsuranceDetails.setEndtNo(homeInsuranceVO.getEndtNo());
		//homeInsuranceDetails.setInsuredChanged();	
		homeInsuranceDetails.setIsQuote(homeInsuranceVO.getIsQuote());
		homeInsuranceDetails.setLocCode(homeInsuranceVO.getCommonVO().getLocCode());
		homeInsuranceDetails.setPolCustomerId(homeInsuranceVO.getPolCustomerId());
		homeInsuranceDetails.setPolDocumentId(homeInsuranceVO.getPolDocumentId());
		homeInsuranceDetails.setPolEffectiveDate(homeInsuranceVO.getCommonVO().getPolEffectiveDate());	
		homeInsuranceDetails.setPolExchangeRate(homeInsuranceVO.getPolExchangeRate());	
		homeInsuranceDetails.setPolExpiryDate(homeInsuranceVO.getPolExpiryDate());
		homeInsuranceDetails.setPolicyClassCode(homeInsuranceVO.getPolicyClassCode());
		homeInsuranceDetails.setPolicyExtended(homeInsuranceVO.isPolicyExtended());	
		homeInsuranceDetails.setPolicyExtensionPeriod(homeInsuranceVO.getPolicyExtensionPeriod());
		homeInsuranceDetails.setPolicyNo(homeInsuranceVO.getPolicyNo());
		homeInsuranceDetails.setPolicyTerm(homeInsuranceVO.getPolicyTerm());
		homeInsuranceDetails.setPolicyType(homeInsuranceVO.getPolicyType());
		homeInsuranceDetails.setProcessedDate(homeInsuranceVO.getProcessedDate());
		homeInsuranceDetails.setQuoteNo(homeInsuranceVO.getCommonVO().getQuoteNo());
		/*homeInsuranceDetails.setSectionId();*/
		homeInsuranceDetails.setStartDate(homeInsuranceVO.getStartDate());
		homeInsuranceDetails.setStatus(homeInsuranceVO.getStatus());
		homeInsuranceDetails.setValidityStartDate(homeInsuranceVO.getValidityStartDate());
		homeInsuranceDetails.setVsd(homeInsuranceVO.getCommonVO().getVsd());
		homeInsuranceDetails.setPolicyId(homeInsuranceVO.getCommonVO().getPolicyId());
		homeInsuranceDetails.setPolicyNo(homeInsuranceVO.getCommonVO().getPolicyNo());
		homeInsuranceDetails.setEndtId(homeInsuranceVO.getCommonVO().getEndtId());
		homeInsuranceDetails.setEndtNo(homeInsuranceVO.getCommonVO().getEndtNo());
		
		mapGeneralInsuranceDetails(homeInsuranceDetails, homeInsuranceVO);
		mapPremiumDetails(homeInsuranceDetails, homeInsuranceVO);
		mapSchemeDetails(homeInsuranceDetails, homeInsuranceVO);
		mapPaymentDetails(homeInsuranceDetails, homeInsuranceVO);
		
	}
	
	private void mapPaymentDetails(HomeInsuranceDetails homeInsuranceDetails,HomeInsuranceVO homeInsuranceVO) {
		
		PaymentDetails paymentDetails = new PaymentDetails();
		
		if (!Utils.isEmpty(homeInsuranceVO.getPaymentVO())) {
			paymentDetails.setAmount(homeInsuranceVO.getPaymentVO()
					.getAmount());
			paymentDetails.setBankCode(homeInsuranceVO.getPaymentVO()
					.getBankCode());
			paymentDetails.setChequeDt(homeInsuranceVO.getPaymentVO()
					.getChequeDt());
			paymentDetails.setChequeNo(homeInsuranceVO.getPaymentVO()
					.getChequeNo());
			paymentDetails.setCreditCardNo(homeInsuranceVO.getPaymentVO()
					.getCreditCardNo());
			paymentDetails.setExpiryDate(homeInsuranceVO.getPaymentVO()
					.getExpiryDate());
			/*paymentDetails.setPaymentDone(homeInsuranceVO.getPaymentVO()
					.isPaymentDone());*/
			paymentDetails.setPaymentMode(homeInsuranceVO.getPaymentVO()
					.getPaymentMode());
			paymentDetails.setPremium(homeInsuranceVO.getPaymentVO()
					.getPremium());
		}
		homeInsuranceDetails.setPaymentVO(paymentDetails);
	}
	
	/**
	 * @param travelInsuranceVO
	 * @param travelInsuranceDetails
	 */
	private void mapSchemeDetails(HomeInsuranceDetails homeInsuranceDetails,HomeInsuranceVO homeInsuranceVO) {
		
		SchemeVO schemeVO =  homeInsuranceVO.getScheme();
		
		SchemeDetails schemeDetails = new SchemeDetails();
		schemeDetails.setEffDate(schemeVO.getEffDate());
		schemeDetails.setExpiryDate(schemeVO.getExpiryDate());
		/*schemeDetails.setId(schemeVO.getId());*/
		//schemeDetails.setPolicyCode(schemeVO.getPolicyCode());
		schemeDetails.setPolicyType(schemeVO.getPolicyType());
		schemeDetails.setSchemeCode(schemeVO.getSchemeCode());
		schemeDetails.setSchemeName(schemeVO.getSchemeName());
		schemeDetails.setTariffCode(schemeVO.getTariffCode());
		schemeDetails.setTariffName(schemeVO.getTariffName());
		/*schemeDetails.setTariffRateType(schemeVO.getTariffRateType());*/
		
		homeInsuranceDetails.setScheme(schemeDetails);
	}
	
	/**
	 * @param homeInsuranceDetails
	 * @param homeInsuranceVO
	 */
	private void mapPremiumDetails(HomeInsuranceDetails homeInsuranceDetails,HomeInsuranceVO homeInsuranceVO) {
		
		PremiumVO premiumVO =  homeInsuranceVO.getPremiumVO();
		PremiumDetails premiumDetails = new PremiumDetails();
		
		premiumDetails.setCurrency(premiumVO.getCurrency());
		premiumDetails.setDiscOrLoadAmt(premiumVO.getDiscOrLoadAmt());
		premiumDetails.setDiscOrLoadPerc(premiumVO.getDiscOrLoadPerc());
		premiumDetails.setGovtTax(premiumVO.getGovtTax());
		premiumDetails.setLossRatio(premiumVO.getLossRatio());
		premiumDetails.setMinPremium(premiumVO.getMinPremium());
		premiumDetails.setPolicyFees(premiumVO.getPolicyFees());
		premiumDetails.setPremiumAmt(premiumVO.getPremiumAmt());
		premiumDetails.setPremiumAmtActual(premiumVO.getPremiumAmtActual());
		premiumDetails.setPromoDiscPerc(premiumVO.getPromoDiscPerc());
		premiumDetails.setSpecialDiscount(premiumVO.getSpecialDiscount());
		
		homeInsuranceDetails.setPremiumVO(premiumDetails);
	}
	

	/**
	 * @param homeInsuranceDetails
	 * @param homeInsuranceVO
	 */
	private void mapGeneralInsuranceDetails(HomeInsuranceDetails homeInsuranceDetails,HomeInsuranceVO homeInsuranceVO) {
		
		GeneralInsuranceDetails generalInsuranceDetails = new GeneralInsuranceDetails();
		InsuredDetails insuredDetails = new InsuredDetails();
		GeneralInfoVO generalInfoVO = homeInsuranceVO.getGeneralInfo();
		
		insuredDetails.setAddress(generalInfoVO.getInsured().getAddress().getAddress());
		insuredDetails.setCity(generalInfoVO.getInsured().getCity());
		insuredDetails.setCountry(generalInfoVO.getInsured().getAddress().getCountry());
		insuredDetails.setEmailId(generalInfoVO.getInsured().getEmailId());
		insuredDetails.setEmirates(generalInfoVO.getInsured().getAddress().getEmirates());
		insuredDetails.setFaxNumber(generalInfoVO.getAdditionalInfo().getFaxNumber());
		insuredDetails.setFirstName(generalInfoVO.getInsured().getFirstName());
		insuredDetails.setHowDidYouHear(generalInfoVO.getInsured().getHowDidYouHear());
		insuredDetails.setInsuredCode(generalInfoVO.getInsured().getInsuredCode());
		insuredDetails.setInsuredId(generalInfoVO.getInsured().getInsuredId());
		insuredDetails.setInsuredStatus(generalInfoVO.getAdditionalInfo().getInsuredStatus());
		insuredDetails.setLastName(generalInfoVO.getInsured().getLastName());
		insuredDetails.setMobileNo(generalInfoVO.getInsured().getMobileNo());
		insuredDetails.setName(generalInfoVO.getInsured().getName());
		insuredDetails.setNationality(generalInfoVO.getInsured().getNationality());
		insuredDetails.setPhoneNo(generalInfoVO.getInsured().getPhoneNo());
		insuredDetails.setPlaceOfEst(generalInfoVO.getAdditionalInfo().getPlaceOfEst());
		insuredDetails.setPoBox(generalInfoVO.getInsured().getAddress().getPoBox());
		insuredDetails.setPolicyId(generalInfoVO.getAdditionalInfo().getPolicyId());
		insuredDetails.setWebsite(generalInfoVO.getAdditionalInfo().getWebsite());
		
		generalInsuranceDetails.setInsured(insuredDetails);
		
		
		UWQuestionDetails uwQuestionDetails = new UWQuestionDetails();
		List<String> uwList = new ArrayList<String>();
		if(!Utils.isEmpty(homeInsuranceVO.getGeneralInfo().getQuestionsVO()) &&
				!Utils.isEmpty(homeInsuranceVO.getGeneralInfo().getQuestionsVO().getQuestions())){
			for(UWQuestionVO uwQuestionsVO : homeInsuranceVO.getGeneralInfo().getQuestionsVO().getQuestions()){
				uwList.add(uwQuestionsVO.getQDesc());
			}
		}
		uwQuestionDetails.setQuestions(uwList);
		generalInsuranceDetails.setQuestionsVO(uwQuestionDetails);
		
		homeInsuranceDetails.setGeneralInfo(generalInsuranceDetails);
	}
	
	/**
	 * @param homeInsuranceDetails
	 * @param homeInsuranceVO
	 */
	private void mapBuildingDetails(HomeInsuranceDetails homeInsuranceDetails,HomeInsuranceVO homeInsuranceVO) {
		
		BuildingDetails buildingDetails = new BuildingDetails();
		BuildingDetailsVO buildingDetailsVO = homeInsuranceVO.getBuildingDetails();
		
		buildingDetails.setArea(buildingDetailsVO.getArea());
		buildingDetails.setBuildingname(buildingDetailsVO.getBuildingname());
		buildingDetails.setCurrency(buildingDetailsVO.getCurrency());
		buildingDetails.setDiscOrLoadAmt(buildingDetailsVO.getDiscOrLoadAmt());
		buildingDetails.setDiscOrLoadPerc(buildingDetailsVO.getDiscOrLoadPerc());
		buildingDetails.setEmirates(buildingDetailsVO.getEmirates());
		buildingDetails.setFlatVillaNo(buildingDetailsVO.getFlatVillaNo());
		buildingDetails.setGeoAreaCode(buildingDetailsVO.getGeoAreaCode());
		buildingDetails.setGovtTax(buildingDetailsVO.getGovtTax());
		buildingDetails.setLoading(buildingDetailsVO.isLoading());
		buildingDetails.setLossRatio(buildingDetailsVO.getLossRatio());
		buildingDetails.setMinPremium(buildingDetailsVO.getMinPremium());
		buildingDetails.setMortgageeName(buildingDetailsVO.getMortgageeName());
		buildingDetails.setOtherDetails(buildingDetailsVO.getOtherDetails());
		buildingDetails.setOwnershipStatus(buildingDetailsVO.getOwnershipStatus());
		buildingDetails.setPolicyFees(buildingDetailsVO.getPolicyFees());
		buildingDetails.setPremiumAmt(buildingDetailsVO.getPremiumAmt());
		buildingDetails.setPremiumAmtActual(buildingDetailsVO.getPremiumAmtActual());
		buildingDetails.setPromoDiscPerc(buildingDetailsVO.getPromoDiscPerc());
		buildingDetails.setSpecialDiscount(buildingDetailsVO.getSpecialDiscount());
		buildingDetails.setTypeOfProperty(buildingDetailsVO.getTypeOfProperty());
		buildingDetails.setVsd(buildingDetailsVO.getVsd());
		
		
		//cover
		if (buildingDetailsVO.getCoverCodes()!=null) {
			
			Cover coverVO = new Cover();
			coverVO.setCovCode(buildingDetailsVO.getCoverCodes().getCovCode());
			coverVO.setCovCriteriaCode(buildingDetailsVO.getCoverCodes()
					.getCovCriteriaCode());
			coverVO.setCovSubTypeCode(buildingDetailsVO.getCoverCodes()
					.getCovSubTypeCode());
			coverVO.setCovTypeCode(buildingDetailsVO.getCoverCodes()
					.getCovTypeCode());
			buildingDetails.setCoverCodes(coverVO);
		}
		
		//suminsured
		if (buildingDetailsVO.getSumInsured() != null) {
			
			SumInsuredDetails sumInsuredVO = new SumInsuredDetails();
			//sumInsuredVO.setaDesc(buildingDetailsVO.getSumInsured().getaDesc());
			sumInsuredVO.setCash_Id(buildingDetailsVO.getSumInsured()
					.getCash_Id());
			sumInsuredVO.setDeductible(buildingDetailsVO.getSumInsured()
					.getDeductible());
			//sumInsuredVO.seteDesc(buildingDetailsVO.getSumInsured().geteDesc());
			sumInsuredVO.setIdentifier(buildingDetailsVO.getSumInsured()
					.getIdentifier());
			sumInsuredVO.setPromoCover(buildingDetailsVO.getSumInsured()
					.isPromoCover());
			sumInsuredVO.setSumInsured(buildingDetailsVO.getSumInsured()
					.getSumInsured());
			sumInsuredVO.setVsd(buildingDetailsVO.getSumInsured().getVsd());
			buildingDetails.setSumInsured(sumInsuredVO);
		}
		
		
		//risk
		if (buildingDetailsVO.getRiskCodes()!=null) {
			RiskDetails riskVO = new RiskDetails();
			riskVO.setBasicRskCode(buildingDetailsVO.getRiskCodes()
					.getBasicRskCode());
			riskVO.setBasicRskId(buildingDetailsVO.getRiskCodes().getBasicRskId());
			riskVO.setRiskCat(buildingDetailsVO.getRiskCodes().getRiskCat());
			riskVO.setRiskCode(buildingDetailsVO.getRiskCodes().getRiskCode());
			riskVO.setRiskType(buildingDetailsVO.getRiskCodes().getRiskType());
			riskVO.setRskId(buildingDetailsVO.getRiskCodes().getRskId());
			buildingDetails.setRiskCodes(riskVO);
		}
		
		homeInsuranceDetails.setBuildingDetails(buildingDetails);
	}
	
	/**
	 * @param homeInsuranceDetails
	 * @param homeInsuranceVO
	 */
	private void mapCovers(HomeInsuranceDetails homeInsuranceDetails,HomeInsuranceVO homeInsuranceVO) {
		
		List<CoverDetails> coverDetailsList = new ArrayList<CoverDetails>();
		CoverDetails coverdDetails = null;
		RiskDetails riskDetails = null;
		Cover cover = null;
		SumInsuredDetails sumInsuredDetails = null;
		
		for(CoverDetailsVO coverDetailsVO : homeInsuranceVO.getCovers()){
			coverdDetails = new CoverDetails();
			
			cover = new Cover();
			cover.setCovCode(coverDetailsVO.getCoverCodes().getCovCode());
			cover.setCovCriteriaCode(coverDetailsVO.getCoverCodes().getCovCriteriaCode());
			cover.setCovSubTypeCode(coverDetailsVO.getCoverCodes().getCovSubTypeCode());
			cover.setCovTypeCode(coverDetailsVO.getCoverCodes().getCovTypeCode());
			
			coverdDetails.setCoverCodes(cover);
			coverdDetails.setCoverDesc(coverDetailsVO.getCoverDesc());
			coverdDetails.setCoverName(coverDetailsVO.getCoverName());
			coverdDetails.setCurrency(coverDetailsVO.getCurrency());
			coverdDetails.setDiscOrLoadAmt(coverDetailsVO.getDiscOrLoadAmt());
			coverdDetails.setDiscOrLoadPerc(coverDetailsVO.getDiscOrLoadPerc());
			//coverdDetails.setGovtTax(coverDetailsVO.getGovtTax());
			coverdDetails.setIsCovered(coverDetailsVO.getIsCovered());
			//coverdDetails.setLoading(coverDetailsVO.isLoading());
			//coverdDetails.setLossRatio(coverDetailsVO.getLossRatio());
			//coverdDetails.setMandatoryIndicator(coverDetailsVO.getMandatoryIndicator());
			//coverdDetails.setMappingField(coverDetailsVO.getMappingField());
			coverdDetails.setMinPremium(coverDetailsVO.getMinPremium());
			//coverdDetails.setPolicyFees(coverDetailsVO.getPolicyFees());
			coverdDetails.setPremiumAmt(coverDetailsVO.getPremiumAmt());
			coverdDetails.setPremiumAmtActual(coverDetailsVO.getPremiumAmtActual());
			coverdDetails.setPromoDiscPerc(coverDetailsVO.getPromoDiscPerc());
			
			riskDetails = new RiskDetails();
			riskDetails.setBasicRskCode(coverDetailsVO.getRiskCodes().getBasicRskCode());
			riskDetails.setBasicRskId(coverDetailsVO.getRiskCodes().getBasicRskId());
			riskDetails.setRiskCat(coverDetailsVO.getRiskCodes().getRiskCat());
			riskDetails.setRiskCode(coverDetailsVO.getRiskCodes().getRiskCode());
			riskDetails.setRiskType(coverDetailsVO.getRiskCodes().getRiskType());
			riskDetails.setRskId(coverDetailsVO.getRiskCodes().getRskId());
			
			
			coverdDetails.setRiskCodes(riskDetails);
			//coverdDetails.setSpecialDiscount(coverDetailsVO.getSpecialDiscount());
			
			sumInsuredDetails = new SumInsuredDetails();
			//sumInsuredDetails.setaDesc(coverDetailsVO.getSumInsured().getaDesc());
			sumInsuredDetails.setCash_Id(coverDetailsVO.getSumInsured().getCash_Id());
			sumInsuredDetails.setDeductible(coverDetailsVO.getSumInsured().getDeductible());
			//sumInsuredDetails.seteDesc(coverDetailsVO.getSumInsured().geteDesc());
			sumInsuredDetails.setIdentifier(coverDetailsVO.getSumInsured().getIdentifier());
			sumInsuredDetails.setPromoCover(coverDetailsVO.getSumInsured().isPromoCover());
			sumInsuredDetails.setSumInsured(coverDetailsVO.getSumInsured().getSumInsured());
			sumInsuredDetails.setVsd(coverDetailsVO.getSumInsured().getVsd());
			
			coverdDetails.setSumInsured(sumInsuredDetails);
			coverdDetails.setTariffCode(coverDetailsVO.getTariffCode());
			coverdDetails.setVsd(coverDetailsVO.getVsd());
			
			coverDetailsList.add(coverdDetails);
		}
		
		homeInsuranceDetails.setCovers(coverDetailsList);

	}
}
