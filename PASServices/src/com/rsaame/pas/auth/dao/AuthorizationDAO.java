package com.rsaame.pas.auth.dao;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.mindtree.ruc.cmn.base.BaseDBDAO;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.kaizen.framework.model.ServiceContext;
import com.rsaame.pas.dao.model.SsVMasLookup;
import com.rsaame.pas.dao.model.SsVMasLookupId;
import com.rsaame.pas.dao.model.TTrnRoleFunScrSecPriv;
import com.rsaame.pas.svc.constants.SvcConstants;

/**
 * creates the role-function-screen-section-privilege data for the PAS system 
 * to be called during system start up.
 * 
 */
public class AuthorizationDAO extends BaseDBDAO implements IAuthorizationDAO{

	private Map<String, Map<String,String>> roleFuntionMap =  new HashMap<String, Map<String,String>>();

	private static final String PRODUCT =  "product";
	private static final String SBS =  "SBS";
	
	public Map<String, Map<String,String>> getAuthenticationDetails()
	{
		String [] pramName = {PRODUCT};
		Object[] value={SBS};
		HibernateTemplate hibernateTemplate = getHibernateTemplate();
		List<TTrnRoleFunScrSecPriv> authDetails = hibernateTemplate.findByNamedQueryAndNamedParam("getRoleFunScrSecPriv",pramName,value);
		
		Iterator<TTrnRoleFunScrSecPriv> authDetailsIterator = authDetails.iterator();
		
		
			while(authDetailsIterator.hasNext())
			{
				TTrnRoleFunScrSecPriv trnRoleFunScrSecPriv = authDetailsIterator.next();
				String roleFuntion = Utils.concat(trnRoleFunScrSecPriv.getId().getRoleFk(),SvcConstants.DELIMITER,trnRoleFunScrSecPriv.getId().getFunctionFk(),SvcConstants.DELIMITER,
						ServiceContext.getLocation());
				//String roleFuntion = trnRoleFunScrSecPriv.getId().getRoleFk()+DELIMITER+trnRoleFunScrSecPriv.getId().getFunctionFk();
				String screenSection = Utils.concat(trnRoleFunScrSecPriv.getId().getScreenFk(),SvcConstants.DELIMITER,trnRoleFunScrSecPriv.getId().getSectionFk());
				//String screenSection  = trnRoleFunScrSecPriv.getId().getScreenFk()+DELIMITER+trnRoleFunScrSecPriv.getId().getSectionFk();
				String privilege = trnRoleFunScrSecPriv.getPrivilegeFk();

				if(!roleFuntionMap.containsKey(roleFuntion)) {
					Map<String, String> screenSectionppriv = new HashMap<String, String>();
					screenSectionppriv.put(screenSection, privilege);
					roleFuntionMap.put(roleFuntion,screenSectionppriv);
				}
				else
				{
					Map<String, String> screenSectionppriv = roleFuntionMap.get(roleFuntion);
					screenSectionppriv.put(screenSection, privilege);
					roleFuntionMap.put(roleFuntion, screenSectionppriv);
				}


			}
			/*
			 * For quick links creating roleFunction map manually from ss_v_mas_lookup
			 */

			List<String> roleList = Arrays.asList( Utils.getMultiValueAppConfig( "ALL_ROLES" ));
			List<String> lobList = Arrays.asList( Utils.getMultiValueAppConfig( "LOB_LIST" ));

			SsVMasLookup lookup;SsVMasLookupId id;
			List<SsVMasLookup> lookupList = hibernateTemplate.find( "from SsVMasLookup where id.category = 'PAS_LOB' and id.level1='ALL' " );
			List<String> userIdList = hibernateTemplate.find( "select distinct(id.level2) from SsVMasLookup where id.category = 'PAS_LOB' and id.level1='ALL'" );
			for( String role : roleList ){
				for( String userId : userIdList ){
					String roleFunction = Utils.concat( role, SvcConstants.DELIMITER, "CREATE_QUO",SvcConstants.DELIMITER,
							ServiceContext.getLocation() );
					for( String lob : lobList ){
						String screenSection = Utils.concat( "HOME_PAGE", SvcConstants.DELIMITER, "LOB_LINKS_", lob, "_", userId );
						String privilege = "EDIT";
						lookup = new SsVMasLookup();
						id = new SsVMasLookupId();
						id.setCategory( "PAS_LOB" );
						id.setLevel1( "ALL" );
						id.setLevel2( userId );
						id.setDescription( lob );
						int code = Integer.parseInt( Utils.getSingleValueAppConfig( lob+"_POLICY_TYPE" ));

						id.setCode( BigDecimal.valueOf( code ));
						lookup.setId( id );
						if( !lookupList.contains( lookup ) ){
							privilege = "HIDE";
						}
						if(privilege!="EDIT"){
							if( !roleFuntionMap.containsKey( roleFunction ) ){
								Map<String, String> screenSectionppriv = new HashMap<String, String>();
								screenSectionppriv.put( screenSection, privilege );
								roleFuntionMap.put( roleFunction, screenSectionppriv );
							}
							else{
								Map<String, String> screenSectionppriv = roleFuntionMap.get( roleFunction );
								screenSectionppriv.put( screenSection, privilege );
								roleFuntionMap.put( roleFunction, screenSectionppriv );
							}
						}
					}
				}
			}
		
		
		return roleFuntionMap;
		
		}
	/*public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}*/
	

}
	
	
