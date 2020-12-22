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
 * <li>com.rsaame.pas.vo.bus.PrintRenewalsSearchCriteriaVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( RequestToPrintRenewalsSearchCriteriaVOMapper.class )</code>.
 */
public class RequestToPrintRenewalsSearchCriteriaVOMapper extends BaseBeanToBeanMapper<javax.servlet.http.HttpServletRequest, com.rsaame.pas.vo.bus.PrintRenewalsSearchCriteriaVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public RequestToPrintRenewalsSearchCriteriaVOMapper(){
		super();
	}

	public RequestToPrintRenewalsSearchCriteriaVOMapper( javax.servlet.http.HttpServletRequest src, com.rsaame.pas.vo.bus.PrintRenewalsSearchCriteriaVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.PrintRenewalsSearchCriteriaVO mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.PrintRenewalsSearchCriteriaVO) Utils.newInstance( "com.rsaame.pas.vo.bus.PrintRenewalsSearchCriteriaVO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		javax.servlet.http.HttpServletRequest beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.PrintRenewalsSearchCriteriaVO beanB = dest;
			
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

 		/* Mapping: "transWithEmailID" -> "withEmailID" */
		if( !Utils.isEmpty( src.getParameter( "transWithEmailID" ) ) ){
			com.mindtree.ruc.cmn.beanmap.BooleanStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.BooleanStringConverter.class, "", "" );
 			beanB.setWithEmailID( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "transWithEmailID" )) ) ); 
 		}
		
 		/* Mapping: "transNotPrinted" -> "transNotPrinted" */
		if( !Utils.isEmpty( src.getParameter( "transNotPrinted" ) ) ){
			com.mindtree.ruc.cmn.beanmap.BooleanStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.BooleanStringConverter.class, "", "" );
 			beanB.setNotYetPrinted(converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "transNotPrinted" )) ) ); 
 		}

 		/* Mapping: "transBranch" -> "branch" */
		if( !Utils.isEmpty( src.getParameter( "transBranch" ) ) ){
 			beanB.setBranch( beanA.getParameter( "transBranch" ) ); 
 		}

		/* Mapping: "transLOB" -> "lob" */
		if( !Utils.isEmpty( src.getParameter( "transLOB" ) ) ){
			beanB.setLob( beanA.getParameter( "transLOB" ) ); 
  		}
		
		/* Mapping: "transStatus" -> "statusId" */
		if( !Utils.isEmpty(src.getParameterValues( "transStatus" )) ){
			
			String[] list = src.getParameterValues( "transStatus" );
			beanB.setStatusIdList(list);
  		}
		
		if(!Utils.isEmpty(src.getParameterValues("renewalTerm"))){
			beanB.setRenewalTerm(beanA.getParameter("renewalTerm"));
		}
		
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.PrintRenewalsSearchCriteriaVO initializeDeepVO( javax.servlet.http.HttpServletRequest beanA, com.rsaame.pas.vo.bus.PrintRenewalsSearchCriteriaVO beanB ){
                     		return beanB;
	}
}
