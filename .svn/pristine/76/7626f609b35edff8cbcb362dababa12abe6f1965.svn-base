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
 * <li>com.rsaame.pas.vo.bus.CustomerSearchVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( RequestToCustonerSearchVO.class )</code>.
 */
public class RequestToCustonerSearchVO extends BaseBeanToBeanMapper<javax.servlet.http.HttpServletRequest, com.rsaame.pas.vo.bus.CustomerSearchVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public RequestToCustonerSearchVO(){
		super();
	}

	public RequestToCustonerSearchVO( javax.servlet.http.HttpServletRequest src, com.rsaame.pas.vo.bus.CustomerSearchVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.CustomerSearchVO mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.CustomerSearchVO) Utils.newInstance( "com.rsaame.pas.vo.bus.CustomerSearchVO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		javax.servlet.http.HttpServletRequest beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.CustomerSearchVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);

		/* Mapping: "customernameName" -> "completeName" */
		if( !Utils.isEmpty( src.getParameter( "customernameName" ) ) ){
 			beanB.setCompleteName( beanA.getParameter( "customernameName" ) ); 
 		}

 		/* Mapping: "customerIDName" -> "customerId" */
		if( !Utils.isEmpty( src.getParameter( "customerIDName" ) ) ){
 			beanB.setCustomerId( beanA.getParameter( "customerIDName" ) ); 
 		}

 		/* Mapping: "policyQuoteNo" -> "policyQuoteNo" */
		if( !Utils.isEmpty( src.getParameter( "policyQuoteNo" ) ) ){
 			beanB.setPolicyQuoteNo( beanA.getParameter( "policyQuoteNo" ) ); 
 		}

 		/* Mapping: "phoneNumberName" -> "phoneNo" */
		if( !Utils.isEmpty( src.getParameter( "phoneNumberName" ) ) ){
 			beanB.setPhoneNo( beanA.getParameter( "phoneNumberName" ) ); 
 		}

 		/* Mapping: "emailIdName" -> "emailId" */
		if( !Utils.isEmpty( src.getParameter( "emailIdName" ) ) ){
 			beanB.setEmailId( beanA.getParameter( "emailIdName" ) ); 
 		}

 		/* Mapping: "mobileNumberName" -> "mobileNo" */
		if( !Utils.isEmpty( src.getParameter( "mobileNumberName" ) ) ){
 			beanB.setMobileNo( beanA.getParameter( "mobileNumberName" ) ); 
 		}

 		/* Mapping: "poboxName" -> "poBoxNo" */
		if( !Utils.isEmpty( src.getParameter( "poboxName" ) ) ){
 			beanB.setPoBoxNo( beanA.getParameter( "poboxName" ) ); 
 		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.CustomerSearchVO initializeDeepVO( javax.servlet.http.HttpServletRequest beanA, com.rsaame.pas.vo.bus.CustomerSearchVO beanB ){
               		return beanB;
	}
}
