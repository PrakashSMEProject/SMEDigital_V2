       /*
 * THIS IS A GENERATED CLASS. THIS CLASS SHOULD NOT BE EDITED.
 */
package com.rsaame.pas.request.vo.mapper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.vo.bus.LOB;

/**
 * Mapper class for:<ol>
 * <li>javax.servlet.http.HttpServletRequest</li>
 * <li>com.rsaame.pas.vo.bus.PaymentDetailsVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( RequestToPaymentDetailsVOMapper.class )</code>.
 */
public class RequestToPaymentDetailsVOMapper extends BaseBeanToBeanMapper<javax.servlet.http.HttpServletRequest, com.rsaame.pas.vo.bus.PaymentDetailsVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public RequestToPaymentDetailsVOMapper(){
		super();
	}

	public RequestToPaymentDetailsVOMapper( javax.servlet.http.HttpServletRequest src, com.rsaame.pas.vo.bus.PaymentDetailsVO dest ){
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
		javax.servlet.http.HttpServletRequest beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.PaymentDetailsVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;
		
		/* Mapping: "auth_amount" -> "authorizedPremiumAmt" */
		if( !Utils.isEmpty( src.getParameter( "auth_amount" ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
 			beanB.setAuthorizedPremiumAmt( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "auth_amount" ) ) ) ); 
 		}

 		/* Mapping: "auth_code" -> "authizationCode" */
		if( !Utils.isEmpty( src.getParameter( "auth_code" ) ) ){
 			beanB.setAuthizationCode( beanA.getParameter( "auth_code" ) ); 
 		}

 		/* Mapping: "auth_time" -> "authirizationTime" */
		if( !Utils.isEmpty( src.getParameter( com.Constant.CONST_SIGNED_DATE_TIME ) ) ){
			System.out.println("signed_date_tim_1"+src.getParameter("signed_date_time"));
			
			
			TimeZone dubaiTimeZone = TimeZone.getTimeZone("Asia/Dubai");
			
			 DateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
			    utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
			    DateFormat dubaiFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
			    dubaiFormat.setTimeZone(dubaiTimeZone);
			    Date timestamp;
			    String output = null;
				try {
					timestamp = utcFormat.parse( src.getParameter( com.Constant.CONST_SIGNED_DATE_TIME ) );
					  output = dubaiFormat.format(timestamp);
					  System.out.println("DubaiTime"+output);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			  
			  
			


			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", "format=yyyy-MM-dd'T'HH:mm:ss'Z'" );
			beanB.setAuthirizationTime( converter.getTypeOfA().cast( converter.getAFromB(output) ) );
  		}

 		/* Mapping: "transactionId" -> "transaction_id" */
		if( !Utils.isEmpty( src.getParameter( "transaction_id" ) ) ){
 			beanB.setTransactionId( beanA.getParameter( "transaction_id" ) ); 
 		}

 		/* Mapping: "decision" -> "decision" */
		if( !Utils.isEmpty( src.getParameter( "decision" ) ) ){
 			beanB.setDecision( beanA.getParameter( "decision" ) ); 
 		}

 		/* Mapping: "reason_code" -> "responseCode" */
		if( !Utils.isEmpty( src.getParameter( "reason_code" ) ) ){
 			beanB.setResponseCode( Integer.valueOf( beanA.getParameter( "reason_code" ) ) ); 
 		}

 		/* Mapping: "decision_rmsg" -> "message" */
		if( !Utils.isEmpty( src.getParameter( "decision_rmsg" ) ) ){
 			beanB.setMessage( beanA.getParameter( "decision_rmsg" ) ); 
 		}

 		/* Mapping: "req_amount" -> "requestedPremiumAmt" */
		if( !Utils.isEmpty( src.getParameter( "req_amount" ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
 			beanB.setRequestedPremiumAmt( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "req_amount" ) ) ) ); 
 		}

 		/* Mapping: "req_bill_to_email" -> "eMailId" */
		if( !Utils.isEmpty( src.getParameter( "req_bill_to_email" ) ) ){
 			beanB.seteMailId( beanA.getParameter( "req_bill_to_email" ) ); 
 		}

 		/* Mapping: "req_bill_to_forename" -> "firstName" */
		if( !Utils.isEmpty( src.getParameter( "req_bill_to_forename" ) ) ){
 			beanB.setFirstName( beanA.getParameter( "req_bill_to_forename" ) ); 
 		}

 		/* Mapping: "req_bill_to_surname" -> "surname" */
		if( !Utils.isEmpty( src.getParameter( "req_bill_to_surname" ) ) ){
 			beanB.setSurname( beanA.getParameter( "req_bill_to_surname" ) ); 
 		}

 		/* Mapping: "req_card_expiry_date" -> "cardExipiryDate" */
		if( !Utils.isEmpty( src.getParameter( "req_card_expiry_date" ) ) ){
 			beanB.setCardExipiryDate( beanA.getParameter( "req_card_expiry_date" ) ); 
 		}

 		/* Mapping: com.Constant.CONST_REQ_CARD_NUMBER -> "cardNumber" */
		if( !Utils.isEmpty( src.getParameter( com.Constant.CONST_REQ_CARD_NUMBER ) ) ){
 			beanB.setCardNumber( beanA.getParameter( com.Constant.CONST_REQ_CARD_NUMBER ).substring( beanA.getParameter( com.Constant.CONST_REQ_CARD_NUMBER ).length() - 4 ) );
 		}

 		/* Mapping: "req_payment_method" -> "paymentMode" */
		if( !Utils.isEmpty( src.getParameter( "req_payment_method" ) ) ){
 			beanB.setPaymentMode( beanA.getParameter( "req_payment_method" ) ); 
 		}

 		/* Mapping: com.Constant.CONST_REQ_REFERENCE_NUMBER -> "transactionRefNo" */
		if( !Utils.isEmpty( src.getParameter( com.Constant.CONST_REQ_REFERENCE_NUMBER ) ) ){
			beanB.setTransactionRefNo( beanA.getParameter( com.Constant.CONST_REQ_REFERENCE_NUMBER ) );
	        beanB.setQuoteNo( Long.valueOf( beanA.getParameter( com.Constant.CONST_REQ_REFERENCE_NUMBER ).split( "-" )[1] ) );
	        beanB.setPolicyId( Long.valueOf( beanA.getParameter( com.Constant.CONST_REQ_REFERENCE_NUMBER ).split( "-" )[2] ) );
 		}

 		/* Mapping: "req_transaction_uuid" -> "transactionUuid" */
		if( !Utils.isEmpty( src.getParameter( "req_transaction_uuid" ) ) ){
 			beanB.setTransactionUuid( beanA.getParameter( "req_transaction_uuid" ) ); 
 		}

 		/* Mapping: "score_card_issuer" -> "cardIssuer" */
		if( !Utils.isEmpty( src.getParameter( "score_card_issuer" ) ) ){
 			beanB.setCardIssuer( beanA.getParameter( "score_card_issuer" ) ); 
 		}

 		/* Mapping: "score_card_scheme" -> "cardType" */
		if( !Utils.isEmpty( src.getParameter( "score_card_scheme" ) ) ){
 			beanB.setCardType( beanA.getParameter( "score_card_scheme" ) ); 
 		}

 		/* Mapping: "req_currency" -> "currency" */
		if( !Utils.isEmpty( src.getParameter( "req_currency" ) ) ){
 			beanB.setCurrency( beanA.getParameter( "req_currency" ) ); 
 		}

 		/* Mapping: "req_bill_to_address_line1" -> "billingAddress" */
		if( !Utils.isEmpty( src.getParameter( "req_bill_to_address_line1" ) ) ){
 			beanB.setBillingAddress( beanA.getParameter( "req_bill_to_address_line1" ) ); 
 		}
		
 		/* Mapping: "req_payment_token_comments" -> "partnerName" */
		if( !Utils.isEmpty( src.getParameter( "req_payment_token_comments" ) ) ){
			String [] comments =  beanA.getParameter( "req_payment_token_comments" ).split( "~" );
			beanB.setPartnerId(comments[0]);
			beanB.setPartnerName(comments.length > 1 ? comments[1] : null);
			beanB.setPartnerCallCenterNo(comments.length > 2 ? comments[2] : null);
 		}
		
		/* Mapping: "merchant_defined_data9" -> "Lob" */
		if( !Utils.isEmpty( src.getParameter( "req_merchant_defined_data9" ) ) ){
			beanB.setLob( LOB.valueOf( beanA.getParameter( "req_merchant_defined_data9" ) ) );
 		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.PaymentDetailsVO initializeDeepVO( javax.servlet.http.HttpServletRequest beanA, com.rsaame.pas.vo.bus.PaymentDetailsVO beanB ){
                                         		return beanB;
	}
}
