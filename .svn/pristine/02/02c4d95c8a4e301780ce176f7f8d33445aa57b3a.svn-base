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
 * <li>com.rsaame.pas.dao.model.TTrnPremiumQuo</li>
 * <li>com.rsaame.pas.dao.model.TTrnPolicyQuo</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( TTrnPolicyQuoToTTrnPremiumQuoMapperReverse.class )</code>.
 */
public class TTrnPolicyQuoToTTrnPremiumQuoMapperReverse extends BaseBeanToBeanMapper<com.rsaame.pas.dao.model.TTrnPremiumQuo, com.rsaame.pas.dao.model.TTrnPolicyQuo>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public TTrnPolicyQuoToTTrnPremiumQuoMapperReverse(){
		super();
	}

	public TTrnPolicyQuoToTTrnPremiumQuoMapperReverse( com.rsaame.pas.dao.model.TTrnPremiumQuo src, com.rsaame.pas.dao.model.TTrnPolicyQuo dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.dao.model.TTrnPolicyQuo mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.dao.model.TTrnPolicyQuo) Utils.newInstance( "com.rsaame.pas.dao.model.TTrnPolicyQuo" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.dao.model.TTrnPremiumQuo beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.dao.model.TTrnPolicyQuo beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);

		/* Mapping: "id.prmPolicyId" -> "id.polPolicyId" */
		if(  !Utils.isEmpty( beanA.getId() ) && !Utils.isEmpty( beanA.getId().getPrmPolicyId() )  ){
 			beanB.getId().setPolPolicyId( beanA.getId().getPrmPolicyId() ); 
 		}

 		/* Mapping: "prmClCode" -> "polClassCode" */
		if(  !Utils.isEmpty( beanA.getPrmClCode() )  ){
 			beanB.setPolClassCode( beanA.getPrmClCode() ); 
 		}

 		/* Mapping: "prmPtCode" -> "polPolicyType" */
		if(  !Utils.isEmpty( beanA.getPrmPtCode() )  ){
 			beanB.setPolPolicyType( beanA.getPrmPtCode() ); 
 		}

 		/* Mapping: "prmEndtId" -> "id.polEndtId" */
		if(  !Utils.isEmpty( beanA.getPrmEndtId() )  ){
 			beanB.getId().setPolEndtId( beanA.getPrmEndtId() ); 
 		}

 		/* Mapping: "id.prmValidityStartDate" -> "polValidityStartDate" */
		if(  !Utils.isEmpty( beanA.getId() ) && !Utils.isEmpty( beanA.getId().getPrmValidityStartDate() )  ){
 			beanB.setPolValidityStartDate( beanA.getId().getPrmValidityStartDate() ); 
 		}

 		/* Mapping: "prmValidityExpiryDate" -> "polValidityExpiryDate" */
		if(  !Utils.isEmpty( beanA.getPrmValidityExpiryDate() )  ){
 			beanB.setPolValidityExpiryDate( beanA.getPrmValidityExpiryDate() ); 
 		}

 		/* Mapping: "prmEffectiveDate" -> "polEffectiveDate" */
		if(  !Utils.isEmpty( beanA.getPrmEffectiveDate() )  ){
 			beanB.setPolEffectiveDate( beanA.getPrmEffectiveDate() ); 
 		}

 		/* Mapping: "prmExpiryDate" -> "polExpiryDate" */
		if(  !Utils.isEmpty( beanA.getPrmExpiryDate() )  ){
 			beanB.setPolExpiryDate( beanA.getPrmExpiryDate() ); 
 		}

 		/* Mapping: "prmPreparedDt" -> "polPreparedDt" */
		if(  !Utils.isEmpty( beanA.getPrmPreparedDt() )  ){
 			beanB.setPolPreparedDt( beanA.getPrmPreparedDt() ); 
 		}

 		/* Mapping: "prmModifiedDt" -> "polModifiedDt" */
		if(  !Utils.isEmpty( beanA.getPrmModifiedDt() )  ){
 			beanB.setPolModifiedDt( beanA.getPrmModifiedDt() ); 
 		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.dao.model.TTrnPolicyQuo initializeDeepVO( com.rsaame.pas.dao.model.TTrnPremiumQuo beanA, com.rsaame.pas.dao.model.TTrnPolicyQuo beanB ){
  		BeanUtils.initializeBeanField( "id", beanB );
                    		return beanB;
	}
}
