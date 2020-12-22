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
import com.rsaame.pas.b2c.ws.vo.ListedItems;
import com.rsaame.pas.b2c.ws.vo.MandatoryCovers;
import com.rsaame.pas.b2c.ws.vo.OptionalCovers;
import com.rsaame.pas.b2c.ws.vo.Staff;
import com.rsaame.pas.b2c.ws.vo.TLLimit;
import com.rsaame.pas.b2c.ws.vo.UpdateHomeQuoteRequest;
import com.rsaame.pas.b2c.ws.vo.WsAppConstants;
import com.rsaame.pas.b2c.wsException.ValidationError;
import com.rsaame.pas.b2c.wsException.ValidationException;

public class UpdateHomeQuoteValidator {

	ValidationException validationException = new ValidationException();
	List<ValidationError> errors = new ArrayList<ValidationError>();
	public ValidationException validate(Object arg0) {
		UpdateHomeQuoteRequest updateHomeQuoteRequest = (UpdateHomeQuoteRequest) arg0;
		int ownerShipStatus=0;
		boolean containPersonalPossession=false;
		boolean containContentItems=false;
		boolean containPpItems=false;
		boolean mandatoryCoverPresent=false;
		boolean containsOptionalCovers=false;
		boolean containsStaffDetails=false;
		boolean containsListedItems=false;
		BigDecimal contentCoveraggeLimit= new BigDecimal(0.0);
		BigDecimal ppCoveraggeLimit= new BigDecimal(0.0);
		BigDecimal sumContentItemsCoveraggeLimit= new BigDecimal(0.0);
		BigDecimal sumPpItemsCoveraggeLimit= new BigDecimal(0.0);
		BigDecimal minContentItemsCoveraggeLimit= new BigDecimal(40000.0);
		BigDecimal minPpItemsCoveraggeLimit= new BigDecimal(10000.0);
		 
		if (!Utils.isEmpty(updateHomeQuoteRequest) && updateHomeQuoteRequest!=null)
				{
			Boolean finalUpdate = finapUpdate(updateHomeQuoteRequest);  //CTS 26.08.2020 TFS# 41983 - Mandatory Validation Change City Update Quote
			/* For Customer Mobile Number */
			if(updateHomeQuoteRequest.getCustomerDetails().getMobileNo()==null || Utils.isEmpty(updateHomeQuoteRequest.getCustomerDetails().getMobileNo()))
			{
				ValidationError error = ErrorMapping.errorMapping("MobileNo", "WS_003");
				errors.add(error);
			}
			else{
				ValidationError error = CommonValidator.isValidPhoneNo(updateHomeQuoteRequest.getCustomerDetails().getMobileNo().toString());
				if (error != null)
				{
					errors.add(error);
				}
			}
			
			
			/* For Customer Email Id */
			if(updateHomeQuoteRequest.getCustomerDetails().getEmailId()==null || Utils.isEmpty(updateHomeQuoteRequest.getCustomerDetails().getEmailId()))
			{
				ValidationError error = ErrorMapping.errorMapping("EmailId", "WS_001");
				errors.add(error);
			}
			else
			{
				ValidationError error = CommonValidator.isValidEmail(updateHomeQuoteRequest.getCustomerDetails().getEmailId());
				if (error != null)
				{
					errors.add(error);
				}
			}
			
			/* For Customer First Name */
			if(updateHomeQuoteRequest.getCustomerDetails().getFirstName()==null || Utils.isEmpty(updateHomeQuoteRequest.getCustomerDetails().getFirstName()))
			{
				ValidationError error = ErrorMapping.errorMapping("FirstName", "WS_062");
				errors.add(error);
			}
			else
			{
					ValidationError error = CommonValidator.isValidFirstName(updateHomeQuoteRequest.getCustomerDetails().getFirstName());
					if (error != null)
					{
						errors.add(error);
					}
			}
			
			/* For Customer Last Name */
			if(updateHomeQuoteRequest.getCustomerDetails().getLastName()==null || Utils.isEmpty(updateHomeQuoteRequest.getCustomerDetails().getLastName()))
			{
				ValidationError error = ErrorMapping.errorMapping("LastName", "WS_064");
				errors.add(error);
			}
			else
			{
					ValidationError error = CommonValidator.isValidLastName(updateHomeQuoteRequest.getCustomerDetails().getLastName());
					if (error != null)
					{
						errors.add(error);
					}
			}
			/* For Customer City */
			//CTS 26.08.2020 TFS# 41983 - Mandatory Validation Change City Update Quote
			if((updateHomeQuoteRequest.getCustomerDetails().getCity()==null || Utils.isEmpty(updateHomeQuoteRequest.getCustomerDetails().getCity())) && finalUpdate) 
			{
				ValidationError error = ErrorMapping.errorMapping("City", "WS_068");
				errors.add(error);
			}
			/*//CTS 26.08.2020 TFS# 41983 - Mandatory Validation Change City Update Quote - start
			 else
			{
					ValidationError error = CommonValidator.isValidCity(updateHomeQuoteRequest.getCustomerDetails().getCity());
					if (error != null)
					{
						errors.add(error);
					}
			}//CTS 26.08.2020 TFS# 41983 - Mandatory Validation Change City Update Quote - end*/
			/* For Customer InsuredId */
			if(updateHomeQuoteRequest.getCustomerDetails().getInsuredId()==null || Utils.isEmpty(updateHomeQuoteRequest.getCustomerDetails().getInsuredId()))
			{
				ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_INSUREDID, "WS_060");
				errors.add(error);
			}
			else
			{
				if(updateHomeQuoteRequest.getCustomerDetails().getInsuredId()<0)
				{
					ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_INSUREDID, com.Constant.CONST_WS_254);
					errors.add(error);
				}
				if(updateHomeQuoteRequest.getCustomerDetails().getInsuredId().toString().length()>WsAppConstants.maxInsuredIdLength)
				{
					ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_INSUREDID, "WS_061");
					errors.add(error);
				}
			}
			/* For Customer Nationality */
			if(updateHomeQuoteRequest.getCustomerDetails().getNationality()!=null && !Utils.isEmpty(updateHomeQuoteRequest.getCustomerDetails().getNationality()))
			{
				ValidationError error = CommonValidator.isValidNationality(updateHomeQuoteRequest.getCustomerDetails().getNationality());
				if (error != null)
				{
					errors.add(error);
				}
			}
			/* For Customer NationalId */
			if(updateHomeQuoteRequest.getCustomerDetails().getNationalID()!=null && !Utils.isEmpty(updateHomeQuoteRequest.getCustomerDetails().getNationalID()))
			{
				ValidationError error = CommonValidator.isValidNationalID(updateHomeQuoteRequest.getCustomerDetails().getNationalID());
				if (error != null)
				{
					errors.add(error);
				}
			}
			/* For Customer VatRegNo */
			if(updateHomeQuoteRequest.getCustomerDetails().getVatRegNo()!=null && !Utils.isEmpty(updateHomeQuoteRequest.getCustomerDetails().getVatRegNo()))
			{
				ValidationError error = CommonValidator.isValidVatRegnNo(updateHomeQuoteRequest.getCustomerDetails().getVatRegNo());
				if (error != null)
				{
					errors.add(error);
				}
			}
			/* For Customer RewardProgrammeType */
			if(updateHomeQuoteRequest.getCustomerDetails().getRewardProgrammeType()!=null && !Utils.isEmpty(updateHomeQuoteRequest.getCustomerDetails().getRewardProgrammeType()))
			{
				ValidationError error = CommonValidator.isValidRewardProgrammeType(updateHomeQuoteRequest.getCustomerDetails().getRewardProgrammeType());
				if (error != null)
				{
					errors.add(error);
				}
			}
			/* For Customer RewardCardNumber */
			if(updateHomeQuoteRequest.getCustomerDetails().getRewardCardNumber()!=null && !Utils.isEmpty(updateHomeQuoteRequest.getCustomerDetails().getRewardCardNumber()))
			{
				ValidationError error = CommonValidator.isValidRewardCardNumber(updateHomeQuoteRequest.getCustomerDetails().getRewardCardNumber());
				if (error != null)
				{
					errors.add(error);
				}
			}
			
			/* For Quotation No */
			if(updateHomeQuoteRequest.getQuotationNo()==null || Utils.isEmpty(updateHomeQuoteRequest.getQuotationNo()))
			{
				ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_QUOTATIONNO, "WS_071");
				errors.add(error);
			}
			else
			{
				if(updateHomeQuoteRequest.getQuotationNo()<0)
				{
					ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_QUOTATIONNO, com.Constant.CONST_WS_254);
					errors.add(error);
				}
				else if(ValidationUtil.countDigits(updateHomeQuoteRequest.getQuotationNo())>WsAppConstants.maxQuotationNoLength)
				{
					ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_QUOTATIONNO, "WS_072");
					errors.add(error);
				}
			}
			/* For Endorsement Id */
			if(updateHomeQuoteRequest.getEndtId()==null || Utils.isEmpty(updateHomeQuoteRequest.getEndtId()))
			{
				ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_ENDTID, "WS_073");
				errors.add(error);
			}
			else
			{
				if(updateHomeQuoteRequest.getEndtId()<0)
				{
					ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_ENDTID, com.Constant.CONST_WS_254);
					errors.add(error);
				}
				else if(ValidationUtil.countDigits(updateHomeQuoteRequest.getEndtId().toString().length())>WsAppConstants.maxEndtIdLength)
				{
					ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_ENDTID, "WS_074");
					errors.add(error);
				}
			}
			/* For Endorsement No */
			if(updateHomeQuoteRequest.getEndtNo()==null || Utils.isEmpty(updateHomeQuoteRequest.getEndtNo()))
			{
				ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_ENDTNO, "WS_075");
				errors.add(error);
			}
			else
			{
				if(updateHomeQuoteRequest.getEndtNo()<0)
				{
					ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_ENDTNO, com.Constant.CONST_WS_254);
					errors.add(error);
				}
				else if(ValidationUtil.countDigits(updateHomeQuoteRequest.getEndtNo().toString().length())>WsAppConstants.maxEndtNoLength)
				{
					ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_ENDTNO, "WS_076");
					errors.add(error);
				}
			}
			/* For Policy Id */
			if(updateHomeQuoteRequest.getPolicyId()==null || Utils.isEmpty(updateHomeQuoteRequest.getPolicyId()))
			{
				ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_POLICYID, "WS_077");
				errors.add(error);
			}
			else
			{
				if(updateHomeQuoteRequest.getPolicyId()<0)
				{
					ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_POLICYID, com.Constant.CONST_WS_254);
					errors.add(error);
				}
				else if(ValidationUtil.countDigits(updateHomeQuoteRequest.getPolicyId().toString().length())>WsAppConstants.maxPolicyIdLength)
				{
					ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_POLICYID, "WS_078");
					errors.add(error);
				}
			}
			/* For Quotation Status */
			if(updateHomeQuoteRequest.getQuoteStatus()!=null || !Utils.isEmpty(updateHomeQuoteRequest.getQuoteStatus()))
			{
			/*	ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_QUOTESTATUS, "WS_123");
				errors.add(error);
			}
			else
			{*/
				if(updateHomeQuoteRequest.getQuoteStatus()<0)
				{
					ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_QUOTESTATUS, com.Constant.CONST_WS_254);
					errors.add(error);
				}
				else if(ValidationUtil.countDigits(updateHomeQuoteRequest.getQuoteStatus().toString().length())>WsAppConstants.maxQuotationStatusLength)
				{
					ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_QUOTESTATUS, "WS_124");
					errors.add(error);
				}
				//07.08.2020 CTS  CR#11645 - Home Digital API - error  message need to display, if trying to update the quote, if already converted into policy- start
				else if(updateHomeQuoteRequest.getQuoteStatus() == 7)
				{
					ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_QUOTESTATUS, "WS_318");
					errors.add(error);
				}
				//07.08.2020 CTS  CR#11645  - ENDS
				//18.08.2020 CTS  CR#11645 - Home Digital - API integration inconsistency fix  - message need to display, if quote is lapsed or reffered to rsa- start
				else if(updateHomeQuoteRequest.getQuoteStatus() == 10)
				{
					ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_QUOTESTATUS, "WS_319");
					errors.add(error);
				}
				else if(updateHomeQuoteRequest.getQuoteStatus() == 20)
				{
					ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_QUOTESTATUS, "WS_320");
					errors.add(error);
				}
				else if(updateHomeQuoteRequest.getQuoteStatus() == 5)
				{
					ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_QUOTESTATUS, "WS_321");
					errors.add(error);
				}
				//18.08.2020 CTS  CR#11645  - ENDS
			}
			/* For Transaction Details*/
			List<ValidationError> errors1 = new ArrayList<ValidationError>();
			if(updateHomeQuoteRequest.getTransactionDetails()==null || Utils.isEmpty(updateHomeQuoteRequest.getTransactionDetails()))
			{
				ValidationError error = ErrorMapping.errorMapping("TransactionalDetails", "WS_020");
				errors.add(error);
			}
			else
			{
				//errors1 = CommonValidator.validateTransactionDetails(updateHomeQuoteRequest.getTransactionDetails());
				if(errors1!=null)
				{
					errors.addAll(errors1);
				}
				/*if(!(updateHomeQuoteRequest.getTransactionDetails().getTariffCode() == WsAppConstants.PACKAGED_HOME_TARIFF_CODE 
						|| updateHomeQuoteRequest.getTransactionDetails().getTariffCode() == WsAppConstants.STANDARD_HOME_TARIFF_CODE))
				{
					ValidationError error = ErrorMapping.errorMapping("TariffCode", "WS_010");
					errors.add(error);
				}
				List<Integer> distChannels = new ArrayList<Integer>();
				distChannels.add(4);
				distChannels.add(9);
				distChannels.add(10);
				 For Transaction Distribution Channel 
				if(!distChannels.contains(updateHomeQuoteRequest.getTransactionDetails().getDistChannel()))
				{
					ValidationError error = ErrorMapping.errorMapping("DistChannel", "WS_257");
					errors.add(error);
				}*/
				/* For Transaction PolicyEffectiveDate */
	            DateFormat dateFormat = new SimpleDateFormat(com.Constant.CONST_YYYY_MM_DD);
	            if (!Utils.isEmpty(updateHomeQuoteRequest.getTransactionDetails().getEffectiveDate()) && !(updateHomeQuoteRequest.getTransactionDetails().getEffectiveDate()==null)) {
	                  String date = dateFormat.format(updateHomeQuoteRequest.getTransactionDetails().getEffectiveDate()).toString();
	                  
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
	            if (!Utils.isEmpty(updateHomeQuoteRequest.getTransactionDetails().getExpiryDate()) && !(updateHomeQuoteRequest.getTransactionDetails().getExpiryDate()==null)) {
	                  String date = dateFormat.format(updateHomeQuoteRequest.getTransactionDetails().getEffectiveDate()).toString();
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
				
				if (!Utils.isEmpty(updateHomeQuoteRequest.getTransactionDetails().getEffectiveDate())
						&& !Utils.isEmpty(updateHomeQuoteRequest.getTransactionDetails().getExpiryDate())) {
					Calendar fromDate = Calendar.getInstance();
					Calendar toDate = Calendar.getInstance();
					fromDate.setTime(updateHomeQuoteRequest.getTransactionDetails().getEffectiveDate());
					toDate.setTime(updateHomeQuoteRequest.getTransactionDetails().getExpiryDate());
					if (toDate.before(fromDate)) {
						ValidationError error = ErrorMapping.errorMapping("Time period for Policy", "WS_019");
						errors.add(error);
					}
				}
				if(updateHomeQuoteRequest.getTransactionDetails().getEffectiveDate()!=null && !Utils.isEmpty(updateHomeQuoteRequest.getTransactionDetails().getEffectiveDate())
						&& updateHomeQuoteRequest.getTransactionDetails().getExpiryDate()!=null && !Utils.isEmpty(updateHomeQuoteRequest.getTransactionDetails().getExpiryDate()))
				{
					//CTS 14.09.2020 - TFS#43972 -  Mismatch in the policy expiry date on test and live - added the Leap Year condition - Start
					if((AppUtils.getDateDifference(updateHomeQuoteRequest.getTransactionDetails().getExpiryDate(), updateHomeQuoteRequest.getTransactionDetails().getEffectiveDate()).intValue()!=365 
							&& AppUtils.isLeapYearMonth(updateHomeQuoteRequest.getTransactionDetails().getEffectiveDate()) == false)
							|| (AppUtils.getDateDifference(updateHomeQuoteRequest.getTransactionDetails().getExpiryDate(), updateHomeQuoteRequest.getTransactionDetails().getEffectiveDate()).intValue()!=366
									&& AppUtils.isLeapYearMonth(updateHomeQuoteRequest.getTransactionDetails().getEffectiveDate()) == true))
					{
						ValidationError error = ErrorMapping.errorMapping("PolicyEffectiveDate and PolicyExpiryDate", "WS_122");
		                errors.add(error);
					}
					//CTS 14.09.2020 - TFS#43972 -  Mismatch in the policy expiry date on test and live - ends
					
					/* For Transaction PolicyTerm */
					/*if(!Utils.isEmpty(updateHomeQuoteRequest.getTransactionDetails().getPolicyTerm()) && !(updateHomeQuoteRequest.getTransactionDetails().getPolicyTerm()==null) )
					{
						if(updateHomeQuoteRequest.getTransactionDetails().getPolicyTerm()<0)
						{
							ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_POLICYTERM, com.Constant.CONST_WS_254);
							errors.add(error);
						}
						if(AppUtils.getDateDifference(updateHomeQuoteRequest.getTransactionDetails().getExpiryDate(), updateHomeQuoteRequest.getTransactionDetails().getEffectiveDate()).intValue()!=updateHomeQuoteRequest.getTransactionDetails().getPolicyTerm() 
								&& !( updateHomeQuoteRequest.getTransactionDetails().getPolicyTerm()==365 ||  updateHomeQuoteRequest.getTransactionDetails().getPolicyTerm()==366))
						{
							ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_POLICYTERM, "WS_006");
							errors.add(error);
						}
					}
					else
					{
						ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_POLICYTERM, "WS_005");
						errors.add(error);
					}*/
				}
				
				ResourceBundle resourceBundle = ResourceBundle.getBundle("config.appconfig");
				/* For Transaction PolicyTypeCode */
				/*if(!Utils.isEmpty(updateHomeQuoteRequest.getTransactionDetails().getPolicyTypeCode()) && !(updateHomeQuoteRequest.getTransactionDetails().getPolicyTypeCode()==null))
				{
					
					if(updateHomeQuoteRequest.getTransactionDetails().getPolicyTypeCode()<0)
					{
						ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_POLICYTYPECODE, com.Constant.CONST_WS_254);
						errors.add(error);
					}
					else if(Integer.parseInt(resourceBundle.getString("HOME_POLICY_TYPE"))!=updateHomeQuoteRequest.getTransactionDetails().getPolicyTypeCode())
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
				 For Transaction Class Code 
				if(!Utils.isEmpty(updateHomeQuoteRequest.getTransactionDetails().getClassCode()) && !(updateHomeQuoteRequest.getTransactionDetails().getClassCode()==null))
				{
					if(updateHomeQuoteRequest.getTransactionDetails().getClassCode()<0)
					{
						ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_CLASSCODE, com.Constant.CONST_WS_254);
						errors.add(error);
					}
					if(Integer.parseInt(resourceBundle.getString("HOME_CLASS_CODE"))!=updateHomeQuoteRequest.getTransactionDetails().getClassCode())
					{
						ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_CLASSCODE, "WS_038");
						errors.add(error);
					}
				}
				else
				{
					ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_CLASSCODE, "WS_037");
					errors.add(error);
				}*/
			}
			/* For Fees and Taxes */
			if(updateHomeQuoteRequest.getFeesAndTaxes()==null || Utils.isEmpty(updateHomeQuoteRequest.getFeesAndTaxes()))
			{
				ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_VATRATEPERCENT, "WS_121");
				errors.add(error5);
			}
			else
			{
				/* for VatRatePercent*/
				if(updateHomeQuoteRequest.getFeesAndTaxes().getVatRatePercent()==null || Utils.isEmpty(updateHomeQuoteRequest.getFeesAndTaxes().getVatRatePercent()))
				{
					ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_VATRATEPERCENT, "WS_121");
					errors.add(error5);
				}
				else
				{
					if(updateHomeQuoteRequest.getFeesAndTaxes().getVatRatePercent().compareTo(new BigDecimal(0.0))==-1)
					{
						ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_VATRATEPERCENT, com.Constant.CONST_WS_254);
						errors.add(error5);
					}
				}
				/* For GovtTaxPercent */
				if(updateHomeQuoteRequest.getFeesAndTaxes().getGovtTaxPercent()!=null && !Utils.isEmpty(updateHomeQuoteRequest.getFeesAndTaxes().getGovtTaxPercent()))
				{
					if(ValidationUtil.integerDigits(updateHomeQuoteRequest.getFeesAndTaxes().getGovtTaxPercent())>WsAppConstants.maxGovtTaxPercentLength)
					{
						ValidationError error5 = ErrorMapping.errorMapping("GovtTaxPercent", com.Constant.CONST_WS_254);
						errors.add(error5);
					}
				}
				/* For PolicyFees */
				if(updateHomeQuoteRequest.getFeesAndTaxes().getPolicyFees()!=null && !Utils.isEmpty(updateHomeQuoteRequest.getFeesAndTaxes().getPolicyFees()))
				{
					if(ValidationUtil.integerDigits(updateHomeQuoteRequest.getFeesAndTaxes().getPolicyFees())>WsAppConstants.maxPolicyFeesLength)
					{
						ValidationError error5 = ErrorMapping.errorMapping("PolicyFees", com.Constant.CONST_WS_254);
						errors.add(error5);
					}
				}
				/* For VatAmount */
				if(updateHomeQuoteRequest.getFeesAndTaxes().getVatAmount()!=null && !Utils.isEmpty(updateHomeQuoteRequest.getFeesAndTaxes().getVatAmount()))
				{
					if(updateHomeQuoteRequest.getFeesAndTaxes().getVatAmount().compareTo(new BigDecimal(0.0))==-1)
					{
						ValidationError error5 = ErrorMapping.errorMapping("VatAmount", com.Constant.CONST_WS_254);
						errors.add(error5);
					}
					else if(ValidationUtil.integerDigits(updateHomeQuoteRequest.getFeesAndTaxes().getVatAmount())>WsAppConstants.maxVatAmountLength)
					{
						ValidationError error5 = ErrorMapping.errorMapping("VatAmount", com.Constant.CONST_WS_254);
						errors.add(error5);
					}
				}
			}
			//Boolean finalUpdate = finapUpdate(updateHomeQuoteRequest);  //CTS 26.08.2020 TFS# 41983 - Mandatory Validation Change City Update Quote -  commented and moved up
			/* For Building Details*/
			if(updateHomeQuoteRequest.getBuildingDetails()!=null && !Utils.isEmpty(updateHomeQuoteRequest.getBuildingDetails()))
			{
				
				/* For OwnerShip Status*/
				if(updateHomeQuoteRequest.getBuildingDetails().getOwnershipStatus()==null || Utils.isEmpty(updateHomeQuoteRequest.getBuildingDetails().getOwnershipStatus()))
				{
					ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_OWNERSHIPSTATUS, "WS_023");
					errors.add(error5);
				}
				else
				{
					if(updateHomeQuoteRequest.getBuildingDetails().getOwnershipStatus()<0)
					{
						ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_OWNERSHIPSTATUS, com.Constant.CONST_WS_254);
						errors.add(error5);
					}
					else if(updateHomeQuoteRequest.getBuildingDetails().getOwnershipStatus()!=WsAppConstants.OwnYourHome && updateHomeQuoteRequest.getBuildingDetails().getOwnershipStatus()!=WsAppConstants.RentYourHome)
						{
							ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_OWNERSHIPSTATUS, "WS_024");
							errors.add(error5);
						}
					else{
						ownerShipStatus = updateHomeQuoteRequest.getBuildingDetails().getOwnershipStatus();
					}
				}
				/* For Emirate */
				if((updateHomeQuoteRequest.getBuildingDetails().getEmirate()==null || Utils.isEmpty(updateHomeQuoteRequest.getBuildingDetails().getEmirate())) && finalUpdate)
				{
					ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_EMITRATE, "WS_114");
					errors.add(error5);
				}
				else if(updateHomeQuoteRequest.getBuildingDetails().getEmirate()!=null || !Utils.isEmpty(updateHomeQuoteRequest.getBuildingDetails().getEmirate()))
				{
					if(Utils.isEmpty(updateHomeQuoteRequest.getBuildingDetails().getEmirate()))
					{
						ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_EMITRATE, com.Constant.CONST_WS_254);
						errors.add(error5);
					}
					/*if(ValidationUtil.countDigits(updateHomeQuoteRequest.getBuildingDetails().getEmirate())>WsAppConstants.maxEmirateLength)
					{
						ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_EMITRATE, "WS_115");
						errors.add(error5);
					}*/
				}
				/* For Area means Area others in request*/ 
				if((updateHomeQuoteRequest.getBuildingDetails().getArea()==null || Utils.isEmpty(updateHomeQuoteRequest.getBuildingDetails().getArea())) && finalUpdate)
				{
					ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_BUILDINGAREA, "WS_079");
					errors.add(error5);
				}else if(updateHomeQuoteRequest.getBuildingDetails().getArea()!=null || !Utils.isEmpty(updateHomeQuoteRequest.getBuildingDetails().getArea())){
						if(updateHomeQuoteRequest.getBuildingDetails().getArea().length()>WsAppConstants.maxAreaOthersLength)
							{
								ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_AREAOTHERS, "WS_103");
									errors.add(error5);
							}
				}
				/*
				else
				{
					if(updateHomeQuoteRequest.getBuildingDetails().getArea() < String.valueOf(0))
					{
						ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_BUILDINGAREA, com.Constant.CONST_WS_254);
						errors.add(error5);
					}
					if(ValidationUtil.countDigits(updateHomeQuoteRequest.getBuildingDetails().getArea())>WsAppConstants.maxAreaLength)
					{
						ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_BUILDINGAREA, "WS_080");
						errors.add(error5);
					}
				}*/
				/* For Area Others means Area in request*/
				/*if(updateHomeQuoteRequest.getBuildingDetails().getAreaOthers()==null || Utils.isEmpty(updateHomeQuoteRequest.getBuildingDetails().getAreaOthers()))
				{
					if(updateHomeQuoteRequest.getBuildingDetails().getAreaOthers()==null || Utils.isEmpty(updateHomeQuoteRequest.getBuildingDetails().getAreaOthers()))
					{
						ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_AREAOTHERS, "WS_117");
						errors.add(error5);
					}
					else
					{
						if(updateHomeQuoteRequest.getBuildingDetails().getArea().length()>WsAppConstants.maxAreaOthersLength)
						{
							ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_AREAOTHERS, "WS_103");
							errors.add(error5);
						}
					}
				}
				else
				{
					if(updateHomeQuoteRequest.getBuildingDetails().getAreaOthers()!=null || !Utils.isEmpty(updateHomeQuoteRequest.getBuildingDetails().getAreaOthers()))
					{
						ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_AREAOTHERS, "WS_118");
						errors.add(error5);
					}
				}*/
				/* For PropertyType*/
				if((updateHomeQuoteRequest.getBuildingDetails().getPropertyType()==null || Utils.isEmpty(updateHomeQuoteRequest.getBuildingDetails().getPropertyType())) && finalUpdate)
				{
					ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_PROPERTYTYPE, "WS_081");
					errors.add(error5);
				}
				else if(updateHomeQuoteRequest.getBuildingDetails().getPropertyType()!=null || !Utils.isEmpty(updateHomeQuoteRequest.getBuildingDetails().getPropertyType()))
				{
					if(updateHomeQuoteRequest.getBuildingDetails().getPropertyType()<0)
					{
						ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_PROPERTYTYPE, com.Constant.CONST_WS_254);
						errors.add(error5);
					}
					if(!(updateHomeQuoteRequest.getBuildingDetails().getPropertyType()==WsAppConstants.VillaCode || updateHomeQuoteRequest.getBuildingDetails().getPropertyType()==WsAppConstants.ApartmentCode))
					{
						ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_PROPERTYTYPE, "WS_082");
						errors.add(error5);
					}
				}
				/* For Building Name */
				if((updateHomeQuoteRequest.getBuildingDetails().getBuildingName()==null || Utils.isEmpty(updateHomeQuoteRequest.getBuildingDetails().getBuildingName())) && finalUpdate)
				{
					ValidationError error5 = ErrorMapping.errorMapping("BuildingName", "WS_083");
					errors.add(error5);
				}
// CTS - TFS #42729 - Building name validation - starts
				else if(updateHomeQuoteRequest.getBuildingDetails().getBuildingName()!=null || !Utils.isEmpty(updateHomeQuoteRequest.getBuildingDetails().getBuildingName()))
				{
					if(updateHomeQuoteRequest.getBuildingDetails().getBuildingName().length()>WsAppConstants.maxBuildingNameLength )
					{
						ValidationError error5 = ErrorMapping.errorMapping("BuildingName", "WS_084");
						errors.add(error5);
					}
				}
// CTS - TFS #42729 - Building name validation - Endss
				/* For FlatVillaNo*/
				if((updateHomeQuoteRequest.getBuildingDetails().getFlatVillaNo()==null || Utils.isEmpty(updateHomeQuoteRequest.getBuildingDetails().getFlatVillaNo())) && finalUpdate)
				{
					ValidationError error5 = ErrorMapping.errorMapping("FlatVillaNo", "WS_085");
					errors.add(error5);
				}
				else if(updateHomeQuoteRequest.getBuildingDetails().getFlatVillaNo()!=null || !Utils.isEmpty(updateHomeQuoteRequest.getBuildingDetails().getFlatVillaNo()))
				{
					if(updateHomeQuoteRequest.getBuildingDetails().getFlatVillaNo().length() > WsAppConstants.maxFlatVillaNoLength)
					{
						ValidationError error5 = ErrorMapping.errorMapping("FlatVillaNo", "WS_086");
						errors.add(error5);
					}
				}
				/* For Post Office */
				if((updateHomeQuoteRequest.getBuildingDetails().getPoBox()==null || Utils.isEmpty(updateHomeQuoteRequest.getBuildingDetails().getPoBox())) && finalUpdate)
				{
					ValidationError error = ErrorMapping.errorMapping("PostBoxNo", "WS_066");
					errors.add(error);
				}
				else if(updateHomeQuoteRequest.getBuildingDetails().getPoBox()!=null || !Utils.isEmpty(updateHomeQuoteRequest.getBuildingDetails().getPoBox()))
				{
						String postBox = updateHomeQuoteRequest.getBuildingDetails().getPoBox();
						if(postBox.length()>WsAppConstants.maxPostBoxLength || (!ValidationUtil.isAlphaNumericWithSpace(postBox) && postBox.length()!=0 ) )
						{
							ValidationError error5 = ErrorMapping.errorMapping("PostBoxNo", "WS_067");
							errors.add(error5);
						}
				}
				/* For Total No Floors*/
				if((updateHomeQuoteRequest.getBuildingDetails().getTotalFloors()==null || Utils.isEmpty(updateHomeQuoteRequest.getBuildingDetails().getTotalFloors())) && finalUpdate)
				{
					ValidationError error5 = ErrorMapping.errorMapping("NumberOfFloors", "WS_305");
					errors.add(error5);
				}
				else if(updateHomeQuoteRequest.getBuildingDetails().getTotalFloors()!=null || !Utils.isEmpty(updateHomeQuoteRequest.getBuildingDetails().getTotalFloors()))
				{
					if(updateHomeQuoteRequest.getBuildingDetails().getTotalFloors() >  Short.valueOf(Utils.getSingleValueAppConfig("TOTAL_NO_FLOORS")) )
					{
						ValidationError error5 = ErrorMapping.errorMapping("NumberOfFloors", "WS_307");
						errors.add(error5);
					}
					else if(updateHomeQuoteRequest.getBuildingDetails().getTotalFloors()<0)
					{
						ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_MORTGAGECODE, com.Constant.CONST_WS_254);
						errors.add(error5);
					}
				}
				/* For Total No Rooms*/
				if((updateHomeQuoteRequest.getBuildingDetails().getTotalRooms()==null || Utils.isEmpty(updateHomeQuoteRequest.getBuildingDetails().getTotalRooms())) && finalUpdate)
				{
					ValidationError error5 = ErrorMapping.errorMapping("NumberOfBedrooms", "WS_306");
					errors.add(error5);
				}
				else if(updateHomeQuoteRequest.getBuildingDetails().getTotalRooms()!=null || !Utils.isEmpty(updateHomeQuoteRequest.getBuildingDetails().getTotalRooms()))
				{
					if(updateHomeQuoteRequest.getBuildingDetails().getTotalRooms() > Short.valueOf(Utils.getSingleValueAppConfig("TOTAL_NO_ROOMS")) )
					{
						ValidationError error5 = ErrorMapping.errorMapping("NumberOfBedrooms", "WS_308");
						errors.add(error5);
					}
					else if(updateHomeQuoteRequest.getBuildingDetails().getTotalRooms()<0)
					{
						ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_MORTGAGECODE, com.Constant.CONST_WS_254);
						errors.add(error5);
					}
				}
				/* For Latitude*/
				if((updateHomeQuoteRequest.getBuildingDetails().getLatitude()==null || Utils.isEmpty(updateHomeQuoteRequest.getBuildingDetails().getLatitude())) && finalUpdate)
				{
					ValidationError error5 = ErrorMapping.errorMapping("Latitude", "WS_312");
					errors.add(error5);
				}

				/* For Longitude*/
				if((updateHomeQuoteRequest.getBuildingDetails().getLongitude()==null || Utils.isEmpty(updateHomeQuoteRequest.getBuildingDetails().getLongitude())) && finalUpdate)
				{
					ValidationError error5 = ErrorMapping.errorMapping("Longitude", "WS_313");
					errors.add(error5);
				}

				/* For Street*/
				if((updateHomeQuoteRequest.getBuildingDetails().getStreet()==null || Utils.isEmpty(updateHomeQuoteRequest.getBuildingDetails().getStreet())) && finalUpdate)
				{
					ValidationError error5 = ErrorMapping.errorMapping("Street", "WS_314");
					errors.add(error5);
				}
				// CTS - TFS #42729 - Building name validation - starts
				else if(updateHomeQuoteRequest.getBuildingDetails().getStreet()!=null || !Utils.isEmpty(updateHomeQuoteRequest.getBuildingDetails().getStreet()))
				{
					if(updateHomeQuoteRequest.getBuildingDetails().getStreet().length()>WsAppConstants.maxBuildingNameLength )
					{
						ValidationError error5 = ErrorMapping.errorMapping("Street", "WS_084");
						errors.add(error5);
					}
				}
				// CTS - TFS #42729 - Building name validation - Ends
				/* For Zone*/
				if((updateHomeQuoteRequest.getBuildingDetails().getZone()==null || Utils.isEmpty(updateHomeQuoteRequest.getBuildingDetails().getZone())) && finalUpdate)
				{
					ValidationError error5 = ErrorMapping.errorMapping("Zone", "WS_315");
					errors.add(error5);
				}
				// CTS - TFS #42729 - Building name validation - starts
			else if(updateHomeQuoteRequest.getBuildingDetails().getZone()!=null || !Utils.isEmpty(updateHomeQuoteRequest.getBuildingDetails().getZone()))
				{
					if(updateHomeQuoteRequest.getBuildingDetails().getZone().length()>WsAppConstants.maxBuildingNameLength )
					{
						ValidationError error5 = ErrorMapping.errorMapping("Zone", "WS_084");
						errors.add(error5);
					}
				}
		// CTS - TFS #42729 - Building name validation - Ends
				/* For GRL*/
				if(updateHomeQuoteRequest.getBuildingDetails().getGrl()==null || Utils.isEmpty(updateHomeQuoteRequest.getBuildingDetails().getGrl()))
				{
					if (finalUpdate) {
						ValidationError error5 = ErrorMapping.errorMapping("GRL", "WS_316");
						errors.add(error5);
					} 
				//  Extra validation
					else if (updateHomeQuoteRequest.getBuildingDetails().getInfoMapStatus() != null) {
						ValidationError error5 = ErrorMapping.errorMapping("InfoMapStatus","WS_317");
						errors.add(error5);
					}
				} 

				/* For Mortgage Code */
				if(updateHomeQuoteRequest.getBuildingDetails().getMortgageeCode()!=null && !Utils.isEmpty(updateHomeQuoteRequest.getBuildingDetails().getMortgageeCode()))
				{
					if(updateHomeQuoteRequest.getBuildingDetails().getOwnershipStatus()==WsAppConstants.RentYourHome)
					{
						ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_MORTGAGECODE, "WS_256");
						errors.add(error5);
					}
					else if(updateHomeQuoteRequest.getBuildingDetails().getMortgageeCode()<0)
					{
						ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_MORTGAGECODE, com.Constant.CONST_WS_254);
						errors.add(error5);
					}
					else if(ValidationUtil.countDigits(updateHomeQuoteRequest.getBuildingDetails().getMortgageeCode())>WsAppConstants.maxMortgageCodeLength)
					{
						ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_MORTGAGECODE, "WS_088");
						errors.add(error5);
					}
				}
				/* For Mortgage Others */
				if(updateHomeQuoteRequest.getBuildingDetails().getMortgageeCode()!=null && updateHomeQuoteRequest.getBuildingDetails().getMortgageeCode()==WsAppConstants.MortgageOthers)
				{
					if(updateHomeQuoteRequest.getBuildingDetails().getMortgageeOthers()==null || Utils.isEmpty(updateHomeQuoteRequest.getBuildingDetails().getMortgageeOthers()))
					{
						ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_MORTGAGEOTHER, "WS_119");
						errors.add(error5);
					}
					else
					{
						if(updateHomeQuoteRequest.getBuildingDetails().getMortgageeOthers().length()>WsAppConstants.maxMortgageOthersLength)
						{
							ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_MORTGAGEOTHER, "WS_104");
							errors.add(error5);
						}
						
					}
				}
				else if(updateHomeQuoteRequest.getBuildingDetails().getMortgageeCode()!=null && updateHomeQuoteRequest.getBuildingDetails().getMortgageeCode()!=WsAppConstants.MortgageOthers)
				{
					if(updateHomeQuoteRequest.getBuildingDetails().getMortgageeOthers()!=null || !Utils.isEmpty(updateHomeQuoteRequest.getBuildingDetails().getMortgageeOthers()))
					{
						ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_MORTGAGEOTHER, "WS_120");
						errors.add(error5);
					}
				}
			}
			else if(updateHomeQuoteRequest.getTransactionDetails().getFinalUpdate().equals(true) && updateHomeQuoteRequest.getBuildingDetails()==null && Utils.isEmpty(updateHomeQuoteRequest.getBuildingDetails()))
			{
				ValidationError error5 = ErrorMapping.errorMapping("BuildingDetails", "WS_027");
				errors.add(error5);
			}
			/* For Mandatory Covers */
			if(updateHomeQuoteRequest.getMandatoryCovers()==null || Utils.isEmpty(updateHomeQuoteRequest.getMandatoryCovers())){
				ValidationError error5 = ErrorMapping.errorMapping("MandatoryCoverDetails", "WS_036");
				errors.add(error5);
			}
			else
			{
				List<MandatoryCovers> mandatoryCovers = new ArrayList<MandatoryCovers>();
				mandatoryCovers = updateHomeQuoteRequest.getMandatoryCovers();
				for(MandatoryCovers c : mandatoryCovers)
				{
					/* For CoverIncluded */
					if(c.getCoverIncluded()==null || Utils.isEmpty(c.getCoverIncluded()))
					{
						ValidationError error5 = ErrorMapping.errorMapping("CoverIncluded", "WS_105");
						errors.add(error5);
					}
					else
					{
						if(c.getCoverIncluded()==true)
						{
							mandatoryCoverPresent=true;
						}
						/* For CoverDesc */
					if((c.getCoverDesc()==null || Utils.isEmpty(c.getCoverDesc()))&&c.getCoverIncluded()==true)
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
					
					/* For CoverMappingCode */
					/*if((c.getCoverMappingCode()==null || Utils.isEmpty(c.getCoverMappingCode())) &&c.getCoverIncluded()==true)
					{
						ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_COVERMAPPINGCODE, "WS_106");
						errors.add(error5);
					}
					else
					{
						if(c.getCoverMappingCode().length()>WsAppConstants.maxHomeCoverMappingCodeLength)
						{
							ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_COVERMAPPINGCODE, "WS_107");
							errors.add(error5);
						}
					}*/
					/* For CoverageLimit */
					{
						if((c.getCoverageLimit()==null || Utils.isEmpty(c.getCoverageLimit())) &&c.getCoverIncluded()==true)
						{
							ValidationError error5 = ErrorMapping.errorMapping("CoverageLimit", "WS_252");
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
					{/*
						 For Risk Id
						if(c.getRiskDetails().getRskId()!=null || !Utils.isEmpty(c.getRiskDetails().getRskId()))
						{
							if(ValidationUtil.countDigits(c.getRiskDetails().getRskId())>6)
							{
								ValidationError error5 = ErrorMapping.errorMapping("RiskId", "WS_108");
								errors.add(error5);
							}
						}
						 For Basic Risk Id
						if(c.getRiskDetails().getBasicRskId()!=null || !Utils.isEmpty(c.getRiskDetails().getBasicRskId()))
						{
							if(ValidationUtil.countDigits(c.getRiskDetails().getBasicRskId())>6)
							{
								ValidationError error5 = ErrorMapping.errorMapping("BasicRiskId", "WS_109");
								errors.add(error5);
							}
						}*/
						/* For  Basic Risk Code*/
						String[] riskCodes= c.getRiskMappingCode().split("-");
						/*if(c.getRiskDetails().getBasicRskCode()==null || Utils.isEmpty(c.getRiskDetails().getBasicRskCode()))
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
						}*/
						/* For Risk Code*/
						if(riskCodes[0]==null || Utils.isEmpty(Integer.parseInt(riskCodes[0])))
						{
							ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_RISKCODE1, "WS_030");
							errors.add(error5);
						}
						else
						{
							if(Integer.parseInt(riskCodes[0])<0)
							{
								ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_RISKCODE1, com.Constant.CONST_WS_254);
								errors.add(error5);
							}
							else if(ValidationUtil.countDigits(Integer.parseInt(riskCodes[0]))>WsAppConstants.maxRiskCodeLength)
							{
								ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_RISKCODE1, "WS_031");
								errors.add(error5);
							}
						}
						/* For Risk Type*/
						if(riskCodes[1]==null || Utils.isEmpty(Integer.parseInt(riskCodes[1])))
						{
							ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_RISKTYPE1, "WS_032");
							errors.add(error5);
						}
						else
						{
							if(Integer.parseInt(riskCodes[1])<0)
							{
								ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_RISKTYPE1, com.Constant.CONST_WS_254);
								errors.add(error5);
							}
							if(ValidationUtil.countDigits(Integer.parseInt(riskCodes[1]))>WsAppConstants.maxRiskTypeLength)
							{
								ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_RISKTYPE1, "WS_033");
								errors.add(error5);
							}
							if(Integer.parseInt(riskCodes[1])==31 && c.getCoverIncluded()==true)
							{
								containsListedItems=true;
								containsOptionalCovers=true;
								containPersonalPossession=true;
								containContentItems=true;
								contentCoveraggeLimit=c.getCoverageLimit();
							}
							if(Integer.parseInt(riskCodes[1])==32 && containPersonalPossession==false && c.getCoverIncluded()==true)
							{
								ValidationError error5 = ErrorMapping.errorMapping("PersonalPossession", "WS_145");
								errors.add(error5);
							}
							else if(Integer.parseInt(riskCodes[1])==32 && containPersonalPossession==true && c.getCoverIncluded()==true)
							{
								ppCoveraggeLimit=c.getCoverageLimit();
								containPpItems=true;
							}
								
						}
						/* For Risk Category*/
						if(riskCodes[2]==null || Utils.isEmpty(Integer.parseInt(riskCodes[2]))) 
						{
							ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_RISKCATEGORY, "WS_034");
							errors.add(error5);
						}
						else
						{
							if(Integer.parseInt(riskCodes[2])<0)
							{
								ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_RISKCATEGORY, com.Constant.CONST_WS_254);
								errors.add(error5);
							}
							else if(ValidationUtil.countDigits(Integer.parseInt(riskCodes[2]))>WsAppConstants.maxRiskCategoryLength)
							{
								ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_RISKCATEGORY, "WS_035");
								errors.add(error5);
							}
						}
						if(Integer.parseInt(riskCodes[0])==1 && Integer.parseInt(riskCodes[1])==1 && Integer.parseInt(riskCodes[2])==0)
						{
							if(ownerShipStatus==WsAppConstants.RentYourHome && c.getCoverIncluded()==true )
							{
								ValidationError error5 = ErrorMapping.errorMapping("BuildingCoverIncluded", "WS_255");
								errors.add(error5);
							}
							if(c.getCoverIncluded()==true && (ValidationUtil.integerDigits(c.getCoverageLimit())<WsAppConstants.minBuildingCoverageLimitLength|| c.getCoverageLimit().compareTo(new BigDecimal(0.0))==-1))
							{
								ValidationError error5 = ErrorMapping.errorMapping("BuildingCoverageLimit", "WS_258");
								errors.add(error5);
							}
						}
						if(Integer.parseInt(riskCodes[0])==2 && Integer.parseInt(riskCodes[1])==31 && Integer.parseInt(riskCodes[2])==1)
						{
							if(c.getCoverIncluded()==true && (ValidationUtil.integerDigits(c.getCoverageLimit())<WsAppConstants.minContentCoverageLimitLength|| c.getCoverageLimit().compareTo(new BigDecimal(0.0))==-1))
							{
								ValidationError error5 = ErrorMapping.errorMapping("ContentCoverageLimit", "WS_259");
								errors.add(error5);
							}
							
						}
						if(Integer.parseInt(riskCodes[0])==2 && Integer.parseInt(riskCodes[1])==32 && Integer.parseInt(riskCodes[2])==1)
						{
							if(c.getCoverIncluded()==true && (ValidationUtil.integerDigits(c.getCoverageLimit())<WsAppConstants.minPersonalPossessionCoverageLimitLength|| c.getCoverageLimit().compareTo(new BigDecimal(0.0))==-1))
							{
								ValidationError error5 = ErrorMapping.errorMapping("PersonalPossessionCoverageLimit", "WS_260");
								errors.add(error5);
							}
							
						}
					}
					}
					
				}
				if(mandatoryCoverPresent==false)
				{
					ValidationError error5 = ErrorMapping.errorMapping("MandatoryCover", "WS_144");
					errors.add(error5);
				}
			}
			/* For Listed Items*/
			if(containsListedItems==false){
				List<ListedItems> listedItems = new ArrayList<ListedItems>();
				listedItems = updateHomeQuoteRequest.getListedItems();
				if(listedItems!=null && !Utils.isEmpty(listedItems) )
				{
					for(ListedItems c : listedItems)
					{
						if(c.getCoverIncluded()==true)
						{
							ValidationError error5 = ErrorMapping.errorMapping("ListedItemsDetails", "WS_304");
							errors.add(error5);
						}
					}
				}
					
			}
			else
			{
				if((updateHomeQuoteRequest.getListedItems()!=null && !Utils.isEmpty(updateHomeQuoteRequest.getListedItems()) ))
				{
					List<ListedItems> optionalCovers = new ArrayList<ListedItems>();
					optionalCovers = updateHomeQuoteRequest.getListedItems();
					for(ListedItems c : optionalCovers)
					{
						String[] riskCodes= c.getRiskMappingCode().split("-");
						/* For CoverIncluded */
						if(c.getCoverIncluded()==null || Utils.isEmpty(c.getCoverIncluded()))
						{
							ValidationError error5 = ErrorMapping.errorMapping("CoverIncluded", "WS_105");
							errors.add(error5);
						}
						else
						{
							/* For CoverDesc */
							if((c.getCoverDesc()==null || Utils.isEmpty(c.getCoverDesc())) && c.getCoverIncluded()==true)
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
							
							/* For CoverMappingCode */
							/*if((c.getCoverMappingCode()==null || Utils.isEmpty(c.getCoverMappingCode())) && c.getCoverIncluded()==true)
							{
								ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_COVERMAPPINGCODE, "WS_106");
								errors.add(error5);
							}
							else
							{
								if(c.getCoverMappingCode().length()>WsAppConstants.maxHomeCoverMappingCodeLength)
								{
									ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_COVERMAPPINGCODE, "WS_107");
									errors.add(error5);
								}
								if(c.getCoverMappingCode().startsWith("3") && c.getCoverIncluded()==true)
								{
									containsStaffDetails=true;
								}
								if(c.getCoverMappingCode().startsWith("4") && c.getCoverIncluded()==true )
								{
									if(ownerShipStatus==WsAppConstants.OwnYourHome)
									{
										ValidationError error5 = ErrorMapping.errorMapping("AdditionalTenantsLiabilityCover", "WS_113");
										errors.add(error5);
									}
								}
							}*/
							/* For Cover Risk Details */
							if(c.getRiskDetails()==null || Utils.isEmpty(c.getRiskDetails()))
							{
								ValidationError error5 = ErrorMapping.errorMapping("RiskDetails", "WS_050");
								errors.add(error5);
							}
							else
							{/*
								 For Risk Id
								if(c.getRiskDetails().getRskId()!=null || !Utils.isEmpty(c.getRiskDetails().getRskId()))
								{
									if(ValidationUtil.countDigits(c.getRiskDetails().getRskId())>6)
									{
										ValidationError error5 = ErrorMapping.errorMapping("RiskId", "WS_108");
										errors.add(error5);
									}
								}
								 For Basic Risk Id
								if(c.getRiskDetails().getBasicRskId()!=null || !Utils.isEmpty(c.getRiskDetails().getBasicRskId()))
								{
									if(ValidationUtil.countDigits(c.getRiskDetails().getBasicRskId())>6)
									{
										ValidationError error5 = ErrorMapping.errorMapping("BasicRiskId", "WS_109");
										errors.add(error5);
									}
								}*/
								/* For  Basic Risk Code*/
								/*if(c.getRiskDetails().getBasicRskCode()==null || Utils.isEmpty(c.getRiskDetails().getBasicRskCode()))
								{
									//ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_BASICRISKCODE, "WS_028");
									//errors.add(error5);
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
								}*/
								/* For Risk Code*/
								if(riskCodes[0]==null || Utils.isEmpty(Integer.parseInt(riskCodes[0])))
								{
									ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_RISKCODE1, "WS_030");
									errors.add(error5);
								}
								else
								{
									if(Integer.parseInt(riskCodes[0])<0)
									{
										ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_RISKCODE1, com.Constant.CONST_WS_254);
										errors.add(error5);
									}
									else if(ValidationUtil.countDigits(Integer.parseInt(riskCodes[0]))>WsAppConstants.maxRiskCodeLength)
									{
										ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_RISKCODE1, "WS_031");
										errors.add(error5);
									}
								}
								/* For Risk Type*/
								if(riskCodes[1]==null || Utils.isEmpty(Integer.parseInt(riskCodes[1])))
								{
									ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_RISKTYPE1, "WS_032");
									errors.add(error5);
								}
								else
								{
									if(Integer.parseInt(riskCodes[1])<0)
									{
										ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_RISKTYPE1, com.Constant.CONST_WS_254);
										errors.add(error5);
									}
									else if(ValidationUtil.countDigits(Integer.parseInt(riskCodes[1]))>WsAppConstants.maxRiskTypeLength)
									{
										ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_RISKTYPE1, "WS_033");
										errors.add(error5);
									}
								}
								/* For Risk Category*/
								if(riskCodes[2]==null || Utils.isEmpty(Integer.parseInt(riskCodes[2])))
								{
									ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_RISKCATEGORY, "WS_034");
									errors.add(error5);
								}
								else
								{
									if(Integer.parseInt(riskCodes[2])<0)
									{
										ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_RISKCATEGORY, com.Constant.CONST_WS_254);
										errors.add(error5);
									}
									if(ValidationUtil.countDigits(Integer.parseInt(riskCodes[2]))>WsAppConstants.maxRiskCategoryLength)
									{
										ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_RISKCATEGORY, "WS_035");
										errors.add(error5);
									}
									if(Integer.parseInt(riskCodes[2])==2 && Integer.parseInt(riskCodes[1])==31 
											&& c.getCoverIncluded()==true && containContentItems==false)
									{
										ValidationError error5 = ErrorMapping.errorMapping("ContentItems", "WS_146");
										errors.add(error5);
									}
									else if (Integer.parseInt(riskCodes[2])==2 && Integer.parseInt(riskCodes[1])==31 
											&& c.getCoverIncluded()==true && containContentItems==true)
									{
										if(c.getCoverageLimit()==null || Utils.isEmpty(c.getCoverageLimit()))
										{
											ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_CONTENTITEMSCOVERAGELIMIT, "WS_253");
											errors.add(error5);
										}
										else
										{
											if(c.getCoverageLimit().compareTo(minContentItemsCoveraggeLimit)==-1)
											{
												ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_CONTENTITEMSCOVERAGELIMIT, "WS_147");
												errors.add(error5);
											}
											else{
												sumContentItemsCoveraggeLimit=sumContentItemsCoveraggeLimit.add(c.getCoverageLimit());
											}
											
										}
										
									}
									else if (Integer.parseInt(riskCodes[2])==2 && Integer.parseInt(riskCodes[1])==32 
											&& c.getCoverIncluded()==true && containPpItems==false)
									{
										ValidationError error5 = ErrorMapping.errorMapping("PersonalPossessionItems", "WS_251");
										errors.add(error5);
									}
									else if (Integer.parseInt(riskCodes[2])==2 && Integer.parseInt(riskCodes[1])==32 
											&& c.getCoverIncluded()==true && containPpItems==true)
									{
										if(c.getCoverageLimit()==null || Utils.isEmpty(c.getCoverageLimit()))
										{
											ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_PERSONALPOSSESSIONITEMSCOVERAGELIMIT, "WS_253");
											errors.add(error5);
										}
										else
										{
											if(c.getCoverageLimit().compareTo(minPpItemsCoveraggeLimit)==-1)
											{
												ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_PERSONALPOSSESSIONITEMSCOVERAGELIMIT, "WS_148");
												errors.add(error5);
											}
											else{
												sumPpItemsCoveraggeLimit=sumPpItemsCoveraggeLimit.add(c.getCoverageLimit());
											}
										}
										
									}
								}
							}
						}
						
					}
				if(contentCoveraggeLimit.compareTo(sumContentItemsCoveraggeLimit)==-1)
				{
					ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_CONTENTITEMSCOVERAGELIMIT, "WS_149");
					errors.add(error5);
				}
				if(ppCoveraggeLimit.compareTo(sumPpItemsCoveraggeLimit)==-1)
				{
					ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_PERSONALPOSSESSIONITEMSCOVERAGELIMIT, "WS_150");
					errors.add(error5);
				}
				}
			}
			/* For Optional Covers*/
			if(containsOptionalCovers==false){
				List<OptionalCovers> optionalCovers = new ArrayList<OptionalCovers>();
				optionalCovers = updateHomeQuoteRequest.getOptionalCovers();
				if(optionalCovers!=null && !Utils.isEmpty(optionalCovers) )
				{
					for(OptionalCovers c : optionalCovers)
					{
						if(c.getCoverIncluded()==true)
						{
							ValidationError error5 = ErrorMapping.errorMapping("OptionalCoverDetails", "WS_110");
							errors.add(error5);
						}
					}
				}
			}
			else
			{
				if((updateHomeQuoteRequest.getOptionalCovers()!=null && !Utils.isEmpty(updateHomeQuoteRequest.getOptionalCovers()) ))
				{

					List<OptionalCovers> optionalCovers = new ArrayList<OptionalCovers>();
					optionalCovers = updateHomeQuoteRequest.getOptionalCovers();
					for(OptionalCovers c : optionalCovers)
					{
						//String[] riskCodes= c.getRiskMappingCode().split("-");
						/* For CoverIncluded */
						if(c.getCoverIncluded()==null || Utils.isEmpty(c.getCoverIncluded()))
						{
							ValidationError error5 = ErrorMapping.errorMapping("CoverIncluded", "WS_105");
							errors.add(error5);
						}
						else
						{
							/* For CoverDesc */
							if((c.getCoverDesc()==null || Utils.isEmpty(c.getCoverDesc())) && c.getCoverIncluded()==true)
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
							
							/* For CoverMappingCode */
							if((c.getCoverMappingCode()==null || Utils.isEmpty(c.getCoverMappingCode())) && c.getCoverIncluded()==true)
							{
								ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_COVERMAPPINGCODE, "WS_106");
								errors.add(error5);
							}
							else
							{
								if(c.getCoverMappingCode().length()>WsAppConstants.maxHomeCoverMappingCodeLength)
								{
									ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_COVERMAPPINGCODE, "WS_107");
									errors.add(error5);
								}
								if(c.getCoverMappingCode().startsWith("3") && c.getCoverIncluded()==true)
								{
									containsStaffDetails=true;
								}
								if(c.getCoverMappingCode().startsWith("4") && c.getCoverIncluded()==true )
								{
									if(ownerShipStatus==WsAppConstants.OwnYourHome)
									{
										ValidationError error5 = ErrorMapping.errorMapping("AdditionalTenantsLiabilityCover", "WS_113");
										errors.add(error5);
									}
								}
							}
					if(c.getCoverMappingCode().startsWith("4")){
						int count = 0;
						int flag = 0;
							for (TLLimit limits : c.getTllLimit()) {
								if(c.getCoverMappingCode().startsWith("4") && c.getCoverIncluded()==true && limits.getSelected().equals(false)) {
									flag = flag + 1;
								}else if(c.getCoverMappingCode().startsWith("4") && c.getCoverIncluded()==false && limits.getSelected().equals(true)) {
									ValidationError error5 = ErrorMapping.errorMapping("AdditionalTenantsLiabilityCover", "WS_310"); // working
									errors.add(error5);
								}
								if(c.getCoverMappingCode().startsWith("4") && c.getCoverIncluded()==true && limits.getSelected()==true) {
									count = count + 1;
								}
							}
							System.out.println(count);
							if(count > 1) {
								ValidationError error5 = ErrorMapping.errorMapping("TLLLimits", "WS_311"); // working
								errors.add(error5);
							}
							if(flag > 2) {
								ValidationError error5 = ErrorMapping.errorMapping("AdditionalTenantsLiabilityCover", "WS_309"); 
								errors.add(error5);
							}
					}
							/* For Cover Risk Details */
							if((c.getRiskDetails()==null || Utils.isEmpty(c.getRiskDetails())) && c.getCoverIncluded()==true)
							{
								ValidationError error5 = ErrorMapping.errorMapping("RiskDetails", "WS_050");
								errors.add(error5);
							}
							else
							{/*
								 For Risk Id
								if(c.getRiskDetails().getRskId()!=null || !Utils.isEmpty(c.getRiskDetails().getRskId()))
								{
									if(ValidationUtil.countDigits(c.getRiskDetails().getRskId())>6)
									{
										ValidationError error5 = ErrorMapping.errorMapping("RiskId", "WS_108");
										errors.add(error5);
									}
								}
								 For Basic Risk Id
								if(c.getRiskDetails().getBasicRskId()!=null || !Utils.isEmpty(c.getRiskDetails().getBasicRskId()))
								{
									if(ValidationUtil.countDigits(c.getRiskDetails().getBasicRskId())>6)
									{
										ValidationError error5 = ErrorMapping.errorMapping("BasicRiskId", "WS_109");
										errors.add(error5);
									}
								}*/
								/* For  Basic Risk Code*/
								/*if(c.getRiskDetails().getBasicRskCode()==null || Utils.isEmpty(c.getRiskDetails().getBasicRskCode()))
								{
									//ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_BASICRISKCODE, "WS_028");
									//errors.add(error5);
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
								}*/
								/* For Risk Code*/
								/*if(riskCodes[0]==null || Utils.isEmpty(Integer.parseInt(riskCodes[0])))
								{
									ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_RISKCODE1, "WS_030");
									errors.add(error5);
								}
								else
								{
									if(Integer.parseInt(riskCodes[0])<0)
									{
										ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_RISKCODE1, com.Constant.CONST_WS_254);
										errors.add(error5);
									}
									else if(ValidationUtil.countDigits(Integer.parseInt(riskCodes[0]))>WsAppConstants.maxRiskCodeLength)
									{
										ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_RISKCODE1, "WS_031");
										errors.add(error5);
									}
								}*/
								/* For Risk Type*/
								/*if(riskCodes[1]==null || Utils.isEmpty(Integer.parseInt(riskCodes[1])))
								{
									ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_RISKTYPE1, "WS_032");
									errors.add(error5);
								}
								else
								{
									if(Integer.parseInt(riskCodes[1])<0)
									{
										ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_RISKTYPE1, com.Constant.CONST_WS_254);
										errors.add(error5);
									}
									else if(ValidationUtil.countDigits(Integer.parseInt(riskCodes[1]))>WsAppConstants.maxRiskTypeLength)
									{
										ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_RISKTYPE1, "WS_033");
										errors.add(error5);
									}
								}*/
								/* For Risk Category*/
								/*if(riskCodes[2]==null || Utils.isEmpty(Integer.parseInt(riskCodes[2])))
								{
									ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_RISKCATEGORY, "WS_034");
									errors.add(error5);
								}
								else
								{
									if(Integer.parseInt(riskCodes[2])<0)
									{
										ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_RISKCATEGORY, com.Constant.CONST_WS_254);
										errors.add(error5);
									}
									if(ValidationUtil.countDigits(Integer.parseInt(riskCodes[2]))>WsAppConstants.maxRiskCategoryLength)
									{
										ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_RISKCATEGORY, "WS_035");
										errors.add(error5);
									}
									if(Integer.parseInt(riskCodes[2])==2 && Integer.parseInt(riskCodes[1])==31 
											&& c.getCoverIncluded()==true && containContentItems==false)
									{
										ValidationError error5 = ErrorMapping.errorMapping("ContentItems", "WS_146");
										errors.add(error5);
									}
									else if (Integer.parseInt(riskCodes[2])==2 && Integer.parseInt(riskCodes[1])==31 
											&& c.getCoverIncluded()==true && containContentItems==true)
									{
										if(c.getCoverageLimit()==null || Utils.isEmpty(c.getCoverageLimit()))
										{
											ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_CONTENTITEMSCOVERAGELIMIT, "WS_253");
											errors.add(error5);
										}
										else
										{
											if(c.getCoverageLimit().compareTo(minContentItemsCoveraggeLimit)==-1)
											{
												ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_CONTENTITEMSCOVERAGELIMIT, "WS_147");
												errors.add(error5);
											}
											else{
												sumContentItemsCoveraggeLimit=sumContentItemsCoveraggeLimit.add(c.getCoverageLimit());
											}
											
										}
										
									}
									else if (Integer.parseInt(riskCodes[2])==2 && Integer.parseInt(riskCodes[1])==32 
											&& c.getCoverIncluded()==true && containPpItems==false)
									{
										ValidationError error5 = ErrorMapping.errorMapping("PersonalPossessionItems", "WS_251");
										errors.add(error5);
									}
									else if (Integer.parseInt(riskCodes[2])==2 && Integer.parseInt(riskCodes[1])==32 
											&& c.getCoverIncluded()==true && containPpItems==true)
									{
										if(c.getCoverageLimit()==null || Utils.isEmpty(c.getCoverageLimit()))
										{
											ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_PERSONALPOSSESSIONITEMSCOVERAGELIMIT, "WS_253");
											errors.add(error5);
										}
										else
										{
											if(c.getCoverageLimit().compareTo(minPpItemsCoveraggeLimit)==-1)
											{
												ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_PERSONALPOSSESSIONITEMSCOVERAGELIMIT, "WS_148");
												errors.add(error5);
											}
											else{
												sumPpItemsCoveraggeLimit=sumPpItemsCoveraggeLimit.add(c.getCoverageLimit());
											}
										}
										
									}
								}*/
							}
						}
						/* For StaffDetails */
						if(containsStaffDetails==true)
						{
							if(c.getStaffDetails()==null || Utils.isEmpty(c.getStaffDetails()))
							{
								ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_STAFFDETAILS, "WS_022");
								errors.add(error5);
							}
							else
							{
								List<Staff> staff = new ArrayList<Staff>();
								staff = c.getStaffDetails().getStaff();
								for(Staff s : staff )
								{
									if(Utils.isEmpty(s.getStaffName())|| Utils.isEmpty(s.getStaffDob())|| s.getStaffName()==null || s.getStaffDob() == null)
									{
										ValidationError error4 = ErrorMapping.errorMapping(com.Constant.CONST_STAFFDETAILS, "WS_112");
										errors.add(error4);
										break;
									}
								}
								containsStaffDetails=false;
							}
						}
						/*else
						{
							if(c.getStaffDetails()!=null && !Utils.isEmpty(c.getStaffDetails()))
							{
								ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_STAFFDETAILS, "WS_111");
								errors.add(error5);
							}
						}*/
					}
				/*if(contentCoveraggeLimit.compareTo(sumContentItemsCoveraggeLimit)==-1)
				{
					ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_CONTENTITEMSCOVERAGELIMIT, "WS_149");
					errors.add(error5);
				}
				if(ppCoveraggeLimit.compareTo(sumPpItemsCoveraggeLimit)==-1)
				{
					ValidationError error5 = ErrorMapping.errorMapping(com.Constant.CONST_PERSONALPOSSESSIONITEMSCOVERAGELIMIT, "WS_150");
					errors.add(error5);
				}*/
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
	
	public Boolean finapUpdate(UpdateHomeQuoteRequest updateHomeQuoteRequest) {
		if(updateHomeQuoteRequest.getTransactionDetails().getFinalUpdate()!=null && updateHomeQuoteRequest.getTransactionDetails().getFinalUpdate().equals(true)) {
			return true;
		}
		return false;
	}
}
