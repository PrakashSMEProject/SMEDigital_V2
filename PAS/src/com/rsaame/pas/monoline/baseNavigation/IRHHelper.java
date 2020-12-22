/**
 * 
 */
package com.rsaame.pas.monoline.baseNavigation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.vo.cmn.CommonVO;

/**
 * @author M1014644
 *
 */
public interface IRHHelper{

	BaseVO mapRequestToVO( HttpServletRequest request, HttpServletResponse response, CommonVO commonVO );

	BaseVO saveData( HttpServletRequest request, HttpServletResponse response, Response mtrucResponse, BaseVO baseVO );

	void ratingPostProcessing( HttpServletRequest request, BaseVO baseVO );

	void postSaveProcessing( HttpServletRequest request, HttpServletResponse response, Response mtrucResponse, BaseVO baseVO );

	BaseVO loadData( HttpServletRequest request, HttpServletResponse response, BaseVO baseVO );
	
	void mapVOTORequest( HttpServletRequest request, HttpServletResponse response,BaseVO baseVO);
	
	void referralAprrove( HttpServletRequest request, HttpServletResponse response,BaseVO baseVO);
	
	/**
	 * Method to set the consolidated referral screen flag to drive the referral pop up display
	 * If it is going to be the final screen for the LOB, based on screen attribute / or logic this method should return true/false
	 * @param request
	 * @param baseVO
	 * @return Boolean
	 * @since Post Phase 3
	 */
	Boolean isConsolidatedReferralScreen(HttpServletRequest request, BaseVO baseVO);
	
}
