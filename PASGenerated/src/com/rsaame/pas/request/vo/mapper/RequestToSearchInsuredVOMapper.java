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
 * <li>com.rsaame.pas.vo.app.SearchInsuredVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( RequestToSearchInsuredVOMapper.class )</code>.
 */
public class RequestToSearchInsuredVOMapper extends BaseBeanToBeanMapper<javax.servlet.http.HttpServletRequest, com.rsaame.pas.vo.app.SearchInsuredVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public RequestToSearchInsuredVOMapper(){
		super();
	}

	public RequestToSearchInsuredVOMapper( javax.servlet.http.HttpServletRequest src, com.rsaame.pas.vo.app.SearchInsuredVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.app.SearchInsuredVO mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.app.SearchInsuredVO) Utils.newInstance( "com.rsaame.pas.vo.app.SearchInsuredVO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		javax.servlet.http.HttpServletRequest beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.app.SearchInsuredVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);

		/* Mapping: "insuredsearch_name_insuredid" -> "insuredId" */
		if( !Utils.isEmpty( src.getParameter( "insuredsearch_name_insuredid" ) ) ){
 			beanB.setInsuredId( beanA.getParameter( "insuredsearch_name_insuredid" ) ); 
 		}

 		/* Mapping: "insuredsearch_name_email" -> "emailId" */
		if( !Utils.isEmpty( src.getParameter( "insuredsearch_name_email" ) ) ){
 			beanB.setEmailId( beanA.getParameter( "insuredsearch_name_email" ) ); 
 		}

 		/* Mapping: "insuredsearch_name_phone" -> "phoneNo" */
		if( !Utils.isEmpty( src.getParameter( "insuredsearch_name_phone" ) ) ){
 			beanB.setPhoneNo( beanA.getParameter( "insuredsearch_name_phone" ) ); 
 		}

 		/* Mapping: "insuredsearch_name_mobile" -> "mobileNo" */
		if( !Utils.isEmpty( src.getParameter( "insuredsearch_name_mobile" ) ) ){
 			beanB.setMobileNo( beanA.getParameter( "insuredsearch_name_mobile" ) ); 
 		}

 		/* Mapping: com.Constant.CONST_INSUREDSEARCH_NAME_QUOTEPOLNO -> "policyQuoteNo" */
		if( !Utils.isEmpty( src.getParameter( com.Constant.CONST_INSUREDSEARCH_NAME_QUOTEPOLNO ) ) && !Utils.isEmpty( src.getParameter( com.Constant.CONST_INSUREDSEARCH_NAME_QUOTEPOLNO ).trim() ) ){
 			beanB.setPolicyQuoteNo( beanA.getParameter( com.Constant.CONST_INSUREDSEARCH_NAME_QUOTEPOLNO ).trim() ); 
 		}

 		/* Mapping: "insuredsearch_name_pobox" -> "poBox" */
		if( !Utils.isEmpty( src.getParameter( "insuredsearch_name_pobox" ) ) ){
 			beanB.setPoBox( beanA.getParameter( "insuredsearch_name_pobox" ) ); 
 		}

 		/* Mapping: "insuredsearch_name_completeName" -> "completeName" */
		if( !Utils.isEmpty( src.getParameter( "insuredsearch_name_completeName" ) ) ){
 			beanB.setCompleteName( beanA.getParameter( "insuredsearch_name_completeName" ) ); 
 		}
		
		/* Mapping: "exactInsuredSearch" -> "exactSearch" */
		if( !Utils.isEmpty( src.getParameter( "exactInsuredSearch" ) ) ){
			com.mindtree.ruc.cmn.beanmap.BooleanStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.BooleanStringConverter.class, "", "" );
			beanB.setExactSearch( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "exactInsuredSearch" ) ) ) );
  		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.app.SearchInsuredVO initializeDeepVO( javax.servlet.http.HttpServletRequest beanA, com.rsaame.pas.vo.app.SearchInsuredVO beanB ){
               		return beanB;
	}
}
