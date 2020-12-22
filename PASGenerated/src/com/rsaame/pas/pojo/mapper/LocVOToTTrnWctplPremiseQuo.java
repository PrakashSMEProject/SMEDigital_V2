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
 * <li>com.rsaame.pas.vo.bus.LocationVO</li>
 * <li>com.rsaame.pas.dao.model.TTrnWctplPremiseQuo</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( LocVOToTTrnWctplPremiseQuo.class )</code>.
 */
public class LocVOToTTrnWctplPremiseQuo extends BaseBeanToBeanMapper<com.rsaame.pas.vo.bus.LocationVO, com.rsaame.pas.dao.model.TTrnWctplPremiseQuo>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public LocVOToTTrnWctplPremiseQuo(){
		super();
	}

	public LocVOToTTrnWctplPremiseQuo( com.rsaame.pas.vo.bus.LocationVO src, com.rsaame.pas.dao.model.TTrnWctplPremiseQuo dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.dao.model.TTrnWctplPremiseQuo mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.dao.model.TTrnWctplPremiseQuo) Utils.newInstance( "com.rsaame.pas.dao.model.TTrnWctplPremiseQuo" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.vo.bus.LocationVO beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.dao.model.TTrnWctplPremiseQuo beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);

		/* Mapping: "address.streetName" -> "wbdEStreetName" */
		if(  !Utils.isEmpty( beanA.getAddress() ) && !Utils.isEmpty( beanA.getAddress().getStreetName() )  ){
 			beanB.setWbdEStreetName( beanA.getAddress().getStreetName() ); 
 		}

 		/* Mapping: "address.officeShopNo" -> "wbdAAddress1" */
		if(  !Utils.isEmpty( beanA.getAddress() ) && !Utils.isEmpty( beanA.getAddress().getOfficeShopNo() )  ){
 			beanB.setWbdAAddress1( beanA.getAddress().getOfficeShopNo() ); 
 		}

 		/* Mapping: "address.locOverrideTer" -> "wbdTerCode" */
		if(  !Utils.isEmpty( beanA.getAddress() ) && !Utils.isEmpty( beanA.getAddress().getLocOverrideTer() )  ){
 			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.setWbdTerCode( converter.getTypeOfB().cast( converter.getBFromA( beanA.getAddress().getLocOverrideTer() ) ) );
  		}

 		/* Mapping: "address.locOverrideJur" -> "wbdJurCode" */
		if(  !Utils.isEmpty( beanA.getAddress() ) && !Utils.isEmpty( beanA.getAddress().getLocOverrideJur() )  ){
 			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.setWbdJurCode( converter.getTypeOfB().cast( converter.getBFromA( beanA.getAddress().getLocOverrideJur() ) ) );
  		}

 		/* Mapping: "freeZone" -> "wbdFreeZone" */
		if(  !Utils.isEmpty( beanA.getFreeZone() )  ){
 			beanB.setWbdFreeZone( beanA.getFreeZone() ); 
 		}

 		/* Mapping: "directorate" -> "wbdDirCode" */
		if(  !Utils.isEmpty( beanA.getDirectorate() )  ){
 			com.rsaame.pas.cmn.converter.LongIntegerConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongIntegerConverter.class, "", "" );
			beanB.setWbdDirCode( converter.getTypeOfB().cast( converter.getBFromA( beanA.getDirectorate() ) ) );
  		}

 		/* Mapping: "occTradeGroup" -> "wbdWayNo" */
		if(  !Utils.isEmpty( beanA.getOccTradeGroup() )  ){
 			com.rsaame.pas.cmn.converter.LongIntegerConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongIntegerConverter.class, "", "" );
			beanB.setWbdWayNo( converter.getTypeOfB().cast( converter.getBFromA( beanA.getOccTradeGroup() ) ) );
  		}

 		/* Mapping: "address.buildingName" -> "wbdEName" */
		if(  !Utils.isEmpty( beanA.getAddress() ) && !Utils.isEmpty( beanA.getAddress().getBuildingName() )  ){
 			beanB.setWbdEName( beanA.getAddress().getBuildingName() ); 
 		}
		
		/* Mapping: "address.Pox Box No" -> "wbdZip" */
		if(  !Utils.isEmpty( beanA.getAddress() ) && !Utils.isEmpty( beanA.getAddress().getPoBox() )  ){
 			beanB.setWbdZip(  beanA.getAddress().getPoBox() ); 
 		}
		
   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.dao.model.TTrnWctplPremiseQuo initializeDeepVO( com.rsaame.pas.vo.bus.LocationVO beanA, com.rsaame.pas.dao.model.TTrnWctplPremiseQuo beanB ){
                 		return beanB;
	}
}
