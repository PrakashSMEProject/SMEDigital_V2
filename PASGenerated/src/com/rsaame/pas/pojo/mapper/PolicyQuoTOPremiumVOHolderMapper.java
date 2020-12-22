       /*
 * THIS IS A GENERATED CLASS. THIS CLASS SHOULD NOT BE EDITED.
 */
package com.rsaame.pas.pojo.mapper;

import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.utils.Utils;

public class PolicyQuoTOPremiumVOHolderMapper extends BaseBeanToBeanMapper<com.rsaame.pas.dao.model.TTrnPolicyQuo, com.rsaame.pas.vo.svc.TTrnPremiumVOHolder>{

	public PolicyQuoTOPremiumVOHolderMapper(){
		super();
	}

	public PolicyQuoTOPremiumVOHolderMapper( com.rsaame.pas.dao.model.TTrnPolicyQuo src, com.rsaame.pas.vo.svc.TTrnPremiumVOHolder dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.svc.TTrnPremiumVOHolder mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.svc.TTrnPremiumVOHolder) Utils.newInstance( "com.rsaame.pas.vo.svc.TTrnPremiumVOHolder" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.dao.model.TTrnPolicyQuo beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.svc.TTrnPremiumVOHolder beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);

		/* Mapping: "id.polPolicyId" -> "id.prmPolicyId" */
		if(  !Utils.isEmpty( beanA.getId() ) && !Utils.isEmpty( beanA.getId().getPolPolicyId() )  ){
 			beanB.setPrmPolicyId( beanA.getId().getPolPolicyId() ); 
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
		// VSD should be set in BaseSaveOperation.
		if(  !Utils.isEmpty( beanA.getPolValidityStartDate() )  ){
 			beanB.setPrmValidityStartDate( beanA.getPolValidityStartDate() ); 
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
	private static com.rsaame.pas.vo.svc.TTrnPremiumVOHolder initializeDeepVO( com.rsaame.pas.dao.model.TTrnPolicyQuo beanA, com.rsaame.pas.vo.svc.TTrnPremiumVOHolder beanB ){
					return beanB;
	}
}
