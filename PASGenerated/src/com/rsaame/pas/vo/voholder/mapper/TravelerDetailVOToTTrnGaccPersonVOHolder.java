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
 * <li>com.rsaame.pas.vo.bus.TravelerDetailsVO</li>
 * <li>com.rsaame.pas.vo.svc.TTrnGaccPersonVOHolder</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( TravelerDetailVOToTTrnGaccPersonVOHolder.class )</code>.
 */
public class TravelerDetailVOToTTrnGaccPersonVOHolder extends BaseBeanToBeanMapper<com.rsaame.pas.vo.bus.TravelerDetailsVO, com.rsaame.pas.vo.svc.TTrnGaccPersonVOHolder>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public TravelerDetailVOToTTrnGaccPersonVOHolder(){
		super();
	}

	public TravelerDetailVOToTTrnGaccPersonVOHolder( com.rsaame.pas.vo.bus.TravelerDetailsVO src, com.rsaame.pas.vo.svc.TTrnGaccPersonVOHolder dest ){
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
		com.rsaame.pas.vo.bus.TravelerDetailsVO beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.svc.TTrnGaccPersonVOHolder beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;
		
		/* Mapping: "name" -> "gprEName" */
		if(  !Utils.isEmpty( beanA.getName() )  ){
 			beanB.setGprEName( beanA.getName() ); 
 		}

 		/* Mapping: "dateOfBirth" -> "gprDateOfBirth" */
		if(  !Utils.isEmpty( beanA.getDateOfBirth() )  ){
 			beanB.setGprDateOfBirth( beanA.getDateOfBirth() ); 
 		}

 		/* Mapping: "relation" -> "gprRelation" */
		if(  !Utils.isEmpty( beanA.getRelation() )  ){
 			beanB.setGprRelation( beanA.getRelation() ); 
 		}
		
		/* Mapping: "gender" -> "gprGender" */
		if(  !Utils.isEmpty( beanA.getGender() )  ){
 			beanB.setGprGender(beanA.getGender()); 
 		}

 		/* Mapping: "nationality" -> "gprNtyEDesc" */
		if(  !Utils.isEmpty( beanA.getNationality() )  ){
 			beanB.setGprNtyEDesc( beanA.getNationality() ); 
 		}
		if(  !Utils.isEmpty( beanA.getGprId() )  ){
 			beanB.setGprId( Long.valueOf(beanA.getGprId().toString() )); 
 		}
		if(  !Utils.isEmpty( beanA.getVsd() )  ){
 			beanB.setGprValidityStartDate( beanA.getVsd()); 
 		}
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.svc.TTrnGaccPersonVOHolder initializeDeepVO( com.rsaame.pas.vo.bus.TravelerDetailsVO beanA, com.rsaame.pas.vo.svc.TTrnGaccPersonVOHolder beanB ){
         		return beanB;
	}
}
