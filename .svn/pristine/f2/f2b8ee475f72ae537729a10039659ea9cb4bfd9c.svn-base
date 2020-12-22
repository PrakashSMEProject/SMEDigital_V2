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
 * <li>com.rsaame.pas.vo.svc.TTrnPremiumVOHolder</li>
 * </ol>
 * Get an instance of this class by making a call to <code>BeanMapperFactory.getMapperInstance( WorkmenCompVOToTTrnPremiumVOHolderMapper.class )</code>.
 */
public class WorkmenCompVOToTTrnPremiumVOHolderMapper extends BaseBeanToBeanMapper<com.rsaame.pas.vo.bus.WorkmenCompVO, com.rsaame.pas.vo.svc.TTrnPremiumVOHolder>{
	private final Logger log = Logger.getLogger( this.getClass() );

	public WorkmenCompVOToTTrnPremiumVOHolderMapper(){
		super();
	}

	public WorkmenCompVOToTTrnPremiumVOHolderMapper( com.rsaame.pas.vo.bus.WorkmenCompVO src, com.rsaame.pas.vo.svc.TTrnPremiumVOHolder dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.svc.TTrnPremiumVOHolder mapBean(){

		log.info( "WC Monoline VO to prm holder started" );
		
		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}

		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.svc.TTrnPremiumVOHolder) Utils.newInstance( "com.rsaame.pas.vo.svc.TTrnPremiumVOHolder" );
		}

		/* Cast the destination bean to a bean of type of BeanA */
		com.rsaame.pas.vo.bus.WorkmenCompVO beanA = src;

		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.svc.TTrnPremiumVOHolder beanB = dest;

		/* Initialize any deepVO inside the destination bean. This is to avoid null pointers at runtime whenever a deep field is being mapped in the destination bean. */
		beanB = initializeDeepVO( beanA, beanB );

		/* A common variable that will be used to store the number of items in the case of multi-mappings. */
		int noOfItems = beanA.getEmpTypeDetails().size();

		/* Mapping: "commonVO.policyId" -> "prmPolicyId" */
		if( !Utils.isEmpty( beanA.getCommonVO() ) && !Utils.isEmpty( beanA.getCommonVO().getPolicyId() ) ){
			beanB.setPrmPolicyId( beanA.getCommonVO().getPolicyId() );
		}

		/* Mapping: "Integer.valueOf( Utils.getSingleValueAppConfig( "WC_BASIC_RISK_CODE" ) )" -> "prmBasicRskCode" */
		if( !Utils.isEmpty( Integer.valueOf( Utils.getSingleValueAppConfig( "WC_BASIC_RISK_CODE" ) ) ) ){
			beanB.setPrmBasicRskCode( Integer.valueOf( Utils.getSingleValueAppConfig( "WC_BASIC_RISK_CODE" ) ) );
		}

		/* Mapping: "empTypeDetails[].riskId" -> "prmBasicRskId" */
		//noOfItems = beanA.getEmpTypeDetails().size();
		for( int i = 0; i < noOfItems; i++ ){
			com.rsaame.pas.cmn.converter.BigDecimalLongConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalLongConverter.class, "", "" );
			beanB.setPrmBasicRskId( converter.getTypeOfA().cast( converter.getAFromB( beanA.getEmpTypeDetails().get( i ).getRiskId() ) ) );
		}

		/* Mapping: "Integer.valueOf( Utils.getSingleValueAppConfig( "WC_RISK_CODE" ) )" -> "prmRskCode" */
		if( !Utils.isEmpty( Integer.valueOf( Utils.getSingleValueAppConfig( "WC_RISK_CODE" ) ) ) ){
			beanB.setPrmRskCode( Integer.valueOf( Utils.getSingleValueAppConfig( "WC_RISK_CODE" ) ) );
		}

		/* Mapping: "empTypeDetails[].riskId" -> "prmRskId" */
		//noOfItems = beanA.getWcEmployeeDetails().size();
		for( int i = 0; i < noOfItems; i++ ){
			com.rsaame.pas.cmn.converter.BigDecimalLongConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalLongConverter.class, "", "" );
			beanB.setPrmRskId( converter.getTypeOfA().cast( converter.getAFromB( beanA.getEmpTypeDetails().get( i ).getRiskId() ) ) );
		}

		/* Mapping: "Short.valueOf( Utils.getSingleValueAppConfig( "WC_BASIC_COVER" ) )" -> "prmCovCode" */
		if( !Utils.isEmpty( Short.valueOf( Utils.getSingleValueAppConfig( "WC_BASIC_COVER" ) ) ) ){
			beanB.setPrmCovCode( Short.valueOf( Utils.getSingleValueAppConfig( "WC_BASIC_COVER" ) ) );
		}

		/* Mapping: "Short.valueOf( Utils.getSingleValueAppConfig( "WC_COVER_TYPE" ) )" -> "prmCtCode" */
		if( !Utils.isEmpty( Short.valueOf( Utils.getSingleValueAppConfig( "WC_COVER_TYPE" ) ) ) ){
			beanB.setPrmCtCode( Short.valueOf( Utils.getSingleValueAppConfig( "WC_COVER_TYPE" ) ) );
		}

		/* Mapping: "Short.valueOf( Utils.getSingleValueAppConfig( "WC_COVER_SUB_TYPE" ) )" -> "prmCstCode" */
		if( !Utils.isEmpty( Short.valueOf( Utils.getSingleValueAppConfig( "WC_COVER_SUB_TYPE" ) ) ) ){
			beanB.setPrmCstCode( Short.valueOf( Utils.getSingleValueAppConfig( "WC_COVER_SUB_TYPE" ) ) );
		}

		/* Mapping: "empTypeDetails[].vsd" -> "prmValidityStartDate" */
		//noOfItems = beanA.getWcEmployeeDetails().size();
		for( int i = 0; i < noOfItems; i++ ){
			beanB.setPrmValidityStartDate( beanA.getEmpTypeDetails().get( i ).getVsd() );
		}

		/* Mapping: "policyClassCode" -> "prmClCode" */
		if( !Utils.isEmpty( beanA.getPolicyClassCode() ) ){
			beanB.setPrmClCode( beanA.getPolicyClassCode().shortValue() ) ; //Short.valueOf( Utils.getSingleValueAppConfig( "WC_CLASS" ) ) );
		}

		/* Mapping: "policyType" -> "prmPtCode" */
		if( !Utils.isEmpty( beanA.getPolicyType() ) ){
			beanB.setPrmPtCode( beanA.getPolicyType().shortValue() ); // Short.valueOf( Utils.getSingleValueAppConfig( "WC_POLICY_TYPE" ) ) );
		}

		/* Mapping: "empTypeDetails[].wageroll" -> "prmSumInsured" */
		//noOfItems = beanA.getWcEmployeeDetails().size();
		for( int i = 0; i < noOfItems; i++ ){
			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.setPrmSumInsured( converter.getTypeOfA().cast( converter.getAFromB( beanA.getEmpTypeDetails().get( i ).getWageroll() ) ) );
		}

		/* Mapping: "empTypeDetails[].premium.premiumAmt" -> "prmPremium" */
		//noOfItems = beanA.getWcEmployeeDetails().size();
		for( int i = 0; i < noOfItems; i++ ){
			com.rsaame.pas.cmn.converter.BigDecimalStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalStringConverter.class, "", "" );
			beanB.setPrmPremium( converter.getAFromB( String.valueOf( beanA.getEmpTypeDetails().get( i ).getPremium().getPremiumAmt() ) ) );
		}

		/* Mapping: "empTypeDetails[].deductibles" -> "prmCompulsoryExcess" */
		//noOfItems = beanA.getWcEmployeeDetails().size();
		for( int i = 0; i < noOfItems; i++ ){
			beanB.setPrmCompulsoryExcess( beanA.getEmpTypeDetails().get( i ).getDeductibles() );
		}

		/* Mapping: "commonVO.endtId" -> "prmEndtId" */
		if( !Utils.isEmpty( beanA.getCommonVO() ) && !Utils.isEmpty( beanA.getCommonVO().getEndtId() ) ){
			beanB.setPrmEndtId( beanA.getCommonVO().getEndtId() );
		}

		/* Mapping: "Integer.valueOf( Utils.getSingleValueAppConfig( SvcConstants.WC_RI_RSK_CODE ) )" -> "prmRiRskCode" */
		if( !Utils.isEmpty( Integer.valueOf( Utils.getSingleValueAppConfig( "WC_RI_RSK_CODE" ) ) ) ){
			beanB.setPrmRiRskCode( Integer.valueOf( Utils.getSingleValueAppConfig( "WC_RI_RSK_CODE" ) ) );
		}

		/* Mapping: "scheme.effDate" -> "prmEffectiveDate" */
		if( !Utils.isEmpty( beanA.getScheme() ) && !Utils.isEmpty( beanA.getScheme().getEffDate() ) ){
			beanB.setPrmEffectiveDate( beanA.getScheme().getEffDate() );
		}

		/* Mapping: "scheme.expiryDate" -> "prmExpiryDate" */
		if( !Utils.isEmpty( beanA.getScheme() ) && !Utils.isEmpty( beanA.getScheme().getExpiryDate() ) ){
			beanB.setPrmExpiryDate( beanA.getScheme().getExpiryDate() );
		}

		/* Mapping: "SvcConstants.PRM_SITYPE_CODE_BASE_COVER" -> "prmSitypeCode" */
		beanB.setPrmSitypeCode( new Byte( "1" ) );

		/* Mapping: "SvcConstants.PRM_FN_CODE" -> "prmFnCode" */
		beanB.setPrmFnCode( new Byte( "7" ) );

		/* Mapping: "Integer.valueOf(Utils.getSingleValueAppConfig( "APP_PRM_RI_LOC_CODE" ))" -> "prmRiLocCode" */
		if( !Utils.isEmpty( Integer.valueOf( Utils.getSingleValueAppConfig( "APP_PRM_RI_LOC_CODE" ) ) ) ){
			beanB.setPrmRiLocCode( Integer.valueOf( Utils.getSingleValueAppConfig( "APP_PRM_RI_LOC_CODE" ) ) );
		}

		/* Mapping: "empTypeDetails[].premium.premiumAmtActual" -> "prmPremiumActual" */
		//noOfItems = beanA.getWcEmployeeDetails().size();
		for( int i = 0; i < noOfItems; i++ ){
			com.rsaame.pas.cmn.converter.BigDecimalStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalStringConverter.class, "", "" );
			beanB.setPrmPremiumActual( converter.getAFromB(  String.valueOf( beanA.getEmpTypeDetails().get( i ).getPremium().getPremiumAmtActual() ) ) );
		}
		
		/* Mapping: "scheme.tariffRateType" -> "prmRateType" */
		if( !Utils.isEmpty( beanA.getScheme() ) && !Utils.isEmpty( beanA.getScheme().getTariffRateType() ) ){
			beanB.setPrmRateType( beanA.getScheme().getTariffRateType() );
		}

		return dest;
	}

	/**
	 * This method initialises all the complex objects and collection instances we need for mapping.
	 */
	private static com.rsaame.pas.vo.svc.TTrnPremiumVOHolder initializeDeepVO( com.rsaame.pas.vo.bus.WorkmenCompVO beanA, com.rsaame.pas.vo.svc.TTrnPremiumVOHolder beanB ){
		return beanB;
	}
}
