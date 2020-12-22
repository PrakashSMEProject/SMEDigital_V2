/**
 * 
 */
package com.rsaame.pas.endorse.svc;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.dao.cmn.IBaseSaveOperation;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.vo.bus.GeneralInfoVO;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.TravelInsuranceVO;
import com.rsaame.pas.vo.cmn.CommonVO;
import com.rsaame.pas.vo.cmn.TableData;

/**
 * @author m1014241
 *
 */
public class GeneralInfoHomeSvc extends BaseService {

	IBaseSaveOperation baseOperationDao;
	


	public IBaseSaveOperation getBaseOperationDao() {
		return baseOperationDao;
	}



	public void setBaseOperationDao(IBaseSaveOperation baseOperationDao) {
		this.baseOperationDao = baseOperationDao;
	}


	//BaseSaveSvc baseSaveSvc = (BaseSaveSvc) Utils.getBean("baseSave");
	DataHolderVO<LinkedHashMap<String, List<TableData>>> toBeSaved = new DataHolderVO<LinkedHashMap<String,List<TableData>>>();
	CommonVO commonVO = new CommonVO();
	private static String ZERO = "0";
	
	/* (non-Javadoc)
	 * @see com.mindtree.ruc.cmn.reflect.IMethodInvocation#invokeMethod(java.lang.String, java.lang.Object[])
	 */
	@Override

	public Object invokeMethod(String methodName, Object... args) {
		
		populateCommonData(commonVO,(BaseVO) args[ 0 ]);
		BaseVO returnValue = null;
		
		
		if( "saveGeneralInfo".equals( methodName ) ){
			returnValue = saveGeneralInfo( (BaseVO) args[ 0 ] );
	    // If any one wants to save insured alone		
		}else if( "saveInsured".equals( methodName ) ){
			returnValue = saveInsured( (BaseVO) args[ 0 ] );
		}
		return returnValue;
	}
	
		private BaseVO saveGeneralInfo(BaseVO baseVO) {
		//HomeInsuranceVO HomeVo = (HomeInsuranceVO)baseVO;
		PolicyDataVO polData = (PolicyDataVO)baseVO;
		//PolicyDataVO polData = HomeVo.getPolicyDetails();
		// Add the condition for saving insured here
		
		if(polData.getGeneralInfo().getInsured().getUpdateMaster()) {
			polData = (PolicyDataVO) saveInsured(polData);
		}
		return savePolicyData(polData);
	}
            
    private BaseVO saveInsured(BaseVO baseVo){
		PolicyDataVO polData = (PolicyDataVO)baseVo;
		GeneralInfoSvc genSaveSvc =  (GeneralInfoSvc) Utils.getBean( "endorseGenInfoSvc" );
		PolicyDataVO policyDataVO  = (PolicyDataVO)genSaveSvc.invokeMethod( "updateTmasInsured", polData );
		return policyDataVO;
	}
	
	private BaseVO savePolicyData(BaseVO baseVo){
		LinkedHashMap<String, List<TableData>> dataMap = new LinkedHashMap<String, List<TableData>>();
		PolicyDataVO polDataVo = (PolicyDataVO)baseVo;
		// For Policy data save
		TableData<PolicyDataVO> polTableData = new TableData<PolicyDataVO>();
		polTableData.setTableData( (PolicyDataVO)baseVo );	
		List<TableData> polList = new ArrayList<TableData>();
		polList.add( polTableData );
		
		// For Cash customer sae
		TableData<GeneralInfoVO> genInfoData = new TableData<GeneralInfoVO>();
		genInfoData.setTableData(polDataVo.getGeneralInfo());
		List<TableData> genInfo =  new ArrayList<TableData>();
		genInfo.add(genInfoData);
		dataMap.put( SvcConstants.T_TRN_POLICY, polList );
		dataMap.put( SvcConstants.SAVE_CASH_CUST_DATA, genInfo );
		toBeSaved.setData( dataMap );
		BaseVO polResultVo =  baseOperationDao.executeSave(toBeSaved, commonVO);
		/*if(polDataVo instanceof TravelInsuranceVO ){*/	/* commented empty if condition (Not content inside if) - sonar violation fix */
			//TravelDetailSvc travelSvc =  (TravelDetailSvc) Utils.getBean( "endorseGenInfoSvc" );
			//travelSvc.invokeMethod( "updateTmasInsured", polDataVo );
		//}
		return polResultVo;
	}

	private void populateCommonData( CommonVO cVo, BaseVO baseVo ){
		PolicyDataVO polDataVo = (PolicyDataVO)baseVo;
		
		//cVo.setAppFLow( Flow.CREATE_QUO );
		if(Utils.isEmpty(polDataVo.getIsQuote())){
			cVo.setIsQuote( Boolean.TRUE );
		} else {
			cVo.setIsQuote(polDataVo.getIsQuote());
		}
		if(Utils.isEmpty(polDataVo.getStatus())){
			cVo.setStatus( 6 );
		} else {
			cVo.setStatus(polDataVo.getStatus());
		}
		cVo.setQuoteNo(polDataVo.getQuoteNo());
		cVo.setPolicyNo(polDataVo.getPolicyNo());
		if(Utils.isEmpty(polDataVo.getEndtId())){
			cVo.setEndtId(Long.valueOf(ZERO));
		} else {
			cVo.setEndtId(polDataVo.getEndtId());
		}
		if(Utils.isEmpty(polDataVo.getEndtNo())){
			cVo.setEndtNo(Long.valueOf(ZERO));
		} else {
			cVo.setEndtNo(polDataVo.getEndtNo());
		}
	}
	
}
