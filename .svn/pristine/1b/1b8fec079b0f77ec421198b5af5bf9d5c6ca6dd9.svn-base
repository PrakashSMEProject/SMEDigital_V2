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
 * <li>com.rsaame.pas.vo.bus.TravelPackageVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( RequestToPackageVOMapper.class )</code>.
 */
public class RequestToPackageVOMapper extends BaseBeanToBeanMapper<javax.servlet.http.HttpServletRequest, com.rsaame.pas.vo.bus.TravelPackageVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	
	private final String STATUS_ON = "on";
	
	
	public RequestToPackageVOMapper(){
		super();
	}

	public RequestToPackageVOMapper( javax.servlet.http.HttpServletRequest src, com.rsaame.pas.vo.bus.TravelPackageVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.TravelPackageVO mapBean(){
		
		Integer tariff = null;
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.TravelPackageVO) Utils.newInstance( "com.rsaame.pas.vo.bus.TravelPackageVO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		javax.servlet.http.HttpServletRequest beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.TravelPackageVO beanB = dest;
		
		tariff = Integer.parseInt( src.getParameter( "tariffCode" ));
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB, tariff);
		
		/*Manually added code.*/
		beanB.setIsSelected( Boolean.TRUE );
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;
		
		/* Mapping: "coverCode" -> "covers[].coverCodes.covCode" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, com.Constant.CONST_COVERCODE_END+tariff ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			com.rsaame.pas.cmn.converter.ShortStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.ShortStringConverter.class, com.Constant.CONST_BPROPS, com.Constant.CONST_APROPS );
			beanB.getCovers().get( i ).getCoverCodes().setCovCode ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( com.Constant.CONST_COVERCODE_END+tariff+"[" + i + "]" ) ) ) );
 		}

 		/* Mapping: "CovSubTypeCode" -> "covers[].coverCodes.covSubTypeCode" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "coverSubTypeCode_"+tariff ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			com.rsaame.pas.cmn.converter.ShortStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.ShortStringConverter.class, com.Constant.CONST_BPROPS, com.Constant.CONST_APROPS );
			beanB.getCovers().get( i ).getCoverCodes().setCovSubTypeCode ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "coverSubTypeCode_"+tariff+"[" + i + "]" ) ) ) );
 		}

 		/* Mapping: "CovTypeCode" -> "covers[].coverCodes.covTypeCode" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "coverTypeCode_"+tariff ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			com.rsaame.pas.cmn.converter.ShortStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.ShortStringConverter.class, com.Constant.CONST_BPROPS, com.Constant.CONST_APROPS );
			beanB.getCovers().get( i ).getCoverCodes().setCovTypeCode ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "coverTypeCode_"+tariff+"[" + i + "]" ) ) ) );
 		}

 		/* Mapping: "RiskCode" -> "covers[].riskCodes.riskCode" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "riskCode_"+tariff ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, com.Constant.CONST_BPROPS, com.Constant.CONST_APROPS );
			beanB.getCovers().get( i ).getRiskCodes().setRiskCode ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "riskCode_"+tariff+"[" + i + "]" ) ) ) );
 		}

 		/* Mapping: "BasicRskCode" -> "covers[].riskCodes.basicRskCode" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "basicRskCode_"+tariff ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, com.Constant.CONST_BPROPS, com.Constant.CONST_APROPS );
			beanB.getCovers().get( i ).getRiskCodes().setBasicRskCode ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "basicRskCode_"+tariff+"[" + i + "]" ) ) ) );
 		}

 		/* Mapping: "riskCat" -> "covers[].riskCodes.riskCat" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "riskCat_"+tariff ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, com.Constant.CONST_BPROPS, com.Constant.CONST_APROPS );
			beanB.getCovers().get( i ).getRiskCodes().setRiskCat ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "riskCat_"+tariff+"[" + i + "]" ) ) ) );
 		}

 		/* Mapping: "riskType" -> "covers[].riskCodes.riskType" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "riskType_"+tariff ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, com.Constant.CONST_BPROPS, com.Constant.CONST_APROPS );
			beanB.getCovers().get( i ).getRiskCodes().setRiskType ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "riskType_"+tariff+"[" + i + "]" ) ) ) );
 		}

 		/* Mapping: "coverSI" -> "covers[].sumInsured.sumInsured" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, com.Constant.CONST_COVERCODE_END+tariff ).size();//TODO HTTPUtils.getMatchingMultiReqParamKeys( beanA, "coverSI_"+tariff ).size();
   		for( int i = 0; i < noOfItems; i++ ){
   			String coverSI = beanA.getParameter( "coverSI_"+tariff+"[" + i + "]" );
   			
   			if( Utils.isEmpty( coverSI ) ){
   				coverSI = "0";
   			}else if( coverSI.equalsIgnoreCase( STATUS_ON ) ){
   				/*coverSI = "1";*/
   				coverSI = beanA.getParameter( "checkBoxSIVal_"+tariff+"[" + i + "]" );
   				beanB.getCovers().get( i ).setIsCovered( STATUS_ON );
   			}
   			
   			if(Utils.getSingleValueAppConfig("DEPLOYED_LOCATION").equalsIgnoreCase("30")){
	   			if((beanB.getCovers().get( i ).getCoverCodes().getCovCode() == Short.valueOf(Utils.getSingleValueAppConfig( "PA_COVER_CODE" )) &&
	   			   coverSI.equals(Utils.getSingleValueAppConfig( "PA_OPTION2_SI"))) || beanB.getCovers().get( i ).getCoverCodes().getCovCode() == Short.valueOf(Utils.getSingleValueAppConfig( "COMPASSIONATE_VISIT_COVER_CODE" ))){
	   				beanB.getCovers().get( i ).setIsCovered( STATUS_ON );
	   			}
   			}
   			
   			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, com.Constant.CONST_BPROPS, com.Constant.CONST_APROPS );
			beanB.getCovers().get( i ).getSumInsured().setSumInsured ( converter.getTypeOfA().cast( converter.getAFromB( coverSI ) ) );
 		}
   		
   		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, com.Constant.CONST_COVERCODE_END+tariff ).size();//TODO HTTPUtils.getMatchingMultiReqParamKeys( beanA, "coverSI_"+tariff ).size();
   		for( int i = 0; i < noOfItems; i++ ){
   			String coverDeductible = beanA.getParameter( "coverDeductible_"+tariff+"[" + i + "]" );
   			
   			if( Utils.isEmpty( coverDeductible ) ){
   				coverDeductible = "0";
   			}
   			
   			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, com.Constant.CONST_BPROPS, com.Constant.CONST_APROPS );
   			beanB.getCovers().get( i ).getSumInsured().setDeductible(converter.getTypeOfA().cast( converter.getAFromB( coverDeductible ) ) );
   		}
 
   		/* Mapping: "aDesc" -> "covers[].sumInsured.aDesc" */
   		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "aDesc_"+tariff ).size();
   		for( int i = 0; i < noOfItems; i++ ){
   			beanB.getCovers().get( i ).getSumInsured().setaDesc( beanA.getParameter( "aDesc_"+tariff+"[" + i + "]" ) );
   		}
   		
 /** Keeping the code commented as it would be required while quote versioning changes.
 * Not to be deleted now - Satish  		
   		 Mapping: "basicRskId" -> "covers[].riskCodes.basicRskId" */
  		/*noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "basicRiskId_"+tariff ).size();
   		for( int i = 0; i < noOfItems; i++ ){
   			if( !Utils.isEmpty( (String)beanA.getParameter( "basicRiskId_"+tariff+"[" + i + "]" ) )){
   				com.rsaame.pas.cmn.converter.BigDecimalStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalStringConverter.class, com.Constant.CONST_BPROPS, com.Constant.CONST_APROPS );
   				beanB.getCovers().get( i ).getRiskCodes().setBasicRskId( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "basicRiskId_"+tariff+"[" + i + "]" ) ) ) );
   			}
        			
 		}

   		// Mapping: "riskId" -> "covers[].riskCodes.rskId" 
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "riskId_"+tariff ).size();
   		for( int i = 0; i < noOfItems; i++ ){
   			if( !Utils.isEmpty( (String)beanA.getParameter( "riskId_"+tariff+"[" + i + "]" ) )){
   				com.rsaame.pas.cmn.converter.BigDecimalStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalStringConverter.class, com.Constant.CONST_BPROPS, com.Constant.CONST_APROPS );
   				beanB.getCovers().get( i ).getRiskCodes().setRskId ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "riskId_"+tariff+"[" + i + "]" ) ) ) );
   			}
        			
 		}
*/
   		// Mapping: "vsd" -> "covers[].riskCodes.vsd" 
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "vsd_"+tariff ).size();
   		for( int i = 0; i < noOfItems; i++ ){
   			if( !Utils.isEmpty( (String)beanA.getParameter( "vsd_"+tariff+"[" + i + "]" ) )){
   				if(!beanA.getParameter( "vsd_"+tariff+"[" + i + "]" ).equals("null"))
   				{
   				com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", "format=yyyy-MM-dd HH:mm:ss.SSS" );
   				beanB.getCovers().get( i ).setVsd( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "vsd_"+tariff+"[" + i + "]" ) ) ) );
   				}
   			}
   			
 		}
   		
   		
   		/* Mapping: "promoDiscount" -> "premiumVO.discOrLoadAmt" */
		if( !Utils.isEmpty( src.getParameter( "promoDiscount" ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, com.Constant.CONST_BPROPS, com.Constant.CONST_APROPS );
			beanB.setPromoDiscPerc( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "promoDiscount" ) ) ) );
  		}
   		
   		// Mapping: "premium" -> "package.premium" 
   		/*com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, com.Constant.CONST_BPROPS, com.Constant.CONST_APROPS );
   		beanB.setPremiumAmt(  converter.getTypeOfA().cast( converter.getAFromB(  (String)beanA.getParameter( "premium_"+tariff )  ) )  );*/
   		

   		if(!Utils.isEmpty( tariff )){
   			dest.setTariffCode( String.valueOf( tariff ));
   		}
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.TravelPackageVO initializeDeepVO( javax.servlet.http.HttpServletRequest beanA, com.rsaame.pas.vo.bus.TravelPackageVO beanB , Integer tariff ){
  		BeanUtils.initializeBeanField( "covers[].coverCodes", beanB, HTTPUtils.getMatchingMultiReqParamKeys( beanA, com.Constant.CONST_COVERCODE_END+tariff ).size() );
       		BeanUtils.initializeBeanField( "covers[].riskCodes", beanB, HTTPUtils.getMatchingMultiReqParamKeys( beanA, "RiskCode[]" ).size() );
         		BeanUtils.initializeBeanField( "covers[].sumInsured", beanB, HTTPUtils.getMatchingMultiReqParamKeys( beanA, "coverSI[]" ).size() );
  		return beanB;
	}
}
