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
 * <li>com.rsaame.pas.vo.svc.TTrnUwQuestionsVOHolder</li>
 * <li>com.rsaame.pas.dao.model.TTrnUwQuestionsQuo</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( TTrnUwQuestionsVOHolderToTTrnUwQuestionsQuo.class )</code>.
 */
public class TTrnUwQuestionsVOHolderToTTrnUwQuestionsQuo extends BaseBeanToBeanMapper<com.rsaame.pas.vo.svc.TTrnUwQuestionsVOHolder, com.rsaame.pas.dao.model.TTrnUwQuestionsQuo>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public TTrnUwQuestionsVOHolderToTTrnUwQuestionsQuo(){
		super();
	}

	public TTrnUwQuestionsVOHolderToTTrnUwQuestionsQuo( com.rsaame.pas.vo.svc.TTrnUwQuestionsVOHolder src, com.rsaame.pas.dao.model.TTrnUwQuestionsQuo dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.dao.model.TTrnUwQuestionsQuo mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.dao.model.TTrnUwQuestionsQuo) Utils.newInstance( "com.rsaame.pas.dao.model.TTrnUwQuestionsQuo" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.vo.svc.TTrnUwQuestionsVOHolder beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.dao.model.TTrnUwQuestionsQuo beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;
		
		/* Mapping: "uqtPolPolicyId" -> "id.uqtPolPolicyId" */
		if(  !Utils.isEmpty( beanA.getUqtPolPolicyId() )  ){
 			beanB.getId().setUqtPolPolicyId( beanA.getUqtPolPolicyId() ); 
 		}

 		/* Mapping: "uqtPolEndtId" -> "id.uqtPolEndtId" */
		if(  !Utils.isEmpty( beanA.getUqtPolEndtId() )  ){
 			beanB.getId().setUqtPolEndtId( beanA.getUqtPolEndtId() ); 
 		}

 		/* Mapping: "uqtUwqCode" -> "id.uqtUwqCode" */
		if(  !Utils.isEmpty( beanA.getUqtUwqCode() )  ){
 			beanB.getId().setUqtUwqCode( beanA.getUqtUwqCode() ); 
 		}

 		/* Mapping: "uqtLocId" -> "id.uqtLocId" */
		if(  !Utils.isEmpty( beanA.getUqtLocId() )  ){
 			beanB.getId().setUqtLocId( beanA.getUqtLocId() ); 
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
	private static com.rsaame.pas.dao.model.TTrnUwQuestionsQuo initializeDeepVO( com.rsaame.pas.vo.svc.TTrnUwQuestionsVOHolder beanA, com.rsaame.pas.dao.model.TTrnUwQuestionsQuo beanB ){
  		BeanUtils.initializeBeanField( "id", beanB );
                        		return beanB;
	}
}
