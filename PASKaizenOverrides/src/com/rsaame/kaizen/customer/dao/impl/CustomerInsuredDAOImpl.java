/*
 * CustomerDAOImpl.java
 *
 * Copyright (c) 2007-2012 Royal and Sun Alliance Insurance Group plc.
 * St.Mark’s Court, Chart Way, Horsham, West Sussex RH12 1XL, U.K.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of Royal
 * and Sun Alliance Insurance Group plc.("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Royal and Sun Alliance Insurance Group plc.
 */
package com.rsaame.kaizen.customer.dao.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.kaizen.admin.dao.LocationDAO;
import com.rsaame.kaizen.admin.model.Agent;
import com.rsaame.kaizen.admin.model.Country;
import com.rsaame.kaizen.customer.dao.CustomerInsuredDAO;
import com.rsaame.kaizen.customer.model.CustomerBO;
import com.rsaame.kaizen.customer.model.CustomerInsured;
import com.rsaame.kaizen.customer.model.CustomerInsuredHistory;
import com.rsaame.kaizen.customer.model.CustomerInsuredHistoryPK;
import com.rsaame.kaizen.framework.constants.AMEConstants;
import com.rsaame.kaizen.framework.constants.AMEPolicyConstants;
import com.rsaame.kaizen.framework.dao.exception.DataAccessException;
import com.rsaame.kaizen.framework.dao.impl.AMEBaseDAOImpl;
import com.rsaame.kaizen.framework.exception.ServiceException;
import com.rsaame.kaizen.framework.logger.AMELogger;
import com.rsaame.kaizen.framework.logger.AMELoggerFactory;
import com.rsaame.kaizen.framework.model.ApplicationContext;
import com.rsaame.kaizen.framework.model.PaginatedResult;
import com.rsaame.kaizen.framework.model.ServiceContext;
import com.rsaame.kaizen.framework.util.AMEUtil;
import com.rsaame.kaizen.framework.util.BeanFactory;
import com.rsaame.kaizen.quote.model.PolicyComment;
import com.rsaame.kaizen.quote.model.PolicyQuo;
/**
 * CustomerDAOImpl class
 * @author Cognizant Technology Solutions
 */

public class CustomerInsuredDAOImpl extends AMEBaseDAOImpl implements CustomerInsuredDAO {

    // LOGGER INSTANCE
    private static final AMELogger logger = AMELoggerFactory.getInstance().getLogger(CustomerInsuredDAOImpl.class);

   /* private static TreeMap customerHashMapPolicy = new TreeMap();*/

    public static final String SEQ_INSURED_ID = "SEQ_INSURED_CODE";
    
    public static final String CTX_GET_MAIL_ID = "getMailIdOfCustomer()";

    public static final String CTX_GET_CUST_STATUS = "getStatusOfCustomer()";
    
    public static final String CTX_GET_CUST_COMMENTS = "getCommentsForCustomer()";
    
    public static final String CTX_GET_CUST_HISTORY = "fetchCustomerInsuredHistory()";

    public static final String UPDATE_POLICY_INSURED_CODE = "updatePolicyQuoInsuredCode()";
    
    public static final String CTX_UPDATE_DISTRIBUTION_CHNL = "updateDistributionChannel()";
    
    public static final String CTX_SAVE_CUSTOMER_INSURED_DETAILS = "saveCustomerInsuredDetails(CustomerInsured customerInsured)";
    
    public static final String CTX_SAVE_CUSTOMER_INSURED_DETAILS_POL = "saveCustomerInsuredDetailsforpolicy(CustomerInsured customerInsured)";
    
    /** PAS Changes Start - Get data from new views */
    public static String CUSTOMER_SELECT_QUERY = "SELECT INS_E_FULL_NAME,INS_E_PHONE_NO,INS_E_MOBILE_NO,INS_LOC_CODE,to_char(INS_DOB1,'DD/MM/YYYY'),INS_DISTRIBUTION_CHN,INS_PREPARED_DT,CIT_DESC,DCH_E_NAME,INS_BR_CODE,CTL_BR_CODE,INS_INSURED_CODE  FROM V_TRN_CUSTOMER_SEARCH_POL_PAS WHERE ";
    // Change made in query to get Insured ID instead of Insured Code from the view. 
   // public static String CUSTOMER_SELECT_QUERY = "SELECT POL_INSURED_ID,INS_E_FULL_NAME,INS_E_PHONE_NO,INS_E_MOBILE_NO,INS_LOC_CODE,to_char(INS_DOB1,'DD/MM/YYYY'),INS_DISTRIBUTION_CHN,INS_PREPARED_DT,CIT_DESC,DCH_E_NAME,INS_BR_CODE,CTL_BR_CODE  FROM V_TRN_CUSTOMER_SEARCH_POL_PAS WHERE ";

    
    public static String CUSTOMER_SELECT_QUERY_QUOTE = "SELECT INS_E_FULL_NAME,INS_E_PHONE_NO,INS_E_MOBILE_NO,INS_LOC_CODE,to_char(INS_DOB1,'DD/MM/YYYY'),INS_DISTRIBUTION_CHN,INS_PREPARED_DT,CIT_DESC,DCH_E_NAME,INS_BR_CODE,CTL_BR_CODE,INS_INSURED_CODE  FROM V_TRN_CUSTOMER_SEARCH_QUO_PAS WHERE ";
  
    
    
    public static String CUSTOMER_SELECT_QUERY_BROKER = "SELECT INS_E_FULL_NAME,INS_E_PHONE_NO,INS_E_MOBILE_NO,INS_LOC_CODE,to_char(INS_DOB1,'DD/MM/YYYY'),INS_DISTRIBUTION_CHN,INS_PREPARED_DT,CIT_DESC,DCH_E_NAME,INS_BR_CODE,CTL_BR_CODE,INS_INSURED_CODE  FROM V_TRN_CUST_SEARCH_POL_PAS_BRK WHERE ";
    
    public static String CUSTOMER_SLECT_QUERY__QUOTE_BROKER = "SELECT INS_E_FULL_NAME,INS_E_PHONE_NO,INS_E_MOBILE_NO,INS_LOC_CODE,to_char(INS_DOB1,'DD/MM/YYYY'),INS_DISTRIBUTION_CHN,INS_PREPARED_DT,CIT_DESC,DCH_E_NAME,INS_BR_CODE,CTL_BR_CODE,INS_INSURED_CODE  FROM V_TRN_CUST_SEARCH_QUO_PAS_BRK WHERE ";
    
    public static String CUSTOMER_SELECT_QUERY_DIRECT = "SELECT INS_E_FULL_NAME,INS_E_PHONE_NO,INS_E_MOBILE_NO,INS_LOC_CODE,to_char(INS_DOB1,'DD/MM/YYYY'),INS_DISTRIBUTION_CHN,INS_PREPARED_DT,CIT_DESC,DCH_E_NAME,INS_BR_CODE,CTL_BR_CODE,INS_INSURED_CODE  FROM V_TRN_CUST_SEARCH_POL_PAS_DIR WHERE ";
    
    public static String CUSTOMER_SELECT_QUERY_QUOTE_DIRECT = "SELECT INS_E_FULL_NAME,INS_E_PHONE_NO,INS_E_MOBILE_NO,INS_LOC_CODE,to_char(INS_DOB1,'DD/MM/YYYY'),INS_DISTRIBUTION_CHN,INS_PREPARED_DT,CIT_DESC,DCH_E_NAME,INS_BR_CODE,CTL_BR_CODE,INS_INSURED_CODE  FROM V_TRN_CUST_SEARCH_QUO_PAS_DIR WHERE ";
    
    public static final String CUSTOMER_EMAIL_ID_EXACT_SEARCH = "INS_E_EMAIL_ID = ':emailId:'";
    
    public static final String CUSTOMER_PHONE_NO_EXACT_SEARCH = "INS_E_PHONE_NO = ':engphoneno:'" ;
    
    public static final String CUSTOMER_GSM_NO_EXACT_SEARCH = "INS_E_MOBILE_NO = ':enggsmno:'";
    
    public static final String CUSTOMER_FIRST_NAME_EXACT_SEARCH = "UPPER(INS_E_FIRST_NAME) = UPPER(':engFirstName:')";
        
    public static final String CUSTOMER_LAST_NAME_EXACT_SEARCH = "UPPER(INS_E_LAST_NAME) = UPPER(':engLastName:')";
       
    public static final String CUSTOMER_MIDDLE_NAME_EXACT_SEARCH = "UPPER(INS_E_MIDDLE_NAME) = UPPER(':engMiddleName:')";
        
    // Change made in query to get Insured ID instead of Insured Code from the view. 
    //public static String CUSTOMER_SELECT_QUERY_QUOTE = "SELECT POL_INSURED_ID,INS_E_FULL_NAME,INS_E_PHONE_NO,INS_E_MOBILE_NO,INS_LOC_CODE,to_char(INS_DOB1,'DD/MM/YYYY'),INS_DISTRIBUTION_CHN,INS_PREPARED_DT,CIT_DESC,DCH_E_NAME,INS_BR_CODE,CTL_BR_CODE  FROM V_TRN_CUSTOMER_SEARCH_QUO_PAS WHERE ";

    /** PAS Changes End - Get data from new views */

    /**
     * Method to saveCustomer() Details saves new customer details
     * @param customerDetailsBO
     * @return boolean
     * @throws DataAccessException
     */
    public CustomerInsured saveCustomerInsuredDetails(CustomerInsured customerInsured) throws DataAccessException {
        try {
            logger.debug(CTX_SAVE_CUSTOMER_INSURED_DETAILS, "Method Star_1");
            boolean flag = false;
//          ADM 25.08.2009 CR67 Distribution Channel (Release 2.5)
            Integer customerId = null;
            if ((customerInsured.getInsuredId() != null) && (customerInsured.getInsuredId().equals(Long.valueOf(0)))) {

                customerInsured.setInsuredId(null);
            }
            if(customerInsured.getInsuredId() == null){            	
            	this.hibernateTemplate.saveOrUpdate(customerInsured);
            }	
            else{            	
//            	this.hibernateTemplate.saveOrUpdate(customerInsured);
            	this.hibernateTemplate.merge(customerInsured);
            }	
            logger.debug(CTX_SAVE_CUSTOMER_INSURED_DETAILS, "End Metho_1");
        } catch (HibernateException e) {
            throw e;
        }

        return customerInsured;
    }

    public void saveCustomerInsuredDetailsforpolicy(CustomerInsured customerInsured) throws DataAccessException {
        try {
            logger.debug(CTX_SAVE_CUSTOMER_INSURED_DETAILS_POL, "Method Star_2");          
            this.hibernateTemplate.saveOrUpdate(customerInsured);
        } catch (HibernateException e) {
            throw e;
        }

        
    }
    

    /**
     * Method getCustomerListForSearch() gets list of Customers
     * @param customerObject
     * @return customerList
     * @throws DataAccessException
     */
    public PaginatedResult getCustomerListForSearch(CustomerBO customerObject) throws DataAccessException {
        logger.debug(AMEConstants.CTX_CUSTOMER_LIST_FOR_SEARCH, "Method Star_3");

        /** Create the SessionFactory, Session */
        Session session = getSession();
        Criteria criteria = null;
        Query customerQuery = null;
        Query countQuery = null;
        Long AgentId = null;
        Agent agent = new Agent();
        int distributionChannel = 0;
        String Channel = null;
        Integer agentCategory = null;
        PaginatedResult paginatedResult = new PaginatedResult();
        TreeMap customerHashMap = new TreeMap();
      //Commented the variable to avoid Dead store to local variable , sonar violation on 20-9-2017
       // Integer  CountryCode = 	ApplicationContext.getCountryCode();
		Integer regionCode =    ApplicationContext.getRegionCode();
        logger.debug(AMEConstants.CTX_CUSTOMER_LIST_FOR_SEARCH, "ServiceContext.getLocation() ::"
                + ServiceContext.getLocation());

      //Commented the variable to avoid Dead store to local variable , sonar violation on 20-9-2017
       //String locationCode = ServiceContext.getLocation();
       System.out.println("locationCodeCreate::::::"+customerObject.getLocationCodeCreate());
       System.out.println("ApplicationContext.getRegionCode()::::::"+ApplicationContext.getRegionCode());
       
        if(customerObject.getFirstName()!=null){
        String firstName = customerObject.getFirstName();
        firstName = firstName.replaceAll("[']","''");
        customerObject.setFirstName(firstName);
        }
        
        if(customerObject.getMiddleName()!=null){
        String middleName = customerObject.getMiddleName();
        middleName = middleName.replaceAll("[']","''");
        customerObject.setMiddleName(middleName);
        }
        
        if(customerObject.getLastName()!=null){
        String lastName = customerObject.getLastName();
        lastName = lastName.replaceAll("[']","''");
        customerObject.setLastName(lastName);
        }
        
        if(customerObject.getCompleteName()!=null){
        String completeName = customerObject.getCompleteName();
        completeName = completeName.replaceAll("[']","''");
        customerObject.setCompleteName(completeName);
        }
        
        String selectCustomerQuery = null;
        String selectCustomerQueryForQuote = null;
        /** PAS Changes Start - Get data from new views */
		if (!Utils.isEmpty(customerObject.getBrokerId())
				&& !customerObject.getBrokerId().equalsIgnoreCase("0")) {
			selectCustomerQuery = CUSTOMER_SELECT_QUERY_BROKER;
			selectCustomerQueryForQuote = CUSTOMER_SLECT_QUERY__QUOTE_BROKER;
		} else {
			selectCustomerQuery = CUSTOMER_SELECT_QUERY_DIRECT;
			selectCustomerQueryForQuote = CUSTOMER_SELECT_QUERY_QUOTE_DIRECT;
		}
       
        //String selectCustomerQuery = CUSTOMER_SELECT_BROKER_QUERY;
      
/*      String selectCustomerQuery = getQuery(AMEConstants.CUSTOMER_SELECT_QUERY);
        String selectCustomerQueryForQuote = getQuery(AMEConstants.CUSTOMER_SELECT_QUERY_QUOTE); */
       /** PAS Changes End - Get data from new views */
        
        if(regionCode.intValue()!=3)
		{              //ADM 24.10.2010  Release 4.0 - Oman MultiBranch Transactions within Single DB Schema
        // ADM 11.03.2010 CR65 Access To Other Location data (Release 3.0) --Added for Checking Existing Customer
        	 
        	 if(customerObject.getCustSaveReq() != null && customerObject.getLocationCodeCreate()!= null){
                if(customerObject.getCustSaveReq().equalsIgnoreCase(AMEConstants.YES)){             
                		customerHashMap.put(AMEConstants.CTX_LOCATION_CODE, AMEPolicyConstants.CUSTOMER_LOCATION_CODE);
                }
            }
        	 
        	 if(customerObject.getCustomerSaveReq() != null  && customerObject.getLocationCodeCreate()!= null){
                if(customerObject.getCustomerSaveReq().equalsIgnoreCase(AMEConstants.YES)){             
                		customerHashMap.put(AMEConstants.CTX_LOCATION_CODE, AMEPolicyConstants.CUSTOMER_LOCATION_CODE);
                }
            }
        
        	 if(customerObject.getCopyButtonReq() != null ){
                if(customerObject.getCopyButtonReq().equalsIgnoreCase(AMEConstants.YES)){             
                		customerHashMap.put(AMEConstants.CTX_LOCATION_CODE, AMEPolicyConstants.CUSTOMER_LOCATION_CODE);
                }
            }
		}
        // ADM 11.03.2010 CR65 Access To Other Location data (Release 3.0) --Added for Checking Existing Customer	
       
       

 /** Dynamically getting the input from UI */
        /*
		 * if broker is logged in then use prepared by check in where clause because
		 * broker can see only customer results which he created.
		 */
        /*if(ServiceContext.getUser().getProfile().equalsIgnoreCase( "Broker" )){
        	 customerHashMap.put(AMEConstants.CTX_USER_ID,AMEPolicyConstants.USER_ID); // ADM 18.02.2010 CR65 Access To Other Location data (Release 3.0)
        }*/

//      customerHashMap.put(AMEConstants.CS_CUSTOMER_ID, AMEPolicyConstants.CUSTOMER_INSURED_ID);
      // Change made in search to pass Insured ID instead of Insured Code. 
        customerHashMap.put(AMEConstants.CS_CUSTOMER_ID,  "INS_INSURED_CODE=:custId:");
        
        if( !Utils.isEmpty( customerObject.getExactSearch() ) && customerObject.getExactSearch()){
        	customerHashMap.put(AMEConstants.CS_EMAIL_ID, CUSTOMER_EMAIL_ID_EXACT_SEARCH );
        	customerHashMap.put(AMEConstants.CS_PHONE_NO, CUSTOMER_PHONE_NO_EXACT_SEARCH );
        	customerHashMap.put(AMEConstants.CS_MOBILE_NO, CUSTOMER_GSM_NO_EXACT_SEARCH );
        	customerHashMap.put(AMEConstants.CS_FIRST_NAME, CUSTOMER_FIRST_NAME_EXACT_SEARCH );
        	customerHashMap.put(AMEConstants.CS_LAST_NAME, CUSTOMER_LAST_NAME_EXACT_SEARCH );
        	customerHashMap.put(AMEConstants.CS_MIDDLE_NAME, CUSTOMER_MIDDLE_NAME_EXACT_SEARCH );
        	customerHashMap.put(AMEConstants.CS_CONTACT_NO, CUSTOMER_PHONE_NO_EXACT_SEARCH );
        }
        else{
        	customerHashMap.put(AMEConstants.CS_EMAIL_ID, AMEPolicyConstants.CUSTOMER_EMAIL_ID);
        	customerHashMap.put(AMEConstants.CS_PHONE_NO, AMEPolicyConstants.CUSTOMER_PHONE_NO);
        	customerHashMap.put(AMEConstants.CS_MOBILE_NO, AMEPolicyConstants.CUSTOMER_GSM_NO);
        	customerHashMap.put(AMEConstants.CS_FIRST_NAME, AMEPolicyConstants.CUSTOMER_FIRST_NAME);
        	customerHashMap.put(AMEConstants.CS_LAST_NAME, AMEPolicyConstants.CUSTOMER_LAST_NAME);
        	customerHashMap.put(AMEConstants.CS_MIDDLE_NAME, AMEPolicyConstants.CUSTOMER_MIDDLE_NAME);
        	customerHashMap.put(AMEConstants.CS_CONTACT_NO, AMEPolicyConstants.CUSTOMER_PHONE_NO);
        }
        customerHashMap.put(AMEConstants.CS_DOB, AMEPolicyConstants.CUSTOMER_DOB);
        customerHashMap.put(AMEConstants.CS_POB_NO, AMEPolicyConstants.CUSTOMER_ZIP_CODE);
        customerHashMap.put(AMEConstants.CS_BROKER_NAME, AMEPolicyConstants.CUSTOMER_BAR_CODE);
        
        // ADM 11.03.2010 CR65 Access To Other Location data (Release 3.0) --Added
        if( !Utils.isEmpty( customerObject.getExactSearch() ) && customerObject.getExactSearch()){
        	customerHashMap.put(AMEConstants.CS_COMPLETE_NAME, "UPPER(INS_E_FIRST_NAME ||DECODE(INS_E_MIDDLE_NAME,null,'',' '||INS_E_MIDDLE_NAME) || DECODE(INS_E_LAST_NAME,null,'',' '||INS_E_LAST_NAME)) = UPPER(':completeName:')");
        }
        else{
        	customerHashMap.put(AMEConstants.CS_COMPLETE_NAME, "UPPER(INS_E_FIRST_NAME ||DECODE(INS_E_MIDDLE_NAME,null,'',' '||INS_E_MIDDLE_NAME) || DECODE(INS_E_LAST_NAME,null,'',' '||INS_E_LAST_NAME)) like UPPER('%:completeName:%')");
        }
        //customerHashMap.put(AMEConstants.CS_POLICY_QUOTE_NO, AMEPolicyConstants.CUSTOMER_POLICY_QUOTE_NO);
        customerHashMap.put(AMEConstants.CS_POLICY_QUOTE_NO,  "POL_QUOTATION_NO = ':policyNo:'");
         
           //customerHashMap.put(AMEConstants.CS_BROKER_ID, AMEPolicyConstants.CUSTOMER_BROKER_ID);

//      ADM 03.03.2010 : Agent Profile Changes starts
        
        
 //customerHashMap.put(AMEConstants.CS_AGENT_NAME, AMEPolicyConstants.CUSTOMER_AGENT_CODE);
   if(customerObject.getAgentId()!=null)
        {
        	
        	try {
        			AgentId = Long.valueOf(customerObject.getAgentId());
        			session = getSession();
				criteria = session.createCriteria(Agent.class);
				criteria.add(Restrictions.eq("agentCode", AgentId));
				agent = (Agent) criteria.uniqueResult();
				agentCategory = agent.getAgentCategory().getCode();
				if(agentCategory.intValue() ==2)
				{
					distributionChannel = 11;
					if(distributionChannel == 11){
						Channel = "DIRECT - Dealership";
						
					}
					
				}
				else if(agentCategory.intValue() ==3)
				{
					distributionChannel = 7;
					if(distributionChannel == 7){
						Channel = "Affinity - AGENT";
						
					}
				}

			} catch (HibernateException hibernateException) {
				throw new DataAccessException(hibernateException);
			}
			
		//Commented for CR-65 location change
			//Channel = Integer.toString(distributionChannel);
			
        	customerObject.setDistributionChannel(Channel);
        	
        	
        //customerHashMap.put(AMEConstants.CS_AGENT_ID, AMEPolicyConstants.CUSTOMER_AGENT_ID);
        customerHashMap.put(AMEConstants.CS_DISTRIBUTION_CHANNEL, AMEPolicyConstants.CUSTOMER_DISTRIBUTION_CHANNEL);
        }
       
        
//      ADM 03.03.2010 : Agent Profile Changes ends
//Format the date of birth and creation date
        getFormattedCustomerBO(customerObject);

        String finalCustomerQueryForQuote = AMEUtil.completeQuery(selectCustomerQueryForQuote, customerHashMap,customerObject);
        
        //Added for CR 54 - Fuzzy Search
		if( Utils.isEmpty( customerObject.getExactSearch() ) || !customerObject.getExactSearch() ){
			logger.debug( AMEConstants.CTX_CUSTOMER_LIST_FOR_SEARCH, "Quote query b4 name--->" + finalCustomerQueryForQuote );
			String CUSTOMER_FULL_NAME_1 = "UPPER(INS_E_FIRST_NAME ||DECODE(INS_E_MIDDLE_NAME,null,'',' '||INS_E_MIDDLE_NAME) || DECODE(INS_E_LAST_NAME,null,'',' '||INS_E_LAST_NAME)) like UPPER('%";
			String CUSTOMER_FULL_NAME_2 = "%')";
			String customerEnteredNameQuote = customerObject.getCompleteName();
			String formingNamePartQuote = "";
			int orFlagQuote = 0;
			if( customerEnteredNameQuote != null && !( ( "" ).equals( customerEnteredNameQuote ) ) ){
				String[] nameParts = customerEnteredNameQuote.split( "\\s" );
				for( int x = 0; x < nameParts.length; x++ ){
					if( orFlagQuote == 0 ){
						formingNamePartQuote = new StringBuffer().append( formingNamePartQuote ).append( " AND ( " ).toString();
					}
					if( orFlagQuote == 1 ){
						formingNamePartQuote = new StringBuffer().append( formingNamePartQuote ).append( " OR " ).toString();
					}
					formingNamePartQuote = new StringBuffer().append( formingNamePartQuote ).append( CUSTOMER_FULL_NAME_1 + nameParts[ x ] + CUSTOMER_FULL_NAME_2 ).toString();
					orFlagQuote = 1;
				}
				formingNamePartQuote = new StringBuffer().append( formingNamePartQuote ).append( " ) " ).toString();
				logger.debug( AMEConstants.CTX_CUSTOMER_LIST_FOR_SEARCH, "formingNamePartQuote--->" + formingNamePartQuote );
				finalCustomerQueryForQuote = new StringBuffer().append( finalCustomerQueryForQuote ).append( formingNamePartQuote ).toString();
			}
		}
        //Set the policy number for searching the policy
        //customerHashMap.put(AMEConstants.CS_POLICY_QUOTE_NO, AMEPolicyConstants.CUSTOMER_POLICY_NO);
       customerHashMap.put(AMEConstants.CS_POLICY_QUOTE_NO, "POL_POLICY_NO = ':policyNo:'");
        String finalCustomerQuery = AMEUtil.completeQuery(selectCustomerQuery, customerHashMap, customerObject);
        
        //Added for CR 54 - Fuzzy Search
		if( Utils.isEmpty( customerObject.getExactSearch() ) || !customerObject.getExactSearch() ){
			logger.debug( AMEConstants.CTX_CUSTOMER_LIST_FOR_SEARCH, "Policy query b4 name--->" + finalCustomerQuery );
			String CUSTOMER_FULL_NAME_3 = "UPPER(INS_E_FIRST_NAME ||DECODE(INS_E_MIDDLE_NAME,null,'',' '||INS_E_MIDDLE_NAME) || DECODE(INS_E_LAST_NAME,null,'',' '||INS_E_LAST_NAME)) like UPPER('%";
			String CUSTOMER_FULL_NAME_4 = "%')";
			String customerEnteredNamePolicy = customerObject.getCompleteName();
			String formingNamePartPolicy = "";
			int orFlagpolicy = 0;
			if( customerEnteredNamePolicy != null && !( ( "" ).equals( customerEnteredNamePolicy ) ) ){
				String[] nameParts = customerEnteredNamePolicy.split( "\\s" );
				for( int x = 0; x < nameParts.length; x++ ){
					if( orFlagpolicy == 0 ){
						formingNamePartPolicy = new StringBuffer().append( formingNamePartPolicy ).append( " AND ( " ).toString();
					}
					if( orFlagpolicy == 1 ){
						formingNamePartPolicy = new StringBuffer().append( formingNamePartPolicy ).append( " OR " ).toString();
					}
					formingNamePartPolicy = new StringBuffer().append( formingNamePartPolicy ).append( CUSTOMER_FULL_NAME_3 + nameParts[ x ] + CUSTOMER_FULL_NAME_4 ).toString();
					orFlagpolicy = 1;
				}
				formingNamePartPolicy = new StringBuffer().append( formingNamePartPolicy ).append( " ) " ).toString();
				logger.debug( AMEConstants.CTX_CUSTOMER_LIST_FOR_SEARCH, "formingNamePartPolicy--->" + formingNamePartPolicy );
				finalCustomerQuery = new StringBuffer().append( finalCustomerQuery ).append( formingNamePartPolicy ).toString();
			}
		}
        finalCustomerQueryForQuote = new StringBuffer().append(finalCustomerQueryForQuote).append(" AND").toString();
    	
        if (!Utils.isEmpty(customerObject.getBrokerId())
				&& !customerObject.getBrokerId().equalsIgnoreCase("0")) {
        	finalCustomerQueryForQuote = new StringBuffer().append(finalCustomerQueryForQuote).append(" INS_BR_CODE = ").append(customerObject.getBrokerId()).toString();
        }else{
        	finalCustomerQueryForQuote = new StringBuffer().append(finalCustomerQueryForQuote).append(" USER_ID in (").append(customerObject.getCcgCode()).append( ")" ).toString();
        }
    	
        finalCustomerQuery = new StringBuffer().append(finalCustomerQuery).append(" AND").toString();
    	
        if (!Utils.isEmpty(customerObject.getBrokerId())
				&& !customerObject.getBrokerId().equalsIgnoreCase("0")) {
        	finalCustomerQuery = new StringBuffer().append(finalCustomerQuery).append(" INS_BR_CODE = ").append(customerObject.getBrokerId()).toString();
        }else{
        	finalCustomerQuery = new StringBuffer().append(finalCustomerQuery).append(" USER_ID in (").append(customerObject.getCcgCode()).append( ")" ).toString();
        }
        
        String finalQuery = finalCustomerQueryForQuote + " UNION " + " " + finalCustomerQuery;

        logger.debug(AMEConstants.CTX_CUSTOMER_LIST_FOR_SEARCH, "Customer Search Final Query ---> " + finalQuery);
        System.out.println("Customer Search Final Query ---> " + finalQuery);
        
        /** Run the query to get the required List */
        //customerQuery = session.createSQLQuery(finalCustomerQuery);
        customerQuery = session.createSQLQuery(finalQuery);
        countQuery = session.createSQLQuery("SELECT COUNT(1) FROM(" + finalQuery + ")");
        List resultCustomerList = new ArrayList();
        List finalCustomerList = new ArrayList();
        int size;
        BigDecimal finalCount;

        if (customerObject.getNumberOfRecords().intValue() == 0) {
            finalCount = (BigDecimal) countQuery.uniqueResult();
            size = finalCount.intValue();
            logger.debug(AMEConstants.CTX_CUSTOMER_LIST_FOR_SEARCH, "Customer Search Query Size = " + size);
            customerObject.setNumberOfRecords(Integer.valueOf(size));
            getPaginatedResult(customerQuery, customerObject.getCurrentPage(), size, paginatedResult);
            resultCustomerList = customerQuery.list();
            if (size > paginatedResult.getRecordsPerPage().intValue()) {
                //if the size of the list is greater than the no. of records
                // per page
                //finalCustomerList will contain the no.of records to be
                // displayed
                //in the selected page
                int firstPageRecords = (paginatedResult.getRecordsPerPage().intValue() * paginatedResult
                        .getCurrentPage().intValue())
                        - paginatedResult.getRecordsPerPage().intValue();
                for (int i = firstPageRecords; i < firstPageRecords + paginatedResult.getRecordsPerPage().intValue(); i++) {
                    Object object = resultCustomerList.get(i);
                    finalCustomerList.add(object);
                }
            } else {
                //if the size is less than the total no of records per page
                //display the whole list
                Iterator iterator = resultCustomerList.iterator();
                while (iterator.hasNext()) {
                    Object object = (Object) iterator.next();
                    finalCustomerList.add(object);
                }

            }
        } else {
            size = customerObject.getNumberOfRecords().intValue();
            getPaginatedResult(customerQuery, customerObject.getCurrentPage(), size, paginatedResult);
            finalCustomerList = customerQuery.list();
        }

        Iterator itr = finalCustomerList.iterator();
        Object[] row = null;
        List customerList = new ArrayList();
        Query query = null;
        // ADM 18.02.2010 CR65 Access To Other Location data (Release 3.0) - Modified inside the while loop
        while (itr.hasNext()) {
            CustomerBO customerBO = new CustomerBO();
            row = (Object[]) itr.next();
            customerBO.setCustomerId(AMEUtil.ObjectToStringSearch(row[11]));
            customerBO.setCompleteName(AMEUtil.ObjectToString(row[0]));
            customerBO.setPhoneNo(AMEUtil.ObjectToStringSearch(row[1]));
            customerBO.setMobileNo(AMEUtil.ObjectToStringSearch(row[2]));
            customerBO.setDateOfBirth(AMEUtil.ObjectToStringSearch(row[4]));
            customerBO.setBrokerName(AMEUtil.ObjectToString(row[5]));
            //Added for CR 309
            customerBO.setCreationDate(AMEUtil.ObjectToStringSearch(row[6]));
            customerBO.setCity(AMEUtil.ObjectToString(row[7]));
            customerBO.setDistributionChannel(AMEUtil.ObjectToString(row[8]));

            customerBO.setInsuredCode(AMEUtil.ObjectToString(row[11]));
            //set new location code for CR 65
            BeanFactory beanFactory = BeanFactory.getInstance();
            LocationDAO locationDAO= (LocationDAO) beanFactory.getBean("locationDAO");
            

            if(row[10] != null){
            	try{
            		//System.out.println("row.length----"+row.length);
            		logger.debug("row selected from location" , AMEUtil.ObjectToString(row[10]));
            		customerBO.setLocationCode(Integer.valueOf(AMEUtil.ObjectToString(row[10])));            		
            	} catch(Exception exp){
            	    logger.debug("Getting error while setting the location code ",exp);
            	}
            }


              if(row[10] != null){
            	try{
            		logger.debug("row selected from location" , AMEUtil.ObjectToString(row[10]));
            		String loc = AMEUtil.ObjectToString(row[10]);            	
            		//System.out.println("--------loc-----"+loc);
            		customerBO.setLocationName(locationDAO.getLocationDesc(loc));            		

            	} catch(Exception exp){
            		exp.printStackTrace();
            	}
            }           
            customerList.add(customerBO);
        }
        paginatedResult.setObjectArray(customerList.toArray());
        //closeSession(session);
        logger.debug( "Result customer List size "+ resultCustomerList.size(),resultCustomerList );
        logger.debug(AMEConstants.CTX_CUSTOMER_LIST_FOR_SEARCH, "End Metho_2");
        return paginatedResult;
    }

    private void getFormattedCustomerBO(CustomerBO customerObject) {
        String dateOfBirth = customerObject.getDateOfBirth();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        if (dateOfBirth != null && !dateOfBirth.trim().equals("")) {
            Date dob = new Date(dateOfBirth);
            customerObject.setDateOfBirth(simpleDateFormat.format(dob));
        }
    }

    /**
     * Method getCustomerListForView() gives customer details
     * @param customerviewObject
     * @return CustomerInsured
     * @throws DataAccessException
     */
    public CustomerInsured getCustomerListForView(CustomerInsured customerInsured) throws DataAccessException {
        logger.debug(AMEConstants.CTX_CUSTOMER_LIST_FOR_VIEW, "Method Star_4");

        /** Create the SessionFactory, Session */
        Session session = getSession();

        Query finalCustomerViewQuery = null;
        String query = getQuery(AMEConstants.CUSTOMER_VIEW_QUERY);
        //String query="SELECT customerInsured FROM CustomerInsured
        // customerInsured LEFT OUTER JOIN customerInsured.nationality
        // nationality WHERE customerInsured.insuredId = :insuredId";
        /** Run the query to get the required List */
        finalCustomerViewQuery = session.createQuery(query);
        /** Query Criteria */
        finalCustomerViewQuery.setLong(com.Constant.CONST_INSUREDID, customerInsured.getInsuredId().longValue());

        CustomerInsured insured = (CustomerInsured) finalCustomerViewQuery.uniqueResult();
        insured.getCustomerSource();
        //closeSession(session);
        logger.debug(AMEConstants.CTX_CUSTOMER_LIST_FOR_VIEW, "End Metho_3");

        return insured;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.rsaame.kaizen.customer.dao.CustomerInsuredDAO#searchForExistingCustomer(com.rsaame.kaizen.customer.model.CustomerInsured)
     */
    public Boolean searchForExistingCustomerWeb(CustomerInsured customerInsured) throws DataAccessException {
        // TODO Auto-generated method stub
        logger.debug(AMEConstants.CTX_SEARCH_EXISTING_CUSTOMER, "Method Star_5");

        //      Create the SessionFactory, Session
        Session session = getSession();

        String firstName = customerInsured.getEngFirstName();
        String lastName = customerInsured.getEngLastName();
        String zipCode = customerInsured.getEngZipCode();
        String dob = AMEUtil.DateToString(customerInsured.getDob());
        //	System.out.println("dob ::"+dob);
        //Date dobDate = customerInsured.getDob();

        String customerCheckQuery = getQuery(AMEConstants.CUSTOMER_CHECK_QUERY);
        /*
         * String customerCheckQuery="SELECT
         * customerInsured.engFirstName,customerInsured.engMiddleName,customerInsured.engLastName," +
         * "customerInsured.engMobileNo,customerInsured.engZipCode,customerInsured.dob " +
         * "FROM CustomerInsured customerInsured";
         */
        List customerInfoList = session.createQuery(customerCheckQuery).list();
        Iterator itr = customerInfoList.iterator();
        Object[] row = null;

        Boolean response = Boolean.FALSE;
        while (itr.hasNext()) {
            row = (Object[]) itr.next();
            /*
             * if((firstName.equalsIgnoreCase(row[0].toString()))) {
             * System.out.println("row[0].toString() equals
             * ::firstName::"+row[0].toString()); }
             * if((middleName.compareToIgnoreCase(row[1].toString()))==0){
             * System.out.println("row[1].toString()
             * ::middleName::"+row[1].toString());}
             * if((lastName.equalsIgnoreCase(row[2].toString()))){
             * System.out.println("row[2].toString()
             * ::lastName::"+row[2].toString());}
             * if((mobileNo.equalsIgnoreCase(row[3].toString()))){
             * System.out.println("row[3].toString()
             * ::mobileNo::"+row[3].toString());}
             * if((zipCode.equalsIgnoreCase(row[4].toString()))){
             * System.out.println("row[4].toString()
             * ::zipCode::"+row[4].toString());}
             */

            //System.out.println("dob ::"+dob);
            //System.out.println("row[5] ::"+row[5]);
            //if((dob.equalsIgnoreCase(row[5].toString()))){
            //        System.out.println("row[5].toString()
            // ::dob::"+row[5].toString());}
            if (firstName != null && lastName != null && zipCode != null && dob != null && row[0] != null
                    && row[2] != null && row[4] != null && row[5] != null
                    && (firstName.equalsIgnoreCase(row[0].toString())) &&
                    //(middleName.equalsIgnoreCase(row[1].toString())) &&
                    (lastName.equalsIgnoreCase(row[2].toString())) &&
                    //(mobileNo.equalsIgnoreCase(row[3].toString())) &&
                    (zipCode.equalsIgnoreCase(row[4].toString())) && (dob.equalsIgnoreCase(row[5].toString()))) {
                response = Boolean.TRUE;
                //  throw new DataAccessException("Customer Already Exists");
                //System.out.println("IN IF response is :"+response);
                //return response;
            }
        }
        logger.debug(AMEConstants.CTX_SEARCH_EXISTING_CUSTOMER, "Method End");
        return response;
    }

    public BigDecimal getNextInsuredSequenceNumber(String sequenceName) {
        Session session = getSession();
        Query query = session.createSQLQuery(SEQ_PART1 + sequenceName + SEQ_PART2);
        BigDecimal nextSequenceNumber = (BigDecimal) query.uniqueResult();
        try {
            closeSession(session);
        } catch (DataAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return nextSequenceNumber;
    }

    /**
     * This method will check whether the Password entered is Valid
     * @param CustomerInsured <code>CustomerInsured</code>
     * @return <code>Boolean</code>
     * @throws <code>DataAccessException</code>
     */
    public boolean isPasswordValid(CustomerInsured customerInsured) throws DataAccessException {
        Session session = null;
        boolean result = false;
        CustomerInsured insured = null;
        try {
            logger.debug(AMEConstants.CTX_IS_PAS_VALID, "Entered Password for Customer ::" + customerInsured);  //SONARFIX -- 26-apr-2018 -- renamed the variable from CTX_IS_PASSWORD_VALID to CTX_IS_PAS_VALID
            session = getSession();
            Query query = session.createQuery(QRY_VALIDATE_PASSWORD);
            logger.debug(AMEConstants.CTX_IS_PAS_VALID, "Created query ::" + query.toString());  //SONARFIX -- 26-apr-2018 -- renamed the variable from CTX_IS_PASSWORD_VALID to CTX_IS_PAS_VALID
            query.setParameter(INSURED_ID, customerInsured.getInsuredId());
            query.setParameter(PASSWORD, customerInsured.getPassword());
            insured = (CustomerInsured) query.uniqueResult();
            if (insured != null) {
                result = true;
            }
            logger.debug(AMEConstants.CTX_IS_PAS_VALID, "Result ::" + result);  //SONARFIX -- 26-apr-2018 -- renamed the variable from CTX_IS_PASSWORD_VALID to CTX_IS_PAS_VALID
        } catch (HibernateException hibernateException) {
            throw new DataAccessException(hibernateException);
        } finally {
            closeSession(session);
        }
        return result;
    }

    /**
     * This method will save the password if it is Valid
     * @param CustomerInsured <code>CustomerInsured</code>
     * @return <code>CustomerInsured</code>
     * @throws <code>DataAccessException</code>
     */
    public CustomerInsured saveNewPassword(CustomerInsured customerInsured) throws DataAccessException {
        CustomerInsured insured = null;
        try {
            logger.debug(AMEConstants.CTX_SAVE_NEW_PAS, "Starting ::" + customerInsured);  //SONARFIX -- 26-apr-2018 -- renamed the variable from CTX_SAVE_NEW_PASSWORD to CTX_SAVE_NEW_PAS
            this.hibernateTemplate.saveOrUpdate(customerInsured);
            logger.debug(AMEConstants.CTX_SAVE_NEW_PAS, "Result ::" + customerInsured);  //SONARFIX -- 26-apr-2018 -- renamed the variable from CTX_SAVE_NEW_PASSWORD to CTX_SAVE_NEW_PAS
        } catch (HibernateException hibernateException) {
            throw new DataAccessException(hibernateException);
        } finally {
            //closeSession(session);
        	 logger.debug(AMEConstants.CTX_SAVE_NEW_PAS, "Ending ::" + customerInsured);  //SONARFIX -- 26-apr-2018 -- renamed the variable from CTX_SAVE_NEW_PASSWORD to CTX_SAVE_NEW_PAS
        }
        return customerInsured;
    }

    /**
     * This method will fetch Hint Question
     * 
     * @param CustomerInsured <code>CustomerInsured</code>
     * @return <code>Boolean</code>
     * @throws ServiceException
     * @throws <code>DataAccessException,ServiceException</code>
     */
    public CustomerInsured fetchHintQuestion(CustomerInsured customerInsured) throws DataAccessException,
            ServiceException {
        Session session = null;
        CustomerInsured insured = null;
        try {
            logger.debug(AMEConstants.CTX_FETCH_HINT_QUESTION, "Entered ::" + customerInsured);
            session = getSession();
            Query query = session.createQuery(QRY_VALIDATE_EMAIL_ID);
            logger.debug(AMEConstants.CTX_FETCH_HINT_QUESTION, "Created query ::" + query.toString());
            query.setParameter(EMAIL_ID, customerInsured.getEngEmailId());
            insured = (CustomerInsured) query.uniqueResult();
            if (insured == null) {
                throw new ServiceException(AMEConstants.INVALID_EMAIL_ID);
            }

        } catch (HibernateException hibernateException) {
            throw new DataAccessException(hibernateException);
        } finally {
            closeSession(session);
        }
        return insured;
    }

    public List checkForExistingInsured(PolicyQuo quote) throws DataAccessException {
        List insured = null;
        Session session = getSession();
        Query query = session.createQuery(QRY_VALIDATE_INSURED);
        query.setParameter(ENG_FIRST_NAME, quote.getCashCustomerQuo().getEngName1());
        query.setParameter(ENG_MOBILE_NO, quote.getCashCustomerQuo().getEngGsmNo());
        //query.setParameter(ENG_ZIP_CODE,
        // quote.getCashCustomerQuo().getEngZipCode());
        query.setParameter(DATE_OF_BIRTH, quote.getCashCustomerQuo().getDob());
        query.setParameter(EMAIL_ID, quote.getCashCustomerQuo().getEngEmailId());
        insured = (List) query.list();
        System.out.println("insured" + insured);
        System.out.println(insured.size());
        return insured;
    }

    public List checkIfInsuredExists(CustomerInsured customerInsured) throws DataAccessException {
        List customerResult = null;
        Session session = null;
        Criteria criteria = null;
        Date dateOfBirth = customerInsured.getDob();
        session = getSession();
        criteria = session.createCriteria(CustomerInsured.class);
        criteria.add(Restrictions.eq("engFirstName", customerInsured.getEngFirstName()));
        
        // CR 70 Matching Customer Insured changes
        // else check was added because the if logic did not work as required when the engMiddleName field was null  
        if(customerInsured.getEngMiddleName()!=null){
	    criteria.add(Restrictions.eq("engMiddleName", customerInsured.getEngMiddleName()));
        }else{
        	 criteria.add(Restrictions.isNull("engMiddleName"));
        }
       
        criteria.add(Restrictions.eq("engLastName", customerInsured.getEngLastName()));
        
        if(customerInsured.getEngMobileNo()!=null)
        	criteria.add(Restrictions.eq("engMobileNo", customerInsured.getEngMobileNo()));
        if(customerInsured.getEngZipCode()!=null)
        criteria.add(Restrictions.eq("engZipCode", customerInsured.getEngZipCode()));
        if(dateOfBirth!=null)
	    criteria.add(Restrictions.eq("dob", dateOfBirth));   
	        
        if(customerInsured.getBrCode()!=null)
        	criteria.add(Restrictions.eq("brCode", customerInsured.getBrCode()));
        customerResult = criteria.list();        
        System.out.println(customerResult.size());
        return customerResult;
    }

    public CustomerInsured fetchInsuredForInsuredId(CustomerInsured customerInsured) throws DataAccessException {
        Session session = null;
        CustomerInsured insured = null;
        try {
            session = getSession();
            Query query = session.createQuery(QRY_VALIDATE_EXISTING_INSURED);
            query.setParameter(INSURED_ID, customerInsured.getInsuredId());
            insured = (CustomerInsured) query.uniqueResult();
        } catch (HibernateException hibernateException) {
            throw new DataAccessException(hibernateException);
        } finally {
            closeSession(session);
        }
        return insured;
    }

    /**
     * This method will generate the new Insured Id.
     * 
     * @return BigDecimal
     * @throws <code>DataAccessException</code>
     */
    public BigDecimal getNextInsuredId() throws DataAccessException {
        return getNextSequenceNumber(SEQ_INSURED_ID);
    }

    /**
     * This method will give the customer details by Insured code.
     * 
     * @return CustomerInsured
     * @throws <code>DataAccessException</code>
     */
    public CustomerInsured getCustomerDetailsByInsuredCode(String insuredCode) throws DataAccessException {
        Session session = null;
        CustomerInsured insured = null;
        try {
            session = getSession();
            Query query = session
                    .createQuery("FROM CustomerInsured customerInsured WHERE customerInsured.insuredId= :insuredId");
            if(insuredCode != null && !"".equals(insuredCode)){
                query.setParameter(com.Constant.CONST_INSUREDID, new Long(insuredCode));
                insured = (CustomerInsured) query.uniqueResult();
            }
        } catch (HibernateException hibernateException) {
            throw new DataAccessException(hibernateException);
        } finally {
            closeSession(session);
        }
        return insured;
    }

    /**
     * The method is used to search for an existing customer.
     * @param customerInsured <code> CustomerInsured </code>
     * @return customerList <code> List </code>
     * @throws DataAccessException
     */
    public List checkForExistingCustomer(CustomerInsured customerInsured) throws DataAccessException {
        List customerResult = null;
        Session session = null;
        Criteria criteria = null;
        Date dateOfBirth = customerInsured.getDob();
        session = getSession();
        criteria = session.createCriteria(CustomerInsured.class);
        criteria.add(Restrictions.like("engFullName", customerInsured.getEngFullName()));
        criteria.add(Restrictions.like("engMobileNo", customerInsured.getEngMobileNo()));
        criteria.add(Restrictions.like("engZipCode", customerInsured.getEngZipCode()));
        criteria.add(Restrictions.like("dob", dateOfBirth));

        if (customerInsured.getBrCode() != null && customerInsured.getDistributionChannel() != null
                && customerInsured.getDistributionChannel().getCode() != null) {
            criteria.add(Restrictions.like("brCode", customerInsured.getBrCode()));
        } else {
            criteria.add(Expression.ne("distributionChannel.code", AMEPolicyConstants.DISTRIBUTION_CHNL_CODE));
        }
        customerResult = criteria.list();
        return customerResult;
    }
    
    
    /**
	 * 
	 * @param insuredId
	 * @return customerInsured
	 * @throws DataAccessException
	 */
	public CustomerInsured getCustomerInsuredDetails(Long insuredId)
			throws DataAccessException {
		CustomerInsured customerInsured = null;
		Session session = getSession();
		Criteria criteria = session.createCriteria(CustomerInsured.class);
		criteria.add(Restrictions.eq(com.Constant.CONST_INSUREDID,insuredId));
		customerInsured = (CustomerInsured) criteria.uniqueResult();
		return customerInsured;
	}
	
	//FETCH CUSTOMER INSURED FOR INSURED TYPE
    public CustomerInsured fetchCustomerInsuredForInsuredId(Long customerInsured) throws DataAccessException {
        Session session = null;
        CustomerInsured insured = null;
        try {
            session = getSession();
            Query query = session.createQuery(QRY_VALIDATE_EXISTING_INSURED);
            query.setParameter(INSURED_ID,customerInsured );
            insured = (CustomerInsured) query.uniqueResult();
        } catch (HibernateException hibernateException) {
            throw new DataAccessException(hibernateException);
        } finally {
            closeSession(session);
        }
        return insured;
    }
    
    public String getMailIdOfCustomer(Long insuredCode) throws DataAccessException{
        logger.debug(CTX_GET_MAIL_ID,"Entered in getMailIdOfCustomer");
        Session session = null;
        Query query = null;
        String mailId = null;
        try{
            session = getSession();
            query = session.createQuery(QRY_GET_INSURED_MAIL_ID);
            query.setParameter(INSURED_ID,insuredCode);
            mailId = (String)query.uniqueResult();
        }catch (HibernateException hibernateException) {
            throw new DataAccessException(hibernateException);
        } finally {
            closeSession(session);
        }
        logger.debug(CTX_GET_MAIL_ID,"Mail Id is ::" + mailId);
        return mailId;
        
    }



/* (non-Javadoc)
 * @see com.rsaame.kaizen.customer.dao.CustomerInsuredDAO#getStatusOfCustomer(java.lang.Long)
 */
public Integer getStatusOfCustomer(Long insuredCode) throws DataAccessException{
    logger.debug(CTX_GET_CUST_STATUS,"Entered in getStatus of cust");
    Session session = null;
    Query query = null;
    Integer custStatus = null;
    try{
        session = getSession();
        query = session.createQuery(QRY_GET_CUST_STATUS);
        query.setParameter(INSURED_ID,insuredCode);
        custStatus = (Integer)query.uniqueResult();
    }catch (HibernateException hibernateException) {
        throw new DataAccessException(hibernateException);
    } finally {
        closeSession(session);
    }
    logger.debug(CTX_GET_CUST_STATUS,"Cust Status is ::" + custStatus);
    return custStatus;
    
}

/**
* The method is used to get the comments of the customer.
* @param insured <code> CustomerInsured </code>
* @return customerCommentsList <code> List </code>
* @throws DataAccessException
*/
//16.12.2008 CTS, Chennai - For CR53 - modified on 31.12.2008 to incorporate review comments 
public List getCommentsForCustomer(CustomerInsured insured) throws DataAccessException{
	logger.debug(CTX_GET_CUST_COMMENTS,"Inside getCommentsForCustomer of Customer");
	Session session = null;
    Query query = null;
    List customerCommentsList = null;
    Long InsuredCode = insured.getInsuredId();
    Object[] commentDetailsObj;
	PolicyComment comment = new PolicyComment();
	List policyCommentsList = new ArrayList();
	    
    try{ 
    	session = getSession();
    	String getCustomerCommentsQuery = getQuery(AMEConstants.QRY_GET_CUST_COMMENTS);
    	logger.debug(CTX_GET_CUST_COMMENTS,"getCustomerCommentsQuery is -->  "+getCustomerCommentsQuery);
    	query = session.createSQLQuery(getCustomerCommentsQuery);
    	query.setLong(0,InsuredCode.longValue());
    	query.setLong(1,InsuredCode.longValue());
    	customerCommentsList = (List)query.list();
    	
    	if (customerCommentsList != null && !customerCommentsList.isEmpty())
		{
			logger.debug(CTX_GET_CUST_COMMENTS,"** customerCommentsList size is ::" +customerCommentsList.size());
			Iterator itr = customerCommentsList.iterator();
			while (itr.hasNext())
			{	comment = new PolicyComment();
				commentDetailsObj = (Object[]) itr.next();
								
				comment.setPocPolicyId(new Long(commentDetailsObj[0].toString()));
				comment.setQuotePolicyNo(AMEUtil.ObjectToString(commentDetailsObj[1]));
				comment.setPocComments(AMEUtil.ObjectToString(commentDetailsObj[2]));
				comment.setDocumentDesc(AMEUtil.ObjectToString(commentDetailsObj[3]));
				comment.setCommentDate(AMEUtil.ObjectToString(commentDetailsObj[4]));
				comment.setPocPreparedBy(new Integer(commentDetailsObj[5].toString()));
				policyCommentsList.add(comment);
			}
		}
        
    }catch (HibernateException hibernateException) {
        throw new DataAccessException(hibernateException);
    }finally {
        closeSession(session);
    }
    logger.debug(CTX_GET_CUST_COMMENTS,"List of Customer Comments ::" + customerCommentsList);
    logger.debug(CTX_GET_CUST_COMMENTS,"Last Customer Comment ::" + comment);
    return policyCommentsList;
}

/** Added by ADM 28.04.2009 CR04 Customer Versioning (Release 2.5) 
 * 
 * @param customerInsured
 * @return PaginatedResult
 * @throws DataAccessException
 */

public PaginatedResult fetchCustomerInsuredHistory(CustomerInsured customerInsured)throws DataAccessException{

	Session session = null;
	PaginatedResult paginatedResult = new PaginatedResult();

	logger.debug(CTX_GET_CUST_HISTORY,"Inside CustomerInsuredDAOImpl -->fetchCustomerInsuredHistory()-----");

	String engFirstName = null;

	// List for Query result
	List customerHistoryList = new ArrayList();
	
	// List for holding response object collection
	List finalHistoryList = new ArrayList();
	
	 try {
        	 	
	 	session = getSession();
	 	/*
	 	 * PAS changes: Query changed
	 	 */
        Query query = session.createQuery("SELECT customerInsuredHistory.engFirstName, customerInsuredHistory.engLastName, customerInsuredHistory.companyName, customerInsuredHistory.engZipCode, customerInsuredHistory.status,  customerInsuredHistory.engEmailId,  customerInsuredHistory.engMobileNo,customerInsuredHistory.customerType,"+  
  "customerInsuredHistory.engAddress,customerInsuredHistory.locationCode,customerInsuredHistory.dob, customerInsuredHistory.dob, customerInsuredHistory.comp_id.versionId, " +
  "nvl(customerInsuredHistory.modifiedDt,customerInsuredHistory.preparedDt),customerInsuredHistory.modifiedBy  FROM CustomerInsuredHistory customerInsuredHistory WHERE customerInsuredHistory.comp_id.insuredId = :insuredCode");
    //    if(insuredCode != null && !"".equals(insuredCode)){

        	query.setParameter("insuredCode",customerInsured.getInsuredId());
        	logger.debug(CTX_GET_CUST_HISTORY,"QRY_GET_CUST_HISTORY-----" + query);
            Query count = session.createQuery(QRY_GET_CUST_HISTORY_COUNT);
            count.setParameter(INS_INSURED_CODE,customerInsured.getInsuredId());
            logger.debug(CTX_GET_CUST_HISTORY,"QRY_GET_CUST_HISTORY_COUNT-----" + count);
            Long sizeOfResult;
            
            int resultSize;
                        
            if(customerInsured.getNumberOfRecords().intValue() == 0){
            	sizeOfResult = (Long) count.uniqueResult();
            	resultSize = sizeOfResult.intValue();
            	logger.debug(CTX_GET_CUST_HISTORY,"resultSize-----" + resultSize);
            	customerInsured.setNumberOfRecords(Integer.valueOf(resultSize));
            	
            getPaginatedResult(query,customerInsured.getCurrentPage(),resultSize,paginatedResult);
            	
            	customerHistoryList = query.list();
            	logger.debug(CTX_GET_CUST_HISTORY,"customerHistoryList-----" + customerHistoryList.size());
            	if(resultSize > paginatedResult.getRecordsPerPage().intValue()){
            		
            		 /**
                     * If the size of the list is greater than the no. of
                     * records per page finalHistoryList will contain the
                     * no.of records to be displayed in the selected page
                     */
            		
            			int firstPageRecords = (paginatedResult.getRecordsPerPage().intValue() * paginatedResult
                            .getCurrentPage().intValue())
                            - paginatedResult.getRecordsPerPage().intValue();
            			
            			for (int i = firstPageRecords; i < firstPageRecords
                        + paginatedResult.getRecordsPerPage().intValue(); i++){
            				Object object = customerHistoryList.get(i);
            				finalHistoryList.add(object);
            			}
            			
            	}else{
            		 //if the size is less than the total no of records per page
                    //display the whole list
            		Iterator iterator = customerHistoryList.iterator();
            		 while (iterator.hasNext()) {
                        Object object = (Object) iterator.next();
                        finalHistoryList.add(object);
                    }
            	}
            	
            }else{
            	
            	resultSize = customerInsured.getNumberOfRecords().intValue();
            	getPaginatedResult(query,customerInsured.getCurrentPage(),resultSize,paginatedResult);
            	finalHistoryList = query.list();
            }
            List resultCustomerList = new ArrayList();
            resultCustomerList = getResultCustomerList(finalHistoryList,customerInsured);
            paginatedResult.setObjectArray(resultCustomerList.toArray());
            
   
            
    } catch (HibernateException hibernateException) {
        throw new DataAccessException(hibernateException);
    } finally {
        closeSession(session);
    }
	logger.debug( CTX_GET_CUST_HISTORY,"Customer History List"+ customerHistoryList  );
	
	return paginatedResult;
}

// ADM 28.04.2009 CR04 Customer Versioning (Release 2.5) 

private List getResultCustomerList(List resultCustomerList, CustomerInsured customerInsured) throws DataAccessException {
	List customerList = new ArrayList();
	Iterator itr = resultCustomerList.iterator();
	Object[] row = null;
	
	 while (itr.hasNext()) {
	
    	CustomerInsuredHistory customerInsuredHistory = new CustomerInsuredHistory();
    	CustomerInsuredHistoryPK customerInsuredHistoryPK = new CustomerInsuredHistoryPK();
    	
    	row = (Object[]) itr.next();
    	
    	customerInsuredHistory.setEngFirstName((String) row[0]);
    	customerInsuredHistory.setEngLastName((String) row[1]);
    	customerInsuredHistory.setCompanyName((String) row[2]);
    	customerInsuredHistory.setEngZipCode((String) row[3]);
    	
    	Integer customerStatus  = (Integer) row[4];
    	String customerStatusDesc = null;
    	if(customerStatus != null){
    	    	customerStatusDesc = getCustomerStatusDesc(customerStatus.toString());
    	}    	
    	customerInsuredHistory.setCustomerStatusDesc(customerStatusDesc);
    	
    	customerInsuredHistory.setEngEmailId((String) row[5]);
    	
    	customerInsuredHistory.setEngMobileNo((String) row[6]);
    	
    	String customerType =  (String) row[7];
    	
    	String customerTypeDesc = null;
    	
    	if(customerType != null){
    		customerTypeDesc = getCustomerTypeDesc(customerType);
    	}		
    	customerInsuredHistory.setCustomerTypeDesc(customerTypeDesc);
    	
    	customerInsuredHistory.setEngAddress((String) row[8]);
    	
    	Integer lcoationCode =  (Integer) row[9];
    	
    	String cityDesc = null;
    	if(lcoationCode != null){
    	    	cityDesc = getCityDesc(lcoationCode.toString());
    	    	customerInsuredHistory.setLocationCode((Integer) row[9]);
    	}
    	    	
    	customerInsuredHistory.setCityDesc(cityDesc);
    	
    	customerInsuredHistory.setDob((java.sql.Date) row[10]);
    	
    	Country country = new Country();
    	/*
    	 * PAS changes : Nationality column removed from the query
    	 */
    	//country = (Country) row[11];
    	
    	
    	String nationalityDesc = null;
    	
    	//String natinalityCode = (country.getCode()).toString();
    	
    	//This code is not executing sonar violation fix on 22-9-2017
    	//String natinalityCode = null;
    	
    //	if(natinalityCode != null){
    //		nationalityDesc = getNationalityDesc(natinalityCode);
    //	}
    	
    	country.setNtyEDesc(nationalityDesc);
    	customerInsuredHistory.setNationality(country);
    	
    	// Setting the customer Id (obtained from the request) for customerInsuredHistoryPK
    	customerInsuredHistoryPK.setInsuredId(customerInsured.getInsuredId());
    	
    	
    	// Setting the version Id for customerInsuredHistoryPK
    	customerInsuredHistoryPK.setVersionId((Integer)row[12]);

    	//	 Setting the comp_id of customerInsuredHistory
    	
    	customerInsuredHistory.setComp_id(customerInsuredHistoryPK);
    	            	
    	java.util.Date preparedDt = (java.util.Date) row[13];
    	
    	customerInsuredHistory.setTransactionDateTime(preparedDt);
    	    	
    	Integer modifiedById = (Integer) row[14];
    	
    	String lastModifiedByUserName = null;
    	
    	if(modifiedById != null){
    		lastModifiedByUserName = getLastModifiedByUserName(modifiedById.toString());
    	}
    	
    	customerInsuredHistory.setLastModifiedByUserName(lastModifiedByUserName);
    	
    	customerList.add(customerInsuredHistory);
	 	
	 }
	 
	return customerList;
}

// ADM 28.04.2009 CR04 Customer Versioning (Release 2.5) 

public String getNationalityDesc(String nationalityCode) throws DataAccessException{
	Session session = null;
	String nationalityDesc = null;
	session = getSession();
    Query query = session.createQuery(QRY_GET_NATIONALITY_DESC_FROM_CODE);
    query.setParameter(NATIONALITY_CODE,new Integer(nationalityCode));
    nationalityDesc = query.uniqueResult().toString();

	return nationalityDesc;
}

// ADM 28.04.2009 CR04 Customer Versioning (Release 2.5) 

public String getCityDesc(String cityCode) throws DataAccessException{
	
	Session session = null;
	String cityDesc = null;

	session = getSession();
    Query query = session.createQuery(QRY_GET_CITY_DESC_FROM_CODE);
    query.setParameter(CITY_CODE,new Integer(cityCode));
    cityDesc = query.uniqueResult().toString();
    
	return cityDesc;
		 
}

// ADM 28.04.2009 CR04 Customer Versioning (Release 2.5) 

public String getCustomerTypeDesc(String customerTypeCode) throws DataAccessException{
	
	Session session = null;
	String customerTypeDesc = null;
	
	session = getSession();
	Query query = session.createQuery(QRY_GET_CUSTOMER_TYPE_DESC_FROM_CODE);
    query.setParameter(CUT_CODE,new Integer(customerTypeCode));
    customerTypeDesc = query.uniqueResult().toString();
    
	return customerTypeDesc;
}

// ADM 28.04.2009 CR04 Customer Versioning (Release 2.5) 

public String getCustomerStatusDesc(String customerStatusCode) throws DataAccessException{
	
	Session session = null;
	String customerStatusDesc = null;
	
	session = getSession();
	Query query = session.createQuery(QRY_GET_CUSTOMER_STATUS_DESC_FROM_CODE);
    query.setParameter(STA_CODE,new Integer(customerStatusCode));
    customerStatusDesc = query.uniqueResult().toString();
    
	return customerStatusDesc;
}

// ADM 28.04.2009 CR04 Customer Versioning (Release 2.5) 

public String getLastModifiedByUserName(String lastModifiedByUserId) throws DataAccessException{
	Session session = null;
	String lastModifiedByUserName = null;
	
	session = getSession();
	
	Query query = session.createQuery(QRY_GET_LAST_MODIFIED_BY_USER_NAME_FROM_ID);
    query.setParameter(USER_ID,new Integer(lastModifiedByUserId));
    lastModifiedByUserName = query.uniqueResult().toString();
    	
	return lastModifiedByUserName; 
}

//ADM 30.09.2009 CR35 Transaction Details : Update Customer id in Insured master
public void updateCustomerId(Long insId, Long custId) throws DataAccessException {
    PolicyQuo policy = null;
    String fullname = null;
    logger.debug("updateCustomerId", "Insured Id ::" + insId + " Customer Id :: "+ custId);

    if (insId != null) {
        Session session = getSession();
        Query query = session.createQuery("update CustomerInsured custIns set custIns.customerId =:custId where custIns.insuredId=:insId");
        System.out.println(query.toString());
        query.setParameter("custId", custId);
        query.setParameter("insId", insId);
      
        query.executeUpdate(); 
    }
    logger.debug("updateCustomerId", "Method End");
}


//Added on 26.11.2009 for CR70 Matching Customer Insured details

public String getBrokerDesc(String brkCode) throws DataAccessException {

	Session session = null;
	String brkdesc = null;

	session = getSession();
	Query query = session.createQuery(QRY_GET_BRK_DESC_FROM_CODE);
	query.setParameter(BR_CODE, new Integer(brkCode));
	brkdesc = query.uniqueResult().toString();

	return brkdesc;

}

public String getNametitleDesc (String nametitlecode) throws DataAccessException{
	Session session = null;
	String nametitledesc= null;
	session = getSession();
	Query query = session.createQuery(QRY_GET_NAMETITLE_DESC_FROM_CODE);
	query.setParameter(NAMETITLE_CODE, new Integer(nametitlecode));
	nametitledesc = query.uniqueResult().toString();
 
	return nametitledesc;
}

public String getEmployeeDesc (String useridcode) throws DataAccessException{
	Session session = null;
	String empdesc=null;
	session = getSession();
	Query query = session.createQuery(QRY_GET_EMP_DESC_FROM_CODE);
	query.setParameter(EMP_CODE, new Integer(useridcode));
	empdesc = query.uniqueResult().toString();
	return empdesc;
	}

public String getExternalexecDesc(String empcode) throws DataAccessException{
	Session session = null;
	String externalexdesc=null;
	session = getSession();
	Query query = session.createQuery(QRY_GET_EXTERNALEXEC_DESC_FROM_CODE);
	query.setParameter(EMP_CODE, new Integer(empcode));
	externalexdesc = query.uniqueResult().toString();
	return externalexdesc;
}
}
