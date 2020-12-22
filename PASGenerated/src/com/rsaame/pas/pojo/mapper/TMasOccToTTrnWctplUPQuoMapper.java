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
 * <li>com.rsaame.pas.dao.model.TMasOccupancy</li>
 * <li>com.rsaame.pas.dao.model.TTrnWctplUnnamedPersonQuo</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( TMasOccToTTrnWctplUPQuoMapper.class )</code>.
 */
public class TMasOccToTTrnWctplUPQuoMapper extends BaseBeanToBeanMapper<com.rsaame.pas.dao.model.TMasOccupancy, com.rsaame.pas.dao.model.TTrnWctplUnnamedPersonQuo>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public TMasOccToTTrnWctplUPQuoMapper(){
		super();
	}

	public TMasOccToTTrnWctplUPQuoMapper( com.rsaame.pas.dao.model.TMasOccupancy src, com.rsaame.pas.dao.model.TTrnWctplUnnamedPersonQuo dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.dao.model.TTrnWctplUnnamedPersonQuo mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.dao.model.TTrnWctplUnnamedPersonQuo) Utils.newInstance( "com.rsaame.pas.dao.model.TTrnWctplUnnamedPersonQuo" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.dao.model.TMasOccupancy beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.dao.model.TTrnWctplUnnamedPersonQuo beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);

		/* Mapping: "ocpRskCode" -> "wupRskCode" */
		if(  !Utils.isEmpty( beanA.getOcpRskCode() )  ){
 			com.rsaame.pas.cmn.converter.LongIntegerConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongIntegerConverter.class, "", "" );
			beanB.setWupRskCode( converter.getTypeOfB().cast( converter.getBFromA( beanA.getOcpRskCode() ) ) );
  		}

 		/* Mapping: "ocpRtCode" -> "wupRtCode" */
		if(  !Utils.isEmpty( beanA.getOcpRtCode() )  ){
 			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.setWupRtCode( converter.getTypeOfB().cast( converter.getBFromA( beanA.getOcpRtCode() ) ) );
  		}

 		/* Mapping: "ocpRcCode" -> "wupRcCode" */
		if(  !Utils.isEmpty( beanA.getOcpRcCode() )  ){
 			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.setWupRcCode( converter.getTypeOfB().cast( converter.getBFromA( beanA.getOcpRcCode() ) ) );
  		}

 		/* Mapping: "ocpRiRskCode" -> "wupRiRskCode" */
		//FIX: Backend fix : there ri risk code for class code 7 is 701
	/*	if(  !Utils.isEmpty( beanA.getOcpRiRskCode() )  ){
 			beanB.setWupRiRskCode( beanA.getOcpRiRskCode() ); 
 		}*/

 		/* Mapping: "ocpTradeCode" -> "wupTradeGroup" */
		if(  !Utils.isEmpty( beanA.getOcpTradeCode() )  ){
 			com.rsaame.pas.cmn.converter.LongIntegerConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongIntegerConverter.class, "", "" );
			beanB.setWupTradeGroup( converter.getTypeOfB().cast( converter.getBFromA( beanA.getOcpTradeCode() ) ) );
  		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.dao.model.TTrnWctplUnnamedPersonQuo initializeDeepVO( com.rsaame.pas.dao.model.TMasOccupancy beanA, com.rsaame.pas.dao.model.TTrnWctplUnnamedPersonQuo beanB ){
           		return beanB;
	}
}
