       /*
 * THIS IS A GENERATED CLASS. THIS CLASS SHOULD NOT BE EDITED.
 */
package com.rsaame.pas.pojo.mapper;

import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.BeanUtils;
import com.mindtree.ruc.cmn.utils.Utils;

/**
 * Mapper class for:<ol>
 * <li>javax.servlet.http.HttpServletRequest</li>
 * <li>com.rsaame.pas.vo.app.SearchTransactionVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( RequestToSearchTransactionVOMapper.class )</code>.
 */
public class RequestToSearchTransactionVOMapper extends BaseBeanToBeanMapper<javax.servlet.http.HttpServletRequest, com.rsaame.pas.vo.app.SearchTransactionVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public RequestToSearchTransactionVOMapper(){
		super();
	}

	public RequestToSearchTransactionVOMapper( javax.servlet.http.HttpServletRequest src, com.rsaame.pas.vo.app.SearchTransactionVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.app.SearchTransactionVO mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.app.SearchTransactionVO) Utils.newInstance( "com.rsaame.pas.vo.app.SearchTransactionVO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		javax.servlet.http.HttpServletRequest beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.app.SearchTransactionVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;
		
		/* Mapping: "smsInsuredName" -> "insuredName" */
		if( !Utils.isEmpty( src.getParameter( "smsInsuredName" ) ) ){
 			beanB.setInsuredName( beanA.getParameter( "smsInsuredName" ) ); 
 		}

 		/* Mapping: "smsQuotationNo" -> "transaction.quoteNo" */
		if( !Utils.isEmpty( src.getParameter( "smsQuotationNo" ) ) ){
 			beanB.getTransaction().setQuoteNo( beanA.getParameter( "smsQuotationNo" ) ); 
 		}

 		/* Mapping: "transPolicyNo" -> "transaction.policyNo" */
		if( !Utils.isEmpty( src.getParameter( "transPolicyNo" ) ) ){
 			beanB.getTransaction().setPolicyNo( beanA.getParameter( "transPolicyNo" ) ); 
 		}

 		/* Mapping: "transScheme" -> "transaction.scheme" */
		if( !Utils.isEmpty( src.getParameter( "transScheme" ) ) ){
 			beanB.getTransaction().setScheme( beanA.getParameter( "transScheme" ) ); 
 		}

 		/* Mapping: "transBrokerName" -> "transaction.brokerName" */
		if( !Utils.isEmpty( src.getParameter( "transBrokerName" ) ) ){
 			beanB.getTransaction().setBrokerName( beanA.getParameter( "transBrokerName" ) ); 
 		}

 		/* Mapping: "agentName" -> "agent" */
		if( !Utils.isEmpty( src.getParameter( "agentName" ) ) ){
 			beanB.setAgent( beanA.getParameter( "agentName" ) ); 
 		}

 		/* Mapping: "quote_name_country" -> "nationality" */
		if( !Utils.isEmpty( src.getParameter( "quote_name_country" ) ) ){
 			beanB.setNationality( beanA.getParameter( "quote_name_country" ) ); 
 		}

 		/* Mapping: "transAllDirect" -> "allDirect" */
		if( !Utils.isEmpty( src.getParameter( "transAllDirect" ) ) ){
			com.mindtree.ruc.cmn.beanmap.BooleanStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.BooleanStringConverter.class, "", "" );
 			beanB.setAllDirect( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "transAllDirect" ))) ); 
 		}

 		/* Mapping: "callStatus" -> "callStatus" */
		if( !Utils.isEmpty( src.getParameter( "callStatus" ) ) ){
 			beanB.setCallStatus( beanA.getParameter( "callStatus" ) ); 
 		}

 		/* Mapping: "transBranch" -> "transaction.branch" */
		if( !Utils.isEmpty( src.getParameter( "transBranch" ) ) ){
 			beanB.getTransaction().setBranch( beanA.getParameter( "transBranch" ) ); 
 		}

 		/* Mapping: "transTransactionFrom" -> "transaction.transactionFrom" */
		if( !Utils.isEmpty( src.getParameter( "transTransactionFrom" ) ) ){
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", "format=yyyy-MM-dd" );
 			beanB.getTransaction().setTransactionFrom(converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "transTransactionFrom" ) ) )); 
 		}

 		/* Mapping: "transTransactionTo" -> "transaction.transactionTo" */
		if( !Utils.isEmpty( src.getParameter( "transTransactionTo" ) ) ){
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", "format=yyyy-MM-dd" );
 			beanB.getTransaction().setTransactionTo(converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "transTransactionTo" ) ) )); 
 		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.app.SearchTransactionVO initializeDeepVO( javax.servlet.http.HttpServletRequest beanA, com.rsaame.pas.vo.app.SearchTransactionVO beanB ){
    		BeanUtils.initializeBeanField( "transaction", beanB );
                      		return beanB;
	}
}
