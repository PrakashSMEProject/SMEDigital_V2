package com.rsaame.pas.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.kaizen.framework.model.ServiceContext;
import com.rsaame.pas.auth.dao.IAuthorizationDAO;
import com.rsaame.pas.kaizen.vo.KaizenSecurityContextWrapper;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.vo.bus.PolicyVO;

/**
 * 
 * @author m1014644
 *
 *Loads the authorization details for the for the application during the start-up
 *Role-Function-Screen-Section-privilege is loaded  
 */

public class PASSecurityContextInit implements
ServletContextListener {

	private static final Logger logger = Logger.getLogger(PASSecurityContextInit.class);

	private void populateSecurityContext() {
		
		DataHolderVO roleMap = null;
		
		
		String locations = Utils.getSingleValueAppConfig("LOCATION_CODE");
		List<String> locationsToAppend = CopyUtils.asList(locations.split(","));
		final Map<String, Map<String,String>> roleFuntionMap =  new HashMap<String, Map<String,String>>(); 
		
		for(String loc :locationsToAppend){
			
			ServiceContext.setLocation(loc);
			roleMap = (DataHolderVO) TaskExecutor.executeTasks( AppConstants.AUTH_DETAILS, roleMap );
			roleFuntionMap.putAll((Map<String, Map<String, String>>) roleMap.getData());
			
		}
		
		
		
		//final IAuthorizationDAO  authorizationDAO = (IAuthorizationDAO) Utils.getBean(AppConstants.AUTHORIZATION_DAO);
		//final Map<String, Map<String,String>> roleFuntionMap =	authorizationDAO.getAuthenticationDetails();
		
		KaizenSecurityContextWrapper.setRoleFunctionMap( roleFuntionMap );
		
	}


	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		if(logger.isInfo())
		logger.info("Starting to fetch the authorization details in contextInitialized method ");
		populateSecurityContext();
		if(logger.isInfo())
			logger.info("Exiting contextInitialized");
		
	}

}
