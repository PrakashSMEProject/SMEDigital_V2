       /*
 * THIS IS A GENERATED CLASS. THIS CLASS SHOULD NOT BE EDITED.
 */
package com.rsaame.pas.pojo.mapper;

import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.beanmap.BeanMapperFactory;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.BeanUtils;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.Utils;

/**
 * Mapper class for:<ol>
 * <li>com.rsaame.pas.vo.bus.CoverDetailsVO</li>
 * <li>com.rsaame.pas.vo.svc.TTrnPremiumVOHolder</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( CoverDetailsVOToTTrnPremiumQuo.class )</code>.
 */
public class CoverDetailsVOToTTrnPremiumQuo extends BaseBeanToBeanMapper<com.rsaame.pas.vo.bus.CoverDetailsVO, com.rsaame.pas.vo.svc.TTrnPremiumVOHolder>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public CoverDetailsVOToTTrnPremiumQuo(){
		super();
	}

	public CoverDetailsVOToTTrnPremiumQuo( com.rsaame.pas.vo.bus.CoverDetailsVO src, com.rsaame.pas.vo.svc.TTrnPremiumVOHolder dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.svc.TTrnPremiumVOHolder mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.svc.TTrnPremiumVOHolder) Utils.newInstance( "com.rsaame.pas.vo.svc.TTrnPremiumVOHolder" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.vo.bus.CoverDetailsVO beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.svc.TTrnPremiumVOHolder beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;
		
		/* Mapping: "coverCodes.covCode" -> "prmCovCode" */
		if(  !Utils.isEmpty( beanA.getCoverCodes() ) && !Utils.isEmpty( beanA.getCoverCodes().getCovCode() )  ){
 			beanB.setPrmCovCode( beanA.getCoverCodes().getCovCode() ); 
 		}

 		/* Mapping: "coverCodes.covTypeCode" -> "prmCtCode" */
		if(  !Utils.isEmpty( beanA.getCoverCodes() ) && !Utils.isEmpty( beanA.getCoverCodes().getCovTypeCode() )  ){
 			beanB.setPrmCtCode( beanA.getCoverCodes().getCovTypeCode() ); 
 		}

 		/* Mapping: "coverCodes.covSubTypeCode" -> "prmCstCode" */
		if(  !Utils.isEmpty( beanA.getCoverCodes() ) && !Utils.isEmpty( beanA.getCoverCodes().getCovSubTypeCode() )  ){
 			beanB.setPrmCstCode( beanA.getCoverCodes().getCovSubTypeCode() ); 
 		}

 		/* Mapping: "riskCodes.rskId" -> "prmRskId" */
		if(  !Utils.isEmpty( beanA.getRiskCodes() ) && !Utils.isEmpty( beanA.getRiskCodes().getRskId() )  ){
 			beanB.setPrmRskId( beanA.getRiskCodes().getRskId() ); 
 		}

 		/* Mapping: "riskCodes.basicRskId" -> "prmBasicRskId" */
		if(  !Utils.isEmpty( beanA.getRiskCodes() ) && !Utils.isEmpty( beanA.getRiskCodes().getBasicRskId() )  ){
 			beanB.setPrmBasicRskId( beanA.getRiskCodes().getBasicRskId() ); 
 		}

 		/* Mapping: "riskCodes.basicRskCode" -> "prmBasicRskCode" */
		if(  !Utils.isEmpty( beanA.getRiskCodes() ) && !Utils.isEmpty( beanA.getRiskCodes().getBasicRskCode() )  ){
 			beanB.setPrmBasicRskCode( beanA.getRiskCodes().getBasicRskCode() ); 
 		}

 		/* Mapping: "riskCodes.riskCode" -> "prmRskCode" */
		if(  !Utils.isEmpty( beanA.getRiskCodes() ) && !Utils.isEmpty( beanA.getRiskCodes().getRiskCode() )  ){
 			beanB.setPrmRskCode( beanA.getRiskCodes().getRiskCode() ); 
 		}

 		/* Mapping: "riskCodes.riskType" -> "prmRtCode" */
		if(  !Utils.isEmpty( beanA.getRiskCodes() ) && !Utils.isEmpty( beanA.getRiskCodes().getRiskType() )  ){
 			beanB.setPrmRtCode( beanA.getRiskCodes().getRiskType() ); 
 		}

 		/* Mapping: "riskCodes.riskCat" -> "prmRcCode" */
		if(  !Utils.isEmpty( beanA.getRiskCodes() ) && !Utils.isEmpty( beanA.getRiskCodes().getRiskCat() )  ){
 			beanB.setPrmRcCode( beanA.getRiskCodes().getRiskCat() ); 
 		}

 		/* Mapping: "sumInsured.sumInsured" -> "prmSumInsured" */
		if(  !Utils.isEmpty( beanA.getSumInsured() ) && !Utils.isEmpty( beanA.getSumInsured().getSumInsured() )  ){
			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.setPrmSumInsured( converter.getTypeOfA().cast( converter.getAFromB( beanA.getSumInsured().getSumInsured() ) ) );
  		}

 		/* Mapping: "premiumAmt" -> "prmPremium" */
		if(  !Utils.isEmpty( beanA.getPremiumAmt() )  ){
			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.setPrmPremium( converter.getTypeOfA().cast( converter.getAFromB( beanA.getPremiumAmt() ) ) );
  		}

 		/* Mapping: "premiumAmtActual" -> "prmPremiumActual" */
		if(  !Utils.isEmpty( beanA.getPremiumAmtActual() )  ){
			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.setPrmPremiumActual( converter.getTypeOfA().cast( converter.getAFromB( beanA.getPremiumAmtActual() ) ) );
  		}

 		/* Mapping: "currency" -> "prmSumInsuredCurr" */
		/*if(  !Utils.isEmpty( beanA.getCurrency() )  ){
 			com.rsaame.pas.cmn.converter.ByteStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.ByteStringConverter.class, "", "" );
			beanB.setPrmSumInsuredCurr( converter.getTypeOfB().cast( converter.getBFromA( beanA.getCurrency() ) ) );
  		}*/

 		/* Mapping: "currency" -> "prmPremiumCurr" */
		if(  !Utils.isEmpty( beanA.getCurrency() )  ){
 			com.rsaame.pas.cmn.converter.ByteStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.ByteStringConverter.class, "", "" );
			beanB.setPrmPremiumCurr( converter.getTypeOfB().cast( converter.getBFromA( beanA.getCurrency() ) ) );
  		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.svc.TTrnPremiumVOHolder initializeDeepVO( com.rsaame.pas.vo.bus.CoverDetailsVO beanA, com.rsaame.pas.vo.svc.TTrnPremiumVOHolder beanB ){
                             		return beanB;
	}
}
