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
 * <li>com.rsaame.pas.vo.bus.TransactionSummaryVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( PaginatedResultToTransactionSummaryVOMapper.class )</code>.
 */
public class PaginatedResultToTransactionSummaryVOMapper extends BaseBeanToBeanMapper<com.rsaame.kaizen.framework.model.PaginatedResult, com.rsaame.pas.vo.bus.TransactionSummaryVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public PaginatedResultToTransactionSummaryVOMapper(){
		super();
	}

	public PaginatedResultToTransactionSummaryVOMapper( com.rsaame.kaizen.framework.model.PaginatedResult src, com.rsaame.pas.vo.bus.TransactionSummaryVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.TransactionSummaryVO mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.TransactionSummaryVO) Utils.newInstance( "com.rsaame.pas.vo.bus.TransactionSummaryVO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.kaizen.framework.model.PaginatedResult beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.TransactionSummaryVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);

		/* Mapping: "objectArray" -> "transactionArray" */
		if(  !Utils.isEmpty( beanA.getObjectArray() )  ){
			BaseBeanToBeanMapper mapper = BeanMapperFactory.getMapperInstanceForRef( "TransactionToTransactionVOMapper", CopyUtils.asList( beanA.getObjectArray() ).get( 0 ) );
			beanB.setTransactionArray( CopyUtils.copy( beanA.getObjectArray(), beanB.getTransactionArray(), mapper.getClass() ));
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
	 * This method initializes the deepVOs inside the destination bean.
	 * 
	 * @param beanB
	 * @return beanB
	 */
	private static com.rsaame.pas.vo.bus.TransactionSummaryVO initializeDeepVO( com.rsaame.kaizen.framework.model.PaginatedResult beanA, com.rsaame.pas.vo.bus.TransactionSummaryVO beanB ){
         		return beanB;
	}
}
