package com.rsaame.pas.b2c.ws.utilities;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.b2c.cmn.constants.AppConstants;
import com.rsaame.pas.b2c.cmn.utils.AppUtils;
import com.rsaame.pas.b2c.ws.vo.OptionalCovers;
import com.rsaame.pas.b2c.ws.vo.RiskDetails;
import com.rsaame.pas.b2c.ws.vo.TLLimit;
import com.rsaame.pas.cmn.currency.Currency;
import com.rsaame.pas.dao.model.TMasPolicyRating;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.bus.HomeInsuranceVO;
import com.rsaame.pas.vo.bus.PolicyDataVO;

public class WebServiceUtils {
	private final static Logger LOGGER = Logger.getLogger(WebServiceUtils.class);
	public List<Integer> splitCovers(String cover)
    {
          
          String[] covers = cover.split("-");
          List<Integer> a = new ArrayList<Integer>();
          for(int i=0;i<covers.length;i++)
          {
                a.add(Integer.parseInt(covers[i]));
          }
          return a;
    }
	
	/**
	 * 
	 * @param coverList
	 * @param optionalCoversList
	 * @param homeInsuranceVO
	 * @return
	 * @throws ParseException
	 */
	public List<OptionalCovers> retrieveOptionalCoversFromRating(List<TMasPolicyRating> coverList, List<OptionalCovers> optionalCoversList,HomeInsuranceVO homeInsuranceVO) throws ParseException {
		optionalCoversList = new ArrayList<OptionalCovers>();
		List<TLLimit> tllLimitList = new ArrayList<TLLimit>();
		TLLimit tenants = null;
		OptionalCovers optionalCovers = null;
		int count = 1;
		int ownershipStatusCheck = homeInsuranceVO.getBuildingDetails().getOwnershipStatus().intValue();
		for (TMasPolicyRating homeCover : coverList) {
			if (homeCover.getId().getPrCtCode() == 0) {
				optionalCovers = new OptionalCovers();
			}
			if(ownershipStatusCheck == 1 || ownershipStatusCheck == 2) {
				if (homeCover.getId().getPrCovCode() == 2 || homeCover.getId().getPrCovCode() == 3) {
					if (homeCover.getId().getPrCovCode() == 2) {
						optionalCovers.setCoverDesc(AppConstants.LOD);
					} else if (homeCover.getId().getPrCovCode() == 3) {
						optionalCovers.setCoverDesc(AppConstants.DSC);
					}
					optionalCovers.setPremium(homeCover.getPrPremRate());
					optionalCovers.setCoverIncluded(homeCover.getPrMandatoryInd());
					optionalCovers.setCoverageLimit(null); // homeCover.getPrLimit()
					optionalCovers.setCoverMappingCode(homeCover.getId().getPrCovCode() + "-"
							+ homeCover.getId().getPrCtCode() + "-" + homeCover.getId().getPrCstCode());
					if (Utils.isEmpty(optionalCovers.getRiskDetails())) {
						optionalCovers.setRiskDetails(new RiskDetails());
					}
					optionalCovers.getRiskDetails().setRskId(0);
					optionalCovers.getRiskDetails().setBasicRskId(0);
				}
			}
			if(ownershipStatusCheck == 2) {
				if (homeCover.getId().getPrCovCode() == 4) {
					if (homeCover.getId().getPrCtCode() == 0) {
						optionalCovers.setCoverDesc(AppConstants.ATLC);
						//optionalCovers.setPremium(homeCover.getPrPremRate());
						optionalCovers.setCoverIncluded(homeCover.getPrMandatoryInd());
						optionalCovers.setCoverageLimit(null); // homeCover.getPrLimit()
						optionalCovers.setCoverMappingCode(homeCover.getId().getPrCovCode() + "-"
								+ homeCover.getId().getPrCtCode() + "-" + homeCover.getId().getPrCstCode());
						if (Utils.isEmpty(optionalCovers.getRiskDetails())) {
							optionalCovers.setRiskDetails(new RiskDetails());
						}
						optionalCovers.getRiskDetails().setRskId(0);
						optionalCovers.getRiskDetails().setBasicRskId(0);
					} else if (homeCover.getId().getPrCtCode() == 9) {
						tenants = new TLLimit();
						String limit = "Limit";
						tenants.setCoverDesc(limit + count);
						tenants.setSelected(homeCover.getPrMandatoryInd());
						if(homeCover.getPrMandatoryInd()) { 
							optionalCovers.setCoverIncluded(true);
						}
						tenants.setCoverageLimit(homeCover.getPrLimit());
						tenants.setPremium(homeCover.getPrPremRate());
						tllLimitList.add(tenants);
						optionalCovers.setTllLimit(tllLimitList);
						count++;
					}
				}
			}
			if (homeCover.getId().getPrCtCode() == 0) {
				optionalCoversList.add(optionalCovers);
			}
			if(homeCover.getPrMandatoryInd()) { 
				LOGGER.info("adding the default optional cover to actualPremium");
				optionalCovers.setCoverIncluded(true);
				double includeDEfaultPremium = homeInsuranceVO.getPremiumVO().getPremiumAmtActual() + homeCover.getPrPremRate().doubleValue();
				homeInsuranceVO.getPremiumVO().setPremiumAmt(includeDEfaultPremium);
				homeInsuranceVO.getPremiumVO().setPremiumAmtActual(includeDEfaultPremium);
				populatePackagePremiumForCovers(homeInsuranceVO);
				LOGGER.info("Successfully added the default optional cover to the premium and calculated vat and discount amount");
			}
		}
		// Remove Additional Tenants Liability Cover if OwnerShipStatus is 1 from response list
		if (ownershipStatusCheck == 1) {
			OptionalCovers tenantsList = optionalCoversList.get(2);
			if (!Utils.isEmpty(tenantsList)) {
					optionalCoversList.remove(tenantsList);
			}
		}
		return optionalCoversList;
	}
	
	
	public void populatePackagePremiumForCovers(PolicyDataVO homeInsuranceData) {

		double onlineDiscOrLoad = Math.round(( homeInsuranceData.getPremiumVO().getPremiumAmt() + homeInsuranceData.getPremiumVO().getMinPremiumApplied().doubleValue() )  * homeInsuranceData.getPremiumVO().getDiscOrLoadPerc() / 100) ;
		
		double promoDisc = 0.0;
		if(!Utils.isEmpty(homeInsuranceData.getPremiumVO().getPromoDiscPerc())){
			promoDisc =  ( homeInsuranceData.getPremiumVO().getPremiumAmt() + homeInsuranceData.getPremiumVO().getMinPremiumApplied().doubleValue() ) * homeInsuranceData.getPremiumVO().getPromoDiscPerc() / 100 ;
		}
		onlineDiscOrLoad = Double.valueOf( Currency.getFormattedCurrency( onlineDiscOrLoad ) );
		//SAT issue Transaction amount and VAT amount difference in B2B and API values 
		BigDecimal onlineDisc= new BigDecimal( onlineDiscOrLoad).round(new MathContext(2, RoundingMode.UP));
		promoDisc = Double.valueOf( Currency.getFormattedCurrency( promoDisc ) );
		homeInsuranceData.getPremiumVO().setPremiumAmt( homeInsuranceData.getPremiumVO().getPremiumAmt() + homeInsuranceData.getPremiumVO().getMinPremiumApplied().doubleValue() + onlineDisc.doubleValue() - promoDisc);
		homeInsuranceData.getPremiumVO().setDiscOrLoadAmt(  onlineDisc.abs());
		homeInsuranceData.getPremiumVO().setSpecialDiscount( promoDisc );
		// For 142244 VAT implementation
		Date effectiveDate = homeInsuranceData.getScheme().getEffDate();
		Date expiryDate = homeInsuranceData.getScheme().getExpiryDate();
		long policyPeriod = AppUtils.getDateDifference(expiryDate, effectiveDate);
		Date vatStartDate = null;
		String defaultDateFormat = Utils.getSingleValueAppConfig("DEFAULT_DATE_FORMAT"); // MM/dd/yyyy
		try { vatStartDate = new SimpleDateFormat(defaultDateFormat).parse(SvcUtils.populateVatDt()); } 
		catch (ParseException e) { e.printStackTrace(); }  
		
		Date maxDate = null;
		if(AppUtils.getDateDifference(effectiveDate,vatStartDate) > 1){	maxDate = effectiveDate; }
		else{ maxDate = vatStartDate; }
		double vatTax = 0.0;
		double vatablePremium=0.0;
		
		if (!Utils.isEmpty(homeInsuranceData.getCommonVO().getPremiumVO().getVatTaxPerc())) {
			homeInsuranceData.getPremiumVO().setVatTaxPerc(homeInsuranceData.getCommonVO().getPremiumVO().getVatTaxPerc());
			vatTax = ((homeInsuranceData.getPremiumVO().getPremiumAmt()*
					(AppUtils.getDateDifference(expiryDate,maxDate) ) / policyPeriod) * homeInsuranceData
					.getCommonVO().getPremiumVO().getVatTaxPerc()) / 100;
			vatTax = Double.parseDouble( AppUtils.getFormattedNumberWithDecimals(vatTax));
			vatablePremium = ((homeInsuranceData.getPremiumVO().getPremiumAmt()*
						(AppUtils.getDateDifference(expiryDate,maxDate) ) / policyPeriod)) ;
			homeInsuranceData.getCommonVO().getPremiumVO().setVatablePrm(vatablePremium);
			homeInsuranceData.getPremiumVO().setVatablePrm(vatablePremium);
			homeInsuranceData.getPremiumVO().setVatTax(vatTax);
			homeInsuranceData.getPremiumVO().setPremiumAmt(homeInsuranceData.getPremiumVO().getPremiumAmt() + vatTax);
			homeInsuranceData.getCommonVO().getPremiumVO().setVatTax(vatTax);
		}
		LOGGER.debug("Actual Premium  ================="+ homeInsuranceData.getPremiumVO().getPremiumAmtActual());
		LOGGER.debug("vat Tax =================" + vatTax);
		LOGGER.debug("VatablePremium =================" + vatablePremium);
		LOGGER.debug("Premium Amount including Vat Tax ================="+ homeInsuranceData.getPremiumVO().getPremiumAmt());
	}
	
	
}
