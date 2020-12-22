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
 * <li>javax.servlet.http.HttpServletRequest</li>
 * <li>com.rsaame.pas.vo.bus.SearchTransactionCriteriaVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( RequestToSearchTransactionCriteriaVOMapper.class )</code>.
 */
public class RequestToSearchTransactionCriteriaVOMapper extends BaseBeanToBeanMapper<javax.servlet.http.HttpServletRequest, com.rsaame.pas.vo.bus.SearchTransactionCriteriaVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public RequestToSearchTransactionCriteriaVOMapper(){
		super();
	}

	public RequestToSearchTransactionCriteriaVOMapper( javax.servlet.http.HttpServletRequest src, com.rsaame.pas.vo.bus.SearchTransactionCriteriaVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.SearchTransactionCriteriaVO mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.SearchTransactionCriteriaVO) Utils.newInstance( "com.rsaame.pas.vo.bus.SearchTransactionCriteriaVO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		javax.servlet.http.HttpServletRequest beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.SearchTransactionCriteriaVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);

		/* Mapping: "transClazz" -> "transaction.clazz" */
		if( !Utils.isEmpty( src.getParameter( "transClazz" ) ) ){
 			beanB.getTransaction().setClazz( beanA.getParameter( "transClazz" ) ); 
 		}

 		/* Mapping: com.Constant.CONST_TRANSQUOTENO -> "transaction.quoteNo" */
		if( !Utils.isEmpty( src.getParameter( com.Constant.CONST_TRANSQUOTENO ) ) && !Utils.isEmpty( src.getParameter( com.Constant.CONST_TRANSQUOTENO ).trim() ) ){
 			beanB.getTransaction().setQuoteNo( beanA.getParameter( com.Constant.CONST_TRANSQUOTENO ).trim() ); 
 		}

 		/* Mapping: com.Constant.CONST_TRANSPOLICYNO -> "transaction.policyNo" */
		if( !Utils.isEmpty( src.getParameter( com.Constant.CONST_TRANSPOLICYNO ) ) && !Utils.isEmpty( src.getParameter( com.Constant.CONST_TRANSPOLICYNO ).trim() ) ){
 			beanB.getTransaction().setPolicyNo( beanA.getParameter( com.Constant.CONST_TRANSPOLICYNO ).trim() ); 
 		}

 		/* Mapping: "transTransactionFrom" -> "transaction.transactionFrom" */
		if( !Utils.isEmpty( src.getParameter( "transTransactionFrom" ) ) ){
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", com.Constant.CONST_FORMAT_YYYY_MM_DD );
			beanB.getTransaction().setTransactionFrom( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "transTransactionFrom" ) ) ) );
  		}

 		/* Mapping: "transTransactionTo" -> "transaction.transactionTo" */
		if( !Utils.isEmpty( src.getParameter( "transTransactionTo" ) ) ){
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", com.Constant.CONST_FORMAT_YYYY_MM_DD );
			beanB.getTransaction().setTransactionTo( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "transTransactionTo" ) ) ) );
  		}

 		/* Mapping: "transCustomerName" -> "transaction.customerName" */
		if( !Utils.isEmpty( src.getParameter( "transCustomerName" ) ) ){
 			beanB.getTransaction().setCustomerName( beanA.getParameter( "transCustomerName" ) ); 
 		}

 		/* Mapping: "transBrokerName" -> "transaction.brokerName" */
		if( !Utils.isEmpty( src.getParameter( "transBrokerName" ) ) ){
 			beanB.getTransaction().setBrokerName( beanA.getParameter( "transBrokerName" ) ); 
 		}

 		/* Mapping: "transScheme" -> "transaction.scheme" */
		if( !Utils.isEmpty( src.getParameter( "transScheme" ) ) ){
 			beanB.getTransaction().setScheme( beanA.getParameter( "transScheme" ) ); 
 		}

 		/* Mapping: "transEffectiveDate" -> "transaction.transactionEffectiveDate" */
		if( !Utils.isEmpty( src.getParameter( "transEffectiveDate" ) ) ){
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", com.Constant.CONST_FORMAT_YYYY_MM_DD );
			beanB.getTransaction().setTransactionEffectiveDate( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "transEffectiveDate" ) ) ) );
  		}

 		/* Mapping: "transExpiryDate" -> "transaction.transactionExpiryDate" */
		if( !Utils.isEmpty( src.getParameter( "transExpiryDate" ) ) ){
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", com.Constant.CONST_FORMAT_YYYY_MM_DD );
			beanB.getTransaction().setTransactionExpiryDate( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "transExpiryDate" ) ) ) );
  		}

 		/* Mapping: "transLastModifiedBy" -> "transaction.lastModifiedBy" */
		if( !Utils.isEmpty( src.getParameter( "transLastModifiedBy" ) ) ){
 			beanB.getTransaction().setLastModifiedBy( beanA.getParameter( "transLastModifiedBy" ) ); 
 		}

 		/* Mapping: "transCreatedBy" -> "transaction.createdBy" */
		if( !Utils.isEmpty( src.getParameter( "transCreatedBy" ) ) ){
 			beanB.getTransaction().setCreatedBy( beanA.getParameter( "transCreatedBy" ) ); 
 		}

 		/* Mapping: "transDistributionCode" -> "transaction.distributionCode" */
		if( !Utils.isEmpty( src.getParameter( "transDistributionCode" ) ) ){
 			beanB.getTransaction().setDistributionCode( beanA.getParameter( "transDistributionCode" ) ); 
 		}

 		/* Mapping: "transStatus" -> "transaction.status" */
		if( !Utils.isEmpty( src.getParameter( "transStatus" ) ) ){
 			beanB.getTransaction().setStatus( beanA.getParameter( "transStatus" ) ); 
 		}

 		/* Mapping: "transSearchQuote" -> "searchQuote" */
		if( !Utils.isEmpty( src.getParameter( "transSearchQuote" ) ) ){
			com.mindtree.ruc.cmn.beanmap.BooleanStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.BooleanStringConverter.class, "", "" );
			beanB.setSearchQuote( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "transSearchQuote" ) ) ) );
  		}

 		/* Mapping: "transSearchPolicy" -> "searchPolicy" */
		if( !Utils.isEmpty( src.getParameter( "transSearchPolicy" ) ) ){
			com.mindtree.ruc.cmn.beanmap.BooleanStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.BooleanStringConverter.class, "", "" );
			beanB.setSearchPolicy( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "transSearchPolicy" ) ) ) );
  		}

 		/* Mapping: "transExactSearch" -> "exactSearch" */
		if( !Utils.isEmpty( src.getParameter( "transExactSearch" ) ) ){
			com.mindtree.ruc.cmn.beanmap.BooleanStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.BooleanStringConverter.class, "", "" );
			beanB.setExactSearch( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "transExactSearch" ) ) ) );
  		}

 		/* Mapping: "transLastTransaction" -> "lastTransaction" */
		if( !Utils.isEmpty( src.getParameter( "transLastTransaction" ) ) ){
			com.mindtree.ruc.cmn.beanmap.BooleanStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.BooleanStringConverter.class, "", "" );
			beanB.setLastTransaction( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "transLastTransaction" ) ) ) );
  		}
		if( !Utils.isEmpty( src.getParameter( "transBranch" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getTransaction().setLocationCode( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "transBranch" ) ) ) );
  		}
		if( !Utils.isEmpty( src.getParameter( "LOB" ) ) ){
			beanB.getTransaction().setPolicyType( beanA.getParameter( "LOB" ) ) ;
  		}
		/* Mapping: com.Constant.CONST_TRANSMOBILENO -> "transaction.mobileNo" */
		if( !Utils.isEmpty( src.getParameter( com.Constant.CONST_TRANSMOBILENO ) ) && !Utils.isEmpty( src.getParameter( com.Constant.CONST_TRANSMOBILENO ).trim() ) ){
 			beanB.getTransaction().setMobileNo( beanA.getParameter( com.Constant.CONST_TRANSMOBILENO ).trim() ); 
 		}
		/* Mapping: com.Constant.CONST_TRANSREFPOLNO -> "transaction.refPolNo" */
		if( !Utils.isEmpty( src.getParameter( com.Constant.CONST_TRANSREFPOLNO ) ) && !Utils.isEmpty( src.getParameter( com.Constant.CONST_TRANSREFPOLNO ).trim() ) ){
 			beanB.getTransaction().setPolReferenceNo( beanA.getParameter( com.Constant.CONST_TRANSREFPOLNO ).trim() ); 
 		}
		
		/* Mapping: com.Constant.CONST_VIEWHISTORY -> com.Constant.CONST_VIEWHISTORY */
		if( !Utils.isEmpty( src.getParameter( com.Constant.CONST_VIEWHISTORY ) ) && !Utils.isEmpty( src.getParameter( com.Constant.CONST_VIEWHISTORY ).trim() ) ){
 			beanB.setForHistoryView( beanA.getParameter( com.Constant.CONST_VIEWHISTORY ).trim()); 
 		}
		
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.SearchTransactionCriteriaVO initializeDeepVO( javax.servlet.http.HttpServletRequest beanA, com.rsaame.pas.vo.bus.SearchTransactionCriteriaVO beanB ){
  		BeanUtils.initializeBeanField( "transaction", beanB );
                                      		return beanB;
	}
}
