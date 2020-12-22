       /*
 * THIS IS A GENERATED CLASS. THIS CLASS SHOULD NOT BE EDITED.
 */
package com.rsaame.pas.pojo.mapper;

import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.beanmap.BeanMapperFactory;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.BeanUtils;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.Utils;

/**
 * Mapper class for:<ol>
 * <li>com.rsaame.pas.dao.model.TTrnBuildingQuo</li>
 * <li>com.rsaame.pas.dao.model.TTrnGaccBuildingQuo</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( BuildingToGACCBuildingMapper.class )</code>.
 */
public class BuildingToGACCBuildingMapper extends BaseBeanToBeanMapper<com.rsaame.pas.dao.model.TTrnBuildingQuo, com.rsaame.pas.dao.model.TTrnGaccBuildingQuo>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public BuildingToGACCBuildingMapper(){
		super();
	}

	public BuildingToGACCBuildingMapper( com.rsaame.pas.dao.model.TTrnBuildingQuo src, com.rsaame.pas.dao.model.TTrnGaccBuildingQuo dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.dao.model.TTrnGaccBuildingQuo mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.dao.model.TTrnGaccBuildingQuo) Utils.newInstance( "com.rsaame.pas.dao.model.TTrnGaccBuildingQuo" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.dao.model.TTrnBuildingQuo beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.dao.model.TTrnGaccBuildingQuo beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		//beanB = initializeDeepVO(beanA, beanB);

		/* Mapping: "id.bldValidityStartDate" -> "id.gbdValidityStartDate" */
		/*if(  !Utils.isEmpty( beanA.getId() ) && !Utils.isEmpty( beanA.getId().getBldValidityStartDate() )  ){
 			beanB.getId().setGbdValidityStartDate( beanA.getId().getBldValidityStartDate() ); 
 		}*/

 		/* Mapping: "bldRskCode" -> "gbdRskCode" */
		if(  !Utils.isEmpty( beanA.getBldRskCode() )  ){
 			com.rsaame.pas.cmn.converter.LongIntegerConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongIntegerConverter.class, "", "" );
			beanB.setGbdRskCode( converter.getTypeOfB().cast( converter.getBFromA( beanA.getBldRskCode() ) ) );
  		}

 		/* Mapping: "bldConstructionCode" -> "gbdConstructionCode" */
		if(  !Utils.isEmpty( beanA.getBldConstructionCode() )  ){
 			com.rsaame.pas.cmn.converter.ShortLongConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.ShortLongConverter.class, "", "" );
			beanB.setGbdConstructionCode( converter.getTypeOfB().cast( converter.getBFromA( beanA.getBldConstructionCode() ) ) );
  		}

 		/* Mapping: "bldStartDate" -> "gbdStartDate" */
		if(  !Utils.isEmpty( beanA.getBldStartDate() )  ){
 			beanB.setGbdStartDate( beanA.getBldStartDate() ); 
 		}

 		/* Mapping: "bldEndDate" -> "gbdEndDate" */
		if(  !Utils.isEmpty( beanA.getBldEndDate() )  ){
 			beanB.setGbdEndDate( beanA.getBldEndDate() ); 
 		}

 		/* Mapping: "bldNo" -> "gbdNo" */
		if(  !Utils.isEmpty( beanA.getBldNo() )  ){
 			beanB.setGbdNo( beanA.getBldNo() ); 
 		}

 		/* Mapping: "bldEName" -> "gbdEName" */
		if(  !Utils.isEmpty( beanA.getBldEName() )  ){
 			beanB.setGbdEName( beanA.getBldEName() ); 
 		}

 		/* Mapping: "bldAName" -> "gbdAName" */
		if(  !Utils.isEmpty( beanA.getBldAName() )  ){
 			beanB.setGbdAName( beanA.getBldAName() ); 
 		}

 		/* Mapping: "bldWayNo" -> "gbdWayNo" */
		if(  !Utils.isEmpty( beanA.getBldWayNo() )  ){
 			beanB.setGbdWayNo( beanA.getBldWayNo() ); 
 		}

 		/* Mapping: "bldEStreetName" -> "gbdEStreetName" */
		if(  !Utils.isEmpty( beanA.getBldEStreetName() )  ){
 			beanB.setGbdEStreetName( beanA.getBldEStreetName() ); 
 		}

 		/* Mapping: "bldAStreetName" -> "gbdAStreetName" */
		if(  !Utils.isEmpty( beanA.getBldAStreetName() )  ){
 			beanB.setGbdAStreetName( beanA.getBldAStreetName() ); 
 		}

 		/* Mapping: "bldBlockNo" -> "gbdBlockNo" */
		if(  !Utils.isEmpty( beanA.getBldBlockNo() )  ){
 			beanB.setGbdBlockNo( beanA.getBldBlockNo() ); 
 		}

 		/* Mapping: "bldDirCode" -> "gbdDirCode" */
		if(  !Utils.isEmpty( beanA.getBldDirCode() )  ){
 			com.rsaame.pas.cmn.converter.LongIntegerConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongIntegerConverter.class, "", "" );
			beanB.setGbdDirCode( converter.getTypeOfB().cast( converter.getBFromA( beanA.getBldDirCode() ) ) );
  		}

 		/* Mapping: "bldMunCode" -> "gbdMunCode" */
		if(  !Utils.isEmpty( beanA.getBldMunCode() )  ){
 			com.rsaame.pas.cmn.converter.LongIntegerConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongIntegerConverter.class, "", "" );
			beanB.setGbdMunCode( converter.getTypeOfB().cast( converter.getBFromA( beanA.getBldMunCode() ) ) );
  		}

 		/* Mapping: "bldAAddress1" -> "gbdAAddress1" */
		if(  !Utils.isEmpty( beanA.getBldAAddress1() )  ){
 			beanB.setGbdAAddress1( beanA.getBldAAddress1() ); 
 		}

 		/* Mapping: "bldEAddress1" -> "gbdEAddress1" */
		if(  !Utils.isEmpty( beanA.getBldEAddress1() )  ){
 			beanB.setGbdEAddress1( beanA.getBldEAddress1() ); 
 		}

 		/* Mapping: "bldZip" -> "gbdZip" */
		if(  !Utils.isEmpty( beanA.getBldZip() )  ){
 			beanB.setGbdZip( beanA.getBldZip() ); 
 		}

 		/* Mapping: "bldSumInsured" -> "gbdSumInsured" */
		/* Commented as PAR sum insured should not be copied to MONEY */
//		if(  !Utils.isEmpty( beanA.getBldSumInsured() )  ){
// 			beanB.setGbdSumInsured( beanA.getBldSumInsured() ); 
// 		}

 		/* Mapping: "bldStatus" -> "gbdStatus" */
		if(  !Utils.isEmpty( beanA.getBldStatus() )  ){
 			beanB.setGbdStatus( beanA.getBldStatus() ); 
 		}

 		/* Mapping: "bldValidityExpiryDate" -> "gbdValidityExpiryDate" */
		if(  !Utils.isEmpty( beanA.getBldValidityExpiryDate() )  ){
 			beanB.setGbdValidityExpiryDate( beanA.getBldValidityExpiryDate() ); 
 		}

 		/* Mapping: "bldEndtId" -> "gbdEndtId" */
		if(  !Utils.isEmpty( beanA.getBldEndtId() )  ){
 			beanB.setGbdEndtId( beanA.getBldEndtId() ); 
 		}

 		/* Mapping: "bldRiRskCode" -> "gbdRiRskCode" */
		if(  !Utils.isEmpty( beanA.getBldRiRskCode() )  ){
 			beanB.setGbdRiRskCode( beanA.getBldRiRskCode() ); 
 		}

 		/* Mapping: "bldPreparedBy" -> "gbdPreparedBy" */
		if(  !Utils.isEmpty( beanA.getBldPreparedBy() )  ){
 			beanB.setGbdPreparedBy( beanA.getBldPreparedBy() ); 
 		}

 		/* Mapping: "bldPreparedDt" -> "gbdPreparedDt" */
		if(  !Utils.isEmpty( beanA.getBldPreparedDt() )  ){
 			beanB.setGbdPreparedDt( beanA.getBldPreparedDt() ); 
 		}

 		/* Mapping: "bldModifiedBy" -> "gbdModifiedBy" */
		if(  !Utils.isEmpty( beanA.getBldModifiedBy() )  ){
 			beanB.setGbdModifiedBy( beanA.getBldModifiedBy() ); 
 		}

 		/* Mapping: "bldModifiedDt" -> "gbdModifiedDt" */
		if(  !Utils.isEmpty( beanA.getBldModifiedDt() )  ){
 			beanB.setGbdModifiedDt( beanA.getBldModifiedDt() ); 
 		}

 		/* Mapping: "id.bldId" -> "gbdBldId" */
		if(  !Utils.isEmpty( beanA.getId() ) && !Utils.isEmpty( beanA.getId().getBldId() )  ){
 			beanB.setGbdBldId( beanA.getId().getBldId() ); 
 		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 *//*
	private static com.rsaame.pas.dao.model.TTrnGaccBuildingQuo initializeDeepVO( com.rsaame.pas.dao.model.TTrnBuildingQuo beanA, com.rsaame.pas.dao.model.TTrnGaccBuildingQuo beanB ){
  		BeanUtils.initializeBeanField( "id", beanB );
                                                      		return beanB;
	}*/
}
