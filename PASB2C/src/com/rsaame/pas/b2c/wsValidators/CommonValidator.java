
package com.rsaame.pas.b2c.wsValidators;

import java.util.ArrayList;
import java.util.List;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.b2c.cmn.constants.AppConstants;
import com.rsaame.pas.b2c.cmn.utils.ValidationUtil;
import com.rsaame.pas.b2c.ws.vo.ErrorMapping;
import com.rsaame.pas.b2c.ws.vo.TransactionDetails;
import com.rsaame.pas.b2c.ws.vo.WsAppConstants;
import com.rsaame.pas.b2c.wsException.ValidationError;

public class CommonValidator {
	
	/* For MobileNo */
	public static ValidationError isValidPhoneNo(String mobileNo)
	{
		ValidationError error = null;
		if (Utils.isEmpty(mobileNo)&& mobileNo == null) 
		{
			 error = ErrorMapping.errorMapping("MobileNo", "WS_003");
		}
		else
		{
			if((!ValidationUtil.isNumeric(mobileNo)
					|| mobileNo.length() < AppConstants.B2C_ALLOWED_MIN_MOB_NUM_LENGTH || mobileNo.length()>16)){
			
				 error = ErrorMapping.errorMapping("MobileNo", "WS_004");
			}
		}
		return error;
	}
	
	/* For EmailID */
	public static ValidationError isValidEmail(String emailId)
	{
		ValidationError error = null;
		if (Utils.isEmpty(emailId) && emailId == null) 
		{
			 error = ErrorMapping.errorMapping("EmailId", "WS_001");
		}
		else
		{
			if(!ValidationUtil.isValidEmail(emailId)
					|| emailId.length() > AppConstants.B2C_ALLOWED_MAX_EMAILID_LENGTH){
			
				 error = ErrorMapping.errorMapping("EmailId", "WS_002");
			}
		}
		return error;
	}
	
	/* For Customer Nationality */
	public static ValidationError isValidNationality(Integer nationality)
	{
		ValidationError error = null;
		if(nationality == null || Utils.isEmpty(nationality))
		{
			error = ErrorMapping.errorMapping(com.Constant.CONST_NATIONALITY, "WS_057");
		}
		else
		{
			if(nationality<0)
			{
				error=ErrorMapping.errorMapping(com.Constant.CONST_NATIONALITY, com.Constant.CONST_WS_254);
			}
			else if(ValidationUtil.countDigits(nationality)>WsAppConstants.maxNationalityLength)
			{
				error = ErrorMapping.errorMapping(com.Constant.CONST_NATIONALITY, "WS_093");
			}
		}
		return error;
	}
	
	/* For First Name */
	public static ValidationError isValidFirstName(String firstName)
	{
		ValidationError error = null;
		if(firstName.length()>WsAppConstants.maxFirstNameLength || !ValidationUtil.isAlphaWithSpace(firstName))
		{
			error = ErrorMapping.errorMapping("FirstName", "WS_063");
		}
		return error;
	}
	/* For Last Name */
	public static ValidationError isValidLastName(String lastName)
	{
		ValidationError error = null;
		if(lastName.length()>WsAppConstants.maxlastNameLength || !ValidationUtil.isAlphaWithSpace(lastName))
		{
			error = ErrorMapping.errorMapping("LastName", "WS_065");
		}
		return error;
	}
	/* For Post Box */
	public static ValidationError isValidPostBox(String postBox)
	{
		ValidationError error = null;
		if(postBox.length()>WsAppConstants.maxPostBoxLength || (!ValidationUtil.isAlphaNumericWithSpace(postBox) && postBox.length()!=0 ) )  //Bugzilla defect fix
		{
			error = ErrorMapping.errorMapping("PostBoxNo", "WS_067");
		}
		return error;
	}
	
	/* For City */
	public static ValidationError isValidCity(int city)
	{
		ValidationError error = null;
		if(city<0)
		{
			error = ErrorMapping.errorMapping("City", com.Constant.CONST_WS_254);
		}
		else if(ValidationUtil.countDigits(city)>WsAppConstants.maxCityLength)
		{
			error = ErrorMapping.errorMapping("City", "WS_069");
		}
		return error;
	}
	
	/* For NationalID */
	public static ValidationError isValidNationalID(String nationalId)
	{
		ValidationError error = null;
		if(nationalId.length()>WsAppConstants.maxNationalIdLength)
		{
			error = ErrorMapping.errorMapping(com.Constant.CONST_NATIONALID, "WS_095");
		}
		return error;
	}
	
	/* For VatRegNo */
	public static ValidationError isValidVatRegnNo(String vatRegNo)
	{
		ValidationError error = null;
		if(vatRegNo.length()>WsAppConstants.maxVatRegnNoLength)
		{
			error = ErrorMapping.errorMapping(com.Constant.CONST_NATIONALID, "WS_097");
		}
		return error;
	}
	
	/* For RewardProgrammeType */
	public static ValidationError isValidRewardProgrammeType(int rewardProgrammeType)
	{
		ValidationError error = null;
		if(rewardProgrammeType<0)
		{
			error = ErrorMapping.errorMapping("RewardProgrammeType", com.Constant.CONST_WS_254);
		}
		else if(ValidationUtil.countDigits(rewardProgrammeType)>WsAppConstants.maxRewardProgrammeTypeLength)
		{
			error = ErrorMapping.errorMapping("RewardProgrammeType", "WS_099");
		}
		return error;
	}
	
	/* For RewardCardNumber */
	public static ValidationError isValidRewardCardNumber(String rewardCardNumber)
	{
		ValidationError error = null;
		if(rewardCardNumber.length()>WsAppConstants.maxRewardCardNumberLength)
		{
			error = ErrorMapping.errorMapping(com.Constant.CONST_NATIONALID, "WS_101");
		}
		return error;
	}
	
	/* For Transaction Details */
	public static List<ValidationError> validateTransactionDetails(TransactionDetails transactionDetails)
	{
		List<ValidationError> errors= new ArrayList<ValidationError>();
		
		if(transactionDetails!=null && !Utils.isEmpty(transactionDetails))
		{
			
			/* For DistChannel */
			if(!Utils.isEmpty(transactionDetails.getDistChannel()) && !(transactionDetails.getDistChannel()==null))
			{
				if(transactionDetails.getDistChannel()<0)
				{
					ValidationError error = ErrorMapping.errorMapping("DistChannel", com.Constant.CONST_WS_254);
					errors.add(error);
				}
				else if(ValidationUtil.countDigits(transactionDetails.getDistChannel())>WsAppConstants.maxDistChannelLength)
				{
					ValidationError error = ErrorMapping.errorMapping("DistChannel", "WS_014");
					errors.add(error);
				}
			}
			else
			{
				ValidationError error = ErrorMapping.errorMapping("DchCode", "WS_013");
				errors.add(error);
			}
			
			/* For Transaction Tariff Code */
			if(!Utils.isEmpty(transactionDetails.getTariffCode()) && !(transactionDetails.getTariffCode()==null))
			{
				if(transactionDetails.getTariffCode()<0)
				{
					ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_TARIFFCODE, com.Constant.CONST_WS_254);
					errors.add(error);
				}
				else if(ValidationUtil.countDigits(transactionDetails.getTariffCode())>WsAppConstants.maxTariffCodeLength)
				{
					ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_TARIFFCODE, "WS_010");
					errors.add(error);
				}
			}
			else
			{
				ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_TARIFFCODE, "WS_009");
				errors.add(error);
			}
			
			/* For Transaction Scheme Code */
			if(!Utils.isEmpty(transactionDetails.getSchemeCode()) && !(transactionDetails.getSchemeCode()==null))
			{
				if(transactionDetails.getSchemeCode()<0)
				{
					ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_SCHEMECODE, com.Constant.CONST_WS_254);
					errors.add(error);
				}
				else if(ValidationUtil.countDigits(transactionDetails.getSchemeCode())>WsAppConstants.maxSchemeCodeLength)
				{
					ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_SCHEMECODE, "WS_008");
					errors.add(error);
				}
			}
			else
			{
				ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_SCHEMECODE, "WS_007");
				errors.add(error);
			}
			
			
			
			/* For directSubAgent */
			if(transactionDetails.getDirectSubAgent()!=null && !Utils.isEmpty(transactionDetails.getDirectSubAgent()))
			{
				if(transactionDetails.getDirectSubAgent()<0)
				{
					ValidationError error = ErrorMapping.errorMapping("DirectSubAgent", com.Constant.CONST_WS_254);
					errors.add(error);
				}
			}
			/* For businessSource */
			if(transactionDetails.getBusinessSource()!=null && !Utils.isEmpty(transactionDetails.getBusinessSource()))
			{
				if(transactionDetails.getBusinessSource()<0)
				{
					ValidationError error = ErrorMapping.errorMapping("BusinessSource", com.Constant.CONST_WS_254);
					errors.add(error);
				}
			}
			/* For promocode */
			if(!Utils.isEmpty(transactionDetails.getPromocode())&& transactionDetails.getPromocode()!=null)
			{
				if(transactionDetails.getPromocode().length()>WsAppConstants.maxPromoCodeLength)
				{
					ValidationError error = ErrorMapping.errorMapping("PromoCode", "WS_021");
					errors.add(error);
				}
			}
			/* For partnerTrnReferenceNumber */
			if(!Utils.isEmpty(transactionDetails.getPartnerTrnReferenceNumber())&& transactionDetails.getPartnerTrnReferenceNumber()!=null)
			{
				if(transactionDetails.getPartnerTrnReferenceNumber().length()>20)
				{
					ValidationError error = ErrorMapping.errorMapping("PartnerTrnReferenceNumber", "WS_102");
					errors.add(error);
				}
			}
		}
		else
		{
			ValidationError error = ErrorMapping.errorMapping("TransactionalDetails", "WS_020");
			errors.add(error);
		}
		
		return errors;
	}
	
}
