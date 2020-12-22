package com.rsaame.pas.b2b.ws.batch.tasks;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.b2b.ws.batch.handler.SBSBatchJobInvoker;
import com.rsaame.pas.b2b.ws.batch.input.BatchInput;

public class ManualJobTrigger {

	public static void main(String[] args) {
		if(args.length!=5) {
			StringBuilder str=new StringBuilder();
			str.append("\nRequire 5 parameters for the job to run\n");
			str.append("Parameters are ordered in the following sequence: \n");
			str.append("1. Quote No\n");
			str.append("2. Endorsement Id\n");
			str.append("3. Policy No\n");
			str.append("4. Policy Linking Id\n");
			str.append("5. String create/update\n");
			str.append("For example 1<space>2<space>3<space>4<space>create\n");
			throw new RuntimeException(str.toString());
		}
		long quoteNo=Long.parseLong(args[0]);
		long endId=Long.parseLong(args[1]);
		long policyNo=Long.parseLong(args[2]);
		long polLinkingId=Long.parseLong(args[3]);
		String jobFlag=args[4];
		if(!(jobFlag.equalsIgnoreCase("Create")||jobFlag.equalsIgnoreCase("Update"))) {
			throw new RuntimeException("\nThe 5th paramater must be either Create or Update");			
		}
		
		SBSBatchJobInvoker invoker = (SBSBatchJobInvoker) Utils.getBean("jobInvoker");
		BatchInput input = new BatchInput.Builder(quoteNo,endId)
				.policyId(policyNo).policyLinkingId(polLinkingId).build();
		if(jobFlag.equalsIgnoreCase("Create")) {
			invoker.createQuote(input);
			}
		else{
			invoker.updateQuote(input);
		}	

	}

}
