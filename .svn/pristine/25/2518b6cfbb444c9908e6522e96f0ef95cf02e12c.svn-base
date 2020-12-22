/**
 * 
 */
package com.rsaame.pas.endorse.svc;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.com.svc.BaseLoadSvc;
import com.rsaame.pas.dao.model.TTrnPolicyQuo;
import com.rsaame.pas.endorse.dao.IAmendPolicyDao;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.vo.bus.HomeInsuranceVO;
import com.rsaame.pas.vo.bus.LOB;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.TravelInsuranceVO;
import com.rsaame.pas.vo.cmn.CommonVO;
import com.rsaame.pas.vo.cmn.LoadDataInputVO;
import com.rsaame.pas.vo.cmn.TableData;

/**
 * @author m1014241
 *
 */
public class AmendPolicySvc extends BaseService {

	private IAmendPolicyDao amendPolicyDao;
	private BaseLoadSvc baseLoadSvc;
	
	private static final Logger LOGGER = Logger.getLogger( AmendPolicySvc.class );
	
	/**
	 * @param baseLoadSvc
	 * For Dependency Injection
	 */
	public void setBaseLoadSvc( BaseLoadSvc baseLoadSvc ){
		this.baseLoadSvc = baseLoadSvc;
	}
	
	/* (non-Javadoc)
	 * @see com.mindtree.ruc.cmn.reflect.IMethodInvocation#invokeMethod(java.lang.String, java.lang.Object[])
	 */
	@Override
	public Object invokeMethod(String methodName, Object... args) {
		
		
		BaseVO returnValue = null;
		if( "isEndorsePending".equals( methodName ) ){
			returnValue = isEndorsePending( (BaseVO) args[ 0 ] );
		}   else if( "isInsuredChanged".equals( methodName ) ){
			returnValue = isInsuredChanged( (BaseVO) args[ 0 ] );
		}   else if( "getEndorsementSummary".equals( methodName ) ){
			returnValue = getEndorsementSummary( (BaseVO) args[ 0 ] );
		}   else if( "getCancelPolRefundPremium".equals( methodName ) ){
			returnValue = getCancelPolRefundPremium( (BaseVO) args[ 0 ] );
		}  else if("deletePendingPolicy".equals( methodName ) ) {
			returnValue = deletePendingPolicy( (BaseVO) args[ 0 ] );
		}  else if("updateInsurePol".equals( methodName ) ) {
			returnValue = updateInsurePol( (BaseVO) args[ 0 ] );
		}  else if("processCancelPolicy".equals( methodName ) ) {
			returnValue = processCancelPolicy( (BaseVO) args[ 0 ] );
		} else if("getActivePolicy".equals( methodName ) ) {
			returnValue = getActivePolicy( (BaseVO) args[ 0 ] );
		} else if("checkEndtEffectiveDate".equals(methodName)){
			returnValue = isEndtEffectiveDateValid( (BaseVO) args[ 0 ] );
		} else if("isRenewalQuoteExists".equals(methodName)){
			returnValue = isRenewalQuoteExists( (BaseVO) args[ 0 ] );
		} else if("retrievePolicyDataVO".equals( methodName )){
			returnValue = retrievePolicyDataVO((BaseVO) args[0]);
		} else if("retrieveActiveRecord".equals( methodName )){
			returnValue = retrieveLatestEndorsedPolicyDataVO((BaseVO)args[0]);
		} else if("checkForEffectiveDate".equals( methodName )){
			returnValue = checkForEffectiveDate((BaseVO) args[0]);
		}else if("retrievePolicyDataVOForLOB".equals( methodName )){
			returnValue = retrievePolicyDataVOForLOB( (BaseVO) args[0] );
		}else if("checkForEffectiveDateShortTerm".equals( methodName )){
			returnValue = checkForEffectiveDateShortTerm((BaseVO) args[0]);
		}
		
			return returnValue;
	}
	

	/**
	 * @param baseVO
	 * @return
	 * 
	 * This method checks if the effective date entered is valid for home and travel endorsement
	 */
	private BaseVO checkForEffectiveDate( BaseVO baseVO ){
		LOGGER.info( "Entering checkForEffectiveDate validation method" );
		PolicyDataVO policyDataVO = null;
		policyDataVO = (PolicyDataVO) baseVO;
		DataHolderVO<Object[]> dataHolder = new DataHolderVO<Object[]>();
		Object[] data = {policyDataVO,""};
		if( baseVO instanceof PolicyDataVO){
			//policyDataVO = (PolicyDataVO) baseVO;
			amendPolicyDao.checkForEffectiveDate(baseVO,data);
			dataHolder.setData( data );
			return dataHolder;
		}
		LOGGER.info( "Exiting checkForEffectiveDate validation method" );
		return baseVO;
	}

	/**
	 * @param baseVO
	 * @return
	 * 
	 * This method returns latest endorsed record
	 */
	private BaseVO retrieveLatestEndorsedPolicyDataVO( BaseVO baseVO ){
		
		TTrnPolicyQuo ttrnPolicyQuo = null;
		DataHolderVO<Long[]> dataHolder = new DataHolderVO<Long[]>();
		ttrnPolicyQuo = amendPolicyDao.getLatestEndorsedPolicyDataVO(baseVO);
		Long[] ids = new Long[2];
		
		if(!Utils.isEmpty( ttrnPolicyQuo )){
			if(!Utils.isEmpty( ttrnPolicyQuo.getPolEndtNo() )){
				ids[0] = ttrnPolicyQuo.getPolEndtNo();
			}
			if(!Utils.isEmpty( ttrnPolicyQuo.getId().getEndtId() )){
				ids[1] = ttrnPolicyQuo.getId().getEndtId();
			}
			dataHolder.setData( ids );
			return dataHolder;
		}
		return null;
	}

	/**
	 * @param baseVO
	 * @return
	 * 
	 * This method returns policyDataVO for the given CommonVO
	 */
	@SuppressWarnings( { "rawtypes", "unchecked" } )
	private BaseVO retrievePolicyDataVO( BaseVO baseVO ){
		CommonVO commonVO = (CommonVO) baseVO;
		
		//Get policy data from t_trn_policy
		LoadDataInputVO inputVO = new LoadDataInputVO();

		//populate LoadDataInputVO from commonVO 
		inputVO.setIsQuote( commonVO.getIsQuote() );
		inputVO.setLocCode( commonVO.getLocCode() );
		inputVO.setEndtId( commonVO.getEndtId() );
		inputVO.setQuoteNo( commonVO.getQuoteNo() );
		inputVO.setPolicyNo( commonVO.getPolicyNo() );
		inputVO.setPolEffectiveDate( commonVO.getPolEffectiveDate() );

		// Fetch existing t_trn_policy record, this record will be the first item in the hashMap in other services
		Map<String, Class<? extends BaseVO>> dataMap = new LinkedHashMap<String, Class<? extends BaseVO>>();
		dataMap.put( SvcConstants.T_TRN_POLICY, PolicyDataVO.class );
		DataHolderVO<LinkedHashMap<String, List<TableData<BaseVO>>>> dataHolder = (DataHolderVO) baseLoadSvc.invokeMethod( "baseLoad", inputVO, dataMap );
		List<TableData<BaseVO>> polTableData = dataHolder.getData().get( SvcConstants.T_TRN_POLICY );
		PolicyDataVO policyDataVo = (PolicyDataVO) polTableData.get( 0 ).getTableData();
		LOGGER.debug( "Value fetched from TTrnPolicy --->" + policyDataVo.getPolicyId() );
		return policyDataVo;
	}
	
	
	@SuppressWarnings( { "rawtypes", "unchecked" } )
	private BaseVO retrievePolicyDataVOForLOB( BaseVO baseVO ){
		CommonVO commonVO = (CommonVO) baseVO;
		
		//Get policy data from t_trn_policy
		LoadDataInputVO inputVO = new LoadDataInputVO();

		//populate LoadDataInputVO from commonVO 
		inputVO.setIsQuote( commonVO.getIsQuote() );
		inputVO.setLocCode( commonVO.getLocCode() );
		inputVO.setEndtId( commonVO.getEndtId() );
		inputVO.setQuoteNo( commonVO.getQuoteNo() );
		inputVO.setPolicyNo( commonVO.getPolicyNo() );
		
		// Fetch existing t_trn_policy record, this record will be the first item in the hashMap in other services
		Map<String, Class<? extends BaseVO>> dataMap = new LinkedHashMap<String, Class<? extends BaseVO>>();
		
		if(commonVO.getLob().equals( LOB.HOME )){
			dataMap.put( SvcConstants.T_TRN_POLICY, HomeInsuranceVO.class );
		}else if(commonVO.getLob().equals( LOB.TRAVEL )){
			dataMap.put( SvcConstants.T_TRN_POLICY, TravelInsuranceVO.class );
		}else{
			dataMap.put( SvcConstants.T_TRN_POLICY, PolicyDataVO.class );
		}
		
		DataHolderVO<LinkedHashMap<String, List<TableData<BaseVO>>>> dataHolder = (DataHolderVO) baseLoadSvc.invokeMethod( "baseLoad", inputVO, dataMap );
		List<TableData<BaseVO>> polTableData = dataHolder.getData().get( SvcConstants.T_TRN_POLICY );
		
		PolicyDataVO policyDataVo = null;
		if( commonVO.getLob().equals( LOB.HOME ) ){
			policyDataVo = (HomeInsuranceVO) polTableData.get( 0 ).getTableData();
		}
		else if( commonVO.getLob().equals( LOB.TRAVEL ) ){
			policyDataVo = (TravelInsuranceVO) polTableData.get( 0 ).getTableData();
		}
		else{
			policyDataVo = (PolicyDataVO) polTableData.get( 0 ).getTableData();
		}
		
		LOGGER.debug( "Value fetched from TTrnPolicy --->" + policyDataVo.getPolicyId() );
		return policyDataVo;
	}


	private BaseVO isEndtEffectiveDateValid(BaseVO baseVO) {
		return amendPolicyDao.isEndtEffectiveDateValid(baseVO);
	}


	private BaseVO getActivePolicy(BaseVO baseVO) {
		
		return amendPolicyDao.getActivePolicy(baseVO);
	}


	private BaseVO isEndorsePending(BaseVO baseVO) {
		
		return amendPolicyDao.isEndorsePending(baseVO);
	}
	
	private BaseVO isInsuredChanged(BaseVO baseVO) {
		
		return amendPolicyDao.isInsuredChanged(baseVO);
	}
	
	private BaseVO getCancelPolRefundPremium(BaseVO baseVO) {
		return amendPolicyDao.getCancelPolRefundPremium(baseVO);
	}

	
	private BaseVO getEndorsementSummary(BaseVO baseVO) {
		
		return amendPolicyDao.getEndorsementSummary(baseVO);

	}
	private BaseVO deletePendingPolicy(BaseVO baseVO) {
	
	return amendPolicyDao.deletePendingPolicy(baseVO);
	
	}

	public void setAmendPolicyDao(IAmendPolicyDao amendPolicyDao) {
		this.amendPolicyDao = amendPolicyDao;
	}
	private BaseVO updateInsurePol(BaseVO baseVO) {
		
		return amendPolicyDao.updateInsurePol(baseVO);
	}
	private BaseVO processCancelPolicy( BaseVO baseVO ){
		return amendPolicyDao.processCancelPolicy( baseVO );
	}
	private BaseVO isRenewalQuoteExists( BaseVO baseVO ){
		return amendPolicyDao.isRenewalQuoteExists( baseVO );
	}
	
	/**
	 * @param baseVO
	 * @return
	 * 
	 * This method checks if the effective date entered is valid for home and travel endorsement- Added for Short term Cancellation - OMAN
	 */
	private BaseVO checkForEffectiveDateShortTerm( BaseVO baseVO ){
		LOGGER.info( "Entering checkForEffectiveDateShortTerm validation method" );
		PolicyDataVO policyDataVO = null;
		policyDataVO = (PolicyDataVO) baseVO;
		DataHolderVO<Object[]> dataHolder = new DataHolderVO<Object[]>();
		Object[] data = {policyDataVO,""};
		if( baseVO instanceof PolicyDataVO){
			//policyDataVO = (PolicyDataVO) baseVO;
			amendPolicyDao.checkForEffectiveDateShortTerm(baseVO,data);
			dataHolder.setData( data );
			return dataHolder;
		}
		LOGGER.info( "Exiting checkForEffectiveDate validation method" );
		return baseVO;
	}

}
