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
 * <li>com.rsaame.kaizen.customer.model.CustomerBO</li>
 * <li>com.rsaame.pas.vo.bus.CustomerSearchVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( CustomerSearchVOToCustomerBOReverse.class )</code>.
 */
public class CustomerSearchVOToCustomerBOReverse extends BaseBeanToBeanMapper<com.rsaame.kaizen.customer.model.CustomerBO, com.rsaame.pas.vo.bus.CustomerSearchVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public CustomerSearchVOToCustomerBOReverse(){
		super();
	}

	public CustomerSearchVOToCustomerBOReverse( com.rsaame.kaizen.customer.model.CustomerBO src, com.rsaame.pas.vo.bus.CustomerSearchVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.CustomerSearchVO mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.CustomerSearchVO) Utils.newInstance( "com.rsaame.pas.vo.bus.CustomerSearchVO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.kaizen.customer.model.CustomerBO beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.CustomerSearchVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);

		/* Mapping: "customerId" -> "customerId" */
		if(  !Utils.isEmpty( beanA.getCustomerId() )  ){
 			beanB.setCustomerId( beanA.getCustomerId() ); 
 		}

 		/* Mapping: "emailId" -> "emailId" */
		if(  !Utils.isEmpty( beanA.getEmailId() )  ){
 			beanB.setEmailId( beanA.getEmailId() ); 
 		}

 		/* Mapping: "phoneNo" -> "phoneNo" */
		if(  !Utils.isEmpty( beanA.getPhoneNo() )  ){
 			beanB.setPhoneNo( beanA.getPhoneNo() ); 
 		}

 		/* Mapping: "mobileNo" -> "mobileNo" */
		if(  !Utils.isEmpty( beanA.getMobileNo() )  ){
 			beanB.setMobileNo( beanA.getMobileNo() ); 
 		}

 		/* Mapping: "policyQuoteNo" -> "policyQuoteNo" */
		if(  !Utils.isEmpty( beanA.getPolicyQuoteNo() )  ){
 			beanB.setPolicyQuoteNo( beanA.getPolicyQuoteNo() ); 
 		}

 		/* Mapping: "firstName" -> "firstName" */
		if(  !Utils.isEmpty( beanA.getFirstName() )  ){
 			beanB.setFirstName( beanA.getFirstName() ); 
 		}

 		/* Mapping: "lastName" -> "lastName" */
		if(  !Utils.isEmpty( beanA.getLastName() )  ){
 			beanB.setLastName( beanA.getLastName() ); 
 		}

 		/* Mapping: "phoneNo" -> "phoneNo" */
		if(  !Utils.isEmpty( beanA.getPhoneNo() )  ){
 			beanB.setPhoneNo( beanA.getPhoneNo() ); 
 		}

 		/* Mapping: "middleName" -> "middleName" */
		if(  !Utils.isEmpty( beanA.getMiddleName() )  ){
 			beanB.setMiddleName( beanA.getMiddleName() ); 
 		}

 		/* Mapping: "completeName" -> "completeName" */
		if(  !Utils.isEmpty( beanA.getCompleteName() )  ){
 			beanB.setCompleteName( beanA.getCompleteName() ); 
 		}

 		/* Mapping: "dateOfBirth" -> "dateOfBirth" */
		if(  !Utils.isEmpty( beanA.getDateOfBirth() )  ){
 			beanB.setDateOfBirth( beanA.getDateOfBirth() ); 
 		}

 		/* Mapping: "poBoxNo" -> "poBoxNo" */
		if(  !Utils.isEmpty( beanA.getPoBoxNo() )  ){
 			beanB.setPoBoxNo( beanA.getPoBoxNo() ); 
 		}

 		/* Mapping: "city" -> "city" */
		if(  !Utils.isEmpty( beanA.getCity() )  ){
 			beanB.setCity( beanA.getCity() ); 
 		}

 		/* Mapping: "contactNo" -> "contactNo" */
		if(  !Utils.isEmpty( beanA.getContactNo() )  ){
 			beanB.setContactNo( beanA.getContactNo() ); 
 		}

 		/* Mapping: "brokerName" -> "brokerName" */
		if(  !Utils.isEmpty( beanA.getBrokerName() )  ){
 			beanB.setBrokerName( beanA.getBrokerName() ); 
 		}

 		/* Mapping: "creationDate" -> "creationDate" */
		if(  !Utils.isEmpty( beanA.getCreationDate() )  ){
 			beanB.setCreationDate( beanA.getCreationDate() ); 
 		}

 		/* Mapping: "brokerId" -> "brokerId" */
		if(  !Utils.isEmpty( beanA.getBrokerId() )  ){
 			beanB.setBrokerId( beanA.getBrokerId() ); 
 		}

 		/* Mapping: "userId" -> "userId" */
		if(  !Utils.isEmpty( beanA.getUserId() )  ){
 			beanB.setUserId( beanA.getUserId() ); 
 		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.CustomerSearchVO initializeDeepVO( com.rsaame.kaizen.customer.model.CustomerBO beanA, com.rsaame.pas.vo.bus.CustomerSearchVO beanB ){
                                     		return beanB;
	}
}
