package com.rsaame.pas.insured.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Response;
//import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.vo.bus.PolicyVO;
import com.rsaame.pas.vo.cmn.CommonVO;

public class UpdateVATRefNoRH implements IRequestHandler{
	
	@Override
	public Response execute( HttpServletRequest request, HttpServletResponse response ){

		String action = request.getParameter( "action" );
		Long polId = Long.parseLong(request.getParameter( "polId" ));
		Long polEndtId = Long.parseLong(request.getParameter( "endtId" ));
		Boolean isSBS = Boolean.valueOf(request.getParameter( "isSBS" ));
		String lob = request.getParameter( "lob" );
				
		String responseData = null;
		
		Response responseObj = new Response();		
		
		if( action.equalsIgnoreCase( "UPDATE_VAT_REG_NO" ) ){
			
			String vatRefNo = request.getParameter( "vatRefNo" );

			//UserProfile profile = (UserProfile) request.getSession( false ).getAttribute( AppConstants.SESSION_USER_PROFILE_VO );	
			
			DataHolderVO<Object> dataHolder = new DataHolderVO<Object>();
			Object [] inputData = new Object[5];
			inputData[0] = vatRefNo;
			inputData[1] = polId;
			inputData[2] = polEndtId;
			inputData[3] = lob;
			inputData[4] = isSBS;
			if(isSBS){
				PolicyVO policyVO = PolicyContextUtil.getPolicyContext( request ).getPolicyDetails();
			}
			else{
				PolicyContext policyContext = PolicyContextUtil.getPolicyContext( request );
				CommonVO commonVO = policyContext.getCommonDetails();
				if(!Utils.isEmpty(commonVO.getLob() )){					
					System.out.println("LOB: "+commonVO.getLob().toString());
				}
			}			
			dataHolder.setData(inputData);		
			dataHolder = (DataHolderVO<Object>) TaskExecutor.executeTasks(action, dataHolder);
			Object [] outputData = (Object[]) dataHolder.getData();
			
			if((Integer)outputData[0] > 0 && (Integer)outputData[1] > 0){
				responseData = "Updated";
			}
			else{
				responseData = "Not updated";
			}						
			
			responseObj.setData( responseData);
		}
		return responseObj;
	}

}
