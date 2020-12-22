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
 * <li>com.rsaame.pas.dao.model.TTrnConsequentialLossQuo</li>
 * <li>com.rsaame.pas.dao.model.TTrnBuildingQuo</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( BuildToConseqLossPojoMapperReverse.class )</code>.
 */
public class BuildToConseqLossPojoMapperReverse extends BaseBeanToBeanMapper<com.rsaame.pas.dao.model.TTrnConsequentialLossQuo, com.rsaame.pas.dao.model.TTrnBuildingQuo>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public BuildToConseqLossPojoMapperReverse(){
		super();
	}

	public BuildToConseqLossPojoMapperReverse( com.rsaame.pas.dao.model.TTrnConsequentialLossQuo src, com.rsaame.pas.dao.model.TTrnBuildingQuo dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.dao.model.TTrnBuildingQuo mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.dao.model.TTrnBuildingQuo) Utils.newInstance( "com.rsaame.pas.dao.model.TTrnBuildingQuo" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.dao.model.TTrnConsequentialLossQuo beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.dao.model.TTrnBuildingQuo beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;
		
		/* Mapping: "colBldId" -> "id.bldId" */
		if(  !Utils.isEmpty( beanA.getColBldId() )  ){
 			beanB.getId().setBldId( beanA.getColBldId() ); 
 		}

 		/* Mapping: "colModifiedBy" -> "bldModifiedBy" */
		if(  !Utils.isEmpty( beanA.getColModifiedBy() )  ){
 			beanB.setBldModifiedBy( beanA.getColModifiedBy() ); 
 		}

 		/* Mapping: "colModifiedDt" -> "bldModifiedDt" */
		if(  !Utils.isEmpty( beanA.getColModifiedDt() )  ){
 			beanB.setBldModifiedDt( beanA.getColModifiedDt() ); 
 		}

 		/* Mapping: "colPreparedBy" -> "bldPreparedBy" */
		if(  !Utils.isEmpty( beanA.getColPreparedBy() )  ){
 			beanB.setBldPreparedBy( beanA.getColPreparedBy() ); 
 		}

 		/* Mapping: "id.colValidityStartDate" -> "id.bldValidityStartDate" */
		if(  !Utils.isEmpty( beanA.getId() ) && !Utils.isEmpty( beanA.getId().getColValidityStartDate() )  ){
 			beanB.getId().setBldValidityStartDate( beanA.getId().getColValidityStartDate() ); 
 		}

 		/* Mapping: "id.colPolicyId" -> "bldPolicyId" */
		if(  !Utils.isEmpty( beanA.getId() ) && !Utils.isEmpty( beanA.getId().getColPolicyId() )  ){
 			beanB.setBldPolicyId( beanA.getId().getColPolicyId() ); 
 		}

 		/* Mapping: "colHazardCategory" -> "bldHazardCode" */
		if(  !Utils.isEmpty( beanA.getColHazardCategory() )  ){
 			beanB.setBldHazardCode( beanA.getColHazardCategory() ); 
 		}

 		/* Mapping: "colValidityExpiryDate" -> "bldValidityExpiryDate" */
		if(  !Utils.isEmpty( beanA.getColValidityExpiryDate() )  ){
 			beanB.setBldValidityExpiryDate( beanA.getColValidityExpiryDate() ); 
 		}

 		/* Mapping: "colPreparedDt" -> "bldPreparedDt" */
		if(  !Utils.isEmpty( beanA.getColPreparedDt() )  ){
 			beanB.setBldPreparedDt( beanA.getColPreparedDt() ); 
 		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.dao.model.TTrnBuildingQuo initializeDeepVO( com.rsaame.pas.dao.model.TTrnConsequentialLossQuo beanA, com.rsaame.pas.dao.model.TTrnBuildingQuo beanB ){
  		BeanUtils.initializeBeanField( "id", beanB );
                  		return beanB;
	}
}
