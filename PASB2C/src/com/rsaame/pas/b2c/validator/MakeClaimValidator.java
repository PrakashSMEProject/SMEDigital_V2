/**
 * 
 */
package com.rsaame.pas.b2c.validator;

import java.util.List;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.LoginLocation;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.b2c.cmn.constants.AppConstants;
import com.rsaame.pas.vo.bus.MotorClaimVO;

/**
 * @author M1021201
 *
 */
public class MakeClaimValidator implements Validator {
	
	private final static Logger LOGGER = Logger.getLogger(MakeClaimValidator.class);

	@Override
	public boolean supports(Class<?> arg0) {
		return false;
	}

	@Override
	public void validate(Object inputVO, Errors error) {
		
		LOGGER.info("Validation for Make Claim Started");
		
		MotorClaimVO claimVO = (MotorClaimVO) inputVO;
		String fileSize = Utils.getSingleValueAppConfig("MAX_FILE_UPLOAD_SIZE");
		long maxFileSize = !Utils.isEmpty( fileSize ) ? Long.parseLong(fileSize) : 5242880;	//Max file size should be 5 MB
		
		//Added for Oman D2c - start
		LoginLocation location = (LoginLocation) Utils.getBean("location");
		String deployedLocation = location.getLocation();
		if (null != deployedLocation && deployedLocation.equals(AppConstants.LOCATION_CODE) ) {
			long totalUploadedFileSize = 0l;
			if(!Utils.isEmpty( claimVO.getPoliceReport())) {
				totalUploadedFileSize = totalUploadedFileSize + claimVO.getPoliceReport().getSize();
			}
			if(!Utils.isEmpty( claimVO.getVehicleVO().getRegistartionCard())) {
				totalUploadedFileSize = totalUploadedFileSize + claimVO.getVehicleVO().getRegistartionCard().getSize();
			}
			if(!Utils.isEmpty( claimVO.getVehicleVO().getDriverLicence())) {
				totalUploadedFileSize = totalUploadedFileSize + claimVO.getVehicleVO().getDriverLicence().getSize();
			}
			if(!Utils.isEmpty( claimVO.getClaimForm())) {
				totalUploadedFileSize = totalUploadedFileSize + claimVO.getClaimForm().getSize();
			}
			if( totalUploadedFileSize > maxFileSize ){
				error.rejectValue(com.Constant.CONST_ERRORMESSAGE, com.Constant.CONST_ERRORMESSAGE_INVALID, "Total file size cannot exceed 5 MB. Please upload smaller sized files.");
			}
		}else if ( !Utils.isEmpty( claimVO.getPoliceReport() ) && !Utils.isEmpty( claimVO.getVehicleVO().getRegistartionCard() ) 
				&& !Utils.isEmpty( claimVO.getVehicleVO().getDriverLicence() ) ){
			/* Max file size altogether should be less than or equal to 5 MB */
			if( ( claimVO.getPoliceReport().getSize() + claimVO.getVehicleVO().getRegistartionCard().getSize() 
					+ claimVO.getVehicleVO().getDriverLicence().getSize() ) > maxFileSize ){
				error.rejectValue(com.Constant.CONST_ERRORMESSAGE, com.Constant.CONST_ERRORMESSAGE_INVALID, "Total file size cannot exceed 5 MB. Please upload smaller sized files.");
			}
			String FIRextension = claimVO.getPoliceReport().getOriginalFilename().substring( claimVO.getPoliceReport().getOriginalFilename().lastIndexOf( "." ) + 1 );
			String RCextension = claimVO.getVehicleVO().getRegistartionCard().getOriginalFilename().substring( claimVO.getVehicleVO().getRegistartionCard().getOriginalFilename().lastIndexOf( "." ) + 1 );
			String DLextension = claimVO.getVehicleVO().getDriverLicence().getOriginalFilename().substring( claimVO.getVehicleVO().getDriverLicence().getOriginalFilename().lastIndexOf( "." ) + 1 );
			Boolean isFIRextnAllowed = isAllowedExtn(FIRextension);
			Boolean isRCextnAllowed = isAllowedExtn(RCextension);
			Boolean isDLextnAllowed = isAllowedExtn(DLextension);
			if(!(isFIRextnAllowed && isRCextnAllowed && isDLextnAllowed )){
				error.rejectValue(com.Constant.CONST_ERRORMESSAGE, com.Constant.CONST_ERRORMESSAGE_INVALID, "Unexpected type of file uploaded. Please upload files of  pdf, doc, docx, jpg format");
			}
		}
		else{
			error.rejectValue(com.Constant.CONST_ERRORMESSAGE, "Please upload all the files to proceed");
		}
		
		LOGGER.info("Validation for Make Claim completed. Error count - " + error.getErrorCount());
	}
	
	/**
	 * * This method returns true if the particular file type can be uploaded
	 * @param extension
	 * @param param
	 * @return 
	 */
	private boolean isAllowedExtn( String extension ){
		boolean isAllowed = false;
		if( Utils.isEmpty( extension ) ){
			return isAllowed;
		}
		String[] extns = Utils.getMultiValueAppConfig( "B2C_MAKE_CLAIM_FILE_UPLOAD_ALLOWED_EXTNS" );

		if( !Utils.isEmpty( extns ) ){
			Utils.trimAllEntries( extns );
			List<String> extnsList = CopyUtils.asList( extns );
			if( extnsList.contains( extension ) ) isAllowed = true;
		}
		return isAllowed;
	}

}
