package com.rsaame.pas.home.svc;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.com.svc.BaseLoadSvc;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.bus.BuildingDetailsVO;
import com.rsaame.pas.vo.cmn.LoadDataInputVO;
import com.rsaame.pas.vo.cmn.RiskVO;
import com.rsaame.pas.vo.cmn.TableData;
import com.rsaame.pas.vo.svc.TTrnPremiumVOHolder;
import org.apache.log4j.Logger;

public class HomeBuildingLoadSvc extends BaseService{
	
	private final static Logger LOGGER = Logger.getLogger(HomeBuildingLoadSvc.class);
	
	private BaseLoadSvc baseLoadSvc;

	@Override
	public Object invokeMethod( String methodName, Object... args ){
		BaseVO returnValue = null;

		if( "homeBuildingDetailsLoadService".equals( methodName ) ){
			returnValue = (BaseVO) loadHomeBuildingDetails( (BaseVO) args[ 0 ] );
		}
		return returnValue;
	}

	@SuppressWarnings( { "rawtypes", "unchecked" } )
	private BuildingDetailsVO loadHomeBuildingDetails( BaseVO baseVO ){
		LOGGER.info("Entered HomeBuildingLoadSvc.loadHomeBuildingDetails method.");
		BuildingDetailsVO buildingDetailsVO = null;
		Boolean buildingDetailsFound = false;
		LoadDataInputVO loadInputVO = (LoadDataInputVO) baseVO;

		Map<String, Class<? extends BaseVO>> dataToLoad = new LinkedHashMap<String, Class<? extends BaseVO>>();

		dataToLoad.put( SvcConstants.T_TRN_BUILDING_QUO, BuildingDetailsVO.class );
		dataToLoad.put( SvcConstants.TABLE_ID_T_TRN_PREMIUM_QUO, TTrnPremiumVOHolder.class );
		
		LOGGER.info("HomeBuildingLoadSvc.loadHomeBuildingDetails method, calling BaseLoadSvc.loadData method.");
		DataHolderVO<LinkedHashMap<String, List<TableData>>> dataHolderVO = (DataHolderVO<LinkedHashMap<String, List<TableData>>>) baseLoadSvc.invokeMethod( "baseLoad",
				loadInputVO, dataToLoad );

		List<TableData> buildingDetailsVOs = dataHolderVO.getData().get( SvcConstants.T_TRN_BUILDING_QUO );
		List<TableData> premiumList = dataHolderVO.getData().get( SvcConstants.TABLE_ID_T_TRN_PREMIUM_QUO );

		if((!Utils.isEmpty( buildingDetailsVOs )) && (!Utils.isEmpty( buildingDetailsVOs.get( 0 ) ))){
			buildingDetailsVO = (BuildingDetailsVO) buildingDetailsVOs.get( 0 ).getTableData();
		}

		if( !Utils.isEmpty( premiumList ) ){
			Iterator<TableData> iterator = premiumList.iterator();

			while( iterator.hasNext() ){

				TTrnPremiumVOHolder premiumVOHolder = (TTrnPremiumVOHolder) iterator.next().getTableData();

				if( isThisBuildingRelatedRecord( premiumVOHolder ) && areTheyRelated( buildingDetailsVO, premiumVOHolder ) ){

					mapBuildingDetailsFromPremium( buildingDetailsVO, premiumVOHolder );
					buildingDetailsFound = true;
					
					break;
				}
			}
		}
		if(!buildingDetailsFound && !Utils.isEmpty(buildingDetailsVO)){
			mapBuildingDetailsFromPremium( buildingDetailsVO, null );
		}
		
		LOGGER.info("Exiting HomeBuildingLoadSvc.loadHomeBuildingDetails method.");
		return buildingDetailsVO;
	}

	/**
	 * Returns boolean base on whether the record is of building type(Just filter in the premium building records).
	 * 
	 * @param premiumVOHolder
	 * @return boolean
	 */
	private boolean isThisBuildingRelatedRecord( TTrnPremiumVOHolder premiumVOHolder ){

		if( premiumVOHolder.getPrmRskCode() == 2 && premiumVOHolder.getPrmRtCode() != 1 ){
			return false;
		}

		return true;
	}

	/**
	 * Returns an boolean based on whether an object of buildingDetailsVO and TTrnPremiumVOHolder are related to
	 * each other(Every entry in buildingDetailsVO will be having a entry in the Premium table). So to find this
	 * difference we are using the below method
	 * 
	 * @param buildingDetailsVO
	 * @param premiumVOHolder
	 * @return boolean
	 */
	public boolean areTheyRelated( BuildingDetailsVO buildingDetailsVO, TTrnPremiumVOHolder premiumVOHolder ){

		RiskVO coverDetRiskVO = buildingDetailsVO.getRiskCodes();

		if( !coverDetRiskVO.getRskId().equals( premiumVOHolder.getPrmRskId() ) ){
			return false;
		}

		return true;

	}

	/**
	 * Will map the data from Premium table to buildingDetailsVO 
	 * 
	 * @param buildingDetailsVO
	 * @param premiumVOHolder
	 */
	private void mapBuildingDetailsFromPremium( BuildingDetailsVO buildingDetailsVO, TTrnPremiumVOHolder premiumVOHolder ){

		Integer emirates = SvcUtils.getLookUpCode( "CITY", "ALL", "ALL", buildingDetailsVO.getEmirates() );
		Integer buildingName = SvcUtils.getLookUpCode( "DIRECTORATE", "ALL", "ALL", buildingDetailsVO.getBuildingname() );
		if(!Utils.isEmpty( emirates ) && !Utils.isEmpty( buildingDetailsVO.getEmirates() ) ){
			buildingDetailsVO.setEmirates( emirates.toString() );
		}
		if(!Utils.isEmpty( buildingName ) && (!Utils.isEmpty( buildingDetailsVO.getBuildingname() ) ) ){
			buildingDetailsVO.setBuildingname( buildingName.toString() );	
		}
		if( !Utils.isEmpty(buildingDetailsVO.getMortgageeName() ) 
				&& !buildingDetailsVO.getMortgageeName().equalsIgnoreCase( "select" ) ){
			Integer mortgaeeName = SvcUtils.getLookUpCode( "PAS_MORTGAGEE_NAME", "ALL", "ALL", 
					buildingDetailsVO.getMortgageeName() );
			if(!Utils.isEmpty(mortgaeeName)){
				buildingDetailsVO.setMortgageeName( mortgaeeName.toString() );
			}
			else if(Utils.isEmpty( mortgaeeName ) ){
				Integer others = SvcUtils.getLookUpCode( "PAS_MORTGAGEE_NAME", "ALL", "ALL", 
						"Others" );
				buildingDetailsVO.setMortgageeName(others+"#"+buildingDetailsVO.getMortgageeName());
			}
		}
		/*else{
			buildingDetailsVO.setMortgageeName( "Select" );
		}*/
		
		if( !Utils.isEmpty( buildingDetailsVO.getArea() ) ){
			
			Integer area = SvcUtils.getLookUpCode( "PAS_AREA", "ALL", "ALL", buildingDetailsVO.getArea() );
			Integer areaOthers = SvcUtils.getLookUpCode( "PAS_AREA", "ALL", "ALL", "Others" );
			
			if( !Utils.isEmpty( area ) ){
//				buildingDetailsVO.setArea( buildingDetailsVO.getArea() );
				buildingDetailsVO.setArea(area.toString());
				buildingDetailsVO.setOtherDetails( null );
			}
			else if( Utils.isEmpty( area ) && !Utils.isEmpty( areaOthers ) ){
				buildingDetailsVO.setArea( areaOthers.toString() );
			}
			
		}
		
		
		BeanMapper.map( premiumVOHolder, buildingDetailsVO );

	}

	/**
	 * 
	 * @param loadOperation
	 * @return
	 */
	public void setBaseLoadSvc( BaseLoadSvc baseLoadSvc ){
		this.baseLoadSvc = baseLoadSvc;
	}
}
