package com.rsaame.pas.b2c.wsValidators;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.b2c.cmn.utils.AppUtils;
import com.rsaame.pas.b2c.cmn.utils.ValidationUtil;
import com.rsaame.pas.b2c.ws.vo.ErrorMapping;
import com.rsaame.pas.b2c.ws.vo.MandatoryCovers;
import com.rsaame.pas.b2c.ws.vo.OptionalCovers;
import com.rsaame.pas.b2c.ws.vo.Products;
import com.rsaame.pas.b2c.ws.vo.Travellers;
import com.rsaame.pas.b2c.ws.vo.UpdateTravelQuoteRequest;
import com.rsaame.pas.b2c.ws.vo.WsAppConstants;
import com.rsaame.pas.b2c.wsException.ValidationError;
import com.rsaame.pas.b2c.wsException.ValidationException;

public class UpdateTravelQuoteValidator {
	boolean containsPersonalBaggage=false;
	boolean containsEmergencyMedicalExpenses=false;
	ValidationException validationException = new ValidationException();
	List<ValidationError> errors = new ArrayList<ValidationError>();
	int countSelf=0;
	int maxAge=0;
	public ValidationException validate(Object arg0) {
		UpdateTravelQuoteRequest updateTravelQuoteRequest =  (UpdateTravelQuoteRequest)arg0;
		
		if(updateTravelQuoteRequest!=null && !Utils.isEmpty(updateTravelQuoteRequest))
		{
			 /* For Customer Mobile Number */ 
			if(updateTravelQuoteRequest.getCustomerDetails().getMobileNo()==null || Utils.isEmpty(updateTravelQuoteRequest.getCustomerDetails().getMobileNo()))
			{
				ValidationError error = ErrorMapping.errorMapping("MobileNo", "WS_003");
				errors.add(error);
			}
			else{
				ValidationError error = CommonValidator.isValidPhoneNo(updateTravelQuoteRequest.getCustomerDetails().getMobileNo().toString());
				if (error != null)
				{
					errors.add(error);
				}
			}
			
			
			 /*For Customer Email Id*/ 
			if(updateTravelQuoteRequest.getCustomerDetails().getEmailId()==null || Utils.isEmpty(updateTravelQuoteRequest.getCustomerDetails().getEmailId()))
			{
				ValidationError error = ErrorMapping.errorMapping("EmailId", "WS_001");
				errors.add(error);
			}
			else
			{
				ValidationError error = CommonValidator.isValidEmail(updateTravelQuoteRequest.getCustomerDetails().getEmailId());
				if (error != null)
				{
					errors.add(error);
				}
			}
			
			 /*For Customer First Name */
			if(updateTravelQuoteRequest.getCustomerDetails().getFirstName()==null || Utils.isEmpty(updateTravelQuoteRequest.getCustomerDetails().getFirstName()))
			{
				ValidationError error = ErrorMapping.errorMapping("FirstName", "WS_062");
				errors.add(error);
			}
			else
			{
					ValidationError error = CommonValidator.isValidFirstName(updateTravelQuoteRequest.getCustomerDetails().getFirstName());
					if (error != null)
					{
						errors.add(error);
					}
			}
			
			 /* For Customer Last Name */
			if(updateTravelQuoteRequest.getCustomerDetails().getLastName()==null || Utils.isEmpty(updateTravelQuoteRequest.getCustomerDetails().getLastName()))
			{
				ValidationError error = ErrorMapping.errorMapping("LastName", "WS_064");
				errors.add(error);
			}
			else
			{
					ValidationError error = CommonValidator.isValidLastName(updateTravelQuoteRequest.getCustomerDetails().getLastName());
					if (error != null)
					{
						errors.add(error);
					}
			}
			
			 /*For Customer Post Office */
			if(updateTravelQuoteRequest.getCustomerDetails().getPoBox()==null || Utils.isEmpty(updateTravelQuoteRequest.getCustomerDetails().getPoBox()))
			{
				ValidationError error = ErrorMapping.errorMapping("PostBoxNo", "WS_066");
				errors.add(error);
			}
			else
			{
					ValidationError error = CommonValidator.isValidPostBox(updateTravelQuoteRequest.getCustomerDetails().getPoBox());
					if (error != null)
					{
						errors.add(error);
					}
			}
			 /*For Customer City*/ 
			if(updateTravelQuoteRequest.getCustomerDetails().getCity()==null || Utils.isEmpty(updateTravelQuoteRequest.getCustomerDetails().getCity()))
			{
				ValidationError error = ErrorMapping.errorMapping("City", "WS_068");
				errors.add(error);
			}
			else
			{
					ValidationError error = CommonValidator.isValidCity(updateTravelQuoteRequest.getCustomerDetails().getCity());
					if (error != null)
					{
						errors.add(error);
					}
			}
			 /*For Customer InsuredId */
			if(updateTravelQuoteRequest.getCustomerDetails().getInsuredId()==null || Utils.isEmpty(updateTravelQuoteRequest.getCustomerDetails().getInsuredId()))
			{
				ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_INSUREDID, "WS_060");
				errors.add(error);
			}
			else
			{
				if(updateTravelQuoteRequest.getCustomerDetails().getInsuredId()<0)
				{
					ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_INSUREDID, com.Constant.CONST_WS_254);
					errors.add(error);
				}
				else if(updateTravelQuoteRequest.getCustomerDetails().getInsuredId().toString().length()>WsAppConstants.maxInsuredIdLength)
				{
					ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_INSUREDID, "WS_061");
					errors.add(error);
				}
			}
			 /*For Customer Nationality */
			if(updateTravelQuoteRequest.getCustomerDetails().getNationality()!=null && !Utils.isEmpty(updateTravelQuoteRequest.getCustomerDetails().getNationality()))
			{
				ValidationError error = CommonValidator.isValidNationality(updateTravelQuoteRequest.getCustomerDetails().getNationality());
				if (error != null)
				{
					errors.add(error);
				}
			}
			 /*For Customer NationalId */
			if(updateTravelQuoteRequest.getCustomerDetails().getNationalID()!=null && !Utils.isEmpty(updateTravelQuoteRequest.getCustomerDetails().getNationalID()))
			{
				ValidationError error = CommonValidator.isValidNationalID(updateTravelQuoteRequest.getCustomerDetails().getNationalID());
				if (error != null)
				{
					errors.add(error);
				}
			}
			 /*For Customer VatRegNo*/ 
			if(updateTravelQuoteRequest.getCustomerDetails().getVatRegNo()!=null && !Utils.isEmpty(updateTravelQuoteRequest.getCustomerDetails().getVatRegNo()))
			{
				ValidationError error = CommonValidator.isValidVatRegnNo(updateTravelQuoteRequest.getCustomerDetails().getVatRegNo());
				if (error != null)
				{
					errors.add(error);
				}
			}
			 /*For Customer RewardProgrammeType*/ 
			if(updateTravelQuoteRequest.getCustomerDetails().getRewardProgrammeType()!=null && !Utils.isEmpty(updateTravelQuoteRequest.getCustomerDetails().getRewardProgrammeType()))
			{
				ValidationError error = CommonValidator.isValidRewardProgrammeType(updateTravelQuoteRequest.getCustomerDetails().getRewardProgrammeType());
				if (error != null)
				{
					errors.add(error);
				}
			}
			 /*For Customer RewardCardNumber*/ 
			if(updateTravelQuoteRequest.getCustomerDetails().getRewardCardNumber()!=null && !Utils.isEmpty(updateTravelQuoteRequest.getCustomerDetails().getRewardCardNumber()))
			{
				ValidationError error = CommonValidator.isValidRewardCardNumber(updateTravelQuoteRequest.getCustomerDetails().getRewardCardNumber());
				if (error != null)
				{
					errors.add(error);
				}
			}
			
			 /*For Quotation No*/ 
			if(updateTravelQuoteRequest.getQuotationNo()==null || Utils.isEmpty(updateTravelQuoteRequest.getQuotationNo()))
			{
				ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_QUOTATIONNO, "WS_071");
				errors.add(error);
			}
			else
			{
				if(updateTravelQuoteRequest.getQuotationNo()<0)
				{
					ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_QUOTATIONNO, com.Constant.CONST_WS_254);
					errors.add(error);
				}
				else if(ValidationUtil.countDigits(updateTravelQuoteRequest.getQuotationNo())>WsAppConstants.maxQuotationNoLength)
				{
					ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_QUOTATIONNO, "WS_072");
					errors.add(error);
				}
			}
			 /*For Endorsement Id*/ 
			if(updateTravelQuoteRequest.getEndtId()==null || Utils.isEmpty(updateTravelQuoteRequest.getEndtId()))
			{
				ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_ENDTID, "WS_073");
				errors.add(error);
			}
			else
			{
				if(updateTravelQuoteRequest.getEndtId()<0)
				{
					ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_ENDTID, com.Constant.CONST_WS_254);
					errors.add(error);
				}
				else if(ValidationUtil.countDigits(updateTravelQuoteRequest.getEndtId().toString().length())>WsAppConstants.maxEndtIdLength)
				{
					ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_ENDTID, "WS_074");
					errors.add(error);
				}
			}
			 /*For Endorsement No*/ 
			if(updateTravelQuoteRequest.getEndtNo()==null || Utils.isEmpty(updateTravelQuoteRequest.getEndtNo()))
			{
				ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_ENDTNO, "WS_075");
				errors.add(error);
			}
			else
			{
				if(updateTravelQuoteRequest.getEndtNo()<0)
				{
					ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_ENDTNO, com.Constant.CONST_WS_254);
					errors.add(error);
				}
				else if(ValidationUtil.countDigits(updateTravelQuoteRequest.getEndtNo().toString().length())>WsAppConstants.maxEndtNoLength)
				{
					ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_ENDTNO, "WS_076");
					errors.add(error);
				}
			}
			 /*For Policy Id*/ 
			if(updateTravelQuoteRequest.getPolicyId()==null || Utils.isEmpty(updateTravelQuoteRequest.getPolicyId()))
			{
				ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_POLICYID, "WS_077");
				errors.add(error);
			}
			else
			{
				if(updateTravelQuoteRequest.getPolicyId()<0)
				{
					ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_POLICYID, com.Constant.CONST_WS_254);
					errors.add(error);
				}
				else if(ValidationUtil.countDigits(updateTravelQuoteRequest.getPolicyId().toString().length())>WsAppConstants.maxPolicyIdLength)
				{
					ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_POLICYID, "WS_078");
					errors.add(error);
				}
			}
			 /*For Quotation Status */
			if(updateTravelQuoteRequest.getQuoteStatus()==null || Utils.isEmpty(updateTravelQuoteRequest.getQuoteStatus()))
			{
				ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_QUOTESTATUS, "WS_0123");
				errors.add(error);
			}
			else
			{
				if(updateTravelQuoteRequest.getQuoteStatus()<0)
				{
					ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_QUOTESTATUS, com.Constant.CONST_WS_254);
					errors.add(error);
				}
				if(ValidationUtil.countDigits(updateTravelQuoteRequest.getQuoteStatus().toString().length())>WsAppConstants.maxQuotationStatusLength)
				{
					ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_QUOTESTATUS, "WS_124");
					errors.add(error);
				}
			}
			 /*For Transaction Details*/
			List<ValidationError> errors1 = new ArrayList<ValidationError>();
			if(updateTravelQuoteRequest.getTransactionDetails()==null || Utils.isEmpty(updateTravelQuoteRequest.getTransactionDetails()))
			{
				ValidationError error = ErrorMapping.errorMapping("TransactionalDetails", "WS_020");
				errors.add(error);
			}
			else
			{
				List<Integer> policyTerm = new ArrayList<Integer>();
				policyTerm.add(5);
				policyTerm.add(9);
				policyTerm.add(15);
				policyTerm.add(22);
				policyTerm.add(31);
				policyTerm.add(45);
				policyTerm.add(62);
				policyTerm.add(92);
				policyTerm.add(180);
				policyTerm.add(365);
				policyTerm.add(366);
				errors1 = CommonValidator.validateTransactionDetails(updateTravelQuoteRequest.getTransactionDetails());
				if(errors1!=null)
				{
					errors.addAll(errors1);
				}
				List<Integer> distChannels = new ArrayList<Integer>();
				distChannels.add(4);
				distChannels.add(9);
				distChannels.add(10);
				/* For Transaction Distribution Channel */
				if(!distChannels.contains(updateTravelQuoteRequest.getTransactionDetails().getDistChannel()))
				{
					ValidationError error = ErrorMapping.errorMapping("DistChannel", "WS_257");
					errors.add(error);
				}
				
				/* For Transaction PolicyEffectiveDate */
	            DateFormat dateFormat = new SimpleDateFormat(com.Constant.CONST_YYYY_MM_DD);
	            if (!Utils.isEmpty(updateTravelQuoteRequest.getTransactionDetails().getEffectiveDate()) && !(updateTravelQuoteRequest.getTransactionDetails().getEffectiveDate()==null)) {
	                  String date = dateFormat.format(updateTravelQuoteRequest.getTransactionDetails().getEffectiveDate()).toString();
	                  
	                  if(!ValidationUtil.validateJavaDate(date,com.Constant.CONST_YYYY_MM_DD))
	                  {
	                        ValidationError error = ErrorMapping.errorMapping("PolicyEffectiveDate", "WS_016");
	                        errors.add(error);
	                  }
	            }
	            else
	            {
	                  ValidationError error = ErrorMapping.errorMapping("PolicyEffectiveDate", "WS_015");
	                  errors.add(error);
	            }
	            
	            /* For Transaction PolicyExpiryDate */
	            if (!Utils.isEmpty(updateTravelQuoteRequest.getTransactionDetails().getExpiryDate()) && !(updateTravelQuoteRequest.getTransactionDetails().getExpiryDate()==null)) {
	                  String date = dateFormat.format(updateTravelQuoteRequest.getTransactionDetails().getEffectiveDate()).toString();
	                  if(!ValidationUtil.validateJavaDate(date,com.Constant.CONST_YYYY_MM_DD))
	                  {
	                        ValidationError error = ErrorMapping.errorMapping("PolicyExpiryDate", "WS_018");
	                        errors.add(error);
	                  }
	            }
	            else
	            {
	                  ValidationError error = ErrorMapping.errorMapping("PolicyExpiryDate", "WS_017");
	                  errors.add(error);
	            }
				
				if (!Utils.isEmpty(updateTravelQuoteRequest.getTransactionDetails().getEffectiveDate())
						&& !Utils.isEmpty(updateTravelQuoteRequest.getTransactionDetails().getExpiryDate())) {
					Calendar fromDate = Calendar.getInstance();
					Calendar toDate = Calendar.getInstance();
					fromDate.setTime(updateTravelQuoteRequest.getTransactionDetails().getEffectiveDate());
					toDate.setTime(updateTravelQuoteRequest.getTransactionDetails().getExpiryDate());
					if (toDate.before(fromDate)) {
						ValidationError error = ErrorMapping.errorMapping("Time period for Policy", "WS_019");
						errors.add(error);
					}
				}
				if(updateTravelQuoteRequest.getTransactionDetails().getEffectiveDate()!=null && !Utils.isEmpty(updateTravelQuoteRequest.getTransactionDetails().getEffectiveDate())
						&& updateTravelQuoteRequest.getTransactionDetails().getExpiryDate()!=null && !Utils.isEmpty(updateTravelQuoteRequest.getTransactionDetails().getExpiryDate()))
				{
					if(!policyTerm.contains(AppUtils.getDateDifference(updateTravelQuoteRequest.getTransactionDetails().getExpiryDate(), updateTravelQuoteRequest.getTransactionDetails().getEffectiveDate()).intValue()))
					{
						ValidationError error = ErrorMapping.errorMapping("PolicyEffectiveDate and PolicyExpiryDate", "WS_125");
		                errors.add(error);
					}
					
					/*For Transaction PolicyTerm */
					if(!Utils.isEmpty(updateTravelQuoteRequest.getTransactionDetails().getPolicyTerm()) && !(updateTravelQuoteRequest.getTransactionDetails().getPolicyTerm()==null) )
					{
						if(updateTravelQuoteRequest.getTransactionDetails().getPolicyTerm()<0)
						{
							ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_POLICYTERM, com.Constant.CONST_WS_254);
							errors.add(error);
						}
						else if(AppUtils.getDateDifference(updateTravelQuoteRequest.getTransactionDetails().getExpiryDate(), updateTravelQuoteRequest.getTransactionDetails().getEffectiveDate()).intValue()!=updateTravelQuoteRequest.getTransactionDetails().getPolicyTerm() 
								|| !(policyTerm.contains(updateTravelQuoteRequest.getTransactionDetails().getPolicyTerm())))
						{
							ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_POLICYTERM, "WS_006");
							errors.add(error);
						}
					}
					else
					{
						ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_POLICYTERM, "WS_005");
						errors.add(error);
					}
				}
				
				ResourceBundle resourceBundle = ResourceBundle.getBundle("config.appconfig");
				/* For Transaction PolicyTypeCode */
				if(!Utils.isEmpty(updateTravelQuoteRequest.getTransactionDetails().getPolicyTypeCode()) && !(updateTravelQuoteRequest.getTransactionDetails().getPolicyTypeCode()==null))
				{
					
					if(updateTravelQuoteRequest.getTransactionDetails().getPolicyTypeCode()<0)
					{
						ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_POLICYTYPECODE, com.Constant.CONST_WS_254);
						errors.add(error);
					}
					else if(!(Integer.parseInt(resourceBundle.getString("TRAVEL_LONG_TERM_POLICY_TYPE"))==updateTravelQuoteRequest.getTransactionDetails().getPolicyTypeCode()
							|| Integer.parseInt(resourceBundle.getString("TRAVEL_SHORT_TERM_POLICY_TYPE"))==updateTravelQuoteRequest.getTransactionDetails().getPolicyTypeCode()))
					{
						ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_POLICYTYPECODE, "WS_012");
						errors.add(error);
					}
				}
				else
				{
					ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_POLICYTYPECODE, "WS_011");
					errors.add(error);
				}
				/* For Transaction Class Code */
				if(!Utils.isEmpty(updateTravelQuoteRequest.getTransactionDetails().getClassCode()) && !(updateTravelQuoteRequest.getTransactionDetails().getClassCode()==null))
				{
					if(updateTravelQuoteRequest.getTransactionDetails().getClassCode()<0)
					{
						ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_CLASSCODE, com.Constant.CONST_WS_254);
						errors.add(error);
					}
					if(Integer.parseInt(resourceBundle.getString("TRAVEL_CLASS_CODE"))!=updateTravelQuoteRequest.getTransactionDetails().getClassCode())
					{
						ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_CLASSCODE, "WS_038");
						errors.add(error);
					}
				}
				else
				{
					ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_CLASSCODE, "WS_037");
					errors.add(error);
				}
			}
			
			/*For Traveller Details */
			if(updateTravelQuoteRequest.getTravellers()==null || Utils.isEmpty(updateTravelQuoteRequest.getTravellers()))
			{
				ValidationError error = ErrorMapping.errorMapping("TravellerDetails", "WS_052");
				errors.add(error);
			}
			else
			{
				List<Travellers> travellers = new ArrayList<Travellers>();
				travellers = updateTravelQuoteRequest.getTravellers();
				for(Travellers t : travellers)
				{
					boolean isSelf= false;
					/* For Traveller Name*/ 
					if(t.getTravellerName()==null || Utils.isEmpty(t.getTravellerName()))
					{
						ValidationError error = ErrorMapping.errorMapping("TravellerName", "WS_053");
						errors.add(error);
					}
					else
					{
						if(!ValidationUtil.isAlphaWithSpace(t.getTravellerName()))
						{
							ValidationError error = ErrorMapping.errorMapping("TravellerName", "WS_054");
							errors.add(error);
						}
					}
					/*For Traveller Dob*/ 
					if(t.getTravellerDOB()==null || Utils.isEmpty(t.getTravellerDOB()))
					{
						ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_TRAVELLERDOB, "WS_055");
						errors.add(error);
					}
					else
					{
						DateFormat dateFormat = new SimpleDateFormat(com.Constant.CONST_YYYY_MM_DD);

						String date = dateFormat.format(t.getTravellerDOB()).toString();
						if(!ValidationUtil.validateJavaDate(date, com.Constant.CONST_YYYY_MM_DD))
						{
							ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_TRAVELLERDOB, "WS_056");
							errors.add(error);
						}
						else
						{
                            Calendar calendar = Calendar.getInstance();
							calendar.setTime(t.getTravellerDOB());
							int age = ValidationUtil.getAge(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DATE)); 
							if(age>maxAge)
							{
								maxAge=age;
							}
						}
					}
					
					/* For Traveller Relation*/ 
					if(t.getRelation()==null || Utils.isEmpty(t.getRelation()))
					{
						ValidationError error = ErrorMapping.errorMapping("TravellerRelation", "WS_058");
						errors.add(error);
					}
					else
					{
						List<Integer> relations = new ArrayList<Integer>();
						relations.add(1);
						relations.add(25);
						relations.add(26);
						relations.add(27);
						if(!relations.contains(t.getRelation().intValue()))
						{
							ValidationError error = ErrorMapping.errorMapping("TravellerRelation", "WS_059");
							errors.add(error);
						}
						if(t.getRelation()==1)
						{
							countSelf++;
							isSelf=true;
						}
					}
					/*For Traveller Nationality*/ 
					if(t.getTravellerNationality()==null || Utils.isEmpty(t.getTravellerNationality()))
					{
						ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_TRAVELLERNATIONALITY, "WS_057");
						errors.add(error);
					}
					else
					{
						if(t.getTravellerNationality()<0)
						{
							ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_TRAVELLERNATIONALITY, com.Constant.CONST_WS_254);
							errors.add(error);
						}
						if(ValidationUtil.countDigits(t.getTravellerNationality())>WsAppConstants.maxNationalityLength)
						{
							ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_TRAVELLERNATIONALITY, "WS_093");
							errors.add(error);
						}
						if(isSelf==true && updateTravelQuoteRequest.getCustomerDetails().getNationality()!=null && !Utils.isEmpty(updateTravelQuoteRequest.getCustomerDetails().getNationality()))
						{
							if( t.getTravellerNationality()!=updateTravelQuoteRequest.getCustomerDetails().getNationality())
							{
								ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_TRAVELLERNATIONALITY, "WS_126");
								errors.add(error);
							}
						}
						
					}
					
				}
				if(countSelf==0)
				{
					ValidationError error = ErrorMapping.errorMapping("TravellerDetails Relation", "WS_262");
					errors.add(error);
				}
				if(countSelf>1)
				{
					ValidationError error = ErrorMapping.errorMapping("TravellerDetails Relation", "WS_127");
					errors.add(error);
				}
				if(maxAge<WsAppConstants.minTravellerAge)
				{
					ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_TRAVELLERDOB, "WS_091");
					errors.add(error);
				}
				if(maxAge>WsAppConstants.maxTravellerAge)
				{
					ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_TRAVELLERDOB, "WS_092");
					errors.add(error);
				}
			}
			
			/* For UnderWriting Questions*/ 
			if(updateTravelQuoteRequest.getUnderWritingQuestions()==null || Utils.isEmpty(updateTravelQuoteRequest.getUnderWritingQuestions()))
			{
				ValidationError error = ErrorMapping.errorMapping("UnderWriting Questions", "WS_128");
				errors.add(error);
			}
			else
			{
				if(updateTravelQuoteRequest.getUnderWritingQuestions().getInclUsaCa()==null || Utils.isEmpty(updateTravelQuoteRequest.getUnderWritingQuestions().getInclUsaCa()))
				{
					ValidationError error = ErrorMapping.errorMapping("InclUsaCa", "WS_051");
					errors.add(error);
				}
				if(updateTravelQuoteRequest.getUnderWritingQuestions().getWarZone()==null || Utils.isEmpty(updateTravelQuoteRequest.getUnderWritingQuestions().getWarZone()))
				{
					ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_WARZONE, "WS_129");
					errors.add(error);
				}
				else if(updateTravelQuoteRequest.getUnderWritingQuestions().getWarZone().equals(true)) {
					ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_WARZONE, "WS_263");
					errors.add(error);
				}
				if(updateTravelQuoteRequest.getUnderWritingQuestions().getClaim()==null || Utils.isEmpty(updateTravelQuoteRequest.getUnderWritingQuestions().getClaim()))
				{
					ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_WARZONE, "WS_130");
					errors.add(error);
				}
			}
			  
			 /* For For Products*/ 
			if(updateTravelQuoteRequest.getProducts()==null || Utils.isEmpty(updateTravelQuoteRequest.getProducts()))
			{
				ValidationError error = ErrorMapping.errorMapping("Products", "WS_131");
				errors.add(error);
			}
			else
			{
				List<Products> products = new ArrayList<Products>();
				products=updateTravelQuoteRequest.getProducts();
				for(Products p : products)
				{
					 /*For ProductCode*/ 
					if(p.getProductCode()==null || Utils.isEmpty(p.getProductCode()))
					{
						ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_PRODUCTCODE, "WS_132");
						errors.add(error);
					}
					else
					{
						if(p.getProductCode()<0)
						{
							ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_PRODUCTCODE, com.Constant.CONST_WS_254);
							errors.add(error);
						}
						else if(!(p.getProductCode()==WsAppConstants.SchengenProductCode || p.getProductCode()==WsAppConstants.ExecutiveProductCode || p.getProductCode()==WsAppConstants.HolidayProductCode))
						{
							ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_PRODUCTCODE, "WS_133");
							errors.add(error);
						}
					}
					 /*For CurrencyType*/ 
					if(p.getCurrencyType()==null || Utils.isEmpty(p.getCurrencyType()))
					{
						ValidationError error = ErrorMapping.errorMapping("CurrencyType", "WS_134");
						errors.add(error);
					}
					else
					{
						if(!(p.getCurrencyType().equalsIgnoreCase("aed")))
						{
							ValidationError error = ErrorMapping.errorMapping("CurrencyType", "WS_138");
							errors.add(error);
						}
					}
					 /*For FinalPremium*/ 
					if(p.getFinalPremium()==null || Utils.isEmpty(p.getFinalPremium()))
					{
						ValidationError error = ErrorMapping.errorMapping("FinalPremium", "WS_135");
						errors.add(error);
					}
					 /*For PremiumPayable*/ 
					if(p.getPremiumPayable()==null || Utils.isEmpty(p.getPremiumPayable()))
					{
						ValidationError error = ErrorMapping.errorMapping("PremiumPayable", "WS_136");
						errors.add(error);
					}
					 /*For ProductDesc*/ 
					if(p.getProductDesc()==null || Utils.isEmpty(p.getProductDesc()))
					{
						ValidationError error = ErrorMapping.errorMapping("ProductDesc", "WS_137");
						errors.add(error);
					}
					else
					{
						if(p.getProductDesc().length()>WsAppConstants.maxProductDescLength)
						{
							ValidationError error = ErrorMapping.errorMapping("ProductDesc", "WS_139");
							errors.add(error);
						}
					}
					 /*For FeesAndTaxes */ 
					if(p.getFeesAndTaxes()==null || Utils.isEmpty(p.getFeesAndTaxes()))
					{
						ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_VATRATEPERCENT, "WS_121");
						errors.add(error);
					}
					else
					{
						/* For VatRatePercent */
						if(p.getFeesAndTaxes().getVatRatePercent()==null || Utils.isEmpty(p.getFeesAndTaxes().getVatRatePercent()))
						{
							ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_VATRATEPERCENT, "WS_121");
							errors.add(error5);
						}
						else
						{
							if(p.getFeesAndTaxes().getVatRatePercent().compareTo(new BigDecimal(0.0))==-1)
							{
								ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_VATRATEPERCENT, com.Constant.CONST_WS_254);
								errors.add(error5);
							}
						}
						/* For GovtTaxPercent */
						if(p.getFeesAndTaxes().getGovtTaxPercent()!=null && !Utils.isEmpty(p.getFeesAndTaxes().getGovtTaxPercent()))
						{
							if(ValidationUtil.integerDigits(p.getFeesAndTaxes().getGovtTaxPercent())>WsAppConstants.maxGovtTaxPercentLength)
							{
								ValidationError error5 = ErrorMapping.errorMapping("GovtTaxPercent", com.Constant.CONST_WS_254);
								errors.add(error5);
							}
						}
						/* For PolicyFees */
						if(p.getFeesAndTaxes().getPolicyFees()!=null && !Utils.isEmpty(p.getFeesAndTaxes().getPolicyFees()))
						{
							if(ValidationUtil.integerDigits(p.getFeesAndTaxes().getPolicyFees())>WsAppConstants.maxPolicyFeesLength)
							{
								ValidationError error5 = ErrorMapping.errorMapping("PolicyFees", com.Constant.CONST_WS_254);
								errors.add(error5);
							}
						}
						/* For VatAmount */
						if(p.getFeesAndTaxes().getVatAmount()!=null && !Utils.isEmpty(p.getFeesAndTaxes().getVatAmount()))
						{
							if(p.getFeesAndTaxes().getVatAmount().compareTo(new BigDecimal(0.0))==-1)
							{
								ValidationError error5 = ErrorMapping.errorMapping("VatAmount", com.Constant.CONST_WS_254);
								errors.add(error5);
							}
							else if(ValidationUtil.integerDigits(p.getFeesAndTaxes().getVatAmount())>WsAppConstants.maxVatAmountLength)
							{
								ValidationError error5 = ErrorMapping.errorMapping("VatAmount", com.Constant.CONST_WS_254);
								errors.add(error5);
							}
						}
						
					}
					/* For Mandatory Covers */ 
					if(p.getMandatoryCovers()==null || Utils.isEmpty(p.getMandatoryCovers())){
						ValidationError error5 = ErrorMapping.errorMapping("MandatoryCoverDetails", "WS_036");
						errors.add(error5);
					}
					else
					{
						List<MandatoryCovers> mandatoryCovers = new ArrayList<MandatoryCovers>();
						mandatoryCovers = p.getMandatoryCovers();
						for(MandatoryCovers c : mandatoryCovers)
						{
							/* For CoverIncluded */
							if((c.getCoverIncluded()==null || Utils.isEmpty(c.getCoverIncluded())) && c.getCoverIncluded()==true)
							{
								ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_COVERINCLUDED, "WS_105");
								errors.add(error5);
							}
							/* For CoverDesc */
							if(c.getCoverDesc()==null || Utils.isEmpty(c.getCoverDesc()))
							{
								if(c.getCoverIncluded()==true)
								{
									ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_COVERDESC, "WS_025");
									errors.add(error5);
								}

							}
							else
							{
								if(c.getCoverDesc().length()>WsAppConstants.maxCoverDescLength && c.getCoverIncluded()==true)
								{
									ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_COVERDESC, "WS_026");
									errors.add(error5);
								}
							}
							/* For Coverage Limit */ 
							if((c.getCoverageLimit()==null || Utils.isEmpty(c.getCoverageLimit())) && c.getCoverIncluded()==true)
							{
								ValidationError error5 = ErrorMapping.errorMapping("CoverageLimit", "WS_116");
								errors.add(error5);
							}
							/* For CoverMappingCode */ 
							if(c.getCoverMappingCode()==null || Utils.isEmpty(c.getCoverMappingCode()))
							{
								if(c.getCoverIncluded()==true)
								{
									ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_COVERMAPPINGCODE, "WS_106");
									errors.add(error5);
								}
							}
							else
							{
								if(c.getCoverIncluded()==true)
								{
									if(c.getCoverMappingCode().length()>WsAppConstants.maxTravelCoverMappingCodeLength)
									{
										ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_COVERMAPPINGCODE, "WS_107");
										errors.add(error5);
									}
									if(c.getCoverMappingCode().startsWith("3") && c.getCoverMappingCode().length()==5)
									{
										containsPersonalBaggage=true;
									}
									if(c.getCoverMappingCode().startsWith("22"))
									{
										containsEmergencyMedicalExpenses=true;
									}
								}else {
									if(c.getCoverMappingCode().startsWith("3") && c.getCoverMappingCode().length()==5)
									{	
										//System.out.println("CMC:--"+c.getCoverMappingCode()+" and Tariff--"+p.getProductCode());
										containsPersonalBaggage=false;
									}
									if(c.getCoverMappingCode().startsWith("22"))
									{
										containsEmergencyMedicalExpenses=false;
									}
								}
								
							}
							/* For Cover Risk Details */ 
							if(c.getRiskDetails()==null || Utils.isEmpty(c.getRiskDetails()))
							{
								ValidationError error5 = ErrorMapping.errorMapping("RiskDetails", "WS_050");
								errors.add(error5);
							}
							else
							{
								/* For Risk Id */
								if(c.getRiskDetails().getRskId()!=null || !Utils.isEmpty(c.getRiskDetails().getRskId()))
								{
									if(ValidationUtil.countDigits(c.getRiskDetails().getRskId())>WsAppConstants.maxRiskIdLength)
									{
										ValidationError error5 = ErrorMapping.errorMapping("RiskId", "WS_108");
										errors.add(error5);
									}
								}
								/* For Basic Risk Id */
								if(c.getRiskDetails().getBasicRskId()!=null || !Utils.isEmpty(c.getRiskDetails().getBasicRskId()))
								{
									if(ValidationUtil.countDigits(c.getRiskDetails().getBasicRskId())>WsAppConstants.maxBasicRiskIdLength)
									{
										ValidationError error5 = ErrorMapping.errorMapping("BasicRiskId", "WS_109");
										errors.add(error5);
									}
								}
								/* For  Basic Risk Code */
								if(c.getRiskDetails().getBasicRskCode()==null || Utils.isEmpty(c.getRiskDetails().getBasicRskCode()))
								{
									ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_BASICRISKCODE, "WS_028");
									errors.add(error5);
								}
								else
								{
									if(c.getRiskDetails().getBasicRskCode()<0)
									{
										ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_BASICRISKCODE, com.Constant.CONST_WS_254);
										errors.add(error5);
									}
									else if(ValidationUtil.countDigits(c.getRiskDetails().getBasicRskCode())>WsAppConstants.maxBasicRiskCodeLength)
									{
										ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_BASICRISKCODE, "WS_029");
										errors.add(error5);
									}
								}
								/* For Risk Code */
								if(c.getRiskDetails().getRiskCode()==null || Utils.isEmpty(c.getRiskDetails().getRiskCode()))
								{
									ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_RISKCODE1, "WS_030");
									errors.add(error5);
								}
								else
								{
									if(c.getRiskDetails().getRiskCode()<0)
									{
										ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_RISKCODE1, com.Constant.CONST_WS_254);
										errors.add(error5);
									}
									else if(ValidationUtil.countDigits(c.getRiskDetails().getRiskCode())>WsAppConstants.maxRiskCodeLength)
									{
										ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_RISKCODE1, "WS_031");
										errors.add(error5);
									}
								}
								/* For Risk Type */
								if(c.getRiskDetails().getRiskType()==null || Utils.isEmpty(c.getRiskDetails().getRiskType()))
								{
									ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_RISKTYPE1, "WS_032");
									errors.add(error5);
								}
								else
								{
									if(c.getRiskDetails().getRiskType()<0)
									{
										ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_RISKTYPE1, com.Constant.CONST_WS_254);
										errors.add(error5);
									}
									else if(ValidationUtil.countDigits(c.getRiskDetails().getRiskType())>WsAppConstants.maxRiskTypeLength)
									{
										ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_RISKTYPE1, "WS_033");
										errors.add(error5);
									}
									
								}
								/* For Risk Category */
								if(c.getRiskDetails().getRiskCat()==null || Utils.isEmpty(c.getRiskDetails().getRiskCat()))
								{
									ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_RISKCATEGORY, "WS_034");
									errors.add(error5);
								}
								else
								{
									if(c.getRiskDetails().getRiskCat()<0)
									{
										ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_RISKCATEGORY, com.Constant.CONST_WS_254);
										errors.add(error5);
									}
									else if(ValidationUtil.countDigits(c.getRiskDetails().getRiskCat())>WsAppConstants.maxRiskCategoryLength)
									{
										ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_RISKCATEGORY, "WS_035");
										errors.add(error5);
									}
								}
							}
						}
						if(containsEmergencyMedicalExpenses==false && containsPersonalBaggage==false)
						{
							ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_COVERINCLUDED, "WS_261");
							errors.add(error5);
						}
					}
					
					/* For OptionalCover */
					if(p.getOptionalCovers()!=null && !Utils.isEmpty(p.getOptionalCovers()))
					{
						List<OptionalCovers> optionalCovers = new ArrayList<OptionalCovers>();
						optionalCovers = p.getOptionalCovers();
						for(OptionalCovers c : optionalCovers)
						{
							/* For CoverDesc */ 
							if(c.getCoverDesc()==null || Utils.isEmpty(c.getCoverDesc()))
							{
								ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_COVERDESC, "WS_025");
								errors.add(error5);
							}
							else
							{
								if(c.getCoverDesc().length()>WsAppConstants.maxCoverDescLength)
								{
									ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_COVERDESC, "WS_026");
									errors.add(error5);
								}
							}
							/* For CoverIncluded */ 
							if(c.getCoverIncluded()==null || Utils.isEmpty(c.getCoverIncluded()))
							{
								ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_COVERINCLUDED, "WS_105");
								errors.add(error5);
							}
							/* For CoverMappingCode */ 
							if(c.getCoverMappingCode()==null || Utils.isEmpty(c.getCoverMappingCode()))
							{
								ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_COVERMAPPINGCODE, "WS_106");
								errors.add(error5);
							}
							else
							{
								if(c.getCoverMappingCode().length()>WsAppConstants.maxTravelCoverMappingCodeLength)
								{
									ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_COVERMAPPINGCODE, "WS_107");
									errors.add(error5);
								}
							}
							/* For Cover Risk Details */ 
							if(c.getRiskDetails()==null || Utils.isEmpty(c.getRiskDetails()))
							{
								ValidationError error5 = ErrorMapping.errorMapping("RiskDetails", "WS_050");
								errors.add(error5);
							}
							else
							{
								/* For Risk Id */
								if(c.getRiskDetails().getRskId()!=null || !Utils.isEmpty(c.getRiskDetails().getRskId()))
								{
									if(ValidationUtil.countDigits(c.getRiskDetails().getRskId())>WsAppConstants.maxRiskIdLength)
									{
										ValidationError error5 = ErrorMapping.errorMapping("RiskId", "WS_108");
										errors.add(error5);
									}
								}
								/* For Basic Risk Id */
								if(c.getRiskDetails().getBasicRskId()!=null || !Utils.isEmpty(c.getRiskDetails().getBasicRskId()))
								{
									if(ValidationUtil.countDigits(c.getRiskDetails().getBasicRskId())>WsAppConstants.maxBasicRiskIdLength)
									{
										ValidationError error5 = ErrorMapping.errorMapping("BasicRiskId", "WS_109");
										errors.add(error5);
									}
								}
								/* For  Basic Risk Code */
								if(c.getRiskDetails().getBasicRskCode()==null || Utils.isEmpty(c.getRiskDetails().getBasicRskCode()))
								{
									ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_BASICRISKCODE, "WS_028");
									errors.add(error5);
								}
								else
								{
									if(c.getRiskDetails().getBasicRskCode()<0)
									{
										ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_BASICRISKCODE, com.Constant.CONST_WS_254);
										errors.add(error5);
									}
									else if(ValidationUtil.countDigits(c.getRiskDetails().getBasicRskCode())>WsAppConstants.maxBasicRiskCodeLength)
									{
										ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_BASICRISKCODE, "WS_029");
										errors.add(error5);
									}
								}
								/* For Risk Code */
								if(c.getRiskDetails().getRiskCode()==null || Utils.isEmpty(c.getRiskDetails().getRiskCode()))
								{
									ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_RISKCODE1, "WS_030");
									errors.add(error5);
								}
								else
								{
									if(c.getRiskDetails().getRiskCode()<0)
									{
										ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_RISKCODE1, com.Constant.CONST_WS_254);
										errors.add(error5);
									}
									else if(ValidationUtil.countDigits(c.getRiskDetails().getRiskCode())>WsAppConstants.maxRiskCodeLength)
									{
										ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_RISKCODE1, "WS_031");
										errors.add(error5);
									}
								}
								/* For Risk Type */
								if(c.getRiskDetails().getRiskType()==null || Utils.isEmpty(c.getRiskDetails().getRiskType()))
								{
									ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_RISKTYPE1, "WS_032");
									errors.add(error5);
								}
								else
								{
									if(c.getRiskDetails().getRiskType()<0)
									{
										ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_RISKTYPE1, com.Constant.CONST_WS_254);
										errors.add(error5);
									}
									else if(ValidationUtil.countDigits(c.getRiskDetails().getRiskType())>WsAppConstants.maxRiskTypeLength)
									{
										ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_RISKTYPE1, "WS_033");
										errors.add(error5);
									}
								}
								/* For Risk Category */
								if(c.getRiskDetails().getRiskCat()==null || Utils.isEmpty(c.getRiskDetails().getRiskCat()))
								{
									ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_RISKCATEGORY, "WS_034");
									errors.add(error5);
								}
								else
								{
									if(c.getRiskDetails().getRiskCat()<0)
									{
										ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_RISKCATEGORY, com.Constant.CONST_WS_254);
										errors.add(error5);
									}
									else if(ValidationUtil.countDigits(c.getRiskDetails().getRiskCat())>WsAppConstants.maxRiskCategoryLength)
									{
										ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_RISKCATEGORY, "WS_035");
										errors.add(error5);
									}
								}
							}
						}
					}
					
				}
			}
		}
		else
		{
			ValidationError error5 = ErrorMapping.errorMapping("RequestObject", "WS_140");
			errors.add(error5);
		}
		validationException.setErrors(errors);
		return validationException;
	}
}
