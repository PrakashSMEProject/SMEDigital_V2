/**
 * 
 */
package com.rsaame.pas.kaizen.vo;

import java.util.Map;

import com.rsaame.kaizen.framework.model.ServiceContext;

/**
 * @author m1014644
 *
 */
public class PASServiceContext extends ServiceContext {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static String CXT_PAS_REPORTS_APP_CTX_PATH ;
	
    /**
     * This method will set the pasreports.war's application context path.
     * @param ctxPath <code>String</code>
     */
    public static void setPasreportsCtxPath(String ctxPath) {
    	CXT_PAS_REPORTS_APP_CTX_PATH = ctxPath;
    }
    
    
    /**
     * This method will set the pasreports.war's application context path.
     * @param ctxPath <code>String</code>
     */
    public static String getPasreportsCtxPath() {
    	return CXT_PAS_REPORTS_APP_CTX_PATH;
    }
	
	
	

}
