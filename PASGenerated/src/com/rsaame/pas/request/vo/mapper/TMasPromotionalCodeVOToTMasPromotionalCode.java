       /*
 * THIS IS A GENERATED CLASS. THIS CLASS SHOULD NOT BE EDITED.
 */
package com.rsaame.pas.request.vo.mapper;

import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;

/**
 * Mapper class for:<ol>
 * <li>com.rsaame.pas.vo.svc.TMasPromotionalCodeVOHolder</li>
 * <li>com.rsaame.pas.dao.model.TMasPromotionalCode</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( TMasPromotionalCodeVOHolderToTMasPromotionalCode.class )</code>.
 */
public class TMasPromotionalCodeVOToTMasPromotionalCode extends BaseBeanToBeanMapper<com.rsaame.pas.vo.svc.TMasPromotionalCodeVO, com.rsaame.pas.dao.model.TMasPromotionalCode>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public TMasPromotionalCodeVOToTMasPromotionalCode(){
		super();
	}

	public TMasPromotionalCodeVOToTMasPromotionalCode( com.rsaame.pas.vo.svc.TMasPromotionalCodeVO src, com.rsaame.pas.dao.model.TMasPromotionalCode dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.dao.model.TMasPromotionalCode mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.dao.model.TMasPromotionalCode) Utils.newInstance( "com.rsaame.pas.dao.model.TMasPromotionalCode" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.vo.svc.TMasPromotionalCodeVO beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.dao.model.TMasPromotionalCode beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;
		
		
 		/* Mapping: "proType" -> "proType" */
		if(  !Utils.isEmpty( beanA.getProType() )  ){
 			beanB.setProType( beanA.getProType() ); 
 		}
		
		

 		/* Mapping: "proPtCode" -> "proPtCode" */
		if(  !Utils.isEmpty( beanA.getProPtCode() )  ){
 			beanB.setProPtCode( beanA.getProPtCode() ); 
 		}

 		/* Mapping: "proClassCode" -> "proClassCode" */
		if(  !Utils.isEmpty( beanA.getProClassCode() )  ){
 			beanB.setProClassCode( beanA.getProClassCode() ); 
 		}

 		/* Mapping: "proDiscPerc" -> "proDiscPerc" */
		if(  !Utils.isEmpty( beanA.getProDiscPerc() )  ){
 			beanB.setProDiscPerc( beanA.getProDiscPerc() ); 
 		}

 		/* Mapping: "proADesc" -> "proADesc" */
		if(  !Utils.isEmpty( beanA.getProADesc() )  ){
 			beanB.setProADesc( beanA.getProADesc() ); 
 		}

 		/* Mapping: "proEDesc" -> "proEDesc" */
		if(  !Utils.isEmpty( beanA.getProEDesc() )  ){
 			beanB.setProEDesc( beanA.getProEDesc() ); 
 		}

 		/* Mapping: "proStartDate" -> "proStartDate" */
		if(  !Utils.isEmpty( beanA.getProStartDate() )  ){
 			beanB.setProStartDate( beanA.getProStartDate() ); 
 		}

 		/* Mapping: "proEndDate" -> "proEndDate" */
		if(  !Utils.isEmpty( beanA.getProEndDate() )  ){
 			beanB.setProEndDate( beanA.getProEndDate() ); 
 		}
		
		/* Mapping: "ProClassCode" -> "ProClassCode" */
		if(  !Utils.isEmpty( beanA.getProClassCode() )  ){
 			beanB.setProClassCode( beanA.getProClassCode() ); 
 		}
		
 		/* Mapping: "proPreparedBy" -> "proPreparedBy" */
		if(  !Utils.isEmpty( beanA.getProPreparedBy() )  ){
 			beanB.setProPreparedBy( beanA.getProPreparedBy() ); 
 		}

 		/* Mapping: "proPreparedDate" -> "proPreparedDate" */
		if(  !Utils.isEmpty( beanA.getProPreparedDate() )  ){
 			beanB.setProPreparedDate( beanA.getProPreparedDate() ); 
 		}

 		/* Mapping: "proModifiedBy" -> "proModifiedBy" */
		if(  !Utils.isEmpty( beanA.getProModifiedBy() )  ){
 			beanB.setProModifiedBy( beanA.getProModifiedBy() ); 
 		}

 		/* Mapping: "proModifiedDate" -> "proModifiedDate" */
		if(  !Utils.isEmpty( beanA.getProModifiedDate() )  ){
 			beanB.setProModifiedDate( beanA.getProModifiedDate() ); 
 		}
		

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.dao.model.TMasPromotionalCode initializeDeepVO( com.rsaame.pas.vo.svc.TMasPromotionalCodeVO beanA, com.rsaame.pas.dao.model.TMasPromotionalCode beanB ){
                           		return beanB;
	}
}
