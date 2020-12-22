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
 * <li>com.rsaame.kaizen.framework.model.PaginatedResult</li>
 * <li>com.rsaame.pas.vo.app.InsuredHistoryListVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( PaginatedResultToInsuredHistoryListVOMapper.class )</code>.
 */
public class PaginatedResultToInsuredHistoryListVOMapper extends BaseBeanToBeanMapper<com.rsaame.kaizen.framework.model.PaginatedResult, com.rsaame.pas.vo.app.InsuredHistoryListVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public PaginatedResultToInsuredHistoryListVOMapper(){
		super();
	}

	public PaginatedResultToInsuredHistoryListVOMapper( com.rsaame.kaizen.framework.model.PaginatedResult src, com.rsaame.pas.vo.app.InsuredHistoryListVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.app.InsuredHistoryListVO mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.app.InsuredHistoryListVO) Utils.newInstance( "com.rsaame.pas.vo.app.InsuredHistoryListVO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.kaizen.framework.model.PaginatedResult beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.app.InsuredHistoryListVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;
		
		/* Mapping: "objectArray" -> "insuredHistoryArray" */
		if(  !Utils.isEmpty( beanA.getObjectArray() )  ){
			BaseBeanToBeanMapper mapper = BeanMapperFactory.getMapperInstanceForRef( "CustomerInsuredHistoryToInsuredHistoryVOMapper", CopyUtils.asList( beanA.getObjectArray() ).get( 0 ) );
			beanB.setInsuredHistoryArray( CopyUtils.copy( beanA.getObjectArray(), beanB.getInsuredHistoryArray(), mapper.getClass() ));
		}
 		/* Mapping: "numberOfRecords" -> "numberOfRecords" */
		if(  !Utils.isEmpty( beanA.getNumberOfRecords() )  ){
 			beanB.setNumberOfRecords( beanA.getNumberOfRecords() ); 
 		}

 		/* Mapping: "recordsPerPage" -> "recordsPerPage" */
		if(  !Utils.isEmpty( beanA.getRecordsPerPage() )  ){
 			beanB.setRecordsPerPage( beanA.getRecordsPerPage() ); 
 		}

 		/* Mapping: "currentPage" -> "currentPage" */
		if(  !Utils.isEmpty( beanA.getCurrentPage() )  ){
 			beanB.setCurrentPage( beanA.getCurrentPage() ); 
 		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.app.InsuredHistoryListVO initializeDeepVO( com.rsaame.kaizen.framework.model.PaginatedResult beanA, com.rsaame.pas.vo.app.InsuredHistoryListVO beanB ){
         		return beanB;
	}
}
