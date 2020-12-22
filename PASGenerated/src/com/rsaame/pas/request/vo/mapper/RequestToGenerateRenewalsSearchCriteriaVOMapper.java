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
 * <li>com.rsaame.pas.vo.bus.GenerateRenewalsSearchCriteriaVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( RequestToGenerateRenewalsSearchCriteriaVOMapper.class )</code>.
 */
public class RequestToGenerateRenewalsSearchCriteriaVOMapper extends BaseBeanToBeanMapper<javax.servlet.http.HttpServletRequest, com.rsaame.pas.vo.bus.GenerateRenewalsSearchCriteriaVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public RequestToGenerateRenewalsSearchCriteriaVOMapper(){
		super();
	}

	public RequestToGenerateRenewalsSearchCriteriaVOMapper( javax.servlet.http.HttpServletRequest src, com.rsaame.pas.vo.bus.GenerateRenewalsSearchCriteriaVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.GenerateRenewalsSearchCriteriaVO mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.GenerateRenewalsSearchCriteriaVO) Utils.newInstance( "com.rsaame.pas.vo.bus.GenerateRenewalsSearchCriteriaVO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		javax.servlet.http.HttpServletRequest beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.GenerateRenewalsSearchCriteriaVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;
		
		/* Mapping: "transClazz" -> "clazz" */
		if( !Utils.isEmpty( src.getParameter( "transClazz" ) ) ){
 			beanB.setClazz( beanA.getParameter( "transClazz" ) ); 
 		}

 		/* Mapping: "transPolicyNo" -> "policyNo" */
		if( !Utils.isEmpty( src.getParameter( "transPolicyNo" ) ) ){
 			beanB.setPolicyNo( beanA.getParameter( "transPolicyNo" ) ); 
 		}

 		/* Mapping: "transTransactionFrom" -> "transactionFrom" */
		if( !Utils.isEmpty( src.getParameter( "transTransactionFrom" ) ) ){
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", "format=yyyy-MM-dd" );
			beanB.setTransactionFrom( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "transTransactionFrom" ) ) ) );
  		}

 		/* Mapping: "transTransactionTo" -> "transactionTo" */
		if( !Utils.isEmpty( src.getParameter( "transTransactionTo" ) ) ){
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", "format=yyyy-MM-dd" );
			beanB.setTransactionTo( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "transTransactionTo" ) ) ) );
  		}

 		/* Mapping: "transInsuredName" -> "insuredName" */
		if( !Utils.isEmpty( src.getParameter( "transInsuredName" ) ) ){
 			beanB.setInsuredName( beanA.getParameter( "transInsuredName" ) ); 
 		}

 		/* Mapping: "transBrokerName" -> "brokerName" */
		if( !Utils.isEmpty( src.getParameter( "transBrokerName" ) ) ){
 			beanB.setBrokerName( beanA.getParameter( "transBrokerName" ) ); 
 		}

 		/* Mapping: "transScheme" -> "scheme" */
		if( !Utils.isEmpty( src.getParameter( "transScheme" ) ) ){
 			beanB.setScheme( beanA.getParameter( "transScheme" ) ); 
 		}

 		/* Mapping: "transAllDirect" -> "allDirect" */
		if( !Utils.isEmpty( src.getParameter( "transAllDirect" ) ) ){
			com.mindtree.ruc.cmn.beanmap.BooleanStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.BooleanStringConverter.class, "", "" );
			beanB.setAllDirect( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "transAllDirect" ) ) ) );
  		}

 		/* Mapping: "transExpiresIn" -> "noOfDays" */
		if( !Utils.isEmpty( src.getParameter( "transExpiresIn" ) ) ){
			beanB.setNoOfDays( beanA.getParameter( "transExpiresIn" ) ); 
  		}

 		/* Mapping: "transBranch" -> "branch" */
		if( !Utils.isEmpty( src.getParameter( "transBranch" ) ) ){
 			beanB.setBranch( beanA.getParameter( "transBranch" ) ); 
 		}
		
		/* Mapping: "transQuoteNo" -> "quoteNo" */
		/*
		 *  Search Criteria :- Search Criteria based on Quotation No.  
		 */
		if( !Utils.isEmpty( src.getParameter( "transQuoteNo" ) ) ){
 			beanB.setQuoteNo( beanA.getParameter( "transQuoteNo" ) ); 
 		}
		/*
		 *  Search Criteria :- Search Criteria based on Status.  
		 */
		if( !Utils.isEmpty( src.getParameter( "transStatus" ) ) ){
 			beanB.setStatusId( beanA.getParameter( "transStatus" ) ); 
 		}
   
   		/* Mapping: "transLOB" -> "lob" */
		if( !Utils.isEmpty( src.getParameter( "transLOB" ) ) ){
			beanB.setLob( beanA.getParameter( "transLOB" ) ); 
  		}
		
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.GenerateRenewalsSearchCriteriaVO initializeDeepVO( javax.servlet.http.HttpServletRequest beanA, com.rsaame.pas.vo.bus.GenerateRenewalsSearchCriteriaVO beanB ){
                     		return beanB;
	}
}
