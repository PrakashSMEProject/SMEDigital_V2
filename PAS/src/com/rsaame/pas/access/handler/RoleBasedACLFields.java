/**
 * 
 */
package com.rsaame.pas.access.handler;

/**
 * @author m1014644
 *Contains all the fields/buttons/section that are to be displayed based on policy/quote status and user roles
 *This enum is created because there are only few elements that are based on logged in user and the status 
 *of the policy/quote
 *This is added only as an optimization mechanism 
 */
public enum RoleBasedACLFields{

	F_EDIT_QUOTE,
	F_APPROVE_QUO,
	F_DECLINE_QUO,
	F_ISSUE_QUO,
	F_COV_POL, 
	F_REJECT_QUO,
	F_DEMAND_REF, 
	F_APPROVE_POL, 
	F_DECLINE_POL, 
	F_IS_AMEND_MODE;
}
