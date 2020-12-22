       /*
 * THIS IS A GENERATED CLASS. THIS CLASS SHOULD NOT BE EDITED.
 */
package com.rsaame.pas.pojo.mapper;

import java.util.ArrayList;
import java.util.List;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.GrantedAuthorityImpl;

import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.kaizen.framework.model.RSAUser;
import com.rsaame.kaizen.framework.model.ServiceContext;

/**
 * Mapper class for:<ol>
 * <li>com.rsaame.pas.vo.bus.SearchTransactionCriteriaVO</li>
 * <li>com.rsaame.kaizen.policy.model.Transaction</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( SearchTransactionCriteriaVOToTransactionMapper.class )</code>.
 */
public class SearchTransactionCriteriaVOToTransactionMapper extends BaseBeanToBeanMapper<com.rsaame.pas.vo.bus.SearchTransactionCriteriaVO, com.rsaame.kaizen.policy.model.Transaction>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public SearchTransactionCriteriaVOToTransactionMapper(){
		super();
	}

	public SearchTransactionCriteriaVOToTransactionMapper( com.rsaame.pas.vo.bus.SearchTransactionCriteriaVO src, com.rsaame.kaizen.policy.model.Transaction dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.kaizen.policy.model.Transaction mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.kaizen.policy.model.Transaction) Utils.newInstance( "com.rsaame.kaizen.policy.model.Transaction" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.vo.bus.SearchTransactionCriteriaVO beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.kaizen.policy.model.Transaction beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);

		/* Mapping: "transaction.clazz" -> "transactionClass" */
		if(  !Utils.isEmpty( beanA.getTransaction() ) && !Utils.isEmpty( beanA.getTransaction().getClazz() )  ){
 			beanB.setTransactionClass( beanA.getTransaction().getClazz() ); 
 		}

 		/* Mapping: "transaction.quoteNo" -> "transactionNumber" */
		if(  !Utils.isEmpty( beanA.getTransaction() ) && !Utils.isEmpty( beanA.getTransaction().getQuoteNo() )  ){
 			beanB.setTransactionNumber( beanA.getTransaction().getQuoteNo() ); 
 		}

 		/* Mapping: "transaction.policyNo" -> "transactionNumberPolicy" */
		if(  !Utils.isEmpty( beanA.getTransaction() ) && !Utils.isEmpty( beanA.getTransaction().getPolicyNo() )  ){
 			beanB.setTransactionNumberPolicy( beanA.getTransaction().getPolicyNo() ); 
 		}

 		/* Mapping: "transaction.transactionFrom" -> "transactionFrom" */
		if(  !Utils.isEmpty( beanA.getTransaction() ) && !Utils.isEmpty( beanA.getTransaction().getTransactionFrom() )  ){
 			beanB.setTransactionFrom( beanA.getTransaction().getTransactionFrom() ); 
 		}

 		/* Mapping: "transaction.transactionTo" -> "transactionTo" */
		if(  !Utils.isEmpty( beanA.getTransaction() ) && !Utils.isEmpty( beanA.getTransaction().getTransactionTo() )  ){
 			beanB.setTransactionTo( beanA.getTransaction().getTransactionTo() ); 
 		}

 		/* Mapping: "transaction.companyName" -> "transactionCompanyName" */
		if(  !Utils.isEmpty( beanA.getTransaction() ) && !Utils.isEmpty( beanA.getTransaction().getCompanyName() )  ){
 			beanB.setTransactionCompanyName( beanA.getTransaction().getCompanyName() ); 
 		}

 		/* Mapping: "transaction.customerName" -> "transactionCustomerName" */
		if(  !Utils.isEmpty( beanA.getTransaction() ) && !Utils.isEmpty( beanA.getTransaction().getCustomerName() )  ){
 			beanB.setTransactionCustomerName( beanA.getTransaction().getCustomerName() ); 
 		}

 		/* Mapping: "transaction.brokerName" -> "transactionBrokerName" */
		if(  !Utils.isEmpty( beanA.getTransaction() ) && !Utils.isEmpty( beanA.getTransaction().getBrokerName() )  ){
 			beanB.setTransactionBrokerName( beanA.getTransaction().getBrokerName() ); 
 		}

 		/* Mapping: "transaction.scheme" -> "transactionScheme" */
		if(  !Utils.isEmpty( beanA.getTransaction() ) && !Utils.isEmpty( beanA.getTransaction().getScheme() )  ){
 			beanB.setTransactionScheme( beanA.getTransaction().getScheme() ); 
 		}

 		/* Mapping: "transaction.transactionEffectiveDate" -> "transactionEffectiveDate" */
		if(  !Utils.isEmpty( beanA.getTransaction() ) && !Utils.isEmpty( beanA.getTransaction().getTransactionEffectiveDate() )  ){
 			beanB.setTransactionEffectiveDate( beanA.getTransaction().getTransactionEffectiveDate() ); 
 		}

 		/* Mapping: "transaction.transactionExpiryDate" -> "transactionExpiryDate" */
		if(  !Utils.isEmpty( beanA.getTransaction() ) && !Utils.isEmpty( beanA.getTransaction().getTransactionExpiryDate() )  ){
 			beanB.setTransactionExpiryDate( beanA.getTransaction().getTransactionExpiryDate() ); 
 		}

 		/* Mapping: "transaction.lastModifiedBy" -> "transactionLastModifiedBy" */
		if(  !Utils.isEmpty( beanA.getTransaction() ) && !Utils.isEmpty( beanA.getTransaction().getLastModifiedBy() )  ){
 			beanB.setTransactionLastModifiedBy( beanA.getTransaction().getLastModifiedBy() ); 
 		}

 		/* Mapping: "transaction.createdBy" -> "transactionCreatedBy" */
		if(  !Utils.isEmpty( beanA.getTransaction() ) && !Utils.isEmpty( beanA.getTransaction().getCreatedBy() )  ){
 			beanB.setTransactionCreatedBy( beanA.getTransaction().getCreatedBy() ); 
 		}

 		/* Mapping: "transaction.distributionCode" -> "transactionDistributionCode" */
		if(  !Utils.isEmpty( beanA.getTransaction() ) && !Utils.isEmpty( beanA.getTransaction().getDistributionCode() )  ){
 			beanB.setTransactionDistributionCode( beanA.getTransaction().getDistributionCode() ); 
 		}

 		/* Mapping: "transaction.status" -> "transactionStatus" */
		if(  !Utils.isEmpty( beanA.getTransaction() ) && !Utils.isEmpty( beanA.getTransaction().getStatus() )  ){
 			beanB.setTransactionStatus( beanA.getTransaction().getStatus() ); 
 		}

 		/* Mapping: "transaction.transactionNo" -> "transactionNumber" */
		if(  !Utils.isEmpty( beanA.getTransaction() ) && !Utils.isEmpty( beanA.getTransaction().getTransactionNo() )  ){
 			beanB.setTransactionNumber( beanA.getTransaction().getTransactionNo() ); 
 		}

 		/* Mapping: "transaction.tempTransactionEndtNo" -> "tempTransactionEndtNo" */
		if(  !Utils.isEmpty( beanA.getTransaction() ) && !Utils.isEmpty( beanA.getTransaction().getTempTransactionEndtNo() )  ){
 			beanB.setTempTransactionEndtNo( beanA.getTransaction().getTempTransactionEndtNo() ); 
 		}

 		/* Mapping: "quoteEntered" -> "quoteEntered" */
		if(  !Utils.isEmpty( beanA.getQuoteEntered() )  ){
 			beanB.setQuoteEntered( beanA.getQuoteEntered() ); 
 		}

 		/* Mapping: "policyEntered" -> "policyEntered" */
		if(  !Utils.isEmpty( beanA.getPolicyEntered() )  ){
 			beanB.setPolicyEntered( beanA.getPolicyEntered() ); 
 		}

 		/* Mapping: "quotePolicy" -> "quotePolicy" */
		if(  !Utils.isEmpty( beanA.getQuotePolicy() )  ){
 			beanB.setQuotePolicy( beanA.getQuotePolicy() ); 
 		}

 		/* Mapping: "exactSearch" -> "exactSearch" */
		if(  !Utils.isEmpty( beanA.getExactSearch() )  ){
 			beanB.setExactSearch( beanA.getExactSearch() ); 
 		}

 		/* Mapping: "lastTransaction" -> "lastTransaction" */
		if(  !Utils.isEmpty( beanA.getLastTransaction() )  ){
 			beanB.setLastTransaction( beanA.getLastTransaction() ); 
 		}
		if(  !Utils.isEmpty( beanA.getTransaction() ) && !Utils.isEmpty( beanA.getTransaction().getLocationCode() )  ){
			 beanB.setLocationCode(beanA.getTransaction().getLocationCode());
		}
		if(  !Utils.isEmpty( beanA.getTransaction() ) && !Utils.isEmpty( beanA.getTransaction().getPolicyType() )  ){
			 beanB.setTransactionPolicyType(beanA.getTransaction().getPolicyType());
		}
		/* Mapping: "exactSearch" -> "exactSearch" */
		if(  !Utils.isEmpty( beanA.getTransaction() ) && !Utils.isEmpty( beanA.getTransaction().getMobileNo() )  ){
			 beanB.setMobileNo(beanA.getTransaction().getMobileNo());
		}

		/* Mapping: "exactSearch" -> "exactSearch" */
		if(  !Utils.isEmpty( beanA.getTransaction() ) && !Utils.isEmpty( beanA.getTransaction().getPhoneNo() )  ){
			 beanB.setPhoneNo(beanA.getTransaction().getPhoneNo());
		}

		/* Mapping: "exactSearch" -> "exactSearch" */
		if(  !Utils.isEmpty( beanA.getTransaction() ) && !Utils.isEmpty( beanA.getTransaction().getPolReferenceNo() )  ){
			 beanB.setPolReferenceNo(beanA.getTransaction().getPolReferenceNo());
		}

		/* Mapping: "ForHistoryView" -> "ForHistoryView" */
		if(  !Utils.isEmpty( beanA.getTransaction() ) && !Utils.isEmpty( beanA.getForHistoryView())){
		    beanB.setForHistoryView(beanA.getForHistoryView());
		
			
		}

		
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.kaizen.policy.model.Transaction initializeDeepVO( com.rsaame.pas.vo.bus.SearchTransactionCriteriaVO beanA, com.rsaame.kaizen.policy.model.Transaction beanB ){
                                             		return beanB;
	}
}
