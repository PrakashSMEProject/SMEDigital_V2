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
 * <li>com.rsaame.pas.vo.bus.MoneyVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( RequestToMoneyVOMapper.class )</code>.
 */
public class RequestToMoneyVOMapper extends BaseBeanToBeanMapper<javax.servlet.http.HttpServletRequest, com.rsaame.pas.vo.bus.MoneyVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public RequestToMoneyVOMapper(){
		super();
	}

	public RequestToMoneyVOMapper( javax.servlet.http.HttpServletRequest src, com.rsaame.pas.vo.bus.MoneyVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.MoneyVO mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.MoneyVO) Utils.newInstance( "com.rsaame.pas.vo.bus.MoneyVO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		javax.servlet.http.HttpServletRequest beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.MoneyVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;
		
		/* Mapping: "commission" -> "commission" */
		if( !Utils.isEmpty( src.getParameter( "commission" ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.setCommission( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "commission" ) ) ) );
  		}
		
		/* Mapping: "basicRiskId" -> "basicRiskId" */
		if( !Utils.isEmpty( src.getParameter( "basicRiskId" ) ) ){
			com.rsaame.pas.cmn.converter.LongStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongStringConverter.class, "", "" );
			beanB.setBasicRiskId( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "basicRiskId" ) ) ) );
		}
		
 		/* Mapping: "cashId" -> "contentsList[].riskId" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "cashId" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			com.rsaame.pas.cmn.converter.LongStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongStringConverter.class, "", "" );
			beanB.getContentsList().get( i ).setRiskId ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "cashId[" + i + "]" ) ) ) );
 		}

 		/* Mapping: "cashVSD" -> "sumInsuredDets[].vsd" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "cashVSD" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", "" );
			beanB.getSumInsuredDets().get( i ).setVsd ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "cashVSD[" + i + "]" ) ) ) );
 		}

 		/* Mapping: "moneySI" -> "sumInsuredDets[].sumInsured" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "moneySI" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.getSumInsuredDets().get( i ).setSumInsured ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "moneySI[" + i + "]" ) ) ) );
 		}

 		/* Mapping: "moneyDeductible" -> "sumInsuredDets[].deductible" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "moneyDeductible" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.getSumInsuredDets().get( i ).setDeductible ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "moneyDeductible[" + i + "]" ) ) ) );
 		}

 		/* Mapping: "moneySI" -> "contentsList[].cover" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "moneySI" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			com.rsaame.pas.cmn.converter.BigDecimalStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalStringConverter.class, "", "" );
			beanB.getContentsList().get( i ).setCover ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "moneySI[" + i + "]" ) ) ) );
 		}

 		/* Mapping: "moneyDeductible" -> "contentsList[].deductibles" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "moneyDeductible" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			com.rsaame.pas.cmn.converter.BigDecimalStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalStringConverter.class, "", "" );
			beanB.getContentsList().get( i ).setDeductibles ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "moneyDeductible[" + i + "]" ) ) ) );
 		}

 		/* Mapping: "riskType" -> "contentsList[].riskType" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "riskType" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getContentsList().get( i ).setRiskType ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "riskType[" + i + "]" ) ) ) );
 		}

 		/* Mapping: "riskCategory" -> "contentsList[].riskCat" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "riskCategory" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getContentsList().get( i ).setRiskCat ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "riskCategory[" + i + "]" ) ) ) );
 		}

 		/* Mapping: "riskSubCategory" -> "contentsList[].riskSubCat" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "riskSubCategory" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getContentsList().get( i ).setRiskSubCat ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "riskSubCategory[" + i + "]" ) ) ) );
 		}

 		/* Mapping: "lsobhId" -> "safeDetails[].id" */
  		/*noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "lsobhId" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        	com.rsaame.pas.cmn.converter.LongStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongStringConverter.class, "", "" );
			beanB.getSafeDetails().get( i ).setId ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "lsobhId[" + i + "]" ) ) ) );
			
			/* Mapping: "lsobhVSD" -> "safeDetails[].vsd" */
		/*	com.mindtree.ruc.cmn.beanmap.DateStringConverter dateConverter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", "" );
			beanB.getSafeDetails().get( i ).setVsd ( dateConverter.getTypeOfA().cast( dateConverter.getAFromB( beanA.getParameter( "lsobhVSD[" + i + "]" ) ) ) );
			
			/* Mapping: "moneyMake" -> "safeDetails[].Make" */
			//beanB.getSafeDetails().get( i ).setMake (  beanA.getParameter( "moneyMake[" + i + "]" ) );
			
			// Mapping: "moneyWeight" -> "safeDetails[].weight" 
			/*com.rsaame.pas.cmn.converter.DoubleStringConverter doubleConverter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
        	if(!(doubleConverter.getAFromB( beanA.getParameter( "moneyWeight[" + i + "]" ) ).compareTo( 0.0 ) == 0))
			beanB.getSafeDetails().get( i ).setWeight ( doubleConverter.getTypeOfA().cast( doubleConverter.getAFromB( beanA.getParameter( "moneyWeight[" + i + "]" ) ) ) );
			
        	//Mapping: "moneyHeight" -> "safeDetails[].height" 
        	if(!(doubleConverter.getAFromB( beanA.getParameter( "moneyHeight[" + i + "]" ) ).compareTo( 0.0 ) == 0))
			beanB.getSafeDetails().get( i ).setHeight ( doubleConverter.getTypeOfA().cast( doubleConverter.getAFromB( beanA.getParameter( "moneyHeight[" + i + "]" ) ) ) );
        	
        	//Mapping: "moneyWidth" -> "safeDetails[].width" 
        	if(!(doubleConverter.getAFromB( beanA.getParameter( "moneyWidth[" + i + "]" ) ).compareTo( 0.0 ) == 0))
    		beanB.getSafeDetails().get( i ).setWidth ( doubleConverter.getTypeOfA().cast( doubleConverter.getAFromB( beanA.getParameter( "moneyWidth[" + i + "]" ) ) ) );
            
        	//Mapping: "moneyAnchored" -> "safeDetails[].anchored" 
        	beanB.getSafeDetails().get( i ).setAnchored (  beanA.getParameter( "moneyAnchored[" + i + "]" ) );
 		}*/

 		
   		/*noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "lsobhVSD" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
   			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", "" );
			beanB.getSafeDetails().get( i ).setVsd ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "lsobhVSD[" + i + "]" ) ) ) );
 		}

 		 Mapping: "moneyMake" -> "safeDetails[].Make" 
  			noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "moneyMake" ).size();
   			for( int i = 0; i < noOfItems; i++ ){
        			beanB.getSafeDetails().get( i ).setMake (  beanA.getParameter( "moneyMake[" + i + "]" ) );
			}

 		 Mapping: "moneyWeight" -> "safeDetails[].weight" 
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "moneyWeight" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
   					com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
        	if(!converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "moneyWeight[" + i + "]" ) ) ).equals( 0.0 ))
			beanB.getSafeDetails().get( i ).setWeight ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "moneyWeight[" + i + "]" ) ) ) );
 		}

 		 Mapping: "moneyHeight" -> "safeDetails[].height" 
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "moneyHeight" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
        			if(!converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "moneyHeight[" + i + "]" ) ) ).equals( 0.0 ))
			beanB.getSafeDetails().get( i ).setHeight ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "moneyHeight[" + i + "]" ) ) ) );
 		}

 		 Mapping: "moneyWidth" -> "safeDetails[].width" 
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "moneyWidth" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
        			if(!converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "moneyWidth[" + i + "]" ) ) ).equals( 0.0 ))		
			beanB.getSafeDetails().get( i ).setWidth ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "moneyWidth[" + i + "]" ) ) ) );
 		}

 		 Mapping: "moneyAnchored" -> "safeDetails[].anchored" 
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "moneyAnchored" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			beanB.getSafeDetails().get( i ).setAnchored (  beanA.getParameter( "moneyAnchored[" + i + "]" ) );
		}*/

 		/* Mapping: com.Constant.CONST_DRINK -> "cashInResidence" */
		if( !Utils.isEmpty( src.getParameter( com.Constant.CONST_BUSINESSHRS ) ) ){
			com.mindtree.ruc.cmn.beanmap.BooleanStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.BooleanStringConverter.class, "", "" );
			beanB.setExcessCashInSafe( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( com.Constant.CONST_BUSINESSHRS ) ) ) );
  		}

   		if(!Utils.isEmpty( beanA.getParameter( com.Constant.CONST_BUSINESSHRS ) )&&((String)beanA.getParameter( com.Constant.CONST_BUSINESSHRS )).equalsIgnoreCase("on")){
   			List<String> items = null;
   			Integer index = null;
   			items = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "lsobhId" );
   			index =0;
   	   		for( String i :items ){
   	   			com.rsaame.pas.cmn.converter.LongStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongStringConverter.class, "", "" );
   				beanB.getSafeDetails().get( index ).setId ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter(i) ) ) );
   				index = index+1;
   	 		}
   	   		items = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "lsobhVSD" );
			index =0;
	   		for( String i :items ){
	   			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance(com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", "" );
				beanB.getSafeDetails().get( index ).setVsd( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter(i) ) ) );
				index = index+1;
	 		}
   	   		items = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "moneyMake" );
   			index =0;
   	   		for( String i :items ){
   				beanB.getSafeDetails().get( index ).setMake( beanA.getParameter(i) ) ;
   				index = index+1;
   	 		}
   	   		items = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "moneyWeight" );
   			index =0;
   	   		for( String i :items ){
   	   		com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
   				beanB.getSafeDetails().get( index ).setWeight ( converter.getAFromB( beanA.getParameter(i) ) );
   				index = index+1;
   	 		}
   	   		items = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "moneyHeight" );
   			index =0;
   	   		for( String i :items ){
   	   			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
   				beanB.getSafeDetails().get( index ).setHeight(converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter(i) ) ) );
   				index = index+1;
   	 		}
   	   		items = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "moneyWidth" );
   	  		index =0;
   	   		for( String i :items ){
   	   		com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
   	   			beanB.getSafeDetails().get( index ).setWidth( converter.getAFromB( beanA.getParameter( i ) ));
   				index = index+1;
   	   		}
   	   	items = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "moneyAnchored" );
	  		index =0;
	   		for( String i :items ){
	   			beanB.getSafeDetails().get( index ).setAnchored( beanA.getParameter( i ) );
				index = index+1;
	   		}
	   		items = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "lsobhVSD" );
	  		index =0;
	   		for( String i :items ){
				beanB.getSafeDetails().get(  index ).setIndex( indexofParameter(i) ) ;
				index = index+1;
	   		}
   			}
 		/* Mapping: com.Constant.CONST_BUSINESSHRS -> "excessCashInSafe" */
		if( !Utils.isEmpty( src.getParameter( com.Constant.CONST_DRINK ) ) ){
			com.mindtree.ruc.cmn.beanmap.BooleanStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.BooleanStringConverter.class, "", "" );
			beanB.setCashInResidence( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( com.Constant.CONST_DRINK ) ) ) );
  		}

		/**
		 * Changes in money add row and remove row
		 */
		
		
 		/* Mapping: "ciaerId" -> "cashResDetails[].id" */
		if( !Utils.isEmpty( src.getParameter( com.Constant.CONST_DRINK ) ) && ((String)beanA.getParameter( com.Constant.CONST_DRINK )).equalsIgnoreCase("no")){
   			beanB.setIsCashResNotSelected(true);
   		}
		if(!Utils.isEmpty( beanA.getParameter( com.Constant.CONST_DRINK ) )&&((String)beanA.getParameter( com.Constant.CONST_DRINK )).equalsIgnoreCase("on")){
		List<String> items = null;
		Integer index = null;
		items = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "ciaerId" );
		index =0;
   		for( String i :items ){
   			com.rsaame.pas.cmn.converter.LongStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongStringConverter.class, "", "" );
			beanB.getCashResDetails().get( index ).setId ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter(i) ) ) );
			index = index+1;
 		}
   		items = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "empName" );
		index =0;
   		for( String i :items ){
   			
			beanB.getCashResDetails().get( index ).setEmpName( beanA.getParameter(i) ) ;
			index = index+1;
 		}
   		items = HTTPUtils.getMatchingMultiReqParamKeys( beanA, com.Constant.CONST_OCCUPATION );
		index =0;
   		for( String i :items ){
			beanB.getCashResDetails().get( index ).setOccupation (( beanA.getParameter(i) ) );
			index = index+1;
 		}
   		items = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "empAmount" );
		index =0;
   		for( String i :items ){
   			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.getCashResDetails().get( index ).getSumInsuredDets().setSumInsured( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter(i) ) ) );
			index = index+1;
 		}
   		items = HTTPUtils.getMatchingMultiReqParamKeys( beanA, com.Constant.CONST_OCCUPATION );
  		index =0;
   		for( String i :items ){
			beanB.getCashResDetails().get(  index ).setIndex( indexofParameter(i) ) ;
			index = index+1;
   		}
		}
   		/*for( int i = 0; i < noOfItems; i++ ){
   			
   			if(((String)beanA.getParameter( com.Constant.CONST_DRINK )).equalsIgnoreCase("no")){
	   			beanB.setIsCashResNotSelected(true);
	   		}
	   			com.rsaame.pas.cmn.converter.LongStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongStringConverter.class, "", "" );
				beanB.getCashResDetails().get( i ).setId ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "ciaerId[" + i + "]" ) ) ) );
				
				com.mindtree.ruc.cmn.beanmap.DateStringConverter dateConverter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", "" );
				beanB.getCashResDetails().get( i ).setVsd ( dateConverter.getTypeOfA().cast( dateConverter.getAFromB( beanA.getParameter( "ciaerVSD[" + i + "]" ) ) ) );
				
				beanB.getCashResDetails().get( i ).setEmpName (  beanA.getParameter( "empName[" + i + "]" ) );
				
				beanB.getCashResDetails().get( i ).setOccupation (  beanA.getParameter( "occupation[" + i + "]" ) );
					
				com.rsaame.pas.cmn.converter.DoubleStringConverter doubleConverter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
				beanB.getCashResDetails().get( i ).getSumInsuredDets().setSumInsured ( doubleConverter.getTypeOfA().cast( doubleConverter.getAFromB( beanA.getParameter( "amount[" + i + "]" ) ) ) );
			
 		}*/
   		
 		/* Mapping: "ciaerVSD" -> "cashResDetails[].vsd" */
  		/*noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "ciaerVSD" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			com.mindtree.ruc.cmn.beanmap.DateStringConverter dateConverter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", "" );
			beanB.getCashResDetails().get( i ).setVsd ( dateConverter.getTypeOfA().cast( dateConverter.getAFromB( beanA.getParameter( "ciaerVSD[" + i + "]" ) ) ) );
 		}*/

 		/* Mapping: "empName" -> "cashResDetails[].empName" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "empName" ).size();
   		/*for( int i = 0; i < noOfItems; i++ ){
        			beanB.getCashResDetails().get( i ).setEmpName (  beanA.getParameter( "empName[" + i + "]" ) );
		}*/

 		/* Mapping: com.Constant.CONST_OCCUPATION -> "cashResDetails[].occupation" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, com.Constant.CONST_OCCUPATION ).size();
   		/*for( int i = 0; i < noOfItems; i++ ){
        			beanB.getCashResDetails().get( i ).setOccupation (  beanA.getParameter( "occupation[" + i + "]" ) );
		}*/

 		/* Mapping: "amount" -> "cashResDetails[].sumInsuredDets.sumInsured" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "amount" ).size();
   		/*for( int i = 0; i < noOfItems; i++ ){
        			com.rsaame.pas.cmn.converter.DoubleStringConverter doubleConverter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.getCashResDetails().get( i ).getSumInsuredDets().setSumInsured ( doubleConverter.getTypeOfA().cast( doubleConverter.getAFromB( beanA.getParameter( "amount[" + i + "]" ) ) ) );
 		}*/

   		
   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.MoneyVO initializeDeepVO( javax.servlet.http.HttpServletRequest beanA, com.rsaame.pas.vo.bus.MoneyVO beanB ){
    	BeanUtils.initializeBeanField( "contentsList[]", beanB, HTTPUtils.getMatchingMultiReqParamKeys( beanA, "cashId[]" ).size() );
   		BeanUtils.initializeBeanField( "sumInsuredDets[]", beanB, HTTPUtils.getMatchingMultiReqParamKeys( beanA, "cashVSD[]" ).size() );
   		if(!Utils.isEmpty( beanA.getParameter( com.Constant.CONST_BUSINESSHRS ) )&&((String)beanA.getParameter( com.Constant.CONST_BUSINESSHRS )).equalsIgnoreCase("on")){
        BeanUtils.initializeBeanField( "safeDetails[]", beanB, HTTPUtils.getMatchingMultiReqParamKeys( beanA, "lsobhId[]" ).size() );
   		}
        if(!Utils.isEmpty( beanA.getParameter( com.Constant.CONST_DRINK ) )&&((String)beanA.getParameter( com.Constant.CONST_DRINK )).equalsIgnoreCase("on")){         	
                BeanUtils.initializeBeanField( "cashResDetails[]", beanB, HTTPUtils.getMatchingMultiReqParamKeys( beanA, "ciaerId[]" ).size() );
         		BeanUtils.initializeBeanField( "cashResDetails[].sumInsuredDets", beanB, HTTPUtils.getMatchingMultiReqParamKeys( beanA, "amount[]" ).size() );
                 		} 
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
