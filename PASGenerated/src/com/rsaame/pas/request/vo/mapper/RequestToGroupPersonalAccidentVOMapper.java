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
 * <li>com.rsaame.pas.vo.bus.GroupPersonalAccidentVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( RequestToGroupPersonalAccidentVOMapper.class )</code>.
 */
public class RequestToGroupPersonalAccidentVOMapper extends BaseBeanToBeanMapper<javax.servlet.http.HttpServletRequest, com.rsaame.pas.vo.bus.GroupPersonalAccidentVO>{
	private static final String GPA_UN_NAMED_EMP = "gpaEmpTypeChk1";
	private static final String GPA_NAMED_EMP = "gpaEmpTypeChk2";

	private static final String ON = "on";
	private final Logger log = Logger.getLogger( this.getClass() );
	boolean isUnnamedEmp = false;
	boolean isNamedEmp = false;

	public RequestToGroupPersonalAccidentVOMapper(){
		super();
	}

	public RequestToGroupPersonalAccidentVOMapper( javax.servlet.http.HttpServletRequest src, com.rsaame.pas.vo.bus.GroupPersonalAccidentVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.GroupPersonalAccidentVO mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.GroupPersonalAccidentVO) Utils.newInstance( "com.rsaame.pas.vo.bus.GroupPersonalAccidentVO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		javax.servlet.http.HttpServletRequest beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.GroupPersonalAccidentVO beanB = dest;
		
		if(!Utils.isEmpty( beanA.getParameter( GPA_UN_NAMED_EMP ) ) && beanA.getParameter( GPA_UN_NAMED_EMP ).equals( ON ) ){
			isUnnamedEmp = true;
		} 
		if(!Utils.isEmpty( beanA.getParameter( GPA_NAMED_EMP ) ) && beanA.getParameter( GPA_NAMED_EMP ).equals( ON ) ){
			isNamedEmp = true;
		}
				
		beanB = initializeDeepVO(beanA, beanB, isUnnamedEmp, isNamedEmp);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		List<String> parameterList = null;
		int noOfItems = 0;
		
		/* Mapping: "commission" -> "commission" */
		if( !Utils.isEmpty( src.getParameter( "commission" ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.setCommission( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "commission" ) ) ) );
  		}
		
		/* Mapping: "gpaDropdownTypeofEmployee" -> "gpaUnnammedEmpVODetails[].gpaTypeofEmployee" */
	
		if ( isUnnamedEmp ) {
		
		parameterList = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "gpaUnNamedtypeOfEmp" );
		noOfItems = 0;
		for( String str : parameterList ){
   			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
   			beanB.getGpaUnnammedEmpVO().get( noOfItems++ ).setUnnammedEmployeeType (converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( str ) ) ) );
		}

 		/* Mapping: "eeTextNewreplacevalue" -> "gpaUnnammedEmpVODetails[].gpaNumOfEmp" */
		parameterList = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "gpaNumOfEmp" );
		noOfItems = 0;
		for( String str : parameterList ){
   			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getGpaUnnammedEmpVO().get( noOfItems++ ).setUnnammedNumberOfEmloyee ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( str ) ) ) );
 		}

 		/* Mapping: "eeTextEquipdes" -> "gpaUnnammedEmpVODetails[].gpaUnnammedEmpAnnualSal" */
		parameterList = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "gpaUnNamedEmpAnnualSal" );
		noOfItems = 0;
		for( String str : parameterList ){
   			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
   			beanB.getGpaUnnammedEmpVO().get( noOfItems++ ).setUnnammedAnnualSalary ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( str ) ) ) );
		}

 		/* Mapping: "eeDropdownDeductible" -> "gpaUnnammedEmpVODetails[].gpaUnnammedEmpCapSI" */
		parameterList = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "gpaUnNamedEmpCapSI" );
		noOfItems = 0;
		for( String str : parameterList ){
        	com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.getGpaUnnammedEmpVO().get( noOfItems++ ).getSumInsuredDetails().setSumInsured( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( str ) ) ) );
 		}

   		/* Mapping: "gupId" -> "gupId" */
   		parameterList = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "gupId" );
		noOfItems = 0;
		for( String str : parameterList ){
			beanB.getGpaUnnammedEmpVO().get( noOfItems++ ).setGupId (  beanA.getParameter( str ) );
		}
		

		parameterList = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "gupId" );
		noOfItems =0;
   		for( String i :parameterList ){
			beanB.getGpaUnnammedEmpVO().get(  noOfItems++ ).setgpaIndex( indexofParameter(i) ) ;
   		}
 	} 
	
	if ( isNamedEmp ) {
  		
   		/***************************************************************************************************/
   		/* Mapping: "NamedEmployee" -> "getGpaNammedEmpVO[].gpaNamedNameOfEmp" */
		parameterList = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "gpaNameOfEmp" );
		noOfItems = 0;
		for( String str : parameterList ){
   			beanB.getGpaNammedEmpVO().get( noOfItems++ ).setNameOfEmployee( beanA.getParameter( str ) );
		}

 		/* Mapping: "NamedEmployee" -> "getGpaNammedEmpVO[].gpaTypeOfEmp" */
		parameterList = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "gpaTypeOfEmp" );
		noOfItems = 0;
		for( String str : parameterList ){
   			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getGpaNammedEmpVO().get( noOfItems++ ).setEmployeeType( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( str ) ) ) );
 		}

 		/* Mapping: "NamedEmployee" -> "getGpaNammedEmpVO[].gpaGender" */
		parameterList = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "gpaGender" );
		noOfItems = 0;
		for( String str : parameterList ){
			com.rsaame.pas.cmn.converter.CharacterStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.CharacterStringConverter.class, "", "" );
   			beanB.getGpaNammedEmpVO().get( noOfItems++ ).setNamedEmpGender( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( str ) ) ) );
		}

 		/* Mapping: "NamedEmployee" -> "getGpaNammedEmpVO[].gpaDOB" */
		parameterList = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "gpaDOB" );
		noOfItems = 0;
		for( String str : parameterList ){
        	beanB.getGpaNammedEmpVO().get( noOfItems++ ).setNammedEmpDob (  beanA.getParameter( str ) );
 		}

 		/* Mapping: "NamedEmployee" -> "getGpaNammedEmpVO[].gpaDesignation" */
		parameterList = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "gpaDesignation" );
		noOfItems = 0;
		for( String str : parameterList ){
   			beanB.getGpaNammedEmpVO().get( noOfItems++ ).setNammedEmpDesignation ( beanA.getParameter( str ));
		}

   		/* Mapping: "NamedEmployee" -> "getGpaNammedEmpVO[].gpaNamedEmpAnnualSal" */
		parameterList = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "gpaNamedEmpAnnualSal" );
		noOfItems = 0;
		for( String str : parameterList ){
        	com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.getGpaNammedEmpVO().get( noOfItems++ ).setNammedEmpAnnualSalary ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( str ) ) ) );
 		}
   		
   		/* Mapping: "NamedEmployee" -> "getGpaNammedEmpVO[].gpaNamedEmpCapSI" */
		parameterList = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "gpaNamedEmpCapSI" );
		noOfItems = 0;
		for( String str : parameterList ){
        			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.getGpaNammedEmpVO().get( noOfItems++ ).getSumInsuredDetails().setSumInsured ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( str ) ) ) );
 		}
   		
		parameterList = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "GpagprId" );
		noOfItems = 0;
		for( String str : parameterList ){
			beanB.getGpaNammedEmpVO().get( noOfItems++ ).setGprId (  beanA.getParameter( str ) );
		}
		
		parameterList = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "GpagprId" );
		noOfItems =0;
   		for( String i :parameterList ){
			beanB.getGpaNammedEmpVO().get(  noOfItems++ ).setgpaIndex( indexofParameter(i) ) ;
   		}
   		
   		   		
	}	

	
	if( !Utils.isEmpty( src.getParameter( "gpaAggregateLimit" ) ) ){
		com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
		beanB.setAggregateLimit ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "gpaAggregateLimit" ) ) ) );
	}

		if( !Utils.isEmpty( src.getParameter( "gpaDeductible[0]" ) ) ){
		com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
		beanB.setGpaDeductible ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "gpaDeductible[0]" ) ) ) );
	}
   		/* Mapping: "gpaRiskId" -> "basicRiskId" */
		if( !Utils.isEmpty( src.getParameter( "gpaRiskId" ) ) ){
			com.rsaame.pas.cmn.converter.LongStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongStringConverter.class, "", "" );
			beanB.setBasicRiskId( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "gpaRiskId" ) ) ) );
  		}

 		/* Mapping: "policyId" -> "policyId" */
		if( !Utils.isEmpty( src.getParameter( "policyId" ) ) ){
			com.rsaame.pas.cmn.converter.LongStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongStringConverter.class, "", "" );
			beanB.setPolicyId( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "policyId" ) ) ) );
  		}
		
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 * @param allow 
	 */
	private static com.rsaame.pas.vo.bus.GroupPersonalAccidentVO initializeDeepVO(
			javax.servlet.http.HttpServletRequest beanA,
			com.rsaame.pas.vo.bus.GroupPersonalAccidentVO beanB, boolean isUnnamedEmp, boolean isNamedEmp ){
			if ( isUnnamedEmp ) {
			BeanUtils.initializeBeanField( "gpaUnnammedEmpVO[].sumInsuredDetails", beanB, HTTPUtils.getMatchingMultiReqParamKeys( beanA, "gpaDeductible[]" ).size() );
    		BeanUtils.initializeBeanField( "gpaUnnammedEmpVO[].sumInsuredDetails", beanB, HTTPUtils.getMatchingMultiReqParamKeys( beanA, "gpaUnNamedEmpCapSI[]" ).size() );
			}
    		if ( isNamedEmp ) {
    		BeanUtils.initializeBeanField( "gpaNammedEmpVO[]", beanB, HTTPUtils.getMatchingMultiReqParamKeys( beanA, "gpaTypeOfEmp[]" ).size() );
    		BeanUtils.initializeBeanField( "gpaNammedEmpVO[].sumInsuredDetails", beanB, HTTPUtils.getMatchingMultiReqParamKeys( beanA, "gpaNamedEmpCapSI[]" ).size() );
    		}
    		
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
