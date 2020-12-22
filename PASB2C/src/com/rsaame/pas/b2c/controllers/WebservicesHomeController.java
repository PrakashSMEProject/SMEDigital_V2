package com.rsaame.pas.b2c.controllers;


import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.exception.SystemException;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.CopyUtils;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.DataHolderVO;
import com.mindtree.ruc.cmn.vo.User;
import com.rsaame.pas.b2b.ws.util.WSAppUtils;
import com.rsaame.pas.b2c.WsAuthentication.BasicAuthenticationService;
import com.rsaame.pas.b2c.cmn.base.BaseController;
import com.rsaame.pas.b2c.cmn.constants.AppConstants;
import com.rsaame.pas.b2c.cmn.handlers.B2CEmailTriggers;
import com.rsaame.pas.b2c.cmn.handlers.CommonHandler;
import com.rsaame.pas.b2c.cmn.utils.AppUtils;
import com.rsaame.pas.b2c.cmn.utils.ReferralUtils;
import com.rsaame.pas.b2c.cmn.utils.ValidationUtil;
import com.rsaame.pas.b2c.homeInsurance.HomeInsuranceSvcHandler;
import com.rsaame.pas.b2c.homeInsurance.IHomeInsuranceSvcHandler;
import com.rsaame.pas.b2c.user.B2CRSAUserWrapper;
import com.rsaame.pas.b2c.ws.handler.CommonServiceHandler;
import com.rsaame.pas.b2c.ws.mapper.BaseRequestVOMapper;
import com.rsaame.pas.b2c.ws.mapper.BaseResponseVOMapper;
import com.rsaame.pas.b2c.ws.mapper.CreatePolicyRequestMapper;
import com.rsaame.pas.b2c.ws.mapper.CreatePolicyResponseMapper;
import com.rsaame.pas.b2c.ws.mapper.HomeCreateQuoteRequestMapper;
import com.rsaame.pas.b2c.ws.mapper.HomeCreateQuoteResponseMapper;
import com.rsaame.pas.b2c.ws.mapper.HomeUpdateQuoteRequestMapper;
import com.rsaame.pas.b2c.ws.mapper.HomeUpdateQuoteResponseMapper;
import com.rsaame.pas.b2c.ws.mapper.RetrieveHomeOptionalCoversResponseMapper;
import com.rsaame.pas.b2c.ws.mapper.RetrievePolicyByPolicyNoMapper;
import com.rsaame.pas.b2c.ws.mapper.RetrieveQuoteByPolicyRequestMapper;
import com.rsaame.pas.b2c.ws.mapper.RetrieveQuoteByPolicyResponseMapper;
import com.rsaame.pas.b2c.ws.mapper.RetrieveQuoteByQuoteIDMapper;
import com.rsaame.pas.b2c.ws.utilities.WebServiceAudit.mapper.WebServiceAuditMapper;
import com.rsaame.pas.b2c.ws.utilities.WebServiceAudit.vo.HeaderInfo;
import com.rsaame.pas.b2c.ws.vo.CreateHomeQuoteRequest;
import com.rsaame.pas.b2c.ws.vo.CreateHomeQuoteResponse;
import com.rsaame.pas.b2c.ws.vo.CreatePolicyRequest;
import com.rsaame.pas.b2c.ws.vo.CreatePolicyResponse;
import com.rsaame.pas.b2c.ws.vo.ErrorMapping;
import com.rsaame.pas.b2c.ws.vo.RetrieveHomeOptionalCoversRequest;
import com.rsaame.pas.b2c.ws.vo.RetrieveHomeOptionalCoversResponse;
import com.rsaame.pas.b2c.ws.vo.RetrieveHomeQuoteByPolicyResponse;
import com.rsaame.pas.b2c.ws.vo.RetrievePolicyByPolicyNo;
import com.rsaame.pas.b2c.ws.vo.RetrieveQuoteByPolicyRequest;
import com.rsaame.pas.b2c.ws.vo.RetrieveQuoteByQuoteId;
import com.rsaame.pas.b2c.ws.vo.TransactionDetails;
import com.rsaame.pas.b2c.ws.vo.UpdateHomeQuoteRequest;
import com.rsaame.pas.b2c.ws.vo.UpdateHomeQuoteResponse;
import com.rsaame.pas.b2c.wsException.ValidationError;
import com.rsaame.pas.b2c.wsException.ValidationException;
import com.rsaame.pas.b2c.wsValidators.CreatePolicyRequestValidator;
import com.rsaame.pas.b2c.wsValidators.HomeCreateQuoteValidator;
import com.rsaame.pas.b2c.wsValidators.RetrieveHomeOptionalCoversValidator;
import com.rsaame.pas.b2c.wsValidators.RetrieveQuoteByPolicyEmailValidator;
import com.rsaame.pas.b2c.wsValidators.RetrieveQuoteByPolicyValidator;
import com.rsaame.pas.b2c.wsValidators.RetriveQuoteByQuoteIdValidator;
import com.rsaame.pas.b2c.wsValidators.UpdateHomeQuoteValidator;
import com.rsaame.pas.cmn.currency.Currency;
import com.rsaame.pas.cmn.vo.UserProfile;
import com.rsaame.pas.com.svc.CommonOpSvc;
import com.rsaame.pas.dao.model.TMasPolicyRating;
import com.rsaame.pas.dao.utils.DAOUtils;
import com.rsaame.pas.doc.svc.PASDocumentService;
import com.rsaame.pas.query.constants.QueryConstants;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.vo.app.Flow;
import com.rsaame.pas.vo.app.MailVO;
import com.rsaame.pas.vo.app.ReportTemplateSet;
import com.rsaame.pas.vo.bus.CoverDetailsVO;
import com.rsaame.pas.vo.bus.GeneralInfoVO;
import com.rsaame.pas.vo.bus.HomeInsuranceVO;
import com.rsaame.pas.vo.bus.InsuredVO;
import com.rsaame.pas.vo.bus.LOB;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.ReferralListVO;
import com.rsaame.pas.vo.bus.ReferralVO;
import com.rsaame.pas.vo.bus.SchemeVO;
import com.rsaame.pas.vo.bus.SourceOfBusinessVO;
import com.rsaame.pas.vo.bus.StaffDetailsVO;
import com.rsaame.pas.vo.cmn.CommonVO;
import com.rsaame.pas.web.UserProfileHandler;

@Controller
@RequestMapping("/rsaservices")
public class WebservicesHomeController extends BaseController{
	private final static Logger LOGGER = Logger.getLogger(WebservicesHomeController.class);
	private static final Byte RELATIONSHIP_SELF = Byte.valueOf("1");
	IHomeInsuranceSvcHandler homeInsuranceSvcHandler = new HomeInsuranceSvcHandler();
	CommonServiceHandler commonServiceHandler = new CommonServiceHandler();
	CommonController commonCtrl = new CommonController();
	CommonHandler handler = new CommonHandler();
	WebServiceAuditMapper webServiceAuditMapper = new WebServiceAuditMapper();
	
	@RequestMapping(value = "**/UpdateHomeQuote", method = RequestMethod.POST) 
	public  @ResponseBody UpdateHomeQuoteResponse updateHomeQuote(@RequestBody  UpdateHomeQuoteRequest updateHomeQuoteRequest, @ModelAttribute("homeInsurnaceVO") HomeInsuranceVO homeInsuranceVO,
			 	BindingResult bindingResult, HttpServletRequest request,
				HttpSession session, HttpServletResponse response) {
		
		LOGGER.info("Update request for Home LOB started");
		UpdateHomeQuoteResponse updateHomeQuoteResponse= new UpdateHomeQuoteResponse();
		BaseRequestVOMapper requestVOMapper = new HomeUpdateQuoteRequestMapper();
		BaseResponseVOMapper responseVOMapper = new HomeUpdateQuoteResponseMapper();
		ValidationException validationException = new ValidationException();
		UpdateHomeQuoteValidator homeQuoteValidator = new UpdateHomeQuoteValidator();
		boolean isPrintCase=false;
		ValidationError validationError2 = new ValidationError();
		updateHomeQuoteResponse.setErrors(new ArrayList<ValidationError>());
		try {
			HeaderInfo headerInfo = new HeaderInfo();
			headerInfo.setHeaderInfo(WSAppUtils.setHeaderInfo(request));
			//07.08.2020 CTS  CR#11645 - Home Digital API - error  message need to display, if trying to update the quote, if already converted into policy- start
			List<Object[]> resultSet = null;
			resultSet = DAOUtils.getSqlResultForPas( QueryConstants.GET_QUOTE_STATUS_BY_QUO_NO,  updateHomeQuoteRequest.getQuotationNo());
			
			if( !Utils.isEmpty( resultSet ) && resultSet.size() > 0 ){
				int documentCode = Integer.valueOf( String.valueOf( resultSet.get( 0 )[0] ) );
				int quoteStatus =  Integer.valueOf( String.valueOf( resultSet.get( 0 )[1] ) );
				
				if((documentCode == 6 || documentCode == 5) ){
					updateHomeQuoteRequest.setQuoteStatus(quoteStatus);
				}
			}
			
			//07.08.2020 CTS  CR#11645  - ENDS
			validationException = homeQuoteValidator.validate(updateHomeQuoteRequest);
			if(!Utils.isEmpty(validationException.getErrors())) {
				List<ValidationError> errors = new ArrayList<ValidationError>();
				for (ValidationError validationError : validationException.getErrors()) {
					ValidationError error = new ValidationError();
					error.setCode(validationError.getCode());
					error.setField(validationError.getField());
					error.setMessage(validationError.getMessage());
					errors.add(error);
				}
				updateHomeQuoteResponse.setErrors(errors);
				return updateHomeQuoteResponse;
			}
			homeInsuranceVO.setAppFlow(Flow.EDIT_QUO);
			String pmmId = request.getHeader(com.Constant.CONST_PARTNERID);
			updateHomeQuoteRequest.setPmmId(pmmId);
			webServiceAuditMapper.mapStartTimeForAudit(pmmId);
			requestVOMapper.mapRequestToVO(updateHomeQuoteRequest, homeInsuranceVO);
			AppUtils.setScaleForLOB(homeInsuranceVO.getCommonVO().getLob());
			String partnerName = request.getHeader(com.Constant.CONST_PARTNERNAME);
			if(!Utils.isEmpty(partnerName)){
				request.setAttribute(com.Constant.CONST_PARTNERNAMECSS, partnerName);
			}
			if (!(Utils.isEmpty(partnerName))) {
				if (Utils.isEmpty(homeInsuranceVO.getGeneralInfo())) {
					GeneralInfoVO generalInfo = new GeneralInfoVO();
					homeInsuranceVO.setGeneralInfo(generalInfo);
				}
				if(Utils.isEmpty(homeInsuranceVO.getGeneralInfo().getSourceOfBus()))
				{
				SourceOfBusinessVO sourceOfBusinessVO = new SourceOfBusinessVO();
				sourceOfBusinessVO.setPartnerName(partnerName);
				homeInsuranceVO.getGeneralInfo().setSourceOfBus(
						sourceOfBusinessVO);
				}
				else
				{
					homeInsuranceVO.getGeneralInfo().getSourceOfBus().setPartnerName( partnerName );
				}
				homeInsuranceVO = (HomeInsuranceVO) HomeInsuranceSvcHandler
						.loadPartnerMgmtDetails(homeInsuranceVO);
				// Added by Vishwa to enable the CSS change for partner
				request.setAttribute(com.Constant.CONST_PARTNERNAMECSS, partnerName); 
				LOGGER.info("Successfully saved..");
			}
			setRequestAttributes(homeInsuranceVO,request);
			// Adding Here its specific to Update Home Quote only
			String mortgageOthers = null;
			if(!Utils.isEmpty(updateHomeQuoteRequest.getBuildingDetails().getMortgageeOthers())) {
				mortgageOthers = updateHomeQuoteRequest.getBuildingDetails().getMortgageeOthers();
			}
			
			if( !Utils.isEmpty(mortgageOthers) && !Utils.isEmpty( homeInsuranceVO.getBuildingDetails().getMortgageeName() )
					&& AppConstants.MORTGAGE_OTHERS_CODE.equalsIgnoreCase( homeInsuranceVO.getBuildingDetails().getMortgageeName() )){
				
					homeInsuranceVO.getBuildingDetails().setMortgageeName(homeInsuranceVO.getBuildingDetails().getMortgageeName() + "#" + mortgageOthers);
			}
			if( Utils.isEmpty( mortgageOthers ) && !Utils.isEmpty( homeInsuranceVO ) && !Utils.isEmpty( homeInsuranceVO.getBuildingDetails() )
					&& !Utils.isEmpty( homeInsuranceVO.getBuildingDetails().getMortgageeName() )
					&& homeInsuranceVO.getBuildingDetails().getMortgageeName().equalsIgnoreCase( AppConstants.MORTGAGE_OTHERS_CODE ) ){
				homeInsuranceVO.getBuildingDetails().setMortgageeName( null );
			}
			
			
			
			///////// For Storing first page info
			homeInsuranceVO = (HomeInsuranceVO) HomeInsuranceSvcHandler.loadPartnerMgmtDetails(homeInsuranceVO);
			if(!Utils.isEmpty(homeInsuranceVO.getGeneralInfo().getSourceOfBus().getPartnerName()) && (Utils.isEmpty(homeInsuranceVO.getCommonVO()) || Utils.isEmpty(homeInsuranceVO.getCommonVO().getLoggedInUser())))
			{ 
				UserProfile userProfile = UserProfileHandler.getUserProfileVo(homeInsuranceVO.getGeneralInfo().getExtAccExecCode());
				request.getSession( false ).setAttribute( AppConstants.SESSION_USER_PROFILE_VO, userProfile );
				homeInsuranceVO.getCommonVO().setLoggedInUser(userProfile);
			}
			HomeInsuranceSvcHandler.setDefaultValues(homeInsuranceVO);
			validatePromotionalCode(homeInsuranceVO, request);
			if(!Utils.isEmpty(updateHomeQuoteRequest.getTransactionDetails().getPartnerTrnReferenceNumber())) {
				if (Utils.isEmpty(updateHomeQuoteResponse.getTransactionDetails())) {
					updateHomeQuoteResponse.setTransactionDetails(new TransactionDetails());
				}
				updateHomeQuoteResponse.getTransactionDetails().setPartnerTrnReferenceNumber(updateHomeQuoteRequest.getTransactionDetails().getPartnerTrnReferenceNumber());
				updateHomeQuoteResponse.getTransactionDetails().setFinalUpdate(updateHomeQuoteRequest.getTransactionDetails().getFinalUpdate());
			}
			homeInsuranceVO = (HomeInsuranceVO) homeInsuranceSvcHandler.saveHomeRenewalInsuranceDetails((PolicyDataVO)homeInsuranceVO, false, request.getRequestURL().toString(), isPrintCase);
			
			AppUtils.setBuildingDropDown( homeInsuranceVO, request );
			AppUtils.setScaleForLOB( homeInsuranceVO.getCommonVO().getLob() );
			PolicyDataVO policyDataVO = (PolicyDataVO)homeInsuranceVO;
			homeInsuranceVO.getGeneralInfo().getSourceOfBus().setPartnerName(request.getHeader(com.Constant.CONST_PARTNERNAME));
			policyDataVO.getGeneralInfo().getSourceOfBus().setPartnerName(homeInsuranceVO.getGeneralInfo().getSourceOfBus().getPartnerName());
			if( !Utils.isEmpty( homeInsuranceVO.getReferralVOList() ) ){
				ReferralListVO referralListVO = homeInsuranceVO.getReferralVOList();

				ResourceBundle resourceBundle = ResourceBundle.getBundle(com.Constant.CONST_CONFIG_MESSAGES);
				for (ReferralVO referralVO : referralListVO.getReferrals()) {
					referralVO.getReferralText();
					for(Entry<String, Map<String, String>> entry : referralVO.getRefDataTextField().entrySet())
			        {
						ValidationError error = new ValidationError();
					  	error.setField(entry.getKey());
					  	error.setCode(resourceBundle.getString(entry.getKey()));
			            Map<String, String> internalMap = entry.getValue();
			            for(Entry<String, String> internalEntry : internalMap.entrySet())
			            {
			                error.setMessage(internalEntry.getValue());
			            }
			            updateHomeQuoteResponse.getErrors().add(error);
			        }	
				}	
				//homeInsuranceVO=(HomeInsuranceVO)homeInsuranceSvcHandler.loadHomeInsuranceDetails(homeInsuranceVO);
				responseVOMapper.mapVOToResponse(homeInsuranceVO, updateHomeQuoteResponse);
				webServiceAuditMapper.mapUpdateHomeQuoteToAudit(updateHomeQuoteRequest, updateHomeQuoteResponse,headerInfo,homeInsuranceVO);
				return updateHomeQuoteResponse;
			}
			//homeInsuranceVO=(HomeInsuranceVO)homeInsuranceSvcHandler.loadHomeInsuranceDetails(homeInsuranceVO);
			if(!Utils.isEmpty(updateHomeQuoteRequest.getProposalForm()) && updateHomeQuoteRequest.getProposalForm()==true) {
				long startTime = System.currentTimeMillis();
				LOGGER.debug( "Calling Document Creation:::_1"  + new Date(startTime)   );
				CommonHandler.printProposalForm(homeInsuranceVO);
				byte[] fileContent = null;
				String file = CommonHandler.encodeToString(Utils.getSingleValueAppConfig(com.Constant.CONST_QUOTE_DOC_PROPOSAL_LOC) +homeInsuranceVO.getCommonVO().getQuoteNo() +com.Constant.CONST_QUOTE_PDF);
				fileContent = file.getBytes();
				long endTime = System.currentTimeMillis();
				LOGGER.debug( " Document Creation Done_1"  + new Date(endTime)   );
				LOGGER.debug( "Time taken for Document creation and converting to byte array::_1"  + ( endTime - startTime )  );
				updateHomeQuoteResponse.setDocument(fileContent);
			}
			if(!Utils.isEmpty(updateHomeQuoteRequest.getQuoteConfirmationEmail()) && updateHomeQuoteRequest.getQuoteConfirmationEmail()==true) {
				CommonHandler.populateAndTriggerEmail(homeInsuranceVO, request.getContextPath(), B2CEmailTriggers.HOME_SAVE_FOR_LATER, null);
			}	
			responseVOMapper.mapVOToResponse(homeInsuranceVO, updateHomeQuoteResponse);
			webServiceAuditMapper.mapUpdateHomeQuoteToAudit(updateHomeQuoteRequest, updateHomeQuoteResponse,headerInfo,homeInsuranceVO);
			  
		} catch(BusinessException e) {
			
			validationError2.setMessage(e.getMessage());
			updateHomeQuoteResponse.getErrors().add(validationError2);
			e.printStackTrace();
		}catch(SystemException e) {
			ResourceBundle resourceBundle = ResourceBundle.getBundle(com.Constant.CONST_CONFIG_MESSAGES);
			validationError2.setMessage(resourceBundle.getString(com.Constant.CONST_CMN_SYSTEMERROR));
			updateHomeQuoteResponse.getErrors().add(validationError2);
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			ResourceBundle resourceBundle = ResourceBundle.getBundle(com.Constant.CONST_CONFIG_MESSAGES);
			validationError2.setMessage(resourceBundle.getString(com.Constant.CONST_CMN_SYSTEMERROR));
			updateHomeQuoteResponse.getErrors().add(validationError2);
			e.printStackTrace();
			
		}
		LOGGER.info("Update request for Home LOB Done...");
		return updateHomeQuoteResponse;
		
	}
	

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "**/CreateHomePolicy", method = RequestMethod.POST) 
	public  @ResponseBody CreatePolicyResponse createHomePolicy(@RequestBody  CreatePolicyRequest createPolicyRequest, @ModelAttribute("homeInsurnaceVO") HomeInsuranceVO homeInsuranceVO,
			 	BindingResult bindingResult, HttpServletRequest request,
				HttpSession session, HttpServletResponse response) {
		
		LOGGER.info("Create Home Policy Started...");
		CreatePolicyResponse createPolicyResponse = new CreatePolicyResponse();
		BaseRequestVOMapper baseRequestVOMapper = new CreatePolicyRequestMapper();
		BaseResponseVOMapper baseResponseVOMapper = new CreatePolicyResponseMapper();
		String pmmId = request.getHeader(com.Constant.CONST_PARTNERID);
		ValidationError validationError2 = new ValidationError();
		ValidationException validationException = new ValidationException();
		CreatePolicyRequestValidator createPolicyRequestValidator = new CreatePolicyRequestValidator();
		if(Utils.isEmpty(createPolicyResponse.getErrors())) {
			createPolicyResponse.setErrors(new ArrayList<ValidationError>());
		}
		
		try {
			validationException = createPolicyRequestValidator.validate(createPolicyRequest);
			if(!Utils.isEmpty(validationException.getErrors())) {
				List<ValidationError> errors = new ArrayList<ValidationError>();
				for (ValidationError validationError : validationException.getErrors()) {
					ValidationError error = new ValidationError();
					error.setCode(validationError.getCode());
					error.setField(validationError.getField());
					error.setMessage(validationError.getMessage());
					errors.add(error);
				}
				createPolicyResponse.setErrors(errors);
				return createPolicyResponse;
			}
			webServiceAuditMapper.mapStartTimeForAudit(pmmId);
			HeaderInfo headerInfo = new HeaderInfo();
			headerInfo.setHeaderInfo(WSAppUtils.setHeaderInfo(request));
			baseRequestVOMapper.mapRequestToVO(createPolicyRequest, homeInsuranceVO);
			setRequestAttributes(homeInsuranceVO, request);
			/*if(!Utils.isEmpty(request.getHeader(com.Constant.CONST_PARTNERID))) {
				homeInsuranceVO.getGeneralInfo().getSourceOfBus().setPartnerId(request.getHeader(com.Constant.CONST_PARTNERID));
				homeInsuranceVO.getGeneralInfo().getSourceOfBus().setPartnerName(request.getHeader(com.Constant.CONST_PARTNERID));
			}*/
			if(!Utils.isEmpty( homeInsuranceVO.getOnlinePaymentDetailsVO() )){
				LOGGER.info( "calling CommonHandler:saveOnlinePaymentDetails method to save paymentDetailsVO." );
				handler.saveOnlinePaymentDetails(homeInsuranceVO.getOnlinePaymentDetailsVO());
				LOGGER.info( "saveOnlinePaymentDetails saved successfully..." );
			}
			if(!Utils.isEmpty(homeInsuranceVO.getOnlinePaymentDetailsVO())) {
				createPolicyResponse.setTransactionRefNo(homeInsuranceVO.getOnlinePaymentDetailsVO().getTransactionId());
			}
			LOGGER.info("Calling covert to policy Procedure ....");
			homeInsuranceVO =  (HomeInsuranceVO) handler.convertToPolicy(homeInsuranceVO, true, request.getRequestURL().toString());
			LOGGER.info("Calling covert to policy Procedure execution done ....");
			
			if((!Utils.isEmpty(createPolicyRequest.getDocuments()) || !Utils.isEmpty(createPolicyRequest.getPolicyConfirmationEmail()))
					&& (createPolicyRequest.getDocuments().getDocsInResponse() || createPolicyRequest.getPolicyConfirmationEmail())
					) {
				LOGGER.info("CommonHandler:convertToPolicy, before triggering mail for HOME LOB.");
				CommonHandler.populateAndTriggerEmail(homeInsuranceVO,  request.getRequestURL().toString(),createPolicyRequest,
						B2CEmailTriggers.HOME_CONVERT_TO_POLICY);
			}
			homeInsuranceVO.getPremiumVO().setPremiumAmt(homeInsuranceVO.getPremiumVO().getPremiumAmt() + homeInsuranceVO.getPremiumVO().getVatTax());
			baseResponseVOMapper.mapVOToResponse(homeInsuranceVO, createPolicyResponse);
			
			if(!Utils.isEmpty(createPolicyRequest.getDocuments())) {
				createPolicyResponse.setDocuments(createPolicyRequest.getDocuments());
				if(!Utils.isEmpty(createPolicyRequest.getDocuments().getDocsDetails())) {
					createPolicyResponse.getDocuments().setDocsDetails(createPolicyRequest.getDocuments().getDocsDetails());
				}
			}
			
			if(!Utils.isEmpty(createPolicyRequest.getDocuments()) && createPolicyRequest.getDocuments().getDocsInResponse()) {
				long startTime = System.currentTimeMillis();
				LOGGER.debug( "Calling Document Creation:::_2"  + new Date(startTime)   );
				
				byte[] fileContent = null;
				CommonHandler.printPolicyDocument(homeInsuranceVO, createPolicyRequest.getDocuments());
				String policySchedulefile = CommonHandler.encodeToString(Utils.getSingleValueAppConfig("POL_DOC_POL_SCHED_LOC")+homeInsuranceVO.getCommonVO().getPolicyNo()+"-PolicySchedule.pdf");
				fileContent = policySchedulefile.getBytes();
				
				createPolicyResponse.getDocuments().setPolicySchedule(fileContent);
				
				if(createPolicyRequest.getDocuments().getDocsDetails().getLetterToBank()==true) {
					
					CommonOpSvc commonOpSvc = (CommonOpSvc) Utils.getBean("geComSvc");
					BaseVO baseVO = (BaseVO) commonOpSvc.invokeMethod(
							"checkForMortgageeName", homeInsuranceVO.getCommonVO());
					DataHolderVO<Boolean> resultVo = null;
					resultVo = (DataHolderVO<Boolean>) baseVO;
					if (!Utils.isEmpty(resultVo) && resultVo.getData()) {
						String bankToLetter = CommonHandler.encodeToString(Utils.getSingleValueAppConfig("POL_DOC_BANK_LETTER") +homeInsuranceVO.getCommonVO().getPolicyNo() +"-BankLetter.pdf");
						byte[] bankLetterContent = bankToLetter.getBytes();
						createPolicyResponse.getDocuments().setLetterToBank(bankLetterContent);
					}
					
				}
				
				long endTime = System.currentTimeMillis();
				LOGGER.debug( " Document Creation Done_2"  + new Date(endTime)   );
				LOGGER.debug( "Time taken for Document creation and converting to byte array::_2"  + ( endTime - startTime )  );
				
			}
			webServiceAuditMapper.mapCreatePolicyToAudit(homeInsuranceVO, createPolicyRequest, createPolicyResponse,headerInfo);
			
		} catch(BusinessException e) {
			
			validationError2.setMessage(e.getMessage());
			createPolicyResponse.getErrors().add(validationError2);
			e.printStackTrace();
		}catch(SystemException e) {
			ResourceBundle resourceBundle = ResourceBundle.getBundle(com.Constant.CONST_CONFIG_MESSAGES);
			validationError2.setMessage(resourceBundle.getString(com.Constant.CONST_CMN_SYSTEMERROR));
			createPolicyResponse.getErrors().add(validationError2);
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			ResourceBundle resourceBundle = ResourceBundle.getBundle(com.Constant.CONST_CONFIG_MESSAGES);
			validationError2.setMessage(resourceBundle.getString(com.Constant.CONST_CMN_SYSTEMERROR));
			createPolicyResponse.getErrors().add(validationError2);
			e.printStackTrace();
			
		}
		LOGGER.info("Create Home Policy Done...");
		return createPolicyResponse;
		
		
	}

	private void setRequestAttributes(HomeInsuranceVO homeInsuranceVO,HttpServletRequest request){
		BasicAuthenticationService authService = new BasicAuthenticationService();
		String authorization = authService.decodeText(request.getHeader("Authorization"));
		String[] credentials = authService.getUserIdAndPassword(authorization);
		int userId =Integer.parseInt(credentials[0]);
		System.out.println(credentials[0]);
		
		
		if(Utils.isEmpty( homeInsuranceVO.getCommonVO() )){homeInsuranceVO.setCommonVO(new CommonVO());};
		
		if(!Utils.isEmpty(request.getHeader(com.Constant.CONST_PARTNERID))) {
			homeInsuranceVO.getGeneralInfo().getSourceOfBus().setPartnerId(Integer.toString(userId));
			homeInsuranceVO.getGeneralInfo().getSourceOfBus().setPartnerName(Integer.toString(userId));
		}
		if(!Utils.isEmpty(request.getHeader(com.Constant.CONST_LOCATION))) {
			homeInsuranceVO.getCommonVO().setLocCode(Integer.parseInt(request.getHeader(com.Constant.CONST_LOCATION)));
		}
		if (Utils.isEmpty(request.getSession(false).getAttribute(
				AppConstants.SESSION_USER_PROFILE_VO)) && !Utils.isEmpty(homeInsuranceVO.getGeneralInfo().getExtAccExecCode())){
			UserProfile userProfile = UserProfileHandler.getUserProfileVo(homeInsuranceVO.getGeneralInfo().getExtAccExecCode());
			request.getSession( false ).setAttribute( AppConstants.SESSION_USER_PROFILE_VO, userProfile );
//			RSAUser user = new RSAUser(username, password, enabled, authorities);
			
		}
		homeInsuranceVO.getCommonVO().setLoggedInUser((User) request.getSession(false).getAttribute(
				AppConstants.SESSION_USER_PROFILE_VO));
		
		if(Utils.isEmpty( homeInsuranceVO.getCommonVO().getLoggedInUser() )) {
			UserProfile userProfile = new UserProfile();
			userProfile.setRsaUser(new B2CRSAUserWrapper());
			 homeInsuranceVO.getCommonVO().setLoggedInUser(userProfile);
//			 ServiceContext.setUser(userProfile);
			 homeInsuranceVO.getCommonVO().getLoggedInUser().setUserId(Integer.toString(userId)); // set the user id from header 
	         homeInsuranceVO.getGeneralInfo().setIntAccExecCode(homeInsuranceVO.getGeneralInfo().getIntAccExecCode()); // set the user id form header
		}
		
		homeInsuranceVO.getCommonVO().setIsQuote(Boolean.TRUE);
		homeInsuranceVO.getCommonVO().setLob(LOB.HOME);
		if(Utils.isEmpty(homeInsuranceVO.getScheme())){
			homeInsuranceVO.setScheme(new SchemeVO());
		}
		if (!(Utils.isEmpty(request.getHeader(com.Constant.CONST_PARTNERNAME)))) {
			
				if (Utils.isEmpty(homeInsuranceVO.getGeneralInfo())) {
					GeneralInfoVO generalInfo = new GeneralInfoVO();
					homeInsuranceVO.setGeneralInfo(generalInfo);
				}
				if(Utils.isEmpty(homeInsuranceVO.getGeneralInfo().getSourceOfBus()))
				{
					SourceOfBusinessVO sourceOfBusinessVO = new SourceOfBusinessVO();
					homeInsuranceVO.getGeneralInfo().setSourceOfBus(
							sourceOfBusinessVO);
				}
				PolicyDataVO policyDataVO = (PolicyDataVO)homeInsuranceVO;
				homeInsuranceVO.getGeneralInfo().getSourceOfBus().setPartnerName(request.getHeader(com.Constant.CONST_PARTNERNAME));
				policyDataVO.getGeneralInfo().getSourceOfBus().setPartnerName(homeInsuranceVO.getGeneralInfo().getSourceOfBus().getPartnerName());
				homeInsuranceVO = (HomeInsuranceVO) HomeInsuranceSvcHandler
						.loadPartnerMgmtDetails(homeInsuranceVO);			
		
		}
		if(	!Utils.isEmpty(homeInsuranceVO.getCommonVO()) && 
				( Short.valueOf( Utils.getSingleValueAppConfig( "REN_QUO_DOC_CODE" ) ).equals( homeInsuranceVO.getCommonVO().getDocCode()) ))
		{
			homeInsuranceVO.getGeneralInfo().getInsured().setBusType( AppConstants.BUS_TYPE_RENEWAL );
		}
		else
		{
			homeInsuranceVO.getGeneralInfo().getInsured().setBusType( AppConstants.BUS_TYPE_NEW );
		}
		/*
		 * For VAT 142244
		 * For setting vatTAx
		 */
		/*if (!(Utils.isEmpty(request.getParameter("vatTax")))) {
			homeInsuranceVO.getPremiumVO().setVatTax(Double.parseDouble(request.getParameter("vatTax")));
			homeInsuranceVO.getPremiumVO().setPremiumAmt(homeInsuranceVO.getPremiumVO().getPremiumAmt() + (Double.parseDouble(request.getParameter("vatTax"))));
		}*/
		
	}
	
	private void setRequestAttributesForRenewal(HomeInsuranceVO homeInsuranceVO, HttpServletRequest request,HttpSession session) throws Exception{
		BasicAuthenticationService authService = new BasicAuthenticationService();
		String authorization = authService.decodeText(request.getHeader("Authorization"));
		String[] credentials = authService.getUserIdAndPassword(authorization);
		int userId =Integer.parseInt(credentials[0]);
		List<BigDecimal> renQuote = new ArrayList<BigDecimal>();
		if(Utils.isEmpty( homeInsuranceVO.getCommonVO() )){homeInsuranceVO.setCommonVO(new CommonVO());};
		
		if(!Utils.isEmpty(request.getHeader(com.Constant.CONST_PARTNERID))) {
			homeInsuranceVO.getGeneralInfo().getSourceOfBus().setPartnerId(request.getHeader(com.Constant.CONST_PARTNERID));
			homeInsuranceVO.getGeneralInfo().getSourceOfBus().setPartnerName(request.getHeader(com.Constant.CONST_PARTNERID));
		}else
			homeInsuranceVO.getGeneralInfo().getSourceOfBus().setPartnerName("null");
		if(!Utils.isEmpty(homeInsuranceVO.getGeneralInfo().getSourceOfBus().getDistributionChannel())) {
			if(Integer.parseInt(Utils.getSingleValueAppConfig("DIST_CHANNEL_DIRECT_WEB"))==homeInsuranceVO.getGeneralInfo().getSourceOfBus().getDistributionChannel()) {
				homeInsuranceVO.getGeneralInfo().setExtAccExecCode(Integer.parseInt(Utils.getSingleValueAppConfig("USER_10")));
			}
			if(Integer.parseInt(Utils.getSingleValueAppConfig("DIST_CHANNEL_BROKER"))==homeInsuranceVO.getGeneralInfo().getSourceOfBus().getDistributionChannel()) {
				homeInsuranceVO.getGeneralInfo().setExtAccExecCode(992);
			}
		}
		if(!Utils.isEmpty(request.getHeader(com.Constant.CONST_LOCATION))) {
			homeInsuranceVO.getCommonVO().setLocCode(Integer.parseInt(request.getHeader(com.Constant.CONST_LOCATION)));
		}
		
		if (Utils.isEmpty(request.getSession(false).getAttribute(
				AppConstants.SESSION_USER_PROFILE_VO))){
			UserProfile userProfile = new UserProfile();
			request.getSession( false ).setAttribute( AppConstants.SESSION_USER_PROFILE_VO, userProfile );
		}
		homeInsuranceVO.getCommonVO().setLoggedInUser((User) request.getSession(false).getAttribute(
				AppConstants.SESSION_USER_PROFILE_VO));	
		if(Utils.isEmpty( homeInsuranceVO.getCommonVO().getLoggedInUser() )) {
			UserProfile userProfile = new UserProfile();
			userProfile.setRsaUser(new B2CRSAUserWrapper());
			 homeInsuranceVO.getCommonVO().setLoggedInUser(userProfile);
//			 ServiceContext.setUser(userProfile);
			 homeInsuranceVO.getCommonVO().getLoggedInUser().setUserId(Integer.toString(userId));
		}
		/*if (! Utils.isEmpty(homeInsuranceVO.getCommonVO().getPolicyNo() ))
		{
			
			try{			
				renQuote = AppUtils.getQuoteFromPolicy(homeInsuranceVO.getCommonVO());
				System.out.println("The Quote number is "+renQuote);
			}
			catch(Exception e)
			{
				throw e;
			}	
		}
		if(! Utils.isEmpty( renQuote )){
			homeInsuranceVO.getCommonVO().setQuoteNo(renQuote.get(0).longValue());
		}*/
		String mortgageOthers = request.getParameter(com.Constant.CONST_MORTGAGEOTHERS);
		if(!Utils.isEmpty(mortgageOthers)){
			homeInsuranceVO.getBuildingDetails().setMortgageeName(homeInsuranceVO.getBuildingDetails().getMortgageeName() + "#" + mortgageOthers);
		}
		//homeInsuranceVO.getCommonVO().setDocCode(Short.valueOf( Utils.getSingleValueAppConfig( "REN_QUO_DOC_CODE" ) ));
	}
	
	private void validatePromotionalCode(HomeInsuranceVO homeInsuranceVO,
			HttpServletRequest request) {
		if( !Utils.isEmpty( homeInsuranceVO.getGeneralInfo().getSourceOfBus())
				&& !Utils.isEmpty( homeInsuranceVO.getScheme().getEffDate() ) && !Utils.isEmpty( homeInsuranceVO.getGeneralInfo().getSourceOfBus().getPromoCode() )){
			
			/*String promoCodePre = (String) request.getAttribute("promoCodeForVal");
			String promoCodePage = homeInsuranceVO.getGeneralInfo().getSourceOfBus().getPromoCode();
			
			if(Utils.isEmpty(promoCodePre) ||(!Utils.isEmpty(promoCodePre) && !promoCodePre.equals(promoCodePage)) ){*/
				
				TaskExecutor.executeTasks( "VALIDATE_PROMO_CODE", homeInsuranceVO );
//			}
			
		}
	}
	
	@RequestMapping(value = "**/CreateHomeQuote", method = RequestMethod.POST)
    public @ResponseBody CreateHomeQuoteResponse createHomeQuote(@RequestBody CreateHomeQuoteRequest createHomeQuoteRequest,@ModelAttribute("homeInsuranceVO") HomeInsuranceVO homeInsuranceVO, BindingResult bindingResult,HttpServletRequest request,
    		HttpServletResponse response, HttpSession session) throws Exception {
 
		LOGGER.info("Save request for Home Risk Cover page started");
		boolean completePurchaseInd = false;
		String partnerName = null;
        CreateHomeQuoteResponse createHomeQuoteResponse=new CreateHomeQuoteResponse();
        ValidationException validationException = new ValidationException();
        validationException = new HomeCreateQuoteValidator().validate(createHomeQuoteRequest);
        ValidationError validationError = new ValidationError();
        if(validationException.getErrors() != null && !Utils.isEmpty(validationException.getErrors())) {
            createHomeQuoteResponse.setErrors(validationException.getErrors());
            return createHomeQuoteResponse;
        }
		try{
			HeaderInfo headerInfo = new HeaderInfo();
			headerInfo.setHeaderInfo(WSAppUtils.setHeaderInfo(request));
			String pmmId = request.getHeader(com.Constant.CONST_PARTNERID);
			webServiceAuditMapper.mapStartTimeForAudit(pmmId);
			createHomeQuoteRequest.setPmmId(pmmId);
			HomeCreateQuoteRequestMapper homeCreateQuoteRequestMapper = new HomeCreateQuoteRequestMapper();
			homeCreateQuoteRequestMapper.mapRequestToVO(createHomeQuoteRequest, homeInsuranceVO);
			setRequestAttributes(homeInsuranceVO, request);
			homeInsuranceVO = (HomeInsuranceVO) HomeInsuranceSvcHandler.loadPartnerMgmtDetails(homeInsuranceVO);
			
			if(!Utils.isEmpty(homeInsuranceVO.getGeneralInfo().getSourceOfBus().getPartnerName()) && (Utils.isEmpty(homeInsuranceVO.getCommonVO()) || Utils.isEmpty(homeInsuranceVO.getCommonVO().getLoggedInUser())))
			{ 
				UserProfile userProfile = UserProfileHandler.getUserProfileVo(homeInsuranceVO.getGeneralInfo().getExtAccExecCode());
				request.getSession( false ).setAttribute( AppConstants.SESSION_USER_PROFILE_VO, userProfile );
				homeInsuranceVO.getCommonVO().setLoggedInUser(userProfile);
			}
			
			HomeInsuranceSvcHandler.setDefaultValues(homeInsuranceVO);
						
			/*
			 * For VAT 142244
			 * For Setting vatTax to PremiumVO object
			 */
			double vatTaxPer = 0.0;
			request.setAttribute(com.Constant.CONST_VATTAXPER, 5.0);
	        if(!Utils.isEmpty(request.getParameter(com.Constant.CONST_VATTAXPER))){
	    		vatTaxPer =Double.parseDouble( request.getParameter(com.Constant.CONST_VATTAXPER));
	    	}else{
	    		vatTaxPer = 5.0;
	    	}
	        if(!Utils.isEmpty(homeInsuranceVO.getPremiumVO())){
	        	homeInsuranceVO.getPremiumVO().setVatTaxPerc(vatTaxPer);
	        	homeInsuranceVO.getCommonVO().getPremiumVO().setVatTaxPerc(vatTaxPer);
	        	homeInsuranceVO.setVatTaxPerc(vatTaxPer);
	    	}
			//String partnerId = request.getParameter("partnerId")== null ?"":(String) request.getParameter("partnerId");		/*commented unused variable- sonar violation fix */
			partnerName = request.getParameter("partnerName")== null ?"":(String) request.getParameter("partnerName");
			partnerName = request.getHeader(com.Constant.CONST_PARTNERNAME);
			homeInsuranceVO.getGeneralInfo().getSourceOfBus().setDefaultAssignToUser(421L);
			if(!Utils.isEmpty(partnerName)){
				request.setAttribute(com.Constant.CONST_PARTNERNAMECSS, partnerName);
			}
			validatePromotionalCode(homeInsuranceVO, request);
			if(!Utils.isEmpty(createHomeQuoteRequest.getTransactionDetails().getPartnerTrnReferenceNumber())) {
				if (Utils.isEmpty(createHomeQuoteResponse.getTransactionDetails())) {
					createHomeQuoteResponse.setTransactionDetails(new TransactionDetails());
				}
				createHomeQuoteResponse.getTransactionDetails().setPartnerTrnReferenceNumber(createHomeQuoteRequest.getTransactionDetails().getPartnerTrnReferenceNumber());
			}
	        homeInsuranceSvcHandler.saveHomeRiskCoverDetails((PolicyDataVO)homeInsuranceVO, completePurchaseInd, request.getRequestURL().toString());

			if(!Utils.isEmpty(homeInsuranceVO.getQuoteNo())){
				request.setAttribute( "quoteNo", homeInsuranceVO.getQuoteNo() );
			}
			
			if(!Utils.isEmpty(homeInsuranceVO.getPremiumVO().getPremiumAmt())){
				request.setAttribute( "quoteValue",Currency.getFormattedCurrency( homeInsuranceVO.getPremiumVO().getPremiumAmt() , homeInsuranceVO.getCommonVO().getLob().toString() ));
			}
			
			AppUtils.setQuoteValidDate( homeInsuranceVO, request );
			HomeCreateQuoteResponseMapper homeCreateQuoteResponseMapper = new HomeCreateQuoteResponseMapper();
			
			if( !Utils.isEmpty( homeInsuranceVO.getReferralVOList() ) ){
				List<ReferralVO> referralMsgs = homeInsuranceVO.getReferralVOList().getReferrals();
				ReferralVO referralVO = null;
				ResourceBundle resourceBundle = ResourceBundle.getBundle(com.Constant.CONST_CONFIG_MESSAGES);
				for (int i=0 ; i<referralMsgs.size();i++){
					referralVO = referralMsgs.get(i);
					  for(Entry<String, Map<String, String>> entry : referralVO.getRefDataTextField().entrySet())
				        {
						  	ValidationError error = new ValidationError();
						  	error.setField(entry.getKey());
						  	error.setCode(resourceBundle.getString(entry.getKey()));
				            Map<String, String> internalMap = entry.getValue();
				            for(Entry<String, String> internalEntry : internalMap.entrySet())
				            {
				                error.setMessage(internalEntry.getValue());
				            }
				            validationException.getErrors().add(error);
				        }		
				}
				homeInsuranceVO = (HomeInsuranceVO) homeInsuranceSvcHandler.loadHomeInsuranceDetails(homeInsuranceVO);
				createHomeQuoteResponse.setErrors(validationException.getErrors());
				homeCreateQuoteResponseMapper.mapVOToResponse(homeInsuranceVO,createHomeQuoteResponse);
				webServiceAuditMapper.mapCreateHomeQuoteToAudit(createHomeQuoteRequest, createHomeQuoteResponse,headerInfo,homeInsuranceVO);
				return createHomeQuoteResponse;
			}
			else{

				homeInsuranceVO = (HomeInsuranceVO) homeInsuranceSvcHandler.loadHomeInsuranceDetails(homeInsuranceVO);
				if (!Utils.isEmpty(homeInsuranceVO.getBuildingDetails()) && !Utils.isEmpty(homeInsuranceVO.getBuildingDetails().getMortgageeName())) {
					String mortgage[] = homeInsuranceVO.getBuildingDetails().getMortgageeName().split("#");
					if(mortgage.length > 1){request.setAttribute(com.Constant.CONST_MORTGAGEOTHERS, mortgage[1]);}
					homeInsuranceVO.getBuildingDetails().setMortgageeName(mortgage[0]);
				}
				if (!(Utils.isEmpty(partnerName))) {
					if (Utils.isEmpty(homeInsuranceVO.getGeneralInfo())) {
						GeneralInfoVO generalInfo = new GeneralInfoVO();
						homeInsuranceVO.setGeneralInfo(generalInfo);
					}
					if(Utils.isEmpty(homeInsuranceVO.getGeneralInfo().getSourceOfBus()))
					{
					SourceOfBusinessVO sourceOfBusinessVO = new SourceOfBusinessVO();
					sourceOfBusinessVO.setPartnerName(partnerName);
					homeInsuranceVO.getGeneralInfo().setSourceOfBus(
							sourceOfBusinessVO);
					}
					else
					{
						homeInsuranceVO.getGeneralInfo().getSourceOfBus().setPartnerName( partnerName ); 
					}
					homeInsuranceVO = (HomeInsuranceVO) HomeInsuranceSvcHandler
							.loadPartnerMgmtDetails(homeInsuranceVO);
					// Added by Vishwa to enable the CSS change for partner
					request.setAttribute(com.Constant.CONST_PARTNERNAMECSS, partnerName); 
					LOGGER.info("Successfully saved..");
				}
				createHomeQuoteResponse.setErrors(validationException.getErrors());
				homeCreateQuoteResponseMapper.mapVOToResponse(homeInsuranceVO,createHomeQuoteResponse);
				webServiceAuditMapper.mapCreateHomeQuoteToAudit(createHomeQuoteRequest, createHomeQuoteResponse,headerInfo,homeInsuranceVO);
				return createHomeQuoteResponse;
			}
		} catch( BusinessException e ){
			validationError.setMessage(e.getMessage());
			validationException.getErrors().add(validationError);
			createHomeQuoteResponse.setErrors(validationException.getErrors());
			LOGGER.error( e.getMessage(), e );
			CommonHandler.renderErrorMessages( bindingResult , e.getMessage() );
		} catch( Exception e ) {
			e.printStackTrace();
			validationError.setMessage(e.getMessage());
			validationException.getErrors().add(validationError);
			createHomeQuoteResponse.setErrors(validationException.getErrors());
			if(Utils.isEmpty( partnerName ))
			{
				CommonHandler.renderErrorMessages( bindingResult, Utils.getAppErrorMessage( "pasb2c.quote.home.error" ) );
			}
			else
			{
				CommonHandler.renderErrorMessages( bindingResult, Utils.getAppErrorMessage( "pasb2c.quote.home.partner.error" ).replace( com.Constant.CONST_CALL_CENTER, homeInsuranceVO.getGeneralInfo().getSourceOfBus().getCallCentreNo() ) );
			}
			LOGGER.error( e.getMessage(), e );
		}
		updateCoverDetails(homeInsuranceVO);
		request.setAttribute( AppConstants.PAGE_VALUE, homeInsuranceVO );
		request.setAttribute( AppConstants.COVERS,homeInsuranceVO.getCovers() );
		request.setAttribute( AppConstants.BUILDING,homeInsuranceVO.getBuildingDetails() );
		
		List<CoverDetailsVO> coverDetailsVOConts = new ArrayList<CoverDetailsVO>();
		List<CoverDetailsVO> coverDetailsVOPPs = new ArrayList<CoverDetailsVO>();
		
		for (CoverDetailsVO coverDetailsVO : homeInsuranceVO.getCovers()) {
			
			if(coverDetailsVO.getRiskCodes().getRiskType().equals( SvcConstants.HOME_CONTENT_RISK_TYPE ) && coverDetailsVO.getRiskCodes().getRiskCat().equals( SvcConstants.HOME_LIST_ITEM_RISK_CATEGORY ) && coverDetailsVO.getCoverCodes().getCovCode() == SvcConstants.DEFAULT_COVER_CODE){
				
				coverDetailsVOConts.add(coverDetailsVO);
				
			}else if(coverDetailsVO.getRiskCodes().getRiskType().equals( SvcConstants.HOME_PERSONAL_POS_RISK_TYPE ) && coverDetailsVO.getRiskCodes().getRiskCat().equals( SvcConstants.HOME_LIST_ITEM_RISK_CATEGORY ) && coverDetailsVO.getCoverCodes().getCovCode() == SvcConstants.DEFAULT_COVER_CODE){
				coverDetailsVOPPs.add(coverDetailsVO);
			}
		}
		
		try{
			
			request.setAttribute( com.Constant.CONST_CONTENTLISTITEMS, AppUtils.createJSONForHomeListDetails( coverDetailsVOConts) );
			request.setAttribute( com.Constant.CONST_PERSPOSLISTITEMS, AppUtils.createJSONForHomeListDetails( coverDetailsVOPPs ));
			request.setAttribute( com.Constant.CONST_ISLOADOPERATION, "true" );
		}
		catch( JSONException e ){
			validationError.setMessage(e.getMessage());
			validationException.getErrors().add(validationError);
			createHomeQuoteResponse.setErrors(validationException.getErrors());
			e.printStackTrace();
		}
		LOGGER.info("Successfully mapped...");
		
		return createHomeQuoteResponse;
		//return new ModelAndView("homeRiskDetails", "homeInsuranceVO", homeInsuranceVO);
    }

	private void updateCoverDetails(PolicyDataVO homeInsuranceData){
		
		if(!Utils.isEmpty(((HomeInsuranceVO)homeInsuranceData).getCovers())){
			Iterator it = ((HomeInsuranceVO)homeInsuranceData).getCovers().iterator();
			while(it.hasNext()){
				CoverDetailsVO cover = (CoverDetailsVO) it.next();
				if( (Utils.isEmpty( cover.getSumInsured() ) || Utils.isEmpty( cover.getSumInsured().getSumInsured() ) 
						|| ( cover.getSumInsured().getSumInsured() <= 0	
						&& Utils.isEmpty( cover.getSumInsured().geteDesc() ) ) ) 
						&& !AppConstants.STATUS_ON.equalsIgnoreCase( cover.getIsCovered() ) ){
					it.remove();
				}
			}
		}
	}
	
	
	
	@RequestMapping( value = "**/RetrieveHomeQuotebyID",method = RequestMethod.POST)
    public @ResponseBody UpdateHomeQuoteResponse retrieveHomeQuoteByQuoteId(@RequestBody RetrieveQuoteByQuoteId retrieveQuoteByQuoteId,@ModelAttribute("homeInsuranceVO") HomeInsuranceVO homeInsuranceVO,BindingResult bindingResult, 
    		HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		
		LOGGER.info("Load request for Home Insurance details started");
		//Setting from the JSON CreateHomeQuoteResponse object to homeInsuranceVO object:
		UpdateHomeQuoteResponse updateHomeQuoteResponse=new UpdateHomeQuoteResponse();
		ValidationException validationException = new ValidationException();
	    validationException = new RetriveQuoteByQuoteIdValidator().validate(retrieveQuoteByQuoteId);
	    ValidationError validationError = new ValidationError();
	  		
	    //03.09.2020 CTS  TFS#42591 - Retrieve Quote for Invalid or incorrect quote number is not returning a valid error message- start
	    List<Object[]> resultSet = null;
	    List<ValidationError> errors_val = new ArrayList<ValidationError>();
  		
  		resultSet = DAOUtils.getSqlResultForPas( QueryConstants.GET_QUOTE_STATUS_BY_QUO_NO,  retrieveQuoteByQuoteId.getQuotationNo());
  		if( Utils.isEmpty( resultSet ) || resultSet.size() == 0 
  				|| (ValidationUtil.countDigits(retrieveQuoteByQuoteId.getQuotationNo())>9)){
	  			
	  			ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_QUOTATIONNO, "WS_322");
	  			errors_val.add(error);
	  			validationException.setErrors(errors_val);
		  		if(validationException.getErrors() != null && !Utils.isEmpty(validationException.getErrors())) {
		  	        	updateHomeQuoteResponse.setErrors(validationException.getErrors());
		  	            return updateHomeQuoteResponse;
		  	        }
	  		}
	  		
   
  		//03.09.2020 CTS  TFS#42591 END
	    
	    
	    //Web-Services Validation starts.
	    HeaderInfo headerInfo = new HeaderInfo();
		headerInfo.setHeaderInfo(WSAppUtils.setHeaderInfo(request));
	    if(validationException.getErrors() != null && !Utils.isEmpty(validationException.getErrors())) {
	    	 updateHomeQuoteResponse.setErrors(validationException.getErrors());
	         return updateHomeQuoteResponse;
	    }
        //Web-Services Validation ends.
		CommonVO commonVO = null;
		HomeInsuranceVO homeinInsuranceVO = null;
		UserProfile userProfile = null;
		String quoteNo = null;
		String partnername = "";
		
		if(Utils.isEmpty(homeInsuranceVO.getCommonVO())){
			homeInsuranceVO.setCommonVO(new CommonVO());
		}
		if(Utils.isEmpty(homeInsuranceVO.getGeneralInfo())){
			homeInsuranceVO.setGeneralInfo(new GeneralInfoVO());
		}
		if(Utils.isEmpty(homeInsuranceVO.getGeneralInfo().getSourceOfBus())){
			homeInsuranceVO.getGeneralInfo().setSourceOfBus(new SourceOfBusinessVO());
		}
		if(Utils.isEmpty(homeInsuranceVO.getGeneralInfo().getInsured())){
			homeInsuranceVO.getGeneralInfo().setInsured(new InsuredVO());
		}
		
		//String partnerId = request.getParameter("partnerId")== null ?"":(String) request.getParameter("partnerId");		/* commented unused variable - sonar violation fix*/
		//String partnerName = request.getParameter("partnerName")== null ?"":(String) request.getParameter("partnerName");
		request.setAttribute(com.Constant.CONST_PARTNERNAMECSS, partnername); //For Css
		try{
			setLocation();
			String pmmId = request.getHeader(com.Constant.CONST_PARTNERID);
			webServiceAuditMapper.mapStartTimeForAudit(pmmId);
			// Code added for if CLICK HERE from email start
			if (!Utils.isEmpty(request.getParameter(AppConstants.QUOTE_NUM_REQ_PARAM)) && !Utils.isEmpty(request.getParameter(AppConstants.EMAIL_REQ_PARAM))) {
			
				Long quoteNumber = null;
				String emailid = null;
				if (!Utils.isEmpty(request.getParameter(AppConstants.DRUPAL_REQ_PARAM))){
					quoteNumber = new Long(request.getParameter(AppConstants.QUOTE_NUM_REQ_PARAM));
					emailid = request.getParameter(AppConstants.EMAIL_REQ_PARAM);
				} else {
				
					quoteNumber = new Long(AppUtils.encryptAndDecryptData(request.getParameter(AppConstants.QUOTE_NUM_REQ_PARAM), Boolean.FALSE));
					emailid = AppUtils.encryptAndDecryptData(request.getParameter(AppConstants.EMAIL_REQ_PARAM), Boolean.FALSE);
				}
				commonVO = new CommonVO();
				commonVO.setQuoteNo(quoteNumber);
				homeInsuranceVO.setCommonVO(commonVO);
				GeneralInfoVO generalInfoVO = new GeneralInfoVO();
				InsuredVO insuredVO = new InsuredVO();
				insuredVO.setEmailId(emailid);
				generalInfoVO.setInsured(insuredVO);				
				homeInsuranceVO.setGeneralInfo(generalInfoVO);
			}
			// Code added for if CLICK HERE from email end
			if (!(Utils.isEmpty(partnername))) {
				if (Utils.isEmpty(homeInsuranceVO.getGeneralInfo())) {
					GeneralInfoVO generalInfo = new GeneralInfoVO();
					homeInsuranceVO.setGeneralInfo(generalInfo);
				}
				if(Utils.isEmpty(homeInsuranceVO.getGeneralInfo().getSourceOfBus())){
					SourceOfBusinessVO sourceOfBusinessVO = new SourceOfBusinessVO();
					homeInsuranceVO.getGeneralInfo().setSourceOfBus(sourceOfBusinessVO);
				}
				homeInsuranceVO.getGeneralInfo().getSourceOfBus().setPartnerName(partnername);
			}
			/********** Code Starts for Request*********/	
			
			RetrieveQuoteByQuoteIDMapper retrieveQuoteByQuoteIDMapper = new RetrieveQuoteByQuoteIDMapper();
			retrieveQuoteByQuoteIDMapper.mapRequestToVO(retrieveQuoteByQuoteId, homeInsuranceVO);
			/********** Code Ends for Request*********/
			setRequestAttributes( homeInsuranceVO, request );
			if (!(Utils.isEmpty(partnername))) {
				LOGGER.info("HomeController.loadHomeInsuranceDetails method, before calling HomeController.loadPartnerMgmtDetails method.");
			homeInsuranceVO = (HomeInsuranceVO) HomeInsuranceSvcHandler
					.loadPartnerMgmtDetails(homeInsuranceVO);
			if (Utils.isEmpty(request.getSession(false).getAttribute(
					AppConstants.SESSION_USER_PROFILE_VO))){
				userProfile = UserProfileHandler.getUserProfileVo(homeInsuranceVO.getGeneralInfo().getExtAccExecCode());
				}
			else
			{
				userProfile = (UserProfile) request.getSession(false).getAttribute(AppConstants.SESSION_USER_PROFILE_VO);
			}
			
			}
			else{
				userProfile = new UserProfile();
			}
			request.getSession( false ).setAttribute( AppConstants.SESSION_USER_PROFILE_VO, userProfile );
			
			homeInsuranceVO.getCommonVO().setLoggedInUser((User) request.getSession(false).getAttribute(
					AppConstants.SESSION_USER_PROFILE_VO));
			homeInsuranceVO.setLoggedInUser((User) request.getSession(false).getAttribute(
					AppConstants.SESSION_USER_PROFILE_VO));
			HomeInsuranceVO homeInsuranceVOToService = CopyUtils.copySerializableObject( homeInsuranceVO );
			LOGGER.info("HomeController.loadHomeInsuranceDetails method, before calling HomeInsuranceSvcHandler.loadHomeInsuranceDetails method.");
		PolicyDataVO policyDataVO = homeInsuranceSvcHandler.loadHomeInsuranceDetails(homeInsuranceVOToService);
		
		/********** Code Starts for Response*********/
		HomeUpdateQuoteResponseMapper homeUpdateQuoteResponseMapper = new HomeUpdateQuoteResponseMapper();
		homeUpdateQuoteResponseMapper.mapVOToResponse(policyDataVO,updateHomeQuoteResponse);
		/********** Code Ends for Response*********/
		
		if (!(Utils.isEmpty(partnername))) {
			if (Utils.isEmpty(homeInsuranceVO.getGeneralInfo())) {
				GeneralInfoVO generalInfo = new GeneralInfoVO();
				homeInsuranceVO.setGeneralInfo(generalInfo);
			}
			if(Utils.isEmpty(homeInsuranceVO.getGeneralInfo().getSourceOfBus())){
				SourceOfBusinessVO sourceOfBusinessVO = new SourceOfBusinessVO();
				homeInsuranceVO.getGeneralInfo().setSourceOfBus(sourceOfBusinessVO);
			}
			homeInsuranceVO.getGeneralInfo().getSourceOfBus().setPartnerName(partnername);
			
			homeInsuranceVO = (HomeInsuranceVO) HomeInsuranceSvcHandler
					.loadPartnerMgmtDetails(homeInsuranceVO);
			if (Utils.isEmpty(request.getSession(false).getAttribute(
					AppConstants.SESSION_USER_PROFILE_VO))){
				//UserProfile userProfile = new UserProfile();
				userProfile = UserProfileHandler.getUserProfileVo(homeInsuranceVO.getGeneralInfo().getExtAccExecCode());
				request.getSession( false ).setAttribute( AppConstants.SESSION_USER_PROFILE_VO, userProfile );
			}
		}
		if ( !(Utils.isEmpty(homeInsuranceVO.getGeneralInfo().getSourceOfBus())) &&
				!(Utils.isEmpty(homeInsuranceVO.getGeneralInfo().getSourceOfBus().getPartnerId()))) {
			if(!Utils.isEmpty(policyDataVO))
			{
			if (Utils.isEmpty(policyDataVO.getGeneralInfo())) {
				policyDataVO.setGeneralInfo(homeInsuranceVO.getGeneralInfo());
			}
			if(Utils.isEmpty(policyDataVO.getGeneralInfo().getSourceOfBus())){
				policyDataVO.getGeneralInfo().setSourceOfBus(
						homeInsuranceVO.getGeneralInfo().getSourceOfBus());
			}
			policyDataVO.getGeneralInfo().getSourceOfBus().setPartnerId(homeInsuranceVO.getGeneralInfo().getSourceOfBus().getPartnerId());
			policyDataVO.getGeneralInfo().getSourceOfBus().setPartnerName(homeInsuranceVO.getGeneralInfo().getSourceOfBus().getPartnerName());
			policyDataVO.getGeneralInfo().getSourceOfBus().setCallCentreNo( homeInsuranceVO.getGeneralInfo().getSourceOfBus().getCallCentreNo());
			policyDataVO.getGeneralInfo().getSourceOfBus().setReplyToEmailId(homeInsuranceVO.getGeneralInfo().getSourceOfBus().getReplyToEmailId());
			policyDataVO.getGeneralInfo().getSourceOfBus().setCcEmailId( homeInsuranceVO.getGeneralInfo().getSourceOfBus().getCcEmailId() );
			policyDataVO.getGeneralInfo().getSourceOfBus().setSourceOfBusiness( homeInsuranceVO.getGeneralInfo().getSourceOfBus().getSourceOfBusiness() );
			policyDataVO.getGeneralInfo().getSourceOfBus().setFromEmailID( homeInsuranceVO.getGeneralInfo().getSourceOfBus().getFromEmailID() );
			policyDataVO.getGeneralInfo().getSourceOfBus().setDefaultOnlineDiscount(homeInsuranceVO.getGeneralInfo().getSourceOfBus().getDefaultOnlineDiscount());
			policyDataVO.getGeneralInfo().getSourceOfBus().setDefaultAssignToUser(homeInsuranceVO.getGeneralInfo().getSourceOfBus().getDefaultAssignToUser());
			policyDataVO.getGeneralInfo().getSourceOfBus().setFaqUrl(homeInsuranceVO.getGeneralInfo().getSourceOfBus().getFaqUrl());
			policyDataVO.getGeneralInfo().getSourceOfBus().setPolicyTermUrl(homeInsuranceVO.getGeneralInfo().getSourceOfBus().getPolicyTermUrl());
			
			
			policyDataVO.setLoggedInUser((User) request.getSession(false).getAttribute(
					AppConstants.SESSION_USER_PROFILE_VO));
			}
		}
				
		if (!Utils.isEmpty(homeInsuranceVO.getBuildingDetails()) && !Utils.isEmpty(homeInsuranceVO.getBuildingDetails().getMortgageeName())) {
			String mortgage[] = homeInsuranceVO.getBuildingDetails().getMortgageeName().split("#");
			if(mortgage.length > 1){request.setAttribute(com.Constant.CONST_MORTGAGEOTHERS, mortgage[1]);}
			homeInsuranceVO.getBuildingDetails().setMortgageeName(mortgage[0]);
		}

		if(AppUtils.inValidEmailId(policyDataVO,homeInsuranceVO) || Utils.isEmpty( policyDataVO )
				|| !AppUtils.isValidDistributionChannel(policyDataVO, homeInsuranceVO.getGeneralInfo())){
			Errors errors = bindingResult;
			//homeInsuranceVO.getCommonVO().setQuoteNo( null );
			homeInsuranceVO.getGeneralInfo().getInsured().setEmailId( null );
			homeInsuranceVO.getCommonVO().setQuoteNo(null);
			errors.rejectValue( com.Constant.CONST_ERRORMESSAGE, com.Constant.CONST_ERRORMESSAGE_INVALID,AppConstants.INVALID_QUOTE );
			errors.rejectValue( com.Constant.CONST_ERRORMESSAGE, com.Constant.CONST_ERRORMESSAGE_INVALID,AppConstants.INVALID_EMAIL );
		}
			LOGGER.info( "Load request for Home Insurance completed" );
			if( !Utils.isEmpty( policyDataVO ) && !bindingResult.hasErrors() ){
				request.setAttribute( "homeInsuranceVO", (HomeInsuranceVO) policyDataVO );
				homeinInsuranceVO = (HomeInsuranceVO) policyDataVO;
				request.setAttribute( AppConstants.PAGE_VALUE, homeinInsuranceVO );
				request.setAttribute( AppConstants.COVERS, homeinInsuranceVO.getCovers() );
				request.setAttribute( AppConstants.BUILDING, homeinInsuranceVO.getBuildingDetails() );

				if( !Utils.isEmpty( homeinInsuranceVO.getGeneralInfo().getSourceOfBus().getPromoCode() ) ){
					request.setAttribute( "promoCode", homeinInsuranceVO.getGeneralInfo().getSourceOfBus().getPromoCode() );
				}
				// ##START - Added by Dinesh for CR-130750 Royalty feature 
				quoteNo = request.getParameter(AppConstants.QUOTE_NUM_REQ_PARAM);
				if(!Utils.isEmpty(quoteNo) && StringUtils.isNumeric(quoteNo) && !Utils.isEmpty(request.getParameter(AppConstants.EMAIL_REQ_PARAM)) ){
					   if (!Utils.isEmpty(request.getParameter(AppConstants.PROMO_CODE_REQ_PARAM))){
						   LOGGER.info("Going to set PROM CODE VALUE: "+request.getParameter(AppConstants.PROMO_CODE_REQ_PARAM));
						   homeinInsuranceVO.getGeneralInfo().getSourceOfBus().setPromoCode(request.getParameter(AppConstants.PROMO_CODE_REQ_PARAM));
						}else{
							LOGGER.info("Going to set PROM CODE VALUE: Empty");
							homeinInsuranceVO.getGeneralInfo().getSourceOfBus().setPromoCode("");
						}
				}//## END 
				List<CoverDetailsVO> coverDetailsVOConts = new ArrayList<CoverDetailsVO>();
				List<CoverDetailsVO> coverDetailsVOPPs = new ArrayList<CoverDetailsVO>();
				List<StaffDetailsVO> staffDetails = new ArrayList<StaffDetailsVO>();

				for( CoverDetailsVO coverDetailsVO : homeinInsuranceVO.getCovers() ){

					if( coverDetailsVO.getRiskCodes().getRiskType().equals( SvcConstants.HOME_CONTENT_RISK_TYPE ) && coverDetailsVO.getRiskCodes().getRiskCat().equals( 2 )
							&& coverDetailsVO.getCoverCodes().getCovCode() == SvcConstants.DEFAULT_COVER_CODE ){

						coverDetailsVOConts.add( coverDetailsVO );

					}
					else if( coverDetailsVO.getRiskCodes().getRiskType().equals( SvcConstants.HOME_PERSONAL_POS_RISK_TYPE )
							&& coverDetailsVO.getRiskCodes().getRiskCat().equals( 2 ) && coverDetailsVO.getCoverCodes().getCovCode() == SvcConstants.DEFAULT_COVER_CODE ){

						coverDetailsVOPPs.add( coverDetailsVO );

					}
				}
				staffDetails.addAll( homeinInsuranceVO.getStaffDetails() );

				request.setAttribute( com.Constant.CONST_CONTENTLISTITEMS, AppUtils.createJSONForHomeListDetails( coverDetailsVOConts ) );
				request.setAttribute( com.Constant.CONST_PERSPOSLISTITEMS, AppUtils.createJSONForHomeListDetails( coverDetailsVOPPs ) );
				request.setAttribute( "staffDetailsListItems", AppUtils.createJSONForStaffListDetails( staffDetails ) );
				request.setAttribute( com.Constant.CONST_ISLOADOPERATION, "true" );
				
				//request.setAttribute( AppConstants.PROMOTIONAL_CODES, promoCodeDetails.get( "promotionalCodes" ) );
				//request.setAttribute( AppConstants.PROMOTIONAL_DISC, promoCodeDetails.get( "promoDiscount" ) );
				if( !Utils.isEmpty( policyDataVO ) ){
					commonVO = policyDataVO.getCommonVO();
					
					if(!Utils.isEmpty( commonVO )){
						homeInsuranceVO.setCommonVO( commonVO );
					}
				}

				/* Set the referral message when the status is referred*/
				if( AppUtils.isReferred( commonVO ) ){
					ReferralUtils.setReferralMessage( bindingResult, policyDataVO );
				}
				if( commonVO.getStatus().equals( AppConstants.CONVERTED_TO_POL_STATUS ) ){
					Errors errors = bindingResult;
					String errorMessage=null;
					if(Utils.isEmpty( homeinInsuranceVO.getGeneralInfo().getSourceOfBus().getPartnerId()))
					{
					
						 errorMessage = Utils.getAppErrorMessage( com.Constant.CONST_PASB2C_POLICYQUOTE_RETRIEVED ) + commonVO.getPolicyNo().toString() + ". " + Utils.getAppErrorMessage( "pasb2c.assistance");
					}
					else
					{
						 errorMessage = Utils.getAppErrorMessage( com.Constant.CONST_PASB2C_POLICYQUOTE_RETRIEVED ) + commonVO.getPolicyNo().toString() + ". " + Utils.getAppErrorMessage( "pasb2c.partner.assistance").replace( com.Constant.CONST_CALL_CENTER, homeinInsuranceVO.getGeneralInfo().getSourceOfBus().getCallCentreNo() ) ;
					}
					errors.rejectValue( com.Constant.CONST_ERRORMESSAGE, com.Constant.CONST_ERRORMESSAGE_INVALID, errorMessage );
				}
				if( Short.valueOf( Utils.getSingleValueAppConfig( "REN_QUO_DOC_CODE" ) ).equals( policyDataVO.getCommonVO().getDocCode()) ){
					String contextPath = request.getContextPath();
					response.sendRedirect( contextPath + "/HomeRenewalStep1.do?renQuote="+AppUtils.encryptAndDecryptData( policyDataVO.getCommonVO().getQuoteNo().toString(), Boolean.TRUE ) );
				}
				boolean isValid = retrieveQuoteByQuoteId.getProposalForm();
				//homeInsuranceVO.setValidityStartDate();
				if(isValid){
					long startTime = System.currentTimeMillis();
					LOGGER.debug( "Calling Document Creation:::_3"  + new Date(startTime)   );
					CommonHandler.printProposalForm(homeInsuranceVO);
					byte[] fileContent = null;
					String file = CommonHandler.encodeToString(Utils.getSingleValueAppConfig(com.Constant.CONST_QUOTE_DOC_PROPOSAL_LOC) +homeInsuranceVO.getCommonVO().getQuoteNo() +com.Constant.CONST_QUOTE_PDF);
					fileContent = file.getBytes();
					long endTime = System.currentTimeMillis();
					LOGGER.debug( " Document Creation Done_3"  + new Date(endTime)   );
					LOGGER.debug( "Time taken for Document creation and converting to byte array::_3"  + ( endTime - startTime )  );
					updateHomeQuoteResponse.setDocument(fileContent);
				}
				webServiceAuditMapper.mapRetrieveHomeQuoteToAudit(retrieveQuoteByQuoteId, updateHomeQuoteResponse,headerInfo,homeInsuranceVO);
			}
			//else{
				//homeinInsuranceVO = new HomeInsuranceVO();
				/*if(Utils.isEmpty(homeinInsuranceVO.getScheme())){
					homeinInsuranceVO.setScheme(new SchemeVO());
				}
				if (!(Utils.isEmpty(partnername))) {
					
						if (Utils.isEmpty(homeinInsuranceVO.getGeneralInfo())) {
							GeneralInfoVO generalInfo = new GeneralInfoVO();
							homeinInsuranceVO.setGeneralInfo(generalInfo);
						}
						if(Utils.isEmpty(homeinInsuranceVO.getGeneralInfo().getSourceOfBus()))
						{
							SourceOfBusinessVO sourceOfBusinessVO = new SourceOfBusinessVO();
							homeinInsuranceVO.getGeneralInfo().setSourceOfBus(
									sourceOfBusinessVO);
						}				
						homeinInsuranceVO.getGeneralInfo().getSourceOfBus().setPartnerName( request.getParameter("partnerName") );
			
						homeinInsuranceVO = (HomeInsuranceVO) HomeInsuranceSvcHandler
								.loadPartnerMgmtDetails(homeinInsuranceVO);			
				
				}*/
				//setRequestAttributes( homeinInsuranceVO, request );
			//}

		}
		catch( JSONException e ){
			validationError.setMessage(e.getMessage());
			validationException.getErrors().add(validationError);
			updateHomeQuoteResponse.setErrors(validationException.getErrors());
			e.printStackTrace();
		}
		catch( BusinessException e ) {
			validationError.setMessage(e.getMessage());
			validationException.getErrors().add(validationError);
			updateHomeQuoteResponse.setErrors(validationException.getErrors());
			CommonHandler.renderErrorMessages( bindingResult, e.getMessage() );
			LOGGER.error( e.getMessage(), e );
		}
		catch( Exception e ) {
			validationError.setMessage(e.getMessage());
			validationException.getErrors().add(validationError);
			updateHomeQuoteResponse.setErrors(validationException.getErrors());
			e.printStackTrace();
			if(Utils.isEmpty( homeInsuranceVO.getGeneralInfo().getSourceOfBus()) 
					|| Utils.isEmpty( homeInsuranceVO.getGeneralInfo().getSourceOfBus().getPartnerId()))
			{
				CommonHandler.renderErrorMessages( bindingResult, Utils.getAppErrorMessage( "pasb2c.quote.home.error" ) );
			}
			else
			{
				homeinInsuranceVO = homeInsuranceVO;
				CommonHandler.renderErrorMessages( bindingResult, Utils.getAppErrorMessage( "pasb2c.quote.home.partner.error" ).replace( com.Constant.CONST_CALL_CENTER, homeInsuranceVO.getGeneralInfo().getSourceOfBus().getCallCentreNo() ) );
			}
			LOGGER.error( e.getMessage(), e );
		}
		//CTS - 24/09/2020 - Home Digital UAT Defect - Populate retrieve quote to webservice audit
		homeInsuranceVO.setClassCode(AppConstants.HOME_CLASS_CODE);
		homeInsuranceVO.setPolicyType(AppConstants.HOME_POLICY_TYPE);
		webServiceAuditMapper.mapRetrieveHomeQuoteToAudit(retrieveQuoteByQuoteId, updateHomeQuoteResponse,headerInfo,homeInsuranceVO);
		//CTS - 24/09/2020 - Home Digital UAT Defect - Populate retrieve quote to webservice audit
		return updateHomeQuoteResponse;
    }
	

	//06.10.2020 - CTS - CR#11645-HomeDIgitalAPI - Renewal Changes - Start
	@RequestMapping( value = "*RetrieveHomeQuotebyPolNoEmailId",method = RequestMethod.POST)
    public @ResponseBody RetrieveHomeQuoteByPolicyResponse RetrieveHomeQuotebyPolNoEmailId(@RequestBody RetrieveQuoteByPolicyRequest quoteByPolicyRequest,@ModelAttribute("homeInsuranceVO") HomeInsuranceVO homeInsuranceVO,BindingResult bindingResult, 
    		HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		
		RetrieveHomeQuoteByPolicyResponse quoteByPolicyResponse= new RetrieveHomeQuoteByPolicyResponse();
		BaseRequestVOMapper requestVOMapper = new RetrieveQuoteByPolicyRequestMapper();
		BaseResponseVOMapper responseVOMapper = new RetrieveQuoteByPolicyResponseMapper();
		LOGGER.info("Load request for Home Insurance details started");
		
		
		//Setting from the JSON CreateHomeQuoteResponse object to homeInsuranceVO object:
		ValidationException validationException = new ValidationException();
	    validationException = new RetrieveQuoteByPolicyEmailValidator().validate(quoteByPolicyRequest);
	    ValidationError validationError = new ValidationError();
		homeInsuranceVO.setClassCode(AppConstants.HOME_CLASS_CODE);
		homeInsuranceVO.setPolicyType(AppConstants.HOME_POLICY_TYPE);
		HeaderInfo headerInfo = new HeaderInfo();
		headerInfo.setHeaderInfo(WSAppUtils.setHeaderInfo(request));
	    //Web-Services Validation starts.
	    if(Utils.isEmpty(quoteByPolicyResponse.getErrors())) {
	    	quoteByPolicyResponse.setErrors(new ArrayList<ValidationError>());
	    }	    
	    
	    if(validationException.getErrors() != null && !Utils.isEmpty(validationException.getErrors())) {
	    	quoteByPolicyResponse.setErrors(validationException.getErrors());
	         return quoteByPolicyResponse;
	    }
        //Web-Services Validation ends.
	    List<BigDecimal> quotes = null;
		String pmmId = request.getHeader(com.Constant.CONST_PARTNERID);
		quoteByPolicyRequest.setPmmId(pmmId);
		webServiceAuditMapper.mapStartTimeForAudit(pmmId);
		try {
			requestVOMapper.mapRequestToVO(quoteByPolicyRequest, homeInsuranceVO);
			setRequestAttributesForRenewal(homeInsuranceVO, request, session);
			LOGGER.info("Getting Quotation numbers of "+homeInsuranceVO.getCommonVO().getPolicyNo());
			quotes = AppUtils.getQuoteFromPol(homeInsuranceVO.getCommonVO());
			LOGGER.info("The Quote number is :::"+quotes);
			for (BigDecimal quote : quotes) {
				
			    
				  
				   List<Object[]> resultSet = null;
				    List<ValidationError> errors_val = new ArrayList<ValidationError>();
			  		
			  		resultSet = DAOUtils.getSqlResultForPas( QueryConstants.FETCH_CSH_CUS_EMAIL_ID,  quote.longValue());
			  		if( !Utils.isEmpty( resultSet ) && resultSet.size() != 0 ){
			  			
						String cshEmailId = null;
						cshEmailId =  String.valueOf( resultSet.get( 0 ) ) ;
						
							if((cshEmailId.trim()).toUpperCase().equals((quoteByPolicyRequest.getEmailId().trim()).toUpperCase()) == false ){
					  			ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_POLICYNO + " AND " + com.Constant.CONST_EMAIL_ID_END, "WS_323");
					  			errors_val.add(error);
					  			validationException.setErrors(errors_val);
						  		if(validationException.getErrors() != null && !Utils.isEmpty(validationException.getErrors())) {
						  			quoteByPolicyResponse.setErrors(validationException.getErrors());
						  			webServiceAuditMapper.mapRetrieveHomeQuoteByPolicyEmailToAudit(quoteByPolicyRequest, quoteByPolicyResponse, headerInfo, homeInsuranceVO);
						  	            return quoteByPolicyResponse;
						  	        }
							}
				  		}
				  		
				
				
				CommonVO commonVO = new CommonVO();
				commonVO.setQuoteNo(quote.longValue());
				commonVO.setLoggedInUser(homeInsuranceVO.getCommonVO().getLoggedInUser());
				commonVO.setIsQuote(Boolean.TRUE);
				homeInsuranceVO.setCommonVO(commonVO);
				
				homeInsuranceVO=(HomeInsuranceVO)homeInsuranceSvcHandler.loadHomeInsuranceDetails(homeInsuranceVO);
				responseVOMapper.mapVOToResponse(homeInsuranceVO, quoteByPolicyResponse);
				
				if(!Utils.isEmpty(quoteByPolicyRequest.getProposalForm()) && quoteByPolicyRequest.getProposalForm()==true) {
					for (UpdateHomeQuoteResponse updateHomeQuoteResponse: quoteByPolicyResponse.getQuotes()) {

						if (updateHomeQuoteResponse.getQuotationNo() == quote.longValue() && (updateHomeQuoteResponse
								.getQuoteStatus() == Integer.parseInt(Utils.getSingleValueAppConfig("QUOTE_ACTIVE"))
								|| updateHomeQuoteResponse.getQuoteStatus() == Integer
										.parseInt(Utils.getSingleValueAppConfig("CONV_TO_POL")))) {
							long startTime = System.currentTimeMillis();
							LOGGER.debug("Calling Document Creation:::_4" + new Date(startTime));
							CommonHandler.printProposalForm(homeInsuranceVO);
							byte[] fileContent = null;
							String file = CommonHandler
									.encodeToString(Utils.getSingleValueAppConfig(com.Constant.CONST_QUOTE_DOC_PROPOSAL_LOC)
											+ homeInsuranceVO.getCommonVO().getQuoteNo() + com.Constant.CONST_QUOTE_PDF);
							fileContent = file.getBytes();
							long endTime = System.currentTimeMillis();
							LOGGER.debug(" Document Creation Done_4" + new Date(endTime));
							LOGGER.debug("Time taken for Document creation and converting to byte array::_4"
									+ (endTime - startTime));
							updateHomeQuoteResponse.setDocument(fileContent);
							break;
						}
					}
					
				}
			}

			
		} catch(BusinessException e) {
			
			validationError.setMessage(e.getMessage());
			quoteByPolicyResponse.getErrors().add(validationError);
			e.printStackTrace();
		}catch(SystemException e) {
			ResourceBundle resourceBundle = ResourceBundle.getBundle(com.Constant.CONST_CONFIG_MESSAGES);
			validationError.setMessage(resourceBundle.getString(com.Constant.CONST_CMN_SYSTEMERROR));
			quoteByPolicyResponse.getErrors().add(validationError);
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			ResourceBundle resourceBundle = ResourceBundle.getBundle(com.Constant.CONST_CONFIG_MESSAGES);
			validationError.setMessage(resourceBundle.getString(com.Constant.CONST_CMN_SYSTEMERROR));
			quoteByPolicyResponse.getErrors().add(validationError);
			e.printStackTrace();
			
		}

		webServiceAuditMapper.mapRetrieveHomeQuoteByPolicyEmailToAudit(quoteByPolicyRequest, quoteByPolicyResponse, headerInfo, homeInsuranceVO);
		return quoteByPolicyResponse;
	}
	//06.10.2020 - CTS - CR#11645-HomeDIgitalAPI - Renewal Changes - End
	
	
	@RequestMapping( value = "**/RetrieveHomeQuotebyPolNo",method = RequestMethod.POST)
    public @ResponseBody RetrieveHomeQuoteByPolicyResponse retrieveHomeQuoteByPolicyNo(@RequestBody RetrieveQuoteByPolicyRequest quoteByPolicyRequest,@ModelAttribute("homeInsuranceVO") HomeInsuranceVO homeInsuranceVO,BindingResult bindingResult, 
    		HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		
		RetrieveHomeQuoteByPolicyResponse quoteByPolicyResponse= new RetrieveHomeQuoteByPolicyResponse();
		BaseRequestVOMapper requestVOMapper = new RetrieveQuoteByPolicyRequestMapper();
		BaseResponseVOMapper responseVOMapper = new RetrieveQuoteByPolicyResponseMapper();
		LOGGER.info("Load request for Home Insurance details started");
		
		//Setting from the JSON CreateHomeQuoteResponse object to homeInsuranceVO object:
		ValidationException validationException = new ValidationException();
	    validationException = new RetrieveQuoteByPolicyValidator().validate(quoteByPolicyRequest);
	    ValidationError validationError = new ValidationError();
	    //Web-Services Validation starts.
	    if(Utils.isEmpty(quoteByPolicyResponse.getErrors())) {
	    	quoteByPolicyResponse.setErrors(new ArrayList<ValidationError>());
	    }
	    if(validationException.getErrors() != null && !Utils.isEmpty(validationException.getErrors())) {
	    	quoteByPolicyResponse.setErrors(validationException.getErrors());
	         return quoteByPolicyResponse;
	    }
        //Web-Services Validation ends.
	    List<BigDecimal> quotes = null;
		try {
			requestVOMapper.mapRequestToVO(quoteByPolicyRequest, homeInsuranceVO);
			setRequestAttributesForRenewal(homeInsuranceVO, request, session);
			LOGGER.info("Getting Quotation numbers of "+homeInsuranceVO.getCommonVO().getPolicyNo());
			quotes = AppUtils.getQuoteFromPolicy(homeInsuranceVO.getCommonVO());
			LOGGER.info("The Quote number is :::"+quotes);
			for (BigDecimal quote : quotes) {
				CommonVO commonVO = new CommonVO();
				commonVO.setQuoteNo(quote.longValue());
				commonVO.setLoggedInUser(homeInsuranceVO.getCommonVO().getLoggedInUser());
				commonVO.setIsQuote(Boolean.TRUE);
				homeInsuranceVO.setCommonVO(commonVO);
				
				homeInsuranceVO=(HomeInsuranceVO)homeInsuranceSvcHandler.loadHomeInsuranceDetails(homeInsuranceVO);
				responseVOMapper.mapVOToResponse(homeInsuranceVO, quoteByPolicyResponse);
				
				if(!Utils.isEmpty(quoteByPolicyRequest.getProposalForm()) && quoteByPolicyRequest.getProposalForm()==true) {
					for (UpdateHomeQuoteResponse updateHomeQuoteResponse: quoteByPolicyResponse.getQuotes()) {

						if (updateHomeQuoteResponse.getQuotationNo() == quote.longValue() && (updateHomeQuoteResponse
								.getQuoteStatus() == Integer.parseInt(Utils.getSingleValueAppConfig("QUOTE_ACTIVE"))
								|| updateHomeQuoteResponse.getQuoteStatus() == Integer
										.parseInt(Utils.getSingleValueAppConfig("CONV_TO_POL")))) {
							long startTime = System.currentTimeMillis();
							LOGGER.debug("Calling Document Creation:::_4" + new Date(startTime));
							CommonHandler.printProposalForm(homeInsuranceVO);
							byte[] fileContent = null;
							String file = CommonHandler
									.encodeToString(Utils.getSingleValueAppConfig(com.Constant.CONST_QUOTE_DOC_PROPOSAL_LOC)
											+ homeInsuranceVO.getCommonVO().getQuoteNo() + com.Constant.CONST_QUOTE_PDF);
							fileContent = file.getBytes();
							long endTime = System.currentTimeMillis();
							LOGGER.debug(" Document Creation Done_4" + new Date(endTime));
							LOGGER.debug("Time taken for Document creation and converting to byte array::_4"
									+ (endTime - startTime));
							updateHomeQuoteResponse.setDocument(fileContent);
							break;
						}
					}
					
				}
			}
			
			
		} catch(BusinessException e) {
			
			validationError.setMessage(e.getMessage());
			quoteByPolicyResponse.getErrors().add(validationError);
			e.printStackTrace();
		}catch(SystemException e) {
			ResourceBundle resourceBundle = ResourceBundle.getBundle(com.Constant.CONST_CONFIG_MESSAGES);
			validationError.setMessage(resourceBundle.getString(com.Constant.CONST_CMN_SYSTEMERROR));
			quoteByPolicyResponse.getErrors().add(validationError);
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			ResourceBundle resourceBundle = ResourceBundle.getBundle(com.Constant.CONST_CONFIG_MESSAGES);
			validationError.setMessage(resourceBundle.getString(com.Constant.CONST_CMN_SYSTEMERROR));
			quoteByPolicyResponse.getErrors().add(validationError);
			e.printStackTrace();
			
		}

		
		return quoteByPolicyResponse;
	}
	/**
	 * Ticket 159083 - Webservices Provider implementation for Home and Travel
	 * Method used to retrieve Home Optional Covers.
	 */
	
	@RequestMapping( value = "**/RetrieveHomeOptionalCovers",method = RequestMethod.POST)
    public @ResponseBody RetrieveHomeOptionalCoversResponse retrieveHomeOptionalCovers(@RequestBody RetrieveHomeOptionalCoversRequest homeOptionalCoversRequest,@ModelAttribute("homeInsuranceVO") HomeInsuranceVO homeInsuranceVO,BindingResult bindingResult, 
    		HttpServletRequest request, HttpServletResponse response, HttpSession session,HibernateTemplate ht) {
		LOGGER.info("Entering to retrieveHomeOptionalCovers method");		
		RetrieveHomeOptionalCoversResponse homeOptionalCoversResponse= new RetrieveHomeOptionalCoversResponse();
		BaseResponseVOMapper responseVOMapper = new RetrieveHomeOptionalCoversResponseMapper();
		ValidationException validationException = new ValidationException();
		RetrieveHomeOptionalCoversValidator homeOptionalCoversValidation = new  RetrieveHomeOptionalCoversValidator();
		List<TMasPolicyRating> coverList= null;
		validationException = homeOptionalCoversValidation.validate(homeOptionalCoversRequest);
		if (!Utils.isEmpty(validationException.getErrors())) {
			List<ValidationError> errors = new ArrayList<ValidationError>();
			for (ValidationError validationError : validationException.getErrors()) {
				ValidationError error = new ValidationError();
				error.setCode(validationError.getCode());
				error.setField(validationError.getField());
				error.setMessage(validationError.getMessage());
				errors.add(error);
			}
			homeOptionalCoversResponse.setErrors(errors);
			return homeOptionalCoversResponse;
		}else {
			Integer classCode = homeOptionalCoversRequest.getClassCode();
			Integer policyType = homeOptionalCoversRequest.getPolicyType();
			Integer schemeCode = homeOptionalCoversRequest.getSchemeCode();
			Integer tariffCode = homeOptionalCoversRequest.getTariffCode();
			// Calling getHomeOptionalCovers to get the optional covers from vmasproductconfigpas
			coverList = DAOUtils.getHomeOptionalCovers(homeInsuranceVO.getCommonVO(),classCode,policyType,schemeCode,tariffCode);
			try {
				responseVOMapper.mapVOToResponse(coverList, homeOptionalCoversResponse);
			}catch (SystemException systemException) {
				CommonHandler.renderErrorMessages(bindingResult,
						systemException.getMessage());
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		LOGGER.info("Exiting from retrieveHomeOptionalCovers method--Retrieved Home Covers");
		if(Utils.isEmpty(homeOptionalCoversResponse.getOptionalCovers())) {
			homeOptionalCoversResponse.setStatus(Boolean.FALSE);
			homeOptionalCoversResponse.setMessage("Failure");
		}
		return homeOptionalCoversResponse;
	}
	

	@RequestMapping( value = "/RetrieveHomePolicyByPolicyNo",method = RequestMethod.POST)
    public @ResponseBody CreatePolicyResponse retrieveHomePolicyByPolicyNo(@RequestBody RetrievePolicyByPolicyNo retrievePolicyByPolicyNo,
    		@ModelAttribute("homeInsuranceVO") HomeInsuranceVO homeInsuranceVO,BindingResult bindingResult, HttpServletRequest request,  HttpSession session) throws Exception {
 
		LOGGER.info("Load request for Home Insurance renewal details started");
		
		HomeInsuranceVO insuranceVO = homeInsuranceVO;
		CreatePolicyResponse createPolicyResponse = new CreatePolicyResponse();
		String pmmId = request.getHeader(com.Constant.CONST_PARTNERID);
		webServiceAuditMapper.mapStartTimeForAudit(pmmId);
		HeaderInfo headerInfo = new HeaderInfo();
		headerInfo.setHeaderInfo(WSAppUtils.setHeaderInfo(request));
		String  renQuote = null;
		try{
			if(Utils.isEmpty(homeInsuranceVO.getCommonVO())){
				homeInsuranceVO.setCommonVO(new CommonVO());
			}
			if(Utils.isEmpty(homeInsuranceVO.getGeneralInfo())){
				homeInsuranceVO.setGeneralInfo(new GeneralInfoVO());
			}
			if(Utils.isEmpty(homeInsuranceVO.getGeneralInfo().getSourceOfBus())){
				homeInsuranceVO.getGeneralInfo().setSourceOfBus(new SourceOfBusinessVO());
			}
			if(Utils.isEmpty(homeInsuranceVO.getGeneralInfo().getInsured())){
				homeInsuranceVO.getGeneralInfo().setInsured(new InsuredVO());
			}
			
			setLocation();
			// ##START - Added by Dinesh for CR-130750 Royalty feature 
			if(!Utils.isEmpty(request.getParameter(AppConstants.RENEWAL_QUOTE_NUM_REQ_PARAM))){
				renQuote = request.getParameter(AppConstants.RENEWAL_QUOTE_NUM_REQ_PARAM);
				if(!Utils.isEmpty(renQuote) && !StringUtils.isNumeric(renQuote)){
						renQuote = AppUtils.encryptAndDecryptData( renQuote, Boolean.FALSE );
					}
			}//#END
			/********** Code Starts for Request*********/
			RetrievePolicyByPolicyNoMapper retrievePolicyByPolicyNoMapper = new RetrievePolicyByPolicyNoMapper();
			retrievePolicyByPolicyNoMapper.mapRequestToVO(retrievePolicyByPolicyNo, homeInsuranceVO);
			/********** Code Ends for Request*********/
			setRequestAttributesForRenewal(homeInsuranceVO,request,retrievePolicyByPolicyNo, session);
		}
		catch(Exception e)
		{
			Errors errors = bindingResult;
			errors.rejectValue( com.Constant.CONST_ERRORMESSAGE, com.Constant.CONST_ERRORMESSAGE_INVALID, "Error : " + e.getMessage() );
			
			 ModelAndView view = new ModelAndView();
		     view.addObject( "errorMsg", e.getMessage() );
		     view.setViewName( "redirect:HomeStep1.do" );
	        return createPolicyResponse;
		}	
		
		CommonVO commonVO = null;
		HomeInsuranceVO homeinInsuranceVO = null;
		
		//HomeInsuranceVO homeInsuranceVOToService = CopyUtils.copySerializableObject( homeInsuranceVO );
		PolicyDataVO policyDataVO = homeInsuranceSvcHandler.loadHomeInsuranceDetails(homeInsuranceVO);
	
		if( Utils.isEmpty( policyDataVO )){
			Errors errors = bindingResult;			
			homeInsuranceVO.setCommonVO( null );
			errors.rejectValue( com.Constant.CONST_ERRORMESSAGE, com.Constant.CONST_ERRORMESSAGE_INVALID,AppConstants.INVALID_QUOTE );
			errors.rejectValue( com.Constant.CONST_ERRORMESSAGE, com.Constant.CONST_ERRORMESSAGE_INVALID,AppConstants.INVALID_EMAIL );
		}
		
		LOGGER.info("Load request for Home Insurance completed");
		
		if(!Utils.isEmpty( policyDataVO )){		
			/*request.setAttribute( "homeInsuranceVO", (HomeInsuranceVO)policyDataVO );
			homeinInsuranceVO = (HomeInsuranceVO)policyDataVO*/;
			
			homeinInsuranceVO = (HomeInsuranceVO)policyDataVO;
			if(!Utils.isEmpty( homeinInsuranceVO.getBuildingDetails().getMortgageeName() ) ){
				String[] mortgageeName = homeinInsuranceVO.getBuildingDetails().getMortgageeName().split( "#" );
				if( mortgageeName.length > 1 ){
					homeinInsuranceVO.getBuildingDetails().setMortgageeName( mortgageeName[0] );
					request.setAttribute( com.Constant.CONST_MORTGAGEOTHERS, mortgageeName[1] );
				}
			}
			
			if (bindingResult.hasErrors()) {
				homeinInsuranceVO.getGeneralInfo().setInsured( insuranceVO.getGeneralInfo().getInsured() );
				homeinInsuranceVO.setBuildingDetails( insuranceVO.getBuildingDetails() );
				homeinInsuranceVO.getScheme().setEffDate( insuranceVO.getScheme().getEffDate() );
			}
			
			request.setAttribute( "homeInsuranceVO", homeinInsuranceVO );
			
			try{
				renderHomeRenewalPage(homeinInsuranceVO,request);
				/********** Code Starts for Response*********/
				CreatePolicyResponseMapper createPolicyResponseMapper = new CreatePolicyResponseMapper();
				createPolicyResponseMapper.mapVOToResponse(homeinInsuranceVO,createPolicyResponse);
				
				if(!Utils.isEmpty(retrievePolicyByPolicyNo.getDocuments())) {
					createPolicyResponse.setDocuments(retrievePolicyByPolicyNo.getDocuments());
					if(!Utils.isEmpty(retrievePolicyByPolicyNo.getDocuments().getDocsDetails())) {
						createPolicyResponse.getDocuments().setDocsDetails(retrievePolicyByPolicyNo.getDocuments().getDocsDetails());
					}
				}
				
				if(!Utils.isEmpty(retrievePolicyByPolicyNo.getDocuments()) && retrievePolicyByPolicyNo.getDocuments().getDocsInResponse()==true) {
					long startTime = System.currentTimeMillis();
					LOGGER.debug( "Calling Document Creation:::_5"  + new Date(startTime)   );
					
					byte[] fileContent = null;
					CommonHandler.printPolicyDocument(homeInsuranceVO, retrievePolicyByPolicyNo.getDocuments());
					String policySchedulefile = CommonHandler.encodeToString(Utils.getSingleValueAppConfig("POL_DOC_POL_SCHED_LOC")+homeInsuranceVO.getCommonVO().getPolicyNo()+"-PolicySchedule.pdf");
					fileContent = policySchedulefile.getBytes();
					
					createPolicyResponse.getDocuments().setPolicySchedule(fileContent);
					
					if(retrievePolicyByPolicyNo.getDocuments().getDocsDetails().getLetterToBank()==true) {
						
						CommonOpSvc commonOpSvc = (CommonOpSvc) Utils.getBean("geComSvc");
						BaseVO baseVO = (BaseVO) commonOpSvc.invokeMethod(
								"checkForMortgageeName", homeInsuranceVO.getCommonVO());
						DataHolderVO<Boolean> resultVo = null;
						resultVo = (DataHolderVO<Boolean>) baseVO;
						//CTS - 21/10/2020 - TFS#49060  HOME DIGITAL DEFECT FIX - Generate Documents if not generated during create quote - Starts
						if (!Utils.isEmpty(resultVo) && resultVo.getData()) {
							String fileName = Utils.getSingleValueAppConfig("POL_DOC_BANK_LETTER") +homeInsuranceVO.getCommonVO().getPolicyNo() +"-BankLetter.pdf";
							File bankLetter = new File(fileName);
							if(!bankLetter.isFile()){
								HashMap<String, String> reportParams = new HashMap<String, String>();
								String [] fileNameArr = new String[1];
								ReportTemplateSet reportTemplateSet = ReportTemplateSet._HOME;
								MailVO mailVO = new MailVO();
								PASDocumentService docCreator = (PASDocumentService) Utils.getBean("docServiceBean");
								if(Utils.isEmpty(policyDataVO.getCommonVO().getVsd())){
									policyDataVO.setCommonVO(CommonHandler.getVsdForPolicy(policyDataVO.getCommonVO()));
								}
								reportParams = CommonHandler.addDefaultReportParameters(policyDataVO.getCommonVO());
								reportParams.put(com.Constant.CONST_BANKLETTER_PDF, "true");
								reportParams.put(com.Constant.CONST_DEBITNOTEREPORT, com.Constant.CONST_FALSE);
								reportParams.put(com.Constant.CONST_GROSSCREDITNOTEREPORT,com.Constant.CONST_FALSE);
								reportParams.put(com.Constant.CONST_ENDSCHEDULEREPORT,com.Constant.CONST_FALSE); 
								reportParams.put(com.Constant.CONST_GROSSDEBITNOTEREPORT, com.Constant.CONST_FALSE);
								reportParams.put(com.Constant.CONST_CREDITNOTEREPORT, com.Constant.CONST_FALSE);
								reportParams.put(com.Constant.CONST_POLICYSCHEDULECLAUSES, com.Constant.CONST_FALSE);
								fileNameArr[0] = fileName;
								mailVO.setFileNames(fileNameArr);
								mailVO.setDocParameter(reportParams);
								
								mailVO = (MailVO) docCreator.invokeMethod("createDocument", mailVO);

							}
							//CTS - 21/10/2020 - TFS#49060 HOME DIGITAL DEFECT FIX - Generate Documents if not generated during create quote - Ends

							String bankToLetter = CommonHandler.encodeToString(fileName);
							byte[] bankLetterContent = bankToLetter.getBytes();
							createPolicyResponse.getDocuments().setLetterToBank(bankLetterContent);
						}
						
					}
					
					long endTime = System.currentTimeMillis();
					LOGGER.debug( " Document Creation Done_5"  + new Date(endTime)   );
					LOGGER.debug( "Time taken for Document creation and converting to byte array::_5"  + ( endTime - startTime )  );
					
				}
				webServiceAuditMapper.mapRequestAndReponseToAuditVO(homeinInsuranceVO, retrievePolicyByPolicyNo, createPolicyResponse,headerInfo);
				/********** Code Ends for Response*********/
			}
			catch( JSONException e ){
				Errors errors = bindingResult;
				errors.rejectValue( com.Constant.CONST_ERRORMESSAGE, com.Constant.CONST_ERRORMESSAGE_INVALID, e.getMessage() );
			}			
			AppUtils.setScaleForLOB( homeInsuranceVO.getCommonVO().getLob() );
			
			if(!Utils.isEmpty( policyDataVO )){
				commonVO = policyDataVO.getCommonVO();
			}
			
			 /*Set the referral message when the status is referred*/
			if(AppUtils.isReferred( commonVO )){
				ReferralUtils.setReferralMessage( bindingResult, policyDataVO );
			}
			
			if( !Utils.isEmpty( commonVO ) && commonVO.getStatus().equals( AppConstants.CONVERTED_TO_POL_STATUS ) ){	/* Added null check for commonVO in if condition - sonar violation fix */
				Errors errors = bindingResult;
				String errorMessage = Utils.getAppErrorMessage( com.Constant.CONST_PASB2C_POLICYQUOTE_RETRIEVED ) + commonVO.getPolicyNo().toString() + ". " + Utils.getAppErrorMessage( "pasb2c.assistance");
				errors.rejectValue( com.Constant.CONST_ERRORMESSAGE, com.Constant.CONST_ERRORMESSAGE_INVALID, errorMessage );
			}
		}else{
			homeinInsuranceVO = new HomeInsuranceVO();
		}
		//setVariablesForOmnSiteCat((HomeInsuranceVO)policyDataVO, request);
        // ##START - Added by Dinesh for CR-130750 Royalty feature 
		if(!Utils.isEmpty(renQuote) && StringUtils.isNumeric(renQuote) && !Utils.isEmpty(request.getParameter(AppConstants.EMAIL_REQ_PARAM)) ){
			   if (!Utils.isEmpty(request.getParameter(AppConstants.PROMO_CODE_REQ_PARAM))){
				   homeinInsuranceVO.getGeneralInfo().getSourceOfBus().setPromoCode(request.getParameter(AppConstants.PROMO_CODE_REQ_PARAM));
				}else{
					homeinInsuranceVO.getGeneralInfo().getSourceOfBus().setPromoCode("");
				}
		}//###END		
		return createPolicyResponse;
    }
	
	private void setRequestAttributesForRenewal(HomeInsuranceVO homeInsuranceVO, HttpServletRequest request,RetrievePolicyByPolicyNo retrievePolicyByPolicyNo,HttpSession session) throws Exception{
		BasicAuthenticationService authService = new BasicAuthenticationService();
		String authorization = authService.decodeText(request.getHeader("Authorization"));
		String[] credentials = authService.getUserIdAndPassword(authorization);
		int userId =Integer.parseInt(credentials[0]);
		System.out.println(credentials[0]);
		String renQuote = null;
		if(Utils.isEmpty( homeInsuranceVO.getCommonVO() )){homeInsuranceVO.setCommonVO(new CommonVO());};
		
		if (Utils.isEmpty(request.getSession(false).getAttribute(
				AppConstants.SESSION_USER_PROFILE_VO))){
			UserProfile userProfile = new UserProfile();
			request.getSession( false ).setAttribute( AppConstants.SESSION_USER_PROFILE_VO, userProfile );
		}
		if(Utils.isEmpty( homeInsuranceVO.getCommonVO().getLoggedInUser() )) {
			UserProfile userProfile = new UserProfile();
			userProfile.setRsaUser(new B2CRSAUserWrapper());
			 homeInsuranceVO.getCommonVO().setLoggedInUser(userProfile);
			 homeInsuranceVO.getCommonVO().getLoggedInUser().setUserId(Integer.toString(userId));
		}
		
		homeInsuranceVO.getCommonVO().setIsQuote(Boolean.TRUE);
		homeInsuranceVO.getCommonVO().setLob(LOB.HOME);
		if(Utils.isEmpty(homeInsuranceVO.getScheme())){
			homeInsuranceVO.setScheme(new SchemeVO());
		}
		
		String policy = retrievePolicyByPolicyNo.getPolicyNo().toString();
		String emailId = retrievePolicyByPolicyNo.getEmailId();
		System.out.println("The renewal Quote is "+policy);
		if (! Utils.isEmpty( policy ))
		{
			
			try{			
				System.out.println("Inside the null check "+policy);
				renQuote = homeInsuranceSvcHandler.getQuoteFromPolicy(Long.parseLong(policy),emailId);
				System.out.println("The renewal Quote is "+renQuote);
				if(Utils.isEmpty(renQuote) || renQuote == null){
					renQuote = homeInsuranceSvcHandler.getRenewalQuoteFromPolicy(Long.parseLong(policy),emailId);
				}
			}
			catch(Exception e)
			{
				throw e;
			}	
		}
		else
		{
			renQuote = request.getParameter("renQuote");
			if(!Utils.isEmpty(renQuote) && !StringUtils.isNumeric(request.getParameter(AppConstants.RENEWAL_QUOTE_NUM_REQ_PARAM))){
				renQuote = AppUtils.encryptAndDecryptData( renQuote, Boolean.FALSE );
			}	
		}
		if(! Utils.isEmpty( renQuote )){
			homeInsuranceVO.getCommonVO().setQuoteNo(Long.parseLong(renQuote));
		}
		String mortgageOthers = request.getParameter(com.Constant.CONST_MORTGAGEOTHERS);
		if(!Utils.isEmpty(mortgageOthers)){
			homeInsuranceVO.getBuildingDetails().setMortgageeName(homeInsuranceVO.getBuildingDetails().getMortgageeName() + "#" + mortgageOthers);
		}
		//homeInsuranceVO.getCommonVO().setDocCode(Short.valueOf( Utils.getSingleValueAppConfig( "REN_QUO_DOC_CODE" ) ));
	}
	
	/**
	 * @param homeinInsuranceVO
	 * @param request
	 */
	private void renderHomeRenewalPage( HomeInsuranceVO homeinInsuranceVO, HttpServletRequest request ) throws JSONException{
		
		request.setAttribute( AppConstants.PAGE_VALUE, homeinInsuranceVO );
		request.setAttribute( AppConstants.COVERS,homeinInsuranceVO.getCovers() );
		request.setAttribute( AppConstants.BUILDING,homeinInsuranceVO.getBuildingDetails() );
		
		List<CoverDetailsVO> coverDetailsVOConts = new ArrayList<CoverDetailsVO>();
		List<CoverDetailsVO> coverDetailsVOPPs = new ArrayList<CoverDetailsVO>();
		List<StaffDetailsVO> staffDetails = new ArrayList<StaffDetailsVO>();
		
		for (CoverDetailsVO coverDetailsVO : homeinInsuranceVO.getCovers()) {
			
			if(coverDetailsVO.getRiskCodes().getRiskType().equals( SvcConstants.HOME_CONTENT_RISK_TYPE ) && coverDetailsVO.getRiskCodes().getRiskCat().equals( 2 ) && coverDetailsVO.getCoverCodes().getCovCode() == SvcConstants.DEFAULT_COVER_CODE){
				
				coverDetailsVOConts.add(coverDetailsVO);
				
			}else if(coverDetailsVO.getRiskCodes().getRiskType().equals( SvcConstants.HOME_PERSONAL_POS_RISK_TYPE ) && coverDetailsVO.getRiskCodes().getRiskCat().equals( 2 ) && coverDetailsVO.getCoverCodes().getCovCode() == SvcConstants.DEFAULT_COVER_CODE){
				
				coverDetailsVOPPs.add(coverDetailsVO);
				
			}
		}		
		AppUtils.setQuoteValidDate( homeinInsuranceVO, request );	
		
		staffDetails.addAll( homeinInsuranceVO.getStaffDetails() );
		
		request.setAttribute( "staffDetailsListItems", AppUtils.createJSONForStaffListDetails( staffDetails ) );
		request.setAttribute( com.Constant.CONST_CONTENTLISTITEMS, AppUtils.createJSONForHomeListDetails( coverDetailsVOConts) );
		request.setAttribute( com.Constant.CONST_PERSPOSLISTITEMS, AppUtils.createJSONForHomeListDetails( coverDetailsVOPPs ));
		request.setAttribute( com.Constant.CONST_ISLOADOPERATION, "true" );
		
	}
	
}
