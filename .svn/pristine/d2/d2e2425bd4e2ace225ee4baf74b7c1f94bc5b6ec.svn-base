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
 * <li>com.rsaame.pas.vo.bus.SmsVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( RequestToSmsVOMapper.class )</code>.
 */
public class RequestToSmsVOMapper extends BaseBeanToBeanMapper<javax.servlet.http.HttpServletRequest, com.rsaame.pas.vo.bus.SmsVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public RequestToSmsVOMapper(){
		super();
	}

	public RequestToSmsVOMapper( javax.servlet.http.HttpServletRequest src, com.rsaame.pas.vo.bus.SmsVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.SmsVO mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.SmsVO) Utils.newInstance( "com.rsaame.pas.vo.bus.SmsVO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		javax.servlet.http.HttpServletRequest beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.SmsVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;	
		
		/* Mapping: "smsID" -> "smsID" */			
		if( !Utils.isEmpty( src.getParameter( "smsID" )) ){				
			com.rsaame.pas.cmn.converter.LongStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongStringConverter.class, "", "" );			
			beanB.setSmsID( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "smsID" ) ) ) );
  		}

 		/* Mapping: "smsMode" -> "smsMode" */
		if( !Utils.isEmpty( src.getParameter( "smsMode" ) ) ){
 			beanB.setSmsMode( beanA.getParameter( "smsMode" ) ); 
 		}

 		/* Mapping: "smsLevel" -> "smsLevel" */
		if( !Utils.isEmpty( src.getParameter( "smsLevel" ) ) ){
			com.rsaame.pas.cmn.converter.ShortStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.ShortStringConverter.class, "", "" );
			beanB.setSmsLevel( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "smsLevel" ) ) ) );
  		}

 		/* Mapping: "smsFrequency" -> "smsFrequency" */
		if( !Utils.isEmpty( src.getParameter( "smsFrequency" ) ) ){
			com.rsaame.pas.cmn.converter.ShortStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.ShortStringConverter.class, "", "" );
			beanB.setSmsFrequency( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "smsFrequency" ) ) ) );
  		}

 		/* Mapping: "engText" -> "smsEngBody" */
		if( !Utils.isEmpty( src.getParameter( "engText" ) ) ){
 			beanB.setSmsEngBody( beanA.getParameter( "engText" ) ); 
 		}

 		/* Mapping: "arabicText" -> "smsArabicBody" */
		if( !Utils.isEmpty( src.getParameter( "arabicText" ) ) ){
 			beanB.setSmsArabicBody( beanA.getParameter( "arabicText" ) ); 
 		}

 		/* Mapping: "smsStatus" -> "smsStatus" */
		if( !Utils.isEmpty( src.getParameter( "smsStatus" ) ) ){
 			beanB.setSmsStatus( beanA.getParameter( "smsStatus" ) ); 
 		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.SmsVO initializeDeepVO( javax.servlet.http.HttpServletRequest beanA, com.rsaame.pas.vo.bus.SmsVO beanB ){
               		return beanB;
	}
}
