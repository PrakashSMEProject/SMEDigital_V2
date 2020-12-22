package com.rsaame.pas.b2c.user;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.b2c.cmn.constants.AppConstants;
import com.rsaame.pas.cmn.context.ThreadLevelContext;
import com.rsaame.pas.cmn.vo.IRSAUser;
import com.rsaame.pas.com.svc.CommonOpSvc;
import com.rsaame.pas.dao.model.TMasUser;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.vo.bus.User;

public class B2CRSAUserWrapper implements IRSAUser, Serializable{

	/*private User user;
	public B2CRSAUserWrapper(){
		if(!Utils.isEmpty(ThreadLevelContext.get("PartnerUserId"))){
			
			user = (User) Utils.getBean("webUser");
			CommonOpSvc commonOpSvc = (CommonOpSvc) Utils.getBean("geComSvcBean");
			@SuppressWarnings("rawtypes")
			DataHolderVO userDetailsData=(DataHolderVO)commonOpSvc.invokeMethod("getUserDetails",
					Integer.valueOf(ThreadLevelContext.get("PartnerUserId").toString()));
			TMasUser userDetails=(TMasUser)userDetailsData.getData();
			user.setUserId(String.valueOf(userDetails.getUserId()));
			user.setTitle(userDetails.getProfile());
			user.setEmailAddress(userDetails.getUserEmailId());
			//user.setUserId(ThreadLevelContext.get("PartnerUserId").toString());
			//user = (User) TaskExecutor.executeTasks( "LOAD_LOGGEDIN_USER_DETAILS", Integer.valueOf(ThreadLevelContext.get("PartnerUserId").toString()) );
			//user = SvcUtils.getUserDetails(Integer.valueOf(ThreadLevelContext.get("PartnerUserId").toString()));
		}
	}*/
	
	@Override
	public Integer getAgentId(){
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getArabicName(){
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getBranchCode(){
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getBrokerId(){
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getCountryCode(){
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getDefaultApprover(){
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getDefaultModule(){
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getEmail(){
		/*if(!Utils.isEmpty(user.getEmailAddress())){
			return user.getEmailAddress();
		}*/
		return null;
	}

	@Override
	public Integer getEmployeeId(){
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getEnglishName(){
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getHighestRole(){
		String higestRole = null;
		Integer prevRank = 999;
		Integer actualrank = null;
		Map<Integer, String> rankRoleMap = new HashMap<Integer, String>();
		String[] roles = getUserRoles();
		for( String role : roles ){
			String rank = Utils.getSingleValueAppConfig( role );
			if( !Utils.isEmpty( rank ) ){
				Integer currentRank = Integer.valueOf( rank );
				rankRoleMap.put( currentRank, role );
				if( currentRank < prevRank ){
					prevRank = currentRank;
					actualrank = currentRank;
				}

			}
		}
		higestRole = rankRoleMap.get( actualrank );
		return higestRole;
	}

	@Override
	public Integer getLoginAttempts(){
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMobileNumber(){
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getProfile(){

		/*if(!Utils.isEmpty(user.getTitle())){
			return user.getTitle();
		}*/
		return null;
	}

	@Override
	public Integer getStatusId(){
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUserName(){
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getUserRoles(){
		String[] userRole = {AppConstants.B2C_USER};
		return userRole;
	}

	@Override
	public boolean hasMessages(){
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isHasMessages(){
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Integer getUserId(){

		/*if(!Utils.isEmpty(user.getUserId())){
			return Integer.valueOf(user.getUserId());
		}*/
		return AppConstants.B2C_USER_ID;
	}

	@Override
	public String getHighestRole( String lob ){
		// TODO Auto-generated method stub
		return getHighestRole();
	}

	@Override
	public String[] getSortedRoles(String[] roles) {
		// TODO Auto-generated method stub
		return null;
	}

}
