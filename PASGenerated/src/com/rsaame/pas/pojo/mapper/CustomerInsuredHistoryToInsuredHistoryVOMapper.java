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
 * <li>com.rsaame.kaizen.customer.model.CustomerInsuredHistory</li>
 * <li>com.rsaame.pas.vo.app.InsuredHistoryVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( CustomerInsuredHistoryToInsuredHistoryVOMapper.class )</code>.
 */
public class CustomerInsuredHistoryToInsuredHistoryVOMapper extends BaseBeanToBeanMapper<com.rsaame.kaizen.customer.model.CustomerInsuredHistory, com.rsaame.pas.vo.app.InsuredHistoryVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public CustomerInsuredHistoryToInsuredHistoryVOMapper(){
		super();
	}

	public CustomerInsuredHistoryToInsuredHistoryVOMapper( com.rsaame.kaizen.customer.model.CustomerInsuredHistory src, com.rsaame.pas.vo.app.InsuredHistoryVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.app.InsuredHistoryVO mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.app.InsuredHistoryVO) Utils.newInstance( "com.rsaame.pas.vo.app.InsuredHistoryVO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.kaizen.customer.model.CustomerInsuredHistory beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.app.InsuredHistoryVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;
		
		/* Mapping: "engFirstName" -> "name" */
		if(  !Utils.isEmpty( beanA.getEngFirstName() )  ){
 			beanB.setName( beanA.getEngFirstName() ); 
 		}

 		/* Mapping: "engZipCode" -> "poBox" */
		if(  !Utils.isEmpty( beanA.getEngZipCode() )  ){
 			beanB.setPoBox( beanA.getEngZipCode() ); 
 		}

 		/* Mapping: "customerStatusDesc" -> "insuredStatusDesc" */
		if(  !Utils.isEmpty( beanA.getCustomerStatusDesc() )  ){
 			beanB.setInsuredStatusDesc( beanA.getCustomerStatusDesc() ); 
 		}

 		/* Mapping: "engEmailId" -> "emailId" */
		if(  !Utils.isEmpty( beanA.getEngEmailId() )  ){
 			beanB.setEmailId( beanA.getEngEmailId() ); 
 		}

 		/* Mapping: "engMobileNo" -> "mobileNo" */
		if(  !Utils.isEmpty( beanA.getEngMobileNo() )  ){
 			beanB.setMobileNo( beanA.getEngMobileNo() ); 
 		}

 		/* Mapping: "customerTypeDesc" -> "insuredTypeDesc" */
		if(  !Utils.isEmpty( beanA.getCustomerTypeDesc() )  ){
 			beanB.setInsuredTypeDesc( beanA.getCustomerTypeDesc() ); 
 		}

 		/* Mapping: "engAddress" -> "address" */
		if(  !Utils.isEmpty( beanA.getEngAddress() )  ){
 			beanB.setAddress( beanA.getEngAddress() ); 
 		}

 		/* Mapping: "comp_id.insuredId" -> "insuredId" */
		if(  !Utils.isEmpty( beanA.getComp_id() ) && !Utils.isEmpty( beanA.getComp_id().getInsuredId() )  ){
 			beanB.setInsuredId( beanA.getComp_id().getInsuredId() ); 
 		}

 		/* Mapping: "transactionDateTime" -> "transactionDate" */
		if(  !Utils.isEmpty( beanA.getTransactionDateTime() )  ){
 			beanB.setTransactionDate( beanA.getTransactionDateTime() ); 
 		}

 		/* Mapping: "transactionDateTime" -> "lastModifiedDate" */
		if(  !Utils.isEmpty( beanA.getTransactionDateTime() )  ){
 			beanB.setLastModifiedDate( beanA.getTransactionDateTime() ); 
 		}

 		/* Mapping: "lastModifiedByUserName" -> "lastModifiedBy" */
		if(  !Utils.isEmpty( beanA.getLastModifiedByUserName() )  ){
 			beanB.setLastModifiedBy( beanA.getLastModifiedByUserName() ); 
 		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.app.InsuredHistoryVO initializeDeepVO( com.rsaame.kaizen.customer.model.CustomerInsuredHistory beanA, com.rsaame.pas.vo.app.InsuredHistoryVO beanB ){
                       		return beanB;
	}
}
