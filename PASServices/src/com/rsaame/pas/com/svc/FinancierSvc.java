package com.rsaame.pas.com.svc;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.pa.svc.PersonalDetailsSvc;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.vo.bus.GeneralInfoVO;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.cmn.CommonVO;
import com.rsaame.pas.vo.cmn.TableData;
import com.rsaame.pas.vo.svc.TTrnHirePurchaseVOHolder;
import com.rsaame.pas.vo.svc.TTrnHirePurchaseVOHolderWrapper;

public class FinancierSvc extends BaseService{


	private DataHolderVO<LinkedHashMap<String, List<TableData>>> toBeSaved = new DataHolderVO<LinkedHashMap<String, List<TableData>>>();
	
	private final static com.mindtree.ruc.cmn.log.Logger logger = com.mindtree.ruc.cmn.log.Logger.getLogger( PersonalDetailsSvc.class );
	
	private BaseSaveSvc baseSaveSvc;
	
	/* (non-Javadoc)
	 * @see com.mindtree.ruc.cmn.reflect.IMethodInvocation#invokeMethod(java.lang.String, java.lang.Object[])
	 */
	@Override
	public Object invokeMethod( String methodName, Object... args ){
				
		BaseVO returnValue = null;
		if( SvcConstants.SAVE_FINANCIER_INFO.equals( methodName ) ){
			PolicyDataVO polData = (PolicyDataVO) (BaseVO) args[ 0 ];
			
				returnValue = saveFinancierDetails( polData);
			}
		
			
		return returnValue;
	}
	

	/**
	 * 
	 * @param baseVo
	 * @return
	 */
	 private BaseVO saveFinancierDetails( BaseVO baseVo ){
		LinkedHashMap<String, List<TableData>> dataMap = new LinkedHashMap<String, List<TableData>>();
		PolicyDataVO polDataVo = (PolicyDataVO) baseVo;		
		
			/*
			 * Get the required VO from BaseVO
			 */
			DataHolderVO<LinkedHashMap<String, List<TableData>>> dataHolder = new DataHolderVO<LinkedHashMap<String, List<TableData>>>();
			if(!Utils.isEmpty(polDataVo.getGeneralInfo()))
			{

				if(!Utils.isEmpty(polDataVo.getGeneralInfo().getFinancier())
						&& !Utils.isEmpty(polDataVo.getGeneralInfo().getFinancier().get(0).getName()))
				{					
					CommonVO commonVO = polDataVo.getCommonVO();
					GeneralInfoVO generalInfoVO = (GeneralInfoVO) polDataVo.getGeneralInfo();		
			 
					TTrnHirePurchaseVOHolderWrapper tTrnHirePurchaseVOHolderWrapper=new TTrnHirePurchaseVOHolderWrapper();
					List<TableData> policyTableDataList = new ArrayList<TableData>( );
			
					//-- Values of policyDataVO is mapped to TTrnHirePurchaseVOHolderWrapper
					tTrnHirePurchaseVOHolderWrapper = BeanMapper.map( generalInfoVO, tTrnHirePurchaseVOHolderWrapper );
		

					/*
					 * Prepare the VOHOlder for T_TRN_HIRE_PURCHASE_QUO table and set the vo holder to table data
					 */
			
					
					TableData<PolicyDataVO> policyTableData = new TableData<PolicyDataVO>();
					policyTableData.setTableData( polDataVo );

					policyTableDataList.add( policyTableData );
					List<TableData> tTrnHirePurchaseVoHolderList = tTrnHirePurchaseVOHolderWrapper.getTTrnHirePurchaseVOHolderList();
					List<TableData> tTrnHirePurchaseVoHolderListPolicyId = new ArrayList<TableData>();
					TTrnHirePurchaseVOHolder trnHirePurchaseVOHolder;
					for(TableData tableData:tTrnHirePurchaseVoHolderList)
					{
						trnHirePurchaseVOHolder = (TTrnHirePurchaseVOHolder)tableData.getTableData();
						trnHirePurchaseVOHolder.setHpPolicyId(commonVO.getPolicyId());
						tableData.setTableData(trnHirePurchaseVOHolder);
						tTrnHirePurchaseVoHolderListPolicyId.add(tableData);
					}

					dataMap.put( SvcConstants.T_TRN_POLICY, policyTableDataList );
					dataMap.put( SvcConstants.T_TRN_HIRE_PURCHASE_QUO, tTrnHirePurchaseVoHolderListPolicyId );
				
					dataHolder.setData( dataMap );

					baseSaveSvc.invokeMethod( "baseSave", dataHolder, commonVO );
				}
			}

		
		
		return polDataVo;
	}


	public BaseSaveSvc getBaseSaveSvc() {
		return baseSaveSvc;
	}


	public void setBaseSaveSvc(BaseSaveSvc baseSaveSvc) {
		this.baseSaveSvc = baseSaveSvc;
	}

}
