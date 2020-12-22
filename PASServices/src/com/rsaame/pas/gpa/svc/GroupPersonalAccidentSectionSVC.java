package com.rsaame.pas.gpa.svc;

import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.rsaame.pas.dao.cmn.BaseSectionSaveDAO;
import com.rsaame.pas.gpa.dao.IGroupPersonalAccidentSectionDAO;
/**
 * 
 * @author m1019834
 *
 */
public class GroupPersonalAccidentSectionSVC extends BaseService{
/**
 * Empty constructor for object creation.
 */
public GroupPersonalAccidentSectionSVC(){
	
	//SONARFIX -- 26/04/2018 -- DO NOTHING IN METHOD.
	
}
/**
 * Service layer initialization and object creation of groupPersonalAccidentSaveDAO.
 */
private IGroupPersonalAccidentSectionDAO groupPersonalAccidentSaveDAO;
/**
 * Service layer initialization and object creation of groupPersonalAccidentLoadDAO.
 */
private IGroupPersonalAccidentSectionDAO groupPersonalAccidentLoadDAO;
	
/**
 * first method executed in service layer GroupPersonalAccidentSectionSVC.
*/
public Object invokeMethod( String methodName, Object... args ){
		BaseVO returnValue = null;
		if( "loadGroupPersonalAccidentSectionSvc".equals( methodName ) ){
			returnValue = loadGroupPersonalAccidentSectionSvc( (BaseVO) args[ 0 ] );
		}
		if( "saveGroupPersonalAccidentSectionSvc".equals( methodName ) ){
 			returnValue = saveGroupPersonalAccidentSectionSvc( (BaseVO) args[ 0 ] );
		}
		
		return returnValue;
	}

/**
 * @param groupPersonalAccidentSaveDAO get values 
*/
public void setGroupPersonalAccidentSaveDAO( IGroupPersonalAccidentSectionDAO groupPersonalAccidentSaveDAO ){
		this.groupPersonalAccidentSaveDAO = groupPersonalAccidentSaveDAO;
	}

/**
 * @return the groupPersonalAccidentSaveDAO
*/
public IGroupPersonalAccidentSectionDAO getGroupPersonalAccidentSaveDAO(){
		return groupPersonalAccidentSaveDAO;
	}
	
/**
 * @param groupPersonalAccidentLoadDAO set value to groupPersonalAccidentLoadDAO
 * 
*/
public void setGroupPersonalAccidentLoadDAO(IGroupPersonalAccidentSectionDAO groupPersonalAccidentLoadDAO) {
		this.groupPersonalAccidentLoadDAO = groupPersonalAccidentLoadDAO;
	}
	
/** 
 * @return the groupPersonalAccidentLoadDAO
*/
public IGroupPersonalAccidentSectionDAO getGroupPersonalAccidentLoadDAO() {
		return groupPersonalAccidentLoadDAO;
	}

/**
 * Calling save section from service layer.
 * @param BaseVO set value to baseVO
 * @return the BaseVO
*/
private BaseVO saveGroupPersonalAccidentSectionSvc(BaseVO baseVO) {
		
		return ( (BaseSectionSaveDAO) groupPersonalAccidentSaveDAO ).save(baseVO);
	}
/**
 * Calling load section from service layer.
 * @param BaseVO set value to baseVO
 * @return the BaseVO
*/
private BaseVO loadGroupPersonalAccidentSectionSvc(BaseVO baseVO) {
		
		 return groupPersonalAccidentLoadDAO.loadGroupPersonalAccidentSection(baseVO);
	}
	
}