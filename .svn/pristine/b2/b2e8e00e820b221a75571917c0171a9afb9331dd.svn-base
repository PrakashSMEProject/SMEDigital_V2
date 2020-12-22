       /*
 * THIS IS A GENERATED CLASS. THIS CLASS SHOULD NOT BE EDITED.
 */
package com.rsaame.pas.pojo.mapper;

import java.math.BigDecimal;

import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.beanmap.BeanMapperFactory;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.BeanUtils;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.cmn.currency.Currency;

/**
 * Mapper class for:<ol>
 * <li>com.rsaame.kaizen.policy.model.Transaction</li>
 * <li>com.rsaame.pas.vo.bus.TransactionVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( TransactionToTransactionVOMapper.class )</code>.
 */
public class TransactionToTransactionVOMapper extends BaseBeanToBeanMapper<com.rsaame.kaizen.policy.model.Transaction, com.rsaame.pas.vo.bus.TransactionVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public TransactionToTransactionVOMapper(){
		super();
	}

	public TransactionToTransactionVOMapper( com.rsaame.kaizen.policy.model.Transaction src, com.rsaame.pas.vo.bus.TransactionVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.TransactionVO mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.TransactionVO) Utils.newInstance( "com.rsaame.pas.vo.bus.TransactionVO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.kaizen.policy.model.Transaction beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.TransactionVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;
		
		/* Mapping: "transactionCustomerName" -> "transactionCustomerName" */
		if(  !Utils.isEmpty( beanA.getTransactionCustomerName() )  ){
 			beanB.setCustomerName( beanA.getTransactionCustomerName() ); 
 		}
		
		/* Mapping: "transactionCompanyName" -> "companyName" */
		if(  !Utils.isEmpty( beanA.getTransactionCompanyName() )  ){
 			beanB.setCompanyName( beanA.getTransactionCompanyName() ); 
 		}

 		/* Mapping: "transactionLastModifiedBy" -> "lastModifiedBy" */
		if(  !Utils.isEmpty( beanA.getTransactionLastModifiedBy() )  ){
 			beanB.setLastModifiedBy( beanA.getTransactionLastModifiedBy() ); 
 		}

 		/* Mapping: "transactionType" -> "transactionType" */
		if(  !Utils.isEmpty( beanA.getTransactionType() )  ){
 			beanB.setTransactionType( beanA.getTransactionType() ); 
 		}

 		/* Mapping: "transactionNumber" -> "transactionNo" */
		if(  !Utils.isEmpty( beanA.getTransactionNumber() )  ){
 			beanB.setTransactionNo( beanA.getTransactionNumber() ); 
 		}

 		/* Mapping: "transactionEndorsementNumber" -> "transactionEndNo" */
		if(  !Utils.isEmpty( beanA.getTransactionEndorsementNumber() )  ){
 			beanB.setTransactionEndNo( beanA.getTransactionEndorsementNumber() ); 
 		}

 		/* Mapping: "transactionPolicyNumber" -> "transactionPolicyNumber" */
		if(  !Utils.isEmpty( beanA.getTransactionPolicyNumber() )  ){
 			beanB.setTransactionPolicyNumber( beanA.getTransactionPolicyNumber() ); 
 		}

 		/* Mapping: "transactionEndtId" -> "transactionEndtId" */
		if(  !Utils.isEmpty( beanA.getTransactionEndtId() )  ){
 			beanB.setTransactionEndtId( beanA.getTransactionEndtId() ); 
 		}

 		/* Mapping: "transactionPolicyType" -> "policyType" */
		if(  !Utils.isEmpty( beanA.getTransactionPolicyType() )  ){
 			beanB.setPolicyType( beanA.getTransactionPolicyType() ); 
 		}

 		/* Mapping: "transactionDateTime" -> "transactionDate" */
		if(  !Utils.isEmpty( beanA.getTransactionDateTime() )  ){
 			beanB.setTransactionDate( beanA.getTransactionDateTime() ); 
 		}

 		/* Mapping: "effectiveDate" -> "effectiveDate" */
		if(  !Utils.isEmpty( beanA.getEffectiveDate() )  ){
 			beanB.setEffectiveDate( beanA.getEffectiveDate() ); 
 		}

 		/* Mapping: "expiryDate" -> "expiryDate" */
		if(  !Utils.isEmpty( beanA.getExpiryDate() )  ){
 			beanB.setExpiryDate( beanA.getExpiryDate() ); 
 		}

 		/* Mapping: "transactionSumInsured" -> "transactionSumInsured" */
		if(  !Utils.isEmpty( beanA.getTransactionSumInsured() )  ){
 			beanB.setTransactionSumInsured( new BigDecimal( Currency.getUnformattedScaledCurrency( beanA.getTransactionSumInsured() ))); 
 		}

 		/* Mapping: "transactionFinalPremium" -> "transactionPremium" */
		if(  !Utils.isEmpty( beanA.getTransactionFinalPremium() )  ){
 			//com.rsaame.pas.cmn.converter.BigDecimalStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalStringConverter.class, "", "" );
			//beanB.setTransactionPremium( converter.getTypeOfB().cast( converter.getBFromA( beanA.getTransactionFinalPremium() ) ) );
			beanB.setTransactionPremium(  Currency.getUnformattedScaledCurrency( beanA.getTransactionFinalPremium() ) );
  		}

 		/* Mapping: "transactionStatus" -> "status" */
		if(  !Utils.isEmpty( beanA.getTransactionStatus() )  ){
 			beanB.setStatus( beanA.getTransactionStatus() ); 
 		}

 		/* Mapping: "policyApprover" -> "referredTo" */
		if(  !Utils.isEmpty( beanA.getPolicyApprover() )  ){
 			beanB.setReferredTo( beanA.getPolicyApprover() ); 
 		}

 		/* Mapping: "locationName" -> "branch" */
		if(  !Utils.isEmpty( beanA.getLocationName() )  ){
 			beanB.setBranch( beanA.getLocationName() ); 
 		}

 		/* Mapping: "policyTariffCode" -> "policyTariffCode" */
		if(  !Utils.isEmpty( beanA.getPolicyTariffCode() )  ){
 			beanB.setPolicyTariffCode( beanA.getPolicyTariffCode() ); 
 		}
		
		if(  !Utils.isEmpty( beanA.getComments() )  ){
 			beanB.setComments( beanA.getComments() ); 
 		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.TransactionVO initializeDeepVO( com.rsaame.kaizen.policy.model.Transaction beanA, com.rsaame.pas.vo.bus.TransactionVO beanB ){
                                   		return beanB;
	}
}
