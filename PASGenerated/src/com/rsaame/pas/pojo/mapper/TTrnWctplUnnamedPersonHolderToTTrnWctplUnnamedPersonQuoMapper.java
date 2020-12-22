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
 * <li>com.rsaame.pas.vo.svc.TTrnWctplUnnamedPersonQuoHolder</li>
 * <li>com.rsaame.pas.dao.model.TTrnWctplUnnamedPersonQuo</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( TTrnWctplUnnamedPersonHolderToTTrnWctplUnnamedPersonQuoMapper.class )</code>.
 */
public class TTrnWctplUnnamedPersonHolderToTTrnWctplUnnamedPersonQuoMapper extends BaseBeanToBeanMapper<com.rsaame.pas.vo.svc.TTrnWctplUnnamedPersonQuoHolder, com.rsaame.pas.dao.model.TTrnWctplUnnamedPersonQuo>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public TTrnWctplUnnamedPersonHolderToTTrnWctplUnnamedPersonQuoMapper(){
		super();
	}

	public TTrnWctplUnnamedPersonHolderToTTrnWctplUnnamedPersonQuoMapper( com.rsaame.pas.vo.svc.TTrnWctplUnnamedPersonQuoHolder src, com.rsaame.pas.dao.model.TTrnWctplUnnamedPersonQuo dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.dao.model.TTrnWctplUnnamedPersonQuo mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.dao.model.TTrnWctplUnnamedPersonQuo) Utils.newInstance( "com.rsaame.pas.dao.model.TTrnWctplUnnamedPersonQuo" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.vo.svc.TTrnWctplUnnamedPersonQuoHolder beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.dao.model.TTrnWctplUnnamedPersonQuo beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;
		
		/* Mapping: "wupId" -> "id.wupId" */
		if(  !Utils.isEmpty( beanA.getWupId() )  ){
 			beanB.getId().setWupId( beanA.getWupId() ); 
 		}

 		/* Mapping: "wupValidityStartDate" -> "id.wupValidityStartDate" */
		if(  !Utils.isEmpty( beanA.getWupValidityStartDate() )  ){
 			beanB.getId().setWupValidityStartDate( beanA.getWupValidityStartDate() ); 
 		}

 		/* Mapping: "wupPolicyId" -> "wupPolicyId" */
		if(  !Utils.isEmpty( beanA.getWupPolicyId() )  ){
 			beanB.setWupPolicyId( beanA.getWupPolicyId() ); 
 		}

 		/* Mapping: "wupBasicRiskId" -> "wupBasicRiskId" */
		if(  !Utils.isEmpty( beanA.getWupBasicRiskId() )  ){
 			beanB.setWupBasicRiskId( beanA.getWupBasicRiskId() ); 
 		}

 		/* Mapping: "wupRskCode" -> "wupRskCode" */
		if(  !Utils.isEmpty( beanA.getWupRskCode() )  ){
 			beanB.setWupRskCode( beanA.getWupRskCode() ); 
 		}

 		/* Mapping: "wupRtCode" -> "wupRtCode" */
		if(  !Utils.isEmpty( beanA.getWupRtCode() )  ){
 			beanB.setWupRtCode( beanA.getWupRtCode() ); 
 		}

 		/* Mapping: "wupRcCode" -> "wupRcCode" */
		if(  !Utils.isEmpty( beanA.getWupRcCode() )  ){
 			beanB.setWupRcCode( beanA.getWupRcCode() ); 
 		}

 		/* Mapping: "wupNoOfPerson" -> "wupNoOfPerson" */
		if(  !Utils.isEmpty( beanA.getWupNoOfPerson() )  ){
 			beanB.setWupNoOfPerson( beanA.getWupNoOfPerson() ); 
 		}

 		/* Mapping: "wupSumInsured" -> "wupSumInsured" */
		if(  !Utils.isEmpty( beanA.getWupSumInsured() )  ){
 			beanB.setWupSumInsured( beanA.getWupSumInsured() ); 
 		}

 		/* Mapping: "wupStatus" -> "wupStatus" */
		if(  !Utils.isEmpty( beanA.getWupStatus() )  ){
 			beanB.setWupStatus( beanA.getWupStatus() ); 
 		}

 		/* Mapping: "wupEmploymentType" -> "wupEmploymentType" */
		if(  !Utils.isEmpty( beanA.getWupEmploymentType() )  ){
 			beanB.setWupEmploymentType( beanA.getWupEmploymentType() ); 
 		}

 		/* Mapping: "wupEmployerEName" -> "wupEmployerEName" */
		if(  !Utils.isEmpty( beanA.getWupEmployerEName() )  ){
 			beanB.setWupEmployerEName( beanA.getWupEmployerEName() ); 
 		}

 		/* Mapping: "wupEmployerAName" -> "wupEmployerAName" */
		if(  !Utils.isEmpty( beanA.getWupEmployerAName() )  ){
 			beanB.setWupEmployerAName( beanA.getWupEmployerAName() ); 
 		}

 		/* Mapping: "wupValidityExpiryDate" -> "wupValidityExpiryDate" */
		if(  !Utils.isEmpty( beanA.getWupValidityExpiryDate() )  ){
 			beanB.setWupValidityExpiryDate( beanA.getWupValidityExpiryDate() ); 
 		}

 		/* Mapping: "wupEndtId" -> "wupEndtId" */
		if(  !Utils.isEmpty( beanA.getWupEndtId() )  ){
 			beanB.setWupEndtId( beanA.getWupEndtId() ); 
 		}

 		/* Mapping: "wupRiRskCode" -> "wupRiRskCode" */
		if(  !Utils.isEmpty( beanA.getWupRiRskCode() )  ){
 			beanB.setWupRiRskCode( beanA.getWupRiRskCode() ); 
 		}

 		/* Mapping: "wupBasicRskCode" -> "wupBasicRskCode" */
		if(  !Utils.isEmpty( beanA.getWupBasicRskCode() )  ){
 			beanB.setWupBasicRskCode( beanA.getWupBasicRskCode() ); 
 		}

 		/* Mapping: "wupPlaceOfWork" -> "wupPlaceOfWork" */
		if(  !Utils.isEmpty( beanA.getWupPlaceOfWork() )  ){
 			beanB.setWupPlaceOfWork( beanA.getWupPlaceOfWork() ); 
 		}

 		/* Mapping: "wupSalary" -> "wupSalary" */
		if(  !Utils.isEmpty( beanA.getWupSalary() )  ){
 			beanB.setWupSalary( beanA.getWupSalary() ); 
 		}

 		/* Mapping: "wupPreparedBy" -> "wupPreparedBy" */
		if(  !Utils.isEmpty( beanA.getWupPreparedBy() )  ){
 			beanB.setWupPreparedBy( beanA.getWupPreparedBy() ); 
 		}

 		/* Mapping: "wupPreparedDt" -> "wupPreparedDt" */
		if(  !Utils.isEmpty( beanA.getWupPreparedDt() )  ){
 			beanB.setWupPreparedDt( beanA.getWupPreparedDt() ); 
 		}

 		/* Mapping: "wupModifiedBy" -> "wupModifiedBy" */
		if(  !Utils.isEmpty( beanA.getWupModifiedBy() )  ){
 			beanB.setWupModifiedBy( beanA.getWupModifiedBy() ); 
 		}

 		/* Mapping: "wupModifiedDt" -> "wupModifiedDt" */
		if(  !Utils.isEmpty( beanA.getWupModifiedDt() )  ){
 			beanB.setWupModifiedDt( beanA.getWupModifiedDt() ); 
 		}

 		/* Mapping: "wupStartDate" -> "wupStartDate" */
		if(  !Utils.isEmpty( beanA.getWupStartDate() )  ){
 			beanB.setWupStartDate( beanA.getWupStartDate() ); 
 		}

 		/* Mapping: "wupEndDate" -> "wupEndDate" */
		if(  !Utils.isEmpty( beanA.getWupEndDate() )  ){
 			beanB.setWupEndDate( beanA.getWupEndDate() ); 
 		}

 		/* Mapping: "wupHazard" -> "wupHazard" */
		if(  !Utils.isEmpty( beanA.getWupHazard() )  ){
 			beanB.setWupHazard( beanA.getWupHazard() ); 
 		}

 		/* Mapping: "wupTradeGroup" -> "wupTradeGroup" */
		if(  !Utils.isEmpty( beanA.getWupTradeGroup() )  ){
 			beanB.setWupTradeGroup( beanA.getWupTradeGroup() ); 
 		}

 		/* Mapping: "wupBldId" -> "wupBldId" */
		if(  !Utils.isEmpty( beanA.getWupBldId() )  ){
 			beanB.setWupBldId( beanA.getWupBldId() ); 
 		}

 		/* Mapping: "wupTerCode" -> "wupTerCode" */
		if(  !Utils.isEmpty( beanA.getWupTerCode() )  ){
 			beanB.setWupTerCode( beanA.getWupTerCode() ); 
 		}

 		/* Mapping: "wupJurCode" -> "wupJurCode" */
		if(  !Utils.isEmpty( beanA.getWupJurCode() )  ){
 			beanB.setWupJurCode( beanA.getWupJurCode() ); 
 		}

 		/* Mapping: "wupFreeZone" -> "wupFreeZone" */
		if(  !Utils.isEmpty( beanA.getWupFreeZone() )  ){
 			beanB.setWupFreeZone( beanA.getWupFreeZone() ); 
 		}

 		/* Mapping: "wupIndemnityDesc" -> "wupIndemnityDesc" */
		if(  !Utils.isEmpty( beanA.getWupIndemnityDesc() )  ){
 			beanB.setWupIndemnityDesc( beanA.getWupIndemnityDesc() ); 
 		}

 		/* Mapping: "wupJurDesc" -> "wupJurDesc" */
		if(  !Utils.isEmpty( beanA.getWupJurDesc() )  ){
 			beanB.setWupJurDesc( beanA.getWupJurDesc() ); 
 		}

 		/* Mapping: "wupEmpLiabLmt" -> "wupEmpLiabLmt" */
		if(  !Utils.isEmpty( beanA.getWupEmpLiabLmt() )  ){
 			beanB.setWupEmpLiabLmt( beanA.getWupEmpLiabLmt() ); 
 		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.dao.model.TTrnWctplUnnamedPersonQuo initializeDeepVO( com.rsaame.pas.vo.svc.TTrnWctplUnnamedPersonQuoHolder beanA, com.rsaame.pas.dao.model.TTrnWctplUnnamedPersonQuo beanB ){
  		BeanUtils.initializeBeanField( "id", beanB );
                                                                    		return beanB;
	}
}
