/*
 * Holds all information related to Travel Insurance
 * List<TravelPackageVO> - Holds the List of all travel schemes data
 * PolicyDataVO - Holds the policy and general information data 
 * TravelDetailsVO - Holds all Travel Details like travel location, traveler details etc
 */
package com.rsaame.pas.vo.bus;

import java.util.List;

import com.mindtree.ruc.cmn.utils.Utils;

/**
 * @author m1017029,m1016996
 * @since Phase3
 */
public class TravelInsuranceVO extends PolicyDataVO{

	private static final long serialVersionUID = 5372165615712829439L;

	private List<TravelPackageVO> travelPackageList;
	private TravelDetailsVO travelDetailsVO;
	private Integer defaultTariff;

	public List<TravelPackageVO> getTravelPackageList(){
		return travelPackageList;
	}

	public void setTravelPackageList( List<TravelPackageVO> travelPackageList ){
		this.travelPackageList = travelPackageList;
	}

	public TravelDetailsVO getTravelDetailsVO(){
		return travelDetailsVO;
	}

	public void setTravelDetailsVO( TravelDetailsVO travelDetailsVO ){
		this.travelDetailsVO = travelDetailsVO;
	}

	@Override
	public Object getFieldValue( String fieldName ){
		Object fieldValue = null;
		if( "travelPackageList".equals( fieldName ) ) fieldValue = getTravelPackageList();
		if( "travelDetailsVO".equals( fieldName ) ) fieldValue = getTravelDetailsVO();
		if("scheme".equals(fieldName)) return getScheme();
		if("policyTerm".equals(fieldName)) return getPolicyTerm();
		if("defaultTariff".equals(fieldName)) return getDefaultTariff();
		
		return fieldValue;
	}

	public Integer getDefaultTariff() {
		return defaultTariff;
	}

	public void setDefaultTariff(Integer defaultTariff) {
		this.defaultTariff = defaultTariff;
	}
	
	public TravelPackageVO getSelectedPackage(){

		if( !Utils.isEmpty( travelPackageList ) ){
			if( travelPackageList.size() == 1 ) return travelPackageList.get( 0 );
			for( TravelPackageVO selectedPackage : travelPackageList ){
				if( selectedPackage.getIsSelected() ) return selectedPackage;
			}
		}
		return null;
	}
}
