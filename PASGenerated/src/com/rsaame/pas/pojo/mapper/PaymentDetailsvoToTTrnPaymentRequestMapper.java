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
 * <li>com.rsaame.pas.dao.model.TTrnPaymentRequest</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( PaymentDetailsVOToTTrnPaymentRequestMapper.class )</code>.
 */
public class PaymentDetailsvoToTTrnPaymentRequestMapper extends BaseBeanToBeanMapper<com.rsaame.pas.vo.bus.PaymentDetailsVO, com.rsaame.pas.dao.model.TTrnPaymentRequest>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public PaymentDetailsvoToTTrnPaymentRequestMapper(){
		super();
	}

	public PaymentDetailsvoToTTrnPaymentRequestMapper( com.rsaame.pas.vo.bus.PaymentDetailsVO src, com.rsaame.pas.dao.model.TTrnPaymentRequest dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.dao.model.TTrnPaymentRequest mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.dao.model.TTrnPaymentRequest) Utils.newInstance( "com.rsaame.pas.dao.model.TTrnPaymentRequest" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.vo.bus.PaymentDetailsVO beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.dao.model.TTrnPaymentRequest beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;
		
	
		if(  !Utils.isEmpty( beanA.getTransactionRefNo() )  ){
			beanB.setPdrRefNo(( beanA.getTransactionRefNo())); 
 		}
		
		/* Mapping: "quoteNo" -> "pdrQutoteNo" */
		if(  !Utils.isEmpty( beanA.getQuoteNo() )  ){
 			beanB.setPdrQuoteNo( beanA.getQuoteNo() ); 
 		}
		
		/* Mapping: "EndtId" -> "pdrEndt" */
		if(  !Utils.isEmpty( beanA.getEndtID())  ){
 			beanB.setPdrEndtId( beanA.getEndtID() ); 
 		}
		
		
		/* Mapping: "policyId" -> "pdrPolicyId" */
		if(  !Utils.isEmpty( beanA.getPolicyId() )  ){
 			beanB.setPdrPolicyId( beanA.getPolicyId() ); 
 		}
		
		/* Mapping: "custName" -> "pdrCustName" */
		if(  !Utils.isEmpty( beanA.getCustName() )  ){
 			beanB.setPdrCustName( beanA.getCustName() ); 
 		}
   
		/* Mapping: "mobile" -> "pdrMobile" */
		if(  !Utils.isEmpty( beanA.getMobileNo() )  ){
 			beanB.setPdrCustMobile( beanA.getMobileNo() ); 
 		}
		
		/* Mapping: "email" -> "pdrEmail" */
		if(  !Utils.isEmpty( beanA.geteMailId() )  ){
 			beanB.setPdrCustEmailId( beanA.geteMailId() ); 
 		}
		
		/* Mapping: "Amt" -> "pdrTranAmt" */
		if(  !Utils.isEmpty( beanA.getRequestedPremiumAmt() )  ){
			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.setPdrTransAmount( converter.getAFromB ( beanA.getRequestedPremiumAmt() ) ); 
 		}
		
		/* Mapping: "TariffCode" -> "pdrTariffCode" */
		if(  !Utils.isEmpty( beanA.getTariffCode() )  ){
 			beanB.setPdrTarCode( beanA.getTariffCode() ); 
 		}
		
		/* Mapping: "DocumentCode" -> "pdrDocumentCode" */
		if(  !Utils.isEmpty( beanA.getDocumentCode() )  ){
 			beanB.setPdrDocumentCode( beanA.getDocumentCode() ); 
 		}
		
		/* Mapping: "date" -> "pdrTransaDate" */
		if(  !Utils.isEmpty( beanA.getTransDate() )  ){
 			beanB.setPdrTransDate( beanA.getTransDate() ); 
 		}
		
		/* Mapping: "request" -> "pdrRequest" */
		if(  !Utils.isEmpty( beanA.getRequestdeatils() )  ){
 			beanB.setPdrRequest( beanA.getRequestdeatils() ); 
 		}

		/* Mapping: "brokerCode" -> "pdrBrokerCode" */
		if(  !Utils.isEmpty( beanA.getBrokerCode() )  ){
 			beanB.setPdrBrokerCode( beanA.getBrokerCode() ); 
 		}
		
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.dao.model.TTrnPaymentRequest initializeDeepVO( com.rsaame.pas.vo.bus.PaymentDetailsVO beanA, com.rsaame.pas.dao.model.TTrnPaymentRequest beanB ){
                           		return beanB;
	}
}