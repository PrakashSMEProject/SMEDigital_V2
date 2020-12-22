/**
 * 
 */
package com.rsaame.pas.b2c.controllers;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.beanmap.BeanMapper;
import com.mindtree.ruc.cmn.exception.BusinessException;
import com.mindtree.ruc.cmn.task.TaskExecutor;
import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.vo.User;
import com.rsaame.pas.b2b.ws.util.WSDAOUtils;
import com.rsaame.pas.b2c.cmn.base.BaseController;
import com.rsaame.pas.b2c.cmn.constants.AppConstants;
import com.rsaame.pas.b2c.cmn.handlers.B2CEmailTriggers;
import com.rsaame.pas.b2c.cmn.handlers.CommonHandler;
import com.rsaame.pas.b2c.cmn.utils.AppUtils;
import com.rsaame.pas.svc.constants.SvcConstants;
import com.rsaame.pas.vo.app.LookUpVO;
import com.rsaame.pas.vo.bus.GeneralInfoVO;
import com.rsaame.pas.vo.bus.LOB;
import com.rsaame.pas.vo.bus.PaymentDetailsVO;
import com.rsaame.pas.vo.bus.PolicyDataVO;
import com.rsaame.pas.vo.bus.PremiumVO;
import com.rsaame.pas.vo.bus.SourceOfBusinessVO;
import com.rsaame.pas.vo.cmn.CommonVO;

/**
 * Controller to handle the generic functionalities  
 * @author Sarath, M1033804
 * @since Phase 3
 *
 */
@Controller
public class CommonController extends BaseController implements ServletContextAware{
	
	private ServletContext servletContext = null;
	@Override
	public void setServletContext(ServletContext arg0) {
	
		servletContext = arg0;
		
	}
	
	private final static Logger LOGGER = Logger.getLogger(CommonController.class);
	CommonHandler handler = new CommonHandler();
	
	/**
	 * Method to invoke the payment gateway form
	 * @param request
	 * @param policyDataVO
	 * @return ModelAndView
	 * @throws UnknownHostException 
	 */
	protected ModelAndView makePayment(HttpServletRequest request, PolicyDataVO policyDataVO) {
		LOGGER.info("Entered into CommonController:makePayment method ");
		Map<String, String> paymentDetails = new HashMap<String, String>();
		
		/*
		 ArrayList premiums.get(0) contains premium from policy_quo table
		 and premium.get(1) contains premium from premium_quo table
		 */
		ArrayList<Double> premiums = WSDAOUtils.getPremiumFromPolicyAndPremiumTable(policyDataVO.getCommonVO().getQuoteNo());
		ResourceBundle resourceBundle = ResourceBundle.getBundle("config.appconfig");
		 Double allowedPremiumDifference = Double.parseDouble(resourceBundle.getString("B2C_ALLOWED_PREMIUM_DIFFERENCE"));
		 
		if(Math.abs(premiums.get(0).doubleValue()-premiums.get(1).doubleValue()) <allowedPremiumDifference && 
				Math.abs(premiums.get(0).doubleValue()-policyDataVO.getPremiumVO().getPremiumAmt()) <allowedPremiumDifference){
			try{		
				paymentDetails.put("customer_ip_address", Inet4Address.getLocalHost().getHostAddress());
				paymentDetails.put("device_fingerprint_id", request.getSession().getId());
				
				LOGGER.info("Entered into CommonController:makePayment method, customer_ip_address: "+Inet4Address.getLocalHost().getHostAddress()+" , device_fingerprint_id:"+request.getSession().getId());
				
				LOGGER.info("CommonController:makePayment method, populating paymentDetails.");
				handler.populatePaymentDetails(policyDataVO, paymentDetails);
				PaymentDetailsVO paymentDetailsvo=new PaymentDetailsVO();
				
				LOGGER.info("CommonController:makePayment method, saving payment request details, by calling handler.saveOnlineRequestPaymentDetails");
				//131378 To Capture Request before going Payment site 
				handler.saveOnlineRequestPaymentDetails(policyDataVO,paymentDetails,paymentDetailsvo);
				
			}
			catch(Exception e){
				e.printStackTrace();
				LOGGER.error("CommonController:makePayment method, Exception in payment gateway Invocation.Exception is :"+e.getMessage());
				throw new BusinessException("paymentGatewayInvokationError", null, "Error while payment request. Please contact system administrator");
			}
			
			
			Boolean isHomeInsurance = policyDataVO.getCommonVO().getLob().equals(LOB.HOME) ? true:false;
			request.setAttribute("isHomeInsurance", isHomeInsurance);
			LOGGER.info( "Redirecting to paymeny gateway.....Ref No " +  paymentDetails.get( "reference_number" ));
			return new ModelAndView("paymentRedirection", "paymentDetails", paymentDetails);
		}
		else {
			LOGGER.error("There is mismatch in premium for this quote.Please contact System Administrator");
			try {
				CommonHandler.paymentFailureEmail(policyDataVO);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			throw new BusinessException("paymentGatewayInvokationError", null, "Premium Mismatch. Error while payment request. Please contact system administrator");
		}
	}
	
	/**
	 * Convert to policy controller
	 * @param request
	 * @return ModelAndView
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/convertToPolicy.do", method = RequestMethod.POST)
    public ModelAndView convertToPolicy(HttpServletRequest request) throws ParseException{
		
		LOGGER.info( "Enter CommonController:convertToPolicy method." );
		PolicyDataVO policyDataVO = new PolicyDataVO();
		boolean isPaymentFaliure = false;
		
		LOGGER.info( "CommonController:convertToPolicy, calling capturePaymentResponseData method to form policyDataVO from the request from PAYMENT GATEWAY." );
		capturePaymentResponseData(request, policyDataVO, isPaymentFaliure);
		
		try{
			if(!Utils.isEmpty( policyDataVO.getOnlinePaymentDetailsVO() )){
				LOGGER.info( "CommonController:convertToPolicy, calling CommonHandler:saveOnlinePaymentDetails method to save paymentDetailsVO." );
				handler.saveOnlinePaymentDetails(policyDataVO.getOnlinePaymentDetailsVO());
			}
			
			LOGGER.info( "CommonController:convertToPolicy, calling CommonHandler:convertToPolicy method to convert to policy." );
			policyDataVO = handler.convertToPolicy(policyDataVO,false,request.getRequestURL().toString());
		}
		catch(Exception e)
		{
			e.printStackTrace();
			LOGGER.info( "Convert to policy UnSuccessfull " +  policyDataVO.getCommonVO().getQuoteNo() );
			request.setAttribute( "ConvPolPaymentError", Utils.getAppErrorMessage( "pasb2c.Conv.policy.decline" ));
			ModelAndView modelAndView = processPaymentFailure(request);
    		PolicyDataVO policyDataVOFrmView = (PolicyDataVO) modelAndView.getModel().get(com.Constant.CONST_POLICYDATAVO);
    		if(!Utils.isEmpty( policyDataVOFrmView.getGeneralInfo().getSourceOfBus().getPartnerName() ) ){
				request.getSession(false).setAttribute( com.Constant.CONST_COMMONVO, modelAndView.getModel().get(com.Constant.CONST_COMMONVO));
				request.getSession(false).setAttribute( com.Constant.CONST_POLICYDATAVO, modelAndView.getModel().get(com.Constant.CONST_POLICYDATAVO) );
				request.getSession(false).setAttribute( com.Constant.CONST_REASONCODE, modelAndView.getModel().get(com.Constant.CONST_REASONCODE) );
				request.getSession(false).setAttribute( com.Constant.CONST_TRANSACTIONID,modelAndView.getModel().get(com.Constant.CONST_TRANSACTIONID) );
				request.getSession(false).setAttribute(com.Constant.CONST_REDIRECTIONPAGE,
						modelAndView.getViewName());
				String url = request.getRequestURL().toString();
				url= url.replace(com.Constant.CONST_PAYMENTRESPONSE_DO,  policyDataVOFrmView.getGeneralInfo().getSourceOfBus().getPartnerName() +com.Constant.CONST_THANKYOU_DO);
				modelAndView = new ModelAndView(com.Constant.CONST_REDIRECT_END+url);
			
			}
	        return modelAndView;
		}
        String redirectionPage = policyDataVO.getCommonVO().getLob() == LOB.HOME ? "homeThankYouPage" : "travelThankYouPage";
        LOGGER.info( "Convert to policy completed for quote No " +  policyDataVO.getCommonVO().getQuoteNo() + " and redirected to " + redirectionPage );
        request.setAttribute(com.Constant.CONST_POLICYDATAVO, policyDataVO);
		return new ModelAndView( redirectionPage, com.Constant.CONST_POLICYDATAVO, policyDataVO );
	}
	
	
	@RequestMapping(value = "**/ThankYou.do", method = RequestMethod.GET)
    public ModelAndView thankyouSendRedirect( HttpServletRequest request, HttpServletResponse response) {
		PolicyDataVO policyDataVO=null;
		if (!Utils.isEmpty(request.getSession(false))
				&& !Utils.isEmpty(request.getSession(false).getAttribute(
						com.Constant.CONST_POLICYDATAVO))) {
			 policyDataVO = (PolicyDataVO) request.getSession(false).getAttribute(
					com.Constant.CONST_POLICYDATAVO);
		}
		
		 String redirectionPage = (String)request.getSession(false).getAttribute(com.Constant.CONST_REDIRECTIONPAGE);
		 ModelAndView modelAndview = new ModelAndView(redirectionPage);
		 modelAndview.addObject(com.Constant.CONST_POLICYDATAVO, policyDataVO);
		 if(("homePaymentError").equals(redirectionPage) || ("travelPaymentError").equals(redirectionPage)){
			 modelAndview.addObject(com.Constant.CONST_COMMONVO,  request.getSession(false).getAttribute(
						com.Constant.CONST_COMMONVO));
			 modelAndview.addObject(com.Constant.CONST_REASONCODE, request.getSession(false).getAttribute(
						com.Constant.CONST_REASONCODE));
			 modelAndview.addObject(com.Constant.CONST_TRANSACTIONID, request.getSession(false).getAttribute(
						com.Constant.CONST_TRANSACTIONID));
		 }
			// Added by Vishwa to enable the CSS change for partner
		 	request.setAttribute("partnerNameCss", policyDataVO.getGeneralInfo().getSourceOfBus().getPartnerName());//For Css
	        LOGGER.info( "Convert to policy completed for quote No " +  policyDataVO.getCommonVO().getQuoteNo() );
	        
			return modelAndview;
	}
	
	/** redirects to thank you page
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = com.Constant.CONST_THANKYOU_DO)
    public ModelAndView redirectThankyou(HttpServletRequest request,HttpServletResponse response) throws ParseException{
		
		LOGGER.info( "CommonController:redirectThankyou, redirecting to /ThankYou.do from PAYMENT GATEWAY site." );
		
        HashMap<String, String> params = new HashMap<String, String>();
        @SuppressWarnings( "rawtypes" )
		Enumeration paramsEnum = request.getParameterNames();
        
        LOGGER.info( "CommonController:redirectThankyou, Request param name:value pair details from the request from PAYMET GATEWAY" );
        while (paramsEnum.hasMoreElements()) {
            String paramName = (String) paramsEnum.nextElement();
            String paramValue = request.getParameter(paramName);
            params.put(paramName, paramValue);
            LOGGER.info(paramName + "                        :  " + paramValue);
        }        
				
        String responseCode = params.get("reason_code");
        LOGGER.info("CommonController:redirectThankyou method, responseCode(reason_code): "+responseCode );
        if( Arrays.asList( AppConstants.PAYMENT_SUCCESS_CODES ).contains( responseCode ) ){
        	LOGGER.info( "Payment Success for Ref No " + params.get( "req_reference_number" ) +". Redirecting to Convert to policy" );
        	try{
        		return convertToPolicy(request);
        	}
        	catch(BusinessException e)
        	{
        		LOGGER.info( "Payment Failure for Ref No_1" + params.get( "req_reference_number" ) +". Redirecting to error pag_1" );
        		return processPaymentFailure(request);
        	}
        }
        else{
        	if(Utils.isEmpty( params )){
        		LOGGER.info( "Payment cancelled - No response from Gateway. Redirecting to blank pag_1" );
        		return getCancellationPage(request);
        	}
        	else{
        		LOGGER.info( "Payment Failure for Ref No_2" + params.get( "req_reference_number" ) +". Redirecting to error pag_2" );
        		return processPaymentFailure(request);
        	}
        }
	}
	
	@RequestMapping(value = "**/paymentError.do", method = RequestMethod.POST)
    public ModelAndView processPaymentFailure(HttpServletRequest request) throws ParseException{
		
		LOGGER.info( "Entered into CommonController:processPaymentFailure method.");
		PolicyDataVO policyDataVO = new PolicyDataVO();
		boolean isPaymentFaliure = true;
		capturePaymentResponseData(request, policyDataVO, isPaymentFaliure);
		if(!Utils.isEmpty( policyDataVO.getOnlinePaymentDetailsVO() )){
			LOGGER.info( "CommonController:processPaymentFailure, calling CommonHandler:saveOnlinePaymentDetails to save policyDataVO.");
			handler.saveOnlinePaymentDetails(policyDataVO.getOnlinePaymentDetailsVO());
		}
        String redirectionPage = policyDataVO.getCommonVO().getLob() == LOB.HOME ? "homePaymentError" : "travelPaymentError";
        
        LOGGER.info( "CommonController:processPaymentFailure,  calling CommonHandler.populateAndTriggerEmail to mail payment failure and redirect to " + redirectionPage);
        //CommonHandler.triggerPaymentFailureMail(policyDataVO);
        CommonHandler.populateAndTriggerEmail(policyDataVO, null, B2CEmailTriggers.PAYMENT_FAILURE, null);
        
        ModelAndView view = new ModelAndView();
        view.addObject( com.Constant.CONST_COMMONVO, policyDataVO.getCommonVO() );
        view.addObject( com.Constant.CONST_POLICYDATAVO, policyDataVO );
        view.addObject( com.Constant.CONST_REASONCODE, policyDataVO.getOnlinePaymentDetailsVO().getResponseCode() );
        view.addObject( com.Constant.CONST_TRANSACTIONID, policyDataVO.getOnlinePaymentDetailsVO().getTransactionRefNo() );
        view.setViewName( redirectionPage );
        
        LOGGER.info( "Exiting into CommonController:processPaymentFailure method. ResponseCode:"+policyDataVO.getOnlinePaymentDetailsVO().getResponseCode() +
        		" transactionRefNo:"+policyDataVO.getOnlinePaymentDetailsVO().getTransactionRefNo());
        return view;
	}
		
	@RequestMapping(value = "**/paymentCancelled.do", method = RequestMethod.GET)
    public ModelAndView getCancellationPage(HttpServletRequest request){
		LOGGER.info( "Entered into CommonController:getCancellationPage method.");
		return new ModelAndView("paymentCancellation");
	}
	
	/**
	 * Get the parameters from payment gateway response 
	 * @param request
	 * @param policyDataVO
	 */
	private void capturePaymentResponseData(HttpServletRequest request, PolicyDataVO policyDataVO, boolean isPaymentFaliure){
		
		LOGGER.info("Entered CommonController:capturePaymentResponseData, formation of policyDataVO from PaymentGateWay response data - starts.");
		
        PaymentDetailsVO onlinePaymentDetailsVO = null;
        onlinePaymentDetailsVO = BeanMapper.map( request, PaymentDetailsVO.class );
        policyDataVO.setOnlinePaymentDetailsVO( onlinePaymentDetailsVO );

        CommonVO commonVO = (CommonVO)Utils.getBean("VO_COMMON");
        commonVO.setLob( onlinePaymentDetailsVO.getLob() );
        commonVO.setPolicyId( onlinePaymentDetailsVO.getPolicyId() );
        commonVO.setQuoteNo( onlinePaymentDetailsVO.getQuoteNo() );
        policyDataVO.setCommonVO( commonVO );
        setRequestAttributes(policyDataVO, request);
        PremiumVO premiumVO = new PremiumVO();
        premiumVO.setPremiumAmt( onlinePaymentDetailsVO.getAuthorizedPremiumAmt() );
        if (isPaymentFaliure) {
        	premiumVO.setPremiumAmtActual( onlinePaymentDetailsVO.getRequestedPremiumAmt() ); //Set only for email trigger
        }
        policyDataVO.setPremiumVO( premiumVO );
        policyDataVO.setGeneralInfo(new GeneralInfoVO());
        policyDataVO.getGeneralInfo().setSourceOfBus(new SourceOfBusinessVO());
        policyDataVO.getGeneralInfo().getSourceOfBus().setPartnerName(onlinePaymentDetailsVO.getPartnerName());
        policyDataVO.getGeneralInfo().getSourceOfBus().setPartnerId(onlinePaymentDetailsVO.getPartnerId());
        policyDataVO.getGeneralInfo().getSourceOfBus().setCallCentreNo(onlinePaymentDetailsVO.getPartnerCallCenterNo());
        
        LOGGER.info("CommonController:capturePaymentResponseData method, \nLOB:"+commonVO.getLob()
        		+", policyId:"+commonVO.getPolicyId()+", policyNo:"+commonVO.getPolicyNo()+", QuoteNo:"+commonVO.getQuoteNo()
        		+ ", \nauthorizedPremiumAmt:"+premiumVO.getPremiumAmt()+", requestedPremiumAmt:"+premiumVO.getPremiumAmtActual()
        		+ ", partnerName:"+onlinePaymentDetailsVO.getPartnerName());
        
        setRequestAttributes(policyDataVO, request);        
	}

	/**
	 * Sets request attributes to VO
	 * @param policyDataVO
	 * @param request
	 */
	private void setRequestAttributes(PolicyDataVO policyDataVO, HttpServletRequest request){
		
		if(Utils.isEmpty( policyDataVO.getCommonVO() )){policyDataVO.setCommonVO(new CommonVO());};
		
		policyDataVO.getCommonVO().setLoggedInUser((User) request.getSession(false).getAttribute(
				AppConstants.SESSION_USER_PROFILE_VO));		
	}
	
	
	/** redirects to thank you page
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/PaymentResponse.do")
    public ModelAndView redirectToThankyou(HttpServletRequest request,HttpServletResponse response) throws ParseException{
			
        HashMap<String, String> params = new HashMap<String, String>();
        @SuppressWarnings( "rawtypes" )
		Enumeration paramsEnum = request.getParameterNames();
        while (paramsEnum.hasMoreElements()) {
            String paramName = (String) paramsEnum.nextElement();
            String paramValue = request.getParameter(paramName);
            params.put(paramName, paramValue);
        }
        String responseCode = params.get("reason_code");
        LOGGER.info( "CommonController.redirectToThankyou method : responseCode:"+ responseCode);
        if( Arrays.asList( AppConstants.PAYMENT_SUCCESS_CODES ).contains( responseCode ) ){
        	LOGGER.info( "Payment Success for Ref No " + params.get( "req_reference_number" ) +". Redirecting to Convert to policy" );
        	ModelAndView modelAndView = null;
			try{
        		modelAndView = convertToPolicy(request);
        		PolicyDataVO policyDataVO = (PolicyDataVO)request.getAttribute(com.Constant.CONST_POLICYDATAVO);
    			if(!Utils.isEmpty( policyDataVO.getGeneralInfo().getSourceOfBus().getPartnerName())){
    				request.getSession(false).setAttribute(com.Constant.CONST_REDIRECTIONPAGE,
    						modelAndView.getViewName());
    				String url = request.getRequestURL().toString();
    				url= url.replace(com.Constant.CONST_PAYMENTRESPONSE_DO, policyDataVO.getGeneralInfo().getSourceOfBus().getPartnerName() +com.Constant.CONST_THANKYOU_DO);
    				modelAndView = new ModelAndView(com.Constant.CONST_REDIRECT_END+url);
    				request.getSession(false).setAttribute(com.Constant.CONST_POLICYDATAVO,
    					policyDataVO);
    			}
        	}
        	catch(BusinessException e)
        	{
        		LOGGER.info( "Payment Failure for Ref No_3" + params.get( "req_reference_number" ) +". Redirecting to error pag_3" );
        		return processPaymentFailure(request);
        	}
			catch (NullPointerException e)
			{
				LOGGER.info( "Payment cancelled - No response from Gateway. Redirecting to blank pag_2" );
				return getCancellationPage(request);
			}
			
			return  modelAndView; 
        }
        else{
        	if(Utils.isEmpty( params )){
        		LOGGER.info( "Payment cancelled - No response from Gateway. Redirecting to blank pag_3" );
        		return getCancellationPage(request);
        	}
        	else{
        		LOGGER.info( "Payment Failure for Ref No_4" + params.get( "req_reference_number" ) +". Redirecting to error pag_4" );
        		ModelAndView modelAndView = processPaymentFailure(request);
        		PolicyDataVO policyDataVO = (PolicyDataVO) modelAndView.getModel().get(com.Constant.CONST_POLICYDATAVO);
        		if(!Utils.isEmpty( policyDataVO.getGeneralInfo().getSourceOfBus().getPartnerName() ) ){
    				request.getSession(false).setAttribute( com.Constant.CONST_COMMONVO, modelAndView.getModel().get(com.Constant.CONST_COMMONVO));
    				request.getSession(false).setAttribute( com.Constant.CONST_POLICYDATAVO, modelAndView.getModel().get(com.Constant.CONST_POLICYDATAVO) );
    				request.getSession(false).setAttribute( com.Constant.CONST_REASONCODE, modelAndView.getModel().get(com.Constant.CONST_REASONCODE) );
    				request.getSession(false).setAttribute( com.Constant.CONST_TRANSACTIONID,modelAndView.getModel().get(com.Constant.CONST_TRANSACTIONID) );
    				request.getSession(false).setAttribute(com.Constant.CONST_REDIRECTIONPAGE,
    						modelAndView.getViewName());
    				String url = request.getRequestURL().toString();
    				url= url.replace(com.Constant.CONST_PAYMENTRESPONSE_DO,  policyDataVO.getGeneralInfo().getSourceOfBus().getPartnerName() +com.Constant.CONST_THANKYOU_DO);
    				modelAndView = new ModelAndView(com.Constant.CONST_REDIRECT_END+url);
    			
    			}
        		return modelAndView;
        	}
        }
	}
	
	@RequestMapping( value = "**/downloadPolicyDocs.do" )
	public void downloadPolicyDocs( @ModelAttribute( "PolicyDataVO" ) PolicyDataVO polDataVO, BindingResult bindingResult,
				HttpServletRequest request, HttpServletResponse response ){

		try{
			String actionType = request.getParameter( "actionType" );
			CommonHandler.createDocumentForDownload( polDataVO );
			CommonHandler.downloadGeneratedDocument( polDataVO, response,actionType );
		}
		catch( Exception e ){
			Errors errors = bindingResult;
			errors.rejectValue( com.Constant.CONST_ERRORMESSAGE, com.Constant.CONST_ERRORMESSAGE_INVALID, com.Constant.CONST_ERROR_END + e.getMessage() );
		}

	}
	
	/**
	 * Controller method to download policy wording
	 * 
	 * @param bindingResult
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "**/downloadPolicyWording.do")
	public void downloadPolicyWording( @ModelAttribute( "PolicyDataVO" )
	PolicyDataVO polDataVO, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response ){
		String lob = request.getParameter( "lob" );
		String schemeCode = request.getParameter( "policyScheme" );
		String partnername = request.getRequestURI();
		partnername = request.getRequestURI().replace("/QuoteAndBuy/", "")
				.replace("/downloadPolicyWording.do", "").replace("downloadPolicyWording.do", "");
		String filePath = null;
		try{
			if( !Utils.isEmpty( lob ) )
			{				
				if(Utils.isEmpty( partnername ))
				{
					if( !Utils.isEmpty( schemeCode ) && schemeCode.equals( Utils.getSingleValueAppConfig( "EMIRATES_SCH_CODE")) )
					{
						filePath = servletContext.getRealPath( Utils.getSingleValueAppConfig( "B2C_DOWNLOAD_EMIRATES_POLICY_WORDING_" + lob ) );
					}
					else 
					{
						filePath = servletContext.getRealPath( Utils.getSingleValueAppConfig( "B2C_DOWNLOAD_DIRECT_POLICY_WORDING_" + lob ) );
					}
				}
				else
				{				
					filePath = servletContext.getRealPath( Utils.getSingleValueAppConfig( "B2C_DOWNLOAD_DIRECT_POLICY_WORDING_PARTNER_" + lob ) );
					filePath = filePath.replace(".pdf","_NEW.pdf");
					filePath = filePath.replace( "partnerName", partnername );
				}
				LOGGER.debug("policy wording filePath:"+filePath);
				String fileName = Utils.getSingleValueAppConfig( "B2C_DOWNLOAD_POLICY_WORDING_FILE_NAME_" + lob );
				
				LOGGER.debug("policy wording fileName:"+fileName);
				CommonHandler.downloadStaticDocument( lob, filePath, fileName, response );
			}
		}
		catch( IOException e ){
			LOGGER.error( "Error while downloading policy wording:"+e.getMessage() );
			Errors errors = bindingResult;
			errors.rejectValue( com.Constant.CONST_ERRORMESSAGE, com.Constant.CONST_ERRORMESSAGE_INVALID, com.Constant.CONST_ERROR_END + e.getMessage() );
		}
	}
	
	
	
	
	
	/**
	 * Controller method to download policy wording
	 * 
	 * @param bindingResult
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "**/downloadPolicyWordingOld.do")
	public void downloadPolicyWordingOld( @ModelAttribute( "PolicyDataVO" )
	PolicyDataVO polDataVO, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response ){
		String lob = request.getParameter( "lob" );
		String schemeCode = request.getParameter( "policyScheme" );
		String partnername = request.getRequestURI();
		partnername = request.getRequestURI().replace("/QuoteAndBuy/", "")
				.replace("/downloadPolicyWordingOld.do", "").replace("downloadPolicyWordingOld.do", "");
		String filePath = null;
		try{
			if( !Utils.isEmpty( lob ) )
			{				
				if(Utils.isEmpty( partnername ))
				{
					if( !Utils.isEmpty( schemeCode ) && schemeCode.equals( Utils.getSingleValueAppConfig( "EMIRATES_SCH_CODE")) )
					{
						filePath = servletContext.getRealPath( Utils.getSingleValueAppConfig( "B2C_DOWNLOAD_EMIRATES_POLICY_WORDING_" + lob ) );
					}
					else 
					{
						filePath = servletContext.getRealPath( Utils.getSingleValueAppConfig( "B2C_DOWNLOAD_DIRECT_POLICY_WORDING_" + lob +"_OLD") );
					}
				}
				else
				{				
					filePath = servletContext.getRealPath( Utils.getSingleValueAppConfig( "B2C_DOWNLOAD_DIRECT_POLICY_WORDING_PARTNER_" + lob +"_OLD") );
					filePath = filePath.replace( "partnerName", partnername );
				}
				LOGGER.debug("policy wording filePath:"+filePath);
				String fileName = Utils.getSingleValueAppConfig( "B2C_DOWNLOAD_POLICY_WORDING_FILE_NAME_" + lob+"_OLD" );
				
				LOGGER.debug("policy wording fileName:"+fileName);
				CommonHandler.downloadStaticDocument( lob, filePath, fileName, response );
			}
		}
		catch( IOException e ){
			LOGGER.error( "Error while downloading policy wording:"+e.getMessage() );
			Errors errors = bindingResult;
			errors.rejectValue( com.Constant.CONST_ERRORMESSAGE, com.Constant.CONST_ERRORMESSAGE_INVALID, com.Constant.CONST_ERROR_END + e.getMessage() );
		}
	}
	
	
	
	
	/**
	 * Controller method for print preview
	 * 
	 * @param polDataVO
	 * @param bindingResult
	 * @param request
	 * @param response
	 */
	@RequestMapping( value = "/PrintPreview.do" )
	public void printPreview( @ModelAttribute( "PolicyDataVO" ) PolicyDataVO polDataVO, BindingResult bindingResult,
				HttpServletRequest request, HttpServletResponse response ){

	
		if(!Utils.isEmpty( polDataVO )){
			
			String actionType = request.getParameter( "actionType" );
			try{
				CommonHandler.printProposalForm(polDataVO,response,actionType);
			}
			catch( IOException e ){
				LOGGER.error( "Error while printing quotation:"+e.getMessage() );
				Errors errors = bindingResult;
				errors.rejectValue( com.Constant.CONST_ERRORMESSAGE, com.Constant.CONST_ERRORMESSAGE_INVALID, com.Constant.CONST_ERROR_END + e.getMessage() );
			}
		}

	}

	/**
	 * Controller to fetch the lookup values from DB
	 * @param request
	 * @param response
	 * @param identifier
	 * @param level1
	 * @param level2
	 * @param code
	 * @return JSON string
	 * @throws Exception
	 */
	@RequestMapping(value = "/LookupInfo.do", method = RequestMethod.GET)
	public  @ResponseBody BaseVO getLookupValues( HttpServletRequest request, HttpServletResponse response, 
			@RequestParam("identifier") String identifier, @RequestParam("level1") String level1,
			@RequestParam("level2") String level2, @RequestParam("code") String code) throws Exception{

		BaseVO baseVO = null;
		JSONObject json = null;
		LookUpVO lookUpVO = null;

		try{
			json = new JSONObject();

			lookUpVO=new LookUpVO();
			if(Utils.isEmpty( code )){
				lookUpVO.setCategory(identifier);
				lookUpVO.setLevel1(level1);
				lookUpVO.setLevel2(level2);
				baseVO = TaskExecutor.executeTasks("LOOKUP_INFO", lookUpVO);
			}
			else{
				lookUpVO.setCategory( identifier );
				lookUpVO.setLevel1( "ALL" );
				lookUpVO.setLevel2( "ALL" );
				lookUpVO.setCode( new BigDecimal(code) );
				baseVO= TaskExecutor.executeTasks("LOOKUP_VALUE", lookUpVO);
			}

			if(!Utils.isEmpty( baseVO)){
				json.put("LOV", baseVO);
			}
		}
		catch(Exception exp){
			throw new Exception(exp.getMessage());
		}
		return baseVO;
	}
	
	/**
	 * Redirect the page to the corresponding URL based on the policy type
	 * 
	 * @param request
	 * @param response
	 * @param quoteNumber
	 * @param emailId
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/RetrieveQuoteDetails.do",method = RequestMethod.POST)
	public ModelAndView retireveQuote(HttpServletRequest request, HttpServletResponse response, @RequestParam("quoteNumber") String quoteNumber,@RequestParam("emailId") String emailId) throws IOException{
		LOGGER.debug( "quoteNumber:"+ quoteNumber );
		LOGGER.debug( "Email Id:"+ emailId );
		LOGGER.debug( "Request URL:"+request.getRequestURL() );
		LOGGER.debug( "Request URL:"+request.getRequestURI() );
	
		StringBuffer redirectURL = null;
		
		String productType =  request.getParameter( "product-type" ) ;
		
		String policyType = Utils.getSingleValueAppConfig( productType );
		
		if( !Utils.isEmpty( policyType ) ){
			// Added equals() instead of == to avoid sonar violation on 25-9-2017
			if( Integer.valueOf( policyType ).equals( Integer.valueOf( SvcConstants.HOME_POL_TYPE ) )){
				redirectURL = new StringBuffer( Utils.getSingleValueAppConfig( "B2C_REQUEST_URL_HOME" ) );
				redirectURL.append( "getHomeInsuranceDetails.do" ).append( "?qn=" ).append( AppUtils.encryptAndDecryptData( quoteNumber, Boolean.TRUE ) ).append( "&em=" )
						.append( AppUtils.encryptAndDecryptData( emailId, Boolean.TRUE ) );
			}// Added equals() instead of == to avoid sonar violation on 26-9-2017
			else if( Integer.valueOf( policyType ) .equals( Integer.valueOf( SvcConstants.TRAVEL_POL_TYPE ) )){
				redirectURL = new StringBuffer( Utils.getSingleValueAppConfig( "B2C_REQUEST_URL_TRAVEL" ) );
				
				
				redirectURL.append( "fetchTravelGeneralInfo.do" ).append( "?qn=" ).append( AppUtils.encryptAndDecryptData( quoteNumber, Boolean.TRUE ) ).append( "&em=" )
				.append( AppUtils.encryptAndDecryptData( emailId , Boolean.TRUE ) );
			}// Added equals() instead of == to avoid sonar violation on 27-9-2017
			else if( Integer.valueOf( policyType ).equals(Integer.valueOf( SvcConstants.MOTOR_POL_TYPE ) )){
				redirectURL = new StringBuffer( Utils.getSingleValueAppConfig( "B2C_REQUEST_URL_MOTOR" ) );
				
				
				redirectURL.append( "retrieveQuoteDetails.do" ).append( "?retQuoteNum=" ).append( quoteNumber ).append( "&retQuoteEmail=" )
				.append(emailId) ;
			}

		}
		return new ModelAndView( com.Constant.CONST_REDIRECT_END+String.valueOf( redirectURL ) );
	}
	
	@RequestMapping( value = "/RetrieveQuote.do" )
	public ModelAndView loadRetrieveQuote(HttpServletRequest request, HttpServletResponse response){
		setLocation();
		return new ModelAndView( "retrieveQuote" );
	}
}
