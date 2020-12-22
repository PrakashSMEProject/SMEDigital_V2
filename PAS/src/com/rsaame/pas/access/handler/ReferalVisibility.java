/**
 * 
 */
package com.rsaame.pas.access.handler;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.mindtree.ruc.mvc.tags.util.BaseVisibilityRule;
import com.mindtree.ruc.mvc.tags.util.RuleResultScope;
import com.mindtree.ruc.mvc.tags.util.VisibilityLevel;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.query.constants.QueryConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.bus.LOB;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.TaskVO;

/**
 * @author m1014644
 *
 */
public class ReferalVisibility extends BaseVisibilityRule{

	private static final Logger logger = Logger.getLogger( ReferalVisibility.class );
	private static final String TASK_TYPE_ENDORSEMENT = "Endorsement";
	HttpServletRequest request;

	/* (non-Javadoc)
	 * @see com.mindtree.ruc.mvc.tags.util.BaseVisibilityRule#calculateVisibilityLevel(javax.servlet.http.HttpServletRequest, java.util.Map)
	 */
	/**
	 * This visibility rule checks if the form is read - only or not in a referral  flow
	 * SECTION_NAME = "RESOLVE_REFERAL"
	 */
	@Override
	protected VisibilityLevel calculateVisibilityLevel( HttpServletRequest req, Map<String, String> input ){
		this.request = req;
		String sectionName = input.get( AppConstants.SECTION_NAME );

		VisibilityLevel visibilityLevel = VisibilityLevel.EDITABLE;
		PolicyContext context = PolicyContextUtil.getPolicyContext( request );
		if( !Utils.isEmpty( context ) ){

			if( context.getAppFlow().toString().equalsIgnoreCase( sectionName ) ){
				visibilityLevel = visibilityLevel( req );
				TaskVO taskVO = context.getTaskDetails();
				
				if( visibilityLevel == VisibilityLevel.READONLY ){
					request.setAttribute( AppConstants.FUNTION_NAME, "VIEW_QUO" );
					/*
					 * This value is used in RH to determine weather referal resolution is read-only or editable
					 */
					req.getSession( false ).setAttribute( com.Constant.CONST_APPFLOW, Flow.VIEW_QUO );
				}
				else if( visibilityLevel == VisibilityLevel.EDITABLE && !Utils.isEmpty( taskVO ) && !Utils.isEmpty( taskVO.getTaskType() ) && !taskVO.getTaskType().equals( TASK_TYPE_ENDORSEMENT )){
					request.setAttribute( AppConstants.FUNTION_NAME, "EDIT_QUO" );
					/*
					 * This value is used in RH to determine weather referal resolution is read-only or editable
					 */
					req.getSession( false ).setAttribute( com.Constant.CONST_APPFLOW, Flow.EDIT_QUO );
				}
				else if( visibilityLevel == VisibilityLevel.EDITABLE && !Utils.isEmpty( taskVO ) && !Utils.isEmpty( taskVO.getTaskType() ) && taskVO.getTaskType().equals( TASK_TYPE_ENDORSEMENT )){
					request.setAttribute( AppConstants.FUNTION_NAME, "AMEND_POL" );
					/*
					 * This value is used in RH to determine weather referal resolution is read-only or editable
					 */
					req.getSession( false ).setAttribute( com.Constant.CONST_APPFLOW, Flow.AMEND_POL );
				}
				visibilityLevel = (VisibilityLevel) request.getAttribute( AppConstants.CASCADEVISIBILITY);
			}
			else
			{
				visibilityLevel = (VisibilityLevel) request.getAttribute( AppConstants.CASCADEVISIBILITY);
			}
		}
		return visibilityLevel;

	}

	@Override
	protected String getCaseIdentifier( Map<String, String> input ){

		String caseIdentifier = "";
		for( Map.Entry<String, String> identifier : input.entrySet() ){
			caseIdentifier = Utils.concat( "B_" + identifier.getKey(), identifier.getValue() );
			break;
		}
		return caseIdentifier;
	}

	@Override
	protected RuleResultScope getRuleResultScope(){

		RuleResultScope visibilityScope = (RuleResultScope) request.getAttribute( AppConstants.RULE_RESULT_SCOPE );

		return visibilityScope;
	}

	private VisibilityLevel visibilityLevel( HttpServletRequest req ){

		//TaskVO task = (TaskVO) ThreadLevelContext.get( "TASKDETAILS" );
		TaskVO task = (TaskVO)request.getSession().getAttribute( "TASKDETAILS" );
		if(Utils.isEmpty( task ))
		{
			throw new BusinessException( "",null, "TaskVO is empty in resolve referral visibility class" );
		}
		UserProfile userProfile = AppUtils.getUserDetailsFromSession( request );
		if( !Utils.isEmpty( userProfile ) ){
			task.setLoggedInUser( userProfile );
		}
		
		/*
		 * IsQuote is set to differentiate the quote flow and the policy flow and switch the sessionFactory to fetch the task status
		 */
		PolicyContext policyContext = PolicyContextUtil.getPolicyContext( request );
		PolicyVO policyVO = policyContext.getPolicyDetails();
		if(!Utils.isEmpty( policyVO )){
			task.setQuote( policyVO.getIsQuote());
		} else if(!Utils.isEmpty( policyContext.getCommonDetails() )){
			task.setQuote( policyContext.getCommonDetails().getIsQuote());
		}
		
		String lob= task.getLob();
		if(Utils.isEmpty( lob )){
			lob = "";
		}
		DataHolderVO<Object[]> holderVO = (DataHolderVO<Object[]>) TaskExecutor.executeTasks( "GET_TASK_STATUS", task );
		task = (TaskVO) holderVO.getData()[ 0 ];
		
		/* Update the task VO to policy context so that further operations on taskVO can happen basic its fetch 
		 * from policyContext */
		setTaskInContext(req,task);
		
		Byte status = (Byte) holderVO.getData()[ 1 ];

		String loggedUser = userProfile.getRsaUser().getUserId().toString();

		VisibilityLevel visibilityLevel = VisibilityLevel.EDITABLE;
		String resolvedLob = lob.equalsIgnoreCase( "" )?LOB.SBS.toString():lob;
		
		if(!Utils.isEmpty( task.getDesc() ) && task.getDesc().contains("Soft Stop Quote"))
		{
			visibilityLevel = VisibilityLevel.EDITABLE;
		} 
		else if( task.getCreatedBy().equalsIgnoreCase( loggedUser ) ){
			if( ( lob.equals( LOB.HOME.toString() ) || lob.equals( LOB.TRAVEL.toString() )||Utils.getSingleValueAppConfig("MONOLINE_LOB").toString().contains(lob.toString())) && status.toString().equals( Utils.getSingleValueAppConfig( com.Constant.CONST_QUOTE_ACCEPT ) ) ){
				visibilityLevel = VisibilityLevel.EDITABLE;
			}
			else{
				visibilityLevel = VisibilityLevel.READONLY;
			}

		}
		else if( task.getAssignedTo().equalsIgnoreCase( loggedUser )
				|| ( resolvedUserRank( task.getAssignedTo(),task.getAssignedBy(), loggedUser, userProfile.getRsaUser().getHighestRole( resolvedLob ), resolvedLob, status ) ) ){

			if( status.toString().equals( Utils.getSingleValueAppConfig( com.Constant.CONST_QUOTE_REFERRED ) ) ){
				visibilityLevel = VisibilityLevel.EDITABLE;
			}
			else{
				visibilityLevel = VisibilityLevel.READONLY;
			}
		}else if( task.getAssignedBy().equalsIgnoreCase( loggedUser ) ){
			if( ( lob.equals( LOB.HOME.toString() ) || lob.equals( LOB.TRAVEL.toString() ) ||Utils.getSingleValueAppConfig("MONOLINE_LOB").toString().contains(lob.toString())) && status.toString().equals( Utils.getSingleValueAppConfig( com.Constant.CONST_QUOTE_ACCEPT ) ) ){
				visibilityLevel = VisibilityLevel.READONLY;
			}
			else{
				visibilityLevel = VisibilityLevel.READONLY;
			}

		}
		

		return visibilityLevel;
	}
	
	private boolean resolvedUserRank( String assignedTo, String assignedBy, String loggedUser, String loggedUserHigestRole, String resolvedLob, Byte status ){
		boolean resolvedUserRank = true;
		if( !assignedTo.equalsIgnoreCase( loggedUser ) && !assignedBy.equalsIgnoreCase( loggedUser ) && status.toString().equals( Utils.getSingleValueAppConfig( com.Constant.CONST_QUOTE_REFERRED ) ) ){

			List<String> assignedToRoles = (List<String>) (List<?>) DAOUtils.getSqlResultSingleColumnPas( QueryConstants.GET_USER_ROLE, assignedTo );
			Integer assignedToRank = Integer.valueOf( Utils.getSingleValueAppConfig( SvcUtils.getHighestRole( resolvedLob, CopyUtils.toArray( assignedToRoles ) ) ) );
			Integer loggedUserRank = Integer.valueOf( Utils.getSingleValueAppConfig( SvcUtils.getHighestRole( resolvedLob, new String[]{ loggedUserHigestRole } ) ) );
			if( loggedUserRank < assignedToRank ){
				resolvedUserRank = true;
			}
		}
		else if( assignedBy.equalsIgnoreCase( loggedUser ) && status.toString().equals( Utils.getSingleValueAppConfig( com.Constant.CONST_QUOTE_REFERRED ) ) ){
			resolvedUserRank = false;
		}
		else if( status.toString().equals( Utils.getSingleValueAppConfig( com.Constant.CONST_QUOTE_ACCEPT ) ) ){
			resolvedUserRank = false;
		}
		return resolvedUserRank;
	}

	/*
	 * In Referral flow the task details is set into policy context
	 * This will be used to check the visibility based on the task creator and assignor
	 */
	private void setTaskInContext( HttpServletRequest request, TaskVO task ){
		PolicyContext policyContext = PolicyContextUtil.getPolicyContext( request );
		if( !Utils.isEmpty( task ) ){
			policyContext.setTaskDetails( task );
		}
		else{
			throw new BusinessException( "pas.auth.taskDetilsNotAvailable", null, "\"taskDetails\" is null in resoving referral flow" );
		}

	}

}
