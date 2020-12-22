package com.rsaame.pas.home.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.LocationHandler;
import com.mindtree.ruc.cmn.utils.LoginLocation;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.cmn.context.ThreadLocationContext;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.vo.app.NoticeBoardVO;


public class HomePageRH implements IRequestHandler {

	private static final Logger logger = Logger.getLogger( HomePageRH.class );
	private static final String HOME_PAGE_RH = "homePageRH"; 
	private LoginLocation location;
	
	/**
	 * @return
	 */
	public LoginLocation getLocation() {
		return location;
	}

	/**
	 * @param location
	 */
	public void setLocation(LoginLocation location) {
		this.location = location;
	}

	@Override
	public Response execute(HttpServletRequest httpservletrequest,
			HttpServletResponse responseObj) {
		
		Response response = new Response();
		HttpSession session = httpservletrequest.getSession(false);
		UserProfile userProfile = new UserProfile();
		
		if(!Utils.isEmpty(location.getLocation())){
			System.out.println("*******In Home Page RH****Check for *location.getLocation()*********"+location.getLocation());
		}else{
			System.out.println("*******In Home Page RH****Check for *location.getLocation() is null *********");
		}
	
		
		//1.set login location of the user once
		if (location!=null && Utils.isEmpty(location.getLocation())) {
			
			String loginLocation = ThreadLocationContext.get();
			
			System.out.println("*******In Home Page RH*****loginLocation*********"+loginLocation);
			
			
			location.setLocation(loginLocation);
			
			LocationHandler locationHandler = (LocationHandler) Utils.getBean("locationHandler");
			locationHandler.setIsApplicationStarted(Boolean.TRUE);
			
		}
		//ends 1
		
		if(Utils.isEmpty(session.getAttribute(AppConstants.SESSION_USER_PROFILE_VO)))
		{
			session.setAttribute(AppConstants.SESSION_USER_PROFILE_VO, userProfile);
		}
		
		/*
		httpservletrequest.setAttribute(AppConstants.MODE, VisibilityLevel.EDITABLE);
		httpservletrequest.setAttribute(AppConstants.FUNTION_NAME, "AMEND_POL");
		httpservletrequest.setAttribute(AppConstants.SCREEN_NAME, "PRM_DETAILS");
		*/
		
		NoticeBoardVO noticeBoardVO = new NoticeBoardVO();
		noticeBoardVO.setLoggedInUser(userProfile);
		
		noticeBoardVO = (NoticeBoardVO) TaskExecutor.executeTasks( "NOTICE_BOARD", noticeBoardVO );
					
		session.setAttribute("noticeBoardVO", noticeBoardVO);
				
		logger.debug(HOME_PAGE_RH +"execute()","PolicyContext created");
		return response;
	}

}
