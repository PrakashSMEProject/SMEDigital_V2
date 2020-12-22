package com.rsaame.pas.referral;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.exception.SystemException;

import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.kaizen.vo.RSAUserWrapper;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.vo.bus.PolicyVO;

public class OnDemandReferralLoadRH implements IRequestHandler {

	private final static Logger LOGGER = Logger.getLogger( OnDemandReferralLoadRH.class );
	@Override
	public Response execute(HttpServletRequest request,
			HttpServletResponse response) {

		PolicyVO policyVO = null;
		PolicyContext policyContext=PolicyContextUtil.getPolicyContext(request);
		if(Utils.isEmpty(policyContext)){
			if(LOGGER.isError()) LOGGER.error("policyContext sis null");
			throw new SystemException("", null,"");
		}else{
			policyVO = policyContext.getPolicyDetails();
		}
		
		// Added by Anveshan
		if( !Utils.isEmpty( policyVO.getPremiumVO() ) ){
			BeanMapper.map( request, policyVO.getPremiumVO() );
		}
		request.setAttribute("demandReferralTaskName", policyVO.getQuoteNo()+" is referred");
		/* get the Default approver for the logged in user */
		RSAUserWrapper rSAUserWrapper = null;
		UserProfile userProfile = AppUtils.getUserDetailsFromSession(request);
		if(!Utils.isEmpty(userProfile)){
			rSAUserWrapper = (RSAUserWrapper)userProfile.getRsaUser();
			if(!Utils.isEmpty(rSAUserWrapper)){
				request.setAttribute("defaultApprover",rSAUserWrapper.getDefaultApprover());
			}
		}	
		Response res = new Response();
		return res;
	}

}
