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
 * <li>com.rsaame.pas.vo.bus.PaymentDetailsVO</li>
 * <li>com.rsaame.pas.dao.model.TTrnPaymentDtl</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( PaymentDetailsVOToTTrnPaymentDtlMapper.class )</code>.
 */
public class PaymentDetailsVOToTTrnPaymentDtlMapper extends BaseBeanToBeanMapper<com.rsaame.pas.vo.bus.PaymentDetailsVO, com.rsaame.pas.dao.model.TTrnPaymentDtl>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public PaymentDetailsVOToTTrnPaymentDtlMapper(){
		super();
	}

	public PaymentDetailsVOToTTrnPaymentDtlMapper( com.rsaame.pas.vo.bus.PaymentDetailsVO src, com.rsaame.pas.dao.model.TTrnPaymentDtl dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.dao.model.TTrnPaymentDtl mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.dao.model.TTrnPaymentDtl) Utils.newInstance( "com.rsaame.pas.dao.model.TTrnPaymentDtl" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.vo.bus.PaymentDetailsVO beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.dao.model.TTrnPaymentDtl beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;
		
		/* Mapping: "quoteNo" -> "pdlQutoteNo" */
		if(  !Utils.isEmpty( beanA.getQuoteNo() )  ){
 			beanB.setPdlQutoteNo( beanA.getQuoteNo() ); 
 		}

 		/* Mapping: "transactionId" -> "pdlTransId" */
		if(  !Utils.isEmpty( beanA.getTransactionId() )  ){
 			beanB.setPdlTransId( beanA.getTransactionId() ); 
 		}
		else if(  !Utils.isEmpty( beanA.getTransactionRefNo() )  ){
			beanB.setPdlTransId( beanA.getTransactionRefNo() ); 
 		}

 		/* Mapping: "policyId" -> "pdlPolicyId" */
		if(  !Utils.isEmpty( beanA.getPolicyId() )  ){
 			beanB.setPdlPolicyId( beanA.getPolicyId() ); 
 		}

 		/* Mapping: "authirizationTime" -> "pdlTransaDate" */
		if(  !Utils.isEmpty( beanA.getAuthirizationTime() )  ){
 			beanB.setPdlTransaDate( beanA.getAuthirizationTime() ); 
 		}

 		/* Mapping: "authorizedPremiumAmt" -> "pdlTransaAmount" */
		if(  !Utils.isEmpty( beanA.getAuthorizedPremiumAmt() )  ){
			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
 			beanB.setPdlTransaAmount( converter.getAFromB ( beanA.getAuthorizedPremiumAmt() ) ); 
 		}

 		/* Mapping: "decision" -> "pdlTransStatus" */
		if(  !Utils.isEmpty( beanA.getDecision() )  ){
 			beanB.setPdlTransStatus( beanA.getDecision() ); 
 		}

 		/* Mapping: "transactionRefNo" -> "pdlMerchantRefNo" */
		if(  !Utils.isEmpty( beanA.getTransactionRefNo() )  ){
 			beanB.setPdlMerchantRefNo( beanA.getTransactionRefNo() ); 
 		}

 		/* Mapping: "currency" -> "pdlCurName" */
		if(  !Utils.isEmpty( beanA.getCurrency() )  ){
 			beanB.setPdlCurName( beanA.getCurrency() ); 
 		}

 		/* Mapping: "responseCode" -> "pdlErrCode" */
		if(  !Utils.isEmpty( beanA.getResponseCode() )  ){
 			beanB.setPdlErrCode( beanA.getResponseCode() ); 
 		}

 		/* Mapping: "message" -> "pdlErrDesc" */
		if(  !Utils.isEmpty( beanA.getMessage() )  ){
 			beanB.setPdlErrDesc( beanA.getMessage() ); 
 		}

 		/* Mapping: "cardNumber" -> "pdlCreditCrdNo" */
		if(  !Utils.isEmpty( beanA.getCardNumber() )  ){
 			beanB.setPdlCreditCrdNo( beanA.getCardNumber() ); 
 		}
		
 		/* Mapping: "cardType" -> "pdlCreditCrdTyp" */
		if(  !Utils.isEmpty( beanA.getCardType() )  ){
 			beanB.setPdlCreditCrdTyp( beanA.getCardType() ); 
 		}

 		/* Mapping: "billingAddress" -> "pdlBillingAddrs" */
		if(  !Utils.isEmpty( beanA.getBillingAddress() )  ){
 			beanB.setPdlBillingAddrs( beanA.getBillingAddress() ); 
 		}

 		/* Mapping: "custName" -> "pdlCustName" */
		if(  !Utils.isEmpty( beanA.getCustName() )  ){
 			beanB.setPdlCustName( beanA.getCustName() ); 
 		}
   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.dao.model.TTrnPaymentDtl initializeDeepVO( com.rsaame.pas.vo.bus.PaymentDetailsVO beanA, com.rsaame.pas.dao.model.TTrnPaymentDtl beanB ){
                           		return beanB;
	}
}