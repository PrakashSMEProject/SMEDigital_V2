/**
 * 
 */
package com.rsaame.pas.tradeLicense.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindtree.ruc.cmn.log.Logger;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.mindtree.ruc.mvc.IRequestHandler;
import com.mindtree.ruc.mvc.Response;
import com.rsaame.pas.ui.cmn.PolicyContext;
import com.rsaame.pas.util.AppConstants;
import com.rsaame.pas.util.AppUtils;
import com.rsaame.pas.util.PolicyContextUtil;
import com.rsaame.pas.vo.bus.PolicyVO;
import java.io.File;
import java.util.List;
import com.rsaame.pas.dao.utils.DAOUtils;	
import com.rsaame.pas.svc.utils.SvcUtils;
/**
 * @author M1016284
 *
 */
public class CheckForTradeLicenseRH implements IRequestHandler {

	private final static Logger logger = Logger.getLogger( CheckForTradeLicenseRH.class );
	private static final String CHECK_TRADE_LICENSE_RENEWAL_QUOTE ="CHECK_TRADE_LICENSE_RENEWAL_QUOTE";
	
	@Override
	public Response execute( HttpServletRequest request, HttpServletResponse responseObj ){
		
		Response response = new Response();
		DataHolderVO<Boolean> isRenQuote = new DataHolderVO<Boolean>();
		logger.debug("*****Checking for trade license*****");
		
		/*  Identifier is passed as a parameter to TaskExecutor call 
		 */
		
		String identifier = request.getParameter( "opType" );
		//CTS - 21.10.2020 - CR#16903 IA Emirates CR - Starts
		String PLIndicator=request.getParameter( "PLIndicator" );
		//CTS - 21.10.2020 - CR#16903 IA Emirates CR - End
		PolicyContext polContext = PolicyContextUtil.getPolicyContext( request );
		PolicyVO policyVO = polContext.getPolicyDetails();
		//CTS - 21.10.2020 - CR#16903 IA Emirates CR - Starts
		if("True".equalsIgnoreCase(PLIndicator)){
			List<String> KYCDtL=SvcUtils.populateKYCDt();
			boolean documentExists=false;
			if(KYCDtL.get(1).equals("1")){
				
				
				Long QuoteNum=polContext.getCommonDetails().getQuoteNo();
				
				if(!Utils.isEmpty(QuoteNum)){
					String InsuredCode=DAOUtils.FetchInsuredCode(QuoteNum.toString());
						if(!Utils.isEmpty(InsuredCode)){
							
							String Insuredfilepath = Utils.getSingleValueAppConfig( "FILE_UPLOAD_ROOT" )+"/"+ Utils.getSingleValueAppConfig( "FILE_UPLOAD_"+Utils.getSingleValueAppConfig("EMIRATE_DEFAULT_DOCID")+"_FOLDER" )+"/"+InsuredCode+"/";
							File Insuredfolder = new File(Insuredfilepath);
							File EmirateslistOfFiles[] = Insuredfolder.listFiles();
					
					if( !Utils.isEmpty(EmirateslistOfFiles) && EmirateslistOfFiles.length>=2){
					documentExists=true;
					response.setSuccess(true);
					}else if(!Utils.isEmpty(EmirateslistOfFiles) && EmirateslistOfFiles.length>=1){
						File Emirates=EmirateslistOfFiles[0];
						if(Emirates.getName().startsWith(Utils.getSingleValueAppConfig("EMIRATE_DEFAULT_DOCID")+"_"+InsuredCode)){
						response.addErrorKey("pas.emirates.back.notUploaded");
					   response.setSuccess(true);
						}else{
							response.addErrorKey("pas.emirates.front.notUploaded");
							   response.setSuccess(true);
						}
					}else{
						response.addErrorKey("pas.emirates.notUploaded");
						response.setSuccess(true);
						
					}
						}
				}
				
			
			}else{
				response.setSuccess(true);
				documentExists=true;
			}
		
		response.setData(documentExists);
		
		}else{
		//CTS - 21.10.2020 - CR#16903 IA Emirates CR - End
		//Below line is commented as already rules is configured for doing the same
		//validate(policyVO);
		
		/* Mapping: "quote_name_tradelicno" -> "generalInfo.insured.tradeLicenseNo" */
		if( !Utils.isEmpty( request.getParameter( "tradeLicNo" ) ) ){
 			policyVO.getGeneralInfo().getInsured().setTradeLicenseNo( request.getParameter( "tradeLicNo" ) ); 
 		}else{
 			policyVO.getGeneralInfo().getInsured().setTradeLicenseNo( null );
 		}
		
		/*
		 * 	Make a call to Task Executor only if it is a quote. If it's a policy, the insured's
		 * 	Trade License details must have been already uploaded during Convert to Policy.
		 */
		if( !Utils.isEmpty( policyVO ) && policyVO.getIsQuote() ){
			
			/* 
			 * 	Making TaskExecutor call to check if the user has uploaded Trade License details .
			 * 	If no file is found, exception is thrown, else the flow will continue for Dubai.
			 * For Oman, service to check for trade license is not required.
			 */
			
			
			if(!Utils.isEmpty(Utils.getSingleValueAppConfig(AppConstants.TRADE_LICENSE_CHECK)) && !Utils.getSingleValueAppConfig(AppConstants.TRADE_LICENSE_CHECK).equalsIgnoreCase("N") ){
				/*
				 * To check whether trade license is being copied during renewal quote generation 
				 */
				if(!Utils.isEmpty( policyVO.getAuthInfoVO().getTxnType()) && policyVO.getAuthInfoVO().getTxnType().equals(6)){
					TaskExecutor.executeTasks( CHECK_TRADE_LICENSE_RENEWAL_QUOTE, policyVO );
				}
				TaskExecutor.executeTasks( identifier, policyVO );
			}
			
			// Check if it is renewal quote
			DataHolderVO<Long>  polLinkingId = new DataHolderVO<Long>();
			polLinkingId.setData( policyVO.getPolLinkingId());					
			isRenQuote  = (DataHolderVO<Boolean>) TaskExecutor.executeTasks("CHECK_RENEWAL_QUOTE", polLinkingId);
	
		}
		
		/*
		 *  If the flow reaches this point that means Trade license details are found. Hence set Success as true. 
		 */
		
		response.setSuccess( true );
		/*responseObj.setHeader( "isJson", "true" );
		response.setResponseType( Response.Type.JSON );*/
		String sendMail = request.getParameter( "sendMail" );
		
		if(!Utils.isEmpty( sendMail )){
			if( !Utils.isEmpty( policyVO.getConCatRefMsgs() )
					&& policyVO.getConCatRefMsgs().contains( Utils.getSingleValueAppConfig( "BROKER_CREDIT_LIMIT_MESSAGE" ) ) ){
				AppUtils.sendCreditLimitMail( policyVO, "MESSAGE_CREDIT_LIMIT", request );
			}
			if( !Utils.isEmpty( policyVO.getConCatRefMsgs() )
					&& policyVO.getConCatRefMsgs().contains( Utils.getSingleValueAppConfig( "BROKER_CREDIT_LIMIT_APPROVAL" ) ) ){
				AppUtils.sendCreditLimitMail( policyVO, "MESSAGE_CREDIT_LIMIT", request );
			}
		}
		response.setData(isRenQuote.getData());
		}
		return response;
	}
	/*
	 * Oman : To validate effective date is later than created date
	 */
	private void validate(PolicyVO policyVO) {
		/*
		 * Oman : validate function called only for Oman to validate created date and effective date
		 */
		if(!Utils.isEmpty(Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION)) && 
				Utils.getSingleValueAppConfig(AppConstants.DEPLOYED_LOCATION).equalsIgnoreCase("30")){ 
		      TaskExecutor.executeTasks("PRM_PAGE_CREATED_DT_VAL", policyVO);
		}
		TaskExecutor.executeTasks("BROKER_ACC_STATUS_VAL", policyVO);
	}

}
