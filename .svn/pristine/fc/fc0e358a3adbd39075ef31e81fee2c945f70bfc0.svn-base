package com.rsaame.pas.web;

import java.util.ArrayList;
import java.util.List;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.GrantedAuthorityImpl;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.dao.model.TMasUser;
import com.rsaame.pas.kaizen.vo.DefaultSchedulerUser;

public class UserProfileHandler {

public static UserProfile getUserProfileVo(String loginId) {
		
		HibernateTemplate ht = (HibernateTemplate) Utils.getBean( com.Constant.CONST_HIBERNATETEMPLATE );
		
		List<TMasUser> userRecs = new ArrayList<TMasUser>();
		
		List<Object> queryData = new ArrayList<Object>();
		queryData.add(loginId);
		
		String query = "from TMasUser where loginId = ?";
		
		userRecs = ht.find( query, queryData.toArray() );
		
		TMasUser tMasUser = userRecs.get(0);
		
		UserProfile userProfile = new UserProfile();
		//not used
		GrantedAuthority [] grantedAuth = new GrantedAuthorityImpl[1];
        grantedAuth[0] = new GrantedAuthorityImpl(Utils.getSingleValueAppConfig( "RSA_PL_USER_1" ) );
        
        int brokerId = 0;
		if (tMasUser.getBrokerId()!=null) {
			brokerId = tMasUser.getBrokerId().intValue();
		}

        DefaultSchedulerUser defaultUser = new DefaultSchedulerUser( tMasUser.getUserEName(), tMasUser.getPassword(), true, grantedAuth , 
        		Integer.valueOf(tMasUser.getCountry()), tMasUser.getBranch(), tMasUser.getEmployeeId(),brokerId,
        		0, 0, 0, 0, Integer.valueOf(tMasUser.getStatusId()), tMasUser.getProfile(),
        		tMasUser.getUserId(), tMasUser.getUserAName(), tMasUser.getUserEName(), tMasUser.getUserEmailId(), tMasUser.getUserMobNo() );

        userProfile.setRsaUser( defaultUser );
        userProfile.setPassword(tMasUser.getPassword());

        userProfile.setUserId( String.valueOf( tMasUser.getUserId() ) );


        return userProfile;
        
        
	}

public static UserProfile getUserProfileVo(Integer userId) {
	
	UserProfile userProfile = new UserProfile();
	GrantedAuthority [] grantedAuth = null;
	
	HibernateTemplate ht = (HibernateTemplate) Utils.getBean( com.Constant.CONST_HIBERNATETEMPLATE );
	List<TMasUser> userRecs = new ArrayList<TMasUser>();
	List<Object> queryData = new ArrayList<Object>();
	queryData.add(userId);
	
	String query = "from TMasUser where userId = ?";
	userRecs = ht.find( query, queryData.toArray() );
	TMasUser tMasUser = userRecs.get(0);
	
	DataHolderVO<Object> dataHolder = new DataHolderVO<Object>();
	Object [] inputData = new Object[1];
	inputData[0] = userId;
	dataHolder.setData(inputData);
	
	dataHolder = (DataHolderVO<Object>) TaskExecutor.executeTasks("GET_USER_ROLES", dataHolder);
	Object [] outputData = (Object[]) dataHolder.getData();
	List<String> userRoles = (List<String>) outputData[0];
	grantedAuth = new GrantedAuthorityImpl[userRoles.size()];
	for(int i = 0; i < userRoles.size(); i++){
		grantedAuth[i] = new GrantedAuthorityImpl(userRoles.get(i).toString());
	}
	
    int brokerId = 0;
	if (tMasUser.getBrokerId()!=null) {
		brokerId = tMasUser.getBrokerId().intValue();
	}

    DefaultSchedulerUser defaultUser = new DefaultSchedulerUser( tMasUser.getUserEName(), tMasUser.getPassword(), true, grantedAuth , 
    		Integer.valueOf(tMasUser.getCountry()), tMasUser.getBranch(), tMasUser.getEmployeeId(),brokerId,
    		0, 0, 0, 0, Integer.valueOf(tMasUser.getStatusId()), tMasUser.getProfile(),
    		tMasUser.getUserId(), tMasUser.getUserAName(), tMasUser.getUserEName(), tMasUser.getUserEmailId(), tMasUser.getUserMobNo() );

    userProfile.setRsaUser( defaultUser );
    userProfile.setPassword(tMasUser.getPassword());

    userProfile.setUserId( String.valueOf( tMasUser.getUserId() ) );


    return userProfile;
}

	/**
	 * @param userID
	 * @return
	 */
	public static int getBrokerDetails(String userID) {
		
		HibernateTemplate ht = (HibernateTemplate) Utils.getBean( com.Constant.CONST_HIBERNATETEMPLATE );
		int brokerId =0;
		
		String query = "from TMasUser where userId = ?";
		List<TMasUser> userRecs = new ArrayList<TMasUser>();
		
		
		List<Object> queryData = new ArrayList<Object>();
		queryData.add(Integer.valueOf(userID));
		
		userRecs = ht.find( query, queryData.toArray());
		
		TMasUser tMasUser = userRecs.get(0);
		
		if(tMasUser.getBrokerId()!=null){
			brokerId = tMasUser.getBrokerId().intValue();
		}
		
		return brokerId;
	}
}
