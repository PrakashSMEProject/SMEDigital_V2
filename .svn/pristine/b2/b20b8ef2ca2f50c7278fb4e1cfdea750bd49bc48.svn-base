package com.rsaame.pas.pojo.mapper;

import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.BeanUtils;
import com.mindtree.ruc.cmn.utils.Utils;

public class TTrnHirePurchaseVOHolderToTTrnHirePurchaseQuo extends BaseBeanToBeanMapper<com.rsaame.pas.vo.svc.TTrnHirePurchaseVOHolder, com.rsaame.pas.dao.model.TTrnHirePurchaseQuo>{

	private final Logger log = Logger.getLogger( this.getClass() );	

	public TTrnHirePurchaseVOHolderToTTrnHirePurchaseQuo(){
		super();
	}

	public TTrnHirePurchaseVOHolderToTTrnHirePurchaseQuo( com.rsaame.pas.vo.svc.TTrnHirePurchaseVOHolder src, com.rsaame.pas.dao.model.TTrnHirePurchaseQuo dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.dao.model.TTrnHirePurchaseQuo mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.dao.model.TTrnHirePurchaseQuo) Utils.newInstance( "com.rsaame.pas.dao.model.TTrnHirePurchaseQuo" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.vo.svc.TTrnHirePurchaseVOHolder beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.dao.model.TTrnHirePurchaseQuo beanB = dest;
			
		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
	
		
		/* Mapping: "gprId" -> "id.gprId" */
		/*if(  !Utils.isEmpty( beanA.getGprId() )  ){
			beanB.getId().setGprId( beanA.getGprId() ); 
		}

		/* Mapping: "gprValidityStartDate" -> "id.gprValidityStartDate" 
		if(  !Utils.isEmpty( beanA.getGprValidityStartDate() )  ){
			beanB.getId().setGprValidityStartDate( beanA.getGprValidityStartDate() ); 
		}*/

		/* Mapping: "gprBasicRiskId" -> "gprBasicRiskId" */
		if(  !Utils.isEmpty( beanA.getHpAmount())  ){
			beanB.setHpAmount( beanA.getHpAmount() ); 
		}

		/* Mapping: "gprRskCode" -> "gprRskCode" */
		if(  !Utils.isEmpty( beanA.getHpExpiryDate())  ){
			beanB.setHpExpiryDate( beanA.getHpExpiryDate() ); 
		}

		/* Mapping: "gprRtCode" -> "gprRtCode" */
		if(  !Utils.isEmpty( beanA.getHpCode() )  ){
			beanB.getId().setHpCode( beanA.getHpCode() ); 
		}

		/* Mapping: "gprRcCode" -> "gprRcCode" */
		if(  !Utils.isEmpty( beanA.getHpValidityExpiryDate() )  ){
			beanB.setHpValidityExpiryDate( beanA.getHpValidityExpiryDate() ); 
		}

		/* Mapping: "gprAName" -> "gprAName" */
		if(  !Utils.isEmpty( beanA.getHpStatus() )  ){
			beanB.setHpStatus( beanA.getHpStatus() ); 
		}

		/* Mapping: "gprEName" -> "gprEName" */
		if(  !Utils.isEmpty( beanA.getHpPreparedBy() )  ){
			beanB.setHpPreparedBy( beanA.getHpPreparedBy() ); 
		}

		/* Mapping: "gprAAddress1" -> "gprAAddress1" */
		if(  !Utils.isEmpty( beanA.getHpPreparedDt() )  ){
			beanB.setHpPreparedDt( beanA.getHpPreparedDt() ); 
		}

		/* Mapping: "gprEAddress1" -> "gprEAddress1" */
		if(  !Utils.isEmpty( beanA.getHpModifiedBy() )  ){
			beanB.setHpModifiedBy( beanA.getHpModifiedBy() ); 
		}

		/* Mapping: "gprAAddress2" -> "gprAAddress2" */
		if(  !Utils.isEmpty( beanA.getHpModifiedDt() )  ){
			beanB.setHpModifiedDt( beanA.getHpModifiedDt() ); 
		}

		/* Mapping: "gprEAddress2" -> "gprEAddress2" */
		if(  !Utils.isEmpty( beanA.getHpPolicyId() )  ){
			beanB.getId().setHpPolicyId(beanA.getHpPolicyId() ); 
		}

		/* Mapping: "gprEAddress2" -> "gprEAddress2" */
		if(  !Utils.isEmpty( beanA.getHpEndtId() )  ){
			beanB.getId().setHpEndtId(beanA.getHpEndtId() ); 
		}
		
		/* Mapping: "gprEAddress2" -> "gprEAddress2" */
		if(  !Utils.isEmpty( beanA.getHpValidityStartDate() )  ){
			beanB.getId().setHpValidityStartDate(beanA.getHpValidityStartDate() ); 
		}
		
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.dao.model.TTrnHirePurchaseQuo initializeDeepVO( com.rsaame.pas.vo.svc.TTrnHirePurchaseVOHolder beanA, com.rsaame.pas.dao.model.TTrnHirePurchaseQuo beanB ){
		BeanUtils.initializeBeanField( "id", beanB );
                                                                                     		return beanB;
	}
	
}
