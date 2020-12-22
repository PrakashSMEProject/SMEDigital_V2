       /*
 * THIS IS A GENERATED CLASS. THIS CLASS SHOULD NOT BE EDITED.
 */
package com.rsaame.pas.vo.voholder.mapper;

import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;

/**
 * Mapper class for:<ol>
 * <li>com.rsaame.pas.vo.bus.BuildingDetailsVO</li>
 * <li>com.rsaame.pas.vo.svc.TTrnPremiumVOHolder</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( BuildingDetailsVOToTTrnPremiumHolderMapper.class )</code>.
 */
public class BuildingDetailsVOToTTrnPremiumHolderMapper extends BaseBeanToBeanMapper<com.rsaame.pas.vo.bus.BuildingDetailsVO, com.rsaame.pas.vo.svc.TTrnPremiumVOHolder>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public BuildingDetailsVOToTTrnPremiumHolderMapper(){
		super();
	}

	public BuildingDetailsVOToTTrnPremiumHolderMapper( com.rsaame.pas.vo.bus.BuildingDetailsVO src, com.rsaame.pas.vo.svc.TTrnPremiumVOHolder dest ){
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
		com.rsaame.pas.vo.bus.BuildingDetailsVO beanA = src;
			
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

 		/* Mapping: "currency" -> "prmPremiumCurr" */
		if(  !Utils.isEmpty( beanA.getCurrency() )  ){
			com.rsaame.pas.cmn.converter.ByteStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.ByteStringConverter.class, "", "" );
 			beanB.setPrmPremiumCurr( converter.getTypeOfB().cast( converter.getBFromA( beanA.getCurrency() ) ) ); 
 		}

 		/* Mapping: "currency" -> "prmSumInsuredCurr" */
		/*if(  !Utils.isEmpty( beanA.getCurrency() )  ){
			com.rsaame.pas.cmn.converter.ByteStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.ByteStringConverter.class, "", "" );
 			beanB.setPrmSumInsuredCurr( converter.getTypeOfB().cast( converter.getBFromA( beanA.getCurrency() ) ) ); 
 		}*/
		
		/* Mapping: "riskCodes.rskId" -> "prmRskId" */
		if(  !Utils.isEmpty( beanA.getRiskCodes() ) && !Utils.isEmpty( beanA.getRiskCodes().getRskId() ) ){
 			beanB.setPrmRskId( beanA.getRiskCodes().getRskId() ); 
 		}

		/* Mapping: "riskCodes.basicRskId" -> "prmBasicRskId" */
		if(  !Utils.isEmpty( beanA.getRiskCodes() ) && !Utils.isEmpty( beanA.getRiskCodes().getBasicRskId() ) ){
 			beanB.setPrmBasicRskId( beanA.getRiskCodes().getBasicRskId() ); 
 		}
		
		//TODO: 
 		/* Mapping: "discOrLoadPerc" -> "prm_disc_load" */
		/*if(  !Utils.isEmpty( beanA.getDiscOrLoadPerc() )  ){
 			beanB.setPrmSumInsuredCurr( beanA.getDiscOrLoadPerc() ); 
 		}*/
		
		/* Mapping: "discOrLoadPerc" -> "prmLoadDisc" */
		if( !Utils.isEmpty( beanA.getDiscOrLoadPerc() ) ){
			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.setPrmLoadDisc( converter.getAFromB( beanA.getDiscOrLoadPerc() ) );
		}
		
		/* Mapping : "prmRiRskCode"*/
		if( !Utils.isEmpty( beanA.getRiRskCode()) ){
			beanB.setPrmRiRskCode( beanA.getRiRskCode() );
		}
		
   
		return dest;
	}	  

	private Integer getRiRskCode( Double sumInsured ){
		Integer cntRiRskCode;
		cntRiRskCode = sumInsured > Double.valueOf( Utils.getSingleValueAppConfig( "HOME_SI_LIMIT" ) ) ?
				 Integer.valueOf( Utils.getSingleValueAppConfig( "HOME_RI_RSK_CODE_GREATER" ) ):
			 Integer.valueOf( Utils.getSingleValueAppConfig( "HOME_RI_RSK_CODE_LESSER" ) );
				 return cntRiRskCode;
	}

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.svc.TTrnPremiumVOHolder initializeDeepVO( com.rsaame.pas.vo.bus.BuildingDetailsVO beanA, com.rsaame.pas.vo.svc.TTrnPremiumVOHolder beanB ){
                       		return beanB;
	}
}
