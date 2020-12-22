package com.rsaame.pas.pojo.mapper;

import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.BeanUtils;
import com.mindtree.ruc.cmn.utils.Utils;

public class TTrnCoinsuranceVOHolderToTTrnCoinsuranceQuo extends BaseBeanToBeanMapper<com.rsaame.pas.vo.svc.TTrnCoInsuranceVOHolder, com.rsaame.pas.dao.model.TTrnCoinsuranceQuo>
{
	
  	private final Logger log = Logger.getLogger( this.getClass() );	

	public TTrnCoinsuranceVOHolderToTTrnCoinsuranceQuo(){
		super();
	}

	public TTrnCoinsuranceVOHolderToTTrnCoinsuranceQuo( com.rsaame.pas.vo.svc.TTrnCoInsuranceVOHolder src, com.rsaame.pas.dao.model.TTrnCoinsuranceQuo dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.dao.model.TTrnCoinsuranceQuo mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.dao.model.TTrnCoinsuranceQuo) Utils.newInstance( "com.rsaame.pas.dao.model.TTrnCoinsuranceQuo" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.vo.svc.TTrnCoInsuranceVOHolder beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.dao.model.TTrnCoinsuranceQuo beanB = dest;
			
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
		if(  !Utils.isEmpty( beanA.getCoiPercentage())  ){
			beanB.setCoiPercentage( beanA.getCoiPercentage() ); 
		}

		/* Mapping: "gprRskCode" -> "gprRskCode" */
		if(  !Utils.isEmpty( beanA.getCoiCommissionPerc())  ){
			beanB.setCoiCommissionPerc( beanA.getCoiCommissionPerc() ); 
		}

		/* Mapping: "gprRtCode" -> "gprRtCode" */
		if(  !Utils.isEmpty( beanA.getCoiValidityExpiryDate() )  ){
			beanB.setCoiValidityExpiryDate( beanA.getCoiValidityExpiryDate() ); 
		}

		/* Mapping: "gprRcCode" -> "gprRcCode" */
		if(  !Utils.isEmpty( beanA.getCoiLeadFlag() )  ){
			beanB.setCoiLeadFlag( beanA.getCoiLeadFlag() ); 
		}

		/* Mapping: "gprAName" -> "gprAName" */
		if(  !Utils.isEmpty( beanA.getCoiPreparedBy() )  ){
			beanB.setCoiPreparedBy( beanA.getCoiPreparedBy() ); 
		}

		/* Mapping: "gprEName" -> "gprEName" */
		if(  !Utils.isEmpty( beanA.getCoiPreparedDt() )  ){
			beanB.setCoiPreparedDt( beanA.getCoiPreparedDt() ); 
		}

		/* Mapping: "gprAAddress1" -> "gprAAddress1" */
		if(  !Utils.isEmpty( beanA.getCoiModifiedBy() )  ){
			beanB.setCoiModifiedBy( beanA.getCoiModifiedBy() ); 
		}

		/* Mapping: "gprEAddress1" -> "gprEAddress1" */
		if(  !Utils.isEmpty( beanA.getCoiModifiedDt() )  ){
			beanB.setCoiModifiedDt( beanA.getCoiModifiedDt() ); 
		}

		/* Mapping: "gprAAddress2" -> "gprAAddress2" */
		if(  !Utils.isEmpty( beanA.getCoiEndtNo() )  ){
			beanB.setCoiEndtNo( beanA.getCoiEndtNo() ); 
		}

		/* Mapping: "gprEAddress2" -> "gprEAddress2" */
		if(  !Utils.isEmpty( beanA.getCoiEndtId() )  ){
			beanB.setCoiEndtId( beanA.getCoiEndtId() ); 
		}

		/* Mapping: "gprAAddress3" -> "gprAAddress3" */
		if(  !Utils.isEmpty( beanA.getCoiCoinsuranceCode() )  ){
			beanB.getId().setCoiCoinsuranceCode( beanA.getCoiCoinsuranceCode() ); 
		}

		/*todo not mapped via mapper */		
		if(  !Utils.isEmpty( beanA.getCoiPolicyId() )  ){
			beanB.getId().setCoiPolicyId( beanA.getCoiPolicyId() ); 
		}
		/*todo not mapped via mapper */
		if(  !Utils.isEmpty( beanA.getCoiValidityStartDate() )  ){
			beanB.getId().setCoiValidityStartDate(  beanA.getCoiValidityStartDate() ); 
		}

		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.dao.model.TTrnCoinsuranceQuo initializeDeepVO( com.rsaame.pas.vo.svc.TTrnCoInsuranceVOHolder beanA, com.rsaame.pas.dao.model.TTrnCoinsuranceQuo beanB ){
		BeanUtils.initializeBeanField( "id", beanB );
                                                                                     		return beanB;
	}



}
