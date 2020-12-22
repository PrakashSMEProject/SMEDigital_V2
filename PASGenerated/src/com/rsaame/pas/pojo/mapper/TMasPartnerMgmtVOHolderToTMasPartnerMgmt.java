       /*
 * THIS IS A GENERATED CLASS. THIS CLASS SHOULD NOT BE EDITED.
 */
package com.rsaame.pas.pojo.mapper;

import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;

/**
 * Mapper class for:<ol>
 * <li>com.rsaame.pas.vo.svc.TMasPartnerMgmtVOHolder</li>
 * <li>com.rsaame.pas.dao.model.TMasPartnerMgmt</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( TMasPartnerMgmtVOHolderToTMasPartnerMgmt.class )</code>.
 */
public class TMasPartnerMgmtVOHolderToTMasPartnerMgmt extends BaseBeanToBeanMapper<com.rsaame.pas.vo.svc.TMasPartnerMgmtVOHolder, com.rsaame.pas.dao.model.TMasPartnerMgmt>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public TMasPartnerMgmtVOHolderToTMasPartnerMgmt(){
		super();
	}

	public TMasPartnerMgmtVOHolderToTMasPartnerMgmt( com.rsaame.pas.vo.svc.TMasPartnerMgmtVOHolder src, com.rsaame.pas.dao.model.TMasPartnerMgmt dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.dao.model.TMasPartnerMgmt mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.dao.model.TMasPartnerMgmt) Utils.newInstance( "com.rsaame.pas.dao.model.TMasPartnerMgmt" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.vo.svc.TMasPartnerMgmtVOHolder beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.dao.model.TMasPartnerMgmt beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;
		
		/* Mapping: "pmmId" -> "pmmId" */
		if(  !Utils.isEmpty( beanA.getPmmId() )  ){
 			beanB.getId().setPmmId(beanA.getPmmId().toString() ); 
 		}

 		/* Mapping: "proCode" -> "TMasPromotionalCode.proCode" */
	/*	if(  !Utils.isEmpty( beanA.getProCode() )  ){
			beanB.setProCode( beanA.getProCode());
			
 		
 		}*/

 		/* Mapping: "proType" -> "TMasPromotionalCode.proType" */
	/*	if(  !Utils.isEmpty( beanA.getProType() )  ){
 			beanB.getTMasPromotionalCode().setProType( beanA.getProType() ); 
 		}

 		 Mapping: "proPtCode" -> "TMasPromotionalCode.proPtCode" 
		if(  !Utils.isEmpty( beanA.getProPtCode() )  ){
 			beanB.getTMasPromotionalCode().setProPtCode( beanA.getProPtCode() ); 
 		}

 		 Mapping: "proClassCode" -> "TMasPromotionalCode.proClassCode" 
		if(  !Utils.isEmpty( beanA.getProClassCode() )  ){
 			beanB.getTMasPromotionalCode().setProClassCode( beanA.getProClassCode() ); 
 		}

 		 Mapping: "proDiscPerc" -> "TMasPromotionalCode.proDiscPerc" 
		if(  !Utils.isEmpty( beanA.getProDiscPerc() )  ){
 			beanB.getTMasPromotionalCode().setProDiscPerc( beanA.getProDiscPerc() ); 
 		}

 		 Mapping: "proADesc" -> "TMasPromotionalCode.proADesc" 
		if(  !Utils.isEmpty( beanA.getProADesc() )  ){
 			beanB.getTMasPromotionalCode().setProADesc( beanA.getProADesc() ); 
 		}

 		 Mapping: "proEDesc" -> "TMasPromotionalCode.proEDesc" 
		if(  !Utils.isEmpty( beanA.getProEDesc() )  ){
 			beanB.getTMasPromotionalCode().setProEDesc( beanA.getProEDesc() ); 
 		}

 		 Mapping: "proStartDate" -> "TMasPromotionalCode.proStartDate" 
		if(  !Utils.isEmpty( beanA.getProStartDate() )  ){
 			beanB.getTMasPromotionalCode().setProStartDate( beanA.getProStartDate() ); 
 		}

 		 Mapping: "proEndDate" -> "TMasPromotionalCode.proEndDate" 
		if(  !Utils.isEmpty( beanA.getProEndDate() )  ){
 			beanB.getTMasPromotionalCode().setProEndDate( beanA.getProEndDate() ); 
 		}

 		 Mapping: "proPreparedBy" -> "TMasPromotionalCode.proPreparedBy" 
		if(  !Utils.isEmpty( beanA.getProPreparedBy() )  ){
 			beanB.getTMasPromotionalCode().setProPreparedBy( beanA.getProPreparedBy() ); 
 		}

 		 Mapping: "proPreparedDate" -> "TMasPromotionalCode.proPreparedDate" 
		if(  !Utils.isEmpty( beanA.getProPreparedDate() )  ){
 			beanB.getTMasPromotionalCode().setProPreparedDate( beanA.getProPreparedDate() ); 
 		}

 		 Mapping: "proModifiedBy" -> "TMasPromotionalCode.proModifiedBy" 
		if(  !Utils.isEmpty( beanA.getProModifiedBy() )  ){
 			beanB.getTMasPromotionalCode().setProModifiedBy( beanA.getProModifiedBy() ); 
 		}

 		 Mapping: "proModifiedDate" -> "TMasPromotionalCode.proModifiedDate" 
		if(  !Utils.isEmpty( beanA.getProModifiedDate() )  ){
 			beanB.getTMasPromotionalCode().setProModifiedDate( beanA.getProModifiedDate() ); 
 		}
*/
 		/* Mapping: "pmmName" -> "pmmName" */
		if(  !Utils.isEmpty( beanA.getPmmName() )  ){
 			beanB.setPmmEName( beanA.getPmmName() ); 
 		}

 		/* Mapping: "pmmDesc" -> "pmmDesc" */
		if(  !Utils.isEmpty( beanA.getPmmDesc() )  ){
 			beanB.setPmmDesc( beanA.getPmmDesc() ); 
 		}

 		/* Mapping: "pmmUrl" -> "pmmUrl" */
		if(  !Utils.isEmpty( beanA.getPmmUrl() )  ){
 			beanB.setPmmUrl( beanA.getPmmUrl() ); 
 		}

 		/* Mapping: "pmmCustSrc" -> "pmmCustSrc" */
		/*if(  !Utils.isEmpty( beanA.getPmmCustSrc() )  ){
 			beanB.setPmmCustSrc( beanA.getPmmCustSrc() ); 
 		}*/

 		/* Mapping: "pmmPtCode" -> "pmmPtCode" */
		if(  !Utils.isEmpty( beanA.getPmmPtCode() )  ){
 			beanB.getId().setPmmPtCode( Integer.valueOf( beanA.getPmmPtCode().toString()) ); 
 		}

 		/* Mapping: "pmmClassCode" -> "pmmClassCode" */
		if(  !Utils.isEmpty( beanA.getPmmClassCode() )  ){
 			beanB.getId().setPmmClassCode( Integer.valueOf( beanA.getPmmClassCode().toString()) ); 
 		}

 		/* Mapping: "pmmStatus" -> "pmmStatus" */
		if(  !Utils.isEmpty( beanA.getPmmStatus() )  ){
 			beanB.setPmmStatus( Integer.valueOf( beanA.getPmmStatus().toString()) ); 
 		}

 		/* Mapping: "pmmPreparedBy" -> "pmmPreparedBy" */
		if(  !Utils.isEmpty( beanA.getPmmPreparedBy() )  ){
 			beanB.setPmmPreparedBy( beanA.getPmmPreparedBy() ); 
 		}

 		/* Mapping: "pmmPreparedDate" -> "pmmPreparedDate" */
		if(  !Utils.isEmpty( beanA.getPmmPreparedDate() )  ){
 			beanB.setPmmPreparedDate( beanA.getPmmPreparedDate() ); 
 		}

 		/* Mapping: "pmmModifiedBy" -> "pmmModifiedBy" */
		if(  !Utils.isEmpty( beanA.getPmmModifiedBy() )  ){
 			beanB.setPmmModifiedBy( beanA.getPmmModifiedBy() ); 
 		}

 		/* Mapping: "pmmModifiedDate" -> "pmmModifiedDate" */
		if(  !Utils.isEmpty( beanA.getPmmModifiedDate() )  ){
 			beanB.setPmmModifiedDate( beanA.getPmmModifiedDate() ); 
 		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.dao.model.TMasPartnerMgmt initializeDeepVO( com.rsaame.pas.vo.svc.TMasPartnerMgmtVOHolder beanA, com.rsaame.pas.dao.model.TMasPartnerMgmt beanB ){
    		//BeanUtils.initializeBeanField( "TMasPromotionalCode", beanB );
                                                		return beanB;
	}
}
