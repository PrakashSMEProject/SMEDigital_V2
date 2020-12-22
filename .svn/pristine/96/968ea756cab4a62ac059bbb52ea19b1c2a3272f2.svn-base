       /*
 * THIS IS A GENERATED CLASS. THIS CLASS SHOULD NOT BE EDITED.
 */
package com.rsaame.pas.request.vo.mapper;

import java.util.List;

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
 * <li>com.rsaame.pas.vo.bus.GoodsInTransitVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( RequestToGoodsInTransitVOMapper.class )</code>.
 */
public class RequestToGoodsInTransitVOMapper extends BaseBeanToBeanMapper<javax.servlet.http.HttpServletRequest, com.rsaame.pas.vo.bus.GoodsInTransitVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public RequestToGoodsInTransitVOMapper(){
		super();
	}

	public RequestToGoodsInTransitVOMapper( javax.servlet.http.HttpServletRequest src, com.rsaame.pas.vo.bus.GoodsInTransitVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.GoodsInTransitVO mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.GoodsInTransitVO) Utils.newInstance( "com.rsaame.pas.vo.bus.GoodsInTransitVO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		javax.servlet.http.HttpServletRequest beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.GoodsInTransitVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		List<String> parameterList = null;
		Integer index = null;
		
		/* Mapping: "commission" -> "commission" */
		if( !Utils.isEmpty( src.getParameter( "commission" ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.setCommission( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "commission" ) ) ) );
  		}

 		/* Mapping: "gitSingleTransitLimit" -> "singleTransitLimit" */
		if( !Utils.isEmpty( src.getParameter( "gitSingleTransitLimit" ) ) ){
			com.rsaame.pas.cmn.converter.LongStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongStringConverter.class, "", "" );
			beanB.setSingleTransitLimit( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "gitSingleTransitLimit" ) ) ) );
  		}

 		/* Mapping: "gitTxtEstiAnnCarryValue" -> "estimatedAnnualCarryValue" */
		if( !Utils.isEmpty( src.getParameter( "gitTxtEstiAnnCarryValue" ) ) ){
			com.rsaame.pas.cmn.converter.LongStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongStringConverter.class, "", "" );
			beanB.setEstimatedAnnualCarryValue( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "gitTxtEstiAnnCarryValue" ) ) ) );
  		}

		if( !Utils.isEmpty( src.getParameter( "gitDropdownVoyageFrom" ) ) ){
			beanB.setVoyageFrom( beanA.getParameter( "gitDropdownVoyageFrom" ) );
		}
		
		if( !Utils.isEmpty( src.getParameter( "gitDropdownVoyageTo" ) ) ){
			beanB.setVoyageTo( beanA.getParameter( "gitDropdownVoyageTo" ) );
		}
		
		if( !Utils.isEmpty( src.getParameter( "gitDeductible" ) ) ){
		    
		    	com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.setDeductible( converter.getTypeOfA().cast( converter.getAFromB(src.getParameter( "gitDeductible" )))) ;
		}
		
		if( !Utils.isEmpty( src.getParameter( "gitTransitId" ) ) ){
		    
		    com.rsaame.pas.cmn.converter.LongStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongStringConverter.class, "", "" );
		    beanB.setDeclarationId( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "gitTransitId" ) ) ) );
		}
		
 		/* Mapping: "gitSettlementCurrency" -> "settlmentCurrency" 
		if( !Utils.isEmpty( src.getParameter( "gitSettlementCurrency" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.setSettlmentCurrency( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "gitSettlementCurrency" ) ) ) );
  		}

 		 Mapping: "gitTxtExchangeRate" -> "exchangeRate" 
		if( !Utils.isEmpty( src.getParameter( "gitTxtExchangeRate" ) ) ){
			com.rsaame.pas.cmn.converter.BigDecimalStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalStringConverter.class, "", "" );
			beanB.setExchangeRate( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "gitTxtExchangeRate" ) ) ) );
  		}

 		 Mapping: "gitSettlingAgent" -> "settlingAgent" 
		if( !Utils.isEmpty( src.getParameter( "gitSettlingAgent" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.setSettlingAgent( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "gitSettlingAgent" ) ) ) );
  		}

 		 Mapping: "gitSettlementLoc" -> "settlingLocation" 
		if( !Utils.isEmpty( src.getParameter( "gitSettlementLoc" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.setSettlingLocation( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "gitSettlementLoc" ) ) ) );
  		}*/

 		/* Mapping: com.Constant.CONST_GITDECLARATIONID -> "commodityDtls[].declarationId" */
  		parameterList = HTTPUtils.getMatchingMultiReqParamKeys( beanA, com.Constant.CONST_GITDECLARATIONID );
  		index = 0;
   		for( String str : parameterList ){
        		com.rsaame.pas.cmn.converter.LongStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongStringConverter.class, "", "" );
			beanB.getCommodityDtls().get( index++).setCommodityId( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( str ) ) ) );
 		}

 		/* Mapping: "gitDropdownModeOfTrnsit" -> "commodityDtls[].modeOfTransit" */
  		parameterList = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "gitDropdownModeOfTrnsit" );
  		index = 0;
  		for( String str : parameterList ){
        		com.rsaame.pas.cmn.converter.ShortStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.ShortStringConverter.class, "", "" );
			beanB.getCommodityDtls().get( index++ ).setModeOfTransit ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter(str ) ) ) );
 		}

 		/* Mapping: "gitDropdownVoyageFrom" -> "commodityDtls[].voyageFrom" 
  		parameterList = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "gitDropdownVoyageFrom" );
  		index = 0;
  		for( String str : parameterList ){
        			beanB.getCommodityDtls().get( index++ ).setVoyageFrom (  beanA.getParameter( str ) );
		}

 		 Mapping: "gitDropdownVoyageTo" -> "commodityDtls[].voyageTo" 
  		parameterList = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "gitDropdownVoyageTo" );
  		index = 0;
  		for( String str : parameterList ){
        			beanB.getCommodityDtls().get( index++ ).setVoyageTo (  beanA.getParameter( str ) );
		}*/

 		/* Mapping: "gitDropdownCommodityType" -> "commodityDtls[].commodityType" */
  		parameterList = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "gitDropdownCommodityType" );
  		index = 0;
  		for( String str : parameterList ){
        		com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getCommodityDtls().get( index++ ).setCommodityType ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( str ) ) ) );
 		}

 		/* Mapping: "gitTextGoodsDesc" -> "commodityDtls[].goodsDescription" */
  		parameterList = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "gitTextGoodsDesc" );
  		index = 0;
  		for( String str : parameterList ){
        			beanB.getCommodityDtls().get( index++ ).setGoodsDescription (  beanA.getParameter( str ) );
		}

 		/* Mapping: "gitDropdownSIBasis" -> "commodityDtls[].siBasis" */
  		parameterList = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "gitDropdownSIBasis" );
  		index = 0;
  		for( String str : parameterList ){
        		com.rsaame.pas.cmn.converter.BigDecimalStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalStringConverter.class, "", "" );
			beanB.getCommodityDtls().get( index++ ).setSiBasis ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( str ) ) ) );
 		}

 		/* Mapping: "gitDeductible" -> "commodityDtls[].deductible" */
  		/*parameterList = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "gitDeductible" );
  		index = 0;
  		for( String str : parameterList ){
        		com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.getCommodityDtls().get( index++ ).setDeductible ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( str ) ) ) );
 		}*/
   		
   		/* Mapping: com.Constant.CONST_GITDECLARATIONID -> "commodityDtls[].gitDeclarationId" */
   		parameterList = HTTPUtils.getMatchingMultiReqParamKeys( beanA, com.Constant.CONST_GITDECLARATIONID );
   		index = 0;
   		for( String str : parameterList ){
   		 com.rsaame.pas.cmn.converter.LongStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongStringConverter.class, "", "" );
			beanB.getCommodityDtls().get( index++ ).setCommodityId(converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( str ) ) ) );
 		}
   		
   		/* Mapping: "rowIndex" -> "travellingEmpDets[].index" */
   		/* This is basically to obtain the exact requestparameter indices and
   		 * this can be done by using any of the incoming parameter.
   		 * here we will use gprtId
   		 */
   		parameterList = HTTPUtils.getMatchingMultiReqParamKeys( beanA, com.Constant.CONST_GITDECLARATIONID );
  		index =0;
   		for( String i :parameterList ){
			beanB.getCommodityDtls().get(  index++ ).setIndex( indexofParameter(i) ) ;
   		}
   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.GoodsInTransitVO initializeDeepVO( javax.servlet.http.HttpServletRequest beanA, com.rsaame.pas.vo.bus.GoodsInTransitVO beanB ){
                		BeanUtils.initializeBeanField( "commodityDtls[]", beanB, HTTPUtils.getMatchingMultiReqParamKeys( beanA, "gitDropdownModeOfTrnsit[]" ).size() );
                		return beanB;
	}
	
	private int indexofParameter(String stringParameter){
		
		if(Utils.isEmpty( stringParameter ) || !stringParameter.contains( "[" ) )
		 	return -999;
		if(!Utils.isEmpty(stringParameter)){
			String split [] = stringParameter.split( "\\[" );
			String tempString[] = split[1].split( "\\]" );
			return (Integer.valueOf(tempString[0]));
		}
		return -999;
	}	
	
}
