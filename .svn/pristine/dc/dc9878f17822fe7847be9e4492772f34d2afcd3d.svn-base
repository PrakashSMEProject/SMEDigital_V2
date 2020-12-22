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
 * <li>com.rsaame.pas.vo.bus.PremiumVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( RequestToPremiumVOMapper.class )</code>.
 */
public class RequestToPremiumVOMapper extends BaseBeanToBeanMapper<javax.servlet.http.HttpServletRequest, com.rsaame.pas.vo.bus.PremiumVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public RequestToPremiumVOMapper(){
		super();
	}

	public RequestToPremiumVOMapper( javax.servlet.http.HttpServletRequest src, com.rsaame.pas.vo.bus.PremiumVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.PremiumVO mapBean(){
		System.out.println("Inside RequestToPremiumVOMapper-----");
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.PremiumVO) Utils.newInstance( "com.rsaame.pas.vo.bus.PremiumVO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		javax.servlet.http.HttpServletRequest beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.PremiumVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;
		
		/* Mapping: "prem_discload" -> "discOrLoadPerc" */
		if( !Utils.isEmpty( src.getParameter( "prem_discload" ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.setDiscOrLoadPerc( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "prem_discload" ).replaceAll( ",", "" ) ) ) );
  		}

 		/* Mapping: "disc_amount" -> "discOrLoadAmt" */
		if( !Utils.isEmpty( src.getParameter( "disc_amount" ) ) ){
			com.rsaame.pas.cmn.converter.BigDecimalStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalStringConverter.class, "", "" );
			beanB.setDiscOrLoadAmt( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "disc_amount" ).replaceAll( ",", "" ) ) ) );
  		}

 		/* Mapping: "govt_tax" -> "govtTax" */
		if( !Utils.isEmpty( src.getParameter( "govt_tax" ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.setGovtTax( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "govt_tax" ).replaceAll( ",", "" ) ) ) );
  		}

 		/* Mapping: "policy_fees" -> "policyFees" */
		if( !Utils.isEmpty( src.getParameter( "policy_fees" ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.setPolicyFees( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "policy_fees" ).replaceAll( ",", "" ) ) ) );
  		}
		
		
		/*VAT MAPPING*/
		/* Mapping: "vat_premium" -> "vat_premium" */
		if( !Utils.isEmpty( src.getParameter( "vatLoadingper" ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.setVatTaxPerc( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "vatLoadingper" ).replaceAll( ",", "" ) ) ) );
  		}
		/* Mapping: "govt_tax" -> "govtTax" */
		if( !Utils.isEmpty( src.getParameter( "vatLoadingamt" ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.setVatTax( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "vatLoadingamt" ).replaceAll( ",", "" ) ) ) );
  		}

		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.PremiumVO initializeDeepVO( javax.servlet.http.HttpServletRequest beanA, com.rsaame.pas.vo.bus.PremiumVO beanB ){
         		return beanB;
	}
}
