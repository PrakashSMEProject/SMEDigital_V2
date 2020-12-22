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
 * <li>com.rsaame.pas.dao.model.TTrnPolicyQuo</li>
 * <li>com.rsaame.pas.dao.model.TTrnPremiumQuo</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( TTrnPolicyQuoToTTrnPremiumQuoMapper.class )</code>.
 */
public class TTrnPolicyQuoToTTrnPremiumQuoMapper extends BaseBeanToBeanMapper<com.rsaame.pas.dao.model.TTrnPolicyQuo, com.rsaame.pas.dao.model.TTrnPremiumQuo>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public TTrnPolicyQuoToTTrnPremiumQuoMapper(){
		super();
	}

	public TTrnPolicyQuoToTTrnPremiumQuoMapper( com.rsaame.pas.dao.model.TTrnPolicyQuo src, com.rsaame.pas.dao.model.TTrnPremiumQuo dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.dao.model.TTrnPremiumQuo mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.dao.model.TTrnPremiumQuo) Utils.newInstance( "com.rsaame.pas.dao.model.TTrnPremiumQuo" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.dao.model.TTrnPolicyQuo beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.dao.model.TTrnPremiumQuo beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);

		/* Mapping: "id.polPolicyId" -> "id.prmPolicyId" */
		if(  !Utils.isEmpty( beanA.getId() ) && !Utils.isEmpty( beanA.getId().getPolPolicyId() )  ){
 			beanB.getId().setPrmPolicyId( beanA.getId().getPolPolicyId() ); 
 		}

 		/* Mapping: "polClassCode" -> "prmClCode" */
		if(  !Utils.isEmpty( beanA.getPolClassCode() )  ){
 			beanB.setPrmClCode( beanA.getPolClassCode() ); 
 		}

 		/* Mapping: "polPolicyType" -> "prmPtCode" */
		if(  !Utils.isEmpty( beanA.getPolPolicyType() )  ){
 			beanB.setPrmPtCode( beanA.getPolPolicyType() ); 
 		}

 		/* Mapping: "id.polEndtId" -> "prmEndtId" */
		if(  !Utils.isEmpty( beanA.getId() ) && !Utils.isEmpty( beanA.getId().getPolEndtId() )  ){
 			beanB.setPrmEndtId( beanA.getId().getPolEndtId() ); 
 		}

 		/* Mapping: "polValidityStartDate" -> "id.prmValidityStartDate" */
		if(  !Utils.isEmpty( beanA.getPolValidityStartDate() )  ){
 			beanB.getId().setPrmValidityStartDate( beanA.getPolValidityStartDate() ); 
 		}

 		/* Mapping: "polValidityExpiryDate" -> "prmValidityExpiryDate" */
		if(  !Utils.isEmpty( beanA.getPolValidityExpiryDate() )  ){
 			beanB.setPrmValidityExpiryDate( beanA.getPolValidityExpiryDate() ); 
 		}

 		/* Mapping: "polEffectiveDate" -> "prmEffectiveDate" */
		if(  !Utils.isEmpty( beanA.getPolEffectiveDate() )  ){
 			beanB.setPrmEffectiveDate( beanA.getPolEffectiveDate() ); 
 		}

 		/* Mapping: "polExpiryDate" -> "prmExpiryDate" */
		if(  !Utils.isEmpty( beanA.getPolExpiryDate() )  ){
 			beanB.setPrmExpiryDate( beanA.getPolExpiryDate() ); 
 		}

 		/* Mapping: "polPreparedDt" -> "prmPreparedDt" */
		if(  !Utils.isEmpty( beanA.getPolPreparedDt() )  ){
 			beanB.setPrmPreparedDt( beanA.getPolPreparedDt() ); 
 		}

 		/* Mapping: "polModifiedDt" -> "prmModifiedDt" */
		if(  !Utils.isEmpty( beanA.getPolModifiedDt() )  ){
 			beanB.setPrmModifiedDt( beanA.getPolModifiedDt() ); 
 		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.dao.model.TTrnPremiumQuo initializeDeepVO( com.rsaame.pas.dao.model.TTrnPolicyQuo beanA, com.rsaame.pas.dao.model.TTrnPremiumQuo beanB ){
  		BeanUtils.initializeBeanField( "id", beanB );
                    		return beanB;
	}
}
