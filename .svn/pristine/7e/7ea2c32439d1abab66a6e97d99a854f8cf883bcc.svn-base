package com.rsaame.pas.util;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.mindtree.ruc.cmn.base.BaseException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.cmn.access.Functionality;
import com.rsaame.pas.cmn.access.Resource;
import com.rsaame.pas.cmn.access.Role;
import com.rsaame.pas.cmn.vo.UserProfile;
/**
 * Tag handler class for the auth tag. 
 */
public class ResourceAuthTag extends TagSupport{

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger( ResourceAuthTag.class );
	private String resource;
	private String minAccessReqd;

	/**
	 * @return the minAccessReqd
	 */
	public String getMinAccessReqd() {
		return minAccessReqd;
	}

	/**
	 * @param minAccessReqd the minAccessReqd to set
	 */
	public void setMinAccessReqd(String minAccessReqd) {
		this.minAccessReqd = minAccessReqd;
	}

	@Override
	public int doStartTag() throws JspException{
		int returnVal = SKIP_BODY;

		/*try{*/		/*commented empty try catch statement (not content inside block as were already commented) - sonar violation fix*/
			/*
			 * (1) get the logged in users's role name from session
			 * (2) get the resource list that the role has access
			 * (3) get the resource name from jsp for the current resource (passed in the field "resource")
			 * (4) from the resource list, get the access level for the role for the resource
			 * (5) depending on the above information give the appropriate access
			 */
			
		/*	UserProfile userProfileVO = (UserProfile) pageContext.getSession().getAttribute( AppConstants.SESSION_USER_PROFILE_VO );
			
			 TODO Get the user's role from the UserProfile instance into roleName. 
			String roleName = null;
			
			Role role = Role.get( roleName );
			Resource res = getResourceInstance( resource );
			
			IAuthorizationSvc authSvc = (IAuthorizationSvc) Utils.getBean( BeanName.AUTHORIZATION_SVC );
			Resource accessResource = authSvc.roleHasAccessToResource( res, role );
			Resource accessResource = null;*/
			
			/* Return SKIP_PAGE if:
			 * (a) the instance is null, or
			 * (b) the instance is of Functionality type and Functionality.accessType is lesser than
			 *     "minAccessReqd".
			 *     
			 * Return EVAL_PAGE if:
			 * (a) the instance is of Functionality type and Functionality.accessType is greater than or
			 *     equal to "minAccessReqd".
			 * (b) the instance is not null and not of Functionality (but of the parent Resource class only)
			 */
			// Strat: It is set as null, so the following code doesnt work
			/*if(Utils.isEmpty(accessResource)) {
				returnVal = SKIP_BODY;
			}
			else if( accessResource instanceof Functionality ) {
				Functionality accessRes = (Functionality)accessResource;
				if( accessRes.getAccessType().compareTo(Functionality.FunctionalityAccessType.valueOf(minAccessReqd)) >= 0 ) {
					returnVal = EVAL_PAGE;
				}
				else {
					returnVal = SKIP_BODY;
				}
			}
			else {
				returnVal = EVAL_PAGE;
			}*/
			//End: It is set as null, so the following code doesnt work
		//}																		/*commented empty try catch statement (not content inside block as were already commented) - sonar violation fix*/
		/*catch( BaseException be ){*/											/*commented empty try catch statement (not content inside block as were already commented) - sonar violation fix*/
//			if( log.isError() ){
//				log.error( "", be );
//			}
			/* TODO default error page */
			/* TODO showing the error on the top of the same page page */

			/* Set the error messages for display. */
			//resolveErrors( request, response, be );
		//}																		/*commented empty try catch statement (not content inside block as were already commented) - sonar violation fix*/
		/*catch( Throwable anyOtherThrowable ){*/								/*commented empty try catch statement (not content inside block as were already commented) - sonar violation fix*/
//			if( log.isError() ){
//				log.error( "", anyOtherThrowable );
//			}

			/* TODO Set "Unknown error" message. */
		//}																		/*commented empty try catch statement (not content inside block as were already commented) - sonar violation fix*/
		return returnVal;
	}

	/*private Resource getResourceInstance( String res ) {
		Resource resource = new Resource();
		resource.setCode( res );
		
		return resource;
	}*/

	public String getResource(){
		return resource;
	}

	public void setResource( String resource ){
		this.resource = resource;
	}


}
