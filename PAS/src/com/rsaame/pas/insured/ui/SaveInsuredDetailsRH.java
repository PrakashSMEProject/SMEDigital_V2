package com.rsaame.pas.insured.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.beanmap.BeanMapperFactory;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.request.vo.mapper.RequestToPolicyVOMapper;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.bus.GeneralInfoVO;
import com.rsaame.pas.vo.bus.InsuredVO;
import com.rsaame.pas.vo.bus.PolicyVO;

public class SaveInsuredDetailsRH implements IRequestHandler{

	private static final Logger logger = Logger.getLogger( SaveInsuredDetailsRH.class );

	@Override
	public Response execute( HttpServletRequest request, HttpServletResponse responseObj ){
		logger.info( "*****Inside SaveInsuredDetailsRH*****" );
		Response response = new Response();
		String identifier = request.getParameter( "opType" );

		PolicyVO policyVO = new PolicyVO(); 
		policyVO.setIsQuote( true );
		BaseBeanToBeanMapper<HttpServletRequest, PolicyVO> requestBeanMapper = (BaseBeanToBeanMapper) BeanMapperFactory.getMapperInstance( RequestToPolicyVOMapper.class );
		policyVO = (PolicyVO) requestBeanMapper.mapBean( request, policyVO );

		BaseVO baseVO = TaskExecutor.executeTasks( identifier, policyVO );
		logger.info( "*****Executed taskExecutor in SaveInsuredDetailsRH*****" );

		if( !Utils.isEmpty( baseVO ) ){
			response.setSuccess( true );
			response.setData( baseVO );
		}
		request.setAttribute( "policyDetails", baseVO );

		/* To pass back the action identifier as part of response header */
		responseObj.setHeader( "actionIdentifier", identifier );

		return response;
	}

}
