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
 * <li>com.rsaame.pas.vo.svc.TTrnPremiumVOHolder</li>
 * <li>com.rsaame.pas.vo.bus.CoverDetailsVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( CoverDetailsVOToTTrnPremiumQuoReverse.class )</code>.
 */
public class CoverDetailsVOToTTrnPremiumQuoReverse extends BaseBeanToBeanMapper<com.rsaame.pas.vo.svc.TTrnPremiumVOHolder, com.rsaame.pas.vo.bus.CoverDetailsVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public CoverDetailsVOToTTrnPremiumQuoReverse(){
		super();
	}

	public CoverDetailsVOToTTrnPremiumQuoReverse( com.rsaame.pas.vo.svc.TTrnPremiumVOHolder src, com.rsaame.pas.vo.bus.CoverDetailsVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.CoverDetailsVO mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.CoverDetailsVO) Utils.newInstance( "com.rsaame.pas.vo.bus.CoverDetailsVO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.vo.svc.TTrnPremiumVOHolder beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.CoverDetailsVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;
		
		/* Mapping: "prmCovCode" -> "coverCodes.covCode" */
		if(  !Utils.isEmpty( beanA.getPrmCovCode() )  ){
 			beanB.getCoverCodes().setCovCode( beanA.getPrmCovCode() ); 
 		}

 		/* Mapping: "prmCtCode" -> "coverCodes.covTypeCode" */
		if(  !Utils.isEmpty( beanA.getPrmCtCode() )  ){
 			beanB.getCoverCodes().setCovTypeCode( beanA.getPrmCtCode() ); 
 		}

 		/* Mapping: "prmCstCode" -> "coverCodes.covSubTypeCode" */
		if(  !Utils.isEmpty( beanA.getPrmCstCode() )  ){
 			beanB.getCoverCodes().setCovSubTypeCode( beanA.getPrmCstCode() ); 
 		}

 		/* Mapping: "prmRskId" -> "riskCodes.rskId" */
		if(  !Utils.isEmpty( beanA.getPrmRskId() )  ){
 			beanB.getRiskCodes().setRskId( beanA.getPrmRskId() ); 
 		}

 		/* Mapping: "prmBasicRskId" -> "riskCodes.basicRskId" */
		if(  !Utils.isEmpty( beanA.getPrmBasicRskId() )  ){
 			beanB.getRiskCodes().setBasicRskId( beanA.getPrmBasicRskId() ); 
 		}

 		/* Mapping: "prmBasicRskCode" -> "riskCodes.basicRskCode" */
		if(  !Utils.isEmpty( beanA.getPrmBasicRskCode() )  ){
 			beanB.getRiskCodes().setBasicRskCode( beanA.getPrmBasicRskCode() ); 
 		}

 		/* Mapping: "prmRskCode" -> "riskCodes.riskCode" */
		if(  !Utils.isEmpty( beanA.getPrmRskCode() )  ){
 			beanB.getRiskCodes().setRiskCode( beanA.getPrmRskCode() ); 
 		}

 		/* Mapping: "prmRtCode" -> "riskCodes.riskType" */
		if(  !Utils.isEmpty( beanA.getPrmRtCode() )  ){
 			beanB.getRiskCodes().setRiskType( beanA.getPrmRtCode() ); 
 		}

 		/* Mapping: "prmRcCode" -> "riskCodes.riskCat" */
		if(  !Utils.isEmpty( beanA.getPrmRcCode() )  ){
 			beanB.getRiskCodes().setRiskCat( beanA.getPrmRcCode() ); 
 		}

 		/* Mapping: "prmSumInsured" -> "sumInsured.sumInsured" */
		if(  !Utils.isEmpty( beanA.getPrmSumInsured() )  ){
			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.getSumInsured().setSumInsured( converter.getTypeOfB().cast( converter.getBFromA( beanA.getPrmSumInsured() ) ) );
  		}

 		/* Mapping: "prmPremium" -> "premiumAmt" */
		if(  !Utils.isEmpty( beanA.getPrmPremium() )  ){
			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.setPremiumAmt( converter.getTypeOfB().cast( converter.getBFromA( beanA.getPrmPremium() ) ) );
  		}

 		/* Mapping: "prmPremiumActual" -> "premiumAmtActual" */
		if(  !Utils.isEmpty( beanA.getPrmPremiumActual() )  ){
			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.setPremiumAmtActual( converter.getTypeOfB().cast( converter.getBFromA( beanA.getPrmPremiumActual() ) ) );
  		}

 		/* Mapping: "prmSumInsuredCurr" -> "currency" */
		if(  !Utils.isEmpty( beanA.getPrmSumInsuredCurr() )  ){
 			com.rsaame.pas.cmn.converter.ByteStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.ByteStringConverter.class, "", "" );
			beanB.setCurrency( converter.getTypeOfA().cast( converter.getAFromB( beanA.getPrmSumInsuredCurr() ) ) );
  		}

 		/* Mapping: "prmPremiumCurr" -> "currency" */
		if(  !Utils.isEmpty( beanA.getPrmPremiumCurr() )  ){
 			com.rsaame.pas.cmn.converter.ByteStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.ByteStringConverter.class, "", "" );
			beanB.setCurrency( converter.getTypeOfA().cast( converter.getAFromB( beanA.getPrmPremiumCurr() ) ) );
  		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.CoverDetailsVO initializeDeepVO( com.rsaame.pas.vo.svc.TTrnPremiumVOHolder beanA, com.rsaame.pas.vo.bus.CoverDetailsVO beanB ){
  		BeanUtils.initializeBeanField( "coverCodes", beanB );
       		BeanUtils.initializeBeanField( "riskCodes", beanB );
             		BeanUtils.initializeBeanField( "sumInsured", beanB );
          		return beanB;
	}
}
