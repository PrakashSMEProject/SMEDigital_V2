       /*
 * THIS IS A GENERATED CLASS. THIS CLASS SHOULD NOT BE EDITED.
 */
package com.rsaame.pas.request.vo.mapper;

import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.BeanUtils;
import com.mindtree.ruc.cmn.utils.Utils;

/**
 * Mapper class for:<ol>
 * <li>com.rsaame.pas.dao.model.TTrnBuildingQuo</li>
 * <li>com.rsaame.pas.vo.bus.BuildingDetailsVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( BuildingDetailsVOToTTrnBuildingQuoMapperReverse.class )</code>.
 */
public class BuildingDetailsVOToTTrnBuildingQuoMapperReverse extends BaseBeanToBeanMapper<com.rsaame.pas.dao.model.TTrnBuildingQuo, com.rsaame.pas.vo.bus.BuildingDetailsVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public BuildingDetailsVOToTTrnBuildingQuoMapperReverse(){
		super();
	}

	public BuildingDetailsVOToTTrnBuildingQuoMapperReverse( com.rsaame.pas.dao.model.TTrnBuildingQuo src, com.rsaame.pas.vo.bus.BuildingDetailsVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.BuildingDetailsVO mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.BuildingDetailsVO) Utils.newInstance( "com.rsaame.pas.vo.bus.BuildingDetailsVO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.dao.model.TTrnBuildingQuo beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.BuildingDetailsVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;
		
		/* Mapping: "id.bldId" -> "riskCodes.basicRskId" */
		/*if(  !Utils.isEmpty( beanA.getId() ) && !Utils.isEmpty( beanA.getId().getBldId() )  ){
 			com.rsaame.pas.cmn.converter.BigDecimalLongConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalLongConverter.class, "", "" );
			beanB.getRiskCodes().setBasicRskId( converter.getTypeOfA().cast( converter.getAFromB( beanA.getId().getBldId() ) ) );
  		}*/

 		/* Mapping: "id.bldId" -> "riskCodes.rskId" */
		if(  !Utils.isEmpty( beanA.getId() ) && !Utils.isEmpty( beanA.getId().getBldId() )  ){
 			com.rsaame.pas.cmn.converter.BigDecimalLongConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalLongConverter.class, "", "" );
			beanB.getRiskCodes().setRskId( converter.getTypeOfA().cast( converter.getAFromB( beanA.getId().getBldId() ) ) );
  		}

 		/* Mapping: "bldRskCode" -> "riskCodes.riskCode" */
		if(  !Utils.isEmpty( beanA.getBldRskCode() )  ){
 			beanB.getRiskCodes().setRiskCode( beanA.getBldRskCode() ); 
 		}

 		/* Mapping: "bldEAddress1" -> "flatVillaNo" */
		if(  !Utils.isEmpty( beanA.getBldEAddress1() )  ){
 			beanB.setFlatVillaNo( beanA.getBldEAddress1() ); 
 		}

 		/* Mapping: "bldEAddress2" -> "otherDetails & area" */
		if(  !Utils.isEmpty( beanA.getBldEAddress2() )  ){
			/*String eAddress = beanA.getBldEAddress2();
			String [] addressArray = eAddress.split( "," );
			if(addressArray.length > 1){
				//beanB.setOtherDetails(addressArray[1]); 
				beanB.setOtherDetails( eAddress.substring( eAddress.indexOf( "," ) + 1 )); 
			}
			beanB.setArea( addressArray[0]);*/
			beanB.setArea( beanA.getBldEAddress2() );
			beanB.setOtherDetails( beanA.getBldEAddress2() );
 		}

 		/* Mapping: "bldEAddress3" -> "emirates" */
		if(  !Utils.isEmpty( beanA.getBldEAddress3() )  ){
 			beanB.setEmirates( beanA.getBldEAddress3() ); 
 		}

 		/* Mapping: "bldSumInsured" -> "sumInsured.sumInsured" */
		if(  !Utils.isEmpty( beanA.getBldSumInsured() )  ){
			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.getSumInsured().setSumInsured( converter.getTypeOfB().cast( converter.getBFromA( beanA.getBldSumInsured() ) ) );
  		}

 		/* Mapping: "bldRskType" -> "riskCodes.riskType" */
		if(  !Utils.isEmpty( beanA.getBldRskType() )  ){
 			beanB.getRiskCodes().setRiskType( beanA.getBldRskType() ); 
 		}

 		/* Mapping: "bldEName" -> "buildingname" */
		if(  !Utils.isEmpty( beanA.getBldEName() )  ){
 			beanB.setBuildingname( beanA.getBldEName() ); 
 		}

		/* Mapping : "BldOwnershipStatus" -> "OwnershipStatus" */
		if( !Utils.isEmpty( beanA.getBldOwnershipStatus() ) ){
			beanB.setOwnershipStatus( beanA.getBldOwnershipStatus() ); 

		}

 		/* Mapping: "bldMorgatgeeName" -> "mortgageeName" */
		if(  !Utils.isEmpty( beanA.getBldMorgatgeeName() )  ){
 			beanB.setMortgageeName( beanA.getBldMorgatgeeName() ); 
 		}
		
		/* Mapping: "bldOccupancyCode" -> "typeOfProperty" */

		if( !Utils.isEmpty( beanA.getBldOccupancyCode() ) ){
		beanB.setTypeOfProperty( beanA.getBldOccupancyCode()); 
		}

        		
		/* Mapping: "BldSumInsured" -> "sumInsured.sumInsured" */
		if( !Utils.isEmpty( beanA.getBldSumInsured() ) ){
		beanB.getSumInsured().setSumInsured( beanA.getBldSumInsured().doubleValue()); 
		}

		/* Mapping: "bldGeoAreaCode" -> "geoAreaCode" */
		if( !Utils.isEmpty( beanA.getBldGeoareaCode()) ){
			beanB.setGeoAreaCode( beanA.getBldGeoareaCode()); 
		}
		
		/* Mapping: "bldValidityStartDate -> "vsd" */
		if( !Utils.isEmpty( beanA.getId().getBldValidityStartDate() )){
			beanB.setVsd( beanA.getId().getBldValidityStartDate() );
		}
		
		if( !Utils.isEmpty( beanA.getBldRiRskCode() )){
			beanB.setRiRskCode( beanA.getBldRiRskCode() );
		}
		//Added for Informap changes
				if( !Utils.isEmpty( beanA.getBldLatitude() ) ){
					beanB.setLatitude( (beanA.getBldLatitude()).doubleValue()) ;
				}
				if( !Utils.isEmpty( beanA.getBldLongitude() ) ){
					beanB.setLongitude((beanA.getBldLongitude()).doubleValue() );
				}
				if( !Utils.isEmpty( beanA.getBldAName() ) ){
					beanB.setAddress( beanA.getBldAName() );
				}
				if( !Utils.isEmpty( beanA.getBldInforMapStatus() ) ){
					beanB.setInforMapStatus( beanA.getBldInforMapStatus() );
				}
		// 11645 - Home Digital API
		if (!Utils.isEmpty(beanA.getBldTotalNoFloors())) {
			beanB.setTotalNoFloors(beanA.getBldTotalNoFloors());
		}
		if (!Utils.isEmpty(beanA.getBldTotalNoRooms())) {
			beanB.setTotalNoRooms(beanA.getBldTotalNoRooms());
		}
   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.BuildingDetailsVO initializeDeepVO( com.rsaame.pas.dao.model.TTrnBuildingQuo beanA, com.rsaame.pas.vo.bus.BuildingDetailsVO beanB ){
  		BeanUtils.initializeBeanField( "riskCodes", beanB );
               		BeanUtils.initializeBeanField( "sumInsured", beanB );
          		return beanB;
	}
}
