       /*
 * THIS IS A GENERATED CLASS. THIS CLASS SHOULD NOT BE EDITED.
 */
package com.rsaame.pas.pojo.mapper;

import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;

/**
 * Mapper class for:<ol>
 * <li>com.rsaame.pas.vo.bus.MotorClaimVO</li>
 * <li>com.rsaame.pas.dao.model.TTrnMakeClaim</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( MotorClaimVOToTTrnMakeClaimMapper.class )</code>.
 */
public class MotorClaimVOToTTrnMakeClaimMapper extends BaseBeanToBeanMapper<com.rsaame.pas.vo.bus.MotorClaimVO, com.rsaame.pas.dao.model.TTrnMakeClaim>{
	
	private final Logger log = Logger.getLogger( this.getClass() );	

	public MotorClaimVOToTTrnMakeClaimMapper(){
		super();
	}

	public MotorClaimVOToTTrnMakeClaimMapper( com.rsaame.pas.vo.bus.MotorClaimVO src, com.rsaame.pas.dao.model.TTrnMakeClaim dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.dao.model.TTrnMakeClaim mapBean(){
		
		log.info("MotorClaimVO To TTrnMakeClaim Mapper started");
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.dao.model.TTrnMakeClaim) Utils.newInstance( "com.rsaame.pas.dao.model.TTrnMakeClaim" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.vo.bus.MotorClaimVO beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.dao.model.TTrnMakeClaim beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		@SuppressWarnings("unused")
		int noOfItems = 0;
		
		/* Mapping: "claimId" -> "macClaimId" */
		if(  !Utils.isEmpty( beanA.getClaimId() )  ){
 			beanB.setMacClaimId( beanA.getClaimId() ); 
 		}

 		/* Mapping: "policyNo" -> "macPolicyNo" */
		if(  !Utils.isEmpty( beanA.getPolicyNo() )  ){
			beanB.setMacPolicyNo( beanA.getPolicyNo().trim() );
  		}

 		/* Mapping: "dateOfAccident" -> "macAccidentDate" */
		if(  !Utils.isEmpty( beanA.getDateOfAccident() )  ){
 			beanB.setMacAccidentDate( beanA.getDateOfAccident() ); 
 		}

 		/* Mapping: "lossDescription" -> "macLossDesc" */
		if(  !Utils.isEmpty( beanA.getLossDescription() )  ){
 			beanB.setMacLossDesc( beanA.getLossDescription().trim() ); 
 		}
		
		//Added for D2C oman
		if(  !Utils.isEmpty( beanA.getVehicleVO() ) && !Utils.isEmpty( beanA.getVehicleVO().getDriverName() )) {
 			beanB.setMacDriverName( beanA.getVehicleVO().getDriverName() ); 
 		}
		
		if(  !Utils.isEmpty( beanA.getVehicleVO() ) && !Utils.isEmpty( beanA.getVehicleVO().getDlNumber() )  ){
 			beanB.setMacDriverLicenseNo( beanA.getVehicleVO().getDlNumber() ); 
 		}
		
		/*if(  !Utils.isEmpty( beanA.getVehicleVO() ) && !Utils.isEmpty( beanA.getVehicleVO().getVehicleMake() )  ){
 			beanB.setMacVehicleMake( beanA.getVehicleVO().getVehicleMake() ); 
 		}*/
		
		if(  !Utils.isEmpty( beanA.getAccidentType() )  ){
 			beanB.setMacAccidentType( beanA.getAccidentType() ); 
 		}
		
		/*if(  !Utils.isEmpty( beanA.getThirdParty() )  ){
 			beanB.setMacTPInvolved( beanA.getThirdParty() ); 
 		}
		
		if(  !Utils.isEmpty( beanA.getTpName() )  && beanA.getThirdParty().equalsIgnoreCase("Y") ){
 			beanB.setMacTPName( beanA.getTpName() ); 
 		}*/
		
		/*if( !Utils.isEmpty( beanA.getVehicleVO() ) && !Utils.isEmpty( beanA.getVehicleVO().getMakeAndModel() ) ){
 			beanB.setMacMakeAndModel( beanA.getVehicleVO().getMakeAndModel() ); 
 		}*/
		//End for D2C oman
		
 		/* Mapping: "remarks" -> "macClaimRemarks" */
		if(  !Utils.isEmpty( beanA.getRemarks() )  ){
 			beanB.setMacClaimRemarks( beanA.getRemarks().trim() ); 
 		}

 		/* Mapping: "insuredVO.name" -> "macInsuredName" */
		if(  !Utils.isEmpty( beanA.getInsuredVO() ) && !Utils.isEmpty( beanA.getInsuredVO().getName() )  ){
 			beanB.setMacInsuredName( beanA.getInsuredVO().getName().trim() ); 
 		}

 		/* Mapping: "insuredVO.mobileNo" -> "macContactNo" */
		if(  !Utils.isEmpty( beanA.getInsuredVO() ) && !Utils.isEmpty( beanA.getInsuredVO().getMobileNo() )  ){
			com.rsaame.pas.cmn.converter.BigDecimalStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalStringConverter.class, "", "" );
			beanB.setMacContactNo( converter.getTypeOfA().cast( converter.getAFromB( beanA.getInsuredVO().getMobileNo().trim() ) ) );
  		}

 		/* Mapping: "insuredVO.emailId" -> "macEmailId" */
		if(  !Utils.isEmpty( beanA.getInsuredVO() ) && !Utils.isEmpty( beanA.getInsuredVO().getEmailId() )  ){
 			beanB.setMacEmailId( beanA.getInsuredVO().getEmailId().trim() ); 
 		}

 		/* Mapping: "vehicleVO.makeCode" -> "macMakeCode" */
		if(  !Utils.isEmpty( beanA.getVehicleVO() ) && !Utils.isEmpty( beanA.getVehicleVO().getMakeCode() )  ){
			com.rsaame.pas.cmn.converter.IntegerBigDecimalConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerBigDecimalConverter.class, "", "" );
			beanB.setMacMakeCode( converter.getTypeOfA().cast( converter.getAFromB( beanA.getVehicleVO().getMakeCode() ) ) );
  		}

 		/* Mapping: "vehicleVO.modelCode" -> "macModelCode" */
		if(  !Utils.isEmpty( beanA.getVehicleVO() ) && !Utils.isEmpty( beanA.getVehicleVO().getModelCode() )  ){
			com.rsaame.pas.cmn.converter.IntegerBigDecimalConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerBigDecimalConverter.class, "", "" );
			beanB.setMacModelCode( converter.getTypeOfA().cast( converter.getAFromB( beanA.getVehicleVO().getModelCode() ) ) );
  		}

 		/* Mapping: "vehicleVO.registrationNo" -> "macVehregNo" */ 
		if(  !Utils.isEmpty( beanA.getVehicleVO() ) && !Utils.isEmpty( beanA.getVehicleVO().getRegistrationNo() ) && !Utils.isEmpty( beanA.getVehicleVO().getRegistrationNo().trim() ) ){
 			beanB.setMacVehregNo( beanA.getVehicleVO().getRegistrationNo() ); 
 		}

 		/* Mapping: "vehicleVO.location" -> "macRepairLoc" */
		if(  !Utils.isEmpty( beanA.getVehicleVO() ) && !Utils.isEmpty( beanA.getVehicleVO().getLocation() )  ){
 			beanB.setMacRepairLoc( beanA.getVehicleVO().getLocation() ); 
 		}
		
		String rootPath = Utils.getSingleValueAppConfig( "FILE_UPLOAD_ROOT" );
		String modulePath = Utils.getSingleValueAppConfig( "FILE_UPLOAD_CLAIMS_FOLDER" );
		String path = Utils.concat( rootPath, "/", Utils.isEmpty( modulePath ) ? "" : modulePath, "/##/" );
		
 		/* Mapping: "vehicleVO.driverLicence" -> "macDriverLicense" */
		if(  !Utils.isEmpty( beanA.getVehicleVO() ) && !Utils.isEmpty( beanA.getVehicleVO().getDriverLicence() )  && (beanA.getVehicleVO().getDriverLicence().getSize() > 0) ){
			String fileName =  beanA.getVehicleVO().getDriverLicence().getOriginalFilename();			
			String extn = fileName.substring( fileName.lastIndexOf( "." ) + 1 );
 			beanB.setMacDriverLicense(path + "DriverLicence." + extn);
 		}
		
 		/* Mapping: "vehicleVO.registartionCard" -> "macRegcardCopy" */
		if(  !Utils.isEmpty( beanA.getVehicleVO() ) && !Utils.isEmpty( beanA.getVehicleVO().getRegistartionCard() )  && (beanA.getVehicleVO().getRegistartionCard().getSize() > 0) ){
			String fileName =  beanA.getVehicleVO().getRegistartionCard().getOriginalFilename();
			String extn = fileName.substring( fileName.lastIndexOf( "." ) + 1 );
 			beanB.setMacRegcardCopy(path + "RegistartionCard." + extn);
 		}
		
 		/* Mapping: "policeReport" -> "macPoliceReport" */
		if(  (!Utils.isEmpty( beanA.getPoliceReport() ))  && (beanA.getPoliceReport().getSize() > 0) ){
			String fileName =  beanA.getPoliceReport().getOriginalFilename();
			String extn = fileName.substring( fileName.lastIndexOf( "." ) + 1 );
 			beanB.setMacPoliceReport(path + "PoliceReport." + extn);
 		}

		//Added for Oman D2C
		/* Mapping: "Mrta form" -> "macMRTAForm" */
		if( (!Utils.isEmpty( beanA.getMrtaForm() )) && (beanA.getMrtaForm().getSize() > 0) ){
			String fileName =  beanA.getMrtaForm().getOriginalFilename();
			String extn = fileName.substring( fileName.lastIndexOf( "." ) + 1 );
 			beanB.setMacMRTAForm( path + "MRTAForm." + extn ); 
 		}
		/* Mapping: "Claim form" -> "macClaimForm" */
		if(  (!Utils.isEmpty( beanA.getClaimForm() )) && (beanA.getClaimForm().getSize() > 0) ){
			String fileName =  beanA.getClaimForm().getOriginalFilename();
			String extn = fileName.substring( fileName.lastIndexOf( "." ) + 1 );
 			beanB.setMacClaimForm( path + "ClaimForm." + extn ); 
 		}
		//End D2C
		
		log.info("MotorClaimVO To TTrnMakeClaim Mapper completed");
		
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.dao.model.TTrnMakeClaim initializeDeepVO( com.rsaame.pas.vo.bus.MotorClaimVO beanA, com.rsaame.pas.dao.model.TTrnMakeClaim beanB ){
                         		return beanB;
	}
}
