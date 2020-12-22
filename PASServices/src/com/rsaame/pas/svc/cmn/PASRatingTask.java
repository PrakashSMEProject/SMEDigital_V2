package com.rsaame.pas.svc.cmn;

import java.math.BigDecimal;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.task.ITask;
import com.mindtree.ruc.cmn.task.TaskOutput;
import com.rsaame.pas.rating.svc.PremiumCalculator;
import com.rsaame.pas.vo.bus.PolicyVO;

public class PASRatingTask implements ITask{
	private final static Logger logger = Logger.getLogger(PASRatingTask.class);
	@Override
	public TaskOutput execute( String identifier, String[] taskInputConfig, BaseVO input ){
		
		long startTime = System.currentTimeMillis();
		TaskOutput to;
		to=invokeRating(input);
		long endTime = System.currentTimeMillis();
	     logger.info(" Rating Engine integration Timer: PAS Task Excecusion time (In MilliSec):: " + (endTime - startTime));
		
		return to;
	}

	private TaskOutput invokeRating( BaseVO input ){
		TaskOutput taskOutput=new TaskOutput();
		PolicyVO policyVO =(PolicyVO) input;
		PremiumCalculator premiumCalculator= new PremiumCalculator();
		// TODO: Need to implement in Renewal 
		//renewalLoading Reserved for Polcy renewal.
		BigDecimal renewalLoading=new BigDecimal(0); 
		policyVO=premiumCalculator.calculateRiskPremium( policyVO, renewalLoading );
		taskOutput.setSuccessful( true );
		taskOutput.setTaskOutput( policyVO );
		return taskOutput;
	}
	
	
	
	

}
