/*
 * @(#)ReportsDAOImpl.java 2007/Nov/10
 * 
 * Copyright (c) 2007-2012 Royal and Sun Alliance Insurance Group plc. St.Mark’s
 * Court, Chart Way, Horsham, West Sussex RH12 1XL, U.K. All rights reserved.
 * 
 * This software is the confidential and proprietary information of Royal and
 * Sun Alliance Insurance Group plc.("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in accordance
 * with the terms of the license agreement you entered into with Royal and Sun
 * Alliance Insurance Group plc.
 */
package com.rsaame.kaizen.reports.dao.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.rsaame.kaizen.admin.model.Document;
import com.rsaame.kaizen.admin.model.Location;
import com.rsaame.kaizen.admin.model.LocationPK;
import com.rsaame.kaizen.customer.model.CustomerInsured;
import com.rsaame.kaizen.customer.model.CustomerInsuredHistory;
import com.rsaame.kaizen.customer.model.CustomerInsuredHistoryPK;
import com.rsaame.kaizen.customer.model.DistributionChannel;
import com.rsaame.kaizen.customer.model.PolicyDocument;
import com.rsaame.kaizen.framework.constants.AMEConstants;
import com.rsaame.kaizen.framework.dao.exception.DataAccessException;
import com.rsaame.kaizen.framework.dao.impl.AMEBaseDAOImpl;
import com.rsaame.kaizen.framework.logger.AMELogger;
import com.rsaame.kaizen.framework.logger.AMELoggerFactory;
import com.rsaame.kaizen.policy.model.CreditNoteDetails;
import com.rsaame.kaizen.policy.model.DebitNoteDetails;
import com.rsaame.kaizen.policy.model.DetailReceipt;
import com.rsaame.kaizen.policy.model.Policy;
import com.rsaame.kaizen.policy.model.PolicyClass;
import com.rsaame.kaizen.policy.model.PolicyCondition;
import com.rsaame.kaizen.policy.model.PolicyPK;
import com.rsaame.kaizen.policy.model.PolicyType;
import com.rsaame.kaizen.policy.model.RenewalStatus;
import com.rsaame.kaizen.policy.model.Vehicle;
import com.rsaame.kaizen.quote.model.PolicyPKQuo;
import com.rsaame.kaizen.quote.model.PolicyQuo;
import com.rsaame.kaizen.reports.dao.ReportsDAO;
import com.rsaame.kaizen.framework.model.ApplicationContext;
import com.rsaame.kaizen.framework.model.RSAUser;
import com.rsaame.kaizen.framework.model.ServiceContext;

public class ReportsDAOImpl extends AMEBaseDAOImpl implements ReportsDAO {

	/**
	 * static final AMELogger logger
	 */
	private static final AMELogger logger = AMELoggerFactory.getInstance()
			.getLogger(ReportsDAOImpl.class);

	private static final String CTX_REPORTS_CLASSES = "getClassesofBusiness()";

	private static final String CTX_REPORTS_POLICIES = "getPolicyTypes()";

	private static final String CTX_REPORTS_LOCATIONS = "getLocations()";

	private static final String CTX_CREDIT_NOTE = "getCreditNote()";

	private static final String CTX_DEBIT_NOTE = "getDebitNoteDetails()";

	private static final String CTX_VEHICLE_DETAILS = "getVehicleDetails()";

	private static final String CTX_DOC_DETAILS = "getDocumentDetails()";

	private static final String CTX_POLICY_DETAILS = "getPolicyEndtDetails()";

	private static final String CTX_POLICY_TERMS_DETAILS = "getPolicyEndtDetailsforTerms()";

	private static final String CTX_RECEIPT_DETAILS = "getReeiptDetails()";

	private static final String CTX_PROPOSAL_FORM_DETAILS = "getProposalFormDetails()";

	private static final String CTX_NCD_DETAILS = "getNCDLetterDetails()";

	private Session session;

	private Integer CustomerInsured;

	/**
	 * This method returns class and description
	 * 
	 * @param PolicyClass
	 * @return PolicyClass[]
	 */
	public PolicyClass[] getClassesofBusiness(PolicyClass policyClass)
			throws DataAccessException {
		logger.debug(CTX_REPORTS_CLASSES, "Enter  getClassesofBusiness .. ");

		List classList = null;
		PolicyClass[] policyClassList = null;
		try {
			session = getSession();
			logger.debug(CTX_REPORTS_CLASSES, "session :_1" + session);
			Query query = session
					.createQuery("FROM PolicyClass policyClass order by policyClass.code");

			logger.debug(CTX_REPORTS_CLASSES,
					"Query :_1" + query.getQueryString());
			classList = query.list();
		} catch (HibernateException hibernateException) {
			logger.error(CTX_REPORTS_CLASSES, hibernateException.getMessage());
			throw new DataAccessException(hibernateException);
		} catch (Exception hibernateException) {
			logger.error(CTX_REPORTS_CLASSES, hibernateException.getMessage());
			throw new DataAccessException(hibernateException);
		}

		finally {

			closeSession(session);
		}

		if (classList != null && classList.size() > 0) {
			logger.debug(CTX_REPORTS_CLASSES, "Number of records found ::"
					+ classList.size());
			policyClassList = new PolicyClass[classList.size()];
			classList.toArray(policyClassList);

			PolicyClass policy = null;
			Iterator itr = classList.iterator();
			if (itr.hasNext()) {
				policy = (PolicyClass) itr.next();
				logger.debug(CTX_REPORTS_CLASSES,
						"policy.getCode ::" + policy.getCode());
				logger.debug(CTX_REPORTS_CLASSES, "policy.getEngDesc ::"
						+ policy.getEngDesc());
			}

		}

		logger.debug(CTX_REPORTS_CLASSES, "Exit  getClassesofBusiness .. ");
		return policyClassList;
	}

	/**
	 * This method returns the policy types
	 * 
	 * @param policyClassCode
	 * @return PolicyType[]
	 * 
	 */
	public PolicyType[] getPolicyTypes(String policyClassCode)
			throws DataAccessException {
		logger.debug(CTX_REPORTS_POLICIES, "Enter  getPolicyTypes .. ");
		PolicyType[] PolicyTypeList = null;
		List classList = null;

		try {
			session = getSession();
			logger.debug(CTX_REPORTS_POLICIES, "session :_2" + session);

			Query query = session
					.createQuery("FROM PolicyType policyType where policyType.comp_id.policyClass.code in ("
							+ policyClassCode
							+ ") order by policyType.comp_id.code ");
			// query.setText("CL_CODE",policyClassCode);
			// query.setInteger("VMA_CODE", vehicleMakeCode);

			logger.debug(CTX_REPORTS_POLICIES,
					"Query :_2" + query.getQueryString());
			classList = query.list();
		} catch (HibernateException hibernateException) {
			logger.error(CTX_REPORTS_POLICIES, hibernateException.getMessage());
			throw new DataAccessException(hibernateException);
		}

		finally {

			closeSession(session);

		}

		if (classList != null && classList.size() > 0) {
			logger.debug(
					CTX_REPORTS_POLICIES,
					"Number of records found in policy type :_1"
							+ classList.size());
			PolicyTypeList = new PolicyType[classList.size()];

			classList.toArray(PolicyTypeList);

		}

		logger.debug(CTX_REPORTS_POLICIES, "Exit  getPolicyTypes .. ");
		return PolicyTypeList;
	}

	/**
	 * This method returns list with desc and code of locations
	 * 
	 * @param Location
	 * @return Location[]
	 */
	public Location[] getLocations(Location premiumRegInq)
			throws DataAccessException {
		logger.debug(CTX_REPORTS_LOCATIONS, "Enter  getLocations .. ");
		List classList = null;
		// Kaizen ADM - Access to other location Data CR-65 Change
		try {
			session = getSession();
			logger.debug(CTX_REPORTS_LOCATIONS, "session :_3" + session);
			// CR - 65
			int regionCode = premiumRegInq.getComp_id().getRegion()
					.getComp_id().getCode().intValue();
			int cityCode = premiumRegInq.getComp_id().getRegion().getComp_id()
					.getCountry().intValue();
			RSAUser user = ServiceContext.getUser();
			Integer userId = user.getUserId();
			String locationQuery = "SELECT LOC_CODE,LOC_E_DESC FROM V_MAS_LOCATION_USER_PAS WHERE LOC_CTY_CODE ="
					+ cityCode
					+ " AND LOC_REG_CODE = "
					+ regionCode
					+ " and USER_ID = " + userId + " ";
			Query query = session.createSQLQuery(locationQuery);

			// Query query = session
			// .createQuery("FROM Location location where location.comp_id.region.comp_id.code = :REG_CODE and location.comp_id.region.comp_id.country = :CTY_CODE order by location.comp_id.locCode");
			// // query.setInteger("CL_CODE", premiumRegInq);
			// query.setInteger("REG_CODE",
			// premiumRegInq.getComp_id().getRegion()
			// .getComp_id().getCode().intValue());
			// query.setInteger("CTY_CODE",
			// premiumRegInq.getComp_id().getRegion()
			// .getComp_id().getCountry().intValue());
			logger.debug(CTX_REPORTS_LOCATIONS,
					"Query :_3" + query.getQueryString());
			classList = query.list();
		} catch (HibernateException hibernateException) {
			logger.error(CTX_REPORTS_LOCATIONS, hibernateException.getMessage());
			throw new DataAccessException(hibernateException);
		}

		finally {

			closeSession(session);
		}
		Location[] LocationList = new Location[classList.size()];
		if (classList != null && classList.size() > 0) {
			logger.debug(
					CTX_REPORTS_LOCATIONS,
					"Number of records found in policy type :_2"
							+ classList.size());

			Iterator itr = classList.iterator();
			Object[] row = null;
			int i = 0;
			while (itr.hasNext()) {

				LocationPK locationPK = new LocationPK();

				row = (Object[]) itr.next();
				java.math.BigDecimal LocCode = (java.math.BigDecimal) row[0];
				//Added Integer.valueOf() to avoid sonar violation on 18-9-2017
				//Integer location = new Integer(LocCode.intValue());
				Integer location =Integer.valueOf(LocCode.intValue());
				locationPK.setLocCode(location);
				String locationDesc = (String) row[1];
				LocationList[i] = new Location();
				LocationList[i].setEngDesc(locationDesc);
				LocationList[i].setComp_id(locationPK);
				i++;
			}
			// LocationList = new Location[classList.size()];
			// classList.toArray(LocationList);
		}
		// Kaizen ADM - Access to other location Data CR-65 Change
		logger.debug(CTX_REPORTS_LOCATIONS, "Exit  getLocations .. ");
		return LocationList;
	}

	/**
	 * This method returns the credit note number and date
	 * 
	 * @param CreditNoteDetails
	 * @return CreditNoteDetails
	 * @throws DataAccessException
	 */
	public CreditNoteDetails getCreditNote(CreditNoteDetails creditNoteDtls)
			throws DataAccessException {
		logger.debug(CTX_CREDIT_NOTE, "Enter  getCreditNote .. ");

		CreditNoteDetails creditNote = null;
		List creditNoteList = null;

		try {
			session = getSession();
			/**
			 * E_PLATFORM Logic starts here Query is changed for E_PLATFORM to
			 * get the debit note number for a policy created in PAS SBS Kaizen
			 * query was --> Query query = session.createQuery(
			 * "FROM  CreditNoteDetails dtlCreditNote where dtlCreditNote.cndPolicyId = :POL_ID and dtlCreditNote.cndEndtId = :ENDT_ID"
			 * ); query.setLong("POL_ID",
			 * creditNoteDtls.getCndPolicyId().longValue())
			 */
			Query query = session
					.createQuery("FROM  CreditNoteDetails dtlCreditNote where dtlCreditNote.cndPolicyNo = :POL_NO and dtlCreditNote.cndEndtId = :ENDT_ID and dtlCreditNote.cndPolicyYear = :POLICY_YEAR and dtlCreditNote.cndPolicyId = :POLICY_ID");
			// query.setInteger("POL_ID",creditNoteDtls.getCndPolicyId().intValue());
			// query.setInteger("ENDT_ID",creditNoteDtls.getCndEndtId().intValue());
			query.setLong("POL_NO", creditNoteDtls.getCndPolicyNo().longValue());
			/**
			 * E_PLATFORM Logic ends here
			 */
			query.setLong("ENDT_ID", creditNoteDtls.getCndEndtId().longValue());
			query.setLong("POLICY_YEAR", creditNoteDtls.getCndPolicyYear()
					.intValue());
			query.setParameter("POLICY_ID", creditNoteDtls.getCndPolicyId());

			logger.debug(CTX_CREDIT_NOTE, "Query :_4" + query.getQueryString());
			creditNoteList = query.list();

		} catch (HibernateException hibernateException) {
			hibernateException.printStackTrace();
			logger.error(CTX_CREDIT_NOTE, hibernateException.getMessage());
			throw new DataAccessException(hibernateException);
		} catch (Exception hibernateException) {
			hibernateException.printStackTrace();
			logger.error(CTX_CREDIT_NOTE, hibernateException.getMessage());
			throw new DataAccessException(hibernateException);
		}

		finally {

			closeSession(session);

		}

		if (creditNoteList != null && creditNoteList.size() > 0) {
			logger.debug(CTX_CREDIT_NOTE,
					"Number of records found in policy type :_3"
							+ creditNoteList.size());

			creditNote = (CreditNoteDetails) creditNoteList.get(0);

			logger.debug(CTX_CREDIT_NOTE, "creditNote : " + creditNote);
			logger.debug(CTX_CREDIT_NOTE, "creditNote.getComp_id :"
					+ creditNote.getComp_id());
			logger.debug(CTX_CREDIT_NOTE, "creditNote date : "
					+ creditNote.getComp_id().getCndCreditNoteDate());
			logger.debug(CTX_CREDIT_NOTE, "creditNote num : "
					+ creditNote.getComp_id().getCndCreditNoteNo());

		}

		logger.debug(CTX_CREDIT_NOTE, "Exit  getCreditNote .. ");
		return creditNote;
	}

	/**
	 * This method returns the debit note number and date
	 * 
	 * @param DebitNoteDetails
	 * @return DebitNoteDetails
	 * @throws DataAccessException
	 */
	public DebitNoteDetails getDebitNote(DebitNoteDetails debitNoteDtls)
			throws DataAccessException {
		logger.debug(CTX_DEBIT_NOTE, "Enter  getDebitNote .. ");

		DebitNoteDetails debitNote = null;
		List debitNoteList = null;
		try {
			session = getSession();
			/**
			 * E_PLATFORM Logic starts here Query is changed for E_PLATFORM to
			 * get the debit note number for a policy created in PAS SBS Kaizen
			 * query was --> Query query = session.createQuery(
			 * "FROM  DebitNoteDetails dtlDebitNote where dtlDebitNote.dndPolicyId = :POL_ID and dtlDebitNote.dndEndtId = :ENDT_ID"
			 * ); query.setLong("POL_ID",
			 * debitNoteDtls.getDndPolicyId().longValue());
			 */
			Query query = session
					.createQuery("FROM  DebitNoteDetails dtlDebitNote where dtlDebitNote.dndPolicyNo = :POL_NO and dtlDebitNote.dndEndtId = :ENDT_ID and dtlDebitNote.dndPolicyYear = :DND_POL_YEAR and dtlDebitNote.dndPolicyId = :POLICY_ID");
			// query.setInteger("POL_ID",debitNoteDtls.getDndPolicyId().intValue());
			// query.setInteger("ENDT_ID",debitNoteDtls.getDndEndtId().intValue());
			query.setLong("POL_NO", debitNoteDtls.getDndPolicyNo().longValue());
			/*
			 * E_PLATFORM Logic ends here
			 */
			query.setLong("ENDT_ID", debitNoteDtls.getDndEndtId().longValue());
			query.setInteger("DND_POL_YEAR", debitNoteDtls.getDndPolicyYear()
					.intValue());
			query.setParameter("POLICY_ID", debitNoteDtls.getDndPolicyId());
			logger.debug(CTX_DEBIT_NOTE, "Query :_5" + query.getQueryString());
			debitNoteList = query.list();
		} catch (HibernateException hibernateException) {
			hibernateException.printStackTrace();
			logger.error(CTX_DEBIT_NOTE, hibernateException.getMessage());
			throw new DataAccessException(hibernateException);
		} catch (Exception hibernateException) {
			hibernateException.printStackTrace();
			logger.error(CTX_DEBIT_NOTE, hibernateException.getMessage());
			throw new DataAccessException(hibernateException);
		} finally {
			closeSession(session);
		}

		if (debitNoteList != null && debitNoteList.size() > 0) {
			logger.debug(
					CTX_DEBIT_NOTE,
					"Number of records found in policy type :_4"
							+ debitNoteList.size());

			debitNote = (DebitNoteDetails) debitNoteList.get(0);

			logger.debug(CTX_DEBIT_NOTE, "debitNote : " + debitNote);
			logger.debug(CTX_DEBIT_NOTE,
					"debitNote.getComp_id :" + debitNote.getComp_id());
			logger.debug(CTX_DEBIT_NOTE, "debitNote date : "
					+ debitNote.getComp_id().getDndDebitNoteDate());
			logger.debug(CTX_DEBIT_NOTE, "debitNote num : "
					+ debitNote.getComp_id().getDndDebitNoteNo());
		}

		logger.debug(CTX_DEBIT_NOTE, "Exit  getDebitNote .. ");
		return debitNote;
	}

	/**
	 * This method retrieves the vehicle details
	 * 
	 * @param Policy
	 * @return Vehicle
	 * @throws DataAccessException
	 */
	public Vehicle getVehicleDetails(Policy policy) throws DataAccessException {
		logger.debug(CTX_VEHICLE_DETAILS, "Entered  getVehicleDetails .. ");
		Vehicle vehicleDetails = null;
		List vehicleList = null;

		try {
			session = getSession();

			logger.debug(CTX_VEHICLE_DETAILS, "policy Id : "
					+ policy.getComp_id().getPolicyId().longValue());
			logger.debug(CTX_VEHICLE_DETAILS,
					"validity start date : " + policy.getValidityStartDate());

			Query query = session
					.createQuery("FROM  Vehicle veh where veh.comp_id.policyId = :POL_ID and veh.comp_id.validityStartDate<= :VALIDITY_START_DATE and veh.validityExpiryDate>:VALIDITY_EXP_DATE");

			// Query query = session
			// .createQuery("FROM Vehicle veh where veh.comp_id.policyId =
			// :POL_ID");
			// Query query = session
			// .createQuery("FROM Vehicle veh where veh.comp_id.policyId =
			// 543");

			query.setLong("POL_ID", policy.getComp_id().getPolicyId()
					.longValue());
			query.setDate("VALIDITY_START_DATE", policy.getValidityStartDate());
			query.setDate("VALIDITY_EXP_DATE", policy.getValidityStartDate());

			logger.debug(CTX_VEHICLE_DETAILS,
					"Query :_6" + query.getQueryString());
			vehicleList = query.list();
		} catch (HibernateException hibernateException) {
			hibernateException.printStackTrace();
			logger.error(CTX_VEHICLE_DETAILS, hibernateException.getMessage());
			throw new DataAccessException(hibernateException);
		} catch (Exception hibernateException) {
			hibernateException.printStackTrace();
			logger.error(CTX_VEHICLE_DETAILS, hibernateException.getMessage());
			throw new DataAccessException(hibernateException);
		}

		finally {
			closeSession(session);
		}

		if (vehicleList != null && !vehicleList.isEmpty()) {
			logger.debug(CTX_VEHICLE_DETAILS, "Vehicle list size.. "
					+ vehicleList.size());
			vehicleDetails = (Vehicle) vehicleList.get(0);
			vehicleDetails.setVehicleModel(null);
			// logger.debug(CTX_VEHICLE_DETAILS,
			// vehicleDetails.getVehicleModel().getComp_id().getVehicleMake);

		}

		logger.debug(CTX_VEHICLE_DETAILS, "Exit  getVehicleDetails .. ");
		return vehicleDetails;
	}

	/**
	 * This method is to get Policy Condition Details
	 * 
	 * @param policyClassCode
	 *            <code>java.lang.Integer</code>
	 * @param policyTypeCode
	 *            <code>java.lang.Integer</code>
	 * @param code
	 *            <code>java.lang.Integer</code>
	 * @return policyCondition <code>PolicyCondition</code>
	 * @throws DataAccessException
	 */
	public PolicyCondition getDocumentDetails(Integer policyClassCode,
			Integer policyTypeCode, Integer code) throws DataAccessException {
		logger.debug(CTX_DOC_DETAILS, "Entering getDocumentDetails .. ");

		PolicyCondition policyCondition = null;

		try {
			// select distinct
			// nvl(t.PC_DOC_TEMPLATE_NAME,'NA'),nvl(t.PC_DOCUMENT_FLAG,0) from
			// t_mas_policy_condition t where t.Pc_cl_code= 1 and pc_code =1 AND
			// t.PC_DOCUMENT_FLAG <>5
			// TODO: Use the below query
			// String queryStr = "Select distinct nvl(,'NA'), nvl(,0) FROM
			// PolicyCondition policyCondition WHERE
			// policyCondition.comp_id.policyClassCode=:policyClassCode AND
			// policyCondition.comp_id.policyTypeCode=:policyTypeCode AND
			// policyCondition.comp_id.code=:code";
			// TODO: commented out policy type for testing purpose
			String queryStr = "FROM PolicyCondition policyCondition WHERE policyCondition.comp_id.policyClassCode=:policyClassCode AND policyCondition.comp_id.policyTypeCode=:policyTypeCode AND policyCondition.comp_id.code=:code";
			// String queryStr = "FROM PolicyCondition policyCondition WHERE
			// policyCondition.comp_id.policyClassCode=:policyClassCode AND
			// policyCondition.comp_id.code=:code";
			session = getSession();
			logger.debug(CTX_DOC_DETAILS, "session " + session);

			Query query = session.createQuery(queryStr);
			query.setInteger("policyClassCode", policyClassCode.intValue());
			query.setInteger("policyTypeCode", policyTypeCode.intValue());
			query.setInteger("code", code.intValue());

			policyCondition = (PolicyCondition) query.uniqueResult();

		} catch (HibernateException e) {
			throw new DataAccessException(e);
		} finally {
			closeSession(session);
		}

		logger.debug(CTX_DOC_DETAILS, "Exit getDocumentDetails .. ");
		return policyCondition;
	}

	/**
	 * This method retrieves the policies that match the search criteria
	 * 
	 * @param RenewalStatus
	 * @return String
	 * @throws DataAccessException
	 */
	public RenewalStatus getPolicyEndtDetails(RenewalStatus renewalStatus)
			throws DataAccessException {
		logger.debug(CTX_POLICY_DETAILS, "Entering getPolicyEndtDetails .. ");

		Integer classCode = renewalStatus.getclassCode();
		Date fromDate = renewalStatus.getFromDate();
		Date toDate = renewalStatus.getToDate();
		String insuredName = renewalStatus.getInsuredName();
		Integer policyNum = renewalStatus.getPolicyNo();
		Integer brokerCode = renewalStatus.getBrokerCode();
		Integer schemeCode = renewalStatus.getSchemeCode();
		Boolean isDirect = renewalStatus.getIsDirect();
		Boolean isVipCustomer = renewalStatus.getIsVIPCustomer();
		// Integer status = renewalStatus.getStatus();
		Integer expiringNextDays = renewalStatus.getExpiringInNextDays();

		logger.debug(CTX_POLICY_DETAILS, "classCode ::" + classCode);
		logger.debug(CTX_POLICY_DETAILS, "fromDate ::" + fromDate);
		logger.debug(CTX_POLICY_DETAILS, "toDate ::" + toDate);
		logger.debug(CTX_POLICY_DETAILS, "insuredName ::" + insuredName);
		logger.debug(CTX_POLICY_DETAILS, "policyNum ::" + policyNum);
		logger.debug(CTX_POLICY_DETAILS, "brokerCode ::" + brokerCode);
		logger.debug(CTX_POLICY_DETAILS, "schemeCode ::" + schemeCode);
		logger.debug(CTX_POLICY_DETAILS, "isDirect ::" + isDirect);
		logger.debug(CTX_POLICY_DETAILS, "isVipCustomer ::" + isVipCustomer);
		logger.debug(CTX_POLICY_DETAILS, "expiringNextDays ::"
				+ expiringNextDays);
		// logger.debug(CTX_POLICY_DETAILS, "status ::" + status);

		List policies = null;
		StringBuffer queryStr = new StringBuffer();
		StringBuffer resultStr = new StringBuffer();
		PolicyPK policy = null;
		Long polId = null;
		Long endtId = null;
		RenewalStatus renewalStatusResp = new RenewalStatus();

		try {
			session = getSession();
			queryStr.append("Select new com.rsaame.kaizen.policy.model.PolicyPK(P2.comp_id.policyId, P2.comp_id.endtId) from Policy P2, CashCustomer C "
					+ "where (P2.comp_id.policyId, P2.comp_id.endtId) in "
					+ "(Select P1.comp_id.policyId, P1.comp_id.endtId from Policy P1, CustomerInsured T"
					+ " where P1.classCode = :classCode");

			if (fromDate != null && toDate != null) {
				queryStr.append(" and P1.expiryDate between trunc(:fromDate) and trunc(:toDate)");
			} else if (expiringNextDays != null) {
				queryStr.append(" and P1.expiryDate between trunc(sysdate) and trunc(sysdate + :expiringNextDays)");

				// queryStr.append(" and P1.effectiveDate between trunc(sysdate)
				// and trunc(sysdate + " + expiringNextDays +")");
			}

			if (policyNum != null) {
				queryStr.append(" and P1.policyNo = :policyNum");
			}

			if (brokerCode != null) {
				queryStr.append(" and P1.brCode = :brokerCode");
			}

			if (schemeCode != null) {
				queryStr.append(" and P1.coverNoteHour = :schemeCode");
			}

			if (isDirect.booleanValue()) {
				queryStr.append(" and P1.distributionChannel in (6,8,9,10)");
			}

			if (isVipCustomer.booleanValue()) {
				queryStr.append(" and T.customerType =2");
			}

			// if (status != null)
			// {
			// queryStr.append(" and P1.status = :status");
			// }
			queryStr.append(" and P1.insuredCode = T.insuredId");
			// queryStr.append(" group by P1.comp_id.policyId)");
			queryStr.append(" AND P1.comp_id.endtId = (SELECT MAX(P3.comp_id.endtId) FROM Policy P3 WHERE P3.comp_id.policyId = P1.comp_id.policyId))");
			queryStr.append(" and P2.comp_id.policyId = C.comp_id.policyId ")
					.append(" and P2.insuredId = C.comp_id.insuredId ")
					.append(" and C.comp_id.validityStartDate <= P2.validityStartDate ")
					.append(" and C.validityExpiryDate > P2.validityStartDate ");

			if (insuredName != null && !"".equals(insuredName.trim())) {
				queryStr.append(" and (upper(C.engName1||substring(' ',1,length(C.engName2))||C.engName2||' '||C.engName3) like upper('%"
						+ insuredName + "%'))");
			}

			logger.debug(CTX_POLICY_DETAILS,
					"queryStr ::" + queryStr.toString());
			Query query = session.createQuery(queryStr.toString());

			query.setInteger("classCode", classCode.intValue());
			if (fromDate != null && toDate != null) {
				query.setDate("fromDate", fromDate);
				query.setDate("toDate", toDate);
			} else if (expiringNextDays != null) {
				query.setInteger("expiringNextDays",
						expiringNextDays.intValue());
			}

			if (policyNum != null) {
				query.setInteger("policyNum", policyNum.intValue());
			}

			if (brokerCode != null) {
				query.setInteger("brokerCode", brokerCode.intValue());
			}

			if (schemeCode != null) {
				query.setInteger("schemeCode", schemeCode.intValue());
			}

			// if (status != null)
			// {
			// query.setInteger("status", status.intValue());
			// }

			logger.debug(CTX_POLICY_DETAILS,
					"Query :_7" + query.getQueryString());
			policies = query.list();
		} catch (HibernateException hibernateException) {
			hibernateException.printStackTrace();
			logger.error(CTX_POLICY_DETAILS, hibernateException.getMessage());
			throw new DataAccessException(hibernateException);
		} catch (Exception hibernateException) {
			hibernateException.printStackTrace();
			logger.error(CTX_POLICY_DETAILS, hibernateException.getMessage());
			throw new DataAccessException(hibernateException);
		} finally {
			closeSession(session);
		}

		logger.debug(CTX_POLICY_DETAILS, "policies .. " + policies);

		if (policies != null && !policies.isEmpty()) {
			renewalStatusResp.setRecordFoundChk(new Boolean(true));
			logger.debug(CTX_POLICY_DETAILS,
					"policies length .. " + policies.size());
			Iterator itr = policies.iterator();

			while (itr.hasNext()) {
				policy = (PolicyPK) itr.next();
				polId = policy.getPolicyId();
				endtId = policy.getEndtId();
				resultStr.append(polId + "-" + endtId);
				if (itr.hasNext()) {
					resultStr.append(",");
				}
			}
		} else {
			renewalStatusResp.setRecordFoundChk(new Boolean(false));
		}

		logger.debug(CTX_POLICY_DETAILS, "resultStr .. " + resultStr);
		renewalStatusResp.setConcPolicyEndtId(resultStr.toString());

		logger.debug(CTX_POLICY_DETAILS, "Exit getPolicyEndtDetails .. ");

		return renewalStatusResp;
	}

	/**
	 * This method retrieves the policies that match the search criteria
	 * 
	 * @param policyClassCode
	 * @return
	 * @throws DataAccessException
	 */
	public RenewalStatus getPolicyEndtDetailsforTerms(
			RenewalStatus renewalStatus) throws DataAccessException {
		logger.debug(CTX_POLICY_TERMS_DETAILS,
				"Entering getPolicyEndtDetailsforTerms .. ");

		Integer classCode = renewalStatus.getclassCode();
		Date fromDate = renewalStatus.getFromDate();
		Date toDate = renewalStatus.getToDate();
		String insuredName = renewalStatus.getInsuredName();
		Integer policyNum = renewalStatus.getPolicyNo();
		Integer brokerCode = renewalStatus.getBrokerCode();
		Integer schemeCode = renewalStatus.getSchemeCode();
		Boolean isDirect = renewalStatus.getIsDirect();
		Boolean isVipCustomer = renewalStatus.getIsVIPCustomer();
		// Integer expiringNextDays = renewalStatus.getExpiringInNextDays();

		logger.debug(CTX_POLICY_TERMS_DETAILS, "classCode ::" + classCode);
		logger.debug(CTX_POLICY_TERMS_DETAILS, "fromDate ::" + fromDate);
		logger.debug(CTX_POLICY_TERMS_DETAILS, "toDate ::" + toDate);
		logger.debug(CTX_POLICY_TERMS_DETAILS, "insuredName ::" + insuredName);
		logger.debug(CTX_POLICY_TERMS_DETAILS, "policyNum ::" + policyNum);
		logger.debug(CTX_POLICY_TERMS_DETAILS, "brokerCode ::" + brokerCode);
		logger.debug(CTX_POLICY_TERMS_DETAILS, "schemeCode ::" + schemeCode);
		logger.debug(CTX_POLICY_TERMS_DETAILS, "isDirect ::" + isDirect);
		logger.debug(CTX_POLICY_TERMS_DETAILS, "isVipCustomer ::"
				+ isVipCustomer);
		// logger.debug(CTX_POLICY_DETAILS, "expiringNextDays ::" +
		// expiringNextDays);

		List policies = null;
		StringBuffer queryStr = new StringBuffer();
		StringBuffer resultStr = new StringBuffer();
		PolicyPKQuo policy = null;
		Long polId = null;
		Long endtId = null;
		RenewalStatus renewalStatusResp = new RenewalStatus();

		try {
			session = getSession();

			queryStr.append(
					"Select new com.rsaame.kaizen.quote.model.PolicyPKQuo(P.comp_id.policyId, P.comp_id.endtId) from PolicyQuo P, CashCustomerQuo C, CustomerInsured T "
							+ " where P.classCode = :classCode")
					.append(" AND P.refPolicyId IS NOT NULL")
					.append(" AND P.document  = 6");

			// queryStr.append(" AND
			// NVL(DECODE(P.policyType.comp_id.policyClass.code, 8
			// ,0,P.declarationType), 0) = 0");
			// queryStr.append(" AND EXISTS (SELECT 1 FROM PolicyQuo Q WHERE
			// P.refPolicyId = Q.refPolicyId AND Q.status =7 AND Q.document =
			// 6)");

			queryStr.append(" and P.insuredId = T.insuredId")
					.append(" and P.comp_id.policyId = C.comp_id.policyId ")
					.append(" and P.insuredCode = C.insuredId ")
					.append(" and C.comp_id.validityStartDate <= P.validityStartDate ")
					.append(" and C.validityExpiryDate > P.validityStartDate ");

			if (fromDate != null && toDate != null) {
				queryStr.append(" AND P.effectiveDate BETWEEN trunc(:fromDate) and trunc(:toDate)");

				// queryStr.append(" and P.expiryDate between trunc(:fromDate)
				// and trunc(:toDate)");
			}
			// else if (expiringNextDays != null)
			// {
			// queryStr.append(" and P.effectiveDate between trunc(sysdate) and
			// trunc(sysdate + " + expiringNextDays +")");
			// }

			if (policyNum != null) {
				queryStr.append(" and P.quotationNo = :policyNum");
			}

			if (brokerCode != null) {
				queryStr.append(" and P.brCode = :brokerCode");
			}

			if (schemeCode != null) {
				queryStr.append(" and P.coverNoteHour = :schemeCode");
			}

			if (isDirect.booleanValue()) {
				queryStr.append(" and P.distributionChannel in (6,8,9,10)");
			}

			if (isVipCustomer.booleanValue()) {
				queryStr.append(" and T.customerType =2");
			}

			if (insuredName != null && !"".equals(insuredName.trim())) {
				queryStr.append(" and (upper(C.engName1||substring(' ',1,length(C.engName2))||C.engName2||' '||C.engName3) like upper('%"
						+ insuredName + "%'))");
			}

			// queryStr.append(" group by P.comp_id.policyId)");

			logger.debug(CTX_POLICY_TERMS_DETAILS,
					"queryStr ::" + queryStr.toString());
			Query query = session.createQuery(queryStr.toString());

			query.setInteger("classCode", classCode.intValue());
			if (fromDate != null && toDate != null) {
				query.setDate("fromDate", fromDate);
				query.setDate("toDate", toDate);
			}

			if (policyNum != null) {
				query.setInteger("policyNum", policyNum.intValue());
			}

			if (brokerCode != null) {
				query.setInteger("brokerCode", brokerCode.intValue());
			}

			if (schemeCode != null) {
				query.setInteger("schemeCode", schemeCode.intValue());
			}

			logger.debug(CTX_POLICY_TERMS_DETAILS,
					"Query :_8" + query.getQueryString());
			policies = query.list();
		} catch (HibernateException hibernateException) {
			hibernateException.printStackTrace();
			logger.error(CTX_POLICY_TERMS_DETAILS,
					hibernateException.getMessage());
			throw new DataAccessException(hibernateException);
		} catch (Exception hibernateException) {
			hibernateException.printStackTrace();
			logger.error(CTX_POLICY_TERMS_DETAILS,
					hibernateException.getMessage());
			throw new DataAccessException(hibernateException);
		} finally {
			closeSession(session);
		}

		logger.debug(CTX_POLICY_TERMS_DETAILS, "policies .. " + policies);

		if (policies != null && !policies.isEmpty()) {
			renewalStatusResp.setRecordFoundChk(new Boolean(true));

			logger.debug(CTX_POLICY_TERMS_DETAILS, "policies length .. "
					+ policies.size());
			Iterator itr = policies.iterator();
			while (itr.hasNext()) {
				policy = (PolicyPKQuo) itr.next();
				polId = policy.getPolicyId();
				endtId = policy.getEndtId();
				resultStr.append(polId + "-" + endtId);
				if (itr.hasNext()) {
					resultStr.append(",");
				}
			}
		} else {
			renewalStatusResp.setRecordFoundChk(new Boolean(false));
		}

		logger.debug(CTX_POLICY_TERMS_DETAILS, "resultStr .. " + resultStr);
		renewalStatusResp.setConcPolicyEndtId(resultStr.toString());

		logger.debug(CTX_POLICY_TERMS_DETAILS,
				"Exit getPolicyEndtDetailsforTerms .. ");

		return renewalStatusResp;
	}

	/**
	 * 
	 * @param policyId
	 * @param endtId
	 * @return
	 * @throws DataAccessException
	 */
	/* PAS CHANGES -- changed policyId to PolicyNo */
	public List getReceiptDetails(Long policyId, Long endtId)
			throws DataAccessException {
		List receiptList = null;
		Criteria criteria = null;
		Session session = null;
		logger.debug(CTX_RECEIPT_DETAILS, "Method entered " + policyId + " : "
				+ endtId);

		session = getSession();
		criteria = session.createCriteria(DetailReceipt.class);
		criteria.add(Restrictions.eq("rcdPolicyId", policyId));
		criteria.add(Restrictions.eq("rcdEndtId", endtId));
		receiptList = criteria.list();
		logger.debug(CTX_RECEIPT_DETAILS, "Method exited " + receiptList);
		return receiptList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.rsaame.kaizen.reports.dao.ReportsDAO#getProposalFormDetails(com.rsaame
	 * .kaizen.policy.model.Policy)
	 */
	public PolicyQuo getProposalFormDetails(Policy inputProposalForm)
			throws DataAccessException {
		// List policyList = null;
		PolicyQuo policyQuo = new PolicyQuo();
		PolicyPKQuo policyPKQuo = new PolicyPKQuo();
		Document document = new Document();
		// Added Integer.valueOf() to avoid sonar violation on 18-9-2017
		//Long quotationNo = new Long(0);
		Long quotationNo =Long.valueOf(0);
		// ADM 19.08.2009 Tkt 8090 - getting the distribution channel instance
		DistributionChannel distributionChannel = new DistributionChannel();
		// Long policyId = new Long(0);
		// Long endtId= new Long(0);
		Object quoteFields[] = null;
		Object polFields[] = null;
		Long location = null;
		Integer classCode = null;

		logger.debug(CTX_PROPOSAL_FORM_DETAILS,
				"Entering ProposalForm Details .. ");
		// Release 4.0 28.12.2010 - Retrieving Quote for the Policy based on
		// Class and Location - UAT Issue fix
		classCode = AMEConstants.POLICY_CLASS_CODE_FOR_MOTOR;
		try {
			session = getSession();

			Query query = session
					.createQuery("select pol.quotationNo, pol.location.comp_id.locCode FROM Policy pol "
							+ "where pol.comp_id.policyId=:policyId and pol.comp_id.endtId=:endtId");

			query.setLong("policyId", inputProposalForm.getComp_id()
					.getPolicyId().longValue());
			query.setLong("endtId", inputProposalForm.getComp_id().getEndtId()
					.longValue());

			// quotationNo = (Long) query.uniqueResult();

			polFields = (Object[]) query.uniqueResult();
			if (polFields[0] != null) {
				quotationNo = new Long(polFields[0].toString());
			}
			if (polFields[1] != null) {
				location = new Long(polFields[1].toString());
			}

			Query quotationQuery = session
					.createQuery("select pol.comp_id.policyId,pol.comp_id.endtId,pol.document.code,"
							+ " pol.coinsuranceType,pol.distributionChannel.code FROM PolicyQuo pol "
							+ " where pol.quotationNo="
							+ quotationNo
							+ " and pol.location.comp_id.locCode = "
							+ location
							+ " and pol.classCode = " + classCode);

			logger.debug(CTX_PROPOSAL_FORM_DETAILS,
					"Query :_9" + query.getQueryString());
			logger.debug(CTX_PROPOSAL_FORM_DETAILS, "quotationQuery ::"
					+ quotationQuery.getQueryString());

			List results = quotationQuery.list();
			Iterator resultIterator = results.iterator();
			while (resultIterator.hasNext()) {
				quoteFields = (Object[]) resultIterator.next();
				if (quoteFields[0] != null) {
					policyPKQuo
							.setPolicyId(new Long(quoteFields[0].toString()));
				}
				if (quoteFields[1] != null) {
					policyPKQuo.setEndtId(new Long(quoteFields[1].toString()));
				}
				if (quoteFields[2] != null) {
					document.setCode(new Integer(quoteFields[2].toString()));
					policyQuo.setDocument(document);
				}
				if (quoteFields[3] != null) {
					policyQuo.setCoinsuranceType(new Integer(quoteFields[3]
							.toString()));
				}
				// ADM 19.08.2009 Tkt 8090 - Adde dist channel code
				if (quoteFields[4] != null) {
					distributionChannel.setCode(new Integer(quoteFields[4]
							.toString()));
					policyQuo.setDistributionChannel(distributionChannel);
				}

			}
			policyQuo.setComp_id(policyPKQuo);
			System.out.println("Policy id:"
					+ policyQuo.getComp_id().getPolicyId());
			System.out.println("endt id:" + policyQuo.getComp_id().getEndtId());
			System.out.println("Document Code:"
					+ policyQuo.getDocument().getCode());
			System.out.println("Coinsurance Type:"
					+ policyQuo.getCoinsuranceType().intValue());
			// System.out.println("Quotation no:"+policyQuo.getQuotationNo());
		} catch (HibernateException hibernateException) {
			hibernateException.printStackTrace();
			logger.error(CTX_PROPOSAL_FORM_DETAILS,
					hibernateException.getMessage());
			throw new DataAccessException(hibernateException);
		} catch (Exception hibernateException) {
			hibernateException.printStackTrace();
			logger.error(CTX_PROPOSAL_FORM_DETAILS,
					hibernateException.getMessage());
			throw new DataAccessException(hibernateException);
		}

		finally {

			closeSession(session);

		}

		return policyQuo;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.rsaame.kaizen.reports.dao.ReportsDAO#getNCDLetterDetails(com.rsaame
	 * .kaizen.policy.model.Policy)
	 */
	public Long getNCDLetterDetails(CustomerInsured inputNCDLetter)
			throws DataAccessException {
		// TODO Auto-generated method stub

		Long policyCount;
		Session session = null;
		logger.debug(CTX_NCD_DETAILS,
				"Entering getNCDLetterDetails method..... ");

		try {
			session = getSession();
			Query countQuery = session
					.createQuery("select count(*) FROM Policy policy where policy.insuredCode=:insuredId");
			countQuery.setLong("insuredId", inputNCDLetter.getInsuredId()
					.longValue());

			logger.debug(CTX_NCD_DETAILS, "Query insuredCode value is::"
					+ countQuery.uniqueResult());
			policyCount = (Long) countQuery.uniqueResult();
		}

		catch (HibernateException hibernateException) {
			hibernateException.printStackTrace();
			logger.error(CTX_NCD_DETAILS, hibernateException.getMessage());
			throw new DataAccessException(hibernateException);
		} catch (Exception hibernateException) {
			hibernateException.printStackTrace();
			logger.error(CTX_NCD_DETAILS, hibernateException.getMessage());
			throw new DataAccessException(hibernateException);
		}

		finally {

			closeSession(session);

		}

		return policyCount;

	}

}
