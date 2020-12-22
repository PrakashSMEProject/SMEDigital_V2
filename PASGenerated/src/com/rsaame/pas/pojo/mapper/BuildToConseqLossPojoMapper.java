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
 * <li>com.rsaame.pas.dao.model.TTrnConsequentialLossQuo</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( BuildToConseqLossPojoMapper.class )</code>.
 */
public class BuildToConseqLossPojoMapper extends BaseBeanToBeanMapper<com.rsaame.pas.dao.model.TTrnBuildingQuo, com.rsaame.pas.dao.model.TTrnConsequentialLossQuo>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public BuildToConseqLossPojoMapper(){
		super();
	}

	public BuildToConseqLossPojoMapper( com.rsaame.pas.dao.model.TTrnBuildingQuo src, com.rsaame.pas.dao.model.TTrnConsequentialLossQuo dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.dao.model.TTrnConsequentialLossQuo mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.dao.model.TTrnConsequentialLossQuo) Utils.newInstance( "com.rsaame.pas.dao.model.TTrnConsequentialLossQuo" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.dao.model.TTrnBuildingQuo beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.dao.model.TTrnConsequentialLossQuo beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;
		
		/* Mapping: "id.bldId" -> "colBldId" */
		if(  !Utils.isEmpty( beanA.getId() ) && !Utils.isEmpty( beanA.getId().getBldId() )  ){
 			beanB.setColBldId( beanA.getId().getBldId() ); 
 		}

 		/* Mapping: "bldModifiedBy" -> "colModifiedBy" */
		if(  !Utils.isEmpty( beanA.getBldModifiedBy() )  ){
 			beanB.setColModifiedBy( beanA.getBldModifiedBy() ); 
 		}

 		/* Mapping: "bldModifiedDt" -> "colModifiedDt" */
		if(  !Utils.isEmpty( beanA.getBldModifiedDt() )  ){
 			beanB.setColModifiedDt( beanA.getBldModifiedDt() ); 
 		}

 		/* Mapping: "bldPreparedBy" -> "colPreparedBy" */
		if(  !Utils.isEmpty( beanA.getBldPreparedBy() )  ){
 			beanB.setColPreparedBy( beanA.getBldPreparedBy() ); 
 		}

 		/* Mapping: "id.bldValidityStartDate" -> "id.colValidityStartDate" */
		if(  !Utils.isEmpty( beanA.getId() ) && !Utils.isEmpty( beanA.getId().getBldValidityStartDate() )  ){
 			beanB.getId().setColValidityStartDate( beanA.getId().getBldValidityStartDate() ); 
 		}

 		/* Mapping: "bldPolicyId" -> "id.colPolicyId" */
		if(  !Utils.isEmpty( beanA.getBldPolicyId() )  ){
 			beanB.getId().setColPolicyId( beanA.getBldPolicyId() ); 
 		}

 		/* Mapping: "bldHazardCode" -> "colHazardCategory" */
		/*if(  !Utils.isEmpty( beanA.getBldHazardCode() )  ){
 			beanB.setColHazardCategory( beanA.getBldHazardCode() ); 
 		}*/
		/*Setting ColHazardCategory to 4 as required by client
		 * */
		beanB.setColHazardCategory(Short.parseShort( Utils.getSingleValueAppConfig( "BI_HAZARD_CODE" )));
		

 		/* Mapping: "bldValidityExpiryDate" -> "colValidityExpiryDate" */
		if(  !Utils.isEmpty( beanA.getBldValidityExpiryDate() )  ){
 			beanB.setColValidityExpiryDate( beanA.getBldValidityExpiryDate() ); 
 		}

 		/* Mapping: "bldPreparedDt" -> "colPreparedDt" */
		if(  !Utils.isEmpty( beanA.getBldPreparedDt() )  ){
 			beanB.setColPreparedDt( beanA.getBldPreparedDt() ); 
 		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.dao.model.TTrnConsequentialLossQuo initializeDeepVO( com.rsaame.pas.dao.model.TTrnBuildingQuo beanA, com.rsaame.pas.dao.model.TTrnConsequentialLossQuo beanB ){
          		BeanUtils.initializeBeanField( "id", beanB );
          		return beanB;
	}
}
