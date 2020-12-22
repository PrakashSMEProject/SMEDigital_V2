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
 * <li>com.rsaame.pas.dao.model.TTrnWctplPersonQuo</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( TMasOccToTTrnWctplPerQuoMapper.class )</code>.
 */
public class TMasOccToTTrnWctplPerQuoMapper extends BaseBeanToBeanMapper<com.rsaame.pas.dao.model.TMasOccupancy, com.rsaame.pas.dao.model.TTrnWctplPersonQuo>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public TMasOccToTTrnWctplPerQuoMapper(){
		super();
	}

	public TMasOccToTTrnWctplPerQuoMapper( com.rsaame.pas.dao.model.TMasOccupancy src, com.rsaame.pas.dao.model.TTrnWctplPersonQuo dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.dao.model.TTrnWctplPersonQuo mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.dao.model.TTrnWctplPersonQuo) Utils.newInstance( "com.rsaame.pas.dao.model.TTrnWctplPersonQuo" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.dao.model.TMasOccupancy beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.dao.model.TTrnWctplPersonQuo beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;
		
		/* Mapping: "ocpRskCode" -> "wprRskCode" */
		if(  !Utils.isEmpty( beanA.getOcpRskCode() )  ){
 			com.rsaame.pas.cmn.converter.LongIntegerConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongIntegerConverter.class, "", "" );
			beanB.setWprRskCode( converter.getTypeOfB().cast( converter.getBFromA( beanA.getOcpRskCode() ) ) );
  		}

 		/* Mapping: "ocpRtCode" -> "wprRtCode" */
		if(  !Utils.isEmpty( beanA.getOcpRtCode() )  ){
 			com.rsaame.pas.cmn.converter.LongIntegerConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongIntegerConverter.class, "", "" );
			beanB.setWprRtCode( converter.getTypeOfB().cast( converter.getBFromA( beanA.getOcpRtCode() ) ) );
  		}

 		/* Mapping: "ocpRcCode" -> "wprRcCode" */
		if(  !Utils.isEmpty( beanA.getOcpRcCode() )  ){
 			com.rsaame.pas.cmn.converter.LongIntegerConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongIntegerConverter.class, "", "" );
			beanB.setWprRcCode( converter.getTypeOfB().cast( converter.getBFromA( beanA.getOcpRcCode() ) ) );
  		}

 		/* Mapping: "ocpRiRskCode" -> "wprRiRskCode" */
		if(  !Utils.isEmpty( beanA.getOcpRiRskCode() )  ){
 			beanB.setWprRiRskCode( beanA.getOcpRiRskCode() ); 
 		}

 		/* Mapping: "ocpTradeCode" -> "wprTradeGroup" */
		if(  !Utils.isEmpty( beanA.getOcpTradeCode() )  ){
 			com.rsaame.pas.cmn.converter.LongIntegerConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongIntegerConverter.class, "", "" );
			beanB.setWprTradeGroup( converter.getTypeOfB().cast( converter.getBFromA( beanA.getOcpTradeCode() ) ) );
  		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.dao.model.TTrnWctplPersonQuo initializeDeepVO( com.rsaame.pas.dao.model.TMasOccupancy beanA, com.rsaame.pas.dao.model.TTrnWctplPersonQuo beanB ){
           		return beanB;
	}
}
