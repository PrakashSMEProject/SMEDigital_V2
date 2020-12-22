package com.rsaame.pas.b2c.validator;

import java.util.List;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.b2c.cmn.constants.AppConstants;
import com.rsaame.pas.b2c.cmn.utils.ValidationUtil;
import com.rsaame.pas.vo.bus.CoverDetailsVO;
import com.rsaame.pas.vo.bus.TravelInsuranceVO;
import com.rsaame.pas.vo.bus.TravelPackageVO;
import com.rsaame.pas.vo.cmn.CoverVO;

public class TravelRiskValidator implements Validator{

	@Override
	public boolean supports( Class<?> arg0 ){
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void validate( Object arg0, Errors errors ){

		TravelInsuranceVO travelInsuranceVO = (TravelInsuranceVO) arg0;
		String[] mandatoryCovers = Utils.getMultiValueAppConfig( "MANDATORY_COVERS_TRAVEL" );

		if( !Utils.isEmpty( travelInsuranceVO ) && !Utils.isEmpty( travelInsuranceVO.getScheme() ) ){
			/* Validation for tariff code */
			if( Utils.isEmpty( travelInsuranceVO.getScheme().getTariffCode() ) ){
				errors.rejectValue( com.Constant.CONST_ERRORMESSAGE, com.Constant.CONST_ERRORMESSAGE_INVALID, "Please select a Insurance Product Cover." );
			}
			
			/* Validation for basic cover checkBoxes.*/
			if( !Utils.isEmpty( mandatoryCovers ) ){

				boolean atLeastOneBasicCoverFound = false;
				List<CoverDetailsVO> covers = null;
				CoverDetailsVO coverDetailsVO = null;
				CoverDetailsVO coverRetrieved = null;
				CoverVO coverVO = null;

				String[] doNotCheckForTariffs = Utils.getMultiValueAppConfig( "COVERS_VALIDATION_NOT_FOR" );

				for( TravelPackageVO packageVO : travelInsuranceVO.getTravelPackageList() ){
					/* Validate only if the tariff is not among the ones not to be checked.
					 * Not to be checked tariffs are those for which personal possession and emergency medical expenses 
					 * are not covered ( they appear as cross mark in the UI ).
					 * */
					if( !Utils.isEmpty( packageVO.getIsSelected() ) && packageVO.getIsSelected()
							&& !CopyUtils.asList( doNotCheckForTariffs ).contains( packageVO.getTariffCode().toString() ) ){

						covers = packageVO.getCovers();

						for( String covCode : mandatoryCovers ){

							coverVO = new CoverVO();
							coverDetailsVO = new CoverDetailsVO();
							coverVO.setCovCode( Short.valueOf( covCode ) );
							coverDetailsVO.setCoverCodes( coverVO );
							coverRetrieved = null;

							if( covers.contains( coverDetailsVO ) ){
								coverRetrieved = covers.get( covers.indexOf( coverDetailsVO ) );
								if( ( coverRetrieved.getSumInsured().getSumInsured().compareTo( Double.valueOf( AppConstants.zeroVal ) ) > 0 )
										|| ( !Utils.isEmpty( coverRetrieved.getIsCovered() ) && AppConstants.STATUS_ON.equalsIgnoreCase( coverRetrieved.getIsCovered() ) ) ){
									atLeastOneBasicCoverFound = true;
									break;
								}

							}

						}

					}
					else if( !Utils.isEmpty( packageVO.getIsSelected() ) && packageVO.getIsSelected()
							&& CopyUtils.asList( doNotCheckForTariffs ).contains( packageVO.getTariffCode().toString() ) ){
						/* Set the boolean variable as true so that exception is not thrown for tariffs on which validation is not required. */
						atLeastOneBasicCoverFound = true;
					}

				}
				if( !atLeastOneBasicCoverFound ){
					errors.rejectValue( com.Constant.CONST_ERRORMESSAGE, com.Constant.CONST_ERRORMESSAGE_INVALID, "It is mandatory to opt for either Personal Baggage or Emergency Medical Expenses. " );
				}
			}
		}

		Integer royaltyType = travelInsuranceVO.getGeneralInfo().getInsured().getRoyaltyType();
		String guestCardNo = travelInsuranceVO.getGeneralInfo().getInsured().getGuestCardNo();

		if( !Utils.isEmpty( travelInsuranceVO.getGeneralInfo() ) && !Utils.isEmpty( travelInsuranceVO.getGeneralInfo().getInsured() )
				&& ( !Utils.isEmpty( royaltyType ) || !Utils.isEmpty( guestCardNo ) ) && !travelInsuranceVO.isPopulateOperation() ){

			if( !Utils.isEmpty( royaltyType ) && Utils.isEmpty( guestCardNo ) ){
				if( !Utils.getSingleValueAppConfig( "AIRML_NOT_A_MEMBER" ).equals( royaltyType.toString() ) ){
					errors.rejectValue( com.Constant.CONST_ERRORMESSAGE, com.Constant.CONST_ERRORMESSAGE_INVALID, "Please provide your Reward Programme number." );
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

}
