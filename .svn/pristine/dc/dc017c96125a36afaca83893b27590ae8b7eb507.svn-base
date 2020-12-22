/*
* THIS IS A GENERATED CLASS. THIS CLASS SHOULD NOT BE EDITED.
*/
package com.rsaame.pas.pojo.mapper;

import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.BeanUtils;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.vo.bus.EmpTypeDetailsVO;

/**
 * Mapper class for:<ol>
 * <li>com.rsaame.pas.vo.svc.TTrnWctplUnnamedPersonQuoHolder</li>
 * <li>com.rsaame.pas.vo.bus.WorkmenCompVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( WorkmenCompVOToTTrnWctplUnnamedPersonQuoMapperReverse.class )</code>.
 */
public class WorkmenCompVOToTTrnWctplUnnamedPersonHolderMapperReverse extends
		BaseBeanToBeanMapper<com.rsaame.pas.vo.svc.TTrnWctplUnnamedPersonQuoHolder, com.rsaame.pas.vo.bus.WorkmenCompVO>{
	private final Logger log = Logger.getLogger( this.getClass() );

	public WorkmenCompVOToTTrnWctplUnnamedPersonHolderMapperReverse(){
		super();
	}

	public WorkmenCompVOToTTrnWctplUnnamedPersonHolderMapperReverse( com.rsaame.pas.vo.svc.TTrnWctplUnnamedPersonQuoHolder src, com.rsaame.pas.vo.bus.WorkmenCompVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.WorkmenCompVO mapBean(){

		log.info( "Mapping from ttrn wctpl unnamed person to wc vo monoline" );

		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}

		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.WorkmenCompVO) Utils.newInstance( "com.rsaame.pas.vo.bus.WorkmenCompVO" );
		}

		/* Cast the destination bean to a bean of type of BeanA */
		com.rsaame.pas.vo.svc.TTrnWctplUnnamedPersonQuoHolder beanA = src;

		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.WorkmenCompVO beanB = dest;

		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO( beanA, beanB );

		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;
/*		if( !Utils.isEmpty( beanB.getEmpTypeDetails() )){
			beanB.setEmpTypeDetails(new com.mindtree.ruc.cmn.utils.List<EmpTypeDetailsVO>( EmpTypeDetailsVO.class ) );
			beanB.getEmpTypeDetails().add( new EmpTypeDetailsVO() );
		}*/

		/* Mapping: "wupValidityStartDate" -> "empTypeDetails[].vsd" */
		if( !Utils.isEmpty( beanA.getWupValidityStartDate() ) ){
			beanB.getEmpTypeDetails().get( 0 ).setVsd( beanA.getWupValidityStartDate() );
		}

		/* Mapping: "wupId" -> "empTypeDetails[].riskId" */
		if( !Utils.isEmpty( beanA.getWupId() ) ){
			beanB.getEmpTypeDetails().get( 0 ).setRiskId( beanA.getWupId() );
		}

		/* Mapping: "wupRskCode" -> "empTypeDetails[].riskCode" */
		if( !Utils.isEmpty( beanA.getWupRskCode() ) ){
			com.rsaame.pas.cmn.converter.LongIntegerConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongIntegerConverter.class, "", "" );
			beanB.getEmpTypeDetails().get( 0 ).setRiskCode( converter.getTypeOfA().cast( converter.getAFromB( beanA.getWupRskCode() ) ) );
		}

		/* Mapping: "wupRtCode" -> "empTypeDetails[].riskType" */
		if( !Utils.isEmpty( beanA.getWupRtCode() ) ){
			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.getEmpTypeDetails().get( 0 ).setRiskType( converter.getTypeOfA().cast( converter.getAFromB( beanA.getWupRtCode() ) ) );
		}

		/* Mapping: "wupRcCode" -> "empTypeDetails[].riskCat" */
		if( !Utils.isEmpty( beanA.getWupRcCode() ) ){
			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.getEmpTypeDetails().get( 0 ).setRiskCat( converter.getTypeOfA().cast( converter.getAFromB( beanA.getWupRcCode() ) ) );
		}

		/* Mapping: "wupNoOfPerson" -> "empTypeDetails[].noOfEmp" */
		if( !Utils.isEmpty( beanA.getWupNoOfPerson() ) ){
			beanB.getEmpTypeDetails().get( 0 ).setNoOfEmp( beanA.getWupNoOfPerson().intValue() );
		}

		/* Mapping: "wupSumInsured" -> "empTypeDetails[].wageroll" */
		if( !Utils.isEmpty( beanA.getWupSumInsured() ) ){
			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.getEmpTypeDetails().get( 0 ).setWageroll( converter.getTypeOfB().cast( converter.getBFromA( beanA.getWupSumInsured() ) ) );
		}

		/* Mapping: "wupEmploymentType" -> "empTypeDetails[].empType" */
		if( !Utils.isEmpty( beanA.getWupEmploymentType() ) ){
			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.getEmpTypeDetails().get( 0 ).setEmpType( converter.getTypeOfA().cast( converter.getAFromB( beanA.getWupEmploymentType() ) ) );
		}

		/* Mapping: "wupBasicRskCode" -> "empTypeDetails[].basicRiskCode" */
		if( !Utils.isEmpty( beanA.getWupBasicRskCode() ) ){
			beanB.getEmpTypeDetails().get( 0 ).setBasicRiskCode( beanA.getWupBasicRskCode() );
		}
		
		/* Mapping: "wupTradeGroup" -> "empTypeDetails[].occTradeGroup" */
		if( !Utils.isEmpty( beanA.getWupTradeGroup() ) ){
			com.rsaame.pas.cmn.converter.LongIntegerConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongIntegerConverter.class, "", "" );
			beanB.getEmpTypeDetails().get( 0 ).setOccTradeGroup( converter.getTypeOfA().cast( converter.getAFromB( beanA.getWupTradeGroup() ) ) );
		}
		
		/* Mapping: "wupHazard" -> "empTypeDetails[].hazardLevel" */
		if( !Utils.isEmpty( beanA.getWupHazard() ) ){
			beanB.getEmpTypeDetails().get( 0 ).setHazardLevel( beanA.getWupHazard() );
		}

		/* Mapping: "wupTradeGroup" -> "locationVO.occTradeGroup" */
/*		if( !Utils.isEmpty( beanA.getWupTradeGroup() ) ){
			com.rsaame.pas.cmn.converter.LongIntegerConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongIntegerConverter.class, "", "" );
			beanB.getLocationVO().setOccTradeGroup( converter.getTypeOfA().cast( converter.getAFromB( beanA.getWupTradeGroup() ) ) );
		}*/

		/* Mapping: "wupTerCode" -> "locationVO.address.locOverrideTer" */
		if( !Utils.isEmpty( beanA.getWupTerCode() ) ){
			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.getLocationVO().getAddress().setLocOverrideTer( converter.getTypeOfA().cast( converter.getAFromB( beanA.getWupTerCode() ) ) );
		}

		/* Mapping: "wupJurCode" -> "locationVO.address.locOverrideJur" */
		if( !Utils.isEmpty( beanA.getWupJurCode() ) ){
			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.getLocationVO().getAddress().setLocOverrideJur( converter.getTypeOfA().cast( converter.getAFromB( beanA.getWupJurCode() ) ) );
		}

		/* Mapping: "wupFreeZone" -> "locationVO.freeZone" */
		if( !Utils.isEmpty( beanA.getWupFreeZone() ) ){
			beanB.getLocationVO().setFreeZone( beanA.getWupFreeZone() );
		}
		
		/* Mapping: "wupHazard" -> "locationVO.hazardLevel" */
		if( !Utils.isEmpty( beanA.getWupHazard() ) ){
			beanB.getLocationVO().setHazardLevel( beanA.getWupHazard() );
		}
		
		/* Mapping: "wupHazard" -> "locationVO.hazardLevel" */
		if( !Utils.isEmpty( beanA.getWupPlaceOfWork() ) ){
			beanB.getLocationVO().setOccTradeGroup( Integer.valueOf( beanA.getWupPlaceOfWork() ) );
		}

		/* Mapping: "wupEmpLiabLmt" -> "limit" */
		if( !Utils.isEmpty( beanA.getWupEmpLiabLmt() ) ){
			beanB.getEmpTypeDetails().get( 0 ).setLimit( beanA.getWupEmpLiabLmt() );
		}

		return dest;
	}

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.WorkmenCompVO initializeDeepVO( com.rsaame.pas.vo.svc.TTrnWctplUnnamedPersonQuoHolder beanA, com.rsaame.pas.vo.bus.WorkmenCompVO beanB ){
		BeanUtils.initializeBeanField( "empTypeDetails[]", beanB, 1 );
		BeanUtils.initializeBeanField( "locationVO", beanB );
		BeanUtils.initializeBeanField( "locationVO.address", beanB );
		return beanB;
	}
}
