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
 * <li>com.rsaame.pas.dao.model.TTrnPolicy</li>
 * <li>com.rsaame.pas.dao.model.TTrnPolicyComments</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( TTrnPolicyToTTrnCommentsMapper.class )</code>.
 */
public class TTrnPolicyToTTrnCommentsMapper extends BaseBeanToBeanMapper<com.rsaame.pas.dao.model.TTrnPolicy, com.rsaame.pas.dao.model.TTrnPolicyComments>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public TTrnPolicyToTTrnCommentsMapper(){
		super();
	}

	public TTrnPolicyToTTrnCommentsMapper( com.rsaame.pas.dao.model.TTrnPolicy src, com.rsaame.pas.dao.model.TTrnPolicyComments dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.dao.model.TTrnPolicyComments mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.dao.model.TTrnPolicyComments) Utils.newInstance( "com.rsaame.pas.dao.model.TTrnPolicyComments" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.dao.model.TTrnPolicy beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.dao.model.TTrnPolicyComments beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);

		/* Mapping: "id.polPolicyId" -> "pocPolicyId" */
		if(  !Utils.isEmpty( beanA.getId() ) && !Utils.isEmpty( beanA.getId().getPolPolicyId() )  ){
 			beanB.setPocPolicyId( beanA.getId().getPolPolicyId() ); 
 		}

 		/* Mapping: "id.polEndtId" -> "pocEndtId" */
		if(  !Utils.isEmpty( beanA.getId() ) && !Utils.isEmpty( beanA.getId().getPolEndtId() )  ){
 			beanB.setPocEndtId( beanA.getId().getPolEndtId() ); 
 		}

 		/* Mapping: "polReasonCode" -> "pocReasonCode" */
		if(  !Utils.isEmpty( beanA.getPolReasonCode() )  ){
 			beanB.setPocReasonCode( beanA.getPolReasonCode() ); 
 		}

 		/* Mapping: "polStatus" -> "pocPolicyStatus" */
		if(  !Utils.isEmpty( beanA.getPolStatus() )  ){
 			beanB.setPocPolicyStatus( beanA.getPolStatus() ); 
 		}

 		/* Mapping: "polPreparedBy" -> "pocPreparedBy" */
		if(  !Utils.isEmpty( beanA.getPolPreparedBy() )  ){
 			beanB.setPocPreparedBy( beanA.getPolPreparedBy() ); 
 		}

 		/* Mapping: "polPreparedDt" -> "pocPreparedDt" */
		if(  !Utils.isEmpty( beanA.getPolPreparedDt() )  ){
 			beanB.setPocPreparedDt( beanA.getPolPreparedDt() ); 
 		}

 		/* Mapping: "polModifiedBy" -> "pocModifiedBy" */
		if(  !Utils.isEmpty( beanA.getPolModifiedBy() )  ){
 			beanB.setPocModifiedBy( beanA.getPolModifiedBy() ); 
 		}

 		/* Mapping: "polModifiedDt" -> "pocModifiedDt" */
		if(  !Utils.isEmpty( beanA.getPolModifiedDt() )  ){
 			beanB.setPocModifiedDt( beanA.getPolModifiedDt() ); 
 		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.dao.model.TTrnPolicyComments initializeDeepVO( com.rsaame.pas.dao.model.TTrnPolicy beanA, com.rsaame.pas.dao.model.TTrnPolicyComments beanB ){
                 		return beanB;
	}
}
