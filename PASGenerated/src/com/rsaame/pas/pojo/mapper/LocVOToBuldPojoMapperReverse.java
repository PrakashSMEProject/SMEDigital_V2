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
 * <li>com.rsaame.pas.vo.bus.LocationVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( LocVOToBuldPojoMapperReverse.class )</code>.
 */
public class LocVOToBuldPojoMapperReverse extends BaseBeanToBeanMapper<com.rsaame.pas.dao.model.TTrnBuildingQuo, com.rsaame.pas.vo.bus.LocationVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public LocVOToBuldPojoMapperReverse(){
		super();
	}

	public LocVOToBuldPojoMapperReverse( com.rsaame.pas.dao.model.TTrnBuildingQuo src, com.rsaame.pas.vo.bus.LocationVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.LocationVO mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.LocationVO) Utils.newInstance( "com.rsaame.pas.vo.bus.LocationVO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.dao.model.TTrnBuildingQuo beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.LocationVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);

		/* Mapping: "bldFreeZone" -> "freeZone" */
		if(  !Utils.isEmpty( beanA.getBldFreeZone() )  ){
 			beanB.setFreeZone( beanA.getBldFreeZone() ); 
 		}

 		/* Mapping: "bldOccupancyCode" -> "occTradeGroup" */
		if(  !Utils.isEmpty( beanA.getBldOccupancyCode() )  ){
 			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.setOccTradeGroup( converter.getTypeOfA().cast( converter.getAFromB( beanA.getBldOccupancyCode() ) ) );
  		}

 		/* Mapping: "bldAAddress1" -> "address.officeShopNo" */
		if(  !Utils.isEmpty( beanA.getBldAAddress1() )  ){
 			beanB.getAddress().setOfficeShopNo( beanA.getBldAAddress1() ); 
 		}

 		/* Mapping: "bldAAddress2" -> "address.floor" */
		if(  !Utils.isEmpty( beanA.getBldAAddress2() )  ){
 			beanB.getAddress().setFloor( beanA.getBldAAddress2() ); 
 		}

 		/* Mapping: "bldEName" -> "riskGroupName" */
		if(  !Utils.isEmpty( beanA.getBldEName() )  ){
 			beanB.setRiskGroupName( beanA.getBldEName() ); 
 		}

 		/* Mapping: "bldEName" -> "address.buildingName" */
		if(  !Utils.isEmpty( beanA.getBldEName() )  ){
 			beanB.getAddress().setBuildingName( beanA.getBldEName() ); 
 		}

 		/* Mapping: "bldEStreetName" -> "address.streetName" */
		if(  !Utils.isEmpty( beanA.getBldEStreetName() )  ){
 			beanB.getAddress().setStreetName( beanA.getBldEStreetName() ); 
 		}

 		/* Mapping: "bldZip" -> "address.POBox" */
		if(  !Utils.isEmpty( beanA.getBldZip() ) ){
 			beanB.getAddress().setPoBox(beanA.getBldZip()); 
 		}
		
		/* Mapping: "bldZip" -> "address.POBox" */
		if(  !Utils.isEmpty( beanA.getBldWayNo() ) ){
 			beanB.getAddress().setWayNo( beanA.getBldWayNo().toString() ); 
 		}
		
		/* Mapping: "bldZip" -> "address.POBox" */
		if(  !Utils.isEmpty( beanA.getBldBlockNo() ) ){
 			beanB.getAddress().setBlockNo( beanA.getBldBlockNo().toString() ); 
 		}
		
		//Added for Infromap changes
		if(  !Utils.isEmpty( beanA.getBldLatitude() )  ){
			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.getAddress().setLatitude( converter.getTypeOfB().cast( converter.getBFromA( beanA.getBldLatitude() ) ) );
  		}
		if(  !Utils.isEmpty( beanA.getBldLongitude() )  ){
			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.getAddress().setLongitude( converter.getTypeOfB().cast( converter.getBFromA( beanA.getBldLongitude() ) ) );
  		}
		if(  !Utils.isEmpty( beanA.getBldAName() ) ){
 			beanB.getAddress().setAddressInDetail(beanA.getBldAName().toString() ); 
 		}
		if(  !Utils.isEmpty( beanA.getBldInforMapStatus() ) ){
 			beanB.getAddress().setInforMapStatus(beanA.getBldInforMapStatus().toString() ); 
 		}
   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.LocationVO initializeDeepVO( com.rsaame.pas.dao.model.TTrnBuildingQuo beanA, com.rsaame.pas.vo.bus.LocationVO beanB ){
      		BeanUtils.initializeBeanField( "address", beanB );
          		return beanB;
	}
}
