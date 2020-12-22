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
 * Mapper class for:
 * <ol>
 * <li>com.rsaame.pas.vo.bus.LocationVO</li>
 * <li>com.rsaame.pas.dao.model.TTrnBuildingQuo</li>
 * </ol>
 * Get an instance of this class by making a call to
 * <code>BeanMapperFactory.getMapperInstance( LocVOToBuldPojoMapper.class )</code>
 * .
 */
public class LocVOToBuldPojoMapper
		extends
		BaseBeanToBeanMapper<com.rsaame.pas.vo.bus.LocationVO, com.rsaame.pas.dao.model.TTrnBuildingQuo> {
	private final Logger log = Logger.getLogger(this.getClass());

	public LocVOToBuldPojoMapper() {
		super();
	}

	public LocVOToBuldPojoMapper(com.rsaame.pas.vo.bus.LocationVO src,
			com.rsaame.pas.dao.model.TTrnBuildingQuo dest) {
		super(src, dest);
	}

	/**
	 * This method maps the contents of the source bean to a destination bean.
	 * 
	 * @param src
	 * @param dest
	 */
	@Override
	public com.rsaame.pas.dao.model.TTrnBuildingQuo mapBean() {

		/* Simply return if the source is null. */
		if (Utils.isEmpty(src)) {
			return null;
		}

		/* If the destination is null, instantiate it. */
		if (Utils.isEmpty(dest)) {
			dest = (com.rsaame.pas.dao.model.TTrnBuildingQuo) Utils
					.newInstance("com.rsaame.pas.dao.model.TTrnBuildingQuo");
		}

		/* Cast the destination bean to a bean of type of BeanA */
		com.rsaame.pas.vo.bus.LocationVO beanA = src;

		/* Cast the destination bean to a bean of type of BeanB */
		com.rsaame.pas.dao.model.TTrnBuildingQuo beanB = dest;

		/*
		 * Initialize any deepVO inside the destination bean. This is to avoid
		 * null pointers at runtime whenever a deep field is being mapped in the
		 * destination bean.
		 */
		beanB = initializeDeepVO(beanA, beanB);

		/* Mapping: "freeZone" -> "bldFreeZone" */
		if (!Utils.isEmpty(beanA.getFreeZone())) {
			beanB.setBldFreeZone(beanA.getFreeZone());
		}

		/* Mapping: "occTradeGroup" -> "bldOccupancyCode" */
		if (!Utils.isEmpty(beanA.getOccTradeGroup())) {
			com.rsaame.pas.cmn.converter.IntegerShortConverter converter = ConverterFactory
					.getInstance(
							com.rsaame.pas.cmn.converter.IntegerShortConverter.class,
							"", "");
			beanB.setBldOccupancyCode(converter.getTypeOfB().cast(
					converter.getBFromA(beanA.getOccTradeGroup())));
		}

		/* Mapping: "address.officeShopNo" -> "bldAAddress1" */
		if (!Utils.isEmpty(beanA.getAddress())
				&& !Utils.isEmpty(beanA.getAddress().getOfficeShopNo())) {
			beanB.setBldAAddress1(beanA.getAddress().getOfficeShopNo());
		}

		/* Mapping: "address.floor" -> "bldAAddress2" */
		if (!Utils.isEmpty(beanA.getAddress())
				&& !Utils.isEmpty(beanA.getAddress().getFloor())) {
			beanB.setBldAAddress2(beanA.getAddress().getFloor());
		}

		/* Mapping: "riskGroupName" -> "bldEName" */
		if (!Utils.isEmpty(beanA.getRiskGroupName())) {
			beanB.setBldEName(beanA.getRiskGroupName());
		}

		/* Mapping: "address.buildingName" -> "bldEName" */
		if (!Utils.isEmpty(beanA.getAddress())
				&& !Utils.isEmpty(beanA.getAddress().getBuildingName())) {
			beanB.setBldEName(beanA.getAddress().getBuildingName());
		}

		/* Mapping: "address.streetName" -> "bldEStreetName" */
		if (!Utils.isEmpty(beanA.getAddress())
				&& !Utils.isEmpty(beanA.getAddress().getStreetName())) {
			beanB.setBldEStreetName(beanA.getAddress().getStreetName());
		}

		/* Mapping: "address.poBox" -> "bldZip" */
		if (!Utils.isEmpty(beanA.getAddress())
				&& !Utils.isEmpty(beanA.getAddress().getPoBox())) {
			beanB.setBldZip(beanA.getAddress().getPoBox());
		}

		/* Mapping: "address.poBox" -> "bldZip" */
		if (!Utils.isEmpty(beanA.getAddress())
				&& !Utils.isEmpty(beanA.getAddress().getWayNo())) {
			com.rsaame.pas.cmn.converter.LongStringConverter converter = ConverterFactory
					.getInstance(
							com.rsaame.pas.cmn.converter.LongStringConverter.class,
							"", "");
			beanB.setBldWayNo(converter.getTypeOfA().cast(
					converter.getAFromB(beanA.getAddress().getWayNo())));
		}

		/* Mapping: "address.poBox" -> "bldZip" */
		if (!Utils.isEmpty(beanA.getAddress())
				&& !Utils.isEmpty(beanA.getAddress().getBlockNo())) {
			com.rsaame.pas.cmn.converter.LongStringConverter converter = ConverterFactory
					.getInstance(
							com.rsaame.pas.cmn.converter.LongStringConverter.class,
							"", "");
			beanB.setBldBlockNo(converter.getTypeOfA().cast(
					converter.getAFromB(beanA.getAddress().getBlockNo())));
		}

		// To avoid empty if block (Sonar defect) 12-9-2017
		/* Mapping: "address.poBox" -> "bldZip" */
		/*
		 * if( !Utils.isEmpty( beanA.getAddress() ) && !Utils.isEmpty(
		 * beanA.getAddress().getArea() ) ){ // Mapping for AREA is not clear }
		 */
		//Added for Infromap changes
		if (!Utils.isEmpty(beanA.getAddress())
				&& !Utils.isEmpty(beanA.getAddress().getLatitude())) {
		
			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.setBldLatitude( converter.getTypeOfA().cast( converter.getAFromB( beanA.getAddress().getLatitude() ) ) );
		}
		if (!Utils.isEmpty(beanA.getAddress())
				&& !Utils.isEmpty(beanA.getAddress().getLongitude())) {
		
			com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.BigDecimalDoubleConverter.class, "", "" );
			beanB.setBldLongitude( converter.getTypeOfA().cast( converter.getAFromB( beanA.getAddress().getLongitude() ) ) );
		}
		/*if (!Utils.isEmpty(beanA.getAddress())
				&& !Utils.isEmpty(beanA.getAddress().getAddressInDetail())) {
		
			beanB.setBldAName(beanA.getAddress().getAddressInDetail());
		}*/
		if (!Utils.isEmpty(beanA.getAddress())
				&& !Utils.isEmpty(beanA.getAddress().getAddressInDetail())) {
		
			beanB.setBldAName(beanA.getAddress().getAddressInDetail());
		}
		if (!Utils.isEmpty(beanA.getAddress())
				&& !Utils.isEmpty(beanA.getAddress().getInforMapStatus())) {
		
			beanB.setBldInforMapStatus(beanA.getAddress().getInforMapStatus());
		}

		return dest;
	}

	/**
	 * This method initialises all the complex objects and collection instances
	 * we need for mapping.
	 */
	private static com.rsaame.pas.dao.model.TTrnBuildingQuo initializeDeepVO(
			com.rsaame.pas.vo.bus.LocationVO beanA,
			com.rsaame.pas.dao.model.TTrnBuildingQuo beanB) {
		return beanB;
	}
}
