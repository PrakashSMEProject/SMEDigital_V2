package com.rsaame.pas.b2c.wsValidators;

import java.util.ArrayList;
import java.util.List;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.b2c.cmn.constants.AppConstants;
import com.rsaame.pas.b2c.cmn.utils.ValidationUtil;
import com.rsaame.pas.b2c.ws.vo.ErrorMapping;
import com.rsaame.pas.b2c.ws.vo.RetrieveQuoteByQuoteId;
import com.rsaame.pas.b2c.ws.vo.WsAppConstants;
import com.rsaame.pas.b2c.wsException.ValidationError;
import com.rsaame.pas.b2c.wsException.ValidationException;

public class RetriveQuoteByQuoteIdValidator {

	ValidationException validationException = new ValidationException();
	List<ValidationError> errors = new ArrayList<ValidationError>();
	public ValidationException validate(Object arg0) {
		RetrieveQuoteByQuoteId retrieveQuoteByQuoteId = (RetrieveQuoteByQuoteId)arg0;
		if(retrieveQuoteByQuoteId!=null && !Utils.isEmpty(retrieveQuoteByQuoteId))
		{
			/* for QuotationNo */
			if(retrieveQuoteByQuoteId.getQuotationNo()==null || Utils.isEmpty(retrieveQuoteByQuoteId.getQuotationNo()))
			{
				ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_QUOTATIONNO, "WS_071");
				errors.add(error);
			}
			else
			{
				if(retrieveQuoteByQuoteId.getQuotationNo()<0)
				{
					ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_QUOTATIONNO, com.Constant.CONST_WS_254);
					errors.add(error);
				}
				else if(ValidationUtil.countDigits(retrieveQuoteByQuoteId.getQuotationNo())>WsAppConstants.maxQuotationNoLength)
				{
					ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_QUOTATIONNO, "WS_072");
					errors.add(error);
				}
			}
			
			/* for EndorsementNo */
			if(retrieveQuoteByQuoteId.getEndtNo()!=null && !Utils.isEmpty(retrieveQuoteByQuoteId.getEndtNo()))
			{
				if(retrieveQuoteByQuoteId.getEndtNo()<0)
				{
					ValidationError error = ErrorMapping.errorMapping("EndtNo", com.Constant.CONST_WS_254);
					errors.add(error);
				}
				else if(ValidationUtil.countDigits(retrieveQuoteByQuoteId.getEndtNo().toString().length())>WsAppConstants.maxEndtNoLength)
				{
					ValidationError error = ErrorMapping.errorMapping("EndtNo", "WS_076");
					errors.add(error);
				}
			}
			
			/* for EndorsementId */
			if(retrieveQuoteByQuoteId.getEndtId()!=null && !Utils.isEmpty(retrieveQuoteByQuoteId.getEndtId()))
			{
				if(retrieveQuoteByQuoteId.getEndtId()<0)
				{
					ValidationError error = ErrorMapping.errorMapping("EndtId", com.Constant.CONST_WS_254);
					errors.add(error);
				}
				else if(ValidationUtil.countDigits(retrieveQuoteByQuoteId.getEndtId().toString().length())>WsAppConstants.maxEndtIdLength)
				{
					ValidationError error = ErrorMapping.errorMapping("EndtId", "WS_074");
					errors.add(error);
				}
			}
			
			/* For Email Id */
			if(retrieveQuoteByQuoteId.getEmailId()!=null && !Utils.isEmpty(retrieveQuoteByQuoteId.getEmailId()))
			{
				if(!ValidationUtil.isValidEmail(retrieveQuoteByQuoteId.getEmailId())
						|| retrieveQuoteByQuoteId.getEmailId().length() > AppConstants.B2C_ALLOWED_MAX_EMAILID_LENGTH){
				
					 ValidationError error = ErrorMapping.errorMapping("EmailId", "WS_002");
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
