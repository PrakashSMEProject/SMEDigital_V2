/**
 * 
 */
package com.rsaame.pas.tradeLicense.ui;

import java.math.BigDecimal;
import java.util.List;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Redirection;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.query.constants.QueryConstants;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.util.SectionRHUtils;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.ReferralListVO;
import com.rsaame.pas.vo.bus.ReferralVO;

/**
 * @author M1019703
 *
 */
public class CheckForEffectiveDateRH implements IRequestHandler {

	/**
	 * 
	 */
	public CheckForEffectiveDateRH() {
		// TODO Auto-generated constructor stub
	}
	private final static Logger logger = Logger.getLogger( CheckForEffectiveDateRH.class );
	private static final String SPACE = " ";
	/* (non-Javadoc)
	 * @see com.mindtree.ruc.mvc.IRequestHandler#execute(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public Response execute(HttpServletRequest request,
			HttpServletResponse responseObj) {
		// TODO Auto-generated method stub
		Response response = new Response();
		Redirection redirection = null;
		String tradeLicNo = null;
		PolicyContext polContext = PolicyContextUtil.getPolicyContext( request );
		PolicyVO policyVO = polContext.getPolicyDetails();
		logger.debug("*****Checking The effective date Evaluation *****");
		/*
		if( !SectionRHUtils.executeReferralTask( response,"CHECK_EFFECTIVE_DATE", policyVO, "SAVE" ) ){ 
			return response;
		}*/
		
	
		Integer brkCode =policyVO.getGeneralInfo().getSourceOfBus().getBrokerName();
        if( !Utils.isEmpty( brkCode ) ){

              java.util.List<Object> valueHolder = DAOUtils.getSqlResultSingleColumnPas( QueryConstants.GET_BROKER_ACC_STATUS, brkCode );
              BigDecimal bkrStatus = null;
              if( !Utils.isEmpty( valueHolder ) && valueHolder.size() > 0 && !Utils.isEmpty( valueHolder.get( 0 ) ) ){
                     bkrStatus = ( (BigDecimal) valueHolder.get( 0 ) );
              }
              if( !Utils.isEmpty( bkrStatus ) && bkrStatus.compareTo( BigDecimal.ZERO ) == 0 ){
            	  responseObj.setHeader("isBrokerBlocked","true");
                     throw new BusinessException( "cmn.brkblocked.cl", null, "The Brk account is blocked" );
              }
        }
		
		String message ="";
		
		if(policyVO.getIsQuote())
		{
			policyVO.setRuleContext(null);
			if( !SectionRHUtils.executeReferralTask( response,"CONV_TO_POLICY", policyVO, "CONV_TO_POLICY" ) ){ 
				//message=Utils.getSingleValueAppConfig( "effectiveDateBackdating", "Your role does not allow to backdate by  " ) +SPACE+policyVO.getPremiumVO().getDiscOrLoadPerc()+"days";
				ReferralListVO refVo = (ReferralListVO) response.getData();
				message = refVo.getReferrals().get(0).getReferralText().get(0);
				boolean allowToConvert = false;
				
				allowToConvert = AppUtils.checkForApprovedReferral( policyVO, refVo, allowToConvert , true );
				
				if( !allowToConvert ){
					AppUtils.addToRequestErrorMessagesMap( request,"pas.conToPolicy", message );
					/*responseObj.setHeader( "isJson", "true" );
					response.setResponseType( Response.Type.JSON );*/
					return response;
				}else{
					response = new Response();
				}
				
			}
			
			/*Starting of ticket 158315*/
			/* The below mentioned block of code will trigger the discount if more than 5% for broker user    */
			if (!SectionRHUtils.executeReferralTask(response,
                    "DISCOUNT_LOADING", policyVO, "DISCOUNT_LOADING")) {
                if (AppUtils.isDiscountRuleRequired(policyVO)) {
                    message = Utils.getSingleValueAppConfig(
                            "discountOrLoadPercentage",
                            "Your role does not allow a Discount/Loading of ")
                            + SPACE
                            + policyVO.getPremiumVO().getDiscOrLoadPerc() + "%";
                    AppUtils.addToRequestErrorMessagesMap(request,
                            "pas.discFail", message);
                    return response;
                }
            }
			/*End of ticket 158315*/
			
			
			if(Utils.getSingleValueAppConfig("ISCREDITCHKRULEREQ").equalsIgnoreCase("YES")&&  !SectionRHUtils.executeReferralTask( response,"CONV_TO_POLICY_REF", policyVO, "CONV_TO_POLICY_REF" ) ){
				ReferralListVO referralListVO = (ReferralListVO) response.getData();
				
				boolean allowToConvert = false;
				ReferralListVO refVo = (ReferralListVO) response.getData();
				message = refVo.getReferrals().get(0).getReferralText().get(0);
				allowToConvert = AppUtils.checkForApprovedReferral( policyVO, referralListVO, allowToConvert , false );
				
				if( !allowToConvert ){
					if( !Utils.isEmpty( referralListVO ) && !Utils.isEmpty( referralListVO.getReferrals() ) ){
						/*
						 * Added string buffer to avoid "+" for sonar violation
						 * on 13-9-2017
						 */
						String consolidatedReferralMessage = "";
						StringBuffer stringBuffer = new StringBuffer();
						//Iterating all the referrals to get consolidated message 
						List<ReferralVO> referralVOs = referralListVO.getReferrals();
						for( ReferralVO voTemp : referralVOs ){
							if( !Utils.isEmpty( voTemp ) ){
							//	consolidatedReferralMessage += voTemp.getReferralText() + "\n";
								consolidatedReferralMessage=stringBuffer.append(voTemp
										.getReferralText()).append("\n").toString();
							}
						}

						polContext.getPolicyDetails().setConCatRefMsgs( consolidatedReferralMessage);
						boolean isMessage = referralListVO.getReferrals().get( 0 ).isMessage();
					

						if( isMessage ){
							responseObj.setHeader( "isMessage", "true" );
							responseObj.setHeader( "referral", "true" );
						}else{
							AppUtils.addToRequestErrorMessagesMap( request,"pas.conToPolicy", message );
						}

						
						
					}

					return response;
				}
				else{
					response = new Response();
				}
			}
		}
		
		tradeLicNo =  request.getParameter( "tradeLicNo" ) ;
		if( !Utils.isEmpty(tradeLicNo))
		{
			redirection = new Redirection("CHECK_TRADE_LICENSE&tradeLicNo="+tradeLicNo, Redirection.Type.TO_NEW_OPERATION);
		}
		else
		{
			redirection = new Redirection("CHECK_TRADE_LICENSE", Redirection.Type.TO_NEW_OPERATION);
		}
		response.setRedirection(redirection);
		
		return response;
	}
		
}
