package com.rsaame.pas.policy.ui;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Redirection;
import com.mindtree.ruc.mvc.Redirection.Type;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.rules.mapper.RulesConstants;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.util.SectionRHUtils;
import com.rsaame.pas.vo.bus.EndorsmentVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.ReferralListVO;
import com.rsaame.pas.vo.bus.ReferralVO;
import com.rsaame.pas.vo.bus.RenewalVO;

public class RenewalPolFlowControl implements IRequestHandler{
	private static final String CHECK_RENEWAL_MESSAGES = "CHECK_RENEWAL_MESSAGES";
	private static final String CHECK_RENEWAL_REFERRALS = "CHECK_RENEWAL_REFERRALS";
	private static final String RENEWAL_RULES_EXECUTION = "RENEWAL_RULES_EXECUTION";
	private static final String ZERO_VAL = "0";

	@Override
	public Response execute( HttpServletRequest request, HttpServletResponse resp ){

		Response response = new Response();
		String action = request.getParameter( "action" );
		Redirection redirection ;
		// By default(When no renewal specific messages to be displayed) next destination is Capture comments 
		redirection = new Redirection( "LOAD_COMMENTS&action=STORE_POL_COMMENTS", Redirection.Type.TO_NEW_OPERATION );
		PolicyContext policyContext = PolicyContextUtil.getPolicyContext( request );
		PolicyVO pol = policyContext.getPolicyDetails();
		DataHolderVO<Map<String,BigDecimal>> disLoadOutput  ;
		//Boolean disLoadPresent = false;
		if( action.equals( CHECK_RENEWAL_MESSAGES ) ){
			RenewalVO renVo = pol.getRenewals();
			if( Utils.isEmpty( renVo ) ){
				renVo = new RenewalVO();
			}
				DataHolderVO<Object[]> claimInput = new DataHolderVO<Object[]>();
				Object claimInputData[] = new Object[ 2 ];
				claimInputData[ 0 ] = null;//Pol_linling_id to be found using renewal quote for renewal quotations
				claimInputData[ 1 ] = pol.getQuoteNo();
				claimInput.setData( claimInputData );
				DataHolderVO<Long> output = (DataHolderVO<Long>) TaskExecutor.executeTasks( "GET_CLAIM_COUNT", claimInput );
				renVo.setClaimCount( output.getData() );

				DataHolderVO<Long> input = new DataHolderVO<Long>();
				input.setData( pol.getQuoteNo() );
				DataHolderVO<BigDecimal> prmOutput = (DataHolderVO<BigDecimal>) TaskExecutor.executeTasks( "GET_OS_PREMIUM", input );
				if( Utils.isEmpty( prmOutput.getData() ) ){
					renVo.setOsPremium( Double.valueOf( ZERO_VAL ) );
				}
				else{
					renVo.setOsPremium( Double.valueOf( prmOutput.getData().toString() ) );
				}
				
				/*
				 * 4. Check if Discount loading percentage >= 30%
				 */
				DataHolderVO<Long> disLoadingInput = new DataHolderVO<Long>();
				disLoadingInput.setData( pol.getQuoteNo() );
				disLoadOutput = (DataHolderVO<Map<String,BigDecimal>>) TaskExecutor.executeTasks( "GET_DIS_LOAD_PER_QUO", disLoadingInput );
				/*
				 * Check discount/Loading percentage >= 30%
				 */
				if(!Utils.isEmpty( disLoadOutput.getData() )){
					Map<String,BigDecimal> percentages = disLoadOutput.getData() ;
					BigDecimal discPercentage = BigDecimal.ZERO;
					BigDecimal loadPercentage = BigDecimal.ZERO;
					if(!Utils.isEmpty(percentages.get(AppConstants.DISCOUNT_PER)) ){ 
						discPercentage = percentages.get(AppConstants.DISCOUNT_PER);
					}
					if(!Utils.isEmpty(percentages.get(AppConstants.LOADING_PER)) ){ 
						loadPercentage = percentages.get(AppConstants.LOADING_PER);
					}
					
					//SONARFIX -- changed the variable name from EQUALS to EQUALS_C
					if ( discPercentage.compareTo(AppConstants.DIS_LOAD_PERCENTAGE_LIMIT) == AppConstants.EQUALS_C ||
							discPercentage.compareTo(AppConstants.DIS_LOAD_PERCENTAGE_LIMIT) == AppConstants.FIRST_RECORD_GREATER){
								//disLoadPresent = true;
								renVo.setDiscountLoadingPer(discPercentage);
					}else {
						renVo.setDiscountLoadingPer(null);
					}
					
					//SONARFIX -- changed the variable name from EQUALS to EQUALS_C
					if ( loadPercentage.compareTo(AppConstants.DIS_LOAD_PERCENTAGE_LIMIT) == AppConstants.EQUALS_C ||
							loadPercentage.compareTo(AppConstants.DIS_LOAD_PERCENTAGE_LIMIT) == AppConstants.FIRST_RECORD_GREATER){
								//disLoadPresent = true;
								renVo.setDiscountLoadingPer(loadPercentage);
					}else {
						renVo.setDiscountLoadingPer(null);
					}
			     // Will track this change for any future issue		
					renVo.setDiscountLoadingPer(null);
				}
				
				/*
				 * 5. Check if broker account blocked
				 */
				DataHolderVO<Long> brBlockedInput = new DataHolderVO<Long>();
				brBlockedInput.setData(  pol.getQuoteNo() );
				DataHolderVO<Byte> brBlockedOutput = (DataHolderVO<Byte>) TaskExecutor.executeTasks( "GET_BR_ACC_BLOCKED", brBlockedInput );
							
				if(!Utils.isEmpty(brBlockedOutput)){
					renVo.setBrokerStatus( brBlockedOutput.getData());
				}
				
				DataHolderVO<List<EndorsmentVO>> endorsementData = (DataHolderVO) TaskExecutor.executeTasks( "GET_ENDORSMENT_AFTER_REN", input );
				renVo.setEndorsmentList( endorsementData.getData() );
				pol.setRenewals( renVo );
			
			
			
			
			if( pol.getRenewals().getClaimCount() != 0 || pol.getRenewals().getOsPremium() != 0 || !Utils.isEmpty( pol.getRenewals().getEndorsmentList() ) ||
					!Utils.isEmpty( pol.getRenewals().getDiscountLoadingPer() ) || 
					(!Utils.isEmpty(pol.getRenewals().getBrokerStatus()) && pol.getRenewals().getBrokerStatus() == AppConstants.BLOCKED_STATUS )){
				DataHolderVO<Object[]> refInput = new DataHolderVO<Object[]>();
				Object refInputData[] = new Object[ 3 ];
				refInputData[ 0 ] = pol.getPolLinkingId();
				refInputData[ 1 ] = Short.parseShort( Utils.getSingleValueAppConfig( RulesConstants.REN ) );
				refInputData[ 2 ] = Long.valueOf( Utils.getSingleValueAppConfig( RulesConstants.RISK_ID_RENEWAL ) );
				refInput.setData( refInputData );
				DataHolderVO<Object[]> needReferral = (DataHolderVO<Object[]>) TaskExecutor.executeTasks( "CHECK_REFERRAL_NEEDED", refInput );
				Object refDetails[] = needReferral.getData();
				boolean claimChkNeeded = (Boolean) refDetails[ 0 ];
				boolean osPrmChkNeeded = (Boolean) refDetails[ 1 ];
				boolean endCheckNeeded = (Boolean) refDetails[ 2 ];
				boolean disLoadPerChkNeeded = (Boolean) refDetails[ 3 ];
				boolean brStatusNeeded = (Boolean) refDetails[ 4 ];
				if( claimChkNeeded || osPrmChkNeeded || endCheckNeeded || disLoadPerChkNeeded || brStatusNeeded){
					if( !claimChkNeeded ){
						renVo.setClaimCount( Long.valueOf( ZERO_VAL ) );
					}
					if( !osPrmChkNeeded ){
						renVo.setOsPremium( Double.valueOf( ZERO_VAL ) );
					}
					if( !endCheckNeeded ){
						renVo.setEndorsmentList( null );
					}
					if ( !disLoadPerChkNeeded ) {
						renVo.setDiscountLoadingPer( null );
					}
					if( !brStatusNeeded ){
						renVo.setBrokerStatus(  null );
					}

					if( pol.getRenewals().getClaimCount() != 0 || pol.getRenewals().getOsPremium() != 0 || !Utils.isEmpty( pol.getRenewals().getEndorsmentList() )
							|| !Utils.isEmpty( pol.getRenewals().getDiscountLoadingPer() ) ||
							(!Utils.isEmpty(pol.getRenewals().getBrokerStatus()) && pol.getRenewals().getBrokerStatus() == AppConstants.BLOCKED_STATUS )){

						redirection = new Redirection( "/jsp/reports/showRenewalMessages.jsp", Type.TO_JSP );

						request.setAttribute( "renewalsVO", pol.getRenewals() );
						request.setAttribute( "renendorsments", pol.getRenewals().getEndorsmentList() );
					}

				}
			}

		}
		else if( action.equals( CHECK_RENEWAL_REFERRALS ) ){
			if( !SectionRHUtils.executeReferralTask( response, RENEWAL_RULES_EXECUTION, pol, RENEWAL_RULES_EXECUTION ) ){
				//if(referalExists){
				TaskExecutor.executeTasks( "STORE_RENEWAL_REFERRAL", pol );
				ReferralVO referalVO = new ReferralVO();
				referalVO.setPolLinkingId( pol.getPolLinkingId() );
				ReferralListVO referralListVO = (ReferralListVO) TaskExecutor.executeTasks( "PREMIUM_PAGE", referalVO );

				if( !Utils.isEmpty( referralListVO ) && !Utils.isEmpty( referralListVO.getReferrals() ) ){
				
					String consolidatedReferralMessage = "";
					/*
					 * Added string buffer to avoid "+" for sonar violation
					 * on 13-9-2017
					 */
					StringBuffer consolidatedReferralMessageBuffer=new StringBuffer();
					//Iterating all the referrals to get consolidated message 
					List<ReferralVO> referralVOs = referralListVO.getReferrals();
					for( ReferralVO voTemp : referralVOs ){
						if( !Utils.isEmpty( voTemp ) ){
							consolidatedReferralMessage = consolidatedReferralMessageBuffer.append(voTemp.getSectionName()).append(" : ").append(voTemp.getReferralText()).append("\n").toString();
							//consolidatedReferralMessage +=voTemp.getSectionName() + " : " + voTemp.getReferralText() + "\n";
						}
					}
					response.setResponseType( Response.Type.JSON );
					policyContext.getPolicyDetails().setConCatRefMsgs( consolidatedReferralMessage );
				}
			}
			return response;
		}

		response.setRedirection( redirection );
		return response;
	}

}
