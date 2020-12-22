/**
 * 
 */
package com.rsaame.pas.svc.cmn;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.task.ITask;
import com.mindtree.ruc.cmn.task.TaskOutput;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.rules.invoker.RuleServiceInvoker;
import com.rsaame.pas.rules.mapper.RulesConstants;
import com.rsaame.pas.vo.bus.ReferralListVO;

/**
 * @author m1014739
 *
 */
public class PASRulesTask implements ITask{

	@Override
	public TaskOutput execute( String identifier, String[] taskInputConfig, BaseVO input ){
		TaskOutput to = new TaskOutput();

		rulesTask: {
			/* If no rules transaction has been configured, consider the execution successful. */
			if( Utils.isEmpty( taskInputConfig ) || RulesConstants.RULES_EXEC_NOT_REQ.equals( taskInputConfig[ 0 ] ) ){
				to.setSuccessful( true );
				break rulesTask;
			}

			executeRules( to, identifier, taskInputConfig, input );
		}

		return to;
	}

	private void executeRules( TaskOutput to, String identifier, String[] taskInputConfig, BaseVO input ){
		String roleType = getUserRole( input );

		if( Utils.isEmpty( roleType, true ) ){
			//TODO:
			//roleType = "BROKER_USER";
			throw new BusinessException( "pas.referrals.userRole", null, "Role could not be fetched from policyVO" );
		}

		Integer[] taskInputConfigInt = new Integer[ taskInputConfig.length ];
		int i = 0;
		for( String str : taskInputConfig ){
			taskInputConfigInt[ i ] = Integer.parseInt( str );
			i++;
		}

		RuleServiceInvoker invoker = new RuleServiceInvoker();
		BaseVO output = invoker.callRuleService( input, taskInputConfigInt, roleType );

		to.setSuccessful( true );
		to.setTaskOutput( output );

		ReferralListVO referralListVO = (ReferralListVO) output;
		if( !Utils.isEmpty( referralListVO ) && !Utils.isEmpty( referralListVO.getReferrals() ) ){
			BusinessException be = new BusinessException( Utils.getAppErrorMessage( "pas.referrals" ), null, "Rules execution resulted in referral" );
			be.setExceptionData( referralListVO );
			throw be;
		}
	}

	/**
	 * 
	 * @param baseVO
	 * @return
	 */
	private String getUserRole( BaseVO baseVO ){
		String highestRole = null;
		UserProfile profile = null;
		if( !Utils.isEmpty( baseVO.getLoggedInUser() ) ){
			profile = (UserProfile) baseVO.getLoggedInUser();
		}
		if( !Utils.isEmpty( profile ) && !Utils.isEmpty( profile.getRsaUser() ) ){
			if( !Utils.isEmpty( profile.getRsaUser().getHighestRole() ) ){
				highestRole = profile.getRsaUser().getHighestRole("SBS");
			}
		}
		return highestRole;
	}

}
