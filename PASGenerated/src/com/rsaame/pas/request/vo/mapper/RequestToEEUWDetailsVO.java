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
 * <li>com.rsaame.pas.vo.bus.EEUWDetailsVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( RequestToEEUWDetailsVO.class )</code>.
 */
public class RequestToEEUWDetailsVO extends BaseBeanToBeanMapper<javax.servlet.http.HttpServletRequest, com.rsaame.pas.vo.bus.EEUWDetailsVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public RequestToEEUWDetailsVO(){
		super();
	}

	public RequestToEEUWDetailsVO( javax.servlet.http.HttpServletRequest src, com.rsaame.pas.vo.bus.EEUWDetailsVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.EEUWDetailsVO mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.EEUWDetailsVO) Utils.newInstance( "com.rsaame.pas.vo.bus.EEUWDetailsVO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		javax.servlet.http.HttpServletRequest beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.EEUWDetailsVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;
		
		/* Mapping: "eeUnderDetailsEml" -> "emlPrc" */
		if( !Utils.isEmpty( src.getParameter( "eeUnderDetailsEml" ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.setEmlPrc( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "eeUnderDetailsEml" ) ) ) );
  		}

 		/* Mapping: "eeUnderDetailsEmlSi" -> "emlSI" */
		if( !Utils.isEmpty( src.getParameter( "eeUnderDetailsEmlSi" ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.setEmlSI( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "eeUnderDetailsEmlSi" ) ) ) );
  		}

 		/* Mapping: "eeUnderDetailsBisl" -> "emlBIPrc" */
		if( !Utils.isEmpty( src.getParameter( "eeUnderDetailsBisl" ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.setEmlBIPrc( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "eeUnderDetailsBisl" ) ) ) );
  		}

 		/* Mapping: "eeUnderDetailsBi" -> "emlBI" */
		if( !Utils.isEmpty( src.getParameter( "eeUnderDetailsBi" ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.setEmlBI( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "eeUnderDetailsBi" ) ) ) );
  		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.EEUWDetailsVO initializeDeepVO( javax.servlet.http.HttpServletRequest beanA, com.rsaame.pas.vo.bus.EEUWDetailsVO beanB ){
         		return beanB;
	}
}
