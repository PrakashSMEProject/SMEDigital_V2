/**
 * 
 */
package com.rsaame.pas.util;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.lookup.svc.LookUpService;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.app.LookUpListVO;
import com.rsaame.pas.vo.app.LookUpVO;
import com.rsaame.pas.vo.bus.LOB;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.cmn.CommonVO;

/**
 * @author m1014644
 * 
 * This class will be used to manage the policy context. 
 * Policy context must not be accessed directly from the session
 *
 */
public class PolicyContextUtil {
	
	
	/**
	 * @param request
	 * @param policyContext
	 */
	public static void setPolicyContext(HttpServletRequest request,PolicyContext policyContext)
	{
		request.getSession(false).setAttribute(AppConstants.POLICY_CONTEXT, policyContext);
	}
	
	/**
	 * @param request
	 */
	public static void deleteContext(HttpServletRequest request)
	{
		request.getSession(false).removeAttribute(AppConstants.POLICY_CONTEXT);
	}
	
	/**
	 * @param request
	 * @return
	 */
	public static PolicyContext getPolicyContext(HttpServletRequest request)
	{
		return (PolicyContext) request.getSession(false).getAttribute(AppConstants.POLICY_CONTEXT);
	}

	/**
	 * @param request
	 * @param appFlow
	 */
	public static void createPolicyContext(HttpServletRequest request , String appFlow)
	{
		/* The flow is mandatory for creating policyContext. 
		 * This is to make sure policy context is aware of the app flow*/
		PolicyContext policyContext = new PolicyContext(Flow.valueOf( appFlow ));
		
		/* Load all available sections for the policy type. */
		loadAllSectionsForPolicyType( request, policyContext );
		
		PolicyVO policy = new PolicyVO();
		policyContext.setPolicyDetails( policy );
		
		request.getSession(false).setAttribute(AppConstants.POLICY_CONTEXT, policyContext);
	}
	
	/**
	 * @param request
	 * @param appFlow
	 */
	public static void createPolicyContext(HttpServletRequest request , String appFlow, LOB lob)
	{
		/* The flow is mandatory for creating policyContext. 
		 * This is to make sure policy context is aware of the app flow*/
		PolicyContext policyContext = new PolicyContext(Flow.valueOf( appFlow ));
		
		CommonVO commonVO = new CommonVO();
		commonVO.setLob( lob );
		policyContext.setCommonDetails( commonVO );
		
		request.getSession(false).setAttribute(AppConstants.POLICY_CONTEXT, policyContext);
	}
	
	/**
	 * @param request
	 * @param pc
	 */
	public static void loadAllSectionsForPolicyType( HttpServletRequest request, PolicyContext pc ){
		LookUpVO lookUpVO = new LookUpVO();
		lookUpVO.setCategory( "SBS_SECTIONS" ); /* TODO Introduce Policy Type based category. */
		lookUpVO.setLevel1( "ALL" );
		lookUpVO.setLevel2( "ALL" );
		LookUpService lookUpService = getLookUpService();
		LookUpListVO lookUpListVo = (LookUpListVO) lookUpService.getListOfDescription( lookUpVO );

		Integer[] sections = null;
		if( !Utils.isEmpty( lookUpListVo ) && !Utils.isEmpty( lookUpListVo.getLookUpList() ) ){
			sections = new Integer[ lookUpListVo.getLookUpList().size() ];
			int i = 0;
			for( LookUpVO lookUpVOfromDB : lookUpListVo.getLookUpList() ){
				if( !Utils.isEmpty( lookUpVOfromDB ) ){
					sections[ i ] = lookUpVOfromDB.getCode().intValue();
					i = i + 1;
				}
			}
		}
		if( !Utils.isEmpty( sections ))		/* Added if condition for null check of sections - sonar violation fix */
		Arrays.sort( sections );
		pc.populateAllAvailableSec( sections );
	}
	
	/**
	 * @return
	 */
	private static LookUpService getLookUpService(){
		return (LookUpService) Utils.getBean( "lookUpService" );
	}
}
