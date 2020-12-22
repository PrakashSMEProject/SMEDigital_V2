       /*
 * THIS IS A GENERATED CLASS. THIS CLASS SHOULD NOT BE EDITED.
 */
package com.rsaame.pas.pojo.mapper;

import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.BeanUtils;
import com.mindtree.ruc.cmn.utils.Utils;

/**
 * Mapper class for:<ol>
 * <li>com.rsaame.pas.dao.model.TTrnMakeClaim</li>
 * <li>com.rsaame.pas.vo.bus.MotorClaimVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( MotorClaimVOToTTrnMakeClaimMapperReverse.class )</code>.
 */
public class MotorClaimVOToTTrnMakeClaimMapperReverse extends BaseBeanToBeanMapper<com.rsaame.pas.dao.model.TTrnMakeClaim, com.rsaame.pas.vo.bus.MotorClaimVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public MotorClaimVOToTTrnMakeClaimMapperReverse(){
		super();
	}

	public MotorClaimVOToTTrnMakeClaimMapperReverse( com.rsaame.pas.dao.model.TTrnMakeClaim src, com.rsaame.pas.vo.bus.MotorClaimVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.MotorClaimVO mapBean(){
		
		log.info("MotorClaimVO To TTrnMakeClaim Mapper started");
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.MotorClaimVO) Utils.newInstance( "com.rsaame.pas.vo.bus.MotorClaimVO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.dao.model.TTrnMakeClaim beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.MotorClaimVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		@SuppressWarnings("unused")
		int noOfItems = 0;
		
		/* Mapping: "macClaimId" -> "claimId" */
		if(  !Utils.isEmpty( beanA.getMacClaimId() )  ){
 			beanB.setClaimId( beanA.getMacClaimId() ); 
 		}

 		/* Mapping: "macPolicyNo" -> "policyNo" */
		if(  !Utils.isEmpty( beanA.getMacPolicyNo() )  ){
			beanB.setPolicyNo( beanA.getMacPolicyNo() );
  		}

 		/* Mapping: "macAccidentDate" -> "dateOfAccident" */
		if(  !Utils.isEmpty( beanA.getMacAccidentDate() )  ){
 			beanB.setDateOfAccident( beanA.getMacAccidentDate() ); 
 		}

 		/* Mapping: "macLossDesc" -> "lossDescription" */
		if(  !Utils.isEmpty( beanA.getMacLossDesc() )  ){
 			beanB.setLossDescription( beanA.getMacLossDesc() ); 
 		}

 		/* Mapping: "macClaimRemarks" -> "remarks" */
		if(  !Utils.isEmpty( beanA.getMacClaimRemarks() )  ){
 			beanB.setRemarks( beanA.getMacClaimRemarks() ); 
 		}

 		/* Mapping: "macInsuredName" -> "insuredVO.name" */
		if(  !Utils.isEmpty( beanA.getMacInsuredName() )  ){
 			beanB.getInsuredVO().setName( beanA.getMacInsuredName() ); 
 		}

 		/* Mapping: "macContactNo" -> "insuredVO.mobileNo" */
		if(  !Utils.isEmpty( beanA.getMacContactNo() )  ){
 			com.rsaame.pas.cmn.converter.BigDecimalStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalStringConverter.class, "", "" );
			beanB.getInsuredVO().setMobileNo( converter.getTypeOfB().cast( converter.getBFromA( beanA.getMacContactNo() ) ) );
  		}

 		/* Mapping: "macEmailId" -> "insuredVO.emailId" */
		if(  !Utils.isEmpty( beanA.getMacEmailId() )  ){
 			beanB.getInsuredVO().setEmailId( beanA.getMacEmailId() ); 
 		}

 		/* Mapping: "macMakeCode" -> "vehicleVO.makeCode" */
		if(  !Utils.isEmpty( beanA.getMacMakeCode() )  ){
 			com.rsaame.pas.cmn.converter.IntegerBigDecimalConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerBigDecimalConverter.class, "", "" );
			beanB.getVehicleVO().setMakeCode( converter.getTypeOfB().cast( converter.getBFromA( beanA.getMacMakeCode() ) ) );
  		}

 		/* Mapping: "macModelCode" -> "vehicleVO.modelCode" */
		if(  !Utils.isEmpty( beanA.getMacModelCode() )  ){
 			com.rsaame.pas.cmn.converter.IntegerBigDecimalConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerBigDecimalConverter.class, "", "" );
			beanB.getVehicleVO().setModelCode( converter.getTypeOfB().cast( converter.getBFromA( beanA.getMacModelCode() ) ) );
  		}

 		/* Mapping: "macVehregNo" -> "modelCode.registrationNo" */
		if(  !Utils.isEmpty( beanA.getMacVehregNo() )  ){
 			beanB.getVehicleVO().setRegistrationNo( beanA.getMacVehregNo() ); 
 		}

 		/* Mapping: "macRepairLoc" -> "vehicleVO.location" */
		if(  !Utils.isEmpty( beanA.getMacRepairLoc() )  ){
 			beanB.getVehicleVO().setLocation( beanA.getMacRepairLoc() ); 
 		}
		log.info("MotorClaimVO To TTrnMakeClaim Mapper completed");
   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.MotorClaimVO initializeDeepVO( com.rsaame.pas.dao.model.TTrnMakeClaim beanA, com.rsaame.pas.vo.bus.MotorClaimVO beanB ){
       		BeanUtils.initializeBeanField( "insuredVO", beanB );
       		BeanUtils.initializeBeanField( "vehicleVO", beanB );
    		return beanB;
	}
}
