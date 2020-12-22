package com.rsaame.pas.b2c.wsValidators;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.b2c.cmn.utils.ValidationUtil;
import com.rsaame.pas.b2c.ws.vo.CreatePolicyRequest;
import com.rsaame.pas.b2c.ws.vo.ErrorMapping;
import com.rsaame.pas.b2c.ws.vo.WsAppConstants;
import com.rsaame.pas.b2c.wsException.ValidationError;
import com.rsaame.pas.b2c.wsException.ValidationException;

public class CreatePolicyRequestValidator {
	ValidationException validationException = new ValidationException();
	List<ValidationError> errors = new ArrayList<ValidationError>();
	public ValidationException validate(Object arg0) {
		CreatePolicyRequest createPolicyRequest = (CreatePolicyRequest)arg0;
		boolean isTidMandatory=false;
		if(createPolicyRequest!=null && !Utils.isEmpty(createPolicyRequest))
		{
			/* for QuotationNo */
			if(createPolicyRequest.getQuotationNo()==null || Utils.isEmpty(createPolicyRequest.getQuotationNo()))
			{
				ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_QUOTATIONNO, "WS_071");
				errors.add(error);
			}
			else
			{
				if(createPolicyRequest.getQuotationNo()<0)
				{
					ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_QUOTATIONNO, com.Constant.CONST_WS_254);
					errors.add(error);
				}
				else if((createPolicyRequest.getQuotationNo().toString().length())>WsAppConstants.maxQuotationNoLength)
				{
					ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_QUOTATIONNO, "WS_072");
					errors.add(error);
				}
			}
			
			/* for PolicyId */
			if(createPolicyRequest.getPolicyId()==null || Utils.isEmpty(createPolicyRequest.getPolicyId()))
			{
				ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_POLICYID, "WS_289");
				errors.add(error);
			}
			else
			{
				if(createPolicyRequest.getPolicyId()<0)
				{
					ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_POLICYID, com.Constant.CONST_WS_254);
					errors.add(error);
				}
				else if((createPolicyRequest.getPolicyId().toString().length())>WsAppConstants.maxPolicyIdLength)
				{
					ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_POLICYID, "WS_290");
					errors.add(error);
				}
			}
			
			/* for EndorsementNo */
			if(createPolicyRequest.getEndtNo()==null || Utils.isEmpty(createPolicyRequest.getEndtNo()))
			{
				ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_ENDTNO, "WS_075");
				errors.add(error);
			}
			else
			{
				if(createPolicyRequest.getEndtNo()<0)
				{
					ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_ENDTNO, com.Constant.CONST_WS_254);
					errors.add(error);
				}
				else if(ValidationUtil.countDigits(createPolicyRequest.getEndtNo().toString().length())>WsAppConstants.maxEndtNoLength)
				{
					ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_ENDTNO, "WS_076");
					errors.add(error);
				}
			}
			
			/* for EndorsementId */
			if(createPolicyRequest.getEndtId()==null || Utils.isEmpty(createPolicyRequest.getEndtId()))
			{
				ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_ENDTID, "WS_073");
				errors.add(error);
			}
			else
			{
				if(createPolicyRequest.getEndtId()<0)
				{
					ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_ENDTID, com.Constant.CONST_WS_254);
					errors.add(error);
				}
				else if(ValidationUtil.countDigits(createPolicyRequest.getEndtId().toString().length())>WsAppConstants.maxEndtIdLength)
				{
					ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_ENDTID, "WS_074");
					errors.add(error);
				}
			}
			
			/* for PremiumPayable */
			if(createPolicyRequest.getPremiumPayable()==null || Utils.isEmpty(createPolicyRequest.getPremiumPayable()))
			{
				ValidationError error = ErrorMapping.errorMapping("PremiumPayable", "WS_292");
				errors.add(error);
			}
			else
			{
				if(ValidationUtil.integerDigits(createPolicyRequest.getPremiumPayable())>WsAppConstants.maxPremiumPayableLength)
				{
					ValidationError error = ErrorMapping.errorMapping("PremiumPayable", "WS_293");
					errors.add(error);
				}
			}
			/* for IsPaymentProcessed */
			if(createPolicyRequest.getIsPaymentProcessed()==null || Utils.isEmpty(createPolicyRequest.getIsPaymentProcessed()))
			{
				ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_ISPAYMENTPROCESSED, "WS_264");
				errors.add(error);
			}
			else
			{
				if(createPolicyRequest.getIsPaymentProcessed().length()>WsAppConstants.maxIsPaymentProcessedLength)
				{
					ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_ISPAYMENTPROCESSED, "WS_265");
					errors.add(error);
				}
				if(!createPolicyRequest.getIsPaymentProcessed().equalsIgnoreCase("success"))
				{
					ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_ISPAYMENTPROCESSED, "WS_294");
					errors.add(error);
				}
			}
			
			/* for CustName */
			if(createPolicyRequest.getCustName()!=null && !Utils.isEmpty(createPolicyRequest.getCustName()))
			{
				if(createPolicyRequest.getCustName().length()>WsAppConstants.maxCustomerNameLength || !ValidationUtil.isAlphaWithSpace(createPolicyRequest.getCustName()))
				{
					ValidationError error = ErrorMapping.errorMapping("CustName", "WS_291");
					errors.add(error);
				}
			}
			/* for CardNo */
			if(createPolicyRequest.getCardNo()!=null && !Utils.isEmpty(createPolicyRequest.getCardNo()))
			{
				if(createPolicyRequest.getCardNo()<0)
				{
					ValidationError error = ErrorMapping.errorMapping("CardNo", com.Constant.CONST_WS_254);
					errors.add(error);
				}
				else if(ValidationUtil.countDigits(createPolicyRequest.getCardNo())!=WsAppConstants.CardNoLength)
				{
					ValidationError error = ErrorMapping.errorMapping("CardNo", "WS_267");
					errors.add(error);
				}
			}
			
			/* for CardType */
			if(createPolicyRequest.getCardType()!=null && !Utils.isEmpty(createPolicyRequest.getCardType()))
			{
				if(createPolicyRequest.getCardType().length()>WsAppConstants.maxCardTypeLength || !ValidationUtil.isAlphaWithSpace(createPolicyRequest.getCardType()))
				{
					ValidationError error = ErrorMapping.errorMapping("CardType", "WS_268");
					errors.add(error);
				}
				if(createPolicyRequest.getCardType().replace(" ", "").equalsIgnoreCase("creditcard"))
				{
					isTidMandatory=true;
				}
			}
			
			/* for ExpiryDate */
			if(createPolicyRequest.getCardExpiryDate()!=null && !Utils.isEmpty(createPolicyRequest.getCardExpiryDate()))
			{
				
				 if(createPolicyRequest.getCardExpiryDate().length()!=WsAppConstants.CardExpiryDateLength)
				{
					ValidationError error = ErrorMapping.errorMapping("ExpiryDate", "WS_269");
					errors.add(error);
				}
			}
			
			/* for PaymtMode */
			if(createPolicyRequest.getPaymtMode()!=null && !Utils.isEmpty(createPolicyRequest.getPaymtMode()))
			{
				
				 if(createPolicyRequest.getPaymtMode().length()>WsAppConstants.maxPaymentModeLength)
				{
					ValidationError error = ErrorMapping.errorMapping("PaymtMode", "WS_270");
					errors.add(error);
				}
			}
			
			/* for CardTypeCode */
			if(createPolicyRequest.getCardTypeCode()!=null && !Utils.isEmpty(createPolicyRequest.getCardTypeCode()))
			{
				if(createPolicyRequest.getCardTypeCode()<0)
				{
					ValidationError error = ErrorMapping.errorMapping("CardTypeCode", com.Constant.CONST_WS_254);
					errors.add(error);
				}
				 if(ValidationUtil.countDigits(createPolicyRequest.getCardTypeCode())>WsAppConstants.maxCardTypeCodeLength)
				{
					ValidationError error = ErrorMapping.errorMapping("CardTypeCode", "WS_271");
					errors.add(error);
				}
			}
			
			/* for PaymtTransactionDate */
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            if (!Utils.isEmpty(createPolicyRequest.getPaymtTransactionDate()) && createPolicyRequest.getPaymtTransactionDate()!=null) {
                  String date = dateFormat.format(createPolicyRequest.getPaymtTransactionDate()).toString();
                  
                  if(!ValidationUtil.validateJavaDate(date,"yyyy-MM-dd"))
                  {
                        ValidationError error = ErrorMapping.errorMapping("PaymtTransactionDate", "WS_272");
                        errors.add(error);
                  }
            }
            
            /* for LocationCode */
			if(createPolicyRequest.getLocationCode()==null || Utils.isEmpty(createPolicyRequest.getLocationCode()))
			{
				ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_LOCATIONCODE, "WS_274");
				errors.add(error);
			}
			else
			{
				if(createPolicyRequest.getLocationCode()<0)
				{
					ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_LOCATIONCODE, com.Constant.CONST_WS_254);
					errors.add(error);
				}
				 if(ValidationUtil.countDigits(createPolicyRequest.getLocationCode())>WsAppConstants.maxLocationCodeLength)
				{
					ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_LOCATIONCODE, "WS_273");
					errors.add(error);
				}
			}
			
			/* for UploadFilePath */
			if(createPolicyRequest.getUploadFilePath()!=null && !Utils.isEmpty(createPolicyRequest.getUploadFilePath()))
			{
				if(createPolicyRequest.getUploadFilePath().length()>WsAppConstants.maxUploadFilePathLength)
				{
					ValidationError error = ErrorMapping.errorMapping("UploadFilePath", "WS_275");
					errors.add(error);
				}
			}
			
			/* for UploadFileName */
			if(createPolicyRequest.getUploadFileName()!=null && !Utils.isEmpty(createPolicyRequest.getUploadFileName()))
			{
				if(createPolicyRequest.getUploadFileName().length()>WsAppConstants.maxUploadFileNameLength)
				{
					ValidationError error = ErrorMapping.errorMapping("UploadFileName", "WS_276");
					errors.add(error);
				}
			}
			
			/* for ApplVer */
			if(createPolicyRequest.getApplVer()!=null && !Utils.isEmpty(createPolicyRequest.getApplVer()))
			{
				if(createPolicyRequest.getApplVer().length()>WsAppConstants.maxApplVerLength)
				{
					ValidationError error = ErrorMapping.errorMapping("ApplVer", "WS_277");
					errors.add(error);
				}
			}
			
			/* for TimeDuration */
			if(createPolicyRequest.getTimeDuration()!=null && !Utils.isEmpty(createPolicyRequest.getTimeDuration()))
			{
				if(createPolicyRequest.getTimeDuration().length()>WsAppConstants.maxTimeDurationLength)
				{
					ValidationError error = ErrorMapping.errorMapping("TimeDuration", "WS_278");
					errors.add(error);
				}
			}
			
			/* for MID */
			if(createPolicyRequest.getmId()!=null && !Utils.isEmpty(createPolicyRequest.getmId()))
			{
				if(createPolicyRequest.getmId().length()>WsAppConstants.maxMIDLength)
				{
					ValidationError error = ErrorMapping.errorMapping("MID", "WS_279");
					errors.add(error);
				}
			}
			
			/* for TID */
			if(createPolicyRequest.gettId()!=null && !Utils.isEmpty(createPolicyRequest.gettId()))
			{
				if(createPolicyRequest.gettId().length()>WsAppConstants.maxTIDLength)
				{
					ValidationError error = ErrorMapping.errorMapping("TID", "WS_281");
					errors.add(error);
				}
			}
			else
			{
				if(isTidMandatory==true)
				{
					ValidationError error = ErrorMapping.errorMapping("TID", "WS_280");
					errors.add(error);
				}
			}
			
			/* for BatchNo */
			if(createPolicyRequest.getBatchNo()!=null && !Utils.isEmpty(createPolicyRequest.getBatchNo()))
			{
				if(createPolicyRequest.getBatchNo().length()>WsAppConstants.maxBatchNoLength)
				{
					ValidationError error = ErrorMapping.errorMapping("BatchNo", "WS_282");
					errors.add(error);
				}
			}
			
			/* for InvoiceNo */
			if(createPolicyRequest.getInvoiceNo()!=null && !Utils.isEmpty(createPolicyRequest.getInvoiceNo()))
			{
				if(createPolicyRequest.getInvoiceNo().length()>WsAppConstants.maxInvoiceNoLength)
				{
					ValidationError error = ErrorMapping.errorMapping("InvoiceNo", "WS_283");
					errors.add(error);
				}
			}
			
			/* for RRN */
			if(createPolicyRequest.getRrn()!=null && !Utils.isEmpty(createPolicyRequest.getRrn()))
			{
				if(createPolicyRequest.getRrn().length()>WsAppConstants.maxRRNLength)
				{
					ValidationError error = ErrorMapping.errorMapping("RRN", "WS_284");
					errors.add(error);
				}
			}
			
			/* for MaskCardNumber */
			if(createPolicyRequest.getMaskCardNumber()!=null && !Utils.isEmpty(createPolicyRequest.getMaskCardNumber()))
			{
				if(createPolicyRequest.getMaskCardNumber().length()>WsAppConstants.maxMaskCardNumberLength)
				{
					ValidationError error = ErrorMapping.errorMapping("MaskCardNumber", "WS_285");
					errors.add(error);
				}
			}
			
			/* for CardHolderName */
			if(createPolicyRequest.getCardHolderName()!=null && !Utils.isEmpty(createPolicyRequest.getCardHolderName()))
			{
				if(createPolicyRequest.getCardHolderName().length()>WsAppConstants.maxCardHolderNameLength)
				{
					ValidationError error = ErrorMapping.errorMapping("CardHolderName", "WS_286");
					errors.add(error);
				}
			}
			
			/* for Amount */
			if(createPolicyRequest.getAmount()!=null && !Utils.isEmpty(createPolicyRequest.getAmount()))
			{
				if(createPolicyRequest.getAmount().length()>WsAppConstants.maxAmountLength)
				{
					ValidationError error = ErrorMapping.errorMapping("Amount", "WS_287");
					errors.add(error);
				}
			}
			
			/* for AuthCode */
			if(createPolicyRequest.getAuthCode()!=null && !Utils.isEmpty(createPolicyRequest.getAuthCode()))
			{
				if(createPolicyRequest.getAuthCode().length()>WsAppConstants.maxAuthCodeLength)
				{
					ValidationError error = ErrorMapping.errorMapping("AuthCode", "WS_288");
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
