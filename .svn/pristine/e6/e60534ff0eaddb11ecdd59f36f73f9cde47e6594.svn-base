package com.rsaame.pas.renewals.scheduler;

import java.util.TimerTask;

import com.mindtree.ruc.cmn.log.Logger;

public class WSImportSBSQuoteScheduledService extends TimerTask {
	private final static Logger LOGGER = Logger.getLogger(WSImportSBSQuoteScheduledService.class);

	private WSImportSBSQuoteScheduledTask wSImportSBSQuoteScheduledTask;

	public WSImportSBSQuoteScheduledTask getwSImportSBSQuoteScheduledTask() {
		return wSImportSBSQuoteScheduledTask;
	}

	public void setwSImportSBSQuoteScheduledTask(WSImportSBSQuoteScheduledTask wSImportSBSQuoteScheduledTask) {
		this.wSImportSBSQuoteScheduledTask = wSImportSBSQuoteScheduledTask;
	}

	@Override
	public void run() {
		LOGGER.debug(":::::::: Renewal Batch execution Method for WSImportSBSQuoteScheduledService: Start:::::::::::");
		wSImportSBSQuoteScheduledTask.run();
		LOGGER.debug(":::::::: Renewal Batch execution Method for WSImportSBSQuoteScheduledService: Completed:::::::::::");
	}

}
