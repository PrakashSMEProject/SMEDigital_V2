/**
 * 
 */
package com.rsaame.pas.insured.ui;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Redirection;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.kaizen.vo.PASServiceContext;
import com.rsaame.pas.svc.utils.SvcUtils;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.vo.app.LookUpListVO;
import com.rsaame.pas.vo.app.LookUpVO;
import com.rsaame.pas.vo.bus.AdditionalInsuredInfoVO;
import com.rsaame.pas.vo.bus.GeneralInfoVO;
import com.rsaame.pas.vo.bus.InsuredVO;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.cmn.CommonVO;

/**
 * @author m1017160
 * 
 */
public class ViewInsuredDetailsCommonRH implements IRequestHandler{

	private static final Logger LOGGER = Logger.getLogger( ViewInsuredDetailsCommonRH.class );

	@Override
	public Response execute( HttpServletRequest request, HttpServletResponse responseObj ){
		LOGGER.info( "*****Inside ViewInsuredDetailsCommonRH*****" );
		
		Response response = new Response();
		String identifier = request.getParameter( "opType" );
		String insuredId = request.getParameter( "insuredId" );
		String lob = request.getParameter("LOB");

		/*PolicyDataVO policyDataVO = null;
		
		if(lob.equalsIgnoreCase( "HOME" )){
			policyDataVO = new HomeInsuranceVO();
		}else if(lob.equalsIgnoreCase( "TRAVEL" )){
			policyDataVO = new TravelInsuranceVO();
		}else if(lob.equalsIgnoreCase( "WC" )){
			policyDataVO = new WorkmenCompVO();
		}*/
		
		//added the below line of code for above commented one TODO: Needs to be get reviewed. 
		PolicyDataVO policyDataVO  = (PolicyDataVO) Utils.getBean("VO_"+lob);
		
		GeneralInfoVO generalInfoVO = new GeneralInfoVO();
		InsuredVO insuredVO = new InsuredVO();
		insuredVO.setInsuredId( Long.valueOf( insuredId ) );
		generalInfoVO.setInsured( insuredVO );
		//modified - cannot use hardcoded decription value for fetch - Start
		LookUpListVO lookUpListVO =  SvcUtils.getLookUpCodesList(AppConstants.INT_ACC_EXE_CATEGORY, AppConstants.INT_ACC_EXE_DEFAULT, AppConstants.INT_ACC_EXE_DEFAULT);
		
		Integer exeCode = null;
		if (!Utils.isEmpty(lookUpListVO.getLookUpList())) {
			List<LookUpVO> lookupList = lookUpListVO.getLookUpList();
			if(!Utils.isEmpty(lookupList.get(0)) && !Utils.isEmpty(lookupList.get(0).getCode())){
				exeCode = lookupList.get(0).getCode().intValue();
			}
			
		}
		//modified - cannot use hardcoded decription value for fetch - END
		generalInfoVO.setIntAccExecCode(exeCode); // Setting the default INT_ACC_EXE 
		/*
		 * set Issuing and processing branch in generalInfoVO. (Add to quote flow) 
		 */
		String branchCode = PASServiceContext.getLocation();
		if( !Utils.isEmpty( branchCode ) ){
			request.setAttribute( "LOCATION", branchCode );
			AdditionalInsuredInfoVO additionalInfo = new AdditionalInsuredInfoVO();
			additionalInfo.setIssueLoc( Integer.parseInt( branchCode ) );
			additionalInfo.setProcessingLoc( Integer.parseInt( branchCode ) );
			generalInfoVO.setAdditionalInfo( additionalInfo );
			
		}

		policyDataVO.setGeneralInfo( generalInfoVO );

		/*
		 * Session factory switching is not required for insured search, hence setting the status as true explicitly. 
		 * For insured search T_MAS_INSURED is searched, hence switching is not required  
		 */
		
		CommonVO commonVO=new CommonVO();
		commonVO.setIsQuote( true );
		policyDataVO.setCommonVO( commonVO );
		BaseVO baseVO = TaskExecutor.executeTasks( identifier, policyDataVO );
		LOGGER.debug( "*****Executed taskExecutor in ViewInsuredDetailsCommonRH*****" );

		if(!Utils.isEmpty( request.getParameter( com.Constant.CONST_ISADDTOQUOTE )) &&  request.getParameter( com.Constant.CONST_ISADDTOQUOTE ).equals( "true" )){
			request.setAttribute( com.Constant.CONST_ISADDTOQUOTE, true );
		}
		if( !Utils.isEmpty( baseVO ) ){
			response.setSuccess( true );
			response.setData( baseVO );
		}
		new Gson().toJson( baseVO );
		request.setAttribute( "policyDetails", baseVO );

		Object obj = request.getSession().getAttribute( com.Constant.CONST_COPYEXISTINGFLOW );
		if( !Utils.isEmpty( obj ) ){
			String copyExistingFlow = (String) obj;
			if( "COPY_TO_EXISTING_INSURED".equals( copyExistingFlow ) ){
				
				responseObj.setHeader( com.Constant.CONST_COPYEXISTINGFLOW, "COPY_TO_EXISTING_INSURED" );
				request.getSession().removeAttribute( com.Constant.CONST_COPYEXISTINGFLOW );
			}
		}
		
		if(!lob.equalsIgnoreCase( "HOME" ) && !lob.equalsIgnoreCase( "TRAVEL" )){
			response.setRedirection(new Redirection(  "COMMON_FUNCTIONALITY&lob="+lob, Redirection.Type.TO_NEW_OPERATION ));
		}

		return response;
	}

}
