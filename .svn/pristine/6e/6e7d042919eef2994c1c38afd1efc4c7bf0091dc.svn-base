package com.rsaame.pas.home.ui;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mindtree.ruc.cmn.exception.SystemException;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.vo.app.RecentActivityVO;

public class RecentActivityRH implements IRequestHandler {
	@Override
	public Response execute(HttpServletRequest httpservletrequest,
			HttpServletResponse httpservletresponse) {
		
		Response response = new Response();
		
		/* Get the action from the request. If it has not been set, default to "SHOW_RECENT_QUOTES". */
		String actionAttr = httpservletrequest.getParameter( AppConstants.ACTION );
		actionAttr = Utils.isEmpty( actionAttr ) ? "SHOW_RECENT_QUOTES" : actionAttr;
		RecentActivityAction action = RecentActivityAction.valueOf( actionAttr );
		
		RecentActivityVO recentActivityVO=new RecentActivityVO();
		
		/*Getting the userID from session and setting it to inputVO  */
		Integer userId = null;
		if (!Utils.isEmpty(AppUtils.getUserDetailsFromSession(httpservletrequest))) {
			userId = AppUtils.getUserDetailsFromSession(httpservletrequest)
					.getRsaUser().getUserId();
		}
		if(Utils.isEmpty( userId )){
			throw new SystemException( "pas.cmn.userUnavailable", null, "User has not been initialized" );
		}
		recentActivityVO.setUserId( userId );
		recentActivityVO.setQuote( false );
		
		/*Set the quote flag as true in case if it is Show_Recent_Quotes flow*/
		if(actionAttr.equals( action.SHOW_RECENT_QUOTES.name() )  || actionAttr.equals( action.SHOW_RENEWAL_QUOTES.name() ))
		{
				recentActivityVO.setQuote( true );
		}
		
		/*This is to make a service task call to get the recent details according to action. Here , action has been used
		 * as an identifier to configure tasks . */
		DataHolderVO<List<RecentActivityVO>> dataHolderVO=
			(DataHolderVO<List<RecentActivityVO>>)TaskExecutor.executeTasks( action.name(), recentActivityVO );
		List<RecentActivityVO> recentActivityVOs=null;
		if( !Utils.isEmpty( dataHolderVO ) ) recentActivityVOs = dataHolderVO.getData();

		if( !Utils.isEmpty( recentActivityVOs ) ) response.setData( recentActivityVOs );
		
		
		
		
		return response;
	}

}