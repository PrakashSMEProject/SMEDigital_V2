package com.rsaame.pas.web;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.kaizen.admin.dao.SystemConfigurationDAO;
import com.rsaame.kaizen.admin.model.Currency;
import com.rsaame.kaizen.framework.dao.exception.DataAccessException;
import com.rsaame.kaizen.framework.model.ApplicationContext;
import com.rsaame.kaizen.framework.model.Control;
import com.rsaame.kaizen.framework.model.ServiceContext;
import com.rsaame.kaizen.framework.util.AMEBeanUtils;
import com.rsaame.kaizen.framework.util.BeanFactory;
import com.rsaame.kaizen.framework.util.PropertiesUtil;
import com.rsaame.pas.com.svc.CommonOpSvc;

/**
 * 
 * @author m1014644
 * 
 * Loads the location context for the application during start-up.
 * This class is needed to reuse the kaizen functionality.
 *
 */
public class PASLocationControlInit  implements
ServletContextListener{
	
		private static final Logger logger = Logger.getLogger(PASLocationControlInit.class);
	
		private static final String LOCATION_CODE_DUBAI="locationcode.Dubai";
		
		private static final String LOCATION_CODE_ABUDHABI="locationcode.Abudhabi";
		
		private static final String LOCATION_CODE_SHARJA="locationcode.Sharjah";
		
		private static final String LOCATION_CODE_JEDDAH="locationcode.Jeddah";
		
		private static final String LOCATION_CODE_ALKOHVAH="locationcode.Alkohvah";
		
		private static final String LOCATION_CODE_RIYADH="locationcode.Riyadh";
		
		private static final String LOCATION_CODE_MUSCAT="locationcode.Muscat";
		
		private static final String LOCATION_CODE_BRANCH1="locationcode.Branch1";
		
		private static final String LOCATION_CODE_BRANCH2="locationcode.Branch2";
		
		private static final String LOCATION_CODE_BRANCH3="locationcode.Branch3";
		
		private static final String LOCATION_CODE_BRANCH4="locationcode.Branch4";
		
		private static final String LOCATION_CODE_BRANCH5="locationcode.Branch5";
		
		private static final String LOCATION_CODE_BRANCH6="locationcode.Branch6";
		
		private static final String LOCATION_CODE_BRANCH7="locationcode.Branch7";
		
		private static final String LOCATION_CODE_BRANCH8="locationcode.Branch8";
		
		private static final String LOCATION_CODE_BAHRAIN="locationcode.Bahrain";
		
		private static final String PATH_AME_CONFIG = "config/AMEConfig.properties";
		
	    private static final String CTX_INIT = "init()";
	
	    private static final String CTX_APPLICATION_CONTEXT = "populateApplicationContext()";
	
	    private static final String CTX_POPULATE_ROLE_FUN = "populateRoleFunScrSec()";
	
	    private static final String SYS_CONFIG_DAO = "SystemConfigurationDAO";
	
	    private static final String SYS_AUTHORIZATION_DAO = "authorizationDAO";

		private static final char DECIMAL_POINT = '.';

		private static final char COMMA = ',';
	
	    private static BeanFactory factory = BeanFactory.getInstance();
	    private void populateApplicationContext() {

	        logger.debug(CTX_APPLICATION_CONTEXT, "Method started");
	        String countryDesc = null;
	        String branchDesc = null;
	        String BAHRAIN_LOC = "50";
	        String TRAVEL_DEF = "888";
	        String TRAVEL = "31";
	        Properties properties = PropertiesUtil.loadProperties(PATH_AME_CONFIG);
	        Map controlMap = ApplicationContext.getControlMap();
	        SystemConfigurationDAO systemConfigurationDAO = null;
	        String dubai = properties.getProperty(LOCATION_CODE_DUBAI);
	        String abuDhabi = properties.getProperty(LOCATION_CODE_ABUDHABI);
	        String sharjah = properties.getProperty(LOCATION_CODE_SHARJA);
	        String jeddah = properties.getProperty(LOCATION_CODE_JEDDAH);
	        String alkohvah = properties.getProperty(LOCATION_CODE_ALKOHVAH);
	        String riyadh = properties.getProperty(LOCATION_CODE_RIYADH);
	        String muscat = properties.getProperty(LOCATION_CODE_MUSCAT);
	        String bahrain = properties.getProperty(LOCATION_CODE_BAHRAIN);
	        String branch1 = properties.getProperty(LOCATION_CODE_BRANCH1); 
	        String branch2 = properties.getProperty(LOCATION_CODE_BRANCH2); 
	        String branch3 = properties.getProperty(LOCATION_CODE_BRANCH3); 
	        String branch4 = properties.getProperty(LOCATION_CODE_BRANCH4); 
	        String branch5 = properties.getProperty(LOCATION_CODE_BRANCH5); 
	        String branch6 = properties.getProperty(LOCATION_CODE_BRANCH6); 
	        String branch7 = properties.getProperty(LOCATION_CODE_BRANCH7); 
	        String branch8 = properties.getProperty(LOCATION_CODE_BRANCH8); 
	        String[] loc = { dubai,abuDhabi,sharjah,jeddah,alkohvah,riyadh,muscat,bahrain,branch1,branch2,branch3,branch4,branch5,
	        		branch6,branch7,branch8,"30","2","10","5","6","4","23","9","12","21","24","27","28","31","1","3","29","37", "39"};
	        
	        java.util.Set<String> locSet = CopyUtils.asSet( loc );
	        loc = locSet.toArray(loc);
	       
            CommonOpSvc commonOpSvc = (CommonOpSvc) Utils.getBean( "geComSvc" );
            DataHolderVO<Map<Short, Integer>> currencyMapHolder = null;//(DataHolderVO<Map<Short, Integer>>) commonOpSvc.invokeMethod( "getPolicyTypeCurrencyScaleMap", null );
            Map<Short, Integer> policyTypeMap = null;//currencyMapHolder.getData();
            
            //get locations configured in prop file
            String locations = Utils.getSingleValueAppConfig("LOCATION_CODE",0);
    		List<String> locationsToAppend = CopyUtils.asList(locations.split(","));
    		String location = ServiceContext.getLocation();

	        for (int i = 0; i < loc.length; i++) {
	            //ServiceContext.setLocation(loc[i]);
	            com.rsaame.kaizen.admin.model.Control control = null;
	            com.rsaame.kaizen.framework.model.Control newcontrol = new Control();

	            try {
	            	
	            	//set location for db switch
	            	if(locationsToAppend.contains( loc[i] )){
	            		ServiceContext.setLocation( loc[i] );
	            	}
	            	systemConfigurationDAO = (SystemConfigurationDAO) factory.getBean(SYS_CONFIG_DAO);
	                control = systemConfigurationDAO.getCodes(branchDesc, countryDesc);
	                
	                //set location for db switch Ends
	                //PolicyTypeCurrencyScaleMap
	                currencyMapHolder = (DataHolderVO<Map<Short, Integer>>) commonOpSvc.invokeMethod( "getPolicyTypeCurrencyScaleMap", null );
	                policyTypeMap = currencyMapHolder.getData();
	                if(BAHRAIN_LOC.equals(loc[i])){ // required in premiumdetails.jsp for precision field
	                	policyTypeMap.put(Short.valueOf(TRAVEL_DEF), policyTypeMap.get(Short.valueOf(TRAVEL)));//copy the value for 31
	                }
	                //PolicyTypeCurrencyScaleMap ends
	                
	                Currency currency = systemConfigurationDAO.getExchangeRate(control.getCtlCurrency());
	                BigDecimal exchangeRate = currency.getExchangeRate();
	                String currencyDesc = currency.getEngDesc();
	                if (control != null) {
	                    AMEBeanUtils.copyProperties(newcontrol, control);
	                    newcontrol.setExchangeRate(exchangeRate);
	                    newcontrol.setCurrencyDesc(currencyDesc);
	                    logger.debug(CTX_APPLICATION_CONTEXT, "Exchange Rate:" + newcontrol.getExchangeRate()
	                            + "Branch code:" + newcontrol.getCtlBrCode() + "Country code:" + control.getCtlCtyCode()
	                            + "Currency Desc:" + newcontrol.getCurrencyDesc());
	                }
	                
	                // Oman Release: Initialize the curreny for pas application on load
	                if(control.getCtlBrCode().toString().equalsIgnoreCase( loc[i] )){
	                	com.rsaame.pas.cmn.currency.Currency.setCurrency( currency.getMinConvFactor(), "", "", DECIMAL_POINT, 
	                			COMMA, currency.getEngShortDesc(), currency.getMinEDesc(), currency.getEngDesc(), currency.getArabicDesc(), loc[i]);
	                	com.rsaame.pas.cmn.currency.Currency.setPolicyTypeScaleMap( policyTypeMap,loc[i]);
	                }
	                ServiceContext.setLocation( location );
	            } catch (IllegalAccessException e) {
	                e.printStackTrace();
	            } catch (InvocationTargetException e) {
	                e.printStackTrace();
	            } catch (NoSuchMethodException e) {
	                e.printStackTrace();
	            } catch (DataAccessException e1) {
	                e1.printStackTrace();
	            }
	            controlMap.put(loc[i], newcontrol);

	        }

	        ServiceContext.setLocation(location);
	        logger.debug(CTX_APPLICATION_CONTEXT, "Method End");
	    }
		


		/*@Override
		public void initialize(ServletConfig arg0) {
			populateApplicationContext();
			
		}*/



		@Override
		public void contextDestroyed(ServletContextEvent arg0) {
			// TODO Auto-generated method stub
			
		}



		@Override
		public void contextInitialized(ServletContextEvent arg0) {
			populateApplicationContext();
			
		}

}
