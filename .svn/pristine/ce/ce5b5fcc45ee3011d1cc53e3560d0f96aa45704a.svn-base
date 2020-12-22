       /*
 * THIS IS A GENERATED CLASS. THIS CLASS SHOULD NOT BE EDITED.
 */
package com.rsaame.pas.request.vo.mapper;

import java.util.List;

import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.BeanUtils;
import com.mindtree.ruc.cmn.utils.HTTPUtils;
import com.mindtree.ruc.cmn.utils.Utils;

/**
 * Mapper class for:<ol>
 * <li>javax.servlet.http.HttpServletRequest</li>
 * <li>com.rsaame.pas.vo.bus.CoverDetails</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( RequestToCoverDetailsMapper.class )</code>.
 */
public class RequesttoCoverdetailsPPList extends BaseBeanToBeanMapper<javax.servlet.http.HttpServletRequest, com.rsaame.pas.vo.bus.CoverDetails>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public RequesttoCoverdetailsPPList(){
		super();
	}

	public RequesttoCoverdetailsPPList( javax.servlet.http.HttpServletRequest src, com.rsaame.pas.vo.bus.CoverDetails dest ){
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
		javax.servlet.http.HttpServletRequest beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.CoverDetails beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		List<String> noOfItems = null;
		int index = 0;
		/* Mapping: "item_name" -> "coverDetails[].coverName" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "sheepItForm2_Desc" );
  	//	index = 0;
   		for(String i : noOfItems ){
   			beanB.getCoverDetails().get( index ).setCoverName (  beanA.getParameter( i ) );
        	index++;
		}

 		/* Mapping: "item_sum" -> "coverDetails[].sumInsured.sumInsured" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, com.Constant.CONST_SHEEPITFORM2_SUMINSURED );
  	  	index = 0;
   		for(String i : noOfItems ){
   			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, com.Constant.CONST_BPROPS, com.Constant.CONST_APROPS );
			beanB.getCoverDetails().get( index ).getSumInsured().setSumInsured ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter(  i  ) ) ) );
			index++;
 		}

  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, com.Constant.CONST_SHEEPITFORM2_SUMINSURED );
  	  	index = 0;
   		for(String i : noOfItems ){
   			com.rsaame.pas.cmn.converter.ShortStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.ShortStringConverter.class, com.Constant.CONST_BPROPS, com.Constant.CONST_APROPS );
			beanB.getCoverDetails().get( index ).getCoverCodes().setCovCode ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter("sheepItForm2_covCode") ) ) );
			index++;
   		}
   		
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, com.Constant.CONST_SHEEPITFORM2_SUMINSURED );
  	  	index = 0;
   		for(String i : noOfItems ){
   			com.rsaame.pas.cmn.converter.ShortStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.ShortStringConverter.class, com.Constant.CONST_BPROPS, com.Constant.CONST_APROPS );
			beanB.getCoverDetails().get( index ).getCoverCodes().setCovTypeCode( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "sheepItForm2_covTypeCode"  ) ) ) );
			index++;
   		}

 		/* Mapping: "risk_basicriskcode" -> "coverDetails[].riskCodes.basicRskCode" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, com.Constant.CONST_SHEEPITFORM2_SUMINSURED );
  	  	index = 0;
   		for(String i : noOfItems ){
   			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, com.Constant.CONST_BPROPS, com.Constant.CONST_APROPS );
			beanB.getCoverDetails().get( index ).getRiskCodes().setBasicRskCode ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "sheepItForm2_basicRskCode" ) ) ) );
			index++;
   		}

 		/* Mapping: "risk_riskcode" -> "coverDetails[].riskCodes.riskCode" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, com.Constant.CONST_SHEEPITFORM2_SUMINSURED );
  	  	index = 0;
   		for(String i : noOfItems ){
   			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, com.Constant.CONST_BPROPS, com.Constant.CONST_APROPS );
			beanB.getCoverDetails().get( index ).getRiskCodes().setRiskCode ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter(  "sheepItForm2_riskCode" ) ) ) );
			index++;
   		}

 		/* Mapping: "risk_risktype" -> "coverDetails[].riskCodes.riskType" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, com.Constant.CONST_SHEEPITFORM2_SUMINSURED );
  	  	index = 0;
   		for(String i : noOfItems ){
   			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, com.Constant.CONST_BPROPS, com.Constant.CONST_APROPS );
			beanB.getCoverDetails().get( index ).getRiskCodes().setRiskType ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "sheepItForm2_riskType" ) ) ) );
			index++;
   		}

 		/* Mapping: "risk_riskcat" -> "coverDetails[].riskCodes.riskCat" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, com.Constant.CONST_SHEEPITFORM2_SUMINSURED );
  	  	index = 0;
   		for(String i : noOfItems ){
   			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, com.Constant.CONST_BPROPS, com.Constant.CONST_APROPS );
			beanB.getCoverDetails().get( index ).getRiskCodes().setRiskCat ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "sheepItForm2_riskCat") ) ) );
			index++;
   		}
   		
   		/*	Mapping: "risk_riskcat" -> "coverDetails[].riskCodes.riskCat" 
   		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "sheepItForm2_isFreeCover" );
   		index = 0;
   		for(String i : noOfItems ){
   			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, com.Constant.CONST_BPROPS, com.Constant.CONST_APROPS );
   			beanB.getCoverDetails().get( index ).( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( i ) ) ) );
   			index++;
   		}*/

 		/* Mapping: "item_vsd" -> "coverDetails[].vsd" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "sheepItForm2_vsd" );
  	  	index = 0;
   		for(String i : noOfItems ){
   			if(!Utils.isEmpty( beanA.getParameter( i ) ) && !beanA.getParameter( i ).equalsIgnoreCase("null")){
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", "format=yyyy-MM-dd HH:mm:ss.SSS" );
			beanB.getCoverDetails().get( index ).setVsd( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( i ) ) ) );
   			}
			index++;
   		}
   		
 		/* Mapping: "risk_id" -> "coverDetails[].riskCodes.risId" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "sheepItForm2_rskId" );
  	  	index = 0;
   		for(String i : noOfItems ){
   			if(!Utils.isEmpty( beanA.getParameter( i ) )){
			com.rsaame.pas.cmn.converter.BigDecimalStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalStringConverter.class, "", "" );
			beanB.getCoverDetails().get( index ).getRiskCodes().setRskId( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( i ) ) ) );
   			}
			index++;
   		}
   		
 		/* Mapping: "risk_id" -> "coverDetails[].riskCodes.risId" 
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "item_rskId" );
  	  	index = 0;
   		for(String i : noOfItems ){
   			if(!Utils.isEmpty( beanA.getParameter( i ) )){
			com.rsaame.pas.cmn.converter.BigDecimalStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalStringConverter.class, "", "" );
			beanB.getCoverDetails().get( index ).getRiskCodes().setRskId( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( i ) ) ) );
   			}
			index++;
   		}*/

 		/* Mapping: "isCovered" -> "coverDetails[].isCovered" */
  		/*noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, com.Constant.CONST_SHEEPITFORM2_SUMINSURED );
  	  	index = 0;
   		for(@SuppressWarnings("unused") String i : noOfItems ){
   			beanB.getCoverDetails().get( index ).setIsCovered("on");
			index++;
   		}
  		*/
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.CoverDetails initializeDeepVO( javax.servlet.http.HttpServletRequest beanA, com.rsaame.pas.vo.bus.CoverDetails beanB ){
  		BeanUtils.initializeBeanField( "coverDetails[]", beanB, HTTPUtils.getMatchingMultiReqParamKeys( beanA, "sheepItForm2_Desc[]" ).size() );
   		BeanUtils.initializeBeanField( "coverDetails[].sumInsured", beanB, HTTPUtils.getMatchingMultiReqParamKeys( beanA, "sheepItForm2_sumInsured[]" ).size() );
   		BeanUtils.initializeBeanField( "coverDetails[].coverCodes", beanB );
   		BeanUtils.initializeBeanField( "coverDetails[].riskCodes", beanB );
        		return beanB;
	}
}
