package com.rsaame.pas.insured.ui;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.vo.app.SearchInsuredSummaryVO;
import com.rsaame.pas.vo.app.SearchInsuredVO;

public class SearchInsuredResultRH implements IRequestHandler{
	
	private static final Logger logger = Logger.getLogger( SearchInsuredResultRH.class );
	private static String SEARCH_INSURED_RH = "SearchInsuredResultRH";

	@Override
	public Response execute( HttpServletRequest request, HttpServletResponse responseObj ){
		Response response = new Response();
		String identifier = request.getParameter( "opType" );
		String action = request.getParameter( "action" );
		
		SearchInsuredVO searchInsuredVO = new SearchInsuredVO();
		
		if( !Utils.isEmpty( action ) && action.equalsIgnoreCase( "GI_PAGE" ) ){
			searchInsuredVO = AppUtils.mapRequestToSearchInsuredVO( request, searchInsuredVO );
		}
		else{
			searchInsuredVO = BeanMapper.map( request, SearchInsuredVO.class );
		}

		UserProfile userProfile = (UserProfile)request.getSession().getAttribute(AppConstants.SESSION_USER_PROFILE_VO);
		
		searchInsuredVO.setBrokerId( String.valueOf( userProfile.getRsaUser().getBrokerId() ) );
		
		String ccgCode = AppUtils.getCcgCode(userProfile);
		
		if(!Utils.isEmpty( ccgCode )){
			searchInsuredVO.setCcgCode( ccgCode );
		}
		
		logger.debug( "Bean Mapper created." );
		logger.debug( "Mapping done." );
		
		BaseVO baseVO = TaskExecutor.executeTasks( identifier, searchInsuredVO );
		logger.debug( "*****Executed taskExecutor*****" );

		SearchInsuredSummaryVO summaryVO = (SearchInsuredSummaryVO) baseVO;

		if( Utils.isEmpty( summaryVO.getInsuredArray() ) ){
			throw new BusinessException( "pas.src.Empty", null, "No records found." );
		}

		if( !Utils.isEmpty( baseVO ) ){
			response.setSuccess( true );
			response.setData( baseVO );
		}

		return response;
	}

}
