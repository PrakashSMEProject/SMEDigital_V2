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
 * <li>com.rsaame.pas.vo.svc.TTrnGaccPersonVOHolder</li>
 * <li>com.rsaame.pas.vo.bus.TravelDetailsVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( TravelDetailsVOToTTrnGaccPersonVOHolderReverse.class )</code>.
 */
public class TravelDetailsVOToTTrnGaccPersonVOHolderReverse extends BaseBeanToBeanMapper<com.rsaame.pas.vo.svc.TTrnGaccPersonVOHolder, com.rsaame.pas.vo.bus.TravelDetailsVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public TravelDetailsVOToTTrnGaccPersonVOHolderReverse(){
		super();
	}

	public TravelDetailsVOToTTrnGaccPersonVOHolderReverse( com.rsaame.pas.vo.svc.TTrnGaccPersonVOHolder src, com.rsaame.pas.vo.bus.TravelDetailsVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.TravelDetailsVO mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.TravelDetailsVO) Utils.newInstance( "com.rsaame.pas.vo.bus.TravelDetailsVO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.vo.svc.TTrnGaccPersonVOHolder beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.TravelDetailsVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;
		
		/* Mapping: "gprDescription" -> "travelLocation" */
		if(  !Utils.isEmpty( beanA.getGprDescription() )  ){
 			beanB.setTravelLocation( beanA.getGprDescription() ); 
 		}

 		/* Mapping: "gprStartDate" -> "startDate" 
		if(  !Utils.isEmpty( beanA.getGprStartDate() )  ){
 			beanB.setStartDate( beanA.getGprStartDate() ); 
 		}

 		/* Mapping: "gprEndDate" -> "endDate" 
		if(  !Utils.isEmpty( beanA.getGprEndDate() )  ){
 			beanB.setEndDate( beanA.getGprEndDate() ); 
 		}*/

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.TravelDetailsVO initializeDeepVO( com.rsaame.pas.vo.svc.TTrnGaccPersonVOHolder beanA, com.rsaame.pas.vo.bus.TravelDetailsVO beanB ){
       		return beanB;
	}
}
