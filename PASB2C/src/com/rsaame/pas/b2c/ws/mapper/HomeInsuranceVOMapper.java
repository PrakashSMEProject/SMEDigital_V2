package com.rsaame.pas.b2c.ws.mapper;

import java.util.ArrayList;
import java.util.List;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.b2c.cmn.constants.AppConstants;
import com.rsaame.pas.b2c.ws.beans.BuildingDetails;
import com.rsaame.pas.b2c.ws.beans.CoverDetails;
import com.rsaame.pas.b2c.ws.beans.HomeInsuranceDetails;
import com.rsaame.pas.b2c.ws.beans.PremiumDetails;
import com.rsaame.pas.b2c.ws.beans.SchemeDetails;
import com.rsaame.pas.vo.bus.AdditionalInsuredInfoVO;
import com.rsaame.pas.vo.bus.AddressVO;
import com.rsaame.pas.vo.bus.BuildingDetailsVO;
import com.rsaame.pas.vo.bus.CoverDetailsVO;
import com.rsaame.pas.vo.bus.GeneralInfoVO;
import com.rsaame.pas.vo.bus.HomeInsuranceVO;
import com.rsaame.pas.vo.bus.InsuredVO;
import com.rsaame.pas.vo.bus.LOB;
import com.rsaame.pas.vo.bus.PaymentVO;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.bus.SchemeVO;
import com.rsaame.pas.vo.bus.SumInsuredVO;
import com.rsaame.pas.vo.cmn.CommonVO;
import com.rsaame.pas.vo.cmn.CoverVO;
import com.rsaame.pas.vo.cmn.RiskVO;

public class HomeInsuranceVOMapper {
	
	/**
	 * @param homeInsuranceDetails
	 * @param homeInsuranceVO
	 */
	public void mapHomeInsuranceVO(HomeInsuranceDetails homeInsuranceDetails,HomeInsuranceVO homeInsuranceVO) {
		
		
		homeInsuranceVO.setCreatedOn(homeInsuranceDetails.getCreated());
		homeInsuranceVO.setEndDate(homeInsuranceDetails.getEndDate());
		homeInsuranceVO.setEndEffectiveDate(homeInsuranceDetails.getEndEffectiveDate());
		homeInsuranceVO.setEndStartDate(homeInsuranceDetails.getEndStartDate());
		homeInsuranceVO.setEndtId(homeInsuranceDetails.getEndtId());
		homeInsuranceVO.setEndtNo(homeInsuranceDetails.getEndtNo());
		//homeInsuranceVO.setInsuredChanged();	
		homeInsuranceVO.setIsQuote(homeInsuranceDetails.getIsQuote());
		homeInsuranceVO.setPolCustomerId(homeInsuranceDetails.getPolCustomerId());
		homeInsuranceVO.setPolDocumentId(homeInsuranceDetails.getPolDocumentId());	
		homeInsuranceVO.setPolExchangeRate(homeInsuranceDetails.getPolExchangeRate());	
		homeInsuranceVO.setPolExpiryDate(homeInsuranceDetails.getPolExpiryDate());
		homeInsuranceVO.setPolicyClassCode(homeInsuranceDetails.getPolicyClassCode());
		homeInsuranceVO.setPolicyExtended(homeInsuranceDetails.isPolicyExtended());	
		homeInsuranceVO.setPolicyExtensionPeriod(homeInsuranceDetails.getPolicyExtensionPeriod());
		homeInsuranceVO.setPolicyNo(homeInsuranceDetails.getPolicyNo());
		homeInsuranceVO.setPolicyTerm(homeInsuranceDetails.getPolicyTerm());
		homeInsuranceVO.setPolicyType(homeInsuranceDetails.getPolicyType());
		homeInsuranceVO.setProcessedDate(homeInsuranceDetails.getProcessedDate());
		homeInsuranceVO.setQuoteNo(homeInsuranceDetails.getQuoteNo());
		/*homeInsuranceVO.setSectionId();*/
		homeInsuranceVO.setStartDate(homeInsuranceDetails.getStartDate());
		homeInsuranceVO.setStatus(homeInsuranceDetails.getStatus());
		homeInsuranceVO.setValidityStartDate(homeInsuranceDetails.getValidityStartDate());
		
		
		CommonVO commonVO = new CommonVO();
		//commonVO.setLocCode(homeInsuranceDetails.getLocCode());
		commonVO.setPolEffectiveDate(homeInsuranceDetails.getPolEffectiveDate());
		commonVO.setVsd(homeInsuranceDetails.getVsd());
		commonVO.setQuoteNo(homeInsuranceDetails.getQuoteNo());
		commonVO.setPolicyNo(homeInsuranceDetails.getPolicyNo());
		commonVO.setPolicyId(homeInsuranceDetails.getPolicyId());
		commonVO.setEndtId(homeInsuranceDetails.getEndtId());
		commonVO.setEndtNo(homeInsuranceDetails.getEndtNo());
		commonVO.setEndtEffectiveDate(homeInsuranceDetails.getEndEffectiveDate());
		commonVO.setStatus(homeInsuranceDetails.getStatus());
		commonVO.setLob(LOB.HOME);
		/*UserProfile user = new UserProfile();
		user.setUserId("512");
		commonVO.setLoggedInUser(user);*/
		homeInsuranceVO.setCommonVO(commonVO);
		
		mapCovers(homeInsuranceDetails, homeInsuranceVO);
		mapBuildingDetails(homeInsuranceDetails, homeInsuranceVO);
		mapGeneralInsuranceDetails(homeInsuranceDetails, homeInsuranceVO);
		mapPremiumDetails(homeInsuranceDetails, homeInsuranceVO);
		mapSchemeDetails(homeInsuranceDetails, homeInsuranceVO);
		mapPaymentDetails(homeInsuranceDetails, homeInsuranceVO);
	
	}
	
	
	/**
	 * @param homeInsuranceDetails
	 * @param homeInsuranceVO
	 */
	private void mapPaymentDetails(HomeInsuranceDetails homeInsuranceDetails,HomeInsuranceVO homeInsuranceVO) {

		PaymentVO paymentVO = new PaymentVO();
		/*PaymentDetails paymentDetails = homeInsuranceDetails.getPaymentVO();*/

		paymentVO.setAmount( Double.valueOf( AppConstants.zeroVal ) );
		/*paymentVO.setBankCode(paymentDetails
				.getBankCode());
		paymentVO.setChequeDt(paymentDetails
				.getChequeDt());
		paymentVO.setChequeNo(paymentDetails
				.getChequeNo());
		paymentVO.setCreditCardNo(paymentDetails
				.getCreditCardNo());
		paymentVO.setExpiryDate(paymentDetails
				.getExpiryDate());*/
		paymentVO.setPaymentDone( false );
		/*paymentVO.setPaymentMode(paymentDetails
				.getPaymentMode());*/
		paymentVO.setPremium( homeInsuranceVO.getPremiumVO().getPremiumAmt() );

		homeInsuranceVO.setPaymentVO(paymentVO);

	}
	
	/**
	 * @param homeInsuranceDetails
	 * @param homeInsuranceVO
	 */
	private void mapSchemeDetails(HomeInsuranceDetails homeInsuranceDetails,HomeInsuranceVO homeInsuranceVO) {

		SchemeVO tschemeVO = new SchemeVO();
		SchemeDetails tschemeDetails = homeInsuranceDetails.getScheme();

		tschemeVO.setEffDate(tschemeDetails.getEffDate());
		tschemeVO.setExpiryDate(tschemeDetails.getExpiryDate());
		//tschemeVO.setPolicyCode(tschemeDetails.getPolicyCode());
		tschemeVO.setPolicyType(tschemeDetails.getPolicyType());
		tschemeVO.setSchemeCode(tschemeDetails.getSchemeCode());
		tschemeVO.setSchemeName(tschemeDetails.getSchemeName());
		tschemeVO.setTariffCode(tschemeDetails.getTariffCode());
		tschemeVO.setTariffName(tschemeDetails.getTariffName());

		homeInsuranceVO.setScheme(tschemeVO);
	}
	
	/**
	 * @param travelInsuranceVO
	 * @param travelInsuranceDetails
	 */
	private void mapPremiumDetails(HomeInsuranceDetails homeInsuranceDetails,HomeInsuranceVO homeInsuranceVO) {

		PremiumDetails premiumDetails = homeInsuranceDetails.getPremiumVO();
		PremiumVO tpremiumVO = new PremiumVO();

		tpremiumVO.setCurrency(premiumDetails.getCurrency());
		tpremiumVO.setDiscOrLoadAmt(premiumDetails.getDiscOrLoadAmt());
		tpremiumVO.setDiscOrLoadPerc(premiumDetails.getDiscOrLoadPerc());
		tpremiumVO.setGovtTax(premiumDetails.getGovtTax());
		tpremiumVO.setLossRatio(premiumDetails.getLossRatio());
		tpremiumVO.setMinPremium(premiumDetails.getMinPremium());
		tpremiumVO.setPolicyFees(premiumDetails.getPolicyFees());
		tpremiumVO.setPremiumAmt(premiumDetails.getPremiumAmt());
		tpremiumVO.setPremiumAmtActual(premiumDetails.getPremiumAmtActual());
		tpremiumVO.setPromoDiscPerc(premiumDetails.getPromoDiscPerc());
		tpremiumVO.setSpecialDiscount(premiumDetails.getSpecialDiscount());

		homeInsuranceVO.setPremiumVO(tpremiumVO);
	}

	/**
	 * @param homeInsuranceDetails
	 * @param homeInsuranceVO
	 */
	private void mapGeneralInsuranceDetails(HomeInsuranceDetails homeInsuranceDetails,HomeInsuranceVO homeInsuranceVO) {

		GeneralInfoVO tgeneralInfoVO = new GeneralInfoVO();
		InsuredVO insuredVO = new InsuredVO();
		AddressVO addressVO = new AddressVO();
		AdditionalInsuredInfoVO additionalInfo = new AdditionalInsuredInfoVO();


		if (homeInsuranceDetails.getGeneralInfo()
					.getInsured() != null) {
			addressVO.setAddress(homeInsuranceDetails.getGeneralInfo()
					.getInsured().getAddress());
			addressVO.setCountry(homeInsuranceDetails.getGeneralInfo()
					.getInsured().getCountry());
			addressVO.setEmirates(homeInsuranceDetails.getGeneralInfo()
					.getInsured().getEmirates());
			addressVO.setPoBox(homeInsuranceDetails.getGeneralInfo()
					.getInsured().getPoBox());
			insuredVO.setAddress(addressVO);
			insuredVO.setCity(homeInsuranceDetails.getGeneralInfo()
					.getInsured().getCity());
			insuredVO.setCity(homeInsuranceDetails.getGeneralInfo()
					.getInsured().getCity());
			insuredVO.setEmailId(homeInsuranceDetails.getGeneralInfo()
					.getInsured().getEmailId());
			insuredVO.setFirstName(homeInsuranceDetails.getGeneralInfo()
					.getInsured().getFirstName());
			insuredVO.setHowDidYouHear(homeInsuranceDetails.getGeneralInfo()
					.getInsured().getHowDidYouHear());
			insuredVO.setInsuredCode(homeInsuranceDetails.getGeneralInfo()
					.getInsured().getInsuredCode());
			insuredVO.setInsuredId(homeInsuranceDetails.getGeneralInfo()
					.getInsured().getInsuredId());
			insuredVO.setLastName(homeInsuranceDetails.getGeneralInfo()
					.getInsured().getLastName());
			insuredVO.setMobileNo(homeInsuranceDetails.getGeneralInfo()
					.getInsured().getMobileNo());
			insuredVO.setName(homeInsuranceDetails.getGeneralInfo()
					.getInsured().getName());
			insuredVO.setNationality(homeInsuranceDetails.getGeneralInfo()
					.getInsured().getNationality());
			insuredVO.setPhoneNo(homeInsuranceDetails.getGeneralInfo()
					.getInsured().getPhoneNo());
			additionalInfo.setPolicyId(homeInsuranceDetails.getGeneralInfo()
					.getInsured().getPolicyId());
			additionalInfo.setWebsite(homeInsuranceDetails.getGeneralInfo()
					.getInsured().getWebsite());
			additionalInfo.setFaxNumber(homeInsuranceDetails.getGeneralInfo()
					.getInsured().getFaxNumber());
			additionalInfo.setPlaceOfEst(homeInsuranceDetails.getGeneralInfo()
					.getInsured().getPlaceOfEst());
			additionalInfo.setInsuredStatus(homeInsuranceDetails
					.getGeneralInfo().getInsured().getInsuredStatus());
		}
		tgeneralInfoVO.setInsured(insuredVO);
		tgeneralInfoVO.setAdditionalInfo(additionalInfo);
		homeInsuranceVO.setGeneralInfo(tgeneralInfoVO);

	}
	
	
	/**
	 * @param homeInsuranceDetails
	 * @param homeInsuranceVO
	 */
	private void mapBuildingDetails(HomeInsuranceDetails homeInsuranceDetails,HomeInsuranceVO homeInsuranceVO) {
		

		BuildingDetailsVO buildingDetailsVO = new BuildingDetailsVO();
		BuildingDetails buildingDetails = homeInsuranceDetails.getBuildingDetails();
		
		buildingDetailsVO.setArea(buildingDetails.getArea());
		buildingDetailsVO.setBuildingname(buildingDetails.getBuildingname());
		buildingDetailsVO.setCurrency(buildingDetails.getCurrency());
		buildingDetailsVO.setDiscOrLoadAmt(buildingDetails.getDiscOrLoadAmt());
		buildingDetailsVO.setDiscOrLoadPerc(buildingDetails.getDiscOrLoadPerc());
		buildingDetailsVO.setEmirates(buildingDetails.getEmirates());
		buildingDetailsVO.setFlatVillaNo(buildingDetails.getFlatVillaNo());
		// Changes Home Revamp requirement 4.1 */
		buildingDetailsVO.setTotalNoFloors(buildingDetails.getTotalNoFloors());
		buildingDetailsVO.setTotalNoRooms(buildingDetails.getTotalNoRooms());
		buildingDetailsVO.setGeoAreaCode(buildingDetails.getGeoAreaCode());
		buildingDetailsVO.setGovtTax(buildingDetails.getGovtTax());
		buildingDetailsVO.setLoading(buildingDetails.isLoading());
		buildingDetailsVO.setLossRatio(buildingDetails.getLossRatio());
		buildingDetailsVO.setMinPremium(buildingDetails.getMinPremium());
		buildingDetailsVO.setMortgageeName(buildingDetails.getMortgageeName());
		buildingDetailsVO.setOtherDetails(buildingDetails.getOtherDetails());
		buildingDetailsVO.setOwnershipStatus(buildingDetails.getOwnershipStatus());
		buildingDetailsVO.setPolicyFees(buildingDetails.getPolicyFees());
		buildingDetailsVO.setPremiumAmt(buildingDetails.getPremiumAmt());
		buildingDetailsVO.setPremiumAmtActual(buildingDetails.getPremiumAmtActual());
		buildingDetailsVO.setPromoDiscPerc(buildingDetails.getPromoDiscPerc());
		buildingDetailsVO.setSpecialDiscount(buildingDetails.getSpecialDiscount());
		buildingDetailsVO.setTypeOfProperty(buildingDetails.getTypeOfProperty());
		buildingDetailsVO.setVsd(buildingDetails.getVsd());
		
		//sum insured
		if (buildingDetails.getSumInsured() != null) {
			
			SumInsuredVO sumInsuredVO = new SumInsuredVO();
			//sumInsuredVO.setaDesc(buildingDetails.getSumInsured().getaDesc());
			sumInsuredVO.setCash_Id(buildingDetails.getSumInsured()
					.getCash_Id());
			sumInsuredVO.setDeductible(buildingDetails.getSumInsured()
					.getDeductible());
			//sumInsuredVO.seteDesc(buildingDetails.getSumInsured().geteDesc());
			sumInsuredVO.setIdentifier(buildingDetails.getSumInsured()
					.getIdentifier());
			sumInsuredVO.setPromoCover(buildingDetails.getSumInsured()
					.isPromoCover());
			sumInsuredVO.setSumInsured(buildingDetails.getSumInsured()
					.getSumInsured());
			sumInsuredVO.setVsd(buildingDetails.getSumInsured().getVsd());
			buildingDetailsVO.setSumInsured(sumInsuredVO);
		}
		
		//Cover
		if (buildingDetails.getCoverCodes()!=null) {
			
			CoverVO coverVO = new CoverVO();
			coverVO.setCovCode(buildingDetails.getCoverCodes().getCovCode());
			coverVO.setCovCriteriaCode(buildingDetails.getCoverCodes()
					.getCovCriteriaCode());
			coverVO.setCovSubTypeCode(buildingDetails.getCoverCodes()
					.getCovSubTypeCode());
			coverVO.setCovTypeCode(buildingDetails.getCoverCodes()
					.getCovTypeCode());
			buildingDetailsVO.setCoverCodes(coverVO);
		}
		
		//Risk
		if (buildingDetails.getRiskCodes()!=null) {
			RiskVO riskVO = new RiskVO();
			riskVO.setBasicRskCode(buildingDetails.getRiskCodes()
					.getBasicRskCode());
			riskVO.setBasicRskId(buildingDetails.getRiskCodes().getBasicRskId());
			riskVO.setRiskCat(buildingDetails.getRiskCodes().getRiskCat());
			riskVO.setRiskCode(buildingDetails.getRiskCodes().getRiskCode());
			riskVO.setRiskType(buildingDetails.getRiskCodes().getRiskType());
			riskVO.setRskId(buildingDetails.getRiskCodes().getRskId());
			buildingDetailsVO.setRiskCodes(riskVO);
		}
		
		homeInsuranceVO.setBuildingDetails(buildingDetailsVO);
	}

	/**
	 * @param homeInsuranceDetails
	 * @param homeInsuranceVO
	 */
	private void mapCovers(HomeInsuranceDetails homeInsuranceDetails,HomeInsuranceVO homeInsuranceVO) {

		List<CoverDetailsVO> coverDetailsVOs = new ArrayList<CoverDetailsVO>();
		CoverDetailsVO coverDetailsVO = null;
		CoverVO coverVO = null;
		SumInsuredVO sumInsuredVO = null;
		RiskVO riskVO = null;
		if (!Utils.isEmpty(homeInsuranceDetails.getCovers())) {
			for (CoverDetails coverDetails : homeInsuranceDetails.getCovers()) {
				coverDetailsVO = new CoverDetailsVO();

				coverDetailsVO.setCoverDesc(coverDetails.getCoverDesc());
				coverDetailsVO.setCoverName(coverDetails.getCoverName());
				coverDetailsVO.setCurrency(coverDetails.getCurrency());
				coverDetailsVO
						.setDiscOrLoadAmt(coverDetails.getDiscOrLoadAmt());
				coverDetailsVO.setDiscOrLoadPerc(coverDetails
						.getDiscOrLoadPerc());
				//coverDetailsVO.setGovtTax(coverDetails.getGovtTax());
				coverDetailsVO.setIsCovered(coverDetails.getIsCovered());
				//coverDetailsVO.setLoading(coverDetails.isLoading());
				//coverDetailsVO.setLossRatio(coverDetails.getLossRatio());
				/*coverDetailsVO.setMandatoryIndicator(coverDetails
						.getMandatoryIndicator());*/
				//coverDetailsVO.setMappingField(coverDetails.getMappingField());
				coverDetailsVO.setMinPremium(coverDetails.getMinPremium());
				//coverDetailsVO.setPolicyFees(coverDetails.getPolicyFees());
				coverDetailsVO.setPremiumAmt(coverDetails.getPremiumAmt());
				coverDetailsVO.setPremiumAmtActual(coverDetails
						.getPremiumAmtActual());
				coverDetailsVO
						.setPromoDiscPerc(coverDetails.getPromoDiscPerc());
				coverDetailsVO.setTariffCode(coverDetails.getTariffCode());
				coverDetailsVO.setVsd(coverDetails.getVsd());

				coverVO = new CoverVO();
				coverVO.setCovCode(coverDetails.getCoverCodes().getCovCode());
				coverVO.setCovCriteriaCode(coverDetails.getCoverCodes()
						.getCovCriteriaCode());
				coverVO.setCovSubTypeCode(coverDetails.getCoverCodes()
						.getCovSubTypeCode());
				coverVO.setCovTypeCode(coverDetails.getCoverCodes()
						.getCovTypeCode());
				coverDetailsVO.setCoverCodes(coverVO);

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

				/*coverDetailsVO.setSpecialDiscount(coverDetails
						.getSpecialDiscount());*/

				sumInsuredVO = new SumInsuredVO();
				//sumInsuredVO.setaDesc(coverDetails.getSumInsured().getaDesc());
				sumInsuredVO.setCash_Id(coverDetails.getSumInsured()
						.getCash_Id());
				sumInsuredVO.setDeductible(coverDetails.getSumInsured()
						.getDeductible());
				//sumInsuredVO.seteDesc(coverDetails.getSumInsured().geteDesc());
				sumInsuredVO.setIdentifier(coverDetails.getSumInsured()
						.getIdentifier());
				sumInsuredVO.setPromoCover(coverDetails.getSumInsured()
						.isPromoCover());
				sumInsuredVO.setSumInsured(coverDetails.getSumInsured()
						.getSumInsured());
				sumInsuredVO.setVsd(coverDetails.getSumInsured().getVsd());
				coverDetailsVO.setSumInsured(sumInsuredVO);

				coverDetailsVOs.add(coverDetailsVO);
			}
		}
		homeInsuranceVO.setCovers(coverDetailsVOs);

	}
}
