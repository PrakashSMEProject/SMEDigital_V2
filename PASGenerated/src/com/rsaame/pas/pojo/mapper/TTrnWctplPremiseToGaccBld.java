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
 * <li>com.rsaame.pas.dao.model.TTrnWctplPremiseQuo</li>
 * <li>com.rsaame.pas.dao.model.TTrnGaccBuildingQuo</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( TTrnWctplPremiseToGaccBld.class )</code>.
 */
public class TTrnWctplPremiseToGaccBld extends BaseBeanToBeanMapper<com.rsaame.pas.dao.model.TTrnWctplPremiseQuo, com.rsaame.pas.dao.model.TTrnGaccBuildingQuo>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public TTrnWctplPremiseToGaccBld(){
		super();
	}

	public TTrnWctplPremiseToGaccBld( com.rsaame.pas.dao.model.TTrnWctplPremiseQuo src, com.rsaame.pas.dao.model.TTrnGaccBuildingQuo dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.dao.model.TTrnGaccBuildingQuo mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.dao.model.TTrnGaccBuildingQuo) Utils.newInstance( "com.rsaame.pas.dao.model.TTrnGaccBuildingQuo" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.dao.model.TTrnWctplPremiseQuo beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.dao.model.TTrnGaccBuildingQuo beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;
		
		/* Mapping: "id.wbdValidityStartDate" -> "id.gbdValidityStartDate" */
		if(  !Utils.isEmpty( beanA.getId() ) && !Utils.isEmpty( beanA.getId().getWbdValidityStartDate() )  ){
 			beanB.getId().setGbdValidityStartDate( beanA.getId().getWbdValidityStartDate() ); 
 		}

 		/* Mapping: "wbdRskCode" -> "gbdRskCode" */
		if(  !Utils.isEmpty( beanA.getWbdRskCode() )  ){
 			beanB.setGbdRskCode( beanA.getWbdRskCode() ); 
 		}

 		/* Mapping: "wbdStartDate" -> "gbdStartDate" */
		if(  !Utils.isEmpty( beanA.getWbdStartDate() )  ){
 			beanB.setGbdStartDate( beanA.getWbdStartDate() ); 
 		}

 		/* Mapping: "wbdEndDate" -> "gbdEndDate" */
		if(  !Utils.isEmpty( beanA.getWbdEndDate() )  ){
 			beanB.setGbdEndDate( beanA.getWbdEndDate() ); 
 		}

 		/* Mapping: "wbdEName" -> "gbdEName" */
		if(  !Utils.isEmpty( beanA.getWbdEName() )  ){
 			beanB.setGbdEName( beanA.getWbdEName() ); 
 		}

 		/* Mapping: "wbdWayNo" -> "gbdWayNo" */
		if(  !Utils.isEmpty( beanA.getWbdWayNo() )  ){
 			beanB.setGbdWayNo( beanA.getWbdWayNo() ); 
 		}

 		/* Mapping: "wbdEStreetName" -> "gbdEStreetName" */
		if(  !Utils.isEmpty( beanA.getWbdEStreetName() )  ){
 			beanB.setGbdEStreetName( beanA.getWbdEStreetName() ); 
 		}

 		/* Mapping: "wbdFlatNo" -> "gbdFlatNo" */
		if(  !Utils.isEmpty( beanA.getWbdFlatNo() )  ){
 			beanB.setGbdFlatNo( beanA.getWbdFlatNo() ); 
 		}

 		/* Mapping: "wbdDirCode" -> "gbdDirCode" */
		if(  !Utils.isEmpty( beanA.getWbdDirCode() )  ){
 			beanB.setGbdDirCode( beanA.getWbdDirCode() ); 
 		}

 		/* Mapping: "wbdNo" -> "gbdNo" */
		if(  !Utils.isEmpty( beanA.getWbdNo() )  ){
 			beanB.setGbdNo( beanA.getWbdNo() ); 
 		}

 		/* Mapping: "wbdMunCode" -> "gbdMunCode" */
		if(  !Utils.isEmpty( beanA.getWbdMunCode() )  ){
 			beanB.setGbdMunCode( beanA.getWbdMunCode() ); 
 		}

 		/* Mapping: "wbdMunCode" -> "gbdMunCode" */
		if(  !Utils.isEmpty( beanA.getWbdMunCode() )  ){
 			beanB.setGbdMunCode( beanA.getWbdMunCode() ); 
 		}

 		/* Mapping: "wbdAName" -> "gbdAName" */
		if(  !Utils.isEmpty( beanA.getWbdAName() )  ){
 			beanB.setGbdAName( beanA.getWbdAName() ); 
 		}

 		/* Mapping: "wbdSumInsured" -> "gbdSumInsured" */
		/*if(  !Utils.isEmpty( beanA.getWbdSumInsured() )  ){
 			beanB.setGbdSumInsured( beanA.getWbdSumInsured() ); 
 		}
*/
 		/* Mapping: "id.wbdId" -> "gbdBldId" */
		if(  !Utils.isEmpty( beanA.getId() ) && !Utils.isEmpty( beanA.getId().getWbdId() )  ){
 			beanB.setGbdBldId( beanA.getId().getWbdId() ); 
 		}

 		/* Mapping: "wbdPolicyId" -> "gbdPolicyId" */
		if(  !Utils.isEmpty( beanA.getWbdPolicyId() )  ){
 			beanB.setGbdPolicyId( beanA.getWbdPolicyId() ); 
 		}

 		/* Mapping: "wbdEndtId" -> "gbdEndtId" */
		if(  !Utils.isEmpty( beanA.getWbdEndtId() )  ){
 			beanB.setGbdEndtId( beanA.getWbdEndtId() ); 
 		}

 		/* Mapping: "wbdStatus" -> "gbdStatus" */
		if(  !Utils.isEmpty( beanA.getWbdStatus() )  ){
 			beanB.setGbdStatus( beanA.getWbdStatus() ); 
 		}

 		/* Mapping: "wbdValidityExpiryDate" -> "gbdValidityExpiryDate" */
		if(  !Utils.isEmpty( beanA.getWbdValidityExpiryDate() )  ){
 			beanB.setGbdValidityExpiryDate( beanA.getWbdValidityExpiryDate() ); 
 		}

 		/* Mapping: "wbdRiRskCode" -> "gbdRiRskCode" */
		/*if(  !Utils.isEmpty( beanA.getWbdRiRskCode() )  ){
 			beanB.setGbdRiRskCode( beanA.getWbdRiRskCode() ); 
 		}
*/
 		/* Mapping: "wbdZip" -> "gbdZip" */
		if(  !Utils.isEmpty( beanA.getWbdZip() )  ){
 			beanB.setGbdZip( beanA.getWbdZip() ); 
 		}

 		/* Mapping: "wbdCbCode" -> "gbdCbCode" */
		if(  !Utils.isEmpty( beanA.getWbdCbCode() )  ){
 			beanB.setGbdCbCode( beanA.getWbdCbCode() ); 
 		}

 		/* Mapping: "wbdPreparedBy" -> "gbdPreparedBy" */
		if(  !Utils.isEmpty( beanA.getWbdPreparedBy() )  ){
 			beanB.setGbdPreparedBy( beanA.getWbdPreparedBy() ); 
 		}

 		/* Mapping: "wbdPreparedDt" -> "gbdPreparedDt" */
		if(  !Utils.isEmpty( beanA.getWbdPreparedDt() )  ){
 			beanB.setGbdPreparedDt( beanA.getWbdPreparedDt() ); 
 		}

 		/* Mapping: "wbdModifiedBy" -> "gbdModifiedBy" */
		if(  !Utils.isEmpty( beanA.getWbdModifiedBy() )  ){
 			beanB.setGbdModifiedBy( beanA.getWbdModifiedBy() ); 
 		}

 		/* Mapping: "wbdModifiedDt" -> "gbdModifiedDt" */
		if(  !Utils.isEmpty( beanA.getWbdModifiedDt() )  ){
 			beanB.setGbdModifiedDt( beanA.getWbdModifiedDt() ); 
 		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.dao.model.TTrnGaccBuildingQuo initializeDeepVO( com.rsaame.pas.dao.model.TTrnWctplPremiseQuo beanA, com.rsaame.pas.dao.model.TTrnGaccBuildingQuo beanB ){
  		BeanUtils.initializeBeanField( "id", beanB );
                                                    		return beanB;
	}
}
