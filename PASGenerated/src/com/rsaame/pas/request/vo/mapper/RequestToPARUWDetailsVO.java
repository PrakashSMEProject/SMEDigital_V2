       /*
 * THIS IS A GENERATED CLASS. THIS CLASS SHOULD NOT BE EDITED.
 */
package com.rsaame.pas.request.vo.mapper;

import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;

/**
 * Mapper class for:<ol>
 * <li>javax.servlet.http.HttpServletRequest</li>
 * <li>com.rsaame.pas.vo.bus.PARUWDetailsVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( RequestToPARUWDetailsVO.class )</code>.
 */
public class RequestToPARUWDetailsVO extends BaseBeanToBeanMapper<javax.servlet.http.HttpServletRequest, com.rsaame.pas.vo.bus.PARUWDetailsVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public RequestToPARUWDetailsVO(){
		super();
	}

	public RequestToPARUWDetailsVO( javax.servlet.http.HttpServletRequest src, com.rsaame.pas.vo.bus.PARUWDetailsVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.PARUWDetailsVO mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.PARUWDetailsVO) Utils.newInstance( "com.rsaame.pas.vo.bus.PARUWDetailsVO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		javax.servlet.http.HttpServletRequest beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.PARUWDetailsVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);

		/* Mapping: "parConstructionYear" -> "ageOfBuilding" */
		if( !Utils.isEmpty( src.getParameter( "parConstructionYear" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.setAgeOfBuilding( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "parConstructionYear" ) ) ) );
  		}

 		/* Mapping: "location" -> "directorate" */
		if( !Utils.isEmpty( src.getParameter( "location" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.setDirectorate( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "location" ) ) ) );
  		}

 		/* Mapping: "parConstructionType" -> "constructionType" */
		if( !Utils.isEmpty( src.getParameter( "parConstructionType" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.setConstructionType( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "parConstructionType" ) ) ) );
  		}

 		/* Mapping: "parHazardNT" -> "hazardousNature" */
		if( !Utils.isEmpty( src.getParameter( "parHazardNT" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.setHazardousNature( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "parHazardNT" ) ) ) );
  		}

 		/* Mapping: "parEMLper" -> "emlPrc" */
		if( !Utils.isEmpty( src.getParameter( "parEMLper" ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.setEmlPrc( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "parEMLper" ) ) ) );
  		}

 		/* Mapping: "parEMLsi" -> "emlSI" */
		if( !Utils.isEmpty( src.getParameter( "parEMLsi" ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.setEmlSI( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "parEMLsi" ) ) ) );
  		}

 		/* Mapping: "hazardLevel" -> "hazardLevel" */
		if( !Utils.isEmpty( src.getParameter( "hazardLevel" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.setHazardLevel( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "hazardLevel" ) ) ) );
  		}

 		/* Mapping: "riCategory" -> "categoryRI" */
		if( !Utils.isEmpty( src.getParameter( "riCategory" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.setCategoryRI( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "riCategory" ) ) ) );
  		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.PARUWDetailsVO initializeDeepVO( javax.servlet.http.HttpServletRequest beanA, com.rsaame.pas.vo.bus.PARUWDetailsVO beanB ){
                 		return beanB;
	}
}
