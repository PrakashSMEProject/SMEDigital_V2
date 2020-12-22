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
import com.rsaame.pas.vo.bus.WCVO;

/**
 * Mapper class for:<ol>
 * <li>javax.servlet.http.HttpServletRequest</li>
 * <li>com.rsaame.pas.vo.bus.WCVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( RequestToWCVOMapper.class )</code>.
 */
public class RequestToWCVOMapper extends BaseBeanToBeanMapper<javax.servlet.http.HttpServletRequest, com.rsaame.pas.vo.bus.WCVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	
	 
	public RequestToWCVOMapper(){
		super();
	}

	public RequestToWCVOMapper( javax.servlet.http.HttpServletRequest src, com.rsaame.pas.vo.bus.WCVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.WCVO mapBean(){
		Integer  size = 0;
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.WCVO) Utils.newInstance( "com.rsaame.pas.vo.bus.WCVO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		javax.servlet.http.HttpServletRequest beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.WCVO beanB = dest;
		
		
		if(!Utils.isEmpty( beanA.getSession().getAttribute(com.Constant.CONST_CURRRGD))){
			
			if(!Utils.isEmpty( (beanA.getSession().getAttribute(com.Constant.CONST_CURRRGD))) && !Utils.isEmpty( ((WCVO)beanA.getSession().getAttribute(com.Constant.CONST_CURRRGD)).getWcEmployeeDetails())){
				
				size = ((WCVO)beanA.getSession().getAttribute(com.Constant.CONST_CURRRGD)).getWcEmployeeDetails().size();
			}
		}
		
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB,size);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;
		
		/* Mapping: "commission" -> "commission" */
		if( !Utils.isEmpty( src.getParameter( "commission" ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.setCommission( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "commission" ) ) ) );
  		}

 		/* Mapping: "WC_dropdown_employeeType" -> "empTypeDetails[].empType" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "WC_dropdown_employeeType" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getEmpTypeDetails().get( i ).setEmpType ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "WC_dropdown_employeeType[" + i + "]" ) ) ) );
 		}

 		/* Mapping: "quote_name_noOfEmployee" -> "empTypeDetails[].noOfEmp" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "quote_name_noOfEmployee" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getEmpTypeDetails().get( i ).setNoOfEmp ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_name_noOfEmployee[" + i + "]" ) ) ) );
 		}

 		/* Mapping: "quote_name_annualWageroll" -> "empTypeDetails[].wageroll" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "quote_name_annualWageroll" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.getEmpTypeDetails().get( i ).setWageroll ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "quote_name_annualWageroll[" + i + "]" ) ) ) );
 		}

 		/* Mapping: "WC_dropdown_deductible" -> "empTypeDetails[].deductibles" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "WC_dropdown_deductible" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			com.rsaame.pas.cmn.converter.BigDecimalStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalStringConverter.class, "", "" );
			beanB.getEmpTypeDetails().get( i ).setDeductibles ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "WC_dropdown_deductible[" + i + "]" ) ) ) );
 		}

 		/* Mapping: "WC_dropdown_limit" -> "empTypeDetails[].limit" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "WC_dropdown_limit" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			com.rsaame.pas.cmn.converter.BigDecimalStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalStringConverter.class, "", "" );
			beanB.getEmpTypeDetails().get( i ).setLimit ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "WC_dropdown_limit[" + i + "]" ) ) ) );
 		}

 		/* Mapping: "wcriskid" -> "empTypeDetails[].riskId" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "wcriskid" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			com.rsaame.pas.cmn.converter.LongStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongStringConverter.class, "", "" );
			beanB.getEmpTypeDetails().get( i ).setRiskId ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "wcriskid[" + i + "]" ) ) ) );
 		}

 		/* Mapping: "24HourPACoverHiddenValue" -> "wcCovers.PACover" */
		if( !Utils.isEmpty( src.getParameter( "24HourPACoverHiddenValue" ) ) ){
			com.mindtree.ruc.cmn.beanmap.BooleanStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.BooleanStringConverter.class, "", "" );
			beanB.getWcCovers().setPACover( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "24HourPACoverHiddenValue" ) ) ) );
  		}
		
		Integer index = null;
		/* Mapping: "wcEmpName" -> "wcEmployeeDetails[].empName" */
		if(!Utils.isEmpty( beanA.getSession().getAttribute(com.Constant.CONST_CURRRGD))){
			WCVO wcVO = (WCVO) beanA.getSession().getAttribute(com.Constant.CONST_CURRRGD);
			if(!Utils.isEmpty( wcVO.getWcEmployeeDetails())){
				
				//Integer  size = wcVO.getWcEmployeeDetails().size();
				index = 0;
				for( int i = 0; i < size ; i++){
					beanB.getWcEmployeeDetails().get( index++ ).setEmpName( wcVO.getWcEmployeeDetails().get(i).getEmpName() );
				}
			}
		}
		
		/* Mapping: "wprWCID" -> "wcEmployeeDetails[].wprWCID" */
		if(!Utils.isEmpty( beanA.getSession().getAttribute(com.Constant.CONST_CURRRGD))){
			WCVO wcVO = (WCVO) beanA.getSession().getAttribute(com.Constant.CONST_CURRRGD);
			if(!Utils.isEmpty( wcVO.getWcEmployeeDetails())){
				
				index = 0;
				for( int i = 0; i < size ; i++){
					beanB.getWcEmployeeDetails().get( index++ ).setWprWCId( wcVO.getWcEmployeeDetails().get(i).getWprWCId() );
				}
			}
		}
		
		 beanA.getSession().removeAttribute(com.Constant.CONST_CURRRGD);
			

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 * @param size 
	 */
	private static com.rsaame.pas.vo.bus.WCVO initializeDeepVO( javax.servlet.http.HttpServletRequest beanA, com.rsaame.pas.vo.bus.WCVO beanB, Integer size ){
    		BeanUtils.initializeBeanField( "empTypeDetails[]", beanB, HTTPUtils.getMatchingMultiReqParamKeys( beanA, "WC_dropdown_employeeType[]" ).size() );
    		BeanUtils.initializeBeanField( "wcEmployeeDetails[]", beanB, size );
             		BeanUtils.initializeBeanField( "wcCovers", beanB );
  		return beanB;
	}
}
