/*
* THIS IS A GENERATED CLASS. THIS CLASS SHOULD NOT BE EDITED.
*/
package com.rsaame.pas.pojo.mapper;

import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.BeanUtils;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.vo.bus.WCNammedEmployeeVO;

/**
 * Mapper class for:<ol>
 * <li>com.rsaame.pas.vo.svc.TTrnWctplPersonQuoHolder</li>
 * <li>com.rsaame.pas.vo.bus.WorkmenCompVO</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( WorkmenCompVOToTTrnWctplPersonHolderMapperReverse.class )</code>.
 */
public class WorkmenCompVOToTTrnWctplPersonHolderMapperReverse extends BaseBeanToBeanMapper<com.rsaame.pas.vo.svc.TTrnWctplPersonQuoHolder, com.rsaame.pas.vo.bus.WorkmenCompVO>{
	private final Logger log = Logger.getLogger( this.getClass() );

	public WorkmenCompVOToTTrnWctplPersonHolderMapperReverse(){
		super();
	}

	public WorkmenCompVOToTTrnWctplPersonHolderMapperReverse( com.rsaame.pas.vo.svc.TTrnWctplPersonQuoHolder src, com.rsaame.pas.vo.bus.WorkmenCompVO dest ){
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

		log.info( "Mapping ttrn wctpl person to wc vo monoline" );

		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}

		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.WorkmenCompVO) Utils.newInstance( "com.rsaame.pas.vo.bus.WorkmenCompVO" );
		}

		/* Cast the destination bean to a bean of type of BeanA */
		com.rsaame.pas.vo.svc.TTrnWctplPersonQuoHolder beanA = src;

		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.WorkmenCompVO beanB = dest;

		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO( beanA, beanB );

		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;

/*		if(!Utils.isEmpty( beanB.getWcEmployeeDetails() )){
			beanB.setWcEmployeeDetails(new com.mindtree.ruc.cmn.utils.List<WCNammedEmployeeVO>( WCNammedEmployeeVO.class ));
			beanB.getWcEmployeeDetails().add( new WCNammedEmployeeVO() );
		}*/
		/* Mapping: "wprValidityStartDate" -> "wcEmployeeDetails[].vsd" */
		if( !Utils.isEmpty( beanA.getWprValidityStartDate() ) ){
			beanB.getWcEmployeeDetails().get( 0 ).setVsd( beanA.getWprValidityStartDate() );
		}

		/* Mapping: "wprId" -> "wcEmployeeDetails[].wprWCId" */
		if( !Utils.isEmpty( beanA.getWprId() ) ){
			beanB.getWcEmployeeDetails().get( 0 ).setWprWCId( beanA.getWprId() );
		}

		/* Mapping: "wprBasicRiskId" -> "wcEmployeeDetails[].wprBasicRiskId" */
		if( !Utils.isEmpty( beanA.getWprBasicRiskId() ) ){
			beanB.getWcEmployeeDetails().get( 0 ).setBasicRiskId( beanA.getWprBasicRiskId() );
		}

		/* Mapping: "wprRskCode" -> "wcEmployeeDetails[].riskCode" */
		if( !Utils.isEmpty( beanA.getWprRskCode() ) ){
			com.rsaame.pas.cmn.converter.LongIntegerConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongIntegerConverter.class, "", "" );
			beanB.getWcEmployeeDetails().get( 0 ).setRiskCode( converter.getTypeOfA().cast( converter.getAFromB( beanA.getWprRskCode() ) ) );
		}

		/* Mapping: "wprRtCode" -> "wcEmployeeDetails[].riskType" */
		if( !Utils.isEmpty( beanA.getWprRtCode() ) ){
			com.rsaame.pas.cmn.converter.LongIntegerConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongIntegerConverter.class, "", "" );
			beanB.getWcEmployeeDetails().get( 0 ).setRiskType( converter.getTypeOfA().cast( converter.getAFromB( beanA.getWprRtCode() ) ) );
		}

		/* Mapping: "wprRcCode" -> "wcEmployeeDetails[].riskCategory" */
		if( !Utils.isEmpty( beanA.getWprRcCode() ) ){
			com.rsaame.pas.cmn.converter.LongIntegerConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongIntegerConverter.class, "", "" );
			beanB.getWcEmployeeDetails().get( 0 ).setRiskCategory( converter.getTypeOfA().cast( converter.getAFromB( beanA.getWprRcCode() ) ) );
		}

		/* Mapping: "wprEName" -> "wcEmployeeDetails[].empName" */
		if( !Utils.isEmpty( beanA.getWprEName() ) ){
			beanB.getWcEmployeeDetails().get( 0 ).setEmpName( beanA.getWprEName() );
		}

		/* Mapping: "wprBasicRskCode" -> "wcEmployeeDetails[].wprBasicRskCode" */
		if( !Utils.isEmpty( beanA.getWprBasicRskCode() ) ){
			beanB.getWcEmployeeDetails().get( 0 ).setBasicRiskcode( beanA.getWprBasicRskCode() );
		}

		/* Mapping: "wprTradeGroup" -> "locationVO.occTradeGroup" */
		if( !Utils.isEmpty( beanA.getWprTradeGroup() ) ){
			com.rsaame.pas.cmn.converter.LongIntegerConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongIntegerConverter.class, "", "" );
			beanB.getLocationVO().setOccTradeGroup( converter.getTypeOfA().cast( converter.getAFromB( beanA.getWprTradeGroup() ) ) );
		}

		/* Mapping: "wprTerCode" -> "locationVO.address.locOverrideTer" */
		if( !Utils.isEmpty( beanA.getWprTerCode() ) ){
			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.getLocationVO().getAddress().setLocOverrideTer( converter.getTypeOfA().cast( converter.getAFromB( beanA.getWprTerCode() ) ) );
		}

		/* Mapping: "wprJurCode" -> "locationVO.address.locOverrideJur" */
		if( !Utils.isEmpty( beanA.getWprJurCode() ) ){
			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.getLocationVO().getAddress().setLocOverrideJur( converter.getTypeOfA().cast( converter.getAFromB( beanA.getWprJurCode() ) ) );
		}

		/* Mapping: "wprFreeZone" -> "locationVO.freeZone" */
		if( !Utils.isEmpty( beanA.getWprFreeZone() ) ){
			beanB.getLocationVO().setFreeZone( beanA.getWprFreeZone() );
		}

		return dest;
	}

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.WorkmenCompVO initializeDeepVO( com.rsaame.pas.vo.svc.TTrnWctplPersonQuoHolder beanA, com.rsaame.pas.vo.bus.WorkmenCompVO beanB ){
		BeanUtils.initializeBeanField( "wcEmployeeDetails[]", beanB, 1 );
		BeanUtils.initializeBeanField( "locationVO", beanB );
		BeanUtils.initializeBeanField( "locationVO.address", beanB );
		return beanB;
	}
}
