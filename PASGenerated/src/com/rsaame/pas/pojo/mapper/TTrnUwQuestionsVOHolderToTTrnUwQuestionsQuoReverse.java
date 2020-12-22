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
 * <li>com.rsaame.pas.dao.model.TTrnUwQuestionsQuo</li>
 * <li>com.rsaame.pas.vo.svc.TTrnUwQuestionsVOHolder</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( TTrnUwQuestionsVOHolderToTTrnUwQuestionsQuoReverse.class )</code>.
 */
public class TTrnUwQuestionsVOHolderToTTrnUwQuestionsQuoReverse extends BaseBeanToBeanMapper<com.rsaame.pas.dao.model.TTrnUwQuestionsQuo, com.rsaame.pas.vo.svc.TTrnUwQuestionsVOHolder>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public TTrnUwQuestionsVOHolderToTTrnUwQuestionsQuoReverse(){
		super();
	}

	public TTrnUwQuestionsVOHolderToTTrnUwQuestionsQuoReverse( com.rsaame.pas.dao.model.TTrnUwQuestionsQuo src, com.rsaame.pas.vo.svc.TTrnUwQuestionsVOHolder dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.svc.TTrnUwQuestionsVOHolder mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.svc.TTrnUwQuestionsVOHolder) Utils.newInstance( "com.rsaame.pas.vo.svc.TTrnUwQuestionsVOHolder" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.dao.model.TTrnUwQuestionsQuo beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.svc.TTrnUwQuestionsVOHolder beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;
		
		/* Mapping: "id.uqtPolPolicyId" -> "uqtPolPolicyId" */
		if(  !Utils.isEmpty( beanA.getId() ) && !Utils.isEmpty( beanA.getId().getUqtPolPolicyId() )  ){
 			beanB.setUqtPolPolicyId( beanA.getId().getUqtPolPolicyId() ); 
 		}

 		/* Mapping: "id.uqtPolEndtId" -> "uqtPolEndtId" */
		if(  !Utils.isEmpty( beanA.getId() ) && !Utils.isEmpty( beanA.getId().getUqtPolEndtId() )  ){
 			beanB.setUqtPolEndtId( beanA.getId().getUqtPolEndtId() ); 
 		}

 		/* Mapping: "id.uqtUwqCode" -> "uqtUwqCode" */
		if(  !Utils.isEmpty( beanA.getId() ) && !Utils.isEmpty( beanA.getId().getUqtUwqCode() )  ){
 			beanB.setUqtUwqCode( beanA.getId().getUqtUwqCode() ); 
 		}

 		/* Mapping: "id.uqtLocId" -> "uqtLocId" */
		if(  !Utils.isEmpty( beanA.getId() ) && !Utils.isEmpty( beanA.getId().getUqtLocId() )  ){
 			beanB.setUqtLocId( beanA.getId().getUqtLocId() ); 
 		}

 		/* Mapping: "uqtUwqAnswer" -> "uqtUwqAnswer" */
		if(  !Utils.isEmpty( beanA.getUqtUwqAnswer() )  ){
 			beanB.setUqtUwqAnswer( beanA.getUqtUwqAnswer() ); 
 		}

 		/* Mapping: "uqtValidityStartDate" -> "uqtValidityStartDate" */
		if(  !Utils.isEmpty( beanA.getUqtValidityStartDate() )  ){
 			beanB.setUqtValidityStartDate( beanA.getUqtValidityStartDate() ); 
 		}

 		/* Mapping: "uqtValidityExpiryDate" -> "uqtValidityExpiryDate" */
		if(  !Utils.isEmpty( beanA.getUqtValidityExpiryDate() )  ){
 			beanB.setUqtValidityExpiryDate( beanA.getUqtValidityExpiryDate() ); 
 		}

 		/* Mapping: "uqtPreparedBy" -> "uqtPreparedBy" */
		if(  !Utils.isEmpty( beanA.getUqtPreparedBy() )  ){
 			beanB.setUqtPreparedBy( beanA.getUqtPreparedBy() ); 
 		}

 		/* Mapping: "uqtPreparedDt" -> "uqtPreparedDt" */
		if(  !Utils.isEmpty( beanA.getUqtPreparedDt() )  ){
 			beanB.setUqtPreparedDt( beanA.getUqtPreparedDt() ); 
 		}

 		/* Mapping: "uqtModifiedBy" -> "uqtModifiedBy" */
		if(  !Utils.isEmpty( beanA.getUqtModifiedBy() )  ){
 			beanB.setUqtModifiedBy( beanA.getUqtModifiedBy() ); 
 		}

 		/* Mapping: "uqtModifiedDt" -> "uqtModifiedDt" */
		if(  !Utils.isEmpty( beanA.getUqtModifiedDt() )  ){
 			beanB.setUqtModifiedDt( beanA.getUqtModifiedDt() ); 
 		}

 		/* Mapping: "uqtStatus" -> "uqtStatus" */
		if(  !Utils.isEmpty( beanA.getUqtStatus() )  ){
 			beanB.setUqtStatus( beanA.getUqtStatus() ); 
 		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.svc.TTrnUwQuestionsVOHolder initializeDeepVO( com.rsaame.pas.dao.model.TTrnUwQuestionsQuo beanA, com.rsaame.pas.vo.svc.TTrnUwQuestionsVOHolder beanB ){
                         		return beanB;
	}
}
