package com.rsaame.pas.b2b.ws.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.b2b.ws.validators.SBSWSValidators;
import com.rsaame.pas.b2b.ws.vo.CreateSBSQuoteResponse;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.app.LookUpListVO;
import com.rsaame.pas.vo.app.LookUpVO;
import com.rsaame.pas.vo.bus.PolicyVO;

public class WSBusinessValidatorUtils {

	public enum SBSErrorCodes {
		ERROR, REFERRAL, WARNING;
	}

	public static boolean checkDateFormat(String strDate, String dateFormat) {
		
		boolean result = true;
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
		simpleDateFormat.setLenient(false);
		Date dateVal = null;
		try {
			System.out.println("This is calling");
			dateVal = simpleDateFormat.parse(strDate);
		}
		/* Date format is invalid */
		catch (ParseException e) {
			result= false;
			//e.printStackTrace();
			return result;
		}
		 if (simpleDateFormat.format(dateVal).equals(strDate)) {
			 result= true;
	        }
		 else {
			 result=false;
			 }
		/* Return true if date format is valid */
		return result;
	}

	public static boolean checkCreationDate(String strDate, String dateFormat) {
		// boolean checkFormat = checkDateFormat(strDate, dateFormat);
		LocalDate localDate = LocalDate.now();
		/* if(checkFormat) { */
		if (localDate.toString().equals(strDate)) {
			return true;
			// }
		}
		return false;
	}
	
	public static boolean checkEffectiveDate(String strDate, String dateFormat) {
			 try {
				 LocalDate localDate = LocalDate.now();
			 if (localDate.toString().equals(strDate) || localDate.isBefore(LocalDate.parse(strDate))) {
					System.out.println("Same Date");
					return true;
			 }		
			 }catch (DateTimeParseException e) {
				 return false;
				// TODO: handle exception
			}
		return false;
	}
	public static boolean checkEffectiveDateValid(String strDate,String format) {
		 try {
			 LocalDate localDate = LocalDate.now();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				//convert String to LocalDate
				LocalDate localDate1 = LocalDate.parse(strDate, formatter);
				long noOfDaysBetween = ChronoUnit.DAYS.between(localDate, localDate1);
				if(noOfDaysBetween<=90) {
					return true;
				}
		 }catch (DateTimeParseException e) {
			 return false;
			// TODO: handle exception
		}
	return false;
}
	public static boolean checkExparationDate(String strDate, String dateFormat) {
		 try {
			 LocalDate localDate = LocalDate.now();
		 if (localDate.toString().equals(strDate) || localDate.isBefore(LocalDate.parse(strDate))) {
				System.out.println("Same Date");
				return true;
		 }		
		 }catch (DateTimeParseException e) {
			 return false;
			// TODO: handle exception
		}
	return false;
}
	public static boolean checkAge(String dateVal) {
		LocalDate localDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
		//convert String to LocalDate
		LocalDate localDate1 = LocalDate.parse(dateVal, formatter);
		int ageVal =Period.between(localDate1, localDate).getYears();
		if(ageVal>=18 && ageVal<=60)
		return true;
		return false;
	}
	
	public static boolean checkExpirationDate(String effDate,String expDate) {
		 try {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		//convert String to LocalDate
		LocalDate dateEffDate = LocalDate.parse(effDate, formatter);
		LocalDate dateExpDate= LocalDate.parse(expDate, formatter);
		//int datediff=dateExpDate.compareTo(dateEffDate);
		Period diff =Period.between(dateEffDate, dateExpDate);
		if( diff.getYears()==0 && diff.getMonths()==11 && diff.getDays()==30) {
		return true;
		} 
		}
		 catch (DateTimeParseException e) {
			 return false;
			// TODO: handle exception
		}
		return false;
	}
	
	public static SBSWSValidators businessErrorMapping(String field, String errorCode, String category,
			String errorType) {
		ResourceBundle resourceBundle = ResourceBundle.getBundle(com.Constant.CONST_CONFIG_SBSWSMESSAGES);
		SBSWSValidators sbsbusinessValidators = new SBSWSValidators();
		sbsbusinessValidators.setCode(errorCode);
		sbsbusinessValidators.setField(field);
		sbsbusinessValidators.setMessage(resourceBundle.getString(errorCode));
		sbsbusinessValidators.setCategory(category);
		sbsbusinessValidators.setType(errorType);
		return sbsbusinessValidators;
	}


	// Business Validation

	public static boolean getLimitofIdemnity(String limit, PolicyVO policyVO) {
		LookUpListVO cityValList = new LookUpListVO();
		cityValList = SvcUtils.getLookUpCodesList("JLT_PL_LIM", policyVO.getScheme().getTariffCode().toString(), "ALL");
		List<LookUpVO> listVal = cityValList.getLookUpList();
		for (LookUpVO lookUpVO : listVal) {
			if (Double.parseDouble(limit)==Double.parseDouble(lookUpVO.getDescription())) {
				return false;
			}
		}
		if(Double.parseDouble(limit)==Double.parseDouble(Utils.getSingleValueAppConfig("JLT_LIMIT").toString())) {
			return false;
		}else if(Double.parseDouble(limit)>Double.parseDouble(Utils.getSingleValueAppConfig("JLT_PL_WC_limit"))) {
			return false;
		}
		return true;
	}

	public static boolean getcityValidators(String checkVal) {
		LookUpListVO cityValList = new LookUpListVO();
		cityValList = SvcUtils.getLookUpCodesList("CITY", "ALL", "ALL");
		List<LookUpVO> listVal = cityValList.getLookUpList();
		for (LookUpVO lookUpVO : listVal) {
			if (checkVal.equals(lookUpVO.getCode().toString())) {
				return false;
			}
		}
		return true;
	}

	public static boolean getPLFreezoonValidation(String freeZone, PolicyVO policyVO) {
		LookUpListVO lookUpListVO = new LookUpListVO();
		lookUpListVO = SvcUtils.getLookUpCodesList("FREE_ZONE", policyVO.getScheme().getTariffCode().toString(), "ALL");
		List<LookUpVO> listLookUp = lookUpListVO.getLookUpList();
		for (LookUpVO lookUpVO : listLookUp) {
			if (freeZone.equals(lookUpVO.getCode().toString())) {
				return false;
			}
		}
		return true;
	}

	public static boolean getBusinessTypeValidation(String freeZone) {
		LookUpListVO lookUpListVO = new LookUpListVO();
		lookUpListVO = SvcUtils.getLookUpCodesList("BUSINESS_TYPE", "ALL", "ALL");
		List<LookUpVO> listLookUp = lookUpListVO.getLookUpList();
		for (LookUpVO lookUpVO : listLookUp) {
			if (freeZone.equals(lookUpVO.getCode().toString())) {
				return false;
			}
		}
		return true;
	}

	public static boolean getEmployeeLiability(String empLiability, PolicyVO policyVO) {
		LookUpListVO lookUpListVO = new LookUpListVO();
		lookUpListVO = SvcUtils.getLookUpCodesList("JLT_WC_LIM", policyVO.getScheme().getTariffCode().toString(), "ALL");
		List<LookUpVO> listLookUp = lookUpListVO.getLookUpList();
		for (LookUpVO lookUpVO : listLookUp) {
			if (Double.parseDouble(empLiability)==Double.parseDouble(lookUpVO.getDescription())) {
				return false;
			}
		}
		if(Double.parseDouble(empLiability)==Double.parseDouble(Utils.getSingleValueAppConfig("JLT_LIMIT").toString())) {
			return false;
		}else if(Double.parseDouble(empLiability)>=Double.parseDouble((Utils.getSingleValueAppConfig("JLT_PL_WC_limit")))) {
			return false;
		}
		return true;
	}
	
	public static boolean getGPAEmployeeType(String empType, PolicyVO policyVO) {
		LookUpListVO lookUpListVO = new LookUpListVO();
		lookUpListVO = SvcUtils.getLookUpCodesList("GP_EMPTYPE", policyVO.getScheme().getTariffCode().toString(), "ALL");
		List<LookUpVO> listLookUp = lookUpListVO.getLookUpList();
		for (LookUpVO lookUpVO : listLookUp) {
			if (empType.equals(lookUpVO.getCode().toString())) {
				return false;
			}
		}
		return true;
	}
	public static boolean getOccupancyTrade(String ocupTrade, PolicyVO policyVO) {
		LookUpListVO lookUpListVO = new LookUpListVO();
		lookUpListVO = SvcUtils.getLookUpCodesList("OCCUPANCY",policyVO.getScheme().getSchemeCode().toString(),policyVO.getScheme().getTariffCode().toString());
		List<LookUpVO> listLookUp = lookUpListVO.getLookUpList();
		for (LookUpVO lookUpVO : listLookUp) {
			if (ocupTrade.equals(lookUpVO.getCode().toString())) {
				return false;
			}
		}
		return true;
	}
	public static boolean getBusinessDesc(String ocupTrade) {
		LookUpListVO lookUpListVO = new LookUpListVO();
		lookUpListVO = SvcUtils.getLookUpCodesList("BUSINESS_DESC","ALL","ALL");
		List<LookUpVO> listLookUp = lookUpListVO.getLookUpList();
		if(listLookUp.size()>0) {
		for (LookUpVO lookUpVO : listLookUp) {
			if (ocupTrade.equals(lookUpVO.getCode().toString())) {
				return false;
			}
		}
		}
		return true;
	}
	public static boolean getLocation(String location) {
		LookUpListVO lookUpListVO = new LookUpListVO();
		lookUpListVO = SvcUtils.getLookUpCodesList("DIRECTORATE","ALL","ALL");
		List<LookUpVO> listLookUp = lookUpListVO.getLookUpList();
		if(listLookUp.size()>0) {
		for (LookUpVO lookUpVO : listLookUp) {
			if (location.equals(lookUpVO.getCode().toString())) {
				return false;
			}
		}
		}
		return true;
	}
	
	
	public static CreateSBSQuoteResponse getExceptionMessage(CreateSBSQuoteResponse createSBSQuoteResponse , Long trnRefNum) {
		ResourceBundle resourceBundle = ResourceBundle.getBundle(com.Constant.CONST_CONFIG_SBSWSMESSAGES);
		createSBSQuoteResponse.setSbswsValidationException(null);
		createSBSQuoteResponse.setAgent(null);
		createSBSQuoteResponse.setLiabilityInformation(null);
		createSBSQuoteResponse.setPolicyHolder(null);
		createSBSQuoteResponse.setQuoteId(null);
		createSBSQuoteResponse.setQuoteStatus(null);
		createSBSQuoteResponse.setSbswsValidators(null);
		createSBSQuoteResponse.setSelectedPlan(null);
		createSBSQuoteResponse.setUwApprovalStatus(null);
		createSBSQuoteResponse.setPolicySchedule(null);
		createSBSQuoteResponse.setQuoteInternalReference(null);
		createSBSQuoteResponse.setMesssage(resourceBundle.getString("SBSWS_EXPN_500")+trnRefNum);
		return createSBSQuoteResponse;
	}
	public static CreateSBSQuoteResponse getExceptionRetriveMessage(CreateSBSQuoteResponse createSBSQuoteResponse , Long trnRefNum) {
		ResourceBundle resourceBundle = ResourceBundle.getBundle(com.Constant.CONST_CONFIG_SBSWSMESSAGES);
		createSBSQuoteResponse.setSbswsValidationException(null);
		createSBSQuoteResponse.setAgent(null);
		createSBSQuoteResponse.setLiabilityInformation(null);
		createSBSQuoteResponse.setPolicyHolder(null);
		createSBSQuoteResponse.setQuoteId(null);
		createSBSQuoteResponse.setQuoteStatus(null);
		createSBSQuoteResponse.setSbswsValidators(null);
		createSBSQuoteResponse.setSelectedPlan(null);
		createSBSQuoteResponse.setUwApprovalStatus(null);
		createSBSQuoteResponse.setPolicySchedule(null);
		createSBSQuoteResponse.setQuoteInternalReference(null);
		createSBSQuoteResponse.setMesssage(resourceBundle.getString("SBSWS_EXPN_501")+trnRefNum);
		return createSBSQuoteResponse;
	}
	
	//created for rounding of decimal in premium value 
	public static Double getFormattedDoubleWithTwoDecimals(Double decValCount) {
	/*	DecimalFormat f = new DecimalFormat("##.00");
		double amount = Double.parseDouble(f.format(decValCount));*/
		   return ((long)(decValCount * 1e2)) / 1e2;
	}
  
	// created for the 2 decimal with .00 if whole number is present.
	public static BigDecimal getFormatForDecimalWithSingleDigit(Number decValFormat) {
		if(decValFormat!=null)
			return new BigDecimal(String.format("%.2f", decValFormat));
		else
			return null;
	}
}
