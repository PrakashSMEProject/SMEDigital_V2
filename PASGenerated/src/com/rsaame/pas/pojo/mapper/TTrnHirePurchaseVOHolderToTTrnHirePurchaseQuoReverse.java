package com.rsaame.pas.pojo.mapper;

import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.BeanUtils;
import com.mindtree.ruc.cmn.utils.Utils;

public class TTrnHirePurchaseVOHolderToTTrnHirePurchaseQuoReverse extends BaseBeanToBeanMapper<com.rsaame.pas.dao.model.TTrnHirePurchaseQuo, com.rsaame.pas.vo.svc.TTrnHirePurchaseVOHolder>{

	private final Logger log = Logger.getLogger( this.getClass() );	

	public TTrnHirePurchaseVOHolderToTTrnHirePurchaseQuoReverse(){
		super();
	}

	public TTrnHirePurchaseVOHolderToTTrnHirePurchaseQuoReverse( com.rsaame.pas.dao.model.TTrnHirePurchaseQuo src, com.rsaame.pas.vo.svc.TTrnHirePurchaseVOHolder dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.svc.TTrnHirePurchaseVOHolder mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.svc.TTrnHirePurchaseVOHolder) Utils.newInstance( "com.rsaame.pas.vo.svc.TTrnHirePurchaseVOHolder" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.dao.model.TTrnHirePurchaseQuo beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.svc.TTrnHirePurchaseVOHolder beanB = dest;
			
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
		if(  !Utils.isEmpty( beanA.getId().getHpCode() )  ){
			beanB.setHpCode( beanA.getId().getHpCode() ); 
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
		if(  !Utils.isEmpty( beanA.getId().getHpPolicyId() )  ){
			beanB.setHpPolicyId(beanA.getId().getHpPolicyId() ); 
		}

		/* Mapping: "gprEAddress2" -> "gprEAddress2" */
		if(  !Utils.isEmpty( beanA.getId().getHpEndtId() )  ){
			beanB.setHpEndtId(beanA.getId().getHpEndtId() ); 
		}
		
		/* Mapping: "gprEAddress2" -> "gprEAddress2" */
		if(  !Utils.isEmpty( beanA.getId().getHpValidityStartDate() )  ){
			beanB.setHpValidityStartDate(beanA.getId().getHpValidityStartDate() ); 
		}
		
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.svc.TTrnHirePurchaseVOHolder initializeDeepVO( com.rsaame.pas.dao.model.TTrnHirePurchaseQuo beanA, com.rsaame.pas.vo.svc.TTrnHirePurchaseVOHolder beanB ){
		BeanUtils.initializeBeanField( "id", beanB );
                                                                                     		return beanB;
	}

}
