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
 * <li>com.rsaame.pas.vo.app.BrReportSearchVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( RequestToBrReportSearchVOMapper.class )</code>.
 */
public class RequestToBrReportSearchVOMapper extends BaseBeanToBeanMapper<javax.servlet.http.HttpServletRequest, com.rsaame.pas.vo.app.BrReportSearchVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public RequestToBrReportSearchVOMapper(){
		super();
	}

	public RequestToBrReportSearchVOMapper( javax.servlet.http.HttpServletRequest src, com.rsaame.pas.vo.app.BrReportSearchVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.app.BrReportSearchVO mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.app.BrReportSearchVO) Utils.newInstance( "com.rsaame.pas.vo.app.BrReportSearchVO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		javax.servlet.http.HttpServletRequest beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.app.BrReportSearchVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;
		
		/* Mapping: "searchBrName" -> "brokerName_Code" */
		if( !Utils.isEmpty( src.getParameter( "searchBrName" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.setBrokerName_Code( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "searchBrName" ) ) ) );
  		}

 		/* Mapping: "searchBrCode" -> "brokerCode" */
		if( !Utils.isEmpty( src.getParameter( "searchBrCode" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.setBrokerCode( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "searchBrCode" ) ) ) );
  		}

 		/* Mapping: "searchBranchCode" -> "branchCode" */
		if( !Utils.isEmpty( src.getParameter( "searchBranchCode" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.setBranchCode( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "searchBranchCode" ) ) ) );
  		}

 		/* Mapping: "startDate" -> "startDate" */
		if( !Utils.isEmpty( src.getParameter( "startDate" ) ) ){
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", "format=yyyy-MM-dd" );
			beanB.setStartDate( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "startDate" ) ) ) );
  		}

 		/* Mapping: "endDate" -> "endDate" */
		if( !Utils.isEmpty( src.getParameter( "endDate" ) ) ){
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", "format=yyyy-MM-dd" );
			beanB.setEndDate( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "endDate" ) ) ) );
  		}

 		/* Mapping: "brokerSeletion" -> "byNameOrCode" */
		if( !Utils.isEmpty( src.getParameter( "brokerSeletion" ) ) ){
 			beanB.setByNameOrCode( beanA.getParameter( "brokerSeletion" ) ); 
 		}

 		/* Mapping: "searchType" -> "allOrUnmatched" */
		if( !Utils.isEmpty( src.getParameter( "searchType" ) ) ){
 			beanB.setAllOrUnmatched( beanA.getParameter( "searchType" ) ); 
 		}

		/* Mapping: "transLOB" -> "lob" */
		if( !Utils.isEmpty( src.getParameter( "transLOB" ) ) ){
			beanB.setLob( beanA.getParameter( "transLOB" ) ); 
  		}
		/* Mapping: "policyNo" -> "policyNo" */
		if( !Utils.isEmpty( src.getParameter( "accPolicyNo" ) ) ){
 			beanB.setPolicyNo( beanA.getParameter( "accPolicyNo" ) ); 
 		}
   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.app.BrReportSearchVO initializeDeepVO( javax.servlet.http.HttpServletRequest beanA, com.rsaame.pas.vo.app.BrReportSearchVO beanB ){
               		return beanB;
	}
}
