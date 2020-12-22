/*
 * FileName.java
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
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import com.cts.writeRate.Policy;
import com.rsaame.kaizen.framework.constants.AMEConstants;
import com.rsaame.kaizen.framework.logger.AMELogger;
import com.rsaame.kaizen.framework.logger.AMELoggerFactory;
import com.rsaame.kaizen.framework.model.CalculatedPremium;
import com.rsaame.kaizen.framework.util.AMEUtil;
import com.rsaame.kaizen.quote.model.FactorBO;
/*import com.rsaame.pas.writerate.client.Item;
import com.rsaame.pas.writerate.client.Policy;
import com.rsaame.pas.writerate.client.Premium;
*/
/**
 * @author M1017952
 *
 */
public class PremiumCaluclatorHelper {
	private static final AMELogger logger = AMELoggerFactory.getInstance()
	.getLogger(PremiumCaluclatorHelper.class);
	/**
	 * @param eRatorPolicyWithPremium
	 * @return
	 */
	public List getPremiumList(com.cts.writeRate.Policy eRatorPolicyWithPremium) {
		// TODO Auto-generated method stub
		logger.debug(AMEConstants.GET_PREMIUM, "Start Method");

		List coverPremiumList = new ArrayList();

		CalculatedPremium cPremium = new CalculatedPremium();

		com.cts.writeRate.Premium premium;

				//Checking if Policy exsists

		if (eRatorPolicyWithPremium != null) {
			cPremium.setCoverageCode("2");
			if (eRatorPolicyWithPremium.getPremium() != null) {
				cPremium.setPremium(eRatorPolicyWithPremium.getPremium().getBasePremium());
			}
			// For getting eRatorPolicyWithPremium level coverage premium
			if (eRatorPolicyWithPremium.getPolicyCoverages() != null) {

				for (int eRatorPolicyWithPremiumCoverCount = 0; eRatorPolicyWithPremiumCoverCount < eRatorPolicyWithPremium
						.getPolicyCoverages().length; eRatorPolicyWithPremiumCoverCount++) {

					CalculatedPremium calPremium = new CalculatedPremium();

					calPremium
							.setCoverageCode(eRatorPolicyWithPremium.getPolicyCoverages()[eRatorPolicyWithPremiumCoverCount]
									.getCoverageCode());

					calPremium
							.setPremium(eRatorPolicyWithPremium.getPolicyCoverages()[eRatorPolicyWithPremiumCoverCount]
									.getPremium().getBasePremium());

					coverPremiumList.add(calPremium);
				}
			}
			if (eRatorPolicyWithPremium.getItems() != null) {

				com.cts.writeRate.Item item;

				for (int eRatorPolicyWithPremiumItemCount = 0; eRatorPolicyWithPremiumItemCount < eRatorPolicyWithPremium
						.getItems().length; eRatorPolicyWithPremiumItemCount++) {

					item = eRatorPolicyWithPremium.getItems()[eRatorPolicyWithPremiumItemCount];

					for (int coverCount = 0; coverCount < item.getCoverages().length; coverCount++) {

						CalculatedPremium calItemPremium = new CalculatedPremium();

						calItemPremium
								.setCoverageCode(item.getCoverages()[coverCount]
										.getCoverageCode());

						logger.debug(AMEConstants.GET_PREMIUM,
								"Getting Cover Code:"
										+ calItemPremium.getCoverageCode());

						calItemPremium
								.setPremium(item.getCoverages()[coverCount]
										.getPremium().getBasePremium());

						logger.debug(AMEConstants.GET_PREMIUM,
								"Getting Premium:"
										+ calItemPremium.getPremium());

						coverPremiumList.add(calItemPremium);
					}
				}
			}
		}

		logger.debug(AMEConstants.GET_PREMIUM, "END Method");

		return coverPremiumList;
	}
	
	
	
	/**
	 * @param factorsList
	 * @return
	 */
	public Policy setPolicyForDetails(ArrayList<FactorBO> factorsList) {
		// TODO Auto-generated method stub: Needed

		// Implement as in
		// com.rsaame.kaizen.quote.businessfunction.GetPremiumForMotor.setPolicyForDetails()
		logger.debug(AMEConstants.SET_POLICY_FOR_DETAILS, "Start Method");
		Policy policy = new Policy();

		Iterator listItr = factorsList.listIterator();

		Calendar gregorianCalendar = new GregorianCalendar();

		policy.setTermsInMonth(12);

		while (listItr.hasNext()) {

			FactorBO keyValuePair = (FactorBO) listItr.next();
			if (keyValuePair != null && keyValuePair.getFactorName() != null) {
				if (AMEConstants.QUOTE_LOB.equals(keyValuePair.getFactorName())) {
					policy.setLob(keyValuePair.getFactorValue());
					logger.debug(AMEConstants.SET_POLICY_FOR_DETAILS,
							"Setting LOB:" + keyValuePair.getFactorValue());
				} else if (AMEConstants.QUOTE_STATE.equals(keyValuePair
						.getFactorName())) {
					policy.setState(keyValuePair.getFactorValue());
					logger.debug(AMEConstants.SET_POLICY_FOR_DETAILS,
							"Setting State:" + keyValuePair.getFactorValue());
				} else if (AMEConstants.QUOTE_COMPANY.equals(keyValuePair
						.getFactorName())) {
					policy.setCompany(keyValuePair.getFactorValue());
					logger.debug(AMEConstants.SET_POLICY_FOR_DETAILS,
							"Setting Company:" + keyValuePair.getFactorValue());
				} else if (AMEConstants.QUOTE_PLAN.equals(keyValuePair
						.getFactorName())) {
					policy.setPlan(keyValuePair.getFactorValue());
					logger.debug(AMEConstants.SET_POLICY_FOR_DETAILS,
							"Setting Plan:" + keyValuePair.getFactorValue());
				} else if (AMEConstants.QUOTE_PRODUCT.equals(keyValuePair
						.getFactorName())) {
					policy.setProduct(keyValuePair.getFactorValue());
					logger.debug(AMEConstants.SET_POLICY_FOR_DETAILS,
							"Setting Product:" + keyValuePair.getFactorValue());
				} else if (AMEConstants.QUOTE_SERVICE.equals(keyValuePair
						.getFactorName())) {
					policy.setService(keyValuePair.getFactorValue());
					logger.debug(AMEConstants.SET_POLICY_FOR_DETAILS,
							"Setting Service:" + keyValuePair.getFactorValue());
				} else if (AMEConstants.QUOTE_DEBUG_INDEX.equals(keyValuePair
						.getFactorName())) {
					policy.setDebugInd((new Boolean(keyValuePair
							.getFactorValue())).booleanValue());
					logger.debug(AMEConstants.SET_POLICY_FOR_DETAILS,
							"Setting Debug Index:"
									+ keyValuePair.getFactorValue());
				} else if (AMEConstants.QUOTE_PASS.equals(keyValuePair
						.getFactorName())) {
					policy.setPassword(keyValuePair.getFactorValue());
					logger
							.debug(AMEConstants.SET_POLICY_FOR_DETAILS,
									"Setting Password:"
											+ keyValuePair.getFactorValue());
				} else if (AMEConstants.QUOTE_CALLER_ID.equals(keyValuePair
						.getFactorName())) {
					policy.setCallerId(keyValuePair.getFactorValue());
					logger
							.debug(AMEConstants.SET_POLICY_FOR_DETAILS,
									"Setting CallerID:"
											+ keyValuePair.getFactorValue());
				} else if (AMEConstants.QUOTE_TRANSACTION_TYPE
						.equals(keyValuePair.getFactorName())) {
					policy.setTransType(keyValuePair.getFactorValue());
					logger.debug(AMEConstants.SET_POLICY_FOR_DETAILS,
							"Setting Transaction Type:"
									+ keyValuePair.getFactorValue());
				} else if (AMEConstants.QUOTE_EFFECTIVE_DATE
						.equals(keyValuePair.getFactorName())) {
					gregorianCalendar.setTime(AMEUtil.StringToDate(keyValuePair
							.getFactorValue()));
					
					policy.setEffectiveDate(AMEUtil.StringToDate(keyValuePair
							.getFactorValue()));
					//TODO: Setting available date as same as effective date
					policy.setAvailableDate(AMEUtil.StringToDate(keyValuePair
							.getFactorValue()));
					logger.debug(AMEConstants.SET_POLICY_FOR_DETAILS,
							"Setting Effective Date:"
									+ keyValuePair.getFactorValue());

					// setting available date to current date (previously
					// effective date).
					GregorianCalendar calendar = new GregorianCalendar();
					logger.debug(AMEConstants.SET_POLICY_FOR_DETAILS,
							"Setting calendar:" + calendar);
					//policy.setAvailableDate(calendar);
					logger.debug(AMEConstants.SET_POLICY_FOR_DETAILS,
							"Setting Avaialable Date:" + calendar);
				} else if (AMEConstants.QUOTE_SOURCE_REGION.equals(keyValuePair
						.getFactorName())) {
					policy.setSourceRegion(keyValuePair.getFactorValue());
					logger.debug(AMEConstants.SET_POLICY_FOR_DETAILS,
							"Setting Source Region:"
									+ keyValuePair.getFactorValue());
				}

			}

		}

		logger.debug(AMEConstants.SET_POLICY_FOR_DETAILS, "End Method");

		return policy;
		
	}
	
}


