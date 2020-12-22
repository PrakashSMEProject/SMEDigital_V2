package com.rsaame.pas.quote.svc;

import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.rsaame.pas.par.svc.ParSvc;
import com.rsaame.pas.quote.dao.ISaveClaimsHistDAO;
import com.rsaame.pas.vo.bus.ClaimsSummaryVO;
import com.rsaame.pas.vo.bus.GeneralInfoVO;
import com.rsaame.pas.vo.bus.PolicyVO;

public class SaveClaimsHistSvc extends BaseService{

	ISaveClaimsHistDAO claimsHistoryDAO;




	
	@Override
	public Object invokeMethod(String methodName, Object... args) {
		BaseVO returnValue = null;
		if( "saveClaimsHistory".equals( methodName ) ){
			returnValue = saveClaimsHistory( (BaseVO) args[ 0 ] );
		}
		return returnValue;
	}
	
	private BaseVO saveClaimsHistory(BaseVO baseVO) {
		//BaseVO returnValue = claimsHistoryDAO.saveClaimsHistory(baseVO);
		claimsHistoryDAO.saveClaimsHistory(baseVO);
		return baseVO;
	}
	
	public void setClaimsHistoryDAO(ISaveClaimsHistDAO claimsHistoryDAO) {
		this.claimsHistoryDAO = claimsHistoryDAO;
	}
	
	
public static void main(String[] args) {
		
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config/spring-config.xml");
		ParSvc pasSvc = (ParSvc) applicationContext.getBean("saveClaimsHistoryService");
		PolicyVO policyVO = new PolicyVO(); 
		//SchemeVO schemeVO = new SchemeVO();
		//schemeVO.setSchemeCode(908);
		//schemeVO.setTariffCode(11);
		//policyVO.setScheme(schemeVO);
		//pol
		GeneralInfoVO generalInfoVO = new GeneralInfoVO();
		policyVO.setGeneralInfo(generalInfoVO);
		
		ClaimsSummaryVO claimsSummary = new ClaimsSummaryVO();
		
		claimsSummary.setLossExp(Integer.valueOf(1));
		policyVO.getGeneralInfo().setClaimsHistory(claimsSummary);
		policyVO.setPolicyNo(new Long("479990"));
		policyVO.setValidityStartDate(new Date());
		pasSvc.invokeMethod("saveClaimsHistory",policyVO);	
		System.out.println("success");
		
	}
}
