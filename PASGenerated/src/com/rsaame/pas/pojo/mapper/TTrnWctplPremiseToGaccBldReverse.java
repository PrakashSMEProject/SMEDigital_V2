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
 * <li>com.rsaame.pas.dao.model.TTrnGaccBuildingQuo</li>
 * <li>com.rsaame.pas.dao.model.TTrnWctplPremiseQuo</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( TTrnWctplPremiseToGaccBldReverse.class )</code>.
 */
public class TTrnWctplPremiseToGaccBldReverse extends BaseBeanToBeanMapper<com.rsaame.pas.dao.model.TTrnGaccBuildingQuo, com.rsaame.pas.dao.model.TTrnWctplPremiseQuo>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public TTrnWctplPremiseToGaccBldReverse(){
		super();
	}

	public TTrnWctplPremiseToGaccBldReverse( com.rsaame.pas.dao.model.TTrnGaccBuildingQuo src, com.rsaame.pas.dao.model.TTrnWctplPremiseQuo dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.dao.model.TTrnWctplPremiseQuo mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.dao.model.TTrnWctplPremiseQuo) Utils.newInstance( "com.rsaame.pas.dao.model.TTrnWctplPremiseQuo" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.dao.model.TTrnGaccBuildingQuo beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.dao.model.TTrnWctplPremiseQuo beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;
		
		/* Mapping: "id.gbdValidityStartDate" -> "id.wbdValidityStartDate" */
		if(  !Utils.isEmpty( beanA.getId() ) && !Utils.isEmpty( beanA.getId().getGbdValidityStartDate() )  ){
 			beanB.getId().setWbdValidityStartDate( beanA.getId().getGbdValidityStartDate() ); 
 		}

 		/* Mapping: "gbdRskCode" -> "wbdRskCode" */
		if(  !Utils.isEmpty( beanA.getGbdRskCode() )  ){
 			beanB.setWbdRskCode( beanA.getGbdRskCode() ); 
 		}

 		/* Mapping: "gbdStartDate" -> "wbdStartDate" */
		if(  !Utils.isEmpty( beanA.getGbdStartDate() )  ){
 			beanB.setWbdStartDate( beanA.getGbdStartDate() ); 
 		}

 		/* Mapping: "gbdEndDate" -> "wbdEndDate" */
		if(  !Utils.isEmpty( beanA.getGbdEndDate() )  ){
 			beanB.setWbdEndDate( beanA.getGbdEndDate() ); 
 		}

 		/* Mapping: "gbdEName" -> "wbdEName" */
		if(  !Utils.isEmpty( beanA.getGbdEName() )  ){
 			beanB.setWbdEName( beanA.getGbdEName() ); 
 		}

 		/* Mapping: "gbdWayNo" -> "wbdWayNo" */
		if(  !Utils.isEmpty( beanA.getGbdWayNo() )  ){
 			beanB.setWbdWayNo( beanA.getGbdWayNo() ); 
 		}

 		/* Mapping: "gbdEStreetName" -> "wbdEStreetName" */
		if(  !Utils.isEmpty( beanA.getGbdEStreetName() )  ){
 			beanB.setWbdEStreetName( beanA.getGbdEStreetName() ); 
 		}

 		/* Mapping: "gbdFlatNo" -> "wbdFlatNo" */
		if(  !Utils.isEmpty( beanA.getGbdFlatNo() )  ){
 			beanB.setWbdFlatNo( beanA.getGbdFlatNo() ); 
 		}

 		/* Mapping: "gbdDirCode" -> "wbdDirCode" */
		if(  !Utils.isEmpty( beanA.getGbdDirCode() )  ){
 			beanB.setWbdDirCode( beanA.getGbdDirCode() ); 
 		}

 		/* Mapping: "gbdNo" -> "wbdNo" */
		if(  !Utils.isEmpty( beanA.getGbdNo() )  ){
 			beanB.setWbdNo( beanA.getGbdNo() ); 
 		}

 		/* Mapping: "gbdMunCode" -> "wbdMunCode" */
		if(  !Utils.isEmpty( beanA.getGbdMunCode() )  ){
 			beanB.setWbdMunCode( beanA.getGbdMunCode() ); 
 		}

 		/* Mapping: "gbdMunCode" -> "wbdMunCode" */
		if(  !Utils.isEmpty( beanA.getGbdMunCode() )  ){
 			beanB.setWbdMunCode( beanA.getGbdMunCode() ); 
 		}

 		/* Mapping: "gbdAName" -> "wbdAName" */
		if(  !Utils.isEmpty( beanA.getGbdAName() )  ){
 			beanB.setWbdAName( beanA.getGbdAName() ); 
 		}

 		/* Mapping: "gbdSumInsured" -> "wbdSumInsured" */
		if(  !Utils.isEmpty( beanA.getGbdSumInsured() )  ){
 			beanB.setWbdSumInsured( beanA.getGbdSumInsured() ); 
 		}

 		/* Mapping: "gbdBldId" -> "id.wbdId" */
		if(  !Utils.isEmpty( beanA.getGbdBldId() )  ){
 			beanB.getId().setWbdId( beanA.getGbdBldId() ); 
 		}

 		/* Mapping: "gbdPolicyId" -> "wbdPolicyId" */
		if(  !Utils.isEmpty( beanA.getGbdPolicyId() )  ){
 			beanB.setWbdPolicyId( beanA.getGbdPolicyId() ); 
 		}

 		/* Mapping: "gbdEndtId" -> "wbdEndtId" */
		if(  !Utils.isEmpty( beanA.getGbdEndtId() )  ){
 			beanB.setWbdEndtId( beanA.getGbdEndtId() ); 
 		}

 		/* Mapping: "gbdStatus" -> "wbdStatus" */
		if(  !Utils.isEmpty( beanA.getGbdStatus() )  ){
 			beanB.setWbdStatus( beanA.getGbdStatus() ); 
 		}

 		/* Mapping: "gbdValidityExpiryDate" -> "wbdValidityExpiryDate" */
		if(  !Utils.isEmpty( beanA.getGbdValidityExpiryDate() )  ){
 			beanB.setWbdValidityExpiryDate( beanA.getGbdValidityExpiryDate() ); 
 		}

 		/* Mapping: "gbdRiRskCode" -> "wbdRiRskCode" */
		if(  !Utils.isEmpty( beanA.getGbdRiRskCode() )  ){
 			beanB.setWbdRiRskCode( beanA.getGbdRiRskCode() ); 
 		}

 		/* Mapping: "gbdZip" -> "wbdZip" */
		if(  !Utils.isEmpty( beanA.getGbdZip() )  ){
 			beanB.setWbdZip( beanA.getGbdZip() ); 
 		}

 		/* Mapping: "gbdCbCode" -> "wbdCbCode" */
		if(  !Utils.isEmpty( beanA.getGbdCbCode() )  ){
 			beanB.setWbdCbCode( beanA.getGbdCbCode() ); 
 		}

 		/* Mapping: "gbdPreparedBy" -> "wbdPreparedBy" */
		if(  !Utils.isEmpty( beanA.getGbdPreparedBy() )  ){
 			beanB.setWbdPreparedBy( beanA.getGbdPreparedBy() ); 
 		}

 		/* Mapping: "gbdPreparedDt" -> "wbdPreparedDt" */
		if(  !Utils.isEmpty( beanA.getGbdPreparedDt() )  ){
 			beanB.setWbdPreparedDt( beanA.getGbdPreparedDt() ); 
 		}

 		/* Mapping: "gbdModifiedBy" -> "wbdModifiedBy" */
		if(  !Utils.isEmpty( beanA.getGbdModifiedBy() )  ){
 			beanB.setWbdModifiedBy( beanA.getGbdModifiedBy() ); 
 		}

 		/* Mapping: "gbdModifiedDt" -> "wbdModifiedDt" */
		if(  !Utils.isEmpty( beanA.getGbdModifiedDt() )  ){
 			beanB.setWbdModifiedDt( beanA.getGbdModifiedDt() ); 
 		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.dao.model.TTrnWctplPremiseQuo initializeDeepVO( com.rsaame.pas.dao.model.TTrnGaccBuildingQuo beanA, com.rsaame.pas.dao.model.TTrnWctplPremiseQuo beanB ){
  		BeanUtils.initializeBeanField( "id", beanB );
                                                    		return beanB;
	}
}
