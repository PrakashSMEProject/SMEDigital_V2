/*
 * CheckIfInsuredChannelExistsBF.java
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.mindtree.ruc.cmn.log.Logger;
import com.rsaame.kaizen.customer.dao.CustomerInsuredDAO;
import com.rsaame.kaizen.customer.model.CustomerInsured;
import com.rsaame.kaizen.customer.model.DistributionChannel;
import com.rsaame.kaizen.framework.businessfunction.BaseBusinessFunction;
import com.rsaame.kaizen.framework.dao.exception.DataAccessException;
import com.rsaame.kaizen.framework.exception.ServiceException;
import com.rsaame.kaizen.framework.logger.AMELogger;
import com.rsaame.kaizen.framework.logger.AMELoggerFactory;
import com.rsaame.kaizen.quote.dao.QuoteDAO;
import com.rsaame.kaizen.quote.model.PolicyQuo;

/**
 * CheckIfInsuredChannelExistsBF class
 * 
 * @version 1.0 Sept 1, 2009
 * @author Cognizant Technology Solutions
 */
public class CheckIfInsuredChannelExistsBF extends BaseBusinessFunction {

	/**
	 * Logger instance
	 */
	private static final AMELogger logger = AMELoggerFactory.getInstance()
			.getLogger(CheckIfInsuredChannelExistsBF.class);
	private static final Logger wrapperLogger = Logger.getLogger(CheckIfInsuredChannelExistsBF.class); //SONAR fix -- 20-apr-2018 -- changed from LOGGER to wrapperLogger
	/**
	 * Method logger context.
	 */
	private static final String CTX_GET_CHANNEL_FOR_CUSTOMER = "CheckIfInsuredChannelExistsBF(PolicyQuo)";
	
	public PolicyQuo checkIfInsuredChannelExists(PolicyQuo policyQuo)
			throws ServiceException {

		logger.debug(CTX_GET_CHANNEL_FOR_CUSTOMER, "Method Entered");
		CustomerInsuredDAO customerInsuredDAO = null;
		CustomerInsured customerInsured = new CustomerInsured();
		List customerList = null;
		DistributionChannel ipChannel = null;		
		CustomerInsured oldInsured = null;
		boolean insuredExists = false;
		int ipBrCode= 0;
		Integer tempBrCode=null;
		
		List customerList_new = new ArrayList();
		boolean matchexists = false;
		 
		if(policyQuo.getBrCode()!=null)
			ipBrCode = policyQuo.getBrCode().intValue();
		
		customerInsuredDAO = (CustomerInsuredDAO) getBean(DAO_CUSTOMER);
		QuoteDAO quoteDAO = (QuoteDAO) getBean(DAO_QUOTE);
		try {			
			customerInsured.setEngFirstName(policyQuo.getCashCustomerQuo().getEngName1());
			
			//if(policyQuo.getCashCustomerQuo().getEngName2()!=null)
			customerInsured.setEngMiddleName(policyQuo.getCashCustomerQuo().getEngName2());
			customerInsured.setEngLastName(policyQuo.getCashCustomerQuo().getEngName3());
			customerInsured.setEngZipCode(policyQuo.getCashCustomerQuo().getPoBox());
			customerInsured.setEngMobileNo(policyQuo.getCashCustomerQuo().getEngGsmNo());
			customerInsured.setDob(policyQuo.getCashCustomerQuo().getDob());
			// Release 4.0 Oman Rollout Cahnges Saving Postal code in insured table
			if(policyQuo.getCashCustomerQuo().getEngZipCode()!=null)
			{
				customerInsured.setPostalCode(policyQuo.getCashCustomerQuo().getEngZipCode());
			}
			
			if(customerInsured.getBrCode()!=null)
				tempBrCode = customerInsured.getBrCode();
			customerInsured.setBrCode(policyQuo.getBrCode());
			
			customerList = customerInsuredDAO.checkIfInsuredExists(customerInsured);
			
			customerInsured.setBrCode(tempBrCode);
			if (customerList != null && !customerList.isEmpty()) {				
				logger.debug(CTX_GET_CHANNEL_FOR_CUSTOMER, "Length of List"+customerList.size());
				
				ipChannel = quoteDAO.getDistributionChannel(policyQuo.getDistributionChannel().getCode());
				
				int ipSob = ipChannel.getSob().intValue();
				int ipCode = ipChannel.getCode().intValue();
				Integer opBrCode=null;
				Iterator i = customerList.iterator();
				//&&(!insuredExists) was removed because this logic works when we have to break the loop when 1 match is found
				//while (i.hasNext()&&(!insuredExists)) {		
				
				while (i.hasNext()) {					
					CustomerInsured cust = (CustomerInsured) i.next();

					int opSob = cust.getDistributionChannel().getSob().intValue();
					int opCode = cust.getDistributionChannel().getCode().intValue();
					if(cust.getBrCode()!=null)
						opBrCode = cust.getBrCode();
					
					if(ipSob!=opSob){	
						insuredExists = false;			
					}
					else if((ipSob==PARAM_TWELVE && opSob==PARAM_TWELVE) && (ipCode!=opCode) 
								&&((ipCode==PARAM_FOUR && opCode!=PARAM_FOUR)
										||((ipCode!=PARAM_FOUR && opCode==PARAM_FOUR)))){
						insuredExists = false;
					}
					else if((ipCode==PARAM_FOUR && opCode==PARAM_FOUR)							 
								&&(opBrCode!=null)&&(ipBrCode!=opBrCode.intValue())){
						insuredExists = false;
					}
					else{
						insuredExists = true;
						oldInsured = cust;
						
						Integer cityCode = cust.getLocationCode();
						if (cityCode != null) {
							String cityDesc = customerInsuredDAO
									.getCityDesc(cityCode.toString());
							cust.setCityDesc(cityDesc);
						}
						Integer brkcode = cust.getBrCode();
						if (brkcode != null) {
							String brkname = customerInsuredDAO
									.getBrokerDesc(brkcode.toString());
							cust.setBrkname(brkname);
						}
						Integer statuscode=cust.getStatus();
						if(statuscode!=null){
							String statusdesc=customerInsuredDAO.getCustomerStatusDesc(statuscode.toString());
							cust.setStatusdesc(statusdesc);
						
						}
						Integer tilecode =cust.getNameTitle();
						if(tilecode!=null){
							String titledesc =customerInsuredDAO.getNametitleDesc(tilecode.toString());
							cust.setNametitleDesc(titledesc);
						}
						Integer extcode = cust.getExtAccExecCode();
						if(extcode!=null){
							String extdesc =customerInsuredDAO.getExternalexecDesc(extcode.toString());
							cust.setExternalexecdesc(extdesc);
						}
						
						Integer internalexcode = cust.getEmployee();
						if(internalexcode!=null){
							String intdesc =customerInsuredDAO.getEmployeeDesc(internalexcode.toString());
							cust.setInternalexecdesc(intdesc);
						}
						customerList_new.add(cust);
						
						
					}
				}
			}			
           //if(!insuredExists){ was changed to if(!(customerList_new.size() > 0)) because this logic works when we have to break the loop when 1 match is found
			if(!(customerList_new.size() > 0)){		
				/** PAS Changes - Changes for Create Quote Existing Customer Flow
				 * Commenting the below lines as Insured Id should not be nullified
				 */
/*				policyQuo.setInsuredId(null);
				policyQuo.setNewCustomer(LETTER_Y);*/
				wrapperLogger.debug( "PAS Changes - Changes for Create Quote Existing Customer Flow" ); //SONAR fix -- 20-apr-2018 -- changed from LOGGER to wrapperLogger
			}
			else{				
				if(oldInsured!=null){					
									
					if (customerList_new != null){
						//policyQuo.setInsuredId(oldInsured.getInsuredId());	
						//CR 70 Matching Customer Insured changes
						if(policyQuo != null){
							logger.debug(CTX_GET_CHANNEL_FOR_CUSTOMER, "getExitingsCustomer");
							
							if (customerList_new != null && !customerList_new.isEmpty()
									&& customerList_new.size() > 0) {
								matchexists = true;
								policyQuo.setMatchExists("Y");
								policyQuo.setCustDetails(customerList_new);
							}
							else {
								policyQuo.setMatchExists("N");
								matchexists = false;
							}

						
						}
						
					}
				}
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		}

		logger.debug(CTX_GET_CHANNEL_FOR_CUSTOMER, "Method Exited");
		return policyQuo;

	}
}