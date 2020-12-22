/**
 * 
 */
package com.rsaame.pas.wc.svc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.com.svc.BaseLoadSvc;
import com.rsaame.pas.com.svc.BaseSaveSvc;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.app.LookUpListVO;
import com.rsaame.pas.vo.bus.EmpTypeDetailsVO;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.bus.WCNammedEmployeeVO;
import com.rsaame.pas.vo.bus.WorkmenCompVO;
import com.rsaame.pas.vo.cmn.CommonVO;
import com.rsaame.pas.vo.cmn.LoadDataInputVO;
import com.rsaame.pas.vo.cmn.TableData;
import com.rsaame.pas.vo.svc.TTrnWctplPersonQuoHolder;
import com.rsaame.pas.vo.svc.TTrnWctplUnnamedPersonQuoHolder;

/**
 * @author Sarath Varier
 * @since Phase 4 - WC Monoline
 * 
 */
public class WorkmenDetailsSvc extends BaseService {

	private final static com.mindtree.ruc.cmn.log.Logger LOGGER = com.mindtree.ruc.cmn.log.Logger
			.getLogger(WCMonolineSvc.class);

	private BaseSaveSvc baseSaveSvc;
	private BaseLoadSvc baseLoadSvc;

	@Override
	public Object invokeMethod(String methodName, Object... args) {

		BaseVO baseVO = null;

		if (SvcConstants.SAVE_WORKMEN_DETAILS.equals(methodName)) {
			baseVO = saveWorkmenDetails((WorkmenCompVO) args[0],
					(BaseVO) args[1]);
		} else if (SvcConstants.LOAD_WORKMEN_DETAILS.equals(methodName)) {
			baseVO = loadWorkmenDetails((LoadDataInputVO) args[0],
					(WorkmenCompVO) args[1], (PolicyDataVO) args[2]);
		}
		return baseVO;
	}

	private BaseVO saveWorkmenDetails(WorkmenCompVO workmenCompVO, BaseVO baseVO) {

		LOGGER.info("Request for save workmen details received in workmen SVC");

		PolicyDataVO policyDataVO = (PolicyDataVO) baseVO;
		// TMasOccupancy occupancy = (TMasOccupancy) occupancyVO;
		WorkmenCompVO WcVO = null;

		DataHolderVO<LinkedHashMap<String, List<TableData>>> dataHolder = new DataHolderVO<LinkedHashMap<String, List<TableData>>>();
		LinkedHashMap<String, List<TableData>> dataMap = new LinkedHashMap<String, List<TableData>>();

		// List<TableData> cashCustTableDataList = new ArrayList<TableData>( 0
		// );
		List<TableData> unnamedPersonTableDataList = new ArrayList<TableData>(0);
		List<TableData> namedPersonTableDataList = new ArrayList<TableData>(0);
		List<TableData> policyTableDataList = new ArrayList<TableData>(0);

		// TableData<TMasCashCustomerVOHolder> cashCustTableData = new
		// TableData<TMasCashCustomerVOHolder>();
		TableData<TTrnWctplUnnamedPersonQuoHolder> unnamedPersonTableData = null;
		TableData<TTrnWctplPersonQuoHolder> namedPersonTableData = null;
		TableData<PolicyDataVO> policyTableData = new TableData<PolicyDataVO>();
		// Save VAT data to policy table ; Added for AdventId:142244
		if (!Utils.isEmpty(workmenCompVO.getPremiumVO().getVatTaxPerc())) {
			if (Utils.isEmpty(policyDataVO.getPremiumVO())) {
				PremiumVO prmVo = new PremiumVO();
				policyDataVO.setPremiumVO(prmVo);
			}
			policyDataVO.getPremiumVO().setVatTaxPerc(
					workmenCompVO.getPremiumVO().getVatTaxPerc());
			policyDataVO.getPremiumVO().setVatTax(
					workmenCompVO.getPremiumVO().getVatTax());
			//142244 Vat
			
/*			if (!Utils.isEmpty(workmenCompVO.getPremiumVO().getVatCode()))
				policyDataVO.getPremiumVO().setVatCode(1
						workmenCompVO.getPremiumVO().getVatCode());*/
			
			if (!Utils.isEmpty(workmenCompVO.getPremiumVO().getVatCode()))
				policyDataVO.getPremiumVO().setVatCode(workmenCompVO.getPremiumVO().getVatCode());
		}
		if (!Utils.isEmpty(workmenCompVO.getGeneralInfo().getInsured().getVatRegNo()))
			policyDataVO.getGeneralInfo().getInsured()
					.setVatRegNo(workmenCompVO.getGeneralInfo().getInsured().getVatRegNo());
		
		policyTableData.setTableData(policyDataVO);
		policyTableDataList.add(policyTableData);

		/*
		 * TMasCashCustomerVOHolder cashCustomerQuo = new
		 * TMasCashCustomerVOHolder(); cashCustomerQuo = BeanMapper.map(
		 * policyDataVO, cashCustomerQuo ); cashCustTableData.setTableData(
		 * cashCustomerQuo ); cashCustTableDataList.add( cashCustTableData );
		 */

		Integer tradeGroup = null;
		LookUpListVO lookUpVOList = SvcUtils.getLookUpCodesList(
				"PAS_B_TYP_TRADE_GROUP", workmenCompVO.getGeneralInfo()
						.getInsured().getBusType().toString(), "ALL");

		if (!Utils.isEmpty(lookUpVOList)
				&& !Utils.isEmpty(lookUpVOList.getLookUpList())
				&& !Utils.isEmpty(lookUpVOList.getLookUpList().get(0))
				&& !Utils
						.isEmpty(lookUpVOList.getLookUpList().get(0).getCode())) {
			tradeGroup = Integer.valueOf(lookUpVOList.getLookUpList().get(0)
					.getCode().toString());
		}
		Long hazardCode = null;
		String hazardDsc = null;
		/*
		 * Long hazardCode = null; LookUpListVO lookUpVOList =
		 * SvcUtils.getLookUpCodesList( "HAZARD_CODE",
		 * workmenCompVO.getLocationVO().getOccTradeGroup().toString(), "ALL" );
		 * if( !Utils.isEmpty( lookUpVOList ) && !Utils.isEmpty(
		 * lookUpVOList.getLookUpList() ) && !Utils.isEmpty(
		 * lookUpVOList.getLookUpList().get( 0 ) ) && !Utils.isEmpty(
		 * lookUpVOList.getLookUpList().get( 0 ).getCode() ) ){ hazardCode =
		 * Long.valueOf( lookUpVOList.getLookUpList().get( 0
		 * ).getCode().toString() ); }
		 */

		// TMasOccupancy occupancy = DAOUtils.getOccDetails(
		// workmenCompVO.getLocationVO().getOccTradeGroup().shortValue() );

		for (EmpTypeDetailsVO unnamedEmployee : workmenCompVO
				.getEmpTypeDetails()) {

			unnamedPersonTableData = new TableData<TTrnWctplUnnamedPersonQuoHolder>();
			WcVO = CopyUtils.copySerializableObject(workmenCompVO);
			WcVO.setEmpTypeDetails(new com.mindtree.ruc.cmn.utils.List<EmpTypeDetailsVO>(
					EmpTypeDetailsVO.class));
			WcVO.getEmpTypeDetails().add(unnamedEmployee);
			TTrnWctplUnnamedPersonQuoHolder unnamedPersonQuo = BeanMapper.map(
					WcVO, TTrnWctplUnnamedPersonQuoHolder.class);

			lookUpVOList = SvcUtils.getLookUpCodesList("PAS_EMP_HAZARD_CODE",
					unnamedEmployee.getEmpType().toString(), "ALL");
			if (!Utils.isEmpty(lookUpVOList)
					&& !Utils.isEmpty(lookUpVOList.getLookUpList())
					&& !Utils.isEmpty(lookUpVOList.getLookUpList().get(0))
					&& !Utils.isEmpty(lookUpVOList.getLookUpList().get(0)
							.getCode())) {
				hazardCode = Long.valueOf(lookUpVOList.getLookUpList().get(0)
						.getCode().toString());
				hazardDsc = SvcUtils.getLookUpDescription(
						"PAS_EMP_HAZARD_CODE", unnamedEmployee.getEmpType()
								.toString(), "ALL", hazardCode.intValue());
			}
			/*
			 * BigDecimal occTradeCode = DAOUtils.getOccupancyTradeCode(
			 * policyDataVO.getScheme(), BigDecimal.valueOf(
			 * unnamedEmployee.getEmpType() ), Integer.valueOf(
			 * Utils.getSingleValueAppConfig( "WC_CRITERIA_CODE" ) ) );
			 */
			unnamedPersonQuo.setWupTradeGroup(tradeGroup.longValue());
			unnamedPersonQuo.setWupRtCode(unnamedEmployee.getEmpType()
					.shortValue());
			unnamedPersonQuo.setWupHazard(new Long(hazardDsc).longValue());

			unnamedPersonTableData.setTableData(unnamedPersonQuo);
			unnamedPersonTableDataList.add(unnamedPersonTableData);

		}
		unnamedPersonTableDataList = updateToBeDeletedUnNamedEmp(
				workmenCompVO.getCommonVO(), unnamedPersonTableDataList);

		dataMap.put(SvcConstants.TABLE_ID_T_TRN_POLICY, policyTableDataList);
		// dataMap.put( SvcConstants.TABLE_ID_T_MAS_CASH_CUSTOMER_QUO,
		// cashCustTableDataList );
		dataMap.put(SvcConstants.TABLE_ID_T_TRN_WCTPL_UNNAMED_PERSON_QUO,
				unnamedPersonTableDataList);

		dataHolder.setData(dataMap);
		dataHolder = (DataHolderVO<LinkedHashMap<String, List<TableData>>>) baseSaveSvc
				.invokeMethod("baseSave", dataHolder,
						workmenCompVO.getCommonVO());

		mapKeyValuesToVO(dataHolder, workmenCompVO);
		Long basicRiskID = getNamedPersonBasicRiskId(dataHolder);
		Short riskTypeCode = getNamedPersonBasicRiskTypeId(dataHolder);
		for (WCNammedEmployeeVO namedEmployee : workmenCompVO
				.getWcEmployeeDetails()) {

			namedPersonTableData = new TableData<TTrnWctplPersonQuoHolder>();
			WcVO = CopyUtils.copySerializableObject(workmenCompVO);
			WcVO.setWcEmployeeDetails(new com.mindtree.ruc.cmn.utils.List<WCNammedEmployeeVO>(
					WCNammedEmployeeVO.class));
			WcVO.getWcEmployeeDetails().add(namedEmployee);

			TTrnWctplPersonQuoHolder namedPersonQuo = BeanMapper.map(WcVO,
					TTrnWctplPersonQuoHolder.class);

			namedPersonQuo.setWprTradeGroup(tradeGroup.longValue());
			namedPersonQuo.setWprRtCode(riskTypeCode.longValue());
			// Radar Fix
			namedPersonQuo.setWprHazard(Long.valueOf(99));

			namedPersonQuo.setWprBasicRiskId(basicRiskID);

			namedPersonTableData.setTableData(namedPersonQuo);
			namedPersonTableDataList.add(namedPersonTableData);

		}

		namedPersonTableDataList = updateToBeDeletedNamedEmp(
				workmenCompVO.getCommonVO(), namedPersonTableDataList);
		dataHolder = new DataHolderVO<LinkedHashMap<String, List<TableData>>>();
		dataMap = new LinkedHashMap<String, List<TableData>>();
		if (namedPersonTableDataList.size() > 0) {
			dataMap.put(SvcConstants.TABLE_ID_T_TRN_POLICY, policyTableDataList);
			dataMap.put(SvcConstants.TABLE_ID_T_TRN_WCTPL_PERSON_QUO,
					namedPersonTableDataList);

			dataHolder.setData(dataMap);
			baseSaveSvc.invokeMethod("baseSave", dataHolder,
					workmenCompVO.getCommonVO());
		}

		LOGGER.info("Request for save workmen details completed in workmen SVC");
		return baseVO;
	}

	private BaseVO loadWorkmenDetails(LoadDataInputVO loadInputVO,
			WorkmenCompVO workmenCompVO, PolicyDataVO policyDataVO) {

		LOGGER.info("Request for load workmen details received in workmen SVC");

		WorkmenCompVO wcVO = null;
		Map<String, Class<? extends BaseVO>> dataToLoad = new LinkedHashMap<String, Class<? extends BaseVO>>();

		dataToLoad.put(SvcConstants.T_TRN_POLICY, PolicyDataVO.class);
		dataToLoad.put(SvcConstants.TABLE_ID_T_TRN_WCTPL_UNNAMED_PERSON_QUO,
				TTrnWctplUnnamedPersonQuoHolder.class);
		dataToLoad.put(SvcConstants.TABLE_ID_T_TRN_WCTPL_PERSON_QUO,
				TTrnWctplPersonQuoHolder.class);
		// dataToLoad.put( SvcConstants.TABLE_ID_T_TRN_PREMIUM_QUO,
		// TTrnPremiumVOHolder.class );

		DataHolderVO<LinkedHashMap<String, List<TableData>>> dataHolderVO = (DataHolderVO<LinkedHashMap<String, List<TableData>>>) baseLoadSvc
				.invokeMethod(com.Constant.CONST_BASELOAD, loadInputVO, dataToLoad);

		List<TableData> unnamedPersonList = dataHolderVO.getData().get(
				SvcConstants.TABLE_ID_T_TRN_WCTPL_UNNAMED_PERSON_QUO);
		List<TableData> namedPersonList = dataHolderVO.getData().get(
				SvcConstants.TABLE_ID_T_TRN_WCTPL_PERSON_QUO);
		// List<TableData> premiumList = dataHolderVO.getData().get(
		// SvcConstants.TABLE_ID_T_TRN_PREMIUM_QUO );

		if (!Utils.isEmpty(unnamedPersonList)) {
			Iterator<TableData> iterator = unnamedPersonList.iterator();
			workmenCompVO
					.setEmpTypeDetails(new com.mindtree.ruc.cmn.utils.List<EmpTypeDetailsVO>(
							EmpTypeDetailsVO.class));
			while (iterator.hasNext()) {
				TTrnWctplUnnamedPersonQuoHolder unnamedPersonHolder = (TTrnWctplUnnamedPersonQuoHolder) iterator
						.next().getTableData();
				wcVO = BeanMapper.map(unnamedPersonHolder, WorkmenCompVO.class);
				workmenCompVO.getEmpTypeDetails().add(
						wcVO.getEmpTypeDetails().get(0));
			}
			workmenCompVO.setLocationVO(wcVO.getLocationVO());
		}

		if (!Utils.isEmpty(namedPersonList)) {
			Iterator<TableData> iterator = namedPersonList.iterator();
			workmenCompVO
					.setWcEmployeeDetails(new com.mindtree.ruc.cmn.utils.List<WCNammedEmployeeVO>(
							WCNammedEmployeeVO.class));
			while (iterator.hasNext()) {
				TTrnWctplPersonQuoHolder namedPersonHolder = (TTrnWctplPersonQuoHolder) iterator
						.next().getTableData();
				wcVO = BeanMapper.map(namedPersonHolder, WorkmenCompVO.class);
				workmenCompVO.getWcEmployeeDetails().add(
						wcVO.getWcEmployeeDetails().get(0));
			}
		}
		if (!Utils.isEmpty(policyDataVO.getScheme())
				&& !Utils.isEmpty(policyDataVO.getScheme().getSchemeCode())) {
			LookUpListVO lookupvoList = SvcUtils.getLookUpCodesList(
					"PAS_COMMISSION", policyDataVO.getScheme().getSchemeCode()
							.toString(),
					SvcUtils.getKeyForCommisionCacheObj(policyDataVO));
			if (!Utils.isEmpty(policyDataVO.getCommission())) {
				workmenCompVO.setCommission(policyDataVO.getCommission());
			}
			if (Utils.isEmpty(workmenCompVO.getCommission())
					&& !Utils.isEmpty(lookupvoList.getLookUpList()))
				workmenCompVO.setCommission(lookupvoList.getLookUpList().get(0)
						.getCode().doubleValue());
		}

		LOGGER.info("Request for load workmen details completed in workmen SVC");

		return workmenCompVO;
	}

	private Long getNamedPersonBasicRiskId(
			DataHolderVO<LinkedHashMap<String, List<TableData>>> dataHolder) {

		LinkedHashMap<String, List<TableData>> dataMap = dataHolder.getData();
		List<TableData> unnamedPersonTable = dataMap
				.get(SvcConstants.TABLE_ID_T_TRN_WCTPL_UNNAMED_PERSON_QUO);
		Long basicRiskID = null;

		for (TableData<TTrnWctplUnnamedPersonQuoHolder> unnamedPerson : unnamedPersonTable) {
			if (!Utils.isEmpty(unnamedPerson.getContentID())
					&& !Utils.isEmpty(unnamedPerson.getTableData())
					&& !Utils.isEmpty(unnamedPerson.getTableData()
							.getWupEmploymentType())
					&& unnamedPerson.getTableData().getWupEmploymentType() == SvcConstants.WUP_ADMIN_EMPLOYMENT_TYPE) {
				basicRiskID = unnamedPerson.getContentID();
				break;
			}
		}
		// if null--> first element
		if (basicRiskID == null) {
			basicRiskID = unnamedPersonTable.get(0).getContentID();
		}
		return basicRiskID;
	}

	private Short getNamedPersonBasicRiskTypeId(
			DataHolderVO<LinkedHashMap<String, List<TableData>>> dataHolder) {

		LinkedHashMap<String, List<TableData>> dataMap = dataHolder.getData();
		List<TableData> unnamedPersonTable = dataMap
				.get(SvcConstants.TABLE_ID_T_TRN_WCTPL_UNNAMED_PERSON_QUO);
		Short basicRiskID = null;

		for (TableData<TTrnWctplUnnamedPersonQuoHolder> unnamedPerson : unnamedPersonTable) {
			if (!Utils.isEmpty(unnamedPerson.getContentID())
					&& !Utils.isEmpty(unnamedPerson.getTableData())
					&& !Utils.isEmpty(unnamedPerson.getTableData()
							.getWupEmploymentType())
					&& unnamedPerson.getTableData().getWupEmploymentType() == SvcConstants.WUP_ADMIN_EMPLOYMENT_TYPE) {
				basicRiskID = ((TTrnWctplUnnamedPersonQuoHolder) unnamedPerson
						.getTableData()).getWupRtCode();
				break;
			}
		}
		// if null--> first element
		if (basicRiskID == null) {
			basicRiskID = ((TTrnWctplUnnamedPersonQuoHolder) unnamedPersonTable
					.get(0).getTableData()).getWupRtCode();
		}
		return basicRiskID;
	}

	/**
	 * 
	 * @param toBeSaved2
	 * @param homeInsuranceVO
	 *            Generated id's are set to the vo
	 */
	private void mapKeyValuesToVO(
			DataHolderVO<LinkedHashMap<String, List<TableData>>> data,
			WorkmenCompVO workmenCompVO) {

		LinkedHashMap<String, List<TableData>> dataMap = data.getData();
		List<TableData> unnamedPersons = dataMap
				.get(SvcConstants.TABLE_ID_T_TRN_WCTPL_UNNAMED_PERSON_QUO);
		List<EmpTypeDetailsVO> employees = workmenCompVO.getEmpTypeDetails();
		Long contentCoverRskId = null;

		if (!Utils.isEmpty(employees) && !Utils.isEmpty(unnamedPersons)) {
			for (EmpTypeDetailsVO employee : employees) {
				for (TableData<TTrnWctplUnnamedPersonQuoHolder> unnamedPerson : unnamedPersons) {
					if ((!Utils.isEmpty(unnamedPerson.getTableData()
							.getWupEmploymentType()) && unnamedPerson
							.getTableData().getWupEmploymentType().shortValue() == employee
							.getEmpType().shortValue())
							&& (!Utils.isEmpty(unnamedPerson.getTableData()
									.getWupSumInsured()) && unnamedPerson
									.getTableData()
									.getWupSumInsured()
									.compareTo(
											BigDecimal.valueOf(employee
													.getWageroll())) == SvcConstants.zeroVal)
							&& (!Utils.isEmpty(unnamedPerson.getTableData()
									.getWupEmpLiabLmt()) && unnamedPerson
									.getTableData().getWupEmpLiabLmt()
									.compareTo(employee.getLimit()) == SvcConstants.zeroVal)
							&& (!Utils.isEmpty(unnamedPerson.getTableData()
									.getWupNoOfPerson()) && unnamedPerson
									.getTableData()
									.getWupNoOfPerson()
									.compareTo(
											Long.valueOf(employee.getNoOfEmp())) == SvcConstants.zeroVal)) {
						employee.setRiskId(unnamedPerson.getContentID());
						// unnamedPerson.getTableData().setWupEmploymentType((short)
						// 0);
						break;
					}
				}
			}
		}
	}

	/**
	 * This method is used to fetch the existing unnamed person stored in DB
	 * call updateTBeDeletedUnNamedEmp method for comparing.
	 * 
	 * @param commonVO
	 * @param unnamedPersonTableDataList
	 * @return
	 */

	private List<TableData> updateToBeDeletedUnNamedEmp(CommonVO commonVO,
			List<TableData> unnamedPersonTableDataList) {

		LoadDataInputVO inputVO = new LoadDataInputVO();
		inputVO.setIsQuote(commonVO.getIsQuote());
		inputVO.setLocCode(commonVO.getLocCode());
		inputVO.setEndtId(commonVO.getEndtId());
		inputVO.setQuoteNo(commonVO.getQuoteNo());
		inputVO.setPolicyNo(commonVO.getPolicyNo());
		Map<String, Class<? extends BaseVO>> dataToLoad = new LinkedHashMap<String, Class<? extends BaseVO>>();
		dataToLoad.put(SvcConstants.TABLE_ID_T_TRN_WCTPL_UNNAMED_PERSON_QUO,
				TTrnWctplUnnamedPersonQuoHolder.class);
		DataHolderVO<LinkedHashMap<String, List<TableData>>> dataHolderVO = (DataHolderVO<LinkedHashMap<String, List<TableData>>>) baseLoadSvc
				.invokeMethod(com.Constant.CONST_BASELOAD, inputVO, dataToLoad);
		// List<TableData> finalListToBeSaved = new ArrayList<TableData>();
		if (!Utils.isEmpty(dataHolderVO)) {
			List<TableData> unNamedEmpList = dataHolderVO.getData().get(
					SvcConstants.TABLE_ID_T_TRN_WCTPL_UNNAMED_PERSON_QUO);
			if (!Utils.isEmpty(unNamedEmpList)) {
				unnamedPersonTableDataList = SvcUtils
						.updateToBeDeletedUnNamedEmp(unNamedEmpList,
								unnamedPersonTableDataList);
			}

		}
		return unnamedPersonTableDataList;
	}

	/**
	 * This method is used to fetch the existing named person stored in DB call
	 * updateTBeDeletedNamedEmp method for comparing.
	 * 
	 * @param commonVO
	 * @param namedPersonTableDataList
	 * @return
	 */
	private List<TableData> updateToBeDeletedNamedEmp(CommonVO commonVO,
			List<TableData> namedPersonTableDataList) {

		LoadDataInputVO inputVO = new LoadDataInputVO();
		inputVO.setIsQuote(commonVO.getIsQuote());
		inputVO.setLocCode(commonVO.getLocCode());
		inputVO.setEndtId(commonVO.getEndtId());
		inputVO.setQuoteNo(commonVO.getQuoteNo());
		inputVO.setPolicyNo(commonVO.getPolicyNo());
		Map<String, Class<? extends BaseVO>> dataToLoad = new LinkedHashMap<String, Class<? extends BaseVO>>();
		dataToLoad.put(SvcConstants.TABLE_ID_T_TRN_WCTPL_PERSON_QUO,
				TTrnWctplPersonQuoHolder.class);
		DataHolderVO<LinkedHashMap<String, List<TableData>>> dataHolderVO = (DataHolderVO<LinkedHashMap<String, List<TableData>>>) baseLoadSvc
				.invokeMethod(com.Constant.CONST_BASELOAD, inputVO, dataToLoad);
		// List<TableData> finalListToBeSaved = new ArrayList<TableData>();
		if (!Utils.isEmpty(dataHolderVO)) {
			List<TableData> unNamedEmpList = dataHolderVO.getData().get(
					SvcConstants.TABLE_ID_T_TRN_WCTPL_PERSON_QUO);
			if (!Utils.isEmpty(unNamedEmpList)) {
				namedPersonTableDataList = SvcUtils.updateToBeDeletedNamedEmp(
						unNamedEmpList, namedPersonTableDataList);
			}

		}
		return namedPersonTableDataList;
	}

	public BaseSaveSvc getBaseSaveSvc() {
		return baseSaveSvc;
	}

	public void setBaseSaveSvc(BaseSaveSvc baseSaveSvc) {
		this.baseSaveSvc = baseSaveSvc;
	}

	public BaseLoadSvc getBaseLoadSvc() {
		return baseLoadSvc;
	}

	public void setBaseLoadSvc(BaseLoadSvc baseLoadSvc) {
		this.baseLoadSvc = baseLoadSvc;
	}

}
