/*
* THIS IS A GENERATED CLASS. THIS CLASS SHOULD NOT BE EDITED.
*/
package com.rsaame.pas.request.vo.mapper;

import java.math.BigDecimal;

import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;

/**
 * Mapper class for:<ol>
 * <li>javax.servlet.http.HttpServletRequest</li>
 * <li>com.rsaame.pas.vo.svc.TMasPartnerMgmtVOHolder</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( RequestToTMasPartnerMgmtVOHolder.class )</code>.
 */
public class RequestToTMasPartnerMgmtVOHolder extends BaseBeanToBeanMapper<javax.servlet.http.HttpServletRequest, com.rsaame.pas.vo.svc.TMasPartnerMgmtVOHolder>{
	private final Logger log = Logger.getLogger( this.getClass() );

	public RequestToTMasPartnerMgmtVOHolder(){
		super();
	}

	public RequestToTMasPartnerMgmtVOHolder( javax.servlet.http.HttpServletRequest src, com.rsaame.pas.vo.svc.TMasPartnerMgmtVOHolder dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.svc.TMasPartnerMgmtVOHolder mapBean(){

		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}

		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.svc.TMasPartnerMgmtVOHolder) Utils.newInstance( "com.rsaame.pas.vo.svc.TMasPartnerMgmtVOHolder" );
		}

		/* Cast the destination bean to a bean of type of BeanA */
		javax.servlet.http.HttpServletRequest beanA = src;

		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.svc.TMasPartnerMgmtVOHolder beanB = dest;

		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO( beanA, beanB );

		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;

		/* Mapping: "partner_name" -> "pmmName" */
		if( !Utils.isEmpty( src.getParameter( "partner_name" ) ) ){
			beanB.setPmmName( beanA.getParameter( "partner_name" ).trim() );
		}

		/* Mapping: "partner_description" -> "pmmDesc" */
		if( !Utils.isEmpty( src.getParameter( "partner_description" ) ) ){
			beanB.setPmmDesc( beanA.getParameter( "partner_description" ).trim() );
		}

		/* Mapping: "src_of_customer" -> "pmmCustSrc" */
		if( !Utils.isEmpty( src.getParameter( "src_of_customer" ) ) ){
			beanB.setPmmCustSrc( new BigDecimal( beanA.getParameter( "src_of_customer" ) ) );
		}

		/* Mapping: "selected_product" -> "pmmPtCode" */
		if( !Utils.isEmpty( src.getParameter( "selected_product" ) ) ){
			beanB.setPmmPtCode( new BigDecimal( beanA.getParameter( "selected_product" ) ) );
		}

		/* Mapping: "selected_promo_code" -> "proCode" */
		if( !Utils.isEmpty( src.getParameter( "PromotinalCode" ) ) ){
			beanB.setProCode( beanA.getParameter( "PromotinalCode" ).trim() );
		}

		/* Mapping: "generated_url" -> "pmmUrl" */
		if( !Utils.isEmpty( src.getParameter( "generated_url" ) ) ){
			beanB.setPmmUrl( beanA.getParameter( "generated_url" ).trim() );
		}

		return dest;
	}

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.svc.TMasPartnerMgmtVOHolder initializeDeepVO( javax.servlet.http.HttpServletRequest beanA, com.rsaame.pas.vo.svc.TMasPartnerMgmtVOHolder beanB ){
		return beanB;
	}
}
