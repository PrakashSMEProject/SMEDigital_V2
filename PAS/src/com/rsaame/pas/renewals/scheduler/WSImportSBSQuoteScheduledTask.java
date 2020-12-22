package com.rsaame.pas.renewals.scheduler;

import java.text.ParseException;

import com.mindtree.ruc.cmn.log.Logger;

public class WSImportSBSQuoteScheduledTask {

	private final static Logger LOGGER = Logger.getLogger(WSImportSBSQuoteScheduledTask.class);

	private String str = "This is Batch Call";

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void run() {
		LOGGER.debug(":::::::: Renewal Batch execution Method for WSImportSBSQuoteScheduledTask: Start:::::::::::");
		LOGGER.debug(":::::::: Renewal Batch execution Method for WSImportSBSQuoteScheduledTask: Completed:::::::::::");
		/*try {
			TestSaveGeneralInfo.invoke();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}*/
	}
}
