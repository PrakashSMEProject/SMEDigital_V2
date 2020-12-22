       /*
 * THIS IS A GENERATED CLASS. THIS CLASS SHOULD NOT BE EDITED.
 */
package com.rsaame.pas.pojo.mapper;

import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;

/**
 * Mapper class for:<ol>
 * <li>com.rsaame.pas.dao.model.TTrnPaymentDtl</li>
 * <li>com.rsaame.pas.vo.bus.PaymentDetailsVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( PaymentDetailsVOToTTrnPaymentDtlMapperReverse.class )</code>.
 */
public class PaymentDetailsVOToTTrnPaymentDtlMapperReverse extends BaseBeanToBeanMapper<com.rsaame.pas.dao.model.TTrnPaymentDtl, com.rsaame.pas.vo.bus.PaymentDetailsVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public PaymentDetailsVOToTTrnPaymentDtlMapperReverse(){
		super();
	}

	public PaymentDetailsVOToTTrnPaymentDtlMapperReverse( com.rsaame.pas.dao.model.TTrnPaymentDtl src, com.rsaame.pas.vo.bus.PaymentDetailsVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.PaymentDetailsVO mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.PaymentDetailsVO) Utils.newInstance( "com.rsaame.pas.vo.bus.PaymentDetailsVO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.dao.model.TTrnPaymentDtl beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.PaymentDetailsVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;
		
		/* Mapping: "pdlQutoteNo" -> "quoteNo" */
		if(  !Utils.isEmpty( beanA.getPdlQutoteNo() )  ){
 			beanB.setQuoteNo( beanA.getPdlQutoteNo() ); 
 		}

 		/* Mapping: "pdlTransId" -> "transactionId" */
		if(  !Utils.isEmpty( beanA.getPdlTransId() )  ){
 			beanB.setTransactionId( beanA.getPdlTransId() ); 
 		}

 		/* Mapping: "pdlPolicyId" -> "policyId" */
		if(  !Utils.isEmpty( beanA.getPdlPolicyId() )  ){
 			beanB.setPolicyId( beanA.getPdlPolicyId() ); 
 		}

 		/* Mapping: "pdlTransaDate" -> "authirizationTime" */
		if(  !Utils.isEmpty( beanA.getPdlTransaDate() )  ){
 			beanB.setAuthirizationTime( beanA.getPdlTransaDate() ); 
 		}

 		/* Mapping: "pdlTransaAmount" -> "authorizedPremiumAmt" */
		if(  !Utils.isEmpty( beanA.getPdlTransaAmount() )  ){
			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
 			beanB.setAuthorizedPremiumAmt( converter.getBFromA( beanA.getPdlTransaAmount() ) ); 
 		}

 		/* Mapping: "pdlTransStatus" -> "decision" */
		if(  !Utils.isEmpty( beanA.getPdlTransStatus() )  ){
 			beanB.setDecision( beanA.getPdlTransStatus() ); 
 		}

 		/* Mapping: "pdlMerchantRefNo" -> "transactionRefNo" */
		if(  !Utils.isEmpty( beanA.getPdlMerchantRefNo() )  ){
 			beanB.setTransactionRefNo( beanA.getPdlMerchantRefNo() ); 
 		}

 		/* Mapping: "pdlCurName" -> "currency" */
		if(  !Utils.isEmpty( beanA.getPdlCurName() )  ){
 			beanB.setCurrency( beanA.getPdlCurName() ); 
 		}

 		/* Mapping: "pdlErrCode" -> "responseCode" */
		if(  !Utils.isEmpty( beanA.getPdlErrCode() )  ){
 			beanB.setResponseCode( beanA.getPdlErrCode() ); 
 		}

 		/* Mapping: "pdlErrDesc" -> "message" */
		if(  !Utils.isEmpty( beanA.getPdlErrDesc() )  ){
 			beanB.setMessage( beanA.getPdlErrDesc() ); 
 		}

 		/* Mapping: "pdlCreditCrdNo" -> "cardNumber" */
		if(  !Utils.isEmpty( beanA.getPdlCreditCrdNo() )  ){
 			beanB.setCardNumber( beanA.getPdlCreditCrdNo() ); 
 		}

 		/* Mapping: "pdlBillingAddrs" -> "billingAddress" */
		if(  !Utils.isEmpty( beanA.getPdlBillingAddrs() )  ){
 			beanB.setBillingAddress( beanA.getPdlBillingAddrs() ); 
 		}

 		/* Mapping: "pdlCustName" -> "custName" */
		if(  !Utils.isEmpty( beanA.getPdlCustName() )  ){
 			beanB.setCustName( beanA.getPdlCustName() ); 
 		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.PaymentDetailsVO initializeDeepVO( com.rsaame.pas.dao.model.TTrnPaymentDtl beanA, com.rsaame.pas.vo.bus.PaymentDetailsVO beanB ){
                           		return beanB;
	}
}
