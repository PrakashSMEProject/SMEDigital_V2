/**
 * 
 */
package com.rsaame.pas.b2c.validator;

import java.util.Calendar;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.b2c.cmn.constants.AppConstants;
import com.rsaame.pas.b2c.cmn.utils.AppUtils;
import com.rsaame.pas.b2c.cmn.utils.ValidationUtil;
import com.rsaame.pas.vo.bus.TravelInsuranceVO;
import com.rsaame.pas.vo.bus.TravelerDetailsVO;

/**
 * @author m1019193
 *
 */
public class TravelRenewalValidator implements Validator{

	@Override
	public boolean supports(Class<?> arg0) {
		return false;
	}

	@Override
	public void validate(Object arg0, Errors errors) {

		TravelInsuranceVO travelInsuranceVO = (TravelInsuranceVO) arg0;
		if (!Utils.isEmpty(travelInsuranceVO) && !Utils.isEmpty(travelInsuranceVO.getGeneralInfo()) ) {
			boolean doubleValidationFlg = true;
			/* For General Info Mobile Number */
			if (Utils.isEmpty(travelInsuranceVO.getGeneralInfo().getInsured().getMobileNo()) || !ValidationUtil.isNumeric(travelInsuranceVO.getGeneralInfo().getInsured().getMobileNo())){
				doubleValidationFlg = false;
				errors.rejectValue(com.Constant.CONST_ERRORMESSAGE, "generalInfo.insured.mobileNo.invalid", "Please enter valid Mobile Number");
			}
			if (doubleValidationFlg && (travelInsuranceVO.getGeneralInfo().getInsured().getMobileNo().length() <  AppConstants.B2C_ALLOWED_MIN_MOB_NUM_LENGTH)) {
				errors.rejectValue(com.Constant.CONST_ERRORMESSAGE, "generalInfo.insured.mobileNo.invalid", "Please enter Mobile Number with minimum 9 digits");
				doubleValidationFlg = true;
			}
			/* For General Info Email Id */
			if (Utils.isEmpty(travelInsuranceVO.getGeneralInfo().getInsured().getEmailId()) || !ValidationUtil.isValidEmail(travelInsuranceVO.getGeneralInfo().getInsured().getEmailId())) {
				doubleValidationFlg = false;
				errors.rejectValue(com.Constant.CONST_ERRORMESSAGE, "generalInfo.insured.emailId.invalid", "Please enter valid E-mail id.");
			}
			if (doubleValidationFlg && (travelInsuranceVO.getGeneralInfo().getInsured().getEmailId().length() > AppConstants.B2C_ALLOWED_MAX_EMAILID_LENGTH)) {
				errors.rejectValue(com.Constant.CONST_ERRORMESSAGE, "generalInfo.insured.emailId.invalid", "Please enter valid E-mail id.");
				doubleValidationFlg = true;
			}
			/* For General Info Reward Programme and Guest Card Number*/
			Integer royaltyType = travelInsuranceVO.getGeneralInfo().getInsured().getRoyaltyType();
			String guestCardNo = travelInsuranceVO.getGeneralInfo().getInsured().getGuestCardNo();
			
			if( !Utils.isEmpty( travelInsuranceVO.getGeneralInfo().getInsured() ) 
					&& ( !Utils.isEmpty( royaltyType ) || !Utils.isEmpty( guestCardNo ) ) ){
				
				if( !Utils.isEmpty( royaltyType ) && Utils.isEmpty( guestCardNo ) ){
					if( !Utils.getSingleValueAppConfig( "AIRML_NOT_A_MEMBER" ).equals( royaltyType.toString() ) ){
						errors.rejectValue( com.Constant.CONST_ERRORMESSAGE, com.Constant.CONST_ERRORMESSAGE_INVALID, "Please enter Guest Card number." );
					}
				}				
				else if( !Utils.isEmpty( royaltyType ) && !Utils.isEmpty( guestCardNo ) ){

					String guestCardStartsWith = null;

					if( Utils.getSingleValueAppConfig( "AIRML_AIRMILES" ).equals( royaltyType.toString() ) ){
						guestCardStartsWith = Utils.getSingleValueAppConfig( "AIRML_AIRMILES_STARTS_WITH" );
						
						if (ValidationUtil.isNumeric(guestCardNo)) {
							if( guestCardNo.length() != Integer.valueOf( Utils.getSingleValueAppConfig( "AIRML_AIRMILES_MAXLENGTH" ) ) ){
								errors.rejectValue( com.Constant.CONST_ERRORMESSAGE, com.Constant.CONST_ERRORMESSAGE_INVALID, "Airmiles No. must be a " + Utils.getSingleValueAppConfig( "AIRML_AIRMILES_MAXLENGTH" )
										+ " digit number." );
							}
							if( !guestCardNo.startsWith( guestCardStartsWith ) ){
								errors.rejectValue( com.Constant.CONST_ERRORMESSAGE, com.Constant.CONST_ERRORMESSAGE_INVALID, "First three digits of Airmiles No. must be " + guestCardStartsWith );
							}
						} else {
							errors.rejectValue( com.Constant.CONST_ERRORMESSAGE, com.Constant.CONST_ERRORMESSAGE_INVALID, "Please provide Air Miles card number in numerical values only.");
						}
					}
					else if( Utils.getSingleValueAppConfig( "AIRML_ETIHAD_GUEST_PROGRAMME" ).equals( royaltyType.toString() ) ){
						guestCardStartsWith = Utils.getSingleValueAppConfig( "AIRML_ETIHAD_GUEST_PROGRAMME_STARTS_WITH" );
						
						if (ValidationUtil.isNumeric(guestCardNo)) {
							if( guestCardNo.length() != Integer.valueOf( Utils.getSingleValueAppConfig( "AIRML_ETIHAD_GUEST_PROGRAMME_MAXLENGTH" ) ) ){
								errors.rejectValue( com.Constant.CONST_ERRORMESSAGE, com.Constant.CONST_ERRORMESSAGE_INVALID,
										"Etihad No. must be a " + Utils.getSingleValueAppConfig( "AIRML_ETIHAD_GUEST_PROGRAMME_MAXLENGTH" ) + " digit number." );
							}
							if( !guestCardNo.startsWith( guestCardStartsWith ) ){
								errors.rejectValue( com.Constant.CONST_ERRORMESSAGE, com.Constant.CONST_ERRORMESSAGE_INVALID, "First two digits of Etihad No. must be " + guestCardStartsWith );
							}
						} else {
							errors.rejectValue( com.Constant.CONST_ERRORMESSAGE, com.Constant.CONST_ERRORMESSAGE_INVALID, "Please provide Etihad card number in numerical values only.");
						}
					}
					else if( Utils.getSingleValueAppConfig( "AIRML_NOT_A_MEMBER" ).equals( royaltyType.toString() ) ){
						errors.rejectValue( com.Constant.CONST_ERRORMESSAGE, com.Constant.CONST_ERRORMESSAGE_INVALID, "Guest card number should be empty." );
					}

				}
				else if( Utils.isEmpty( royaltyType ) && !Utils.isEmpty( guestCardNo ) ){
					errors.rejectValue( com.Constant.CONST_ERRORMESSAGE, com.Constant.CONST_ERRORMESSAGE_INVALID, "Please select a Reward Programme." );
				}

			}

		}		
		
		if (!Utils.isEmpty(travelInsuranceVO) && !Utils.isEmpty(travelInsuranceVO.getTravelDetailsVO() ) &&  !Utils.isEmpty(travelInsuranceVO.getTravelDetailsVO().getTravelerDetailsList() ) ) {
		/* For General Info Travelers details*/		
			int i = 1; //Index started from index as 1 for displaying the error message only.
			for(TravelerDetailsVO travelers:travelInsuranceVO.getTravelDetailsVO().getTravelerDetailsList()) {
					
				if (!ValidationUtil.isAlphaWithSpace(travelers.getName())) {
						errors.rejectValue(com.Constant.CONST_ERRORMESSAGE, "travelDetailsVO.travelPeriod.invalid", com.Constant.CONST_FOR_TRAVELLER_END+i+" Please enter valid traveller name.");
				}
				if (Utils.isEmpty(travelers.getDateOfBirth())) {
					errors.rejectValue(com.Constant.CONST_ERRORMESSAGE, "travelDetailsVO.dob.invalid", com.Constant.CONST_FOR_TRAVELLER_END+i+" Please enter the travellers date of birth.");
				}
				if (Utils.isEmpty(travelers.getNationality()) || !ValidationUtil.isNumeric(String.valueOf(travelers.getNationality()))) {
					errors.rejectValue(com.Constant.CONST_ERRORMESSAGE, "travelDetailsVO.nationality.invalid", com.Constant.CONST_FOR_TRAVELLER_END+i+" Please enter valid nationality.");
				}
				if (Utils.isEmpty(travelers.getRelation()) || !ValidationUtil.isNumeric(travelers.getRelation().toString())) {
					errors.rejectValue(com.Constant.CONST_ERRORMESSAGE, "travelDetailsVO.relation.invalid", com.Constant.CONST_FOR_TRAVELLER_END+i+" Please enter valid relation.");
				}
				i++;
			}
		}
			
			/* For General Info Travel Location */
		if (!Utils.isEmpty(travelInsuranceVO) && !Utils.isEmpty(travelInsuranceVO.getTravelDetailsVO() )){
			if (Utils.isEmpty(travelInsuranceVO.getTravelDetailsVO().getTravelLocation())) {
				errors.rejectValue(com.Constant.CONST_ERRORMESSAGE, "travelDetailsVO.travelLocation.invalid", "Please select any travel location.");
			}
		}
			
		/* For Start Date of Coverage if it is null */
		if(Utils.isEmpty(travelInsuranceVO.getCommonVO().getPolEffectiveDate())){
			errors.rejectValue(com.Constant.CONST_ERRORMESSAGE, "commonVO.polEffectiveDate.invalid", "Please Enter Start Date of coverage.");
		}
			
			/* For Start Date of Coverage if it is not null */
		if (!Utils.isEmpty(travelInsuranceVO.getCommonVO().getPolEffectiveDate()) && 
				!Utils.isEmpty(travelInsuranceVO.getScheme().getEffDate())) {
			Calendar actualStartDate = Calendar.getInstance();
			Calendar newStartDate = Calendar.getInstance();
			actualStartDate.setTime(travelInsuranceVO.getCommonVO().getPolEffectiveDate());
			newStartDate.setTime(travelInsuranceVO.getScheme().getEffDate());
			if (newStartDate.before(actualStartDate)) {
					//errorFlag = true;
				errors.rejectValue(com.Constant.CONST_ERRORMESSAGE, com.Constant.CONST_ERRORMESSAGE_INVALID, "Error : Start date cannot be less than "+AppUtils.dateFormatter( actualStartDate.getTime()) );
			}
		}			
			
			/*if(!ValidationUtil.isNumeric(String.valueOf(travelInsuranceVO.getCommonVO().getQuoteNo()))) {
				errors.rejectValue(com.Constant.CONST_ERRORMESSAGE, "commonVO.quoteNo.invalid", "Quote number should be numeric");
			}*/
		
	}

}
