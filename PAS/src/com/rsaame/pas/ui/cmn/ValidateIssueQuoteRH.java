package com.rsaame.pas.ui.cmn;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Redirection;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.util.SectionRHUtils;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.ReferralListVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
/**
 * This request handler is used to perform isuue Quote operation check
 *  This class checks if all the selected sections are added with details 
 *  before a quote can be issued
 */
public class ValidateIssueQuoteRH implements IRequestHandler {

	@Override
	public Response execute( HttpServletRequest request, HttpServletResponse response ){
		// TODO Auto-generated method stub
		
		Response responseObj = new Response();
		
		PolicyContext polContext = PolicyContextUtil.getPolicyContext( request );
		Redirection redirection = null;
		PolicyVO policyVO = polContext.getPolicyDetails();
		
		if(!Utils.isEmpty(Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION)) && 
                Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION).equalsIgnoreCase("30"))
		{ 
			if(policyVO.getIsQuote())
			{
				policyVO.setRuleContext(null);
				if( !SectionRHUtils.executeReferralTask( responseObj,"CONV_TO_POLICY", policyVO, "CONV_TO_POLICY" ) ){ 
					//message=Utils.getSingleValueAppConfig( "effectiveDateBackdating", "Your role does not allow to backdate by  " ) +SPACE+policyVO.getPremiumVO().getDiscOrLoadPerc()+"days";
					String message ="";
					ReferralListVO refVo = (ReferralListVO) responseObj.getData();
					message = refVo.getReferrals().get(0).getReferralText().get(0);
					AppUtils.addToRequestErrorMessagesMap( request,"pas.conToPolicy", message );
					return responseObj;
				}
			}
		}
		for(Integer section: polContext.getAllSelectedSections()){
			boolean todo = true;
			if(!Utils.isEmpty(polContext.getSectionDetails( section )) && !Utils.isEmpty( polContext.getSectionDetails( section ).getRiskGroupDetails())){
				for( Map.Entry<? extends RiskGroup, ? extends RiskGroupDetails> locationEntry : polContext.getSectionDetails( section ).getRiskGroupDetails().entrySet() ){
					/* Check if any of the selected section is empty(data not added) */
					if(!Utils.isEmpty(locationEntry.getValue())){
						todo = false;
					}
				}
			}
			// Commented this to fix loading issue in eplatform. Anveshan
			/*if(todo){
				request.getSession().setAttribute( "fromPremiumPage", "fromPremiumPage" ); 
				request.getSession().setAttribute( "premiumSectionId", section );
				redirection = new Redirection( "/jsp/quote/premium-page.jsp", Type.TO_JSP );
				redirection = new Redirection("SECTION&action=LOAD_DATA&premiumSectionId="+section, Redirection.Type.TO_NEW_OPERATION);
				responseObj.setSuccess( true );
				responseObj.setRedirection( redirection );
				return responseObj;
			}*/
		}
		  /*
         * Oman : validate function called only for Oman to validate created date and effective date
         */
        
		//Below code snippet is commented as already Rules configuration exists for same purpose
		
		/* if(!Utils.isEmpty(Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION)) && 
                           Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION).equalsIgnoreCase("30"))
        { 
              validate(policyVO);
        }*/
		
		redirection = new Redirection("POLICY_ACTION&action=ISSUE_QUOTE", Redirection.Type.TO_NEW_OPERATION);
		responseObj.setRedirection(redirection);
		return responseObj;
	}
	/*
	 * Oman : To validate effective date is later than created date
	 */
	private void validate(PolicyVO policyVO) {
		/*
		 * Oman : validate function called only for Oman to validate created date and effective date
		 */
	  TaskExecutor.executeTasks("PRM_PAGE_CREATED_DT_VAL", policyVO);
		
	}


}
