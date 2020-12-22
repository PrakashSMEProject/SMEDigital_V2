       /*
 * THIS IS A GENERATED CLASS. THIS CLASS SHOULD NOT BE EDITED.
 */
package com.rsaame.pas.request.vo.mapper;

import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.BeanUtils;
import com.mindtree.ruc.cmn.utils.Utils;

/**
 * Mapper class for:<ol>
 * <li>javax.servlet.http.HttpServletRequest</li>
 * <li>com.rsaame.pas.vo.bus.TravelInsuranceVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( RequestToTravelInsuranceVOCoverMapper.class )</code>.
 */
public class RequestToTravelInsuranceVOCoverMapper extends BaseBeanToBeanMapper<javax.servlet.http.HttpServletRequest, com.rsaame.pas.vo.bus.TravelInsuranceVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public RequestToTravelInsuranceVOCoverMapper(){
		super();
	}

	public RequestToTravelInsuranceVOCoverMapper( javax.servlet.http.HttpServletRequest src, com.rsaame.pas.vo.bus.TravelInsuranceVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.TravelInsuranceVO mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.TravelInsuranceVO) Utils.newInstance( "com.rsaame.pas.vo.bus.TravelInsuranceVO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		javax.servlet.http.HttpServletRequest beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.TravelInsuranceVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;
		
		/* Mapping: "total_premium" -> "premiumVO.premiumAmt" */
		if( !Utils.isEmpty( src.getParameter( "total_premium" ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.getPremiumVO().setPremiumAmt( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "total_premium" ) ) ) );
  		}

 		/* Mapping: "premium_payable" -> "premiumVO.premiumAmtActual" */
		if( !Utils.isEmpty( src.getParameter( "premium_payable" ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.getPremiumVO().setPremiumAmtActual( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "premium_payable" ) ) ) );
  		}

 		/* Mapping: "currency" -> "premiumVO.currency" */
		if( !Utils.isEmpty( src.getParameter( "currency" ) ) ){
 			beanB.getPremiumVO().setCurrency( beanA.getParameter( "currency" ) ); 
 		}

 		/* Mapping: "govt_tax" -> "premiumVO.govtTax" */
		if( !Utils.isEmpty( src.getParameter( "govt_tax" ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.getPremiumVO().setGovtTax( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "govt_tax" ) ) ) );
  		}
		
 		/* Mapping: "vatTax" -> "premiumVO.vatTax" */ //142244
		if( !Utils.isEmpty( src.getParameter( "vatTax" ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.getPremiumVO().setVatTax( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "vatTax" ) ) ) );
  		}
		
 		/* Mapping: "vatTaxPerc" -> "premiumVO.vatTaxPerc" */ //142244
		if( !Utils.isEmpty( src.getParameter( "vatTaxPerc" ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.getPremiumVO().setVatTaxPerc( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "vatTaxPerc" ) ) ) );
  		}
		
		/* Mapping: "vat_tax_code" -> "premiumVO.vatCode" */ 
		if( !Utils.isEmpty( src.getParameter( "vatCode" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getPremiumVO().setVatCode( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "vatCode" ) ) ) );
		}

 		/* Mapping: "special_discount" -> "premiumVO.specialDiscount" */
		if( !Utils.isEmpty( src.getParameter( "special_discount" ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.getPremiumVO().setSpecialDiscount( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "special_discount" ) ) ) );
  		}

 		/* Mapping: "policy_fees" -> "premiumVO.policyFees" */
		if( !Utils.isEmpty( src.getParameter( "policy_fees" ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.getPremiumVO().setPolicyFees( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "policy_fees" ) ) ) );
  		}

 		/* Mapping: "disc_or_load_perc" -> "premiumVO.discOrLoadPerc" */
		if( !Utils.isEmpty( src.getParameter( "disc_or_load_perc" ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.getPremiumVO().setDiscOrLoadPerc( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "disc_or_load_perc" ) ) ) );
  		}

 		/* Mapping: "disc_or_load_amt" -> "premiumVO.discOrLoadAmt" */
		if( !Utils.isEmpty( src.getParameter( "disc_or_load_amt" ) ) ){
			com.rsaame.pas.cmn.converter.BigDecimalStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalStringConverter.class, "", "" );
			beanB.getPremiumVO().setDiscOrLoadAmt( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "disc_or_load_amt" ) ) ) );
  		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.TravelInsuranceVO initializeDeepVO( javax.servlet.http.HttpServletRequest beanA, com.rsaame.pas.vo.bus.TravelInsuranceVO beanB ){
  		BeanUtils.initializeBeanField( "premiumVO", beanB );
                		return beanB;
	}
}
