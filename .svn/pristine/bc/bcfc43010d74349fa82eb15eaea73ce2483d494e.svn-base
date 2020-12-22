/**
 * 
 */
package com.rsaame.pas.homeInsurance.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.exception.SystemException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Redirection;
import com.mindtree.ruc.mvc.Response;
import com.mindtree.ruc.mvc.constants.Constants;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.ui.cmn.Action;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.vo.bus.CoverDetails;
import com.rsaame.pas.vo.bus.CoverDetailsVO;
import com.rsaame.pas.vo.bus.StaffDetailsVO;

/**
 * @author Sarath Varier
 *
 */
public class HomeInsurancePageRH implements IRequestHandler{

	private static final Logger LOGGER = Logger.getLogger( HomeInsurancePageRH.class );
	private static final String HOME_INSURANCE_PAGE_RH = "homeRiskCoverRH"; 
	
	@Override
	public Response execute(HttpServletRequest request,
			HttpServletResponse responseObj) {
		
		Response response = new Response();
		HttpSession session = request.getSession(false);
		UserProfile userProfile = new UserProfile();
		
		if(Utils.isEmpty(session.getAttribute(AppConstants.SESSION_USER_PROFILE_VO)))
		{
			session.setAttribute(AppConstants.SESSION_USER_PROFILE_VO, userProfile);
		}
		
		PolicyContext policyContext = PolicyContextUtil.getPolicyContext(request);
		
		if( Utils.isEmpty( policyContext ) ){
			throw new SystemException( "pas.cmn.policyContextUnavailable", null, "PolicyContext has not been initialised" );
		}

		/* Get the action from the request. If it has not been set, default to "LOAD_SCREEN". */
		String actionAttr = request.getParameter( AppConstants.ACTION );
		String opType = request.getParameter( AppConstants.OPERATIONTYPE);
		actionAttr = Utils.isEmpty( actionAttr ) ? "LOAD_SCREEN" : actionAttr;
		Action action = Action.valueOf( actionAttr );
		
		String referralAction = (String) request.getAttribute( AppConstants.REQ_ATTR_REFERRAL_ACTION );
		if( !Utils.isEmpty( referralAction ) && referralAction.equalsIgnoreCase( AppConstants.REQ_ATTR_REFERRAL_ACTION ) ){
			action = action.equals( Action.SAVE ) ? Action.LOAD_SCREEN : action;
		}
		
		/*Ticket 154656 | Enable/Disable VAT changes in e-platform with a single DB flag change*/
		DAOUtils.checkVatEnabled(request);
		request.setAttribute( AppConstants.REQ_ATTR_CURR_ACTION, action.name() );
		request.setAttribute( "LOB", policyContext.getCommonDetails().getLob().toString());
		switch( action ){
			case SAVE:
				response = handleSaveAction( request, responseObj, policyContext, "HOME_INSURANCE_SAVE" );
				break;
			case PREVIOUS:
	//			response = handleNextPreviousAction( request, response, policyContext, Boolean.FALSE );
				break;
			case REJECT_QUOTE:
	//			response = handleAddLocationAction( request, response, policyContext );
				break;
			case WORDINGS:
	//			response = handleCalculatePremiumAction( request, response, policyContext );
				break;
			case LOAD_SCREEN:
				response = handleLoadScreenAction( request, responseObj, policyContext, opType );
				break;
			case LOAD_DATA:
				response = handleLoadDataAction( request, responseObj, policyContext, "HOME_INSURANCE_LOAD" );
				break;
			case CLOSE:
	//			response = handleFetchPPDataAction( request, response, policyContext );
				break;
			case LIST_ITEM_SAVE:
				response = handleListItemSaveAction( request, responseObj, policyContext, opType );
				break;
			case LIST_ITEM_SCREEN:
				response = handleListItemLoadAction( request, responseObj, policyContext, opType );
				break;
			case POPULATE_PREMIUM:
				response = handlePopulatePremiumAction(request,responseObj, "HOME_INSURANCE_SAVE");
				break;
			case CAPTURE_STAFF_DETAILS:
				response = handleCaptureStaffDetails( request, responseObj, policyContext, opType );
				break;
			case SAVE_STAFF_DETAILS:
				response = handleSaveStaffDetails( request, responseObj, policyContext, opType );
				break;
			default:
				break;
		}
				
		LOGGER.debug("Exiting from Home Insurance Page RH after " + action + "action");
		return response;
	}
	
	/**
	 * @param request
	 * @param responseObj
	 * @param policyContext
	 * @param opType
	 * @return
	 *  
	 *  This method is executed on change of value of SI in home risk page
	 */
	private Response handlePopulatePremiumAction( HttpServletRequest request, HttpServletResponse responseObj, String opType ){
		
		return executeRequestHandler( opType, request, responseObj );
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param policyContext
	 * @param opType
	 * @return
	 */
	private Response handleSaveAction( HttpServletRequest request, HttpServletResponse response, PolicyContext policyContext, String opType ){
		
		return executeRequestHandler( opType, request, response );
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param policyContext
	 * @param opType
	 * @return
	 */
	private Response handleLoadScreenAction( HttpServletRequest request, HttpServletResponse response, PolicyContext policyContext, String opType ){
		return new Response();
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param policyContext
	 * @param opType
	 * @return
	 */
	private Response handleLoadDataAction( HttpServletRequest request, HttpServletResponse response, PolicyContext policyContext, String opType ){
		return executeRequestHandler( opType, request, response );
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param policyContext
	 * @param opType
	 * @return
	 */
	private Response handleListItemLoadAction( HttpServletRequest request, HttpServletResponse response, PolicyContext policyContext, String opType ){
		
		String sumInsured = null;
		String codes = null;
		CoverDetails coverDetails = null;
		Response res = null;
		String [] covercodes = null;
		short ccode = 0;
		Integer risktype = null;
		Integer riskcat = null;
		List<CoverDetailsVO> coverList = null;
		
		try{
			res = new Response();
			Redirection redirection = new Redirection("/jsp/quote/listItemContents.jsp",Redirection.Type.TO_JSP);
			res.setRedirection( redirection );

			request.setAttribute(AppConstants.FUNTION_NAME,  policyContext.getAppFlow().toString() );
			
			sumInsured = request.getParameter("contentsuminsured");
			codes = request.getParameter("covercodes");
			if(sumInsured != null && codes != null)
			{
				request.setAttribute("suminsured", sumInsured);
				request.setAttribute("covercode", codes);
				covercodes = codes.split("~");
				ccode = Short.parseShort( covercodes[0] );
				//basicriskcode = covercodes[1];
				//riskcode = covercodes[2];
				risktype = Integer.valueOf( covercodes[3] );
				riskcat = Integer.valueOf( covercodes[4] );		//Always 2 for list items
				coverDetails = policyContext.getCoverDetails();
				if (!Utils.isEmpty( coverDetails )){
					coverList = new ArrayList<CoverDetailsVO>();
					for (CoverDetailsVO cover : coverDetails.getCoverDetails()){
						if ((cover.getRiskCodes().getRiskType().equals( risktype)) &&
								(cover.getRiskCodes().getRiskCat().equals(riskcat)) &&
								(cover.getCoverCodes().getCovCode() == ccode)){
							coverList.add( cover );
							
						}
					}
				}
			}
			request.setAttribute("coverList", coverList);
		}
		catch(Exception exp){
			exp.printStackTrace();
		}
		return res;
	}

	/**
	 * 
	 * @param request
	 * @param responseObj
	 * @param policyContext
	 * @param opType
	 * @return
	 */
	private Response handleListItemSaveAction( HttpServletRequest request, HttpServletResponse responseObj, PolicyContext policyContext, String opType ){
		 
		CoverDetails coverDetailsFromReq = null;
		CoverDetails coverDetailsToReq = null;
		Integer riskType = null;
		short coverCode = 0;

		coverDetailsFromReq = (CoverDetails) policyContext.getCoverDetails();
		coverDetailsToReq = BeanMapper.map(request, CoverDetails.class);
		if(!Utils.isEmpty( coverDetailsToReq )){
			riskType = coverDetailsToReq.getCoverDetails().get( 0 ).getRiskCodes().getRiskType();
			coverCode = coverDetailsToReq.getCoverDetails().get( 0 ).getCoverCodes().getCovCode();
		}
		if(!Utils.isEmpty( coverDetailsFromReq ) && (!Utils.isEmpty( coverDetailsFromReq.getCoverDetails() )) ){
			Iterator<CoverDetailsVO> itr = coverDetailsFromReq.getCoverDetails().iterator();
			while(itr.hasNext()){
				CoverDetailsVO cover = (CoverDetailsVO) itr.next();
				if((cover.getRiskCodes().getRiskType().equals( riskType )) && (cover.getCoverCodes().getCovCode()== coverCode)){
					itr.remove();
				}
			}
		}
			
		if(coverDetailsFromReq != null){
			coverDetailsToReq.getCoverDetails().addAll( coverDetailsFromReq.getCoverDetails() );
		}
		policyContext.setCoverDetails( coverDetailsToReq );
		Response res = new Response();
		return res;
	}

	
	/**
	 * This method is used to execute Request Handler for a given opType
	 * 
	 * @param opType
	 * @param request
	 * @param response
	 * @return
	 */
	private Response executeRequestHandler( String opType, HttpServletRequest request, HttpServletResponse response ){

		String rhBeanName = Utils.getSingleValueAppConfig( Constants.APP_CONFIG_REQ_HANDLER_PREFIX + opType );
		IRequestHandler rh = null;
		if( !Utils.isEmpty( rhBeanName ) ){
			rh = (IRequestHandler) Utils.getBean( rhBeanName );
		}

		return Utils.isEmpty( rh ) ? null : rh.execute( request, response );
	}
	
	private Response handleCaptureStaffDetails(HttpServletRequest request,
			HttpServletResponse responseObj, PolicyContext policyContext,
			String opType) {
		String staffCount = null;
		String codes = null;
		CoverDetails coverDetails = null;
		Response res = null;
		String [] covercodes = null;
		short ccode = 0;
		Integer risktype = null;
		Integer riskcat = null;
		List<CoverDetailsVO> coverList = null;
		List<StaffDetailsVO> staffDetailsFromReq = null;
		try{
			res = new Response();
			Redirection redirection = new Redirection("/jsp/quote/staffDetails.jsp",Redirection.Type.TO_JSP);
			res.setRedirection( redirection );

			request.setAttribute(AppConstants.FUNTION_NAME,  policyContext.getAppFlow().toString() );
			
			staffCount = request.getParameter("staffCount");
			codes = request.getParameter("covercodes");
			if(staffCount != null && codes != null)
			{
				request.setAttribute("staffCount", staffCount);
				if( staffCount.equals("0") ){
					policyContext.setStaffDetailsVO(null);
				}
				request.setAttribute("covercode", codes);
				covercodes = codes.split("~");
				ccode = Short.parseShort( covercodes[0] );
				//basicriskcode = covercodes[1];
				//riskcode = covercodes[2];
				risktype = Integer.valueOf( covercodes[3] );
				riskcat = Integer.valueOf( covercodes[4] );		//Always 2 for list items
				coverDetails = policyContext.getCoverDetails();
				if (!Utils.isEmpty( coverDetails )){
					coverList = new ArrayList<CoverDetailsVO>();
					for (CoverDetailsVO cover : coverDetails.getCoverDetails()){
						if ((cover.getRiskCodes().getRiskType().equals( risktype)) &&
								(cover.getRiskCodes().getRiskCat().equals(riskcat)) &&
								(cover.getCoverCodes().getCovCode() == ccode)){
							coverList.add( cover );
							
						}
					}
				}
			}
			staffDetailsFromReq = (List<StaffDetailsVO>) policyContext.getStaffDetailsVO();
			request.setAttribute("coverList", coverList);
			request.setAttribute("staffDetails", staffDetailsFromReq);
		}
		catch(Exception exp){
			exp.printStackTrace();
		}
		return res;
	}
	
	/**
	 * 
	 * @param request
	 * @param responseObj
	 * @param policyContext
	 * @param opType
	 * @return
	 */
	private Response handleSaveStaffDetails( HttpServletRequest request, HttpServletResponse responseObj, PolicyContext policyContext, String opType ){
		 
		CoverDetails staffDetailsToReq = null;
		staffDetailsToReq = BeanMapper.map(request, CoverDetails.class);
		
		policyContext.setStaffDetailsVO( staffDetailsToReq.getStaffDetails() );
		Response res = new Response();
		return res;
	}

}
