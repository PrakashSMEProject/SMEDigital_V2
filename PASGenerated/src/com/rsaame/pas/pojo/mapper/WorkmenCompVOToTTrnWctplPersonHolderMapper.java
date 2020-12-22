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
 * <li>com.rsaame.pas.vo.svc.TTrnWctplPersonQuoHolder</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( WorkmenCompVOToTTrnWctplPersonHolderMapper.class )</code>.
 */
public class WorkmenCompVOToTTrnWctplPersonHolderMapper extends BaseBeanToBeanMapper<com.rsaame.pas.vo.bus.WorkmenCompVO, com.rsaame.pas.vo.svc.TTrnWctplPersonQuoHolder>{
	private final Logger log = Logger.getLogger( this.getClass() );

	public WorkmenCompVOToTTrnWctplPersonHolderMapper(){
		super();
	}

	public WorkmenCompVOToTTrnWctplPersonHolderMapper( com.rsaame.pas.vo.bus.WorkmenCompVO src, com.rsaame.pas.vo.svc.TTrnWctplPersonQuoHolder dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.svc.TTrnWctplPersonQuoHolder mapBean(){

		log.info( "Mapper for WC to wctpl named person started" );

		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}

		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.svc.TTrnWctplPersonQuoHolder) Utils.newInstance( "com.rsaame.pas.vo.svc.TTrnWctplPersonQuoHolder" );
		}

		/* Cast the destination bean to a bean of type of BeanA */
		com.rsaame.pas.vo.bus.WorkmenCompVO beanA = src;

		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.svc.TTrnWctplPersonQuoHolder beanB = dest;

		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO( beanA, beanB );

		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = 0;

		/* Mapping: "wcEmployeeDetails[].vsd" -> "wprValidityStartDate" */
		noOfItems = beanA.getWcEmployeeDetails().size();
		for( int i = 0; i < noOfItems; i++ ){
			if( !Utils.isEmpty( beanA.getWcEmployeeDetails().get( i ).getVsd() ) ){
				beanB.setWprValidityStartDate( beanA.getWcEmployeeDetails().get( i ).getVsd() );
			}
		}

		/* Mapping: "wprWCId" -> "wprId" */
		noOfItems = beanA.getWcEmployeeDetails().size();
		for( int i = 0; i < noOfItems; i++ ){
			if( !Utils.isEmpty( beanA.getWcEmployeeDetails().get( i ).getWprWCId() ) ){
				beanB.setWprId( beanA.getWcEmployeeDetails().get( i ).getWprWCId() );
			}
		}

		/* Mapping: "commonVO.policyId" -> "wprPolicyId" */
		if( !Utils.isEmpty( beanA.getCommonVO() ) && !Utils.isEmpty( beanA.getCommonVO().getPolicyId() ) ){
			beanB.setWprPolicyId( beanA.getCommonVO().getPolicyId() );
		}

		/* Mapping: "[]" -> "wprBasicRiskId" */
		/*if(  !Utils.isEmpty( beanA.get() ) && !Utils.isEmpty( beanA.get() )  ){
			beanB.setWprBasicRiskId( beanA.getnull() ); 
		}*/

		/* Mapping: "wcEmployeeDetails[].riskId" -> "wprRskCode" */
		noOfItems = beanA.getWcEmployeeDetails().size();
		String riskCode = Utils.getSingleValueAppConfig( "WC_BASIC_RISK_CODE" );
		if( !Utils.isEmpty( riskCode ) ){
			beanB.setWprRskCode( Long.valueOf( riskCode ) );
		}

		/* Mapping: "locationVO.occTradeGroup" -> "wprRtCode" */
		/*if(  !Utils.isEmpty( beanA.getLocationVO() ) && !Utils.isEmpty( beanA.getLocationVO().getOccTradeGroup() )  ){
			beanB.setWprRtCode( beanA.getLocationVO().getOccTradeGroup() ); 
		}*/

		/* Mapping: "Short.valueOf( Utils.getSingleValueAppConfig( "WC_RISK_CATEGORY" ) )" -> "wprRcCode" */
		if( !Utils.isEmpty( Utils.getSingleValueAppConfig( "WC_RISK_CATEGORY" ) ) ){
			beanB.setWprRcCode( Long.valueOf( Utils.getSingleValueAppConfig( "WC_RISK_CATEGORY" ) ) );
		}

		/* Mapping: "wcEmployeeDetails[].empName" -> "wprEName" */
		noOfItems = beanA.getWcEmployeeDetails().size();
		for( int i = 0; i < noOfItems; i++ ){
			beanB.setWprEName( beanA.getWcEmployeeDetails().get( i ).getEmpName() );
		}

		/* Mapping: "commonVO.endtId" -> "wprEndtId" */
		if( !Utils.isEmpty( beanA.getCommonVO() ) && !Utils.isEmpty( beanA.getCommonVO().getEndtId() ) ){
			beanB.setWprEndtId( beanA.getCommonVO().getEndtId() );
		}

		/* Mapping: "Integer.valueOf( Utils.getSingleValueAppConfig( SvcConstants.WC_RI_RSK_CODE ) )" -> "wprRiRskCode" */
		if( !Utils.isEmpty( Utils.getSingleValueAppConfig( "WC_RI_RSK_CODE" ) ) ){
			beanB.setWprRiRskCode( Integer.valueOf( Utils.getSingleValueAppConfig( "WC_RI_RSK_CODE" ) ) );
		}

		/* Mapping: "Integer.valueOf( Utils.getSingleValueAppConfig( "WC_BASIC_RISK_CODE" ) )" -> "wprBasicRskCode" */
		if( !Utils.isEmpty( riskCode ) ){
			beanB.setWprBasicRskCode( Integer.valueOf( riskCode ) );
		}

		/* Mapping: "commonVO.polEffectiveDate" -> "wprStartDate" */
		if( !Utils.isEmpty( beanA.getCommonVO() ) && !Utils.isEmpty( beanA.getCommonVO().getPolEffectiveDate() ) ){
			beanB.setWprStartDate( beanA.getCommonVO().getPolEffectiveDate() );
		}

		/* Mapping: "commonVO.endtEffectiveDate" -> "wprEndDate" */
		if( !Utils.isEmpty( beanA.getCommonVO() ) && !Utils.isEmpty( beanA.getCommonVO().getEndtEffectiveDate() ) ){
			beanB.setWprEndDate( beanA.getCommonVO().getEndtEffectiveDate() );
		}

		/* Mapping: "locationVO.occTradeGroup" -> "wprTradeGroup" */
		/*if(  !Utils.isEmpty( beanA.getLocationVO() ) && !Utils.isEmpty( beanA.getLocationVO().getOccTradeGroup() )  ){
			com.rsaame.pas.cmn.converter.LongIntegerConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.LongIntegerConverter.class, "", "" );
			beanB.setWprTradeGroup( converter.getTypeOfB().cast( converter.getBFromA( beanA.getLocationVO().getOccTradeGroup() ) ) ); 
		}*/

		/* Mapping: "locationVO.address.locOverrideTer" -> "wprTerCode" */
		if( !Utils.isEmpty( beanA.getLocationVO() ) && !Utils.isEmpty( beanA.getLocationVO().getAddress() )
				&& !Utils.isEmpty( beanA.getLocationVO().getAddress().getLocOverrideTer() ) ){
			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.setWprTerCode( converter.getTypeOfB().cast( converter.getBFromA( beanA.getLocationVO().getAddress().getLocOverrideTer() ) ) );
		}

		/* Mapping: "locationVO.address.locOverrideJur" -> "wprJurCode" */
		if( !Utils.isEmpty( beanA.getLocationVO() ) && !Utils.isEmpty( beanA.getLocationVO().getAddress() )
				&& !Utils.isEmpty( beanA.getLocationVO().getAddress().getLocOverrideJur() ) ){
			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerShortConverter.class, "", "" );
			beanB.setWprJurCode( converter.getTypeOfB().cast( converter.getBFromA( beanA.getLocationVO().getAddress().getLocOverrideJur() ) ) );
		}

		/* Mapping: "locationVO.freeZone" -> "wprFreeZone" */
		if( !Utils.isEmpty( beanA.getLocationVO() ) && !Utils.isEmpty( beanA.getLocationVO().getFreeZone() ) ){
			beanB.setWprFreeZone( beanA.getLocationVO().getFreeZone() );
		}
		
		/* Mapping: "locationVO.hazardLevel" -> "wprHazard" */
		if( !Utils.isEmpty( beanA.getLocationVO() ) && !Utils.isEmpty( beanA.getLocationVO().getHazardLevel() ) ){
			beanB.setWprHazard( beanA.getLocationVO().getHazardLevel() );
		}

		return dest;
	}

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.svc.TTrnWctplPersonQuoHolder initializeDeepVO( com.rsaame.pas.vo.bus.WorkmenCompVO beanA, com.rsaame.pas.vo.svc.TTrnWctplPersonQuoHolder beanB ){
		return beanB;
	}
}
