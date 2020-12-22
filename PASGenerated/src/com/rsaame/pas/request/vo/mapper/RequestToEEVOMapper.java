/*
* THIS IS A GENERATED CLASS. THIS CLASS SHOULD NOT BE EDITED.
*/
package com.rsaame.pas.request.vo.mapper;

import java.util.Iterator;
import java.util.List;

import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.beanmap.BeanMapperFactory;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.BeanUtils;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.HTTPUtils;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.vo.bus.EEVO;
import com.rsaame.pas.vo.bus.EquipmentVO;

/**
 * Mapper class for:<ol>
 * <li>javax.servlet.http.HttpServletRequest</li>
 * <li>com.rsaame.pas.vo.bus.EEVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( RequestToEEVOMapper.class )</code>.
 */
public class RequestToEEVOMapper extends BaseBeanToBeanMapper<javax.servlet.http.HttpServletRequest, com.rsaame.pas.vo.bus.EEVO>{
	private final Logger log = Logger.getLogger( this.getClass() );

	public RequestToEEVOMapper(){
		super();
	}

	public RequestToEEVOMapper( javax.servlet.http.HttpServletRequest src, com.rsaame.pas.vo.bus.EEVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.EEVO mapBean(){

		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}

		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.EEVO) Utils.newInstance( "com.rsaame.pas.vo.bus.EEVO" );
		}

		/* Cast the destination bean to a bean of type of BeanA */
		javax.servlet.http.HttpServletRequest beanA = src;

		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.EEVO beanB = dest;

		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO( beanA, beanB );

		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		List<String> noOfItems = null;
		Integer index = null;
		/* Mapping: "commission" -> "commission" */
		if( !Utils.isEmpty( src.getParameter( "commission" ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.setCommission( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "commission" ) ) ) );
		}

		/* Mapping: "eeDropdownEquipmenttype" -> "equipmentDtls[].equipmentType" */
		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "eeDropdownEquipmenttype" );
		index = 0;
		for( String i : noOfItems ){
			beanB.getEquipmentDtls().get( index++ ).setEquipmentType( beanA.getParameter( i ) );
		}

		/* Mapping: "eeTextNewreplacevalue" -> "equipmentDtls[].sumInsuredDetails.sumInsured" */
		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "eeTextNewreplacevalue" );
		index = 0;
		for( String i : noOfItems ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.getEquipmentDtls().get( index++ ).getSumInsuredDetails().setSumInsured( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( i ) ) ) );
		}

		/* Mapping: "eeTextEquipdes" -> "equipmentDtls[].equipmentDesc" */
		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "eeTextEquipdes" );
		index = 0;
		for( String i : noOfItems ){
			beanB.getEquipmentDtls().get( index++ ).setEquipmentDesc( beanA.getParameter( i ) );
		}

		/* Mapping: "eeDropdownDeductible" -> "equipmentDtls[].sumInsuredDetails.deductible" */
		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "eeDropdownDeductible" );
		index = 0;
		for( String i : noOfItems ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.getEquipmentDtls().get( index++ ).getSumInsuredDetails().setDeductible( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( i ) ) ) );
		}

	/*	 Mapping: "eeTextYearofmake" -> "equipmentDtls[].yearOfMake" 
		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "eeTextYearofmake" );
		index = 0;
		for( String i : noOfItems ){
			beanB.getEquipmentDtls().get( index++ ).setYearOfMake( beanA.getParameter( i ) );
		}

		 Mapping: "eeTextSerialnumber" -> "equipmentDtls[].serialNumber" 
		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "eeTextSerialnumber" );
		index = 0;
		for( String i : noOfItems ){
			beanB.getEquipmentDtls().get( index++ ).setSerialNumber( beanA.getParameter( i ) );
		}

		/* Mapping: "eeTextQuantity" -> "equipmentDtls[].quantity" 
		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "eeTextQuantity" );
		index = 0;
		for( String i : noOfItems ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getEquipmentDtls().get( index++ ).setQuantity( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( i ) ) ) );
		}
*/
		/* Mapping: "contentId" -> "equipmentDtls[].contentId" */
		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "contentId" );
		index = 0;
		for( String i : noOfItems ){
			com.rsaame.pas.cmn.converter.LongStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongStringConverter.class, "", "" );
			beanB.getEquipmentDtls().get( index++ ).setContentId( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( i ) ) ) );
		}

		/* Mapping: "rowIndex" -> "travellingEmpDets[].index" */
		/* This is basically to obtain the exact requestparameter indices and
		 * this can be done by using any of the incoming parameter.
		 * here we will use gprtId
		 */
		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "contentId" );
		index = 0;
		for( String i : noOfItems ){
			beanB.getEquipmentDtls().get( index++ ).setIndex( indexofParameter( i ) );
		}
		
		Iterator iter = dest.getEquipmentDtls().iterator();
		EquipmentVO equipmentVO;
		while(iter.hasNext()){
			equipmentVO = (EquipmentVO) iter.next();
			if(equipmentVO.getSumInsuredDetails().getSumInsured()==0.0 
					&& equipmentVO.getSumInsuredDetails().getDeductible() == 0.0){
				iter.remove();
			}
		}
		return dest;
	}

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.EEVO initializeDeepVO( javax.servlet.http.HttpServletRequest beanA, com.rsaame.pas.vo.bus.EEVO beanB ){
		BeanUtils.initializeBeanField( "equipmentDtls[]", beanB, HTTPUtils.getMatchingMultiReqParamKeys( beanA, "eeDropdownEquipmenttype[]" ).size() );
		BeanUtils.initializeBeanField( "equipmentDtls[].sumInsuredDetails", beanB, HTTPUtils.getMatchingMultiReqParamKeys( beanA, "eeTextNewreplacevalue[]" ).size() );
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
