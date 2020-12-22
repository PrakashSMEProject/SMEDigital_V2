/**
 * 
 */
package com.rsaame.pas.pa.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.monoline.baseNavigation.IRHHelper;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.vo.bus.PersonalAccidentVO;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.cmn.CommonVO;

/**
 * @author M1021201
 *
 */
public class PersonalAccidentRHHelper implements IRHHelper {

	@Override
	public BaseVO mapRequestToVO(HttpServletRequest request, HttpServletResponse response, CommonVO commonVO) {

		if (request.getParameter( com.Constant.CONST_PAGETYPE ).equals(com.Constant.CONST_GENINFO)){

			PolicyDataVO policyDataVO = BeanMapper.map( request, PolicyDataVO.class );
			policyDataVO.getGeneralInfo().getInsured().setUpdateMaster( true );
			policyDataVO.setPolicyType( policyDataVO.getScheme().getPolicyCode() );
			policyDataVO.setPolicyTerm( 1 );
			policyDataVO.setPolicyClassCode( 5 );
			
			UserProfile userProfile = (UserProfile)request.getSession().getAttribute(AppConstants.SESSION_USER_PROFILE_VO);
			if(!Utils.isEmpty(userProfile)){
				policyDataVO.setLoggedInUser(userProfile);
			}
			commonVO.setLoggedInUser(userProfile);
			policyDataVO.setCommonVO(commonVO);
			return policyDataVO;
		}
		else{
			PersonalAccidentVO personalAccidentVO = BeanMapper.map(request, PersonalAccidentVO.class);
			
			UserProfile userProfile = (UserProfile)request.getSession().getAttribute(AppConstants.SESSION_USER_PROFILE_VO);
			if(!Utils.isEmpty(userProfile)){
				personalAccidentVO.setLoggedInUser(userProfile);
			}
			commonVO.setLoggedInUser(userProfile);
			personalAccidentVO.setCommonVO(commonVO);

			return personalAccidentVO;
		}
	}

	@Override
	public BaseVO saveData(HttpServletRequest request, HttpServletResponse response, Response mtrucResponse, BaseVO baseVO) {
		
		PolicyDataVO policyDataVO = null;
		if (request.getParameter( com.Constant.CONST_PAGETYPE ).equals(com.Constant.CONST_GENINFO)){
		
			((PolicyDataVO)baseVO).setStatus(((PolicyDataVO)baseVO).getCommonVO().getStatus());
			policyDataVO = (PolicyDataVO) TaskExecutor.executeTasks("GEN_INFO_COMMON_SAVE", baseVO);
			TaskExecutor.executeTasks("COINSURANCE_SAVE", policyDataVO);
			TaskExecutor.executeTasks("FINANCIER_SAVE", policyDataVO);
		}
		else{
			PolicyDataVO generalInfo = (PolicyDataVO) TaskExecutor.executeTasks("GEN_INFO_COMMON_LOAD", baseVO);
			
			PersonalAccidentVO personalAccidentVO = new PersonalAccidentVO();
			personalAccidentVO.setPersonalAccidentPersonVO(((PersonalAccidentVO)baseVO).getPersonalAccidentPersonVO());
			personalAccidentVO.setGeneralInfo(generalInfo.getGeneralInfo());
			personalAccidentVO.setCommonVO(generalInfo.getCommonVO());
			personalAccidentVO.setScheme(generalInfo.getScheme());
			personalAccidentVO.setAuthenticationInfoVO(generalInfo.getAuthenticationInfoVO());
			personalAccidentVO.setPolicyType(generalInfo.getPolicyType());
			
/*			PersonalAccidentVO personalAccidentVO = (PersonalAccidentVO) TaskExecutor.executeTasks("GEN_INFO_COMMON_LOAD", baseVO);
			personalAccidentVO.setPersonalAccidentPersonVO(((PersonalAccidentVO)baseVO).getPersonalAccidentPersonVO());*/
			
			policyDataVO = (PolicyDataVO) TaskExecutor.executeTasks("PERSONAL_ACCIDENT_DETAILS_SAVE", personalAccidentVO);
		}
		
		return policyDataVO;
	}

	@Override
	public void ratingPostProcessing(HttpServletRequest request, BaseVO baseVO) {
		// TODO Auto-generated method stub

	}

	@Override
	public void postSaveProcessing(HttpServletRequest request,
			HttpServletResponse response, Response mtrucResponse, BaseVO baseVO) {
		
		PolicyContext policyContext = PolicyContextUtil.getPolicyContext( request );
		policyContext.setCommonDetails(((PolicyDataVO)baseVO).getCommonVO());
		
		
	}

	@Override
	public BaseVO loadData(HttpServletRequest request,
			HttpServletResponse response, BaseVO baseVO) {
		
		if (request.getParameter( com.Constant.CONST_PAGETYPE ).equals(com.Constant.CONST_GENINFO))
		{			
			baseVO = (PolicyDataVO) TaskExecutor.executeTasks("GEN_INFO_COMMON_LOAD", baseVO);
		}
		return baseVO;
	}

	@Override
	public void mapVOTORequest(HttpServletRequest request,
			HttpServletResponse response, BaseVO baseVO) {
		
		if(!Utils.isEmpty(request.getParameter(AppConstants.COUNTRY_LOOKUP_VAL)))
			request.setAttribute( AppConstants.COUNTRY_LOOKUP_VAL, request.getParameter(AppConstants.COUNTRY_LOOKUP_VAL).toString() );
		request.setAttribute( AppConstants.PAGE_VALUE, (PolicyDataVO)baseVO );

	}

	@Override
	public Boolean isConsolidatedReferralScreen(HttpServletRequest request,
			BaseVO baseVO) {
		// TODO Auto-generated method stub
		//Sonar fix for Null is returned instead of Boolean
		return false;
	}

	@Override
	public void referralAprrove(HttpServletRequest request,
			HttpServletResponse response, BaseVO baseVO) {
		// TODO Auto-generated method stub
		
	}

}
