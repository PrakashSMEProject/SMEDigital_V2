/**
 * 
 */
package com.rsaame.pas.clauses.svc;

import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.clauses.dao.IViewClausesDAO;
import com.rsaame.pas.dao.cmn.ISectionLoadDAO;

/**
 * @author m1016303
 *
 */
public class ViewClausesSvc extends BaseService {

		IViewClausesDAO viewClauseDAO;
	/* (non-Javadoc)
	 * @see com.mindtree.ruc.cmn.reflect.IMethodInvocation#invokeMethod(java.lang.String, java.lang.Object[])
	 */
	@Override
	public Object invokeMethod(String methodName, Object... args) {
		
		BaseVO returnValue = null;
		
		if( "getClauses".equals( methodName ) ){
			returnValue = getClauses( (BaseVO) args[ 0 ] );
		} else if( "getNonStdClauses".equals( methodName ) ){
			returnValue = getNonStdClauses( (BaseVO) args[ 0 ] );
		} else if( "saveClauses".equals( methodName ) ){
			returnValue = saveClauses( (BaseVO) args[ 0 ] );
		} else if( "saveNonStdClauses".equals( methodName ) ){
			returnValue = saveNonStdClauses( (BaseVO) args[ 0 ] );
		}else if( "insertReferralForConditions".equals( methodName ) ){
			returnValue = insertReferralForConditions( (BaseVO) args[ 0 ] );
		}
		
		return returnValue;
	}
	/**
	 * Returns non standard clauses saved for the policy id and endorsement id combination from T_TRN_NON_STD_TEXT(_QUO)
	 * @param baseVO
	 * @return
	 */
	private BaseVO getNonStdClauses( BaseVO baseVO ){
		return viewClauseDAO.getNonStdClauses(baseVO);
	}
	
	/**
	 * Saves Non Std Text which includes non std conditions, exclusions and warranties
	 * @param baseVO
	 * @return
	 */
	private BaseVO saveNonStdClauses( BaseVO baseVO ){
		return viewClauseDAO.saveNonStandardClauses( baseVO );
	}
	/**
	 * Saves standard clauses which includes conditions, exclusions and warranties
	 * @param baseVO
	 * @return
	 */
	private BaseVO saveClauses( BaseVO baseVO ){
		return viewClauseDAO.saveClauses(baseVO);
	}

	private BaseVO getClauses(BaseVO baseVO) {
		return viewClauseDAO.getClauses(baseVO);
	}

	/**
	 * @param viewClauseDAO the viewClauseDAO to set
	 */
	public void setViewClauseDAO(IViewClausesDAO viewClauseDAO) {
		this.viewClauseDAO = viewClauseDAO;
	}
	
	private BaseVO insertReferralForConditions(BaseVO baseVO) {
		return viewClauseDAO.insertReferralForConditions(baseVO);
	}

	

}
