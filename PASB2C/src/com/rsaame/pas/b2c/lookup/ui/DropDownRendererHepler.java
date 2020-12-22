/**
 * 
 */
package com.rsaame.pas.b2c.lookup.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.b2c.cmn.constants.AppConstants;
import com.rsaame.pas.vo.app.LookUpListVO;
import com.rsaame.pas.vo.app.LookUpVO;

/**
 * @author m1016303
 *
 */
public class DropDownRendererHepler{
	public static final String[] DEFAULT_SCHEME_CODE = Utils.getMultiValueAppConfig( "DEFAULT_SCHEME_CODE" );
	public static final String SCHEME_CATEGORY = Utils.getSingleValueAppConfig( "SCHEME_CATEGORY" );

	public static LookUpListVO getLookFilteredList( LookUpListVO lookUpL, HttpSession session ){

		//UserProfile userProfile = (UserProfile) session.getAttribute( AppConstants.SESSION_USER_PROFILE_VO );
		String loggedInUser = "";
		/*if( !Utils.isEmpty( userProfile ) ){
			loggedInUser = userProfile.getRsaUser().getHighestRole();
		}*/

		List<LookUpVO> lookupList = lookUpL.getLookUpList();
		List<LookUpVO> newLookupList = new ArrayList<LookUpVO>();
		List<LookUpVO> oldLookupList;
		if(!Utils.isEmpty( lookupList )){
		/*for( LookUpVO lkv : lookupList ){
			if( lkv.getCategory().equalsIgnoreCase( "RSA_USER" ) ){
				String userRole = SvcUtils.getLookUpDescription( "USER_ROLE_PAS", "ALL", "ALL", lkv.code.intValue() );
				if( getHighestRoleRank( userRole ) < getHighestRoleRank( loggedInUser ) || (getHighestRoleRank( loggedInUser )==1 && getHighestRoleRank( userRole ) == getHighestRoleRank( loggedInUser ))){
					newLookupList.add( CopyUtils.copySerializableObject( lkv ) );
				}
				}
			
		}*/
		/* This will set the default scheme in the dropdown list to DEFAULT_SCHEME_CODE */
		if(!Utils.isEmpty( lookupList ) && !Utils.isEmpty( lookupList.get( 0 ).getCategory() ) &&  lookupList.get( 0 ).getCategory() .equals( SCHEME_CATEGORY )){
			oldLookupList = new ArrayList<LookUpVO>();
			oldLookupList = CopyUtils.copy( lookupList, oldLookupList );
			Iterator<LookUpVO> iterator = oldLookupList.iterator();
			while(iterator.hasNext()){
				LookUpVO lkv = iterator.next();
				if(!Utils.isEmpty( lkv ) && !Utils.isEmpty( lkv.getDescription() )){
				if(lkv.getDescription().toLowerCase().contains( "flexi" ) || !(lkv.getDescription().toLowerCase().contains( "pre-pack" )) ){
					newLookupList.add( CopyUtils.copySerializableObject( lkv ) );
					iterator.remove();
				}
				}
			}
			newLookupList.addAll( oldLookupList );
		}
		}
		if(!Utils.isEmpty( newLookupList ) && newLookupList.size() > 0){
			LookUpListVO lookUpNew = new LookUpListVO();
			lookUpNew.setLookUpList( newLookupList );
			return lookUpNew;
		}
		
		return lookUpL;
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
	public static Integer getHighestRoleRank( String role ){
		return Integer.valueOf( Utils.getSingleValueAppConfig( role ) );

	}
	
	
}
