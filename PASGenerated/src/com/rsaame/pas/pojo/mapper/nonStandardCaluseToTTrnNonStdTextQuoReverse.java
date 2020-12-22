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
 * <li>com.rsaame.pas.dao.model.TTrnNonStdTextQuo</li>
 * <li>com.rsaame.pas.vo.bus.NonStandardClause</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( nonStandardCaluseToTTrnNonStdTextQuoReverse.class )</code>.
 */
public class nonStandardCaluseToTTrnNonStdTextQuoReverse extends BaseBeanToBeanMapper<com.rsaame.pas.dao.model.TTrnNonStdTextQuo, com.rsaame.pas.vo.bus.NonStandardClause>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public nonStandardCaluseToTTrnNonStdTextQuoReverse(){
		super();
	}

	public nonStandardCaluseToTTrnNonStdTextQuoReverse( com.rsaame.pas.dao.model.TTrnNonStdTextQuo src, com.rsaame.pas.vo.bus.NonStandardClause dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.NonStandardClause mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.NonStandardClause) Utils.newInstance( "com.rsaame.pas.vo.bus.NonStandardClause" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.dao.model.TTrnNonStdTextQuo beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.NonStandardClause beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;
		
		/* Mapping: "nstEText" -> "description" */
		if(  !Utils.isEmpty( beanA.getNstEText() )  ){
 			beanB.setDescription( beanA.getNstEText() ); 
 		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.NonStandardClause initializeDeepVO( com.rsaame.pas.dao.model.TTrnNonStdTextQuo beanA, com.rsaame.pas.vo.bus.NonStandardClause beanB ){
   		return beanB;
	}
}
