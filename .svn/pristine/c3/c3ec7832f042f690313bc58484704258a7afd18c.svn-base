/*
 * Created on May 16, 2008
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.rsaame.kaizen.reports.businessfunction;

import java.util.List;

import com.rsaame.kaizen.framework.businessfunction.BaseBusinessFunction;
import com.rsaame.kaizen.framework.dao.exception.DataAccessException;
import com.rsaame.kaizen.framework.exception.ServiceException;
import com.rsaame.kaizen.framework.logger.AMELogger;
import com.rsaame.kaizen.framework.logger.AMELoggerFactory;
import com.rsaame.kaizen.policy.model.DetailReceipt;
import com.rsaame.kaizen.reports.dao.ReportsDAO;

/**
 * @author 159036
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class IsRecieptDetailsAvailableBF extends BaseBusinessFunction {
	// INSTANTIATE LOGGER
	private static final AMELogger logger = AMELoggerFactory.getInstance()
			.getLogger(IsRecieptDetailsAvailableBF.class);

	// LOGGER CONTEXT CONSTANTS
	public static final String CTX_IS_RECPT_DTLS_AVAIL = "isRecieptDetailsAvailable(PolicyQuo policyQuo)";

	/**
	 * 
	 * @param detailReceipt
	 * @return
	 */
	public DetailReceipt isRecieptDetailsAvailable(DetailReceipt detailReceipt)
			throws ServiceException {
		ReportsDAO reportsDAO = null;
		
		//Commented the variable to avoid Dead store to local variable to fix sonar violation on 20-9-2017
		//Long policyNo = null;
		Long policyId = null;
		Long endtId = null;
		List receiptList = null;
		logger
				.debug(CTX_IS_RECPT_DTLS_AVAIL, "Method entered "
						+ detailReceipt);
		if (detailReceipt != null) {
			logger.debug(CTX_IS_RECPT_DTLS_AVAIL, "Details are avaialable");
			try {
				if (detailReceipt.getRcdPolicyNo() != null
						&& detailReceipt.getRcdEndtId() != null) {
					logger.debug(CTX_IS_RECPT_DTLS_AVAIL, "POLICY ID :: "
							+ detailReceipt.getRcdPolicyNo());
					logger.debug(CTX_IS_RECPT_DTLS_AVAIL, "ENDT ID :: "
							+ detailReceipt.getRcdEndtId());
					reportsDAO = (ReportsDAO) getBean(DAO_REPORTS);
					//policyNo = detailReceipt.getRcdPolicyNo();
					endtId = detailReceipt.getRcdEndtId();
					policyId = detailReceipt.getRcdPolicyId();
					receiptList = reportsDAO
							.getReceiptDetails(policyId, endtId);

					// CHECK IF THE RECEIPT DETAILS ARE AVAIALABLE FOR THE GIVEN
					// POLICY AND ENDORSEMENT ID
					if (receiptList != null && !receiptList.isEmpty()) {
						logger.debug(CTX_IS_RECPT_DTLS_AVAIL,
								"Receipt List is not Empty...");
						logger.debug(CTX_IS_RECPT_DTLS_AVAIL,
								"Setting receiptDtsAvailable to TRUE");
						detailReceipt = (DetailReceipt) receiptList.get(0);
						logger.debug(CTX_IS_RECPT_DTLS_AVAIL,"LIST ENTRY : "+detailReceipt);
					}
				}
			} catch (DataAccessException dataAccessException) {
				logger.error(CTX_IS_RECPT_DTLS_AVAIL, dataAccessException
						.getMessage());
				throw new ServiceException(dataAccessException);
			}
		}
		logger.debug(CTX_IS_RECPT_DTLS_AVAIL, "Method exited "
				+ detailReceipt);
		return detailReceipt;
	}
}