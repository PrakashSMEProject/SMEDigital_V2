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
 * <li>com.rsaame.pas.vo.app.ReportsSearchVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( RequestToSearchClassWisePrmVOMapper.class )</code>.
 */
public class RequestToSearchClassWisePrmVOMapper extends BaseBeanToBeanMapper<javax.servlet.http.HttpServletRequest, com.rsaame.pas.vo.app.ReportsSearchVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public RequestToSearchClassWisePrmVOMapper(){
		super();
	}

	public RequestToSearchClassWisePrmVOMapper( javax.servlet.http.HttpServletRequest src, com.rsaame.pas.vo.app.ReportsSearchVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.app.ReportsSearchVO mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.app.ReportsSearchVO) Utils.newInstance( "com.rsaame.pas.vo.app.ReportsSearchVO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		javax.servlet.http.HttpServletRequest beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.app.ReportsSearchVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;
		
		/* Mapping: "classPrmFromDate" -> "startDate" */
		if( !Utils.isEmpty( src.getParameter( "classPrmFromDate" ) ) ){
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", com.Constant.CONST_FORMAT_YYYY_MM_DD );
			beanB.setStartDate( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "classPrmFromDate" ) ) ) );
  		}

 		/* Mapping: "classPrmToDate" -> "endDate" */
		if( !Utils.isEmpty( src.getParameter( "classPrmToDate" ) ) ){
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", com.Constant.CONST_FORMAT_YYYY_MM_DD );
			beanB.setEndDate( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "classPrmToDate" ) ) ) );
  		}

 		/* Mapping: "classPrmBrName" -> "brokerName_Code" */
		if( !Utils.isEmpty( src.getParameter( "classPrmBrName" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.setBrokerName_Code( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "classPrmBrName" ) ) ) );
  		}
		
		
		/* Mapping: "livePolBrCodeLst" -> "brokerCodes" */
		if( !Utils.isEmpty(src.getParameterValues( "classPrmBrLst" )) ){
			
			String[] list = src.getParameterValues( "classPrmBrLst" );
			Integer[] intList = new Integer[list.length];
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			for(int i=0; i<list.length ;i++){
				intList[i] =  converter.getTypeOfA().cast( converter.getAFromB(list[i]) ) ;
			}
			
			beanB.setBrokerCodeList(intList);
  		}
		
		/* Mapping: "livePolBrCodeLst" -> "brokerCodes" */
		if( !Utils.isEmpty(src.getParameterValues( "livePolBrCodeLst" )) ){
			
			String[] list = src.getParameterValues( "livePolBrCodeLst" );
			Integer[] intList = new Integer[list.length];
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			for(int i=0; i<list.length ;i++){
				intList[i] =  converter.getTypeOfA().cast( converter.getAFromB(list[i]) ) ;
			}
			
			beanB.setBrokerCodeList(intList);
  		}
		
		/* Mapping: "livePolBrCode" -> "brokerCode" */
		if( !Utils.isEmpty( src.getParameter( "livePolBrCode" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.setBrokerCode( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "livePolBrCode" ) ) ) );
  		}

 		/* Mapping: "livePolFromDate" -> "startDate" */
		if( !Utils.isEmpty( src.getParameter( "livePolFromDate" ) ) ){
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", com.Constant.CONST_FORMAT_YYYY_MM_DD );
			beanB.setStartDate( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "livePolFromDate" ) ) ) );
  		}

 		/* Mapping: "livePolToDate" -> "endDate" */
		if( !Utils.isEmpty( src.getParameter( "livePolToDate" ) ) ){
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", com.Constant.CONST_FORMAT_YYYY_MM_DD );
			beanB.setEndDate( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "livePolToDate" ) ) ) );
  		}
		
 		/* Mapping: "transBranch" -> "branchCode" */
		if( !Utils.isEmpty( src.getParameter( "transBranch" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.setBranchCode( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "transBranch" ) ) ) );
  		}
		
		/* Mapping: "startDate" -> "startDate" */
		if( !Utils.isEmpty( src.getParameter( "startDate" ) ) ){
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", com.Constant.CONST_FORMAT_YYYY_MM_DD );
			beanB.setStartDate( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "startDate" ) ) ) );
  		}

 		/* Mapping: "endDate" -> "endDate" */
		if( !Utils.isEmpty( src.getParameter( "endDate" ) ) ){
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", com.Constant.CONST_FORMAT_YYYY_MM_DD );
			beanB.setEndDate( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "endDate" ) ) ) );
  		}
		
		/* Mapping: "promocode" -> "promocode" */
		if( !Utils.isEmpty( src.getParameter( "promoCode" ) ) ){
			beanB.setPromoCode(  beanA.getParameter( "promoCode" ));
  		}

		/* Mapping: "businessLine" -> "DistributionChannel" */
		if( !Utils.isEmpty( src.getParameter( "businessLine" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.setdistributionChannel( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "businessLine" ) ) ) );
  		}
		
		/* Mapping: "transLOB" -> "lob" */
		if( !Utils.isEmpty( src.getParameter( "transLOB" ) ) ){
			beanB.setLob( beanA.getParameter( "transLOB" ) ); 
  		}

		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.app.ReportsSearchVO initializeDeepVO( javax.servlet.http.HttpServletRequest beanA, com.rsaame.pas.vo.app.ReportsSearchVO beanB ){
       		return beanB;
	}
}
