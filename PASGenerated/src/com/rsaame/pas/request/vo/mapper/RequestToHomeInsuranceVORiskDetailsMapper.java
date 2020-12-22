/**
 * 
 */
package com.rsaame.pas.request.vo.mapper;

import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.utils.BeanUtils;
import com.mindtree.ruc.cmn.utils.HTTPUtils;
import com.mindtree.ruc.cmn.utils.Utils;

/**
 * @author Sarath Varier
 *
 */
/**
 * Mapper class for:
 * <ol>
 * <li>javax.servlet.http.HttpServletRequest</li>
 * <li>com.rsaame.pas.vo.bus.HomeInsuranceVO</li>
 * </ol>
 * Get an instance of this class by making a call to
 * <code>BeanMapperFactory.getMapperInstance( RequestToHomeInsuranceVOMapper.class )</code>
 * .
 */
public class RequestToHomeInsuranceVORiskDetailsMapper extends BaseBeanToBeanMapper<javax.servlet.http.HttpServletRequest, com.rsaame.pas.vo.bus.HomeInsuranceVO>{

	public RequestToHomeInsuranceVORiskDetailsMapper(){
		super();
	}

	public RequestToHomeInsuranceVORiskDetailsMapper( javax.servlet.http.HttpServletRequest src, com.rsaame.pas.vo.bus.HomeInsuranceVO dest ){
		super( src, dest );
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.vo.bus.HomeInsuranceVO mapBean(){

		/* Simply return if the source is null. */
		if( Utils.isEmpty( src ) ){
			return null;
		}

		/* If the destination is null, instantiate it. */
		if( Utils.isEmpty( dest ) ){
			dest = (com.rsaame.pas.vo.bus.HomeInsuranceVO) Utils.newInstance( "com.rsaame.pas.vo.bus.HomeInsuranceVO" );
		}

		/* Cast the destination bean to a bean of type of BeanA */
		javax.servlet.http.HttpServletRequest beanA = src;

		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.vo.bus.HomeInsuranceVO beanB = dest;

		/*
		 * Initialize any deepVO inside the destination bean. This is to avoid
		 * null pointers at runtime whenever a deep field is being mapped in the
		 * destination bean.
		 */
		beanB = initializeDeepVO( beanA, beanB );

		/*
		 * A common variable that will be used to store the number of items in
		 * the case of multi-mappings.
		 */
		int noOfItems = 0;

		/* Mapping: "coverName" -> "covers[].coverName" */
		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, com.Constant.CONST_COVER_COVERNAME ).size() + 1;
		for( int i = 0; i < noOfItems; i++ ){
			if( !Utils.isEmpty( beanA.getParameter( "cover_coverName[" + i + "]" ) ) )
				beanB.getCovers().get( i ).setCoverName( beanA.getParameter( "cover_coverName[" + i + "]" ) );
		}

		/* Mapping: "coverCode" -> "covers[].coverCodes.covCode" */
		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, com.Constant.CONST_COVER_COVERNAME ).size() + 1;
		for( int i = 0; i < noOfItems; i++ ){
			com.rsaame.pas.cmn.converter.ShortStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.ShortStringConverter.class, com.Constant.CONST_BPROPS,
					com.Constant.CONST_APROPS );
			if( !Utils.isEmpty( beanA.getParameter( "cover_covCode[" + i + "]" ) ) )
				beanB.getCovers().get( i ).getCoverCodes().setCovCode( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "cover_covCode[" + i + "]" ) ) ) );
		}
		/* Mapping: "CovSubTypeCode" -> "covers[].coverCodes.covSubTypeCode" */
		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, com.Constant.CONST_COVER_COVERNAME ).size() + 1;
		for( int i = 0; i < noOfItems; i++ ){
			com.rsaame.pas.cmn.converter.ShortStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.ShortStringConverter.class, com.Constant.CONST_BPROPS,
					com.Constant.CONST_APROPS );
			if( !Utils.isEmpty( beanA.getParameter( "cover_CovSubTypeCode[" + i + "]" ) ) )
				beanB.getCovers().get( i ).getCoverCodes()
						.setCovSubTypeCode( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "cover_CovSubTypeCode[" + i + "]" ) ) ) );
		}

		/* Mapping: "CovTypeCode" -> "covers[].coverCodes.covTypeCode" */
		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, com.Constant.CONST_COVER_COVERNAME ).size() + 1;
		for( int i = 0; i < noOfItems; i++ ){
			com.rsaame.pas.cmn.converter.ShortStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.ShortStringConverter.class, com.Constant.CONST_BPROPS,
					com.Constant.CONST_APROPS );

			if( !Utils.isEmpty( beanA.getParameter( "cover_covTypeCode[" + i + "]" ) ) )
				beanB.getCovers().get( i ).getCoverCodes()
						.setCovTypeCode( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "cover_covTypeCode[" + i + "]" ) ) ) );
		}
		/* Mapping: "BasicRskCode" -> "covers[].riskCodes.basicRskCode" */
		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, com.Constant.CONST_COVER_COVERNAME ).size() + 1;
		for( int i = 0; i < noOfItems; i++ ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, com.Constant.CONST_BPROPS,
					com.Constant.CONST_APROPS );

			if( !Utils.isEmpty( beanA.getParameter( "cover_basicRskCode[" + i + "]" ) ) )
				beanB.getCovers().get( i ).getRiskCodes()
						.setBasicRskCode( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "cover_basicRskCode[" + i + "]" ) ) ) );
		}

		/* Mapping: "RiskCode" -> "covers[].riskCodes.riskCode" */
		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, com.Constant.CONST_COVER_COVERNAME ).size() + 1;
		for( int i = 0; i < noOfItems; i++ ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, com.Constant.CONST_BPROPS,
					com.Constant.CONST_APROPS );
			if( !Utils.isEmpty( beanA.getParameter( "cover_riskCode[" + i + "]" ) ) )
				beanB.getCovers().get( i ).getRiskCodes().setRiskCode( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "cover_riskCode[" + i + "]" ) ) ) );
		}

		/* Mapping: "riskCat" -> "covers[].riskCodes.riskCat" */
		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, com.Constant.CONST_COVER_COVERNAME ).size() + 1;
		for( int i = 0; i < noOfItems; i++ ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, com.Constant.CONST_BPROPS,
					com.Constant.CONST_APROPS );

			if( !Utils.isEmpty( beanA.getParameter( "cover_riskCat[" + i + "]" ) ) )
				beanB.getCovers().get( i ).getRiskCodes().setRiskCat( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "cover_riskCat[" + i + "]" ) ) ) );
		}

		/* Mapping: "riskType" -> "covers[].riskCodes.riskType" */
		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, com.Constant.CONST_COVER_COVERNAME ).size() + 1;
		for( int i = 0; i < noOfItems; i++ ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, com.Constant.CONST_BPROPS,
					com.Constant.CONST_APROPS );
			if( !Utils.isEmpty( beanA.getParameter( com.Constant.CONST_COVER_RISKTYPE_END + i + "]" ) ) )
				beanB.getCovers().get( i ).getRiskCodes().setRiskType( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( com.Constant.CONST_COVER_RISKTYPE_END + i + "]" ) ) ) );
		}

		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, com.Constant.CONST_COVER_COVERNAME ).size() + 1;
		for( int i = 0; i < noOfItems; i++ ){
			com.rsaame.pas.cmn.converter.BigDecimalStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalStringConverter.class, "", "" );

			if( !Utils.isEmpty( beanA.getParameter( "cover_id[" + i + "]" ) ) )
				beanB.getCovers().get( i ).getRiskCodes().setRskId( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "cover_id[" + i + "]" ) ) ) );
		}

		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, com.Constant.CONST_COVER_COVERNAME ).size() + 1;
		for( int i = 0; i < noOfItems; i++ ){
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "",
					"format=yyyy-MM-dd HH:mm:ss.SSS" );

			if( !Utils.isEmpty( beanA.getParameter( "cover_vsd[" + i + "]" ) ) )
				beanB.getCovers().get( i ).setVsd( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "cover_vsd[" + i + "]" ) ) ) );
		}

		/* Mapping: "coverSI" -> "covers[].sumInsured.sumInsured" */
		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, com.Constant.CONST_COVER_COVERNAME ).size() + 1;
		for( int i = 0; i < noOfItems; i++ ){
			if( !Utils.isEmpty( beanA.getParameter( com.Constant.CONST_COVER_SI_END + i + "]" ) ) ){
				com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, com.Constant.CONST_BPROPS,
						com.Constant.CONST_APROPS );
				beanB.getCovers().get( i ).getSumInsured().setSumInsured( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( com.Constant.CONST_COVER_SI_END + i + "]" ) ) ) );
				if( beanA.getParameter( com.Constant.CONST_COVER_RISKTYPE_END + i + "]" ).equals( "32" ) && !Utils.isEmpty( beanA.getParameter( "riskCover32_sumInsured[" + i + "]" ) ) ){
					beanB.getCovers().get( i ).getSumInsured()
							.setSumInsured( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "riskCover32_sumInsured[" + i + "]" ) ) ) );
				}
			}
		}

		/* Mapping: "coverSI" -> "covers[].sumInsured.sumInsured" */
		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, com.Constant.CONST_COVER_COVERNAME ).size() + 1;
		for( int i = 0; i < noOfItems; i++ ){
			if( !Utils.isEmpty( beanA.getParameter( com.Constant.CONST_COVER_PRMVALUE_END + i + "]" ) ) ){
				beanB.getCovers().get( i ).getSumInsured().seteDesc( beanA.getParameter( com.Constant.CONST_COVER_PRMVALUE_END + i + "]" ) );
			}
		}

		/* Mapping: "prmAmt" -> "covers[].premiumAmt" */
		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, com.Constant.CONST_COVER_COVERNAME ).size() + 1;
		for( int i = 0; i < noOfItems; i++ ){
			if( !Utils.isEmpty( beanA.getParameter( "cover_premium[" + i + "]" ) ) )
			/*&& (!Utils.isEmpty(beanA
					.getParameter(com.Constant.CONST_COVER_SI_END + i + "]"))))*/{
				com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, com.Constant.CONST_BPROPS,
						com.Constant.CONST_APROPS );
				beanB.getCovers().get( i ).setPremiumAmt( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "cover_premium[" + i + "]" ) ) ) );
			}
		}

		/* Mapping: "discOrLoad" -> "covers[].discOrLoadPerc" */
		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, com.Constant.CONST_COVER_COVERNAME ).size() + 1;
		for( int i = 0; i < noOfItems; i++ ){
			if( ( !Utils.isEmpty( beanA.getParameter( "cover_discount[" + i + "]" ) ) ) && ( !Utils.isEmpty( beanA.getParameter( com.Constant.CONST_COVER_SI_END + i + "]" ) ) ) ){
				com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, com.Constant.CONST_BPROPS,
						com.Constant.CONST_APROPS );
				beanB.getCovers().get( i ).setDiscOrLoadPerc( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "cover_discount[" + i + "]" ) ) ) );
			}
			else{

				// for buiding_discount mapping
				if( ( !Utils.isEmpty( beanA.getParameter( "building_discount[" + i + "]" ) ) ) && ( !Utils.isEmpty( beanA.getParameter( "building_SI[" + i + "]" ) ) ) ){
					com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class,
							com.Constant.CONST_BPROPS, com.Constant.CONST_APROPS );
					beanB.getBuildingDetails().setDiscOrLoadPerc( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "building_discount[" + i + "]" ) ) ) );
				}
			}
		}

		/* Mapping: "cover_isFreeCover" -> "covers[].sumInsured.promoCover" */
		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, com.Constant.CONST_COVER_COVERNAME ).size() + 1;
		for( int i = 0; i < noOfItems; i++ ){
			if( !Utils.isEmpty( beanA.getParameter( "cover_isFreeCover[" + i + "]" ) ) ){
				beanB.getCovers().get( i ).getSumInsured().setPromoCover( Boolean.valueOf( beanA.getParameter( "cover_isFreeCover[" + i + "]" ) ) );
			}
		}

		if( !Utils.isEmpty( beanA.getParameter( "building_covCode[0]" ) ) ){
			com.rsaame.pas.cmn.converter.ShortStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.ShortStringConverter.class, com.Constant.CONST_BPROPS,
					com.Constant.CONST_APROPS );
			beanB.getBuildingDetails().getCoverCodes().setCovCode( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "building_covCode[0]" ) ) ) );
		}
		if( !Utils.isEmpty( beanA.getParameter( "building_CovSubTypeCode[0]" ) ) ){
			com.rsaame.pas.cmn.converter.ShortStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.ShortStringConverter.class, com.Constant.CONST_BPROPS,
					com.Constant.CONST_APROPS );
			beanB.getBuildingDetails().getCoverCodes().setCovSubTypeCode( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "building_CovSubTypeCode[0]" ) ) ) );
		}

		if( !Utils.isEmpty( beanA.getParameter( "building_covTypeCode[0]" ) ) ){
			com.rsaame.pas.cmn.converter.ShortStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.ShortStringConverter.class, com.Constant.CONST_BPROPS,
					com.Constant.CONST_APROPS );
			beanB.getBuildingDetails().getCoverCodes().setCovTypeCode( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "building_covTypeCode[0]" ) ) ) );
		}

		if( !Utils.isEmpty( beanA.getParameter( "building_basicRskCode[0]" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, com.Constant.CONST_BPROPS,
					com.Constant.CONST_APROPS );
			beanB.getBuildingDetails().getRiskCodes().setBasicRskCode( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "building_basicRskCode[0]" ) ) ) );
		}
		if( !Utils.isEmpty( beanA.getParameter( "building_riskCode[0]" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, com.Constant.CONST_BPROPS,
					com.Constant.CONST_APROPS );
			beanB.getBuildingDetails().getRiskCodes().setRiskCode( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "building_riskCode[0]" ) ) ) );
		}
		if( !Utils.isEmpty( beanA.getParameter( "building_riskCat[0]" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, com.Constant.CONST_BPROPS,
					com.Constant.CONST_APROPS );
			beanB.getBuildingDetails().getRiskCodes().setRiskCat( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "building_riskCat[0]" ) ) ) );
		}
		if( !Utils.isEmpty( beanA.getParameter( "building_riskType[0]" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, com.Constant.CONST_BPROPS,
					com.Constant.CONST_APROPS );
			beanB.getBuildingDetails().getRiskCodes().setRiskType( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "building_riskType[0]" ) ) ) );
		}
		if( !Utils.isEmpty( beanA.getParameter( com.Constant.CONST_BUILDING_SI_0_END ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, com.Constant.CONST_BPROPS,
					com.Constant.CONST_APROPS );
			beanB.getBuildingDetails().getSumInsured().setSumInsured( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( com.Constant.CONST_BUILDING_SI_0_END ) ) ) );
		}
		if( ( !Utils.isEmpty( beanA.getParameter( com.Constant.CONST_BUILDING_SI_0_END ) ) ) && ( !Utils.isEmpty( beanA.getParameter( "building_premium[0]" ) ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, com.Constant.CONST_BPROPS,
					com.Constant.CONST_APROPS );
			if( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( com.Constant.CONST_BUILDING_SI_0_END ) ) ) > 0 ){
				beanB.getBuildingDetails().setPremiumAmt( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "building_premium[0]" ) ) ) );
			}
		}
		if( ( !Utils.isEmpty( beanA.getParameter( com.Constant.CONST_BUILDING_SI_0_END ) ) ) && ( !Utils.isEmpty( beanA.getParameter( "building_discount[0]" ) ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, com.Constant.CONST_BPROPS,
					com.Constant.CONST_APROPS );
			beanB.getBuildingDetails().setDiscOrLoadPerc( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "building_discount[0]" ) ) ) );
		}
		/*
		 * Emirates dropdown fix
		 */
		if( !Utils.isEmpty( src.getParameter( "home_emirates" ) ) ){
			beanB.getBuildingDetails().setEmirates( beanA.getParameter( "home_emirates" ) );
		}
		if( !Utils.isEmpty( src.getParameter( "home_area" ) ) ){
			beanB.getBuildingDetails().setArea( beanA.getParameter( "home_area" ) );
		}
		if( !Utils.isEmpty( src.getParameter( "home_area_dropdown" ) ) ){
			com.rsaame.pas.cmn.converter.ShortStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.ShortStringConverter.class, com.Constant.CONST_BPROPS,
					com.Constant.CONST_APROPS );
			beanB.getBuildingDetails().setGeoAreaCode( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "home_area_dropdown" ) ) ) );
		}
		if( !Utils.isEmpty( src.getParameter( "home_other" ) ) ){
			beanB.getBuildingDetails().setOtherDetails( beanA.getParameter( "home_other" ) );
		}
		if( !Utils.isEmpty( src.getParameter( "home_building_name" ) ) ){
			beanB.getBuildingDetails().setBuildingname( beanA.getParameter( "home_building_name" ) );
		}
		if( !Utils.isEmpty( src.getParameter( "home_property_type" ) ) ){
			com.rsaame.pas.cmn.converter.ShortStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.ShortStringConverter.class, com.Constant.CONST_BPROPS,
					com.Constant.CONST_APROPS );
			beanB.getBuildingDetails().setTypeOfProperty( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "home_property_type" ) ) ) );
		}
		if( !Utils.isEmpty( src.getParameter( "home_flat_villa" ) ) ){
			beanB.getBuildingDetails().setFlatVillaNo( beanA.getParameter( "home_flat_villa" ) );
		}
		
		
		
		if( !Utils.isEmpty( src.getParameter( com.Constant.CONST_HOME_MORTGAGEE_NAME ) ) ){
			if( src.getParameter( com.Constant.CONST_HOME_MORTGAGEE_NAME ).equals( "Others" ))
				beanB.getBuildingDetails().setMortgageeName( beanA.getParameter( "home_mortg_name" ) );
			else
				beanB.getBuildingDetails().setMortgageeName( beanA.getParameter( com.Constant.CONST_HOME_MORTGAGEE_NAME ) );
		}
		if( !Utils.isEmpty( src.getParameter( "ownershipStatus" ) ) ){
			com.rsaame.pas.cmn.converter.ShortStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.ShortStringConverter.class, com.Constant.CONST_BPROPS,
					com.Constant.CONST_APROPS );
			beanB.getBuildingDetails().setOwnershipStatus( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "ownershipStatus" ) ) ) );
		}
		if( !Utils.isEmpty( src.getParameter( "buildingVSD" ) ) ){
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "",
					"format=yyyy-MM-dd HH:mm:ss.SSS" );
			beanB.getBuildingDetails().setVsd( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "buildingVSD" ) ) ) );
		}
		if( !Utils.isEmpty( src.getParameter( "buildingRskId" ) ) ){
			com.rsaame.pas.cmn.converter.BigDecimalStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalStringConverter.class, "", "" );
			beanB.getBuildingDetails().getRiskCodes().setRskId( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "buildingRskId" ) ) ) );
		}

		/* Mapping: "total_premium" -> "premiumVO.premiumAmt" */
		if( !Utils.isEmpty( src.getParameter( "totalPremium" ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.getPremiumVO().setPremiumAmt( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "totalPremium" ) ) ) );
		}

		/* Mapping: "premium_payable" -> "premiumVO.premiumAmtActual" */
		if( !Utils.isEmpty( src.getParameter( "payable_premium" ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.getPremiumVO().setPremiumAmtActual( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "payable_premium" ) ) ) );
		}

		/* Mapping: "currency" -> "premiumVO.currency" */
		if( !Utils.isEmpty( src.getParameter( "currency" ) ) ){
			beanB.getPremiumVO().setCurrency( beanA.getParameter( "currency" ) );
		}

		/* Mapping: "govt_tax" -> "premiumVO.govtTax" */
		if( !Utils.isEmpty( src.getParameter( "govtTax" ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.getPremiumVO().setGovtTax( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "govtTax" ) ) ) );
		}

		/* Mapping: "vat_tax" -> "premiumVO.vattTax" */ 
		//142244
		if( !Utils.isEmpty( src.getParameter( "vatTax" ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.getPremiumVO().setVatTax( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "vatTax" ) ) ) );
		}
		/* Mapping: "vat_tax_perc" -> "premiumVO.vattTaxPerc" */ 
		if( !Utils.isEmpty( src.getParameter( "vatTaxPerc" ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.getPremiumVO().setVatTaxPerc( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "vatTaxPerc" ) ) ) );
		}
		
		/* Mapping: "vat_tax_code" -> "premiumVO.vatCode" */ 
		if( !Utils.isEmpty( src.getParameter( "vatCode" ) ) ){
			com.rsaame.pas.cmn.converter.IntegerStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.IntegerStringConverter.class, "", "" );
			beanB.getPremiumVO().setVatCode( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "vatCode" ) ) ) );
		}
		
		/* Mapping: "special_discount" -> "premiumVO.specialDiscount" */
		if( !Utils.isEmpty( src.getParameter( "special_discount" ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.getPremiumVO().setSpecialDiscount( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "special_discount" ) ) ) );
		}

		/* Mapping: "policy_fees" -> "premiumVO.policyFees" */
		if( !Utils.isEmpty( src.getParameter( "policyFees" ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.getPremiumVO().setPolicyFees( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "policyFees" ) ) ) );
		}

		/* Mapping: "disc_or_load_perc" -> "premiumVO.discOrLoadPerc" */
		if( !Utils.isEmpty( src.getParameter( "discountPercentage" ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.getPremiumVO().setDiscOrLoadPerc( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "discountPercentage" ) ) ) );
		}

		/* Mapping: "disc_or_load_amt" -> "premiumVO.discOrLoadAmt" */
		if( !Utils.isEmpty( src.getParameter( "discountAmount" ) ) ){
			com.rsaame.pas.cmn.converter.BigDecimalStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalStringConverter.class, "", "" );
			beanB.getPremiumVO().setDiscOrLoadAmt( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "discountAmount" ).replaceAll(",", "") ) ) );
		}

		/* Mapping "commissionPremium" -> "commission" */
		if( !Utils.isEmpty( src.getParameter( "commissionPremium" ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.setCommission( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "commissionPremium" ) ) ) );
		}

		/* Mapping "promoDisc" -> "premiumVO.promoDiscPerc" */
		if( !Utils.isEmpty( src.getParameter( "promoDiscount" ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.getPremiumVO().setPromoDiscPerc( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "promoDiscount" ) ) ) );
		}

		/* Mapping: "cover_checkBox" -> "covers[].isCovered" */
		noOfItems = HTTPUtils.getMatchingMultiReqParamKeys( beanA, com.Constant.CONST_COVER_COVERNAME ).size() + 1;
		for( int i = 0; i < noOfItems; i++ ){
			if( !Utils.isEmpty( beanA.getParameter( com.Constant.CONST_COVER_CHECKBOX_END + i + "]" ) ) && beanA.getParameter( com.Constant.CONST_COVER_CHECKBOX_END + i + "]" ).equalsIgnoreCase( "on" ) ){
				beanB.getCovers().get( i ).setIsCovered( beanA.getParameter( com.Constant.CONST_COVER_CHECKBOX_END + i + "]" ) );
			}
			else if( !Utils.isEmpty( beanA.getParameter( com.Constant.CONST_COVER_PRMVALUE_END + i + "]" ) ) || !Utils.isEmpty( beanA.getParameter( com.Constant.CONST_COVER_SI_END + i + "]" ) ) ){
				beanB.getCovers().get( i ).setIsCovered( "on" );
			}
			else{
				beanB.getCovers().get( i ).setIsCovered( "off" );
			}
		}
		
		/* Mapping: "total_premium" -> "premiumVO.premiumAmt" */
		if( !Utils.isEmpty( src.getParameter( "vatablePrm" ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.getPremiumVO().setVatablePrm( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "vatablePrm" ) ) ) );
		}
		//Added for Informap Changes
		if( !Utils.isEmpty( src.getParameter( "home_latitude" ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.getBuildingDetails().setLatitude( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "home_latitude" ) ) ) );
		}
		if( !Utils.isEmpty( src.getParameter( "home_longitude" ) ) ){
			com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
			beanB.getBuildingDetails().setLongitude( converter.getTypeOfA().cast( converter.getAFromB( beanA.getParameter( "home_longitude" ) ) ) );
		}
		if( !Utils.isEmpty( src.getParameter( "home_address" ) ) ){
			beanB.getBuildingDetails().setAddress( beanA.getParameter( "home_address" ) );
		}
		if( !Utils.isEmpty( src.getParameter( "home_informap_status" ) ) ){
			beanB.getBuildingDetails().setInforMapStatus( beanA.getParameter( "home_informap_status" ) );
		}
		// 11645 - Home Digital API
		if (!Utils.isEmpty(src.getParameter("home_total_floors"))) {
			beanB.getBuildingDetails().setTotalNoFloors(Short.valueOf(beanA.getParameter("home_total_floors")));
		}

		if (!Utils.isEmpty(src.getParameter("home_total_Rooms"))) {
			beanB.getBuildingDetails().setTotalNoRooms(Short.valueOf(beanA.getParameter("home_total_Rooms")));
		}
		
		return dest;

	}

	/**
	 * This method initialises all the complex objects and collection instances
	 * we need for mapping.
	 */
	private static com.rsaame.pas.vo.bus.HomeInsuranceVO initializeDeepVO( javax.servlet.http.HttpServletRequest beanA, com.rsaame.pas.vo.bus.HomeInsuranceVO beanB ){
		BeanUtils.initializeBeanField( "covers[].sumInsured", beanB, ( HTTPUtils.getMatchingMultiReqParamKeys( beanA, com.Constant.CONST_COVER_COVERNAME_END ).size() + 1 ) );
		BeanUtils.initializeBeanField( "covers[].coverCodes", beanB, ( HTTPUtils.getMatchingMultiReqParamKeys( beanA, com.Constant.CONST_COVER_COVERNAME_END ).size() + 1 ) );
		BeanUtils.initializeBeanField( "covers[].riskCodes", beanB, ( HTTPUtils.getMatchingMultiReqParamKeys( beanA, com.Constant.CONST_COVER_COVERNAME_END ).size() + 1 ) );
		BeanUtils.initializeBeanField( "buildingDetails.sumInsured", beanB );
		BeanUtils.initializeBeanField( "buildingDetails.coverCodes", beanB );
		BeanUtils.initializeBeanField( "buildingDetails.riskCodes", beanB );
		BeanUtils.initializeBeanField( "premiumVO", beanB );

		return beanB;
	}
}
