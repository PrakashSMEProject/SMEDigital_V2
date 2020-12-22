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
 * <li>com.rsaame.pas.vo.bus.InsuredVO</li>
 * <li>com.rsaame.kaizen.customer.model.CustomerInsured</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( InsuredVOToCustomerInsuredMapper.class )</code>.
 */
public class InsuredVOToCustomerInsuredMapper extends BaseBeanToBeanMapper<com.rsaame.pas.vo.bus.InsuredVO, com.rsaame.kaizen.customer.model.CustomerInsured>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public InsuredVOToCustomerInsuredMapper(){
		super();
	}

	public InsuredVOToCustomerInsuredMapper( com.rsaame.pas.vo.bus.InsuredVO src, com.rsaame.kaizen.customer.model.CustomerInsured dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.kaizen.customer.model.CustomerInsured mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.kaizen.customer.model.CustomerInsured) Utils.newInstance( "com.rsaame.kaizen.customer.model.CustomerInsured" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.vo.bus.InsuredVO beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.kaizen.customer.model.CustomerInsured beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;
		
		/* Mapping: "insuredId" -> "insuredId" */
		if(  !Utils.isEmpty( beanA.getInsuredId() )  ){
 			beanB.setInsuredId( beanA.getInsuredId() ); 
 		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.kaizen.customer.model.CustomerInsured initializeDeepVO( com.rsaame.pas.vo.bus.InsuredVO beanA, com.rsaame.kaizen.customer.model.CustomerInsured beanB ){
   		return beanB;
	}
}
