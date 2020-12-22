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
 * <li>com.rsaame.pas.vo.bus.ParVO</li>
 * <li>com.rsaame.pas.dao.model.TTrnBuildingQuo</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( ParVOToBuldPojoMapper.class )</code>.
 */
public class ParVOToBuldPojoMapper extends BaseBeanToBeanMapper<com.rsaame.pas.vo.bus.ParVO, com.rsaame.pas.dao.model.TTrnBuildingQuo>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public ParVOToBuldPojoMapper(){
		super();
	}

	public ParVOToBuldPojoMapper( com.rsaame.pas.vo.bus.ParVO src, com.rsaame.pas.dao.model.TTrnBuildingQuo dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.dao.model.TTrnBuildingQuo mapBean(){
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.dao.model.TTrnBuildingQuo) Utils.newInstance( "com.rsaame.pas.dao.model.TTrnBuildingQuo" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.vo.bus.ParVO beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.dao.model.TTrnBuildingQuo beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);

		/* Mapping: "builCovered" -> "bldCoverIndicator" */
		if(  !Utils.isEmpty( beanA.getBuilCovered() )  ){
 			com.rsaame.pas.cmn.converter.IntegerByteConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerByteConverter.class, "", "" );
			beanB.setBldCoverIndicator( converter.getTypeOfB().cast( converter.getBFromA( beanA.getBuilCovered() ) ) );
  		}

 		/* Mapping: "surveyDetails.surveyRequired" -> "bldFixturesInd" */
		if(  !Utils.isEmpty( beanA.getSurveyDetails() ) && !Utils.isEmpty( beanA.getSurveyDetails().getSurveyRequired() )  ){
 			beanB.setBldFixturesInd( beanA.getSurveyDetails().getSurveyRequired() ); 
 		}

 		/* Mapping: "surveyDetails.bldConstYear" -> "bldConstYr" */
		if(  !Utils.isEmpty( beanA.getSurveyDetails() ) && !Utils.isEmpty( beanA.getSurveyDetails().getBldConstYear() )  ){
 			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.setBldConstYr( converter.getTypeOfB().cast( converter.getBFromA( beanA.getSurveyDetails().getBldConstYear() ) ) );
  		}

 		/* Mapping: "surveyDetails.surveyRequired" -> "bldFixturesInd" */
		if(  !Utils.isEmpty( beanA.getSurveyDetails() ) && !Utils.isEmpty( beanA.getSurveyDetails().getSurveyRequired() )  ){
 			beanB.setBldFixturesInd( beanA.getSurveyDetails().getSurveyRequired() ); 
 		}

 		/* Mapping: "surveyDetails.surveyDt" -> "bldDeclDate" */
		if(  !Utils.isEmpty( beanA.getSurveyDetails() ) && !Utils.isEmpty( beanA.getSurveyDetails().getSurveyDt() )  ){
 			beanB.setBldDeclDate( beanA.getSurveyDetails().getSurveyDt() ); 
 		}

 		/* Mapping: "surveyDetails.latitude" -> "bldLatitude" */
		if(  !Utils.isEmpty( beanA.getSurveyDetails() ) && !Utils.isEmpty( beanA.getSurveyDetails().getLatitude() )  ){
			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.setBldLatitude( converter.getTypeOfA().cast( converter.getAFromB( beanA.getSurveyDetails().getLatitude() ) ) );
  		}

 		/* Mapping: "surveyDetails.longitude" -> "bldLongitude" */
		if(  !Utils.isEmpty( beanA.getSurveyDetails() ) && !Utils.isEmpty( beanA.getSurveyDetails().getLongitude() )  ){
			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.setBldLongitude( converter.getTypeOfA().cast( converter.getAFromB( beanA.getSurveyDetails().getLongitude() ) ) );
  		}

 		/* Mapping: "surveyDetails.noOfFloor" -> "bldTotalNoFloors" */
		if(  !Utils.isEmpty( beanA.getSurveyDetails() ) && !Utils.isEmpty( beanA.getSurveyDetails().getNoOfFloor() )  ){
 			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.setBldTotalNoFloors( converter.getTypeOfB().cast( converter.getBFromA( beanA.getSurveyDetails().getNoOfFloor() ) ) );
  		}

 		/* Mapping: "surveyDetails.lowestFloor" -> "bldLowestFloor" */
		if(  !Utils.isEmpty( beanA.getSurveyDetails() ) && !Utils.isEmpty( beanA.getSurveyDetails().getLowestFloor() )  ){
 			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.setBldLowestFloor( converter.getTypeOfB().cast( converter.getBFromA( beanA.getSurveyDetails().getLowestFloor() ) ) );
  		}

 		/* Mapping: "surveyDetails.occupiedFloors" -> "bldNoOccFloors" */
		if(  !Utils.isEmpty( beanA.getSurveyDetails() ) && !Utils.isEmpty( beanA.getSurveyDetails().getOccupiedFloors() )  ){
 			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.setBldNoOccFloors( converter.getTypeOfB().cast( converter.getBFromA( beanA.getSurveyDetails().getOccupiedFloors() ) ) );
  		}

 		/* Mapping: "surveyDetails.dispDate" -> "bldDispDate" */
		if(  !Utils.isEmpty( beanA.getSurveyDetails() ) && !Utils.isEmpty( beanA.getSurveyDetails().getDispDate() )  ){
 			beanB.setBldDispDate( beanA.getSurveyDetails().getDispDate() ); 
 		}

 		/* Mapping: "surveyDetails.resurveyPeriod" -> "bldResurveyPeriod" */
		if(  !Utils.isEmpty( beanA.getSurveyDetails() ) && !Utils.isEmpty( beanA.getSurveyDetails().getResurveyPeriod() )  ){
 			beanB.setBldResurveyPeriod( beanA.getSurveyDetails().getResurveyPeriod() ); 
 		}

 		/* Mapping: "surveyDetails.resurveyReqDt" -> "bldRoutineResurveyDt" */
		if(  !Utils.isEmpty( beanA.getSurveyDetails() ) && !Utils.isEmpty( beanA.getSurveyDetails().getResurveyReqDt() )  ){
 			beanB.setBldRoutineResurveyDt( beanA.getSurveyDetails().getResurveyReqDt() ); 
 		}

 		/* Mapping: "surveyDetails.dispensationAgreed" -> "bldDispensationAgreed" */
		if(  !Utils.isEmpty( beanA.getSurveyDetails() ) && !Utils.isEmpty( beanA.getSurveyDetails().getDispensationAgreed() )  ){
 			beanB.setBldDispensationAgreed( beanA.getSurveyDetails().getDispensationAgreed() ); 
 		}

 		/* Mapping: "surveyDetails.specResurveyReqDt" -> "bldSpecificResurveyDt" */
		if(  !Utils.isEmpty( beanA.getSurveyDetails() ) && !Utils.isEmpty( beanA.getSurveyDetails().getSpecResurveyReqDt() )  ){
 			beanB.setBldSpecificResurveyDt( beanA.getSurveyDetails().getSpecResurveyReqDt() ); 
 			
		}


 		/* Mapping: "bldDesc" -> "bldDesc" */
		if( !Utils.isEmpty( beanA.getBldDesc() )  ){
			beanB.setBldDesc(beanA.getBldDesc());
		}
		
		
 		/* Mapping: "surveyDetails.surveyorOpinion" -> "bldSurveyorOpinion" */
		if(  !Utils.isEmpty( beanA.getSurveyDetails() ) && !Utils.isEmpty( beanA.getSurveyDetails().getSurveyorOpinion() )  ){
 			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.setBldSurveyorOpinion( converter.getTypeOfB().cast( converter.getBFromA( beanA.getSurveyDetails().getSurveyorOpinion() ) ) );
  		}

 		/* Mapping: "surveyDetails.SRFDate" -> "bldSrfDt" */
		if(  !Utils.isEmpty( beanA.getSurveyDetails() ) && !Utils.isEmpty( beanA.getSurveyDetails().getSRFDate() )  ){
 			beanB.setBldSrfDt( beanA.getSurveyDetails().getSRFDate() ); 
 		}

 		/* Mapping: "surveyDetails.pointSource" -> "bldPointScore" */
		if(  !Utils.isEmpty( beanA.getSurveyDetails() ) && !Utils.isEmpty( beanA.getSurveyDetails().getPointSource() )  ){
 			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.setBldPointScore( converter.getTypeOfB().cast( converter.getBFromA( beanA.getSurveyDetails().getPointSource() ) ) );
  		}

 		/* Mapping: "surveyDetails.rcpSentDt" -> "bldRcpDt" */
		if(  !Utils.isEmpty( beanA.getSurveyDetails() ) && !Utils.isEmpty( beanA.getSurveyDetails().getRcpSentDt() )  ){
 			beanB.setBldRcpDt( beanA.getSurveyDetails().getRcpSentDt() ); 
 		}

 		/* Mapping: "surveyDetails.rcpConfirmDt" -> "bldRcpConfirmationDt" */
		if(  !Utils.isEmpty( beanA.getSurveyDetails() ) && !Utils.isEmpty( beanA.getSurveyDetails().getRcpConfirmDt() )  ){
 			beanB.setBldRcpConfirmationDt( beanA.getSurveyDetails().getRcpConfirmDt() ); 
 		}

 		/* Mapping: "surveyDetails.rcpStatus" -> "bldRcpStatus" */
		if(  !Utils.isEmpty( beanA.getSurveyDetails() ) && !Utils.isEmpty( beanA.getSurveyDetails().getRcpStatus() )  ){
 			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.setBldRcpStatus( converter.getTypeOfB().cast( converter.getBFromA( beanA.getSurveyDetails().getRcpStatus() ) ) );
  		}

   
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.dao.model.TTrnBuildingQuo initializeDeepVO( com.rsaame.pas.vo.bus.ParVO beanA, com.rsaame.pas.dao.model.TTrnBuildingQuo beanB ){
                                           		return beanB;
	}
}
