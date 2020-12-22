/**
 * 
 */
package com.rsaame.pas.wcMonoline.ui;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.cmn.currency.Currency;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.lookup.ui.DropDownRendererHepler;
import com.rsaame.pas.monoline.baseNavigation.IRHHelper;
import com.rsaame.pas.premiumHelper.PremiumHelper;
import com.rsaame.pas.query.constants.QueryConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.app.LookUpListVO;
import com.rsaame.pas.vo.app.LookUpVO;
import com.rsaame.pas.vo.bus.EmpTypeDetailsVO;
import com.rsaame.pas.vo.bus.EndorsmentVO;
import com.rsaame.pas.vo.bus.LOB;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.bus.WorkmenCompVO;
import com.rsaame.pas.vo.cmn.CommonVO;

/**
 * 
 * This class is created as part of WC-MonoLine implementation
 * This class and BaseRH class, together handles the requests generated as part of WC-MonoLine work flow
 * 
 */
public class WCMonolineRHHelper implements IRHHelper{

	@Override
	public BaseVO mapRequestToVO(HttpServletRequest request, HttpServletResponse response, CommonVO commonVO) {

		if (( !Utils.isEmpty( request.getParameter( com.Constant.CONST_PAGETYPE ) )) && ( request.getParameter( com.Constant.CONST_PAGETYPE ).equals(com.Constant.CONST_GENINFO) ) ){

			PolicyDataVO policyDataVO = BeanMapper.map( request, PolicyDataVO.class );
			if( !Utils.isEmpty( request.getParameter( "mastInsure" )) && request.getParameter( "mastInsure" ).equals( "N" ) ){
				policyDataVO.getGeneralInfo().getInsured().setUpdateMaster( false );
			}
			else{
				policyDataVO.getGeneralInfo().getInsured().setUpdateMaster( true );
			}
			policyDataVO.setPolicyType( policyDataVO.getScheme().getPolicyCode() );
			policyDataVO.setPolicyClassCode( Integer.valueOf( Utils.getSingleValueAppConfig( commonVO.getLob() + "_CLASS_CODE" ) ) );
			policyDataVO.getGeneralInfo().getInsured().setCcgCode( AppConstants.WC_CCG_CODE.intValue() );
			//added to fix defect #129210
			Integer brkCode =policyDataVO.getGeneralInfo().getSourceOfBus().getBrokerName();
            if( !Utils.isEmpty( brkCode ) ){

                  java.util.List<Object> valueHolder = DAOUtils.getSqlResultSingleColumnPas( QueryConstants.GET_BROKER_ACC_STATUS, brkCode );
                  BigDecimal bkrStatus = null;
                  if( !Utils.isEmpty( valueHolder ) && valueHolder.size() > 0 && !Utils.isEmpty( valueHolder.get( 0 ) ) ){
                         bkrStatus = ( (BigDecimal) valueHolder.get( 0 ) );
                  }
                  if( !Utils.isEmpty( bkrStatus ) && bkrStatus.compareTo( BigDecimal.ZERO ) == 0 ){
                         throw new BusinessException( "cmn.brkblocked.cl", null, "The Brk account is blocked" );
                  }
            }


				//142244 WC
				String  vatcode = request.getParameter("quote_type_code");
				String  vatcodeonGI = request.getParameter("vatCodeonGI");
			
							
			   //	String  vatPer = request.getParameter("vatTaxPerc");
				//PremiumVO premiumVO = policyDataVO.getPremiumVO();
				if(Utils.isEmpty(policyDataVO.getPremiumVO())){
					policyDataVO.setPremiumVO(new PremiumVO());
					
				}
				
				if (!Utils.isEmpty(vatcode)){
					policyDataVO.setVatCode(Integer.valueOf(vatcode));
					policyDataVO.getPremiumVO().setVatCode(Integer.valueOf(vatcode));
					//policyDataVO.getPremiumVO().setVatTaxPerc(Integer.valueOf(vatPer));
				}
				
				else if (!Utils.isEmpty(vatcodeonGI)){
					policyDataVO.setVatCode(Integer.valueOf(vatcodeonGI));
					policyDataVO.getPremiumVO().setVatCode(Integer.valueOf(vatcodeonGI));
					//policyDataVO.getPremiumVO().setVatTaxPerc(Integer.valueOf(vatPer));
				}
			
			if( !Utils.isEmpty( policyDataVO.getGeneralInfo().getInsured().getAddress().getEmirates() )
					&& !Utils.isEmpty( policyDataVO.getGeneralInfo().getInsured().getAddress().getEmirates() ) ){
				policyDataVO.getGeneralInfo().getInsured().setCity(policyDataVO.getGeneralInfo().getInsured().getAddress().getEmirates());
			}
			
			UserProfile userProfile = (UserProfile)request.getSession().getAttribute(AppConstants.SESSION_USER_PROFILE_VO);
			if(!Utils.isEmpty(userProfile)){
				policyDataVO.setLoggedInUser(userProfile);
			}
			commonVO.setLoggedInUser(userProfile);
			policyDataVO.setCommonVO(commonVO);
			return policyDataVO;
		}
		else{
			LocationVO locationDetails = BeanMapper.map(request, LocationVO.class);
			WorkmenCompVO WcVO = BeanMapper.map(request, WorkmenCompVO.class);
			
			// Vrequest.setAttribute("vatTax" vatablePrm
			String vatablePrm = request.getParameter(com.Constant.CONST_VATABLEPRM);
			if (!Utils.isEmpty(vatablePrm)) {
				// setVatablePrm 142244
				//WcVO.setVatablePrm(Double.parseDouble(vatablePrm));
				
				com.rsaame.pas.cmn.converter.DoubleStringConverter converter = ConverterFactory.getInstance( com.rsaame.pas.cmn.converter.DoubleStringConverter.class, "", "" );
				WcVO.setVatablePrm( converter.getTypeOfA().cast( converter.getAFromB( request.getParameter( com.Constant.CONST_VATABLEPRM ) ) ) );
			}
			
			WcVO.setLocationVO(locationDetails);
			UserProfile userProfile = (UserProfile)request.getSession().getAttribute(AppConstants.SESSION_USER_PROFILE_VO);
			if(!Utils.isEmpty(userProfile)){
				WcVO.setLoggedInUser(userProfile);
			}
			commonVO.setLoggedInUser(userProfile);
			WcVO.setCommonVO(commonVO);
			
			PolicyDataVO policyDetails = (PolicyDataVO) TaskExecutor.executeTasks("GEN_INFO_COMMON_LOAD", WcVO);
			WcVO.setGeneralInfo(policyDetails.getGeneralInfo());
			WcVO.setScheme(policyDetails.getScheme());
			WcVO.setAuthenticationInfoVO( policyDetails.getAuthenticationInfoVO() );
		 	WcVO.setPolicyType( policyDetails.getPolicyType() );
			WcVO.setScheme( policyDetails.getScheme() );
			WcVO.setPolicyClassCode( policyDetails.getPolicyClassCode() );
			WcVO.setPolExpiryDate( policyDetails.getScheme().getExpiryDate() );
			WcVO.setStartDate( policyDetails.getScheme().getEffDate() );
			WcVO.setPolicyTerm(policyDetails.getPolicyTerm());
			WcVO.setPolRenTermTxt(policyDetails.getPolRenTermTxt());
			//142244 WC
			WcVO.setVatCode(policyDetails.getVatCode());
			
			if( WcVO.getScheme().getTariffCode().equals(AppConstants.WC_DMCC_TARIFF_CODE)){
				WcVO.getLocationVO().setFreeZone(AppConstants.DMCC_FREEZONE_CODE.toString());
			}
			else if( WcVO.getScheme().getTariffCode().equals(AppConstants.WC_JAFZA_TARIFF_CODE)){
				WcVO.getLocationVO().setFreeZone(AppConstants.JAFZA_FREEZONE_CODE.toString());
			}
			 
			return WcVO;
		}
	}

	@Override
	public BaseVO saveData(HttpServletRequest request, HttpServletResponse response, Response mtrucResponse, BaseVO baseVO) {
		
		PolicyDataVO policyDataVO = null;
		if ( ( !Utils.isEmpty( request.getParameter( com.Constant.CONST_PAGETYPE ) )) && ( request.getParameter( com.Constant.CONST_PAGETYPE ).equals(com.Constant.CONST_GENINFO) ) ){
			
			((PolicyDataVO)baseVO).setStatus(((PolicyDataVO)baseVO).getCommonVO().getStatus());
			
			if(Utils.isEmpty( ((PolicyDataVO)baseVO).getAppFlow() ) ) { 
				((PolicyDataVO)baseVO).setAppFlow(((PolicyDataVO)baseVO).getCommonVO().getAppFlow());
			}
			
			policyDataVO = (PolicyDataVO) TaskExecutor.executeTasks("GEN_INFO_COMMON_SAVE", baseVO);
		}
		else{
	
			buildEmployeeDetailsToSave((WorkmenCompVO)baseVO);
			policyDataVO = (PolicyDataVO) TaskExecutor.executeTasks("WC_MONOLINE_SAVE", baseVO);
			if(!policyDataVO.getCommonVO().getAppFlow().equals( Flow.AMEND_POL ) && !policyDataVO.getCommonVO().getAppFlow().equals( Flow.RESOLVE_REFERAL ))
				policyDataVO.getCommonVO().setStatus( AppConstants.QUOTE_ACTIVE );
			if( policyDataVO.getCommonVO().getIsQuote() ){
				policyDataVO.getCommonVO().setAppFlow( Flow.VIEW_QUO );
			}
			else if(!policyDataVO.getCommonVO().getAppFlow().equals( Flow.AMEND_POL ) && !policyDataVO.getCommonVO().getAppFlow().equals( Flow.RESOLVE_REFERAL )){
				policyDataVO.getCommonVO().setAppFlow( Flow.VIEW_POL );
			}
		}
		
		return policyDataVO;
	}

	@Override
	public void ratingPostProcessing(HttpServletRequest request, BaseVO baseVO) {
		WorkmenCompVO workmenCompVO = (WorkmenCompVO)baseVO;
		if(!Utils.isEmpty( workmenCompVO.getPremiumVO())){
			
			CommonVO commonVO = workmenCompVO.getCommonVO();
			//populate EndorsmentVo in case of endorsement no greater than 0
			if(!Utils.isEmpty(commonVO.getEndtEffectiveDate())){
				workmenCompVO.setEndEffectiveDate(commonVO.getEndtEffectiveDate());
			}
			TaskExecutor.executeTasks( "WC_MONOLINE_PRORATE_PREMIUM", workmenCompVO );
			if( commonVO.getEndtNo() > 0 && 
				( Flow.AMEND_POL.equals( commonVO.getAppFlow() ) || 
						Flow.VIEW_POL.equals( commonVO.getAppFlow() ) || 
						( Flow.RESOLVE_REFERAL.equals( commonVO.getAppFlow() ) && 
								!commonVO.getIsQuote() ) ) ){
				
				/*if(!Utils.isEmpty(commonVO.getEndtEffectiveDate())){
					workmenCompVO.setEndEffectiveDate(commonVO.getEndtEffectiveDate());
				}*/
				SvcUtils.populateEndorsmentVO(workmenCompVO);
				//TaskExecutor.executeTasks( "WC_MONOLINE_PRORATE_PREMIUM", workmenCompVO );
				if( !Utils.isEmpty( workmenCompVO.getEndorsmentVO() ) && !Utils.isEmpty( workmenCompVO.getEndorsmentVO().get(0) ) 
						&& !Utils.isEmpty( workmenCompVO.getEndorsmentVO().get(0).getPremiumVO() ) 
						&& !Utils.isEmpty( workmenCompVO.getPremiumVO() ) ){
					workmenCompVO.getEndorsmentVO().get(0).getPremiumVO().setPremiumAmt(workmenCompVO.getPremiumVO().getPremiumAmt());
				}
			}
			setPremiumValues(workmenCompVO, request, workmenCompVO.getCommonVO());
		}
		if( workmenCompVO.getScheme().getTariffCode().equals(AppConstants.WC_DMCC_TARIFF_CODE)){
			workmenCompVO.getLocationVO().setFreeZone(AppConstants.DMCC_FREEZONE_CODE.toString());
		}
		else if( workmenCompVO.getScheme().getTariffCode().equals(AppConstants.WC_JAFZA_TARIFF_CODE)){
			workmenCompVO.getLocationVO().setFreeZone(AppConstants.JAFZA_FREEZONE_CODE.toString());
		}
		if(workmenCompVO.getScheme().getTariffCode().equals(AppConstants.WC_DMCC_TARIFF_CODE) ||
				workmenCompVO.getScheme().getTariffCode().equals(AppConstants.WC_JAFZA_TARIFF_CODE)){
			request.setAttribute( "freezoneDisabled", "true" );
		}
		PolicyVO policyVO = new PolicyVO();
		
		//
		//policyVO.setPolVatAmt(workmenCompVO.getPolVattableAmt());
		policyVO.setScheme(workmenCompVO.getScheme());
		policyVO.setIsQuote(workmenCompVO.getCommonVO().getIsQuote());
		BaseVO empTypeBaseVO = TaskExecutor.executeTasks( "WC_PAGE", policyVO );
		DataHolderVO<List<EmpTypeDetailsVO>> empTypeDets = (DataHolderVO<List<EmpTypeDetailsVO>>) empTypeBaseVO;
		request.setAttribute( "wcEmpTypeDetsList", empTypeDets );
		request.setAttribute( AppConstants.PAGE_VALUE, (WorkmenCompVO)baseVO );

	}

	@Override
	public void postSaveProcessing(HttpServletRequest request,
			HttpServletResponse response, Response mtrucResponse, BaseVO baseVO) {
		
		PolicyContext policyContext = PolicyContextUtil.getPolicyContext( request );
		policyContext.setAppFlow( ((PolicyDataVO)baseVO).getCommonVO().getAppFlow() );
		policyContext.setCommonDetails(((PolicyDataVO)baseVO).getCommonVO());
	}

	@Override
	public BaseVO loadData(HttpServletRequest request, HttpServletResponse response, BaseVO baseVO) {
		
		String navigationParam = request.getParameter( "navigation" );
		String navigationAttr = (String)request.getAttribute( "navigation" );
		
		if ( ( !Utils.isEmpty( request.getParameter( com.Constant.CONST_PAGETYPE ) )) && ( request.getParameter( com.Constant.CONST_PAGETYPE ).equals(com.Constant.CONST_GENINFO) ) &&
			( ( !Utils.isEmpty(navigationParam) && ( navigationParam.equals("SAVE") || navigationParam.equals("LOAD")) ) 
		   || ( !Utils.isEmpty(navigationAttr) && (navigationAttr.equals("SAVE") || navigationAttr.equals("LOAD"))) ))
		{			
			baseVO = (PolicyDataVO) TaskExecutor.executeTasks("GEN_INFO_COMMON_LOAD", baseVO);
			
			/*if(request.getParameter( "navigation" ).equals("NEXT")){
				PolicyVO policyVO = new PolicyVO();
				policyVO.setScheme(new SchemeVO());
				policyVO.getScheme().setTariffCode(204);
				policyVO.setIsQuote(true);
				BaseVO empTypeBaseVO = TaskExecutor.executeTasks( "WC_PAGE", policyVO );
				DataHolderVO<List<EmpTypeDetailsVO>> empTypeDets = (DataHolderVO<List<EmpTypeDetailsVO>>) empTypeBaseVO;
				request.setAttribute( "wcEmpTypeDetsList", empTypeDets );
			}*/
		}
		else{
			//Radar fix
			WorkmenCompVO workmenCompVO = null; //new WorkmenCompVO();
			workmenCompVO = (WorkmenCompVO) TaskExecutor.executeTasks("WC_MONOLINE_LOAD", baseVO);
			
			// 142244 WC
			if (!Utils.isEmpty(workmenCompVO.getVatCode())) {
				@SuppressWarnings("unchecked")
				DataHolderVO<Object[]> holder1 = (DataHolderVO<Object[]>) TaskExecutor
						.executeTasks("LOAD_WC_VAT_RATE_AND_CODE",
								workmenCompVO);
				@SuppressWarnings("unchecked")
				Map<String, Object> vat = (Map<String, Object>) holder1
						.getData()[0];

				// LOGGER.debug("**********WC vatRate**********"+vat.get(com.Constant.CONST_VATRATE));
				// LOGGER.debug("**********vatCode**********"+vat.get("vatCode"));

				if (Utils.isEmpty(workmenCompVO.getPremiumVO())) {
					workmenCompVO.setPremiumVO(new PremiumVO());
				}

				workmenCompVO.getPremiumVO().setVatTaxPerc(
						(Double) vat.get(com.Constant.CONST_VATRATE));
				workmenCompVO.setVatTaxPerc((Double) vat.get(com.Constant.CONST_VATRATE));
				request.setAttribute("vatTaxPerc", vat.get(com.Constant.CONST_VATRATE));
			}
		//	homeInsuranceVO.getPremiumVO().setVatCode((Integer) vat.get("vatCode"));
			
			workmenCompVO.setPolExpiryDate( workmenCompVO.getScheme().getExpiryDate() );
			
			if(!Utils.isEmpty(workmenCompVO) && Utils.isEmpty(workmenCompVO.getLocationVO())){
				workmenCompVO.setLocationVO(new LocationVO());
			}
			if( workmenCompVO.getScheme().getTariffCode().equals(AppConstants.WC_DMCC_TARIFF_CODE)){
				workmenCompVO.getLocationVO().setFreeZone(AppConstants.DMCC_FREEZONE_CODE.toString());
			}
			else if( workmenCompVO.getScheme().getTariffCode().equals(AppConstants.WC_JAFZA_TARIFF_CODE)){
				workmenCompVO.getLocationVO().setFreeZone(AppConstants.JAFZA_FREEZONE_CODE.toString());
			}
			if(workmenCompVO.getScheme().getTariffCode().equals(AppConstants.WC_DMCC_TARIFF_CODE) ||
					workmenCompVO.getScheme().getTariffCode().equals(AppConstants.WC_JAFZA_TARIFF_CODE)){
				request.setAttribute( "freezoneDisabled", "true" );
			}
			
			PolicyVO policyVO = new PolicyVO();
			policyVO.setScheme(workmenCompVO.getScheme());
			policyVO.setIsQuote(workmenCompVO.getCommonVO().getIsQuote());
			
			BaseVO empTypeBaseVO = TaskExecutor.executeTasks( "WC_PAGE", policyVO );
			DataHolderVO<List<EmpTypeDetailsVO>> empTypeDets = (DataHolderVO<List<EmpTypeDetailsVO>>) empTypeBaseVO;
			request.setAttribute( "wcEmpTypeDetsList", empTypeDets );

			BaseVO baseLookUpVO=null;
			LookUpVO lookUpVO=new LookUpVO();
			lookUpVO.setCategory("COUNTRY");
			lookUpVO.setLevel1("ALL");
			lookUpVO.setLevel2("ALL");
			baseLookUpVO= TaskExecutor.executeTasks("LOOKUP_INFO", lookUpVO);
			LookUpListVO lookUpList = new LookUpListVO();
			if(baseLookUpVO instanceof LookUpListVO){
				lookUpList = DropDownRendererHepler.getLookFilteredList((LookUpListVO) baseLookUpVO,request.getSession(false));
			}
			request.setAttribute(AppConstants.COUNTRY_LOOKUP_VAL, lookUpList.getLookUpList().get(0).getCode());
			
						

		/*	double vatTaxPerc = SvcUtils.getLookUpCode("VATTAX","1", "151", "5");
			if(!Utils.isEmpty(vatTaxPerc)){
				
			if(Utils.isEmpty(workmenCompVO.getPremium()))
				
				workmenCompVO.setVatTaxPerc(vatTaxPerc);
			request.setAttribute("vatTaxPerc", vatTaxPerc);
			}*/
		return workmenCompVO;
		}
		return baseVO;
	}

	@Override
	public void mapVOTORequest(HttpServletRequest request,
			HttpServletResponse response, BaseVO baseVO) {
		
		if(!Utils.isEmpty(request.getParameter(AppConstants.COUNTRY_LOOKUP_VAL)))
			request.setAttribute( AppConstants.COUNTRY_LOOKUP_VAL, request.getParameter(AppConstants.COUNTRY_LOOKUP_VAL).toString() );
		
		if( baseVO instanceof WorkmenCompVO && !Utils.isEmpty( ((WorkmenCompVO)baseVO).getPremiumVO())){
			setPremiumValues((WorkmenCompVO)baseVO, request, ((WorkmenCompVO)baseVO).getCommonVO());
			request.setAttribute( AppConstants.FUNTION_NAME, ((WorkmenCompVO)baseVO).getCommonVO().getAppFlow().toString() );
			
/*			//142244 WC
			
			if ( !Utils.isEmpty(((WorkmenCompVO)baseVO).getVatCode().toString())){
			request.setAttribute( "vatCodeonGI", ((WorkmenCompVO)baseVO).getVatCode());
			request.setAttribute( "quote_type_code", ((WorkmenCompVO)baseVO).getVatCode());}*/
		}
		
		if ( !Utils.isEmpty( request.getParameter( com.Constant.CONST_PAGETYPE ) ) && request.getParameter( com.Constant.CONST_PAGETYPE ).equals(com.Constant.CONST_GENINFO) ){
			request.setAttribute( AppConstants.PAGE_VALUE, (PolicyDataVO)baseVO );
			request.setAttribute( "policyType", ((PolicyDataVO)baseVO).getScheme().getPolicyType() );
		}
		else{
			request.setAttribute( AppConstants.PAGE_VALUE, (WorkmenCompVO)baseVO );
		}
		setDefaultRequestValues(baseVO,request);
	}

	@Override
	public Boolean isConsolidatedReferralScreen(HttpServletRequest request,
			BaseVO baseVO) {
		
		String pageType = request.getParameter(com.Constant.CONST_PAGETYPE);

		if(pageType.equalsIgnoreCase("riskPage")){
			return true;
		}
		
		return false;
	}
	
	/**
	 * @param workmenCompVO
	 * @param request
	 * This function is used to set the values to the UI fields
	 */
	private void setPremiumValues( WorkmenCompVO workmenCompVO, HttpServletRequest request, CommonVO commonVO ){

		request.setAttribute( "commissionPercentage", workmenCompVO.getCommission() );
		request.setAttribute( "minPrem", workmenCompVO.getPremiumVO().getMinPremium() );
		request.setAttribute( "minPremiumApplied", workmenCompVO.getPremiumVO().getMinPremiumApplied() );

		double totalPremium = 0,premiumAfterDiscount=0,payablePremium=0,commissionAmount=0,minPrmCancellation=0.0;
		double totalDiscountAmount = 0;
		double promoDiscountAmt = 0;
		double vatAmount =0.0;
		List<Object> vatData = Collections.emptyList();
		
		PolicyDataVO policyDataVO = (PolicyDataVO) request.getAttribute( "cancelDetails" );
		
		if(!Utils.isEmpty( workmenCompVO.getPremiumVO() )){

				if(!Utils.isEmpty( policyDataVO ) &&  !Utils.isEmpty( policyDataVO.getEndorsmentVO() ) ){
					totalPremium = getTotalPremiumForCancellation(policyDataVO , workmenCompVO.getPremiumVO());
				}
				else if(!Utils.isEmpty(workmenCompVO.getCommonVO()) && !Utils.isEmpty(workmenCompVO.getCommonVO().getStatus()) && workmenCompVO.getCommonVO().getStatus()==4){
					totalPremium = workmenCompVO.getPremiumVO().getPremiumAmt() -workmenCompVO.getPremiumVO().getVatTax();
				}
				
				else  			
				{
					
					totalPremium = workmenCompVO.getPremiumVO().getPremiumAmt();
				}
			/*
			 * Min Premium is prorated while cancelling
			 */
			if( !Utils.isEmpty( policyDataVO ) && !Utils.isEmpty( policyDataVO.getEndorsmentVO() ) ){
				minPrmCancellation = getMinPremiumForCancellation( policyDataVO, workmenCompVO.getPremiumVO() );
			}
			//Prorated MinPremium should be taken from the table for cancelled records
			else if(( Utils.getSingleValueAppConfig( "POLICY_CANCELLED" ) ).equals( commonVO.getStatus().toString() ) && !Utils.isEmpty( policyDataVO ) ){
				java.util.List<Object> valueHolder1 = DAOUtils.getSqlResultSingleColumnPas( QueryConstants.GET_MIN_PREM_FROM_PRM_TABLE, policyDataVO.getCommonVO().getPolicyId(),
						workmenCompVO.getCommonVO().getEndtId() );
				if( !Utils.isEmpty( valueHolder1 ) && valueHolder1.size() > 0 && !Utils.isEmpty( valueHolder1.get( 0 ) ) ){
					minPrmCancellation = ( (BigDecimal) valueHolder1.get( 0 ) ).doubleValue();
				}
				
			}
			totalPremium = totalPremium + minPrmCancellation;
			
			if(!Utils.isEmpty( workmenCompVO.getPremiumVO().getPromoDiscPerc() )){
				totalDiscountAmount -=  ((workmenCompVO.getPremiumVO().getPromoDiscPerc()*totalPremium)/100);
				promoDiscountAmt = (workmenCompVO.getPremiumVO().getPromoDiscPerc()*totalPremium)/100;
			}
			
			if(!Utils.isEmpty( workmenCompVO.getPremiumVO().getDiscOrLoadPerc() )){
				totalDiscountAmount  += (workmenCompVO.getPremiumVO().getDiscOrLoadPerc()*totalPremium)/100;
			}
			
			totalPremium = Double.parseDouble( Currency.getUnformattedScaledCurrency(new BigDecimal( totalPremium ),LOB.WC.name() ) );
			totalDiscountAmount = Double.parseDouble( Currency.getUnformattedScaledCurrency(new BigDecimal( totalDiscountAmount ), LOB.WC.name() ) );
			
			premiumAfterDiscount = totalPremium + totalDiscountAmount;
			/*
			 * Setting minPrem as premiumAfterDiscount if the totalPrem after discounts is < minPrem
			 * And should not be set during Cancellation and for cancelled records
			 */
			/*if( premiumAfterDiscount < homeInsuranceVO.getPremiumVO().getMinPremium().doubleValue() && Utils.isEmpty( request.getAttribute( "cancelDetails" ) )
					&& !( Utils.getSingleValueAppConfig( "POLICY_CANCELLED" ) ).equals( commonVO.getStatus().toString() ) ){
				premiumAfterDiscount = homeInsuranceVO.getPremiumVO().getMinPremium().doubleValue();
			}*/
			
			payablePremium = premiumAfterDiscount + workmenCompVO.getPremiumVO().getGovtTax() + workmenCompVO.getPremiumVO().getPolicyFees();
			
		//142244 WC
			if(!Utils.isEmpty(workmenCompVO.getPremiumVO().getVatTaxPerc()))
			//vatAmount = premiumAfterDiscount*(workmenCompVO.getPremiumVO().getVatTaxPerc()/100);
		
				vatData  = AppUtils.calculateWcVatTaxAmount(workmenCompVO, premiumAfterDiscount, request);
			vatAmount=(Double) vatData.get(0);
			//vatablePrm
			//workmenCompVO.setPolVattableAmt(BigDecimal.valueOf((Double)vatData.get(1)));
			request.setAttribute(com.Constant.CONST_VATABLEPRM, vatData.get(1).toString());
			
			payablePremium =  payablePremium + vatAmount; 
			if(!Utils.isEmpty( workmenCompVO.getCommission())){
				commissionAmount = (workmenCompVO.getCommission()*premiumAfterDiscount)/100;
			}
			
		
		}
		
		/*if( Flow.VIEW_QUO.equals( commonVO.getAppFlow() ) && !Utils.isEmpty( request.getParameter( "buttonClick" ) )
				&& request.getParameter( "buttonClick" ).equalsIgnoreCase( "saveQuote" ) && commonVO.getStatus().equals( Integer.valueOf( AppConstants.QUOTE_ACTIVE ) ) ){
			request.setAttribute( "renewalC2P", "true" );
		}*/

		if(!Utils.isEmpty(workmenCompVO.getEndorsmentVO())){
			EndorsmentVO endorsmentVO = workmenCompVO.getEndorsmentVO().get( 0 );
			if(!Utils.isEmpty(request.getAttribute( "cancelDetails" )))
				request.setAttribute( com.Constant.CONST_PAYABLEPREMIUM,  Currency.getFormattedCurrency(endorsmentVO.getPremiumVO().getPremiumAmt() - 
						endorsmentVO.getOldPremiumVO().getPremiumAmt(), commonVO.getLob().toString()));
			else{
				endorsmentVO.getPremiumVO().setPremiumAmt(payablePremium);
				request.setAttribute( com.Constant.CONST_PAYABLEPREMIUM,  Currency.getFormattedCurrency(payablePremium - 
						endorsmentVO.getOldPremiumVO().getPremiumAmt(), commonVO.getLob().toString()));
			}
		}
		else if(commonVO.getAppFlow().equals(  Flow.AMEND_POL )){
			//for initial page load in AMEND there should not be any change in payable/refund premium
			request.setAttribute( com.Constant.CONST_PAYABLEPREMIUM,  Currency.getFormattedCurrency(0.0, commonVO.getLob().toString() ));
			request.setAttribute( "initialAmendPageLoad", "initialAmendPageLoad");
		}
		else {
			request.setAttribute( com.Constant.CONST_PAYABLEPREMIUM,  Currency.getFormattedCurrency(payablePremium, commonVO.getLob().toString() ));
		}
		
		
		//for the 'direct walk in',direct web,direct call center and brokers, commission field should be disabled
		if(AppUtils.isBrokerOrDirectWalkin( workmenCompVO, request )){
				request.setAttribute( "isCommissionEditable",  AppConstants.TRUE);
		}else{
				request.setAttribute( "isCommissionEditable",  AppConstants.FALSE);
		}
		
		//for ANA Scheme, policy fees is editable
		String[] schemeCodes = Utils.getMultiValueAppConfig( "POLICY_FEES_ENABLED_SCHEMES" );
		//Integer ANACode = 1204;//SvcUtils.getLookUpCode( "SCHEME", "ALL", "ALL", "ANA Scheme" );
		if( !Utils.isEmpty( workmenCompVO ) && !Utils.isEmpty( workmenCompVO.getScheme() ) && !Utils.isEmpty( workmenCompVO.getScheme().getSchemeCode() ) && Arrays.asList( schemeCodes ).contains( workmenCompVO.getScheme().getSchemeCode().toString() ) ){
			request.setAttribute( "isPolicyFeesEditable", AppConstants.TRUE );
		}
		else{
			request.setAttribute( "isPolicyFeesEditable", AppConstants.FALSE );
		}
		request.setAttribute( "policyFees", Currency.getFormattedCurrency(workmenCompVO.getPremiumVO().getPolicyFees(), commonVO.getLob().toString() ) );
		request.setAttribute( "govtTax", Currency.getFormattedCurrency(workmenCompVO.getPremiumVO().getGovtTax(), commonVO.getLob().toString() ) );
		request.setAttribute( "distributionChannel", workmenCompVO.getGeneralInfo().getSourceOfBus().getDistributionChannel() );
		request.setAttribute( "premiumAfterCoverDiscount", Currency.getFormattedCurrency( totalPremium, commonVO.getLob().toString() ) );
		request.setAttribute( "totalPremium", Currency.getFormattedCurrency(  totalPremium, commonVO.getLob().toString() ));
		request.setAttribute( "premiumDiscountAmount", Currency.getFormattedCurrency((workmenCompVO.getPremiumVO().getDiscOrLoadPerc()*totalPremium)/100, commonVO.getLob().toString()) );
		request.setAttribute( "premiumAfterDiscount",Currency.getFormattedCurrency(premiumAfterDiscount, commonVO.getLob().toString() ) );
		request.setAttribute( "commissionAmount", Currency.getFormattedCurrency(commissionAmount, commonVO.getLob().toString() ));
		request.setAttribute( "promoDiscountAmt", Currency.getFormattedCurrency(promoDiscountAmt, commonVO.getLob().toString() ));
		request.setAttribute( "schemeCode", workmenCompVO.getScheme().getSchemeCode() );
		
		// 142244 WC
		if(!Utils.isEmpty(workmenCompVO.getPremiumVO().getVatTaxPerc()))
		{
			request.setAttribute( "vatTaxPerc", workmenCompVO.getPremiumVO().getVatTaxPerc() );
		}
/*		if(workmenCompVO.getCommonVO().getAppFlow().equals(Flow.AMEND_POL) || !Utils.isEmpty( workmenCompVO.getEndorsmentVO())) {
					
			request.setAttribute( "vatTax", Currency.getFormattedCurrency((vatData.get(2).toString()), commonVO.getLob().toString()));
			}else*/
		request.setAttribute( "vatTax", Currency.getFormattedCurrency((vatAmount ), commonVO.getLob().toString()));
		
		double provatTaX = Math.abs(Double.parseDouble(vatData.get(2).toString()));
		
		request.setAttribute( "ProvatTaX", Currency.getFormattedCurrency(provatTaX, commonVO.getLob().toString()));
		
		
	}

	
	/**
	 * @param policyDataVO
	 * @param premiumVO 
	 * @return
	 * calculation of totalPremium for 'Total Premium' field in premium detail 
	 */
	private double getTotalPremiumForCancellation( PolicyDataVO policyDataVO, PremiumVO premiumVO ){
		
		double newAnnualizedPremium = 0;
		double totalPremium = 0;
		double totalDiscountPercent = 0;
		
		/*if(policyDataVO.getEndorsmentVO().size()>0){
			newAnnualizedPremium = policyDataVO.getEndorsmentVO().get( 0 ).getPremiumVO().getPremiumAmt();
			
			if(!Utils.isEmpty( premiumVO.getPromoDiscPerc() )){
				totalDiscountPercent +=  premiumVO.getPromoDiscPerc();
			}
			
			if(!Utils.isEmpty( premiumVO.getDiscOrLoadPerc() )){
				totalDiscountPercent -=  premiumVO.getDiscOrLoadPerc();
			}
			
			totalPremium = (newAnnualizedPremium * 100) / (100 - totalDiscountPercent);
		}
		*/
		totalPremium = PremiumHelper.totalCancelPrm( policyDataVO, premiumVO );
		return totalPremium;
	}
	
	/**
	 * @param policyDataVO
	 * @param premiumVO 
	 * @return minPremium
	 * calculation of prorated Min Prem  
	 */
	private double getMinPremiumForCancellation( PolicyDataVO policyDataVO, PremiumVO premiumVO ){
		double minPrmCancel = PremiumHelper.cancelMinPrm( policyDataVO, premiumVO );
		return minPrmCancel;
	}
	@Override
	public void referralAprrove(HttpServletRequest request,
			HttpServletResponse response, BaseVO baseVO) {

		
		//DataHolderVO<Object[]> dataHolder = new DataHolderVO<Object[]>();
		//Object[] inpObjects = { (PolicyDataVO)baseVO, false };
		//dataHolder.setData( inpObjects );
		
		TaskExecutor.executeTasks( "MONOLINE_APPROVE_QUO", baseVO );
		
	}
	
	private void setDefaultRequestValues(BaseVO baseVO, HttpServletRequest request)
	{
		
		request.setAttribute( "commissionPercentage", ((PolicyDataVO)baseVO).getCommission() );
		
		//142244 WC
		
		if ( !Utils.isEmpty(((PolicyDataVO)baseVO).getVatCode())){
			request.setAttribute( "vatCodeonGI", ((PolicyDataVO)baseVO).getVatCode());
			request.setAttribute( "quote_type_code", ((PolicyDataVO)baseVO).getVatCode());
		}

	}
	
	private void buildEmployeeDetailsToSave(WorkmenCompVO workmenCompVO){
		
		List<EmpTypeDetailsVO> empTypeDetails = new com.mindtree.ruc.cmn.utils.List<EmpTypeDetailsVO>( EmpTypeDetailsVO.class );
		for(EmpTypeDetailsVO emp: workmenCompVO.getEmpTypeDetails()){
			if ( !Utils.isEmpty( emp.getEmpType() ) && !Utils.isEmpty( emp.getWageroll() ) && 
					/*!Utils.isEmpty( emp.getNoOfEmp() ) && emp.getNoOfEmp() != 0*/ 
					!Utils.isEmpty( emp.getDeductibles() ) && !Utils.isEmpty( emp.getLimit() ) ){
					
				empTypeDetails.add( emp );
			}
		}
		workmenCompVO.setEmpTypeDetails( (com.mindtree.ruc.cmn.utils.List<EmpTypeDetailsVO>) empTypeDetails );
	}

}
