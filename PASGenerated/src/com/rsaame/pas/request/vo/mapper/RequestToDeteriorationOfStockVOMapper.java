       /*
 * THIS IS A GENERATED CLASS. THIS CLASS SHOULD NOT BE EDITED.
 */
package com.rsaame.pas.request.vo.mapper;

import java.util.Iterator;

import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.BeanUtils;
import com.mindtree.ruc.cmn.utils.HTTPUtils;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.vo.bus.DeteriorationOfStockDetailsVO;

/**
 * Mapper class for:<ol>
 * <li>javax.servlet.http.HttpServletRequest</li>
 * <li>com.rsaame.pas.vo.bus.DeteriorationOfStockVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( RequestToDeteriorationOfStockVOMapper.class )</code>.
 */
public class RequestToDeteriorationOfStockVOMapper extends BaseBeanToBeanMapper<javax.servlet.http.HttpServletRequest, com.rsaame.pas.vo.bus.DeteriorationOfStockVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public RequestToDeteriorationOfStockVOMapper(){
		super();
	}

	public RequestToDeteriorationOfStockVOMapper( javax.servlet.http.HttpServletRequest src, com.rsaame.pas.vo.bus.DeteriorationOfStockVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.DeteriorationOfStockVO mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.DeteriorationOfStockVO) Utils.newInstance( "com.rsaame.pas.vo.bus.DeteriorationOfStockVO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		javax.servlet.http.HttpServletRequest beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.DeteriorationOfStockVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;
		
		/* Mapping: "commission" -> "commission" */
		if( !Utils.isEmpty( src.getParameter( "commission" ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.setCommission( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "commission" ) ) ) );
  		}

 		/* Mapping: "dosStockType" -> "deteriorationOfStockDetails[].deteriorationOfStockType" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "dosStockType" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			beanB.getDeteriorationOfStockDetails().get( i ).setDeteriorationOfStockType (  beanA.getParameter( "dosStockType[" + i + "]" ) );
		}

 		/* Mapping: "dosDescription" -> "deteriorationOfStockDetails[].deteriorationOfStockDesc" 
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "dosDescription" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			beanB.getDeteriorationOfStockDetails().get( i ).setDeteriorationOfStockDesc (  beanA.getParameter( "dosDescription[" + i + "]" ) );
		}*/

 		/* Mapping: "dosQuantity" -> "deteriorationOfStockDetails[].deteriorationOfStockQuantity" 
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "dosQuantity" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getDeteriorationOfStockDetails().get( i ).setDeteriorationOfStockQuantity ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "dosQuantity[" + i + "]" ) ) ) );
 		}*/

 		/* Mapping: "dosValue" -> "deteriorationOfStockDetails[].sumInsuredDetails.sumInsured" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "dosValue" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.getDeteriorationOfStockDetails().get( i ).getSumInsuredDetails().setSumInsured ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "dosValue[" + i + "]" ) ) ) );
 		}

 		/* Mapping: "dosDeuductible" -> "deteriorationOfStockDetails[].sumInsuredDetails.deductible" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "dosDeductible" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.getDeteriorationOfStockDetails().get( i ).getSumInsuredDetails().setDeductible ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "dosDeductible[" + i + "]" ) ) ) );
 		}

   		/* Mapping: "contentId" -> "deteriorationOfStockDetails[].contentId" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "contentId" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			com.rsaame.pas.cmn.converter.LongStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongStringConverter.class, "", "" );
			beanB.getDeteriorationOfStockDetails().get( i ).setContentId ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "contentId[" + i + "]" ) ) ) );
 		}
   		
 		/* Mapping: "dosEMLper" -> "deteriorationOfStockUWDetails.emlPercentage" */
		if( !Utils.isEmpty( src.getParameter( "dosEMLper" ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.getDeteriorationOfStockUWDetails().setEmlPercentage( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "dosEMLper" ) ) ) );
  		}

 		/* Mapping: "dosEMLsi" -> "deteriorationOfStockUWDetails.emlSI" */
		if( !Utils.isEmpty( src.getParameter( "dosEMLsi" ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.getDeteriorationOfStockUWDetails().setEmlSI( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "dosEMLsi" ) ) ) );
  		}

 		/* Mapping: "UnderDetailsBi" -> "deteriorationOfStockUWDetails.emlBIPercentage" */
		if( !Utils.isEmpty( src.getParameter( "UnderDetailsBi" ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.getDeteriorationOfStockUWDetails().setEmlBIPercentage( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "UnderDetailsBi" ) ) ) );
  		}

 		/* Mapping: "UnderDetailsBisl" -> "deteriorationOfStockUWDetails.emlBI" */
		if( !Utils.isEmpty( src.getParameter( "UnderDetailsBisl" ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.getDeteriorationOfStockUWDetails().setEmlBI( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "UnderDetailsBisl" ) ) ) );
  		}
		
		Iterator itr = dest.getDeteriorationOfStockDetails().iterator();
		DeteriorationOfStockDetailsVO detailsVO;
		while(itr.hasNext()){
			detailsVO = (DeteriorationOfStockDetailsVO)itr.next();
			if(!com.mindtree.ruc.cmn.utils.Utils.isEmpty(detailsVO))
			if(detailsVO.getSumInsuredDetails().getSumInsured() == 0.0 &&  
					detailsVO.getSumInsuredDetails().getDeductible()==0.0)
				itr.remove();
			}
   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.DeteriorationOfStockVO initializeDeepVO( javax.servlet.http.HttpServletRequest beanA, com.rsaame.pas.vo.bus.DeteriorationOfStockVO beanB ){
    		BeanUtils.initializeBeanField( "deteriorationOfStockDetails[]", beanB, HTTPUtils.getMatchingMultiReqParamKeys( beanA, "dosStockType[]" ).size() );
       		BeanUtils.initializeBeanField( "deteriorationOfStockDetails[].sumInsuredDetails", beanB, HTTPUtils.getMatchingMultiReqParamKeys( beanA, "dosValue[]" ).size() );
     		BeanUtils.initializeBeanField( "deteriorationOfStockUWDetails", beanB );
        		return beanB;
	}
}
