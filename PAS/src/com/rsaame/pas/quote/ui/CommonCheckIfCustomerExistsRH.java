package com.rsaame.pas.quote.ui;

/**
 * @author m1017029
 * since Phase 3
 */
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Redirection;
import com.mindtree.ruc.mvc.Redirection.Type;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.insured.svc.InsuredDetailsSvc;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.vo.bus.LOB;
import com.rsaame.pas.vo.bus.PolicyDataVO;

public class CommonCheckIfCustomerExistsRH implements IRequestHandler{

	public static final String GENERAL_INFO_SAVE = "COMMON_GENERALINFO_PAGE_SAVE";
	public static final String GENERAL_INFO_SAVE_MONOLINE = "COMMON_FUNCTIONALITY&checkIfInsuredExistsFlag=false";
	private static final String CUSTOMER_VALIDATION_POPUP_JSP = "/jsp/quote/customerValidation.jsp";
	
	/* (non-Javadoc)
	 * @see com.mindtree.ruc.mvc.IRequestHandler#execute(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public Response execute( HttpServletRequest request, HttpServletResponse response ){
		Response resp = new Response();

		String identifier = request.getParameter( "opType" );
		PolicyContext policyContext = PolicyContextUtil.getPolicyContext( request );

		//Map values from request to PolicyDataVO
		PolicyDataVO policyDataVO = BeanMapper.map( request, PolicyDataVO.class );
		if( !Utils.isEmpty( policyContext ) && !Utils.isEmpty( policyContext.getCommonDetails() ) ){
			policyDataVO.setCommonVO( policyContext.getCommonDetails() );
		}

		/* To identify the action performed by the user on the screen i.e. SAVE & NEXT button click
		 */
		String action = request.getParameter( "action" );
		if( !Utils.isEmpty( action ) ){
			request.setAttribute( "action", action );
		}
		Boolean validationRequired = true;
		String insValidation = request.getParameter( "insValidation" );
		if(!Utils.isEmpty( insValidation )){
			PolicyDataVO policy = new PolicyDataVO();
			policy = SvcUtils.mapGeneralInfoVO( policyDataVO.getCommonVO().getLob().toString(), request ,policyDataVO.getCommonVO() );
			policyDataVO.getGeneralInfo().getInsured().setInsuredCode( policy.getGeneralInfo().getInsured().getInsuredCode() );
			TaskExecutor.executeTasks( "INSURED_CHANGE_CHECK", policyDataVO );
			if(!policyDataVO.isInsuredChanged()){
				validationRequired = false;
			}
		}
		if(validationRequired){
			//Invoking CheckIfInsuredExistsDao, to check if the insured already exists.
			try{
				TaskExecutor.executeTasks( identifier, policyDataVO );
			}catch(BusinessException e){
				List<String> errorList = e.getErrorKeysList();
				
				if(errorList.contains( "customer_exists" )){
					response.setHeader( "isCustomerExist", "true" );
				}
				Redirection redirection = new Redirection( CUSTOMER_VALIDATION_POPUP_JSP, Type.TO_JSP );
				resp.setRedirection( redirection );
				return resp;
			}
		}
		//Control reaches here only if customer in request is not an existing customer
		//So redirect to Common General Info Save
		Redirection redirection = null;
		if(!policyDataVO.getCommonVO().getLob().toString().equalsIgnoreCase(LOB.HOME.toString()) 
				&& !policyDataVO.getCommonVO().getLob().toString().equalsIgnoreCase(LOB.TRAVEL.toString())){
			
			if("COPY_QUOTE".equals(action)){
				policyDataVO.getCommonVO().setQuoteNo(Long.valueOf(request.getParameter("oldQuoteNo")));
				InsuredDetailsSvc insuredSvc = (InsuredDetailsSvc) Utils.getBean( "insuredDetailsSvc" );
				policyDataVO = (PolicyDataVO) insuredSvc.invokeMethod( SvcConstants.SAVE_INSURED, policyDataVO );
				 redirection = new Redirection( "COPY_QUOTE_COMMON&action=COPY_TO_SAME_INSURED&oldPolPoliocyId="+request.getParameter("quote_polPolicyId_cpquote")+"&insuredCode="+policyDataVO.getGeneralInfo().getInsured().getInsuredCode()+"&policyType="+request.getParameter("policyType"), Type.TO_NEW_OPERATION );
			}
			else{
				PolicyContextUtil.setPolicyContext( request,policyContext );
			
				request.setAttribute("checkIfInsuredExistsFlag","false") ;
			
				redirection = new Redirection( GENERAL_INFO_SAVE_MONOLINE, Type.TO_NEW_OPERATION );
			}
		}else{
			redirection = new Redirection( GENERAL_INFO_SAVE, Type.TO_NEW_OPERATION );
		}
		
		resp.setRedirection( redirection );

		return resp;
	}

}
