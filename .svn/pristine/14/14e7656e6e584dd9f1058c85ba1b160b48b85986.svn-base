package com.rsaame.pas.endorsement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.beanmap.BeanMapperFactory;
import com.mindtree.ruc.cmn.beanmap.ConverterFactory;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Redirection;
import com.mindtree.ruc.mvc.Redirection.Type;
import com.mindtree.ruc.mvc.Response;
import com.mindtree.ruc.mvc.constants.Constants;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.endorse.svc.ClaimsSvc;
import com.rsaame.pas.request.vo.mapper.RequestToPolicyVOMapper;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.SectionVO;

public class AmendPolicyRH implements IRequestHandler{
	private static final Logger log = Logger.getLogger( AmendPolicyRH.class );

	private static final String OPTYPE_SECTION_RH = "SECTION";

	@Override
	public Response execute( HttpServletRequest request, HttpServletResponse response ){

		
		Response responseObj = new Response();
		PolicyContext policyContext = PolicyContextUtil.getPolicyContext( request );
		PolicyVO policyVO = policyContext.getPolicyDetails();
		BaseVO baseVO = null;
		
		
			
		String action = request.getParameter("action");
		
		if(action.equals("GET_REFUND_PREMIUM")) {
			
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "",
			"format=yyyy/MM/dd" );
			policyVO.setEndEffectiveDate( converter.getAFromB( request.getParameter( "polExpiryDate" ) ) );
	
			policyVO.setAppFlow( Flow.AMEND_POL );
			request.setAttribute( "amendAction", "GET_REFUND_PREMIUM" );	
			policyContext.setPolicyCancelled( true );
			Redirection redirection = new Redirection( "PREMIUM_PAGE", Type.TO_NEW_OPERATION );
			responseObj.setRedirection( redirection );
			
		}
		else if( action.equals( "AMEND_POLICY_STATUS_CHECK" ) ){

			if( !Utils.isEmpty( request.getParameter( "ruleInfo" ) ) ){
				 
				UserProfile userProfile = (UserProfile) request.getSession().getAttribute( AppConstants.SESSION_USER_PROFILE_VO );
				if( !Utils.isEmpty( userProfile ) && !Utils.isEmpty( userProfile.getRsaUser() )
						&& !Utils.isEmpty( userProfile.getRsaUser().getProfile() ) ){
						if(userProfile.getRsaUser().getProfile().equalsIgnoreCase( "Broker" )){
							responseObj.setData( "CANCEL_RULE_FAIL" );
							return responseObj;
						}
				}
				
				
				// check if the policy is having claims history; call the rule is yes
				if(policyVO.getIsQuote()){
					Long policyNo = policyVO.getPolicyNo();
					
					if(!Utils.isEmpty(policyNo)){
						ClaimsSvc claimsService = (ClaimsSvc) Utils.getBean( "claimsSvc" );
						if(claimsService.checkClaimsExistForPolicyNumber(policyNo)){
							//Call Rule				
							if(!AppUtils.allowQuoteCreation(request,responseObj,policyVO)){
								return responseObj;
							}				
						}		
					}
				}				
				
			}

			DataHolderVO<Long[]> outputHolder = (DataHolderVO<Long[]>) TaskExecutor.executeTasks( "AMEND_POLICY_GET_ACTIVE_RECORD", policyVO );
			Long[] endIdNo = outputHolder.getData();
			if( endIdNo[ 0 ].equals( policyVO.getEndtNo() ) && endIdNo[ 1 ].equals( policyVO.getEndtId() ) ){

				baseVO = TaskExecutor.executeTasks( action, policyVO );
				responseObj.setData( baseVO );
				//PolicyVO	policyVOStatus = (PolicyVO)baseVO;
				//if (!policyVOStatus.isInsuredChanged())
				{
					baseVO = TaskExecutor.executeTasks( "AMEND_POLICY_STATUS_PENDING", policyVO );
					responseObj.setData( baseVO );
				}

			}
			else{

				boolean isSelectedPolicyActive = false;
				responseObj.setData( isSelectedPolicyActive );
			}

		}
		else if(action.equals("UPDATE_INS_POL")){
			policyVO = mapRequestToPolicyVO( request, policyVO );
			baseVO =  TaskExecutor.executeTasks(action, policyVO );
			responseObj.setData(baseVO);
		} 
		/* AMEND_EFFECTIVE_DATE_CHECK = action is used for checking if new endt effective date entered is less than previous
		 * endt effective date. 
		 * 
		 */
		else if(action.equals("AMEND_EFFECTIVE_DATE_CHECK")){
			//policyVO = mapRequestToPolicyVO( request, policyVO );
			com.mindtree.ruc.cmn.beanmap.DateStringConverter converter = ConverterFactory.getInstance( com.mindtree.ruc.cmn.beanmap.DateStringConverter.class, "",
			"format=yyyy/MM/dd" );
			policyVO.setEndEffectiveDate( converter.getTypeOfA().cast( converter.getAFromB( request.getParameter( "effDate" ) ) ) );
			if(!Utils.isEmpty( request.getParameter( "endCreateDate" ) ))
			{
				policyVO.setEndStartDate( converter.getTypeOfA().cast( converter.getAFromB( request.getParameter( "endCreateDate" ) )));
			}
			DataHolderVO holder =  (DataHolderVO) TaskExecutor.executeTasks(action, policyVO );
			Object[] data = (Object[]) holder.getData();
			
			//if(data[1].equals(true)){
			/* Commenting the above code as we set the reason in data[1] which is a string.
			 * Reason is added to accommodate 2 validations ( closing date and endt effective date ) on the same field.
			 * */
			if(data[1].equals("")){
				
				 /* if true is set in holder then previous endt effective date is less
				 *  than entered endt effective date.Then set policy in json response.
				 */
				responseObj.setData((PolicyVO)data[0]);
			}else {
				/*  if false is set in holder then previous endt effective date is more
				 *  than entered endt effective date. Then set false in json response.
				 */
				//boolean isEndEffDateLess = false;
				/* Commenting the above code as we set the reason in data[1] which is a string.
				 * Reason is added to accommodate 2 validations ( closing date and endt effective date ) on the same field.
				 * */
				String reason = (String) data[1];
				responseObj.setData(reason);
			}
			
			
			
			
		} else {
			//policyVO = mapRequestToPolicyVO( request, policyVO );
			baseVO =  TaskExecutor.executeTasks(action, policyVO );
		 	responseObj.setData(baseVO);
		}
	
		
		return responseObj;
	}

	/**
	 * @param request
	 * @param policyVO
	 * @return
	 */
	private PolicyVO mapRequestToPolicyVO( HttpServletRequest request, PolicyVO policyVO ){
		BaseBeanToBeanMapper<HttpServletRequest, PolicyVO> requestBeanMapper = (BaseBeanToBeanMapper) BeanMapperFactory.getMapperInstance( RequestToPolicyVOMapper.class );
		policyVO = (PolicyVO) requestBeanMapper.mapBean( request, policyVO );
		return policyVO;
	}

	/**
	 * This is a workaround to know the current location. This is required for the section screens to load the JSON JSP for the 
	 * current section.
	 * @param policyVO
	 * @return
	 *//*
	private String getCurrentRiskGroupId( PolicyVO policyVO ){
		RiskGroup currRG = null;

		if( !Utils.isEmpty( policyVO.getRiskDetails() ) ){
			for( SectionVO section : policyVO.getRiskDetails() ){
				java.util.Set<? extends RiskGroup> rgs = Utils.isEmpty( section.getRiskGroupDetails() ) ? null : section.getRiskGroupDetails().keySet();

				if( Utils.isEmpty( rgs ) ) continue;

				for( RiskGroup rg : rgs ){
					LocationVO l = (LocationVO) rg;
					if( Boolean.TRUE.equals( l.getToSave() ) ){
						currRG = rg;
						break;
					}
				}

				if( !Utils.isEmpty( currRG ) ) break;
			}
		}

		return Utils.isEmpty( currRG ) ? null : currRG.getRiskGroupId();
	}
*/
	/**
	 * This method is used to execute Request Handler for a given opType
	 * 
	 * @param opType
	 * @param request
	 * @param response
	 * @return
	 *//*
	private Response executeRequestHandler( String opType, HttpServletRequest request, HttpServletResponse response ){
		
		 * (a) Based on the passed UIOperationType, get the class name for the IRequestHandler implementation
		 *     from app config.
		 * (b) Load the class and execute the execute() method.
		 * (c) Return the object returned by the call directly. 
		 
		String rhBeanName = Utils.getSingleValueAppConfig( Constants.APP_CONFIG_REQ_HANDLER_PREFIX + opType );
		IRequestHandler rh = null;
		if( !Utils.isEmpty( rhBeanName ) ){
			rh = (IRequestHandler) Utils.getBean( rhBeanName );
		}

		return Utils.isEmpty( rh ) ? null : rh.execute( request, response );
	}*/
}
