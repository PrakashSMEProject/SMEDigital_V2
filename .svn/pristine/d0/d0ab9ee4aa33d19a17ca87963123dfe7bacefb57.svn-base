       /*
 * THIS IS A GENERATED CLASS. THIS CLASS SHOULD NOT BE EDITED.
 */
package com.rsaame.pas.request.vo.mapper;

import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.beanmap.BeanMapperFactory;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.BeanUtils;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.HTTPUtils;
import com.mindtree.ruc.cmn.utils.Utils;

/**
 * Mapper class for:<ol>
 * <li>javax.servlet.http.HttpServletRequest</li>
 * <li>com.rsaame.pas.vo.bus.PaymentVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( RequestToPaymentVOMapper.class )</code>.
 */
public class RequestToPaymentVOMapper extends BaseBeanToBeanMapper<javax.servlet.http.HttpServletRequest, com.rsaame.pas.vo.bus.PaymentVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public RequestToPaymentVOMapper(){
		super();
	}

	public RequestToPaymentVOMapper( javax.servlet.http.HttpServletRequest src, com.rsaame.pas.vo.bus.PaymentVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.PaymentVO mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.PaymentVO) Utils.newInstance( "com.rsaame.pas.vo.bus.PaymentVO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		javax.servlet.http.HttpServletRequest beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.PaymentVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);

		/* Mapping: "paymentCode" -> "payModeCode" */
		if( !Utils.isEmpty( src.getParameter( "paymentCode" ) ) ){
			com.rsaame.pas.cmn.converter.ByteStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.ByteStringConverter.class, "", "" );
			// Sonar fix for blocker on 25-201-2017
			beanB.setPayModeCode( converter.getTypeOfB().cast( converter.getBFromA( beanA.getParameter( "paymentCode" ) ) ) );
  		}

 		/* Mapping: "chequeNumber" -> "chequeNo" */
		if( !Utils.isEmpty( src.getParameter( "chequeNumber" ) ) ){
			com.rsaame.pas.cmn.converter.LongStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongStringConverter.class, "", "" );
			beanB.setChequeNo( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "chequeNumber" ) ) ) );
  		}

 		/* Mapping: "chequeDate" -> "chequeDt" */
		if( !Utils.isEmpty( src.getParameter( "chequeDate" ) ) ){
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", "format=yyyy-MM-dd" );
			beanB.setChequeDt( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "chequeDate" ) ) ) );
  		}

 		/* Mapping: "bankName" -> "bankCode" */
		if( !Utils.isEmpty( src.getParameter( "bankName" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.setBankCode( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "bankName" ) ) ) );
  		}

 		/* Mapping: "cardNumber" -> "creditCardNo" */
		if( !Utils.isEmpty( src.getParameter( "cardNumber" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.setCreditCardNo( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "cardNumber" ) ) ) );
  		}

 		/* Mapping: "expiryDate" -> "expiryDate" */
		if( !Utils.isEmpty( src.getParameter( "expiryDate" ) ) ){
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", "format=yyyy-MM-dd" );
			beanB.setExpiryDate( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "expiryDate" ) ) ) );
  		}

 		/* Mapping: "amount" -> "amount" */
		if( !Utils.isEmpty( src.getParameter( "amount" ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.setAmount( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "amount" ) ) ) );
  		}

		/* Mapping: "terminalId" -> "terminalId" */
		if( !Utils.isEmpty( src.getParameter( "terminalId" ) ) ){
			com.rsaame.pas.cmn.converter.LongStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongStringConverter.class, "", "" );
			beanB.setTerminalId( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "terminalId" ) ) ) );
  		}
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.PaymentVO initializeDeepVO( javax.servlet.http.HttpServletRequest beanA, com.rsaame.pas.vo.bus.PaymentVO beanB ){
               		return beanB;
	}
}
