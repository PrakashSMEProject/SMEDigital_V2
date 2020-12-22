/**
 * 
 */
package com.rsaame.pas.referral.ui;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.springframework.dao.DataAccessException;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.vo.cmn.CommonVO;

/**
 * @author M1012799
 *
 */
public class ConsolidatedReferralMessageTagHandler extends SimpleTagSupport{

	
	/*
	 * 
	 * @see javax.servlet.jsp.tagext.SimpleTagSupport#doTag()
	 */
	public void doTag() throws IOException {

		PageContext pageContext=(PageContext)getJspContext();  
		JspWriter out = pageContext.getOut();
		
		/*Fetch the details from policy context and forward the same through attribute list to the renderer.*/
		PolicyContext context = PolicyContextUtil.getPolicyContext((HttpServletRequest) pageContext.getRequest());
		CommonVO commonVO = context.getCommonDetails();
		
		ConsolidatedReferralMessageRenderer consolidatedReferralMessageRenderer = new ConsolidatedReferralMessageRenderer();
		
		HashMap<String,Object> attributeList = new HashMap<String,Object>(); 
		try {
			if ( !Utils.isEmpty(commonVO) ) {
				
				attributeList.put( AppConstants.POLICY_ID, commonVO.getPolicyId() );
				attributeList.put( AppConstants.ENDT_ID, commonVO.getEndtId() );
				attributeList.put( AppConstants.OUT, out );
				//attributeList.put( AppConstants.SECTION_ID, commonVO.getS );
				attributeList.put( AppConstants.COMMON_VO, commonVO );
				
				Map<String,Map<String,String>> referralData = (Map<String,Map<String,String>>) pageContext.getSession().getAttribute( "ReferralMap" );
				attributeList.put( AppConstants.REFERRAL_VO, referralData );
				
				String nextAction = (String)pageContext.getRequest().getAttribute( "nextAction" );
				attributeList.put( "nextAction", nextAction );
				consolidatedReferralMessageRenderer.buildHTMLContent( attributeList );
				
			}
		} catch (DataAccessException dataAccessException) {
			dataAccessException.printStackTrace();
		} catch (Exception exception) {
			consolidatedReferralMessageRenderer.buildEmptyControl(out);
		}	
		
	}
}
