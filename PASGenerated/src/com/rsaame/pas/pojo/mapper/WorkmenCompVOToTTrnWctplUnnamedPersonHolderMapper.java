       /*
 * THIS IS A GENERATED CLASS. THIS CLASS SHOULD NOT BE EDITED.
 */
package com.rsaame.pas.pojo.mapper;

import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;

/**
 * Mapper class for:<ol>
 * <li>com.rsaame.pas.vo.bus.WorkmenCompVO</li>
 * <li>com.rsaame.pas.dao.model.TTrnWctplUnnamedPersonQuoHolder</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( WorkmenCompVOToTTrnWctplUnnamedPersonQuoMapper.class )</code>.
 */
public class WorkmenCompVOToTTrnWctplUnnamedPersonHolderMapper extends BaseBeanToBeanMapper<com.rsaame.pas.vo.bus.WorkmenCompVO, com.rsaame.pas.vo.svc.TTrnWctplUnnamedPersonQuoHolder>{
	private final Logger log = Logger.getLogger( this.getClass() );	

	public WorkmenCompVOToTTrnWctplUnnamedPersonHolderMapper(){
		super();
	}

	public WorkmenCompVOToTTrnWctplUnnamedPersonHolderMapper( com.rsaame.pas.vo.bus.WorkmenCompVO src, com.rsaame.pas.vo.svc.TTrnWctplUnnamedPersonQuoHolder dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.svc.TTrnWctplUnnamedPersonQuoHolder mapBean(){
		
		log.info("Mapping started - WC to ttrn wctpl unnamed person");
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}
		
		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.svc.TTrnWctplUnnamedPersonQuoHolder) Utils.newInstance( "com.rsaame.pas.vo.svc.TTrnWctplUnnamedPersonQuoHolder" );
		}
		
		/* Cast the destination bean to a bean of type of BeanA */	
		com.rsaame.pas.vo.bus.WorkmenCompVO beanA = src;
			
		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.svc.TTrnWctplUnnamedPersonQuoHolder beanB = dest;
			
  		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO(beanA, beanB);
		
		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;
		
		/* Mapping: "empTypeDetails[].vsd" -> "wupValidityStartDate" */
		noOfItems = beanA.getEmpTypeDetails().size();
		for( int i = 0; i < noOfItems; i++ ){
			if( !Utils.isEmpty( beanA.getEmpTypeDetails().get( i ).getVsd() ) ){
				beanB.setWupValidityStartDate( beanA.getEmpTypeDetails().get( i ).getVsd() );
			}
		}

		/* Mapping: "empTypeDetails[].riskId" -> "wupId" */
		noOfItems = beanA.getEmpTypeDetails().size();
		for( int i = 0; i < noOfItems; i++ ){
			if( !Utils.isEmpty( beanA.getEmpTypeDetails().get( i ).getRiskId() ) ){
				beanB.setWupId( beanA.getEmpTypeDetails().get( i ).getRiskId() );
			}
		}

 		/* Mapping: "commonVO.policyId" -> "wupPolicyId" */
		if(  !Utils.isEmpty( beanA.getCommonVO() ) && !Utils.isEmpty( beanA.getCommonVO().getPolicyId() )  ){
 			beanB.setWupPolicyId( beanA.getCommonVO().getPolicyId() ); 
 		}

 		/* Mapping: "empTypeDetails[].riskId" -> "wupBasicRiskId" */
/*		noOfItems = beanA.getEmpTypeDetails().size();
   		for( int i = 0; i < noOfItems; i++ ){
			beanB.setWupBasicRiskId( beanA.getEmpTypeDetails().get( i ).getRiskId() );
 		}*/

 		/* Mapping: "Utils.getSingleValueAppConfig( com.Constant.CONST_WC_BASIC_RISK_CODE )" -> "wupRskCode" */
  		noOfItems = beanA.getEmpTypeDetails().size();
   		for( int i = 0; i < noOfItems; i++ ){
       		beanB.setWupRskCode( Long.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_WC_BASIC_RISK_CODE ) ) );
		}

 		/* Mapping: "locationVO.occTradeGroup" -> "wupRtCode" 
		if(  !Utils.isEmpty( beanA.getLocationVO() ) && !Utils.isEmpty( beanA.getLocationVO().getOccTradeGroup() )  ){
 			beanB.setWupRtCode( beanA.getLocationVO().getOccTradeGroup().shortValue() ); 
 		}*/

 		/* Mapping: "Short.valueOf( Utils.getSingleValueAppConfig( "WC_RISK_CATEGORY" ) )" -> "wupRcCode" */
		if(  !Utils.isEmpty( Utils.getSingleValueAppConfig( "WC_RISK_CATEGORY" ) ) ) {
 			beanB.setWupRcCode( Short.valueOf( Utils.getSingleValueAppConfig( "WC_RISK_CATEGORY" ) ) ); 
 		}

 		/* Mapping: "empTypeDetails[].noOfEmp" -> "wupNoOfPerson" */
  		noOfItems = beanA.getEmpTypeDetails().size();
   		for( int i = 0; i < noOfItems; i++ ){
   			com.rsaame.pas.cmn.converter.LongIntegerConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongIntegerConverter.class, "", "" );
       		beanB.setWupNoOfPerson( converter.getTypeOfB().cast( converter.getBFromA( beanA.getEmpTypeDetails().get( i ).getNoOfEmp() ) ) );
		}

 		/* Mapping: "empTypeDetails[].wageroll" -> "wupSumInsured" */
  		noOfItems = beanA.getEmpTypeDetails().size();
   		for( int i = 0; i < noOfItems; i++ ){
        	com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.setWupSumInsured( converter.getTypeOfA().cast( converter.getAFromB( beanA.getEmpTypeDetails().get( i ).getWageroll() ) ) );
 		}

 		/* Mapping: "empTypeDetails[].empType" -> "wupEmploymentType" */
  		noOfItems = beanA.getEmpTypeDetails().size();
   		for( int i = 0; i < noOfItems; i++ ){
   			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
       		beanB.setWupEmploymentType( converter.getTypeOfB().cast( converter.getBFromA( beanA.getEmpTypeDetails().get( i ).getEmpType() ) ) );
		}

 		/* Mapping: "commonVO.endtId" -> "wupEndtId" */
		if(  !Utils.isEmpty( beanA.getCommonVO() ) && !Utils.isEmpty( beanA.getCommonVO().getEndtId() )  ){
 			beanB.setWupEndtId( beanA.getCommonVO().getEndtId() ); 
 		}

 		/* Mapping: "Integer.valueOf( Utils.getSingleValueAppConfig( SvcConstants.WC_RI_RSK_CODE ) )" -> "wupRiRskCode" */
		if(  !Utils.isEmpty( Utils.getSingleValueAppConfig( "WC_RI_RSK_CODE" )  ) ){
 			beanB.setWupRiRskCode( Integer.valueOf( Utils.getSingleValueAppConfig( "WC_RI_RSK_CODE" ) ) ); 
 		}

 		/* Mapping: "Integer.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_WC_BASIC_RISK_CODE ) )" -> "wupBasicRskCode" */
		if(  !Utils.isEmpty( Utils.getSingleValueAppConfig( com.Constant.CONST_WC_BASIC_RISK_CODE ) ) ){
 			beanB.setWupBasicRskCode( Integer.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_WC_BASIC_RISK_CODE ) ) ); 
 		}

 		/* Mapping: "commonVO.polEffectiveDate" -> "wupStartDate" */
		if(  !Utils.isEmpty( beanA.getCommonVO() ) && !Utils.isEmpty( beanA.getCommonVO().getPolEffectiveDate() )  ){
 			beanB.setWupStartDate( beanA.getCommonVO().getPolEffectiveDate() ); 
 		}

 		/* Mapping: "commonVO.endtEffectiveDate" -> "wupEndDate" */
		if(  !Utils.isEmpty( beanA.getCommonVO() ) && !Utils.isEmpty( beanA.getCommonVO().getEndtEffectiveDate() )  ){
 			beanB.setWupEndDate( beanA.getCommonVO().getEndtEffectiveDate() ); 
 		}

/* 		 Mapping: "locationVO.occTradeGroup" -> "wupTradeGroup" 
		if(  !Utils.isEmpty( beanA.getLocationVO() ) && !Utils.isEmpty( beanA.getLocationVO().getOccTradeGroup() )  ){
			com.rsaame.pas.cmn.converter.LongIntegerConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongIntegerConverter.class, "", "" );
 			beanB.setWupTradeGroup( converter.getTypeOfB().cast( converter.getBFromA( beanA.getLocationVO().getOccTradeGroup() ) ) ); 
 		}*/

 		/* Mapping: "locationVO.address.locOverrideTer" -> "wupTerCode" */
		if(  !Utils.isEmpty( beanA.getLocationVO() ) && !Utils.isEmpty( beanA.getLocationVO().getAddress() ) && !Utils.isEmpty( beanA.getLocationVO().getAddress().getLocOverrideTer() )  ){
			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
 			beanB.setWupTerCode( converter.getTypeOfB().cast( converter.getBFromA( beanA.getLocationVO().getAddress().getLocOverrideTer() ) ) ); 
 		}

 		/* Mapping: "locationVO.address.locOverrideJur" -> "wupJurCode" */
		if(  !Utils.isEmpty( beanA.getLocationVO() ) && !Utils.isEmpty( beanA.getLocationVO().getAddress() ) && !Utils.isEmpty( beanA.getLocationVO().getAddress().getLocOverrideJur() )  ){
			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
 			beanB.setWupJurCode( converter.getTypeOfB().cast( converter.getBFromA( beanA.getLocationVO().getAddress().getLocOverrideJur() ) ) ); 
 		}

 		/* Mapping: "locationVO.freeZone" -> "wupFreeZone" */
		if(  !Utils.isEmpty( beanA.getLocationVO() ) && !Utils.isEmpty( beanA.getLocationVO().getFreeZone() )  ){
 			beanB.setWupFreeZone( beanA.getLocationVO().getFreeZone() ); 
 		}
		
		/* Mapping: "locationVO.OccTradeGroup" -> "wupPlaceOfWork" */
		/*if(  !Utils.isEmpty( beanA.getLocationVO() ) && !Utils.isEmpty( beanA.getLocationVO().getOccTradeGroup() )  ){
 			beanB.setWupPlaceOfWork( beanA.getLocationVO().getOccTradeGroup().toString() ); 
 		}*/
		
		/* Mapping: "locationVO.hazardLevel" -> "wupHazard" */
		if(  !Utils.isEmpty( beanA.getLocationVO() ) && !Utils.isEmpty( beanA.getLocationVO().getHazardLevel() )  ){
 			beanB.setWupHazard( beanA.getLocationVO().getHazardLevel() ); 
 		}

 		/* Mapping: "limit" -> "wupEmpLiabLmt" */
		noOfItems = beanA.getEmpTypeDetails().size();
   		for( int i = 0; i < noOfItems; i++ ){
   			if(  !Utils.isEmpty( beanA.getEmpTypeDetails().get(i).getLimit() )  ){
   				beanB.setWupEmpLiabLmt( beanA.getEmpTypeDetails().get( i ).getLimit() );
   			}
   		}
		return dest;
	}	  

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.svc.TTrnWctplUnnamedPersonQuoHolder initializeDeepVO( com.rsaame.pas.vo.bus.WorkmenCompVO beanA, com.rsaame.pas.vo.svc.TTrnWctplUnnamedPersonQuoHolder beanB ){
		return beanB;
	}
}
