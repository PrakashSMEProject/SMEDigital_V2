package com.rsaame.pas.recentActivity.svc;

import com.mindtree.ruc.cmn.base.BaseService;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.rsaame.pas.recentActivity.dao.IShowRecentActivityDAO;

public class ShowRecentActivitySvc extends BaseService implements IShowRecentActivitySvc{

	private IShowRecentActivityDAO showRecentActivityDAO;
	
	


	@Override
	public Object invokeMethod( String methodName, Object... args ){
		BaseVO returnValue = null;
		if( "showRecentEndorsements".equals( methodName ) ){
 			returnValue = showRecentEndorsements( (BaseVO) args[ 0 ] );
		}
		if( "showRecentQuotes".equals( methodName ) ){
 			returnValue = showRecentQuotes( (BaseVO) args[ 0 ] );
		}
		if( "showRecentRenewals".equals( methodName ) ){
 			returnValue = showRecentRenewals( (BaseVO) args[ 0 ] );
		}
		if( "showRecentNewBusiness".equals( methodName ) ){
 			returnValue = showRecentNewBusiness( (BaseVO) args[ 0 ] );
		}
		if( "showRenewalsQuote".equals( methodName ) ){
 			returnValue = showRenewalQuotes( (BaseVO) args[ 0 ] );
		}
		return returnValue;
	}

	
	/**
	 * This method is used to get recent endorsements done by a user
	 * @param baseVO
	 * @return
	 */
	@Override
	public BaseVO showRecentEndorsements( BaseVO baseVO ){
		
		 return showRecentActivityDAO.showRecentEndorsements( baseVO );
	}

	
	/**
	 * This method is used to get recent quotes created by a user
	 * @param baseVO
	 * @return
	 */
	@Override
	public BaseVO showRecentQuotes( BaseVO baseVO ){
		
		return showRecentActivityDAO.showRecentQuotes( baseVO );
	}

	
	/**
	 * This method is used to get recent quotes created by a user
	 * @param baseVO
	 * @return
	 */
	@Override
	public BaseVO showRenewalQuotes( BaseVO baseVO ){
		
		return showRecentActivityDAO.showRenewalQuotes(baseVO );
	}
	
	
	
	/**
	 * This method is used to get recent renewals done by a user
	 * @param baseVO
	 * @return
	 */
	@Override
	public BaseVO showRecentRenewals( BaseVO baseVO ){
		
		return showRecentActivityDAO.showRecentRenewals( baseVO );
	}

	
	/**
	 * This method is used to get recent policy created by a user
	 * @param baseVO
	 * @return
	 */
	@Override
	public BaseVO showRecentNewBusiness( BaseVO baseVO ){
		
		return showRecentActivityDAO.showRecentNewBusiness( baseVO );
	}


	/**
	 * @return the showRecentActivityDAO
	 */
	public IShowRecentActivityDAO getShowRecentActivityDAO(){
		return showRecentActivityDAO;
	}


	/**
	 * @param showRecentActivityDAO the showRecentActivityDAO to set
	 */
	public void setShowRecentActivityDAO( IShowRecentActivityDAO showRecentActivityDAO ){
		this.showRecentActivityDAO = showRecentActivityDAO;
	}

	
	
}
