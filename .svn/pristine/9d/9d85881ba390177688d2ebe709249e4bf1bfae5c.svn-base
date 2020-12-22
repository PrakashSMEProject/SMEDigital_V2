package com.rsaame.pas.renewals.scheduler;

import java.util.List;
import java.util.TimerTask;

import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.LoginLocation;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.kaizen.framework.model.ServiceContext;

public class RenewalBatchService extends TimerTask{
	
	private RenewalBatchSchedulerSvc renewalBatchSchedulerSvc;

	public RenewalBatchSchedulerSvc getRenewalBatchSchedulerSvc() {
		return renewalBatchSchedulerSvc;
	}

	public void setRenewalBatchSchedulerSvc(
			RenewalBatchSchedulerSvc renewalBatchSchedulerSvc) {
		this.renewalBatchSchedulerSvc = renewalBatchSchedulerSvc;
	}

	@Override
	public synchronized void  run() {

		String batchFlag = Utils.getSingleValueAppConfig("BATCH_FLAG", 0);
		if (batchFlag.equalsIgnoreCase("Y")) {

			String location = ServiceContext.getLocation();
			System.out.println("The current thread is ------------------------------------------------------"
					+ Thread.currentThread().getName());
			String locations = Utils.getSingleValueAppConfig("LOCATION_CODE", 0);
			List<String> locationsToAppend = CopyUtils.asList(locations.split(","));
			for (String loc : locationsToAppend) {
				ServiceContext.setLocation(loc);
				ServiceContext.setLoginLocation(loc);
				renewalBatchSchedulerSvc.run();
			}

			ServiceContext.setLocation(location);
		}
		
	}

}
