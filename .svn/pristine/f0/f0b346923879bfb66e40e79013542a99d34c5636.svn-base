/**
 * 
 */
package com.rsaame.pas.wc.svc;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.cmn.currency.Currency;
import com.rsaame.pas.com.svc.BaseLoadSvc;
import com.rsaame.pas.dao.cmn.PASStoredProcedure;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.endorse.svc.GeneralInfoCommonSvc;
import com.rsaame.pas.policyAction.svc.PolicyActionCommonSvc;
import com.rsaame.pas.premiumHelper.PremiumHelper;
import com.rsaame.pas.query.constants.QueryConstants;
import com.rsaame.pas.rating.svc.RatingServiceInvoker;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.app.LookUpListVO;
import com.rsaame.pas.vo.app.PolicyCommentsHolder;
import com.rsaame.pas.vo.bus.EmpTypeDetailsVO;
import com.rsaame.pas.vo.bus.EndorsmentVO;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.bus.WorkmenCompVO;
import com.rsaame.pas.vo.cmn.CommonVO;
import com.rsaame.pas.vo.cmn.LoadDataInputVO;
import com.rsaame.pas.vo.cmn.TableData;
import com.rsaame.pas.vo.svc.TTrnPremiumVOHolder;
import java.text.ParseException;

/**
 * @author Sarath varier
 * @since Phase 4 - WC monoline implementation
 *
 */
public class WCMonolineSvc extends BaseService{

	private final static com.mindtree.ruc.cmn.log.Logger LOGGER = com.mindtree.ruc.cmn.log.Logger.getLogger( WCMonolineSvc.class );

	private GeneralInfoCommonSvc commonGenSvcBean;
	private WCPremiumDetailsSvc wcpremiumSvc;
	private WorkmenDetailsSvc workmenSvc;
	private BaseLoadSvc baseLoadSvc;
	private static Long zeroVal = 0L;
	private PolicyActionCommonSvc polComnSvc; /* approve quote or policy */
	
	@Override
	public Object invokeMethod( String methodName, Object... args ){

		LOGGER.info( "Save WC monoline risk details started" );

		BaseVO returnValue = null;

		if( SvcConstants.SAVE_WC_INSURANCE.equals( methodName ) ){
			returnValue = saveWorkmenCompDetails( (BaseVO) args[ 0 ] );
		}
		else if( SvcConstants.LOAD_WC_INSURANCE.equals( methodName ) ){
			returnValue = loadWorkmenCompDetails( (BaseVO) args[ 0 ] );
		}
		else if( SvcConstants.APPROVE_QUO_WC_INSURANCE.equals( methodName ) ){
			returnValue = approveQuoWorkmenComp( (BaseVO) args[ 0 ] );
		}
		else if( SvcConstants.PRORATE_PREMIUM_WC_INSURANCE.equals( methodName ) ){
			proratePremium(  (BaseVO) args[ 0 ] ,  ( PolicyDataVO ) args[ 0 ]  );
		}
		else if(SvcConstants.SAVE_RENEWAL_QUOTE.equals( methodName )){
			returnValue = saveRenewalQuote( (BaseVO) args[ 0 ] );
		}
		else if(SvcConstants.LOAD_RENEWAL_QUOTE.equals( methodName )){
			returnValue = loadWorkmenCompDetails( (BaseVO) args[ 0 ] );
		}
		else if( SvcConstants.LOAD_WC_VAT_RATE_AND_CODE.equals( methodName ) ){
			returnValue = wcfetchVatRateForVatCode( (BaseVO) args[ 0 ] );
		}
		LOGGER.info( "Save WC monoline risk details completed" );

		return returnValue;
	}

	private BaseVO wcfetchVatRateForVatCode(BaseVO baseVO) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		WorkmenCompVO workmenCompVO = (WorkmenCompVO) baseVO;
		DataHolderVO<Object[]> dataHolder = new DataHolderVO<Object[]>();

		double vatTaxPerc = 0.0;
			if( !Utils.isEmpty(workmenCompVO.getAuthenticationInfoVO()) )
			{
		vatTaxPerc =  DAOUtils.VatCodeAndVatRateFromCodeMaster(workmenCompVO.getVatCode(), 
				workmenCompVO.getAuthenticationInfoVO().getAccountingDate());
			}
			if( !Utils.isEmpty(vatTaxPerc)) {
			result.put( "vatRate", vatTaxPerc ); // Vat Percent			
			LOGGER.debug( "**In fetchVatRateForVatCode()**  vat Rate" + vatTaxPerc);
		} else{			
			result.put( "vatRate", 0.0 );
		}
	
		Object[] inpObjects = { result };
		dataHolder.setData( inpObjects );
		return dataHolder;	
	
	}

	private BaseVO saveWorkmenCompDetails( BaseVO baseVO ){

		WorkmenCompVO workmenCompVO = (WorkmenCompVO) baseVO;

		PolicyDataVO policyDataVO = (PolicyDataVO) commonGenSvcBean.invokeMethod( SvcConstants.LOAD_GEN_INFO, workmenCompVO );

		if(!Utils.isEmpty(policyDataVO.getScheme().getExpiryDate()) && Utils.isEmpty( policyDataVO.getPolExpiryDate())){
			policyDataVO.setPolExpiryDate( policyDataVO.getScheme().getExpiryDate() );
		}
		policyDataVO.setCommission( workmenCompVO.getCommission() );
		
		//prorate premium
		proratePremium( workmenCompVO, policyDataVO );
		
		//Server side validation to stop giving discount on top of minimum premium
		if(workmenCompVO.getPremiumVO().getPremiumAmt() == workmenCompVO.getPremiumVO().getMinPremium().doubleValue()
				&& workmenCompVO.getPremiumVO().getDiscOrLoadPerc() < 0){
			throw new BusinessException("pas.wc.minPrm.validation",null, 
					"WC Monoline - Premium below minimum premium for Policy ID " + workmenCompVO.getCommonVO().getPolicyId());
		}
		// save named & unnamed employees
		workmenSvc.invokeMethod(SvcConstants.SAVE_WORKMEN_DETAILS, workmenCompVO, policyDataVO);

		// save premium
		wcpremiumSvc.invokeMethod( SvcConstants.SAVE_PREMIUM, workmenCompVO, policyDataVO );

		//save UW		
		//uwqaSaveCommonSvc.invokeMethod( SvcConstants.SAVE_UW_QUES_ANS, workmenCompVO, policyDataVO );

		if( workmenCompVO.getCommonVO().getIsQuote() ){
			//Insert the default clauses for MONOLINE flow
			DAOUtils.saveUpdateDefaultClauses(policyDataVO.getCommonVO());
			PASStoredProcedure sp = null;
			sp = (PASStoredProcedure) Utils.getBean( "UpdPrmQuoEndtMonoline" );
			if( LOGGER.isInfo() ) LOGGER.info( "Invoking PRO_UPDATE_PRM_QUO_ENDT_MONOLINE procedure with inputs {[" );
			sp.call( workmenCompVO.getCommonVO().getPolicyId(), workmenCompVO.getCommonVO().getEndtId(),Integer.valueOf(Utils.getSingleValueAppConfig( workmenCompVO.getCommonVO().getLob()+"_CLASS_CODE" ) ));
			
			DAOUtils.callUpdateStatusProcedureForIssueQuote((PolicyDataVO) baseVO);
			policyDataVO.getCommonVO().setStatus( SvcConstants.POL_STATUS_ACTIVE);
		}
		else{
			PASStoredProcedure sp = null;
			sp = (PASStoredProcedure) Utils.getBean( "UpdPrmPolEndtMonoline" );
			if( LOGGER.isInfo() ) LOGGER.info( "Invoking PRO_UPDATE_PRM_POL_ENDT_MONOLINE procedure with inputs {[" );
			sp.call( workmenCompVO.getCommonVO().getPolicyId(), workmenCompVO.getCommonVO().getEndtId(),Integer.valueOf(Utils.getSingleValueAppConfig( workmenCompVO.getCommonVO().getLob()+"_CLASS_CODE" )));
			//sp.call( policyDataVo.getCommonVO().getPolicyId(), policyDataVo.getCommonVO().getEndtId(),Integer.valueOf(Utils.getSingleValueAppConfig( commonVO.getLob()+"_CLASS_CODE" )));
			
			java.util.List<Object> valueHolder = DAOUtils.getSqlResultSingleColumn( QueryConstants.FETCH_TOTAL_POLICY_PRM_TOTAL,(HibernateTemplate) Utils.getBean( com.Constant.CONST_HIBERNATETEMPLATE ), workmenCompVO.getCommonVO()
					.getPolicyId(), workmenCompVO.getCommonVO().getEndtId() );
			double polPrm = 0.0;
			if( !Utils.isEmpty( valueHolder ) && valueHolder.size() > 0 && !Utils.isEmpty( valueHolder.get( 0 ) ) ){
				polPrm = ( (BigDecimal) valueHolder.get( 0 ) ).doubleValue();
			}
			DAOUtils.updateSql( QueryConstants.UPDATE_PRM,(HibernateTemplate) Utils.getBean( com.Constant.CONST_HIBERNATETEMPLATE ), BigDecimal.valueOf( polPrm ),  workmenCompVO.getCommonVO().getEndtId() , workmenCompVO.getCommonVO()
					.getPolicyId() );
			
		}
		//Generate Endt Text
		SvcUtils.generateEndtTextMonoline(workmenCompVO);
		
		return baseVO;
	}

	private BaseVO loadWorkmenCompDetails( BaseVO baseVO ){

		CommonVO commonVO = ((PolicyDataVO) baseVO).getCommonVO();
		WorkmenCompVO workmenCompVO = new WorkmenCompVO();
		workmenCompVO.setCommonVO( commonVO );

		PolicyDataVO policyDataVO = (PolicyDataVO) commonGenSvcBean.invokeMethod( SvcConstants.LOAD_GEN_INFO, workmenCompVO );
		LOGGER.debug( "General Info loaded - Policy Id : " + policyDataVO.getPolicyId() );

		LoadDataInputVO loadDataInputVO = new LoadDataInputVO();
		loadDataInputVO.setIsQuote( commonVO.getIsQuote() );
		loadDataInputVO.setQuoteNo( commonVO.getQuoteNo() );
		loadDataInputVO.setEndtId( commonVO.getEndtId() );
		loadDataInputVO.setLocCode( commonVO.getLocCode() );
		loadDataInputVO.setPolicyNo( commonVO.getPolicyNo() );
		loadDataInputVO.setDocCode( commonVO.getDocCode() );
		loadDataInputVO.setPolEffectiveDate( commonVO.getPolEffectiveDate() );

		workmenCompVO = (WorkmenCompVO) workmenSvc.invokeMethod( "loadWorkmenDetails", loadDataInputVO, workmenCompVO, policyDataVO );
		workmenCompVO = (WorkmenCompVO) wcpremiumSvc.invokeMethod( "loadPremiumDetails", loadDataInputVO, workmenCompVO, policyDataVO );
		
		workmenCompVO.setCommonVO( commonVO );
		workmenCompVO.setGeneralInfo( policyDataVO.getGeneralInfo() );
		workmenCompVO.setScheme( policyDataVO.getScheme() );
		workmenCompVO.setClassCode( policyDataVO.getPolicyClassCode() );
		workmenCompVO.setEndEffectiveDate( policyDataVO.getEndEffectiveDate() );
		workmenCompVO.setAuthenticationInfoVO( policyDataVO.getAuthenticationInfoVO() );
		workmenCompVO.setPolicyType(policyDataVO.getPolicyType());
		workmenCompVO.setPolicyTerm(policyDataVO.getPolicyTerm());
		workmenCompVO.setPolRenTermTxt(policyDataVO.getPolRenTermTxt());
		
		//142244
		if( !Utils.isEmpty( workmenCompVO.getPremiumVO()) && !Utils.isEmpty(policyDataVO.getPremiumVO().getVatTaxPerc()) ){
			workmenCompVO.getPremiumVO().setVatTaxPerc(policyDataVO.getPremiumVO().getVatTaxPerc());
			workmenCompVO.getPremiumVO().setVatTax(policyDataVO.getPremiumVO().getVatTax());
			workmenCompVO.getPremiumVO().setVatCode(policyDataVO.getVatCode());
		}
		
		
		
		if( !Utils.isEmpty( workmenCompVO.getGeneralInfo().getInsured()) && !Utils.isEmpty(commonVO.getVatRegNo()) )
		workmenCompVO.getGeneralInfo().getInsured().setVatRegNo(commonVO.getVatRegNo());
		workmenCompVO.setVatCode(policyDataVO.getVatCode());
		
		if( Utils.isEmpty( workmenCompVO.getLocationVO() )){
			 workmenCompVO.setLocationVO( new LocationVO());
		}
		//if( Utils.isEmpty( workmenCompVO.getLocationVO().getHazardLevel() ) ){
			Integer tradeGroup = null;
			LookUpListVO lookUpVOList = SvcUtils.getLookUpCodesList( "PAS_B_TYP_TRADE_GROUP" , 
					workmenCompVO.getGeneralInfo().getInsured().getBusType().toString(), "ALL" );
			
			if( !Utils.isEmpty( lookUpVOList ) && !Utils.isEmpty( lookUpVOList.getLookUpList() ) && !Utils.isEmpty( lookUpVOList.getLookUpList().get( 0 ) )
					&& !Utils.isEmpty( lookUpVOList.getLookUpList().get( 0 ).getCode() ) ){
				tradeGroup = Integer.valueOf( lookUpVOList.getLookUpList().get( 0 ).getCode().toString() );
			}
			workmenCompVO.getLocationVO().setOccTradeGroup(tradeGroup);
		//}
		populatePackagePremium( workmenCompVO, loadDataInputVO );

		//populate EndorsmentVo in case of endorsement no greater than 0
		if( commonVO.getEndtNo() > 0
				&& ( Flow.AMEND_POL.equals( commonVO.getAppFlow() ) || Flow.VIEW_POL.equals( commonVO.getAppFlow() ) || ( Flow.RESOLVE_REFERAL.equals( commonVO.getAppFlow() ) && !commonVO
						.getIsQuote() ) ) ){
			populateEndorsmentVO( workmenCompVO );
		}

		return workmenCompVO;
	}

	private BaseVO approveQuoWorkmenComp( BaseVO baseVO ){
		LOGGER.info( "Entering WC Insurance Approve Quote" );
		saveWorkmenCompDetails( baseVO );
		//DataHolderVO<Object[]> dataHolder = (DataHolderVO<Object[]>) baseVO;
		//HomeInsuranceVO homeInsuranceVO = (HomeInsuranceVO) dataHolder.getData()[ 0 ];
		WorkmenCompVO workmenCompVO = (WorkmenCompVO) baseVO;
		PolicyCommentsHolder polCommHoler = new PolicyCommentsHolder();
		polCommHoler.setCommonDetails( workmenCompVO.getCommonVO() );
		polComnSvc.invokeMethod( "approveQuote", polCommHoler );
		return workmenCompVO;
		
	}

	/**
	 * @param homeInsuranceVO
	 * calculates the package premium that need to be displayed in home risk page
	 */
	private void populatePackagePremium( WorkmenCompVO workmenCompVO, LoadDataInputVO loadDataInputVO ){

		double packagePremium = 0;
		double packageActualPremium = 0;

		if( !Utils.isEmpty( workmenCompVO.getEmpTypeDetails() ) ){

			for( EmpTypeDetailsVO empTypeDetails : workmenCompVO.getEmpTypeDetails() ){
				if(!Utils.isEmpty( empTypeDetails.getPremium() )){
				packagePremium += empTypeDetails.getPremium().getPremiumAmt();
				packageActualPremium += empTypeDetails.getPremium().getPremiumAmtActual();
				}
			}
			if( Utils.isEmpty( workmenCompVO.getPremiumVO() ) ){
				workmenCompVO.setPremiumVO( new PremiumVO() );
			}

			workmenCompVO.getPremiumVO().setMinPremium( (BigDecimal) wcpremiumSvc.invokeMethod( SvcConstants.GET_CONFIG_MIN_PRM, workmenCompVO ) );

			if( workmenCompVO.getCommonVO().getStatus() == SvcConstants.DEL_SEC_LOC_STATUS && !workmenCompVO.getCommonVO().getIsQuote() ){
				
				// Ticket - 120595 : 22-Aug-16 : Himanish : Access to variable lob removed, rather the variable is passed as parameter in the getPremiumForProrate function : Start
				//PremiumHelper.lob = SvcConstants.COMMON_FLOW; 
					
				
				DataHolderVO<HashMap<String, Double>> canceledPrm = PremiumHelper.getPremiumForProrate( workmenCompVO.getCommonVO().getPolicyId(), 
						workmenCompVO.getCommonVO().getEndtId(),workmenCompVO.getCommonVO().getLob() );
				// Ticket - 120595 : 22-Aug-16 : Himanish : Access to variable lob removed, rather the variable is passed as parameter in the getPremiumForProrate function : End
				
				Double discAmt = PremiumHelper.getSplPrm( workmenCompVO.getCommonVO(), workmenCompVO.getCommonVO().getEndtId(),
						new Short[]{ SvcConstants.SC_PRM_COVER_LOADING, SvcConstants.SC_PRM_COVER_DISCOUNT, Short.valueOf( SvcConstants.SPECIAL_COVER_MIN_PRM ),
								Short.valueOf( SvcConstants.SC_PRM_COVER_GOVT_TAX ), Short.valueOf( SvcConstants.SC_PRM_COVER_POLICY_FEE ) },
						(HibernateTemplate) Utils.getBean( com.Constant.CONST_HIBERNATETEMPLATE ) );
				Double minPrm = PremiumHelper.getSplPrm( workmenCompVO.getCommonVO(), workmenCompVO.getCommonVO().getEndtId(),
						new Short[]{ Short.valueOf( SvcConstants.SPECIAL_COVER_MIN_PRM ) }, (HibernateTemplate) Utils.getBean( com.Constant.CONST_HIBERNATETEMPLATE ) );
				workmenCompVO.getPremiumVO().setMinPremiumApplied( BigDecimal.valueOf( minPrm ) );

				workmenCompVO.getPremiumVO().setPremiumAmt( canceledPrm.getData().get( SvcConstants.NEW_ANNUALIZED_PREMIUM ) - discAmt + minPrm );
			}
			else{
				workmenCompVO.getPremiumVO().setPremiumAmt( packagePremium );
				addDeletedCoverPremium( workmenCompVO, loadDataInputVO );
				Double minPrm = PremiumHelper.getSplPrm( workmenCompVO.getCommonVO(), workmenCompVO.getCommonVO().getEndtId(),
						new Short[]{ Short.valueOf( SvcConstants.SPECIAL_COVER_MIN_PRM ) }, (HibernateTemplate) Utils.getBean( com.Constant.CONST_HIBERNATETEMPLATE ) );
				workmenCompVO.getPremiumVO().setMinPremiumApplied( BigDecimal.valueOf( minPrm ) );
				workmenCompVO.getPremiumVO().setPremiumAmt( workmenCompVO.getPremiumVO().getPremiumAmt() + minPrm );
			}
			workmenCompVO.getPremiumVO().setPremiumAmtActual( packageActualPremium );
			//Add the deleted cover premiums to total premium if policy journey
		}
	}

	/**
	 * @author Sarath Varier
	 * Method to fetch the deleted covers and add up the permium to total premium
	 */
	private void addDeletedCoverPremium( WorkmenCompVO workmenCompVO, LoadDataInputVO loadDataInputVO ){

		double packagePremium = workmenCompVO.getPremiumVO().getPremiumAmt();
		double actualProratedPremium = 0.0;

		if( !Utils.isEmpty( workmenCompVO.getPremiumVO() ) && !Utils.isEmpty( workmenCompVO.getPremiumVO().getActualProratedPremium() ) ){
			actualProratedPremium = workmenCompVO.getPremiumVO().getActualProratedPremium();
		}

		if( !workmenCompVO.getCommonVO().getIsQuote() ){
			// Sonar fix
			loadDataInputVO.setStatus(Integer.valueOf( 4 ));
			Map<String, Class<? extends BaseVO>> dataToLoad = new LinkedHashMap<String, Class<? extends BaseVO>>();
			dataToLoad.put( SvcConstants.TABLE_ID_T_TRN_PREMIUM_QUO, TTrnPremiumVOHolder.class );
			@SuppressWarnings( { "unchecked", "rawtypes" } )
			DataHolderVO<LinkedHashMap<String, List<TableData>>> dataHolderVO = 
					(DataHolderVO<LinkedHashMap<String, List<TableData>>>) baseLoadSvc.invokeMethod( "baseLoad", loadDataInputVO, dataToLoad );
			@SuppressWarnings( "rawtypes" )
			List<TableData> premiumList = dataHolderVO.getData().get( SvcConstants.TABLE_ID_T_TRN_PREMIUM_QUO );

			if( !Utils.isEmpty( premiumList ) ){
				@SuppressWarnings( "rawtypes" )
				Iterator<TableData> iterator = premiumList.iterator();
				while( iterator.hasNext() ){
					TTrnPremiumVOHolder premiumVOHolder = (TTrnPremiumVOHolder) iterator.next().getTableData();
					packagePremium += ( premiumVOHolder.getPrmPremium() ).doubleValue();
					/* This is done to calculate the prorated premium without cover level discount */
					actualProratedPremium += ( premiumVOHolder.getPrmPremium() ).doubleValue();
				}
			}
		}
 
		workmenCompVO.getPremiumVO().setActualProratedPremium( actualProratedPremium );
		workmenCompVO.getPremiumVO().setPremiumAmt( packagePremium );
	}
	
	private void populateEndorsmentVO( WorkmenCompVO workmenCompVO ){

		com.mindtree.ruc.cmn.utils.List<EndorsmentVO> endorsements = new com.mindtree.ruc.cmn.utils.List<EndorsmentVO>( EndorsmentVO.class );
		/* Update endt no and id before loading premium page*/

		EndorsmentVO endorsmentVO = null;
		Long endtId = workmenCompVO.getCommonVO().getEndtId();
		Long polId = workmenCompVO.getCommonVO().getPolicyId();
		boolean isViewMode = false;

		if( !Utils.isEmpty( endtId ) ){

			if( Flow.VIEW_POL.equals( workmenCompVO.getCommonVO().getAppFlow() ) || Flow.RESOLVE_REFERAL
					.equals( workmenCompVO.getCommonVO().getAppFlow() ) ){
				BaseVO baseVO1 = TaskExecutor.executeTasks( "CAPTURE_AMEND_POLICY_ENDT_TEXT", workmenCompVO );
				workmenCompVO = (WorkmenCompVO) baseVO1;
				if( !Utils.isEmpty( workmenCompVO.getEndorsmentVO() ) ){
					endorsmentVO = workmenCompVO.getEndorsmentVO().get( 0 );
					/*String endtTxt = "";						//commented unused assignment of endTxt - sonar violation fix - review change
					if( !Utils.isEmpty( workmenCompVO.getEndorsmentVO() ) ){
						for( EndorsmentVO endt : workmenCompVO.getEndorsmentVO() ){
							if( !Utils.isEmpty( endt.getEndText() ) ) {
								endtTxt += endt.getEndText() + "\n";
							}
						}
					}
					endtTxt.trim();*/
					//endorsmentVO.setEndText( endtTxt );
					isViewMode = true;
				}
				else{
					endorsmentVO = new EndorsmentVO();
					endorsmentVO.setEndNo( workmenCompVO.getCommonVO().getEndtNo() );
					endorsmentVO.setEndtId( workmenCompVO.getCommonVO().getEndtId() );
					endorsmentVO.setPolicyId( workmenCompVO.getCommonVO().getPolicyId() );
					endorsmentVO.setSlNo( 1 );
				}
			}
			else{
				endorsmentVO = new EndorsmentVO();
				endorsmentVO.setEndNo( workmenCompVO.getCommonVO().getEndtNo() );
				endorsmentVO.setEndtId( workmenCompVO.getCommonVO().getEndtId() );
				endorsmentVO.setPolicyId( workmenCompVO.getCommonVO().getPolicyId() );
				endorsmentVO.setSlNo( 1 );
			}

			PremiumVO oldPremiumVO = new PremiumVO();
			PremiumVO newPremiumVO = new PremiumVO();

			/*
			 * After cancellation of policy the premium will be 0, and since the need is to display the 
			 * refund amount before actually cancellation of policy new premium for calculation is taken as 0.0
			 */
			Double newPremiumAmt = 0.0;
			
			
			
			// Ticket - 120595 : 22-Aug-16 : Himanish : Access to variable lob removed, the variable is passed as parameter in the getPremiumForProrate function : Start
			//PremiumHelper.lob = SvcConstants.COMMON_FLOW;
			DataHolderVO<HashMap<String, Double>> premiumHolder = PremiumHelper.getPremiumForProrate( polId, endtId,workmenCompVO.getCommonVO().getLob() );
			// Ticket - 120595 : 22-Aug-16 : Himanish : Access to variable lob removed, rather the variable is passed as parameter in the getPremiumForProrate function : End
			
			newPremiumAmt = premiumHolder.getData().get( SvcConstants.NEW_ANNUALIZED_PREMIUM );

			Double currentPremiumAmt = premiumHolder.getData().get( SvcConstants.OLD_ANNUALIZED_PREMIUM );

			newPremiumVO.setPremiumAmt( newPremiumAmt ); // New premium amount.
			oldPremiumVO.setPremiumAmt( currentPremiumAmt ); // Old premium amount.

			endorsmentVO.setOldPremiumVO( oldPremiumVO );
			endorsmentVO.setPremiumVO( newPremiumVO );
			endorsmentVO.setEndEffDate( workmenCompVO.getEndEffectiveDate() );
			endorsmentVO.setEndDate( workmenCompVO.getScheme().getExpiryDate() );
			endorsmentVO.setEndNo( workmenCompVO.getCommonVO().getEndtNo() );
			//Added by Anveshan
			if(isViewMode) {
				endorsements = workmenCompVO.getEndorsmentVO();
				endorsements.set( 0,endorsmentVO );
			}
			else {
				endorsements.add(endorsmentVO);
			}
			
			/*Collections.sort(endorsements, new Comparator<EndorsmentVO>(){
				@Override
				public int compare(EndorsmentVO o1, EndorsmentVO o2) {
					// TODO Auto-generated method stub
					if (Utils.isEmpty(o1.getSlNo())) return -1;
					else if  (Utils.isEmpty(o2.getSlNo())) return 1;
					else return (o1.getSlNo().compareTo(o2.getSlNo()));
				}
			});*/
			workmenCompVO.setEndorsmentVO( endorsements );

		}

	}
	
	/**
	 * Method to get the prorate the premium
	 * 
	 * @param homeInsuranceVO
	 */
	private void proratePremium( BaseVO baseVO, PolicyDataVO policyDataVO ){
		WorkmenCompVO workmenCompVO = (WorkmenCompVO) baseVO;

		List<EmpTypeDetailsVO> empTypeDetails = workmenCompVO.getEmpTypeDetails();

		Double proratedPremium = null;
		Double totalPremium = 0.0;

		Double actualProratedPremium = 0.0;
//		if( Utils.isEmpty( occupancy ) ){
//			occupancy = DAOUtils.getOccDetails( workmenCompVO.getLocationVO().getOccTradeGroup().shortValue() );
//		}
		Integer rskCat = null;
		Integer rtCode = null;
		for( EmpTypeDetailsVO emp : empTypeDetails ){
			Integer coverCode = emp.getCoverCode();
			//Integer rtCode = emp.getRiskType();
			//Integer rskCat = emp.getRiskCat();
			//Integer rskCat = occupancy.getOcpRcCode();
			//Integer rtCode = emp.getEmpType();
			Long riskId = emp.getRiskId();
			
			Double currentPremium = emp.getPremium().getPremiumAmt() + ( ( emp.getPremium().getPremiumAmt() * 
					( emp.getPremium().getDiscOrLoadPerc() / 100 ) ) );

			if( !policyDataVO.getCommonVO().getIsQuote() 
					|| ( policyDataVO.getCommonVO().getIsQuote() && !policyDataVO.getPolicyTerm().equals(Integer.valueOf( 1 )))){
				proratedPremium = getProratedPremium( workmenCompVO, policyDataVO, coverCode, rtCode , rskCat, currentPremium,riskId);

				/* This is done to calculate the prorated premium without cover level discount */
				actualProratedPremium += getProratedPremium( workmenCompVO, policyDataVO, coverCode, rtCode , rskCat, emp.getPremium().getPremiumAmt(),riskId);
			}
			else{
				proratedPremium = actualProratedPremium = currentPremium;
			}
			if( !Utils.isEmpty( proratedPremium ) ){
				totalPremium+= proratedPremium;
				emp.getPremium().setPremiumAmt( proratedPremium.doubleValue() );
			}
			
			proratedPremium = null;
		}
		

		workmenCompVO.getPremiumVO().setVatCode(workmenCompVO.getVatCode());
		workmenCompVO.getPremiumVO().setPremiumAmt( totalPremium );
		/* This is done to calculate the prorated premium without cover level discount */
		workmenCompVO.getPremiumVO().setActualProratedPremium( actualProratedPremium );
		//Add the deleted cover premiums to total premium if policy journey
		LoadDataInputVO loadDataInputVO = new LoadDataInputVO();
		loadDataInputVO.setIsQuote( workmenCompVO.getCommonVO().getIsQuote() );
		loadDataInputVO.setQuoteNo( workmenCompVO.getCommonVO().getQuoteNo() );
		loadDataInputVO.setEndtId( workmenCompVO.getCommonVO().getEndtId() );
		loadDataInputVO.setLocCode( workmenCompVO.getCommonVO().getLocCode() );
		loadDataInputVO.setPolicyNo( workmenCompVO.getCommonVO().getPolicyNo() );
		loadDataInputVO.setDocCode( workmenCompVO.getCommonVO().getDocCode() );
		loadDataInputVO.setPolEffectiveDate( workmenCompVO.getCommonVO().getPolEffectiveDate() );
		addDeletedCoverPremium(workmenCompVO, loadDataInputVO);
		applyMinPremium( workmenCompVO );
	}

	/**
	 * Method to get the prorated premium
	 * 
	 * @param homeVo
	 * @param policyDataVO 
	 * @param coverCode
	 * @param rtCode
	 * @return
	 */
	private Double getProratedPremium( WorkmenCompVO workmenCompVO, PolicyDataVO policyDataVO, Integer coverCode, 
			Integer rtCode, Integer rskCat, Double currentPremium, Long riskId ){
		CommonVO commonVO = workmenCompVO.getCommonVO();
		List<Object[]> previousData = null;
		Double proratedPremium = null;
		BigDecimal previousPremium = BigDecimal.ZERO;
		BigDecimal prevAnnualPrm = BigDecimal.ZERO;

		Date polStartDate = policyDataVO.getScheme().getEffDate();
		Long newPolExpiryDays = zeroVal;
		Long oldPolExpiryDays = zeroVal;

		Date prevStartDate = policyDataVO.getScheme().getEffDate();
		Date prevEndDate = policyDataVO.getScheme().getExpiryDate();
		
		if( !Utils.isEmpty( policyDataVO.getEndEffectiveDate() ) ){
			newPolExpiryDays = PremiumHelper.getDifference( policyDataVO.getPolExpiryDate(), policyDataVO.getEndEffectiveDate() );
		}
		else{
			newPolExpiryDays = PremiumHelper.getDifference( policyDataVO.getPolExpiryDate(), polStartDate );
		}

		
		List<Object[]>  result = DAOUtils.getSqlResultForPas (  QueryConstants.POLICY_EXT,commonVO.getPolicyId(),
				commonVO.getEndtId());
		
		int policyExtDays = 0;
		if(!Utils.isEmpty( result ) && result.size() > 0 ){
			policyExtDays = ( Integer.valueOf( result.get( 0 )[0].toString() ) ).intValue() ;
			policyExtDays = policyExtDays < 0 ? policyExtDays * -1 : policyExtDays;
			/*if(policyExtDays > 0){
				policyExtDays=policyExtDays+1;
			}*/
		}
		
		//Assuming policy extension is not done
		oldPolExpiryDays = newPolExpiryDays;
		
		
		if( !Utils.isEmpty( riskId ) ){
			previousData = DAOUtils.getPreviousDataRiskId( commonVO.getPolicyId(), commonVO.getEndtId(), riskId );
		}
				
		if( !Utils.isEmpty( previousData ) && !Utils.isEmpty( previousData.get( 0 ) ) ){
			
			BigDecimal prevPremium = (BigDecimal) previousData.get( 0 )[ 0 ];
			prevAnnualPrm = (BigDecimal) previousData.get( 0 )[ 2 ];
			
			//While re adding the deleted covers, pro rated premium should be calculated without considering previous premium
			BigDecimal status = (BigDecimal) previousData.get( 0 )[ 3 ];
			if(status.equals( BigDecimal.valueOf( 4 ) )){
				prevPremium = BigDecimal.ZERO;
				prevAnnualPrm = BigDecimal.ZERO;
				previousPremium = BigDecimal.ZERO;
			}
			if(!Utils.isEmpty( prevPremium )){
				previousPremium = prevPremium;
			}
			/*
			 * Added for #131212
			 */
			prevStartDate = (Date) previousData.get( 0 )[ 1 ];
			prevEndDate = (Date) previousData.get( 0 )[ 4];
		}
		if(policyExtDays>0){
			oldPolExpiryDays = oldPolExpiryDays - policyExtDays;
		}
		
		long oldPolicyDays = PremiumHelper.getDifference( prevEndDate, prevStartDate );
		if(policyExtDays==0){
			oldPolicyDays=oldPolExpiryDays;
		}
		BigDecimal currPremium =  BigDecimal.valueOf( currentPremium );
		proratedPremium = PremiumHelper.getProratedPrm( oldPolicyDays, newPolExpiryDays, previousPremium,
				prevAnnualPrm, currPremium, polStartDate ).doubleValue();
		proratedPremium = Double.parseDouble( Currency.getUnformattedScaledCurrency( BigDecimal.valueOf( proratedPremium ), 
				workmenCompVO.getCommonVO().getLob().name() ) );
		LOGGER.debug( "PRORATE PREM "+proratedPremium + " cov code "+ coverCode + " riskId "+ riskId );
		return proratedPremium;
	}

	private void applyMinPremium( WorkmenCompVO workmenCompVO ){
		workmenCompVO.getPremiumVO().setMinPremium( (BigDecimal) wcpremiumSvc.invokeMethod( SvcConstants.GET_CONFIG_MIN_PRM, workmenCompVO ) );
		double minPrmToApply = ( (BigDecimal) wcpremiumSvc.invokeMethod( SvcConstants.GET_MIN_PRM_TO_APPLY, workmenCompVO ) ).doubleValue();
	//	if( minPrmToApply > 0 ){
		workmenCompVO.getPremiumVO().setMinPremiumApplied( BigDecimal.valueOf( minPrmToApply ) );
		workmenCompVO.getPremiumVO().setPremiumAmt( workmenCompVO.getPremiumVO().getPremiumAmt() + minPrmToApply );
	//	}
	}

	private BaseVO saveRenewalQuote( BaseVO baseVO ){

		PolicyDataVO dataVO = (PolicyDataVO) baseVO;
		dataVO.setPolicyClassCode(dataVO.getClassCode());
		RatingServiceInvoker ratingService = (RatingServiceInvoker) Utils.getBean( "LOB_RATING_" + dataVO.getCommonVO().getLob() );
		baseVO = ratingService.invokeRating( baseVO );
		WorkmenCompVO workmenCompVO = (WorkmenCompVO) baseVO;
		workmenCompVO = UpdateVatforRenewalQuote(workmenCompVO);
		saveWorkmenCompDetails(baseVO);
		return baseVO;
	}
	
	//142244 renewal quote issue WC 
    private WorkmenCompVO UpdateVatforRenewalQuote(WorkmenCompVO workmenCompVO) {
          Double vatTaxAmount = 0.0;
          Double vatTaxPerc = 0.0;
          Double premiumAmt = 0.0;
          Double vatbleAmt = 0.0;
          BigDecimal MinPremiumApplied =new BigDecimal(0);
          int diffInDays=0;
          int policyPeriodDays=0;
          
          Double loading=0.0;
          
          Date polEffectiveDate = null;
          Date polExpiryDate = null;
          Date vatEffDate = null;
                
          Map<Integer, Double> vatResults = new HashMap<Integer, Double>();
          
    
          /* String vatStartDate = Utils.getSingleValueAppConfig("TRAVEL_VAT_START_DATE"); // 01/01/2018 */
          String defaultDateFormat = Utils.getSingleValueAppConfig("DEFAULT_DATE_FORMAT"); // MM/dd/yyyy
          try {
                //SvcUtils.populateVatDt();
                vatEffDate = new SimpleDateFormat(defaultDateFormat).parse(SvcUtils.populateVatDt());
          } catch (ParseException e) {
                LOGGER.error(e.getMessage());
                e.printStackTrace();
          }  
          
          List<Date> polDateList = new LinkedList<Date>();
          polDateList.add(vatEffDate); // VAT Effective Date for Policy
          
          //MinPremiumApplied=workmenCompVO.getPremiumVO().getMinPremiumApplied();
          premiumAmt=workmenCompVO.getPremiumVO().getPremiumAmt();
          
      	
          
         if(!Utils.isEmpty(workmenCompVO.getPremiumVO().getMinPremiumApplied().doubleValue()))
           {
        	 
        	  	premiumAmt=premiumAmt+ workmenCompVO.getPremiumVO().getMinPremiumApplied().doubleValue();
                 	
            }
    
         if(!Utils.isEmpty( workmenCompVO.getPremiumVO().getDiscOrLoadPerc() )){
        	 premiumAmt  += (workmenCompVO.getPremiumVO().getDiscOrLoadPerc()*premiumAmt)/100;
 		}
     /*	if(!Utils.isEmpty( workmenCompVO.getPremiumVO().getDiscOrLoadAmt() ))
     	  {
     	    premiumAmt=premiumAmt+workmenCompVO.getPremiumVO().getDiscOrLoadAmt().doubleValue();
     	  }*/
        
     	if(!Utils.isEmpty( workmenCompVO.getPremiumVO().getSpecialDiscount() ))
     	{
     		premiumAmt=premiumAmt+workmenCompVO.getPremiumVO().getSpecialDiscount();
     	}
            
          vatTaxPerc=workmenCompVO.getPremiumVO().getVatTaxPerc();
          
          Comparator<Date> cmp = new Comparator<Date>() {
              @Override
              public int compare(Date date1, Date date2) {
                  return date1.compareTo(date2);
              }
          };
        polEffectiveDate = workmenCompVO.getCommonVO().getPolEffectiveDate();
        polDateList.add(polEffectiveDate);
          polExpiryDate = workmenCompVO.getScheme().getExpiryDate();
          diffInDays = (int) ((polExpiryDate.getTime() - Collections.max(polDateList, cmp).getTime()) / (1000 * 60 * 60 * 24)) + 1;
          policyPeriodDays = (int) ((polExpiryDate.getTime() - polEffectiveDate.getTime()) / (1000 * 60 * 60 * 24)) + 1;
            
           
          if(policyPeriodDays != 0) {
                      vatTaxAmount = premiumAmt * diffInDays/policyPeriodDays * vatTaxPerc/100;
                      vatbleAmt= (Double) (premiumAmt * diffInDays/policyPeriodDays);
          
                }
          workmenCompVO.getPremiumVO().setVatTax(vatTaxAmount);
          workmenCompVO.getPremiumVO().setVatablePrm(vatbleAmt);
          return workmenCompVO;
    }

	public GeneralInfoCommonSvc getCommonGenSvcBean(){
		return commonGenSvcBean;
	}

	public void setCommonGenSvcBean( GeneralInfoCommonSvc commonGenSvcBean ){
		this.commonGenSvcBean = commonGenSvcBean;
	}

	public WorkmenDetailsSvc getWorkmenSvc(){
		return workmenSvc;
	}

	public void setWorkmenSvc( WorkmenDetailsSvc workmenSvc ){
		this.workmenSvc = workmenSvc;
	}

	public WCPremiumDetailsSvc getWcpremiumSvc(){
		return wcpremiumSvc;
	}

	public void setWcpremiumSvc( WCPremiumDetailsSvc wcpremiumSvc ){
		this.wcpremiumSvc = wcpremiumSvc;
	}

	public BaseLoadSvc getBaseLoadSvc(){
		return baseLoadSvc;
	}

	public void setBaseLoadSvc( BaseLoadSvc baseLoadSvc ){
		this.baseLoadSvc = baseLoadSvc;
	}
	public PolicyActionCommonSvc getPolComnSvc() {
		return polComnSvc;
	}

	public void setPolComnSvc(PolicyActionCommonSvc polComnSvc) {
		this.polComnSvc = polComnSvc;
	}
}
