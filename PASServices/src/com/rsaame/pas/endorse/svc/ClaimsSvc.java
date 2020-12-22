package com.rsaame.pas.endorse.svc;

import com.rsaame.pas.lookup.svc.BaseService;
import com.rsaame.pas.quote.dao.IClaimsDAO;


public class ClaimsSvc extends BaseService {	
	
	private IClaimsDAO claimsDAO;

	public IClaimsDAO getClaimsDAO() {
		return claimsDAO;
	}

	public void setClaimsDAO(IClaimsDAO claimsDAO) {
		this.claimsDAO = claimsDAO;
	}	
	
	public  boolean checkClaimsExistForPolicyNumber(long policyNumber){
		return claimsDAO.checkClaimsExistForPolicyNumber(policyNumber);
	}
	
	public  boolean checkClaimsExistForInsured(String insuredId){
		return claimsDAO.checkClaimsExistForInsured(insuredId);		
	}
}
