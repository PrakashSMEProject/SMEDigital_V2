/*
 * THIS IS A GENERATED CLASS. THIS CLASS SHOULD NOT BE EDITED.
 */
package com.rsaame.pas.request.vo.mapper;

import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.utils.Utils;


/**
 * Mapper class for:<ol>
 * <li>javax.servlet.http.HttpServletRequest</li>
 * <li>com.rsaame.pas.vo.svc.TMasPromotionalCodeVOHolder</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( RequestToTMasPromotionalCodeVOHolder.class )</code>.
 */		
public class RequestToTMasPromotionalCodeVO extends BaseBeanToBeanMapper<javax.servlet.http.HttpServletRequest, com.rsaame.pas.vo.svc.TMasPromotionalCodeVO>{
	public RequestToTMasPromotionalCodeVO(){
		super();
	}

	public RequestToTMasPromotionalCodeVO( javax.servlet.http.HttpServletRequest src, com.rsaame.pas.vo.svc.TMasPromotionalCodeVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.svc.TMasPromotionalCodeVO mapBean(){

		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}

		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.svc.TMasPromotionalCodeVO) Utils.newInstance( "com.rsaame.pas.vo.svc.TMasPromotionalCodeVO" );
		}

		/* Cast the destination bean to a bean of type of BeanA */
		javax.servlet.http.HttpServletRequest beanA = src;

		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.svc.TMasPromotionalCodeVO beanB = dest;

		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO( beanA, beanB );

		/* Mapping: "promotional_code" -> "proCode" */
		if( !Utils.isEmpty( src.getParameter( "promotional_code" ) ) ){
			beanB.setProCode( beanA.getParameter( "promotional_code" ) );
		}

		/* Mapping: "promo_cd_desc" -> "proEDesc" */
		if( !Utils.isEmpty( src.getParameter( "promo_cd_desc" ) ) ){
			beanB.setProEDesc( beanA.getParameter( "promo_cd_desc" ) );
		}

		/* Mapping: "startDate" -> "proStartDate" */
		if( !Utils.isEmpty( src.getParameter( "startDate" ) ) ){
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "","format=yyyy-MM-dd" );
			beanB.setProStartDate( converter.getAFromB( beanA.getParameter( "startDate" ) ) );
		}

		/* Mapping: "endDate" -> "proEndDate" */
		if( !Utils.isEmpty( src.getParameter( "endDate" ) ) ){
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "","format=yyyy-MM-dd" );
			beanB.setProEndDate( converter.getAFromB(beanA.getParameter( "endDate" )));
		}

		/* Mapping: "selected_product" -> "proCode" */
		if( !Utils.isEmpty( src.getParameter( "LOB" ) ) ){
			com.rsaame.pas.cmn.converter.BigDecimalStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalStringConverter.class, "", "" );
			beanB.setProPtCode(  converter.getAFromB( beanA.getParameter( "LOB" ) )) ;
		}

		/* Mapping: "promotional_type" -> "proType" */
		if( !Utils.isEmpty( src.getParameter( "PromoType" ) ) ){
			beanB.setProType( beanA.getParameter( "PromoType" ) );
		}

		/* Mapping: "discount_opted" -> "proDiscPerc" */
		if( !Utils.isEmpty( src.getParameter( "discount_opted" ) ) ){
			com.rsaame.pas.cmn.converter.BigDecimalStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalStringConverter.class, "", "" );
			beanB.setProDiscPerc( converter.getAFromB( beanA.getParameter( "discount_opted" ) ) );
		}
		
		
		
		return dest;
	}

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.svc.TMasPromotionalCodeVO initializeDeepVO( javax.servlet.http.HttpServletRequest beanA,
			com.rsaame.pas.vo.svc.TMasPromotionalCodeVO beanB ){
		return beanB;
	}
}
