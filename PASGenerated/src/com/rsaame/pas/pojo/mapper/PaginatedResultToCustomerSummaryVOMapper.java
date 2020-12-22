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
 * <li>com.rsaame.pas.vo.bus.CustomerSummaryVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( PaginatedResultToCustomerSummaryVOMapper.class )</code>.
 */
public class PaginatedResultToCustomerSummaryVOMapper extends BaseBeanToBeanMapper<com.rsaame.kaizen.framework.model.PaginatedResult, com.rsaame.pas.vo.bus.CustomerSummaryVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public PaginatedResultToCustomerSummaryVOMapper(){
		super();
	}

	public PaginatedResultToCustomerSummaryVOMapper( com.rsaame.kaizen.framework.model.PaginatedResult src, com.rsaame.pas.vo.bus.CustomerSummaryVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.CustomerSummaryVO mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.CustomerSummaryVO) Utils.newInstance( "com.rsaame.pas.vo.bus.CustomerSummaryVO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.kaizen.framework.model.PaginatedResult beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.CustomerSummaryVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);

		/* Mapping: "objectArray" -> "customerArray" */
		if(  !Utils.isEmpty( beanA.getObjectArray() )  ){
			BaseBeanToBeanMapper mapper = BeanMapperFactory.getMapperInstanceForRef( "TransactionToTransactionVOMapper", CopyUtils.asList( beanA.getObjectArray() ).get( 0 ) );
			beanB.setCustomerArray( CopyUtils.copy( beanA.getObjectArray(), beanB.getCustomerArray(), mapper.getClass() ));
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
	private static com.rsaame.pas.vo.bus.CustomerSummaryVO initializeDeepVO( com.rsaame.kaizen.framework.model.PaginatedResult beanA, com.rsaame.pas.vo.bus.CustomerSummaryVO beanB ){
         		return beanB;
	}
}
