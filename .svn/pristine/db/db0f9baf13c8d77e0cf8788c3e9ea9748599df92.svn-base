/**
 * 
 */
package com.rsaame.pas.policy.ui;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Redirection;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.kaizen.vo.PASServiceContext;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.util.AppConstants;

/**
 * @author m1014644
 *
 */
public class GetPolLinkRH implements IRequestHandler{

	private static String GET_LINKING_ID = "GET_LINKING_ID";
	private static String GEN_INFO_RH = "GENERAL_INFO_PAGE";
	/* (non-Javadoc)
	 * @see com.mindtree.ruc.mvc.IRequestHandler#execute(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public Response execute( HttpServletRequest request, HttpServletResponse response ){
		UserProfile userProfile = (UserProfile) request.getSession().getAttribute( AppConstants.SESSION_USER_PROFILE_VO );
		Long polQuoNo = Long.valueOf( request.getParameter( "polQuoNo" ) );
		Long endID = Long.valueOf(request.getParameter( "endID" ));
		String polEffectiveDate = request.getParameter( "polEffectiveDate" );
		String polExpiryDate = request.getParameter( "polExpiryDate" );
		//Radar fix
		// Oman multibranching. Set the current location in Service Context after transaction search
		//String branch = request.getParameter("branch");
	    String tranType = request.getParameter("tranType");
	    
	    //Added to set Hard Stop for lapsed code -Oman 
	    String polStatus = request.getParameter( "polStatus" );
	    
	    Integer brCode=  SvcUtils.getLookUpCode("BRANCH_NO", userProfile.getRsaUser().getUserId().toString(), "ALL", request.getParameter("branch"));
	    Integer docCode=  SvcUtils.getLookUpCode("DOC_TYPE_ALL", "ALL", "ALL", tranType);
		// For normal quotes set the location of the selected quote/policy in Service context. For renewal quotes continue with the current location and user has to change the issuing branch 
		if(!Utils.isEmpty(brCode)){
			PASServiceContext.setLocation(brCode.toString());
			request.getSession().setAttribute(AppConstants.CTX_LOCATION,brCode.toString());
		}
		// Oman multibranching. Quote and policy can have same number.  Userthe document code also to get the linking id
		Object[] LinkIdSrcCre = {polQuoNo,endID,polEffectiveDate,polExpiryDate,docCode};
		DataHolderVO<Object[]> data = new DataHolderVO<Object[]>();
		data.setData( LinkIdSrcCre );
		
		BaseVO baseVO = TaskExecutor.executeTasks( GET_LINKING_ID, data );
		DataHolderVO<List<String>> dataList = (DataHolderVO<List<String>>) baseVO;
		List<String> dataResult=null;
		if(!Utils.isEmpty( dataList ))
			dataResult=dataList.getData();
		
		if(Utils.isEmpty(dataResult)&&Utils.isEmpty(dataResult.get( 0 )))
		{
			throw new BusinessException("pas.src.linkerror", null,"Unable to get PolicyLinkingID");
		}
		
		//Added to set Hard Stop for lapsed code -Oman
		request.setAttribute( "polStatus", polStatus );
		
		if(!Utils.isEmpty( dataResult )){
			request.setAttribute("polLinkingId", dataResult.get( 0 ));
		}	
		if(!Utils.isEmpty( dataResult ) && !Utils.isEmpty( dataResult .get( 1 ))){
			request.setAttribute("PolQuoteFlow", dataResult.get( 1 ));
		}
		request.setAttribute("endtId", endID.toString());
		Response responseObj = new Response();
		Redirection redirection = new Redirection(GEN_INFO_RH, Redirection.Type.TO_NEW_OPERATION);
		responseObj.setRedirection(redirection);
		return responseObj;
		
	}

}
