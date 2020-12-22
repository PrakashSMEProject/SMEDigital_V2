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
 * <li>com.rsaame.pas.vo.bus.FidelityVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( RequestToFidelityVOMapper.class )</code>.
 */
public class RequestToFidelityVOMapper extends BaseBeanToBeanMapper<javax.servlet.http.HttpServletRequest, com.rsaame.pas.vo.bus.FidelityVO>{
	private static final String NAMED_EMP = "namedfidelity";
	private static final String UN_NAMED_EMP = "unNamedfidelity";

	private static final String ON = "on";
	private final Logger log = Logger.getLogger( this.getClass() );

	public RequestToFidelityVOMapper(){
		super();
	}

	public RequestToFidelityVOMapper( javax.servlet.http.HttpServletRequest src, com.rsaame.pas.vo.bus.FidelityVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.FidelityVO mapBean(){

		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}

		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.FidelityVO) Utils.newInstance( "com.rsaame.pas.vo.bus.FidelityVO" );
		}

		/* Cast the destination bean to a bean of type of BeanA */
		javax.servlet.http.HttpServletRequest beanA = src;

		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.FidelityVO beanB = dest;
		boolean isUnNamedEmployee = false;
		boolean isNamedEmployee = false;
		if( !Utils.isEmpty( beanA.getParameter( UN_NAMED_EMP ) ) && beanA.getParameter( UN_NAMED_EMP ).equals( ON ) ){
			isUnNamedEmployee = true;
		}

		if( !Utils.isEmpty( beanA.getParameter( NAMED_EMP ) ) && beanA.getParameter( NAMED_EMP ).equals( ON ) ){
			isNamedEmployee = true;
		}

		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO( beanA, beanB, isNamedEmployee, isUnNamedEmployee );
		
		/* Mapping: "commission" -> "commission" */
		if( !Utils.isEmpty( src.getParameter( "commission" ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.setCommission( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "commission" ) ) ) );
  		}

		/* Mapping: "fidelityAggregateLimit" -> "aggregateLimit" */
		if( !Utils.isEmpty( src.getParameter( "fidelityAggregateLimit" ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.setAggregateLimit( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "fidelityAggregateLimit" ) ) ) );
		}

		/* Mapping: "fidelityDeductible" -> "deductible" */
		if( !Utils.isEmpty( src.getParameter( "fidelityDeductible" ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.setDeductible( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "fidelityDeductible" ) ) ) );
		}

		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		List<String> noOfItemsGpr = null;
		Integer index = null;
		if( isNamedEmployee ){
			/* Mapping: "fidelityName" -> "fidelityEmployeeDetails[].empName" */
			noOfItemsGpr = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "fidelityName" );
			index = 0;
			for( String i : noOfItemsGpr ){
				beanB.getFidelityEmployeeDetails().get( index++ ).setEmpName( beanA.getParameter( i ) );
			}

			/* Mapping: "fidelityDesignation" -> "fidelityEmployeeDetails[].empDesignation" */
			noOfItemsGpr = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "fidelityDesignation" );
			index = 0;
			for( String i : noOfItemsGpr ){
				beanB.getFidelityEmployeeDetails().get( index++ ).setEmpDesignation( beanA.getParameter( i ) );
			}

			/* Mapping: "fidelitySpecificLimit" -> "fidelityEmployeeDetails[].limitPerPerson" */
			noOfItemsGpr = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "fidelitySpecificLimit" );
			index = 0;
			for( String i : noOfItemsGpr ){
				com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
				beanB.getFidelityEmployeeDetails().get( index++ ).setLimitPerPerson( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( i ) ) ) );
			}

			/* Mapping: "fidelityEmpType" -> "fidelityEmployeeDetails[].empType" */
			noOfItemsGpr = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "fidelityEmpType" );
			index = 0;
			for( String i : noOfItemsGpr ){
				com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
				beanB.getFidelityEmployeeDetails().get( index++ ).setEmpType( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( i ) ) ) );
			}

			/* Mapping: "gprFidelityId" -> "fidelityEmployeeDetails[].gprFidelity" */
			noOfItemsGpr = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "gprFidelityId" );
			index = 0;
			for( String i : noOfItemsGpr ){
				com.rsaame.pas.cmn.converter.LongStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongStringConverter.class, "", "" );
				beanB.getFidelityEmployeeDetails().get( index++ ).setGprFidelityId( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( i ) ) ) );
			}

			noOfItemsGpr = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "gprFidelityId" );
			index = 0;
			for( String i : noOfItemsGpr ){
				beanB.getFidelityEmployeeDetails().get( index++ ).setIndex( indexofParameter( i ) );
			}
		}
		List<String> noOfItemsGup = null;

		if( isUnNamedEmployee ){
			/* Mapping: "fidelityEmpTypeUnnamed" -> "unnammedEmployeeDetails[].empType" */
			noOfItemsGup = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "fidelityEmpTypeUnnamed" );
			index = 0;
			for( String i : noOfItemsGup ){
				com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
				beanB.getUnnammedEmployeeDetails().get( index++ ).setEmpType( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( i ) ) ) );
			}

			/* Mapping: "fidelityEmpCnt" -> "unnammedEmployeeDetails[].totalNumberOfEmployee" */
			noOfItemsGup = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "fidelityEmpCnt" );
			index = 0;
			for( String i : noOfItemsGup ){
				com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
				beanB.getUnnammedEmployeeDetails().get( index++ ).setTotalNumberOfEmployee( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( i ) ) ) );
			}

			/* Mapping: "fidelitySpecificLimitUnnamed" -> "unnammedEmployeeDetails[].limitPerPerson" */
			noOfItemsGup = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "fSpUnnamed" );
			index = 0;
			for( String i : noOfItemsGup ){
				com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
				beanB.getUnnammedEmployeeDetails().get( index++ ).setLimitPerPerson( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( i ) ) ) );
			}

			/* Mapping: "gupFidelityId" -> "unnammedEmployeeDetails[].gupFidelity" */
			noOfItemsGup = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "gupFidelityId" );
			index = 0;
			for( String i : noOfItemsGup ){
				com.rsaame.pas.cmn.converter.LongStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongStringConverter.class, "", "" );
				beanB.getUnnammedEmployeeDetails().get( index++ ).setGupFidelityId( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( i ) ) ) );
			}
			
			noOfItemsGup = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "gupFidelityId" );
			index = 0;
			for( String i : noOfItemsGup ){
				beanB.getUnnammedEmployeeDetails().get( index++ ).setIndex( indexofParameter( i ) );
			}
		}
		return dest;
	}

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 * @param isUnNamedEmployee 
	 * @param isNamedEmployee 
	 */
	private static com.rsaame.pas.vo.bus.FidelityVO initializeDeepVO( javax.servlet.http.HttpServletRequest beanA, com.rsaame.pas.vo.bus.FidelityVO beanB, boolean isNamedEmployee,
			boolean isUnNamedEmployee ){

		if( isNamedEmployee ){
			BeanUtils.initializeBeanField( "fidelityEmployeeDetails[]", beanB, HTTPUtils.getMatchingMultiReqParamKeys( beanA, "fidelityName[]" ).size() );
		}
		if( isUnNamedEmployee ){
			BeanUtils.initializeBeanField( "unnammedEmployeeDetails[]", beanB, HTTPUtils.getMatchingMultiReqParamKeys( beanA, "fidelityEmpTypeUnnamed[]" ).size() );
		}

		return beanB;
	}

	private int indexofParameter( String stringParameter ){

		if( Utils.isEmpty( stringParameter ) || !stringParameter.contains( "[" ) ) return -999;
		if( !Utils.isEmpty( stringParameter ) ){
			String split[] = stringParameter.split( "\\[" );
			String tempString[] = split[ 1 ].split( "\\]" );
			return ( Integer.valueOf( tempString[ 0 ] ) );
		}
		return -999;
	}
}
