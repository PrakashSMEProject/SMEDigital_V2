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
 * <li>com.rsaame.pas.vo.app.ForgotPwdDetailsVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( RequestToForgotPwdDetailsVOMapper.class )</code>.
 */
public class RequestToForgotPwdDetailsVOMapper extends BaseBeanToBeanMapper<javax.servlet.http.HttpServletRequest, com.rsaame.pas.vo.app.ForgotPwdDetailsVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public RequestToForgotPwdDetailsVOMapper(){
		super();
	}

	public RequestToForgotPwdDetailsVOMapper( javax.servlet.http.HttpServletRequest src, com.rsaame.pas.vo.app.ForgotPwdDetailsVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.app.ForgotPwdDetailsVO mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.app.ForgotPwdDetailsVO) Utils.newInstance( "com.rsaame.pas.vo.app.ForgotPwdDetailsVO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		javax.servlet.http.HttpServletRequest beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.app.ForgotPwdDetailsVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;
		
		/* Mapping: "j_username" -> "userLoginName" */
		if( !Utils.isEmpty( src.getParameter( "j_username" ) ) ){
 			beanB.setUserLoginName( beanA.getParameter( "j_username" ) ); 
 		}

 		/* Mapping: "LOCATION" -> "location" */
		if( !Utils.isEmpty( src.getParameter( "LOCATION" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.setLocation( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "LOCATION" ) ) ) );
  		}

 		/* Mapping: "lastName" -> "lastName" */
		if( !Utils.isEmpty( src.getParameter( "lastName" ) ) ){
 			beanB.setLastName( beanA.getParameter( "lastName" ) ); 
 		}

 		/* Mapping: "forgotPwdDOB" -> "dateOfBirth" */
		if( !Utils.isEmpty( src.getParameter( "forgotPwdDOB" ) ) ){
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", "format=yyyy-MM-dd" );
			beanB.setDateOfBirth( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "forgotPwdDOB" ) ) ) );
  		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.app.ForgotPwdDetailsVO initializeDeepVO( javax.servlet.http.HttpServletRequest beanA, com.rsaame.pas.vo.app.ForgotPwdDetailsVO beanB ){
         		return beanB;
	}
}
