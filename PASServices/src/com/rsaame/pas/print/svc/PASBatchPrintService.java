package com.rsaame.pas.print.svc;

import java.util.List;
import java.util.TimerTask;

import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.kaizen.framework.model.ServiceContext;

public class PASBatchPrintService extends TimerTask{

	PASBatchPrinterService pasBatchPrinterService;
	
	public PASBatchPrinterService getPasBatchPrinterService() {
		return pasBatchPrinterService;
	}

	public void setPasBatchPrinterService(
			PASBatchPrinterService pasBatchPrinterService) {
		this.pasBatchPrinterService = pasBatchPrinterService;
	}

	@Override
	public void run() {
		
		String location = ServiceContext.getLocation();;
		String locations = Utils.getSingleValueAppConfig("LOCATION_CODE",0);
		List<String> locationsToAppend = CopyUtils.asList(locations.split(","));
		
		for(String loc : locationsToAppend){
			ServiceContext.setLocation(loc);
			pasBatchPrinterService.run();
		}
		
		ServiceContext.setLocation(location);
		
	}

}
