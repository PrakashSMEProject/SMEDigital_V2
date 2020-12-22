       /*
 * THIS IS A GENERATED CLASS. THIS CLASS SHOULD NOT BE EDITED.
 */
package com.rsaame.pas.request.vo.mapper;

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
 * <li>com.rsaame.pas.vo.bus.BIVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( RequestToBIVOMapper.class )</code>.
 */
public class RequestToBIVOMapper extends BaseBeanToBeanMapper<javax.servlet.http.HttpServletRequest, com.rsaame.pas.vo.bus.BIVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public RequestToBIVOMapper(){
		super();
	}

	public RequestToBIVOMapper( javax.servlet.http.HttpServletRequest src, com.rsaame.pas.vo.bus.BIVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.BIVO mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.BIVO) Utils.newInstance( "com.rsaame.pas.vo.bus.BIVO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		javax.servlet.http.HttpServletRequest beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.BIVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;
		
		/* Mapping: "commission" -> "commission" */
		if( !Utils.isEmpty( src.getParameter( "commission" ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.setCommission( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "commission" ) ) ) );
  		}

 		/* Mapping: "indemnity_period" -> "indemnityPeriod" */
		if( !Utils.isEmpty( src.getParameter( "indemnity_period" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.setIndemnityPeriod( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "indemnity_period" ) ) ) );
  		}

 		/* Mapping: "inmPeriodDed" -> "deductible" */
		if( !Utils.isEmpty( src.getParameter( "inmPeriodDed" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.setDeductible( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "inmPeriodDed" ) ) ) );
  		}

 		/* Mapping: "grossIncomeOfBusiness" -> "estimatedGrossIncome" */
		if( !Utils.isEmpty( src.getParameter( "grossIncomeOfBusiness" ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.setEstimatedGrossIncome( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "grossIncomeOfBusiness" ) ) ) );
  		}

 		/* Mapping: "costOfWorkingLimit" -> "workingLimit" */
		if( !Utils.isEmpty( src.getParameter( "costOfWorkingLimit" ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.setWorkingLimit( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "costOfWorkingLimit" ) ) ) );
  		}

 		/* Mapping: "rentReceivable" -> "rentRecievable" */
		if( !Utils.isEmpty( src.getParameter( "rentReceivable" ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.setRentRecievable( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "rentReceivable" ) ) ) );
  		}

 		/* Mapping: "biRiskId" -> "basicRiskId" */
		if( !Utils.isEmpty( src.getParameter( "biRiskId" ) ) ){
			com.rsaame.pas.cmn.converter.LongStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongStringConverter.class, "", "" );
			beanB.setBasicRiskId( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "biRiskId" ) ) ) );
  		}

 		/* Mapping: "policyId" -> "policyId" */
		if( !Utils.isEmpty( src.getParameter( "policyId" ) ) ){
			com.rsaame.pas.cmn.converter.LongStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongStringConverter.class, "", "" );
			beanB.setPolicyId( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "policyId" ) ) ) );
  		}

 		/* Mapping: "biCwsAcwlId" -> "biCwsAcwlId" */
		if( !Utils.isEmpty( src.getParameter( "biCwsAcwlId" ) ) ){
			com.rsaame.pas.cmn.converter.LongStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongStringConverter.class, "", "" );
			beanB.setBiCwsAcwlId( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "biCwsAcwlId" ) ) ) );
  		}

 		/* Mapping: "biCwsRentId" -> "biCwsRentId" */
		if( !Utils.isEmpty( src.getParameter( "biCwsRentId" ) ) ){
			com.rsaame.pas.cmn.converter.LongStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongStringConverter.class, "", "" );
			beanB.setBiCwsRentId( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "biCwsRentId" ) ) ) );
  		}

 		/* Mapping: "biCwsEGIncomeId" -> "biCwsEGIncomeId" */
		if( !Utils.isEmpty( src.getParameter( "biCwsEGIncomeId" ) ) ){
			com.rsaame.pas.cmn.converter.LongStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongStringConverter.class, "", "" );
			beanB.setBiCwsEGIncomeId( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "biCwsEGIncomeId" ) ) ) );
  		}
		// Added for Adventnet Id:103286;To Move BI Section from PAR to BI 
		//Commented requirement on Annual Rent to be moved to BI as requirement need not to be supported in 3.7
		/* Mapping: "annualRent" -> "annualRent" */
		/*if( !Utils.isEmpty( src.getParameter( "annualRent" ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.setAnnualRent( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "annualRent" ) ) ) );
  		}
		 Mapping: "biCwsAnnualRentId" -> "biCwsAnnualRentId" 
		if( !Utils.isEmpty( src.getParameter( "biCwsAnnualRentId" ) ) ){
			com.rsaame.pas.cmn.converter.LongStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongStringConverter.class, "", "" );
			beanB.setBiCwsAnnualRentId( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "biCwsAnnualRentId" ) ) ) );
  		}*/
		// Added END for Adventnet Id:103286
   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.BIVO initializeDeepVO( javax.servlet.http.HttpServletRequest beanA, com.rsaame.pas.vo.bus.BIVO beanB ){
                       		return beanB;
	}
}
