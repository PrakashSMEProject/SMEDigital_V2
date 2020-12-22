package com.rsaame.pas.b2c.controllers;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.http.HttpStatus;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.kaizen.framework.model.RSAUser;
import com.rsaame.kaizen.framework.model.ServiceContext;
import com.rsaame.pas.b2b.ws.batch.handler.SBSBatchJobInvoker;
import com.rsaame.pas.b2b.ws.batch.input.BatchInput;
import com.rsaame.pas.b2b.ws.constant.GetQuoteResponse;
import com.rsaame.pas.b2b.ws.dao.vo.EplatformWsStaging;
import com.rsaame.pas.b2b.ws.exception.SBSCommonExceptions;
import com.rsaame.pas.b2b.ws.exception.SBSWSValidationException;
import com.rsaame.pas.b2b.ws.handler.DocumentHandler;
import com.rsaame.pas.b2b.ws.handler.SBSCreateQuoteCommonRH;
import com.rsaame.pas.b2b.ws.handler.StagingTableDBHandler;
import com.rsaame.pas.b2b.ws.mapper.SBSCreateQuoteRequestMapper;
import com.rsaame.pas.b2b.ws.mapper.SBSCreateQuoteResponseMapper;
import com.rsaame.pas.b2b.ws.util.WSAppUtils;
import com.rsaame.pas.b2b.ws.util.WSBusinessValidatorUtils;
import com.rsaame.pas.b2b.ws.util.WSBusinessValidatorUtils.SBSErrorCodes;
import com.rsaame.pas.b2b.ws.util.WSDAOUtils;
import com.rsaame.pas.b2b.ws.validators.CommonGetQuoteValidators;
import com.rsaame.pas.b2b.ws.validators.CommonValidators;
import com.rsaame.pas.b2b.ws.validators.CommonValidatorsForUpdate;
import com.rsaame.pas.b2b.ws.validators.SBSWSValidators;
import com.rsaame.pas.b2b.ws.vo.CreateSBSQuoteRequest;
import com.rsaame.pas.b2b.ws.vo.CreateSBSQuoteResponse;
import com.rsaame.pas.b2b.ws.vo.Document;
import com.rsaame.pas.b2b.ws.vo.GetDocumentListResponse;
import com.rsaame.pas.b2b.ws.vo.GetDocumentResponse;
import com.rsaame.pas.b2b.ws.vo.RetrieveSBSQuoteResponse;
import com.rsaame.pas.b2b.ws.vo.UploadDocumentRequest;
import com.rsaame.pas.b2b.ws.vo.UploadDocumentResponse;
import com.rsaame.pas.b2c.cmn.base.BaseController;
import com.rsaame.pas.b2c.cmn.constants.AppConstants;
import com.rsaame.pas.b2c.ws.utilities.WebServiceAudit.mapper.UpdateWebServiceAuditMapper;
import com.rsaame.pas.b2c.ws.utilities.WebServiceAudit.mapper.WebServiceAuditMapper;
import com.rsaame.pas.b2c.ws.utilities.WebServiceAudit.vo.HeaderInfo;
import com.rsaame.pas.b2c.ws.utilities.WebServiceAudit.vo.WebServiceAudit;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.dao.model.TTrnPolicyQuo;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.bus.GeneralInfoVO;
import com.rsaame.pas.vo.bus.InsuredVO;
import com.rsaame.pas.vo.bus.PolicyVO;

@Controller
@RequestMapping("/rsaservices")
public class SBSQuotationController extends BaseController {

	private final static Logger LOGGER = Logger.getLogger(SBSQuotationController.class);
	private static final String SESSION_USER = "USER";
	public static boolean ischeckUpdate;
	
	@RequestMapping(value = "**/quotes", method = RequestMethod.POST)
	public @ResponseBody CreateSBSQuoteResponse createSBSQuote(@RequestBody CreateSBSQuoteRequest createSBSQuoteRequest,
			@ModelAttribute("policyVO") PolicyVO policyVO, BindingResult bindingResult, HttpServletRequest request,
			HttpSession session, HttpServletResponse response) throws Exception {
		CreateSBSQuoteResponse createSBSQuoteResponse = new CreateSBSQuoteResponse();
		WebServiceAuditMapper webServiceAuditMapper = new WebServiceAuditMapper();
		UpdateWebServiceAuditMapper updateWebServiceAuditMapper = new UpdateWebServiceAuditMapper();
		WebServiceAudit webServiceAudit = new WebServiceAudit();
		SBSCreateQuoteCommonRH sbsCreateQuoteCommonRH = new SBSCreateQuoteCommonRH();
		Long trnRefId=0L;
		try {
			StopWatch stopWatch = new StopWatch();
			stopWatch.start();
			ischeckUpdate = true;
			LOGGER.info("Create SBS Quote Request recieved");
			HeaderInfo headerInfo = new HeaderInfo();
			headerInfo.setHeaderInfo(WSAppUtils.setHeaderInfo(request));
			
			//Fetching the default values and user id from authentication
			sbsCreateQuoteCommonRH.fectchDefaultValues(policyVO, request);
			sbsCreateQuoteCommonRH.fectchUserId(policyVO,request);
			
			// Step 0
			// Adding the request to the Audit Table.
			webServiceAudit = webServiceAuditMapper.mapcreateSBSQuoteToAudit(policyVO, createSBSQuoteRequest,
					createSBSQuoteResponse, headerInfo);
			Long twa_id = webServiceAudit.getTwa_id().longValue();
			trnRefId=twa_id;
			// 1. call validator and returns a list of validator message and
			// status.
			LOGGER.info("Calling SBS Quote Validator");
			CommonValidators commonValidators = new CommonValidators();
			List<SBSWSValidators> sbswsValidatorsList = new ArrayList<SBSWSValidators>();
			sbswsValidatorsList = commonValidators.validate(createSBSQuoteRequest, sbswsValidatorsList,policyVO);
			if (sbswsValidatorsList.size() != 0) {
				List<SBSWSValidators> sbswsErrors = new ArrayList<>();
				for (SBSWSValidators validators : sbswsValidatorsList) {
					if (validators.getCategory().contains(com.Constant.CONST_ERROR2)) {
						SBSWSValidators sbswsValidators = new SBSWSValidators();
						sbswsValidators.setCategory(validators.getCategory());
						sbswsValidators.setCode(validators.getCode());
						sbswsValidators.setField(validators.getField());
						sbswsValidators.setMessage(validators.getMessage());
						sbswsValidators.setType(validators.getType());
						LOGGER.error(
								"Error in Field :" + validators.getField() + " Message :" + validators.getMessage());
						sbswsErrors.add(sbswsValidators);
					}
				}
				if (sbswsErrors.size() != 0) {
					createSBSQuoteResponse.setSbswsValidators(sbswsErrors);
					createSBSQuoteResponse.setQuoteInternalReference("" + twa_id);
					updateWebServiceAuditMapper.mapcreateSBSQuoteToAudit(webServiceAudit, createSBSQuoteResponse);
					return createSBSQuoteResponse;
				}
			}

			// 2. call request mapper and which returns policyVO
			policyVO.setCreatedBy(policyVO.getLoggedInUser().getUserId()); //added to set created by to JLT user ID of 994
			SBSCreateQuoteRequestMapper sbsCreateRequestMapper = new SBSCreateQuoteRequestMapper();
			sbsCreateRequestMapper.mapRequestToPolicyVO(createSBSQuoteRequest, policyVO, request);
			policyVO.setIsRenewal(Boolean.FALSE);
			UserProfile userProfile = WSAppUtils.getWSUserProfileVo(policyVO.getLoggedInUser().getUserId());
			request.getSession(false).setAttribute(AppConstants.SESSION_USER_PROFILE_VO, userProfile);

			session = request.getSession(false);
			session.setAttribute(SESSION_USER, userProfile);
			RSAUser user = (RSAUser) userProfile.getRsaUser();

			ServiceContext.setUser(user);
			if (ServiceContext.getMessage() != null) {
				ServiceContext.setMessage(null);
			}

			// 3. call request handler with policyVO and returns updated
			// policyVO
			// response
			sbsCreateQuoteCommonRH.executeCommonHandler(policyVO);
			if (sbswsValidatorsList.size() != 0) {
				List<SBSWSValidators> sbswsWarnings = new ArrayList<>();
				for (SBSWSValidators validators : sbswsValidatorsList) {
					if (validators.getCategory().contains("WARNING")) {
						SBSWSValidators sbswsValidators = new SBSWSValidators();
						sbswsValidators.setCategory(validators.getCategory());
						sbswsValidators.setCode(validators.getCode());
						sbswsValidators.setField(validators.getField());
						sbswsValidators.setMessage(validators.getMessage());
						sbswsValidators.setType(validators.getType());
						sbswsWarnings.add(sbswsValidators);
					}
				}
				if (sbswsWarnings.size() != 0) {
					createSBSQuoteResponse.setSbswsValidators(sbswsWarnings);
				}
			}
			// 4. call response mapper with policyVO and returns
			// CreateSBSQuoteResponse
			// CreateSBSQuoteResponse createSBSQuoteResponse = new
			// CreateSBSQuoteResponse();

			SBSCreateQuoteResponseMapper sbsCreateQuoteResponseMapper = new SBSCreateQuoteResponseMapper();
			sbsCreateQuoteResponseMapper.mapRequestToVO(createSBSQuoteRequest, policyVO, createSBSQuoteResponse,
					request, twa_id);
			sbsCreateQuoteResponseMapper.addReferralsToResponse(policyVO, createSBSQuoteResponse);

			// 5. DB Handler to save data to Staging Table
			sbsCreateQuoteCommonRH.executeWSDBHandler(policyVO, createSBSQuoteRequest, createSBSQuoteResponse , twa_id);

			// 6.Start the SBS batch job asynchronously.
			triggerBatchJob(policyVO);

			stopWatch.stop();
			LOGGER.info("Response time for createSBSQuote.do IS : " + stopWatch.getTime() + " milisecond");
			// return createSBSQuoteResponse;
		} catch (Exception e) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			System.out.println(e.getMessage());
			e.printStackTrace();
			//Commented as per JLT,changed the response
			/*SBSCommonExceptions commonExceptions = new SBSCommonExceptions();
			SBSWSValidationException sbsValidationexception = commonExceptions.exceptionMapping("Server","SBSWS_EXPN_500");*/
			createSBSQuoteResponse=WSBusinessValidatorUtils.getExceptionMessage(createSBSQuoteResponse,trnRefId);
			
			e.printStackTrace();
		}
		//createSBSQuoteResponse.setQuoteInternalReference("" + twa_id);
		updateWebServiceAuditMapper.mapcreateSBSQuoteToAudit(webServiceAudit, createSBSQuoteResponse);
		return createSBSQuoteResponse ;

	}

	private void triggerBatchJob(PolicyVO policyVO) {
		//BaseController.setLocation();//test code need to be reverted quickly

		SBSBatchJobInvoker invoker = (SBSBatchJobInvoker) Utils.getBean("jobInvoker");
		BatchInput input = new BatchInput.Builder(policyVO.getQuoteNo(), policyVO.getEndtId())
				.policyId(policyVO.getPolicyNo()).policyLinkingId(policyVO.getPolLinkingId()).build();
		invoker.createQuote(input);
	}

	@RequestMapping(value = "**/quotes", method = RequestMethod.GET)
	public @ResponseBody GetQuoteResponse getSBSQuote(@ModelAttribute("policyVO") PolicyVO policyVO, 
			BindingResult bindingResult, HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		// Recieve quote_id from web service to quotationNo
		String quotationNo = "";
		String policyNo = "", expiryPolicyYear ="";
		Long trnRefId=0L;
		StagingTableDBHandler stagingTableDBHandler = new StagingTableDBHandler();
		CreateSBSQuoteResponse createSBSQuoteResponse = new CreateSBSQuoteResponse();
		
		WebServiceAuditMapper webServiceAuditMapper = new WebServiceAuditMapper();
		UpdateWebServiceAuditMapper updateWebServiceAuditMapper = new UpdateWebServiceAuditMapper();
		WebServiceAudit webServiceAudit = new WebServiceAudit();
		CommonGetQuoteValidators commonGetQuoteValidators = new CommonGetQuoteValidators();
		List<SBSWSValidators> sbswsValidatorsList = new ArrayList<SBSWSValidators>();
		SBSCreateQuoteCommonRH sbsCreateQuoteCommonRH = new SBSCreateQuoteCommonRH();
		
		// Added for JLT Renewal #11424
		if(!Utils.isEmpty(request.getParameter("quote_id"))) {
			quotationNo=request.getParameter("quote_id");
			policyVO.setIsRenewal(false);
		}else if(!Utils.isEmpty(request.getParameter("policyNo")) || !Utils.isEmpty(request.getParameter("expiryPolicyYear"))) {
			policyNo=request.getParameter("policyNo");
			expiryPolicyYear = request.getParameter("expiryPolicyYear");
			policyVO.setIsRenewal(true);
		}else {
			SBSWSValidators SBSbusinessValidators = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_QUOTEPOLICYNOYEAR, "SBSWS_ERR_001",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
			sbswsValidatorsList.add(SBSbusinessValidators);
		}
		try {
			StopWatch stopWatch = new StopWatch();
			stopWatch.start();
			LOGGER.info("Get SBS Quote Request recieved");
			HeaderInfo headerInfo = new HeaderInfo();
			headerInfo.setHeaderInfo(WSAppUtils.setHeaderInfo(request));
			
			//Fetching the default values and user id from authentication
			sbsCreateQuoteCommonRH.fectchDefaultValues(policyVO, request);
			sbsCreateQuoteCommonRH.fectchUserId(policyVO,request);
			
			// Step 0
			// Adding the request to the Audit Table.
			webServiceAudit = webServiceAuditMapper.mapGetSBSQuoteToAudit(policyVO, quotationNo,policyNo,expiryPolicyYear, "RetrieveSBSQuote",
					headerInfo);
			Long twa_id = webServiceAudit.getTwa_id().longValue();
			trnRefId=twa_id;
			// 1. call validator and returns a list of validator message and
			// status.
			LOGGER.info("Calling SBS get Quote Validator");

			sbswsValidatorsList = commonGetQuoteValidators.validate(policyVO,quotationNo, sbswsValidatorsList,policyNo,expiryPolicyYear);
			if (sbswsValidatorsList.size() != 0) {
				List<SBSWSValidators> sbswsErrors = new ArrayList<>();
				for (SBSWSValidators validators : sbswsValidatorsList) {
					if (validators.getCategory().contains(com.Constant.CONST_ERROR2)) {
						SBSWSValidators sbswsValidators = new SBSWSValidators();
						sbswsValidators.setCategory(validators.getCategory());
						sbswsValidators.setCode(validators.getCode());
						sbswsValidators.setField(validators.getField());
						sbswsValidators.setMessage(validators.getMessage());
						sbswsValidators.setType(validators.getType());
						sbswsErrors.add(sbswsValidators);
					}
				}
				if (sbswsErrors.size() != 0) {
					createSBSQuoteResponse.setSbswsValidators(sbswsErrors);
					createSBSQuoteResponse.setQuoteInternalReference("" + twa_id);
					updateWebServiceAuditMapper.mapGetSBSQuoteToAudit(webServiceAudit, createSBSQuoteResponse);
					return createSBSQuoteResponse;
				}
			}
			
			if(!policyVO.getIsRenewal()) {
				// To retrieve quotation for new business
				return stagingTableDBHandler.getQuote(policyVO, createSBSQuoteResponse, twa_id, webServiceAudit);
			}else {
				TTrnPolicyQuo policy = null;
				policy = sbsCreateQuoteCommonRH.getRenewalQuoteByPolicyNo(policyVO);
				if (policy.getPolDocumentCode() == Short.parseShort(Utils.getSingleValueAppConfig("REN_QUO_DOC_CODE"))) {
					return stagingTableDBHandler.getRenewalQuote(policyVO, createSBSQuoteResponse, twa_id, webServiceAudit);
				} else {

					return stagingTableDBHandler.getQuote(policyVO, createSBSQuoteResponse, twa_id, webServiceAudit);
				}
			}
			
			
			/* End */

			// 3. call request handler with quotationNo and returns updated
			// policyVO
			// response
			// SBSCreateQuoteCommonRH sbsCreateQuoteCommonRH = new
			// SBSCreateQuoteCommonRH();
			// sbsCreateQuoteCommonRH.executeCommonHandler(policyVO);
			// createSBSQuoteResponse =
			// sbsCreateQuoteCommonRH.excuteSBSgetQuoteHandler(quotationNo);

		} catch (Exception e) {
			createSBSQuoteResponse=WSBusinessValidatorUtils.getExceptionRetriveMessage(createSBSQuoteResponse,trnRefId);
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			System.out.println(e.getMessage());
			e.printStackTrace();

		}
		return createSBSQuoteResponse;
	}

	@RequestMapping(value = "**/quotes/{quote_id}", method = RequestMethod.PUT)
	public @ResponseBody CreateSBSQuoteResponse updateSBSQuote(@PathVariable String quote_id,
			@RequestBody CreateSBSQuoteRequest createSBSQuoteRequest, @ModelAttribute("policyVO") PolicyVO policyVO,
			BindingResult bindingResult, HttpServletRequest request, HttpSession session,
			HttpServletResponse response) {
		// Recieve quote_id from web service to quotationNo
		String quotationNo = quote_id;
		Long trnRefId=0L;
		CreateSBSQuoteResponse createSBSQuoteResponse = new CreateSBSQuoteResponse();
		WebServiceAuditMapper webServiceAuditMapper = new WebServiceAuditMapper();
		UpdateWebServiceAuditMapper updateWebServiceAuditMapper = new UpdateWebServiceAuditMapper();
		WebServiceAudit webServiceAudit = new WebServiceAudit();
		SBSCreateQuoteCommonRH sbsCreateQuoteCommonRH = new SBSCreateQuoteCommonRH();
		try {
			ischeckUpdate = false;
			StopWatch stopWatch = new StopWatch();
			stopWatch.start();
			LOGGER.info("Update SBS Quote Request recieved ");
			HeaderInfo headerInfo = new HeaderInfo();
			headerInfo.setHeaderInfo(WSAppUtils.setHeaderInfo(request));
			
			//Fetching the default values and user id from authentication
			sbsCreateQuoteCommonRH.fectchDefaultValues(policyVO, request);
			sbsCreateQuoteCommonRH.fectchUserId(policyVO,request);
			
			// Step 0
			//Checking in Auditing table for the any in progress request for the same quote
			Boolean checkIfPreviosBatchIsRunning = WSDAOUtils.checkIfPreviosBatchIsRunning(Long.valueOf(quotationNo));
			if(checkIfPreviosBatchIsRunning) {
				List<SBSWSValidators> sbswsValidatorsList = new ArrayList<SBSWSValidators>();
				SBSWSValidators sbsWSValidators = new SBSWSValidators();
				sbsWSValidators = WSBusinessValidatorUtils.businessErrorMapping("Previous Batch Status",
						"SBSWS_ERR_098", SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
				sbswsValidatorsList.add(sbsWSValidators);
				createSBSQuoteResponse.setSbswsValidators(sbswsValidatorsList);
				//Long twa_id = webServiceAudit.getTwa_id().longValue();
				//createSBSQuoteResponse.setQuoteInternalReference("" + twa_id);
				System.out.println(webServiceAudit.toString());
				webServiceAudit = webServiceAuditMapper.mapUpdateSBSQuoteToAudit(policyVO, createSBSQuoteRequest,
						createSBSQuoteResponse, headerInfo , quotationNo);
				Long twa_id = webServiceAudit.getTwa_id().longValue();
				createSBSQuoteResponse.setQuoteInternalReference("" + twa_id);
				return createSBSQuoteResponse;
			}
			//Step 1 
			// Adding the request to the Audit Table.
			webServiceAudit = webServiceAuditMapper.mapUpdateSBSQuoteToAudit(policyVO, createSBSQuoteRequest,
					createSBSQuoteResponse, headerInfo , quotationNo);
			Long twa_id = webServiceAudit.getTwa_id().longValue();
			trnRefId=twa_id;
			// 1. call validator and returns a list of validator message and
			// status.
			LOGGER.info("Calling SBS Quote Validator");
			policyVO.setQuoteNo(Long.valueOf(quotationNo));
			policyVO.setAppFlow(Flow.EDIT_QUO);
			CommonValidators commonValidators = new CommonValidators();
			List<SBSWSValidators> sbswsValidatorsList = new ArrayList<SBSWSValidators>();
			sbswsValidatorsList = commonValidators.validate(createSBSQuoteRequest, sbswsValidatorsList,policyVO);
			
			// Will validate the request and mandatory fields
			if (sbswsValidatorsList.size() != 0) {
				List<SBSWSValidators> sbswsErrors = new ArrayList<>();
				for (SBSWSValidators validators : sbswsValidatorsList) {
					if (validators.getCategory().contains(com.Constant.CONST_ERROR2)) {
						SBSWSValidators sbswsValidators = new SBSWSValidators();
						sbswsValidators.setCategory(validators.getCategory());
						sbswsValidators.setCode(validators.getCode());
						sbswsValidators.setField(validators.getField());
						sbswsValidators.setMessage(validators.getMessage());
						sbswsValidators.setType(validators.getType());
						sbswsErrors.add(sbswsValidators);
					}
				}
				if (sbswsErrors.size() != 0) {
					createSBSQuoteResponse.setSbswsValidators(sbswsErrors);
					createSBSQuoteResponse.setQuoteInternalReference("" + twa_id);
					updateWebServiceAuditMapper.mapUpdateSBSQuoteToAudit(webServiceAudit, createSBSQuoteResponse);
					return createSBSQuoteResponse;
				}
			}
			// 2. call request mapper and which returns policyVO
			
			policyVO = sbsCreateQuoteCommonRH.getQuoteStatus(policyVO);
			
			// Added for JLT Renewal Scope #11424
			/**
			 * New validation has been added for renewal quote
			 * The renewal quote effective date should not be before policy expiry date
			 */
			if (!Utils.isEmpty(policyVO.getAuthInfoVO())) {
				if (policyVO.getAuthInfoVO().getTxnType() == Integer
						.parseInt(Utils.getSingleValueAppConfig("REN_QUO_DOC_CODE"))) {
					sbswsValidatorsList = commonValidators.validateRenewalQuoteEffectiveDate(createSBSQuoteRequest,
							sbswsValidatorsList, policyVO);

					if (sbswsValidatorsList.size() != 0) {
						List<SBSWSValidators> sbswsErrors = new ArrayList<>();
						for (SBSWSValidators validators : sbswsValidatorsList) {
							if (validators.getCategory().contains(com.Constant.CONST_ERROR2)) {
								SBSWSValidators sbswsValidators = new SBSWSValidators();
								sbswsValidators.setCategory(validators.getCategory());
								sbswsValidators.setCode(validators.getCode());
								sbswsValidators.setField(validators.getField());
								sbswsValidators.setMessage(validators.getMessage());
								sbswsValidators.setType(validators.getType());
								sbswsErrors.add(sbswsValidators);
							}
						}
						if (sbswsErrors.size() != 0) {
							createSBSQuoteResponse.setSbswsValidators(sbswsErrors);
							createSBSQuoteResponse.setQuoteInternalReference("" + twa_id);
							updateWebServiceAuditMapper.mapUpdateSBSQuoteToAudit(webServiceAudit,
									createSBSQuoteResponse);
							return createSBSQuoteResponse;
						}
					}
				}
			}
				
			if (!Utils.isEmpty(policyVO) && Utils.isEmpty(policyVO.getStatus())) {
				SBSWSValidators SBSbusinessValidators = WSBusinessValidatorUtils.businessErrorMapping("Quote Number",
						"SBSWS_ERR_081", SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
				sbswsValidatorsList.add(SBSbusinessValidators);
			}else { 
				
				// Removed the JLT Prepared By Condition to accept update quote request for JLT Renewal Quote
				//adding new code here to provision edit quote for JLT API even though the quote is the referred status..
				
				if((!Utils.isEmpty(policyVO)) && ((policyVO.getStatus() == Integer.parseInt(Utils.getSingleValueAppConfig("QUOTE_REFERRED"))))) {
					
					WSDAOUtils.deletePreviuosReferralsForAPI(policyVO);
					policyVO.setStatus(1); //added so that referred quotes can be provisioned for edit.
				}

				if (!Utils.isEmpty(policyVO) && ((policyVO.getStatus() == Integer
						.parseInt(Utils.getSingleValueAppConfig("QUOTE_ACTIVE"))
						|| (policyVO.getStatus() == Integer.parseInt(Utils.getSingleValueAppConfig("QUOTE_PENDING")))
						|| policyVO.getStatus() == Integer.parseInt(Utils.getSingleValueAppConfig("QUOTE_ACCEPT"))
						)))
				{

					SBSCreateQuoteRequestMapper sbsCreateRequestMapper = new SBSCreateQuoteRequestMapper();
					sbsCreateRequestMapper.mapRequestToPolicyVO(createSBSQuoteRequest, policyVO, request);
					List<TTrnPolicyQuo> policy = sbsCreateQuoteCommonRH.getPolicyRecord(policyVO);
					List<EplatformWsStaging> stagings = sbsCreateQuoteCommonRH
							.getPolicyRecordFromStaging(policyVO.getQuoteNo());
					//CTS JLT API Change - 23.07.2020 ADD policyYear and policyNo for create, update and retrieve Quote - Starts
					if(policyVO.getAuthInfoVO().getTxnType() == Integer
							.parseInt(Utils.getSingleValueAppConfig("REN_QUO_DOC_CODE"))){
						policyVO.setPolicyNo(policy.get(0).getPolPolicyNo());
						policyVO.setIsRenewal(Boolean.TRUE);
					}else{
						policyVO.setIsRenewal(Boolean.FALSE);
					}
					//CTS JLT API Change - 23.07.2020 ADD policyYear and policyNo for create, update and retrieve Quote - Ends
					if (stagings.size() > 0) {
						if (policy.get(0).getId().getPolEndtId() >= stagings.get(0).getId().getPolEndtId()) {
							// Eplatform trans table
							policyVO.setPolLinkingId(policy.get(0).getPolLinkingId());
							policyVO.setEndtId(policy.get(0).getId().getPolEndtId());
							policyVO.setEndtNo(policy.get(0).getPolEndtNo());
							policyVO.getGeneralInfo().getInsured().setInsuredId(policy.get(0).getPolInsuredCode());
							policyVO.getGeneralInfo().getInsured().setInsuredCode(policy.get(0).getPolInsuredCode());
							if (policy.get(0).getPolStatus() == 6) {
								if (!Utils.isEmpty(policy.get(0).getId().getPolEndtId())) {
									policyVO.setNewEndtId(policy.get(0).getId().getPolEndtId());
								}
								if (policy.get(0).getPolValidityStartDate() != null) {
									policyVO.setNewValidityStartDate(policy.get(0).getPolValidityStartDate());
								}
							} else if(policy.get(0).getPolStatus() == Integer
									.parseInt(Utils.getSingleValueAppConfig("QUOTE_ACCEPT"))) {
								WSDAOUtils.updateStatus(policyVO);
								
								policyVO.setStatus(1);
							}
						} else {
							// staging table

							policyVO.setPolLinkingId(stagings.get(0).getPolLinkingId());
							policyVO.setEndtId(stagings.get(0).getId().getPolEndtId());
							policyVO.setEndtNo(stagings.get(0).getPolEndtNo());
							policyVO.getGeneralInfo().getInsured().setInsuredId(stagings.get(0).getInsInsuredCode());
							policyVO.getGeneralInfo().getInsured().setInsuredCode(stagings.get(0).getInsInsuredCode());
							if (stagings.get(0).getPolStatus() == 6) {
								if (!Utils.isEmpty(stagings.get(0).getId().getPolEndtId())) {
									policyVO.setNewEndtId(stagings.get(0).getId().getPolEndtId());
								}
								if (policy.get(0).getPolValidityStartDate() != null) {
									policyVO.setNewValidityStartDate(policy.get(0).getPolValidityStartDate());
								}
							}
						}

					} else {
						policyVO.setPolLinkingId(policy.get(0).getPolLinkingId());
						policyVO.setEndtId(policy.get(0).getId().getPolEndtId());
						policyVO.setEndtNo(policy.get(0).getPolEndtNo());
						policyVO.getGeneralInfo().getInsured().setInsuredId(policy.get(0).getPolInsuredCode());
						policyVO.getGeneralInfo().getInsured().setInsuredCode(policy.get(0).getPolInsuredCode());
						if (policy.get(0).getPolStatus() == 6) {
							if (!Utils.isEmpty(policy.get(0).getId().getPolEndtId())) {
								policyVO.setNewEndtId(policy.get(0).getId().getPolEndtId());
							}
							if (policy.get(0).getPolValidityStartDate() != null) {
								policyVO.setNewValidityStartDate(policy.get(0).getPolValidityStartDate());
							}
						}
					}
					policyVO.getAuthInfoVO().setCreatedOn((Timestamp) policy.get(0).getPolPreparedDt());
					policyVO = sbsCreateQuoteCommonRH.getBaseSecPolicyId(policyVO);
					policyVO.setIsQuote(true);
					policyVO.setStatus(Integer.parseInt(Utils.getSingleValueAppConfig("POLICY_PENDING")));

					UserProfile userProfile = WSAppUtils
							.getWSUserProfileVo(policyVO.getLoggedInUser().getUserId());
					request.getSession(false).setAttribute(AppConstants.SESSION_USER_PROFILE_VO, userProfile);

					session = request.getSession(false);
					session.setAttribute(SESSION_USER, userProfile);
					RSAUser user = (RSAUser) userProfile.getRsaUser();

					ServiceContext.setUser(user);
					if (ServiceContext.getMessage() != null) {
						ServiceContext.setMessage(null);
					}

					// 3. call request handler with policyVO and returns updated
					// policyVO
					// response
					sbsCreateQuoteCommonRH.executeCommonHandler(policyVO);

					if (sbswsValidatorsList.size() != 0) {
						List<SBSWSValidators> sbswsWarnings = new ArrayList<>();
						for (SBSWSValidators validators : sbswsValidatorsList) {
							if (validators.getCategory().contains("WARNING")) {
								SBSWSValidators sbswsValidators = new SBSWSValidators();
								sbswsValidators.setCategory(validators.getCategory());
								sbswsValidators.setCode(validators.getCode());
								sbswsValidators.setField(validators.getField());
								sbswsValidators.setMessage(validators.getMessage());
								sbswsValidators.setType(validators.getType());
								sbswsWarnings.add(sbswsValidators);
							}
						}
						if (sbswsWarnings.size() != 0) {
							createSBSQuoteResponse.setSbswsValidators(sbswsWarnings);
						}
					}
					// 4. call response mapper with policyVO and returns
					// CreateSBSQuoteResponse
					// CreateSBSQuoteResponse createSBSQuoteResponse = new
					// CreateSBSQuoteResponse();

					SBSCreateQuoteResponseMapper sbsCreateQuoteResponseMapper = new SBSCreateQuoteResponseMapper();
					sbsCreateQuoteResponseMapper.mapRequestToVO(createSBSQuoteRequest, policyVO, createSBSQuoteResponse,
							request, twa_id);
					sbsCreateQuoteResponseMapper.addReferralsToResponse(policyVO, createSBSQuoteResponse);

					// 5. DB Handler to save data to Staging Table
					sbsCreateQuoteCommonRH.executeWSDBHandler(policyVO, createSBSQuoteRequest, createSBSQuoteResponse , twa_id);
					triggerBatchJobForUpdate(policyVO);

				} else if (policyVO.getStatus() == Integer.parseInt(Utils.getSingleValueAppConfig("POLICY_REFERRED"))
						|| policyVO.getStatus() == Integer.parseInt(Utils.getSingleValueAppConfig("CONV_TO_POL"))
						|| policyVO.getStatus() == Integer.parseInt(Utils.getSingleValueAppConfig("QUOTE_REJECT"))
						|| policyVO.getStatus() == Integer.parseInt(Utils.getSingleValueAppConfig("QUOTE_DECLINED"))
						|| policyVO.getStatus() == Integer.parseInt(Utils.getSingleValueAppConfig("QUOTE_LAPSED_STATUS"))
						|| policyVO.getStatus() == Integer.parseInt(Utils.getSingleValueAppConfig("QUOTE_SOFT_STOP"))
						|| policyVO.getStatus() == Integer.parseInt(Utils.getSingleValueAppConfig("QUOTE_REFERRED"))
						|| policyVO.getStatus() == Integer.parseInt(Utils.getSingleValueAppConfig("QUOTE_EXPIRED"))
			//			|| policyVO.getStatus() == Integer.parseInt(Utils.getSingleValueAppConfig("QUOTE_ACCEPT"))
						) 
				{
					SBSWSValidators SBSbusinessValidators = WSBusinessValidatorUtils.businessErrorMapping("Quote Level",
							"SBSWS_ERR_082", SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
					sbswsValidatorsList.add(SBSbusinessValidators);

				}
			} 
			
			if (sbswsValidatorsList.size() != 0) {
				List<SBSWSValidators> sbswsErrors = new ArrayList<>();
				for (SBSWSValidators validators : sbswsValidatorsList) {
					if (validators.getCategory().contains(com.Constant.CONST_ERROR2)) {
						SBSWSValidators sbswsValidators = new SBSWSValidators();
						sbswsValidators.setCategory(validators.getCategory());
						sbswsValidators.setCode(validators.getCode());
						sbswsValidators.setField(validators.getField());
						sbswsValidators.setMessage(validators.getMessage());
						sbswsValidators.setType(validators.getType());
						sbswsErrors.add(sbswsValidators);
					}
				}
				if (sbswsErrors.size() != 0) {
					createSBSQuoteResponse.setSbswsValidators(sbswsErrors);
					createSBSQuoteResponse.setQuoteInternalReference("" + twa_id);
					updateWebServiceAuditMapper.mapUpdateSBSQuoteToAudit(webServiceAudit, createSBSQuoteResponse);
					return createSBSQuoteResponse;
				}
			}
			stopWatch.stop();
			LOGGER.info("Response time for updateSBSQuote.do IS : " + stopWatch.getTime() + " milisecond");
			// return createSBSQuoteResponse;
		} catch (Exception e) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			System.out.println(e.getMessage());
			e.printStackTrace();
			createSBSQuoteResponse=WSBusinessValidatorUtils.getExceptionMessage(createSBSQuoteResponse,trnRefId);
			//
				/*SBSCommonExceptions commonExceptions = new SBSCommonExceptions();
			SBSWSValidationException sbsValidationexception = commonExceptions.exceptionMapping("Server",
					"SBSWS_EXPN_500");
			createSBSQuoteResponse.setSbswsValidationException(sbsValidationexception);*/
			e.printStackTrace();
		}
		try {
			updateWebServiceAuditMapper.mapUpdateSBSQuoteToAudit(webServiceAudit, createSBSQuoteResponse);
		} catch (ParseException e) {
			createSBSQuoteResponse=WSBusinessValidatorUtils.getExceptionMessage(createSBSQuoteResponse,trnRefId);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return createSBSQuoteResponse;
	}

	private void triggerBatchJobForUpdate(PolicyVO policyVO) {
		
		BaseController.setLocation();//test code need to be reverted quickly
		SBSBatchJobInvoker invoker = (SBSBatchJobInvoker) Utils.getBean("jobInvoker");
		BatchInput input = new BatchInput.Builder(policyVO.getQuoteNo(), policyVO.getEndtId())
				.policyId(policyVO.getPolicyNo()).policyLinkingId(policyVO.getPolLinkingId()).build();
		invoker.updateQuote(input);
	}

	@RequestMapping(value = "**/{policy_id}/documents/list", method = RequestMethod.GET)
	public @ResponseBody GetDocumentListResponse getDocumentsList(@PathVariable String policy_id,
			@ModelAttribute("policyVO") PolicyVO policyVO, BindingResult bindingResult, HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {

		GetDocumentListResponse documentListResponse = new GetDocumentListResponse();
		WebServiceAuditMapper webServiceAuditMapper = new WebServiceAuditMapper();
		UpdateWebServiceAuditMapper updateWebServiceAuditMapper = new UpdateWebServiceAuditMapper();
		WebServiceAudit webServiceAudit = new WebServiceAudit();
		CommonValidators commonValidators = new CommonValidators();
		List<SBSWSValidators> sbsWSValidatorsList  =new ArrayList<SBSWSValidators>();
		SBSCreateQuoteCommonRH sbsCreateQuoteCommonRH = new SBSCreateQuoteCommonRH();
		
		Long twa_id = null;
		if (!Utils.isEmpty(policy_id) && !Utils.isEmpty(request.getParameter("policyYear"))) {
			policyVO.setPolicyNo(Long.parseLong(policy_id));
			policyVO.setPolicyYear(Short.parseShort(request.getParameter("policyYear")));
		} else {
			SBSWSValidators SBSbusinessValidators = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_POLICYNOPOLICYYEAR, "SBSWS_ERR_001",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
			sbsWSValidatorsList.add(SBSbusinessValidators);
		}
		LOGGER.debug("Entered GetDocumentList.do method with Policy Number::::" + policyVO.getPolicyNo());
		// Step 0
		// Adding the request to the Audit Table.
		HeaderInfo headerInfo = new HeaderInfo();
		headerInfo.setHeaderInfo(WSAppUtils.setHeaderInfo(request));
		
		//Fetching the default values and user id from authentication
		sbsCreateQuoteCommonRH.fectchDefaultValues(policyVO, request);
		sbsCreateQuoteCommonRH.fectchUserId(policyVO,request);
		
		try {
			webServiceAudit = webServiceAuditMapper.mapGetDocumentsListToAudit(policyVO, "GetDocumentList", headerInfo);
			twa_id = webServiceAudit.getTwa_id().longValue();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		/*if (Utils.isEmpty(policyVO.getLoggedInUser())) {

			UserProfile userProfile = WSAppUtils.getWSUserProfileVo("jltuser_idb");

			request.getSession().setAttribute(AppConstants.SESSION_USER_PROFILE_VO, userProfile);

			policyVO.setLoggedInUser(userProfile);
			policyVO.getLoggedInUser().setUserId(Utils.getSingleValueAppConfig(com.Constant.CONST_JLT_USER_ID));
		}*/


		sbsWSValidatorsList = commonValidators.validateGetDocumentList(policyVO, sbsWSValidatorsList);
		
		if (sbsWSValidatorsList.size() != 0) {
			List<SBSWSValidators> sbswsErrors = new ArrayList<>();
			for (SBSWSValidators validators : sbsWSValidatorsList) {
				if (validators.getCategory().contains(com.Constant.CONST_ERROR2)) {
					SBSWSValidators sbswsValidators = new SBSWSValidators();
					sbswsValidators.setCategory(validators.getCategory());
					sbswsValidators.setCode(validators.getCode());
					sbswsValidators.setField(validators.getField());
					sbswsValidators.setMessage(validators.getMessage());
					sbswsValidators.setType(validators.getType());
					sbswsErrors.add(sbswsValidators);
				}
			}
			if (sbswsErrors.size() != 0) {
				documentListResponse.setSbswsValidators(sbswsErrors);
				documentListResponse.setInternalReference("" + twa_id);
				try {
					updateWebServiceAuditMapper.mapGetDocumentsListToAudit(webServiceAudit, documentListResponse);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
					e.printStackTrace();
				}
				return documentListResponse;
			}
		}
		
		DocumentHandler documentHandler = new DocumentHandler();
		List<Document> documentList = documentHandler.getDocumentList(policyVO);
		
		for (Document documant : documentList) {
			LOGGER.debug("Document List:::: " + documant.getDocid() + "::::" + documant.getName());
		}
		documentListResponse.setDocument(documentList);
		try {
			updateWebServiceAuditMapper.mapGetDocumentsListToAudit(webServiceAudit, documentListResponse);
		} catch (ParseException e) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		documentListResponse.setInternalReference(""+twa_id);
		return documentListResponse;
	}

	@RequestMapping(value = "**/documents/{document_id}", method = RequestMethod.GET)
	public @ResponseBody GetDocumentResponse getDocument(@PathVariable String document_id, @ModelAttribute("policyVO") PolicyVO policyVO,
			BindingResult bindingResult, HttpServletRequest request, HttpSession session,
			HttpServletResponse response) {

		GetDocumentResponse documentResponse = new GetDocumentResponse();
		WebServiceAuditMapper webServiceAuditMapper = new WebServiceAuditMapper();
		UpdateWebServiceAuditMapper updateWebServiceAuditMapper = new UpdateWebServiceAuditMapper();
		WebServiceAudit webServiceAudit = new WebServiceAudit();
		SBSCreateQuoteCommonRH sbsCreateQuoteCommonRH = new SBSCreateQuoteCommonRH();
		String documentId = null;
		Long twa_id = null;
		List<SBSWSValidators> sbsWSValidatorsList  =new ArrayList<SBSWSValidators>();
		CommonValidators commonValidators = new CommonValidators();
		String [] fileNames= new String[1];
		String [] reportParameter=null;
		StringTokenizer st = null;
		
		
		Long policyNo = null;
		
		if (!Utils.isEmpty(document_id)) {
			documentId = document_id;
			st = new StringTokenizer(documentId, ":");
			reportParameter = new String[st.countTokens()];
			reportParameter = documentId.split(":");
			policyVO.setPolicyNo(Long.parseLong(reportParameter[0]));
		} else {
			SBSWSValidators SBSbusinessValidators = WSBusinessValidatorUtils.businessErrorMapping(com.Constant.CONST_POLICYNOPOLICYYEAR, "SBSWS_ERR_001",SBSErrorCodes.ERROR.name(), com.Constant.CONST_BUSINESS);
			sbsWSValidatorsList.add(SBSbusinessValidators);
		}
		LOGGER.debug("Entered in Get Document API with and Document Id :" + documentId);
		// Adding the request to the Audit Table.
		try {
			HeaderInfo headerInfo = new HeaderInfo();
			headerInfo.setHeaderInfo(WSAppUtils.setHeaderInfo(request));
			
			//Fetching the default values and user id from authentication
			sbsCreateQuoteCommonRH.fectchDefaultValues(policyVO, request);
			sbsCreateQuoteCommonRH.fectchUserId(policyVO,request);
			
			webServiceAudit = webServiceAuditMapper.mapGetDocumentsListToAudit(policyVO,"GetDocument",headerInfo);
			twa_id = webServiceAudit.getTwa_id().longValue();
		} catch (ParseException e1) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			e1.printStackTrace();
		}
		DocumentHandler documentHandler = new DocumentHandler();
		
		sbsWSValidatorsList = commonValidators.validateGetDocument(sbsWSValidatorsList, policyVO);
		
		if (sbsWSValidatorsList.size() != 0) {
			List<SBSWSValidators> sbswsErrors = new ArrayList<>();
			for (SBSWSValidators validators : sbsWSValidatorsList) {
				if (validators.getCategory().contains(com.Constant.CONST_ERROR2)) {
					SBSWSValidators sbswsValidators = new SBSWSValidators();
					sbswsValidators.setCategory(validators.getCategory());
					sbswsValidators.setCode(validators.getCode());
					sbswsValidators.setField(validators.getField());
					sbswsValidators.setMessage(validators.getMessage());
					sbswsValidators.setType(validators.getType());
					sbswsErrors.add(sbswsValidators);
				}
			}
			if (sbswsErrors.size() != 0) {
				documentResponse.setSbswsValidators(sbswsErrors);
				documentResponse.setInternalReference("" + twa_id);
				try {
					updateWebServiceAuditMapper.mapGetDocumentsListToAudit(webServiceAudit, documentResponse);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
					e.printStackTrace();
				}
				return documentResponse;
			}
		}
		
		try {
			documentResponse.setDocumentDownload(documentHandler.getDocument(documentId));
		} catch (IOException e) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			e.printStackTrace();
			try {
				updateWebServiceAuditMapper.mapGetDocumentsListToAudit(webServiceAudit, documentResponse);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			documentResponse.setInternalReference(""+twa_id);
			return documentResponse;
		}
		try {
			updateWebServiceAuditMapper.mapGetDocumentsListToAudit(webServiceAudit, documentResponse);
		} catch (ParseException e) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		documentResponse.setInternalReference(""+twa_id);
		return documentResponse;
	}

	@RequestMapping(value = "**/tradelicense/{quote_id}", method = RequestMethod.POST)
	public @ResponseBody UploadDocumentResponse submitTradeLicenceDocument(@PathVariable String quote_id,
			@RequestBody UploadDocumentRequest uploadDocumentRequest, @ModelAttribute("policyVO") PolicyVO policyVO,
			BindingResult bindingResult, HttpServletRequest request, HttpSession session,
			HibernateTemplate hibernateTemplate, HttpServletResponse response) throws IOException {
		
		WebServiceAuditMapper webServiceAuditMapper = new WebServiceAuditMapper();
		UpdateWebServiceAuditMapper updateWebServiceAuditMapper = new UpdateWebServiceAuditMapper();
		WebServiceAudit webServiceAudit = new WebServiceAudit();
		SBSCreateQuoteCommonRH sbsCreateQuoteCommonRH = new SBSCreateQuoteCommonRH();
		CommonValidators commonValidators = new CommonValidators();
		List<SBSWSValidators> sbsWSValidatorsList  =new ArrayList<SBSWSValidators>();
		UploadDocumentResponse uploadDocumentResponse = new UploadDocumentResponse();
		Long twa_id = null;
		// Adding the request to the Audit Table.
		try {
			HeaderInfo headerInfo = new HeaderInfo();
			headerInfo.setHeaderInfo(WSAppUtils.setHeaderInfo(request));
			
			sbsCreateQuoteCommonRH.fectchDefaultValues(policyVO, request);
			sbsCreateQuoteCommonRH.fectchUserId(policyVO,request);
			
			webServiceAudit = webServiceAuditMapper.mapUploadDocumentToAudit(quote_id, "UploadDocument", uploadDocumentRequest, headerInfo,policyVO);
			twa_id = webServiceAudit.getTwa_id().longValue();
		} catch (Exception e1) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			e1.printStackTrace();
		}
		DocumentHandler documentHandler = new DocumentHandler();
		Long quoteNo = null;
		String responseMessage = null;
		if (Utils.isEmpty(policyVO.getGeneralInfo())) {
			policyVO.setGeneralInfo(new GeneralInfoVO());
		}
		if (Utils.isEmpty(policyVO.getGeneralInfo().getInsured())) {
			policyVO.getGeneralInfo().setInsured(new InsuredVO());
		}
		if (!Utils.isEmpty(quote_id)) {
			quoteNo = Long.parseLong(quote_id);
		} else {
			uploadDocumentResponse.setResponseMessage("Please provide Quote Id");
			uploadDocumentResponse.setInternalReferenceNumber(twa_id.toString());
			updateWebServiceAuditMapper.mapUploadDocumentToAudit(twa_id, uploadDocumentResponse);
			return uploadDocumentResponse;
		}
		policyVO.setQuoteNo(quoteNo);
		sbsWSValidatorsList = commonValidators.validateQuoteNumber(policyVO,sbsWSValidatorsList);
		
		if (sbsWSValidatorsList.size() != 0) {
			List<SBSWSValidators> sbswsErrors = new ArrayList<>();
			for (SBSWSValidators validators : sbsWSValidatorsList) {
				if (validators.getCategory().contains(com.Constant.CONST_ERROR2)) {
					SBSWSValidators sbswsValidators = new SBSWSValidators();
					sbswsValidators.setCategory(validators.getCategory());
					sbswsValidators.setCode(validators.getCode());
					sbswsValidators.setField(validators.getField());
					sbswsValidators.setMessage(validators.getMessage());
					sbswsValidators.setType(validators.getType());
					sbswsErrors.add(sbswsValidators);
				}
			}
			if (sbswsErrors.size() != 0) {
				uploadDocumentResponse.setSbswsValidators(sbswsErrors);
				uploadDocumentResponse.setInternalReferenceNumber(twa_id.toString());
				updateWebServiceAuditMapper.mapUploadDocumentToAudit(twa_id, uploadDocumentResponse);
				return uploadDocumentResponse;
			}
		}
		try {
			if (!Utils.isEmpty(quoteNo)) {
				responseMessage = documentHandler.submitTradeLicenceDocument(uploadDocumentRequest,uploadDocumentResponse, policyVO);
			}
		} catch (Exception e) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			// TODO Auto-generated catch block
			e.printStackTrace();
			uploadDocumentResponse.setResponseMessage("Error While Submitting the trade licence Document");
			uploadDocumentResponse.setInternalReferenceNumber(twa_id.toString());
			updateWebServiceAuditMapper.mapUploadDocumentToAudit(twa_id, uploadDocumentResponse);
			return uploadDocumentResponse;
		}
		uploadDocumentResponse.setResponseMessage(responseMessage);
		uploadDocumentResponse.setInternalReferenceNumber(twa_id.toString());
		updateWebServiceAuditMapper.mapUploadDocumentToAudit(twa_id, uploadDocumentResponse);
		return uploadDocumentResponse;
	}
}
