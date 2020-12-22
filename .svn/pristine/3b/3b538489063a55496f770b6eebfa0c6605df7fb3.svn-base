package com.rsaame.pas.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.vo.cmn.CommonVO;

public class AuditLogger {

	public final static String USER_ID = "userid";
	public final static String ACTIVITY = "activity";
	
	private static String FQCN =  "com.rsaame.pas.web.AuditLogger";
    
    /**
     * @param userid
     * @param activity
     * @param activityDetail
     * @param request
     */
    public static void info (String userid, String activity, String activityDetail, HttpServletRequest request) {
    	String appFlow = null;
    	String lob = null;
    	String action = null;
    	CommonVO commonVO = null;
    	StringBuilder activityDtl = new StringBuilder();
    	 
           try {
			   MDC.put(USER_ID, userid);
			   MDC.put(ACTIVITY, activity);
			   
			   if(!Utils.isEmpty(request)){
				   
				   
				   appFlow = (String) request.getParameter( "appFlow" );
				   lob = (String) request.getParameter( "LOB" );
				   action = request.getParameter( "action" );
				   
					PolicyContext policyContext = PolicyContextUtil.getPolicyContext( request );
					if (!Utils.isEmpty(policyContext)) {
						commonVO = policyContext.getCommonDetails();
						
						if(!Utils.isEmpty(commonVO)){
							
							   activityDtl.append("| policy : ");
							   activityDtl.append(commonVO.getPolicyNo());
							   activityDtl.append(" |");
							   
							   activityDtl.append("| quote : ");
							   activityDtl.append(commonVO.getQuoteNo());
							   activityDtl.append(" |");
							   
							   activityDtl.append("| endtId : ");
							   activityDtl.append(commonVO.getEndtId());
							   activityDtl.append(" |");
							   
							   activityDtl.append("| endtNumber : ");
							   activityDtl.append(commonVO.getEndtNo());
							   activityDtl.append(" |");
							
						}
					}
					
				if (!Utils.isEmpty(appFlow)) {
					   activityDtl.append("| appFlow : ");
					   activityDtl.append(appFlow);
					   activityDtl.append(" |");
				   }
				   
				   if(!Utils.isEmpty(lob)){
					   activityDtl.append("| lob : ");
					   activityDtl.append(lob);
					   activityDtl.append(" |");
				   }
				   
				   if(!Utils.isEmpty(action)){
					   activityDtl.append("| action : ");
					   activityDtl.append(action);
					   activityDtl.append(" |");
				   }
				   
				   
				   activityDtl.append(activityDetail);
				   
				   Logger.getLogger(AuditLogger.class.getName()).log(FQCN, Level.INFO,  activityDtl.toString(), null);

			   } else {
				   Logger.getLogger(AuditLogger.class.getName()).log(FQCN, Level.INFO,  activityDetail, null);
			   }
			   
			   
		} finally {
			   MDC.remove(USER_ID);
	           MDC.remove(ACTIVITY);
		}

         
    }
}
