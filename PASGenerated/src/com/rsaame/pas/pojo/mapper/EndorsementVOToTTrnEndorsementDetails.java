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
 * <li> com.rsaame.pas.vo.bus.EndorsmentVO</li>
 * <li>com.rsaame.pas.dao.model.TTrnEndorsementDetail</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( EndorsementVOToTTrnEndorsementDetails.class )</code>.
 */
public class EndorsementVOToTTrnEndorsementDetails extends BaseBeanToBeanMapper< com.rsaame.pas.vo.bus.EndorsmentVO, com.rsaame.pas.dao.model.TTrnEndorsementDetail>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public EndorsementVOToTTrnEndorsementDetails(){
		super();
	}

	public EndorsementVOToTTrnEndorsementDetails(  com.rsaame.pas.vo.bus.EndorsmentVO src, com.rsaame.pas.dao.model.TTrnEndorsementDetail dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.dao.model.TTrnEndorsementDetail mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.dao.model.TTrnEndorsementDetail) Utils.newInstance( "com.rsaame.pas.dao.model.TTrnEndorsementDetail" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		 com.rsaame.pas.vo.bus.EndorsmentVO beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.dao.model.TTrnEndorsementDetail beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;
		
		/* Mapping: "policyId" -> "id.edlPolicyId" */
		if(  !Utils.isEmpty( beanA.getPolicyId() )  ){
 			beanB.getId().setEdlPolicyId( beanA.getPolicyId() ); 
 		}

 		/* Mapping: "endtId" -> "id.edlEndorsementId" */
		if(  !Utils.isEmpty( beanA.getEndtId() )  ){
 			beanB.getId().setEdlEndorsementId( beanA.getEndtId() ); 
 		}

 		/* Mapping: "endNo" -> "id.edlEndNo" */
		if(  !Utils.isEmpty( beanA.getEndNo() )  ){
 			beanB.getId().setEdlEndNo( beanA.getEndNo() ); 
 		}

 		/* Mapping: "slNo" -> "id.edlSerialNo" */
		if(  !Utils.isEmpty( beanA.getSlNo() )  ){
 			beanB.getId().setEdlSerialNo( beanA.getSlNo() ); 
 		}

 		/* Mapping: "endText" -> "edlText" */
 		beanB.setEdlText( beanA.getEndText() ); 
		
		/* Mapping: "recCreatedBy" -> "edlPreparedBy" */
		if( !Utils.isEmpty(beanA.getRecCreatedBy())){
			beanB.setEdlPreparedBy(beanA.getRecCreatedBy());
		}
		
		/* Mapping: "recCreatedDt" -> "edlPreparedDt" */
		if( !Utils.isEmpty(beanA.getRecCreaDate())){
			beanB.setEdlPreparedDt(beanA.getRecCreaDate());
		}

		/* Mapping: "recModifiedBy" -> "edlModifiedBy" */
		if(  !Utils.isEmpty(beanA.getRecModifiedBy())) {
			beanB.setEdlModifiedBy(beanA.getRecModifiedBy());
		}
		
		/* Mapping: "recModifiedDt" -> "edlModifiedDt" */
		if( !Utils.isEmpty(beanA.getRecModifiedDt())){
			beanB.setEdlModifiedDt(beanA.getRecModifiedDt());
		}
		
		if( !Utils.isEmpty(beanA.getEndSecId()) && Utils.isEmpty(beanB.getId().getEdlSecId())){
			beanB.getId().setEdlSecId( beanA.getEndSecId() );
		}
		
		
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.dao.model.TTrnEndorsementDetail initializeDeepVO(  com.rsaame.pas.vo.bus.EndorsmentVO beanA, com.rsaame.pas.dao.model.TTrnEndorsementDetail beanB ){
  		BeanUtils.initializeBeanField( "id", beanB );
          		return beanB;
	}
}
