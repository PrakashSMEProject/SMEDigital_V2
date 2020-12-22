package com.rsaame.pas.endorsement;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.BaseBeanToBeanMapper;
import com.mindtree.ruc.cmn.beanmap.BeanMapperFactory;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Redirection;
import com.mindtree.ruc.mvc.Redirection.Type;
import com.mindtree.ruc.mvc.constants.Constants;
import com.rsaame.pas.request.vo.mapper.RequestToPolicyVOMapper;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.vo.bus.LocationVO;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.bus.RiskGroup;
import com.rsaame.pas.vo.bus.SectionVO;
import com.rsaame.pas.vo.bus.TaskVO;
import com.mindtree.ruc.mvc.Response;

public class UpdateTmasRH implements IRequestHandler{
	private static final Logger log = Logger.getLogger( UpdateTmasRH.class );

	private static final String OPTYPE_SECTION_RH = "SECTION";

	@Override
	public Response execute( HttpServletRequest request, HttpServletResponse response ){

		
		Response responseObj = new Response();
		PolicyContext policyContext = PolicyContextUtil.getPolicyContext( request );
		PolicyVO policyVO = policyContext.getPolicyDetails();
		PolicyVO baseVO = null;
		//BaseBeanToBeanMapper<HttpServletRequest, PolicyVO> requestBeanMapper = (BaseBeanToBeanMapper) BeanMapperFactory.getMapperInstance( RequestToPolicyVOMapper.class );
		//policyVO = (PolicyVO) requestBeanMapper.mapBean( request, policyVO );
			 	baseVO =  (PolicyVO) TaskExecutor.executeTasks("UPDATE_TMASINSURED", policyVO );
			 	
				 baseVO = (PolicyVO) TaskExecutor.executeTasks("ENDORSE_GENINFO_SAVE_INVSVC", policyVO );
			 	//responseObj.setData(baseVO);
			 	 baseVO.setIsQuote(false);
			 	if( !Utils.isEmpty( baseVO ) ){

					AppUtils.addErrorMessage( request, "pas.saveSuccessful" );
					responseObj.setSuccess( true );
					responseObj.setData( baseVO );

					/* Set the response obtained to Policy Context so that next sections can obtain the value using policy context */

					policyContext.setPolicyDetails( (PolicyVO) baseVO );
					//polContext.setCurrentSection(AppConstants.GENERAL_INFO_SECTION);

				}
		
		return responseObj;
	}

	
}
