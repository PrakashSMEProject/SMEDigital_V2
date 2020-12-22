package com.rsaame.pas.referral;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.kaizen.vo.PASServiceContext;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.TaskVO;

public class OnDemandReferralRH implements IRequestHandler{
	private static final String ON_DEMAND ="onDemand";
	@Override
	public Response execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		Response res = new Response();
		//System.out.println("In side On Demand Referral RH");
		
		PolicyContext policyDetails=PolicyContextUtil.getPolicyContext(request);
		PolicyVO policyVO=policyDetails.getPolicyDetails();
		TaskVO taskVO = new TaskVO();
		UserProfile userProfile = (UserProfile)request.getSession().getAttribute(AppConstants.SESSION_USER_PROFILE_VO);
		if(!Utils.isEmpty(userProfile)){
			taskVO.setLoggedInUser(userProfile);	
		}
		taskVO = new ReferralHandler().map(request, taskVO, policyVO,ON_DEMAND);
		// Oman multibranching implementation
		taskVO.setLocation(PASServiceContext.getLocation());
		
		
		TaskExecutor.executeTasks( "SAVE_ALL_REFERRALS_INSVC", taskVO );
		
		TaskExecutor.executeTasks( "SAVE_DISCOUNT_INSVC", policyVO );
		
		/* Send mail in case of referral.*/
		policyVO.setTaskDetails( taskVO );
		AppUtils.sendMail( policyVO, "REFERRAL" );
		AppUtils.addErrorMessage( request, "pas.saveSuccessful" );
		
		/* Changing status in policyContext. */
		policyVO.setStatus( Integer.valueOf( Utils.getSingleValueAppConfig( SvcConstants.QUOTE_REFERRED ) ) );
		policyDetails.getPolicyDetails().setAppFlow( Flow.VIEW_QUO );
		//System.out.println(taskVO.getDesc());
		return res;
	}

}
