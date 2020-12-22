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
 * <li>com.rsaame.pas.dao.model.VTrnBldWbdQuo</li>
 * <li>com.rsaame.pas.vo.bus.LocationVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( VTrnBldWbdQuoToLocVOMapper.class )</code>.
 */
public class VTrnBldWbdQuoToLocVOMapper extends BaseBeanToBeanMapper<com.rsaame.pas.dao.model.VTrnBldWbdQuo, com.rsaame.pas.vo.bus.LocationVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public VTrnBldWbdQuoToLocVOMapper(){
		super();
	}

	public VTrnBldWbdQuoToLocVOMapper( com.rsaame.pas.dao.model.VTrnBldWbdQuo src, com.rsaame.pas.vo.bus.LocationVO dest ){
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
		com.rsaame.pas.dao.model.VTrnBldWbdQuo beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.LocationVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);

		/* Mapping: "occTradeGrp" -> "occTradeGroup" */
		if(  !Utils.isEmpty( beanA.getOccTradeGrp() )  ){
 			com.rsaame.pas.cmn.converter.IntegerBigDecimalConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerBigDecimalConverter.class, "", "" );
			beanB.setOccTradeGroup( converter.getTypeOfB().cast( converter.getBFromA( beanA.getOccTradeGrp() ) ) );
  		}

 		/* Mapping: "dirCode" -> "directorate" */
		if(  !Utils.isEmpty( beanA.getDirCode() )  ){
 			com.rsaame.pas.cmn.converter.IntegerBigDecimalConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerBigDecimalConverter.class, "", "" );
			beanB.setDirectorate( converter.getTypeOfB().cast( converter.getBFromA( beanA.getDirCode() ) ) );
  		}

 		/* Mapping: "freeZone" -> "freeZone" */
		if(  !Utils.isEmpty( beanA.getFreeZone() )  ){
 			beanB.setFreeZone( beanA.getFreeZone() ); 
 		}

 		/* Mapping: "validityStartDate" -> "validitySrtDt" */
		if(  !Utils.isEmpty( beanA.getValidityStartDate() )  ){
 			beanB.setValiditySrtDt( beanA.getValidityStartDate() ); 
 		}

 		/* Mapping: "key.id" -> "riskGroupId" */
		if(  !Utils.isEmpty( beanA.getKey() ) && !Utils.isEmpty( beanA.getKey().getId() )  ){
 			com.rsaame.pas.cmn.converter.BigDecimalStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalStringConverter.class, "", "" );
			beanB.setRiskGroupId( converter.getTypeOfB().cast( converter.getBFromA( beanA.getKey().getId() ) ) );
  		}

 		/* Mapping: "flatNo" -> "address.officeShopNo" */
		if(  !Utils.isEmpty( beanA.getFlatNo() )  ){
 			beanB.getAddress().setOfficeShopNo( beanA.getFlatNo() ); 
 		}

 		/* Mapping: "floorNo" -> "address.floor" */
		if(  !Utils.isEmpty( beanA.getFloorNo() )  ){
 			beanB.getAddress().setFloor( beanA.getFloorNo() ); 
 		}

 		/* Mapping: "name" -> "riskGroupName" */
		if(  !Utils.isEmpty( beanA.getName() )  ){
 			beanB.setRiskGroupName( beanA.getName() ); 
 		}

 		/* Mapping: "name" -> "address.buildingName" */
		if(  !Utils.isEmpty( beanA.getName() )  ){
 			beanB.getAddress().setBuildingName( beanA.getName() ); 
 		}

 		/* Mapping: "streetName" -> "address.streetName" */
		if(  !Utils.isEmpty( beanA.getStreetName() )  ){
 			beanB.getAddress().setStreetName( beanA.getStreetName() ); 
 		}

 		/* Mapping: "terCode" -> "address.locOverrideTer" */
		if(  !Utils.isEmpty( beanA.getTerCode() )  ){
 			com.rsaame.pas.cmn.converter.IntegerBigDecimalConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerBigDecimalConverter.class, "", "" );
			beanB.getAddress().setLocOverrideTer( converter.getTypeOfB().cast( converter.getBFromA( beanA.getTerCode() ) ) );
  		}

 		/* Mapping: "jurCode" -> "address.locOverrideJur" */
		if(  !Utils.isEmpty( beanA.getJurCode() )  ){
 			com.rsaame.pas.cmn.converter.IntegerBigDecimalConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerBigDecimalConverter.class, "", "" );
			beanB.getAddress().setLocOverrideJur( converter.getTypeOfB().cast( converter.getBFromA( beanA.getJurCode() ) ) );
  		}

 		/* Mapping: "streetName" -> "address.streetName" */
		if(  !Utils.isEmpty( beanA.getStreetName() )  ){
 			beanB.getAddress().setStreetName( beanA.getStreetName() ); 
 		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.LocationVO initializeDeepVO( com.rsaame.pas.dao.model.VTrnBldWbdQuo beanA, com.rsaame.pas.vo.bus.LocationVO beanB ){
            		BeanUtils.initializeBeanField( "address", beanB );
                		return beanB;
	}
}
