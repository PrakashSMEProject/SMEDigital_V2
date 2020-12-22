/**
 * 
 */
package com.rsaame.pas.policy.ui;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Redirection;
import com.mindtree.ruc.mvc.Response;
import com.mindtree.ruc.mvc.Redirection.Type;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.kaizen.vo.PASServiceContext;
import com.rsaame.pas.query.constants.QueryConstants;
import com.rsaame.pas.rules.mapper.RulesConstants;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.bus.EndorsmentVO;
import com.rsaame.pas.vo.bus.HomeInsuranceVO;
import com.rsaame.pas.vo.bus.LOB;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.bus.ReferralListVO;
import com.rsaame.pas.vo.bus.RenewalVO;
import com.rsaame.pas.vo.bus.SchemeVO;
import com.rsaame.pas.vo.bus.TaskVO;
import com.rsaame.pas.vo.bus.TravelInsuranceVO;
import com.rsaame.pas.vo.bus.TravelPackageVO;
import com.rsaame.pas.vo.cmn.CommonVO;
import com.rsaame.pas.util.SectionRHUtils;
import com.rsaame.pas.vo.bus.ReferralVO;

/**
 * @author m1019193
 *RH class to check if claim or OutStanding Premium exists and also check for REFERRAL in Renewal Flow Home/Travel-Phase 3
 */
public class RenewalPolFlowControlCommonRH implements IRequestHandler {

	private static final Logger LOGGER = Logger.getLogger(RenewalPolFlowControlCommonRH.class);
	private static final String CHECK_RENEWAL_MESSAGES = "CHECK_RENEWAL_MESSAGES";
	private static final String CHECK_RENEWAL_REFERRALS = "CHECK_RENEWAL_REFERRALS";
	private static final String SAVE_RENEWAL_REFERRAL = "SAVE_RENEWAL_REFERRAL";
	private static final String HOME = "HOME";

	@SuppressWarnings("unchecked")
	@Override
	public Response execute(HttpServletRequest request, HttpServletResponse response) {
		
		Response responseObj = new Response();
		String action = request.getParameter("action");
		PolicyContext policyContext = PolicyContextUtil.getPolicyContext(request);
		CommonVO commonVO = policyContext.getCommonDetails();
		if(action.equals(SAVE_RENEWAL_REFERRAL)){
			
			TravelInsuranceVO travelInsuranceDetailsVo = new TravelInsuranceVO();
			List<TravelPackageVO> packages = new ArrayList<TravelPackageVO>();
			TravelPackageVO travelPackageVO = BeanMapper.map( request, TravelPackageVO.class );

			packages.add( travelPackageVO );
			travelInsuranceDetailsVo.setTravelPackageList( packages );
			
			//TaskExecutor.executeTasks( "CHECK_BASIC_COVERS", travelInsuranceDetailsVo );
			PremiumVO premiumVO = new PremiumVO();
			travelInsuranceDetailsVo.setPremiumVO( premiumVO );
			AppUtils.mapPermiumSummary( travelInsuranceDetailsVo, request );

			SchemeVO schemeVO = new SchemeVO();
			schemeVO.setSchemeCode( Integer.parseInt( request.getParameter( "schemeCode" ) ) );
			schemeVO.setTariffCode( Integer.parseInt( request.getParameter( "tariffCode" ) ) );

			/* Save all details.*/
			travelInsuranceDetailsVo.setCommonVO( commonVO );
			travelInsuranceDetailsVo.setScheme( schemeVO );

			String operation = request.getParameter( "operation" );
			
			/*Mapping the required values from request for rules call.*/
			/*if( !commonVO.getIsQuote() ){
				mapEndorsementVOFromRequest( request, travelInsuranceDetailsVo );
			}*/

			DataHolderVO<Object[]> dataHolder = new DataHolderVO<Object[]>();
			if( !Utils.isEmpty( operation ) && operation.equals( AppConstants.POPULATE ) ){
				Object[] inpObjects = { travelInsuranceDetailsVo, true };
				dataHolder.setData( inpObjects );
			}
			else{
				/* Save all details in General Info. Second parameter: True - only call rating service, False - rating service call + save operation */
				Object[] inpObjects = { travelInsuranceDetailsVo, false };
				dataHolder.setData( inpObjects );
			}			
			String assignTO = null;
			String referalLoc = null;
			String referalComments = null;

			if( !Utils.isEmpty( (Map<String, Map<String, String>>) request.getSession().getAttribute( com.Constant.CONST_REFERRALMAP ) ) || AppUtils.isRefMsgForGenInfo( policyContext ) ){
				if( !Utils.isEmpty( request.getParameter( "assignto" ) ) ){
					assignTO = request.getParameter( "assignto" );
				}
				if( !Utils.isEmpty( request.getParameter( "referalLoc" ) ) ){
					referalLoc = request.getParameter( "referalLoc" );
				}
				if( !Utils.isEmpty( request.getParameter( "referalComments" ) ) ){
					referalComments = request.getParameter( "referalComments" );
				}
				ReferralListVO referralListVO = new ReferralListVO();
				ReferralVO referralVO = new ReferralVO();
				List<ReferralVO> refVOList = new ArrayList<ReferralVO>();
				if( Utils.isEmpty( (Map<String, Map<String, String>>) request.getSession().getAttribute( com.Constant.CONST_REFERRALMAP ) ) ){
					//In case there is referral in general info and no referral in risk page 
					HashMap<String, Map<String, String>> emptyHolder = new HashMap<String, Map<String, String>>();
					emptyHolder.put( null, null );
					referralVO.setRefDataTextField( emptyHolder );
				}
				else{
					referralVO.setRefDataTextField( (Map<String, Map<String, String>>) request.getSession().getAttribute( com.Constant.CONST_REFERRALMAP ) );
				}

				request.getSession().removeAttribute( com.Constant.CONST_REFERRALMAP );
				referralVO.setLocationCode( PASServiceContext.getLocation() );
				refVOList.add( referralVO );
				referralListVO.setReferrals( refVOList );
				travelInsuranceDetailsVo.setPolicyId( travelInsuranceDetailsVo.getCommonVO().getPolicyId() );
				
				PolicyDataVO policyDataVO = (PolicyDataVO) TaskExecutor.executeTasks( "POLICY_DATAVO_FROM_COMMONVO", commonVO );

				
				travelInsuranceDetailsVo.setPolicyClassCode( policyDataVO.getPolicyClassCode() );
				travelInsuranceDetailsVo.setPolicyType( policyDataVO.getPolicyType() );
				TaskVO taskVO = AppUtils.populateTaskVO( assignTO, referalLoc, referalComments, travelInsuranceDetailsVo, request,commonVO );
				referralListVO.setTaskVO( taskVO );
				travelInsuranceDetailsVo.setReferralVOList( referralListVO );

				UserProfile userProfile = (UserProfile) request.getSession().getAttribute( AppConstants.SESSION_USER_PROFILE_VO );
				travelInsuranceDetailsVo.setLoggedInUser( userProfile );
			}			
			
			TravelInsuranceVO travelInsuranceVO = null;

			if(Flow.VIEW_QUO.equals(commonVO.getAppFlow())){
				travelInsuranceVO =  (TravelInsuranceVO) TaskExecutor.executeTasks( "TRAVEL_RENEWAL_REFERRAL_SAVE", dataHolder );
				//travelInsuranceVO =  (TravelInsuranceVO) TaskExecutor.executeTasks( "SAVE_QUOTE_TRAVEL", dataHolder );
			}
			return responseObj;
		}
		Redirection redirection = new Redirection();
		// By default(When no renewal specific messages to be displayed) next destination is Capture comments 
		redirection = new Redirection("COMMON_CONVERT_TO_POLICY&action=STORE_POL_COMMENTS", Redirection.Type.TO_NEW_OPERATION);
		
		PolicyDataVO policyDataVO = null;
		HomeInsuranceVO homeInsuranceVO = new HomeInsuranceVO();		
		TravelInsuranceVO travelInsuranceVO = new TravelInsuranceVO();
		LOGGER.debug("**********LOB = " + commonVO.getLob());	
		if (commonVO.getLob().name().equalsIgnoreCase(HOME)) {		
			homeInsuranceVO.setCommonVO(commonVO);			
			policyDataVO = (PolicyDataVO) TaskExecutor.executeTasks(com.Constant.CONST_GEN_INFO_COMMON_LOAD, homeInsuranceVO);
		}
		else if(commonVO.getLob().equals(LOB.TRAVEL)) {			
			travelInsuranceVO.setCommonVO(commonVO);			
			policyDataVO = (PolicyDataVO) TaskExecutor.executeTasks(com.Constant.CONST_GEN_INFO_COMMON_LOAD, travelInsuranceVO);
		}
		//added to fix defect id #129210
		else if(commonVO.getLob().equals(LOB.WC)) {                
            travelInsuranceVO.setCommonVO(commonVO);                    
            policyDataVO = (PolicyDataVO) TaskExecutor.executeTasks(com.Constant.CONST_GEN_INFO_COMMON_LOAD, travelInsuranceVO);
            Integer brkCode = policyDataVO.getGeneralInfo().getSourceOfBus().getBrokerName();

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
     }


		else{
			policyDataVO = new PolicyDataVO();
			policyDataVO.setCommonVO(commonVO);			
			policyDataVO = (PolicyDataVO) TaskExecutor.executeTasks(com.Constant.CONST_GEN_INFO_COMMON_LOAD, policyDataVO);
		}
		
		if (action.equals(CHECK_RENEWAL_MESSAGES)) {
			RenewalVO renVo = policyDataVO.getRenewals();
			if (Utils.isEmpty(renVo)){
				renVo = new RenewalVO();				
				DataHolderVO<Object[]> claimInput = new DataHolderVO<Object[]>();
				Object [] claimInputData = new Object[3];
				claimInputData[0] = null; 
				claimInputData[1] = policyDataVO.getQuoteNo();
				claimInputData[2] = policyDataVO.getCommonVO().getLob();
				claimInput.setData(claimInputData);
				DataHolderVO<Long> claimOutput = (DataHolderVO<Long>) TaskExecutor.executeTasks("GET_CLAIM_COUNT_COMMON", claimInput);
				renVo.setClaimCount(claimOutput.getData());

				DataHolderVO<Long> input = new DataHolderVO<Long>();
				input.setData(policyDataVO.getQuoteNo());
				DataHolderVO<BigDecimal> prmOutput = (DataHolderVO<BigDecimal>) TaskExecutor.executeTasks("GET_OS_PREMIUM", input);
				if (Utils.isEmpty(prmOutput.getData())) {
					renVo.setOsPremium(Double.valueOf(AppConstants.zeroVal));
				}
				else {
					renVo.setOsPremium(Double.valueOf(prmOutput.getData().toString()));
				}
				DataHolderVO<List<EndorsmentVO>> endorsementData = (DataHolderVO<List<EndorsmentVO>>) TaskExecutor.executeTasks("GET_ENDORSMENT_AFTER_REN", input);
				renVo.setEndorsmentList(endorsementData.getData());
				policyDataVO.setRenewals(renVo);
				//request.getSession().setAttribute(com.Constant.CONST_RENEWALSVO,renVo);
				
			}
			if (policyDataVO.getRenewals().getClaimCount() != 0 || policyDataVO.getRenewals().getOsPremium() != 0 || !Utils.isEmpty(policyDataVO.getRenewals().getEndorsmentList())) {
				DataHolderVO<Object[]> refInput = new DataHolderVO<Object[]>();
				request.getSession().setAttribute(com.Constant.CONST_RENEWALSVO,renVo);
				Object refInputData[] = new Object[3];				
				refInputData[ 0 ] = policyDataVO.getPolicyId();
				refInputData[ 1 ] = Short.parseShort(Utils.getSingleValueAppConfig(RulesConstants.REN));			
				refInputData[ 2 ] = Long.valueOf(Utils.getSingleValueAppConfig(RulesConstants.RISK_ID_RENEWAL));	
				refInput.setData(refInputData);
				DataHolderVO<Object[]> needReferral = (DataHolderVO<Object[]>) TaskExecutor.executeTasks("CHECK_REFERRAL_NEEDED_COMMON", refInput);
				Object refDetails[] = needReferral.getData();
				boolean claimChkNeeded = (Boolean) refDetails[ 0 ];
				boolean osPrmChkNeeded = (Boolean) refDetails[ 1 ];
				boolean endCheckNeeded = (Boolean) refDetails[ 2 ];

				if (claimChkNeeded || osPrmChkNeeded || endCheckNeeded) {
					if (!claimChkNeeded) {
						renVo.setClaimCount(Long.valueOf(AppConstants.zeroVal));
					}
					if (!osPrmChkNeeded){
						renVo.setOsPremium(Double.valueOf(AppConstants.zeroVal));
					}
					if (!endCheckNeeded){
						renVo.setEndorsmentList(null);
					}

					if (policyDataVO.getRenewals().getClaimCount() != 0 || policyDataVO.getRenewals().getOsPremium() != 0 || !Utils.isEmpty(policyDataVO.getRenewals().getEndorsmentList())) {

						redirection = new Redirection("/jsp/reports/showRenewalMessages.jsp", Type.TO_JSP);

						request.setAttribute(com.Constant.CONST_RENEWALSVO, policyDataVO.getRenewals());
						request.setAttribute("renendorsments", policyDataVO.getRenewals().getEndorsmentList());
						
					}

				}
			}
		}
		
		else if(action.equals(CHECK_RENEWAL_REFERRALS)){
			RenewalVO renVO = (RenewalVO) request.getSession().getAttribute(com.Constant.CONST_RENEWALSVO);
			
			if( policyDataVO instanceof HomeInsuranceVO ){				
				homeInsuranceVO.setRenewals(renVO);				
				if( !SectionRHUtils.executeReferralTaskHome( homeInsuranceVO, "", "Home Risk Cover", request,true ) ){
					redirectReferral(homeInsuranceVO,request,response,responseObj,null);
				}
			}
			else if(policyDataVO instanceof TravelInsuranceVO){
				Integer[] sectionArray = AppUtils.getIntegerArray( Utils.getMultiValueAppConfig( AppConstants.TRAVEL_SEC_ID ) );				
				/*
				 * If referral is present redirect to show referral pop - up
				 */
				travelInsuranceVO.setRenewals(renVO);
				if( !SectionRHUtils.executeReferralTaskForTravel( travelInsuranceVO, "", "", sectionArray, request ) ){		
					redirectReferral( travelInsuranceVO, request, response, responseObj, null );
				
				}				
			}
			else{
				Integer[] sectionArray = AppUtils.getIntegerArray( Utils.getMultiValueAppConfig( policyDataVO.getCommonVO().getLob().toString()+"_SEC_ID" ) );				
				policyDataVO.setRenewals(renVO);
				if( !SectionRHUtils.executeReferralTaskForTravel( policyDataVO, "", "", sectionArray, request ) ){		
					SectionRHUtils.redirectReferralForMonoline( policyDataVO, request, response, responseObj, redirection );
					response.setHeader( "isRef", "true" );
				}				
			}
			
			request.getSession(false).removeAttribute( com.Constant.CONST_RENEWALSVO );
			return responseObj;
		}
		
		responseObj.setRedirection(redirection);
		return responseObj;
	}
	
	/**
	 * Method to redirect to referral
	 * 
	 * @param homeInsuranceVO
	 * @param request
	 * @param response
	 * @param responseObj
	 * @param redirection
	 */
	private void redirectReferral( PolicyDataVO policyDataVO, HttpServletRequest request, HttpServletResponse response, Response responseObj, Redirection redirection ){
		List<String> messageList = new ArrayList<String>();
		ReferralListVO refListVO = policyDataVO.getReferralVOList();
		if( !Utils.isEmpty( refListVO ) ){
			for( ReferralVO refVO : refListVO.getReferrals() ){
				for( String refText : refVO.getReferralText() ){
					String message = new String( refText );
					messageList.add( message );
				}
			}
		}
		
		if(   !Utils.isEmpty( policyDataVO.getReferralVOList() ) && !Utils.isEmpty( policyDataVO.getReferralVOList().getReferrals() ) && !Utils.isEmpty( policyDataVO.getReferralVOList().getReferrals().size() > 0 )
				&& !Utils.isEmpty( policyDataVO.getReferralVOList().getReferrals().get( 0 ).getRefDataTextField() ) ){
			HttpSession session = request.getSession();
			//Holding the referral messages map in session
			session.setAttribute( com.Constant.CONST_REFERRALMAP, policyDataVO.getReferralVOList().getReferrals().get( 0 ).getRefDataTextField() );
		}	
		request.setAttribute( "referralListVO", refListVO );
		responseObj.setData( refListVO );
		response.setHeader( "isRef", "true" );
		redirection = new Redirection( "/jsp/quote/consolidatedReferralCommon.jsp", Type.TO_JSP );
		responseObj.setRedirection( redirection );
		
	}	
}
