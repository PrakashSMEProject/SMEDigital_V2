       /*
 * THIS IS A GENERATED CLASS. THIS CLASS SHOULD NOT BE EDITED.
 */
package com.rsaame.pas.request.vo.mapper;

import java.math.BigDecimal;

import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.BeanUtils;
import com.mindtree.ruc.cmn.utils.Utils;

/**
 * Mapper class for:<ol>
 * <li>com.rsaame.pas.vo.bus.BuildingDetailsVO</li>
 * <li>com.rsaame.pas.dao.model.TTrnBuildingQuo</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( BuildingDetailsVOToTTrnBuildingQuoMapper.class )</code>.
 */
public class BuildingDetailsVOToTTrnBuildingQuoMapper extends BaseBeanToBeanMapper<com.rsaame.pas.vo.bus.BuildingDetailsVO, com.rsaame.pas.dao.model.TTrnBuildingQuo>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public BuildingDetailsVOToTTrnBuildingQuoMapper(){
		super();
	}

	public BuildingDetailsVOToTTrnBuildingQuoMapper( com.rsaame.pas.vo.bus.BuildingDetailsVO src, com.rsaame.pas.dao.model.TTrnBuildingQuo dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.dao.model.TTrnBuildingQuo mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.dao.model.TTrnBuildingQuo) Utils.newInstance( "com.rsaame.pas.dao.model.TTrnBuildingQuo" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.vo.bus.BuildingDetailsVO beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.dao.model.TTrnBuildingQuo beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;
		
		/* Mapping: "riskCodes.basicRskId" -> "id.bldId" */
		if(  !Utils.isEmpty( beanA.getRiskCodes() ) && !Utils.isEmpty( beanA.getRiskCodes().getBasicRskId() )  ){
 			com.rsaame.pas.cmn.converter.BigDecimalLongConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalLongConverter.class, "", "" );
			beanB.getId().setBldId( converter.getTypeOfB().cast( converter.getBFromA( beanA.getRiskCodes().getBasicRskId() ) ) );
  		}

 		/* Mapping: "riskCodes.rskId" -> "id.bldId" */
		if(  !Utils.isEmpty( beanA.getRiskCodes() ) && !Utils.isEmpty( beanA.getRiskCodes().getRskId() )  ){
 			com.rsaame.pas.cmn.converter.BigDecimalLongConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalLongConverter.class, "", "" );
			beanB.getId().setBldId( converter.getTypeOfB().cast( converter.getBFromA( beanA.getRiskCodes().getRskId() ) ) );
  		}

 		/* Mapping: "riskCodes.riskCode" -> "bldRskCode" */
		if(  !Utils.isEmpty( beanA.getRiskCodes() ) && !Utils.isEmpty( beanA.getRiskCodes().getRiskCode() )  ){
 			beanB.setBldRskCode( beanA.getRiskCodes().getRiskCode() ); 
 		}

 		/* Mapping: "flatVillaNo" -> "bldAAddress1" */
		if(  !Utils.isEmpty( beanA.getFlatVillaNo() )  ){
 			beanB.setBldEAddress1( beanA.getFlatVillaNo() ); 
 		}

 		/* Mapping: "area" -> "bldEAddress1" */
		if(  !Utils.isEmpty( beanA.getArea() ) && !("Others").equals( beanA.getArea() ) && !("999").equals( beanA.getArea() )){
 			beanB.setBldEAddress2( beanA.getArea() ); 
 		}

 		/* Mapping: "otherDetails" -> "bldEAddress2" */
		if(  !Utils.isEmpty( beanA.getOtherDetails() )   ){
 			beanB.setBldEAddress2( beanA.getOtherDetails() ); 
 		}

 		/* Mapping: "emirates" -> "bldEAddress3" */
		if(  !Utils.isEmpty( beanA.getEmirates() )  ){
 			beanB.setBldEAddress3( beanA.getEmirates() ); 
 		}

 		/* Mapping: "sumInsured.sumInsured" -> "bldSumInsured" */
		if(  !Utils.isEmpty( beanA.getSumInsured() ) && !Utils.isEmpty( beanA.getSumInsured().getSumInsured() )  ){
			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.setBldSumInsured( converter.getTypeOfA().cast( converter.getAFromB( beanA.getSumInsured().getSumInsured() ) ) );
  		}

 		/* Mapping: "riskCodes.riskType" -> "bldRskType" */
		if(  !Utils.isEmpty( beanA.getRiskCodes() ) && !Utils.isEmpty( beanA.getRiskCodes().getRiskType() )  ){
 			beanB.setBldRskType( beanA.getRiskCodes().getRiskType() ); 
 		}

 		/* Mapping: "buildingname" -> "bldEName" */
		if(  !Utils.isEmpty( beanA.getBuildingname() )  ){
 			beanB.setBldEName( beanA.getBuildingname() ); 
 		}

		/* Mapping: "ownerShipStatus" -> "bldOwnerShipStatus" */
		if( !Utils.isEmpty( beanA.getOwnershipStatus() ) ){
			beanB.setBldOwnershipStatus( beanA.getOwnershipStatus() );
		}

 		/* Mapping: "mortgageeName" -> "bldMorgatgeeName" */
		if(  !Utils.isEmpty( beanA.getMortgageeName() )  ){
 			beanB.setBldMorgatgeeName( beanA.getMortgageeName() ); 
 		}
		/* Mapping: "typeOfProperty" -> "bldOccupancyCode" */

		if( !Utils.isEmpty( beanA.getTypeOfProperty() )){
		beanB.setBldOccupancyCode( beanA.getTypeOfProperty() );
		}
		
		

		/* Mapping: "sumInsured.sumInsured" -> "bldSumInsured" */

		if(!Utils.isEmpty( beanA.getSumInsured().getSumInsured() )){
		beanB.setBldSumInsured( new BigDecimal( beanA.getSumInsured().getSumInsured() ) );
		}

		/* Mapping: "sumInsured.sumInsured" -> "bldMplFire"*/

		if(!Utils.isEmpty( beanA.getSumInsured().getSumInsured() )){
		beanB.setBldMplFire( new BigDecimal( beanA.getSumInsured().getSumInsured() ) );
		}
		
		/* Mapping: "geoAreaCode" -> "bldGeoareaCode"*/

		if(!Utils.isEmpty( beanA.getGeoAreaCode() )){
			beanB.setBldGeoareaCode( beanA.getGeoAreaCode() );
		}
		
		/* Mapping: "vsd" -> "bldValidityStartDate" */
		if(!Utils.isEmpty( beanA.getVsd() )){
			beanB.getId().setBldValidityStartDate( beanA.getVsd() );
		}
		
		if( !Utils.isEmpty( beanA.getRiRskCode() ) ){
			beanB.setBldRiRskCode( beanA.getRiRskCode() );
		}
		
		//Added for Informap changes
		if( !Utils.isEmpty( beanA.getLatitude() ) ){
			beanB.setBldLatitude( BigDecimal.valueOf(beanA.getLatitude()) );
		}
		if( !Utils.isEmpty( beanA.getLongitude() ) ){
			beanB.setBldLongitude( BigDecimal.valueOf(beanA.getLongitude()) );
		}
		if( !Utils.isEmpty( beanA.getAddress() ) ){
			beanB.setBldAName( beanA.getAddress() );
		}
		if( !Utils.isEmpty( beanA.getInforMapStatus() ) ){
			beanB.setBldInforMapStatus( beanA.getInforMapStatus() );
		}
		// 11645 - Home Digital API
		/* Mapping: "TotalNoFloors" -> "TotalNoFloors" */
		if (!Utils.isEmpty(beanA.getTotalNoFloors())) {
			beanB.setBldTotalNoFloors(beanA.getTotalNoFloors());
		}

		/* Mapping: "TotalNoRooms" -> "TotalNoRooms" */
		if (!Utils.isEmpty(beanA.getTotalNoRooms())) {
			beanB.setBldTotalNoRooms(beanA.getTotalNoRooms());
		}
   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.dao.model.TTrnBuildingQuo initializeDeepVO( com.rsaame.pas.vo.bus.BuildingDetailsVO beanA, com.rsaame.pas.dao.model.TTrnBuildingQuo beanB ){
  		BeanUtils.initializeBeanField( "id", beanB );
                        		return beanB;
	}
}
