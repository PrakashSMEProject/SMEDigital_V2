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
 * <li>com.rsaame.pas.dao.model.TTrnWctplPremiseQuo</li>
 * <li>com.rsaame.pas.vo.bus.LocationVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( LocVOToTTrnWctplPremiseQuoReverse.class )</code>.
 */
public class LocVOToTTrnWctplPremiseQuoReverse extends BaseBeanToBeanMapper<com.rsaame.pas.dao.model.TTrnWctplPremiseQuo, com.rsaame.pas.vo.bus.LocationVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public LocVOToTTrnWctplPremiseQuoReverse(){
		super();
	}

	public LocVOToTTrnWctplPremiseQuoReverse( com.rsaame.pas.dao.model.TTrnWctplPremiseQuo src, com.rsaame.pas.vo.bus.LocationVO dest ){
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
		com.rsaame.pas.dao.model.TTrnWctplPremiseQuo beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.LocationVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);

		/* Mapping: "wbdEStreetName" -> "address.streetName" */
		if(  !Utils.isEmpty( beanA.getWbdEStreetName() )  ){
 			beanB.getAddress().setStreetName( beanA.getWbdEStreetName() ); 
 		}

 		/* Mapping: "wbdAAddress1" -> "address.officeShopNo" */
		if(  !Utils.isEmpty( beanA.getWbdAAddress1() )  ){
 			beanB.getAddress().setOfficeShopNo( beanA.getWbdAAddress1() ); 
 		}

 		/* Mapping: "wbdTerCode" -> "address.locOverrideTer" */
		if(  !Utils.isEmpty( beanA.getWbdTerCode() )  ){
 			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.getAddress().setLocOverrideTer( converter.getTypeOfA().cast( converter.getAFromB( beanA.getWbdTerCode() ) ) );
  		}

 		/* Mapping: "wbdJurCode" -> "address.locOverrideJur" */
		if(  !Utils.isEmpty( beanA.getWbdJurCode() )  ){
 			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.getAddress().setLocOverrideJur( converter.getTypeOfA().cast( converter.getAFromB( beanA.getWbdJurCode() ) ) );
  		}

 		/* Mapping: "wbdFreeZone" -> "freeZone" */
		if(  !Utils.isEmpty( beanA.getWbdFreeZone() )  ){
 			beanB.setFreeZone( beanA.getWbdFreeZone() ); 
 		}

 		/* Mapping: "wbdDirCode" -> "directorate" */
		if(  !Utils.isEmpty( beanA.getWbdDirCode() )  ){
 			com.rsaame.pas.cmn.converter.LongIntegerConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongIntegerConverter.class, "", "" );
			beanB.setDirectorate( converter.getTypeOfA().cast( converter.getAFromB( beanA.getWbdDirCode() ) ) );
  		}

 		/* Mapping: "wbdWayNo" -> "occTradeGroup" */
		if(  !Utils.isEmpty( beanA.getWbdWayNo() )  ){
 			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.setOccTradeGroup( converter.getTypeOfA().cast( converter.getAFromB( beanA.getWbdGeoLimit() ) ) );
  		}

 		/* Mapping: "wbdEName" -> "address.buildingName" */
		if(  !Utils.isEmpty( beanA.getWbdEName() )  ){
 			beanB.getAddress().setBuildingName( beanA.getWbdEName() ); 
 		}
		
 		/* Mapping: "wbdZip" -> "address.POBox" */
		if(  !Utils.isEmpty( beanA.getWbdZip() ) ){
			beanB.getAddress().setPoBox(beanA.getWbdZip()); 
 		}
   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.LocationVO initializeDeepVO( com.rsaame.pas.dao.model.TTrnWctplPremiseQuo beanA, com.rsaame.pas.vo.bus.LocationVO beanB ){
  		BeanUtils.initializeBeanField( "address", beanB );
                		return beanB;
	}
}
