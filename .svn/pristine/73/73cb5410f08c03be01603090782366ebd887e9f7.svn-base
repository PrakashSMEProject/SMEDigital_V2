package com.rsaame.pas.quote.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Redirection;
import com.mindtree.ruc.mvc.Redirection.Type;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.cmn.CommonVO;

/**
 * @author M1006438
 * 
 * This class is used for activating the soft stop quote for home/travel
 *
 */

public class ActivateQuoteRH implements IRequestHandler{

	private static final String ACTIVATE_QUOTE = "ACTIVATE_QUOTE";
	private static final String LOAD_COMMON_GENERAL_INFO_PAGE = "LOAD_COMMON_GENERAL_INFO_PAGE";
	@Override
	public Response execute( HttpServletRequest request, HttpServletResponse response ){
		// TODO Auto-generated method stub
		String opType = (String)request.getParameter( "opType" );
		Response res = new Response();
		PolicyContext policyContext = PolicyContextUtil.getPolicyContext( request );
		CommonVO commonVO = policyContext.getCommonDetails();
		PolicyDataVO polVo = new  PolicyDataVO();
		polVo.setPolicyId( commonVO.getPolicyId() );
		polVo.setEndtId(commonVO.getEndtId());
		polVo.setCommonVO( policyContext.getCommonDetails() );
		// Call the Issue Quote SP to change the Soft Stop status to 'Active' status
		TaskExecutor.executeTasks( opType, polVo );		
		if(!Utils.isEmpty(commonVO.getLob() )){
			request.setAttribute("currentLob",commonVO.getLob().toString());
		}
		request.setAttribute( "appFlow", Flow.EDIT_QUO.toString() );
		policyContext.getCommonDetails().setAppFlow( Flow.EDIT_QUO );
		res.setRedirection(  new Redirection( LOAD_COMMON_GENERAL_INFO_PAGE, Redirection.Type.TO_NEW_OPERATION ) );
		return res;
	}

}
