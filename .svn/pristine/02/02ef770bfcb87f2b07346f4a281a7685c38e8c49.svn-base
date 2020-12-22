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
 * <li>javax.servlet.http.HttpServletRequest</li>
 * <li>com.rsaame.pas.vo.bus.LocationVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( RequestToLocationVOMapper.class )</code>.
 */
public class RequestToLocationVOMapper extends BaseBeanToBeanMapper<javax.servlet.http.HttpServletRequest, com.rsaame.pas.vo.bus.LocationVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public RequestToLocationVOMapper(){
		super();
	}

	public RequestToLocationVOMapper( javax.servlet.http.HttpServletRequest src, com.rsaame.pas.vo.bus.LocationVO dest ){
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
		javax.servlet.http.HttpServletRequest beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.LocationVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);

		/* Mapping: "officeShopNo" -> "address.officeShopNo" */
//		if( !Utils.isEmpty( src.getParameter( "officeShopNo" ) ) ){
			beanB.getAddress().setOfficeShopNo( Utils.isEmpty( beanA.getParameter( "officeShopNo" ) ) ?  null : beanA.getParameter( "officeShopNo" ).trim() );  
// 		}

 		/* Mapping: "location" -> "directorate" */
		if( !Utils.isEmpty( src.getParameter( "location" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.setDirectorate( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "location" ) ) ) );
  		}

 		/* Mapping: "floor" -> "address.floor" */
//		if( !Utils.isEmpty( src.getParameter( "floor" ) ) ){
			beanB.getAddress().setFloor( Utils.isEmpty( beanA.getParameter( "floor" ) ) ? null : beanA.getParameter( "floor" ).trim() );  
// 		}

 		/* Mapping: "buildingName" -> "address.buildingName" */
		if( !Utils.isEmpty( src.getParameter( "buildingName3" ) ) ){
 			beanB.getAddress().setBuildingName( beanA.getParameter( "buildingName3" ) ); 
 		}

 		/* Mapping: "buildingName" -> "riskGroupName" */
		if( !Utils.isEmpty( src.getParameter( "buildingName" ) ) ){
 			beanB.setRiskGroupName( beanA.getParameter( "buildingName" ) ); 
 		}

 		/* Mapping: "buildingNameOth" -> "address.buildingName" */
		if( !Utils.isEmpty( src.getParameter( "buildingNameOth" ) ) ){
 			beanB.getAddress().setBuildingName( beanA.getParameter( "buildingNameOth" ) ); 
 		}

 		/* Mapping: "streetName" -> "address.streetName" */
		if( !Utils.isEmpty( src.getParameter( "streetName" ) ) ){
 			beanB.getAddress().setStreetName( beanA.getParameter( "streetName" ) ); 
 		}

 		/* Mapping: "freeZone" -> "freeZone" */
		if( !Utils.isEmpty( src.getParameter( "freeZone" ) ) ){
 			beanB.setFreeZone( beanA.getParameter( "freeZone" ) ); 
 		}

 		/* Mapping: "freeZoneOth" -> "freeZoneOthers" */
		if( !Utils.isEmpty( src.getParameter( "freeZoneOth" ) ) ){
 			beanB.setFreeZoneOthers( beanA.getParameter( "freeZoneOth" ) ); 
 		}

 		/* Mapping: "territory" -> "address.locOverrideTer" */
		if( !Utils.isEmpty( src.getParameter( "territory" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getAddress().setLocOverrideTer( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "territory" ) ) ) );
  		}

 		/* Mapping: "jurisdiction" -> "address.locOverrideJur" */
		if( !Utils.isEmpty( src.getParameter( "jurisdiction" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getAddress().setLocOverrideJur( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "jurisdiction" ) ) ) );
  		}

 		/* Mapping: "occupancyGroup" -> "occTradeGroup" */
		if( !Utils.isEmpty( src.getParameter( "occupancyGroup" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.setOccTradeGroup( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "occupancyGroup" ) ) ) );
  		}

 		/* Mapping: "riskGroupId" -> "riskGroupId" */
		if( !Utils.isEmpty( src.getParameter( "riskGroupId" ) ) ){
 			beanB.setRiskGroupId( beanA.getParameter( "riskGroupId" ) ); 
 		}

		/* Mapping: "poBox" -> "poBox" */
		if( !Utils.isEmpty( src.getParameter( "poBox" ) ) ){
 			beanB.getAddress().setPoBox( beanA.getParameter( "poBox" ) ); 
 		}
		
		if( !Utils.isEmpty( src.getParameter( "par_way_no" ) ) ){
 			beanB.getAddress().setWayNo( beanA.getParameter( "par_way_no" ) ); 
 		}
		
		if( !Utils.isEmpty( src.getParameter( "par_block_no" ) ) ){
 			beanB.getAddress().setBlockNo ( beanA.getParameter( "par_block_no" ) ); 
 		}
		
		if( !Utils.isEmpty( src.getParameter( "par_area" ) ) ){
 			beanB.getAddress().setArea ( beanA.getParameter( "par_area" ) ); 
 		}
		
		if( !Utils.isEmpty( src.getParameter( "hazardLevel" ) ) ){
			com.rsaame.pas.cmn.converter.LongStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongStringConverter.class, "", "" );
 			beanB.setHazardLevel( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "hazardLevel" ) ) ) ); 
 		}
		
		//Added for Informap changes
		if( !Utils.isEmpty( src.getParameter( "parLatitude" ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.getAddress().setLatitude( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "parLatitude" ) ) ) );
 		}
		if( !Utils.isEmpty( src.getParameter( "parLongitude" ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.getAddress().setLongitude( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "parLongitude" ) ) ) );
 		}
		if( !Utils.isEmpty( src.getParameter( "parAddress" ) ) ){
 			beanB.getAddress().setAddressInDetail( beanA.getParameter( "parAddress" ) ); 
 		}
		if( !Utils.isEmpty( src.getParameter( "parInformapStatus" ) ) ){
 			beanB.getAddress().setInforMapStatus( beanA.getParameter( "parInformapStatus" ) ); 
 		}
		
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.LocationVO initializeDeepVO( javax.servlet.http.HttpServletRequest beanA, com.rsaame.pas.vo.bus.LocationVO beanB ){
  		BeanUtils.initializeBeanField( "address", beanB );
                          		return beanB;
	}
}
