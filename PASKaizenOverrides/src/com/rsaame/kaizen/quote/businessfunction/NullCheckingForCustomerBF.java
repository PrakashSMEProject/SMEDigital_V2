/*
 * ValidateCustomerNullCheckBF.java
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
package com.rsaame.kaizen.quote.businessfunction;

import com.mindtree.ruc.cmn.exception.BusinessException;
import com.rsaame.kaizen.framework.businessfunction.BaseBusinessFunction;
import com.rsaame.kaizen.framework.constants.ErrorConstants;
import com.rsaame.kaizen.framework.exception.ValidationException;
import com.rsaame.kaizen.framework.logger.AMELogger;
import com.rsaame.kaizen.framework.logger.AMELoggerFactory;
import com.rsaame.kaizen.framework.model.ServiceContext;
import com.rsaame.kaizen.framework.util.AppProperties;
import com.rsaame.kaizen.quote.model.CashCustomerQuo;

/**
 * 
 * This class is copied from Kaizen to override the logic which is not 
 * required for ePlatform - PAS 
 */ 


public class NullCheckingForCustomerBF extends BaseBusinessFunction {


	/**
	 * This method validates wheather the Customer name is null or not
	 * 
	 * @param customer
	 * @return isNullCustomerName
	 */

	public void nullCheckingForCustomer(CashCustomerQuo customer)
			throws ValidationException {
		boolean isValid = true;

		 
	}
}