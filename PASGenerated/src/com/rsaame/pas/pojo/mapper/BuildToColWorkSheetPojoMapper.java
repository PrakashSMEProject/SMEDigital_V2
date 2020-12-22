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
 * <li>com.rsaame.pas.dao.model.TTrnBuildingQuo</li>
 * <li>com.rsaame.pas.dao.model.TTrnColWorkSheetQuo</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( BuildToColWorkSheetPojoMapper.class )</code>.
 */
public class BuildToColWorkSheetPojoMapper extends BaseBeanToBeanMapper<com.rsaame.pas.dao.model.TTrnBuildingQuo, com.rsaame.pas.dao.model.TTrnColWorkSheetQuo>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public BuildToColWorkSheetPojoMapper(){
		super();
	}

	public BuildToColWorkSheetPojoMapper( com.rsaame.pas.dao.model.TTrnBuildingQuo src, com.rsaame.pas.dao.model.TTrnColWorkSheetQuo dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.dao.model.TTrnColWorkSheetQuo mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.dao.model.TTrnColWorkSheetQuo) Utils.newInstance( "com.rsaame.pas.dao.model.TTrnColWorkSheetQuo" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.dao.model.TTrnBuildingQuo beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.dao.model.TTrnColWorkSheetQuo beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;
		
		/* Mapping: "bldPolicyId" -> "id.cwsPolicyId" */
		if(  !Utils.isEmpty( beanA.getBldPolicyId() )  ){
 			beanB.getId().setCwsPolicyId( beanA.getBldPolicyId() ); 
 		}

 		/* Mapping: "id.bldValidityStartDate" -> "id.cwsValidityStartDate" */
		if(  !Utils.isEmpty( beanA.getId() ) && !Utils.isEmpty( beanA.getId().getBldValidityStartDate() )  ){
 			beanB.getId().setCwsValidityStartDate( beanA.getId().getBldValidityStartDate() ); 
 		}

 		/* Mapping: "bldPreparedBy" -> "cwsPreparedBy" */
		if(  !Utils.isEmpty( beanA.getBldPreparedBy() )  ){
 			beanB.setCwsPreparedBy( beanA.getBldPreparedBy() ); 
 		}

 		/* Mapping: "bldPreparedDt" -> "cwsPreparedDt" */
		if(  !Utils.isEmpty( beanA.getBldPreparedDt() )  ){
 			beanB.setCwsPreparedDt( beanA.getBldPreparedDt() ); 
 		}

 		/* Mapping: "bldValidityExpiryDate" -> "cwsValidityExpiryDate" */
		if(  !Utils.isEmpty( beanA.getBldValidityExpiryDate() )  ){
 			beanB.setCwsValidityExpiryDate( beanA.getBldValidityExpiryDate() ); 
 		}

 		/* Mapping: "bldModifiedBy" -> "cwsModifiedBy" */
		if(  !Utils.isEmpty( beanA.getBldModifiedBy() )  ){
 			beanB.setCwsModifiedBy( beanA.getBldModifiedBy() ); 
 		}

 		/* Mapping: "bldModifiedDt" -> "cwsModifiedDt" */
		if(  !Utils.isEmpty( beanA.getBldModifiedDt() )  ){
 			beanB.setCwsModifiedDt( beanA.getBldModifiedDt() ); 
 		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.dao.model.TTrnColWorkSheetQuo initializeDeepVO( com.rsaame.pas.dao.model.TTrnBuildingQuo beanA, com.rsaame.pas.dao.model.TTrnColWorkSheetQuo beanB ){
  		BeanUtils.initializeBeanField( "id", beanB );
              		return beanB;
	}
}
