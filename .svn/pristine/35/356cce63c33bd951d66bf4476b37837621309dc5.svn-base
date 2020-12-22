/**
 * 
 */
package com.rsaame.pas.quote.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.beanmap.BeanMapperFactory;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Redirection;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.request.vo.mapper.RequestToPolicyVOMapper;
import com.rsaame.pas.rules.mapper.RulesConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.PremiumVO;

/**
 * @author M1012290
 *
 */
public class CheckIfCustomerExistsRH implements IRequestHandler{

	private static final Logger logger = Logger.getLogger( CheckIfCustomerExistsRH.class );
	private static String CHECK_IF_CUSTOMER_EXISTS = "CheckIfCustomerExistsRH";
	private static String SAVE_OPERATION_OP_TYPE = "QUOTE_SAVE_GEN_INFO";
	@Override
	public Response execute(HttpServletRequest request,HttpServletResponse response) {
		
		
		Response responseObj = new Response();
		
		

			/* Identifier will be used further in TaskExecutor to identify request processing */
			String identifier = request.getParameter("opType");
			
			
			/* As part of processing convert the HTTP request object
			 * obtained to required VO by using request to bean mapper available as part of framework
			 */
			
			PolicyContext policyContext=PolicyContextUtil.getPolicyContext( request );
			PolicyVO policyVO = policyContext.getPolicyDetails();
			
			BaseBeanToBeanMapper<HttpServletRequest,PolicyVO> requestBeanMapper = (BaseBeanToBeanMapper)BeanMapperFactory.getMapperInstance(RequestToPolicyVOMapper.class);
			

			policyVO = (PolicyVO)requestBeanMapper.mapBean(request, policyVO);
			
			
			/* Once the VO is instantiated and populated with request values, call task executor to perform tasks to be performed */
			policyVO = (PolicyVO) TaskExecutor.executeTasks(identifier, policyVO);
			
			/* To identify the action performed by the user on the screen i.e. SAVE & NEXT button click
			 */
			String action = request.getParameter("action");
			/*String policyFee = SvcUtils.getLookUpDescription( RulesConstants.POLICY_FEE, RulesConstants.LOOKUP_LEVEL1,RulesConstants.LOOKUP_LEVEL2, 
					policyVO.getScheme().getSchemeCode() );
			
			if( !Utils.isEmpty(policyFee) )
			 {
				 if( Utils.isEmpty( policyVO.getPremiumVO()) )
				 {
					 policyVO.setPremiumVO( new PremiumVO() );
				 }
				policyVO.getPremiumVO().setPolicyFees(Double.valueOf(policyFee));
			 }*/
			
			
			if(!Utils.isEmpty(action)){
				request.setAttribute("action",action);
			}
			
			Redirection redirection = new Redirection(SAVE_OPERATION_OP_TYPE, Redirection.Type.TO_NEW_OPERATION);
			responseObj.setRedirection(redirection);
				
			/* Set the response obtained to Policy Context so that next sections can obtain the value using policy context */
			PolicyContext polContext = PolicyContextUtil.getPolicyContext(request);
			logger.debug(CHECK_IF_CUSTOMER_EXISTS,"polContext obtained"+polContext);
			polContext.setPolicyDetails(policyVO);
				
	return responseObj;
	}

}