package com.rsaame.pas.b2c.ws.mapper;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import com.rsaame.pas.b2c.ws.vo.Covers;
import com.rsaame.pas.b2c.ws.vo.Customer;
import com.rsaame.pas.b2c.ws.vo.Discount;
import com.rsaame.pas.b2c.ws.vo.FeesAndTaxes;
import com.rsaame.pas.b2c.ws.vo.Product;
import com.rsaame.pas.b2c.ws.vo.Quote;
import com.rsaame.pas.b2c.ws.vo.RiskDetails;
import com.rsaame.pas.b2c.ws.vo.Travellers;
import com.rsaame.pas.vo.bus.CoverDetailsVO;
import com.rsaame.pas.vo.bus.TravelInsuranceVO;
import com.rsaame.pas.vo.bus.TravelPackageVO;
import com.rsaame.pas.vo.bus.TravelerDetailsVO;

/**
 * @author m1020637
 * 
 */
public class TravelCreateQuoteResponseMapper implements BaseResponseVOMapper {
	
	@Override
	public void mapVOToResponse(Object valueObj, Object requestObj) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	public void mapVOToTravelResponse(Object valueObj, Object responseObj, Object helperObj)
			throws Exception {
		// TODO Auto-generated method stub
		if (responseObj instanceof Quote
				&& valueObj instanceof TravelInsuranceVO) {
			Quote quote = (Quote) responseObj;
			TravelInsuranceVO travelInsuranceVO = (TravelInsuranceVO) valueObj;
			Customer customer=(Customer)helperObj;

			quote.setQid(travelInsuranceVO.getCommonVO().getQuoteNo());
			quote.setEndtId(travelInsuranceVO.getCommonVO().getEndtId());
			quote.setEndtNo(travelInsuranceVO.getCommonVO().getEndtNo());
			quote.setPolicyId(travelInsuranceVO.getCommonVO().getPolicyId());
			
			Boolean isReferral= (null ==   travelInsuranceVO.getReferralVOList()  ) ? false : true;
			
			/*CustomerDetails customerDetails=new CustomerDetails();
			customerDetails.setEmailId(travelInsuranceVO.getGeneralInfo().getInsured().getEmailId());
			customerDetails.setMobileNo(travelInsuranceVO.getGeneralInfo().getInsured().getMobileNo());
			customerDetails.setInsuredId(travelInsuranceVO.getGeneralInfo().getInsured().getInsuredId());
			customerDetails.setCity(travelInsuranceVO.getGeneralInfo().getInsured().getCity());
			customerDetails.setNationality(travelInsuranceVO.getGeneralInfo().getInsured().getNationality());
			customerDetails.setVatRegNo(travelInsuranceVO.getGeneralInfo().getInsured().getVatRegNo());
			customerDetails.setRewardProgrammeType(travelInsuranceVO.getGeneralInfo().getInsured().getRoyaltyType());
			customerDetails.setRewardCardNumber( (null != travelInsuranceVO.getGeneralInfo().getInsured().getGuestCardNo()) ? travelInsuranceVO.getGeneralInfo().getInsured().getGuestCardNo()  : null );
			customerDetails.setFirstName(travelInsuranceVO.getGeneralInfo().getInsured().getFirstName());
			customerDetails.setLastName(travelInsuranceVO.getGeneralInfo().getInsured().getLastName());
			customerDetails.setPoBox(travelInsuranceVO.getGeneralInfo().getInsured().getAddress().getPoBox());
			
			quote.setCustomerDetails(customerDetails);*/
			
			customer.getCustomerDetails().setInsuredId(travelInsuranceVO.getGeneralInfo().getInsured().getInsuredCode());
			quote.setCustomerDetails(customer.getCustomerDetails());
			
			
			/*TransactionDetails transactionDetails= new TransactionDetails();
			transactionDetails.setClassCode(travelInsuranceVO.getPolicyClassCode());
			transactionDetails.setPolicyTypeCode(travelInsuranceVO.getPolicyType());
			transactionDetails.setPolicyTerm(travelInsuranceVO.getPolicyTerm());
			//transactionDetails.setEffectiveDate(customer.getStartDate().toString()); 
			transactionDetails.setEffectiveDate( customer.getTransactionDetails().getEffectiveDate() );			
			//transactionDetails.setExpiryDate(customer.getEndDate().toString());
			transactionDetails.setExpiryDate( customer.getTransactionDetails().getExpiryDate() );
			transactionDetails.setSchemeCode(travelInsuranceVO.getScheme().getSchemeCode());
			BusinessChannel bc=travelInsuranceVO.getCommonVO().getChannel(); Integer distChannel=bc.getId();
			transactionDetails.setDistChannel(distChannel);
			transactionDetails.setBusinessSource(travelInsuranceVO.getGeneralInfo().getClaimsHistory().getSourceOfBusiness());
			transactionDetails.setPromocode(customer.getTransactionDetails().getPromocode());
			transactionDetails.setPartnerTrnReferenceNumber(customer.getTransactionDetails().getPartnerTrnReferenceNumber());
						
			quote.setTransactionDetails(transactionDetails);*/	
			quote.setTransactionDetails(customer.getTransactionDetails());
						
			quote.setPartnerDetails(customer.getPartnerDetails());
			
			/*UnderWritingQuestions underWritingQuestions=new UnderWritingQuestions();
			underWritingQuestions.setInclUsaCa(customer.getUnderWritingQuestions().getInclUsaCa());
			underWritingQuestions.setClaim(Boolean.valueOf(travelInsuranceVO.getGeneralInfo().getClaimsHistory().getStatusActive()));
			
			quote.setUnderWritingQuestions(underWritingQuestions);*/
			quote.setUnderWritingQuestions(customer.getUnderWritingQuestions());
			
			//quote.setTravelers(customer.getTravelDetails().getTravelers());
			//quote.setTravelers(customer.getTravellers());
			
			Travellers[] travellersList=new Travellers[travelInsuranceVO.getTravelDetailsVO().getTravelerDetailsList().size()];
			Travellers travellers=null;
			int c=0;
			List t=travelInsuranceVO.getTravelDetailsVO().getTravelerDetailsList();
			for(TravelerDetailsVO t2:travelInsuranceVO.getTravelDetailsVO().getTravelerDetailsList()) {
				travellers=new Travellers();
				
				travellers.setTravellerId(t2.getGprId());
				travellers.setRelation(t2.getRelation());
				travellers.setTravellerDOB(t2.getDateOfBirth());
				travellers.setTravellerName(t2.getName());
				travellers.setTravellerNationality(t2.getNationality().intValue());
				
				travellersList[c]=travellers;
				c++;
			}
			quote.setTravelers(travellersList);
			
			//quote.setUid(travelInsuranceVO.getCommonVO().getPolicyId());//new Long(991);//PKS hardcoded.
			//quote.setgPremium(travelInsuranceVO.getPremiumVO().getPremiumAmt());
			//quote.setVatTaxPerc(travelInsuranceVO.getPremiumVO().getVatTaxPerc());
			//quote.setVatTaxAmt(travelInsuranceVO.getPremiumVO().getVatTax());
				
			Discount discount_p=new Discount();
			Discount discount_f=new Discount();
			Discount[] discountList=new Discount[2];
				
			discount_p.setType("p"); 
				
			discount_p.setValue(Math.abs(travelInsuranceVO.getPremiumVO().getDiscOrLoadPerc()) );//10.0; 
				
			/*discount_f.type="f";
			discount_f.value= travelInsuranceVO.getPremiumVO().getPremiumAmtActual() /  discount_p.value; //85.0;
			 */			
			discountList[0]=discount_p;
			//discountList[1]=discount_f;
				
			//quote.setDiscount(discountList);
				
			//quote.setValidUntil((String)request.getAttribute("validQuoteDate"));  //travelInsuranceVO.getCommonVO().getVsd();		
			/*try{
				quote.validUntil=new SimpleDateFormat("dd/MM/yyyy").parse(t);  
			}catch(ParseException e){}*/
				
			Product[] benefitsList=new Product[3];//3 Package list
			Covers[] coversList=null;
			Covers[] optionalCoversList=null;
			//OptionalCovers[] optionalCoversList=null;
			Product b1=null;
			int i=0;
			int n;
			
		if(null != travelInsuranceVO.getTravelPackageList()) {
			//check if the quote is referred.
			if(! isReferral){	
				for(TravelPackageVO travelPackageVO:travelInsuranceVO.getTravelPackageList()){
					b1=new Product();
					b1.setProductDesc(travelPackageVO.getPackageName());
					
					b1.setProductCode(Integer.parseInt(travelPackageVO.getTariffCode()));
					//b1.setPremiumPayable(new BigDecimal(travelPackageVO.getPremiumAmt() ));
					Double varPromoDiscPerc= travelPackageVO.getPromoDiscPerc();
					Double varPromoDisc=0.0;
					Double varOnlineDisc=0.0;
					Double vatTax=0.0;
					Double finalPremium=0.0;
					Double finalPremiumBeforeVAT=0.0;
					Double premiumPayable=travelPackageVO.getPremiumAmt();
					b1.setPremiumPayable(new BigDecimal(premiumPayable ));
					varOnlineDisc=(travelInsuranceVO.getPremiumVO().getDiscOrLoadPerc()/100) * travelPackageVO.getPremiumAmt();
					
					if(null != varPromoDiscPerc) {
						varPromoDiscPerc= - varPromoDiscPerc;
						varPromoDisc= travelPackageVO.getPremiumAmt() * (varPromoDiscPerc / 100);						
					}
					finalPremiumBeforeVAT= premiumPayable + varPromoDisc + varOnlineDisc;
					//finalPremiumBeforeVAT=Math.round(finalPremiumBeforeVAT * 100.0)/100.0;
					vatTax= (travelInsuranceVO.getVatTaxPerc()/100) * finalPremiumBeforeVAT;
					DecimalFormat df = new DecimalFormat("###.###");
					vatTax=Double.valueOf(df.format(vatTax));
					
					finalPremium = travelPackageVO.getPremiumAmt() + varPromoDisc + varOnlineDisc +vatTax;
					finalPremium=Double.valueOf(df.format(finalPremium));
					
					b1.setFinalPremium(new BigDecimal( finalPremium ) );
					b1.setCurrencyType("AED");//Harcoding
					
					FeesAndTaxes feesAndTaxes=new FeesAndTaxes();
					feesAndTaxes.setLoadingOrDiscountPercent(new BigDecimal(travelInsuranceVO.getPremiumVO().getDiscOrLoadPerc() ));
					//feesAndTaxes.setLoadingOrDiscountAmount(new BigDecimal((travelInsuranceVO.getPremiumVO().getDiscOrLoadPerc()/100) * travelPackageVO.getPremiumAmt()));
					feesAndTaxes.setLoadingOrDiscountAmount(new BigDecimal( varOnlineDisc ));
					
					if(null != varPromoDiscPerc) {
						feesAndTaxes.setPromoCodeDiscountPercent(new BigDecimal(varPromoDiscPerc));
						feesAndTaxes.setPromoCodeDiscountAmount(new BigDecimal((varPromoDiscPerc/100 )* travelPackageVO.getPremiumAmt()));
					}
					//feesAndTaxes.setVatRatePercent(new BigDecimal(travelPackageVO.getVatTaxPerc()));
					feesAndTaxes.setVatRatePercent(new BigDecimal(travelInsuranceVO.getVatTaxPerc()));
					feesAndTaxes.setVatAmount(new BigDecimal(vatTax));
					feesAndTaxes.setGovtTaxPercent(new BigDecimal(travelPackageVO.getGovtTax()));
					feesAndTaxes.setPolicyFees(new BigDecimal(travelPackageVO.getPolicyFees()));
					b1.setFeesAndTaxes(feesAndTaxes);
					
										
					//b1.setVatTaxPerc(travelPackageVO.getVatTaxPerc() );
					//b1.setVatTaxAmt(travelPackageVO.getVatTax() );				
					//b1.setIsRecommended(travelPackageVO.getIsRecommended() );
					
					/*if(travelPackageVO.getIsSelected().equals(true)) { 
						transactionDetails.setTariffCode(travelPackageVO.getTariffCode());
						if(null != travelPackageVO.getPromoDiscPerc()) {
							Double tempPromoDiscPerc=travelPackageVO.getPromoDiscPerc();
							Double tempPromoDisc= b1.getPremium() * (tempPromoDiscPerc / 100);
							quote.setgPremium(quote.getgPremium() - tempPromoDisc); 
						}
					}*/
					
					benefitsList[i]=b1;
					i++;
					
					int k=0;int j=0;
					//Iterating for count the Mandatory and non Mandatory covers first:
					int countMandatoryCovers=0, countOptionalCovers=0;
					for(CoverDetailsVO coverDetailsVO:travelPackageVO.getCovers()){
						if(coverDetailsVO.getMandatoryIndicator()){
							countMandatoryCovers++;
						}else {
							countOptionalCovers++;
						}							
					}
					//n=travelPackageVO.getCovers().size();
					coversList=new Covers[countMandatoryCovers];
					optionalCoversList=new Covers[countOptionalCovers];
					//coversList=new Covers[countMandatoryCovers];
					//optionalCoversList=new OptionalCovers[countOptionalCovers];
					for(CoverDetailsVO coverDetailsVO:travelPackageVO.getCovers()){
						if(coverDetailsVO.getMandatoryIndicator()){//Mandatory covers
							Covers c1=new Covers();
							c1.setCoverName(coverDetailsVO.getCoverName());
							//c1.setProductId(coverDetailsVO.getTariffCode());
							//c1.setCoverId( "" + Integer.toString(travelInsuranceVO.getPolicyClassCode()) + "-" + travelInsuranceVO.getPolicyType() + "-" + coverDetailsVO.getCoverCodes().getCovCode() ); 
							c1.setCoverId( "" + Integer.toString(coverDetailsVO.getCoverCodes().getCovCode()) + "-" + coverDetailsVO.getCoverCodes().getCovTypeCode() + "-" + coverDetailsVO.getCoverCodes().getCovSubTypeCode() );
							c1.setSumInsured(coverDetailsVO.getSumInsured().getaDesc() );//.getSumInsured();
							//c1.setMandatoryIndicator(coverDetailsVO.getMandatoryIndicator() );
							c1.setIsCovered(coverDetailsVO.getIsCovered() );
							//c1.setFieldType(coverDetailsVO.getFieldType().toString());
							
							RiskDetails riskDetails=new RiskDetails();
							riskDetails.setRiskCode(coverDetailsVO.getRiskCodes().getRiskCode());
							riskDetails.setBasicRskCode(coverDetailsVO.getRiskCodes().getBasicRskCode());
							riskDetails.setRiskCat(coverDetailsVO.getRiskCodes().getRiskCat());
							riskDetails.setRiskType(coverDetailsVO.getRiskCodes().getRiskType());
							c1.setRiskDetails(riskDetails);
						
							coversList[k]=c1;
							k++;
							//if(k==countMandatoryCovers) break;
						}else {
							Covers c2=new Covers();
							c2.setCoverName(coverDetailsVO.getCoverName());
							//c2.setProductId(coverDetailsVO.getTariffCode());
							c2.setCoverId( "" + Integer.toString(travelInsuranceVO.getPolicyClassCode()) + "-" + travelInsuranceVO.getPolicyType() + "-" + coverDetailsVO.getCoverCodes().getCovCode() ); 
							c2.setSumInsured(coverDetailsVO.getSumInsured().geteDesc() );//.getSumInsured();
							//c2.setMandatoryIndicator(coverDetailsVO.getMandatoryIndicator() );
							c2.setIsCovered(coverDetailsVO.getIsCovered() );
							//c2.setFieldType(coverDetailsVO.getFieldType().toString());
							
							RiskDetails riskDetails=new RiskDetails();
							riskDetails.setRiskCode(coverDetailsVO.getRiskCodes().getRiskCode());
							riskDetails.setBasicRskCode(coverDetailsVO.getRiskCodes().getBasicRskCode());
							riskDetails.setRiskCat(coverDetailsVO.getRiskCodes().getRiskCat());
							riskDetails.setRiskType(coverDetailsVO.getRiskCodes().getRiskType());
							c2.setRiskDetails(riskDetails);
							
							optionalCoversList[j]=c2;
							j++;
							//if(j==countOptionalCovers) break;
						}
					}
					b1.setCovers(coversList);
					b1.setOptionalCovers(optionalCoversList);
				}
				quote.setBenefits(benefitsList);
				quote.setInclCommunication(true);
				//quote.setMessage("Webservices succefully completed");				
			}
		}
		
		if(isReferral) {
			//List t= travelInsuranceVO.getReferralVOList().getReferrals().get(0).getReferralText();
			Map referralMap=travelInsuranceVO.getReferralVOList().getReferrals().get(0).getRefDataTextField(); //System.out.println(referralMap);
			
			String field=null;String message=null;
			Iterator it=referralMap.entrySet().iterator();
			while(it.hasNext()) {
				Map.Entry entry=(Map.Entry)it.next();
				field=(String)entry.getKey(); 
				Map map=(Map)entry.getValue();
				Iterator it2=map.entrySet().iterator();
				while(it2.hasNext()) {
					Map.Entry entry2=(Map.Entry) it2.next();
					String unknownNo=(String)entry2.getKey();
					message=(String)entry2.getValue();					
				}
			}
						
			ResourceBundle resourceBundle = ResourceBundle.getBundle("config.messages");
			String code="";
			try {
				code=resourceBundle.getString(field);
			}catch(NullPointerException e) {
				code="";
				System.out.println("Null value from properties file...");
			}
			com.rsaame.pas.b2c.ws.vo.Errors[] errorList=new com.rsaame.pas.b2c.ws.vo.Errors[2];
			com.rsaame.pas.b2c.ws.vo.Errors error=error= new com.rsaame.pas.b2c.ws.vo.Errors();//PKS
			error.setCode(code);
	        error.setMessage(message);
	        error.setField(field);
	        
			errorList[i]=error;
			quote.setErrors(errorList);
			//quote.setMessage( (String)t.get(0) );			
		}			
		} else {
			throw new Exception("Unexpected request or value object");
		}

	}

}
