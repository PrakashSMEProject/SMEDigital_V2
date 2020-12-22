       /*
 * THIS IS A GENERATED CLASS. THIS CLASS SHOULD NOT BE EDITED.
 */
package com.rsaame.pas.pojo.mapper;

import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.beanmap.BeanMapperFactory;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.BeanUtils;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.Utils;

/**
 * Mapper class for:<ol>
 * <li>com.rsaame.pas.dao.model.TTrnBuildingQuo</li>
 * <li>com.rsaame.pas.vo.bus.ParVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( ParVOToBuldPojoMapperReverse.class )</code>.
 */
public class ParVOToBuldPojoMapperReverse extends BaseBeanToBeanMapper<com.rsaame.pas.dao.model.TTrnBuildingQuo, com.rsaame.pas.vo.bus.ParVO>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public ParVOToBuldPojoMapperReverse(){
		super();
	}

	public ParVOToBuldPojoMapperReverse( com.rsaame.pas.dao.model.TTrnBuildingQuo src, com.rsaame.pas.vo.bus.ParVO dest ){
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
		com.rsaame.pas.dao.model.TTrnBuildingQuo beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.ParVO beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);

		/* Mapping: "bldCoverIndicator" -> "builCovered" */
		if(  !Utils.isEmpty( beanA.getBldCoverIndicator() )  ){
 			com.rsaame.pas.cmn.converter.IntegerByteConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerByteConverter.class, "", "" );
			beanB.setBuilCovered( converter.getTypeOfA().cast( converter.getAFromB( beanA.getBldCoverIndicator() ) ) );
  		}

 		/* Mapping: "bldFixturesInd" -> "surveyDetails.surveyRequired" */
		if(  !Utils.isEmpty( beanA.getBldFixturesInd() )  ){
 			beanB.getSurveyDetails().setSurveyRequired( beanA.getBldFixturesInd() ); 
 		}

 		/* Mapping: "bldConstYr" -> "surveyDetails.bldConstYear" */
		if(  !Utils.isEmpty( beanA.getBldConstYr() )  ){
 			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.getSurveyDetails().setBldConstYear( converter.getTypeOfA().cast( converter.getAFromB( beanA.getBldConstYr() ) ) );
  		}

 		/* Mapping: "bldFixturesInd" -> "surveyDetails.surveyRequired" */
		if(  !Utils.isEmpty( beanA.getBldFixturesInd() )  ){
 			beanB.getSurveyDetails().setSurveyRequired( beanA.getBldFixturesInd() ); 
 		}

 		/* Mapping: "bldDeclDate" -> "surveyDetails.surveyDt" */
		if(  !Utils.isEmpty( beanA.getBldDeclDate() )  ){
 			beanB.getSurveyDetails().setSurveyDt( beanA.getBldDeclDate() ); 
 		}

 		/* Mapping: "bldLatitude" -> "surveyDetails.latitude" */
		if(  !Utils.isEmpty( beanA.getBldLatitude() )  ){
			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.getSurveyDetails().setLatitude( converter.getTypeOfB().cast( converter.getBFromA( beanA.getBldLatitude() ) ) );
  		}

 		/* Mapping: "bldLongitude" -> "surveyDetails.longitude" */
		if(  !Utils.isEmpty( beanA.getBldLongitude() )  ){
			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.getSurveyDetails().setLongitude( converter.getTypeOfB().cast( converter.getBFromA( beanA.getBldLongitude() ) ) );
  		}

 		/* Mapping: "bldTotalNoFloors" -> "surveyDetails.noOfFloor" */
		if(  !Utils.isEmpty( beanA.getBldTotalNoFloors() )  ){
 			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.getSurveyDetails().setNoOfFloor( converter.getTypeOfA().cast( converter.getAFromB( beanA.getBldTotalNoFloors() ) ) );
  		}

 		/* Mapping: "bldLowestFloor" -> "surveyDetails.lowestFloor" */
		if(  !Utils.isEmpty( beanA.getBldLowestFloor() )  ){
 			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.getSurveyDetails().setLowestFloor( converter.getTypeOfA().cast( converter.getAFromB( beanA.getBldLowestFloor() ) ) );
  		}

 		/* Mapping: "bldNoOccFloors" -> "surveyDetails.occupiedFloors" */
		if(  !Utils.isEmpty( beanA.getBldNoOccFloors() )  ){
 			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.getSurveyDetails().setOccupiedFloors( converter.getTypeOfA().cast( converter.getAFromB( beanA.getBldNoOccFloors() ) ) );
  		}

 		/* Mapping: "bldDispDate" -> "surveyDetails.dispDate" */
		if(  !Utils.isEmpty( beanA.getBldDispDate() )  ){
 			beanB.getSurveyDetails().setDispDate( beanA.getBldDispDate() ); 
 		}

 		/* Mapping: "bldResurveyPeriod" -> "surveyDetails.resurveyPeriod" */
		if(  !Utils.isEmpty( beanA.getBldResurveyPeriod() )  ){
 			beanB.getSurveyDetails().setResurveyPeriod( beanA.getBldResurveyPeriod() ); 
 		}

 		/* Mapping: "bldRoutineResurveyDt" -> "surveyDetails.resurveyReqDt" */
		if(  !Utils.isEmpty( beanA.getBldRoutineResurveyDt() )  ){
 			beanB.getSurveyDetails().setResurveyReqDt( beanA.getBldRoutineResurveyDt() ); 
 		}

 		/* Mapping: "bldDispensationAgreed" -> "surveyDetails.dispensationAgreed" */
		if(  !Utils.isEmpty( beanA.getBldDispensationAgreed() )  ){
 			beanB.getSurveyDetails().setDispensationAgreed( beanA.getBldDispensationAgreed() ); 
 		}

 		/* Mapping: "bldSpecificResurveyDt" -> "surveyDetails.specResurveyReqDt" */
		if(  !Utils.isEmpty( beanA.getBldSpecificResurveyDt() )  ){
 			beanB.getSurveyDetails().setSpecResurveyReqDt( beanA.getBldSpecificResurveyDt() ); 
 		}

 		/* Mapping: "bldSurveyorOpinion" -> "surveyDetails.surveyorOpinion" */
		if(  !Utils.isEmpty( beanA.getBldSurveyorOpinion() )  ){
 			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.getSurveyDetails().setSurveyorOpinion( converter.getTypeOfA().cast( converter.getAFromB( beanA.getBldSurveyorOpinion() ) ) );
  		}

 		/* Mapping: "bldSrfDt" -> "surveyDetails.SRFDate" */
		if(  !Utils.isEmpty( beanA.getBldSrfDt() )  ){
 			beanB.getSurveyDetails().setSRFDate( beanA.getBldSrfDt() ); 
 		}

 		/* Mapping: "bldPointScore" -> "surveyDetails.pointSource" */
		if(  !Utils.isEmpty( beanA.getBldPointScore() )  ){
 			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.getSurveyDetails().setPointSource( converter.getTypeOfA().cast( converter.getAFromB( beanA.getBldPointScore() ) ) );
  		}

 		/* Mapping: "bldRcpDt" -> "surveyDetails.rcpSentDt" */
		if(  !Utils.isEmpty( beanA.getBldRcpDt() )  ){
 			beanB.getSurveyDetails().setRcpSentDt( beanA.getBldRcpDt() ); 
 		}

 		/* Mapping: "bldRcpConfirmationDt" -> "surveyDetails.rcpConfirmDt" */
		if(  !Utils.isEmpty( beanA.getBldRcpConfirmationDt() )  ){
 			beanB.getSurveyDetails().setRcpConfirmDt( beanA.getBldRcpConfirmationDt() ); 
 		}

 		/* Mapping: "bldRcpStatus" -> "surveyDetails.rcpStatus" */
		if(  !Utils.isEmpty( beanA.getBldRcpStatus() )  ){
 			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.getSurveyDetails().setRcpStatus( converter.getTypeOfA().cast( converter.getAFromB( beanA.getBldRcpStatus() ) ) );
  		}
		/* Mapping : "bldDesc" -> "bldDesc" */
		if(  !Utils.isEmpty( beanA.getBldDesc() )  ){
 			beanB.setBldDesc(  beanA.getBldDesc());
  		}


   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.ParVO initializeDeepVO( com.rsaame.pas.dao.model.TTrnBuildingQuo beanA, com.rsaame.pas.vo.bus.ParVO beanB ){
    		BeanUtils.initializeBeanField( "surveyDetails", beanB );
                                        		return beanB;
	}
}
