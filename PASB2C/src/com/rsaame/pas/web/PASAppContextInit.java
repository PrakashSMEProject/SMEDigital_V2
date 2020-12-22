package com.rsaame.pas.web;

import java.util.Enumeration;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.mindtree.ruc.mvc.init.WebInitExecutor;
import com.rsaame.pas.b2c.cmn.constants.AppConstants;
import com.rsaame.pas.com.svc.CommonOpSvc;

/**
 * 
 * @author m1014644
 *
 *Loads the spring application context for the PAS web application
 *
 */
public class PASAppContextInit implements
ServletContextListener  {

	private ServletConfig config ;
	private static final Logger logger = Logger.getLogger(PASAppContextInit.class);
	private static final char DECIMAL_POINT = '.';
	private static final char COMMA = ',';
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(final ServletContextEvent arg0) {
		
		if(logger.isInfo())
			logger.debug("Entering contextInitialized method");
		/* 
		 * This initializing is performed, coz the web init method is being used as a listener 
		 * Web init excepts the Servlet Config
		 * Servlet Config is out of scope in aListener  
		 */
		config = new ServletConfig(){
			 public String 	getInitParameter(String name){ return null; }
			 public Enumeration getInitParameterNames(){ return null; }
	         public ServletContext 	getServletContext(){ return arg0.getServletContext(); }
	         public String 	getServletName(){ return null; }
		};
		
		if(logger.isDebug())
			logger.debug("Intializating application context:");
		WebInitExecutor.execute(config);
		
		try {
			/*SystemConfigDAO systemConfigDAO  = (SystemConfigDAO)ApplicationContextUtils.getBean("systemConfigDAO");
			TMasCurrency tMasCurrency = systemConfigDAO.fetchCurrency();
				
			com.rsaame.pas.cmn.currency.Currency.setCurrency( tMasCurrency.getMinConvFactor(), "", "", DECIMAL_POINT, 
        			COMMA, tMasCurrency.getEngShortDesc(), tMasCurrency.getMinEDesc(), tMasCurrency.getEngDesc(), tMasCurrency.getArabicDesc() );*/
			if(Utils.getSingleValueAppConfig(com.Constant.CONST_DEPLOYED_LOCATION).equals(AppConstants.LOCATION_CODE)) {
			com.rsaame.pas.cmn.currency.Currency.setCurrency( 100, "", "", DECIMAL_POINT, 
        			COMMA, "RO","BAIZA", "OMANI RIAL", null,Utils.getSingleValueAppConfig(com.Constant.CONST_DEPLOYED_LOCATION) );
			}else {
				com.rsaame.pas.cmn.currency.Currency.setCurrency( 100, "", "", DECIMAL_POINT, 
	        			COMMA, "AED","FILS", "UAE DIRHAMS", null,Utils.getSingleValueAppConfig(com.Constant.CONST_DEPLOYED_LOCATION) );
			}
			
			CommonOpSvc commonOpSvc = (CommonOpSvc) Utils.getBean( "geComSvc" );
            DataHolderVO<Map<Short, Integer>> currencyMapHolder = (DataHolderVO<Map<Short, Integer>>) commonOpSvc.invokeMethod( "getPolicyTypeCurrencyScaleMap", null );
            Map<Short, Integer> policyTypeMap = currencyMapHolder.getData();
            com.rsaame.pas.cmn.currency.Currency.setPolicyTypeScaleMap( policyTypeMap,Utils.getSingleValueAppConfig(com.Constant.CONST_DEPLOYED_LOCATION) );
			
		} catch (Exception e) {
			logger.debug("Currency not set");
		}
		
		if(logger.isInfo())
			logger.debug("Exiting contextInitialized method");
	}

}
