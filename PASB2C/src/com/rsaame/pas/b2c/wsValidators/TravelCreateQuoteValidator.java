
package com.rsaame.pas.b2c.wsValidators;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.b2c.cmn.utils.AppUtils;
import com.rsaame.pas.b2c.cmn.utils.ValidationUtil;
import com.rsaame.pas.b2c.ws.vo.Customer;
import com.rsaame.pas.b2c.ws.vo.ErrorMapping;
import com.rsaame.pas.b2c.ws.vo.Travellers;
import com.rsaame.pas.b2c.ws.vo.WsAppConstants;
import com.rsaame.pas.b2c.wsException.ValidationError;
import com.rsaame.pas.b2c.wsException.ValidationException;
public class TravelCreateQuoteValidator {
	ValidationException validationException = new ValidationException();
	List<ValidationError> errors = new ArrayList<ValidationError>();
	public ValidationException validate(Object arg0) {
		Customer customer = (Customer)arg0;
		int countSelf=0;
		int maxAge=0;
		if(customer!=null || Utils.isEmpty(customer))
		{
			/* For Customer Mobile Number */
			if(customer.getCustomerDetails().getMobileNo()==null || Utils.isEmpty(customer.getCustomerDetails().getMobileNo()))
			{
				ValidationError error = ErrorMapping.errorMapping("MobileNo", "WS_003");
				errors.add(error);
			}
			else{
				ValidationError error = CommonValidator.isValidPhoneNo(customer.getCustomerDetails().getMobileNo());
				if (error != null)
				{
					errors.add(error);
				}
			}
			
			
			/* For Customer Email Id */
			if(customer.getCustomerDetails().getEmailId()==null || Utils.isEmpty(customer.getCustomerDetails().getEmailId()))
			{
				ValidationError error = ErrorMapping.errorMapping("EmailId", "WS_001");
				errors.add(error);
			}
			else
			{
				ValidationError error = CommonValidator.isValidEmail(customer.getCustomerDetails().getEmailId());
				if (error != null)
				{
					errors.add(error);
				}
			}
			
			/* For Customer Nationality */
			if(customer.getCustomerDetails().getNationality()!=null && !Utils.isEmpty(customer.getCustomerDetails().getNationality()))
			{
				ValidationError error = CommonValidator.isValidNationality(customer.getCustomerDetails().getNationality());
				if (error != null)
				{
					errors.add(error);
				}
			}
			/* For Customer First Name */
			if(customer.getCustomerDetails().getFirstName()!=null && !Utils.isEmpty(customer.getCustomerDetails().getFirstName()))
			{
					ValidationError error = CommonValidator.isValidFirstName(customer.getCustomerDetails().getFirstName());
					if (error != null)
					{
						errors.add(error);
					}
			}
			
			/* For Customer Last Name */
			if(customer.getCustomerDetails().getLastName()!=null && !Utils.isEmpty(customer.getCustomerDetails().getLastName()))
			{
				ValidationError error = CommonValidator.isValidLastName(customer.getCustomerDetails().getLastName());
				if (error != null)
				{
					errors.add(error);
				}
			}
			
			/* For Customer Post Office */
			if(customer.getCustomerDetails().getPoBox()!=null || !Utils.isEmpty(customer.getCustomerDetails().getPoBox()))
			{
					ValidationError error = CommonValidator.isValidPostBox(customer.getCustomerDetails().getPoBox());
					if (error != null)
					{
						errors.add(error);
					}
			}
			/* For Customer City */
			if(customer.getCustomerDetails().getCity()==null || Utils.isEmpty(customer.getCustomerDetails().getCity()))
			{
				ValidationError error=ErrorMapping.errorMapping("City","WS_068");
				errors.add(error);
			}
			if(customer.getCustomerDetails().getCity()!=null && !Utils.isEmpty(customer.getCustomerDetails().getCity()))
			{
					ValidationError error = CommonValidator.isValidCity(customer.getCustomerDetails().getCity());
					if (error != null)
					{
						errors.add(error);
					}
			}
			/* For Customer InsuredID */
			if(customer.getCustomerDetails().getInsuredId()!=null && !Utils.isEmpty(customer.getCustomerDetails().getInsuredId()))
			{
				if(customer.getCustomerDetails().getInsuredId()<0)
				{
					ValidationError error = ErrorMapping.errorMapping("InsuredId", com.Constant.CONST_WS_254);
					errors.add(error);
				}
				else if(customer.getCustomerDetails().getInsuredId().toString().length()>WsAppConstants.maxInsuredIdLength)
				{
					ValidationError error = ErrorMapping.errorMapping("InsuredId", "WS_061");
					errors.add(error);
				}
			}
			
			/* For Customer NationalId */
			if(customer.getCustomerDetails().getNationalID()!=null && !Utils.isEmpty(customer.getCustomerDetails().getNationalID()))
			{
				ValidationError error = CommonValidator.isValidNationalID(customer.getCustomerDetails().getNationalID());
				if (error != null)
				{
					errors.add(error);
				}
			}
			/* For Customer VatRegNo */
			if(customer.getCustomerDetails().getVatRegNo()!=null && !Utils.isEmpty(customer.getCustomerDetails().getVatRegNo()))
			{
				ValidationError error = CommonValidator.isValidVatRegnNo(customer.getCustomerDetails().getVatRegNo());
				if (error != null)
				{
					errors.add(error);
				}
			}
			/* For Customer RewardProgrammeType */
			if(customer.getCustomerDetails().getRewardProgrammeType()!=null && !Utils.isEmpty(customer.getCustomerDetails().getRewardProgrammeType()))
			{
				ValidationError error = CommonValidator.isValidRewardProgrammeType(customer.getCustomerDetails().getRewardProgrammeType());
				if (error != null)
				{
					errors.add(error);
				}
			}
			/* For Customer RewardCardNumber */
			if(customer.getCustomerDetails().getRewardCardNumber()!=null && !Utils.isEmpty(customer.getCustomerDetails().getRewardCardNumber()))
			{
				ValidationError error = CommonValidator.isValidRewardCardNumber(customer.getCustomerDetails().getRewardCardNumber());
				if (error != null)
				{
					errors.add(error);
				}
			}
			/* For Transaction Details*/
			List<ValidationError> errors1 = new ArrayList<ValidationError>();
			if(customer.getTransactionDetails()==null || Utils.isEmpty(customer.getTransactionDetails()))
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
				errors1 = CommonValidator.validateTransactionDetails(customer.getTransactionDetails());
				if(errors1!=null)
				{
					errors.addAll(errors1);
				}
				List<Integer> distChannels = new ArrayList<Integer>();
				distChannels.add(4);
				distChannels.add(9);
				distChannels.add(10);
				/* For Transaction Distribution Channel */
				if(!distChannels.contains(customer.getTransactionDetails().getDistChannel()))
				{
					ValidationError error = ErrorMapping.errorMapping("DistChannel", "WS_257");
					errors.add(error);
				}
				
				/* For Transaction PolicyEffectiveDate */
	            DateFormat dateFormat = new SimpleDateFormat(com.Constant.CONST_YYYY_MM_DD);
	            if (!Utils.isEmpty(customer.getTransactionDetails().getEffectiveDate()) && !(customer.getTransactionDetails().getEffectiveDate()==null)) {
	                  String date = dateFormat.format(customer.getTransactionDetails().getEffectiveDate()).toString();
	                  
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
	            if (!Utils.isEmpty(customer.getTransactionDetails().getExpiryDate()) && !(customer.getTransactionDetails().getExpiryDate()==null)) {
	                  String date = dateFormat.format(customer.getTransactionDetails().getExpiryDate()).toString();
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
				
				if (!Utils.isEmpty(customer.getTransactionDetails().getEffectiveDate())
						&& !Utils.isEmpty(customer.getTransactionDetails().getExpiryDate())) {
					Calendar fromDate = Calendar.getInstance();
					Calendar toDate = Calendar.getInstance();
					fromDate.setTime(customer.getTransactionDetails().getEffectiveDate());
					toDate.setTime(customer.getTransactionDetails().getExpiryDate());
					if (toDate.before(fromDate)) {
						ValidationError error = ErrorMapping.errorMapping("Time period for Policy", "WS_019");
						errors.add(error);
					}
				}
				if(customer.getTransactionDetails().getEffectiveDate()!=null && !Utils.isEmpty(customer.getTransactionDetails().getEffectiveDate())
						&& customer.getTransactionDetails().getExpiryDate()!=null && !Utils.isEmpty(customer.getTransactionDetails().getExpiryDate()))
				{
					if(!policyTerm.contains(AppUtils.getDateDifference(customer.getTransactionDetails().getExpiryDate(), customer.getTransactionDetails().getEffectiveDate()).intValue()))
					{
						ValidationError error = ErrorMapping.errorMapping("PolicyEffectiveDate and PolicyExpiryDate", "WS_125");
		                errors.add(error);
					}
					/* For Transaction PolicyTerm */
					if(!Utils.isEmpty(customer.getTransactionDetails().getPolicyTerm()) && !(customer.getTransactionDetails().getPolicyTerm()==null) )
					{
						if(customer.getTransactionDetails().getPolicyTerm()<0)
						{
							ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_POLICYTERM, com.Constant.CONST_WS_254);
							errors.add(error);
						}
						else if(AppUtils.getDateDifference(customer.getTransactionDetails().getExpiryDate(), customer.getTransactionDetails().getEffectiveDate()).intValue()!=customer.getTransactionDetails().getPolicyTerm() 
								|| !(policyTerm.contains(customer.getTransactionDetails().getPolicyTerm())))
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
				if(!Utils.isEmpty(customer.getTransactionDetails().getPolicyTypeCode()) && !(customer.getTransactionDetails().getPolicyTypeCode()==null))
				{
					
					if(customer.getTransactionDetails().getPolicyTypeCode()<0)
					{
						ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_POLICYTYPECODE, com.Constant.CONST_WS_254);
						errors.add(error);
					}
					else if(!(Integer.parseInt(resourceBundle.getString("TRAVEL_LONG_TERM_POLICY_TYPE"))==customer.getTransactionDetails().getPolicyTypeCode()
							|| Integer.parseInt(resourceBundle.getString("TRAVEL_SHORT_TERM_POLICY_TYPE"))==customer.getTransactionDetails().getPolicyTypeCode()))
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
				if(!Utils.isEmpty(customer.getTransactionDetails().getClassCode()) && !(customer.getTransactionDetails().getClassCode()==null))
				{
					if(customer.getTransactionDetails().getClassCode()<0)
					{
						ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_CLASSCODE, com.Constant.CONST_WS_254);
						errors.add(error);
					}
					if(Integer.parseInt(resourceBundle.getString("TRAVEL_CLASS_CODE"))!=customer.getTransactionDetails().getClassCode())
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
			
			/* For Traveller Details */
			if(customer.getTravellers()==null || Utils.isEmpty(customer.getTravellers()))
			{
				ValidationError error = ErrorMapping.errorMapping("TravellerDetails", "WS_052");
				errors.add(error);
			}
			else
			{
				Travellers[] travellers =  customer.getTravellers();
				for(Travellers t : travellers)
				{
					boolean isSelf= false;
					/* For Traveller Name */
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
					/* For Traveller Dob */
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
					
					/* For Traveller Relation */
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
					/* For Traveller Nationality */
					if(t.getTravellerNationality()==null || Utils.isEmpty(t.getTravellerNationality()))
					{
						ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_TRAVELLERNATIONALITY, "WS_057");
						errors.add(error);
					}
					else
					{
						if(ValidationUtil.countDigits(t.getTravellerNationality())>WsAppConstants.maxNationalityLength)
						{
							ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_TRAVELLERNATIONALITY, "WS_093");
							errors.add(error);
						}
						if(isSelf==true && customer.getCustomerDetails().getNationality()!=null && !Utils.isEmpty(customer.getCustomerDetails().getNationality()))
						{
							if( t.getTravellerNationality()!=customer.getCustomerDetails().getNationality())
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
			/* For UnderWriting Questions */
			if(customer.getUnderWritingQuestions()==null || Utils.isEmpty(customer.getUnderWritingQuestions()))
			{
				ValidationError error = ErrorMapping.errorMapping("UnderWriting Questions", "WS_128");
				errors.add(error);
			}
			else
			{
				if(customer.getUnderWritingQuestions().getWarZone()==null || Utils.isEmpty(customer.getUnderWritingQuestions().getWarZone()))
				{
					ValidationError error = ErrorMapping.errorMapping("WarZone", "WS_129");
					errors.add(error);
				}
				else if(customer.getUnderWritingQuestions().getWarZone().equals(true)) {
					ValidationError error = ErrorMapping.errorMapping("WarZone", "WS_263");
					errors.add(error);
				}
				if(customer.getUnderWritingQuestions().getInclUsaCa()==null || Utils.isEmpty(customer.getUnderWritingQuestions().getInclUsaCa()))
				{
					ValidationError error = ErrorMapping.errorMapping("InclUsaCa", "WS_051");
					errors.add(error);
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
