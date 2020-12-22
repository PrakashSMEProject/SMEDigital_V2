package com.rsaame.pas.home.svc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.com.svc.BaseLoadSvc;
import com.rsaame.pas.com.svc.BaseSaveSvc;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.bus.BuildingDetailsVO;
import com.rsaame.pas.vo.bus.HomeInsuranceVO;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.cmn.TableData;

/**
 * 
 * @author M1020204
 * 
 *	This is the Service to Load/Save building details of Home LOB
 */
public class BuildingDetailsSvc extends BaseService{

	private BaseSaveSvc baseSaveSvc;
	private BaseLoadSvc baseLoadSvc;
	
	private final static Logger LOGGER = Logger.getLogger( BuildingDetailsSvc.class );

	/* (non-Javadoc)
	 * @see com.mindtree.ruc.cmn.reflect.IMethodInvocation#invokeMethod(java.lang.String, java.lang.Object[])
	 * This Method is invoked on loading / saving building section details
	 */
	@Override
	public Object invokeMethod( String methodName, Object... args ){

		BaseVO returnValue = null;

		if( "buildingSaveService".equals( methodName ) ){
			returnValue = saveBuildingDetailsSection( (BaseVO) args[ 0 ] ,(BaseVO) args[1]);
		}
		else if( "buildingLoadService".equals( methodName ) ){
			returnValue = loadBuildingDetailsSection( (BaseVO) args[ 0 ] );
		}
		return returnValue;
	}

	/**
	 * @param input
	 * @return
	 * This method used to load the building section details from the back end
	 */
	private BaseVO loadBuildingDetailsSection( BaseVO input ){
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param input
	 * @param baseVO 
	 * @return
	 * This method is used to save the building section details to the back end
	 */
	@SuppressWarnings( "rawtypes" )
	private BaseVO saveBuildingDetailsSection( BaseVO input, BaseVO baseVO ){

		LOGGER.info( "Entered Building Detail Save Service" );

		if( input != null ){
			
			//getting home insurance VO
			HomeInsuranceVO homeInsuranceVO = (HomeInsuranceVO)input;
			
			PolicyDataVO policyDataVO = (PolicyDataVO) baseVO;
			// Creating DataHolder
			DataHolderVO<LinkedHashMap<String, List<TableData>>> dataHolder = new DataHolderVO<LinkedHashMap<String, List<TableData>>>();
			
			// Creating DataMap
			LinkedHashMap<String, List<TableData>> dataMap = new LinkedHashMap<String, List<TableData>>();
			// Creating TableDataList
			List<TableData> buildingTableDataList = new ArrayList<TableData>();
			//Getting BuildingVO
			BuildingDetailsVO buildingDetailVO = homeInsuranceVO.getBuildingDetails();
			
			double totalSI = SvcUtils.getTotalSIForHome( homeInsuranceVO );
			
			
			if( Double.valueOf( totalSI ).longValue() <= Long.valueOf( Utils.getSingleValueAppConfig( "HOME_SI_LIMIT" ) ) ){
				buildingDetailVO.setRiRskCode( Integer.valueOf( Utils.getSingleValueAppConfig( "HOME_RI_RSK_CODE_LESSER" ) ) );
			}
			else{
				buildingDetailVO.setRiRskCode( Integer.valueOf( Utils.getSingleValueAppConfig( "HOME_RI_RSK_CODE_GREATER" ) ) );
			}
			
			//144873 Set Mortgage name
			
			String mortgagee = buildingDetailVO.getMortgageeName();
						
			if((homeInsuranceVO.getCommonVO().getDocCode()==6) && (!Utils.isEmpty(mortgagee))){	
				Integer mortgageCode=null;
				String mortgageName=null;
				if (mortgagee.contains("#")){
					String[] mort = mortgagee.split("#");					
					mortgageCode=SvcUtils.getLookUpCode(com.Constant.CONST_PAS_MORTGAGEE_NAME, "ALL", "ALL", mort[1]);
					if(!Utils.isEmpty(mortgageCode)){
						buildingDetailVO.setMortgageeName(mort[1]);
					}else if(Utils.isEmpty(mortgageCode)){
						try{
							mortgageName=SvcUtils.getLookUpDescription(com.Constant.CONST_PAS_MORTGAGEE_NAME, "ALL", "ALL", Integer.parseInt(mort[1]));
							buildingDetailVO.setMortgageeName(mortgageName);
						} catch(NumberFormatException ex){
							buildingDetailVO.setMortgageeName(mort[1]);
						}
					}
				} else {					
					mortgageCode=SvcUtils.getLookUpCode(com.Constant.CONST_PAS_MORTGAGEE_NAME, "ALL", "ALL", buildingDetailVO.getMortgageeName());
					if(!Utils.isEmpty(mortgageCode)){
						buildingDetailVO.setMortgageeName(mortgagee);
					} else if(Utils.isEmpty(mortgageCode)){
						try{
							mortgageName=SvcUtils.getLookUpDescription(com.Constant.CONST_PAS_MORTGAGEE_NAME, "ALL", "ALL", Integer.parseInt(buildingDetailVO.getMortgageeName()));
							buildingDetailVO.setMortgageeName(mortgageName);
						} catch(NumberFormatException ex){
							buildingDetailVO.setMortgageeName(mortgagee);
						}	
					}
					
				}
			}
			 		
			//144873 Set Mortgage name
			
			// Creating TableData
			TableData<BuildingDetailsVO> buildingTableData = new TableData<BuildingDetailsVO>();

			//Setting buildingVO to TableData
			buildingTableData.setTableData( buildingDetailVO );
			//Adding TableData to TableData List
			buildingTableDataList.add( buildingTableData );
            TableData<PolicyDataVO> polTableData = new TableData<PolicyDataVO>();
            polTableData.setTableData( policyDataVO );
            List<TableData> polTableDataList = new ArrayList<TableData>();
            polTableDataList.add( polTableData );
            
            
            //Adding TableDataList to the dataMap
           dataMap.put( SvcConstants.T_TRN_POLICY, polTableDataList );
			dataMap.put( SvcConstants.T_TRN_BUILDING_QUO, buildingTableDataList );
			//Setting dataMap to DataHolder
			dataHolder.setData( dataMap );

			dataHolder = (DataHolderVO<LinkedHashMap<String, List<TableData>>>) baseSaveSvc.invokeMethod( "baseSave", dataHolder,homeInsuranceVO.getCommonVO());
			
			//144873 Set Mortgage name
			homeInsuranceVO.getBuildingDetails().setMortgageeName(mortgagee);
			//144873 Set Mortgage name
			
			mapKeyValuesToVO(dataHolder,homeInsuranceVO);
			return homeInsuranceVO;
		}

		LOGGER.info( "Exited Building Detail Save Service" );

		return input;
	}



	private void mapKeyValuesToVO( DataHolderVO<LinkedHashMap<String, List<TableData>>> dataHolder, HomeInsuranceVO homeInsuranceVO ){
		LinkedHashMap<String, List<TableData>> dataMap = dataHolder.getData();
		List<TableData> buildingTableData = dataMap.get( SvcConstants.T_TRN_BUILDING_QUO );
		BuildingDetailsVO buildingDetailsVO = homeInsuranceVO.getBuildingDetails();
		buildingDetailsVO.getRiskCodes().setBasicRskId( new BigDecimal( buildingTableData.get( 0 ).getContentID() ));
		buildingDetailsVO.getRiskCodes().setRskId( new BigDecimal( buildingTableData.get( 0 ).getContentID() ));
	}

	/**
	 * @return
	 */
	public BaseSaveSvc getBaseSaveSvc(){
		return baseSaveSvc;
	}

	/**
	 * @param baseSaveSvc is injected from applicationContext.xml
	 */
	public void setBaseSaveSvc( BaseSaveSvc baseSaveSvc ){
		this.baseSaveSvc = baseSaveSvc;
	}

	/**
	 * @return
	 */
	public BaseLoadSvc getBaseLoadSvc(){
		return baseLoadSvc;
	}

	/**
	 * @param baseLoadSvc
	 */
	public void setBaseLoadSvc( BaseLoadSvc baseLoadSvc ){
		this.baseLoadSvc = baseLoadSvc;
	}
	
	

}
