/**
 * 
 */
package com.rsaame.pas.access.handler;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.tags.util.RuleResultScope;
import com.mindtree.ruc.mvc.tags.util.VisibilityLevel;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.kaizen.vo.KaizenSecurityContextWrapper;
import com.rsaame.pas.kaizen.vo.RSAUserWrapper;
import com.rsaame.pas.query.constants.QueryConstants;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.bus.LOB;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.TaskVO;
import com.rsaame.pas.vo.cmn.CommonVO;

/**
 * @author m1014644
 * Fetch the privilege of a field based on
 * 1 - DB configuration
 * 2 - Quote/Policy Status
 * 3 - Based on the logged in user
 */
public class GetPrivilegeForUserUtil{

	private static final Logger logger = Logger.getLogger( GetPrivilegeForUserUtil.class );

	public VisibilityLevel getVisibility( HttpServletRequest request, Map<String, String> input, String aclType ){

		if( logger.isTrace() ) logger.trace( "Entering getVisibility for tag auth " );
		VisibilityLevel visibilityLevel = VisibilityLevel.EDITABLE;

		String sectionName = input.get( AppConstants.SECTION_NAME );
		String funtionName = (String) request.getAttribute( AppConstants.FUNTION_NAME );
		String screenName = (String) request.getAttribute( AppConstants.SCREEN_NAME );

		if( Utils.isEmpty( funtionName ) || Utils.isEmpty( screenName ) ){
			visibilityLevel = VisibilityLevel.EDITABLE;
		}
		else if( funtionName.trim().equalsIgnoreCase( "" ) || screenName.trim().equalsIgnoreCase( "" ) ){
			visibilityLevel = VisibilityLevel.EDITABLE;
		}
		else{

			visibilityLevel = getPrivilegeForUser( funtionName, screenName, sectionFormatBasedOnStatus( sectionName, aclType, request ), request);
		}

		RuleResultScope resultScope = (RuleResultScope) request.getAttribute( AppConstants.RULE_RESULT_SCOPE );

		if( !Utils.isEmpty( resultScope ) && resultScope.equals( RuleResultScope.SCREEN ) ){
			if( visibilityLevel.equals( VisibilityLevel.EDITABLE ) ){
				visibilityLevel = VisibilityLevel.READONLY;
			}
		}
		else{
			request.setAttribute( AppConstants.RULE_RESULT_SCOPE, RuleResultScope.TAG_INSTANCE );
		}

		if( logger.isTrace() ) logger.trace( "exiting getVisibility for for tag auth " );
		return visibilityLevel;

	}

	/*
	 * Formats the section name - which is a static entry in the jsp's to append pol/quo status or other identifier
	 * so to determine the visibility of the element on other factors to
	 */
	private String sectionFormatBasedOnStatus( String sectionName, String aclType, HttpServletRequest request ){

		String statusSectionName = null;
		/*
		 * The "aclType" if not null the authorization needs to be handled in a 
		 */
		if( !Utils.isEmpty( aclType ) ){
			/*AppConstants.POL_QUO_STATUS is for policy/quote status based authorization in future any other
			 status based authorization can add other if else conditions*/
			if( AppConstants.POL_QUO_STATUS.equalsIgnoreCase( aclType ) ){
				statusSectionName = getPolQuoStatusVisibility(sectionName, request);
			}
			else if( AppConstants.USER_POL_QUO_STATUS.equalsIgnoreCase( aclType ) ){
				// The visibility is based only the status of the quote/ policy and the user
				statusSectionName = getUserPolQuoStatusVisibility(sectionName, request);
			}

		}
		return Utils.isEmpty( statusSectionName ) ? sectionName : statusSectionName;
	}
	
	
	/*
	 * The returns the formated section name that will be present in data base 
	 * Here only policy/quote status is taken into consideration
	 */
	private String getUserPolQuoStatusVisibility( String sectionName, HttpServletRequest request ){
		PolicyContext context = PolicyContextUtil.getPolicyContext( request );
		// The visibility is based only the status of the quote/ policy
		String statusSectionName = null;
		if( !Utils.isEmpty( context ) && !Utils.isEmpty( context.getStatus() ) ){
			String status =  context.getStatus().toString(); // Status should not be null here and the app should fail if its null
			String roleVisibility = getRoleVisiblity( sectionName, context, request);
			statusSectionName = Utils.concat( sectionName, "_", status, "_", roleVisibility );
		}

		return statusSectionName;
	}

	/*
	 * Fields that needs specific handling and cannot be generalized  is done here
	 * 
	 */
	private String getRoleVisiblity( String sectionName, PolicyContext context, HttpServletRequest request ){
		String roleVisibility = "H";
		for( RoleBasedACLFields fields : RoleBasedACLFields.values() ){
			if( fields.toString().equalsIgnoreCase( sectionName ) ){
				switch( RoleBasedACLFields.valueOf( sectionName ) ){
					case F_APPROVE_QUO:
						roleVisibility = visibilityForAssignedTo( sectionName, context, request );
						break;
					case F_APPROVE_POL :
						roleVisibility = visibilityForAssignedTo( sectionName, context, request );
						break;
					case F_DECLINE_QUO :
						roleVisibility = visibilityForAssignedTo( sectionName, context, request );
						break;
					case F_DECLINE_POL :
						roleVisibility = visibilityForAssignedTo( sectionName, context, request );
						break;
					case F_EDIT_QUOTE:
						roleVisibility = visbilityForEditQuote( sectionName, context, request );
						break;
					case F_ISSUE_QUO:
						roleVisibility = visibilityForCreatedBy( sectionName, context, request );
						break;
					case F_COV_POL:
						roleVisibility = visibilityForCreatedBy( sectionName, context, request );
						break;
					case F_REJECT_QUO:
						roleVisibility = visibilityForCreatedBy( sectionName, context, request );
						break;
					case F_DEMAND_REF:
						roleVisibility = visibilityForCreatedBy( sectionName, context, request );
						break;
					case F_IS_AMEND_MODE:
						roleVisibility = visibilityForCreatedBy( sectionName, context, request );
						break;
						//sonar fix
					default:
						break;
				}
			}
		}

		return roleVisibility;
	}

	private String visbilityForEditQuote( String sectionName, PolicyContext context,  HttpServletRequest request){
		UserProfile profile = AppUtils.getUserDetailsFromSession( request );
		String roleVisibility = "H";
		TaskVO  taskDetails = context.getTaskDetails();
		String[] editableQuoStatus = Utils.getMultiValueAppConfig( "QUOTE_EDITABLE", "," );
		
		if(context.getAppFlow().equals( Flow.VIEW_QUO ))
		{
			if(!Utils.isEmpty(context.getPolicyDetails())){
				if( !Utils.isEmpty( editableQuoStatus ) && editableQuoStatus.length > 0
					&& CopyUtils.asList( editableQuoStatus ).contains( context.getStatus().toString() )  && context.getPolicyDetails().getIsQuote()
					&&  nonRefVisibility( context, profile, request ) ){
				roleVisibility = "E";
				}
			}else if(!Utils.isEmpty( context.getCommonDetails() )){
				if( !Utils.isEmpty( editableQuoStatus ) && editableQuoStatus.length > 0
						&& CopyUtils.asList( editableQuoStatus ).contains( context.getStatus().toString() )  && context.getCommonDetails().getIsQuote()
						&&  nonRefVisibility( context, profile, request ) ){
					roleVisibility = "E";
				}
			}
		}
		else if(context.getAppFlow().equals( Flow.RESOLVE_REFERAL ))
		{
			if(!Utils.isEmpty(context.getPolicyDetails())){
				if(  nonRefVisibility( context, profile, request )&& context.getPolicyDetails().getIsQuote()
						&&  !Utils.isEmpty( taskDetails ) &&  ( context.getStatus().equals( Integer.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_QUOTE_ACCEPT ) ) ) || context.getPolicyDetails().getStatus().equals( Integer.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_QUOTE_DECLINED ) ) )) ){
					roleVisibility = "E";
				}
			} else if(!Utils.isEmpty( context.getCommonDetails() )){
				if(  nonRefVisibility( context, profile, request )&& context.getCommonDetails().getIsQuote()
						&&  !Utils.isEmpty( taskDetails ) &&  ( context.getStatus().equals( Integer.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_QUOTE_ACCEPT ) ) ) || context.getCommonDetails().getStatus().equals( Integer.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_QUOTE_DECLINED ) ) )) ){
					roleVisibility = "E";
				}
			}
			
		}
		if( logger.isTrace() ) logger.trace( sectionName+com.Constant.CONST_VISIBILITY_END + roleVisibility);
		return roleVisibility;
	}

	private String visibilityForAssignedTo( String sectionName, PolicyContext context, HttpServletRequest request ){
		
		UserProfile profile = AppUtils.getUserDetailsFromSession( request );
		TaskVO  taskDetails = context.getTaskDetails();
		String roleVisibility = "H";
		if( context.getStatus().equals( Integer.valueOf( Utils.getSingleValueAppConfig( "QUOTE_REFERRED" ) ) )
				||  context.getStatus().equals( Integer.valueOf( Utils.getSingleValueAppConfig( "QUOTE_SOFT_STOP" ) ) ))
		{
			if(isAssignedToRefVisibility( profile, taskDetails ))
			{
				roleVisibility = "E";
			}
		}
		if( logger.isTrace() ) logger.trace( sectionName+com.Constant.CONST_VISIBILITY_END + roleVisibility);
		return roleVisibility;
	}

	/** Checks if the button should be displayed to Assignee, during resolving this method decides weather the button is displayed or not
	 * 
	 * @param profile
	 * @param taskDetails
	 * @return
	 */
	private boolean isAssignedToRefVisibility( UserProfile profile, TaskVO taskDetails ){

		if(!Utils.isEmpty( taskDetails )){
			String lob = taskDetails.getLob();
			if( Utils.isEmpty( lob ) ){
				lob = "";
			}
			String resolvedLob = lob.equalsIgnoreCase( "" ) ? LOB.SBS.toString() : lob;
			return  checkUserRank( profile, taskDetails )
					&& ( ( !Utils.isEmpty( taskDetails ) && !Utils.isEmpty( taskDetails.getAssignedTo() ) && profile.getRsaUser().getUserId()
							.equals( Integer.valueOf( taskDetails.getAssignedTo() ) ) ) || resolvedUserRank( taskDetails.getAssignedTo(), profile.getRsaUser().getUserId().toString(), profile.getRsaUser().getHighestRole( lob ), resolvedLob ) );
		}else{
			return false;
		}
		
	}
	
	
	private boolean resolvedUserRank( String assignedTo, String loggedUser, String loggedUserHigestRole, String resolvedLob ){
		boolean resolvedUserRank = true;
		if( !assignedTo.equalsIgnoreCase( loggedUser ) ){

			List<String> assignedToRoles = (List<String>) (List<?>) DAOUtils.getSqlResultSingleColumnPas( QueryConstants.GET_USER_ROLE, assignedTo );
			Integer assignedToRank = Integer.valueOf( Utils.getSingleValueAppConfig( SvcUtils.getHighestRole( resolvedLob, CopyUtils.toArray( assignedToRoles ) ) ) );
			Integer loggedUserRank = Integer.valueOf( Utils.getSingleValueAppConfig( SvcUtils.getHighestRole( resolvedLob, new String[]{ loggedUserHigestRole } ) ) );
			if( loggedUserRank > assignedToRank ){
				resolvedUserRank = false;
			}
		}
		return resolvedUserRank;
	}
	
	
	/**
	 * Checks if the user resolving the referral has higher authority than the assigner 
	 * @param profile
	 * @param taskDetails
	 * @return
	 */
	private boolean checkUserRank( UserProfile profile, TaskVO taskDetails ){

		String assignedToRole = SvcUtils.getLookUpDescription( com.Constant.CONST_USER_ROLE_PAS, "ALL", "ALL", Integer.valueOf( taskDetails.getAssignedTo() ) );
		String assignedByRole = SvcUtils.getLookUpDescription( com.Constant.CONST_USER_ROLE_PAS, "ALL", "ALL", Integer.valueOf( taskDetails.getAssignedBy() ) );
		String createdBy = SvcUtils.getLookUpDescription( com.Constant.CONST_USER_ROLE_PAS, "ALL", "ALL", Integer.valueOf( taskDetails.getCreatedBy() ) );
		
		if(!Utils.isEmpty( taskDetails.getDesc() ) && taskDetails.getDesc().contains( "Soft Stop Quote"))
		{
			return true;
		}
		/* If created user has lesser ran than assigned to and assigned by process further*/
		if(getHighestRoleRank( createdBy ) > getHighestRoleRank( assignedToRole ) && getHighestRoleRank( createdBy ) >= getHighestRoleRank( assignedByRole ) ){
			return ( getHighestRoleRank( assignedToRole ) < getHighestRoleRank( assignedByRole ) || getHighestRoleRank( assignedToRole ) < getHighestRoleRank( createdBy ) ) ? true
					: false;
		}else{
			return false;
		}
		
	}
	
	/**
	 * Gets the rank of the role
	 * BROKER_USER = 4
	 * RSA_USER_1 = 3
	 * RSA_USER_2 = 2
	 * RSA_USER_3 = 1
	 * @param role
	 * @return
	 */
	public Integer getHighestRoleRank( String role ){
		return Integer.valueOf( Utils.getSingleValueAppConfig( role ) );

	}

private String visibilityForCreatedBy( String sectionName, PolicyContext context, HttpServletRequest request ){
		
		UserProfile profile = AppUtils.getUserDetailsFromSession( request );
		TaskVO  taskDetails = context.getTaskDetails();
		String roleVisibility = "H";
		
		if(sectionName.contains( "F_REJECT_QUO" ) && !context.getAppFlow().equals( Flow.VIEW_POL ) && ( context.getStatus().equals( Integer.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_QUOTE_ACCEPT ) ) ) 
				||  context.getStatus().equals( Integer.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_QUOTE_ACTIVE ) ) ) 
				||  context.getStatus().equals( Integer.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_QUOTE_PENDING ) ) )) || (getHomeTravelRef(sectionName,context,request))){
			if(!Utils.isEmpty(context.getCommonDetails()) && !Utils.isEmpty(taskDetails))
			{
				if(isCommonCreatedByRefVisibility(context, taskDetails,profile))
					roleVisibility = "E";
			}
			else if(isCreatedByRefVisibility( profile, taskDetails ))
					{
						roleVisibility = "E";
				
					}
			
			else if(Utils.isEmpty( taskDetails ) && !context.getAppFlow().equals( Flow.VIEW_POL ) && (  context.getStatus().equals( Integer.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_QUOTE_PENDING ) ) ) || context.getStatus().equals( Integer.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_QUOTE_ACTIVE )))
					|| (getHomeTravelRef(sectionName,context,request))))
			{
				if( nonRefVisibility( context, profile, request ) ){
					roleVisibility = "E";
				}
			}
		}else if(sectionName.contains( "F_ISSUE_QUO" ) && (context.getStatus().equals( Integer.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_QUOTE_ACCEPT ) ) ) 
				|| context.getStatus().equals( Integer.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_QUOTE_PENDING ) ) ) || (getHomeTravelRef(sectionName,context,request)) ) ){
			if(!Utils.isEmpty(context.getCommonDetails()) && !Utils.isEmpty(taskDetails))
			{
				if(isCommonCreatedByRefVisibility(context, taskDetails, profile))
					roleVisibility = "E";
			}
			else if(isCreatedByRefVisibility( profile, taskDetails ))
					{
						roleVisibility = "E";
				
					}
			
			else if(Utils.isEmpty( taskDetails ) && (context.getStatus().equals( Integer.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_QUOTE_PENDING ) ) ) || getHomeTravelRef(sectionName,context,request)) )
					{
						if( nonRefVisibility( context, profile, request ) ){
								roleVisibility = "E";
						}
					}
			
		}
		else if(sectionName.contains( "F_COV_POL" ) && context.getStatus().equals( Integer.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_QUOTE_ACTIVE ) ) ) && !context.getAppFlow().equals( Flow.VIEW_POL )){
			if(isCreatedByRefVisibility( profile, taskDetails ))
			{
				roleVisibility = "E";
			}
			else if(Utils.isEmpty( taskDetails ) && !context.getAppFlow().equals( Flow.VIEW_POL ) && context.getStatus().equals( Integer.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_QUOTE_ACTIVE ) ) ))
			{
				if( nonRefVisibility( context, profile, request ) ){
					roleVisibility = "E";
				}
			}
		}else if((sectionName.contains( "F_DEMAND_REF" ) && (context.getStatus().equals( Integer.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_QUOTE_DECLINED ) ) ) 
				|| context.getStatus().equals( Integer.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_QUOTE_PENDING ) ) ))) && !(Arrays.asList( profile.getRsaUser().getUserRoles()).contains( "RSA_USER_3" ))){
			if(isCreatedByRefVisibility( profile, taskDetails ))
			{
				roleVisibility = "E";
			}
			else if(Utils.isEmpty( taskDetails )&& (context.getStatus().equals( Integer.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_QUOTE_DECLINED ) ) ) 
					|| context.getStatus().equals( Integer.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_QUOTE_PENDING ) ) ))){
				if( nonRefVisibility( context, profile, request ) ){
					roleVisibility = "E";
				}
			}
		} else if(sectionName.contains( "F_IS_AMEND_MODE" ) && context.getAppFlow().equals( Flow.VIEW_POL )){
			if( nonRefVisibility( context, profile, request ) ){
				// To remove Amend/cancel policy link created by user when status is Referred to RSA.
				if( !context.getStatus().equals( Integer.valueOf( Utils.getSingleValueAppConfig( "POLICY_REFERRED" ) ) )){
					roleVisibility = "E";
				}
			}
		}
		if( logger.isTrace() ) logger.trace( sectionName+com.Constant.CONST_VISIBILITY_END + roleVisibility);
		return roleVisibility;
	}

	private boolean getHomeTravelRef( String sectionName, PolicyContext context, HttpServletRequest request ){
		// For Home/Travel status will not be PENDING in commonVO even after edit quote clicked. For a declined quote status will be QUOTE_DECLINED in commonVO.
		// we need to have SAVE_QUO button in risk page when the quote is edited after declined by RSA
		if( sectionName.contains( "F_ISSUE_QUO" ) || sectionName.contains( "F_REJECT_QUO" )){
			if(!Utils.isEmpty( context.getCommonDetails() ) && !Utils.isEmpty( context.getCommonDetails().getStatus() ) && (context.getAppFlow().equals( Flow.EDIT_QUO ) ||context.getAppFlow().equals( Flow.AMEND_POL ))){
				return (context.getCommonDetails().getStatus().equals(  Integer.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_QUOTE_DECLINED ) )  )||
						context.getCommonDetails().getStatus().equals(  Integer.valueOf( Utils.getSingleValueAppConfig( "QUOTE_REJECT" ) )  )||
						context.getCommonDetails().getStatus().equals(  Integer.valueOf( Utils.getSingleValueAppConfig( com.Constant.CONST_QUOTE_ACCEPT ) )  ));
			}
		}
	return false;
}

	/**Checks the authorization in case there is no referral
	 * @param context
	 * @param profile
	 * @return
	 */
	
	
	private boolean nonRefVisibility( PolicyContext context, UserProfile profile, HttpServletRequest request ){
		boolean nonRefVisibility = false;
		Integer loggedinBrCode = null;
		Integer polBrCode = null;
		Boolean isBrokerAllowedToEdit = false;
		
		if(!Utils.isEmpty( context.getPolicyDetails() ))
		{
			if(!Utils.isEmpty(profile.getRsaUser().getBrokerId()) && 
					!Utils.isEmpty(context.getPolicyDetails().getGeneralInfo().getSourceOfBus().getBrokerName()))
			{
				loggedinBrCode = profile.getRsaUser().getBrokerId();
				polBrCode = context.getPolicyDetails().getGeneralInfo().getSourceOfBus().getBrokerName();
				if(loggedinBrCode.equals(polBrCode))
				{
					isBrokerAllowedToEdit = true;
				}
			}
			nonRefVisibility = isBrokerAllowedToEdit || AppUtils.isRSAUser( profile );
		}
		else if(!Utils.isEmpty( context.getCommonDetails() )){
			PolicyDataVO policyDataVO = (PolicyDataVO) request.getAttribute( AppConstants.PAGE_VALUE );
			if(!Utils.isEmpty(profile.getRsaUser().getBrokerId()) && 
					!Utils.isEmpty(policyDataVO.getGeneralInfo().getSourceOfBus().getBrokerName()))
			{
				loggedinBrCode = profile.getRsaUser().getBrokerId();
				polBrCode = policyDataVO.getGeneralInfo().getSourceOfBus().getBrokerName();
				if(loggedinBrCode.equals(polBrCode))
				{
					isBrokerAllowedToEdit = true;
				}
			}
			if(!Utils.isEmpty( policyDataVO ) && !Utils.isEmpty( policyDataVO.getAuthenticationInfoVO() ) && !Utils.isEmpty( policyDataVO.getAuthenticationInfoVO().getCreatedBy() )){
				nonRefVisibility = profile.getRsaUser().getUserId().equals( Integer.valueOf( policyDataVO.getAuthenticationInfoVO().getCreatedBy() )) || AppUtils.isRSAUser( profile ) || isBrokerAllowedToEdit;
			}
		}
		return nonRefVisibility;
	}

	/**Checks if the button needs to be displayed to the assignee
	 * @param profile
	 * @param taskDetails
	 * @return
	 */
	private boolean isCreatedByRefVisibility( UserProfile profile, TaskVO taskDetails ){
		return !Utils.isEmpty( taskDetails ) && !Utils.isEmpty( taskDetails.getCreatedBy() )
				&& profile.getRsaUser().getUserId().equals( Integer.valueOf( taskDetails.getCreatedBy() ) );
	}
/*	private boolean isCommonCreatedByRefVisibility(PolicyContext context, TaskVO taskDetails, UserProfile profile){
		return !(!Utils.isEmpty( taskDetails ) && !Utils.isEmpty( taskDetails.getCreatedBy() ) && !Utils.isEmpty( taskDetails.getAssignedTo() )
				&& ((Integer.valueOf( taskDetails.getAssignedTo()).equals( Integer.valueOf( taskDetails.getCreatedBy() )))||(profile.getRsaUser().getUserId().equals( Integer.valueOf( taskDetails.getAssignedTo() )))) );
		
	}*/
	private boolean isCommonCreatedByRefVisibility(PolicyContext context, TaskVO taskDetails, UserProfile profile){
			if(!Utils.isEmpty( taskDetails ) && !Utils.isEmpty(taskDetails.getCreatedBy()) && !Utils.isEmpty(profile.getRsaUser()) && profile.getRsaUser().getUserId().equals( Integer.valueOf( taskDetails.getCreatedBy() )) && !Integer.valueOf( taskDetails.getAssignedTo()).equals( Integer.valueOf( taskDetails.getCreatedBy())) )
			{
				return true;
			}
			return false;
	}
	private String getPolQuoStatusVisibility( String sectionName, HttpServletRequest request ){
		PolicyContext context = PolicyContextUtil.getPolicyContext( request );
		// The visibility is based only the status of the quote/ policy
		String statusSectionName = null;
		if( !Utils.isEmpty( context ) && !Utils.isEmpty( context.getStatus() ) ){

			String status =  context.getStatus().toString(); // Status should not be null here and the app should fail if its null
			statusSectionName = Utils.concat( sectionName, "_", status );	
		}
		
		return statusSectionName;
	}
	// function is made public as visibility is explicitly set in GeneralInfoRH in case of insured search 
	public VisibilityLevel getPrivilegeForUser( String function, String screen, String sectionName, HttpServletRequest request ){

		if( logger.isTrace() ) logger.trace( "Entering getPrivilegeForUser to fetch the user roles and call getPrivilege " );
		UserProfile userProfile = null;
		String[] roles = null;
		String lob = null;
		CommonVO commonVO = null;
		VisibilityLevel privilege = VisibilityLevel.EDITABLE;

		userProfile = AppUtils.getUserDetailsFromSession( request );

		if( !Utils.isEmpty( userProfile ) && !Utils.isEmpty( userProfile.getRsaUser() ) && !Utils.isEmpty( userProfile.getRsaUser().getUserRoles() ) ){
			roles = userProfile.getRsaUser().getUserRoles();

		}
		
		PolicyContext policyContext = PolicyContextUtil.getPolicyContext(request);
		if(!Utils.isEmpty(policyContext)){
			lob = "SBS";
			commonVO = policyContext.getCommonDetails();
			if (!Utils.isEmpty(commonVO)){
				lob = commonVO.getLob().toString();
			}
		}
		if(!Utils.isEmpty(lob)){
			
			String[] lob_roles = new String[10];
			int i=0;
			List<String> lobRoleArray = Arrays.asList(Utils.getMultiValueAppConfig("LOB_"+lob));
			List<String> commonRoleArray = Arrays.asList(Utils.getMultiValueAppConfig("ALL_PROFILES"));
			for(String role:roles)
			{				
				if(commonRoleArray.contains(role) || lobRoleArray.contains(role))
				{
					lob_roles[i++]=role;
				}
			}
			roles=lob_roles;
			
		}
		/*Changes for Bug: 95170 : Reports not showign for brokers*/
		/*if((screen.equalsIgnoreCase("HOME_PAGE") || screen.equalsIgnoreCase("BrokerStmt")) && roles.length > 1 )
				&& function.equalsIgnoreCase("CREATE_QUO") && (sectionName.equalsIgnoreCase("CUSTOMER_LINK") || 
						sectionName.equalsIgnoreCase("REPORTS_LINK") || sectionName.equalsIgnoreCase("PAYMENT_RPT") || sectionName.equalsIgnoreCase("QUOTE_RPT")))
		{*/
		if(!Utils.isEmpty(roles) && roles.length>1){		 /*Added null check additional condition - sonar violation fix */
			RSAUserWrapper rsaUserWrapper = new RSAUserWrapper();
			roles = rsaUserWrapper.getSortedRoles(roles);
		}
	/*	}*/
		/*Changes for Bug: 95170 : Reports not showign for brokers*/
		// GET THR PRIV
		privilege = getPrivilege( roles, function, screen, sectionName );

		if( logger.isTrace() ) logger.trace( "Exiting getPrivilegeForUser " );
		return privilege;

	}

	private VisibilityLevel getPrivilege( String[] roles, String function, String screen, String section){

		if( logger.isTrace() ) logger.trace( "Entering getPrivilege to check the privilage" );

		VisibilityLevel privilege = VisibilityLevel.EDITABLE;
		
		Map<String, Map<String, String>> roleFunctionMap = KaizenSecurityContextWrapper.getRoleFunctionMap(); //SecurityContext.getRoleFunctionMap();
		if( !Utils.isEmpty( roles ) ){
			for( int i = 0; i < roles.length; i++ ){
				if( roleFunctionMap.containsKey( Utils.concat( roles[ i ], AppConstants.DELIMITER, function,SvcConstants.DELIMITER,
						Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION) ) ) ){
					if( logger.isTrace() ) logger.trace( "For ROLE - FUNTION " + Utils.concat( roles[ i ], AppConstants.DELIMITER, function ) );
					Map<String, String> screenSectionMap = roleFunctionMap.get( Utils.concat( roles[ i ], AppConstants.DELIMITER, function,SvcConstants.DELIMITER,
							Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION) ) );
					if( !Utils.isEmpty( screen ) && !Utils.isEmpty( section ) ){
						if( logger.isTrace() ) logger.trace( "and SCREEN - SECTION " + Utils.concat( screen, AppConstants.DELIMITER, section ) );
						String priv = screenSectionMap.get( Utils.concat( screen, AppConstants.DELIMITER, section ) );
						/*Changes for Bug: 95170 : Reports not showign for brokers*/
						/*if(Utils.isEmpty(priv)){
							priv = "Edit";
						}*/
						/*Changes for Bug: 95170 : Reports not showign for brokers*/
						if( !Utils.isEmpty( priv ) && !priv.trim().equalsIgnoreCase( "" ) ){
							if( priv.startsWith( AppConstants.READ_MODE ) ){
								privilege = VisibilityLevel.READONLY;
							}
							else if( priv.startsWith( AppConstants.HIDDEN_MODE ) ){
								privilege = VisibilityLevel.HIDDEN;
							}
							else if( priv.startsWith( AppConstants.WRITE_MODE ) ){
								privilege = VisibilityLevel.EDITABLE;
							}
							if( logger.isTrace() ) logger.trace( "privilege is " + privilege );
							break;
						}
					}
				}
			}
		}

		return privilege;
	}
	
	
}
