package com.rsaame.pas.b2c.ws.mapper;

import java.util.ArrayList;
import java.util.List;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.b2c.cmn.constants.AppConstants;
import com.rsaame.pas.b2c.ws.beans.CoverDetails;
import com.rsaame.pas.b2c.ws.beans.PremiumDetails;
import com.rsaame.pas.b2c.ws.beans.SchemeDetails;
import com.rsaame.pas.b2c.ws.beans.TravelDetails;
import com.rsaame.pas.b2c.ws.beans.TravelInsuranceDetails;
import com.rsaame.pas.b2c.ws.beans.TravelPackage;
import com.rsaame.pas.b2c.ws.beans.TravelPackageDetails;
import com.rsaame.pas.b2c.ws.beans.TravelerDetails;
import com.rsaame.pas.vo.bus.AdditionalInsuredInfoVO;
import com.rsaame.pas.vo.bus.AddressVO;
import com.rsaame.pas.vo.bus.CoverDetailsVO;
import com.rsaame.pas.vo.bus.GeneralInfoVO;
import com.rsaame.pas.vo.bus.InsuredVO;
import com.rsaame.pas.vo.bus.PaymentVO;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.bus.SchemeVO;
import com.rsaame.pas.vo.bus.SumInsuredVO;
import com.rsaame.pas.vo.bus.TravelDetailsVO;
import com.rsaame.pas.vo.bus.TravelInsuranceVO;
import com.rsaame.pas.vo.bus.TravelPackageVO;
import com.rsaame.pas.vo.bus.TravelerDetailsVO;
import com.rsaame.pas.vo.cmn.CommonVO;
import com.rsaame.pas.vo.cmn.CoverVO;
import com.rsaame.pas.vo.cmn.RiskVO;

/**
 * @author m1020637
 *
 */
public class TravelInsuranceVOMapper {


	/**
	 * @param travelInsuranceVO
	 * @param travelInsuranceDetails
	 */
	public void mapTraveldetailsToTravelInsuranceVO(TravelInsuranceVO travelInsuranceVO,TravelInsuranceDetails travelInsuranceDetails) {

		CommonVO commonVO = new CommonVO();

		commonVO.setPolEffectiveDate(travelInsuranceDetails.getPolEffectiveDate());
		//commonVO.setLocCode(travelInsuranceDetails.getLocCode());
		commonVO.setVsd(travelInsuranceDetails.getVsd());
		commonVO.setQuoteNo(travelInsuranceDetails.getQuoteNo());
		commonVO.setPolicyNo(travelInsuranceDetails.getPolicyNo());
		commonVO.setPolicyId(travelInsuranceDetails.getPolicyId());
		commonVO.setEndtId(travelInsuranceDetails.getEndtId());
		commonVO.setEndtNo(travelInsuranceDetails.getEndtNo());
		commonVO.setEndtEffectiveDate(travelInsuranceDetails.getEndEffectiveDate());
		//commonVO.setStatus(travelInsuranceDetails.getStatus());
		travelInsuranceVO.setCommonVO(commonVO);

		travelInsuranceVO.setCreatedOn(travelInsuranceDetails.getCreated());
		travelInsuranceVO.setDefaultTariff(travelInsuranceDetails.getDefaultTariff());
		travelInsuranceVO.setEndDate(travelInsuranceDetails.getEndDate());
		travelInsuranceVO.setEndEffectiveDate(travelInsuranceDetails.getEndEffectiveDate());
		travelInsuranceVO.setEndStartDate(travelInsuranceDetails.getEndStartDate());
		travelInsuranceVO.setEndtId(travelInsuranceDetails.getEndtId());
		travelInsuranceVO.setEndtNo(travelInsuranceDetails.getEndtNo());
		travelInsuranceVO.setIsQuote(travelInsuranceDetails.getIsQuote());
		//travelInsuranceVO.setLocCode(travelInsuranceDetails.getCommonVO().getLocCode());
		travelInsuranceVO.setPolCustomerId(travelInsuranceDetails.getPolCustomerId());
		travelInsuranceVO.setPolDocumentId(travelInsuranceDetails.getPolDocumentId());
		//travelInsuranceVO.setPolEffectiveDate(travelInsuranceDetails.getCommonVO().getPolEffectiveDate());	
		travelInsuranceVO.setPolExchangeRate(travelInsuranceDetails.getPolExchangeRate());	
		travelInsuranceVO.setPolExpiryDate(travelInsuranceDetails.getPolExpiryDate());
		//travelInsuranceVO.setPolicyClassCode(travelInsuranceDetails.getPolicyClassCode());
		//travelInsuranceVO.setPolicyExtended(travelInsuranceDetails.isPolicyExtended());	
		travelInsuranceVO.setPolicyExtensionPeriod(travelInsuranceDetails.getPolicyExtensionPeriod());
		travelInsuranceVO.setPolicyNo(travelInsuranceDetails.getPolicyNo());
		travelInsuranceVO.setPolicyTerm(travelInsuranceDetails.getPolicyTerm());
		if( !Utils.isEmpty( travelInsuranceVO.getPolicyTerm() ) ){
			if( travelInsuranceVO.getPolicyTerm() > AppConstants.LONG_TERM_TRAVEL_DAYS ){
				travelInsuranceVO.setPolicyType( AppConstants.TRAVEL_LONG_TERM_POLICY_TYPE );
			}
			else{
				travelInsuranceVO.setPolicyType( AppConstants.TRAVEL_SHORT_TERM_POLICY_TYPE );
			}
		}
		//travelInsuranceVO.setPolicyType(travelInsuranceDetails.getPolicyType());
		//travelInsuranceVO.setPolLinkingId(travelInsuranceDetails.getPolLinkingId());
		travelInsuranceVO.setProcessedDate(travelInsuranceDetails.getProcessedDate());
		travelInsuranceVO.setQuoteNo(travelInsuranceDetails.getQuoteNo());
		travelInsuranceVO.setStartDate(travelInsuranceDetails.getStartDate());
		travelInsuranceVO.setStatus( AppConstants.QUOTE_ACTIVE );
		travelInsuranceVO.setValidityStartDate(travelInsuranceDetails.getValidityStartDate());
		//travelInsuranceVO.setVsd(travelInsuranceDetails.getCommonVO().getVsd());

		/*travelInsuranceDetails.setSectionId();*/
		//travelInsuranceDetails.setInsuredChanged();	


		mapGeneralInsuranceDetails(travelInsuranceVO, travelInsuranceDetails);
		mapPremiumDetails(travelInsuranceVO, travelInsuranceDetails);
		mapSchemeDetails(travelInsuranceVO, travelInsuranceDetails);
		travelInsuranceVO.getScheme().setPolicyCode( travelInsuranceVO.getPolicyType() );
		mapPaymentDetails(travelInsuranceVO, travelInsuranceDetails);
		mapTravelDetails(travelInsuranceVO, travelInsuranceDetails);
		mapTravelPackageList(travelInsuranceVO, travelInsuranceDetails);

	}

	/**
	 * @param travelInsuranceVO
	 * @param travelInsuranceDetails
	 */
	public void mapTravelPackageList(TravelInsuranceVO travelInsuranceVO,TravelInsuranceDetails travelInsuranceDetails) {

		List<TravelPackageVO> travelPackageVOs = new ArrayList<TravelPackageVO>();
		TravelPackageVO travelPackageVO = null;
		TravelPackage travelPackage =null;

		for(TravelPackageDetails travelPackageDetails2 : travelInsuranceDetails.getTravelPackageList()){
			travelPackageVO = new TravelPackageVO();

			mapCovers(travelPackageDetails2, travelPackageVO);
			travelPackageVO.setPackageName(travelPackageDetails2.getPackageName());
			travelPackageVO.setTariffCode(travelPackageDetails2.getTariffCode());
			travelPackageVO.setIsSelected(travelPackageDetails2.getIsSelected());
			travelPackageVOs.add(travelPackageVO);
		}

		travelInsuranceVO.setTravelPackageList(travelPackageVOs);
	}

	/**
	 * @param travelPackageDetails
	 * @param travelPackageVO
	 */
	private void mapCovers(TravelPackageDetails travelPackageDetails,TravelPackageVO travelPackageVO) {

		List<CoverDetailsVO> coverDetailsVOs = new ArrayList<CoverDetailsVO>();
		CoverDetailsVO coverDetailsVO = null;
		CoverVO coverVO = null;
		SumInsuredVO sumInsuredVO = null;
		RiskVO riskVO = null;
		for(CoverDetails coverDetails : travelPackageDetails.getCovers()){
			coverDetailsVO = new CoverDetailsVO();

			coverDetailsVO.setCoverDesc(coverDetails.getCoverDesc());
			coverDetailsVO.setCoverName(coverDetails.getCoverName());
			coverDetailsVO.setCurrency(coverDetails.getCurrency());
			coverDetailsVO.setDiscOrLoadAmt(coverDetails.getDiscOrLoadAmt());
			coverDetailsVO.setDiscOrLoadPerc(coverDetails.getDiscOrLoadPerc());
			//coverDetailsVO.setGovtTax(coverDetails.getGovtTax());
			coverDetailsVO.setIsCovered(coverDetails.getIsCovered());
			//coverDetailsVO.setLoading(coverDetails.isLoading());
		//	coverDetailsVO.setLossRatio(coverDetails.getLossRatio());
	//		coverDetailsVO.setMandatoryIndicator(coverDetails.getMandatoryIndicator());
	//		coverDetailsVO.setMappingField(coverDetails.getMappingField());
			coverDetailsVO.setMinPremium(coverDetails.getMinPremium());
	//		coverDetailsVO.setPolicyFees(coverDetails.getPolicyFees());
			coverDetailsVO.setPremiumAmt(coverDetails.getPremiumAmt());
			coverDetailsVO.setPremiumAmtActual(coverDetails.getPremiumAmtActual());
			coverDetailsVO.setPromoDiscPerc(coverDetails.getPromoDiscPerc());
			coverDetailsVO.setTariffCode(coverDetails.getTariffCode());
			coverDetailsVO.setVsd(coverDetails.getVsd());

			if (coverDetails.getCoverCodes()!=null) {
				coverVO = new CoverVO();
				coverVO.setCovCode(coverDetails.getCoverCodes().getCovCode());
				coverVO.setCovCriteriaCode(coverDetails.getCoverCodes()
						.getCovCriteriaCode());
				coverVO.setCovSubTypeCode(coverDetails.getCoverCodes()
						.getCovSubTypeCode());
				coverVO.setCovTypeCode(coverDetails.getCoverCodes()
						.getCovTypeCode());
				coverDetailsVO.setCoverCodes(coverVO);
			}
			
			if (coverDetails.getRiskCodes()!=null) {
				riskVO = new RiskVO();
				riskVO.setBasicRskCode(coverDetails.getRiskCodes()
						.getBasicRskCode());
				riskVO.setBasicRskId(coverDetails.getRiskCodes()
						.getBasicRskId());
				riskVO.setRiskCat(coverDetails.getRiskCodes().getRiskCat());
				riskVO.setRiskCode(coverDetails.getRiskCodes().getRiskCode());
				riskVO.setRiskType(coverDetails.getRiskCodes().getRiskType());
				riskVO.setRskId(coverDetails.getRiskCodes().getRskId());
				coverDetailsVO.setRiskCodes(riskVO);
			}
			
		//	coverDetailsVO.setSpecialDiscount(coverDetails.getSpecialDiscount());

			if (coverDetails.getSumInsured()!=null) {
				sumInsuredVO = new SumInsuredVO();
		//		sumInsuredVO.setaDesc(coverDetails.getSumInsured().getaDesc());
				sumInsuredVO.setCash_Id(coverDetails.getSumInsured()
						.getCash_Id());
				sumInsuredVO.setDeductible(coverDetails.getSumInsured()
						.getDeductible());
	//			sumInsuredVO.seteDesc(coverDetails.getSumInsured().geteDesc());
				sumInsuredVO.setIdentifier(coverDetails.getSumInsured()
						.getIdentifier());
				sumInsuredVO.setPromoCover(coverDetails.getSumInsured()
						.isPromoCover());
				sumInsuredVO.setSumInsured(coverDetails.getSumInsured()
						.getSumInsured());
				sumInsuredVO.setVsd(coverDetails.getSumInsured().getVsd());
				coverDetailsVO.setSumInsured(sumInsuredVO);
			}
			
			coverDetailsVOs.add(coverDetailsVO);
		}

		travelPackageVO.setCovers(coverDetailsVOs);

	}

	/**
	 * @param travelInsuranceVO
	 * @param travelInsuranceDetails
	 */
	private void mapTravelDetails(TravelInsuranceVO travelInsuranceVO,TravelInsuranceDetails travelInsuranceDetails) {

		TravelDetailsVO travelDetailsVO = new TravelDetailsVO();
		TravelDetails travelDetails = travelInsuranceDetails.getTravelDetailsVO();
		List<TravelerDetailsVO> travelerDetailsList = new ArrayList<TravelerDetailsVO>();
		TravelerDetailsVO travelerDetailsVO = null;

		travelDetailsVO.setEndDate(travelDetails.getEndDate());
		travelDetailsVO.setStartDate(travelDetails.getStartDate());
		travelDetailsVO.setTravelLocation(travelDetails.getTravelLocation());
		travelDetailsVO.setTravelPeriod(travelDetails.getTravelPeriod());

		for(TravelerDetails travelerDetails2 : travelInsuranceDetails.getTravelDetailsVO().getTravelerDetailsList()){

			travelerDetailsVO = new TravelerDetailsVO();
			travelerDetailsVO.setDateOfBirth(travelerDetails2.getDateOfBirth());
			travelerDetailsVO.setGprId(travelerDetails2.getGprId());
			travelerDetailsVO.setName(travelerDetails2.getName());
			travelerDetailsVO.setNationality(travelerDetails2.getNationality());
			travelerDetailsVO.setRelation(travelerDetails2.getRelation());
			travelerDetailsVO.setVsd(travelerDetails2.getVsd());
			travelerDetailsList.add(travelerDetailsVO);
		}

		travelDetailsVO.setTravelerDetailsList(travelerDetailsList);

		travelInsuranceVO.setTravelDetailsVO(travelDetailsVO);
	}

	/**
	 * @param travelInsuranceVO
	 * @param travelInsuranceDetails
	 */
	private void mapGeneralInsuranceDetails(TravelInsuranceVO travelInsuranceVO,TravelInsuranceDetails travelInsuranceDetails) {

		GeneralInfoVO tgeneralInfoVO = new GeneralInfoVO();
		InsuredVO insuredVO = new InsuredVO();
		AddressVO addressVO = new AddressVO();
		AdditionalInsuredInfoVO additionalInfo = new AdditionalInsuredInfoVO();


		addressVO.setAddress(travelInsuranceDetails.getGeneralInfo().getInsured().getAddress());
		addressVO.setCountry(travelInsuranceDetails.getGeneralInfo().getInsured().getCountry());
		addressVO.setEmirates(travelInsuranceDetails.getGeneralInfo().getInsured().getEmirates());
		addressVO.setPoBox(travelInsuranceDetails.getGeneralInfo().getInsured().getPoBox());

		insuredVO.setAddress(addressVO);
		insuredVO.setCity(travelInsuranceDetails.getGeneralInfo().getInsured().getCity());
		insuredVO.setCity(travelInsuranceDetails.getGeneralInfo().getInsured().getCity());
		insuredVO.setEmailId(travelInsuranceDetails.getGeneralInfo().getInsured().getEmailId());
		insuredVO.setFirstName(travelInsuranceDetails.getGeneralInfo().getInsured().getFirstName());
		insuredVO.setHowDidYouHear(travelInsuranceDetails.getGeneralInfo().getInsured().getHowDidYouHear());
		insuredVO.setInsuredCode(travelInsuranceDetails.getGeneralInfo().getInsured().getInsuredCode());
		insuredVO.setInsuredId(travelInsuranceDetails.getGeneralInfo().getInsured().getInsuredId());
		insuredVO.setLastName(travelInsuranceDetails.getGeneralInfo().getInsured().getLastName());
		insuredVO.setMobileNo(travelInsuranceDetails.getGeneralInfo().getInsured().getMobileNo());
		insuredVO.setName(travelInsuranceDetails.getGeneralInfo().getInsured().getName());
		insuredVO.setNationality(travelInsuranceDetails.getGeneralInfo().getInsured().getNationality());
		insuredVO.setPhoneNo(travelInsuranceDetails.getGeneralInfo().getInsured().getPhoneNo());

		additionalInfo.setPolicyId(travelInsuranceDetails.getGeneralInfo().getInsured().getPolicyId());
		additionalInfo.setWebsite(travelInsuranceDetails.getGeneralInfo().getInsured().getWebsite());
		additionalInfo.setFaxNumber(travelInsuranceDetails.getGeneralInfo().getInsured().getFaxNumber());
		additionalInfo.setPlaceOfEst(travelInsuranceDetails.getGeneralInfo().getInsured().getPlaceOfEst());
		additionalInfo.setInsuredStatus(travelInsuranceDetails.getGeneralInfo().getInsured().getInsuredStatus());

		tgeneralInfoVO.setInsured(insuredVO);
		tgeneralInfoVO.setAdditionalInfo(additionalInfo);
		travelInsuranceVO.setGeneralInfo(tgeneralInfoVO);

	}

	/**
	 * @param travelInsuranceVO
	 * @param travelInsuranceDetails
	 */
	private void mapPremiumDetails(TravelInsuranceVO travelInsuranceVO,TravelInsuranceDetails travelInsuranceDetails) {

		PremiumDetails premiumDetails = new PremiumDetails();
		premiumDetails.setDiscOrLoadPerc( Double.parseDouble( Utils.getSingleValueAppConfig( "TRAVEL_POLICY_LEVEL_DISCOUNT" ) ) );
		
		PremiumVO tpremiumVO = new PremiumVO();

		tpremiumVO.setCurrency(premiumDetails.getCurrency());
		tpremiumVO.setDiscOrLoadAmt(premiumDetails.getDiscOrLoadAmt());
		tpremiumVO.setDiscOrLoadPerc(premiumDetails.getDiscOrLoadPerc());
		tpremiumVO.setGovtTax(premiumDetails.getGovtTax());
		
		/* VAT - Dileep*/
		tpremiumVO.setVatTax(premiumDetails.getVatTax());
		
		tpremiumVO.setLossRatio(premiumDetails.getLossRatio());
		tpremiumVO.setMinPremium(premiumDetails.getMinPremium());
		tpremiumVO.setPolicyFees(premiumDetails.getPolicyFees());
		tpremiumVO.setPremiumAmt(premiumDetails.getPremiumAmt());
		tpremiumVO.setPremiumAmtActual(premiumDetails.getPremiumAmtActual());
		tpremiumVO.setPromoDiscPerc(premiumDetails.getPromoDiscPerc());
		tpremiumVO.setSpecialDiscount(premiumDetails.getSpecialDiscount());

		travelInsuranceVO.setPremiumVO(tpremiumVO);
	}

	/**
	 * @param travelInsuranceVO
	 * @param travelInsuranceDetails
	 */
	private void mapSchemeDetails(TravelInsuranceVO travelInsuranceVO,TravelInsuranceDetails travelInsuranceDetails) {

		SchemeVO tschemeVO = new SchemeVO();
		SchemeDetails tschemeDetails = travelInsuranceDetails.getScheme();

		tschemeVO.setEffDate(tschemeDetails.getEffDate());
		tschemeVO.setExpiryDate(tschemeDetails.getExpiryDate());
	//	tschemeVO.setPolicyCode(tschemeDetails.getPolicyCode());
		tschemeVO.setPolicyType(tschemeDetails.getPolicyType());
		tschemeVO.setSchemeCode(tschemeDetails.getSchemeCode());
		tschemeVO.setSchemeName(tschemeDetails.getSchemeName());
		tschemeVO.setTariffCode(tschemeDetails.getTariffCode());
		tschemeVO.setTariffName(tschemeDetails.getTariffName());

		travelInsuranceVO.setScheme(tschemeVO);
	}

	/**
	 * @param travelInsuranceVO
	 * @param travelInsuranceDetails
	 */
	private void mapPaymentDetails(TravelInsuranceVO travelInsuranceVO,TravelInsuranceDetails travelInsuranceDetails) {

		PaymentVO paymentVO = new PaymentVO();
		/*PaymentDetails paymentDetails = travelInsuranceDetails.getPaymentVO();

		paymentVO.setAmount(paymentDetails.getAmount());
		paymentVO.setBankCode(paymentDetails
				.getBankCode());
		paymentVO.setChequeDt(paymentDetails
				.getChequeDt());
		paymentVO.setChequeNo(paymentDetails
				.getChequeNo());
		paymentVO.setCreditCardNo(paymentDetails
				.getCreditCardNo());
		paymentVO.setExpiryDate(paymentDetails
				.getExpiryDate());*/
		paymentVO.setPaymentDone(false);
		/*paymentVO.setPaymentMode(paymentDetails
				.getPaymentMode());
		paymentVO.setPremium(travelInsuranceVO.getPremiumVO().getPremiumAmt());*/

		travelInsuranceVO.setPaymentVO(paymentVO);

	}



}
