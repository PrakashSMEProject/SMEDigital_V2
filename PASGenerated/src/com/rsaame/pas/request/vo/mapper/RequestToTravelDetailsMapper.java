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
 * <li>com.rsaame.pas.vo.bus.TravelDetailsVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( RequestToTravelDetailsMapper.class )</code>.
 */
public class RequestToTravelDetailsMapper extends BaseBeanToBeanMapper<javax.servlet.http.HttpServletRequest, com.rsaame.pas.vo.bus.TravelDetailsVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public RequestToTravelDetailsMapper(){
		super();
	}

	public RequestToTravelDetailsMapper( javax.servlet.http.HttpServletRequest src, com.rsaame.pas.vo.bus.TravelDetailsVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.TravelDetailsVO mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.TravelDetailsVO) Utils.newInstance( "com.rsaame.pas.vo.bus.TravelDetailsVO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		javax.servlet.http.HttpServletRequest beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.TravelDetailsVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		List<String> noOfItems = null;
		int index = 0;
		/* Mapping: "travel_quote_name_location" -> "travelLocation" */
		if( !Utils.isEmpty( src.getParameter( "travel_quote_name_location" ) ) ){
 			beanB.setTravelLocation( beanA.getParameter( "travel_quote_name_location" ) ); 
 		}

 		/* Mapping: "travel_quote_name_period" -> "travelPeriod" */
		if( !Utils.isEmpty( src.getParameter( "travel_quote_name_period" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.setTravelPeriod( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "travel_quote_name_period" ) ) ) );
  		}

 		/* Mapping: "travel_quote_name_startDate" -> "startDate" */
		if( !Utils.isEmpty( src.getParameter( "travel_quote_name_startDate" ) ) ){
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", com.Constant.CONST_FORMAT_YYYY_MM_DD );
			beanB.setStartDate( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "travel_quote_name_startDate" ) ) ) );
  		}

 		/* Mapping: "travel_quote_name_endDate" -> "endDate" */
		if( !Utils.isEmpty( src.getParameter( "travel_quote_name_endDate" ) ) ){
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", com.Constant.CONST_FORMAT_YYYY_MM_DD );
			beanB.setEndDate( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "travel_quote_name_endDate" ) ) ) );
  		}

 		/* Mapping: "traveller_name" -> "travelerDetailsList[].name" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "traveller_name" );
  		index = 0;
   		for( String i : noOfItems ){
        			beanB.getTravelerDetailsList().get( index ).setName (  beanA.getParameter( i ) );
        			index++;
		}

 		/* Mapping: "traveller_dob" -> "travelerDetailsList[].dateOfBirth" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "traveller_dob" );
  		index = 0;
   		for( String i : noOfItems  ){
        			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", com.Constant.CONST_FORMAT_YYYY_MM_DD );
			beanB.getTravelerDetailsList().get( index ).setDateOfBirth ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( i ) ) ) );
			index++;
 		}

 		/* Mapping: "traveller_relation" -> "travelerDetailsList[].relation" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "traveller_relation" );
  		index = 0;
   		for(String i : noOfItems ){
        			beanB.getTravelerDetailsList().get( index ).setRelation (Byte.valueOf( beanA.getParameter( i )) );
        			index++;
		}

 		/* Mapping: "traveller_nationality" -> "travelerDetailsList[].nationality" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "traveller_nationality" );
  		index = 0;
   		for( String i : noOfItems ){
        			com.rsaame.pas.cmn.converter.ShortStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.ShortStringConverter.class, "", com.Constant.CONST_FORMAT_YYYY_MM_DD );
			beanB.getTravelerDetailsList().get( index ).setNationality ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( i ) ) ) );
			index++;
 		}

 		/* Mapping: "traveller_nationality" -> "travelerDetailsList[].traveller_table_id" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "traveller_table_id" );
  		index = 0;
   		for( String i : noOfItems){
			com.rsaame.pas.cmn.converter.BigDecimalStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalStringConverter.class, "", "" );
			beanB.getTravelerDetailsList().get( index ).setGprId( converter.getTypeOfA().cast( converter.getAFromB(beanA.getParameter( i) ) ) );
			index++;
 		}

 		/* Mapping: "traveller_nationality" -> "travelerDetailsList[].traveller_vsd" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "traveller_vsd" );
  		index = 0;
   		for( String i : noOfItems ){
   			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", "format=dd/MM/yyyy HH:mm:ss" );
   			beanB.getTravelerDetailsList().get( index ).setVsd( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( i ) ) ) );
   			index++;
 		}
		
   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.TravelDetailsVO initializeDeepVO( javax.servlet.http.HttpServletRequest beanA, com.rsaame.pas.vo.bus.TravelDetailsVO beanB ){
          		BeanUtils.initializeBeanField( "travelerDetailsList[]", beanB, HTTPUtils.getMatchingMultiReqParamKeys( beanA, "traveller_name[]" ).size() );
        		return beanB;
	}
}
