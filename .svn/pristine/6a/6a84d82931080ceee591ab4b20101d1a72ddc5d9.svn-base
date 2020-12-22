package com.rsaame.pas.b2c.validator;


import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.mindtree.ruc.cmn.utils.LoginLocation;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.b2c.cmn.constants.AppConstants;
import com.rsaame.pas.b2c.cmn.utils.AppUtils;
import com.rsaame.pas.b2c.cmn.utils.ValidationUtil;
import com.rsaame.pas.dao.utils.NextSequenceValue;
import com.rsaame.pas.vo.bus.TravelInsuranceVO;
import com.rsaame.pas.vo.bus.TravelerDetailsVO;

/**
 * @author M1020859
 *
 */
public class TravelGIQuoteCreateValidator implements Validator {

	@Override
	public boolean supports(Class<?> arg0) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 * org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object arg0, Errors errors) {
		
		/*
		 * D2C Gender Validation : BEGIN
		 */
		LoginLocation location = (LoginLocation) Utils
				.getBean("location");
		String deployedLocation = location.getLocation();
		// boolean errorFlag = false;
		TravelInsuranceVO travelInsuranceVO = (TravelInsuranceVO) arg0;
		if (!Utils.isEmpty(travelInsuranceVO)
				&& !Utils.isEmpty(travelInsuranceVO.getGeneralInfo())) {
			boolean doubleValidationFlg = true;
			/* For General Info Mobile Number */
			if (Utils.isEmpty(travelInsuranceVO.getGeneralInfo().getInsured()
					.getMobileNo())
					|| !ValidationUtil.isNumeric(travelInsuranceVO
							.getGeneralInfo().getInsured().getMobileNo())) {
				doubleValidationFlg = false;
				
				if (null != deployedLocation
						&& deployedLocation.equalsIgnoreCase(AppConstants.LOCATION_CODE)) {
				errors.rejectValue(com.Constant.CONST_ERRORMESSAGE,
						com.Constant.CONST_GENERALINFO_INSURED_MOBILENO_INVALID,
						"Please provide Mobile Number in numeric values with a minimum of eight digits.");
				}
				else
				{
					/*d2c*/
					errors.rejectValue(com.Constant.CONST_ERRORMESSAGE,
							com.Constant.CONST_GENERALINFO_INSURED_MOBILENO_INVALID,
							"Please provide Mobile Number in numeric values with a minimum of nine digits.");
				}
			}
			if (doubleValidationFlg
					&& (travelInsuranceVO.getGeneralInfo().getInsured()
							.getMobileNo().length() < AppConstants.B2C_ALLOWED_MIN_MOB_NUM_LENGTH)) {
				
				
				if (null != deployedLocation && deployedLocation.equals(AppConstants.LOCATION_CODE) ) {
				errors.rejectValue(com.Constant.CONST_ERRORMESSAGE,
						com.Constant.CONST_GENERALINFO_INSURED_MOBILENO_INVALID,
						"Please provide Mobile Number in numeric values with a minimum of eight digits.");
				}
				else {
					errors.rejectValue(com.Constant.CONST_ERRORMESSAGE,
							com.Constant.CONST_GENERALINFO_INSURED_MOBILENO_INVALID,
							"Please provide Mobile Number in numeric values with a minimum of nine digits.");
				}
				doubleValidationFlg = true;
			}
		
			/* For General Info Email Id */
			if (Utils.isEmpty(travelInsuranceVO.getGeneralInfo().getInsured()
					.getEmailId())
					|| !ValidationUtil.isValidEmail(travelInsuranceVO
							.getGeneralInfo().getInsured().getEmailId())) {
				doubleValidationFlg = false;
				errors.rejectValue(com.Constant.CONST_ERRORMESSAGE,
						"generalInfo.insured.emailId.invalid",
						"Please provide email address.");
			}
			if (doubleValidationFlg
					&& (travelInsuranceVO.getGeneralInfo().getInsured()
							.getEmailId().length() > AppConstants.B2C_ALLOWED_MAX_EMAILID_LENGTH)) {
				errors.rejectValue(com.Constant.CONST_ERRORMESSAGE,
						"generalInfo.insured.emailId.invalid",
						"Please provide valid email address.");
				doubleValidationFlg = true;
			}
			/* For General Info Travel Period */
			if (Utils.isEmpty(travelInsuranceVO.getScheme().getEffDate())) {
				errors.rejectValue(com.Constant.CONST_ERRORMESSAGE, "scheme.effDate.invalid",
						"Please select start date of journey.");
			}
			if (Utils.isEmpty(travelInsuranceVO.getScheme().getExpiryDate())) {
				errors.rejectValue(com.Constant.CONST_ERRORMESSAGE, "scheme.expiryDate.invalid",
						"Please enter travel end date.");
			}
			/* For travel start date and end date validation */
			if (!Utils.isEmpty(travelInsuranceVO.getScheme().getEffDate())
					&& !Utils.isEmpty(travelInsuranceVO.getScheme()
							.getExpiryDate())) {
				Calendar fromDate = Calendar.getInstance();
				Calendar toDate = Calendar.getInstance();
				fromDate.setTime(travelInsuranceVO.getScheme().getEffDate());
				toDate.setTime(travelInsuranceVO.getScheme().getExpiryDate());
				if (toDate.before(fromDate)) {
					// errorFlag = true;
					errors.rejectValue(com.Constant.CONST_ERRORMESSAGE, com.Constant.CONST_ERRORMESSAGE_INVALID,
							"Travel start date cannot be later than travel end date.");
				}
			}
			if (!Utils.isEmpty(travelInsuranceVO.getScheme().getEffDate())
					&& !Utils.isEmpty(travelInsuranceVO.getScheme()
							.getExpiryDate())
					&& !Utils.isEmpty(travelInsuranceVO.getPolicyTerm())) {
				Long difference = AppUtils.getDateDifference(travelInsuranceVO
						.getScheme().getExpiryDate(), travelInsuranceVO
						.getScheme().getEffDate());
				if (difference > 365) {
					difference = 365l;
				}
				if (travelInsuranceVO.getPolicyTerm() > difference.intValue()) {
					errors.rejectValue(com.Constant.CONST_ERRORMESSAGE, com.Constant.CONST_ERRORMESSAGE_INVALID,
							"Travel period is more than the selected Start Date and End Date");
				}
				if (travelInsuranceVO.getPolicyTerm() < difference) {
					errors.rejectValue(com.Constant.CONST_ERRORMESSAGE, com.Constant.CONST_ERRORMESSAGE_INVALID,
							"Travel period is less than the selected Start Date and End Date");
				}
			}
			/* For General Info Travel Location */
			if (Utils.isEmpty(travelInsuranceVO.getTravelDetailsVO()
					.getTravelLocation())) {
				errors.rejectValue(com.Constant.CONST_ERRORMESSAGE,
						"travelDetailsVO.travelLocation.invalid",
						"Please select location of travel.");
			}

			/* For General Info Travelers details start */
			int i = 1; // Index started from 1 for displaying the error message
						// only.
			for (TravelerDetailsVO travelers : travelInsuranceVO
					.getTravelDetailsVO().getTravelerDetailsList()) {

				if (!ValidationUtil.isAlphaWithSpace(travelers.getName())) {
					errors.rejectValue(com.Constant.CONST_ERRORMESSAGE,
							"travelDetailsVO.travelPeriod.invalid",
							com.Constant.CONST_FOR_TRAVELLER_END + i
									+ " Please provide names of travellers.");
				}
				if (Utils.isEmpty(travelers.getDateOfBirth())) {
					errors.rejectValue(
							com.Constant.CONST_ERRORMESSAGE,
							"travelDetailsVO.dob.invalid",
							com.Constant.CONST_FOR_TRAVELLER_END
									+ i
									+ " Please provide date of birth for Travellers.");
				}
				if (Utils.isEmpty(travelers.getNationality())
						|| !ValidationUtil.isNumeric(String.valueOf(travelers
								.getNationality()))) {
					errors.rejectValue(com.Constant.CONST_ERRORMESSAGE,
							"travelDetailsVO.nationality.invalid",
							com.Constant.CONST_FOR_TRAVELLER_END + i
									+ " Please select travellers nationality.");
				}
				if (Utils.isEmpty(travelers.getRelation())
						|| !ValidationUtil.isNumeric(travelers.getRelation()
								.toString())) {
					errors.rejectValue(com.Constant.CONST_ERRORMESSAGE,
							"travelDetailsVO.relation.invalid",
							com.Constant.CONST_FOR_TRAVELLER_END + i
									+ " Please select travellers relation.");
				}

		
				if (null != deployedLocation
						&& deployedLocation.equalsIgnoreCase(AppConstants.LOCATION_CODE)) {

					if (Utils.isEmpty(travelers.getGender())
							&& !(String.valueOf(travelers.getGender()).equalsIgnoreCase("f")||String.valueOf(travelers.getGender()).equalsIgnoreCase("m"))) {
						errors.rejectValue(com.Constant.CONST_ERRORMESSAGE,
								"travelDetailsVO.nationality.invalid",
								com.Constant.CONST_FOR_TRAVELLER_END + i
										+ " Please select travellers gender.");
					}
				}
				/*
				 * D2C Gender Validation : END
				 */
				i++;
			}
			/* For General Info Travelers details end */
			
			/* check for the number of quotes for same user */
			if(!Utils.isEmpty(travelInsuranceVO.getGeneralInfo().getInsured().getMobileNo()) &&
					!Utils.isEmpty(travelInsuranceVO.getGeneralInfo().getInsured().getEmailId())) {
				HibernateTemplate ht = (HibernateTemplate) Utils.getBean("hibernateTemplate");
				Session session = ht.getSessionFactory().openSession();
				try{
					
					Date date = new Date();  
				    SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yy");  
				    String strDate= formatter.format(date); 
				    ResourceBundle resourceBundle = ResourceBundle.getBundle("config.appconfig");
				    String expiryDate = resourceBundle.getString("DEFAULT_POL_VALIDITY_EXPIRY_DATE");
				    Integer userId = Integer.parseInt(resourceBundle.getString("USER_6"));
				    Integer policyType=Integer.parseInt(resourceBundle.getString("SHORT_TRAVEL_POL_TYPE"));
				    Integer policyTypeannual=Integer.parseInt(resourceBundle.getString("LONG_TRAVEL_POL_TYPE"));
				   Query query= session.createSQLQuery("select count(pol_policy_id) from t_mas_cash_customer_quo a join t_trn_policy_quo b on a.csh_policy_id = b.pol_policy_id and  a.csh_validity_expiry_date='"+expiryDate+"' "+
				            "  and b.pol_validity_expiry_date='"+expiryDate+"' "+
							" and a.csh_prepared_by in  " +"("+userId+",992)" + 
							"    and  a.csh_e_email_id='"+ travelInsuranceVO.getGeneralInfo().getInsured().getEmailId()+ "'"+
							"    and a.csh_e_gsm_no='"+travelInsuranceVO.getGeneralInfo().getInsured().getMobileNo()+"'"+ 
							"    and a.csh_loc_code= "+travelInsuranceVO.getCommonVO().getLocCode() + 
							"    and TRUNC(a.csh_prepared_dt) = '"+strDate.toUpperCase()+"'"+
							 "   and b.pol_policy_type in" +"("+policyType+","+policyTypeannual+")");
					
				Object o = query.uniqueResult();
					int size = ((Number)o).intValue();
					if(size>=Integer.parseInt(resourceBundle.getString("B2C_USER_QUOTE_LIMIT_PER_DAY").trim())) {
						errors.rejectValue(com.Constant.CONST_ERRORMESSAGE,
								"travelInsuranceVO.getGeneralInfo().getInsured()",
								"You are not allowed to have more than 5 quotes per day");
					}
				}
				catch(Exception e)
				{
					System.out.println(e);
					//LOGGER.info("Exception while saving in Auditing Table : "+e);
					
				}
				finally {
					session.close();
				}
			}
		}
	}
}
