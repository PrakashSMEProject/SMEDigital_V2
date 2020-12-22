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
 * <li>com.rsaame.pas.vo.bus.TravelBaggageVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( RequestToTravelBaggageVOMapper.class )</code>.
 */
public class RequestToTravelBaggageVOMapper extends BaseBeanToBeanMapper<javax.servlet.http.HttpServletRequest, com.rsaame.pas.vo.bus.TravelBaggageVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public RequestToTravelBaggageVOMapper(){
		super();
	}

	public RequestToTravelBaggageVOMapper( javax.servlet.http.HttpServletRequest src, com.rsaame.pas.vo.bus.TravelBaggageVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.TravelBaggageVO mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.TravelBaggageVO) Utils.newInstance( "com.rsaame.pas.vo.bus.TravelBaggageVO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		javax.servlet.http.HttpServletRequest beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.TravelBaggageVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		List<String> noOfItems = null;
		Integer index = null;
		
		/* Mapping: "commission" -> "commission" */
		if( !Utils.isEmpty( src.getParameter( "commission" ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.setCommission( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "commission" ) ) ) );
  		}
		
		/* Mapping: "travelempName" -> "travellingEmpDets[].name" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "travelempName" );
  		index =0;
   		for( String i :noOfItems){
        			beanB.getTravellingEmpDets().get( index ).setName (  beanA.getParameter( i ) );
        			index = index+1;
		}

 		/* Mapping: "dateofbirth" -> "travellingEmpDets[].dateOfBirth" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "dateofbirth" );
  		index =0;
   		for( String i :noOfItems ){
        			beanB.getTravellingEmpDets().get(  index ).setDateOfBirth (  beanA.getParameter( i ) );
        			index = index+1;
		}

 		/* Mapping: "limitrequired" -> "travellingEmpDets[].sumInsuredDtl.sumInsured" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "limitrequired" );
  		index =0;
   		for( String i :noOfItems ){
        			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "$bProps", "$aProps" );
			beanB.getTravellingEmpDets().get( index).getSumInsuredDtl().setSumInsured ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( i ) ) ) );
			index = index+1;
 		}

 		/* Mapping: "travelDeductible" -> "travellingEmpDets[].sumInsuredDtl.deductible" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "travelDeductible" );
  		index =0;
   		for( String i :noOfItems ){
        			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "$bProps", "$aProps" );
			beanB.getTravellingEmpDets().get( index ).getSumInsuredDtl().setDeductible ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( i ) ) ) );
			index = index+1;
 		}

 		/* Mapping: "gprId" -> "travellingEmpDets[].gprId" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "gprId" );
  		index =0;
   		for( String i :noOfItems ){
        			beanB.getTravellingEmpDets().get(  index ).setGprId (  beanA.getParameter( i ) );
        			index = index+1;
		}

   		/* Mapping: "rowIndex" -> "travellingEmpDets[].index" */
   		/* This is basically to obtain the exact requestparameter indices and
   		 * this can be done by using any of the incoming parameter.
   		 * here we will use gprtId
   		 */
   		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "gprId" );
  		index =0;
   		for( String i :noOfItems ){
			beanB.getTravellingEmpDets().get(  index ).setIndex( indexofParameter(i) ) ;
			index = index+1;
   		}
   		
   		if( !Utils.isEmpty( src.getParameter( "cnt" ) ) ){
   			Integer cnt =Integer.valueOf( beanA.getParameter( "cnt" ) );
   			beanB.setIndex( cnt );
   			
   		}
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.TravelBaggageVO initializeDeepVO( javax.servlet.http.HttpServletRequest beanA, com.rsaame.pas.vo.bus.TravelBaggageVO beanB ){
  		BeanUtils.initializeBeanField( "travellingEmpDets[]", beanB, HTTPUtils.getMatchingMultiReqParamKeys( beanA, "travelempName[]" ).size() );
     		BeanUtils.initializeBeanField( "travellingEmpDets[].sumInsuredDtl", beanB, HTTPUtils.getMatchingMultiReqParamKeys( beanA, "limitrequired[]" ).size() );
      		return beanB;
	}
	
	private Integer indexofParameter(String stringParameter){
		
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
	
