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
import com.rsaame.pas.vo.app.FieldType;

/**
 * Mapper class for:<ol>
 * <li>com.rsaame.pas.dao.model.VMasProductConfigPasWrapper</li>
 * <li>com.rsaame.pas.vo.bus.CoverDetails</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( VMasProductConfigPasToCoverDetailsVOMapper.class )</code>.
 */
public class VMasProductConfigPasToCoverDetailsVOMapper extends BaseBeanToBeanMapper<com.rsaame.pas.dao.model.VMasProductConfigPasWrapper, com.rsaame.pas.vo.bus.CoverDetails>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public VMasProductConfigPasToCoverDetailsVOMapper(){
		super();
	}

	public VMasProductConfigPasToCoverDetailsVOMapper( com.rsaame.pas.dao.model.VMasProductConfigPasWrapper src, com.rsaame.pas.vo.bus.CoverDetails dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.CoverDetails mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.CoverDetails) Utils.newInstance( "com.rsaame.pas.vo.bus.CoverDetails" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.dao.model.VMasProductConfigPasWrapper beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.CoverDetails beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;
		
		/* Mapping: "vProductConfigPasList[].pcEDesc" -> "coverDetails[].coverName" */
  		noOfItems = beanA.getVProductConfigPasList().size();
   		for( int i = 0; i < noOfItems; i++ ){
          			beanB.getCoverDetails().get( i ).setCoverName (  beanA.getVProductConfigPasList().get( i ).getPcEDesc() );
		}

 		/* Mapping: "vProductConfigPasList[].pcHelpMsg" -> "coverDetails[].coverDesc" */
  		noOfItems = beanA.getVProductConfigPasList().size();
   		for( int i = 0; i < noOfItems; i++ ){
          			beanB.getCoverDetails().get( i ).setCoverDesc (  beanA.getVProductConfigPasList().get( i ).getPcHelpMsg() );
		}

 		/* Mapping: "vProductConfigPasList[].pcDisplayValue" -> "coverDetails[].sumInsured.eDesc" */
  		noOfItems = beanA.getVProductConfigPasList().size();
   		for( int i = 0; i < noOfItems; i++ ){
          			beanB.getCoverDetails().get( i ).getSumInsured().seteDesc (  beanA.getVProductConfigPasList().get( i ).getPcDisplayValue() );
		}

 		/* Mapping: "vProductConfigPasList[].pcCovCode" -> "coverDetails[].coverCodes.covCode" */
  		noOfItems = beanA.getVProductConfigPasList().size();
   		for( int i = 0; i < noOfItems; i++ ){
          			beanB.getCoverDetails().get( i ).getCoverCodes().setCovCode (  beanA.getVProductConfigPasList().get( i ).getPcCovCode().shortValue() );
		}

 		/* Mapping: "vProductConfigPasList[].pcCtCode" -> "coverDetails[].coverCodes.covTypeCode" */
  		noOfItems = beanA.getVProductConfigPasList().size();
   		for( int i = 0; i < noOfItems; i++ ){
          			beanB.getCoverDetails().get( i ).getCoverCodes().setCovTypeCode (  beanA.getVProductConfigPasList().get( i ).getPcCtCode().shortValue() );
		}

 		/* Mapping: "vProductConfigPasList[].pcCstCode" -> "coverDetails[].coverCodes.covSubTypeCode" */
  		noOfItems = beanA.getVProductConfigPasList().size();
   		for( int i = 0; i < noOfItems; i++ ){
          			beanB.getCoverDetails().get( i ).getCoverCodes().setCovSubTypeCode (  beanA.getVProductConfigPasList().get( i ).getPcCstCode().shortValue() );
		}

 		/* Mapping: "vProductConfigPasList[].pcCriCode" -> "coverDetails[].coverCodes.covCriteriaCode" */
/*  		noOfItems = beanA.getVProductConfigPasList().size();
   		for( int i = 0; i < noOfItems; i++ ){
          			beanB.getCoverDetails().get( i ).getCoverCodes().setCovCriteriaCode (  beanA.getVProductConfigPasList().get( i ).getPcCriCode() );
		}
*/
 		/* Mapping: "vProductConfigPasList[].prSumInsured" -> "coverDetails[].sumInsured.sumInsured" */
  		noOfItems = beanA.getVProductConfigPasList().size();
   		for( int i = 0; i < noOfItems; i++ ){
           			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, com.Constant.CONST_APROPS, com.Constant.CONST_BPROPS );
			beanB.getCoverDetails().get( i ).getSumInsured().setSumInsured ( converter.getTypeOfB().cast( converter.getBFromA( beanA.getVProductConfigPasList().get( i ).getPrSumInsured() ) ) );
 		}

   		noOfItems = beanA.getVProductConfigPasList().size();
   		for( int i = 0; i < noOfItems; i++ ){
           			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, com.Constant.CONST_APROPS, com.Constant.CONST_BPROPS );
			beanB.getCoverDetails().get( i ).getSumInsured().setDeductible(converter.getTypeOfB().cast( converter.getBFromA( beanA.getVProductConfigPasList().get( i ).getPrCompulsoryExcess() ) ) );
 		}
   		
   		
   		noOfItems = beanA.getVProductConfigPasList().size();
   		for( int i = 0; i < noOfItems; i++ ){
           			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, com.Constant.CONST_APROPS, com.Constant.CONST_BPROPS );
			beanB.getCoverDetails().get( i ).setPrLimit( converter.getTypeOfB().cast( converter.getBFromA( beanA.getVProductConfigPasList().get( i ).getPrLimit()) ) );
 		}
   		
  		noOfItems = beanA.getVProductConfigPasList().size();
   		for( int i = 0; i < noOfItems; i++ ){
          			beanB.getCoverDetails().get( i ).setHelpMessage( beanA.getVProductConfigPasList().get( i ).getPcHelpMsg());
		}

   		
 		/* Mapping: "vProductConfigPasList[].pcDispItem" -> "coverDetails[].fieldType" */
  		noOfItems = beanA.getVProductConfigPasList().size();
   		for( int i = 0; i < noOfItems; i++ ){
          			beanB.getCoverDetails().get( i ).setFieldType ( FieldType.valueOf( beanA.getVProductConfigPasList().get( i ).getPcDispItem() ) );
		}

 		/* Mapping: "vProductConfigPasList[].pcTariff" -> "coverDetails[].tariffCode" */
  		noOfItems = beanA.getVProductConfigPasList().size();
   		for( int i = 0; i < noOfItems; i++ ){
          			beanB.getCoverDetails().get( i ).setTariffCode (  beanA.getVProductConfigPasList().get( i ).getPcTariff() );
		}

 		/* Mapping: "vProductConfigPasList[].prMandatoryInd" -> "coverDetails[].mandatoryIndicator" */
  		noOfItems = beanA.getVProductConfigPasList().size();
   		for( int i = 0; i < noOfItems; i++ ){
          			beanB.getCoverDetails().get( i ).setMandatoryIndicator (  beanA.getVProductConfigPasList().get( i ).getPrMandatoryInd() );
		}
   		
   		/* Mapping: "vProductConfigPasList[].PrcDisplayInd" -> "coverDetails[].PrcDisplayInd" */
  		noOfItems = beanA.getVProductConfigPasList().size();
   		for( int i = 0; i < noOfItems; i++ ){
          			beanB.getCoverDetails().get( i ).setPrcDisplayInd(  beanA.getVProductConfigPasList().get( i ).getPrcDisplayInd() );
		}

   		/* Mapping: "vProductConfigPasList[].PrcBToCDisplayInd" -> "coverDetails[].PrcBToCDisplayInd" */
  		noOfItems = beanA.getVProductConfigPasList().size();
   		for( int i = 0; i < noOfItems; i++ ){
          			beanB.getCoverDetails().get( i ).setPrcBToCDisplayInd(  beanA.getVProductConfigPasList().get( i ).getPrcBToCDisplayInd() );
		}

 		/* Mapping: "vProductConfigPasList[].pcMappingFld" -> "coverDetails[].mappingField" */
  		noOfItems = beanA.getVProductConfigPasList().size();
   		for( int i = 0; i < noOfItems; i++ ){
          			beanB.getCoverDetails().get( i ).setMappingField (  beanA.getVProductConfigPasList().get( i ).getPcMappingFld() );
		}
   		/* Mapping: "vProductConfigPasList[].pcRskCode" -> "coverDetails[].riskCodes.riskCode" */
  		noOfItems = beanA.getVProductConfigPasList().size();
   		for( int i = 0; i < noOfItems; i++ ){
          			beanB.getCoverDetails().get( i ).getRiskCodes().setRiskCode( beanA.getVProductConfigPasList().get( i ).getPcRskCode() );
		}
   		
   		/* Mapping: "vProductConfigPasList[].pcBasicRskCode" -> "coverDetails[].riskCodes.basicRskCode" */
  		noOfItems = beanA.getVProductConfigPasList().size();
   		for( int i = 0; i < noOfItems; i++ ){
          			beanB.getCoverDetails().get( i ).getRiskCodes().setBasicRskCode( beanA.getVProductConfigPasList().get( i ).getPcBasicRskCode() );
		}
   		/* Mapping: "vProductConfigPasList[].pcRtCode" -> "coverDetails[].riskCodes.riskType" */
  		noOfItems = beanA.getVProductConfigPasList().size();
   		for( int i = 0; i < noOfItems; i++ ){
          			beanB.getCoverDetails().get( i ).getRiskCodes().setRiskType( beanA.getVProductConfigPasList().get( i ).getPcRtCode() );
		}
   		/* Mapping: "vProductConfigPasList[].pcRcCode" -> "coverDetails[].riskCodes.riskCat" */
  		noOfItems = beanA.getVProductConfigPasList().size();
   		for( int i = 0; i < noOfItems; i++ ){
          			beanB.getCoverDetails().get( i ).getRiskCodes().setRiskCat( beanA.getVProductConfigPasList().get( i ).getPcRcCode() );
		}
   		
 		/* Mapping: "vProductConfigPasList[].pcBasicRskCode" -> "coverDetails[].riskCodes.basicRskCode" */
  		noOfItems = beanA.getVProductConfigPasList().size();
   		for( int i = 0; i < noOfItems; i++ ){
          			beanB.getCoverDetails().get( i ).getRiskCodes().setBasicRskCode (  beanA.getVProductConfigPasList().get( i ).getPcBasicRskCode() );
		}

 		/* Mapping: "vProductConfigPasList[].pcRskCode" -> "coverDetails[].riskCodes.riskCode" */
  		noOfItems = beanA.getVProductConfigPasList().size();
   		for( int i = 0; i < noOfItems; i++ ){
          			beanB.getCoverDetails().get( i ).getRiskCodes().setRiskCode (  beanA.getVProductConfigPasList().get( i ).getPcRskCode() );
		}

 		/* Mapping: "vProductConfigPasList[].pcRtCode" -> "coverDetails[].riskCodes.riskType" */
  		noOfItems = beanA.getVProductConfigPasList().size();
   		for( int i = 0; i < noOfItems; i++ ){
          			beanB.getCoverDetails().get( i ).getRiskCodes().setRiskType (  beanA.getVProductConfigPasList().get( i ).getPcRtCode() );
		}

 		/* Mapping: "vProductConfigPasList[].pcRcCode" -> "coverDetails[].riskCodes.riskCat" */
  		noOfItems = beanA.getVProductConfigPasList().size();
   		for( int i = 0; i < noOfItems; i++ ){
          			beanB.getCoverDetails().get( i ).getRiskCodes().setRiskCat (  beanA.getVProductConfigPasList().get( i ).getPcRcCode() );
		}

   		/* Mapping: "vProductConfigPasList[].prSumInsured" -> "coverDetails[].sumInsured.aDesc" */
  		noOfItems = beanA.getVProductConfigPasList().size();
   		for( int i = 0; i < noOfItems; i++ ){
           			com.rsaame.pas.cmn.converter.BigDecimalStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalStringConverter.class, com.Constant.CONST_APROPS, com.Constant.CONST_BPROPS );
			beanB.getCoverDetails().get( i ).getSumInsured().setaDesc( converter.getTypeOfB().cast( converter.getBFromA( beanA.getVProductConfigPasList().get( i ).getPrSumInsured() ) ) );
 		}
   		
   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.CoverDetails initializeDeepVO( com.rsaame.pas.dao.model.VMasProductConfigPasWrapper beanA, com.rsaame.pas.vo.bus.CoverDetails beanB ){
  		BeanUtils.initializeBeanField( "coverDetails[]", beanB, beanA.getVProductConfigPasList().size() );
     	BeanUtils.initializeBeanField( "coverDetails[].sumInsured", beanB, beanA.getVProductConfigPasList().size() );
   		BeanUtils.initializeBeanField( "coverDetails[].coverCodes", beanB, beanA.getVProductConfigPasList().size() );
   		BeanUtils.initializeBeanField( "coverDetails[].riskCodes", beanB, beanA.getVProductConfigPasList().size() );
        return beanB;
	}
}
