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
 * <li>com.rsaame.pas.dao.model.VTrnPasPremiumSummary</li>
 * <li>com.rsaame.pas.vo.app.PremiumSummaryVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( PremiumSummaryVOToVTrnPasPremiumReverse.class )</code>.
 */
public class PremiumSummaryVOToVTrnPasPremiumReverse extends BaseBeanToBeanMapper<com.rsaame.pas.dao.model.VTrnPasPremiumSummary, com.rsaame.pas.vo.app.PremiumSummaryVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public PremiumSummaryVOToVTrnPasPremiumReverse(){
		super();
	}

	public PremiumSummaryVOToVTrnPasPremiumReverse( com.rsaame.pas.dao.model.VTrnPasPremiumSummary src, com.rsaame.pas.vo.app.PremiumSummaryVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.app.PremiumSummaryVO mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.app.PremiumSummaryVO) Utils.newInstance( "com.rsaame.pas.vo.app.PremiumSummaryVO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.dao.model.VTrnPasPremiumSummary beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.app.PremiumSummaryVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;
		
		/* Mapping: "id.linkingId" -> "linkingId" */
		if(  !Utils.isEmpty( beanA.getId() ) && !Utils.isEmpty( beanA.getId().getLinkingId() )  ){
 			beanB.setLinkingId( beanA.getId().getLinkingId() ); 
 		}

 		/* Mapping: "id.policyId" -> "policyId" */
		if(  !Utils.isEmpty( beanA.getId() ) && !Utils.isEmpty( beanA.getId().getPolicyId() )  ){
 			beanB.setPolicyId( beanA.getId().getPolicyId() ); 
 		}

 		/* Mapping: "id.endtId" -> "endtId" */
		if(  !Utils.isEmpty( beanA.getId() ) && !Utils.isEmpty( beanA.getId().getEndtId() )  ){
 			beanB.setEndtId( beanA.getId().getEndtId() ); 
 		}

 		/* Mapping: "id.class_" -> "class_" */
		if(  !Utils.isEmpty( beanA.getId() ) && !Utils.isEmpty( beanA.getId().getClass_() )  ){
 			beanB.setClass_( beanA.getId().getClass_() ); 
 		}

 		/* Mapping: "id.secId" -> "secId" */
		if(  !Utils.isEmpty( beanA.getId() ) && !Utils.isEmpty( beanA.getId().getSecId() )  ){
 			beanB.setSecId( beanA.getId().getSecId() ); 
 		}

 		/* Mapping: "id.secName" -> "secName" */
		if(  !Utils.isEmpty( beanA.getId() ) && !Utils.isEmpty( beanA.getId().getSecName() )  ){
 			beanB.setSecName( beanA.getId().getSecName() ); 
 		}

 		/* Mapping: "id.locationId" -> "locationId" */
		if(  !Utils.isEmpty( beanA.getId() ) && !Utils.isEmpty( beanA.getId().getLocationId() )  ){
 			beanB.setLocationId( beanA.getId().getLocationId() ); 
 		}

 		/* Mapping: "id.locationName" -> "locationName" */
		if(  !Utils.isEmpty( beanA.getId() ) && !Utils.isEmpty( beanA.getId().getLocationName() )  ){
 			beanB.setLocationName( beanA.getId().getLocationName() ); 
 		}

 		/* Mapping: "id.coverId" -> "coverId" */
		if(  !Utils.isEmpty( beanA.getId() ) && !Utils.isEmpty( beanA.getId().getCoverId() )  ){
 			beanB.setCoverId( beanA.getId().getCoverId() ); 
 		}

 		/* Mapping: "commission" -> "commission" */
		if(  !Utils.isEmpty( beanA.getCommission() )  ){
 			beanB.setCommission( beanA.getCommission() ); 
 		}

 		/* Mapping: "coverSiAmt" -> "coverSiAmt" */
		if(  !Utils.isEmpty( beanA.getCoverSiAmt() )  ){
 			beanB.setCoverSiAmt( beanA.getCoverSiAmt() ); 
 		}

 		/* Mapping: "coverPrmAmt" -> "coverPrmAmt" */
		if(  !Utils.isEmpty( beanA.getCoverPrmAmt() )  ){
 			beanB.setCoverPrmAmt( beanA.getCoverPrmAmt() ); 
 		}

 		/* Mapping: "valStartDate" -> "valStartDate" */
		if(  !Utils.isEmpty( beanA.getValStartDate() )  ){
 			beanB.setValStartDate( beanA.getValStartDate() ); 
 		}

 		/* Mapping: "valExpDate" -> "valExpDate" */
		if(  !Utils.isEmpty( beanA.getValExpDate() )  ){
 			beanB.setValExpDate( beanA.getValExpDate() ); 
 		}

 		/* Mapping: "status" -> "status" */
		if(  !Utils.isEmpty( beanA.getStatus() )  ){
 			beanB.setStatus( beanA.getStatus() ); 
 		}

 		/* Mapping: "polQuoFlag" -> "polQuoFlag" */
		if(  !Utils.isEmpty( beanA.getPolQuoFlag() )  ){
 			beanB.setPolQuoFlag( beanA.getPolQuoFlag() ); 
 		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.app.PremiumSummaryVO initializeDeepVO( com.rsaame.pas.dao.model.VTrnPasPremiumSummary beanA, com.rsaame.pas.vo.app.PremiumSummaryVO beanB ){
                                 		return beanB;
	}
}
