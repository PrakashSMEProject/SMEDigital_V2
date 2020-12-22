package com.rsaame.pas.partnermanagement.ui;

import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Redirection;
import com.mindtree.ruc.mvc.Redirection.Type;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.vo.svc.TMasPartnerMgmtVOHolder;

/**
 * This request handler will be invoked while saving the data from
 * Partner Management configuration screen
 * 
 * @author M1020859
 *
 */
public class PartnerManagementSaveRH implements IRequestHandler{

	private final static Logger logger = Logger.getLogger( PartnerManagementSaveRH.class );
	final static Redirection partner = new Redirection( "/jsp/partnerManagement.jsp", Type.TO_JSP );

	@Override
	public Response execute( HttpServletRequest request, HttpServletResponse responseObj ){
		
		
		logger.debug( "Start processing to save the screen contents" );
		Response response = new Response();
		String identifier = null;
		identifier = request.getParameter( "opType" );
		logger.debug( "opType is -----------> " + identifier );
		logger.debug( "Start mapping the request object to TMasPartnerMgmtVOHolder" );
		TMasPartnerMgmtVOHolder tmasPartnerMgmtVOHolder = BeanMapper.map( request, TMasPartnerMgmtVOHolder.class );
		/** Start setting the relevant fields required for persisting the object */
		if( !Utils.isEmpty( tmasPartnerMgmtVOHolder ) ){
			tmasPartnerMgmtVOHolder.setPmmStatus( new BigDecimal( 1 ) ); 
			if( !Utils.isEmpty( request.getParameter( com.Constant.CONST_SELECTED_PRODUCT ) ) ){ 
				if( request.getParameter( com.Constant.CONST_SELECTED_PRODUCT ).equals( String.valueOf(SvcConstants.HOME_POL_TYPE) ) ){
					if (!Utils.isEmpty(request.getParameter(com.Constant.CONST_CLASS_CODE))) {
						tmasPartnerMgmtVOHolder.setPmmClassCode( new BigDecimal( request.getParameter(com.Constant.CONST_CLASS_CODE) ) );
					} else {
						tmasPartnerMgmtVOHolder.setPmmClassCode( new BigDecimal( 2 ) );
					}
					tmasPartnerMgmtVOHolder.setPmmPtCode( new BigDecimal( request.getParameter( com.Constant.CONST_SELECTED_PRODUCT ) ) ); 
				}
				else if( request.getParameter( com.Constant.CONST_SELECTED_PRODUCT ).equals( "5" ) || request.getParameter( com.Constant.CONST_SELECTED_PRODUCT ).equals( "888" )){  // TRAVEL POLICY TYPE
					if (!Utils.isEmpty(request.getParameter(com.Constant.CONST_CLASS_CODE))) {
						tmasPartnerMgmtVOHolder.setPmmClassCode( new BigDecimal( request.getParameter(com.Constant.CONST_CLASS_CODE) ) );
					} else {
						tmasPartnerMgmtVOHolder.setPmmClassCode( new BigDecimal( 5 ) );
					}
					if (request.getParameter( com.Constant.CONST_SELECTED_PRODUCT ).equals( "888" )) {
						tmasPartnerMgmtVOHolder.setPmmPtCode( new BigDecimal( 5 ) );
					} else {
						tmasPartnerMgmtVOHolder.setPmmPtCode( new BigDecimal( request.getParameter( com.Constant.CONST_SELECTED_PRODUCT ) ) );
					}
				}
				else if( request.getParameter( com.Constant.CONST_SELECTED_PRODUCT ).equals( "50" ) ){ // SBS POLICY TYPE
					if (!Utils.isEmpty(request.getParameter(com.Constant.CONST_CLASS_CODE))) {
						tmasPartnerMgmtVOHolder.setPmmClassCode( new BigDecimal( request.getParameter(com.Constant.CONST_CLASS_CODE) ) );
					} else {
						tmasPartnerMgmtVOHolder.setPmmClassCode( new BigDecimal( 2 ) ); //TODO has to query from data base
					}
					tmasPartnerMgmtVOHolder.setPmmPtCode( new BigDecimal( request.getParameter( com.Constant.CONST_SELECTED_PRODUCT ) ) );
				}
			}
		}
		/** Construct the URL from properties file on the basis of selected promotional code on screen */
		logger.debug( "Constructing the URL based on selected promotional code" );
		if( (request.getParameter( com.Constant.CONST_SELECTED_PRODUCT ).equals( "7" )) || (request.getParameter( com.Constant.CONST_SELECTED_PRODUCT ).equals( "5" )) 
				|| request.getParameter( com.Constant.CONST_SELECTED_PRODUCT ).equals( "888" )){
			StringBuilder urlBuilder = null;//new StringBuilder(AppConstants.B2C_REQUEST_URL);
			if (request.getParameter( com.Constant.CONST_SELECTED_PRODUCT ).equals( "888" ) || request.getParameter( com.Constant.CONST_SELECTED_PRODUCT ).equals( "5" )) {
				urlBuilder = new StringBuilder( Utils.getSingleValueAppConfig( "B2C_REQUEST_URL_TRAVEL" ) );
				urlBuilder.append(AppConstants.B2C_TRAVEL_INITIAL_LOAD_CONTROLLER);
			} else {
				urlBuilder = new StringBuilder( Utils.getSingleValueAppConfig( "B2C_REQUEST_URL_HOME" ) );
				urlBuilder.append(AppConstants.B2C_HOME_INITIAL_LOAD_CONTROLLER);
			}
			tmasPartnerMgmtVOHolder.setPmmUrl( urlBuilder.toString() );
			logger.debug( "Generated URL is - " + urlBuilder.toString() );
		}
		tmasPartnerMgmtVOHolder.setPmmPreparedDate( new Date() );
		logger.debug( "Calling the TaskExecutor for executing the save task" );
		BaseVO baseVO = TaskExecutor.executeTasks( identifier, tmasPartnerMgmtVOHolder );
		TMasPartnerMgmtVOHolder masPartnerMgmt = (TMasPartnerMgmtVOHolder) baseVO;
		if( !Utils.isEmpty( masPartnerMgmt ) ){
			
			AppUtils.addErrorMessage( request, "pas.saveSuccessful" );
			response.setSuccess( true );
			response.setData( masPartnerMgmt );
			logger.debug( "Setting the generated URL into the request object" );
			request.setAttribute( "generatedURL", masPartnerMgmt.getPmmUrl() );
					
		}
		else{
			throw new BusinessException( "pas.src.Empty", null, "Error ocuured while saving the data. Please try after some time." );
		}
		logger.debug( "Executed taskExecutor" );
		return response;
	}

}
