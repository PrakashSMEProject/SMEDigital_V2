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
 * <li>com.rsaame.pas.dao.model.TTrnWctplPremiseQuo</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( TMasOccupToTTrnWctplPremiseQuo.class )</code>.
 */
public class TMasOccupToTTrnWctplPremiseQuo extends BaseBeanToBeanMapper<com.rsaame.pas.dao.model.TMasOccupancy, com.rsaame.pas.dao.model.TTrnWctplPremiseQuo>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public TMasOccupToTTrnWctplPremiseQuo(){
		super();
	}

	public TMasOccupToTTrnWctplPremiseQuo( com.rsaame.pas.dao.model.TMasOccupancy src, com.rsaame.pas.dao.model.TTrnWctplPremiseQuo dest ){
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
		com.rsaame.pas.dao.model.TMasOccupancy beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.dao.model.TTrnWctplPremiseQuo beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);

		/* Mapping: "ocpTradeCode" -> "wbdWayNo" */
		if(  !Utils.isEmpty( beanA.getOcpTradeCode() )  ){
 			com.rsaame.pas.cmn.converter.LongIntegerConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongIntegerConverter.class, "", "" );
			beanB.setWbdWayNo( converter.getTypeOfB().cast( converter.getBFromA( beanA.getOcpTradeCode() ) ) );
  		}

 		/* Mapping: "ocpRiRskCode" -> "wbdRiRskCode" */
		/*
		 * FIX: Backend fix: this field should be blank in case of sbs policy - 22-Jul_2012
		 */
	/*	if(  !Utils.isEmpty( beanA.getOcpRiRskCode() )  ){
 			beanB.setWbdRiRskCode( beanA.getOcpRiRskCode() ); 
 		}*/

 		/* Mapping: "ocpRtCode" -> "wbdPremiseType" */
		if(  !Utils.isEmpty( beanA.getOcpRtCode() )  ){
 			com.rsaame.pas.cmn.converter.IntegerByteConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerByteConverter.class, "", "" );
			beanB.setWbdPremiseType( converter.getTypeOfB().cast( converter.getBFromA( beanA.getOcpRtCode() ) ) );
  		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.dao.model.TTrnWctplPremiseQuo initializeDeepVO( com.rsaame.pas.dao.model.TMasOccupancy beanA, com.rsaame.pas.dao.model.TTrnWctplPremiseQuo beanB ){
       		return beanB;
	}
}
