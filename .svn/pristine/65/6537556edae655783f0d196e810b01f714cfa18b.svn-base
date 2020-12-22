package com.rsaame.pas.b2c.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.rsaame.pas.b2b.ws.batch.handler.SBSBatchJobInvoker;
import com.rsaame.pas.b2b.ws.batch.input.BatchInput;
import com.rsaame.pas.b2b.ws.handler.EmailPolicyCreationRH;
import com.rsaame.pas.b2b.ws.handler.RetrievePolicyByPolicyNo;
import com.rsaame.pas.b2b.ws.handler.SBSCreatePolicyCommonRH;
import com.rsaame.pas.b2b.ws.mapper.SBSCreatePolicyRequestMapper;
import com.rsaame.pas.b2b.ws.mapper.SBSCreatePolicyResponseMapper;
import com.rsaame.pas.b2b.ws.util.WSAppUtils;
import com.rsaame.pas.b2b.ws.util.WSBusinessValidatorUtils;
import com.rsaame.pas.b2b.ws.util.WSDAOUtils;
import com.rsaame.pas.b2b.ws.util.WSBusinessValidatorUtils.SBSErrorCodes;
import com.rsaame.pas.b2b.ws.validators.CommonAcceptQuoteValidators;
import com.rsaame.pas.b2b.ws.validators.RetrievePolicyByPolicyNoValidator;
import com.rsaame.pas.b2b.ws.validators.SBSWSValidators;
import com.rsaame.pas.b2b.ws.vo.CreateSBSPolicyRequest;
import com.rsaame.pas.b2b.ws.vo.CreateSBSPolicyResponse;
import com.rsaame.pas.b2b.ws.vo.RetrievePolicyByPolicyNoResponse;
import com.rsaame.pas.b2c.cmn.base.BaseController;
import com.rsaame.pas.b2c.ws.utilities.WebServiceAudit.mapper.UpdateWebServiceAuditMapper;
import com.rsaame.pas.b2c.ws.utilities.WebServiceAudit.mapper.WebServiceAuditMapper;
import com.rsaame.pas.b2c.ws.utilities.WebServiceAudit.vo.HeaderInfo;
import com.rsaame.pas.b2c.ws.utilities.WebServiceAudit.vo.WebServiceAudit;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.query.constants.QueryConstants;
import com.rsaame.pas.vo.bus.PolicyVO;

@Controller
@RequestMapping("/rsaservices")
public class SBSPolicyController extends BaseController {

	private final static Logger LOGGER = Logger.getLogger(SBSPolicyController.class);
	
	@RequestMapping(value = "**/policy", method = RequestMethod.POST)
	public @ResponseBody CreateSBSPolicyResponse getSBSPolicy(
			@RequestBody CreateSBSPolicyRequest createSBSPolicyRequest, HttpServletRequest request, HttpSession session,
			HttpServletResponse response) {
		CreateSBSPolicyResponse createSBSPolicyResponse = new CreateSBSPolicyResponse();
		WebServiceAudit webServiceAudit = new WebServiceAudit();
		WebServiceAuditMapper webServiceAuditMapper = new WebServiceAuditMapper();
		UpdateWebServiceAuditMapper updateWebServiceAuditMapper = new UpdateWebServiceAuditMapper();
		SBSCreatePolicyCommonRH sbsCreatePolicyCommonRH = new SBSCreatePolicyCommonRH();
		PolicyVO policyVO=new PolicyVO();
		Long twa_id = null;
		try {
			StopWatch stopWatch = new StopWatch();
			stopWatch.start();
			LOGGER.info("Enter SBSGetPolicyController:createSBSPolicy method.");
			// Step 0
			// Adding the request to the Audit Table.
			HeaderInfo headerInfo = new HeaderInfo();
			headerInfo.setHeaderInfo(WSAppUtils.setHeaderInfo(request));
			
			sbsCreatePolicyCommonRH.fectchDefaultValues(policyVO, request);
			sbsCreatePolicyCommonRH.fectchUserId(policyVO,request);
			
			Boolean checkPreviousRequestStatus = WSDAOUtils.checkPreviousRequestStatus(Long.parseLong(createSBSPolicyRequest.getQuoteId()));
			if(checkPreviousRequestStatus) {
				List<SBSWSValidators> sbswsValidatorsList = new ArrayList<SBSWSValidators>();
				SBSWSValidators sbsWSValidators = new SBSWSValidators();
				sbsWSValidators = WSBusinessValidatorUtils.businessErrorMapping("Previous Create Policy Request Status",
						"SBSWS_ERR_099", SBSErrorCodes.ERROR.name(), "Business");
				sbswsValidatorsList.add(sbsWSValidators);
				createSBSPolicyResponse.setSbswsValidators(sbswsValidatorsList);
				webServiceAudit = webServiceAuditMapper.mapCreateSBSPolicyToAudit(createSBSPolicyRequest,
						createSBSPolicyResponse, "CreateSBSPolicy", headerInfo,policyVO);
				twa_id = webServiceAudit.getTwa_id().longValue();
				createSBSPolicyResponse.setPolicyInternalReference("" + twa_id);
				return createSBSPolicyResponse;
			}
			webServiceAudit = webServiceAuditMapper.mapCreateSBSPolicyToAudit(createSBSPolicyRequest,
					createSBSPolicyResponse, "CreateSBSPolicy", headerInfo,policyVO);
			twa_id =webServiceAudit.getTwa_id().longValue();
			// 1. call validator and returns a list of validator message and
			// status.
			LOGGER.info("Calling SBS Accept Quote Validator");
			policyVO.setQuoteNo(Long.parseLong(createSBSPolicyRequest.getQuoteId()));
			//CTS - 13.10.2020 - JLT UAT CR - Check whether the quote has been converted to Policy - Starts
			List<Object[]> resultSet = null;
			resultSet = DAOUtils.getSqlResultForPas( QueryConstants.GET_QUOTE_STATUS_BY_QUO_NO,  createSBSPolicyRequest.getQuoteId());
			
			if( !Utils.isEmpty( resultSet ) && resultSet.size() > 0 ){
				int documentCode = Integer.valueOf( String.valueOf( resultSet.get( 0 )[0] ) );
				int quoteStatus =  Integer.valueOf( String.valueOf( resultSet.get( 0 )[1] ) );
				
				if((documentCode == 6 || documentCode == 5) ){
					policyVO.setStatus(quoteStatus);
				}
			}
			//CTS - 13.10.2020 - JLT UAT CR - Check whether the quote has been converted to Policy - Ends
			CommonAcceptQuoteValidators commonAcceptQuoteValidators = new CommonAcceptQuoteValidators();
			List<SBSWSValidators> sbswsValidatorsList = new ArrayList<SBSWSValidators>();
			sbswsValidatorsList = commonAcceptQuoteValidators.validate(policyVO,createSBSPolicyRequest, sbswsValidatorsList);
			if (sbswsValidatorsList.size() != 0) {
				List<SBSWSValidators> sbswsValidatorsList2 = new ArrayList<>();
				for (SBSWSValidators validators : sbswsValidatorsList) {
					if (validators.getCategory().contains(com.Constant.CONST_ERROR2)) {
						SBSWSValidators sbswsValidators = new SBSWSValidators();
						sbswsValidators.setCategory(validators.getCategory());
						sbswsValidators.setCode(validators.getCode());
						sbswsValidators.setField(validators.getField());
						sbswsValidators.setMessage(validators.getMessage());
						sbswsValidators.setType(validators.getType());
						sbswsValidatorsList2.add(sbswsValidators);
					}
				}
				if (sbswsValidatorsList2.size() != 0) {
					//CTS - 13.10.2020 - JLT UAT CR - Check whether the quote has been converted to Policy - Starts
					for(SBSWSValidators validators : sbswsValidatorsList2) {
						if(validators.getCode()=="SBSWS_ERR_100") {
							if(sbswsValidatorsList2.size()==1) {
								createSBSPolicyResponse = WSDAOUtils.fetchPolicyData(createSBSPolicyRequest);
								createSBSPolicyResponse.setPolicyInternalReference(""+twa_id);
								updateWebServiceAuditMapper.mapCreateSBSPolicyToAudit(webServiceAudit, createSBSPolicyResponse);
								return createSBSPolicyResponse;
							}
							else {
								sbswsValidatorsList2.remove(validators);
								break;
							}
							
						}
						
					}
					//CTS - 13.10.2020 - JLT UAT CR - Check whether the quote has been converted to Policy - Ends
					createSBSPolicyResponse.setSbswsValidators(sbswsValidatorsList2);
					updateWebServiceAuditMapper.mapCreateSBSPolicyToAudit(webServiceAudit, createSBSPolicyResponse);
					createSBSPolicyResponse.setPolicyInternalReference(""+twa_id);
					return createSBSPolicyResponse;
				}
			}

			/* Call request mapper and which returns dataHolderVO */
			DataHolderVO<List<BaseVO>> dataHolderVO = new DataHolderVO<List<BaseVO>>();
			SBSCreatePolicyRequestMapper sbsCreatePolicyRequestMapper = new SBSCreatePolicyRequestMapper();
			sbsCreatePolicyRequestMapper.mapRequestToPolicyVO(createSBSPolicyRequest, dataHolderVO, request, session,policyVO);

			/* Call the TradeLicence method to validate the size */
			sbswsValidatorsList = commonAcceptQuoteValidators.ValidateTradeLicenceDoc(dataHolderVO,
					sbswsValidatorsList);
			if (sbswsValidatorsList.size() != 0) {
				List<SBSWSValidators> sbswsValidatorsList2 = new ArrayList<>();
				for (SBSWSValidators validators : sbswsValidatorsList) {
					if (validators.getCategory().contains(com.Constant.CONST_ERROR2)) {
						SBSWSValidators sbswsValidators = new SBSWSValidators();
						sbswsValidators.setCategory(validators.getCategory());
						sbswsValidators.setCode(validators.getCode());
						sbswsValidators.setField(validators.getField());
						sbswsValidators.setMessage(validators.getMessage());
						sbswsValidators.setType(validators.getType());
						sbswsValidatorsList2.add(sbswsValidators);
					}
				}
				if (sbswsValidatorsList2.size() != 0) {
					createSBSPolicyResponse.setSbswsValidators(sbswsValidatorsList2);
					updateWebServiceAuditMapper.mapCreateSBSPolicyToAudit(webServiceAudit, createSBSPolicyResponse);
					createSBSPolicyResponse.setPolicyInternalReference(""+twa_id);
					return createSBSPolicyResponse;
				}
			}

			/* Call the handler with input dataHolderVO */
			sbsCreatePolicyCommonRH.excuteSBSCreatePolicyHandler(dataHolderVO, createSBSPolicyRequest);

			/*
			 * call response mapper with dataHolderVO and
			 * returnssbsCreatePolicyResponseMapper
			 */
			SBSCreatePolicyResponseMapper sbsCreatePolicyResponseMapper = new SBSCreatePolicyResponseMapper();
			sbsCreatePolicyResponseMapper.mapDataToResponse(createSBSPolicyResponse, dataHolderVO, request);

			/*
			 * Auto email to the customer to notify that the policy is
			 * processed.
			 */
             //Not sending email after CTP as confirmed by JLT
			if (createSBSPolicyRequest.getPayment().getPaymentMode().equalsIgnoreCase("CASH")) {

				boolean isAttachment = false;
				
				if (!(Utils.isEmpty((createSBSPolicyRequest.getPayment().getPolicyEmailAttachment()))) && createSBSPolicyRequest.getPayment().getPolicyEmailAttachment().equalsIgnoreCase("Y") && createSBSPolicyRequest.getPayment().getPolicyEmailAttachment() !=null) {
					isAttachment = true;
					triggerBatchJobForAcceptQuote(dataHolderVO,isAttachment,createSBSPolicyResponse);
				}
			}
			
			createSBSPolicyResponse.setPolicyInternalReference("" + twa_id);
			stopWatch.stop();
			LOGGER.info("Response time for createSBSPolicy.do IS : " + stopWatch.getTime() + " milisecond");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();

		}
		updateWebServiceAuditMapper.mapCreateSBSPolicyToAudit(webServiceAudit, createSBSPolicyResponse);
		LOGGER.debug(" response sent to SOAP UI " + createSBSPolicyResponse.toString());
		LOGGER.debug(" response sent to SOAP UI " + createSBSPolicyResponse);
		createSBSPolicyResponse.setPolicyInternalReference(""+twa_id);
		
		return createSBSPolicyResponse;
	}
	//added to send email in async job-m1043116
	private void triggerBatchJobForAcceptQuote(DataHolderVO<List<BaseVO>> dataHolderVO,boolean isAttachment,CreateSBSPolicyResponse createSBSPolicyResponse) {
		SBSBatchJobInvoker invoker = (SBSBatchJobInvoker) Utils.getBean("jobInvoker");
		List inputData = dataHolderVO.getData();
		PolicyVO policyVO = (PolicyVO) inputData.get(0);
		BatchInput input = new BatchInput.Builder(policyVO.getQuoteNo(), policyVO.getEndtId())
				.policyId(policyVO.getPolicyNo()).policyLinkingId(policyVO.getPolLinkingId()).isAttachment(isAttachment).createSBSPolicyResponse(createSBSPolicyResponse).dataHolderVO(dataHolderVO).build();
				
		invoker.converQuoteToPolicy(input);
	}


	@RequestMapping(value = "**/policy/{policy_id}", method = RequestMethod.GET)
	public @ResponseBody RetrievePolicyByPolicyNoResponse retrieveSBSPolicy(@PathVariable String policy_id,
			@ModelAttribute("policyVO") PolicyVO policyVO, BindingResult bindingResult, HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		RetrievePolicyByPolicyNoResponse retrievePolicyByPolicyNoResponse = new RetrievePolicyByPolicyNoResponse();
		WebServiceAudit webServiceAudit = new WebServiceAudit();
		WebServiceAuditMapper webServiceAuditMapper = new WebServiceAuditMapper();
		UpdateWebServiceAuditMapper updateWebServiceAuditMapper = new UpdateWebServiceAuditMapper();
		SBSCreatePolicyCommonRH sbsCreatePolicyCommonRH = new SBSCreatePolicyCommonRH();
		List<SBSWSValidators> sbswsValidatorsList = new ArrayList<SBSWSValidators>();
		try {
			StopWatch stopWatch = new StopWatch();
			stopWatch.start();
			LOGGER.info("Enter SBSPolicyController:retrieveSBSPolicy method.");
			String policyNo = policy_id;
			Short policyYear = new Short("0");
			
			if(!Utils.isEmpty(request.getParameter("policyYear"))) {
				policyYear = Short.parseShort(request.getParameter("policyYear"));
			}else {
				SBSWSValidators SBSbusinessValidators = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_POLICYNOPOLICYYEAR, "SBSWS_ERR_001",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
				sbswsValidatorsList.add(SBSbusinessValidators);
			}
			
			// Step 0
			// Adding the request to the Audit Table.
			HeaderInfo headerInfo = new HeaderInfo();
			headerInfo.setHeaderInfo(WSAppUtils.setHeaderInfo(request));
			
			sbsCreatePolicyCommonRH.fectchDefaultValues(policyVO, request);
			sbsCreatePolicyCommonRH.fectchUserId(policyVO,request);
			
			webServiceAudit = webServiceAuditMapper.mapGetSBSPolicyToAudit(policy_id,policyYear, retrievePolicyByPolicyNoResponse,
					"GetSBSPolicy", headerInfo,policyVO);
			Long twa_id = webServiceAudit.getTwa_id().longValue();
			// 1. call validator and returns a list of validator message and
			// status.
			LOGGER.info("Calling SBS get Policy Validator");
			RetrievePolicyByPolicyNoValidator retrievePolicyByPolicyNoValidator = new RetrievePolicyByPolicyNoValidator();

			sbswsValidatorsList = retrievePolicyByPolicyNoValidator.validate(policyNo, policyYear, sbswsValidatorsList,
					policyVO);
			if (sbswsValidatorsList.size() != 0) {
				List<SBSWSValidators> sbswsValidatorsList2 = new ArrayList<>();
				for (SBSWSValidators validators : sbswsValidatorsList) {
					if (validators.getCategory().contains(com.Constant.CONST_ERROR2)) {
						SBSWSValidators sbswsValidators = new SBSWSValidators();
						sbswsValidators.setCategory(validators.getCategory());
						sbswsValidators.setCode(validators.getCode());
						sbswsValidators.setField(validators.getField());
						sbswsValidators.setMessage(validators.getMessage());
						sbswsValidators.setType(validators.getType());
						sbswsValidatorsList2.add(sbswsValidators);
					}
				}
				if (sbswsValidatorsList2.size() != 0) {
					retrievePolicyByPolicyNoResponse.setSbswsValidators(sbswsValidatorsList2);
					retrievePolicyByPolicyNoResponse.setInternalReferenceNumber(twa_id.toString());
					updateWebServiceAuditMapper.mapGetSBSPolicyToAudit(webServiceAudit,
							retrievePolicyByPolicyNoResponse);
					return retrievePolicyByPolicyNoResponse;
				}
			}

			/*
			 * Call the handler with input which calls PolicyVO to response
			 * mapper by itself
			 */
			RetrievePolicyByPolicyNo retrievePolicyByPolicyNo = new RetrievePolicyByPolicyNo();
			retrievePolicyByPolicyNoResponse = retrievePolicyByPolicyNo.retrievePolicyByPolicyNo(policyVO);

			retrievePolicyByPolicyNoResponse.setInternalReferenceNumber("" + twa_id);
			stopWatch.stop();
			LOGGER.info("Response time for retrieveSBSPolicy.do IS : " + stopWatch.getTime() + " milisecond");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();

		}
		updateWebServiceAuditMapper.mapGetSBSPolicyToAudit(webServiceAudit, retrievePolicyByPolicyNoResponse);
		return retrievePolicyByPolicyNoResponse;
	}
}
