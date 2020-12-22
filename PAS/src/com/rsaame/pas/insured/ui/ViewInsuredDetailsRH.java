package com.rsaame.pas.insured.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.kaizen.vo.PASServiceContext;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.bus.AdditionalInsuredInfoVO;
import com.rsaame.pas.vo.bus.GeneralInfoVO;
import com.rsaame.pas.vo.bus.InsuredVO;
import com.rsaame.pas.vo.bus.PolicyVO;

public class ViewInsuredDetailsRH implements IRequestHandler{

	private static final Logger logger = Logger.getLogger( ViewInsuredDetailsRH.class );

	@Override
	public Response execute( HttpServletRequest request, HttpServletResponse responseObj ){
		logger.info( "*****Inside ViewInsuredDetailsRH*****" );
		Response response = new Response();
		String identifier = request.getParameter( "opType" );
		String insuredId = request.getParameter( "insuredId" );

		PolicyVO policyVO = new PolicyVO();
		GeneralInfoVO generalInfoVO = new GeneralInfoVO();
		InsuredVO insuredVO = new InsuredVO();
		insuredVO.setInsuredId( Long.valueOf( insuredId ) );
		/*VAT*/
		if(!Utils.isEmpty(policyVO)    && !Utils.isEmpty(policyVO.getGeneralInfo())   && !Utils.isEmpty(policyVO.getGeneralInfo().getInsured())  &&  !Utils.isEmpty(policyVO.getGeneralInfo().getInsured().getVatRegNo()) ){
		insuredVO.setVatRegNo(policyVO.getGeneralInfo().getInsured().getVatRegNo());
		}
		
		generalInfoVO.setInsured( insuredVO );
		
		
		/*
		 * set Issuing and processing branch in generalInfoVO. (Add to quote flow) 
		 */
		String branchCode = PASServiceContext.getLocation();
		if( !Utils.isEmpty( branchCode ) ){
			request.setAttribute("LOCATION", branchCode );
			AdditionalInsuredInfoVO additionalInfo = new AdditionalInsuredInfoVO();
			additionalInfo.setIssueLoc(Integer.parseInt(branchCode));
			additionalInfo.setProcessingLoc(Integer.parseInt(branchCode));
			generalInfoVO.setAdditionalInfo(additionalInfo);
		}
		
		policyVO.setGeneralInfo( generalInfoVO );
		
		/*
		 * Session factory switching is not required for insured search, hence setting the status as true explicitly. 
		 * For insured search T_MAS_INSURED is searched, hence switching is not required  
		 */
		policyVO.setIsQuote( true );
		BaseVO baseVO = TaskExecutor.executeTasks( identifier, policyVO );
		logger.debug( "*****Executed taskExecutor in ViewInsuredDetailsRH*****" );

		if( !Utils.isEmpty( baseVO ) ){
			response.setSuccess( true );
			response.setData( baseVO );
		}
		new Gson().toJson( baseVO );
		request.setAttribute( "policyDetails", baseVO );

		Object obj = request.getSession().getAttribute( com.Constant.CONST_COPYEXISTINGFLOW );
		if( !Utils.isEmpty( obj ) ){
			String copyExistingFlow = (String) obj;
			if( "COPY_TO_EXISTING_INSURED".equals( copyExistingFlow ) ) {
				responseObj.setHeader( com.Constant.CONST_COPYEXISTINGFLOW, "COPY_TO_EXISTING_INSURED" );
				request.getSession().removeAttribute( com.Constant.CONST_COPYEXISTINGFLOW );
			}
		}

		return response;
	}

}
