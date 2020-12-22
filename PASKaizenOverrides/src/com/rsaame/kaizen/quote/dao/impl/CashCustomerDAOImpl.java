/*
 * CashCustomerDAOImpl.java
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
package com.rsaame.kaizen.quote.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.rsaame.kaizen.customer.model.CashCustomer;
import com.rsaame.kaizen.framework.constants.AMEConstants;
import com.rsaame.kaizen.framework.dao.exception.DataAccessException;
import com.rsaame.kaizen.framework.dao.impl.AMEBaseDAOImpl;
import com.rsaame.kaizen.framework.logger.AMELogger;
import com.rsaame.kaizen.framework.logger.AMELoggerFactory;
import com.rsaame.kaizen.policy.model.Policy;
import com.rsaame.kaizen.quote.dao.CashCustomerDAO;
import com.rsaame.kaizen.quote.model.CashCustomerQuo;
import com.rsaame.kaizen.quote.model.PolicyQuo;
import com.rsaame.kaizen.renewal.model.RenewalPolicy;

/**
 * 
 * 
 * @version 1.0 Oct 24, 2007
 * @author Cognizant Technology Solutions
 */

public class CashCustomerDAOImpl extends AMEBaseDAOImpl implements
		CashCustomerDAO {

	public static final String CTX_CASHCUSTOMER = "getCashCustomerForPolicy()";

	public static final String CTX_SAVE_CASHCUSTOMER = "saveCashCustomerForQuote()";

	public static final String CTX_SAVE_CASHCUSTOMER_QUO = "getCashCustomerForQuote()";

	public static final String CTX_GET_ARA_INFO_FOR_CUSTOMER = "getArabicInfoForCustomer";
	
	public static final String CTX_CASHCUSTOMER_FOR_ENDT = "getCashCustomerForEndt()";
	
	public static final String CTX_CASHCUSTOMER_COMP_ID  = "getCashCustomerForCompId()";
	
	public static final String  CTX_DEL_CSH_CUST_QUO = "deleteCashCustomerForQuo";
	public static final String  CTX_GET_CUST_NAME =  "getCashCustomerNameForQuote";
	//ADM 15.08.2009 : CR04 Quote versioning start
	public static final String CTX_UPDT_CASH_CUST = "updateExpiryDtForCashCustomerQuo";
	
	public static final String CTX_CASHCUSTOMER_QUO  = "getCashCustomerForQuoWithEndId";
	//ADM 15.08.2009 : CR04 Quote versioning END

	/**
	 * Logger instance
	 */
	private static final AMELogger logger = AMELoggerFactory.getInstance()
			.getLogger(CashCustomerDAOImpl.class);

	/**
	 * Method saveCashCustomerDetails() saves details for existing customers
	 * 
	 * @param cashCustomerQuo
	 * @return cashCustomerQuo
	 * @throws DataAccessException
	 */
	public CashCustomerQuo saveCashCustomerDetails(
			CashCustomerQuo cashCustomerQuo) throws DataAccessException {
		logger.debug(AMEConstants.CTX_SAVE_CASH_CUSTOMER_DETAILS,
				"Method Star_1");
		//  Create the SessionFactory, Session
		//            SessionFactory sessionFactory = hibernateTemplate
		//                    .getSessionFactory();
		//            Session session = getSession();
		//ADM 15.08.2009 : CR04 Quote versioning 
		try{
			this.hibernateTemplate.save( cashCustomerQuo );
			this.hibernateTemplate.flush();
		}
		catch( org.springframework.dao.DataIntegrityViolationException e ){
			System.out.println( "CashCustomerQuo SAVE failed. Trying to update..." );
			this.hibernateTemplate.update( cashCustomerQuo );
			this.hibernateTemplate.flush();
			System.out.println( "CashCustomerQuo UPDATE done. Trying to update..." );
		}
		/*this.hibernateTemplate.merge(cashCustomerQuo);
		this.hibernateTemplate.flush();
		this.hibernateTemplate.evict(cashCustomerQuo);*/
		//            
		//            closeSession(session);
		logger.debug(AMEConstants.CTX_SAVE_DETAILS, "End Metho_1");
		return cashCustomerQuo;
	}

	/**
	 * Method saveCashCustomerDetails() saves details for existing customers for
	 * policy
	 * 
	 * @param cashCustomer
	 * @return cashCustomer
	 * @throws DataAccessException
	 */
	public CashCustomer saveCashCustomerDetails(CashCustomer cashCustomer)
			throws DataAccessException {
		logger.debug(AMEConstants.CTX_SAVE_CASH_CUSTOMER_DETAILS,
				"Method Star_2");

		try {
			this.hibernateTemplate.merge(cashCustomer);
		} catch (org.springframework.dao.DataAccessException dataAccessException) {

			throw new DataAccessException(dataAccessException);
		}

		logger.debug(AMEConstants.CTX_SAVE_CASH_CUSTOMER_DETAILS, "End Metho_2");
		return cashCustomer;
	}

	//RENEWALS

	/**
	 * This method will select the customer from the existing policy to a
	 * quoteId.
	 * 
	 * @param policy
	 *            <code>Policy</code>
	 * @return <code>CashCustomer</code>[]
	 * @throws <code>DataAccessException</code>
	 */
	public CashCustomer[] getCashCustomerForPolicy(Policy policy)
			throws DataAccessException {

		CashCustomer[] cash = null;
		Session session = null;
		Query query = null;
		List listCashCustomer = null;

		logger.debug(CTX_CASHCUSTOMER, "Entered with :_1" + policy);

		try {
			session = getSession();
			query = session.createQuery(QRY_POLICY_CASHCUSTOMER);
			query.setParameter(POLICYID, policy.getComp_id().getPolicyId());
			query.setParameter(VALIDITY_START_DATE, policy
					.getValidityStartDate());
			query.setParameter(VALIDITY_EXPIRY_DATE, policy
					.getValidityStartDate());
			listCashCustomer = query.list();
			logger.debug(CTX_CASHCUSTOMER, "Customer list ::"
					+ listCashCustomer);

			if (listCashCustomer != null && !listCashCustomer.isEmpty()) {
				cash = (CashCustomer[]) listCashCustomer
						.toArray(new CashCustomer[0]);
			}
		} catch (HibernateException hibernateException) {
			logger.debug(CTX_CASHCUSTOMER, hibernateException.getMessage());
			throw new DataAccessException(hibernateException);
		} finally {
			closeSession(session);
		}

		logger.debug(CTX_CASHCUSTOMER, "Method Exited :_1");
		return cash;

	}

	/**
	 * This method will save the customer information in CashCustomer Quotation.
	 * 
	 * @param policy
	 *            <code>Policy</code>
	 */
	public void saveCashCustomerForQuote(CashCustomerQuo[] quoteCashCustomer)
			throws DataAccessException {
		Session session = null;
		logger.debug(CTX_SAVE_CASHCUSTOMER, "Enteered in save cash customer");
		if (quoteCashCustomer != null && quoteCashCustomer.length >= 0) {
			try {
				//Added toString() on an array for sonar violation fix on 22-9-2017
				logger.debug(CTX_SAVE_CASHCUSTOMER, "Entered with :_2"
						+ ArrayUtils.toString(quoteCashCustomer));
				session = getSession();
				for (int i = 0; i < quoteCashCustomer.length; i++) {
					logger.debug(CTX_SAVE_CASHCUSTOMER, "Saving with ::"
							+ quoteCashCustomer[i]);
					this.hibernateTemplate.saveOrUpdate(quoteCashCustomer[i]);
				}
			} catch (HibernateException hibernateException) {
				throw new DataAccessException(hibernateException);
			} catch (org.springframework.dao.DataAccessException dataAccessException) {
				throw new DataAccessException(dataAccessException);
			} finally {
				closeSession(session);
			}
		}

	}

	/**
	 * This method will select the customer from the existing quoteId to a
	 * object.
	 * 
	 * @param policyQuo
	 *            <code>PolicyQuo</code>
	 * @return <code>CashCustomerQuo</code>[]
	 * @throws <code>DataAccessException</code>
	 */
	public CashCustomerQuo[] getCashCustomerForQuote(PolicyQuo policyQuo)
			throws DataAccessException {

		CashCustomerQuo[] cashQuo = null;
		Session session = null;
		Query query = null;
		List listCashCustomerQuo = null;

		logger.debug(CTX_SAVE_CASHCUSTOMER_QUO, "Entered with :_3" + policyQuo);

		try {
			session = getSession();
			query = session.createQuery(QRY_CASHCUSTOMER_QUO);
			query.setParameter(POLICYID, policyQuo.getComp_id().getPolicyId());			
			//11.09.2009 : CR04 Quote Versioning : Added the endtId to fetch the latest record for mail name
			query.setParameter(ENDORSEMENTID, policyQuo.getComp_id().getEndtId());
			listCashCustomerQuo = query.list();
			logger.debug(CTX_SAVE_CASHCUSTOMER_QUO, "Customer list ::"
					+ listCashCustomerQuo);

			if (listCashCustomerQuo != null && listCashCustomerQuo.size() > 0)
				cashQuo = (CashCustomerQuo[]) listCashCustomerQuo
						.toArray(new CashCustomerQuo[0]);
		} catch (HibernateException hibernateException) {
			logger.error(CTX_SAVE_CASHCUSTOMER_QUO, hibernateException
					.getMessage());
			throw new DataAccessException(hibernateException);
		} finally {

			closeSession(session);
		}
		return cashQuo;

	}

	/**
	 * This method will save the Arabic Information for Customer
	 * 
	 * @param cashCustomer
	 *            <code>CashCustomer</code>
	 * @throws <code>DataAccessException</code>
	 */
	public CashCustomer getArabicInfoForCustomer(Policy policy)
			throws DataAccessException {
		logger.debug(CTX_GET_ARA_INFO_FOR_CUSTOMER, "Method Entered :");

		Query finalQuery = null;
		CashCustomer cashCustomer = null;
		Session session = null;
		try {
		    session = getSession();
			finalQuery = session.createQuery(QRY_GET_ARABIC_INFO_FOR_CUSTOMER);
			
			logger.debug(CTX_GET_ARA_INFO_FOR_CUSTOMER, "Query :"+ finalQuery.getQueryString());
			finalQuery.setParameter(POLICYID, policy.getComp_id().getPolicyId());
			finalQuery.setParameter(VALIDITY_START_DATE, policy.getValidityStartDate());
			finalQuery.setParameter(CSH_INSURED_ID, policy.getInsuredId());
			cashCustomer = (CashCustomer) finalQuery.uniqueResult();
			logger.debug(CTX_GET_ARA_INFO_FOR_CUSTOMER, "Customer ::"+ cashCustomer);
			
		} catch (HibernateException hibernateException) {
			logger.debug(CTX_GET_ARA_INFO_FOR_CUSTOMER, hibernateException
					.getMessage());
			throw new DataAccessException(hibernateException);
		} finally {
			closeSession(session);
		}
		logger.debug(CTX_CASHCUSTOMER, "Method Exited with ::"+cashCustomer);
		return cashCustomer;
	}
	
	/**
	 * This method will select the customer from the existing policy for a
	 * endtId.
	 * 
	 * @param policy
	 *            <code>Policy</code>
	 * @return <code>CashCustomer</code>
	 * @throws <code>DataAccessException</code>
	 */
	public CashCustomer getCashCustomerForCompId(Policy policy)
			throws DataAccessException {

		CashCustomer cash = null;
		Session session = null;
		Query query = null;
		List listCashCustomer = null;

		logger.debug(CTX_CASHCUSTOMER_COMP_ID, "Entered with :_4" + policy);

		try {
			session = getSession();
			query = session.createQuery(QRY_CASHCUSTOMER_FOR_ENDT);
			query.setParameter(POLICYID, policy.getComp_id().getPolicyId());
			query.setParameter(VALIDITY_START_DATE, policy.getValidityStartDate());
			query.setParameter(INSUREDID,policy.getInsuredId());
			cash = (CashCustomer)query.uniqueResult();
			logger.debug(CTX_CASHCUSTOMER_COMP_ID, "Customer  ::"+ cash);
		} catch (HibernateException hibernateException) {
			logger.debug(CTX_CASHCUSTOMER_COMP_ID, hibernateException.getMessage());
			throw new DataAccessException(hibernateException);
		} finally {
			closeSession(session);
		}

		logger.debug(CTX_CASHCUSTOMER_COMP_ID, "Method Exited :_2");
		return cash;

	}
	
	
	public void deleteCashCustomerForQuo(RenewalPolicy renewalPolicy) throws DataAccessException{
	    logger.debug(CTX_DEL_CSH_CUST_QUO, "Entered in delete renewal quote with::" + renewalPolicy.getRefPolicyId());
        Session session = null;
        try {
            session = getSession();
            Query query = session.createQuery(QRY_DEL_CASH_CUSTOMER_FOR_QUO);
            query.setParameter(POLICYID, renewalPolicy.getRefPolicyId());
            query.executeUpdate();
            logger.debug(CTX_DEL_CSH_CUST_QUO, "Exiting:_1");
        } catch (HibernateException hibernateException) {
            throw new DataAccessException(hibernateException);
        } finally {
            closeSession(session);
        }
	}
	
	/**
     * 
     * @param quotationNo
     * @return customerName
     * @throws DataAccessException
     */
	public String getCashCustomerNameForQuote(Long renQuoteNo) throws DataAccessException{
	    logger.debug(CTX_GET_CUST_NAME,"Entered getCashCustName::"+renQuoteNo);
	    String customerName = null;
	    Session session = null;
	    try {
            session = getSession();
            Query query = session.createQuery(QRY_GET_CUSTOMER_NAME_FOR_QUOTE);
            query.setParameter(QUOTATION_NO, renQuoteNo);
            List nameList = query.list();
            if(nameList != null && !nameList.isEmpty()){
                customerName = (String)nameList.get(0);
                logger.debug(CTX_GET_CUST_NAME,"Customer name is::"+customerName);
            }
            logger.debug(CTX_GET_CUST_NAME, "Exiting:_2");
        } catch (HibernateException hibernateException) {
            throw new DataAccessException(hibernateException);
        } finally {
            closeSession(session);
        }
	    return customerName;
	}

	//ADM 25.08.2009 CR35 Transaction details (Release 2.5)

	public void updateCashCustomerDetails(PolicyQuo policyQuo) throws DataAccessException {
		logger.debug("updateCashCustomerDetails", "Method Entered");
		System.out.println(policyQuo.getComp_id().getPolicyId()+"   Policy Id ");
		System.out.println(policyQuo.getValidityStartDate()+"   validity Start Date");
        Session session = null;
        try {	        	
			 session = getSession();
			 Query hibernateQuery = null;
	            hibernateQuery = session.createQuery("update CashCustomerQuo cashCustomerQuo set cashCustomerQuo.customerId =:custId "
	        							+ "where cashCustomerQuo.comp_id.policyId =:polId and cashCustomerQuo.comp_id.validityStartDate=:valStartDate");
	            hibernateQuery.setParameter("custId", policyQuo.getCustomerId());
	            hibernateQuery.setParameter("polId", policyQuo.getComp_id().getPolicyId());
	            hibernateQuery.setParameter("valStartDate", policyQuo.getValidityStartDate());
	            	            
	            hibernateQuery.executeUpdate();            

        } catch (HibernateException hibernateException) {
            throw new DataAccessException(hibernateException);
        }

        logger.debug("updateCashCustomerDetails", "Method Exited");
	}
	
    /**
	 * This method will select the customer from the existing Quote for a endtId.
	 * @param policyQuo <code>PolicyQuo</code>
	 * @return <code>CashCustomerQuo</code>
	 * @throws <code>DataAccessException</code>
	 * 01.08.2009 : CR04 Quote Versioning : Get the CashCustomerQuo with the endtId also
	 */
	public CashCustomerQuo getCashCustomerForQuoWithEndId(PolicyQuo policyQuo)
			throws DataAccessException {

		CashCustomerQuo cash = null;
		Session session = null;
		Query query = null;
		logger.debug(CTX_CASHCUSTOMER_QUO, "Entered with :_5" + policyQuo);

		try {
			session = getSession();
			query = session.createQuery(QRY_CASHCUSTOMER_FOR_QUO);
			query.setParameter(POLICYID, policyQuo.getComp_id().getPolicyId());
			logger.debug(CTX_CASHCUSTOMER_QUO, "policyQuo.getComp_id().getPolicyId()::" + policyQuo.getComp_id().getPolicyId());
			logger.debug(CTX_CASHCUSTOMER_QUO, "policyQuo.getInsuredId()::" + policyQuo.getInsuredId());
			logger.debug(CTX_CASHCUSTOMER_QUO, "policyQuo.getComp_id().getEndtId()::" + policyQuo.getComp_id().getEndtId());
			
			//query.setParameter(VALIDITY_START_DATE, policyQuo.getValidityStartDate());
			//query.setParameter(INSUREDID,policyQuo.getInsuredId());
			query.setParameter(ENDORSEMENTID,policyQuo.getComp_id().getEndtId());
			cash = (CashCustomerQuo)query.uniqueResult();
			logger.debug(CTX_CASHCUSTOMER_QUO, "Customer  ::"+ cash);
		} catch (HibernateException hibernateException) {
			logger.debug(CTX_CASHCUSTOMER_QUO, hibernateException.getMessage());
			throw new DataAccessException(hibernateException);
		} 
		logger.debug(CTX_CASHCUSTOMER_QUO, "Method Exited :_3");
		return cash;
	}
	
	/**
	 * This method will update the old cashcustomer details with expiry date
	 * @param policyQuo <code>PolicyQuo</code>
	 * @return <code>CashCustomerQuo</code>
	 * @throws <code>DataAccessException</code>
	 * 01.08.2009 : CR04 Quote Versioning : 
	 */
	public void updateExpiryDtForCashCustomerQuo(PolicyQuo policyQuo) throws DataAccessException {
        logger.debug(CTX_UPDT_CASH_CUST, "Entered in policy quote with::"
                + policyQuo.getComp_id().getPolicyId());
        Session session = null;
        try {
            session = getSession();
            Query query = session.createQuery(QRY_UPDT_OLD_CASH_CUST);
            //ADM 27.10.2009 : UAT Issue fix : Previous version record's expiry dt = current version's valstartdt  
            query.setParameter("date", policyQuo.getValidityStartDate());
            query.setParameter(POLICYID, policyQuo.getComp_id().getPolicyId());
            query.setParameter(VALIDITY_EXPIRY_DATE, new Date("12/31/2049"));
            query.setParameter(ENDORSEMENTID, policyQuo.getComp_id().getEndtId());

            logger.debug(CTX_UPDT_CASH_CUST, "Query:: " + query.toString());
            query.executeUpdate();
            logger.debug(CTX_UPDT_CASH_CUST, "Exiting:_3");
        } catch (HibernateException hibernateException) {
            throw new DataAccessException(hibernateException);
        } 
    }
	
	//ADM 28.08.2009: CR04 Quote Versioning
	public void insertCashCustomerDetails(CashCustomerQuo cashCustomerQuo)
				throws DataAccessException {
		logger.debug("insertCashCustomerDetails",
				"Method Star_3");
		
		try {
			this.hibernateTemplate.save(cashCustomerQuo);
		} catch (org.springframework.dao.DataAccessException dataAccessException) {
		
			throw new DataAccessException(dataAccessException);
		}
		
		logger.debug(AMEConstants.CTX_SAVE_CASH_CUSTOMER_DETAILS, "End Metho_3");
		
		}
}

