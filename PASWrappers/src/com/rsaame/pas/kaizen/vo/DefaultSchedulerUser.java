/**
 * 
 */
package com.rsaame.pas.kaizen.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.acegisecurity.GrantedAuthority;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.kaizen.framework.model.RSAUser;
import com.rsaame.pas.cmn.vo.IRSAUser;

/**
 * @author m1019193
 *
 */
public class DefaultSchedulerUser extends RSAUser implements IRSAUser {

	private static final long serialVersionUID = -4636554853805169759L;

	public DefaultSchedulerUser( String username, String password, boolean enabled, GrantedAuthority[] authorities, Integer countryCode, Integer branchCode, Integer employeeId,
			Integer brokerId, Integer agentId, Integer defaultModule, Integer defaultApprover, Integer loginAttempts, Integer statusId, String profile, Integer userId,
			String arabicName, String englishName, String email, String mobile ){

		super( username, password, enabled, authorities, countryCode, branchCode, employeeId, brokerId, agentId, defaultModule, defaultApprover, loginAttempts, statusId, profile, userId,
				arabicName, englishName, email, mobile );
	}

	@Override
	public String getHighestRole(){
    	String higestRole = null;
    	Integer prevRank  = 999;
    	Integer actualrank = null;
    	Map<Integer, String> rankRoleMap =  new HashMap<Integer, String>();
    	String [] roles = getUserRoles();
    	for(String role : roles )
    	{
    		String rank = Utils.getSingleValueAppConfig(role);
    		if(!Utils.isEmpty(rank))
    		{
    			Integer currentRank = Integer.valueOf(rank);
    			rankRoleMap.put(currentRank, role);
    			if(currentRank < prevRank)
    			{
    				prevRank = currentRank;
    				actualrank = currentRank;
    			}
    			
    		}
    	}
    	higestRole = rankRoleMap.get(actualrank);
    	return higestRole;
    }

    @Override
    public String[] getUserRoles() {
        String[] roles = null;
        if(this.getAuthorities() != null ) {
            roles = new String[this.getAuthorities().length];
            for(int i=0;i<this.getAuthorities().length;i++) {
                roles[i] = this.getAuthorities()[i].getAuthority();
            }
        } else {
            roles = new String[0];
        }
        return roles;
    }
	@Override
	public String getHighestRole( String lob ){
		String higestRole = null;
		Integer prevRank = 999;
		Integer actualrank = null;
		Map<Integer, String> rankRoleMap = new HashMap<Integer, String>();
		String[] roles = getUserRoles();
		
		roles = getLobRoles(lob,roles);
		
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
	
	private String[] getLobRoles( String lob, String[] roles ){
		List<String> lobRoles = new ArrayList<String>();
		String[] lobConfigRoles = Utils.getMultiValueAppConfig( "LOB_" + lob );
		if( Utils.isEmpty( lobConfigRoles ) ) return roles;

		for( String role : roles ){
			for( String configRole : lobConfigRoles ){
				if( configRole.equalsIgnoreCase( role ) ){
					lobRoles.add( role );
				}
			}

		}

		return lobRoles.toArray( new String[ lobRoles.size() ] );
	}
	
	@Override
	 public String[]  getSortedRoles(String[] roles)
	 {
		 return null;
	 }
}
