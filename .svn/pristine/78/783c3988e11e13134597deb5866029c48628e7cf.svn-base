/**
 * 
 */
package com.rsaame.pas.b2c.validator;

import java.util.Calendar;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.b2c.cmn.constants.AppConstants;
import com.rsaame.pas.b2c.cmn.utils.ValidationUtil;
import com.rsaame.pas.vo.bus.HomeInsuranceVO;

/**
 * @author m1019193
 *
 */
public class HomeRenewalValidator implements Validator{

	
	@Override
	public boolean supports( Class<?> arg0 ){		
		return false;
	}

	@Override
	public void validate( Object obj, Errors errors ){
		
		HomeInsuranceVO homeInsuranceVO = (HomeInsuranceVO) obj;
		if (!Utils.isEmpty(homeInsuranceVO) && !Utils.isEmpty(homeInsuranceVO.getGeneralInfo()) ) {
			boolean validationFlag = true;
			
			/* For General Info First Name */
			if( Utils.isEmpty( homeInsuranceVO.getGeneralInfo().getInsured().getFirstName() ) ){				
				errors.rejectValue(com.Constant.CONST_ERRORMESSAGE, com.Constant.CONST_GENERALINFO_INSURED_FIRSTNAME_INVALID, "Please provide First Name");
			}
			else if( !ValidationUtil.isAlphabets( homeInsuranceVO.getGeneralInfo().getInsured().getFirstName() ) ){				
				errors.rejectValue(com.Constant.CONST_ERRORMESSAGE, com.Constant.CONST_GENERALINFO_INSURED_FIRSTNAME_INVALID, "Special characters are not allowed in First Name");
			}
			else if( homeInsuranceVO.getGeneralInfo().getInsured().getFirstName().length() > AppConstants.B2C_ALLOWED_MAX_NAME_LENGTH ) {				
				errors.rejectValue(com.Constant.CONST_ERRORMESSAGE, com.Constant.CONST_GENERALINFO_INSURED_FIRSTNAME_INVALID, "First Name can not be larger than 50");
			}
			
			/* For General Info Last Name */			
			if( Utils.isEmpty( homeInsuranceVO.getGeneralInfo().getInsured().getLastName() ) ){			
				errors.rejectValue(com.Constant.CONST_ERRORMESSAGE, com.Constant.CONST_GENERALINFO_INSURED_LASTNAME_INVALID, "Please provide Second Name");
			}
			else if( !ValidationUtil.isAlphabets( homeInsuranceVO.getGeneralInfo().getInsured().getLastName() ) ){				
				errors.rejectValue(com.Constant.CONST_ERRORMESSAGE, com.Constant.CONST_GENERALINFO_INSURED_LASTNAME_INVALID, "Special characters are not allowed in Second Name");
			}
			else if( homeInsuranceVO.getGeneralInfo().getInsured().getLastName().length() > AppConstants.B2C_ALLOWED_MAX_NAME_LENGTH ) {				
				errors.rejectValue(com.Constant.CONST_ERRORMESSAGE, com.Constant.CONST_GENERALINFO_INSURED_LASTNAME_INVALID, "Second Name can not be larger than 50");
			}
			
			
			/* For General Info Mobile Number */			
			if (Utils.isEmpty( homeInsuranceVO.getGeneralInfo().getInsured().getMobileNo()) || !ValidationUtil.isNumeric(homeInsuranceVO.getGeneralInfo().getInsured().getMobileNo())){
				errors.rejectValue(com.Constant.CONST_ERRORMESSAGE, com.Constant.CONST_GENERALINFO_INSURED_MOBILENO_INVALID, "Please enter valid Mobile Number");
			}
			else if ( homeInsuranceVO.getGeneralInfo().getInsured().getMobileNo().length() <  AppConstants.B2C_ALLOWED_MIN_MOB_NUM_LENGTH ) {
				errors.rejectValue(com.Constant.CONST_ERRORMESSAGE, com.Constant.CONST_GENERALINFO_INSURED_MOBILENO_INVALID, "Please enter Mobile Number with minimum 9 digits");				
			}
			/* For General Info Email Id */
			if ( Utils.isEmpty(homeInsuranceVO.getGeneralInfo().getInsured().getEmailId()) || !ValidationUtil.isValidEmail(homeInsuranceVO.getGeneralInfo().getInsured().getEmailId())) {
				errors.rejectValue(com.Constant.CONST_ERRORMESSAGE, "generalInfo.insured.emailId.invalid", "Please enter valid E-mail id.");
			}
			else if ( homeInsuranceVO.getGeneralInfo().getInsured().getEmailId().length() > AppConstants.B2C_ALLOWED_MAX_EMAILID_LENGTH ) {
				errors.rejectValue(com.Constant.CONST_ERRORMESSAGE, "generalInfo.insured.emailId.invalid", "Please enter valid E-mail id.");
				
			}
			
			/* For General Info PO Box No */			
			if (Utils.isEmpty(homeInsuranceVO.getGeneralInfo().getInsured().getAddress().getPoBox()) || !ValidationUtil.isNumeric(homeInsuranceVO.getGeneralInfo().getInsured().getAddress().getPoBox())){
				errors.rejectValue(com.Constant.CONST_ERRORMESSAGE, "generalInfo.insured.address.poBox.invalid", "Please Provide a valid PO Box");
			}
			else if ( !ValidationUtil.isNumeric(homeInsuranceVO.getGeneralInfo().getInsured().getAddress().getPoBox())){				
				errors.rejectValue(com.Constant.CONST_ERRORMESSAGE, "generalInfo.insured.address.poBox.invalid", "Enter numeric value only ( PO Box)");
			}
			else if (homeInsuranceVO.getGeneralInfo().getInsured().getAddress().getPoBox().length() >  AppConstants.B2C_ALLOWED_POBOX_LENGTH ) {
				errors.rejectValue(com.Constant.CONST_ERRORMESSAGE, com.Constant.CONST_GENERALINFO_INSURED_MOBILENO_INVALID, "Please enter PO Box of 6 digits");				
			}
			
			/*For General Info Emirates */			
			if ( Utils.isEmpty(homeInsuranceVO.getGeneralInfo().getInsured().getAddress().getEmirates() ) ){				
				errors.rejectValue(com.Constant.CONST_ERRORMESSAGE, com.Constant.CONST_ERRORMESSAGE_INVALID, "Please provide the Emirate");
			}
			
			/* For General Info Reward Programme and Guest Card Number*/
			Integer royaltyType = homeInsuranceVO.getGeneralInfo().getInsured().getRoyaltyType();
			String guestCardNo = homeInsuranceVO.getGeneralInfo().getInsured().getGuestCardNo();
			
			if( !Utils.isEmpty( homeInsuranceVO.getGeneralInfo().getInsured() ) 
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

		if (!Utils.isEmpty(homeInsuranceVO) && !Utils.isEmpty(homeInsuranceVO.getBuildingDetails()) ) {			
	
			/*For Building Details -Emirates */			
			if ( Utils.isEmpty(homeInsuranceVO.getBuildingDetails().getEmirates() ) ){				
				System.out.println("Emirates = "+homeInsuranceVO.getBuildingDetails().getEmirates());
				errors.rejectValue(com.Constant.CONST_ERRORMESSAGE, "buildingDetails.emirates.invalid", "Please provide the Emirate where your home is located");
			}
			
			/*For Building Details -Area */			
			if ( Utils.isEmpty(homeInsuranceVO.getBuildingDetails().getArea() ) ){				
				System.out.println("Area = "+homeInsuranceVO.getBuildingDetails().getArea());
				errors.rejectValue(com.Constant.CONST_ERRORMESSAGE, "buildingDetails.area.invalid", "Please provide the Area where your home is located");
			}
			
			/*For Building Details -Property Type */			
			if ( Utils.isEmpty(homeInsuranceVO.getBuildingDetails().getTypeOfProperty() ) ){			
				errors.rejectValue(com.Constant.CONST_ERRORMESSAGE, "buildingDetails.typeOfProperty.invalid", "Please provide the type of your Property");
			}
			
			/*For Building Details -Building Name */			
			if ( Utils.isEmpty(homeInsuranceVO.getBuildingDetails().getBuildingname() ) ){			
				errors.rejectValue(com.Constant.CONST_ERRORMESSAGE, "buildingDetails.buildingname.invalid", "Please provide the name of your Building or Community");
			}
			//CTS - TFS # 42729 - Building name validation - Starts
			/*else if ( !ValidationUtil.isAlphaNumericWithSpace( homeInsuranceVO.getBuildingDetails().getBuildingname() ) ){			
				errors.rejectValue(com.Constant.CONST_ERRORMESSAGE, "buildingDetails.buildingname.invalid", "Please remove special characters from Building or Community Name");
			}*/
			//CTS - TFS # 42729 - Building name validation - Ends
			/*For Building Details -Flat/Villa No */			
			if ( Utils.isEmpty(homeInsuranceVO.getBuildingDetails().getFlatVillaNo() ) ) {			
				errors.rejectValue(com.Constant.CONST_ERRORMESSAGE, "buildingDetails.flatVillaNo.invalid", "Please provide your Flat or Villa Number");
			}
		}
			
		if ( !Utils.isEmpty( homeInsuranceVO ) && !Utils.isEmpty( homeInsuranceVO.getCommonVO() ) && !Utils.isEmpty(homeInsuranceVO.getScheme() ) ) {		
		/* For Start Date of Coverage if it is null */
			//if( Utils.isEmpty( homeInsuranceVO.getCommonVO().getPolEffectiveDate() ) ){
			if( Utils.isEmpty( homeInsuranceVO.getScheme().getEffDate() ) ){
				errors.rejectValue(com.Constant.CONST_ERRORMESSAGE, "commonVO.polEffectiveDate.invalid", "Please Enter Start Date of coverage.");
			}
				
				/* For Start Date of Coverage if it is not null */
			if (!Utils.isEmpty(homeInsuranceVO.getCommonVO().getPolEffectiveDate()) && 
					!Utils.isEmpty(homeInsuranceVO.getScheme().getEffDate())) {
				Calendar actualStartDate = Calendar.getInstance();
				Calendar newStartDate = Calendar.getInstance();
				actualStartDate.setTime(homeInsuranceVO.getCommonVO().getPolEffectiveDate());
				newStartDate.setTime(homeInsuranceVO.getScheme().getEffDate());
				if (newStartDate.before(actualStartDate)) {
						//errorFlag = true;
					errors.rejectValue(com.Constant.CONST_ERRORMESSAGE, com.Constant.CONST_ERRORMESSAGE_INVALID, "Error : Start date cannot be less than "+actualStartDate);
				}
			}	
		}
		
		
	}

}
