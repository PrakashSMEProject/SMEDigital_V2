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
 * <li>com.rsaame.pas.vo.bus.PublicLiabilityVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( RequestToPublicLiabilityVOMapper.class )</code>.
 */
public class RequestToPublicLiabilityVOMapper extends BaseBeanToBeanMapper<javax.servlet.http.HttpServletRequest, com.rsaame.pas.vo.bus.PublicLiabilityVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public RequestToPublicLiabilityVOMapper(){
		super();
	}

	public RequestToPublicLiabilityVOMapper( javax.servlet.http.HttpServletRequest src, com.rsaame.pas.vo.bus.PublicLiabilityVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.PublicLiabilityVO mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.PublicLiabilityVO) Utils.newInstance( "com.rsaame.pas.vo.bus.PublicLiabilityVO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		javax.servlet.http.HttpServletRequest beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.PublicLiabilityVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);

		/* Mapping: com.Constant.CONST_PLANNUALTO -> "sumInsuredDets.sumInsured" */
		if( !Utils.isEmpty( src.getParameter( com.Constant.CONST_PLANNUALTO ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.getSumInsuredDets().setSumInsured( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( com.Constant.CONST_PLANNUALTO ) ) ) );
  		}
		
		/* Mapping: com.Constant.CONST_PLANNUALTO -> "sumInsuredDets.sumInsured" */
		if( !Utils.isEmpty( src.getParameter( com.Constant.CONST_PLANNUALTO ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.setSumInsured( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( com.Constant.CONST_PLANNUALTO ) ) ) );
  		}
		
		

 		/* Mapping: "plDeductible" -> "sumInsuredDets.deductible" */
		if( !Utils.isEmpty( src.getParameter( "plDeductible" ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.getSumInsuredDets().setDeductible( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "plDeductible" ) ) ) );
  		}

 		/* Mapping: "plLimitOfIndem" -> "indemnityAmtLimit" */
		if( !Utils.isEmpty( src.getParameter( "plLimitOfIndem" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.setIndemnityAmtLimit( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "plLimitOfIndem" ) ) ) );
  		}

 		/* Mapping: "plSumInsBasis" -> "sumInsuredBasis" */
		if( !Utils.isEmpty( src.getParameter( "plSumInsBasis" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.setSumInsuredBasis( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "plSumInsBasis" ) ) ) );
  		}

 		/* Mapping: "commission" -> "commission" */
		if( !Utils.isEmpty( src.getParameter( "commission" ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.setCommission( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "commission" ) ) ) );
  		}

		/* Mapping: com.Constant.CONST_RISKGROUPID -> com.Constant.CONST_RISKGROUPID */
		if( !Utils.isEmpty( src.getParameter( com.Constant.CONST_RISKGROUPID ) ) ){
			beanB.setWbdId( Long.valueOf( beanA.getParameter( com.Constant.CONST_RISKGROUPID ) ));
  		}
   
		if( !Utils.isEmpty( src.getParameter( com.Constant.CONST_RISKGROUPID ) ) ){
			beanB.setBasicRiskId( Long.valueOf( beanA.getParameter( com.Constant.CONST_RISKGROUPID ) ));
  		}
		
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.PublicLiabilityVO initializeDeepVO( javax.servlet.http.HttpServletRequest beanA, com.rsaame.pas.vo.bus.PublicLiabilityVO beanB ){
  		BeanUtils.initializeBeanField( "sumInsuredDets", beanB );
          		return beanB;
	}
}
