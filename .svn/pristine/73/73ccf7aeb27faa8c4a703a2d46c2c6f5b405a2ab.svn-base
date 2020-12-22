       /*
 * THIS IS A GENERATED CLASS. THIS CLASS SHOULD NOT BE EDITED.
 */
package com.rsaame.pas.request.vo.mapper;

import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.beanmap.BeanMapperFactory;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.BeanUtils;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.HTTPUtils;
import com.mindtree.ruc.cmn.utils.Utils;

/**
 * Mapper class for:<ol>
 * <li>javax.servlet.http.HttpServletRequest</li>
 * <li>com.rsaame.pas.vo.bus.MotorFleetVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( RequestToMotorFleetVOMapper.class )</code>.
 */
public class RequestToMotorFleetVOMapper extends BaseBeanToBeanMapper<javax.servlet.http.HttpServletRequest, com.rsaame.pas.vo.bus.MotorFleetVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public RequestToMotorFleetVOMapper(){
		super();
	}

	public RequestToMotorFleetVOMapper( javax.servlet.http.HttpServletRequest src, com.rsaame.pas.vo.bus.MotorFleetVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.MotorFleetVO mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.MotorFleetVO) Utils.newInstance( "com.rsaame.pas.vo.bus.MotorFleetVO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		javax.servlet.http.HttpServletRequest beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.MotorFleetVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;
		
		/* Mapping: "commission" -> "commission" */
		if( !Utils.isEmpty( src.getParameter( "commission" ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.setCommission( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "commission" ) ) ) );
  		}

 		/* Mapping: "vehicleYearOfMake" -> "vehicleDetails[].vehicleYearOfMake" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "vehicleYearOfMake" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			beanB.getVehicleDetails().get( i ).setVehicleYearOfMake (  beanA.getParameter( "vehicleYearOfMake[" + i + "]" ) );
		}

 		/* Mapping: "vehicleModel" -> "vehicleDetails[].vehicleModel" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "vehicleModel" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			beanB.getVehicleDetails().get( i ).setVehicleModel (  beanA.getParameter( "vehicleModel[" + i + "]" ) );
		}

 		/* Mapping: "vehicleCategory" -> "vehicleDetails[].vehicleCategory" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "vehicleCategory" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getVehicleDetails().get( i ).setVehicleCategory ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "vehicleCategory[" + i + "]" ) ) ) );
 		}

 		/* Mapping: "vehicleYearofMfg" -> "vehicleDetails[].vehicleYearofMfg" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "vehicleYearofMfg" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			beanB.getVehicleDetails().get( i ).setVehicleYearofMfg (  beanA.getParameter( "vehicleYearofMfg[" + i + "]" ) );
		}

 		/* Mapping: "vehicleIEV" -> "vehicleDetails[].vehicleIEV" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "vehicleIEV" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			//beanB.getVehicleDetails().get( i ).setVehicleIEV ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "vehicleIEV[" + i + "]" ) ) ) );
 		}

 		/* Mapping: "vehicleExcess" -> "vehicleDetails[].vehicleExcess" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "vehicleExcess" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getVehicleDetails().get( i ).setVehicleExcess ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "vehicleExcess[" + i + "]" ) ) ) );
 		}

 		/* Mapping: "vehicleDealerOption" -> "vehicleDetails[].vehicleDealerOption" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "vehicleDealerOption" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			beanB.getVehicleDetails().get( i ).setVehicleDealerOption (  beanA.getParameter( "vehicleDealerOption[" + i + "]" ) );
		}

 		/* Mapping: "vehicleSeatCapacity" -> "vehicleDetails[].vehicleSeatCapacity" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "vehicleSeatCapacity" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getVehicleDetails().get( i ).setVehicleSeatCapacity ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "vehicleSeatCapacity[" + i + "]" ) ) ) );
 		}

 		/* Mapping: "vehiclePABD" -> "covrageDetails[].vehiclePABD" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "vehiclePABD" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			com.rsaame.pas.cmn.converter.CharacterStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.CharacterStringConverter.class, "", "" );
			beanB.getCovrageDetails().get( i ).setVehiclePABD ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "vehiclePABD[" + i + "]" ) ) ) );
 		}

 		/* Mapping: "vehiclePABP" -> "covrageDetails[].vehiclePABP" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "vehiclePABP" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			com.rsaame.pas.cmn.converter.CharacterStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.CharacterStringConverter.class, "", "" );
			beanB.getCovrageDetails().get( i ).setVehiclePABP ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "vehiclePABP[" + i + "]" ) ) ) );
 		}

 		/* Mapping: "vehicleLossofuse" -> "covrageDetails[].vehicleLossofuse" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "vehicleLossofuse" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			com.rsaame.pas.cmn.converter.CharacterStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.CharacterStringConverter.class, "", "" );
			beanB.getCovrageDetails().get( i ).setVehicleLossofuse ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "vehicleLossofuse[" + i + "]" ) ) ) );
 		}

 		/* Mapping: "vehicleGeographicalExtension" -> "covrageDetails[].vehicleGeographicalExtension" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "vehicleGeographicalExtension" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			com.rsaame.pas.cmn.converter.CharacterStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.CharacterStringConverter.class, "", "" );
			beanB.getCovrageDetails().get( i ).setVehicleGeographicalExtension ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "vehicleGeographicalExtension[" + i + "]" ) ) ) );
 		}

 		/* Mapping: "vehicleGeographicalPlace" -> "covrageDetails[].vehicleGeographicalPlace[]" 
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "vehicleGeographicalPlace" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			com.rsaame.pas.cmn.converter.CharacterStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.CharacterStringConverter.class, "", "" );
			beanB.getCovrageDetails().get( i ).setVehicleGeographicalPlace ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "vehicleGeographicalPlace[" + i + "]" ) ) ) );
 		}*/

 		/* Mapping: "vehicleEngineNo" -> "additionalVehicleDetails[].vehicleEngineNo" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "vehicleEngineNo" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			beanB.getAdditionalVehicleDetails().get( i ).setVehicleEngineNo (  beanA.getParameter( "vehicleEngineNo[" + i + "]" ) );
		}

 		/* Mapping: "vehicleChassisNo" -> "additionalVehicleDetails[].vehicleChassisNo" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "vehicleChassisNo" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			beanB.getAdditionalVehicleDetails().get( i ).setVehicleChassisNo (  beanA.getParameter( "vehicleChassisNo[" + i + "]" ) );
		}

 		/* Mapping: "vehicleRegnTxt" -> "additionalVehicleDetails[].vehicleRegnTxt" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "vehicleRegnTxt" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			beanB.getAdditionalVehicleDetails().get( i ).setVehicleRegnTxt (  beanA.getParameter( "vehicleRegnTxt[" + i + "]" ) );
		}

 		/* Mapping: "vehicleRegnNo" -> "additionalVehicleDetails[].vehicleRegnNo" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "vehicleRegnNo" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getAdditionalVehicleDetails().get( i ).setVehicleRegnNo ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "vehicleRegnNo[" + i + "]" ) ) ) );
 		}

 		/* Mapping: "vehicleTCFNo" -> "additionalVehicleDetails[].vehicleTCFNo" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "vehicleTCFNo" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			beanB.getAdditionalVehicleDetails().get( i ).setVehicleTCFNo (  beanA.getParameter( "vehicleTCFNo[" + i + "]" ) );
		}

 		/* Mapping: "vehicleCylinder" -> "additionalVehicleDetails[].vehicleCylinder" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "vehicleCylinder" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getAdditionalVehicleDetails().get( i ).setVehicleCylinder ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "vehicleCylinder[" + i + "]" ) ) ) );
 		}

 		/* Mapping: "vehicleOriginalCost" -> "additionalVehicleDetails[].vehicleOriginalCost" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "vehicleOriginalCost" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.getAdditionalVehicleDetails().get( i ).setVehicleOriginalCost ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "vehicleOriginalCost[" + i + "]" ) ) ) );
 		}

 		/* Mapping: "vehicleDateofRegn" -> "additionalVehicleDetails[].vehicleDateofRegn" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "vehicleDateofRegn" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			beanB.getAdditionalVehicleDetails().get( i ).setVehicleDateofRegn (  beanA.getParameter( "vehicleDateofRegn[" + i + "]" ) );
		}

 		/* Mapping: "vehicleBody" -> "additionalVehicleDetails[].vehicleBody" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "vehicleBody" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getAdditionalVehicleDetails().get( i ).setVehicleBody ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "vehicleBody[" + i + "]" ) ) ) );
 		}

 		/* Mapping: "vehicleTonnage" -> "additionalVehicleDetails[].vehicleTonnage" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "vehicleTonnage" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getAdditionalVehicleDetails().get( i ).setVehicleTonnage ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "vehicleTonnage[" + i + "]" ) ) ) );
 		}

 		/* Mapping: "vehicleEnColor" -> "additionalVehicleDetails[].vehicleEnColor" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "vehicleEnColor" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getAdditionalVehicleDetails().get( i ).setVehicleEnColor ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "vehicleEnColor[" + i + "]" ) ) ) );
 		}

 		/* Mapping: "vehiclePlaceofRegn" -> "additionalVehicleDetails[].vehiclePlaceofRegn" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "vehiclePlaceofRegn" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			beanB.getAdditionalVehicleDetails().get( i ).setVehiclePlaceofRegn (  beanA.getParameter( "vehiclePlaceofRegn[" + i + "]" ) );
		}

 		/* Mapping: "vehiclePA" -> "additionalVehicleDetails[].vehiclePA" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "vehiclePA" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getAdditionalVehicleDetails().get( i ).setVehiclePA ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "vehiclePA[" + i + "]" ) ) ) );
 		}

 		/* Mapping: "vehiclePL" -> "additionalVehicleDetails[].vehiclePL" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "vehiclePL" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getAdditionalVehicleDetails().get( i ).setVehiclePL ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "vehiclePL[" + i + "]" ) ) ) );
 		}

 		/* Mapping: "vehicleCertNo" -> "additionalVehicleDetails[].vehicleCertNo" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "vehicleCertNo" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			beanB.getAdditionalVehicleDetails().get( i ).setVehicleCertNo (  beanA.getParameter( "vehicleCertNo[" + i + "]" ) );
		}

 		/* Mapping: "vehicleCertStartDate" -> "additionalVehicleDetails[].vehicleCertStartDate" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "vehicleCertStartDate" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			beanB.getAdditionalVehicleDetails().get( i ).setVehicleCertStartDate (  beanA.getParameter( "vehicleCertStartDate[" + i + "]" ) );
		}

 		/* Mapping: "vehicleCertEndDate" -> "additionalVehicleDetails[].vehicleCertEndDate" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "vehicleCertEndDate" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			beanB.getAdditionalVehicleDetails().get( i ).setVehicleCertEndDate (  beanA.getParameter( "vehicleCertEndDate[" + i + "]" ) );
		}

 		/* Mapping: "vehicleHirePurchase" -> "additionalVehicleDetails[].vehicleHirePurchase" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "vehicleHirePurchase" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			beanB.getAdditionalVehicleDetails().get( i ).setVehicleHirePurchase (  beanA.getParameter( "vehicleHirePurchase[" + i + "]" ) );
		}

 		/* Mapping: "vehicleArColor" -> "additionalVehicleDetails[].vehicleArColor" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "vehicleArColor" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			beanB.getAdditionalVehicleDetails().get( i ).setVehicleArColor (  beanA.getParameter( "vehicleArColor[" + i + "]" ) );
		}

 		/* Mapping: "vehicleUse" -> "additionalVehicleDetails[].vehicleUse" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "vehicleUse" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getAdditionalVehicleDetails().get( i ).setVehicleUse ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "vehicleUse[" + i + "]" ) ) ) );
 		}

 		/* Mapping: "vehicleArMakeandModel" -> "additionalVehicleDetails[].vehicleArMakeandModel" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "vehicleArMakeandModel" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			beanB.getAdditionalVehicleDetails().get( i ).setVehicleArMakeandModel (  beanA.getParameter( "vehicleArMakeandModel[" + i + "]" ) );
		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.MotorFleetVO initializeDeepVO( javax.servlet.http.HttpServletRequest beanA, com.rsaame.pas.vo.bus.MotorFleetVO beanB ){
    		BeanUtils.initializeBeanField( "vehicleDetails[]", beanB, HTTPUtils.getMatchingMultiReqParamKeys( beanA, "vehicleYearOfMake[]" ).size() );
                 		BeanUtils.initializeBeanField( "covrageDetails[]", beanB, HTTPUtils.getMatchingMultiReqParamKeys( beanA, "vehiclePABD[]" ).size() );
         		BeanUtils.initializeBeanField( "covrageDetails[].vehicleGeographicalPlace[]", beanB, HTTPUtils.getMatchingMultiReqParamKeys( beanA, "vehicleGeographicalPlace[]" ).size() );
   		BeanUtils.initializeBeanField( "additionalVehicleDetails[]", beanB, HTTPUtils.getMatchingMultiReqParamKeys( beanA, "vehicleEngineNo[]" ).size() );
                                          		return beanB;
	}
}
