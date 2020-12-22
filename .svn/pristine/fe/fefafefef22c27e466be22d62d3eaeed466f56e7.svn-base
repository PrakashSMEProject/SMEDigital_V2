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
import com.rsaame.pas.vo.bus.DeteriorationOfStockDetailsVO;
import com.rsaame.pas.vo.bus.MachineDetailsVO;

/**
 * Mapper class for:<ol>
 * <li>javax.servlet.http.HttpServletRequest</li>
 * <li>com.rsaame.pas.vo.bus.MBVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( RequestToMBVOMapper.class )</code>.
 */
public class RequestToMBVOMapper extends BaseBeanToBeanMapper<javax.servlet.http.HttpServletRequest, com.rsaame.pas.vo.bus.MBVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public RequestToMBVOMapper(){
		super();
	}

	public RequestToMBVOMapper( javax.servlet.http.HttpServletRequest src, com.rsaame.pas.vo.bus.MBVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.MBVO mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.MBVO) Utils.newInstance( "com.rsaame.pas.vo.bus.MBVO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		javax.servlet.http.HttpServletRequest beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.MBVO beanB = dest;
			
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

 		/* Mapping: "coverId" -> "machineryDetails[].contents.coverId" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "coverId" );
  		index =0;
  		for( String i :noOfItems){
        			com.rsaame.pas.cmn.converter.LongStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongStringConverter.class, "", "" );
			beanB.getMachineryDetails().get(  index++ ).getContents().setCoverId ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter(i) ) ) );
 		}

 		/* Mapping: "coverVSD" -> "machineryDetails[].contents.setValidityStartDate" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "coverVSD" );
  		index =0;
  		for( String i :noOfItems){
        			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", "" );
			beanB.getMachineryDetails().get(  index++ ).getContents().setSetValidityStartDate ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter(i) ) ) );
 		}

 		/* Mapping: "mbNewReplaVal" -> "machineryDetails[].sumInsuredVO.sumInsured" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "mbNewReplaVal" );
  		index =0;
  		for( String i :noOfItems){
        			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.getMachineryDetails().get(  index++ ).getSumInsuredVO().setSumInsured ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter(i) ) ) );
 		}
   		
   		/* Mapping: "mbNewReplaVal" -> "machineryDetails[].contents.cover" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "mbNewReplaVal" );
  		index =0;
  		for( String i :noOfItems){
        			com.rsaame.pas.cmn.converter.BigDecimalStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalStringConverter.class, "", "" );
			beanB.getMachineryDetails().get(  index++ ).getContents().setCover( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter(i ) ) ) );
 		}

 		/* Mapping: "mbDropdownDeductible" -> "machineryDetails[].sumInsuredVO.deductible" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "mbDropdownDeductible" );
  		index =0;
  		for( String i :noOfItems){
        			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.getMachineryDetails().get(  index++ ).getSumInsuredVO().setDeductible ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter(i) ) ) );
 		}

 		/* Mapping: "mbTextYearofmake" -> "machineryDetails[].yearOfMake" 
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "mbTextYearofmake" );
  		index =0;
  		for( String i :noOfItems){
        			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getMachineryDetails().get(  index++ ).setYearOfMake ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter(i) ) ) );
 		}*/

 		/* Mapping: "mbDropdownMBtype" -> "machineryDetails[].machineryType" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "mbDropdownMBtype" );
  		index =0;
  		for( String i :noOfItems){
        			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getMachineryDetails().get(  index++ ).setMachineryType ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter(i) ) ) );
 		}

 		/* Mapping: "mbTextDescription" -> "machineryDetails[].machineDescription" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "mbTextDescription" );
  		index =0;
  		for( String i :noOfItems){
        			beanB.getMachineryDetails().get( index++ ).setMachineDescription (  beanA.getParameter(i) );
		}
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "coverId" );
		index = 0;
		for( String i : noOfItems ){
			beanB.getMachineryDetails().get( index++ ).setIndex( indexofParameter( i ) );
		}
   
		Iterator itr = dest.getMachineryDetails().iterator();
		MachineDetailsVO machineDetailsVO;
		while(itr.hasNext()){
			machineDetailsVO = (MachineDetailsVO)itr.next();
			if(!com.mindtree.ruc.cmn.utils.Utils.isEmpty(machineDetailsVO))
			if(machineDetailsVO.getSumInsuredVO().getSumInsured() == 0.0 && machineDetailsVO.getSumInsuredVO().getDeductible()==0.0)
				itr.remove();
			}
		
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.MBVO initializeDeepVO( javax.servlet.http.HttpServletRequest beanA, com.rsaame.pas.vo.bus.MBVO beanB ){
    		BeanUtils.initializeBeanField( "machineryDetails[].contents", beanB, HTTPUtils.getMatchingMultiReqParamKeys( beanA, "mbDropdownMBtype[]" ).size() );
     		BeanUtils.initializeBeanField( "machineryDetails[].sumInsuredVO", beanB, HTTPUtils.getMatchingMultiReqParamKeys( beanA, "mbNewReplaVal[]" ).size() );
     		BeanUtils.initializeBeanField( "machineryDetails[]", beanB, HTTPUtils.getMatchingMultiReqParamKeys( beanA, "mbTextYearofmake[]" ).size() );
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
