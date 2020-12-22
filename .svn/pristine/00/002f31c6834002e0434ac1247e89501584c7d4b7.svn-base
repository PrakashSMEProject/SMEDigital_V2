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
 * <li>com.rsaame.pas.dao.model.TTrnWctplPersonQuo</li>
 * <li>com.rsaame.pas.dao.model.TMasOccupancy</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( TMasOccToTTrnWctplPerQuoMapperReverse.class )</code>.
 */
public class TMasOccToTTrnWctplPerQuoMapperReverse extends BaseBeanToBeanMapper<com.rsaame.pas.dao.model.TTrnWctplPersonQuo, com.rsaame.pas.dao.model.TMasOccupancy>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public TMasOccToTTrnWctplPerQuoMapperReverse(){
		super();
	}

	public TMasOccToTTrnWctplPerQuoMapperReverse( com.rsaame.pas.dao.model.TTrnWctplPersonQuo src, com.rsaame.pas.dao.model.TMasOccupancy dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.dao.model.TMasOccupancy mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.dao.model.TMasOccupancy) Utils.newInstance( "com.rsaame.pas.dao.model.TMasOccupancy" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.dao.model.TTrnWctplPersonQuo beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.dao.model.TMasOccupancy beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;
		
		/* Mapping: "wprRskCode" -> "ocpRskCode" */
		if(  !Utils.isEmpty( beanA.getWprRskCode() )  ){
 			com.rsaame.pas.cmn.converter.LongIntegerConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongIntegerConverter.class, "", "" );
			beanB.setOcpRskCode( converter.getTypeOfA().cast( converter.getAFromB( beanA.getWprRskCode() ) ) );
  		}

 		/* Mapping: "wprRtCode" -> "ocpRtCode" */
		if(  !Utils.isEmpty( beanA.getWprRtCode() )  ){
 			com.rsaame.pas.cmn.converter.LongIntegerConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongIntegerConverter.class, "", "" );
			beanB.setOcpRtCode( converter.getTypeOfA().cast( converter.getAFromB( beanA.getWprRtCode() ) ) );
  		}

 		/* Mapping: "wprRcCode" -> "ocpRcCode" */
		if(  !Utils.isEmpty( beanA.getWprRcCode() )  ){
 			com.rsaame.pas.cmn.converter.LongIntegerConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongIntegerConverter.class, "", "" );
			beanB.setOcpRcCode( converter.getTypeOfA().cast( converter.getAFromB( beanA.getWprRcCode() ) ) );
  		}

 		/* Mapping: "wprRiRskCode" -> "ocpRiRskCode" */
		if(  !Utils.isEmpty( beanA.getWprRiRskCode() )  ){
 			beanB.setOcpRiRskCode( beanA.getWprRiRskCode() ); 
 		}

 		/* Mapping: "wprTradeGroup" -> "ocpTradeCode" */
		if(  !Utils.isEmpty( beanA.getWprTradeGroup() )  ){
 			com.rsaame.pas.cmn.converter.LongIntegerConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongIntegerConverter.class, "", "" );
			beanB.setOcpTradeCode( converter.getTypeOfA().cast( converter.getAFromB( beanA.getWprTradeGroup() ) ) );
  		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.dao.model.TMasOccupancy initializeDeepVO( com.rsaame.pas.dao.model.TTrnWctplPersonQuo beanA, com.rsaame.pas.dao.model.TMasOccupancy beanB ){
           		return beanB;
	}
}
