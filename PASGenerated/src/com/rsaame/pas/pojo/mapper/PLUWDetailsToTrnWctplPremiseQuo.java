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
 * <li>com.rsaame.pas.vo.bus.PLUWDetails</li>
 * <li>com.rsaame.pas.dao.model.TTrnWctplPremiseQuo</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( PLUWDetailsToTrnWctplPremiseQuo.class )</code>.
 */
public class PLUWDetailsToTrnWctplPremiseQuo extends BaseBeanToBeanMapper<com.rsaame.pas.vo.bus.PLUWDetails, com.rsaame.pas.dao.model.TTrnWctplPremiseQuo>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public PLUWDetailsToTrnWctplPremiseQuo(){
		super();
	}

	public PLUWDetailsToTrnWctplPremiseQuo( com.rsaame.pas.vo.bus.PLUWDetails src, com.rsaame.pas.dao.model.TTrnWctplPremiseQuo dest ){
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
		com.rsaame.pas.vo.bus.PLUWDetails beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.dao.model.TTrnWctplPremiseQuo beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);

		/* Mapping: "hazardLevel" -> "wbdBlockNo" */
		if(  !Utils.isEmpty( beanA.getHazardLevel() )  ){
 			com.rsaame.pas.cmn.converter.LongIntegerConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongIntegerConverter.class, "", "" );
			beanB.setWbdBlockNo( converter.getTypeOfB().cast( converter.getBFromA( beanA.getHazardLevel() ) ) );
  		}

 		/* Mapping: "categoryRI" -> "wbdRiRskCode" */
		if(  !Utils.isEmpty( beanA.getCategoryRI() )  ){
 			beanB.setWbdRiRskCode( beanA.getCategoryRI() ); 
 		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.dao.model.TTrnWctplPremiseQuo initializeDeepVO( com.rsaame.pas.vo.bus.PLUWDetails beanA, com.rsaame.pas.dao.model.TTrnWctplPremiseQuo beanB ){
     		return beanB;
	}
}
