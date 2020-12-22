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
 * <li>com.rsaame.pas.dao.model.TMasOccupancy</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( TMasOccupToTTrnWctplPremiseQuoReverse.class )</code>.
 */
public class TMasOccupToTTrnWctplPremiseQuoReverse extends BaseBeanToBeanMapper<com.rsaame.pas.dao.model.TTrnWctplPremiseQuo, com.rsaame.pas.dao.model.TMasOccupancy>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public TMasOccupToTTrnWctplPremiseQuoReverse(){
		super();
	}

	public TMasOccupToTTrnWctplPremiseQuoReverse( com.rsaame.pas.dao.model.TTrnWctplPremiseQuo src, com.rsaame.pas.dao.model.TMasOccupancy dest ){
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
		com.rsaame.pas.dao.model.TTrnWctplPremiseQuo beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.dao.model.TMasOccupancy beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);

		/* Mapping: "wbdWayNo" -> "ocpTradeCode" */
		if(  !Utils.isEmpty( beanA.getWbdWayNo() )  ){
 			com.rsaame.pas.cmn.converter.LongIntegerConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongIntegerConverter.class, "", "" );
			beanB.setOcpTradeCode( converter.getTypeOfA().cast( converter.getAFromB( beanA.getWbdWayNo() ) ) );
  		}

 		/* Mapping: "wbdRiRskCode" -> "ocpRiRskCode" */
		if(  !Utils.isEmpty( beanA.getWbdRiRskCode() )  ){
 			beanB.setOcpRiRskCode( beanA.getWbdRiRskCode() ); 
 		}

 		/* Mapping: "wbdPremiseType" -> "ocpRtCode" */
		if(  !Utils.isEmpty( beanA.getWbdPremiseType() )  ){
 			com.rsaame.pas.cmn.converter.IntegerByteConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerByteConverter.class, "", "" );
			beanB.setOcpRtCode( converter.getTypeOfA().cast( converter.getAFromB( beanA.getWbdPremiseType() ) ) );
  		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.dao.model.TMasOccupancy initializeDeepVO( com.rsaame.pas.dao.model.TTrnWctplPremiseQuo beanA, com.rsaame.pas.dao.model.TMasOccupancy beanB ){
       		return beanB;
	}
}
