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
import com.rsaame.pas.cmn.currency.Currency;
import com.rsaame.pas.com.svc.BaseLoadSvc;
import com.rsaame.pas.com.svc.BaseSaveSvc;
import com.rsaame.pas.dao.cmn.ICommonOpDAO;
import com.rsaame.pas.dao.premium.IMinPremiumDAO;
import com.rsaame.pas.premiumHelper.PremiumHelper;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.app.LookUpListVO;
import com.rsaame.pas.vo.bus.EmpTypeDetailsVO;
import com.rsaame.pas.vo.bus.LOB;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.WorkmenCompVO;
import com.rsaame.pas.vo.cmn.CommonVO;
import com.rsaame.pas.vo.cmn.LoadDataInputVO;
import com.rsaame.pas.vo.cmn.TableData;
import com.rsaame.pas.vo.svc.TMasCashCustomerVOHolder;
import com.rsaame.pas.vo.svc.TTrnPremiumVOHolder;

/**
 * @author Sarath Varier
 * @since Ph 4 WC implementation
 *
 */
public class WCPremiumDetailsSvc extends BaseService{

	private final static com.mindtree.ruc.cmn.log.Logger LOGGER = com.mindtree.ruc.cmn.log.Logger.getLogger( WCPremiumDetailsSvc.class );

	private BaseSaveSvc baseSaveSvc;
	private BaseLoadSvc baseLoadSvc;
	private ICommonOpDAO commonOpDAO;
	private IMinPremiumDAO minPrmMonolineDao;

	@Override
	public Object invokeMethod( String methodName, Object... args ){

		LOGGER.info( "service for WC monoline premium started" );

		Object returnValue = null;

		if( SvcConstants.SAVE_PREMIUM.equals( methodName ) ){
			returnValue = savePremiumDetails( (WorkmenCompVO) args[ 0 ], (PolicyDataVO) args[ 1 ]);
		}
		else if( SvcConstants.LOAD_PREMIUM.equals( methodName ) ){
			returnValue = loadPremiumDetails( (LoadDataInputVO) args[ 0 ], (WorkmenCompVO) args[ 1 ], (PolicyDataVO) args[ 2 ] );
		}
		else if( SvcConstants.GET_CONFIG_MIN_PRM.equals( methodName ) ){
			returnValue = getConfiguredMinPremium( (BaseVO) args[ 0 ] );
		}
		else if( SvcConstants.GET_MIN_PRM_TO_APPLY.equals( methodName ) ){
			returnValue = getMiniumPremiumToApply( (BaseVO) args[ 0 ] );
		}


		LOGGER.info( "service for WC monoline premium completed" );
		return returnValue;
	}

	private BaseVO savePremiumDetails( WorkmenCompVO workmenCompVO, PolicyDataVO policyDataVO ){

		WorkmenCompVO WcVO = null;
		Object[] splCodesForBasicCovers = null;
		Boolean fetchedCodesForBasicCovers = false;
		
		//TMasOccupancy occupancy = (TMasOccupancy) occupancyVO; //DAOUtils.getOccDetails( workmenCompVO.getLocationVO().getOccTradeGroup().shortValue() );

		DataHolderVO<LinkedHashMap<String, List<TableData>>> dataHolder = new DataHolderVO<LinkedHashMap<String, List<TableData>>>();
		LinkedHashMap<String, List<TableData>> dataMap = new LinkedHashMap<String, List<TableData>>();

		TableData<TTrnPremiumVOHolder> premiumTableData = null;
		TMasCashCustomerVOHolder cashCustomerQuo = null;
		
		TableData<PolicyDataVO> policyTableData = new TableData<PolicyDataVO>();
		TableData<TMasCashCustomerVOHolder> cashCustTableData = new TableData<TMasCashCustomerVOHolder>();

		List<TableData> premiumTableDataList = new ArrayList<TableData>( 0 );
		List<TableData> policyTableDataList = new ArrayList<TableData>( 0 );
		List<TableData> cashCustTableDataList = new ArrayList<TableData>( 0 );
		
		for( EmpTypeDetailsVO unnamedEmployee : workmenCompVO.getEmpTypeDetails() ){

			premiumTableData = new TableData<TTrnPremiumVOHolder>();
			WcVO = CopyUtils.copySerializableObject( workmenCompVO );
			WcVO.setEmpTypeDetails( new com.mindtree.ruc.cmn.utils.List<EmpTypeDetailsVO>( EmpTypeDetailsVO.class ) );
			WcVO.getEmpTypeDetails().add( unnamedEmployee );
			TTrnPremiumVOHolder premiumQuo = BeanMapper.map( WcVO, TTrnPremiumVOHolder.class );

			premiumQuo.setPrmRcCode( 0 );
			premiumQuo.setPrmRscCode( Integer.valueOf( Utils.getSingleValueAppConfig( "WC_BASIC_RISK_CODE" ) ) );
			premiumQuo.setPrmRtCode( unnamedEmployee.getEmpType() );

			if( !fetchedCodesForBasicCovers ){
				splCodesForBasicCovers = commonOpDAO.getSpecialCodes( premiumQuo );
				fetchedCodesForBasicCovers = true;
			}
			
			if( !Utils.isEmpty( splCodesForBasicCovers ) ){
				setValuesToPojo( premiumQuo, splCodesForBasicCovers );
			}
			premiumTableData.setTableData( premiumQuo );
			premiumTableDataList.add( premiumTableData );
		}
		premiumTableDataList.addAll( SvcUtils.getPremiumSplCovers( workmenCompVO ) );
		premiumTableDataList = updateToBeDeletedPrmRecs( workmenCompVO.getCommonVO(), premiumTableDataList );
		
		if(!Utils.isEmpty( policyDataVO.getPremiumVO() )){
			policyDataVO.getPremiumVO().setPremiumAmt(workmenCompVO.getPremiumVO().getPremiumAmt() );
			policyDataVO.getPremiumVO().setPremiumAmtActual(workmenCompVO.getPremiumVO().getPremiumAmtActual() );
			policyDataVO.getPremiumVO().setGovtTax( workmenCompVO.getPremiumVO().getGovtTax());
			policyDataVO.getPremiumVO().setPolicyFees( workmenCompVO.getPremiumVO().getPolicyFees() );
			policyDataVO.getPremiumVO().setVatTax(workmenCompVO.getPremiumVO().getVatTax());
			policyDataVO.getPremiumVO().setVatTaxPerc(workmenCompVO.getPremiumVO().getVatTaxPerc());
			if(!Utils.isEmpty(workmenCompVO.getPremiumVO().getVatCode()))
				policyDataVO.getPremiumVO().setVatCode(workmenCompVO.getPremiumVO().getVatCode());
			if(!Utils.isEmpty(workmenCompVO.getVatablePrm()))
				policyDataVO.getPremiumVO().setVatablePrm(workmenCompVO.getVatablePrm());
			policyDataVO.setVatablePrm(workmenCompVO.getVatablePrm());
		}

		policyTableData.setTableData( policyDataVO );
		policyTableDataList.add( policyTableData );
		
		cashCustomerQuo = BeanMapper.map( policyDataVO, TMasCashCustomerVOHolder.class );
		cashCustTableData.setTableData( cashCustomerQuo );
		cashCustTableDataList.add( cashCustTableData );
		
		dataMap.put( SvcConstants.TABLE_ID_T_TRN_POLICY, policyTableDataList );
		dataMap.put( SvcConstants.TABLE_ID_T_MAS_CASH_CUSTOMER_QUO, cashCustTableDataList );
		dataMap.put( SvcConstants.TABLE_ID_T_TRN_PREMIUM_QUO, premiumTableDataList );
		dataHolder.setData( dataMap );
		baseSaveSvc.invokeMethod( "baseSave", dataHolder, workmenCompVO.getCommonVO() );
		
		return workmenCompVO;
	}

	private BaseVO loadPremiumDetails( LoadDataInputVO inputVO, WorkmenCompVO workmenCompVO, PolicyDataVO policyDataVO ){
		
		Map<String, Class<? extends BaseVO>> dataToLoad = new LinkedHashMap<String, Class<? extends BaseVO>>();

		dataToLoad.put( SvcConstants.T_TRN_POLICY, PolicyDataVO.class );
		dataToLoad.put( SvcConstants.TABLE_ID_T_TRN_PREMIUM_QUO, TTrnPremiumVOHolder.class );
		
		DataHolderVO<LinkedHashMap<String, List<TableData>>> dataHolderVO = (DataHolderVO<LinkedHashMap<String, List<TableData>>>) 
				baseLoadSvc.invokeMethod( "baseLoad", inputVO, dataToLoad );
		
		List<TableData> premiumList = dataHolderVO.getData().get( SvcConstants.TABLE_ID_T_TRN_PREMIUM_QUO );
		
		if( !Utils.isEmpty( premiumList ) ){
			Iterator<TableData> iterator = premiumList.iterator();
			
			while( iterator.hasNext() ){
				TTrnPremiumVOHolder premiumHolder = (TTrnPremiumVOHolder) iterator.next().getTableData();
				workmenCompVO = BeanMapper.map( premiumHolder, workmenCompVO );
			}
		}
		
		return workmenCompVO;
	}

	private BigDecimal getConfiguredMinPremium( BaseVO baseVO ){
		WorkmenCompVO workmenCompVO = (WorkmenCompVO) baseVO;
		BigDecimal minPremiumPerYear = minPrmMonolineDao.getConfiguredMinPremium( workmenCompVO.getScheme().getTariffCode(), 
				Short.valueOf( Utils.getSingleValueAppConfig( "WC_SECTION_ID" ) ),
				Integer.valueOf( workmenCompVO.getScheme().getPolicyCode() ), LOB.WC );
		BigDecimal proratedMinPremium = new BigDecimal(minPremiumPerYear.toString());
		if(!workmenCompVO.getPolicyTerm().equals(Integer.valueOf( 1))){
			proratedMinPremium = PremiumHelper.getProratedBasePremium((PolicyDataVO) baseVO, minPremiumPerYear);
		}
		return proratedMinPremium;
	}
	
	private BigDecimal getMiniumPremiumToApply( BaseVO baseVO ){
		BigDecimal miniumPremiumToApply = minPrmMonolineDao.getMiniumPremiumToApply( baseVO, LOB.WC );
		/**
		 * CR for WC Monoline. If total annual wage roll is lesser than specified limit, premium should be minimum premium
		 */
		BigDecimal SI = BigDecimal.ZERO;
		BigDecimal minSI = BigDecimal.ZERO;
		WorkmenCompVO wcVO = (WorkmenCompVO) baseVO;
		if(!Utils.isEmpty(wcVO.getEmpTypeDetails())){
			for(EmpTypeDetailsVO emp: wcVO.getEmpTypeDetails()){
				if(!Utils.isEmpty( emp.getWageroll() ))
				{
					SI = SI.add(BigDecimal.valueOf(emp.getWageroll()));
				}
			}
		}
		LookUpListVO limitList = SvcUtils.getLookUpCodesList("PAS_WC_MIN_PRM_LIMIT", wcVO.getScheme().getTariffCode().toString(), "ALL");
		if( !Utils.isEmpty(limitList) && !Utils.isEmpty(limitList.getLookUpList()) ){
			minSI = limitList.getLookUpList().get(0).getCode();
		}
		
		if( SI.compareTo(minSI) > 0 ){
			miniumPremiumToApply = miniumPremiumToApply
					.compareTo(BigDecimal.ZERO) > 0 ? miniumPremiumToApply
					: BigDecimal.ZERO;
		}
		return BigDecimal.valueOf(Double.parseDouble(Currency
				.getUnformattedScaledCurrency(miniumPremiumToApply, wcVO
						.getCommonVO().getLob().name())));
	}
	
	private void setValuesToPojo( TTrnPremiumVOHolder trnPremiumQuoVOHolder, Object[] splCodes ){

		if( !Utils.isEmpty( splCodes[ 0 ] ) ) trnPremiumQuoVOHolder.setPrmSitypeCode( ( (BigDecimal) splCodes[ 0 ] ).byteValue() );
		if( !Utils.isEmpty( splCodes[ 1 ] ) ) trnPremiumQuoVOHolder.setPrmFnCode( ( (BigDecimal) splCodes[ 1 ] ).byteValue() );
		if( !Utils.isEmpty( splCodes[ 2 ] ) ) trnPremiumQuoVOHolder.setPrmRateType( ( (BigDecimal) splCodes[ 2 ] ).byteValue() );

	}
	
	private List<TableData> updateToBeDeletedPrmRecs( CommonVO commonVO, List<TableData> premiumTableDataList ){

		LoadDataInputVO inputVO = new LoadDataInputVO();
		inputVO.setIsQuote( commonVO.getIsQuote() );
		inputVO.setLocCode( commonVO.getLocCode() );
		inputVO.setEndtId( commonVO.getEndtId() );
		inputVO.setQuoteNo( commonVO.getQuoteNo() );
		inputVO.setPolicyNo( commonVO.getPolicyNo() );
		Map<String, Class<? extends BaseVO>> dataToLoad = new LinkedHashMap<String, Class<? extends BaseVO>>();
		dataToLoad.put( SvcConstants.TABLE_ID_T_TRN_PREMIUM_QUO, TTrnPremiumVOHolder.class );
		DataHolderVO<LinkedHashMap<String, List<TableData>>> dataHolderVO = (DataHolderVO<LinkedHashMap<String, List<TableData>>>) baseLoadSvc.invokeMethod( "baseLoad", inputVO,
				dataToLoad );
		 //List<TableData> finalListToBeSaved = new ArrayList<TableData>();
		if( !Utils.isEmpty( dataHolderVO ) ){
			List<TableData> premiumList = dataHolderVO.getData().get( SvcConstants.TABLE_ID_T_TRN_PREMIUM_QUO );
			if( !Utils.isEmpty( premiumList ) ){
				premiumTableDataList = SvcUtils.updateToBeDeletedPremiums( premiumList, premiumTableDataList );
			}

		}
		return premiumTableDataList;
	}

	public BaseSaveSvc getBaseSaveSvc(){
		return baseSaveSvc;
	}

	public void setBaseSaveSvc( BaseSaveSvc baseSaveSvc ){
		this.baseSaveSvc = baseSaveSvc;
	}

	public BaseLoadSvc getBaseLoadSvc(){
		return baseLoadSvc;
	}

	public void setBaseLoadSvc( BaseLoadSvc baseLoadSvc ){
		this.baseLoadSvc = baseLoadSvc;
	}
	public ICommonOpDAO getCommonOpDAO(){
		return commonOpDAO;
	}

	public void setCommonOpDAO( ICommonOpDAO commonOpDao ){
		this.commonOpDAO = commonOpDao;
	}

	public IMinPremiumDAO getMinPrmMonolineDao(){
		return minPrmMonolineDao;
	}

	public void setMinPrmMonolineDao( IMinPremiumDAO minPrmMonolineDao ){
		this.minPrmMonolineDao = minPrmMonolineDao;
	}
}
