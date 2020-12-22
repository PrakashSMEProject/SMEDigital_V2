       /*
 * THIS IS A GENERATED CLASS. THIS CLASS SHOULD NOT BE EDITED.
 */
package com.rsaame.pas.vo.voholder.mapper;

import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.beanmap.BeanMapperFactory;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.BeanUtils;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.Utils;

/**
 * Mapper class for:<ol>
 * <li>com.rsaame.pas.vo.bus.TravelDetailsVO</li>
 * <li>com.rsaame.pas.vo.svc.TTrnGaccPersonVOHolder</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( TravelDetailsVOToTTrnGaccPersonVOHolder.class )</code>.
 */
public class TravelDetailsVOToTTrnGaccPersonVOHolder extends BaseBeanToBeanMapper<com.rsaame.pas.vo.bus.TravelDetailsVO, com.rsaame.pas.vo.svc.TTrnGaccPersonVOHolder>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public TravelDetailsVOToTTrnGaccPersonVOHolder(){
		super();
	}

	public TravelDetailsVOToTTrnGaccPersonVOHolder( com.rsaame.pas.vo.bus.TravelDetailsVO src, com.rsaame.pas.vo.svc.TTrnGaccPersonVOHolder dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.svc.TTrnGaccPersonVOHolder mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.svc.TTrnGaccPersonVOHolder) Utils.newInstance( "com.rsaame.pas.vo.svc.TTrnGaccPersonVOHolder" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.vo.bus.TravelDetailsVO beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.svc.TTrnGaccPersonVOHolder beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;
		
		/* Mapping: "travelLocation" -> "gprDescription" */
		if(  !Utils.isEmpty( beanA.getTravelLocation() )  ){
 			beanB.setGprDescription( beanA.getTravelLocation() ); 
 		}

 		/* Mapping: "startDate" -> "gprStartDate" 
		if(  !Utils.isEmpty( beanA.getStartDate() )  ){
 			beanB.setGprStartDate( beanA.getStartDate() ); 
 		}

 		/* Mapping: "endDate" -> "gprEndDate" 
		if(  !Utils.isEmpty( beanA.getEndDate() )  ){
 			beanB.setGprEndDate( beanA.getEndDate() ); 
 		}*/

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.svc.TTrnGaccPersonVOHolder initializeDeepVO( com.rsaame.pas.vo.bus.TravelDetailsVO beanA, com.rsaame.pas.vo.svc.TTrnGaccPersonVOHolder beanB ){
       		return beanB;
	}
}
