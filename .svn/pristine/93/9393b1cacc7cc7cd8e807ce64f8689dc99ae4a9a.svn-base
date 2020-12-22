package com.rsaame.pas.kaizen.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.acegisecurity.GrantedAuthority;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.kaizen.framework.model.RSAUser;
import com.rsaame.kaizen.framework.model.ServiceContext;
import com.rsaame.pas.cmn.vo.IRSAUser;

public class RSAUserWrapper implements IRSAUser,Serializable{

    RSAUser kaizenRSAUser = null;
    
    public RSAUserWrapper(){
    		this.kaizenRSAUser = ServiceContext.getUser() ;
    }

	/**
     * Constructor with the basic parameters.
     * @param kaizenRSAUser.getUserName() <code>String</code>
     * @param password <code>String</code>
     * @param enabled <code>boolean</code>
     * @param authorities <code>GrantedAuthority</code>[]
     */
    private RSAUserWrapper(String username, String password, boolean enabled, GrantedAuthority[] authorities) {
        //super(username, password, enabled, authorities);
    }

    
    /**
     * Getter method for hasMessages.
     * @return hasMessages <code>boolean</code>
     */
    @Override
    public boolean hasMessages() {
        return kaizenRSAUser.isHasMessages();
    }

    /**
     * Getter method for agentId.
     * @return agentId <code>Integer</code>
     */
    @Override
    public Integer getAgentId() {
        return kaizenRSAUser.getAgentId();
    }

    /**
     * Getter method for branchCode.
     * @return branchCode <code>Integer</code>
     */
    @Override
    public Integer getBranchCode() {
        return kaizenRSAUser.getBranchCode();
    }

    /**
     * Getter method for brokerId.
     * @return brokerId <code>Integer</code>
     */
    @Override
    public Integer getBrokerId() {
        return kaizenRSAUser.getBrokerId();
    }

    /**
     * Getter method for countryCode.
     * @return countryCode <code>Integer</code>
     */
    @Override
    public Integer getCountryCode() {
        return kaizenRSAUser.getCountryCode();
    }

    /**
     * Getter method for defaultApprover.
     * @return defaultApprover <code>Integer</code>
     */
    @Override
    public Integer getDefaultApprover() {
        return kaizenRSAUser.getDefaultApprover();
    }
    /**
     * Getter method for defaultModule.
     * @return defaultModule <code>Integer</code>
     */
    @Override
    public Integer getDefaultModule() {
        return kaizenRSAUser.getDefaultModule();
    }
    /**
     * Getter method for employeeId.
     * @return employeeId <code>Integer</code>
     */
    @Override
    public Integer getEmployeeId() {
        return kaizenRSAUser.getEmployeeId();
    }
    /**
     * Getter method for userId.
     * @return userId <code>Integer</code>
     */
    @Override
    public Integer getUserId() {
        return kaizenRSAUser.getUserId();
    }
    /**
     * Getter method for loginAttempts.
     * @return loginAttempts <code>Integer</code>
     */
    @Override
    public Integer getLoginAttempts() {
        return kaizenRSAUser.getLoginAttempts();
    }
    /**
     * Getter method for statusId.
     * @return statusId <code>Integer</code>
     */
    @Override
    public Integer getStatusId() {
        return kaizenRSAUser.getStatusId();
    }
    /**
     * Getter method for hasMessages.
     * @return hasMessages <code>boolean</code>
     */
    @Override
    public boolean isHasMessages() {
        return kaizenRSAUser.isHasMessages();
    }
    /**
     * Getter method for profile.
     * @return profile <code>String</code>
     */
    @Override
    public String getProfile() {
        return kaizenRSAUser.getProfile();
    }
    
    /**
     * <code>String</code> representation of <code>RSAUser</code> object
     * @return <code>String</code> 
     */
    /*@Override
    public String toString()
    {
        return new ToStringBuilder(this)
            .append("AgentId:", getAgentId())
            .append("BranchCode:", getBranchCode())
            .append("BrokerId:", getBrokerId())
            .append("CountryCode:", getCountryCode())
            .append("DefaultApprover:", getDefaultApprover())
            .append("DefaultModule:", getDefaultModule())
            .append("EmployeeId:", getEmployeeId())
            .append("UserId:", getUserId())
            .append("LoginAttempts:", getLoginAttempts())
            .append("Profile:", getProfile())
            .append("StatusId:", getStatusId())
            .append("Username:", this.kaizenRSAUser.getUsername())
            .toString();
    }*/

    /**
     * Getter method for arabicName.
     * @return arabicName <code>String</code>
     */
    @Override
    public String getArabicName() {
        return kaizenRSAUser.getArabicName();
    }
    /**
     * Getter method for email.
     * @return email <code>String</code>
     */
    @Override
    public String getEmail() {
        return kaizenRSAUser.getEmail();
    }
    /**
     * Getter method for englishName.
     * @return englishName <code>String</code>
     */
    @Override
    public String getEnglishName() {
        return kaizenRSAUser.getEnglishName();
    }
    /**
     * Getter method for mobileNumber.
     * @return mobileNumber <code>String</code>
     */
    @Override
    public String getMobileNumber() {
        return kaizenRSAUser.getMobileNumber();
    }
    
    /**
     * This method will return the array of user-role String.
     * @return <code>String</code>[]
     */
    @Override
    public String[] getUserRoles() {
        String[] roles = null;
        if(this.kaizenRSAUser != null && this.kaizenRSAUser.getAuthorities() != null) {
            roles = new String[this.kaizenRSAUser.getAuthorities().length];
            for(int i=0;i<this.kaizenRSAUser.getAuthorities().length;i++) {
                roles[i] = this.kaizenRSAUser.getAuthorities()[i].getAuthority();
            }
        } else {
            roles = new String[0];
        }
        return roles;
    }
    
    
  
    //Method to sort the roles array accordingly : SBS roles based on rank - Home/Travel roles based on rank - other entries like Profile
   @Override
    public String[]  getSortedRoles(String[] roles)
    {
    	// get string of LOB_SBS roles from app_config
    	
    	String lobSbsRoleString = Utils.getSingleValueAppConfig("LOB_SBS");
    	String lobSbsRoles[] = lobSbsRoleString.split(",");
    	
    	String lobHomeRoleString = Utils.getSingleValueAppConfig("LOB_HOME");
    	String lobHomeRoles[] = lobHomeRoleString.split(",");
    	
    	String lobTravelRoleString = Utils.getSingleValueAppConfig("LOB_HOME");
    	String lobTravelRoles[] = lobTravelRoleString.split(",");
    	
    	// convert lobSbsRoles to a Map
    	
    	Map<String, Integer> allSbsRoleMap = new HashMap<String, Integer>();
    	for(String sbsRole : lobSbsRoles)
    	{
    		String rank = Utils.getSingleValueAppConfig(sbsRole);
    		allSbsRoleMap.put(sbsRole,  Integer.valueOf(rank));
    	
    	}
    	Map<String, Integer> allHomeTravelRoleMap = new HashMap<String, Integer>();
    	for(String homeRole : lobHomeRoles)
    	{
    		String rank = Utils.getSingleValueAppConfig(homeRole);
    		allHomeTravelRoleMap.put(homeRole,  Integer.valueOf(rank));
    	
    	}
    	for(String travelRole : lobTravelRoles)
    	{
    		String rank = Utils.getSingleValueAppConfig(travelRole);
    		allHomeTravelRoleMap.put(travelRole,  Integer.valueOf(rank));
    	}
    	
    	Map<Integer, String > SbsMap = new HashMap<Integer, String>();
    	Map<Integer, String> TravelHomeMap = new HashMap<Integer, String>();
    	List<String> otherRoles = new ArrayList<String>();
    	for(String role : roles)
    	{
    		if(allSbsRoleMap.get(role) != null)
    		{
    			
    			SbsMap.put(allSbsRoleMap.get(role), role);
    		}
    		else if( allHomeTravelRoleMap.get(role) != null)
    		{
    			TravelHomeMap.put(allHomeTravelRoleMap.get(role), role);
    		}
    		else
    		{
    			otherRoles.add(role);
    		}
    	}
    	
    	// now sort the arrays according to their ranks
    	
    	Map<Integer, String> sortedSbsRoleMap = new TreeMap<Integer, String>( new Comparator<Integer>() {
 
			@Override
			public int compare(Integer o1, Integer o2) {
				
					
				return o1.compareTo(o2);
			}
    	});
    	
    	sortedSbsRoleMap.putAll(SbsMap);
    	
    	Map<Integer, String> sortedHomeTravelRoleMap = new TreeMap<Integer, String>( new Comparator<Integer>() {
    		 
			@Override
			public int compare(Integer o1, Integer o2) {
				
					
				return o1.compareTo(o2);
			}
    	});
    	
    	sortedHomeTravelRoleMap.putAll(TravelHomeMap);
    	
    	String finalRoles[] = new String[sortedSbsRoleMap.size() + sortedHomeTravelRoleMap.size() + otherRoles.size()];
    	int i =0;
    	for(Entry<Integer, String> r : sortedSbsRoleMap.entrySet())
    	{
    		finalRoles[i] = r.getValue();
    		i++;
    	}
    	for(Entry<Integer, String> r : sortedHomeTravelRoleMap.entrySet())
    	{
    		finalRoles[i] = r.getValue();
    		i++;
    	}
    	for(String r : otherRoles)
    	{
    		finalRoles[i] = r;
    		i++;
    	}
    	
    	allSbsRoleMap = null;
    	allHomeTravelRoleMap= null;
    	SbsMap= null;
    	TravelHomeMap= null;
    	sortedSbsRoleMap= null;
    	sortedHomeTravelRoleMap= null;
    	lobSbsRoles = null;
    	lobHomeRoles = null;
    	lobTravelRoles = null;
    	otherRoles= null;
    	
    	return finalRoles;
    }
    
    //Return the role with the highest privilege.
    @Override
	public String getHighestRole()
    {
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

	/**
	 * @return Returns the userName.
	 */
    @Override
	public String getUserName() {
		return kaizenRSAUser.getUserName();
	}

}
