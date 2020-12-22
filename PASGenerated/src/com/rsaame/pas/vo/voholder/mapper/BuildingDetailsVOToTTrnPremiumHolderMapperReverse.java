       /*
 * THIS IS A GENERATED CLASS. THIS CLASS SHOULD NOT BE EDITED.
 */
package com.rsaame.pas.vo.voholder.mapper;

import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.BeanUtils;
import com.mindtree.ruc.cmn.utils.Utils;

/**
 * Mapper class for:<ol>
 * <li>com.rsaame.pas.vo.svc.TTrnPremiumVOHolder</li>
 * <li>com.rsaame.pas.vo.bus.BuildingDetailsVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( BuildingDetailsVOToTTrnPremiumHolderMapperReverse.class )</code>.
 */
public class BuildingDetailsVOToTTrnPremiumHolderMapperReverse extends BaseBeanToBeanMapper<com.rsaame.pas.vo.svc.TTrnPremiumVOHolder, com.rsaame.pas.vo.bus.BuildingDetailsVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public BuildingDetailsVOToTTrnPremiumHolderMapperReverse(){
		super();
	}

	public BuildingDetailsVOToTTrnPremiumHolderMapperReverse( com.rsaame.pas.vo.svc.TTrnPremiumVOHolder src, com.rsaame.pas.vo.bus.BuildingDetailsVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.BuildingDetailsVO mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.BuildingDetailsVO) Utils.newInstance( "com.rsaame.pas.vo.bus.BuildingDetailsVO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.vo.svc.TTrnPremiumVOHolder beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.BuildingDetailsVO beanB = dest;
			
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

 		/* Mapping: "prmPremiumCurr" -> "currency" */
		if(  !Utils.isEmpty( beanA.getPrmPremiumCurr() )  ){
			com.rsaame.pas.cmn.converter.ByteStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.ByteStringConverter.class, "", "" );
 			beanB.setCurrency( converter.getTypeOfA().cast( converter.getAFromB( beanA.getPrmPremiumCurr() ) ) ); 
 		}

 		/* Mapping: "prmSumInsuredCurr" -> "currency" */
		if(  !Utils.isEmpty( beanA.getPrmSumInsuredCurr() )  ){
			com.rsaame.pas.cmn.converter.ByteStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.ByteStringConverter.class, "", "" );
 			beanB.setCurrency( converter.getTypeOfA().cast( converter.getAFromB( beanA.getPrmPremiumCurr() ) ) ); 
 		}

 		/* Mapping: "prm_load_disc" -> "discOrLoadPerc" */
		/*if(  !Utils.isEmpty( beanA.getPrmSumInsuredCurr() )  ){
 			beanB.setDiscOrLoadPerc( beanA.getPrmSumInsuredCurr() ); 
 		}
*/
		
		/* Mapping: "prmLoadDisc" -> "discOrLoadPerc" */
		if( !Utils.isEmpty( beanA.getPrmLoadDisc() ) ){
			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.setDiscOrLoadPerc( converter.getBFromA( beanA.getPrmLoadDisc() ) );
		}
		
		/* Mapping: "riskCodes.rskId" -> "prmRskId" */
		if( !Utils.isEmpty( beanA.getPrmRskId() )){
 			beanB.getRiskCodes().setRskId( beanA.getPrmRskId() );
 		}

		/* Mapping: "riskCodes.basicRskId" -> "prmBasicRskId" */
		if( !Utils.isEmpty( beanA.getPrmBasicRskId() )){
 			beanB.getRiskCodes().setBasicRskId( beanA.getPrmBasicRskId() ); 
 		}
		
		if( !Utils.isEmpty( beanA.getPrmRiRskCode() ) ){
 			beanB.setRiRskCode(  beanA.getPrmRiRskCode() );
 		}
   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.BuildingDetailsVO initializeDeepVO( com.rsaame.pas.vo.svc.TTrnPremiumVOHolder beanA, com.rsaame.pas.vo.bus.BuildingDetailsVO beanB ){
  		BeanUtils.initializeBeanField( "coverCodes", beanB );
       		BeanUtils.initializeBeanField( "riskCodes", beanB );
       		BeanUtils.initializeBeanField( "sumInsured", beanB );
          		return beanB;
	}
}
