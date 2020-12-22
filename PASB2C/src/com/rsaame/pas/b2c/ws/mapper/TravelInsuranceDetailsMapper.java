package com.rsaame.pas.b2c.ws.mapper;

import java.util.ArrayList;
import java.util.List;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.b2c.ws.beans.Cover;
import com.rsaame.pas.b2c.ws.beans.CoverDetails;
import com.rsaame.pas.b2c.ws.beans.CoverDetailsList;
import com.rsaame.pas.b2c.ws.beans.GeneralInsuranceDetails;
import com.rsaame.pas.b2c.ws.beans.InsuredDetails;
import com.rsaame.pas.b2c.ws.beans.PaymentDetails;
import com.rsaame.pas.b2c.ws.beans.PremiumDetails;
import com.rsaame.pas.b2c.ws.beans.RiskDetails;
import com.rsaame.pas.b2c.ws.beans.SchemeDetails;
import com.rsaame.pas.b2c.ws.beans.SumInsuredDetails;
import com.rsaame.pas.b2c.ws.beans.TravelDetails;
import com.rsaame.pas.b2c.ws.beans.TravelInsuranceDetails;
import com.rsaame.pas.b2c.ws.beans.TravelPackage;
import com.rsaame.pas.b2c.ws.beans.TravelPackageDetails;
import com.rsaame.pas.b2c.ws.beans.TravelerDetails;
import com.rsaame.pas.b2c.ws.beans.UWQuestionDetails;
import com.rsaame.pas.vo.bus.CoverDetailsVO;
import com.rsaame.pas.vo.bus.GeneralInfoVO;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.bus.SchemeVO;
import com.rsaame.pas.vo.bus.TravelInsuranceVO;
import com.rsaame.pas.vo.bus.TravelPackageVO;
import com.rsaame.pas.vo.bus.TravelerDetailsVO;
import com.rsaame.pas.vo.bus.UWQuestionVO;

/**
 * @author m1020637
 * Maps TravelInsuranceVo to TravelDetails
 */
public class TravelInsuranceDetailsMapper {

	public void mapTravelInsuranceVOToTraveldetails(TravelInsuranceVO travelInsuranceVO,TravelInsuranceDetails travelInsuranceDetails) {
		
		travelInsuranceDetails.setCreated(travelInsuranceVO.getCreated());
		travelInsuranceDetails.setDefaultTariff(travelInsuranceVO.getDefaultTariff());
		travelInsuranceDetails.setEndDate(travelInsuranceVO.getEndDate());
		travelInsuranceDetails.setEndEffectiveDate(travelInsuranceVO.getEndEffectiveDate());
		travelInsuranceDetails.setEndStartDate(travelInsuranceVO.getEndStartDate());
		travelInsuranceDetails.setEndtEffectiveDate(travelInsuranceVO.getEndEffectiveDate());
		//travelInsuranceDetails.setInsuredChanged( false );	
		travelInsuranceDetails.setIsQuote(travelInsuranceVO.getIsQuote());
		//travelInsuranceDetails.setLocCode(travelInsuranceVO.getCommonVO().getLocCode());
		travelInsuranceDetails.setPolCustomerId(travelInsuranceVO.getPolCustomerId());
		travelInsuranceDetails.setPolDocumentId(travelInsuranceVO.getPolDocumentId());
		travelInsuranceDetails.setPolEffectiveDate(travelInsuranceVO.getCommonVO().getPolEffectiveDate());	
		travelInsuranceDetails.setPolExchangeRate(travelInsuranceVO.getPolExchangeRate());	
		travelInsuranceDetails.setPolExpiryDate(travelInsuranceVO.getPolExpiryDate());
		//travelInsuranceDetails.setPolicyClassCode(travelInsuranceVO.getPolicyClassCode());
		//travelInsuranceDetails.setPolicyExtended(travelInsuranceVO.isPolicyExtended());	
		travelInsuranceDetails.setPolicyExtensionPeriod(travelInsuranceVO.getPolicyExtensionPeriod());
		travelInsuranceDetails.setPolicyTerm(travelInsuranceVO.getPolicyTerm());
		travelInsuranceDetails.setPolicyType(travelInsuranceVO.getPolicyType());
		//travelInsuranceDetails.setPolLinkingId(travelInsuranceVO.getPolLinkingId());
		travelInsuranceDetails.setProcessedDate(travelInsuranceVO.getProcessedDate());
		travelInsuranceDetails.setQuoteNo(travelInsuranceVO.getQuoteNo());
		/*travelInsuranceDetails.setSectionId();*/
		travelInsuranceDetails.setStartDate(travelInsuranceVO.getStartDate());
		//travelInsuranceDetails.setStatus(travelInsuranceVO.getCommonVO().getStatus());
		travelInsuranceDetails.setValidityStartDate(travelInsuranceVO.getValidityStartDate());
		travelInsuranceDetails.setVsd(travelInsuranceVO.getCommonVO().getVsd());
		travelInsuranceDetails.setPolicyId(travelInsuranceVO.getCommonVO().getPolicyId());
		travelInsuranceDetails.setPolicyNo(travelInsuranceVO.getCommonVO().getPolicyNo());
		travelInsuranceDetails.setEndtId(travelInsuranceVO.getCommonVO().getEndtId());
		travelInsuranceDetails.setEndtNo(travelInsuranceVO.getCommonVO().getEndtNo());
		
		mapGeneralInsuranceDetails(travelInsuranceVO, travelInsuranceDetails);
		mapPremiumDetails(travelInsuranceVO, travelInsuranceDetails);
		mapSchemeDetails(travelInsuranceVO, travelInsuranceDetails);
		//mapPaymentDetails(travelInsuranceVO, travelInsuranceDetails);
		mapTravelDetails(travelInsuranceVO, travelInsuranceDetails);
		mapTravelPackageList(travelInsuranceVO, travelInsuranceDetails);
		
	}
	
	/**
	 * @param travelInsuranceVO
	 * @param travelInsuranceDetails
	 */
	private void mapTravelPackageList(TravelInsuranceVO travelInsuranceVO,TravelInsuranceDetails travelInsuranceDetails) {
		
		List<TravelPackageDetails> packagelist = new ArrayList<TravelPackageDetails>();
		TravelPackageDetails travelPackageDetails = null;
		
		for(TravelPackageVO travelPackageVO : travelInsuranceVO.getTravelPackageList()){
			
			travelPackageDetails = new TravelPackageDetails();
			mapCovers(travelPackageDetails, travelPackageVO);
			travelPackageDetails.setPackageName(travelPackageVO.getPackageName());
			//travelPackageDetails.setRecommended(travelPackageVO.getIsRecommended());
			travelPackageDetails.setTariffCode(travelPackageVO.getTariffCode());
			travelPackageDetails.setIsSelected(travelPackageVO.getIsSelected());
			packagelist.add(travelPackageDetails);
		}

		travelInsuranceDetails.setTravelPackageList(packagelist);
	}
	
	
	/**
	 * @param travelInsuranceVO
	 * @param travelInsuranceDetails
	 */
	public TravelPackage mapTravelPackage(List<TravelPackageVO> packageList) {
		
		TravelPackage travelPackage = new TravelPackage();
		
		List<TravelPackageDetails> packagelist = new ArrayList<TravelPackageDetails>();
		TravelPackageDetails travelPackageDetails = null;
		
		for(TravelPackageVO travelPackageVO : packageList){
			
			travelPackageDetails = new TravelPackageDetails();
			mapCovers(travelPackageDetails, travelPackageVO);
			travelPackageDetails.setPackageName(travelPackageVO.getPackageName());
			//travelPackageDetails.setRecommended(travelPackageVO.getIsRecommended());
			travelPackageDetails.setTariffCode(travelPackageVO.getTariffCode());
			travelPackageDetails.setIsSelected(travelPackageVO.getIsSelected());
			packagelist.add(travelPackageDetails);
		}

		
		travelPackage.setTravelPackageList(packagelist);
		
		return travelPackage;
	}
	
	private void mapCovers(TravelPackageDetails travelPackageDetails,TravelPackageVO travelPackageVO) {
		
		List<CoverDetails> coverDetailsList = new ArrayList<CoverDetails>();
		CoverDetails coverdDetails = null;
		RiskDetails riskDetails = null;
		Cover cover = null;
		SumInsuredDetails sumInsuredDetails = null;
		
		for(CoverDetailsVO coverDetailsVO : travelPackageVO.getCovers()){
			coverdDetails = new CoverDetails();
			
			if (coverDetailsVO.getCoverCodes()!=null) {
				cover = new Cover();
				cover.setCovCode(coverDetailsVO.getCoverCodes().getCovCode());
				cover.setCovCriteriaCode(coverDetailsVO.getCoverCodes()
						.getCovCriteriaCode());
				cover.setCovSubTypeCode(coverDetailsVO.getCoverCodes()
						.getCovSubTypeCode());
				cover.setCovTypeCode(coverDetailsVO.getCoverCodes()
						.getCovTypeCode());
				coverdDetails.setCoverCodes(cover);
			}
			
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
			
			if (coverDetailsVO.getRiskCodes()!=null) {
				riskDetails = new RiskDetails();
				riskDetails.setBasicRskCode(coverDetailsVO.getRiskCodes()
						.getBasicRskCode());
				riskDetails.setBasicRskId(coverDetailsVO.getRiskCodes()
						.getBasicRskId());
				riskDetails.setRiskCat(coverDetailsVO.getRiskCodes()
						.getRiskCat());
				riskDetails.setRiskCode(coverDetailsVO.getRiskCodes()
						.getRiskCode());
				riskDetails.setRiskType(coverDetailsVO.getRiskCodes()
						.getRiskType());
				riskDetails.setRskId(coverDetailsVO.getRiskCodes().getRskId());
				coverdDetails.setRiskCodes(riskDetails);
			}
			//coverdDetails.setSpecialDiscount(coverDetailsVO.getSpecialDiscount());
			
			if (coverDetailsVO.getSumInsured()!=null) {
				sumInsuredDetails = new SumInsuredDetails();
				/*sumInsuredDetails.setaDesc(coverDetailsVO.getSumInsured()
						.getaDesc());*/
				sumInsuredDetails.setCash_Id(coverDetailsVO.getSumInsured()
						.getCash_Id());
				sumInsuredDetails.setDeductible(coverDetailsVO.getSumInsured()
						.getDeductible());
				/*sumInsuredDetails.seteDesc(coverDetailsVO.getSumInsured()
						.geteDesc());*/
				sumInsuredDetails.setIdentifier(coverDetailsVO.getSumInsured()
						.getIdentifier());
				sumInsuredDetails.setPromoCover(coverDetailsVO.getSumInsured()
						.isPromoCover());
				sumInsuredDetails.setSumInsured(coverDetailsVO.getSumInsured()
						.getSumInsured());
				sumInsuredDetails.setVsd(coverDetailsVO.getSumInsured()
						.getVsd());
				coverdDetails.setSumInsured(sumInsuredDetails);
			}
			coverdDetails.setTariffCode(coverDetailsVO.getTariffCode());
			coverdDetails.setVsd(coverDetailsVO.getVsd());
			
			coverDetailsList.add(coverdDetails);
		}
		
		travelPackageDetails.setCovers(coverDetailsList);

	}
	
	
/** For product details ampping
 * @param coverDetailsVO
 * @return
 */
private CoverDetails mapCovers(CoverDetailsVO coverDetailsVO) {
		
		
		CoverDetails coverdDetails = null;
		RiskDetails riskDetails = null;
		Cover cover = null;
		SumInsuredDetails sumInsuredDetails = null;
		
			coverdDetails = new CoverDetails();
			
			if (coverDetailsVO.getCoverCodes()!=null) {
				cover = new Cover();
				cover.setCovCode(coverDetailsVO.getCoverCodes().getCovCode());
				cover.setCovCriteriaCode(coverDetailsVO.getCoverCodes()
						.getCovCriteriaCode());
				cover.setCovSubTypeCode(coverDetailsVO.getCoverCodes()
						.getCovSubTypeCode());
				cover.setCovTypeCode(coverDetailsVO.getCoverCodes()
						.getCovTypeCode());
				coverdDetails.setCoverCodes(cover);
			}
			
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
			
			if (coverDetailsVO.getRiskCodes()!=null) {
				riskDetails = new RiskDetails();
				riskDetails.setBasicRskCode(coverDetailsVO.getRiskCodes()
						.getBasicRskCode());
				riskDetails.setBasicRskId(coverDetailsVO.getRiskCodes()
						.getBasicRskId());
				riskDetails.setRiskCat(coverDetailsVO.getRiskCodes()
						.getRiskCat());
				riskDetails.setRiskCode(coverDetailsVO.getRiskCodes()
						.getRiskCode());
				riskDetails.setRiskType(coverDetailsVO.getRiskCodes()
						.getRiskType());
				riskDetails.setRskId(coverDetailsVO.getRiskCodes().getRskId());
				coverdDetails.setRiskCodes(riskDetails);
			}
			//coverdDetails.setSpecialDiscount(coverDetailsVO.getSpecialDiscount());
			
			if (coverDetailsVO.getSumInsured()!=null) {
				sumInsuredDetails = new SumInsuredDetails();
				/*sumInsuredDetails.setaDesc(coverDetailsVO.getSumInsured()
						.getaDesc());*/
				sumInsuredDetails.setCash_Id(coverDetailsVO.getSumInsured()
						.getCash_Id());
				sumInsuredDetails.setDeductible(coverDetailsVO.getSumInsured()
						.getDeductible());
				/*sumInsuredDetails.seteDesc(coverDetailsVO.getSumInsured()
						.geteDesc());*/
				sumInsuredDetails.setIdentifier(coverDetailsVO.getSumInsured()
						.getIdentifier());
				sumInsuredDetails.setPromoCover(coverDetailsVO.getSumInsured()
						.isPromoCover());
				sumInsuredDetails.setSumInsured(coverDetailsVO.getSumInsured()
						.getSumInsured());
				sumInsuredDetails.setVsd(coverDetailsVO.getSumInsured()
						.getVsd());
				coverdDetails.setSumInsured(sumInsuredDetails);
			}
			coverdDetails.setTariffCode(coverDetailsVO.getTariffCode());
			coverdDetails.setVsd(coverDetailsVO.getVsd());
			
			
		
			return coverdDetails;
	}


/**Prod details mapping
 * @param coverDetailsLstDB
 * @return
 */
public CoverDetailsList mapCoversList(com.rsaame.pas.vo.bus.CoverDetails coverDetailsLstDB) {
	
	//confusing start
	List<CoverDetails> coverDetailsWSLst = new ArrayList<CoverDetails>();
	CoverDetails coverdDetailsWS = null;
	
	for(CoverDetailsVO coverDetailsVO:coverDetailsLstDB.getCoverDetails()){
		coverdDetailsWS = mapCovers(coverDetailsVO);
		coverDetailsWSLst.add(coverdDetailsWS);
	}
	
	CoverDetailsList coverDetailsList = new CoverDetailsList();
	coverDetailsList.setCoverDetailsList(coverDetailsWSLst);
	
	return coverDetailsList;

}

	
	/**
	 * @param travelInsuranceVO
	 * @param travelInsuranceDetails
	 */
	private void mapTravelDetails(TravelInsuranceVO travelInsuranceVO,TravelInsuranceDetails travelInsuranceDetails) {
		
		TravelDetails travelDetailsVO = new TravelDetails();
		
		travelDetailsVO.setEndDate(travelInsuranceVO.getTravelDetailsVO().getEndDate());
		travelDetailsVO.setStartDate(travelInsuranceVO.getTravelDetailsVO().getStartDate());
		travelDetailsVO.setTravelLocation(travelInsuranceVO.getTravelDetailsVO().getTravelLocation());
		travelDetailsVO.setTravelPeriod(travelInsuranceVO.getTravelDetailsVO().getTravelPeriod());
		
		List<TravelerDetails> travlerList = new ArrayList<TravelerDetails>();
		TravelerDetails travelerDetails = null;
		for(TravelerDetailsVO travelerDetailsVO:  travelInsuranceVO.getTravelDetailsVO().getTravelerDetailsList()){
			
			travelerDetails = new TravelerDetails();
			travelerDetails.setDateOfBirth(travelerDetailsVO.getDateOfBirth());
			travelerDetails.setGprId(travelerDetailsVO.getGprId());
			travelerDetails.setName(travelerDetailsVO.getName());
			travelerDetails.setNationality(travelerDetailsVO.getNationality());
			travelerDetails.setRelation(travelerDetailsVO.getRelation());
			travelerDetails.setVsd(travelerDetailsVO.getVsd());
			travlerList.add(travelerDetails);
			
		}
		travelDetailsVO.setTravelerDetailsList(travlerList);
		
		travelInsuranceDetails.setTravelDetailsVO(travelDetailsVO);

	}
	
	
	/**
	 * @param travelInsuranceVO
	 * @param travelInsuranceDetails
	 */
	private void mapPaymentDetails(TravelInsuranceVO travelInsuranceVO,TravelInsuranceDetails travelInsuranceDetails) {
		
		PaymentDetails paymentDetails = new PaymentDetails();
		
		if (!Utils.isEmpty(travelInsuranceVO.getPaymentVO())) {
			paymentDetails.setAmount(travelInsuranceVO.getPaymentVO()
					.getAmount());
			paymentDetails.setBankCode(travelInsuranceVO.getPaymentVO()
					.getBankCode());
			paymentDetails.setChequeDt(travelInsuranceVO.getPaymentVO()
					.getChequeDt());
			paymentDetails.setChequeNo(travelInsuranceVO.getPaymentVO()
					.getChequeNo());
			paymentDetails.setCreditCardNo(travelInsuranceVO.getPaymentVO()
					.getCreditCardNo());
			paymentDetails.setExpiryDate(travelInsuranceVO.getPaymentVO()
					.getExpiryDate());
			/*paymentDetails.setPaymentDone(travelInsuranceVO.getPaymentVO()
					.isPaymentDone());*/
			paymentDetails.setPaymentMode(travelInsuranceVO.getPaymentVO()
					.getPaymentMode());
			paymentDetails.setPremium(travelInsuranceVO.getPaymentVO()
					.getPremium());
		}
		travelInsuranceDetails.setPaymentVO(paymentDetails);
	}
	
	/**
	 * @param travelInsuranceVO
	 * @param travelInsuranceDetails
	 */
	private void mapSchemeDetails(TravelInsuranceVO travelInsuranceVO,TravelInsuranceDetails travelInsuranceDetails) {
		
		SchemeVO schemeVO =  travelInsuranceVO.getScheme();
		
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
		
		travelInsuranceDetails.setScheme(schemeDetails);
	}
	
	/**
	 * @param travelInsuranceVO
	 * @param travelInsuranceDetails
	 */
	private void mapPremiumDetails(TravelInsuranceVO travelInsuranceVO,TravelInsuranceDetails travelInsuranceDetails) {
		
		PremiumVO premiumVO =  travelInsuranceVO.getPremiumVO();
		PremiumDetails premiumDetails = new PremiumDetails();
		
		premiumDetails.setCurrency(premiumVO.getCurrency());
		premiumDetails.setDiscOrLoadAmt(premiumVO.getDiscOrLoadAmt());
		premiumDetails.setDiscOrLoadPerc(premiumVO.getDiscOrLoadPerc());
		premiumDetails.setGovtTax(premiumVO.getGovtTax());
		
		/*VAT - Dileep*/
		premiumDetails.setVatTax(premiumVO.getVatTax());
		
		premiumDetails.setLossRatio(premiumVO.getLossRatio());
		premiumDetails.setMinPremium(premiumVO.getMinPremium());
		premiumDetails.setPolicyFees(premiumVO.getPolicyFees());
		premiumDetails.setPremiumAmt(premiumVO.getPremiumAmt());
		premiumDetails.setPremiumAmtActual(premiumVO.getPremiumAmtActual());
		premiumDetails.setPromoDiscPerc(premiumVO.getPromoDiscPerc());
		premiumDetails.setSpecialDiscount(premiumVO.getSpecialDiscount());
		
		travelInsuranceDetails.setPremiumVO(premiumDetails);
	}
	
	/**
	 * @param travelInsuranceVO
	 * @param travelInsuranceDetails
	 */
	private void mapGeneralInsuranceDetails(TravelInsuranceVO travelInsuranceVO,TravelInsuranceDetails travelInsuranceDetails) {
		
		GeneralInsuranceDetails generalInsuranceDetails = new GeneralInsuranceDetails();
		InsuredDetails insuredDetails = new InsuredDetails();
		GeneralInfoVO generalInfoVO = travelInsuranceVO.getGeneralInfo();
		
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
		if(!Utils.isEmpty(travelInsuranceVO.getGeneralInfo().getQuestionsVO()) &&
				!Utils.isEmpty(travelInsuranceVO.getGeneralInfo().getQuestionsVO().getQuestions())){
			for(UWQuestionVO uwQuestionsVO : travelInsuranceVO.getGeneralInfo().getQuestionsVO().getQuestions()){
				uwList.add(uwQuestionsVO.getQDesc());
			}
		}
		uwQuestionDetails.setQuestions(uwList);
		generalInsuranceDetails.setQuestionsVO(uwQuestionDetails);
		
		
		
		travelInsuranceDetails.setGeneralInfo(generalInsuranceDetails);
	}
	
}
