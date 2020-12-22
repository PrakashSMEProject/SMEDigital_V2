/**
 * 
 */
package com.rsaame.pas.auth.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Redirection;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.vo.app.ForgotPwdDetailsVO;

/**
 * @author M1016284
 *
 */
public class ChangePasswordRH implements IRequestHandler{

	@Override
	public Response execute( HttpServletRequest request, HttpServletResponse response ){

		String action = request.getParameter( "action" );
		Response responseObj = new Response();

		Redirection redirection = null;

		if( action.equalsIgnoreCase( "SHOW_CHNG_PSWRD_POPUP" ) ){

			redirection = new Redirection( "/jsp/changePassword.jsp", Redirection.Type.TO_JSP );
			responseObj.setRedirection( redirection );

		}
		else if( action.equalsIgnoreCase( "UPDATE_PASSWORD" ) ){
			
			String newPassword = request.getParameter( "newPswrd" );

			UserProfile profile = (UserProfile) request.getSession( false ).getAttribute( AppConstants.SESSION_USER_PROFILE_VO );
			
			/*Set userId and new password to VO and update it using taskExecutor.*/
			ForgotPwdDetailsVO pwdDetailsVO = new ForgotPwdDetailsVO();
			pwdDetailsVO.setRandomPassword( newPassword );
			pwdDetailsVO.setUserId( profile.getRsaUser().getUserId() );
			
			TaskExecutor.executeTasks( action, pwdDetailsVO );

			responseObj.setData( "Password updated successfully" );

		}

		return responseObj;
	}

}
