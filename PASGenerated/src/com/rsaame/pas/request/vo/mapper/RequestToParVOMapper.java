       /*
 * THIS IS A GENERATED CLASS. THIS CLASS SHOULD NOT BE EDITED.
 */
package com.rsaame.pas.request.vo.mapper;

import java.util.Iterator;

import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.beanmap.BeanMapperFactory;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.BeanUtils;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.HTTPUtils;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.vo.bus.PropertyRiskDetails;

/**
 * Mapper class for:<ol>
 * <li>javax.servlet.http.HttpServletRequest</li>
 * <li>com.rsaame.pas.vo.bus.ParVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( RequestToParVOMapper.class )</code>.
 */
public class RequestToParVOMapper extends BaseBeanToBeanMapper<javax.servlet.http.HttpServletRequest, com.rsaame.pas.vo.bus.ParVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public RequestToParVOMapper(){
		super();
	}

	public RequestToParVOMapper( javax.servlet.http.HttpServletRequest src, com.rsaame.pas.vo.bus.ParVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.ParVO mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.ParVO) Utils.newInstance( "com.rsaame.pas.vo.bus.ParVO" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		javax.servlet.http.HttpServletRequest beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.ParVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);

		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;
		
		/* Mapping: "commission" -> "commission" */
		if( !Utils.isEmpty( src.getParameter( "commission" ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.setCommission( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "commission" ) ) ) );
  		}

 		/* Mapping: "parBuildingCoveredHiddenValue" -> "builCovered" */
		if( !Utils.isEmpty( src.getParameter( "parBuildingCoveredHiddenValue" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.setBuilCovered( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "parBuildingCoveredHiddenValue" ) ) ) );
  		}
		
		/* Mapping: "riskGroupId" -> "basicRiskId" */
		if( !Utils.isEmpty( src.getParameter( "riskGroupId" ) ) ){
			com.rsaame.pas.cmn.converter.LongStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongStringConverter.class, "", "" );
			beanB.setBasicRiskId( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "riskGroupId" ) ) ) );
  		}
		
 		/* Mapping: "coverId" -> "covers.propertyCoversDetails[].coverId" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "coverId" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			com.rsaame.pas.cmn.converter.LongStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongStringConverter.class, "", "" );
			beanB.getCovers().getPropertyCoversDetails().get( i ).setCoverId ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "coverId[" + i + "]" ) ) ) );
 		}

 		/* Mapping: "coverVSD" -> "covers.propertyCoversDetails[].setValidityStartDate" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "coverVSD" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", "" );
			beanB.getCovers().getPropertyCoversDetails().get( i ).setSetValidityStartDate ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "coverVSD[" + i + "]" ) ) ) );
 		}

 		/* Mapping: "bldSIName" -> "bldCover" */
		if( !Utils.isEmpty( src.getParameter( "bldSIName" ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.setBldCover( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "bldSIName" ) ) ) );
  		}

 		/* Mapping: "bldDescName" -> "bldDesc" */
		if( !Utils.isEmpty( src.getParameter( "bldDescName" ) ) ){
 			beanB.setBldDesc( beanA.getParameter( "bldDescName" ) ); 
 		}

 		/* Mapping: "bldDedName" -> "bldDeductibles" */
		if( !Utils.isEmpty( src.getParameter( "bldDedName" ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.setBldDeductibles( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "bldDedName" ) ) ) );
		}
		

 		/* Mapping: "si" -> "covers.propertyCoversDetails[].cover" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "si" ).size();
  		
  		int SIZE_OF_BASE_COVER_CNTS = noOfItems;
   		for( int i = 0; i < noOfItems; i++ ){
        			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.getCovers().getPropertyCoversDetails().get( i ).setCover ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "si[" + i + "]" ) ) ) );
 		}

 		/* Mapping: "desc" -> "covers.propertyCoversDetails[].desc" */
  		 noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "desc" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			beanB.getCovers().getPropertyCoversDetails().get( i ).setDesc (  beanA.getParameter( "desc[" + i + "]" ) );
		}

 		/* Mapping: "deductible" -> "covers.propertyCoversDetails[].deductibles" */
  		 noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "deductible" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.getCovers().getPropertyCoversDetails().get( i ).setDeductibles ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "deductible[" + i + "]" ) ) ) );
 		}

 		/* Mapping: "riskType" -> "covers.propertyCoversDetails[].riskType" */
  		 noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "riskType" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getCovers().getPropertyCoversDetails().get( i ).setRiskType ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "riskType[" + i + "]" ) ) ) );
 		}

 		/* Mapping: "parSurveyReq" -> "surveyDetails.surveyRequired" */
		if( !Utils.isEmpty( src.getParameter( "parSurveyReq" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getSurveyDetails().setSurveyRequired( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "parSurveyReq" ) ) ) );
  		}

 		/* Mapping: "parSurveyDate" -> "surveyDetails.surveyDt" */
		if( !Utils.isEmpty( src.getParameter( "parSurveyDate" ) ) ){
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", com.Constant.CONST_FORMAT_YYYY_MM_DD );
			beanB.getSurveyDetails().setSurveyDt( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "parSurveyDate" ) ) ) );
  		}

 		/* Mapping: "parConstructionYear" -> "surveyDetails.bldConstYear" */
		if( !Utils.isEmpty( src.getParameter( "parConstructionYear" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getSurveyDetails().setBldConstYear( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "parConstructionYear" ) ) ) );
  		}

 		/* Mapping: "parLatitude" -> "surveyDetails.latitude" */
		if( !Utils.isEmpty( src.getParameter( "parLatitude" ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.getSurveyDetails().setLatitude( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "parLatitude" ) ) ) );
  		}

 		/* Mapping: "parLongitude" -> "surveyDetails.longitude" */
		if( !Utils.isEmpty( src.getParameter( "parLongitude" ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.getSurveyDetails().setLongitude( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "parLongitude" ) ) ) );
  		}

 		/* Mapping: "parNoFloors" -> "surveyDetails.noOfFloor" */
		if( !Utils.isEmpty( src.getParameter( "parNoFloors" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getSurveyDetails().setNoOfFloor( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "parNoFloors" ) ) ) );
  		}

 		/* Mapping: "parLowestFloor" -> "surveyDetails.lowestFloor" */
		if( !Utils.isEmpty( src.getParameter( "parLowestFloor" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getSurveyDetails().setLowestFloor( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "parLowestFloor" ) ) ) );
  		}

 		/* Mapping: "parFloorsOccupied" -> "surveyDetails.occupiedFloors" */
		if( !Utils.isEmpty( src.getParameter( "parFloorsOccupied" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getSurveyDetails().setOccupiedFloors( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "parFloorsOccupied" ) ) ) );
  		}

 		/* Mapping: "parDispDate" -> "surveyDetails.dispDate" */
		if( !Utils.isEmpty( src.getParameter( "parDispDate" ) ) ){
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", com.Constant.CONST_FORMAT_YYYY_MM_DD );
			beanB.getSurveyDetails().setDispDate( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "parDispDate" ) ) ) );
  		}

 		/* Mapping: "parResurveyPeriod" -> "surveyDetails.resurveyPeriod" */
		if( !Utils.isEmpty( src.getParameter( "parResurveyPeriod" ) ) ){
 			beanB.getSurveyDetails().setResurveyPeriod( beanA.getParameter( "parResurveyPeriod" ) ); 
 		}

 		/* Mapping: "parResurveyReqDate" -> "surveyDetails.resurveyReqDt" */
		if( !Utils.isEmpty( src.getParameter( "parResurveyReqDate" ) ) ){
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", com.Constant.CONST_FORMAT_YYYY_MM_DD );
			beanB.getSurveyDetails().setResurveyReqDt( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "parResurveyReqDate" ) ) ) );
  		}

 		/* Mapping: "parDispensationAgreed" -> "surveyDetails.dispensationAgreed" */
		if( !Utils.isEmpty( src.getParameter( "parDispensationAgreed" ) ) ){
			com.rsaame.pas.cmn.converter.CharacterStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.CharacterStringConverter.class, "", "" );
			beanB.getSurveyDetails().setDispensationAgreed( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "parDispensationAgreed" ) ) ) );
  		}

 		/* Mapping: "parSpResuveyReqDate" -> "surveyDetails.specResurveyReqDt" */
		if( !Utils.isEmpty( src.getParameter( "parSpResuveyReqDate" ) ) ){
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", com.Constant.CONST_FORMAT_YYYY_MM_DD );
			beanB.getSurveyDetails().setSpecResurveyReqDt( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "parSpResuveyReqDate" ) ) ) );
  		}

 		/* Mapping: "parSurveyorOpinion" -> "surveyDetails.surveyorOpinion" */
		if( !Utils.isEmpty( src.getParameter( "parSurveyorOpinion" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getSurveyDetails().setSurveyorOpinion( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "parSurveyorOpinion" ) ) ) );
  		}

 		/* Mapping: "parSRFSubmissionDate" -> "surveyDetails.SRFDate" */
		if( !Utils.isEmpty( src.getParameter( "parSRFSubmissionDate" ) ) ){
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", com.Constant.CONST_FORMAT_YYYY_MM_DD );
			beanB.getSurveyDetails().setSRFDate( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "parSRFSubmissionDate" ) ) ) );
  		}

 		/* Mapping: "parPointSource" -> "surveyDetails.pointSource" */
		if( !Utils.isEmpty( src.getParameter( "parPointSource" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getSurveyDetails().setPointSource( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "parPointSource" ) ) ) );
  		}

 		/* Mapping: "parRCPSentDate" -> "surveyDetails.rcpSentDt" */
		if( !Utils.isEmpty( src.getParameter( "parRCPSentDate" ) ) ){
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", com.Constant.CONST_FORMAT_YYYY_MM_DD );
			beanB.getSurveyDetails().setRcpSentDt( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "parRCPSentDate" ) ) ) );
  		}

 		/* Mapping: "parRCPConfirmDate" -> "surveyDetails.rcpConfirmDt" */
		if( !Utils.isEmpty( src.getParameter( "parRCPConfirmDate" ) ) ){
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "", com.Constant.CONST_FORMAT_YYYY_MM_DD );
			beanB.getSurveyDetails().setRcpConfirmDt( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "parRCPConfirmDate" ) ) ) );
  		}

 		/* Mapping: "parRCPStatus" -> "surveyDetails.rcpStatus" */
		if( !Utils.isEmpty( src.getParameter( "parRCPStatus" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getSurveyDetails().setRcpStatus( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "parRCPStatus" ) ) ) );
  		}
		
		/* Mapping: "coverCode" -> "covers.propertyCoversDetails[].coverCode" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "coverCode" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getCovers().getPropertyCoversDetails().get( i ).setCoverCode ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "coverCode[" + i + "]" ) ) ) );
 		}

 		/* Mapping: "coverType" -> "covers.propertyCoversDetails[].coverType" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "coverType" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getCovers().getPropertyCoversDetails().get( i ).setCoverType ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "coverType[" + i + "]" ) ) ) );
 		}

 		/* Mapping: "coverSubType" -> "covers.propertyCoversDetails[].coverSubType" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "coverSubType" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getCovers().getPropertyCoversDetails().get( i ).setCoverSubType ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "coverSubType[" + i + "]" ) ) ) );
 		}
   		
		/* Mapping: "additionalCoverOpted" -> "covers.propertyCoversDetails[].coverOpted" */
  		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, "additionalCoverOpted" ).size();
   		for( int i = 0; i < noOfItems; i++ ){
        			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getCovers().getPropertyCoversDetails().get( i ).setCoverOpted ( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "additionalCoverOpted[" + i + "]" ) ) ) );
 		}
   		
   		
   		Iterator iter = dest.getCovers().getPropertyCoversDetails().iterator();
   		PropertyRiskDetails propertyRiskDetails;
		
		
		while(iter.hasNext()){
			propertyRiskDetails = (PropertyRiskDetails) iter.next();
			if(propertyRiskDetails.getCover()==0.0 
					&& propertyRiskDetails.getDeductibles() == 0.0){
				propertyRiskDetails.setDesc(null);
			}
		}
   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.ParVO initializeDeepVO( javax.servlet.http.HttpServletRequest beanA, com.rsaame.pas.vo.bus.ParVO beanB ){
      		BeanUtils.initializeBeanField( "covers.propertyCoversDetails[]", beanB, HTTPUtils.getMatchingMultiReqParamKeys( beanA, "si[]" ).size() );
         		BeanUtils.initializeBeanField( "surveyDetails", beanB );
                                      		return beanB;
	}
}
