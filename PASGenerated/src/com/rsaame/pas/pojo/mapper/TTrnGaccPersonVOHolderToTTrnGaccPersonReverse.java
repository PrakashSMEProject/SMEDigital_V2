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
 * <li>com.rsaame.pas.dao.model.TTrnGaccPersonQuo</li>
 * <li>com.rsaame.pas.vo.svc.TTrnGaccPersonVOHolder</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( TTrnGaccPersonVOHolderToTTrnGaccPersonReverse.class )</code>.
 */
public class TTrnGaccPersonVOHolderToTTrnGaccPersonReverse extends BaseBeanToBeanMapper<com.rsaame.pas.dao.model.TTrnGaccPersonQuo, com.rsaame.pas.vo.svc.TTrnGaccPersonVOHolder>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public TTrnGaccPersonVOHolderToTTrnGaccPersonReverse(){
		super();
	}

	public TTrnGaccPersonVOHolderToTTrnGaccPersonReverse( com.rsaame.pas.dao.model.TTrnGaccPersonQuo src, com.rsaame.pas.vo.svc.TTrnGaccPersonVOHolder dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.svc.TTrnGaccPersonVOHolder mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.svc.TTrnGaccPersonVOHolder) Utils.newInstance( "com.rsaame.pas.vo.svc.TTrnGaccPersonVOHolder" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.dao.model.TTrnGaccPersonQuo beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.svc.TTrnGaccPersonVOHolder beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;
		
		/* Mapping: "id.gprId" -> "gprId" */
		if(  !Utils.isEmpty( beanA.getId() ) && !Utils.isEmpty( beanA.getId().getGprId() )  ){
 			beanB.setGprId( beanA.getId().getGprId() ); 
 		}

 		/* Mapping: "id.gprValidityStartDate" -> "gprValidityStartDate" */
		if(  !Utils.isEmpty( beanA.getId() ) && !Utils.isEmpty( beanA.getId().getGprValidityStartDate() )  ){
 			beanB.setGprValidityStartDate( beanA.getId().getGprValidityStartDate() ); 
 		}

 		/* Mapping: "gprBasicRiskId" -> "gprBasicRiskId" */
		if(  !Utils.isEmpty( beanA.getGprBasicRiskId() )  ){
 			beanB.setGprBasicRiskId( beanA.getGprBasicRiskId() ); 
 		}

 		/* Mapping: "gprRskCode" -> "gprRskCode" */
		if(  !Utils.isEmpty( beanA.getGprRskCode() )  ){
 			beanB.setGprRskCode( beanA.getGprRskCode() ); 
 		}

 		/* Mapping: "gprRtCode" -> "gprRtCode" */
		if(  !Utils.isEmpty( beanA.getGprRtCode() )  ){
 			beanB.setGprRtCode( beanA.getGprRtCode() ); 
 		}

 		/* Mapping: "gprRcCode" -> "gprRcCode" */
		if(  !Utils.isEmpty( beanA.getGprRcCode() )  ){
 			beanB.setGprRcCode( beanA.getGprRcCode() ); 
 		}

 		/* Mapping: "gprAName" -> "gprAName" */
		if(  !Utils.isEmpty( beanA.getGprAName() )  ){
 			beanB.setGprAName( beanA.getGprAName() ); 
 		}

 		/* Mapping: "gprEName" -> "gprEName" */
		if(  !Utils.isEmpty( beanA.getGprEName() )  ){
 			beanB.setGprEName( beanA.getGprEName() ); 
 		}

 		/* Mapping: "gprAAddress1" -> "gprAAddress1" */
		if(  !Utils.isEmpty( beanA.getGprAAddress1() )  ){
 			beanB.setGprAAddress1( beanA.getGprAAddress1() ); 
 		}

 		/* Mapping: "gprEAddress1" -> "gprEAddress1" */
		if(  !Utils.isEmpty( beanA.getGprEAddress1() )  ){
 			beanB.setGprEAddress1( beanA.getGprEAddress1() ); 
 		}

 		/* Mapping: "gprAAddress2" -> "gprAAddress2" */
		if(  !Utils.isEmpty( beanA.getGprAAddress2() )  ){
 			beanB.setGprAAddress2( beanA.getGprAAddress2() ); 
 		}

 		/* Mapping: "gprEAddress2" -> "gprEAddress2" */
		if(  !Utils.isEmpty( beanA.getGprEAddress2() )  ){
 			beanB.setGprEAddress2( beanA.getGprEAddress2() ); 
 		}

 		/* Mapping: "gprAAddress3" -> "gprAAddress3" */
		if(  !Utils.isEmpty( beanA.getGprAAddress3() )  ){
 			beanB.setGprAAddress3( beanA.getGprAAddress3() ); 
 		}

 		/* Mapping: "gprEAddress3" -> "gprEAddress3" */
		if(  !Utils.isEmpty( beanA.getGprEAddress3() )  ){
 			beanB.setGprEAddress3( beanA.getGprEAddress3() ); 
 		}

 		/* Mapping: "gprZip" -> "gprZip" */
		if(  !Utils.isEmpty( beanA.getGprZip() )  ){
 			beanB.setGprZip( beanA.getGprZip() ); 
 		}

 		/* Mapping: "gprTelephone" -> "gprTelephone" */
		if(  !Utils.isEmpty( beanA.getGprTelephone() )  ){
 			beanB.setGprTelephone( beanA.getGprTelephone() ); 
 		}

 		/* Mapping: "gprFax" -> "gprFax" */
		if(  !Utils.isEmpty( beanA.getGprFax() )  ){
 			beanB.setGprFax( beanA.getGprFax() ); 
 		}

 		/* Mapping: "gprGsm" -> "gprGsm" */
		if(  !Utils.isEmpty( beanA.getGprGsm() )  ){
 			beanB.setGprGsm( beanA.getGprGsm() ); 
 		}

 		/* Mapping: "gprEmail" -> "gprEmail" */
		if(  !Utils.isEmpty( beanA.getGprEmail() )  ){
 			beanB.setGprEmail( beanA.getGprEmail() ); 
 		}

 		/* Mapping: "gprAge" -> "gprAge" */
		if(  !Utils.isEmpty( beanA.getGprAge() )  ){
 			beanB.setGprAge( beanA.getGprAge() ); 
 		}

 		/* Mapping: "gprOcCode" -> "gprOcCode" */
		if(  !Utils.isEmpty( beanA.getGprOcCode() )  ){
 			beanB.setGprOcCode( beanA.getGprOcCode() ); 
 		}

 		/* Mapping: "gprSumInsured" -> "gprSumInsured" */
		if(  !Utils.isEmpty( beanA.getGprSumInsured() )  ){
 			beanB.setGprSumInsured( beanA.getGprSumInsured() ); 
 		}

 		/* Mapping: "gprStatus" -> "gprStatus" */
		if(  !Utils.isEmpty( beanA.getGprStatus() )  ){
 			beanB.setGprStatus( beanA.getGprStatus() ); 
 		}

 		/* Mapping: "gprValidityExpiryDate" -> "gprValidityExpiryDate" */
		if(  !Utils.isEmpty( beanA.getGprValidityExpiryDate() )  ){
 			beanB.setGprValidityExpiryDate( beanA.getGprValidityExpiryDate() ); 
 		}

 		/* Mapping: "gprEndtId" -> "gprEndtId" */
		if(  !Utils.isEmpty( beanA.getGprEndtId() )  ){
 			beanB.setGprEndtId( beanA.getGprEndtId() ); 
 		}

 		/* Mapping: "gprRiRskCode" -> "gprRiRskCode" */
		if(  !Utils.isEmpty( beanA.getGprRiRskCode() )  ){
 			beanB.setGprRiRskCode( beanA.getGprRiRskCode() ); 
 		}

 		/* Mapping: "gprBasicRskCode" -> "gprBasicRskCode" */
		if(  !Utils.isEmpty( beanA.getGprBasicRskCode() )  ){
 			beanB.setGprBasicRskCode( beanA.getGprBasicRskCode() ); 
 		}

 		/* Mapping: "gprGender" -> "gprGender" */
		if(  !Utils.isEmpty( beanA.getGprGender() )  ){
 			beanB.setGprGender( beanA.getGprGender() ); 
 		}

 		/* Mapping: "gprRelation" -> "gprRelation" */
		if(  !Utils.isEmpty( beanA.getGprRelation() )  ){
 			beanB.setGprRelation( beanA.getGprRelation() ); 
 		}

 		/* Mapping: "gprRetroactiveDate" -> "gprRetroactiveDate" */
		if(  !Utils.isEmpty( beanA.getGprRetroactiveDate() )  ){
 			beanB.setGprRetroactiveDate( beanA.getGprRetroactiveDate() ); 
 		}

 		/* Mapping: "gprSalary" -> "gprSalary" */
		if(  !Utils.isEmpty( beanA.getGprSalary() )  ){
 			beanB.setGprSalary( beanA.getGprSalary() ); 
 		}

 		/* Mapping: "gprPreparedBy" -> "gprPreparedBy" */
		if(  !Utils.isEmpty( beanA.getGprPreparedBy() )  ){
 			beanB.setGprPreparedBy( beanA.getGprPreparedBy() ); 
 		}

 		/* Mapping: "gprPreparedDt" -> "gprPreparedDt" */
		if(  !Utils.isEmpty( beanA.getGprPreparedDt() )  ){
 			beanB.setGprPreparedDt( beanA.getGprPreparedDt() ); 
 		}

 		/* Mapping: "gprModifiedBy" -> "gprModifiedBy" */
		if(  !Utils.isEmpty( beanA.getGprModifiedBy() )  ){
 			beanB.setGprModifiedBy( beanA.getGprModifiedBy() ); 
 		}

 		/* Mapping: "gprModifiedDt" -> "gprModifiedDt" */
		if(  !Utils.isEmpty( beanA.getGprModifiedDt() )  ){
 			beanB.setGprModifiedDt( beanA.getGprModifiedDt() ); 
 		}

		/* Mapping: "getGprStartDate" -> "getGprStartDate" */
		if(  !Utils.isEmpty( beanA.getGprStartDate() )  ){
 			beanB.setGprStartDate( beanA.getGprStartDate() ); 
 		}
 		/* Mapping: "gprEndDate" -> "gprEndDate" */
		if(  !Utils.isEmpty( beanA.getGprEndDate() )  ){
 			beanB.setGprEndDate( beanA.getGprEndDate() ); 
 		}

 		/* Mapping: "gprDateOfBirth" -> "gprDateOfBirth" */
		if(  !Utils.isEmpty( beanA.getGprDateOfBirth() )  ){
 			beanB.setGprDateOfBirth( beanA.getGprDateOfBirth() ); 
 		}

 		/* Mapping: "gprDescription" -> "gprDescription" */
		if(  !Utils.isEmpty( beanA.getGprDescription() )  ){
 			beanB.setGprDescription( beanA.getGprDescription() ); 
 		}

 		/* Mapping: "gprPersonId" -> "gprPersonId" */
		if(  !Utils.isEmpty( beanA.getGprPersonId() )  ){
 			beanB.setGprPersonId( beanA.getGprPersonId() ); 
 		}

 		/* Mapping: "gprDateOfJoining" -> "gprDateOfJoining" */
		if(  !Utils.isEmpty( beanA.getGprDateOfJoining() )  ){
 			beanB.setGprDateOfJoining( beanA.getGprDateOfJoining() ); 
 		}

 		/* Mapping: "gprBldId" -> "gprBldId" */
		if(  !Utils.isEmpty( beanA.getGprBldId() )  ){
 			beanB.setGprBldId( beanA.getGprBldId() ); 
 		}

 		/* Mapping: "gprTradeGroup" -> "gprTradeGroup" */
		if(  !Utils.isEmpty( beanA.getGprTradeGroup() )  ){
 			beanB.setGprTradeGroup( beanA.getGprTradeGroup() ); 
 		}

 		/* Mapping: "gprAgrLmt" -> "gprAgrLmt" */
		if(  !Utils.isEmpty( beanA.getGprAgrLmt() )  ){
 			beanB.setGprAgrLmt( beanA.getGprAgrLmt() ); 
 		}

 		/* Mapping: "gprNtyEDesc" -> "gprNtyEDesc" */
		if(  !Utils.isEmpty( beanA.getGprNtyEDesc() )  ){
 			beanB.setGprNtyEDesc( beanA.getGprNtyEDesc() ); 
 		}
		
		if(  !Utils.isEmpty( beanA.getGprPolicyId() )  ){
 			beanB.setGprPolicyId( beanA.getGprPolicyId() ); 
 		}
   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.svc.TTrnGaccPersonVOHolder initializeDeepVO( com.rsaame.pas.dao.model.TTrnGaccPersonQuo beanA, com.rsaame.pas.vo.svc.TTrnGaccPersonVOHolder beanB ){
                                                                                         		return beanB;
	}
}
