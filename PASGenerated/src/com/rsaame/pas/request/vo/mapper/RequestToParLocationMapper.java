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
 * <li>com.rsaame.pas.vo.bus.LocationVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( RequestToParLocationMapper.class )</code>.
 */
public class RequestToParLocationMapper extends BaseBeanToBeanMapper<javax.servlet.http.HttpServletRequest, com.rsaame.pas.vo.bus.LocationVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public RequestToParLocationMapper(){
		super();
	}

	public RequestToParLocationMapper( javax.servlet.http.HttpServletRequest src, com.rsaame.pas.vo.bus.LocationVO dest ){
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

		/* Mapping: "parOffice" -> "address.officeShopNo" */
		if( !Utils.isEmpty( src.getParameter( "parOffice" ) ) ){
 			beanB.getAddress().setOfficeShopNo( beanA.getParameter( "parOffice" ) ); 
 		}

 		/* Mapping: "parLocation" -> "directorate" */
		if( !Utils.isEmpty( src.getParameter( "parLocation" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.setDirectorate( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "parLocation" ) ) ) );
  		}

 		/* Mapping: "parFloor" -> "address.floor" */
		if( !Utils.isEmpty( src.getParameter( "parFloor" ) ) ){
 			beanB.getAddress().setFloor( beanA.getParameter( "parFloor" ) ); 
 		}

 		/* Mapping: "parBuildingName" -> "address.buildingName" */
		if( !Utils.isEmpty( src.getParameter( "parBuildingName" ) ) ){
 			beanB.getAddress().setBuildingName( beanA.getParameter( "parBuildingName" ) ); 
 		}

 		/* Mapping: "parBuildingNameOth" -> "address.buildingName" */
		if( !Utils.isEmpty( src.getParameter( "parBuildingNameOth" ) ) ){
 			beanB.getAddress().setBuildingName( beanA.getParameter( "parBuildingNameOth" ) ); 
 		}

 		/* Mapping: "parStreetName" -> "address.streetName" */
		if( !Utils.isEmpty( src.getParameter( "parStreetName" ) ) ){
 			beanB.getAddress().setStreetName( beanA.getParameter( "parStreetName" ) ); 
 		}

 		/* Mapping: "parfreeZone" -> "freeZone" */
		if( !Utils.isEmpty( src.getParameter( "parfreeZone" ) ) ){
 			beanB.setFreeZone( beanA.getParameter( "parfreeZone" ) ); 
 		}

 		/* Mapping: "parFreeZoneOth" -> "freeZoneOthers" */
		if( !Utils.isEmpty( src.getParameter( "parFreeZoneOth" ) ) ){
 			beanB.setFreeZoneOthers( beanA.getParameter( "parFreeZoneOth" ) ); 
 		}

 		/* Mapping: "paroccupancyGroup" -> "occTradeGroup" */
		if( !Utils.isEmpty( src.getParameter( "paroccupancyGroup" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.setOccTradeGroup( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "paroccupancyGroup" ) ) ) );
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
