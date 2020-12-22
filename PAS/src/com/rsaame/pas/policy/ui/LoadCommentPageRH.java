/**
 * 
 */
package com.rsaame.pas.policy.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.vo.bus.PolicyVO;

/**
 * @author M1017029
 *
 */
public class LoadCommentPageRH implements IRequestHandler{

	/* (non-Javadoc)
	 * @see com.mindtree.ruc.mvc.IRequestHandler#execute(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public Response execute( HttpServletRequest httpRequest, HttpServletResponse httpdRresponse ){
		Response response = new Response();
        String operationType=httpRequest.getParameter( "action" );
        PolicyContext policyContext = PolicyContextUtil.getPolicyContext( httpRequest );
        PolicyVO policyVO = policyContext.getPolicyDetails();
        if( (operationType.equals( "APPROVE_QUOTE" ) || operationType.equals("APPROVE_POLICY"))&& !Utils.isEmpty( policyVO)){
              
              if( !Utils.isEmpty( policyVO.getPremiumVO() ) ){
                    BeanMapper.map( httpRequest, policyVO.getPremiumVO() );
                    mapTradeLicNo( httpRequest, policyVO );
              }
        }
        
        httpRequest.setAttribute( "action", operationType );
        return response;
  }
  private void mapTradeLicNo(HttpServletRequest request, PolicyVO policyVO) {
        
        /* Mapping: "quote_name_tradelicno" -> "generalInfo.insured.tradeLicenseNo" */
        if( !Utils.isEmpty( request.getParameter( "quote_name_tradelicno" ) ) ){
             policyVO.getGeneralInfo().getInsured().setTradeLicenseNo( request.getParameter( "quote_name_tradelicno" ) ); 
        }else{
             policyVO.getGeneralInfo().getInsured().setTradeLicenseNo(  null );
       }
        
  }


}
