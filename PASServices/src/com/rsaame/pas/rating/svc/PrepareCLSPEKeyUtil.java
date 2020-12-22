/*
 * PrepareCLSPEKeyUtil.java
 *
 * Copyright (c) 2011-2012 Royal and Sun Alliance Insurance Group plc.
 * St.Marks Court, Chart Way, Horsham, West Sussex RH12 1XL, U.K.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of Royal
 * and Sun Alliance Insurance Group plc.("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Royal and Sun Alliance Insurance Group plc.
 */
package com.rsaame.pas.rating.svc;

import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import org.hibernate.Session;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.kaizen.framework.constants.AMEConstants;
import com.rsaame.kaizen.framework.constants.AMEPolicyConstants;
import com.rsaame.kaizen.framework.constants.PropertyFileConstants;
import com.rsaame.kaizen.framework.dao.exception.DataAccessException;
import com.rsaame.kaizen.framework.model.ApplicationContext;
import com.rsaame.kaizen.framework.model.ServiceContext;
import com.rsaame.kaizen.framework.util.AMEUtil;
import com.rsaame.kaizen.framework.util.BeanFactory;
import com.rsaame.kaizen.framework.util.PropertiesUtil;
import com.rsaame.kaizen.quote.dao.QuoteDAO;
import com.rsaame.kaizen.quote.model.FactorBO;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.tariff.svc.TariffService;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.RiskGroupDetails;
import com.rsaame.pas.vo.bus.SchemeVO;

/**
 * @author M1017952
 *
 */
//TODO: Spring integration
public class PrepareCLSPEKeyUtil implements
PropertyFileConstants{
	 /**
     * method to get CLSPE key for policyQuo
     * @param policyQuo
     * @return factorList
     * @throws DataAccessException
     */
    
	private final static Logger logger = Logger.getLogger(PrepareCLSPEKeyUtil.class);
    	/**
    	 * Properties file containing the parameters required for erater properties.
    	 */
    	private  Properties actions = PropertiesUtil
    			.loadProperties(PATH_MAPPING_CLSPE_MAPPING);

    	/**
    	 * Properties file containing Config Properties.
    	 */
    	private  Properties ameConfigActions = PropertiesUtil
    			.loadProperties(PATH_AME_CONFIG);

    	/**
    	 * method to get CLSPE key for policyQuo
    	 * 
    	 * @param policyQuo
    	 * @return factorList
    	 * @throws DataAccessException
    	 */
    	
    	/*Added prepared Date as argument to pass to Rating service - Ticket Id: 140443 */
    	public ArrayList<FactorBO> getCLSPEKey(RiskGroupDetails riskDetails,
	    			RiskGroup locationInfo, String sectionName, SchemeVO schemeVO,Date quotePreparedDt) {
    			long startTime = System.currentTimeMillis();
	    		
	    		ArrayList<FactorBO> factorList = new ArrayList<FactorBO>();
	    		factorList.add(getState());
	    		factorList.add(getCompany());
	    		Integer tarrLocCode = null;
	    		// Added to Fetch the location code for rating
	    		if (schemeVO.getTariffCode() != null) {
	    			logger.debug("Rating :getCLSPEKey: schemeVO.getTariffCode()"+schemeVO.getTariffCode());
	    			tarrLocCode = getLocCodeRating(schemeVO.getTariffCode());
	    		}
	    		if (tarrLocCode != null) {
	    			logger.debug("Rating :getCLSPEKey: schemeVO.getTariffCode() is null");
	    			FactorBO state = new FactorBO();
	    			state.setFactorName(AMEConstants.QUOTE_STATE);
	    			state.setFactorValue(tarrLocCode.toString());
	    			factorList.add(state);
	    			logger.debug("Rating :getCLSPEKey: QUOTE_STATE"+state.getFactorValue());
	
	    		} else {
	    			factorList.add(getState());
	    		}
	
	    		/**
	    		 * Plan is the concatenation of tariff code and location code. Added to
	    		 * Fetch the location code for rating
	    		 */
	
	    		LocationVO locVO = (LocationVO) locationInfo;
	    		// String (locVO.getLocCode());
	
	    		if (tarrLocCode != null) {
	    			FactorBO plan = new FactorBO();
	    			plan.setFactorName(AMEConstants.QUOTE_PLAN);
	    			String tarCode = String.valueOf(schemeVO.getTariffCode());
	    			String locationCode = String.valueOf(tarrLocCode);
	    			String planValue = appendZeros(locationCode) + appendZeros(tarCode);
	    			plan.setFactorValue(planValue);
	    			factorList.add(plan);
	    			logger.debug("Rating :getCLSPEKey: QUOTE_PLAN"+plan.getFactorValue());
	    		} else {
	    			if (schemeVO.getTariffCode() != null && locVO.getRiskGroupId()!= null) {
	    				//TODO : Logic to get the Location Code is wrong
	    				FactorBO plan = new FactorBO();
	    				plan.setFactorName(AMEConstants.QUOTE_PLAN);
	    				String tarCode = String.valueOf(schemeVO.getTariffCode());
	    				logger.debug("Rating :getCLSPEKey: schemeVO.getTariffCode()"+schemeVO.getTariffCode());
	    				String locationCode = String.valueOf(locVO.getRiskGroupId());
	    				logger.debug("Rating :getCLSPEKey: locVO.getRiskGroupId()"+locVO.getRiskGroupId());
	    				String planValue = appendZeros(locationCode)
	    						+ appendZeros(tarCode);
	    				plan.setFactorValue(planValue);
	    				factorList.add(plan);
	    				logger.debug("Rating :getCLSPEKey: QUOTE_PLAN"+plan.getFactorValue());
	    			}
	    		}
	
	    		/**
	    		 * Product is the concatenation of class code and policy type code.
	    		 * Check if class code and policy type code is not null and concatenate
	    		 * them to form a product code
	    		 */
	
	    		// TODO Class code need to include in VO and accordingly look for the signature of the method.
	    		// Policy Type Code take from SchemeVO
	    		
	    		if (!Utils.isEmpty(schemeVO.getPolicyType()) ) {
	
	    			FactorBO product = new FactorBO();
	    			product.setFactorName(AMEConstants.QUOTE_PRODUCT);
	    			
	    			// Convert class code and policy type code to String
	    			
	    			//String classCode = riskDetails.getClassCode().toString();
	    			//LOB of SBS =2
	    			String classCode=String.valueOf(2);
	    			logger.debug("Rating :getCLSPEKey: classCode"+classCode);
	    			String policyType = schemeVO.getPolicyType();
	    			logger.debug("Rating :getCLSPEKey: schemeVO.getPolicyType()"+policyType);
	    			// Add LOB	
	    			if(!Utils.isEmpty(classCode)){
	    			factorList.add(getLOB(classCode));
	    			}else {
	    				logger.error( "Error: LOB [classCode] is empty" );
	    				BusinessException businessExcp=new BusinessException( "rating.classCodeEmpty",null, "Error: LOB [classCode] is empty" );
	    				throw businessExcp;
	    			}
	    			// If length of class code is 1 then prefix 0 else take the same
	    			// code
	    			classCode = (classCode.length() == 1) ? AMEPolicyConstants.APPEND_ZERO
	    					+ classCode
	    					: classCode;
	    			// If length of policy type code is 1 then prefix 0 else take the
	    			// same code
	    			policyType = (policyType.length() == 1) ? AMEPolicyConstants.APPEND_ZERO
	    					+ policyType
	    					: policyType;
	
	    			// concatenate the class code and policy type code based on the
	    			// result retreived from above
	    			//Add Product
	    			product.setFactorValue(classCode + policyType);
	    			factorList.add(product);
	    			logger.debug("Rating :getCLSPEKey: QUOTE_PRODUCT"+product.getFactorValue());
	    		}
	
	    		// To set factor effective date
	    		/***********commented by Dileep to send quote prepared date of quote to Rating engine***************/
	    		/*if (schemeVO.getEffDate() != null) {
	    			FactorBO effectiveDate = new FactorBO();
	    			effectiveDate.setFactorName(AMEConstants.QUOTE_EFFECTIVE_DATE);
	    			effectiveDate.setFactorValue(AMEUtil.DateToString(schemeVO
	    					.getEffDate()));
	    			factorList.add(effectiveDate);
	    			logger.debug("Rating :getCLSPEKey: QUOTE_EFFECTIVE_DATE"+effectiveDate.getFactorValue());
	    		}*/
	    		
	    		/***********Added by Dileep to send quote prepared date of quote to Rating engine Ticket Id 140443 - Start***************/
	    		
	    		if (quotePreparedDt != null) {
	    			FactorBO effectiveDate = new FactorBO();
	    			effectiveDate.setFactorName(AMEConstants.QUOTE_EFFECTIVE_DATE);
	    			effectiveDate.setFactorValue(AMEUtil.DateToString(quotePreparedDt));
	    			factorList.add(effectiveDate);
	    			logger.debug("Rating :getCLSPEKey: QUOTE_EFFECTIVE_DATE"+effectiveDate.getFactorValue());
	    		}
	    		/***********Added by Dileep to send quote prepared date of quote to Rating engine - Ticket Id 140443 - End***************/
	    		factorList.add(getService());
	
	    		factorList.add(getSourceRegion());
	
	    		factorList.add(getDebugIndex());
	
	    		factorList.add(getPassword());
	
	    		factorList.add(getCallerId());
	
	    		factorList.add(getWriteRateTransactionType());
	
	    		// Important:
	    		// 1. Input is SBS Policy VO So map it accordingly
	    		// 2. Have a close look in to the original code what is the difference
	    		// b/w quote and policy and process it accordingly.
	    		// If required create two private methods and invoke them from here
	    		// after checking the Policyvo.isQuote variable
	    		long endTime = System.currentTimeMillis();
	    		logger.info(" Rating Engine integration Timer: CLSPEKey util creation time (In MilliSec):: " + (endTime - startTime));
	    		return factorList;
	
	    	}

    	/**
    	 * @return WriteRateTransactionType
    	 */
    	private FactorBO getWriteRateTransactionType() {
    		String writeRateTransactionTypeValue = actions
    				.getProperty(AMEConstants.QUOTE_ERATER_TRANSACTION_VALUE_NEW_BUSINESS);
    		FactorBO writeRateTransactionType = new FactorBO();
    		writeRateTransactionType
    				.setFactorName(AMEConstants.QUOTE_TRANSACTION_TYPE);
    		writeRateTransactionType.setFactorValue(writeRateTransactionTypeValue);
    		logger.debug("Rating :getCLSPEKey: QUOTE_TRANSACTION_TYPE"+writeRateTransactionType.getFactorValue());
    		return writeRateTransactionType;
    	}

    	/**
    	 * @return Caller ID
    	 */
    	private FactorBO getCallerId() {
    		String callerID = actions
    				.getProperty(AMEConstants.QUOTE_CALLER_ID_VALUE);
    		FactorBO callerId = new FactorBO();
    		callerId.setFactorName(AMEConstants.QUOTE_CALLER_ID);
    		callerId.setFactorValue(callerID);
    		logger.debug("Rating :getCLSPEKey: QUOTE_CALLER_ID"+callerId.getFactorValue());
    		return callerId;
    	}

    	/**
    	 * @return Password
    	 */
    	private FactorBO getPassword() {
    		String passwordValue = actions
    				.getProperty(AMEConstants.QUOTE_ERATER_PASS);
    		FactorBO password = new FactorBO();
    		password.setFactorName(AMEConstants.QUOTE_PASS);
    		password.setFactorValue(passwordValue);
    		logger.info("Rating :getCLSPEKey: QUOTE_PASSWORD"+password.getFactorValue());
    		return password;
    	}

    	/**
    	 * @return Debug Index
    	 */
    	private FactorBO getDebugIndex() {
    		String debugIndexValue = actions
    				.getProperty(AMEConstants.QUOTE_DEBUG_INDEX_VALUE);
    		FactorBO debugIndex = new FactorBO();
    		debugIndex.setFactorName(AMEConstants.QUOTE_DEBUG_INDEX);
    		debugIndex.setFactorValue(debugIndexValue);
    		logger.debug("Rating :getCLSPEKey: QUOTE_DEBUG_INDEX"+debugIndex.getFactorValue());
    		return debugIndex;
    	}

    	/**
    	 * @return Source region
    	 */
    	private FactorBO getSourceRegion() {
    		String sourceRegion = ameConfigActions
    				.getProperty(AMEConstants.QUOTE_GET_SOURCE_REGION);
    		FactorBO source = new FactorBO();
    		source.setFactorName(AMEConstants.QUOTE_SOURCE_REGION);
    		source.setFactorValue(sourceRegion);
    		logger.debug("Rating :getCLSPEKey: QUOTE_SOURCE_REGION"+source.getFactorValue());
    		return source;
    	}

    	/**
    	 * @return Service
    	 */
    	private FactorBO getService() {
    		String serviceName = actions
    				.getProperty(AMEConstants.QUOTE_GET_SERVICE_NAME);
    		FactorBO service = new FactorBO();
    		service.setFactorName(AMEConstants.QUOTE_SERVICE);
    		service.setFactorValue(serviceName);
    		logger.debug("Rating :getCLSPEKey: QUOTE_SERVICE"+service.getFactorValue());
    		return service;
    	}

    	/**
    	 * @return LOB
    	 */
    	private FactorBO getLOB(String classCode) {
    		FactorBO lob = new FactorBO();
    		lob.setFactorName(AMEConstants.QUOTE_LOB);
    		lob.setFactorValue(classCode);
    		logger.debug("Rating :getCLSPEKey: QUOTE_LOB"+lob.getFactorValue());
    		return lob;
    	}

    	/**
    	 * @return zeros appended string
    	 */
    	private String appendZeros(String str) {

    		if (str != null && str.length() > 0 && str.length() < 3) {

    			if (str.length() == 1)
    				str = AMEPolicyConstants.APPEND_ZERO
    						+ AMEPolicyConstants.APPEND_ZERO + str;
    			else if (str.length() == 2)
    				str = AMEPolicyConstants.APPEND_ZERO + str;
    		}

    		return str;
    	}

    	/**
    	 * @return State
    	 */
    	private FactorBO getState() {
    		
    		//TODO: Need to change the implementation 
    		// Need to get the state from PAS apart from KAIZEN
    		String stateCode = null;
    		//Code removes since Location can be populated directly from ServiceContext.  
    		/*if (ApplicationContext.getBranchCode() != null) {
    			stateCode = ApplicationContext.getBranchCode().toString();
    		}*/
    		
    		stateCode=ServiceContext.getLocation();
    		logger.debug("Rating :getCLSPEKey: ServiceContext.getLocation()"+ServiceContext.getLocation());
    		if(Utils.isEmpty( ServiceContext.getLocation() )){
    			// Defaulted to 20
    			stateCode="20";
    		}
    		else {
    			stateCode= ServiceContext.getLocation();
    		}
    		FactorBO state = new FactorBO();
    		state.setFactorName(AMEConstants.QUOTE_STATE);
    		state.setFactorValue(stateCode);
    		logger.debug("Rating :getCLSPEKey: QUOTE_STATE"+state.getFactorValue());
    		return state;
    	}

    	/**
    	 * @return Company
    	 */
    	private FactorBO getCompany() {

    		String companyCode = null;
    		//Commented , because select CTL_REG_CODE from T_MAS_CONTROL is the query to access the company Code, Only one raw present. 
    		/*	if (ApplicationContext.getRegionCode() != null) {
    			companyCode = ApplicationContext.getRegionCode().toString();
    		}*/
    		
    		
    		
    		if(Utils.isEmpty( Utils.getSingleValueAppConfig( "RSA_COMPANY_CODE") )){
    			// Defaulted to 2
    			companyCode="2";
    		}else{
    			
    			companyCode=Utils.getSingleValueAppConfig( "RSA_COMPANY_CODE");	
    		}
    		
    		
    		FactorBO company = new FactorBO();
    		company.setFactorName(AMEConstants.QUOTE_COMPANY);
    		company.setFactorValue(companyCode);
    		logger.debug("Rating :getCLSPEKey: QUOTE_COMPANY"+company.getFactorValue());
    		return company;
    	}

    	/**
    	 * method to get Location code from Tariff Master for Erater
    	 * 
    	 * @param tarcode
    	 * @return LocCode
    	 * @throws DataAccessException
    	 */
    	private Integer getLocCodeRating(Integer tariffCode) {
    		String stateCode = null;
    		Session session = null;
    		Integer locCode = null;
    		if (tariffCode != null) {

    			// TODO: Need to change the getBean Implementation
    			// As of now, extended from a temporary SpringUtil class

    			//QuoteDAO quoteDAO = (QuoteDAO) getBean("TODO://");
    			//QuoteDAO quoteDAO =new QuoteDAOImpl();
    			//ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("config/spring/ame-beans.xml");
    			//QuoteDAO quoteDAO = (QuoteDAO) applicationContext.getBean( "quoteDAO" );
    			//QuoteDAO quoteDAO = (QuoteDAO) BeanFactory.getInstance().getBean("quoteDAO");
    			
    			//ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("config/applicationContext.xml");
    			//TariffService tariffService = (TariffService) applicationContext.getBean("tariffService");
    			/*TariffService tariffService = (TariffService) Utils.getBean("tariffService");

    			
    				//LocCode = quoteDAO.getStateTariff(tariffCode);
    			locCode=Integer.parseInt(tariffService.invokeMethod("getTarLocation",tariffCode.toString()).toString());
    				*/
    			locCode = Integer.parseInt(Utils.getSingleValueAppConfig(SvcConstants.RATE_DEPLOYED_LOC));
    			 
    		}
    		return locCode;
    	}

    	/*public static void main( String[] args ){
    		PrepareCLSPEKeyUtil clspeKeyUtil = new PrepareCLSPEKeyUtil();
    		clspeKeyUtil.getLocCodeRating( 201 );
		}*/
    }
   


